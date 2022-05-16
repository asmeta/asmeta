package org.asmeta.codegenerator

import asmeta.definitions.ControlledFunction
import asmeta.definitions.DerivedFunction
import asmeta.definitions.Function
import asmeta.definitions.OutFunction
import asmeta.definitions.domains.BasicTd
import asmeta.definitions.domains.EnumElement
import asmeta.definitions.domains.EnumTd
import asmeta.definitions.domains.IntegerDomain
import asmeta.definitions.domains.impl.AnyDomainImpl
import asmeta.definitions.domains.impl.BasicTdImpl
import asmeta.definitions.domains.impl.BooleanDomainImpl
import asmeta.definitions.domains.impl.ConcreteDomainImpl
import asmeta.definitions.domains.impl.EnumTdImpl
import asmeta.definitions.domains.impl.IntegerDomainImpl
import asmeta.structure.Asm
import org.asmeta.asm2code.main.TranslatorOptions
import org.asmeta.codegenerator.arduino.ArduinoBoard
import org.asmeta.codegenerator.arduino.ArduinoPinFeature
import org.asmeta.codegenerator.configuration.Binding
import org.asmeta.codegenerator.configuration.HWConfiguration

/**
 * Generate the code for the setOutput function. 
 * For each out function generates the equivalent code to set the outputs.
 */
class OutputFunctionCreator {
	HWConfiguration config
	protected TranslatorOptions options

	new(HWConfiguration config) {
		this.config = config
	}

	new(HWConfiguration config, TranslatorOptions options) {
		this.config = config
		this.options = options
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
		// if (functionDefinition.size == 0)
		// throw new RuntimeException("Error: function " + binding.function + " unknown.")
		// println("FUNCTION " + functionDefinition )
		}

