package org.eclipse.dltk.internal.ui.rse;

import org.eclipse.rse.ui.RSEUIPlugin;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 * @since 2.0
 */
public class DLTKRSEUI extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.dltk.rse.ui"; //$NON-NLS-1$

	// The shared instance
	private static DLTKRSEUI plugin;

	/**
	 * The constructor
	 */
	public DLTKRSEUI() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		RSEUIPlugin.getDefault();
		RSEConnectionMonitor.start();
		// try {
		// PlatformUI.getWorkbench().addWorkbenchListener(
		// new ShutdownCloseProjectsWithLinkedFiles());
		// } catch (IllegalStateException e) {
		// // IGNORE: workbench has not been created yet.
		// }
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		RSEConnector.stop();
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static DLTKRSEUI getDefault() {
		return plugin;
	}
}
