package org.asmeta.tocpp.tocunit

import asmeta.AsmCollection
import atgt.coverage.AsmTestSequence
import atgt.coverage.AsmTestSuite

// Asm test suite to ASM n
class AsmTsToCatch2Test extends TestSuiteTranslator{

// name of the ASM and also CPP class
	int counter = 0;
	
	new(AsmCollection asm) {
		super(asm, "REQUIRE")
	}


// convert the test suite
	override convertTestSuite(AsmTestSuite testSuite) {
'''#define CATCH_CONFIG_MAIN  // This tells Catch to provide a main() - only do this in one cpp file
#include "catch.hpp"

#include <iostream>
#include "«asmName».h"
using namespace std;

«getAbstractDomain(asm)»

«FOR t : testSuite»
«printTestCase(t)»
//«counter++»
«ENDFOR»
'''
	}
	

	def printTestCase(AsmTestSequence test) {
		'''TEST_CASE( "my_test_«counter»", "my_test_«counter»"){
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