		for (Binding binding : config.bindings) {

			var func = model.headerSection.signature.function
							.filter [ x | x instanceof OutFunction || x instanceof ControlledFunction || x instanceof DerivedFunction]
							.filter [ x |
								(x.name == binding.function.substring(0, (if (binding.function.contains("("))
									binding.function.indexOf("(")
								else
									binding.function.length)))
							]
			
			// only generate output function for controlled, out and derived functions
			if (func.size() > 0) { // PWM and ANALOGLINEAROUT are only for output

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
					case SWITCH:
						outputFunction += getEnumSwitchBinding(model, binding, func.get(0))
				}
			}
		}

		// LCD
		if (config.lcd !== null)
		{
			var lcdFunc = model.headerSection.signature.function
							.filter [ x | x instanceof OutFunction || x instanceof ControlledFunction || x instanceof DerivedFunction]
							.filter [ x | x.name == config.lcd.function]
			
			if (lcdFunc.size() > 0)
			{
				var f = lcdFunc.get(0)
				
				if (f.codomain instanceof EnumTdImpl)
				{
					outputFunction += getEnumSwitchBinding(model, null, f)
				}
				else
				{
					outputFunction += '''
						if («f.name»[0] != «f.name»[1])
						{
							«config.lcd.name».clear();
							«config.lcd.name».print(«f.name»[1]);
						}
					'''
				}
			}
		}

		/*if(outputFunction.length != 0) 
		 * return '''
		 * 	void ï¿½model.nameï¿½::setOutputs(){
		 * 		ï¿½outputFunctionï¿½
		 * 	}
		 * '''
		 * else return ""
		 */
		if (outputFunction.length != 0)
			return '''
				«outputFunction»
			'''
		else
			return ""

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
					if («binding.function»[0] != «binding.function»[1])
					{
						if(«binding.function» == «(enumDef.eContents.get(0) as EnumElement).symbol»)
							digitalWrite(«Util.arduinoPinToString(binding.pin)», HIGH);
						else
							digitalWrite(«Util.arduinoPinToString(binding.pin)», LOW);
					}
						
				'''
			} else {
				var funcName = binding.function.substring(0, binding.function.indexOf("("))
				return '''
					if («funcName»[0] != «funcName»[1])
					{
						if(«funcName + "[1][" +
							binding.function.substring(binding.function.indexOf("(")+1, binding.function.indexOf(")"))  + "]"» == «(enumDef.eContents.get(0) as EnumElement).symbol»)
							digitalWrite(«Util.arduinoPinToString(binding.pin)», HIGH);
						else
							digitalWrite(«Util.arduinoPinToString(binding.pin)», LOW);
					}
						
				'''
			}
		else if (!binding.function.contains("(")) {
			return '''
				if («binding.function»[0] != «binding.function»[1])
				{
					if(«binding.function» == «(enumDef.eContents.get(0) as EnumElement).symbol»)
						digitalWrite(«Util.arduinoPinToString(binding.pin)», LOW);
					else
						digitalWrite(«Util.arduinoPinToString(binding.pin)», HIGH);
				}
					
			'''
		} else {
				var funcName = binding.function.substring(0, binding.function.indexOf("("))
				return '''
					if («funcName»[0] != «funcName»[1])
					{
						if(«funcName + "[1][" +
								binding.function.substring(binding.function.indexOf("(")+1, binding.function.indexOf(")"))  + "]"» == «(enumDef.eContents.get(0) as EnumElement).symbol»)
							digitalWrite(«Util.arduinoPinToString(binding.pin)», LOW);
						else
							digitalWrite(«Util.arduinoPinToString(binding.pin)», HIGH);
					}
						
			'''
		}
	}

	def String getIntegerToAnalogPin(Asm model, Binding binding, double fullscale) {
		return '''
			if («binding.function»[0] != «binding.function»[1])
			{
				«
				if (binding.minVal == 0)
					'''
						analogWrite(«Util.arduinoPinToString(binding.pin)»,(«binding.function»[1])*(double)(«fullscale»/«binding.maxVal-binding.minVal +1»));
					'''
				else
					'''
						analogWrite(«Util.arduinoPinToString(binding.pin)»,(«binding.function»[1]-(«binding.minVal»))*(double)(«fullscale»/«binding.maxVal-binding.minVal +1»));
					'''
				»
			}
		'''
	}

	def String getNumberToAnalogPin(Asm model, Binding binding, double fullscale) {
		return '''
			if («binding.function»[0] != «binding.function»[1])
			{
				«
				if (binding.minVal == 0)
					'''
						analogWrite(«Util.arduinoPinToString(binding.pin)»,(«binding.function»[1])*(double)(«fullscale»/«binding.maxVal-binding.minVal»));
					'''
				else
					'''
						analogWrite(«Util.arduinoPinToString(binding.pin)»,(«binding.function»[1]-(«binding.minVal»))*(double)(«fullscale»/«binding.maxVal-binding.minVal»));
					'''
				»
			}
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
		if (outDefinition.codomain instanceof BooleanDomainImpl) { // BasicTdImpl && (outDefinition.codomain as BasicTdImpl) == 
			return getBooleanToDigitalPin(model, binding, inverted)
		// TODO: controllare AnyDomainImpl
		} else if (outDefinition.codomain instanceof AnyDomainImpl) {
			var domDefinitions = model.bodySection.functionDefinition.filter [ x |
				x.definedFunction == (outDefinition as AnyDomainImpl)
			]
			// D1 of BOOLEAN binded to DIGITALPIN
			if (domDefinitions.size > 0) {
				val domDefinition = domDefinitions.get(0)
				if (domDefinition.body.domain instanceof BasicTdImpl)
					if ((domDefinition.body.domain as BasicTdImpl) == BooleanDomainImpl)
						return getBooleanToDigitalPin(model, binding, inverted)
				// D1 of (BOOLEAN) binded to DIGITALPIN
				// TODO: controllare domDefinition.body.domain.constraint.get(0).constrainedDomain
				if (domDefinition.body.domain.constraint.get(0).constrainedDomain instanceof BasicTdImpl)
					if ((domDefinition.body.domain.constraint.get(0).constrainedDomain as BasicTdImpl) == BooleanDomainImpl)
						return getBooleanToDigitalPin(model, binding, inverted)

			}
		}
		// /////////////////////////////
		// 2-VALUES-ENUM -> DIGITALPIN
		// /////////////////////////////
		if (outDefinition.codomain instanceof EnumTdImpl) {
			if (outDefinition.codomain.eContents.size == 2)
				return getEnumToDigitalPin(model, binding, outDefinition.codomain as EnumTdImpl, inverted)
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
		// println("DEFINITIONS " + definitions + " " + definitions.size)
		// println("DEFINITION " + definitions)
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
		if ((monDefinition.codomain instanceof BasicTdImpl) &&
			(monDefinition.codomain as BasicTdImpl) == IntegerDomainImpl) {
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
		// if ((monDefinition.codomain instanceof BasicTd) && (monDefinition.codomain as BasicTd) == IntegerDomain) {
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

		// println(monDefinition.name + "Mondef domain" + monDefinition.domain)
		if (monDefinition.codomain instanceof EnumTdImpl || monDefinition.codomain instanceof ConcreteDomainImpl) {
			return getIntegerToAnalogPin(model, binding, fullscale)
		}
		/* 
		 * 	 	if (model.bodySection.functionDefinition.exists [ x |
		 * 	x.definedFunction == (monDefinition as AnyDomainImpl)
		 * ]) {
		 * 	println("exists")
		 * 	var enumDef = model.bodySection.functionDefinition.filter [ x |
		 * 		x.definedFunction == (monDefinition as AnyDomainImpl)
		 * 	].get(0) 
		 * 	
		 * 	return '''
		 * 		analogWrite(«binding.functionï¿½*(double)(«fullscaleï¿½/«enumDef.eContents.sizeï¿½));
		 * 	'''
		 }*/
		// if(true) return getIntegerToAnalogPin(model, binding, fullscale)
		println("PROBLEM " + monDefinition.codomain)

		throw new RuntimeException('''Error with «binding.function»: PWMBinding only supports INTEGER, NUMBER or Enumerative''')
	}

	def String getUserDefinedBinding(Asm model, Binding binding) {
		return '''
			//
			//TODO place here your input binding for function «binding.function»
			//
		'''
	}

	def String getEnumSwitchBinding(Asm asm, Binding binding, Function func) {
		
		var boolean useCasesForSwitch = (this.options !== null)
										? this.options.useCasesForSwitch
										: true;
		
		var String variable = (binding !== null && binding.function.contains("("))
									? '''[«binding.function.substring(binding.function.indexOf("(")+1, binding.function.indexOf(")"))»]'''
									: ''''''
		
		return '''
			if («func.name»[0]«variable» != «func.name»[1]«variable»)
			{
				«
				if (config.lcd !== null && func.name == config.lcd.function) { // LCD
					'''«config.lcd.name».clear();'''
				}
				»
				«
					if (useCasesForSwitch)
					{
						'''
						switch («func.name»[1])
						{
						'''
					}
				»
					«getCases(func, binding, useCasesForSwitch, variable)»
				«
					if (useCasesForSwitch)
					{
						'''
						}
						'''
					}
				»
			}
			
		''';
	}
	
	def String getCases(Function func, Binding binding, boolean useCasesForSwitch, String variable) {
		var StringBuffer sb = new StringBuffer
		var enumDef = func.codomain as EnumTdImpl
		
		var caseTerm = (useCasesForSwitch)
						? '''case %s:'''
						: '''if'''
		
		var conditionTerm = (useCasesForSwitch)
						? ''''''
						: '''(%s == %s)'''
		
		var breakTerm = (useCasesForSwitch)
						? '''break;'''
						: ''''''
		
		for (var i = 0; i < enumDef.eContents.size(); i++)
		{
			var enumEl = enumDef.eContents.get(i) as EnumElement
			
			sb.append('''
				«String.format(caseTerm, enumEl.symbol)» «String.format(conditionTerm, '''«func.name»[1]«variable»''', enumEl.symbol)»
				{
					«
					if (config.lcd !== null && func.name == config.lcd.function) { // LCD
						'''
							«config.lcd.name».print("«enumEl.symbol»");
						'''
					}
					»
					«breakTerm»
				}
			''')
		}
		
		return sb.toString
	}
}
