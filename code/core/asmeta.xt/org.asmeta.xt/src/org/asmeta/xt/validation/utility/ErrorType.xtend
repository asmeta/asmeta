package org.asmeta.xt.validation.utility

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature

class ErrorType extends MessageType{
	
	
	new(String msg, EStructuralFeature feature, String code){
		this(msg,feature,code,null)
	}
	
	new(String msg, EStructuralFeature feature, String code, EObject error_obj) {
		super(msg,feature,code,error_obj)
	}	
}