package org.asmeta.codegenerator

import org.asmeta.codegenerator.configuration.HWConfiguration
import org.asmeta.codegenerator.configuration.Binding
import asmeta.transitionrules.basictransitionrules.ChooseRule
import asmeta.structure.Asm
import asmeta.definitions.MonitoredFunction
import asmeta.transitionrules.basictransitionrules.TermAsRule
import asmeta.definitions.OutFunction
import asmeta.definitions.ControlledFunction

/**
 * Generate the code for the setInputFunction.
 * For each monitored function generate the equivalent code to acquire the input.
 */
class SetupFunctionCreator {
	HWConfiguration config

	new(HWConfiguration config) {
		this.config = config
	}

	def String getSetupFunction(Asm asm) {
		var String setupFunction = ""
		for (Binding binding : config.bindings) {
			if (asm.headerSection.signature.function.exists[x|
				if (binding.function.contains("("))
					return x.name == binding.function.substring(0, binding.function.indexOf("("))
				else
					return x.name == binding.function	
			]) {
				// PWM and ANALOGLINEAROUT are only for output
				switch (binding.configMode) {
					// TODO PROVARE CON ARDUINO QUALI SETUP SONO NECESSARI!!
					case DIGITAL:
						setupFunction += getDigitalSetup(asm, binding)
					case DIGITALINVERTED:
						setupFunction += getInvertedDigitalSetup(asm, binding)
					case ANALOGLINEARIN:
						setupFunction += getAnalogLinearInSetup(asm, binding)
					case USERDEFINED:
						setupFunction += ""
					case PWM:
						setupFunction += getPWMSetup(asm, binding)
					case ANALOGLINEAROUT:
						setupFunction += getAnalogLinearOutSetup(asm, binding)
					case SWITCH: 
						setupFunction += getSwitchSetup(asm, binding)
				}
			}
		}
		return '''
			void setup(){
				«setupFunction»
				«getRandomSeed(asm)»
				«getLCDSetup»
			}
		'''
	}

	def getLCDSetup() {
		if (config.lcd !== null)
		{
			if (config.lcd.isi2c)
			{
				return '''
					«config.lcd.name».init();
					«config.lcd.name».clear();
					«config.lcd.name».backlight();
				'''
			}
			else
			{
				return '''
					«config.lcd.name».begin(«config.lcd.numberofcolumns»,«config.lcd.numberofrows»);
					«config.lcd.name».clear();
					
				'''
			}
		}
	}

	def String getRandomSeed(Asm asm) {
		if (asm.bodySection.functionDefinition.filter(ChooseRule).size > 0 ||
			asm.bodySection.functionDefinition.filter(TermAsRule).size > 0) // verificare TermAsRule, era PickTerm
			return '''
				randomSeed(readAnalog(A0));
			'''
		else
			return ""
	}

	def String getStandardSetup(Asm asm, Binding binding) {
		if (asm.headerSection.signature.function.filter(ControlledFunction).exists[x|
				if (binding.function.contains("("))
					return x.name == binding.function.substring(0, binding.function.indexOf("("))
				else
					return x.name == binding.function
		])
			return '''
				pinMode(«Util.arduinoPinToString(binding.pin)», OUTPUT);
			'''
		else if (asm.headerSection.signature.function.filter(MonitoredFunction).exists[x|
				if (binding.function.contains("("))
					return x.name == binding.function.substring(0, binding.function.indexOf("("))
				else
					return x.name == binding.function
		])
			return '''
				pinMode(«Util.arduinoPinToString(binding.pin)», INPUT);
			'''
		else
			throw new RuntimeException("Error: no monitored/out functions with name " + binding.function + " found.")
	}

	def String getDigitalSetup(Asm asm, Binding binding) {
		return '''
			«getStandardSetup(asm, binding)»
		'''
	}

	def String getInvertedDigitalSetup(Asm asm, Binding binding) {
		return '''
			«getStandardSetup(asm, binding)»
		'''
	}

	def String getAnalogLinearInSetup(Asm asm, Binding binding) {
		return '''
			«getStandardSetup(asm, binding)»
		'''
	}

	def String getPWMSetup(Asm asm, Binding binding) {
		return '''
			«getStandardSetup(asm, binding)»
		'''
	}

	def String getAnalogLinearOutSetup(Asm asm, Binding binding) {
		return '''
			«getStandardSetup(asm, binding)»
		'''
	}

	def String getSwitchSetup(Asm asm, Binding binding) {
		return '''
			«getStandardSetup(asm, binding)»
		'''
	}
}
