package org.asmeta.atgt.generator.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class ATGTActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "asmeta.atgt.ui"; //$NON-NLS-1$

	// The shared instance
	private static ATGTActivator plugin;
	
	/**
	 * The constructor
	 */
	public ATGTActivator() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static ATGTActivator getDefault() {
		return plugin;
	}

}
