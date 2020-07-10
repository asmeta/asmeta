package org.asmeta.codegenerator

import org.asmeta.codegenerator.configuration.HWConfiguration
import org.eclipse.xtext.generator.IGenerator
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess
import asmeta.structure.Asm
import org.eclipse.xtext.generator.InMemoryFileSystemAccess
import java.nio.file.Files
import java.nio.file.Paths
import java.util.Arrays
import java.nio.charset.StandardCharsets

class InoGenerator implements IGenerator {
	public static String Ext = ".ino"
	SetupFunctionCreator setup
	public HWConfiguration config

	new(HWConfiguration config) {
		this.config = config
		setup = new org.asmeta.codegenerator.SetupFunctionCreator(config)
	}

	def generate(Asm model, String path) {
		Files.write(Paths.get(path), compile(model).getBytes())
	}

	def String compile(Asm asm) {
		val asmName = asm.name
		val asmInstance = asmName.toFirstLower

		return '''
			#include"«asmName».h"
			«if(config.lcd !== null)
				return "#include <LiquidCrystal.h>\nLiquidCrystal lcd(7, 8, 9, 10, 11, 12);"»
			«setup.getSetupFunction(asm)»
			
			«asmName» «asmInstance»;
						
			void loop(){
			  «IF (config.stepTime > 0)»
			  	long cicleStart = millis();
			  «ENDIF»
			  «asmInstance».getInputs();
			  «asmInstance».«asm.mainrule.name»();
			  «asmInstance».fireUpdateSet();
			  «asmInstance».setOutputs();
			  «IF (config.stepTime > 0)»
			  	delay(«config.stepTime»-(millis()-cicleStart));
			  «ENDIF»
			}
			
		'''
	}
	
	override doGenerate(Resource input, IFileSystemAccess fsa) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

}
