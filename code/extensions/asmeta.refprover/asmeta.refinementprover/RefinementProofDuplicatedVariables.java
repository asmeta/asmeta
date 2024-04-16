package refinement;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.asmeta.parser.ASMParser;
import org.asmeta.parser.Defs;

import toyices.SMTbasedASMsimulator;
import toyices.TermVisitor;
import toyices.YicesModel;
import asmeta.definitions.DynamicFunction;
import asmeta.definitions.Function;
import asmeta.structure.Asm;
import asmeta.structure.FunctionInitialization;

/**
 * In this version we duplicate the variables for the functions of the abstract
 * machine and the of refined machine (even when they are the same function)
 * OLD CODE. IT IS KEPT ONLY FOR DOCUMENTATION.
 * IT SHOULD NOT BE USED ANYMORE.
 *
 */
public class RefinementProofDuplicatedVariables {
	private Asm absAsm;
	private Asm refinementAsm;
	private Set<LinkedFunctions> conformantFunctions;
	private Set<LinkedFunctions> linkedMonitoredFunctions;
	private Asm refinementAsmSameFunctionNames;//for proving initial refinement (second version)
	private Set<String> conformantFunctionsAbstractMachine;

	public RefinementProofDuplicatedVariables(String abs, String refinement) throws Exception {
		absAsm = ASMParser.setUpReadAsm(new File(abs)).getMain();
		//fixFunctionNames(absAsm);
		Set<String> abstractFunctions = new HashSet<String>();
		for(Function func: absAsm.getHeaderSection().getSignature().getFunction()) {
			abstractFunctions.add(func.getName());
		}

		refinementAsm = ASMParser.setUpReadAsm(new File(refinement)).getMain();
		//fixFunctionNames(refinementAsm);
		conformantFunctions = new HashSet<LinkedFunctions>();
		linkedMonitoredFunctions = new HashSet<LinkedFunctions>();

		conformantFunctionsAbstractMachine = new HashSet<String>();//for proving initial refinement (second version)

		for(Function func: refinementAsm.getHeaderSection().getSignature().getFunction()) {
			String funcName = func.getName();
			func.setName(funcName + "r");
			if(abstractFunctions.contains(funcName)) {
				conformantFunctions.add(new LinkedFunctions(funcName, funcName));
				
				conformantFunctionsAbstractMachine.add(funcName);
			}
			if(Defs.isMonitored(func)) {
				if(abstractFunctions.contains(funcName)) {
					linkedMonitoredFunctions.add(new LinkedFunctions(funcName, funcName));
				}
			}
		}
	}

	public RefinementProofDuplicatedVariables(String abs, String refinement, Set<String> functionsToCheck) throws Exception {
		absAsm = ASMParser.setUpReadAsm(new File(abs)).getMain();
		Set<String> abstractFunctions = new HashSet<String>();
		for(Function func: absAsm.getHeaderSection().getSignature().getFunction()) {
			abstractFunctions.add(func.getName());
		}
		refinementAsm = ASMParser.setUpReadAsm(new File(refinement)).getMain();
		conformantFunctions = new HashSet<LinkedFunctions>();
		for(String func: functionsToCheck) {
			conformantFunctions.add(new LinkedFunctions(func, func));
		}
		linkedMonitoredFunctions = new HashSet<LinkedFunctions>();
		for(Function func: refinementAsm.getHeaderSection().getSignature().getFunction()) {
			String funcName = func.getName();
			func.setName(funcName + "r");
			if(Defs.isMonitored(func)) {
				if(abstractFunctions.contains(funcName)) {
					linkedMonitoredFunctions.add(new LinkedFunctions(funcName, funcName));
				}
			}
		}
		
		//for proving initial refinement (second version)
		refinementAsmSameFunctionNames = ASMParser.setUpReadAsm(new File(refinement)).getMain();
	}

	public RefinementProofDuplicatedVariables(String script) {
		//TODO
	}

