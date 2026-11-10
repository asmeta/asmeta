package org.asmeta.atgt.coverage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.asmeta.flattener.AsmetaMultipleFlattener;
import org.asmeta.flattener.RemoveArgumentsFlattener;
import org.asmeta.flattener.nesting.RemoveNestingFlattener;
import org.asmeta.flattener.rule.AsmetaFlattener;
import org.asmeta.flattener.rule.CaseRuleFlattener;
import org.asmeta.flattener.rule.ChooseRuleINVARFlattener;
import org.asmeta.flattener.rule.ForallRuleFlattener;
import org.asmeta.flattener.rule.LetRuleFlattener;
import org.asmeta.flattener.rule.MacroCallRuleFlattener;
import org.asmeta.nusmv.MapVisitor;

import asmeta.AsmCollection;
import asmeta.definitions.RuleDeclaration;
import asmeta.definitions.domains.Domain;
import asmeta.structure.Asm;
import atgt.parser.asmeta.AsmetaLLoader;
import atgt.specification.ASMSpecification;
import atgt.specification.AsmGeneralSpecification;
import tgtlib.definitions.expression.type.EnumConstCreator;
import tgtlib.definitions.expression.type.Type;
import tgtlib.definitions.expression.type.Variable;
import tgtlib.specification.Axiom;
import tgtlib.specification.ParseException;
import tgtlib.specification.Specification;
import tgtlib.specification.location.AsmVariable;
import tgtlib.specification.location.Constant;
import tgtlib.specification.location.DerivedFunction;
import tgtlib.specification.location.Function;
import tgtlib.specification.location.PickedVariable;

/** 
 * new class for the specification of an ASM.  It is based on ASM of asmeta.
 * TO be completed
 * 
 * @author Angelo Gargantini
 *
 */
public class AsmetaAsSpec extends AsmGeneralSpecification {

	public static Class<? extends AsmetaFlattener>[] flattersforATGTAsmeta = new Class[] {
			// SMV flatternes
			MacroCallRuleFlattener.class,
			ForallRuleFlattener.class,
			RemoveArgumentsFlattener.class,
			LetRuleFlattener.class,
			CaseRuleFlattener.class,
			RemoveNestingFlattener.class,
			// remove choose
			ChooseRuleINVARFlattener.class
		};

	
	
	Asm asm;
	// using the ASMSpecification as a proxy for the Specification interface
	// TODO push up some methods to the AsmGeneralSpecification class and avoid composition
	private ASMSpecification asmspec;

	public AsmetaAsSpec(AsmCollection asm) throws Exception {
		// flatten the spec
		this.asm = AsmetaMultipleFlattener.flattenWithExtra(asm.getMain(), flattersforATGTAsmeta);
	}

	public void setAsmspec(ASMSpecification asmspec) {
		assert asmspec != null;
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
		AsmVariable var = asmspec.getVariable(locationName);
		if (var == null) {
			RuleDeclaration chooseRule = ChooseRuleINVARFlattener.lastChooseVars.get(locationName);
			if (chooseRule!= null) {
				System.err.println(chooseRule + locationName);
				return new PickedVariable(locationName, chooseRule.getName());
			}			
		}
		return var;
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

	@Override
	public Type getDomain(Domain dom, EnumConstCreator ecc) {
		return asmspec.getDomain(dom,ecc);
	}

	@Override
	public Type getTypeFor(String typeName) {
		return asmspec.getTypeFor(typeName);
	}

}