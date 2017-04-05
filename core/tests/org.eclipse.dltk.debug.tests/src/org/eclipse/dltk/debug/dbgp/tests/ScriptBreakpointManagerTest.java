package org.eclipse.dltk.debug.dbgp.tests;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.dltk.dbgp.IDbgpSession;
import org.eclipse.dltk.dbgp.breakpoints.DbgpBreakpointConfig;
import org.eclipse.dltk.dbgp.commands.IDbgpCoreCommands;
import org.eclipse.dltk.dbgp.exceptions.DbgpException;
import org.eclipse.dltk.debug.core.IDLTKDebugToolkit2;
import org.eclipse.dltk.debug.core.model.IScriptBreakpointLineMapper;
import org.eclipse.dltk.debug.core.model.IScriptDebugTarget;
import org.eclipse.dltk.debug.core.model.IScriptLineBreakpoint;
import org.eclipse.dltk.debug.core.model.IScriptWatchpoint;
import org.eclipse.dltk.internal.debug.core.model.IScriptBreakpointPathMapperExtension;
import org.eclipse.dltk.internal.debug.core.model.ScriptBreakpointManager;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

public class ScriptBreakpointManagerTest {
	private static final URI inputUri = URI.create("inputURI");
	private static final URI outputURI = URI.create("outputURI");

	private final IScriptDebugTarget target = Mockito.mock(IScriptDebugTarget.class);
	private final IScriptBreakpointPathMapperExtension pathMapper = Mockito.mock(IScriptBreakpointPathMapperExtension.class);
	private final IScriptBreakpointLineMapper lineMapper = Mockito.mock(IScriptBreakpointLineMapper.class);
	private final IBreakpointManager breakpointManager = Mockito.mock(IBreakpointManager.class);
	private final IDbgpSession session = Mockito.mock(IDbgpSession.class);
	private final IDbgpCoreCommands coreCommands = Mockito.mock(IDbgpCoreCommands.class);
	private final ILaunch launch = Mockito.mock(ILaunch.class);
	private final ILaunchConfiguration launchConfiguration = Mockito.mock(ILaunchConfiguration.class);
	private final IDLTKDebugToolkit2 debugToolkit = Mockito.mock(IDLTKDebugToolkit2.class);
	private final String modelId = "modelId";

	{
		when(target.getModelIdentifier()).thenReturn(modelId);
		when(session.getCoreCommands()).thenReturn(coreCommands);
		when(breakpointManager.getBreakpoints(modelId)).thenReturn(new IBreakpoint[0]);
		when(target.getLaunch()).thenReturn(launch);
		when(launch.getLaunchConfiguration()).thenReturn(launchConfiguration);
		when(launchConfiguration.getName()).thenReturn("Launch configuration name");
		when(target.supportsBreakpoint(Matchers.any())).thenReturn(true);
		Mockito.doAnswer(invocation -> {
			DbgpBreakpointConfig config = (DbgpBreakpointConfig) invocation.getArguments()[2];
			config.setLineNo(config.getLineNo() + 10);
			return null;
		})
				.when(lineMapper)
				.toDebuggerBreakpoint(Matchers.any(), Matchers.anyInt(), Matchers.any());

		when(pathMapper.map(inputUri)).thenReturn(outputURI);
	}

	@Test
	public void lineAndPathMappersForScriptLineBreakpoint() throws DbgpException, CoreException {
		IScriptLineBreakpoint scriptBreakpoint = Mockito.mock(IScriptLineBreakpoint.class);
		when(scriptBreakpoint.getResourceURI()).thenReturn(inputUri);
		when(scriptBreakpoint.getLineNumber()).thenReturn(5);
		when(coreCommands.setLineBreakpoint(Matchers.eq(outputURI), Matchers.eq(15), Matchers.any())).thenReturn("bpId");

		ScriptBreakpointManager subject = createSubject();
		subject.breakpointAdded(scriptBreakpoint);
		join();
		Mockito.verify(coreCommands).setLineBreakpoint(Matchers.eq(outputURI), Matchers.eq(15), Matchers.any());
		Mockito.verify(scriptBreakpoint).setId(session, "bpId");

		when(scriptBreakpoint.getLineNumber()).thenReturn(6);
		subject.breakpointChanged(scriptBreakpoint, null);
		join();
		Mockito.verify(coreCommands).setLineBreakpoint(Matchers.eq(outputURI), Matchers.eq(16), Matchers.any());
		Mockito.verifyNoMoreInteractions(coreCommands);
	}

	@Test
	public void lineAndPathMappersForWatchPoint() throws DbgpException, CoreException {
		IScriptWatchpoint scriptBreakpoint = Mockito.mock(IScriptWatchpoint.class);
		when(scriptBreakpoint.getResourceURI()).thenReturn(inputUri);
		when(scriptBreakpoint.getLineNumber()).thenReturn(5);
		when(coreCommands.setWatchBreakpoint(Matchers.eq(outputURI), Matchers.eq(15), Matchers.any())).thenReturn("bpId");

		ScriptBreakpointManager subject = createSubject();
		subject.breakpointAdded(scriptBreakpoint);
		join();
		Mockito.verify(coreCommands).setWatchBreakpoint(Matchers.eq(outputURI), Matchers.eq(15), Matchers.any());
		Mockito.verifyNoMoreInteractions(coreCommands);
		Mockito.verify(scriptBreakpoint).setId(session, "bpId");

		when(scriptBreakpoint.getLineNumber()).thenReturn(6);
		subject.breakpointChanged(scriptBreakpoint, null);
		join();
		verify(coreCommands).setWatchBreakpoint(Matchers.eq(outputURI), Matchers.eq(16), Matchers.any());
		Mockito.verifyNoMoreInteractions(coreCommands);
	}

	@Test
	public void pathMapperForFirstSuspend() throws DbgpException {
		ScriptBreakpointManager subject = createSubject();
		subject.setBreakpointUntilFirstSuspend(inputUri, 5);
		join();
		// Line should be 15, a bug in line mapper
		verify(coreCommands).setLineBreakpoint(Matchers.eq(outputURI), Matchers.eq(5), Matchers.any());
	}

	@Test
	public void threadTerminated() {
		ScriptBreakpointManager subject = createSubject();
		subject.threadTerminated();
		join();
		verify(pathMapper).clearCache();
	}

	private static void join() {
		IJobManager jm = Job.getJobManager();
		try {
			for (Job job : jm.find(null)) {
				int state = job.getState();
				if (state != Job.SLEEPING)
					job.join(1000, null);
			}
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private ScriptBreakpointManager createSubject() {
		ScriptBreakpointManager rv = new ScriptBreakpointManager(
				target,
				pathMapper,
				lineMapper,
				breakpointManager, ignored -> debugToolkit);
		rv.initializeSession(session, new NullProgressMonitor());
		return rv;
	}

}