	public boolean[] buildProof() throws Exception {
		boolean[] result = new boolean[2];
		result[0] = initStateProof();//result init state
		System.err.println(";; Initial states are" + (result[0]?"":" not") + " conformant.");		
		result[1] = genericStepProof();//result generic step
		System.err.println(";; Generic step is" + (result[1]?"":" not") + " conformant.");
		return result;
	}

	private String buildInitProof(YicesModel model) {
		ArrayList<String> operands = new ArrayList<String>();
		for(LinkedFunctions couple: conformantFunctions) {
			operands.add(model.eq(couple.getAbstractFunctionInitState(), couple.getRefinedFunctionInitState()));
		}
		return model.not(model.and(operands));
	}

	private boolean initStateProof() throws Exception {
		SMTbasedASMsimulator visitorAbsAsm = new SMTbasedASMsimulator(absAsm);
		visitorAbsAsm.setLogger();
		visitorAbsAsm.visitASM();
		visitorAbsAsm.shutdownLogger();
		SMTbasedASMsimulator visitorRefinementAsm = new SMTbasedASMsimulator(refinementAsm, visitorAbsAsm.yicesModel);
		visitorRefinementAsm.setLogger();
		visitorRefinementAsm.visitASM();
		visitorRefinementAsm.parseAndAssertCommand("Initial state proof", buildInitProof(visitorRefinementAsm.yicesModel));
		String checkResult = visitorRefinementAsm.check();
		visitorRefinementAsm.shutdownLogger();
		return checkResult.equals("unsat");
	}

	//TODO
	private void initStateProof_v2() {
		for(FunctionInitialization init: absAsm.getDefaultInitialState().getFunctionInitialization()) {
			DynamicFunction func = init.getInitializedFunction();
			String funcName = func.getName();
			if(conformantFunctionsAbstractMachine.contains(funcName)) {
				TermVisitor tv = new TermVisitor(new YicesModel());
				String abstractInit = tv.visitFunctionDefinition(funcName, init.getVariable(), init.getBody());
				System.out.println(abstractInit);
			}
		}
		for(FunctionInitialization init: refinementAsmSameFunctionNames.getDefaultInitialState().getFunctionInitialization()) {
			DynamicFunction func = init.getInitializedFunction();
			String funcName = func.getName();
			if(conformantFunctionsAbstractMachine.contains(funcName)) {
				TermVisitor tv = new TermVisitor(new YicesModel());
				String abstractInit = tv.visitFunctionDefinition(funcName, init.getVariable(), init.getBody());
				System.out.println(abstractInit);
			}
		}
	}

	private String buildGenericStepProof(YicesModel model) {
		ArrayList<String> operandsCurrStatesEq = new ArrayList<String>();
		ArrayList<String> operandsNextStateCurrStateEq = new ArrayList<String>();
		ArrayList<String> operandsNextStatesEq = new ArrayList<String>();
		for(LinkedFunctions couple: conformantFunctions) {
			operandsCurrStatesEq.add(model.eq(couple.getAbstractFunctionInitState(), couple.getRefinedFunctionInitState()));
			operandsNextStateCurrStateEq.add(model.eq(couple.getAbstractFunctionInitState(), couple.getRefinedFunctionNextState()));
			operandsNextStatesEq.add(model.eq(couple.getAbstractFunctionNextState(), couple.getRefinedFunctionNextState()));
		}
		for(LinkedFunctions couple: linkedMonitoredFunctions) {
			if(!conformantFunctions.contains(couple)) {
				operandsCurrStatesEq.add(model.eq(couple.getAbstractFunctionInitState(), couple.getRefinedFunctionInitState()));
			}
		}
		String eqCurrStates = model.and(operandsCurrStatesEq);
		String eqNextStateCurrentState = model.and(operandsNextStateCurrStateEq);
		String eqNextStates = model.and(operandsNextStatesEq);
		String proof = model.implies(eqCurrStates, model.or(eqNextStateCurrentState, eqNextStates));
		proof = model.not(proof);
		return proof;
	}

