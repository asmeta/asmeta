package org.asmeta.asm2code

import org.eclipse.emf.common.util.EList
import asmeta.transitionrules.basictransitionrules.BlockRule
import asmeta.transitionrules.basictransitionrules.Rule
import asmeta.transitionrules.turbotransitionrules.SeqRule
import asmeta.transitionrules.basictransitionrules.SkipRule
import asmeta.transitionrules.basictransitionrules.MacroCallRule
import asmeta.terms.basicterms.Term
import asmeta.transitionrules.basictransitionrules.LetRule
import asmeta.transitionrules.basictransitionrules.ConditionalRule
import asmeta.transitionrules.derivedtransitionrules.CaseRule
import asmeta.structure.Asm
import asmeta.transitionrules.basictransitionrules.ForallRule
import asmeta.transitionrules.basictransitionrules.UpdateRule
import asmeta.transitionrules.basictransitionrules.ChooseRule
import asmeta.transitionrules.basictransitionrules.TermAsRule
import asmeta.transitionrules.basictransitionrules.ExtendRule
import asmeta.definitions.RuleDeclaration
import asmeta.transitionrules.derivedtransitionrules.IterativeWhileRule
import asmeta.transitionrules.turbotransitionrules.IterateRule
import asmeta.definitions.domains.StringDomain
import asmeta.transitionrules.basictransitionrules.MacroDeclaration
import asmeta.definitions.domains.PowersetDomain
import org.asmeta.simulator.RuleVisitor
import asmeta.definitions.domains.IntegerDomain
import asmeta.definitions.domains.Domain
import asmeta.definitions.domains.CharDomain
import asmeta.definitions.domains.BooleanDomain
import asmeta.definitions.domains.RealDomain
import asmeta.definitions.domains.NaturalDomain
import asmeta.definitions.domains.AbstractTd
import org.asmeta.asm2code.main.TranslatorOptions
import java.util.ArrayList

class RuleToCpp extends RuleVisitor<String> {

	// private static final Logger LOGGER = Logger.getLogger(RuleToCpp.name);
	Asm res;
	boolean seqBlock;
	TranslatorOptions options

	/**  seqBlock iff it is called in a seq rule*/
	new(Asm resource, boolean seqBlock, TranslatorOptions options) {
		this.res = resource
		this.seqBlock = seqBlock
		this.options = options
	}

	override String visit(BlockRule object) {
		return '''
		{ //par
			«new RuleToCpp(res,false,options).printRules(object.getRules())»
		}//endpar'''
	}

	def private String printRules(EList<Rule> rules) {
		var StringBuffer sb = new StringBuffer
		for (var int i = 0; i < rules.size(); i++) {
			sb.append(new RuleToCpp(res, seqBlock, options).visit(rules.get(i)))
		}
		return sb.toString()
	}

	override String visit(MacroCallRule object) {
		var String methodName = object.calledMacro.name
		// if it is inside a seq, use the seq variant
		if(seqBlock) methodName += "_seq"
		if (object.parameters.size() == 0)
			return '''«methodName»();
'''
		else
			return '''«methodName»(«printListTerm(object.parameters)»);
'''
	}

	def private String printListTerm(EList<Term> term) {
		var sb = new StringBuffer
		for (var int i = 0; i < term.size(); i++) {
			sb.append('''«new TermToCpp(res).visit(term.get(i))», ''')
		}
		return sb.substring(0, sb.length - 2)
	}

