package org.asmeta.asm2java

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
import asmeta.transitionrules.derivedtransitionrules.IterativeWhileRule
import asmeta.transitionrules.turbotransitionrules.IterateRule
import asmeta.definitions.domains.StringDomain
import asmeta.definitions.domains.PowersetDomain
import org.asmeta.simulator.RuleVisitor
import asmeta.definitions.domains.AbstractTd
import org.asmeta.asm2java.main.TranslatorOptions
import asmeta.definitions.domains.ConcreteDomain
import asmeta.terms.basicterms.VariableTerm
import asmeta.definitions.domains.EnumTd
import asmeta.definitions.ControlledFunction

class RuleToJava extends RuleVisitor<String> {

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
	

    //Metodo per studiare e tradurre i blocchi di regole par/endpar
	override String visit(BlockRule object) {
		return '''
		{ //par
			«new RuleToJava(res,false,options).printRules(object.getRules(), false)»
		}//endpar'''
	}

    //Metodo di supporto per tradurre le singole righe di un blocco regole
	def private String printRules(EList<Rule> rules, boolean addFire) {
		var StringBuffer sb = new StringBuffer
		for (var int i = 0; i < rules.size(); i++) {
			sb.append(new RuleToJava(res, seqBlock, options).visit(rules.get(i)))
			if (addFire){
				sb.append("\nfireUpdateSet();\n")
			}
			
		}
		return sb.toString()
	}

    //Metodo per le macro rule, identifica l'eventuale presenza di variabili in ingresso
	override String visit(MacroCallRule object) {
		var String methodName = object.calledMacro.name
		// if it is inside a seq, use the seq variant
		if(seqBlock) methodName += ""
		if (object.parameters.size() == 0)
			return '''«methodName»();
'''
		else
			return '''«methodName»(«printListTerm(object.parameters)»);
'''
	}

    //Metodo che identifica le variabili in ingresso alla macro rule
	def private String printListTerm(EList<Term> term) {
		var sb = new StringBuffer
		for (var int i = 0; i < term.size(); i++) {
			if(i==0 && term.get(0).domain instanceof ConcreteDomain)
			sb.append('''«new TermToJava(res).visit(term.get(i))».value, ''')
			else
			sb.append('''«new TermToJava(res).visit(term.get(i))», ''')
		}
		return sb.substring(0, sb.length - 2)
	}


	 
	//Meto per tradurre delle sequenze di regole nel blocco par/endpar
	override String visit(SeqRule object) {
		
		return '''
			{//seq
				«new RuleToJava(res,true,options).printRules(object.rules,true)»
			}//endseq
		'''
	}

	 
	 
	//Metodo per identificare una regola di aggiornamento in cui si rileva il comando :=
	override String visit(UpdateRule object) {		
		
		var StringBuffer result = new StringBuffer
		
		
		if( object.location instanceof VariableTerm) 
		result.
			append('''«new TermToJava(res,true).visit(object.location)» = («new TermToJava(res,false).visit(object.updatingTerm)»);
			   
			''')

		else if(object.updatingTerm.domain instanceof ConcreteDomain)
		result.
			append('''«new TermToJava(res,true).visit(object.location)»«new TermToJava(res,false).visit(object.updatingTerm)».value);
			   «new TermToJavaSupportoProdMap(res,false).visit(object.location)»
			''')
		else
		result.
			append('''«new TermToJava(res,true).visit(object.location)»«new TermToJava(res,false).visit(object.updatingTerm)»);
			   «new TermToJavaSupportoProdMap(res,false).visit(object.location)»
			''')
		if (seqBlock) {
			// add the fire update
			var functionName = new TermToJavaConditionalAbs(res,true).visit(object.location)
			var isZeroC = false
			for (cf : res.headerSection.signature.function)
				if (cf.name.equals(functionName))
					if (cf instanceof ControlledFunction && cf.domain !== null)
						isZeroC = false
					else if(cf instanceof ControlledFunction && cf.domain === null)
						isZeroC = true		
			
			if (isZeroC)
				result.
					append('''«new TermToJavaConditionalAbs(res,true).visit(object.location)».oldValue = «new TermToJavaConditionalAbs(res,true).visit(object.location)».newValue;
					''')
			else
				result.
					append('''«new TermToJavaConditionalAbs(res,true).visit(object.location)».oldValues = «new TermToJavaConditionalAbs(res,true).visit(object.location)».newValues;
					''')
				
			
				
		}
		return result.toString
	}
	
	
    //Metodo per identificare la regola di "salto"
	override String visit(SkipRule object) {
		return "; \n"
	}

    //Metodo per tradurre una sequenza da 1 a n if
	override String visit(CaseRule object) {
		var StringBuffer sb = new StringBuffer
		for (var i = 0; i < object.getCaseBranches().size; i++) {
			if (i == 0)
				sb.append(
					'''
				if(«compareTerms(object.getTerm,object.getCaseTerm.get(i))»){
					«new RuleToJava(res,seqBlock,options).visit(object.getCaseBranches.get(i))»
				}''')
			else
				sb.append(
					'''
				else if(«compareTerms(object.getTerm,object.getCaseTerm.get(i))»){
					«new RuleToJava(res,seqBlock,options).visit(object.getCaseBranches().get(i))»
				}''')
		}
		if (object.getOtherwiseBranch() !== null)
			sb.append(
			'''
				else{ 
				 	«new RuleToJava(res,seqBlock,options).visit(object.getOtherwiseBranch())»
				}
			''')
		return sb.toString
	}

