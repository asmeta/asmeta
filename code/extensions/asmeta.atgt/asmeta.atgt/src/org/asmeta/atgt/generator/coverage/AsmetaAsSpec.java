package org.asmeta.atgt.generator.coverage;

import java.util.Collection;

import org.asmeta.flattener.AsmetaMultipleFlattener;
import org.asmeta.nusmv.MapVisitor;

import asmeta.AsmCollection;
import asmeta.structure.Asm;
import atgt.specification.ASMSpecification;
import atgt.specification.location.Function;
import tgtlib.specification.Axiom;

// should implement Specification
public class AsmetaAsSpec extends ASMSpecification{	
	
	Asm asm;

	public AsmetaAsSpec(AsmCollection asm) throws Exception {		
		// flatten the spec
		this.asm = AsmetaMultipleFlattener.flatten(asm.getMain(), MapVisitor.ALL_SMV_FLATTENERS); 
	}

	@Override
	public String getName() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Collection<atgt.specification.location.Variable> getVariables() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Collection<Axiom> getAxiom() {
		throw new RuntimeException("not implemented");
	}
	
	@Override
	public Function getFunction(String var) {
		throw new RuntimeException("not implemented");
	}
	
}