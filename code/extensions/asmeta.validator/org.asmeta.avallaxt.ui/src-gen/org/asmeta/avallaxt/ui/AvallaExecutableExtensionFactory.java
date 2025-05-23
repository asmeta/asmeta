/*
 * generated by Xtext 2.36.0
 */
package org.asmeta.avallaxt.ui;

import com.google.inject.Injector;
import org.asmeta.avallaxt.ui.internal.AvallaxtActivator;
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
		return activator != null ? activator.getInjector(AvallaxtActivator.ORG_ASMETA_AVALLAXT_AVALLA) : null;
	}

}
