package org.asmeta.codegenerator

import org.asmeta.codegenerator.configuration.HWConfiguration
import org.asmeta.codegenerator.configuration.Binding
import org.asmeta.codegenerator.arduino.ArduinoBoard
import org.asmeta.codegenerator.arduino.ArduinoPinFeature
import asmeta.structure.Asm
import asmeta.definitions.domains.BasicTd
import asmeta.definitions.domains.EnumTd
import asmeta.definitions.domains.BooleanDomain
import asmeta.definitions.domains.IntegerDomain
import asmeta.definitions.domains.impl.AnyDomainImpl
import asmeta.definitions.OutFunction
import asmeta.definitions.ControlledFunction
import asmeta.definitions.domains.impl.EnumTdImpl
import asmeta.definitions.domains.EnumElement
import asmeta.definitions.domains.impl.TypeDomainImpl
import asmeta.definitions.domains.impl.DomainImpl
import asmeta.definitions.domains.impl.IntegerDomainImpl
import asmeta.definitions.domains.impl.BasicTdImpl
import asmeta.definitions.domains.impl.ConcreteDomainImpl
import asmeta.definitions.impl.DerivedFunctionImpl
import asmeta.definitions.DerivedFunction

/**
 * Generate the code for the setOutput function. 
 * For each out function generates the equivalent code to set the outputs.
 */
class OutputFunctionCreator {
	private HWConfiguration config

	new(HWConfiguration config) {
		this.config = config
	}

	def String getOutputFunction(Asm model) {
		var String outputFunction = ""
		// Check whether all functions are defined in the spec
		for (Binding binding : config.bindings) {
			var functionDefinition = model.headerSection.signature.function.filter [ x |
				(x.name == binding.function.substring(0, (if (binding.function.contains("("))
					binding.function.indexOf("(")
				else
					binding.function.length)))
			]
			//if (functionDefinition.size == 0)
				//throw new RuntimeException("Error: function " + binding.function + " unknown.")
				//println("FUNCTION " + functionDefinition )
		}

		for (Binding binding : config.bindings) {
			//println("BINDING 1 " + binding)
			if (model.headerSection.signature.function.filter(ControlledFunction).exists [ x |
				(x.name == binding.function.substring(0, (if (binding.function.contains("("))
					binding.function.indexOf("(")
				else
					binding.function.length)))
			] || 
			model.headerSection.signature.function.filter(DerivedFunction).exists [ x |
				(x.name == binding.function.substring(0, (if (binding.function.contains("("))
					binding.function.indexOf("(")
				else
					binding.function.length)))
			]) { // PWM and ANALOGLINEAROUT are only for output
				//println("BINDING 2 " + binding)
				switch (binding.configMode) {
					case DIGITAL:
						outputFunction += getDigitalBinding(model, binding)
					case DIGITALINVERTED:
						outputFunction += getInvertedDigitalBinding(model, binding)
					case ANALOGLINEAROUT:
						outputFunction += getAnalogLinearBinding(model, binding)
					case PWM:
						outputFunction += getPWMBinding(model, binding)
					case USERDEFINED:
						outputFunction += getUserDefinedBinding(model, binding)
					case ANALOGLINEARIN:
						outputFunction += ""
				}
			}
		}

		// LCD
		if (config.lcd !== null) {
			outputFunction += '''
				«config.lcd.name».clear();
				«config.lcd.name».print(«config.lcd.function»);
			'''
		}
		/*if(outputFunction.length != 0) 
		return '''
			void «model.name»::setOutputs(){
				«outputFunction»
			}
		'''
		else return ""
		*/
		if(outputFunction.length != 0)
		return '''
			«outputFunction»
		'''
		else return ""
	
	}

	def String getBooleanToDigitalPin(Asm model, Binding binding, boolean inverted) {
		/*
		 * We have to check if the function name contains the symbol "(" because, in that case, we have to use a MAP to
		 * manage the value of the function
		 */
		if (inverted)
			return '''
				if(«binding.function»)
					digitalWrite(«Util.arduinoPinToString(binding.pin)», LOW);
				else
					digitalWrite(«Util.arduinoPinToString(binding.pin)», HIGH);
					
			'''
		else
			return '''
				if(«binding.function»)
					digitalWrite(«Util.arduinoPinToString(binding.pin)», HIGH);
				else
					digitalWrite(«Util.arduinoPinToString(binding.pin)», LOW);
				
			'''
	}

