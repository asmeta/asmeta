package asm2code_inoproject

import asmeta.structure.Asm
import org.asmeta.asm2code.main.CppGenerator
import org.asmeta.asm2code.main.HeaderGenerator
import org.asmeta.codegenerator.HWIntegrationGenerator
import org.asmeta.codegenerator.InoGenerator
import org.asmeta.codegenerator.configuration.HWConfiguration
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.generator.IGenerator

class Asmeta2Project implements IGenerator {
	var CppGenerator cppGen
	var HeaderGenerator hGen
	var HWIntegrationGenerator hwGen
	var InoGenerator inoGen

	new(HWConfiguration config) {
		cppGen = new CppGenerator
		hGen = new HeaderGenerator
		hwGen = new HWIntegrationGenerator(config)
		inoGen = new InoGenerator(config)
	}

	// it takes the model to translate and the destination path and the destination name that will be the name of the files
	def generateAll(Asm model, String destinationPath, String destinationName) {
		cppGen.generate(model, destinationPath + destinationName + CppGenerator.Ext)
		hGen.generate(model, destinationPath + destinationName + HeaderGenerator.Ext)
		hwGen.generate(model, destinationPath + destinationName + HWIntegrationGenerator.Ext)
		inoGen.generate(model, destinationPath + destinationName + InoGenerator.Ext)
	}

	override doGenerate(Resource input, IFileSystemAccess fsa) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
}
