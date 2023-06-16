package validator.plugin;

import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.apache.log4j.WriterAppender;
import org.asmeta.eclipse.AsmeeConsole;
import org.asmeta.eclipse.AsmetaUtility;
import org.asmeta.xt.validator.AsmetaFromAvallaBuilder;
import org.asmeta.xt.validator.AsmetaV;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.console.IOConsoleOutputStream;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import validator.plugin.handlers.AsmetaVConsole;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "validator.plugin"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	private void setupLogging() {
		// set the log4j levels
		Logger.getLogger(AsmetaFromAvallaBuilder.class).setLevel(Level.ALL);
		Logger.getLogger(AsmetaV.class).setLevel(Level.ALL);
		// BasicConfigurator.configure();
		Logger log = Logger.getRootLogger();
		// log.setLevel(Level.INFO);
		// get the console and activate it
		AsmetaVConsole myConsole = AsmetaUtility.findConsole(AsmetaVConsole.class);
		// set the outstream
		IOConsoleOutputStream out = myConsole.newOutputStream();
		Appender consoleApp = new WriterAppender(new SimpleLayout(),out);
		log.addAppender(consoleApp);		
		
		
/*		// 03/03/2021 - Andrea
		// Delete all the appenders of the root logger except a single ConsoleAppender
		// org.eclipse.xtext.logging.EclipseLogAppender
		Enumeration<?> allAppenders = log.getAllAppenders();
		// if there is one ConsoleAppender
		if (Collections.list(allAppenders).stream().filter(x -> (x instanceof ConsoleAppender)).count() > 1) {
			// should a ConsoleAppendere
			java.util.Optional<?> app = Collections.list(allAppenders).stream()
					.filter(x -> (x instanceof ConsoleAppender)).findFirst();
			if (app != null) {
				ConsoleAppender newConsoleApp;
				if (!app.isEmpty()) {
					ConsoleAppender consoleApp = (ConsoleAppender) app.get();
					newConsoleApp = new ConsoleAppender(consoleApp.getLayout(), consoleApp.getTarget());					
				} else {
					PatternLayout l = new org.apache.log4j.PatternLayout();
					newConsoleApp = new ConsoleAppender(l);
				}
				log.removeAllAppenders();
				log.addAppender(newConsoleApp);
			}
		}*/

		/*
		 * while(it.hasMoreElements()) { ((Appender)it.nextElement()).setLayout(new
		 * PatternLayout()); }
		 */
		
		
	}
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		//set up loggin
		setupLogging();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
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
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