	def String getEnumToDigitalPin(Asm model, Binding binding, EnumTd enumDef, boolean inverted) {
		/*
		 * We have to check if the function name contains the symbol "(" because, in that case, we have to use a MAP to
		 * manage the value of the function
		 */
		if (inverted)
			if (!binding.function.contains("(")) {
				return '''
					if(«binding.function» == «(enumDef.eContents.get(0) as EnumElement).symbol»)
						digitalWrite(«Util.arduinoPinToString(binding.pin)», HIGH);
					else
						digitalWrite(«Util.arduinoPinToString(binding.pin)», LOW);
					
				'''
			} else {
				return '''
					if(«binding.function.substring(0, binding.function.indexOf("(")) + "[1][" +
						binding.function.substring(binding.function.indexOf("(")+1, binding.function.indexOf(")"))  + "]"» == «(enumDef.eContents.get(0) as EnumElement).symbol»)
						digitalWrite(«Util.arduinoPinToString(binding.pin)», HIGH);
					else
						digitalWrite(«Util.arduinoPinToString(binding.pin)», LOW);
					
				'''
			}
		else if (!binding.function.contains("(")) {
			return '''
				if(«binding.function» == «(enumDef.eContents.get(0) as EnumElement).symbol»)
					digitalWrite(«Util.arduinoPinToString(binding.pin)», LOW);
				else
					digitalWrite(«Util.arduinoPinToString(binding.pin)», HIGH);
				
			'''
		} else {
			return '''
				if(«binding.function.substring(0, binding.function.indexOf("(")) + "[1][" +
						binding.function.substring(binding.function.indexOf("(")+1, binding.function.indexOf(")"))  + "]"» == «(enumDef.eContents.get(0) as EnumElement).symbol»)
					digitalWrite(«Util.arduinoPinToString(binding.pin)», LOW);
				else
					digitalWrite(«Util.arduinoPinToString(binding.pin)», HIGH);
				
			'''
		}
	}

	def String getIntegerToAnalogPin(Asm model, Binding binding, double fullscale) {
		if (binding.minVal == 0)
			return '''
				analogWrite(«Util.arduinoPinToString(binding.pin)»,(«binding.function»)*(double)(«fullscale»/«binding.maxVal-binding.minVal +1»));
			'''
		else
			return '''
				analogWrite(«Util.arduinoPinToString(binding.pin)»,(«binding.function»-(«binding.minVal»))*(double)(«fullscale»/«binding.maxVal-binding.minVal +1»));
			'''
	}

	def String getNumberToAnalogPin(Asm model, Binding binding, double fullscale) {
		if (binding.minVal == 0)
			return '''
				analogWrite(«Util.arduinoPinToString(binding.pin)»,(«binding.function»)*(double)(«fullscale»/«binding.maxVal-binding.minVal»));
			'''
		else
			return '''
				analogWrite(«Util.arduinoPinToString(binding.pin)»,(«binding.function»-(«binding.minVal»))*(double)(«fullscale»/«binding.maxVal-binding.minVal»));
			'''
	}

	def String getDigitalBinding(Asm model, Binding binding) {
		return getDigitalBindingSupp(model, binding, false)
	}

	def String getInvertedDigitalBinding(Asm model, Binding binding) {
		return getDigitalBindingSupp(model, binding, true)
	}

	def String getDigitalBindingSupp(Asm model, Binding binding, boolean inverted) {

		var outDefinitions = model.headerSection.signature.function.filter(ControlledFunction).filter [ x |
			(x.name == binding.function.substring(0, (if (binding.function.contains("("))
				binding.function.indexOf("(")
			else
				binding.function.length)))
		]

		if (outDefinitions.size < 1)
			throw new RuntimeException("Error: no Out function found with name: " + binding.function)

		val outDefinition = outDefinitions.get(0)
		// ///////////////////////
		// BOOLEAN -> DIGITALPIN
		// ///////////////////////
		if (outDefinition instanceof BasicTd && (outDefinition as BasicTd) == BooleanDomain) {
			return getBooleanToDigitalPin(model, binding, inverted)
		// TODO: controllare AnyDomainImpl
		} else if (outDefinition instanceof AnyDomainImpl) {
			var domDefinitions = model.bodySection.functionDefinition.filter [ x |
				x.definedFunction == (outDefinition as AnyDomainImpl)
			]
			// D1 of BOOLEAN binded to DIGITALPIN
			if (domDefinitions.size > 0) {
				val domDefinition = domDefinitions.get(0)
				if (domDefinition.body.domain instanceof BasicTd)
					if ((domDefinition.body.domain as BasicTd) == BooleanDomain)
						return getBooleanToDigitalPin(model, binding, inverted)
				// D1 of (BOOLEAN) binded to DIGITALPIN
				// TODO: controllare domDefinition.body.domain.constraint.get(0).constrainedDomain
				if (domDefinition.body.domain.constraint.get(0).constrainedDomain instanceof BasicTd)
					if ((domDefinition.body.domain.constraint.get(0).constrainedDomain as BasicTd) == BooleanDomain)
						return getBooleanToDigitalPin(model, binding, inverted)

			}
		}
		// /////////////////////////////
		// 2-VALUES-ENUM -> DIGITALPIN
		// /////////////////////////////
		if (outDefinition.codomain instanceof EnumTdImpl) {
			if (outDefinition.codomain.eContents.size == 2)
				return getEnumToDigitalPin(model, binding, outDefinition.codomain as EnumTd, inverted)
		}

		throw new RuntimeException('''Error with «binding.function»: DigitalBinding only supports booleans or 2-values-enums''')
	}

