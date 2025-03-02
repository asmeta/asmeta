package org.asmeta.nusmv.plugin.handlers;

import org.asmeta.nusmv.main.AsmetaSMV;

/**
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class AsmetaSMVtranslateHandler extends AsmetaSMVHandler {

	public AsmetaSMVtranslateHandler() {
		super("model checking (no execution)");
	}

	@Override
	void exec(AsmetaSMV asmetaSMV) throws Exception {
		// do nothing (no execution)
	}
}