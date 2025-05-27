package org.asmeta.parser;

import org.eclipse.emf.ecore.EObject;

// single ocl checker 
// TODO go from a single class with all the checkers as names, to a more distributed architecture

interface OCLChecker<T extends EObject> {
	
	OCLCheckerResult check(T t);

}


record OCLCheckerResult(boolean ok, String errorMessage) {}