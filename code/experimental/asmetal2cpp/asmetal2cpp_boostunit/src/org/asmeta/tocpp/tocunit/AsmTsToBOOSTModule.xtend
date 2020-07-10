package org.asmeta.tocpp.tocunit

import atgt.coverage.AsmTestSequence
import atgt.coverage.AsmTestSuite
import atgt.specification.location.FunctionApplication
import atgt.specification.location.Location
import java.util.Map
import org.eclipse.xtend2.lib.StringConcatenation
import asmeta.AsmCollection
import org.junit.Assert
import asmeta.definitions.DerivedFunction
import asmeta.definitions.ControlledFunction
import asmeta.definitions.domains.AbstractTd
import java.util.ArrayList
import asmeta.definitions.StaticFunction

// Asm test suite to ASM n
public class AsmTsToBOOSTModule {

// name of the ASM and also CPP class
	String asmName;
	String mainRuleName;
	AsmCollection asm
	int counter = 0;
	ArrayList<String> abstractFunction = new ArrayList;

	new(AsmCollection asm) {
		this.asm = asm;
		var asmMain = asm.main;
		this.asmName = asmMain.name
		mainRuleName = asmMain.mainrule.name
	}

// convert the test suite
	def convertTestSuite(AsmTestSuite testSuite) {
		'''#define BOOST_TEST_MODULE Test«asmName»
#define BOOST_TEST_DYN_LINK
#include <boost/test/unit_test.hpp>
#include <iostream>
#include "«asmName».h"
using namespace std;

«getAbstractDomain(asm)»

BOOST_AUTO_TEST_SUITE(Test«asmName»)
«FOR t : testSuite»
«printTestCase(t)»
//«counter++»
«ENDFOR»
BOOST_AUTO_TEST_SUITE_END()
'''
	}
	
 	def getAbstractDomain(AsmCollection asm) {
		for (fd : asm.main.headerSection.signature.function) {
			if (fd instanceof StaticFunction && fd.codomain instanceof AbstractTd)
			abstractFunction.add(fd.name)
		}
	}

	def printTestCase(AsmTestSequence test) {
		var boolean firstState = true
		'''BOOST_AUTO_TEST_CASE( my_test_«counter» ){
	// instance of the SUT
	«asmName» «asmName.toLowerCase»;	
	«FOR t : test.allInstructions()»
		// state 
		«printState(t,test.allInstructions.get(0)===t)»
		// call main rule
		«asmName.toLowerCase».«mainRuleName»();
		«asmName.toLowerCase».fireUpdateSet();
	«ENDFOR»
}'''
	}

	// traduce il singolo stato in C++
	def StringConcatenation printState(Map<Location, String> state, boolean firstState) {
		var builder = new StringConcatenation();
		// ALL the monitored vars
		builder.append("// set monitored variables \n")
		// set the monitored variables
		for (assignment : state.entrySet) {
			// var location = assignment.key;
			// assert of the state
			var location = assignment.key;
			if (location.monitored) {
				var String monLoc;
				if (location instanceof FunctionApplication) {
					val arg = (location as FunctionApplication).args.toString
					Assert.assertTrue(arg + " does not contain [", arg.contains("["))
					val name = (location as FunctionApplication).name
					Assert.assertFalse(name.contains("("))
					// () -> []
					monLoc = name + arg
				} else {
					monLoc = location.toString
					Assert.assertFalse(monLoc.contains("("))
				}
				builder.append(asmName.toLowerCase + "." + monLoc + "=" + assignment.value + ";\n")
			}
		}

		if (firstState) {
			builder.append("//Init controlled with monitored term \n")
			builder.append(asmName.toLowerCase + "." + "initControlledWithMonitored();\n")
		}
		// ALL the controlled vars
		builder.append("// check controlled variables \n")
		for (assignment : state.entrySet) {
			var location = assignment.key;
			val ctrLocName = location.idExpression.toString
			// if derived then skip
			// get the function definition for this function name 
			val functions = asm.main.headerSection.signature.function.filter[it.name.equals(ctrLocName)]
			if (functions.size === 0) {
				println(ctrLocName + " not found")
			} else {
				val f = functions.get(0)
				print(ctrLocName + " found (" + functions.size + ") .. ")
				if (f instanceof DerivedFunction) {
					println("derived function location: " + location + " " + location.varKind)
				// do nothing
				} else if (location.controlled || location.varKind === null) {
					Assert.assertTrue(f instanceof ControlledFunction)
					println("controlled location: " + location + " " + location.varKind)
					// controlled part 
					// assert of the state
					var ctrVar = ctrLocName + "[0]"
					// add arguments
					// from functions to maps aa(bb) -> aa[0][bb]
					if (location instanceof FunctionApplication) {
						var arguments = (location as FunctionApplication).args
						if (arguments.size>=2){
							//ctrVar += arguments
							var stringArgs = arguments +""
							stringArgs = stringArgs.replace("[", "(").replace("]", ")")
							ctrVar += "[make_tuple"+ stringArgs+"]"
							}
						else
							ctrVar += arguments
					}
					builder.append("BOOST_CHECK(" + asmName.toLowerCase + "." + ctrVar + "==" + assignment.value +
						");\n")
				}
			}
		}
		
			var replaced = builder.toString
			for (var i=0; i<abstractFunction.size; i++)
			replaced =  replaced.replaceAll(abstractFunction.get(i), asmName.toLowerCase+"."+abstractFunction.get(i))
		
			var newbuilder = new StringConcatenation();
			newbuilder.append(replaced)
		return newbuilder
		
	//	return builder
	}

}
