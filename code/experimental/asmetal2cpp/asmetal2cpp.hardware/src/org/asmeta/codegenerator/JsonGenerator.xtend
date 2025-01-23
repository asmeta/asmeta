package org.asmeta.codegenerator

import asmeta.definitions.Function
import asmeta.definitions.MonitoredFunction
import asmeta.definitions.OutFunction
import asmeta.definitions.domains.BooleanDomain
import asmeta.definitions.domains.EnumTd
import asmeta.definitions.domains.IntegerDomain
import asmeta.definitions.domains.NaturalDomain
import asmeta.definitions.domains.impl.AnyDomainImpl
import asmeta.structure.Asm
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.nio.file.Files
import java.nio.file.Paths
import java.util.ArrayList
import java.util.List
import org.asmeta.codegenerator.arduino.ArduinoBoard
import org.asmeta.codegenerator.arduino.ArduinoPin
import org.asmeta.codegenerator.arduino.ArduinoPinFeature
import org.asmeta.codegenerator.arduino.ArduinoVersion
import org.asmeta.codegenerator.configuration.Binding
import org.asmeta.codegenerator.configuration.HWConfiguration
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.generator.IGenerator
import asmeta.structure.FunctionDefinition
import asmeta.structure.impl.FunctionDefinitionImpl
import asmeta.definitions.ControlledFunction
import asmeta.definitions.StaticFunction
import org.asmeta.codegenerator.arduino.ArduinoMode
import java.io.File
import org.asmeta.parser.ASMParser
import asmeta.AsmCollection
import asmeta.definitions.DerivedFunction

class JsonGenerator implements IGenerator {
	public static String Ext = ".a2c"
	HWConfiguration config
	ArduinoBoard arduino
	boolean derivedFunction
	public String folderPath
	public String previousPath
	public List<ArduinoPin> available 

	new(ArduinoVersion av) {
		config = new HWConfiguration
		config.arduinoVersion = av.name
		config.stepTime = 0
		arduino = new ArduinoBoard(av)
		available = new ArrayList<ArduinoPin>(arduino.pins)
		derivedFunction = false
	}
	/**
	 * @param model : it must be an instance of Asm or AsmCollection
	 */
	def generate(Object model, String path, boolean derivedFunction, boolean ...folder){
		this.derivedFunction = derivedFunction
		if(folder !== null && model instanceof Asm)
			generate(model, path, folder.get(0))
		else if (model instanceof Asm)
			generate(model, path)
		else if (model instanceof AsmCollection)
			generate(model, path)
		else throw new Exception("The Object passed must be an instance of Asm or AsmCollection")
	}
	
	/**
	 * Genera il file JSON.
	 * Se l'ASM ha import e non si vuole creare il JSON limitandosi alla cartella della main ASM, utilizzare generate con ASMCollection perch� � pi� performante
	 */
	def generate(Asm model, String path) {
		Files.write(Paths.get(path), compile(model,true,false).getBytes())
	}
	
	/**
	 @param folder : if set true the JSON file is limited to the ASM folder.
	 * 				 if set false the JSON file is created looking at all the imported modules also from other folders.
	 * 				 default is false.
	 */
	def generate(Asm model, String path, boolean folder){
		Files.write(Paths.get(path), compile(model,true,folder).getBytes())
	}
	
	def generate(AsmCollection collection, String path){
		Files.write(Paths.get(path), compile(collection).getBytes())
	}
	
	def getFolderPath(Asm model, String asmPath){
		this.folderPath = asmPath.substring(0,asmPath.length - model.name.length - 4) //retreive the directory path of the asm.
	}

