package org.asmeta.tocpp.mwe2;

import org.eclipse.emf.mwe2.runtime.workflow.IWorkflowComponent;
import org.eclipse.emf.mwe2.runtime.workflow.IWorkflowContext;


// questo rappresenta un code generator
public class AsmetaCodeGenerator implements IWorkflowComponent {

	protected String modelPath;

	public void setAmsetaModel(String modelPath) {
		this.modelPath = modelPath;
	}

	public String getAmsetaModel() {
		return modelPath;
	}

	@Override
	public void invoke(IWorkflowContext ctx) {		
		System.out.println("leggo del file "+ modelPath);
	}

	@Override
	public void postInvoke() {
	}

	@Override
	public void preInvoke() {
	}
}