	/* 	see class CppGenerator ruleImplementation
	 * 
	 *  def String visit(RuleDeclaration object) {
	 * 		var bb = #{true, false}
	 * 		for (boolean seq : bb) {
	 * 			var methodName = object.name
	 * 			if(seq) methodName += "_seq"
	 * 			if (object.variable.isEmpty)
	 * 				return '''
	 * 					void «methodName»(){ 
	 * 						«new RuleToCpp(res,seq).visit(object.ruleBody)»
	 * 					}
	 * 				'''
	 * 			else {
	 * 				return '''
	 * 					
	 * 					void «methodName» «new Util().adaptRuleParam(object.variable,res)»{ 
	 * 						«new RuleToCpp(res,seq).visit(object.ruleBody)»
	 * 					}
	 * 				'''
	 * 			}
	 * 		}
	 * 	}

	 * 	def String visit(MacroDeclaration object) {
	 * 		var bb = #{true, false}
	 * 		for (boolean seq : bb) {
	 * 			var methodName = object.name
	 * 			if(seq) methodName += "_seq"
	 * 			if (object.variable.isEmpty)
	 * 				return '''
	 * 					void «methodName»(){ 
	 * 						«new RuleToCpp(res,seq).visit(object.ruleBody)»
	 * 					}
	 * 				'''
	 * 			else {
	 * 				return '''
	 * 					
	 * 					void «methodName» «new Util().adaptRuleParam(object.variable,res)»{ 
	 * 						«new RuleToCpp(res,seq).visit(object.ruleBody)»
	 * 					}
	 * 				'''
	 * 			}

	 * 		}
	 }*/
	// TODO TURBO DECLARATION
	/*override String caseRuleDefinition(RuleDefinition object) {
	 * 	if (object.parameters == null)
	 * 		return '''
	 * 			void «object.idRule»(){ 
	 * 				«new RuleDefinitionIno(res,false).visit(object.rule)»
	 * 			}
	 * 		'''
	 * 	else {
	 * 		return '''
	 * 			
	 * 			void «object.idRule» «adaptParam(object.parameters, object.idRule)»{ 
	 * 				«new RuleDefinitionIno(res,false).visit(object.rule)»
	 * 			}
	 * 		'''
	 * 	}
	 }*/
	/* 	def private String adaptParam(EList<VariableTerm> variables) {
	 * 		var StringBuffer paramDef = new StringBuffer
	 * 		paramDef.append("(");
	 * //		var numparamNoDomain = 0
	 * 		// var EList<SingleParameterDefinition> singleParam = parameters.getSingleParam()
	 * 		for (var i = 0; i < variables.size; i++) {
	 * 			paramDef.append('''«new ToString(res).visit(variables.get(i).domain)» «variables.get(i).name», ''')
	 * 		/*paramDef.append(
	 '''«new ToString(res).visit(singleParam.get(i).domainIn)» «singleParam.get(i).idIn», ''')*/
	/*if (singleParam.get(i).id !== null)
	 * 	throw new RuntimeException("IdDomain null in rule definition not allowed")
	 * if (singleParam.get(i).domain !== null)
	 *  throw new RuntimeException("Domain null in rule definition not allowed")
	 * }
	 * return paramDef.substring(0, paramDef.length - 2) + ")"
	 }*/
	override String visit(SeqRule object) {
		return '''
			{//seq
				«new RuleToCpp(res,true,options).printRules(object.rules)»
			}//endseq
		'''
	}

	/* 	override String visit(SeqNext object) {
	 * 		return '''
	 * 			{//seqnext
	 * 				«new RuleDefinitionIno(res,true).printRules(object.rules)»
	 * 			}//endseqnext
	 * 		'''
	 }*/
	override String visit(UpdateRule object) {
		// return '''«new TermToCpp(res,true,seqBlock).visit(object.location)» = «new TermToCpp(res,false,seqBlock).visit(object.updatingTerm)»;
		// '''
		var StringBuffer result = new StringBuffer
		result.
			append('''«new TermToCpp(res,true).visit(object.location)» = «new TermToCpp(res,false).visit(object.updatingTerm)»;
			''')
		if (seqBlock) {
			// add the fire update
			result.
				append('''«new TermToCpp(res,false).visit(object.location)» = «new TermToCpp(res,true).visit(object.location)»;''')
		}
		return result.toString
	}

	override String visit(SkipRule object) {
		return "; \n"
	}

	override String visit(CaseRule object) {
		var StringBuffer sb = new StringBuffer
		for (var i = 0; i < object.getCaseBranches().size; i++) {
			if (i == 0)
				sb.append(
					'''
				if(«compareTerms(object.getTerm,object.getCaseTerm.get(i))»){
					«new RuleToCpp(res,seqBlock,options).visit(object.getCaseBranches.get(i))»
				}''')
			else
				sb.append(
					'''
				else if(«compareTerms(object.getTerm,object.getCaseTerm.get(i))»){
					«new RuleToCpp(res,seqBlock,options).visit(object.getCaseBranches().get(i))»
				}''')
		}
		if (object.getOtherwiseBranch() !== null)
			sb.append(
			'''
				else{ 
				 	«new RuleToCpp(res,seqBlock,options).visit(object.getOtherwiseBranch())»
				}
			''')
		return sb.toString
	}

