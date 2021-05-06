package org.asmeta.nusmv.plugin.handlers;

import org.asmeta.nuxmv.AsmetaSMV;
import org.asmeta.nuxmv.AsmetaSMVOptions;

/**
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class AsmetaSMVtranslateAndExecHandler extends AsmetaSMVHandler {

	@Override
	void exec(AsmetaSMV asmetaSMV) throws Exception {
		AsmetaSMVOptions.setPrintNuSMVoutput(true);
		asmetaSMV.executeNuSMV();		
	}
}