	private boolean genericStepProof() throws Exception {
		SMTbasedASMsimulator visitorAbsAsm = new SMTbasedASMsimulator(absAsm);
		visitorAbsAsm.defaultInitState = null;//the initial state must be removed
		visitorAbsAsm.setLogger();
		visitorAbsAsm.visitASM();
		visitorAbsAsm.assertRules();
		visitorAbsAsm.shutdownLogger();
		SMTbasedASMsimulator visitorRefinementAsm = new SMTbasedASMsimulator(refinementAsm, visitorAbsAsm.yicesModel);
		visitorRefinementAsm.defaultInitState = null;//the initial state must be removed
		visitorRefinementAsm.setLogger();
		visitorRefinementAsm.visitASM();
		visitorRefinementAsm.assertRules();
		visitorRefinementAsm.parseAndAssertCommand("Generic step proof", buildGenericStepProof(visitorRefinementAsm.yicesModel));
		String checkResult = visitorRefinementAsm.check();
		visitorRefinementAsm.shutdownLogger();
		return checkResult.equals("unsat");
	}

	public static void main(String[] args) throws Exception {
		//String asm = "refinement/increment.asm";
		//String asm = "refinement/increment_ref1.asm";
		/*String asm = "refinement/increment_ref2.asm";
		SMTbasedASMsimulator visitor = new SMTbasedASMsimulator(asm);
		visitor.setLogger();
		visitor.visitASM();
		visitor.assertRules();*/
		//RefinementProofDuplicatedVariables proof = new RefinementProofDuplicatedVariables("refinement/increment.asm", "refinement/increment_ref1.asm");
		//RefinementProofDuplicatedVariables proof = new RefinementProofDuplicatedVariables("refinement/increment.asm", "refinement/increment_ref2.asm");
		//RefinementProofDuplicatedVariables proof = new RefinementProofDuplicatedVariables("../../../Asmeta/asm_examples/examples/landingGearSystem/LGS_GM.asm", "../../../Asmeta/asm_examples/examples/landingGearSystem/LGS_EV.asm");
		//Set<String> functionsToCheck = new HashSet<String>(Arrays.asList(new String[]{"doors", "gears"}));
		//RefinementProofDuplicatedVariables proof = new RefinementProofDuplicatedVariables("../../../Asmeta/asm_examples/examples/landingGearSystem/LGS_GM.asm", "../../../Asmeta/asm_examples/examples/landingGearSystem/LGS_EV.asm", functionsToCheck);
		//RefinementProofDuplicatedVariables proof = new RefinementProofDuplicatedVariables("../../../Asmeta/asm_examples/examples/landingGearSystem/LGS_EV.asm", "../../../Asmeta/asm_examples/examples/landingGearSystem/LGS_SE.asm", functionsToCheck);

		Set<String> functionsToCheck = new HashSet<String>(Arrays.asList(new String[]{"state"}));
		RefinementProofDuplicatedVariables proof = new RefinementProofDuplicatedVariables("refinement/cloudRoxana/ClientDisplayOutput.asm", "refinement/cloudRoxana/ClientDisplayOutputWithCookie.asm", functionsToCheck);
		proof.buildProof();
	}

	private class LinkedFunctions {
		private String abstractFunction;
		private String refinedFunction;

		private LinkedFunctions(String abstractFunction, String refinedFunction) {
			this.abstractFunction = abstractFunction;
			this.refinedFunction = refinedFunction + "r";
		}

		private String getAbstractFunction() {
			return abstractFunction;
		}

		private String getRefinedFunction() {
			return refinedFunction;
		}

		private String getAbstractFunctionInitState() {
			return abstractFunction + 0;
		}

		private String getRefinedFunctionInitState() {
			return refinedFunction + 0;
		}

		private String getAbstractFunctionNextState() {
			return abstractFunction + 1;
		}

		private String getRefinedFunctionNextState() {
			return refinedFunction + 1;
		}

		@Override
		public boolean equals(Object obj) {
			if(obj instanceof LinkedFunctions) {
				LinkedFunctions other = (LinkedFunctions)obj;
				return abstractFunction.equals(other.abstractFunction) && refinedFunction.equals(other.refinedFunction);
			}
			return false;
		}

		@Override
		public int hashCode() {
			return (abstractFunction + refinedFunction).hashCode();
		}
	}
}