	def String compareTerms(Term leftTerm, Term rightTerm) {
		if (leftTerm.domain.toString.compareTo(rightTerm.domain.toString) != 0)
			return '''Impossible to compare Terms'''
		else if (leftTerm instanceof StringDomain)
			return '''«new TermToCpp(res).visit(leftTerm)».compare(«new TermToCpp(res).visit(rightTerm)»)==0'''
		else /* This is valid also for complex term*/
			return '''«new TermToCpp(res).visit(leftTerm)»==«new TermToCpp(res).visit(rightTerm)»'''
	}

	override String visit(ChooseRule object) {
		var counter = 0
		var StringBuffer sb = new StringBuffer
		for (var i = 0; i < object.getRanges.size; i++)
			if (object.getRanges.get(i).domain instanceof PowersetDomain)
				sb.append('''
					set<const «new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)»*> point«i»;
				''')
			// println("Object ranges: " + (object.getRanges.get(i).domain as PowersetDomain).baseDomain)
			else
				sb.append('''
					NOT IMPLEMENTED IN C++ (RuleToCpp line 264)
				''')
		for (var i = 0; i < object.getRanges.size; i++)
			if (object.getRanges.get(i).domain instanceof PowersetDomain) {
				// devo dichiarare il set di valori nel caso si metta un dominio diverso da un nome di un dominio definito, per esempio: {0..5} {1,5,9} 
				if (new Util().isNotNumerable((object.getRanges.get(i).domain as PowersetDomain).baseDomain)) {
					sb.append('''
						«new DomainToH(res).visit(object.getRanges.get(i).domain)» «new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)+i»_elems = «new TermToCpp(res).visit(object.getRanges.get(i))»;
					''')
					counter = counter + 1
					sb.append('''
						for(auto const& «new TermToCpp(res).visit(object.getVariable.get(i))» : «new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)+i»_elems){
					''')
				} else if ((object.getRanges.get(i).domain as PowersetDomain).baseDomain instanceof AbstractTd)
					sb.append('''
						for(const auto& «new TermToCpp(res).visit(object.getVariable.get(i))» : «new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)»::elems)
					''')
				else
					sb.append('''
						for(auto const& «new TermToCpp(res).visit(object.getVariable.get(i))» : «new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)»_elems)
					''')
			} else
				sb.append('''
					//NOT IMPLEMENTED IN C++ (RuleToCpp line 283)	
						''')
		if (object.getGuard !== null)
			sb.append('''
				if(«new TermToCpp(res).visit(object.getGuard)»){
			''')
		else
			sb.append('''{
			''')
	//	var ArrayList<String> pointerTerms = new ArrayList()
		for (var i = 0; i < object.getVariable.size; i++)
			if ((object.getRanges.get(i).domain as PowersetDomain).baseDomain instanceof AbstractTd){
				var termName= new TermToCpp(res).visit(object.getVariable.get(i))
				sb.append('''
					point«i».push_back(&(*«termName»));
				''')
				/*println("TERM: " + termName)
				pointerTerms.add(termName)*/
				}
			else
				sb.append('''
					point«i».push_back(&«new TermToCpp(res).visit(object.getVariable.get(i))»);
				''')
		for (var i = 0; i < counter; i++)
			sb.append('''
				}
			''')
		sb.append('''
			}
		''')
		if (options.shuffleRandom)
			sb.append('''
				int rndm = rand() % point0.size();
			''')
		else
			sb.append('''
				int rndm = 0;
			''')
		sb.append('''
			{
		''')
		for (var i = 0; i < object.getVariable.size; i++)
			sb.append('''
				auto «new TermToCpp(res).visit(object.getVariable.get(i))» = *point«i»[rndm];
			''')
		if (object.getIfnone !== null)
		{
			var doRule= visit(object.getDoRule)
			/*for (var j=0; j<pointerTerms.size; j++){
				doRule=doRule.replaceAll(pointerTerms.get(j), ("&"+pointerTerms.get(j)))
				}*/
			sb.append('''
			  if(point0.size()>0){
				«doRule»
				 }else{
				 	«visit(object.getIfnone)»
				 }
			}''')
			}
		else {
			var doRule= visit(object.getDoRule)		
			/*for (var j=0; j<pointerTerms.size; j++){
				doRule=(doRule.replaceAll(pointerTerms.get(j), ("&"+pointerTerms.get(j))))
			}*/
			sb.append('''
			  if(point0.size()>0){
				«doRule»
				 }
			}''')
		}
		return sb.toString
	}