	def void allocatePins(Asm model) {
		// the idea (greedy) is to allocate first pins which are more rare (ANALOG,PWM) and the finally the digital pins
		// (theoretically each pin can work as digital pin)
		//var List<ArduinoPin> available = new ArrayList<ArduinoPin>(arduino.pins)
		//  . .functionDefinition.map[getDefinedFunction]
		var List<Function> definedFunctions = model.headerSection.signature.function.toList
		//var List<Function> outDefs = new ArrayList<Function>(definedFunctions.filter(ControlledFunction).toList)
		var List<Function> outDefs = new ArrayList<Function>(definedFunctions.filter[
			x | x instanceof ControlledFunction || x instanceof OutFunction
		].toList)
		var List<Function> monDefs = new ArrayList<Function>(definedFunctions.filter(MonitoredFunction).toList)
		var List<Function> derDefs = new ArrayList<Function>(definedFunctions.filter(DerivedFunction).toList)
		
		println("outDefs" + outDefs)
		println("monDefs" + monDefs)
		println("derDefs" + derDefs)
		println()
		
		// mode list containing necessary pin features and min/max values for bindings
		var modeList = new ArrayList<ArduinoMode>()
		
		/////////////////////////////////////////////
		// CONTROLLED AND OUT FUNCTION BINDINGS            //
		/////////////////////////////////////////////		
		
		// INTEGER or NUMBER FUNCTIONS
		// **ANALOG_OUT PINS**  **PWM PINS**
		modeList.add(new ArduinoMode("PWM", 0, 255, ArduinoPinFeature.PWM8))
		modeList.add(new ArduinoMode("ANALOGOUT", 0, 1023, ArduinoPinFeature.ANALOGOUT10))
		getIntegerAndNaturalNumberBindings(outDefs, available, modeList)
		//println(modeList.get(modeList.length - 1))				
		// n-ENUM FUNCTIONS
		// **ANALOG_OUT PINS**  **PWM PINS**
		modeList.clear()
		modeList.add(new ArduinoMode("PWM", ArduinoPinFeature.PWM8))
		modeList.add(new ArduinoMode("ANALOGLINEAROUT", ArduinoPinFeature.ANALOGOUT10))
		getEnumerativeBindings(model, outDefs, available, modeList)

		// BOOLEAN or 2-ENUM FUNCTIONS
		// **DIGITAL PINS** (output)
		getBooleanBindings(model, outDefs, available);		
		
		/////////////////////////////////////////////
		// n-ENUM to switch FUNCTIONS              //
		/////////////////////////////////////////////		

		// n-ENUM FUNCTIONS to switch
		// **USERDEFINED**
		getEnumerativeSwitchBindings(model, outDefs);		
		
		/////////////////////////////////////////////
		// MONITORED FUNCTION BINDINGS             //
		/////////////////////////////////////////////		
		
		// INTEGER or NUMBER FUNCTIONS
		// **ANALOG IN PINS** 
		modeList.clear()
		modeList.add(new ArduinoMode("ANALOGLINEARIN", 0, 1023, ArduinoPinFeature.ANALOGIN10))
		modeList.add(new ArduinoMode("ANALOGLINEARIN", 0, 2047, ArduinoPinFeature.ANALOGIN12))
		getIntegerAndNaturalNumberBindings(monDefs, available, modeList)
		
		// n-ENUM FUNCTIONS
		// **ANALOG IN PINS**
		modeList.clear()
		modeList.add(new ArduinoMode("ANALOGLINEARIN", ArduinoPinFeature.ANALOGIN10))
		modeList.add(new ArduinoMode("ANALOGLINEARIN", ArduinoPinFeature.ANALOGIN12))
		getEnumerativeBindings(model, monDefs, available, modeList)

		// BOOLEAN or 2-ENUM FUNCTIONS
		// **DIGITAL PINS** (input)
		getBooleanBindings(model, monDefs, available)
		
		// remove all bound function from respective lists
		removeBoundFunctions(outDefs)
		removeBoundFunctions(monDefs)
		// removeBoundFunctions(statDefs)
				
		//
		// THIS IS THE LAST CHANCE... IF NO IDEA HOW TO BIND OR NO PIN LEFT --> USERDEFINED
		//
		var remainingFunctions1 = ""
		if (outDefs.size != 0)
			remainingFunctions1 = outDefs.map[x|return x.name].reduce[x1, x2|x1 + ";" + x2]

		var remainingFunctions2 = ""
		if (monDefs.size != 0)
			remainingFunctions2 = monDefs.map[x|return x.name].reduce[x1, x2|x1 + ";" + x2]
		
		
		var derFunctions = ""
		if(derivedFunction){
			if(derDefs.size != 0)
			derFunctions = derDefs.map[ x | return x.name ].reduce[x1, x2 | x1 + ";" + x2]
		}
		

		var List<String> remainingFunctions = (remainingFunctions1 + ";" + remainingFunctions2 + ";" + derFunctions).split(";")

		if (remainingFunctions1 + remainingFunctions2 + derDefs != "")
			for (String def : remainingFunctions) {
				var Binding newbinding = new Binding
				var found = false
				// Check the domain of each function in controlled function
				for (var i = 0; i < outDefs.size && !found; i++)
					if (outDefs.get(i).name == def && outDefs.get(i).domain != null) {
						found = true
						println(found + "  " + outDefs.get(i).name)
						// Search all the static functions that has the domain equals than the considered CONTROLLED Funct.
						/*for (Function f : statDefs) {
							if (f.codomain.name==outDefs.get(i).domain.name) {
								var Binding newbinding2 = new Binding
								newbinding2.function = def + "(" + f.name + ")"
								newbinding2.mode = "USERDEFINED"
								config.bindings.add(newbinding2)
							}
						}*/					
					}
				if (!found) {
					// Check the domain of each function in monitored function
					for (var i = 0; i < monDefs.size && !found; i++)
						if (monDefs.get(i).name == def && monDefs.get(i).domain != null) {
							found = true
							// Search all the static functions that has the domain equals than the considered MONITORED Funct.
							/*for (Function f : statDefs) {
								if (f.codomain.name==monDefs.get(i).domain.name) {
									var Binding newbinding2 = new Binding
									newbinding2.function = def + "(" + f.name + ")"
									newbinding2.mode = "USERDEFINED"
									config.bindings.add(newbinding2)
								}
							}*/	
						}
				}
				if (!found) {
					var foundBinding = false;
					for(var i = 0; i< available.size && !foundBinding; i++){
					foundBinding = true;
					newbinding.function = def
					newbinding.mode = "USERDEFINED"
					newbinding.pin = available.get(i).id.name
					available.remove(i)
					config.bindings.add(newbinding)
					}
				
				}
			}
	}
	
