package org.eclipse.dltk.internal.ui.rse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.environment.EnvironmentManager;
import org.eclipse.dltk.core.environment.IEnvironment;
import org.eclipse.dltk.core.environment.IEnvironmentChangedListener;
import org.eclipse.dltk.core.environment.IEnvironmentProvider;
import org.eclipse.dltk.core.internal.rse.RSEEnvironment;
import org.eclipse.dltk.core.internal.rse.RSEEnvironmentProvider;
import org.eclipse.dltk.internal.core.DeltaProcessor;
import org.eclipse.dltk.internal.core.ModelManager;
import org.eclipse.dltk.internal.core.ScriptProject;
import org.eclipse.dltk.internal.core.search.ProjectIndexerManager;
import org.eclipse.rse.core.model.IHost;
import org.eclipse.rse.core.subsystems.CommunicationsEvent;
import org.eclipse.rse.core.subsystems.ICommunicationsListener;
import org.eclipse.rse.core.subsystems.IConnectorService;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

/**
 * @since 2.0
 */
public class RSEConnectionMonitor {

	private static final class ProjectUpdateFamily {
		private final RSEEnvironment environment;

		public ProjectUpdateFamily(RSEEnvironment environment) {
			this.environment = environment;
		}

	}

	private static final class ProjectUpdateJob extends Job {

		private final RSEEnvironment environment;

		private ProjectUpdateJob(RSEEnvironment environment) {
			super("Environment configuration changed. Updating projects.");
			this.environment = environment;
		}

		@Override
		protected IStatus run(IProgressMonitor inputMonitor) {
			final SubMonitor monitor = SubMonitor.convert(inputMonitor, "Checking projects consistency", 100);
			IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
			List<IScriptProject> projectsToProcess = new ArrayList<>();
			SubMonitor m = monitor.newChild(10);
			m.beginTask("Locate projects for environment", projects.length);
			for (IProject project : projects) {
				if (project.isAccessible()) {
					final String envId = EnvironmentManager.getEnvironmentId(project, false);
					if (envId != null) {
						if (envId.equals(environment.getId())) {
							final IScriptProject scriptProject = DLTKCore.create(project);
							projectsToProcess.add(scriptProject);
						}

					}
				}
				m.worked(1);
			}
			IEnvironmentProvider provider = EnvironmentManager.getEnvironmentProvider(RSEEnvironmentProvider.ID);
			if (provider != null && provider instanceof RSEEnvironmentProvider) {
				((RSEEnvironmentProvider) provider).fireAdded(environment);
			}
			SubMonitor mm = monitor.newChild(20);
			mm.beginTask("Indexing projects", projectsToProcess.size() * 3);
			for (IScriptProject project : projectsToProcess) {
				((ScriptProject) project).updateProjectFragments();
				mm.worked(1);
				try {
					DeltaProcessor processor = ModelManager.getModelManager().getDeltaProcessor();
					processor.clearCustomTimestampsFor(project.getProjectFragments());
					processor.checkExternalChanges(new IModelElement[] { project }, mm.newChild(1));
				} catch (ModelException e) {
					DLTKCore.error(e);
				}
				ProjectIndexerManager.indexProject(project);
				mm.worked(1);
			}
			mm.done();
			monitor.done();
			return Status.OK_STATUS;
		}

		@Override
		public boolean belongsTo(Object family) {
			return family instanceof ProjectUpdateFamily
					&& environment.equals(((ProjectUpdateFamily) family).environment);
		}

	}

	private void updateDecorator() {
		Runnable uiTask = new Runnable() {
			
			@Override
			public void run() {
				if (updatingDecorators) {
					return;
				}
				updatingDecorators = true;
				try {
					PlatformUI.getWorkbench().getDecoratorManager().update(RemoteProjectLabelDecorator.ID);
				} finally {
					updatingDecorators = false;
				}
				
			}
		};
		if (Display.getCurrent() != null) {
				uiTask.run();
		} else {
			Display current = PlatformUI.getWorkbench().getDisplay();
			if (!current.isDisposed()) {
				current.asyncExec(uiTask);
			}	
		}
	}

	private boolean updatingDecorators = false;

	private static RSEConnectionMonitor monitor = new RSEConnectionMonitor();
	final private Set<String> eventListenerAdded = Collections.synchronizedSet(new HashSet<>());

	public static void start() {
		new Job("Install RSE Connection Minitor") { //$NON-NLS-1$
			
			@Override
			protected IStatus run(IProgressMonitor progressMonitor) {
				EnvironmentManager.waitInitialized();
				monitor.scanEnvironments();
				EnvironmentManager.addEnvironmentChangedListener(new IEnvironmentChangedListener() {

					@Override
					public void environmentsModified() {
						monitor.scanEnvironments();
					}

					@Override
					public void environmentRemoved(IEnvironment environment) {
						// do nothing
					}

					@Override
					public void environmentChanged(IEnvironment environment) {
						// do nothing
					}

					@Override
					public void environmentAdded(IEnvironment environment) {
						// do nothing
					}
				});
				return Status.OK_STATUS;
			}
		}.schedule();
	}


	protected void scanEnvironments() {
		new Job("Scan environments") { //$NON-NLS-1$
			
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				if (!Platform.isRunning()) {
					return Status.CANCEL_STATUS;
				}
				IEnvironment[] environments = EnvironmentManager.getEnvironments(false);
				for (final IEnvironment env : environments) {
					if (env instanceof RSEEnvironment) {
						final RSEEnvironment rseENV = (RSEEnvironment) env;
						if (eventListenerAdded.add(rseENV.getId())) {
							// Add connection status listener
							IHost host = rseENV.getHost();
							IConnectorService[] services = host.getConnectorServices();
							for (IConnectorService service : services) {
								service.addCommunicationsListener(new ICommunicationsListener() {
									@Override
									public boolean isPassiveCommunicationsListener() {
										return false;
									}

									@Override
									public void communicationsStateChange(CommunicationsEvent e) {
										if (e.getState() == CommunicationsEvent.AFTER_CONNECT) {
											if (rseENV.isConnected()) {
												// Need to update
												// environment.
												rseENV.setTryToConnect(true);
												if (Job.getJobManager().find(new ProjectUpdateFamily(rseENV)).length == 0) {
													ProjectUpdateJob job = new ProjectUpdateJob(rseENV);
													job.setUser(true);
													job.schedule();
												}
											}
										}
										updateDecorator();
									}
								});
							}
						}
					}
				}
				return Status.OK_STATUS;
			}
		}.schedule();
	}
}
