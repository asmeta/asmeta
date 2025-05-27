package org.asmeta.tocpp.mwe2;

import org.eclipse.emf.mwe2.runtime.workflow.IWorkflowContext;


// questo rappresenta un code generator for Cpp
public class AsmetaToCppGenerator extends AsmetaCodeGenerator{

	@Override
	public void invoke(IWorkflowContext ctx) {		
		System.out.println("genero cpp code for "+ modelPath);
	}

	@Override
	public void postInvoke() {
	}

	@Override
	public void preInvoke() {
	}
}