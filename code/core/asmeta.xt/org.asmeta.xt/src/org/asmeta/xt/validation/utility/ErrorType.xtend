package org.asmeta.xt.validation.utility

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature

class ErrorType {
	
	var String message;
	var String code;
	var EStructuralFeature feature;
	var EObject obj;
	
	new(String msg, EStructuralFeature feature, String code) {
		this.message = msg
		this.feature = feature		
		this.code = code
		obj = null
	}
	
	new(String msg, EStructuralFeature feature, String code, EObject error_obj) {
		this.message = msg
		this.feature = feature		
		this.code = code
		this.obj = error_obj
	}
	
	def public String getMsg() { return message; }
	def public String getCode() { return code; }
	def public EStructuralFeature getFeature() { return feature; }
	def public EObject getObj() { return obj; }
	
	override toString() {
		return "Error: " + message	
	}
	
	
}