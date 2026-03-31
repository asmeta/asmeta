package org.asmeta.xt.validation.utility

import org.eclipse.emf.ecore.EStructuralFeature


class WarningType extends MessageType {
	
	var Integer position
	
	new(String msg, EStructuralFeature feature, String code) {
		this(msg,feature,code,-1)
		this.position = -1
	}
	
	new(String msg, EStructuralFeature feature, String code, Integer position) {
		super(msg,feature,code)
		this.position = position
	}
	def Integer getPosition() { return position; }	
}