	override String visit(ForallRule object) {
		var StringBuffer sb = new StringBuffer
		for (var i = 0; i < object.getRanges.size; i++)
			if ((object.getRanges.get(i).domain as PowersetDomain).baseDomain instanceof AbstractTd)
				sb.append(
			'''
					for(auto «new TermToCpp(res).visit(object.getVariable.get(i))» : «new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)»::elems)
				''')
			else
				sb.append(
			'''
					for(auto «new TermToCpp(res).visit(object.getVariable.get(i))» : «new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)»_elems)
				''')

		if (object.getGuard !== null)
			sb.append('''
				«""»
					if(«new TermToCpp(res).visit(object.getGuard)»){	
						«visit(object.getDoRule)»
					}
			''')
		else
			sb.append('''
				{
					«visit(object.getDoRule)»
				}
			''')
		return sb.toString
	}

	override String visit(LetRule object) {
		var let = new StringBuffer
		let.append("{\n");

		for (var int i = 0; i < object.getVariable.size; i++) {
			let.
				append('''auto «new TermToCpp(res).visit(object.getVariable.get(i))» = «new TermToCpp(res).visit(object.getInitExpression.get(i))»;
				''')
		}
		let.append('''«new RuleToCpp(res,seqBlock,options).visit(object.getInRule)»
}''')
		return let.toString
	}

	def String visit(IterativeWhileRule object) {
		return '''
		while («new TermToCpp(res,false).visit(object.guard)»){
			«new RuleToCpp(res,true,options).visit(object.rule)»
		}'''
	}

	/** TODO: iterateRule */
	def String visit(IterateRule object) {
		return '''
			//#iterate rule not yet implemented
			
		'''
	}

	override String visit(ConditionalRule object) {
		if (object.getElseRule() === null)
			return '''
				if («new TermToCpp(res).visit(object.guard)»){ 
					«new RuleToCpp(res,seqBlock,options).visit(object.thenRule)»
				}
			'''
		else if (object.elseRule instanceof ConditionalRule)
			return '''
				if («new TermToCpp(res).visit(object.getGuard)»){ 
						«new RuleToCpp(res,seqBlock,options).visit(object.thenRule)»
				} else «new RuleToCpp(res,seqBlock,options).visit(object.elseRule)»
			'''
		else
			return '''
					if («new TermToCpp(res).visit(object.getGuard)»){ 
					«new RuleToCpp(res,seqBlock,options).visit(object.thenRule)»
					}else{
					«new RuleToCpp(res,seqBlock,options).visit(object.elseRule)»
				}
			'''
	}

	/*override String casePrintRule(PrintRule object) {
	 return */
	/*'''
	 * 	#ifdef ARDUINOCOMPILER
	 * 	Serial.print(" + new ToString(res).visit(object.term) + ");
	 * 	  #endif
	 * '''
	 }*/
	override visit(TermAsRule rule) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

	override visit(ExtendRule rule) {
		var string = new StringBuffer
		for (var i = 0; i < rule.boundVar.size; i++)
			string.append(
				new DomainToH(res, true).visit(rule.extendedDomain) + " " +
					new TermToCpp(res).visit(rule.boundVar.get(i)) + " = new " +
					new DomainToH(res).visit(rule.extendedDomain) + "();\n");
		return string.toString + new RuleToCpp(res, seqBlock, options).visit(rule.doRule)
	// throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

}
