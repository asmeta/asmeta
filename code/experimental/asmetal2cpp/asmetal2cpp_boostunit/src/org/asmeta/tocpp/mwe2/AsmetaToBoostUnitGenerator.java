package org.asmeta.tocpp.mwe2;

import org.eclipse.emf.mwe2.runtime.workflow.IWorkflowContext;


// questo rappresenta un code generator for Cunit (boost)
public class AsmetaToBoostUnitGenerator extends AsmetaCodeGenerator{

	@Override
	public void invoke(IWorkflowContext ctx) {		
		System.out.println("generating tests for "+ modelPath);
	}

	@Override
	public void postInvoke() {
	}

	@Override
	public void preInvoke() {
	}
}