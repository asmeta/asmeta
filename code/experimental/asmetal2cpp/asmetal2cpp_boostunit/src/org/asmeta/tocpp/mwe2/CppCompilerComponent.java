package org.asmeta.tocpp.mwe2;

import java.io.File;

import org.asmeta.asm2code.compiler.CppCompiler;
import org.eclipse.emf.mwe2.runtime.workflow.IWorkflowComponent;
import org.eclipse.emf.mwe2.runtime.workflow.IWorkflowContext;

// questo compila il codice Cpp
public class CppCompilerComponent implements IWorkflowComponent {

	private String name = "pillbox.cpp";

	private File directory = new File("F:\\Dati-Andrea\\GitHub\\quasmed\\PillboxASM\\cpptest");

	public void setDirectory(String dir) {

	}

	@Override
	public void invoke(IWorkflowContext ctx) {
		CppCompiler.compile(name, directory.getAbsolutePath(), true, false);

	}

	@Override
	public void postInvoke() {
	}

	@Override
	public void preInvoke() {
	}
}