	def void getEnumerativeSwitchBindings(Asm model, List<Function> defList) {
		for (Function def : defList) {
			if (def.codomain instanceof EnumTd && getEnumerativeDomainSize(model, def) > 2
				&& def.name.toLowerCase().indexOf("switch") >= 0
			) { // n-ENUM and function name contains "switch" substring
				var Binding newbinding = new Binding
				var foundBinding = false;
				for(var i = 0; i< available.size && !foundBinding; i++){
					foundBinding = true;
					newbinding.function = def.name
					newbinding.mode = "SWITCH"
					newbinding.pin = available.get(i).id.name
					available.remove(i)
					config.bindings.add(newbinding)
				}
			}
		}
	}
	
	def void getIntegerAndNaturalNumberBindings(List<Function> defList, List<ArduinoPin> pinList, List<ArduinoMode> modeList) {
		for (Function def : defList) {
			if (def.codomain instanceof IntegerDomain || def.codomain instanceof NaturalDomain) {
				var foundBinding = false
				var Binding newbinding = new Binding
				newbinding.function = def.name
				for (var i = 0; i < pinList.size && (!foundBinding); i++) {
					for (ArduinoMode mode : modeList) {
						if (!foundBinding && pinList.get(i).supportFeature(mode.getPinFeature())) {
							newbinding.mode = mode.getName()
							newbinding.pin = pinList.get(i).id.name
							newbinding.minVal = mode.getMinValue()
							newbinding.maxVal = mode.getMaxValue()
							newbinding.offset = 0
							pinList.remove(i)
							config.bindings.add(newbinding)
							foundBinding = true
						}
					}
				}
			}
		}
	}
	
	def void getBooleanBindings(Asm model, List<Function> defList, List<ArduinoPin> pinList) {
		for (Function def : defList) {
			if (def.codomain instanceof BooleanDomain || (def.definition instanceof EnumTd && // def.definition.get
			getEnumerativeDomainSize(model, def) == 2)) {
				var foundBinding = false
				var Binding newbinding = new Binding
				newbinding.function = def.name
				for (var i = 0; i < pinList.size && (!foundBinding); i++) {
					if (pinList.get(i).supportFeature(ArduinoPinFeature.DIGITAL)) {
						newbinding.mode = "DIGITAL"
						newbinding.pin = pinList.get(i).id.name
						pinList.remove(i)
						config.bindings.add(newbinding)
						foundBinding = true
					}
				}
			}
		}
	}

