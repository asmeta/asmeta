package org.asmeta.codegenerator

import asmeta.definitions.MonitoredFunction
import asmeta.definitions.domains.BooleanDomain
import asmeta.definitions.domains.EnumTd
import asmeta.structure.Asm
import asmeta.structure.FunctionDefinition
import org.asmeta.codegenerator.arduino.ArduinoBoard
import org.asmeta.codegenerator.configuration.Binding
import org.asmeta.codegenerator.configuration.HWConfiguration
import org.eclipse.emf.ecore.resource.Resource
import asmeta.definitions.domains.BasicTd
import asmeta.definitions.domains.impl.IntegerDomainImpl
import org.asmeta.flattener.AsmetaMultipleFlattener;
import org.asmeta.flattener.rule.LetRuleFlattener
import org.asmeta.flattener.rule.ForallRuleFlattener
import org.asmeta.flattener.rule.MacroCallRuleFlattener
import org.asmeta.flattener.rule.CaseRuleFlattener
import org.asmeta.flattener.nesting.RemoveNestingFlattener
import org.asmeta.flattener.RemoveArgumentsFlattener
import java.util.List
import org.asmeta.flattener.rule.AsmetaFlattener
import org.asmeta.flattener.rule.ChooseRuleFlattener
import asmeta.definitions.domains.impl.RealDomainImpl
import asmeta.definitions.domains.impl.ConcreteDomainImpl
import asmeta.definitions.domains.impl.EnumTdImpl

/**
 * Generate the code for the setInputFunction. For each monitored function generate the equivalent code to acquire the input.
 */
class InputFunctionCreator {
	HWConfiguration config
	new(HWConfiguration config) {
		this.config = config
	}

	def String getInputFunction(Asm model) {
		var String inputFunction = ""
		// Check whether all functions are defined in the spec
		for (Binding binding : config.bindings) {
			var functionDefinition = model.headerSection.signature.function.filter [ x |
				(x.name == binding.function.substring(0, (if (binding.function.contains("("))
					binding.function.indexOf("(")
				else
					binding.function.length)))
			]
			//println("Function Definition " + functionDefinition)
			//if (functionDefinition.size == 0)
				//throw new RuntimeException("Error: function " + binding.function + " unknown.")
		}

		for (Binding binding : config.bindings) {
			if (model.headerSection.signature.function.filter(MonitoredFunction).exists [ x |
				(x.name == binding.function.substring(0, (if (binding.function.contains("("))
					binding.function.indexOf("(")
				else
					binding.function.length)))
			]) { // PWM and ANALOGLINEAROUT are only for output
				switch (binding.configMode) {
					case DIGITAL:
						inputFunction += getDigitalBinding(model, binding)
					case DIGITALINVERTED:
						inputFunction += getInvertedDigitalBinding(model, binding)
					case ANALOGLINEARIN:
						inputFunction += getAnalogLinearBinding(model, binding)
					case USERDEFINED:
						inputFunction += getUserDefinedBinding(model, binding)
					case PWM:
						inputFunction += ""
					case ANALOGLINEAROUT:
						inputFunction += ""
				}
			}
		}
		
		/*if(inputFunction.length != 0)
		return '''
			void «model.name»::getInputs(){
				«inputFunction»
			}
		'''
		else return ""*/
		if(inputFunction.length != 0)
		return '''
			«inputFunction»
		'''
		else return ""
	}

	def String getBooleanFromDigitalPin(Asm model, Binding binding, boolean inverted) {
		/*
		 * We have to check if the function name contains the symbol "(" because, in that case, we have to use a MAP to
		 * manage the value of the function
		 */
		if (inverted)
			if (!binding.function.contains("(")) {
				return '''
					«binding.function» = (digitalRead(«Util.arduinoPinToString(binding.pin)») == LOW);
				'''
			} else {
				return '''
					«binding.function.substring(0, binding.function.indexOf("(")) + "[" +
						binding.function.substring(binding.function.indexOf("(")+1, binding.function.indexOf(")"))  + "]"» = (digitalRead(«Util.arduinoPinToString(binding.pin)») == LOW);
				'''
			}
		else if (!binding.function.contains("(")) {
			return '''
				«binding.function» = (digitalRead(«Util.arduinoPinToString(binding.pin)») == HIGH);
			'''
		} else {
			return '''
				«binding.function.substring(0, binding.function.indexOf("(")) + "[" +
						binding.function.substring(binding.function.indexOf("(")+1, binding.function.indexOf(")"))  + "]"» = (digitalRead(«Util.arduinoPinToString(binding.pin)») == HIGH);
			'''
		}
	}

	def String getEnumFromDigitalPin(Asm model, Binding binding, EnumTd enumDef, boolean inverted) {
		if (inverted)
			return '''
				if(digitalRead(«Util.arduinoPinToString(binding.pin)») == HIGH)
					«binding.function» = «enumDef.element.get(0)»;
				else
					«binding.function» = «enumDef.element.get(1)»;
				
			'''
		else
			return '''
				if(digitalRead(«Util.arduinoPinToString(binding.pin)») == LOW)
					«binding.function» = «enumDef.element.get(0)»;
				else
					«binding.function» = «enumDef.element.get(1)»;
				
			'''
	}

