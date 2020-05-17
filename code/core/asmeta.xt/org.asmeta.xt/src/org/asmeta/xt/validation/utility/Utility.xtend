package org.asmeta.xt.validation.utility

import com.google.inject.Injector
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.ArrayList
import java.util.Enumeration
import java.util.HashMap
import java.util.List
import javax.swing.tree.DefaultMutableTreeNode

import org.asmeta.xt.asmetal.AnyDomain
import org.asmeta.xt.asmetal.Asm
import org.asmeta.xt.asmetal.BagDomain
import org.asmeta.xt.asmetal.BagTerm
import org.asmeta.xt.asmetal.BasicTerm
import org.asmeta.xt.asmetal.BinaryOperation
import org.asmeta.xt.asmetal.Body
import org.asmeta.xt.asmetal.BooleanTerm
import org.asmeta.xt.asmetal.ChooseRule
import org.asmeta.xt.asmetal.ComprehensionTerm
import org.asmeta.xt.asmetal.ConcreteDomain
import org.asmeta.xt.asmetal.ConstantTerm
import org.asmeta.xt.asmetal.Domain
import org.asmeta.xt.asmetal.EnumElement
import org.asmeta.xt.asmetal.EnumTD
import org.asmeta.xt.asmetal.Expression
import org.asmeta.xt.asmetal.ExtendRule
import org.asmeta.xt.asmetal.FiniteQuantificationTerm
import org.asmeta.xt.asmetal.ForallRule
import org.asmeta.xt.asmetal.FunctionDefinition
import org.asmeta.xt.asmetal.FunctionInitialization
import org.asmeta.xt.asmetal.Function
import org.asmeta.xt.asmetal.FunctionTerm
import org.asmeta.xt.asmetal.Header
import org.asmeta.xt.asmetal.ImportClause
import org.asmeta.xt.asmetal.LetRule
import org.asmeta.xt.asmetal.LetTerm
import org.asmeta.xt.asmetal.MapDomain
import org.asmeta.xt.asmetal.PowersetDomain
import org.asmeta.xt.asmetal.ProductDomain
import org.asmeta.xt.asmetal.RuleDeclaration
import org.asmeta.xt.asmetal.RuleDomain
import org.asmeta.xt.asmetal.SequenceDomain
import org.asmeta.xt.asmetal.SetTerm
import org.asmeta.xt.asmetal.Signature
import org.asmeta.xt.asmetal.StructuredTD
import org.asmeta.xt.asmetal.Term
import org.asmeta.xt.asmetal.VariableTerm
import org.asmeta.xt.asmetal.importData
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.Path
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.asmeta.xt.asmetal.ExportClause
import java.util.HashSet
import org.asmeta.xt.asmetal.BooleanDomain
import org.asmeta.xt.AsmetaLStandaloneSetup
import org.asmeta.xt.asmetal.LocationTerm
import org.asmeta.xt.asmetal.AsmetalFactory
import org.asmeta.xt.asmetal.TupleTerm
import org.asmeta.xt.asmetal.DomainDefinition
import org.asmeta.xt.asmetal.impl.RuleAsTermImpl
import org.asmeta.xt.asmetal.ForallTerm
import org.eclipse.emf.common.util.BasicEList
import org.asmeta.xt.asmetal.DomainTerm
import org.eclipse.emf.ecore.util.InternalEList
import org.eclipse.emf.ecore.util.BasicInternalEList
import org.eclipse.emf.ecore.util.EObjectContainmentEList
import org.asmeta.xt.asmetal.AsmetalPackage
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.util.EObjectEList
import org.asmeta.xt.asmetal.impl.DomainTermImpl
import org.asmeta.xt.asmetal.AbstractTD
import org.asmeta.xt.asmetal.BasicTD
import org.asmeta.xt.asmetal.SetCT
import org.asmeta.xt.asmetal.MapCT
import org.asmeta.xt.asmetal.SequenceCT
import org.asmeta.xt.asmetal.BagCT
import org.asmeta.xt.asmetal.LocalFunction
import org.asmeta.xt.asmetal.AgentDomain
import org.asmeta.xt.asmetal.ReserveDomain
import javax.swing.tree.TreeNode

class Utility {

	// LIST OF ALL DATA
	public var static HashMap<String, Domain> imported_all_domain_map = new HashMap
	public var static HashMap<String, Function> imported_all_function_map = new HashMap
	public var static HashMap<String, ArrayList<RuleDeclaration>> imported_all_rule_map = new HashMap
	public var static HashMap<Function, FunctionDefinition> function_declarations_map = new HashMap
	public var static HashMap<Function, FunctionInitialization> function_initialization_map = new HashMap
	public var static HashMap<Domain, DomainDefinition> domain_declarations_map = new HashMap
	public var static HashMap<FunctionInitialization, FunctionDefinition> function_init_to_def = new HashMap

	// HASHMAP FOR IMPORTS
	public var static HashMap<String, String> imported_asm_data = new HashMap
	public var static HashMap<String, Domain> imported_domain_map = new HashMap
	public var static HashMap<String, Function> imported_function_map = new HashMap
	public var static HashMap<String, Function> imported_name_function_map = new HashMap
	public var static HashMap<String, ArrayList<Function>> imported_list_function_map = new HashMap
	public var static HashMap<String, ArrayList<RuleDeclaration>> imported_rule_map = new HashMap

	// HASHMAP FOR SIGNATURE
	public var static HashMap<String, Domain> signature_domain_map = new HashMap
	public var static HashMap<String, Domain> signature_function_domain_map = new HashMap
	public var static HashMap<String, Domain> signature_enum_domain_map = new HashMap
	public var static HashMap<String, Function> signature_function_map = new HashMap
	public var static HashMap<String, Function> signature_name_function_map = new HashMap
	public var static HashMap<String, ArrayList<Function>> signature_list_function_map = new HashMap

	// HASHMAP FOR BODY
	public var static HashMap<String, ArrayList<RuleDeclaration>> body_rule_map = new HashMap
	public var static HashMap<String, Domain> variable_domain_map = new HashMap
	public var static HashMap<String, ArrayList<EObject>> variable_origin_map = new HashMap

	// HASHMAP FOR CONVERSION
	public var static HashMap<BinaryOperation, FunctionTerm> binary_operation_conversion_map = new HashMap
	public var static HashMap<Expression, FunctionTerm> expression_conversion_map = new HashMap

	/**
	 * relative_path: path given in the ASM file (like STDL/StandardLibrary) or other formats
	 * resource_abs_path: absolute path of the file containing the import  
	 * return the absolute address or null if does not exists 
	 */
	def static String getAbsoluteAddressAsm(String relative_path, String resource_abs_path) {
		// relative path is absolute
		if(new File(relative_path).exists) return relative_path
		// muts be relative
		var String res
		if (relative_path.endsWith('.asm'))
			res = relative_path
		else
			res = relative_path + ".asm"
		// search for  relative_path in resource_abs_path
		// combination
		// var address =  resolvedPath.normalize();
		var basePath = Paths.get(resource_abs_path)
		var resolvedPath = basePath.resolve(res)
		if(Files.exists(resolvedPath)) return resolvedPath.normalize().toString
		return null
	}

	/**
	 * return null, if not found
	 */
	def static Asm getImportedAsm(String relative_path, String resource_abs_path) {

		// check if asm was already imported
		var address_str = getAbsoluteAddressAsm(relative_path, resource_abs_path)

		if(address_str === null) return null;

		// parsing
		var Injector injector = new AsmetaLStandaloneSetup().createInjectorAndDoEMFRegistration();
		var ResourceSet resourceSet = injector.getInstance(ResourceSet);
		var Resource r = resourceSet.getResource(URI.createFileURI(address_str), true);
		// aaaaa("file at: " + address_str)
		r.load(null);

		var asm_list = r.allContents.filter(Asm).toIterable
		var imported = asm_list.get(0)

		return imported

	}

	static def Integer calculateRuleArity(RuleDeclaration rule) {
		if(rule === null) return 0
		if(rule.variables === null) return 0
		return rule.variables.size
	}

	// TODO da sistemare
	static def String calculateDomainCode(Domain domain) {

		var String res

		if (domain === null)
			res = ""
		else {

			if (domain instanceof StructuredTD) {

				res = domain.name

				if (domain instanceof RuleDomain) {
					if (domain.domains.size > 0) {
						res += "("
						for (Domain dom : domain.domains)
							res += calculateDomainCode(dom) + ","
						res = res.substring(0, res.length - 1)
						res += ")"
					}
				} else if (domain instanceof ProductDomain) {
					res += "("
					for (Domain dom : domain.domains)
						res += calculateDomainCode(dom) + ","
					res = res.substring(0, res.length - 1)
					res += ")"
				} else if (domain instanceof SequenceDomain)
					res += "(" + calculateDomainCode(domain.domain) + ")"
				else if (domain instanceof BagDomain)
					res += "(" + calculateDomainCode(domain.domain) + ")"
				else if (domain instanceof PowersetDomain)
					res += "(" + calculateDomainCode(domain.baseDomain) + ")"
				else if (domain instanceof MapDomain)
					res +=
						"(" + calculateDomainCode(domain.sourceDomain) + "," +
							calculateDomainCode(domain.targetDomain) + ")"

			} else {
				res = domain.name
			}

		}

		return res
	}

	static def String calculateDomainCodeFromList(EList<Domain> domains) {

		var String res = ""

		for (Domain dom : domains) {
			res += calculateDomainCode(dom) + ","
		}
		if(!res.isEmpty) res = res.substring(0, res.length - 1)

		if(domains.size > 1) res = "Prod(" + res + ")"

		return res
	}

