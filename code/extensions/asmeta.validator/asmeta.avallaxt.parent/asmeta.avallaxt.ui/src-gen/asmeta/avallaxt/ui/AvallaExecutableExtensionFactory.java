/*
 * generated by Xtext 2.32.0
 */
package asmeta.avallaxt.ui;

import asmeta.avallaxt.ui.internal.AvallaxtActivator;
import com.google.inject.Injector;
import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * This class was generated. Customizations should only happen in a newly
 * introduced subclass. 
 */
public class AvallaExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {

	@Override
	protected Bundle getBundle() {
		return FrameworkUtil.getBundle(AvallaxtActivator.class);
	}
	
	@Override
	protected Injector getInjector() {
		AvallaxtActivator activator = AvallaxtActivator.getInstance();
		return activator != null ? activator.getInjector(AvallaxtActivator.ASMETA_AVALLAXT_AVALLA) : null;
	}

}