package org.asmeta.atgt.generator.coverage;

import java.util.Collection;

import org.asmeta.flattener.AsmetaMultipleFlattener;
import org.asmeta.nusmv.MapVisitor;

import asmeta.AsmCollection;
import asmeta.structure.Asm;
import tgtlib.definitions.expression.type.Variable;
import tgtlib.specification.Axiom;
import tgtlib.specification.Specification;

// should implement Specification
//class AsmetaAsSpec extends ASMSpecification{
	
class AsmetaAsSpec implements Specification{
	
	
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
	public Collection<? extends Variable> getVariables() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Collection<Axiom> getAxiom() {
		throw new RuntimeException("not implemented");
	}	
}