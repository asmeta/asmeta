package org.asmeta.atgt.coverage;

import java.util.Collection;

import org.asmeta.flattener.AsmetaMultipleFlattener;
import org.asmeta.nusmv.MapVisitor;

import asmeta.AsmCollection;
import asmeta.structure.Asm;
import atgt.specification.location.Constant;
import atgt.specification.location.DerivedFunction;
import atgt.specification.location.Function;
import tgtlib.definitions.expression.type.Variable;
import tgtlib.specification.Axiom;
import tgtlib.specification.Specification;

/** new class for the specification of an ASM.  It sis based on ASM of asmeta.
 * 
 * @author Angelo Gargantini
 *
 */
public class AsmetaAsSpec implements Specification {

	Asm asm;

	public AsmetaAsSpec(AsmCollection asm) throws Exception {
		// flatten the spec
		this.asm = AsmetaMultipleFlattener.flattenWithExtra(asm.getMain(), MapVisitor.ALL_SMV_FLATTENERS);
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

	@Override
	public atgt.specification.location.Variable getVariable(String locationName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Function getFunction(String funName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Constant getConstantByName(String var) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DerivedFunction getDerivedFunction(String var) {
		// TODO Auto-generated method stub
		return null;
	}
}