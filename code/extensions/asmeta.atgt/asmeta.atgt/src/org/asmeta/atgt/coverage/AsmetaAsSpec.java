package org.asmeta.atgt.coverage;

import java.io.File;
import java.util.Collection;

import org.asmeta.flattener.AsmetaMultipleFlattener;
import org.asmeta.nusmv.MapVisitor;

import asmeta.AsmCollection;
import asmeta.structure.Asm;
import atgt.parser.asmeta.AsmetaLLoader;
import atgt.specification.ASMSpecification;
import atgt.specification.AsmGeneralSpecification;
import tgtlib.definitions.expression.type.Variable;
import tgtlib.specification.Axiom;
import tgtlib.specification.ParseException;
import tgtlib.specification.Specification;
import tgtlib.specification.location.AsmVariable;
import tgtlib.specification.location.Constant;
import tgtlib.specification.location.DerivedFunction;
import tgtlib.specification.location.Function;

/** 
 * new class for the specification of an ASM.  It is based on ASM of asmeta.
 * TO be completed
 * 
 * @author Angelo Gargantini
 *
 */
public class AsmetaAsSpec extends AsmGeneralSpecification {

	Asm asm;
	// using the ASMSpecification as a proxy for the Specification interface
	// TODO push up some methods to the AsmGeneralSpecification class and avoid composition
	private ASMSpecification asmspec;

	public AsmetaAsSpec(AsmCollection asm) throws Exception {
		// flatten the spec
		this.asm = AsmetaMultipleFlattener.flattenWithExtra(asm.getMain(), MapVisitor.ALL_SMV_FLATTENERS);
	}

	public void setAsmspec(ASMSpecification asmspec) {
		this.asmspec = asmspec;
	}

	@Override
	public String getName() {
		return asmspec.getName();
	}

	@Override
	public Collection<? extends Variable> getVariables() {
		return asmspec.getVariables();
	}

	@Override
	public Collection<Axiom> getAxiom() {
		return asmspec.getAxiom();
	}

	@Override
	public AsmVariable getVariable(String locationName) {
		return asmspec.getVariable(locationName);
	}

	@Override
	public Function getFunction(String funName) {
		return asmspec.getFunction(funName);
	}

	@Override
	public Constant getConstantByName(String var) {
		return asmspec.getConstantByName(var);
	}

	@Override
	public DerivedFunction getDerivedFunction(String var) {
		return asmspec.getDerivedFunction(var);
	}

}