	def String getIntegerFromAnalogPin(Asm model, Binding binding, double fullscale) {
		if (binding.minVal == 0)
			return '''
				«binding.function» = analogRead(«Util.arduinoPinToString(binding.pin)»)*(double)(«binding.maxVal-binding.minVal +1»/«fullscale»);
			'''
		else
			return '''
				«binding.function» = analogRead(«Util.arduinoPinToString(binding.pin)»)*(double)(«binding.maxVal-binding.minVal +1»/«fullscale») + («binding.minVal»);
			'''
	}

	//def String getNumberFromAnalogPin(Resource res, Binding binding, double fullscale) {
	def String getNumberFromAnalogPin(Asm res, Binding binding, double fullscale) {
		if( binding.offset == 0){
		if (binding.minVal == 0)
			return '''
				«binding.function» = analogRead(«Util.arduinoPinToString(binding.pin)»)*(double)(«binding.maxVal-binding.minVal»/«fullscale»);
			'''
		else
			return '''
				«binding.function» = analogRead(«Util.arduinoPinToString(binding.pin)»)*(double)(«binding.maxVal-binding.minVal»/«fullscale») + («binding.minVal»);
			'''
		}else{
			if (binding.minVal == 0)
			return '''
				«binding.function» = analogRead(«Util.arduinoPinToString(binding.pin)»)*(double)(«binding.maxVal-binding.minVal»/«fullscale») - «binding.offset»;
			'''
			else
			return '''
				«binding.function» = analogRead(«Util.arduinoPinToString(binding.pin)»)*(double)(«binding.maxVal-binding.minVal»/«fullscale») + («binding.minVal»)  - «binding.offset»;
			'''
			}
	}

	def String getDigitalBinding(Asm model, Binding binding) {
		return getDigitalBindingSupp(model, binding, false)
	}

	def String getInvertedDigitalBinding(Asm model, Binding binding) {
		return getDigitalBindingSupp(model, binding, true)
	}

	def String getDigitalBindingSupp(Asm model, Binding binding, boolean inverted) {

		var monDefinitions = model.headerSection.signature.function.filter(MonitoredFunction).filter [ x |
			(x.name == binding.function.substring(0, (if (binding.function.contains("("))
				binding.function.indexOf("(")
			else
				binding.function.length)))
		]

		if (monDefinitions.size < 1)
			throw new RuntimeException("Error: no Monitored function found with name: " + binding.function)

		val monDefinition = monDefinitions.get(0)

		// /////////////////////////////
		// BOOLEAN -> DIGITALPIN
		// /////////////////////////////
		if (monDefinition.codomain instanceof BooleanDomain) {
			return getBooleanFromDigitalPin(model, binding, inverted)
		}
		// /////////////////////////////
		// 2-VALUES-ENUM -> DIGITALPIN
		// /////////////////////////////
		else if (monDefinition.codomain instanceof EnumTd) {
			if (monDefinition.codomain.eContents.size == 2)
				return getEnumFromDigitalPin(model, binding, monDefinition.codomain as EnumTd, inverted)
		}
		 
		throw new RuntimeException('''Error with «binding.function»: DigitalBinding only supports booleans or 2-values-enums''')
	}