	static def String calculateFunctionCode(Function function) {

		var String res

		if (function.domain === null)
			res = function.name + "(" + calculateDomainCode(function.codomain) + ")"
		else
			res = function.name + "(" + calculateDomainCode(function.domain) + ")"

		return res
	}

	static def String calculateFunctionCode(FunctionInitialization function_init) {

		var String res = ""

		// if the arity of the function is > 1, the domain of the function was a product
		if (function_init.variables.size == 0)
			res = function_init.inizializedFunctionName
		else if (function_init.variables.size == 1)
			res = function_init.inizializedFunctionName + "(" + calculateDomainCode(function_init.domain.get(0)) + ")"
		else {
			res = function_init.inizializedFunctionName + "(Prod("
			for (Domain domain : function_init.domain) {
				res += calculateDomainCode(domain) + ","
			}
			res = res.substring(0, res.length - 1)
			res += "))"
		}

		return res
	}

	static def String calculateFunctionCode(FunctionDefinition function_definition) {

		var String res = ""

		// if the arity of the function is > 1, the domain of the function was a product
		if (function_definition.variables.size == 0)
			res = function_definition.definedFunctionName
		else if (function_definition.variables.size == 1)
			res = function_definition.definedFunctionName + "(" +
				calculateDomainCode(function_definition.domain.get(0)) + ")"
		else {
			res = function_definition.definedFunctionName + "(Prod("
			for (Domain domain : function_definition.domain) {
				res += calculateDomainCode(domain) + ","
			}
			res = res.substring(0, res.length - 1)
			res += "))"
		}

		return res
	}

	static def boolean fillSignatureMap(Signature signature, Boolean reset) {

		if (reset) {
			signature_domain_map.clear
			signature_enum_domain_map.clear
			signature_function_map.clear
			signature_name_function_map.clear
			signature_list_function_map.clear
		}

		var String code

		for (Domain domain : signature.domain) {
			code = calculateDomainCode(domain)

			signature_domain_map.put(code, domain)
			if (domain instanceof EnumTD) {
				for (EnumElement el : domain.element)
					signature_enum_domain_map.put(el.symbol, domain)
			}
		}

		var ArrayList<Function> temp = null

		for (Function function : signature.function) {

			code = calculateFunctionCode(function)

			putInFunctionDomainMapRec(function.domain)
			putInFunctionDomainMapRec(function.codomain)

			if (signature_list_function_map.containsKey(function.name)) {
				temp = signature_list_function_map.get(function.name)
				temp.add(function)
			} else {
				temp = new ArrayList
				temp.add(function)
				signature_list_function_map.put(function.name, temp)
			}

			signature_function_map.put(code, function)
			signature_name_function_map.put(function.name, function)
		}

		return true

	}

	static def boolean fillImportedEnumAndDomainMap(Signature signature, Boolean reset, Asm curAsm,
		ImportClause importClause) {

		if (reset) {
			imported_domain_map.clear
		}

		var String code

		for (Domain domain : signature.domain) {
			var Boolean importDomain = false;

			code = calculateDomainCode(domain)

			for (ImportClause i : curAsm.headerSection.importClause) {
				if (i == importClause) {
					if (i.importedList === null || i.importedList.size() == 0) {
						importDomain = true;
					} else {
						for (importData id : i.importedList) {
							if(id.name.equals(code.split("\\(").get(0))) importDomain = true;
						}
					}
				}
			}

			if (importDomain) {
				imported_domain_map.put(code, domain)
				if (domain instanceof EnumTD) {
					for (EnumElement el : domain.element)
						signature_enum_domain_map.put(el.symbol, domain)
				}
			}
		}

		return true

	}

	static def void putInFunctionDomainMapRec(Domain domain) {
		if (domain instanceof StructuredTD) {

			signature_function_domain_map.put(calculateDomainCode(domain), domain)

			var type = ""

			if (domain instanceof RuleDomain) {
				if (domain.domains.size > 0) {
					for (Domain dom : domain.domains)
						putInFunctionDomainMapRec(dom)
				}
			} else if (domain instanceof ProductDomain) {
				type = "Prod("

				var ProductDomain pd = AsmetalFactory.eINSTANCE.createProductDomain
				pd.name = "Prod"
				
				for (Domain dom : domain.domains) {
					putInFunctionDomainMapRec(dom)
					if (getDomain(dom.name) instanceof ConcreteDomain) {
						type = type + (getDomain(dom.name) as ConcreteDomain).typeDomain.calculateDomainCode + ","
						
						var Domain tempDom = AsmetalFactory.eINSTANCE.createDomain
						tempDom.name = (getDomain(dom.name) as ConcreteDomain).typeDomain.calculateDomainCode
						pd.domains.add(tempDom)
						
					} else {
						type = type + dom.calculateDomainCode + ","
						
						var Domain tempDom = AsmetalFactory.eINSTANCE.createDomain
						tempDom.name = dom.calculateDomainCode
						pd.domains.add(tempDom)
												
					}	
				}
				type = type.substring(0, type.length - 1) + ")"
				if (!Utility.isCompatible(pd, domain))
					signature_function_domain_map.put(type, pd)
				else
					signature_function_domain_map.put(type, domain)
				
				/*for (Domain dom : domain.domains) {
					putInFunctionDomainMapRec(dom)
					if (getDomain(dom.name) instanceof ConcreteDomain)
						type = type + (getDomain(dom.name) as ConcreteDomain).typeDomain.calculateDomainCode + ","
					else
						type = type + dom.calculateDomainCode + ","
				}
				type = type.substring(0, type.length - 1) + ")"
				signature_function_domain_map.put(type, domain)*/

			} else if (domain instanceof SequenceDomain)
				putInFunctionDomainMapRec(domain.domain)
			else if (domain instanceof BagDomain)
				putInFunctionDomainMapRec(domain.domain)
			else if (domain instanceof PowersetDomain)
				putInFunctionDomainMapRec(domain.baseDomain)

		} else
			return
	}

	static def boolean fillBodyMap(Body body) {

		body_rule_map.clear

		var String code

		for (RuleDeclaration rule : body.ruleDeclaration) {
			code = rule.name
			
			if (body_rule_map.containsKey(code)) {
				if (body_rule_map.get(code).contains(rule))
					return false
				else
					body_rule_map.get(code).add(rule)
			}	
			else {
				var ArrayList<RuleDeclaration> list = new ArrayList<RuleDeclaration>()
				list.add(rule)
				body_rule_map.put(code, list)
			}
			
		}
	}

	static def boolean fillOnlyDomainMap(Signature signature) {

		signature_domain_map.clear
		var String code

		for (Domain domain : signature.domain) {
			code = calculateDomainCode(domain)
			signature_domain_map.put(code, domain)
		}

		return true

	}
	// TODO attenzione cje potrebbe essere chiamata ricorsivamente all'infinito quando ho import ciclici
	// da fare un controllo 
	static def void fillAllImportedMap(Header header, Boolean reset) {
		//
		//header.eContainer
		if (reset) {
			imported_all_domain_map.clear
			imported_all_function_map.clear
			imported_all_rule_map.clear

			imported_asm_data.clear
			imported_domain_map.clear
			imported_function_map.clear
			imported_rule_map.clear

			imported_list_function_map.clear
		}

		var Asm imported_asm

		for (ImportClause importClause : header.importClause) {

			// get the imported main file directory
			var dir = getBaseDir(importClause);
			imported_asm = Utility.getImportedAsm(importClause.moduleName, dir)

			if (imported_asm !== null && !imported_asm_data.containsKey(imported_asm.name)) {
				
				imported_asm_data.put(imported_asm.name, imported_asm.name)

				// Import all the files included into the imported ASM
				if (imported_asm.headerSection !== null && imported_asm.headerSection.importClause !== null &&
					imported_asm.headerSection.importClause.size() > 0)
					fillAllImportedMap(imported_asm.headerSection, false);

				// Import all the enums
				if (imported_asm.headerSection !== null && imported_asm.headerSection.signature !== null) {
					fillImportedEnumAndDomainMap(imported_asm.headerSection.signature, false, header.eContainer as Asm,
						importClause)
				}

				// Import all the exported rules
				if (imported_asm.headerSection !== null && imported_asm.headerSection.exportClause !== null)
					fillImportedRuleMap(imported_asm.bodySection, imported_asm.headerSection.exportClause)

				// parse the asm				 
				// add the data from the imported asm to the maps 
				fillImportedMap(imported_asm.headerSection, importClause)
			}
		}
	}

	static def void fillImportedRuleMap(Body body, ExportClause exportClause) {

		var String code

		for (RuleDeclaration rule : body.ruleDeclaration) {
			code = rule.name
			
			if (exportClause.exportedList.contains(code) || exportClause.exportAll) {
				
				if (!imported_rule_map.containsKey(code)) {
					var ArrayList<RuleDeclaration> list = new ArrayList<RuleDeclaration> ()
					list.add (rule)
					imported_rule_map.put(code, list)
				} else {
					if (!imported_rule_map.get(code).contains(rule))
						imported_rule_map.get(code).add(rule)
				}
				
			}			
			
		}
	}

	/** return the base dir for an import clause */
	static def String getBaseDir(ImportClause importClause) {
		// try to get the location when the file is open in eclipse
		// for example in case of the plugin
		val uri = importClause.eResource.getURI()
		var platformString = uri.toPlatformString(true)
		if (platformString !== null) {
			try {
				ResourcesPlugin.getWorkspace();
				// workspace is open 
				// get the main file	
				var myFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(platformString));
				return myFile.getLocation().toFile().parent;
			} catch (java.lang.IllegalStateException e) {
				// workspace is closed
			}
		}
		// it is a local file
		// File(uri.toFileString()).paren ritorna null
		var res = new File(uri.toFileString()).parent
		if(res !== null) return res;

