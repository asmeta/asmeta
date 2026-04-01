package org.asmeta.xt.validation.utility

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature

abstract class MessageType {
	
	var String message;
	var String code;
	var EStructuralFeature feature;
	var EObject obj;
	
	new(String msg, EStructuralFeature feature, String code) {
		this(msg,feature,code,null)
	}
	
	new(String msg, EStructuralFeature feature, String code, EObject error_obj) {
		this.message = msg
		this.feature = feature		
		this.code = code
		this.obj = error_obj
	}
	
	def String getMsg() { return message; }
	def String getCode() { return code; }
	def EStructuralFeature getFeature() { return feature; }
	def EObject getObj() { return obj; }
	
	override toString() {
		return "Error: " + message	
	}
	
	
}