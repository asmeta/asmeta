package org.asmeta.atgt.generator.ui;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
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

	protected static Logger log = Logger.getLogger("org.asmeta.atgt");
	
	static {
		// to test : activate the logger
		log.setLevel(Level.ALL);
		log.addAppender(new ConsoleAppender());
	}
	
	/**
	 * The constructor
	 */
	public ATGTActivator() {}

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