		return new File(".").absolutePath
	}

	static def void fillImportedMap(Header importedHeader, ImportClause importClause) {

		var asm = importedHeader.eContainer as Asm

		// TODO non devo prendere tutti i domain, solo quelli che esporto
		var String name
		var boolean exporting
		var boolean importing

		var HashMap<String, String> imported_list_map = new HashMap
		for (importData data : importClause.importedList) {
			imported_list_map.put(data.name, data.name)
		}

		for (Domain domain : importedHeader.signature.domain) {
			name = calculateDomainCode(domain)
			imported_all_domain_map.put(name, domain)
			imported_asm_data.put(asm.name, asm.name)

			// we add a domain to the imported domain only if we have exported and imported
			if (importedHeader.exportClause !== null) {
				exporting = importedHeader.exportClause.exportAll ||
					importedHeader.exportClause.exportedList.contains(domain.name)
				importing = importClause.importedList === null || importClause.importedList.size == 0 ||
					imported_list_map.containsKey(domain.name)

				if (importClause.importedList !== null && importClause.importedList.size() > 0) {
					var Boolean exists = false;
					for (importData i : importClause.importedList) {
						if(i.name.equals(name.split("\\(").get(0))) exists = true;
					}
					importing = importing && exists;
				}

				if (exporting && importing) {
					imported_domain_map.put(name, domain)
				}
			}

		}

		var ArrayList<Function> temp = null

		for (Function function : importedHeader.signature.function) {
			name = calculateFunctionCode(function)
			imported_all_function_map.put(name, function)
			imported_asm_data.put(name, asm.name)

			// aaaaa( "adding: " + name )
			if (importedHeader.exportClause !== null) {
				exporting = importedHeader.exportClause.exportAll ||
					importedHeader.exportClause.exportedList.contains(function.name)
				importing = importClause.importedList === null || importClause.importedList.size == 0 ||
					imported_list_map.containsKey(function.name)

				if (importClause.importedList !== null && importClause.importedList.size() > 0) {
					var Boolean exists = false;
					for (importData i : importClause.importedList) {
						if(i.name.equals(name.split("\\(").get(0))) exists = true;
					}
					importing = importing && exists;
				}

				if (exporting && importing) {
					imported_name_function_map.put(function.name, function)
					imported_function_map.put(name, function)

					if (imported_list_function_map.containsKey(function.name)) {
						temp = imported_list_function_map.get(function.name)
						temp.add(function)
					} else {
						temp = new ArrayList
						temp.add(function)
						imported_list_function_map.put(function.name, temp)
					}

					putInFunctionDomainMapRec(function.domain)
					putInFunctionDomainMapRec(function.codomain)

				}

			}
		}

	}

	def static boolean isInMaps(String id) {
		return isInImportedMap(id) || isInSignatureMap(id)
	}

	def static boolean isInImportedMap(String id) {
		return imported_domain_map.containsKey(id) || imported_name_function_map.containsKey(id) ||
			imported_rule_map.containsKey(id)
	}

	def static boolean isFunctionName(String id) {
		return imported_name_function_map.containsKey(id) || signature_name_function_map.containsKey(id)
	}

	def static boolean isRule(String id) {
		return imported_rule_map.containsKey(id) || body_rule_map.containsKey(id)
	}

	def static boolean isInSignatureMap(String id) {
		return signature_domain_map.containsKey(id) || signature_name_function_map.containsKey(id) ||
			body_rule_map.containsKey(id)
	}

	def static boolean isDomainDeclared(Domain domain) {
		if(domain === null) return false
		var name = calculateDomainCode(domain)
		return imported_domain_map.containsKey(name) || signature_domain_map.containsKey(name)
	}

	def static boolean isDomainDeclaredPrivate(String name) {
		return !imported_domain_map.containsKey(name) && imported_all_domain_map.containsKey(name)
	}

	def static boolean isFunctionDeclaredPrivate(String name) {
		return !imported_function_map.containsKey(name) && imported_all_function_map.containsKey(name)
	}

	def static boolean isRuleDeclaredPrivate(String name) {
		return !imported_rule_map.containsKey(name) && imported_all_rule_map.containsKey(name)
	}

	def static Domain getDomain(String id) {
		var domain = signature_domain_map.get(id)
		if(domain !== null) return domain
		domain = signature_function_domain_map.get(id)
		if(domain !== null) return domain
		domain = imported_domain_map.get(id)
		if(domain !== null) return domain
		domain = imported_all_domain_map.get(id)
		if(domain !== null) return domain
		domain = signature_enum_domain_map.get(id)
		if(domain !== null) return domain
		domain = variable_domain_map.get(id)
		if(domain !== null) return domain

		return null;
	}

	def static Domain getEnumParent(String id) {
		return signature_enum_domain_map.get(id)
	}

	def static String getOriginalAsm(String data) {
		return imported_asm_data.get(data)
	}

	// formato: name(TIPO1,TIPO2)
	//
	def static Function getFunction(String id) {
		var function = signature_function_map.get(id)
		if(function !== null) return function

		// Substitute the domains in Seq(...), Powerset(...), Bag(...) with "D"
		var String newName
		newName = id.replaceAll("Seq\\(([^\\)]*)\\)", "Seq(D)")
		newName = newName.replaceAll("Powerset\\(([^\\)]*)\\)", "Powerset(D)")
		newName = newName.replaceAll("Bag\\(([^\\)]*)\\)", "Bag(D)")

		function = imported_function_map.get(newName)
		if(function !== null) return function


		// Try to search the function applicable to the TyoeDomain
		if (newName.contains("(")) {
			newName = newName.split("\\(").get(1)
			newName = newName.replaceAll("\\)", "")
			if (Utility.getDomain(newName) instanceof ConcreteDomain) {
				var ConcreteDomain newDomain = Utility.getDomain(newName) as ConcreteDomain
				if (newDomain !== null) {
					newName = id
					newName = newName.replaceAll("\\(([^\\)]*)\\)",
						"(" + newDomain.typeDomain.name + ")")
					function = imported_function_map.get(newName)
				}
			}
		}
		function = imported_function_map.get(newName)
		if(function !== null) return function

		newName = id.replaceAll("Seq\\(([^\\)]*)\\)", "Seq(D)")
		newName = newName.replaceAll("Powerset\\(([^\\)]*)\\)", "Powerset(D)")
		newName = newName.replaceAll("Bag\\(([^\\)]*)\\)", "Bag(D)")
		function = imported_function_map.get(newName)
		if(function !== null) return function

		// Try to search the function applicable to the TyoeDomain
		if (newName.contains("(")) {
			newName = newName.split("\\(").get(1)
			newName = newName.replaceAll("\\)", "")
			if (Utility.getDomain(newName) instanceof ConcreteDomain) {
				var ConcreteDomain newDomain = Utility.getDomain(newName) as ConcreteDomain
				if (newDomain !== null) {
					newName = id
					newName = newName.replaceAll("\\(([^\\)]*)\\)",
						"(" + newDomain.typeDomain.name + ")")
					function = imported_function_map.get(newName)
				}
			}
		}
		return imported_function_map.get(newName)
	}

	def static Function getFunctionByName(String id) {
		// TODO da utilizzar einevce queall con gli argomenti per permettere overload di nomi
		var function = signature_name_function_map.get(id)
		if(function !== null) return function
		function = imported_name_function_map.get(id)
		if(function !== null) return function
		function = imported_all_function_map.get(id)
		if(function !== null) return function
		return null
	}

	def static ArrayList<RuleDeclaration> getRule(String id) {
		var rule = body_rule_map.get(id)
		if(rule !== null) return rule
		rule = imported_rule_map.get(id)
		if(rule !== null) return rule
		return imported_all_rule_map.get(id)
	}

	def static resetAllMap() {

		imported_all_domain_map.clear
		imported_all_function_map.clear
		imported_all_function_map.clear

		imported_asm_data.clear
		imported_domain_map.clear
		imported_function_map.clear
		imported_name_function_map.clear
		imported_rule_map.clear

		signature_domain_map.clear
		signature_function_domain_map.clear
		signature_function_map.clear
		signature_name_function_map.clear
		body_rule_map.clear

	}

	/**
	 * Calculate the term's domain and then check if it's compatible with <code>domain</code>
	 */
	def static boolean isBoolean(Term body) {

		if (body instanceof BinaryOperation) {
			var String[] boolean_operation = #["or", "xor", "iff", "implies", "and", "=", "!=", ">", "<", ">=", "<="]

			for (var int i = 0; i < boolean_operation.length; i++) {
				if(body.op.equals(boolean_operation.get(i))) return true
			}

			return false
		} // variable term like $X - salvato nella mappa
		else if (body instanceof VariableTerm) {
			var domain = Utility.getDomainFromVariable((body as VariableTerm).name)
			// check if variable exist
			if (domain === null) {
				return false
			}
			return domain.name.equals("Boolean") || domain instanceof BooleanDomain
		} else if (body instanceof FunctionTerm) {
			// TODO approccio naive, controllo solo attraverso il nome, non tengo conto dell'overloading
			var func = getFunctionByName(body.functionName)

			if(func === null || func.codomain === null) return false
			return func.codomain.name.equals("Boolean") || func.codomain instanceof BooleanDomain
		} else if (body instanceof FiniteQuantificationTerm) {
			return true
		} else if (body instanceof BooleanTerm) {
			return true
		} else if (body instanceof Expression) {
			if(body.op === null) return false
			return body.op.equals("not")
		} else if (body instanceof LetTerm) {
			if(body.body instanceof BooleanTerm) return true
			return false
		}

		return false
	}

	def static String getBasicTermCode(BasicTerm term) {

		var String res = ""

		if (term instanceof FunctionTerm)
			res = term.functionName
		else if (term instanceof ConstantTerm)
			res = term.symbol
		else if (term instanceof VariableTerm)
			res = term.name

		return res

	}

	def static boolean isFromAnyDomain(Domain domain) {

		if (domain instanceof StructuredTD) {

			var boolean res = true

			if (domain instanceof RuleDomain) {
				if (domain.domains.size > 0) {
					for (Domain dom : domain.domains) {
						res = res && isFromAnyDomain(dom)
					}
				}
			} else if (domain instanceof ProductDomain) {
				for (Domain dom : domain.domains) {
					res = res && isFromAnyDomain(dom)
				}
			} else if (domain instanceof SequenceDomain)
				res = isFromAnyDomain(domain.domain)
			else if (domain instanceof BagDomain)
				res = isFromAnyDomain(domain.domain)
			else if(domain instanceof PowersetDomain) res = isFromAnyDomain(domain.baseDomain)

			return res
		} else {
			// TOOD per ora se non trova il dominio torno true
			if(isFromTrueAnyDomain(domain)) return true
		}

		return false

	}

	def static boolean isFromTrueAnyDomain(Domain domain) {
		if(domain === null) return false
		var Domain dom = getDomain(domain.name)
		if(dom instanceof AnyDomain) return true
		return false
	}

	def static boolean isCompatible(DomainTree origin_dom, DomainTree destination_dom) {
		var HashMap<String, DefaultMutableTreeNode> any_map = new HashMap
		// println("---")
		var res = isCompatible(origin_dom.model.root as DefaultMutableTreeNode,
			destination_dom.model.root as DefaultMutableTreeNode, any_map)
		// println("---")
		return res
	}
	
	def static boolean isSubsetOfAbstractDomain (String dom1, String dom2) {
		var domain1 = getDomain(dom1)
		var domain2 = getDomain(dom2)
		
		if (!(domain1 instanceof ConcreteDomain) && !(domain2 instanceof ConcreteDomain) && !(domain2 instanceof AbstractTD))
			return true;
		
		if(domain1 instanceof ConcreteDomain) {
			while (domain1 instanceof ConcreteDomain && !domain1.name.equals(domain2.name))
				domain1 = getDomain(domain1.typeDomain.name)
				
			if (domain1.name.equals(domain2.name))
				return true
			else 
				return false
		}
		
		return true
	}

	def static boolean isCompatible(DefaultMutableTreeNode origin_node, DefaultMutableTreeNode destination_node,
		HashMap<String, DefaultMutableTreeNode> any_map) {

		if(origin_node === null || destination_node === null) return false
		if(origin_node.toString.equals("Undef") && !destination_node.toString.equals("Undef")) return true
		if(!origin_node.toString.equals("Undef") && destination_node.toString.equals("Undef")) return true

		// name of the node. if the domain is strucutred domain, it return the type
		// ex: origin_node=Seq(Integer) 		name=Seq
		var name_origin = origin_node.toString
		var name_destination = destination_node.toString

		// return the compleate name of the domain from the tree
		// ex: origin_node=Seq(Integer)			complete_name_origin = Seq(Integer)
		var complete_name_origin = DomainTree.getCodeFromTree(origin_node)
		var complete_name_destination = DomainTree.getCodeFromTree(destination_node)

		if(name_origin.equals("D") || complete_name_destination.equals("Any")) return true;

		if(name_origin === null || name_destination === null) return false
		if(name_origin.isEmpty || name_destination.isEmpty) return false

		// get the domains
		var dom_source = getDomain(complete_name_origin)
		var dom_target = getDomain(complete_name_destination)

		if(dom_source instanceof AnyDomain || dom_target instanceof AnyDomain) return true;
		
		if (dom_source instanceof ConcreteDomain && dom_target instanceof StructuredTD) {
			
			var domName = (dom_target as StructuredTD).calculateDomainCode.split("\\(").get(1).replace(")","")
			if (getDomain(domName) instanceof AnyDomain)
				return true;
			
			return isCompatible(DomainTree.getTreeFromDomain((dom_source as ConcreteDomain).typeDomain).model.root as DefaultMutableTreeNode, DomainTree.getTreeFromDomain(dom_target).model.root  as DefaultMutableTreeNode, any_map)
		}

		// if the origin is from a structured domain but the destination was an
		// empty list, the domains are compatible
		var String[] check_empty = #["Map", "Powerset", "Seq", "Bag"]
		for (String str : check_empty) {
			var name1 = str
			var name2 = "Any_" + str
			if(name_destination.equals(name1) && name_origin.equals(name2)) return true
			if(name_origin.equals(name1) && name_destination.equals(name2)) return true
			
			

			if(name_destination.equals(name1) &&
				("Any_" + name_origin.replaceAll("\\(\\)", "")).equals(name2)) return true
			if(name_origin.equals(name1) &&
				("Any_" + name_destination.replaceAll("\\(\\)", "")).equals(name2)) return true
		}

		if (complete_name_origin.equals(complete_name_destination))
			return true
		else {

			var dom_origin_any = isFromTrueAnyDomain(dom_source)
			// println("check " + dom_origin_any)	
			if (dom_origin_any) {
				// we have to check the any compatibility
				if (any_map.containsKey(complete_name_origin)) {
					var dom_mapped = any_map.get(complete_name_origin)
					// check if the two domain are compatible
					return isCompatible(dom_mapped, destination_node, any_map)
				} else {
					any_map.put(complete_name_origin, destination_node)
					return true
				}
			} else {

				if (dom_source !== null && dom_target !== null) {
					var r = isCompatible(dom_source, dom_target)

					// println("controllo d " + calculateDomainCode(dom_source) + " " + calculateDomainCode(dom_target) +" = " + r)
					if (!r) {
						if (dom_target instanceof ConcreteDomain && (!(dom_source instanceof AbstractTD) || isAgentDomain(dom_source.name))) {
							r = isCompatible(dom_source, (dom_target as ConcreteDomain).typeDomain)
						}
					}
					return r
				} else {
					if (dom_target instanceof ConcreteDomain) {
						return isCompatible(origin_node,
							DomainTree.getTreeFromDomain(dom_target.typeDomain).model.root as DefaultMutableTreeNode,
							new HashMap)
					}
				}

			}
		}

		if (origin_node.childCount !== destination_node.childCount && !isSpecialCase(dom_source, dom_target))
			return false

		var DefaultMutableTreeNode n1
		var DefaultMutableTreeNode n2
		var int i = 0
		var boolean res = true
		for (var Enumeration e = origin_node.children(); e.hasMoreElements();) {
			n1 = e.nextElement() as DefaultMutableTreeNode
			if (i < destination_node.childCount) {
				n2 = destination_node.getChildAt(i) as DefaultMutableTreeNode
				res = res && isCompatible(n1, n2, any_map)
				i++
			}
		}

		return res

	}

	def static boolean isSpecialCase(Domain dom1, Domain dom2) {
		if (dom1 instanceof PowersetDomain)
			return isCompatible(dom1.baseDomain, dom2)
		else if(dom2 instanceof PowersetDomain) return isCompatible(dom1, dom2.baseDomain) else return false
	}

	def static boolean isCompatible(Domain dom2, Domain dom1) {
		// One of the two domains is AnyDomain, so they are always compatible
		if((isFromAnyDomain(dom1) || isFromAnyDomain(dom2)) && dom1.class.equals(dom2.class)) return true

		// If one of the two domain is null, they are not compatible
		if(dom1 === null || dom2 === null) return false

		var name1 = calculateDomainCode(dom1)
		var name2 = calculateDomainCode(dom2)
		
		if((name1.equals("AnyAgent") && Utility.isAgentDomain(name2)) ||
			(name2.equals("AnyAgent") && Utility.isAgentDomain(name1)))
			return true;

		// Domains with the same name -> COMPATIBLE
		if(name1.equals(name2)) return true

		// Concrete domains
		if (dom1 instanceof ConcreteDomain && dom2 instanceof ConcreteDomain) {
			var type_name1 = calculateDomainCode((dom1 as ConcreteDomain).typeDomain)
			var type_name2 = calculateDomainCode((dom2 as ConcreteDomain).typeDomain)
			if(type_name1.equals(type_name2)) return true
		} else if (dom1 instanceof ConcreteDomain  && 
			(!(getDomain((dom1 as ConcreteDomain).typeDomain.name) instanceof AbstractTD))) {
			var type_name1 = calculateDomainCode((dom1 as ConcreteDomain).typeDomain)
			if(type_name1.equals(name2)) return true
		} else if (dom2 instanceof ConcreteDomain) {
			var type_name2 = calculateDomainCode((dom2 as ConcreteDomain).typeDomain)
			if(name1.equals(type_name2)) return true
		}else {
			var domain1 = getDomain(dom1.name)
			var domain2 = getDomain(dom2.name)
			if (domain1 !== null && domain2 !== null)
				if ((domain1 instanceof ConcreteDomain /**/ &&
					(!(getDomain((domain1 as ConcreteDomain).typeDomain.name) instanceof AbstractTD))/**/) ||
					domain2 instanceof ConcreteDomain)
					if(isCompatible(domain1, domain2)) return true
		}

		// Powerset domains
		if (dom1 instanceof PowersetDomain && dom2 instanceof PowersetDomain) {
			var power_dom1 = dom1 as PowersetDomain
			var power_dom2 = dom2 as PowersetDomain
			return isCompatible(power_dom1.baseDomain, power_dom2.baseDomain)
		} else if (dom1 instanceof PowersetDomain) {
			var domain1 = getDomain(dom1.baseDomain.name)
			var domain2 = dom2
			if (domain1 !== null) {
				return isCompatible(domain1, domain2)
			} else {
				return isCompatible(dom1.baseDomain, dom2)
			}
		} else if (dom2 instanceof PowersetDomain) {
			var domain1 = dom1
			var domain2 = getDomain(dom2.baseDomain.name)
			if (domain2 !== null) {
				return isCompatible(domain1, domain2)
			} else {
				return isCompatible(dom1, dom2.baseDomain)
			}
		}

		// Product domains
		if (dom1 instanceof ProductDomain && dom2 instanceof ProductDomain) {
			// If the two domains are "Product", it is important to check first their size
			// -> If the sizes are different, the two domains cannot be compatible
			// -> If the sizes are the same, each element in the first domain must be compatible with 
			// the corresponding element of the second domain 
			var product_dom1 = dom1 as ProductDomain
			var product_dom2 = dom2 as ProductDomain
			if (product_dom1.domains.size != product_dom2.domains.size)
				return false
			else {
				var Boolean isComp = true;
				/*for (var i = 0; i < product_dom1.domains.size; i++) {
				 * 	if (!isCompatible(getDomain(product_dom1.domains.get(i).name),
				 * 		getDomain(product_dom2.domains.get(i).name)))
				 * 		isComp = false;
				 }*/
				for (var i = 0; i < product_dom1.domains.size; i++) {
					if (!isCompatible(product_dom1.domains.get(i), product_dom2.domains.get(i)))
						isComp = false;
				}

				return isComp;
			}
		} else if (dom1 instanceof PowersetDomain) {
			return false;
		} else if (dom2 instanceof PowersetDomain) {
			return false;
		}

		// Bag domains
		if (dom2 instanceof BagDomain) {
			return isCompatible(dom1, dom2.domain)
		}

		// Sequence domains
		if (dom1 instanceof SequenceDomain && dom2 instanceof SequenceDomain) {
			var seq_dom1 = dom1 as SequenceDomain
			var seq_dom2 = dom2 as SequenceDomain
			return isCompatible(seq_dom1, seq_dom2)
		} else if (dom1 instanceof SequenceDomain) {
			return isCompatible(dom1.domain, dom2)
		} else if (dom2 instanceof SequenceDomain) {
			return isCompatible(dom1, dom2.domain)
		}

		// If none of the previous cases is matched, the two domains are not compatible
		return false
	}
	
	def static boolean isCompatibleFunction(Domain dom2, Domain dom1) {
		// One of the two domains is AnyDomain, so they are always compatible
		if((isFromAnyDomain(dom1) || isFromAnyDomain(dom2)) && dom1.class.equals(dom2.class)) return true

		// If one of the two domain is null, they are not compatible
		if(dom1 === null || dom2 === null) return false

		var name1 = calculateDomainCode(dom1)
		var name2 = calculateDomainCode(dom2)
		
		if((name1.equals("AnyAgent") && Utility.isAgentDomain(name2)) ||
			(name2.equals("AnyAgent") && Utility.isAgentDomain(name1)))
			return true;

		// Domains with the same name -> COMPATIBLE
		if(name1.equals(name2)) return true

		// Concrete domains
		if (dom1 instanceof ConcreteDomain && dom2 instanceof ConcreteDomain) {
			var type_name1 = calculateDomainCode((dom1 as ConcreteDomain).typeDomain)
			var type_name2 = calculateDomainCode((dom2 as ConcreteDomain).typeDomain)
			if(type_name1.equals(type_name2)) return true
		} else if (dom1 instanceof ConcreteDomain/**/  && 
			(!(getDomain((dom1 as ConcreteDomain).typeDomain.name) instanceof AbstractTD) /*||
				Utility.isAgentDomain(name1) ||
				Utility.isReserveDomain(name1)*/)) {
			var type_name1 = calculateDomainCode((dom1 as ConcreteDomain).typeDomain)
			if(type_name1.equals(name2)) return true
		} else if (dom2 instanceof ConcreteDomain && dom1 instanceof AbstractTD/* && !Utility.isAgentDomain(name1) && !Utility.isReserveDomain(name1)*/) {
			return false
		} else if (dom2 instanceof ConcreteDomain) {
			var type_name2 = calculateDomainCode((dom2 as ConcreteDomain).typeDomain)
			if(name1.equals(type_name2)) return true
		} else if (dom1 instanceof ConcreteDomain){
			if(isCompatibleFunction(getDomain(dom1.typeDomain.name), dom2)) return true
		}

		// Powerset domains
		if (dom1 instanceof PowersetDomain && dom2 instanceof PowersetDomain) {
			var power_dom1 = dom1 as PowersetDomain
			var power_dom2 = dom2 as PowersetDomain
			return isCompatibleFunction(power_dom1.baseDomain, power_dom2.baseDomain)
		} else if (dom1 instanceof PowersetDomain) {
			var domain1 = getDomain(dom1.baseDomain.name)
			var domain2 = dom2
			if (domain1 !== null) {
				return isCompatibleFunction(domain1, domain2)
			} else {
				return isCompatibleFunction(dom1.baseDomain, dom2)
			}
		} else if (dom2 instanceof PowersetDomain) {
			var domain1 = dom1
			var domain2 = getDomain(dom2.baseDomain.name)
			if (domain2 !== null) {
				return isCompatibleFunction(domain1, domain2)
			} else {
				return isCompatibleFunction(dom1, dom2.baseDomain)
			}
		}

		// Product domains
		if (dom1 instanceof ProductDomain && dom2 instanceof ProductDomain) {
			// If the two domains are "Product", it is important to check first their size
			// -> If the sizes are different, the two domains cannot be compatible
			// -> If the sizes are the same, each element in the first domain must be compatible with 
			// the corresponding element of the second domain 
			var product_dom1 = dom1 as ProductDomain
			var product_dom2 = dom2 as ProductDomain
			if (product_dom1.domains.size != product_dom2.domains.size)
				return false
			else {
				var Boolean isComp = true;
				/*for (var i = 0; i < product_dom1.domains.size; i++) {
				 * 	if (!isCompatible(getDomain(product_dom1.domains.get(i).name),
				 * 		getDomain(product_dom2.domains.get(i).name)))
				 * 		isComp = false;
				 }*/
				for (var i = 0; i < product_dom1.domains.size; i++) {
					if (!isCompatibleFunction(product_dom1.domains.get(i), product_dom2.domains.get(i)))
						isComp = false;
				}

				return isComp;
			}
		} else if (dom1 instanceof PowersetDomain) {
			return false;
		} else if (dom2 instanceof PowersetDomain) {
			return false;
		}

		// Bag domains
		if (dom2 instanceof BagDomain) {
			return isCompatibleFunction(dom1, dom2.domain)
		}

		// Sequence domains
		if (dom1 instanceof SequenceDomain && dom2 instanceof SequenceDomain) {
			var seq_dom1 = dom1 as SequenceDomain
			var seq_dom2 = dom2 as SequenceDomain
			return isCompatibleFunction(seq_dom1, seq_dom2)
		} else if (dom1 instanceof SequenceDomain) {
			return isCompatibleFunction(dom1.domain, dom2)
		} else if (dom2 instanceof SequenceDomain) {
			return isCompatibleFunction(dom1, dom2.domain)
		}

		// If none of the previous cases is matched, the two domains are not compatible
		return false
	}

	def static boolean isCompatible(Domain dom1, ArrayList<Domain> dom) {

		if(dom1 === null) return false

		if(dom.size === 0) return false

		if(dom.size === 1) return isCompatible(dom1, dom.get(0))

		if (dom1 instanceof ProductDomain) {

			var boolean res = true
			var Domain true_dom

			for (var int i = 0; i < dom1.domains.size; i++) {
				true_dom = getDomain(dom1.domains.get(i).name)
				res = res && isCompatible(true_dom, dom.get(i))
			}

			return res

		}

		return false

	}

	def static Domain getCommonDomain(DefaultMutableTreeNode node1, DefaultMutableTreeNode node2) {

		// TODO versione semplice, poi confrontere gli alberi
		var dom1 = getDomain(DomainTree.getCodeFromTree(node1))
		var dom2 = getDomain(DomainTree.getCodeFromTree(node2))

		if (dom1 === null || dom2 === null) {
			if((node2 !== null && node2.userObject !== null) && (node1 !== null && node1.userObject !== null) &&
				node1.userObject.toString.equals("Any_" + node2.userObject.toString)) return dom2
			if((node2 !== null && node2.userObject !== null) && (node1 !== null && node1.userObject !== null) &&
				node2.userObject.toString.equals("Any_" + node1.userObject.toString)) return dom1
			return null
		}

		if(dom1 === dom2) return dom2

		if(dom1 instanceof AnyDomain && dom2 instanceof AnyDomain) return dom1
		if(dom1 instanceof AnyDomain) return dom2
		if(dom2 instanceof AnyDomain) return dom1

		if (dom1.class === dom2.class) {
			if (dom1 instanceof ConcreteDomain && dom2 instanceof ConcreteDomain) {
				var conc_dom1 = dom1 as ConcreteDomain
				var conc_dom2 = dom1 as ConcreteDomain

				if(conc_dom2.name.equals(dom1.name)) return dom1
				if(conc_dom1.name.equals(dom2.name)) return dom2
				if(conc_dom1.name.equals(conc_dom2.name)) return conc_dom1

			}

			// Check if the one of them is a Seq/Set/Map and the other is a AnyDomain based Seq/Set/Map
			if (dom1 instanceof StructuredTD && dom2 instanceof StructuredTD) {
				var sub_dom1 = dom1.calculateDomainCode().split("\\(").get(1)
				var sub_dom2 = dom2.calculateDomainCode().split("\\(").get(1)

				sub_dom1 = sub_dom1.substring(0, sub_dom1.length - 1)
				sub_dom2 = sub_dom2.substring(0, sub_dom2.length - 1)

				var commonDomain = getCommonDomain(DomainTree.getNodeFromDomain(getDomain(sub_dom1)),
					DomainTree.getNodeFromDomain(getDomain(sub_dom2)))

				var ArrayList<DefaultMutableTreeNode> list = new ArrayList
				list.add(DomainTree.getNodeFromDomain(commonDomain))

				if (dom1 instanceof PowersetDomain) {
					return getDomain(DomainTree.getCodeFromTree(DomainTree.getComposedNodeFromNodes("Powerset", list)))
				} else if (dom1 instanceof SequenceDomain) {
					return getDomain(DomainTree.getCodeFromTree(DomainTree.getComposedNodeFromNodes("Seq", list)))
				} else if (dom1 instanceof BagDomain) {
					return getDomain(DomainTree.getCodeFromTree(DomainTree.getComposedNodeFromNodes("Bag", list)))
				} else if (dom1 instanceof MapDomain) {
					return getDomain(DomainTree.getCodeFromTree(DomainTree.getComposedNodeFromNodes("Map", list)))
				} else if (dom1 instanceof ProductDomain) {
					return getDomain(DomainTree.getCodeFromTree(DomainTree.getComposedNodeFromNodes("Prod", list)))
				}

			}

		} else {

			// se uno dei due è un Undef e l'altro no, ritorno quello non Undef
			if(dom1.name.equals("Undef") && !dom2.name.equals("Undef")) return dom2;
			if(dom2.name.equals("Undef") && !dom1.name.equals("Undef")) return dom1;

			// se uno dei due è concrete, allora scelgo il type domain 
			if (dom1 instanceof ConcreteDomain) {
				if(dom1.typeDomain.name.equals(dom2.name)) return dom2
			}

			if (dom2 instanceof ConcreteDomain) {
				if(dom2.typeDomain.name.equals(dom1.name)) return dom1
			}

		}

		return null

	}

	def static Domain getCommonDomain(Domain dom1, Domain dom2) {

		if(dom1 === null || dom2 === null) return null

		if(dom1 === dom2) return dom2

		// Se uno dei due è undefDomain, ritorno l'altro
		if(dom1.name.equals("Undef")) return dom2
		if(dom2.name.equals("Undef")) return dom1

		if (dom1.class === dom2.class) {
			if (dom1 instanceof ConcreteDomain && dom2 instanceof ConcreteDomain) {
				var conc_dom1 = dom1 as ConcreteDomain
				var conc_dom2 = dom1 as ConcreteDomain

				if(conc_dom2.name.equals(dom1.name)) return dom1
				if(conc_dom1.name.equals(dom2.name)) return dom2
				if(conc_dom1.name.equals(conc_dom2.name)) return conc_dom1

			}

		} else {

			// se uno dei due è concrete, allora scelgo il type domain 
			if (dom1 instanceof ConcreteDomain) {
				if(dom1.typeDomain.name.equals(dom2.name)) return dom2
			}

			if (dom2 instanceof ConcreteDomain) {
				if(dom2.typeDomain.name.equals(dom1.name)) return dom1
			}
		}

		return null

	}

	def static Domain getCommonDomainFromListBasic(List<BasicTerm> basic_terms) {

		var ArrayList<Term> terms = new ArrayList
		for (BasicTerm basic : basic_terms) {
			terms.add(basic)
		}
		return getCommonDomainFromList(terms)

	}

	def static Domain getCommonDomainFromList(List<Term> terms) {

		var DomainTree tree = null

		if(terms.size === 0) return null
		if (terms.size === 1) {
			tree = DomainCalculator.getDomainTerm(terms.get(0))
			return getDomain(tree.getCodeFromTree)
		}

		tree = DomainCalculator.getDomainTerm(terms.get(0))

		var Domain base = getDomain(tree.getCodeFromTree)

		var Domain dom

		for (var int i = 1; i < terms.size; i++) {
			tree = DomainCalculator.getDomainTerm(terms.get(i))
			dom = getDomain(tree.getCodeFromTree)
			base = getCommonDomain(base, dom)
		}

		return base

	}

	def static boolean isAgentDomain(String id) {

		var name_of_agent = "Agent"

		if(id.equals(name_of_agent)) return true

		var Domain dom = getDomain(id)

		if (dom instanceof ConcreteDomain) {
			return dom.typeDomain.name.equals(name_of_agent) || isAgentDomain(dom.typeDomain.name) || dom instanceof AgentDomain
		} else
			return false

	}
	
	def static boolean isReserveDomain(String id) {

		var name_of_agent = "Reserve"

		if(id.equals(name_of_agent)) return true

		var Domain dom = getDomain(id)

		if (dom instanceof ConcreteDomain) {
			return dom.typeDomain.name.equals(name_of_agent) || isAgentDomain(dom.typeDomain.name) || dom instanceof ReserveDomain
		} else
			return false

	}

	def static boolean isVariableDeclaredOnceByMe(String id_variable, EObject parent) {

		// the variable must be declared once (array.size == 1) and by the object 
		// passed as a parameters
		if(!variable_origin_map.containsKey(id_variable)) return true

		var array = variable_origin_map.get(id_variable)

		if(array.size != 1) return false

		// declared once
		return array.get(0).equals(parent)
	}

	def static Domain getDomainFromVariable(String id_variable) {
		return variable_domain_map.get(id_variable)
	}

	def static fillVariableMap(FunctionDefinition function_definition) {
		fillVariableMap(function_definition.variables, function_definition.domain, function_definition, true)
		fillFunctionDefinitionMap(function_definition)
	}

	def static fillFunctionDefinitionMap(FunctionDefinition definition) {
		function_declarations_map.put(definition.definedFunction, definition);
	}

	def static fillFunctionInitializationMap(FunctionInitialization initialization) {
		function_initialization_map.put(initialization.initializedFunction, initialization);
	}

	def static fillVariableMap(RuleDeclaration rule_declaration) {
		fillVariableMap(rule_declaration.variables, rule_declaration.domain, rule_declaration, true)
	}

	def static fillVariableMap(LetRule let_rule) {
		fillVariableMapWithTerm(let_rule.variable, let_rule.initExpression, let_rule, false, true)
	}

	def static fillVariableMap(ChooseRule choose_rule) {
		fillVariableMapWithTerm(choose_rule.variable, choose_rule.ranges, choose_rule, false, false)
	}

	def static fillVariableMap(ForallRule forall_rule) {
		fillVariableMapWithTerm(forall_rule.variable, forall_rule.ranges, forall_rule, false, false)
	}

	def static fillVariableMap(ComprehensionTerm compr_term) {
		fillVariableMapWithTerm(compr_term.variable, compr_term.ranges, compr_term, false, true)
	}

	def static fillVariableMap(ExtendRule extend_rule) {
		var String domain_code = extend_rule.extendedDomain.name
		var Domain dom = getDomain(domain_code)

		for (VariableTerm variable : extend_rule.boundVar) {
			variable_domain_map.put(variable.name, dom)
			addToVariableOrigin(extend_rule, variable.name)
			variable.kind = VariableKind.RULE_VAR;
		}
	}

	def static void addToVariableOrigin(EObject parent, String variable) {

		// aaaaa("adding " + variable + " and " + parent)
		var ArrayList<EObject> array

		if (!variable_origin_map.containsKey(variable)) {
			// aaaaa("new var")
			array = new ArrayList
			array.add(parent)
			variable_origin_map.put(variable, array)
		} else {
			array = variable_origin_map.get(parent)
			if (array === null) {
				array = new ArrayList
				array.add(parent)
			}
		}

	// aaaaa("array for " + variable + ":" + array)
	}

	def static resetVariableMap() {
		variable_domain_map.clear
		variable_origin_map.clear
	}

	def static fillVariableMap(LetTerm let_term) {
		fillVariableMapWithTerm(let_term.variable, let_term.assignmentTerm, let_term, false, false)
	}

	def static fillVariableMap(FiniteQuantificationTerm finite_term) {
		fillVariableMapWithTerm(finite_term.variable, finite_term.ranges, finite_term, false, false)
	}

	def static fillVariableMap(FunctionInitialization function_init) {
		fillVariableMap(function_init.variables, function_init.domain, function_init, true)
	}
	
	def static fillVariableMap(LocalFunction function_init) {
		signature_name_function_map.put(function_init.name, function_init);
	}

	def static fillVariableMapWithTerm(EList<VariableTerm> variable_list, EList<Term> term_list, EObject parent,
		boolean reset, boolean fromComprehensionTerm) {

		if (reset) {
			variable_domain_map.clear
			variable_origin_map.clear
		}

		var String variable
		var Domain dom
		var DomainTree tree
		var String domainAsString

		for (var int i = 0; i < variable_list.size; i++) {

			variable = variable_list.get(i).name

			tree = DomainCalculator.getDomainTerm(term_list.get(i))
			var toBeSplitted = true;

			if (parent instanceof ChooseRule || parent instanceof ForallRule || parent instanceof ForallTerm ||
				parent instanceof ComprehensionTerm) {
				if (parent instanceof ChooseRule) {
					if (parent.ranges.get(i) instanceof Domain) {
						toBeSplitted = false;
					}
				} else if (parent instanceof ForallRule) {
					if (parent.ranges.get(i) instanceof Domain) {
						toBeSplitted = false;
					}
				} else if (parent instanceof ForallTerm) {
					if (parent.ranges.get(i) instanceof Domain) {
						toBeSplitted = false;
					}
				} else if (parent instanceof ComprehensionTerm) {
					if (parent.ranges.get(i) instanceof Domain) {
						toBeSplitted = false;
					}
				}

				if (toBeSplitted) {

					var treeModel = tree.model.root

					if (treeModel.toString.equals("Powerset")) {
						treeModel = tree.model.getChild(treeModel, 0)

						if (treeModel instanceof DefaultMutableTreeNode &&
							(treeModel as DefaultMutableTreeNode).childCount > 0) {
							var ArrayList<DefaultMutableTreeNode> list = new ArrayList
							for (var int k = 0; k < (treeModel as DefaultMutableTreeNode).childCount; k++) {
								list.add((treeModel as DefaultMutableTreeNode).getChildAt(k) as DefaultMutableTreeNode)
							}

							var DefaultMutableTreeNode new_root = DomainTree.getComposedNodeFromNodes("Powerset", list)
							tree = new DomainTree(new_root)
						} else {
							tree = DomainCalculator.getDomainTerm(getDomain(treeModel.toString))
						// tree = new DomainTree(DomainTree.getNodeFromDomain(getDomain(treeModel.toString)))
						}
					} else if (treeModel.toString.equals("Bag")) {
						treeModel = tree.model.getChild(treeModel, 0)

						if (treeModel instanceof DefaultMutableTreeNode &&
							(treeModel as DefaultMutableTreeNode).childCount > 0) {
							var ArrayList<DefaultMutableTreeNode> list = new ArrayList
							for (var int k = 0; k < (treeModel as DefaultMutableTreeNode).childCount; k++) {
								list.add((treeModel as DefaultMutableTreeNode).getChildAt(k) as DefaultMutableTreeNode)
							}

							var DefaultMutableTreeNode new_root = DomainTree.getComposedNodeFromNodes("Bag", list)
							tree = new DomainTree(new_root)
						} else {
							tree = DomainCalculator.getDomainTerm(getDomain(treeModel.toString))
						// tree = new DomainTree(DomainTree.getNodeFromDomain(getDomain(treeModel.toString)))
						}
					} else if (treeModel !== null && !treeModel.toString.equals("Seq") && !treeModel.toString.equals("Bag")) {
						tree = DomainCalculator.getDomainTerm(getDomain(treeModel.toString))
					// tree = new DomainTree(DomainTree.getNodeFromDomain(getDomain(treeModel.toString)))
					}
				}
			}

			domainAsString = tree.getCodeFromTree

			// Any_Seq, Any_Powerset, Any_Bag
			switch (domainAsString) {
				case "Any_Seq": domainAsString = "Seq(D)"
				case "Any_Powerset": domainAsString = "Powerset(D)"
				case "Any_Bag": domainAsString = "Bag(D)"
			}

			dom = getDomain(domainAsString)

			// If the domain is null, try to put the AnyDomain instead of the actual types
			if (dom === null && tree.model !== null && tree.model.root !== null) {
				var HashSet<String> types = new HashSet<String>();
				for (var int j = 0; j < tree.model.getChildCount(tree.model.root); j++) {
					types.add(tree.model.getChild(tree.model.root, j).toString);
				}
				// Substitution 
				var int j = 0;
				for (String s : types) {
					domainAsString = domainAsString.replace(s, "D" + ( if(j == 0) "" else j))
					j++
				}
				dom = getDomain(domainAsString)
			}

			if (fromComprehensionTerm && dom instanceof SequenceDomain && !domainAsString.equals("Seq(D)")) {
				dom = getDomain(tree.getCodeFromTree.split("\\(").get(1).replace(")", ""))
			}

			if (fromComprehensionTerm && (dom === null && domainAsString.startsWith("Powerset("))) {
				dom = getDomain(tree.getCodeFromTree.split("\\(").get(1).replace(")", ""))
			}

			variable_domain_map.put(variable, dom)
			addToVariableOrigin(parent, variable)
		}

	}

	def static fillVariableMap(EList<String> variable_list, EList<Domain> domain_list, EObject parent, boolean reset) {

		if (reset) {
			variable_domain_map.clear
			variable_origin_map.clear
		}

		var String variable
		var String domain_code
		var Domain dom

		for (var int i = 0; i < variable_list.size; i++) {
			variable = variable_list.get(i)
			domain_code = calculateDomainCode(domain_list.get(i))
			dom = getDomain(domain_code)
			if (dom === null && domain_code !== null && !domain_code.equals("") && domain_list.get(i) !== null) {
				signature_domain_map.put(domain_code, domain_list.get(i))
				dom = getDomain(domain_code)
			}
			variable_domain_map.put(variable, dom)
			addToVariableOrigin(parent, variable)
		}

	}

	def static String getOperandFunctionName(String op) {
		switch op {
			case "+": return "plus"
			case "-": return "minus"
			case "*": return "mult"
			case "/": return "div"
			case ">": return "gt"
			case ">=": return "ge"
			case "<": return "lt"
			case "<=": return "le"
			case "^": return "pow"
			case "=": return "eq"
			case "!=": return "neq"
			default: return op
		}
	}

	def static String getRightDomainNameForBinary(Domain domain) {

		var String res

		if (domain === null)
			res = "null"
		else {
			// if the domain is concrete domain, we have to use the type domain
			if (domain instanceof ConcreteDomain)
				res = domain.typeDomain.name
			else if(domain instanceof PowersetDomain) res = domain.baseDomain.name else res = domain.name
		}

		return res

	}

	def static Integer calculateFunctionArity(Function function) {

		var domain = function.domain

		if(domain === null) return 0

		if (domain instanceof ProductDomain) {
			return domain.domains.size
		}

		return 1

	}

	def static boolean symbolExist(String symbol) {
		return signature_enum_domain_map.containsKey(symbol)
	}

	def static ArrayList<Function> getListOfPossibleFunction(String function_name) {

		var ArrayList<Function> imported = imported_list_function_map.get(function_name)
		var ArrayList<Function> declared = signature_list_function_map.get(function_name)

		if(imported === null) return declared
		if(declared === null) return imported

		// if neither of them are null, we have to concanate the string
		var ArrayList<Function> result = new ArrayList

		for (Function f : imported)
			result.add(f)
		for (Function f : declared)
			result.add(f)

		return result

	}

	def static Function searchForMostCompatible(ArrayList<Function> list, DomainTree function_domain_tree) {

		var DomainTree domain_tree
//		 println( "*** cerco " + function_domain_tree.codeFromTree )
		for (Function function : list) {
			domain_tree = DomainTree.getTreeFromDomain(function.domain)
//			println( "candidata " + domain_tree.codeFromTree )
			if (isCompatible(function_domain_tree, domain_tree)) {
				
				var dom1 = getDomain(DomainTree.getCodeFromTree(domain_tree.model.root as TreeNode))
				var dom2 = getDomain(DomainTree.getCodeFromTree(function_domain_tree.model.root as TreeNode))
				
				if ((dom1 instanceof AbstractTD || dom1 instanceof ConcreteDomain) && (dom2 instanceof AbstractTD || dom2 instanceof ConcreteDomain)) {
					if (isCompatibleFunction(dom1,dom2))
						// println( "trovata -> " + domain_tree.codeFromTree )
						return function
				} else {
					// println( "trovata -> " + domain_tree.codeFromTree )
					return function
				}
			}
		}
	}
	
	def static RuleDeclaration searchForMostCompatibleRule(ArrayList<RuleDeclaration> list, List<Domain> domainList) {

		var DomainTree domain_tree
		for (RuleDeclaration rule : list) {
			
			if (rule.domain.length == domainList.length) {
				
				var comp = true;
				
				for (var int i = 0; i<rule.domain.length; i++) {
					if (!isCompatible(DomainTree.getNodeFromDomain(domainList.get(i)), DomainTree.getNodeFromDomain(rule.domain.get(i)), new HashMap))
						comp = false;
				}
				
				if (comp) return rule;
			}
		
		}
		
		return null;
	}

	def static String getStringFromTerm(Term term) {
		if (term instanceof BasicTerm) {
			var String res
			if (term instanceof FunctionTerm)
				res = term.functionName
			else if (term instanceof ConstantTerm)
				if(term.symbol.endsWith("n")) res = term.symbol.substring(0, term.symbol.length() - 1) else res = term.
					symbol
			else if(term instanceof VariableTerm) res = term.name
			return res
		} else
			return "null"
	}

	def static boolean isPowerSetDomain(Term range) {

		// The range is a powerset only if:
		// it's a set term
		// it's a DomainTerm
		// it's a function with Powerset([something]) as domain
		if (range instanceof FunctionTerm) {

			// domain term
			var Domain domain = getDomain(range.functionName)
			if(range.arguments === null && domain !== null) return true

			// it's a function term, we check if the domain is powerset
			var Function function = DomainCalculator.getFunctionFromFunctionTerm(range)
			if(function === null) return false

			var Domain dom = getDomain(calculateDomainCode(function.codomain))

			if(dom === null) return false

			// Check if it is a concreteDomain
			if (dom instanceof ConcreteDomain)
				dom = dom.typeDomain

			return ( dom instanceof AnyDomain ) || ( dom instanceof PowersetDomain )

		} else if (range instanceof VariableTerm) {
			var dom = getDomainFromVariable(range.name)
			if(dom === null) return false
			return ( dom instanceof AnyDomain ) || ( dom instanceof PowersetDomain )
		} else if(range instanceof SetTerm || range instanceof PowersetDomain ||
			range instanceof Domain) return true else return false

	}

	def static boolean isBagDomain(Term range) {

		// The range is a bag only if:
		// it's a bag term
		// it's a DomainTerm
		// it's a function with Bag([something]) as codomain
		if (range instanceof FunctionTerm) {

			// domain term
			var Domain domain = getDomain(range.functionName)
			if(range.arguments === null && domain !== null) return true

			// it's a function term, we check if the domain is powerset
			var Function function = DomainCalculator.getFunctionFromFunctionTerm(range)
			if(function === null) return false

			var Domain dom = getDomain(calculateDomainCode(function.codomain))

			if(dom === null) return false

			return ( dom instanceof AnyDomain ) || ( dom instanceof BagDomain )

		} else if (range instanceof VariableTerm) {
			var dom = getDomainFromVariable(range.name)
			if(dom === null) return false
			return ( dom instanceof AnyDomain ) || ( dom instanceof BagDomain )
		} else if(range instanceof BagTerm) return true else return false

	}

	def static boolean isSequenceDomain(Term range) {

		// The range is a sequence only if:
		// it's a sequence term
		// it's a DomainTerm
		// it's a function with Seq([something]) as codomain
		if (range instanceof FunctionTerm) {

			// domain term
			var Domain domain = getDomain(range.functionName)
			if(range.arguments === null && domain !== null) return true

			// it's a function term, we check if the domain is powerset
			var Function function = DomainCalculator.getFunctionFromFunctionTerm(range)
			if(function === null) return false

			var Domain dom = getDomain(calculateDomainCode(function.codomain))

			if(dom === null) return false

			return ( dom instanceof AnyDomain ) || ( dom instanceof SequenceDomain )

		} else if (range instanceof VariableTerm) {
			var dom = getDomainFromVariable(range.name)
			if(dom === null) return false
			return ( dom instanceof AnyDomain ) || ( dom instanceof SequenceDomain )
		} else if(range instanceof SequenceDomain) return true else return false

	}

	/**
	 * Get the String representation of a rule
	 * @return A string representation of the rule: {rule_name} '(' [domain ',' domain] )
	 */
	def static String getRuleaAsString(RuleDeclaration rule_declaration) {

		var String domains_string = ""

		for (Domain d : rule_declaration.domain) {
			domains_string += calculateDomainCode(d) + ","
		}
		if(domains_string.length > 0) domains_string = domains_string.substring(0, domains_string.length)
		return rule_declaration.name + "(" + domains_string + ")";
	}

	def static isDomainName(String s) {
		var boolean res = false;

		if (signature_domain_map.containsKey(s) || imported_all_domain_map.containsKey(s) ||
			imported_domain_map.containsKey(s))
			res = true

		return res
	}

	def static ArrayList<DomainTree> getDomainTreeListFromListOfFunctions(ArrayList<Function> list) {

		var ArrayList<DomainTree> treeList = new ArrayList
		var DefaultMutableTreeNode res
		var String domain_code
		var Domain dom

		for (function : list) {
			domain_code = Utility.calculateDomainCode(function.codomain)
			dom = Utility.getDomain(domain_code)
			if (Utility.isFromAnyDomain(dom))
				res = DomainTree.getNodeFromDomain(dom)
			else
				res = DomainTree.getNodeFromDomain(dom)
			treeList.add(new DomainTree(res))
		}

		return treeList
	}

	def static Boolean existOneCompatible(Term location, Term updatingTerm) {
		var DomainTree location_domain
		var ArrayList<DomainTree> locationList = new ArrayList
		var DomainTree update_domain
		var ArrayList<DomainTree> updateList = new ArrayList
		var Boolean compatible = false;

		if (!(location instanceof FunctionTerm || location instanceof LocationTerm)) {
			location_domain = DomainCalculator.getDomainTerm(location)
			locationList.add(location_domain)
		} else {
			// List of the possible functions
			var ArrayList<Function> tempList = new ArrayList<Function>

			if (location instanceof FunctionTerm)
				tempList = DomainCalculator.getAllFunctionsFromFunctionTerm(location as FunctionTerm)
			else
				tempList = DomainCalculator.getAllFunctionsFromFunctionTerm(location as LocationTerm)
				
			if (tempList === null)
				return false;

			locationList = getDomainTreeListFromListOfFunctions(tempList)
		}

		if (!(updatingTerm instanceof FunctionTerm || updatingTerm instanceof LocationTerm)) {
			update_domain = DomainCalculator.getDomainTerm(updatingTerm)
			updateList.add(update_domain)
		} else {
			// List of the possible functions
			var ArrayList<Function> tempList = new ArrayList<Function>

			if (updatingTerm instanceof FunctionTerm)
				tempList = DomainCalculator.getAllFunctionsFromFunctionTerm(updatingTerm as FunctionTerm)
			else
				tempList = DomainCalculator.getAllFunctionsFromFunctionTerm(updatingTerm as LocationTerm)
		}

		for (dom1 : locationList) {
			for (dom2 : updateList) {
				if (Utility.isCompatible(dom1, dom2))
					compatible = true;
			}
		}

		return compatible

	}

	def static FunctionTerm getFunctionTermFromBO(BinaryOperation bo) {
		if (binary_operation_conversion_map.containsKey(bo))
			return binary_operation_conversion_map.get(bo)

		var FunctionTerm ft = AsmetalFactory.eINSTANCE.createFunctionTerm()
		var String fn = org.asmeta.xt.validation.utility.Utility.getOperandFunctionName(bo.getOp())
		var Function function = org.asmeta.xt.validation.utility.Utility.getFunctionByName(fn)
		ft.setFunction(function)
		var TupleTerm tt = AsmetalFactory.eINSTANCE.createTupleTerm()
		if (bo.getLeft() !== null)
			tt.getTerms().add(bo.getLeft())
		if (bo.getRight() !== null)
			tt.getTerms().add(bo.getRight())
		ft.setArguments(tt)
		binary_operation_conversion_map.put(bo, ft)

		return ft
	}

	def static FunctionTerm getFunctionTermFromExpression(Expression exp) {
		if (expression_conversion_map.containsKey(exp))
			return expression_conversion_map.get(exp)

		var FunctionTerm ft = AsmetalFactory.eINSTANCE.createFunctionTerm()
		var String fn = org.asmeta.xt.validation.utility.Utility.getOperandFunctionName(exp.op)
		var Function function = org.asmeta.xt.validation.utility.Utility.getFunctionByName(fn)
		ft.setFunction(function)
		var TupleTerm tt = AsmetalFactory.eINSTANCE.createTupleTerm()
		tt.getTerms().add(exp.operand)
		ft.setArguments(tt)
		expression_conversion_map.put(exp, ft)

		return ft
	}

	def static changeFunctionsInDomains(FiniteQuantificationTerm term) {
		var InternalEList<Term> rangesTermList = new EObjectEList<Term>(Term, term as InternalEObject,
			AsmetalPackage.FINITE_QUANTIFICATION_TERM__RANGES)

		for (Term t : term.ranges) {
			if (!isFunctionName(getStringFromTerm(t)) && isDomainName(getStringFromTerm(t))) {
				var String name = getStringFromTerm(t)
				var Domain d = getDomain(name)
				var DomainTerm nt;
				/*if (d instanceof AbstractTD)
				 * 	nt = AsmetalFactory.eINSTANCE.createAbstractTD()
				 * else if (d instanceof ConcreteDomain)
				 * 	nt = AsmetalFactory.eINSTANCE.createConcreteDomain()
				 * else if (d instanceof BasicTD)
				 * 	nt = AsmetalFactory.eINSTANCE.createBasicTD()
				 else*/
				nt = AsmetalFactory.eINSTANCE.createDomain()

				nt.domain = d;
				rangesTermList.add(nt)
			} else {
				rangesTermList.add(t)
			}
		}

		term.ranges = rangesTermList
	}

	def static changeFunctionsInDomains(ChooseRule term) {
		var InternalEList<Term> rangesTermList = new EObjectEList<Term>(Term, term as InternalEObject,
			AsmetalPackage.FINITE_QUANTIFICATION_TERM__RANGES)

		for (Term t : term.ranges) {
			if (!isFunctionName(getStringFromTerm(t)) && isDomainName(getStringFromTerm(t))) {
				var String name = getStringFromTerm(t)
				var Domain d = getDomain(name)
				var DomainTerm nt;
				/*if (d instanceof AbstractTD)
				 * 	nt = AsmetalFactory.eINSTANCE.createAbstractTD()
				 * else if (d instanceof ConcreteDomain)
				 * 	nt = AsmetalFactory.eINSTANCE.createConcreteDomain()
				 * else if (d instanceof BasicTD)
				 * 	nt = AsmetalFactory.eINSTANCE.createBasicTD()
				 else*/
				nt = AsmetalFactory.eINSTANCE.createDomain()

				nt.domain = d;
				rangesTermList.add(nt)
			} else {
				rangesTermList.add(t)
			}
		}

		term.ranges = rangesTermList
	}

}
