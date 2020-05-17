package org.asmeta.xt.validation.utility

import org.eclipse.emf.ecore.EStructuralFeature


class WarningType {
	
	var String message
	var EStructuralFeature feature
	var String code
	var Integer position
	
	new(String msg, EStructuralFeature feature, String code) {
		this.message = msg
		this.feature = feature		
		this.code = code
		this.position = -1
	}
	
	new(String msg, EStructuralFeature feature, String code, Integer position) {
		this.message = msg
		this.feature = feature		
		this.code = code
		this.position = position
	}
	
	def public String getMessage() { return message; }
	def public EStructuralFeature getFeature() { return feature; }
	def public String getCode() { return code; }
	def public Integer getPosition() { return position; }
	
}