	def void getEnumerativeBindings(Asm model, List<Function> defList, List<ArduinoPin> pinList, List<ArduinoMode> modeList) {
		for (Function def : defList) {
			if (def.codomain instanceof EnumTd && getEnumerativeDomainSize(model, def) > 2
				&& def.name.toLowerCase().indexOf("switch") < 0
			) { // n-ENUM
				var foundBinding = false
				var Binding newbinding = new Binding
				newbinding.function = def.name
				for (var i = 0; i < pinList.size && (!foundBinding); i++) {
					for (ArduinoMode mode : modeList) {
						if (!foundBinding && pinList.get(i).supportFeature(mode.getPinFeature())) {
							newbinding.mode = mode.getName()
							newbinding.pin = pinList.get(i).id.name
							newbinding.minVal = 0
							newbinding.maxVal = getEnumerativeDomainSize(model, def) - 1
							pinList.remove(i)
							config.bindings.add(newbinding)
							foundBinding = true
						}
					}
				}
			}
		}
	}
	

	def void removeBoundFunctions(List<Function> defList) {
		// remove the functions which are already bound
		for (Binding binding : config.bindings) {
			var boolean found = false
			for (var i = 0; i < defList.size && !found; i++)
				if (defList.get(i).name == binding.function) {
					defList.remove(i)
					found = true
				}
		}
	}

	def int getEnumerativeDomainSize(Asm model, Function definition) {
		if (definition.codomain instanceof EnumTd) {
			var enumDefs = model.headerSection.signature.getDomain().filter [ x |
				x.name == (definition.codomain as EnumTd).name
			]
			if (enumDefs.size == 0)
				return -1
			else
				return enumDefs.get(0).eContents().size
		}
		return -1
	}
	
	def String compile(AsmCollection asmCol){
		config.clearBindings();
		for(asm : asmCol){
			if(!asm.name.contains("StandardLibrary"))
			allocatePins(asm)
		}
		var Gson gson = new GsonBuilder().setPrettyPrinting().create()
		return gson.toJson(config)
	}
	
	
	def String compile(Asm asm, boolean isFirst, boolean folder) {
		if(isFirst) {config.clearBindings();
		}
		var String currentFolder
		var boolean lock = true
		var List<String> imports = newArrayList();
		var i = 0;
		for(inc : asm.headerSection.importClause){
				imports.add(inc.moduleName)
				i++
		}
		if(folder){
			imports = getFolderImports(imports)
		}else{
		imports = sortImports(imports)
		//println("SORTED IMPORTS : " + imports )
		}
		for(imp : imports){
			if (!imp.contains("StandardLibrary")){
			if(lock){
				lock = false
				currentFolder = folderPath
			}
			var File asmDir = new File(currentFolder + imp + ASMParser.ASM_EXTENSION)
			//println("Path: " + asmDir.path)
			if(!asmDir.exists) throw new Exception("File Path Not Found")
			var Asm model = ASMParser.setUpReadAsm(asmDir).getMain()
			getFolderPath(model,asmDir.canonicalPath)
			compile(model,false,folder)
				
			//println(imp + " Added")
			}
		}
		allocatePins(asm)
		var Gson gson = new GsonBuilder().setPrettyPrinting().create()
		return gson.toJson(config)
	}
	/* 
	def String compile(Asm asm){
		config.clearBindings();
		allocatePins(asm)
		var Gson gson = new GsonBuilder().setPrettyPrinting().create()
		return gson.toJson(config)
	}
	*/
	def String[] sortImports(String[] imports){
		var int a = 0;
		var String buffer;
		for(var i = 0; i < imports.length; i++){
			if(!imports.get(i).contains("/")){ //if the imported module is in the same directory of the main file
				buffer = imports.get(a)
				imports.set(a,imports.get(i))
				imports.set(i,buffer)
				a++ 
			}
		}
		return imports;
	}
	
	
	def String[] getFolderImports(String[] imports){
		var List<String> result = newArrayList();
		for(s : imports){
			if(!s.contains("/")){
				result.add(s)
			}
		}
		return result
	}
	
	override doGenerate(Resource input, IFileSystemAccess fsa) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	

}