	def String getAnalogLinearBinding(Asm model, Binding binding) {
		// var definitions = model.bodySection.functionDefinition.filter[x|x.definedFunction == binding.function]
		var definitions = model.headerSection.signature.function.filter [ x |
			(x.name == binding.function.substring(0, (if (binding.function.contains("("))
				binding.function.indexOf("(")
			else
				binding.function.length)))
		]
		
		if (definitions.size < 1)
			throw new RuntimeException("Error: no Out Function found with name: " + binding.function)
		val monDefinition = definitions.get(0)
		var int analogOutResolution
		if (new ArduinoBoard(config.createArduinoVersion).supportFeature(ArduinoPinFeature.ANALOGOUT10))
			analogOutResolution = 10
		else
			throw new RuntimeException("Error: arduino " + config.createArduinoVersion +
				" does not support ANALOGOUT10 feature. Use PWM instead.")

		val double fullscale = Math.pow(2, analogOutResolution)

		// /////////////////////////////////////
		// INTEGER  -> ANALOGLINEARIN
		// /////////////////////////////////////
		if ((monDefinition instanceof BasicTd) && (monDefinition as BasicTd) == IntegerDomain) {
			return getIntegerToAnalogPin(model, binding, fullscale)
		} else if (monDefinition instanceof AnyDomainImpl) {
			var domDefinitions = model.bodySection.functionDefinition.filter [ x |
				x.definedFunction == (monDefinition as AnyDomainImpl)
			]
			if (domDefinitions.size > 0) {
				val domDefinition = domDefinitions.get(0)
				// D1 of INTEGER binded to ANALOGPIN
				if ((domDefinition.body.domain instanceof BasicTd))
					if ((domDefinition.body.domain as BasicTd) == IntegerDomain)
						return getIntegerToAnalogPin(model, binding, fullscale)
				// D1 of (INTEGER) binded to ANALOGPIN
				if (domDefinition.body.domain.constraint != null)
					if (domDefinition.body.domain.constraint.get(0).constrainedDomain instanceof BasicTd)
						if ((domDefinition.body.domain.constraint.get(0).constrainedDomain as BasicTd) == IntegerDomain)
							return getIntegerToAnalogPin(model, binding, fullscale)
			}
		}

		// /////////////////////////////////////
		// NUMBER  -> ANALOGLINEARIN
		// /////////////////////////////////////			
		if ((monDefinition instanceof BasicTd) && (monDefinition as BasicTd) == IntegerDomain) {
			return getNumberToAnalogPin(model, binding, fullscale)
		} else if (monDefinition instanceof AnyDomainImpl) {
			var domDefinitions = model.bodySection.functionDefinition.filter [ x |
				x.definedFunction == (monDefinition as AnyDomainImpl)
			]
			if (domDefinitions.size > 0) {
				val domDefinition = domDefinitions.get(0)
				// D1 of NUMBER binded to ANALOGPIN
				if (domDefinition.body.domain instanceof BasicTd)
					if ((domDefinition.body.domain as BasicTd) == IntegerDomain)
						return getNumberToAnalogPin(model, binding, fullscale)
				// D1 of (NUMBER) binded to ANALOGPIN
				if (domDefinition.body.domain.constraint.get(0).constrainedDomain instanceof BasicTd)
					if ((domDefinition.body.domain.constraint.get(0).constrainedDomain as BasicTd) == IntegerDomain)
						return getNumberToAnalogPin(model, binding, fullscale)
			}
		}

		// /////////////////////////////
		// n-VALUES-ENUM -> ANALOGPIN
		// /////////////////////////////
		if (model.bodySection.functionDefinition.exists [ x |
			x == (monDefinition as AnyDomainImpl)
		]) {

			var enumDef = model.bodySection.functionDefinition.filter [ x |
				x == (monDefinition as AnyDomainImpl)
			].get(0)

			return '''
				analogWrite(«binding.function»*(double)(«fullscale»/«enumDef.eContents.size»));
			'''
		}
		throw new RuntimeException('''Error with «binding.function»: AnalogLinearBinding only supports INTEGER, NUMBER or Enumerative''')

	}

