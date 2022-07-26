package org.asmeta.xt.validation.utility

import org.asmeta.xt.asmetal.AnyDomain
import org.asmeta.xt.asmetal.BagCT
import org.asmeta.xt.asmetal.BagTerm
import org.asmeta.xt.asmetal.BasicTerm
import org.asmeta.xt.asmetal.BinaryOperation
import org.asmeta.xt.asmetal.BooleanTerm
import org.asmeta.xt.asmetal.CaseTerm
import org.asmeta.xt.asmetal.CharTerm
import org.asmeta.xt.asmetal.CollectionTerm
import org.asmeta.xt.asmetal.ComplexTerm
import org.asmeta.xt.asmetal.ComprehensionTerm
import org.asmeta.xt.asmetal.ConditionalTerm
import org.asmeta.xt.asmetal.ConstantTerm
import org.asmeta.xt.asmetal.Domain
import org.asmeta.xt.asmetal.EnumTerm
import org.asmeta.xt.asmetal.Expression
import org.asmeta.xt.asmetal.ExtendedTerm
import org.asmeta.xt.asmetal.FiniteQuantificationTerm
import org.asmeta.xt.asmetal.FunctionTerm
import org.asmeta.xt.asmetal.IntegerTerm
import org.asmeta.xt.asmetal.LetTerm
import org.asmeta.xt.asmetal.MapCT
import org.asmeta.xt.asmetal.NaturalTerm
import org.asmeta.xt.asmetal.RealTerm
import org.asmeta.xt.asmetal.RuleAsTerm
import org.asmeta.xt.asmetal.SequenceCT
import org.asmeta.xt.asmetal.SequenceTerm
import org.asmeta.xt.asmetal.SetCT
import org.asmeta.xt.asmetal.SetTerm
import org.asmeta.xt.asmetal.StringTerm
import org.asmeta.xt.asmetal.Term
import org.asmeta.xt.asmetal.TupleTerm
import org.asmeta.xt.asmetal.UndefTerm
import org.asmeta.xt.asmetal.VariableBindingTerm
import org.asmeta.xt.asmetal.VariableTerm
import org.asmeta.xt.asmetal.basicExpr

class DomainCalculatorBackup {
	
	public def static Domain getDomainTerm( Term term ) {

		var Domain res		

		if ( term instanceof Expression ) res = getDomainExpression(term)
		else if ( term instanceof ExtendedTerm ) res = getDomainExtendedTerm(term)
		else res = null
		
		return res
		
	}
	
	public def static Domain getDomainExpression( Expression term ) {

		var Domain res		

		if ( term instanceof BinaryOperation ) res = getDomainBinaryOperation( term )
		else if ( term instanceof basicExpr ) res = getDomainbasicExpr( term )
		else res = getDomainUnaryOperation( term )
		
		return res
		
	}
	
	public def static Domain getDomainBinaryOperation( BinaryOperation term ) {
		
		var boolean_operators = #[ 'or', 'xor', 'iff', 'implies', 'and', '=', '!=', '>', '>=', '<', '<=' ]
		
		for ( String operator : boolean_operators ) {
			if ( term.op.equals(operator) ) return Utility.getDomain("Boolean")
		}
		
		var dom_left = getDomainTerm( term.left )
		var dom_right = getDomainTerm( term.right )
	
		return Utility.getCommonDomain( dom_left, dom_right )
		
	}
	
	public def static Domain getDomainUnaryOperation( Expression term ) {
		
		if ( term.op.equals("not") ) return Utility.getDomain("Boolean")
		
		return getDomainTerm( term.operand )
		
	}
	
	public def static Domain getDomainbasicExpr( basicExpr term ) {
		
		var Domain res		

		if ( term instanceof BasicTerm ) res = getDomainBasicTerm( term )
		else if ( term instanceof FiniteQuantificationTerm ) res = getDomainFiniteQuantificationTerm( term )
		
		return res
		
	}
	
