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
class AsmTsToBOOSTModule extends TestSuiteTranslator{

	int counter = 0;
	
	new(AsmCollection asm) {
		super(asm,"BOOST_CHECK")
	}
	
// name of the ASM and also CPP class

// convert the test suite
	override convertTestSuite(AsmTestSuite testSuite) {
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


}