	def String getAnalogLinearBinding(Asm model, Binding binding) {
		
		// Check whether the monitored function exists
		var definitions = model.headerSection.signature.function.filter(MonitoredFunction).filter [ x |
			(x.name == binding.function.substring(0, (if (binding.function.contains("("))
				binding.function.indexOf("(")
			else
				binding.function.length)))
		]

		if (definitions.size < 1)
			throw new RuntimeException("Error: no Monitored Function found with name: " + binding.function)
			
		val monDefinition = definitions.get(0)
		val analogInResolution = new ArduinoBoard(config.createArduinoVersion).analogInResolution
		val double fullscale = Math.pow(2, analogInResolution)
		//println("monDefinition Prima " + monDefinition.codomain)
		// /////////////////////////////////////
		// INTEGER  -> ANALOGLINEARIN
		// /////////////////////////////////////
		
		if (monDefinition.codomain instanceof IntegerDomainImpl) {
		  	return getIntegerFromAnalogPin(model, binding, fullscale)
		}
		
		// /////////////////////////////////////
		// n-ENUM VALUES  -> ANALOGLINEARIN
		// /////////////////////////////////////
		
		if (monDefinition.codomain instanceof EnumTdImpl){
			if(binding.offset == 0)
			return '''
		  		«binding.function» = static_cast<«monDefinition.codomain.name»>(analogRead(«Util.arduinoPinToString(binding.pin)»)*(double)(«monDefinition.codomain.eContents.size»/«fullscale»));
		  	'''
		  	else
		  	return '''
		  		«binding.function» = static_cast<«monDefinition.codomain.name»>(analogRead(«Util.arduinoPinToString(binding.pin)»)*(double)(«monDefinition.codomain.eContents.size»/«fullscale»)) - «binding.offset»;
		  	'''
		  	}
		
		// /////////////////////////////////////
		// CONCRETEDOMAIN  -> ANALOGLINEARIN
		// /////////////////////////////////////
		if (monDefinition.codomain instanceof ConcreteDomainImpl){
			return getNumberFromAnalogPin(model, binding, fullscale) 
			
			}

		// /////////////////////////////////////
		// NUMBER  -> ANALOGLINEARIN
		// /////////////////////////////////////
		/* 
		println("monDefinition Dopo " + monDefinition.codomain)
		if (monDefinition.codomain instanceof ConcreteDomainImpl){
			return getIntegerFromAnalogPin(model,binding,fullscale)
		}*/
		
		
		/*if ((monDefinition.to instanceof BasicTd) &&
		 * 	(monDefinition.to as BasicTd).typeBasicDom == BasicDomainEnum.INTEGER) {
		 * 	return getIntegerFromAnalogPin(res, binding, fullscale)
		 * } else if (monDefinition.to instanceof ExtendableDomainImpl) {
		 * 	var domDefinitions = asmModel.body.definitions.filter(DomainDefinition).filter [ x |
		 * 		x.name == (monDefinition.to as ExtendableDomainImpl).type
		 * 	]
		 * 	// D1 of BOOLEAN binded to DIGITALPIN
		 * 	if (domDefinitions.size > 0) {
		 * 		val domDefinition = domDefinitions.get(0)
		 * 		// D1 of INTEGER binded to ANALOGPIN
		 * 		if ((domDefinition.domainParamDef.domain instanceof BasicTd))
		 * 			if ((domDefinition.domainParamDef.domain as BasicTd).typeBasicDom == BasicDomainEnum.INTEGER)
		 * 				return getIntegerFromAnalogPin(res, binding, fullscale)
		 * 		// D1 of (INTEGER) binded to ANALOGPIN
		 * 		if (domDefinition.domainParamDef.paramDef != null)
		 * 			if (domDefinition.domainParamDef.paramDef.singleParam.get(0).domain instanceof BasicTd)
		 * 				if ((domDefinition.domainParamDef.paramDef.singleParam.get(0).domain as BasicTd).typeBasicDom ==
		 * 					BasicDomainEnum.INTEGER)
		 * 					return getIntegerFromAnalogPin(res, binding, fullscale)
		 * 	}
		 * }

		 * // /////////////////////////////////////
		 * // NUMBER  -> ANALOGLINEARIN
		 * // /////////////////////////////////////			
		 * if ((monDefinition.to instanceof BasicTd) &&
		 * 	(monDefinition.to as BasicTd).typeBasicDom == BasicDomainEnum.NUMBER) {
		 * 	return getNumberFromAnalogPin(res, binding, fullscale)
		 * } else if (monDefinition.to instanceof ExtendableDomainImpl) {
		 * 	var domDefinitions = asmModel.body.definitions.filter(DomainDefinition).filter [ x |
		 * 		x.name == (monDefinition.to as ExtendableDomainImpl).type
		 * 	]
		 * 	// D1 of BOOLEAN binded to DIGITALPIN
		 * 	if (domDefinitions.size > 0) {
		 * 		val domDefinition = domDefinitions.get(0)
		 * 		// D1 of NUMBER binded to ANALOGPIN
		 * 		if (domDefinition.domainParamDef.domain instanceof BasicTd)
		 * 			if ((domDefinition.domainParamDef.domain as BasicTd).typeBasicDom == BasicDomainEnum.NUMBER)
		 * 				return getNumberFromAnalogPin(res, binding, fullscale)
		 * 		// D1 of (NUMBER) binded to ANALOGPIN
		 * 		if (domDefinition.domainParamDef.paramDef.singleParam.get(0).domain instanceof BasicTd)
		 * 			if ((domDefinition.domainParamDef.paramDef.singleParam.get(0).domain as BasicTd).typeBasicDom ==
		 * 				BasicDomainEnum.NUMBER)
		 * 				return getNumberFromAnalogPin(res, binding, fullscale)
		 * 	}
		 * }
		 
		 * // /////////////////////////////
		 * // n-VALUES-ENUM -> DIGITALPIN
		 * // /////////////////////////////
		 * if (asmModel.body.definitions.filter(EnumerateDefinition).exists [ x |
		 * 	x.name == (monDefinition.to as ExtendableDomainImpl).type
		 * ]) {

		 * 	var enumDef = asmModel.body.definitions.filter(EnumerateDefinition).filter [ x |
		 * 		x.name == (monDefinition.to as ExtendableDomainImpl).type
		 * 	].get(0)

		 * 	return '''
		 * 		«binding.function» = static_cast<«enumDef.name»>(readAnalog(«Util.arduinoPinToString(binding.pin)»)*(double)(«enumDef.enums.size»/«fullscale»));
		 * 	'''
		 }*/
		 
		
		throw new RuntimeException('''Error with «binding.function»: AnalogLinearBinding only supports INTEGER, NUMBER or Enumerative''')

	}

	def String getUserDefinedBinding(Asm model, Binding binding) {
		return '''
			//
			//TODO place here your input binding for function «binding.function»
			//
		'''
	}
}