    //Metodo di supporto per tradurre gli elementi di un confronto nella classe precedente
	def String compareTerms(Term leftTerm, Term rightTerm) {
		if (leftTerm.domain.toString.compareTo(rightTerm.domain.toString) != 0)
			return '''Impossible to compare Terms'''
		else if (leftTerm instanceof StringDomain)
			return '''«new TermToJava(res).visit(leftTerm)».compareTo(«new TermToJava(res).visit(rightTerm)»)==0'''
		else /* This is valid also for complex term*/
			return '''«new TermToJava(res).visit(leftTerm)»==«new TermToJava(res).visit(rightTerm)»'''
	}

	override String visit(ChooseRule object) {
		var counter = 0
		var StringBuffer sb = new StringBuffer
		for (var i = 0; i < object.getRanges.size; i++)
			if (object.getRanges.get(i).domain instanceof PowersetDomain)
				sb.append('''
					List<«new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)»> point«i» = new ArrayList<«new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)»>();
				''')
			// println("Object ranges: " + (object.getRanges.get(i).domain as PowersetDomain).baseDomain)
			else
				sb.append('''
					NOT IMPLEMENTED IN JAVA
				''')
		for (var i = 0; i < object.getRanges.size; i++)
			if (object.getRanges.get(i).domain instanceof PowersetDomain) {
				// devo dichiarare il set di valori nel caso si metta un dominio diverso da un nome di un dominio definito, per esempio: {0..5} {1,5,9} 
				if (new Util().isNotNumerable((object.getRanges.get(i).domain as PowersetDomain).baseDomain)) {
					sb.append('''
						point«i» = Collections.unmodifiableList(Arrays.asList «new TermToJava(res).visit(object.getRanges.get(i))»);
					''')
					counter = counter + 1
					sb.append('''
						for(«new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)» «new TermToJava(res).visit(object.getVariable.get(i))» : point«i»){
					''')
				} else if ((object.getRanges.get(i).domain as PowersetDomain).baseDomain instanceof AbstractTd)
					sb.append('''
						for(«new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)» «new TermToJava(res).visit(object.getVariable.get(i))» : «new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)».elems)
					''')
				else
					sb.append('''
						for(«new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)» «new TermToJava(res).visit(object.getVariable.get(i))» : «new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)»_lista)
					''')
			} else
				sb.append('''
					//NOT IMPLEMENTED IN JAVA	
						''')
		if (object.getGuard !== null)
			sb.append('''
				if(«new TermToJava(res).visit(object.getGuard)»){
			''')
		else
			sb.append('''{
			''')
	//	var ArrayList<String> pointerTerms = new ArrayList()
		for (var i = 0; i < object.getVariable.size; i++)
			if ((object.getRanges.get(i).domain as PowersetDomain).baseDomain instanceof AbstractTd){
				var termName= new TermToJava(res).visit(object.getVariable.get(i))
				sb.append('''
					point«i».add(«termName»);
				''')
				/*println("TERM: " + termName)
				pointerTerms.add(termName)*/
				}
			else
				sb.append('''
					point«i».add(«new TermToJava(res).visit(object.getVariable.get(i))»);
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
				int rndm = ThreadLocalRandom.current().nextInt(0, point0.size());
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
				«new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)» «new TermToJava(res).visit(object.getVariable.get(i))» = point«i».get(rndm);
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
		if ((object.getRanges.get(i).domain as PowersetDomain).baseDomain instanceof EnumTd)
				sb.append(
			'''
					for(«new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)» «new TermToJava(res).visit(object.getVariable.get(i))» : «new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)»_lista)
				''')
			else
				sb.append(
			'''
					for(«new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)» «new TermToJava(res).visit(object.getVariable.get(i))» : «new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)».elems)
				''')
			


		if (object.getGuard !== null)
			sb.append('''
				«""»
					if(«(new TermToJava(res)).visit(object.getGuard)»){	
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
				append('''Object «new TermToJava(res).visit(object.getVariable.get(i))» = «new TermToJava(res).visit(object.getInitExpression.get(i))»;
				''')
		}
		let.append('''«new RuleToJava(res,seqBlock,options).visit(object.getInRule)»
}''')
		return let.toString
	}

	def String visit(IterativeWhileRule object) {
		return '''
		while («new TermToJava(res,false).visit(object.guard)»){
			«new RuleToJava(res,true,options).visit(object.rule)»
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
				if («new TermToJava(res).visit(object.guard)»){ 
					«new RuleToJava(res,seqBlock,options).visit(object.thenRule)»
				}
			'''
		else if (object.elseRule instanceof ConditionalRule)
			return '''
				if («new TermToJava(res).visit(object.getGuard)»){ 
						«new RuleToJava(res,seqBlock,options).visit(object.thenRule)»
				} else «new RuleToJava(res,seqBlock,options).visit(object.elseRule)»
			'''
		else
			return '''
					if («new TermToJava(res).visit(object.getGuard)»){ 
					«new RuleToJava(res,seqBlock,options).visit(object.thenRule)»
					}else{
					«new RuleToJava(res,seqBlock,options).visit(object.elseRule)»
				}
			'''
	}


	override visit(TermAsRule rule) {
		
		return "Caso TermAs rule"
		//throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

	override visit(ExtendRule rule) {
		var string = new StringBuffer
		for (var i = 0; i < rule.boundVar.size; i++)
			string.append(
				new DomainToJavaSigDef(res, true).visit(rule.extendedDomain) + " " +
					new TermToJava(res).visit(rule.boundVar.get(i)) + " = new " +
					new DomainToJavaSigDef(res).visit(rule.extendedDomain) + "();\n");
		return string.toString + new RuleToJava(res, seqBlock, options).visit(rule.doRule)

	}

}