	public def static Domain getDomainBasicTerm( BasicTerm term ) {
		
		var Domain res		

		if ( term instanceof FunctionTerm ) res = getDomainFunctionTerm( term )
		else if ( term instanceof ConstantTerm ) res = getDomainConstantTerm( term )
		else if ( term instanceof VariableTerm ) res = getDomainVariableTerm( term )
		
		return res
		
	}
	
	public def static Domain getDomainFunctionTerm( FunctionTerm term ) {
		
		// if the arguments are null, the function term could be a domain term
		var domain_term = Utility.getDomain( term.functionName )
		if ( domain_term !== null ) return domain_term
		
		// TODO non bastera' solo il metodo naive
		var function = Utility.getFunctionByName( term.functionName )
		
		if ( function === null ) return null

		// we cannot return func.domain, we have to search for the domain in the domain maps, because func.domain !== domain
		// 
		// it's simpler to explain with an example: let's say we have
		// 
		//		Domain: 		abstract domain Philosopher	
		//		Function#1: 	static philo1a: Philosopher
		//		Function#2: 	static philo2a: Philosopher
		//
		// so we have Domain.name === Function#1.codomain.name and Domain.name === Function#2.codomain.name 
		// BUT Domain !== Function#1.codomain.name and Domain !== Function#2.codomain !!!
		//
		// that's because in the grammar, in the function declaration (Function) we have codomain=getDomainByID
		// so a new domain with the same name of the domain in Signature is created
		// we cannot use codomain=ID or something like that because a domain can have a StructuredDomain i.e. eq : Prod( Char, Char ) -> Boolean
		
		var domain_code = Utility.calculateDomainCode( function.codomain )
		
		var Domain dom = Utility.getDomain( domain_code )


		return dom
	}
	
	public def static Domain getDomainConstantTerm( ConstantTerm term ) {
			
		if ( term instanceof EnumTerm ) return Utility.getEnumParent(term.symbol)
		
		var String domain_name = ""
		
		if ( term instanceof BooleanTerm ) domain_name = "Boolean"
		else if ( term instanceof UndefTerm ) domain_name = "Undef"
		else if ( term instanceof ComplexTerm ) {
			if ( term.symbol.contains("i") ) domain_name = "Complex"
			else domain_name = "Integer"
		}
		else if ( term instanceof RealTerm ) domain_name = "Real"
		else if ( term instanceof NaturalTerm ) domain_name = "Natural"
		else if ( term instanceof IntegerTerm ) domain_name = "Integer"
		else if ( term instanceof CharTerm ) domain_name = "Char"
		else if ( term instanceof StringTerm ) domain_name = "String"
		
		return Utility.getDomain( domain_name )
		
	}
	
	public def static Domain getDomainVariableTerm( VariableTerm term ) {
		return Utility.getDomainFromVariable(term.name)
	}
	
	public def static Domain getDomainFiniteQuantificationTerm( FiniteQuantificationTerm term ) {
		return Utility.getDomain("Boolean")
	}
	
	public def static Domain getDomainExtendedTerm( ExtendedTerm term ) {
		// da espandere
			
		var Domain res		

		if ( term instanceof ConditionalTerm ) res = getDomainConditionalTerm( term )
		else if ( term instanceof CaseTerm ) res = getDomainCaseTerm( term )
		else if ( term instanceof TupleTerm ) res = getDomainTupleTerm( term )
		else if ( term instanceof VariableBindingTerm ) res = getDomainVariableBindingTerm( term )
		else if ( term instanceof CollectionTerm ) res = getDomainCollectionTerm( term )
		else if ( term instanceof RuleAsTerm ) res = getDomainRuleAsTerm( term )
		
		return res
		
	}
	//  | TupleTerm | V
	
	public def static Domain getDomainConditionalTerm( ConditionalTerm term ) {
	
		if ( term.elseTerm === null ) return getDomainTerm( term.thenTerm )
		
		var dom_then = getDomainTerm( term.thenTerm )
		var dom_else = getDomainTerm( term.elseTerm )
		
		return Utility.getCommonDomain( dom_then, dom_else  )
	}
	
	public def static Domain getDomainCaseTerm( CaseTerm term ) {
		return Utility.getCommonDomainFromList( term.resultTerms )
	}
	
