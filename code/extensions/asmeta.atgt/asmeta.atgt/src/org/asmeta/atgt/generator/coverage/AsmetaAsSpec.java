package org.asmeta.atgt.generator.coverage;

import java.io.File;
import java.util.Collection;
import java.util.Enumeration;

import org.asmeta.flattener.AsmetaMultipleFlattener;
import org.asmeta.nusmv.MapVisitor;
import org.asmeta.parser.ASMParser;

import asmeta.AsmCollection;
import asmeta.structure.Asm;
import atgt.specification.location.Constant;
import atgt.specification.location.DerivedFunction;
import atgt.specification.location.Function;
import tgtlib.definitions.expression.type.Variable;
import tgtlib.specification.Axiom;
import tgtlib.specification.Specification;

// should implement Specification
//class AsmetaAsSpec extends ASMSpecification{	
public class AsmetaAsSpec implements Specification{
	
	
	Asm asm;

	AsmetaAsSpec(AsmCollection asm) throws Exception {		
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
	//TODO da calcelllare usare quello prima
	public Enumeration<atgt.specification.location.Variable> allVariables() {
		throw new RuntimeException("not implemented");
	} 

	@Override
	public Collection<Axiom> getAxiom() {
		throw new RuntimeException("not implemented");
	}
	
	
	public static AsmetaAsSpec read(File f) throws Exception {
		AsmCollection setUpReadAsm = ASMParser.setUpReadAsm(f);
		return new AsmetaAsSpec(setUpReadAsm);		
	}

	public atgt.specification.location.Variable getVariable(String locationName) {
		throw new RuntimeException("not implemented");
	}

	public Function getFunction(String funName) {
		throw new RuntimeException("not implemented");
	}

	public DerivedFunction getDerivedFunction(String var) {
		throw new RuntimeException("not implemented");
	}

	public Constant getConstantByName(String var) {
		throw new RuntimeException("not implemented");
	}

	
}