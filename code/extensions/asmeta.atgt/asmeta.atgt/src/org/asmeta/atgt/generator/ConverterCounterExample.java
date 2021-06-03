package org.asmeta.atgt.generator;

import java.util.Collections;

import org.apache.log4j.Logger;

import atgt.coverage.AsmTestCondition;
import atgt.coverage.AsmTestSeqContent;
import atgt.coverage.AsmTestSequence;
import atgt.specification.ASMSpecification;
import atgt.specification.location.Constant;
import atgt.specification.location.DerivedFunction;
import atgt.specification.location.Function;
import atgt.specification.location.Location;
import atgt.specification.location.Variable;
import tgtlib.definitions.expression.FunctionTerm;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.type.EnumConstCreator;
import tgtlib.definitions.expression.type.EnumType;
import tgtlib.definitions.expression.type.EnumerableType;
import tgtlib.definitions.expression.type.IntegerType;

/** convert a counter example of the model checker to a test sequence
 */
public class ConverterCounterExample {
	
	
	private static final EnumConstCreator ENUM_CONST_CREATOR = new EnumConstCreator();


	public static boolean IncludeUnchangedVariables = false;  
	
	
	private final static Logger LOG = Logger.getLogger(ConverterCounterExample.class);
	

	public static AsmTestSequence convert(Counterexample test, ASMSpecification spec, AsmTestCondition tc) {
		return convert(test, spec, tc, IncludeUnchangedVariables);
	}
	
	public static AsmTestSequence convert(Counterexample test, ASMSpecification spec, AsmTestCondition tc, boolean includeUnchangedVariables) {
		//
		LOG.debug("converting cex with " + test.length() + " states to ASM test");
		//
		AsmTestSeqContent.addOnlyChangeValues = ! includeUnchangedVariables;
		AsmTestSequence asmTestSequence = new AsmTestSequence(tc);
		for (ModelCheckerState mcs : test) {
			asmTestSequence.addState();
			for (String var : mcs.getVars()) {
				//System.out.println(var);
				// value
				String val = mcs.getVarValue(var);
				assert val != null;
				String locationName = var.replaceAll("\\(", "\\[").replaceAll("\\)", "\\]");
				// case 1: a variable
				// if it is a variable
				Variable v = spec.getVariable(locationName);
				if (v != null) {
					asmTestSequence.addAssignment(v, val);
					continue;
				}
				// case 2 a function
				// it can be a function
				if (var.contains("_")) {
					String funName = var.substring(0, var.indexOf('_'));
					if (spec.getFunction(funName) != null){						
						asmTestSequence.addAssignment(var, val, Location.VarKind.CONTROLLED);
						continue;
					}
				} 
				if (var.contains("(")) {
					String funName = var.substring(0, var.indexOf('('));
					Function function = spec.getFunction(funName);
					if (function != null){
						FunctionTerm ft = ConverterCounterExample.extractFunctionTerm(var, spec);
						// TODO let asmtest sequence accept also function
						asmTestSequence.addAssignment(ft, val, function.getVarKind());
						continue;
					}
				}
				// derived function
				DerivedFunction dv = spec.getDerivedFunction(var);
				if (dv != null){
					// add as controlled (useful to link)
					asmTestSequence.addAssignment(var, val, Location.VarKind.CONTROLLED);
					continue;
				}
				// static functions, 
				Constant constant = spec.getConstantByName(var);
				if (constant != null){
					// add as controlled - so to be checked 
					asmTestSequence.addAssignment(var,val, Location.VarKind.CONTROLLED);
					continue;
				}
				System.err.println("variable " + var + " assigned to " + val + " not found");
				throw new RuntimeException();
			}
		}
		return asmTestSequence;
	}

	/**
	 * given a location of tuple foo(kkk) build the funtionTerm (subtype of
	 * Expression for ATGT)
	 * 
	 * @param location
	 * @return
	 */
	private static FunctionTerm extractFunctionTerm(String location, ASMSpecification spec) {
		String functionName = location.substring(0, location.indexOf("("));
		Function f = spec.getFunction(functionName);
		assert f != null;
		String argument = location.substring(location.indexOf("(") + 1, location.length() - 1);
		// only one argument allowed
		assert !argument.contains(",");
		IdExpression argId = null;
		/*
		 * if (argument.equalsIgnoreCase(BoolType.FALSE_STR)){ argId =
		 * BoolType.TRUE_CONST; }else if (argument.equalsIgnoreCase(BoolType.TRUE_STR)){
		 * argId = BoolType.TRUE_CONST; } else{ assert false; // Constant constantByName
		 * = getSpec().getConstantByName(argument); //if(constantByName != null; }
		 */
		// assuming now it is a enumerative
		/*
		 * EnumType et = (EnumType) f.getDomain(); argId = et.getEnumConst(argument);
		 * assert argId != null : argument + " not found in " + et; FunctionTerm ft =
		 * new FunctionTerm(f.getIdExpression(), f.getCodomain(),
		 * Collections.singletonList(argId)); return ft;
		 */
		EnumerableType et = (EnumerableType) f.getDomain();
		if (et instanceof EnumType) {
			argId = ((EnumType) et).getEnumConst(argument);
		} else {
			argId = ENUM_CONST_CREATOR.createIdExpression(argument, IntegerType.INTEGER_TYPE);
		}
		assert argId != null : argument + " not found in " + et;
		FunctionTerm ft = new FunctionTerm(f.getIdExpression(), f.getCodomain(), Collections.singletonList(argId));
		return ft;
	}
}