	def String getPWMBinding(Asm model, Binding binding) {
		// var definitions = model.bodySection.functionDefinition.filter[x|x.definedFunction == binding.function]
		var definitions = model.headerSection.signature.function.filter [ x |
			(x.name == binding.function.substring(0, (if (binding.function.contains("("))
				binding.function.indexOf("(")
			else
				binding.function.length)))
		]
		//println("DEFINITIONS " + definitions + " " + definitions.size)
		//println("DEFINITION " + definitions)
		println("Binding " + binding)
		if (definitions.size < 1)
			throw new RuntimeException("Error: no Out Function found with name: " + binding.function)
		val monDefinition = definitions.get(0)
		println(monDefinition.name + "Mondef codomain " + monDefinition.codomain)
		var int PWMResolution = 8
		val double fullscale = Math.pow(2, PWMResolution) - 1

		// /////////////////////////////////////
		// INTEGER  -> PWM
		// /////////////////////////////////////
		if ((monDefinition.codomain instanceof BasicTdImpl) && (monDefinition.codomain as BasicTdImpl) == IntegerDomainImpl) {
			return getIntegerToAnalogPin(model, binding, fullscale)
		} else if (monDefinition instanceof AnyDomainImpl) {
			var domDefinitions = model.bodySection.functionDefinition.filter [ x |
				x.definedFunction == (monDefinition as AnyDomainImpl)
			]
			if (domDefinitions.size > 0) {
				val domDefinition = domDefinitions.get(0)
				// D1 of INTEGER binded to PWM
				if ((domDefinition.body.domain instanceof BasicTd))
					if ((domDefinition.body.domain as BasicTd) == IntegerDomain)
						return getIntegerToAnalogPin(model, binding, fullscale)
				// D1 of (INTEGER) binded to PWM
				if (domDefinition.body.domain.constraint != null)
					if (domDefinition.body.domain.constraint.get(0).constrainedDomain instanceof BasicTd)
						if ((domDefinition.body.domain.constraint.get(0).constrainedDomain as BasicTd) == IntegerDomain)
							return getIntegerToAnalogPin(model, binding, fullscale)
			}
		}
		// /////////////////////////////////////
		// NUMBER  -> PWM
		// /////////////////////////////////////			
		//if ((monDefinition.codomain instanceof BasicTd) && (monDefinition.codomain as BasicTd) == IntegerDomain) {
		if (monDefinition.codomain instanceof IntegerDomainImpl) {
			return getNumberToAnalogPin(model, binding, fullscale)
		} else if (monDefinition instanceof AnyDomainImpl) {
			var domDefinitions = model.bodySection.functionDefinition.filter [ x |
				x.definedFunction == (monDefinition as AnyDomainImpl)
			]
			if (domDefinitions.size > 0) {
				val domDefinition = domDefinitions.get(0)
				// D1 of NUMBER binded to PWM
				if (domDefinition.body.domain instanceof BasicTd)
					if ((domDefinition.body.domain.constraint as BasicTd) == IntegerDomain)
						return getNumberToAnalogPin(model, binding, fullscale)
				// D1 of (NUMBER) binded to PWM
				if (domDefinition.body.domain.constraint.get(0).constrainedDomain instanceof BasicTd)
					if ((domDefinition.body.domain.constraint.get(0).constrainedDomain as BasicTd
					) == IntegerDomain)
						return getNumberToAnalogPin(model, binding, fullscale)
			}
		}
		
		
		// /////////////////////////////
		// n-VALUES-ENUM -> PWM
		// ///////////////////////////
		println(monDefinition.name + " Mondef domain " + monDefinition.domain)
		if (monDefinition.codomain instanceof EnumTdImpl || monDefinition.codomain instanceof ConcreteDomainImpl){
			
		 	return getIntegerToAnalogPin(model,binding,fullscale)
		}
		/* 
	 	if (model.bodySection.functionDefinition.exists [ x |
			x.definedFunction == (monDefinition as AnyDomainImpl)
		]) {
			println("exists")
			var enumDef = model.bodySection.functionDefinition.filter [ x |
				x.definedFunction == (monDefinition as AnyDomainImpl)
			].get(0) 
			
			return '''
				analogWrite(«binding.function»*(double)(«fullscale»/«enumDef.eContents.size»));
			'''
		}*/
		//if(true) return getIntegerToAnalogPin(model, binding, fullscale)
		//println("PROBLEM " + monDefinition.codomain)
		throw new RuntimeException('''Error with «binding.function»: PWMBinding only supports INTEGER, NUMBER or Enumerative''')
	}

	def String getUserDefinedBinding(Asm model, Binding binding) {
		return '''
			//
			//TODO place here your input binding for function «binding.function»
			//
		'''
	}
}