	public def static Domain getDomainTupleTerm( TupleTerm term ) {
		
		if ( term.terms.size === 0 ) return null
	
		if ( term.terms.size === 1 ) {
			var Domain dom = getDomainTerm( term.terms.get(0) )
			return Utility.getDomain( Utility.calculateDomainCode(dom) )
		}
		
		// we have to get a product domain
		var String res = "Prod("
		var Domain dom
		for ( Term t : term.terms ) {
			dom = getDomainTerm( t )
			if ( dom === null ) return null
			// we should check for the right domain, right now we just ignore the anydomain
			if ( dom instanceof AnyDomain ) return Utility.getDomain( "Any" )
			res += dom.name + ","
		}
		res = res.substring(0, res.length-1)
		res += ")"
		
		return Utility.getDomain( res )
	}
	
	public def static Domain getDomainVariableBindingTerm( VariableBindingTerm term ) {
		
		var Domain res	
		
		if ( term instanceof LetTerm ) res = getDomainLetTerm( term )
		else if ( term instanceof FiniteQuantificationTerm ) res = getDomainFiniteQuantificationTerm( term )
		else if ( term instanceof ComprehensionTerm ) res = getDomainComprehensionTerm( term )
		
		return res
	}
	
	public def static Domain getDomainLetTerm( LetTerm term ) {
		return getDomainTerm( term.body )
	}
	
	public def static Domain getDomainComprehensionTerm( ComprehensionTerm term ) {
		
		var Domain res	
		
		if ( term instanceof SetCT ) res = getDomainSetCT( term )
		else if ( term instanceof MapCT ) res = getDomainMapCT( term )
		else if ( term instanceof SequenceCT ) res = getDomainSequenceCT( term )
		else if ( term instanceof BagCT ) res = getDomainBagCT( term )
		
		return res
	}
	
	public def static Domain getDomainSetCT( SetCT term ) {
		
		var Domain dom = Utility.getCommonDomainFromList( term.ranges )
		
		var String res = "Powerset(" + dom.name + ")"

		var r = Utility.getDomain(res)

		return r
		
	}
	
	public def static Domain getDomainMapCT( MapCT term ) {
		return null
	}
	
	public def static Domain getDomainSequenceCT( SequenceCT term ) {
		var Domain dom = Utility.getCommonDomainFromList( term.ranges )
		var String res = "Seq(" + dom.name + ")"
		
		return Utility.getDomain(res)
	}
	
	public def static Domain getDomainBagCT( BagCT term ) {
		var Domain dom = Utility.getCommonDomainFromList( term.ranges )
		var String res = "Bag(" + dom.name + ")"

		return Utility.getDomain(res)
	}
	
	public def static Domain getDomainCollectionTerm( CollectionTerm term ) {
		
		var Domain res
		
		if ( term instanceof SetTerm ) res = getDomainSetTerm(term)
		else if ( term instanceof SequenceTerm ) res = getDomainSequenceTerm(term)
		else if ( term instanceof BagTerm ) res = getDomainBagTerm(term)
		
		return res
	}
	
	public def static Domain getDomainSetTerm( SetTerm term ) {
		
		if ( term.start_term === null ) return Utility.getDomain("Any")
		
		return getDomainTerm( term.start_term )
	}
	
	public def static Domain getDomainSequenceTerm( SequenceTerm term ) {
		
		if ( term.start_term === null ) return Utility.getDomain("Any")
		
		var dom = getDomainTerm( term.start_term )
		var res = "Seq(" + dom.name + ")"
		
		return Utility.getDomain( res )

	}
	
	public def static Domain getDomainBagTerm( BagTerm term ) {
		
		if ( term.start_term === null ) return Utility.getDomain("Any")
		
		var dom = getDomainTerm( term.start_term )
		var res = "Bag(" + dom.name + ")"
		
		return Utility.getDomain( res )

	}
	
	public def static Domain getDomainRuleAsTerm( RuleAsTerm term ) {
		return Utility.getDomain("Rule")
	}

	
}