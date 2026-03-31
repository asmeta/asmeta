package org.asmeta.xt.validation.utility

import java.util.ArrayList
import javax.swing.tree.DefaultMutableTreeNode
import org.asmeta.xt.asmetal.BagCT
import org.asmeta.xt.asmetal.BagDomain
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
import org.asmeta.xt.asmetal.Function
import org.asmeta.xt.asmetal.FunctionTerm
import org.asmeta.xt.asmetal.IntegerTerm
import org.asmeta.xt.asmetal.LetTerm
import org.asmeta.xt.asmetal.MapCT
import org.asmeta.xt.asmetal.MapTerm
import org.asmeta.xt.asmetal.NaturalTerm
import org.asmeta.xt.asmetal.RealTerm
import org.asmeta.xt.asmetal.RuleAsTerm
import org.asmeta.xt.asmetal.SequenceCT
import org.asmeta.xt.asmetal.SequenceDomain
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
import org.asmeta.xt.asmetal.StructuredTD
import org.asmeta.xt.asmetal.UpdateRule
import org.asmeta.xt.asmetal.LocationTerm
import org.asmeta.xt.asmetal.AsmetalFactory
import org.asmeta.xt.asmetal.AgentDomain

class DomainCalculator {

	public def static DomainTree getDomainTerm( Term term ) {

		var DefaultMutableTreeNode _root = getDomainTermNode( term )
		var tree =  new DomainTree( _root )
		return tree

	}	
	
	public def static DefaultMutableTreeNode getDomainTermNode( Term term) {

		var DefaultMutableTreeNode res	
		
		if (term instanceof FunctionTerm)
			if (Utility.getDomain(term.functionName) !== null)
				return DomainTree.getNodeFromDomain(Utility.getDomain(term.functionName))

		if ( term instanceof Expression ) res = getDomainExpression(term)
		else if ( term instanceof ExtendedTerm ) res = getDomainExtendedTerm(term)
		else if ( term instanceof Domain) res = DomainTree.getNodeFromDomain(term)
		else res = null
		
		return res
		
	}
	
	public def static DefaultMutableTreeNode getDomainExpression( Expression term ){

		var DefaultMutableTreeNode res		

		if ( term instanceof BinaryOperation ) res = getDomainBinaryOperation( term )
		else if ( term instanceof basicExpr ) res = getDomainbasicExpr( term )
		else res = getDomainUnaryOperation( term )
		
		return res
		
	}
	
	public def static DefaultMutableTreeNode getDomainBinaryOperation( BinaryOperation term ) {
		
		var boolean_operators = #[ 'or', 'xor', 'iff', 'implies', 'and', '=', '!=', '>', '>=', '<', '<=' ]
		
		for ( String operator : boolean_operators ) {
			if ( term.op.equals(operator) ) return DomainTree.getNodeFromString("Boolean")
		}
	
		var dom_left = getDomainTermNode( term.left )
	
		var dom_right = getDomainTermNode( term.right )
		
		if (dom_left !== null && dom_right !== null && dom_left.userObject !== null && dom_right.userObject !== null) {
		
			var functionName = Utility.getOperandFunctionName(term.op) + "(Prod(" + dom_left.userObject.toString + "," + dom_right.userObject.toString + "))"			
			var functionDomain = Utility.getFunction(functionName)	
			if (functionDomain !== null)
				return DomainTree.getNodeFromDomain(functionDomain.codomain);
				
		}
		
		var common = Utility.getCommonDomain( dom_left, dom_right )
		
		return DomainTree.getNodeFromDomain(common)
		
	}
	
	public def static DefaultMutableTreeNode getDomainUnaryOperation( Expression term ) {
		
		if ( term.op.equals("not") ) return DomainTree.getNodeFromString("Boolean")
		
		return getDomainTermNode( term.operand )
		
	}
	
	public def static DefaultMutableTreeNode getDomainbasicExpr( basicExpr term ) {
		
		var DefaultMutableTreeNode res		

		if ( term instanceof BasicTerm ) res = getDomainBasicTerm( term )
		else if ( term instanceof FiniteQuantificationTerm ) res = getDomainFiniteQuantificationTerm( term )
		
		return res
		
	}
	
	public def static DefaultMutableTreeNode getDomainBasicTerm( BasicTerm term ){
		
		var DefaultMutableTreeNode res		

		if ( term instanceof FunctionTerm ) res = getDomainFunctionTerm( term )
		else if ( term instanceof LocationTerm ) res = getDomainLocationTerm( term )
		else if ( term instanceof ConstantTerm ) res = getDomainConstantTerm( term )
		else if ( term instanceof VariableTerm ) res = getDomainVariableTerm( term )
		
		return res
		
	}
	
	public def static Function getFunctionFromFunctionTerm( FunctionTerm term ) {

		var String complete_function_name
		var Function function = null
		
		if (  term.arguments === null ) {
			complete_function_name = term.functionName
						
			function = Utility.getFunctionByName( complete_function_name )
		}
		else {
			var domain_arguments = getDomainTupleTerm( term.arguments )
			complete_function_name = term.functionName + "(" + DomainTree.getCodeFromTree( domain_arguments ) + ")"
			function = Utility.getFunction( complete_function_name )	
			
			if ( function === null ) {
				var DomainTree tree =  new DomainTree(domain_arguments)
				var ArrayList<Function> list = Utility.getListOfPossibleFunction( term.functionName )
				if ( list === null ) return null
				function = Utility.searchForMostCompatible( list, tree )
			}	
		}
	
		/*println( "*****funzione trovata " + function )
		println( "funzione trovata " + Utility.calculateDomainCode(function.domain) )
		println( "----------")*/
		
		return function
	
	}
	
	public def static Function getFunctionFromFunctionTerm( LocationTerm term ) {

		var String complete_function_name
		var Function function = null
		
		if (  term.arguments === null ) {
			complete_function_name = term.functionName
						
			function = Utility.getFunctionByName( complete_function_name )
		}
		else {
			var domain_arguments = getDomainTupleTerm( term.arguments )
			complete_function_name = term.functionName + "(" + DomainTree.getCodeFromTree( domain_arguments ) + ")"
			function = Utility.getFunction( complete_function_name )	
			
			if ( function === null ) {
				var DomainTree tree =  new DomainTree(domain_arguments)
				var ArrayList<Function> list = Utility.getListOfPossibleFunction( term.functionName )
				if ( list === null ) return null
				function = Utility.searchForMostCompatible( list, tree )
			}	
		}
	
		/*println( "*****funzione trovata " + function )
		println( "funzione trovata " + Utility.calculateDomainCode(function.domain) )
		println( "----------")*/
		
		return function
	
	}
	
	
	public def static ArrayList<Function> getAllFunctionsFromFunctionTerm( FunctionTerm term ) {

		var String complete_function_name
		var Function function = null
		var ArrayList<Function> listFunctions =  new ArrayList
		
		if (  term.arguments === null ) {
			complete_function_name = term.functionName
						
			function = Utility.getFunctionByName( complete_function_name )
		}
		else {
			var domain_arguments = getDomainTupleTerm( term.arguments )
			complete_function_name = term.functionName + "(" + DomainTree.getCodeFromTree( domain_arguments ) + ")"
			function = Utility.getFunction( complete_function_name )	
			
			if ( function === null ) {
				var DomainTree tree =  new DomainTree(domain_arguments)
				var ArrayList<Function> list = Utility.getListOfPossibleFunction( term.functionName )
				if ( list === null ) return null
				listFunctions = list
			}	
		}
		
		return listFunctions
	
	}
	
	public def static ArrayList<Function> getAllFunctionsFromFunctionTerm( LocationTerm term ) {

		var String complete_function_name
		var Function function = null
		var ArrayList<Function> listFunctions =  new ArrayList
		
		if (  term.arguments === null ) {
			complete_function_name = term.functionName
						
			function = Utility.getFunctionByName( complete_function_name )
		}
		else {
			var domain_arguments = getDomainTupleTerm( term.arguments )
			complete_function_name = term.functionName + "(" + DomainTree.getCodeFromTree( domain_arguments ) + ")"
			function = Utility.getFunction( complete_function_name )	
			
			if ( function === null ) {
				var DomainTree tree =  new DomainTree(domain_arguments)
				var ArrayList<Function> list = Utility.getListOfPossibleFunction( term.functionName )
				if ( list === null ) return null
				listFunctions = list
			}	
		}
		
		return listFunctions
	
	}
	
	public def static DefaultMutableTreeNode getDomainFunctionTerm( FunctionTerm term ){

		// if the arguments are null, the function term could be a domain term		
		var Domain domain_term

		domain_term = Utility.getDomain( term.functionName )
			
		if ( domain_term !== null ) return DomainTree.getNodeFromDomain(domain_term)
		
		var function = getFunctionFromFunctionTerm(term)
		
		if ( function === null ) return null
		
		if ( function.name.equals("self") ) {
			
			// Create a new AnyAgent Domain
			var AgentDomain anyAgent = AsmetalFactory.eINSTANCE.createAgentDomain()
			anyAgent.name = "AnyAgent"
			Utility.signature_domain_map.put("AnyAgent", anyAgent)
			return DomainTree.getNodeFromDomain(anyAgent)
			
		}
		
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
		// so a  domain with the same name of the domain in Signature is created
		// we cannot use codomain=ID or something like that because a domain can have a StructuredDomain i.e. eq : Prod( Char, Char ) -> Boolean

		var DefaultMutableTreeNode res
		var String domain_code = Utility.calculateDomainCode( function.codomain )
		var Domain dom = Utility.getDomain( domain_code )
		
		if (dom === null) {
			// Check if it is a basic domain
			for (Domain d : BasicDomains.basic_domain_list) {
				if (d.name.equals(domain_code)) {
					dom = d;
					Utility.imported_all_domain_map.put(domain_code, dom);
				}
			}
		}

		if ( Utility.isFromAnyDomain(dom) ) {
			
			// serve calcolare il vero valore del dominio
			//res = Utility.getMappedCodomain
			res = DomainTree.getNodeFromDomain(dom)
			
		}
		else res = DomainTree.getNodeFromDomain(dom)
	
		return res
		
	}
	
	public def static DefaultMutableTreeNode getDomainLocationTerm( LocationTerm term ){

		// if the arguments are null, the function term could be a domain term		
		var Domain domain_term

		domain_term = Utility.getDomain( term.functionName )
			
		if ( domain_term !== null ) return DomainTree.getNodeFromDomain(domain_term)
		
		var function = getFunctionFromFunctionTerm(term)
		
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
		// so a  domain with the same name of the domain in Signature is created
		// we cannot use codomain=ID or something like that because a domain can have a StructuredDomain i.e. eq : Prod( Char, Char ) -> Boolean

		var DefaultMutableTreeNode res
		var String domain_code = Utility.calculateDomainCode( function.codomain )
		var Domain dom = Utility.getDomain( domain_code )
		
		if (dom === null) {
			// Check if it is a basic domain
			for (Domain d : BasicDomains.basic_domain_list) {
				if (d.name.equals(domain_code)) {
					dom = d;
					Utility.imported_all_domain_map.put(domain_code, dom);
				}
			}
		}

		if ( Utility.isFromAnyDomain(dom) ) {
			
			// serve calcolare il vero valore del dominio
			//res = Utility.getMappedCodomain
			res = DomainTree.getNodeFromDomain(dom)
			
		}
		else res = DomainTree.getNodeFromDomain(dom)
	
		return res
		
	}
	
	public def static DefaultMutableTreeNode getDomainConstantTerm( ConstantTerm term ){
			
		if ( term instanceof EnumTerm ) {	
			var dom = Utility.getEnumParent(term.symbol)
			return DomainTree.getNodeFromDomain(dom)
		}
		
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
		
		return DomainTree.getNodeFromString( domain_name )
		
	}
	
	public def static DefaultMutableTreeNode getDomainVariableTerm( VariableTerm term ){		
		var dom = Utility.getDomainFromVariable(term.name)
		var node =  DomainTree.getNodeFromDomain( dom )
		
		if (dom instanceof StructuredTD && (term.eContainer instanceof BinaryOperation)) {
			return node.firstLeaf
		}
			

		return node
	}
	
	public def static DefaultMutableTreeNode getDomainFiniteQuantificationTerm( FiniteQuantificationTerm term ){
		return DomainTree.getNodeFromString("Boolean" )
	}
	
	public def static DefaultMutableTreeNode getDomainExtendedTerm( ExtendedTerm term ){
		// da espandere
			
		var DefaultMutableTreeNode res		

		if ( term instanceof ConditionalTerm ) res = getDomainConditionalTerm( term )
		else if ( term instanceof CaseTerm ) res = getDomainCaseTerm( term )
		else if ( term instanceof TupleTerm ) res = getDomainTupleTerm( term )
		else if ( term instanceof VariableBindingTerm ) res = getDomainVariableBindingTerm( term )
		else if ( term instanceof CollectionTerm ) res = getDomainCollectionTerm( term )
		else if ( term instanceof RuleAsTerm ) res = getDomainRuleAsTerm( term )
		
		return res
		
	}
	//  | TupleTerm | V
	
	public def static DefaultMutableTreeNode getDomainConditionalTerm( ConditionalTerm term ){
	
		if ( term.elseTerm === null ) return getDomainTermNode( term.thenTerm )
		
		var DefaultMutableTreeNode root_then =  new DefaultMutableTreeNode
		var dom_then = getDomainTermNode( term.thenTerm )
		
		var DefaultMutableTreeNode root_else =  new DefaultMutableTreeNode
		var dom_else = getDomainTermNode( term.elseTerm )
		
		var dom =  Utility.getCommonDomain( dom_then, dom_else  )
		
		return DomainTree.getNodeFromDomain( dom )
	}
	
	public def static DefaultMutableTreeNode getDomainCaseTerm( CaseTerm term ){		
		var dom = Utility.getCommonDomainFromList( term.resultTerms )		
		return DomainTree.getNodeFromDomain( dom )
	}
	
	public def static DefaultMutableTreeNode getDomainTupleTerm( TupleTerm term ){
		
		if ( term.terms.size === 0 ) return null
	
		if ( term.terms.size === 1 ) {
			var res = getDomainTermNode( term.terms.get(0) )
			return res
		}
		
		// we have to get a product domain
		var ArrayList<DefaultMutableTreeNode> nodes = new ArrayList

		var DefaultMutableTreeNode node
		for ( Term t : term.terms ) {
			node = getDomainTermNode( t )
			nodes.add( node )		
		}
		
		return DomainTree.getComposedNodeFromNodes( "Prod", nodes )
	}
	
	public def static DefaultMutableTreeNode getDomainVariableBindingTerm( VariableBindingTerm term ){
		
		var DefaultMutableTreeNode res	
		
		if ( term instanceof LetTerm ) res = getDomainLetTerm( term )
		else if ( term instanceof FiniteQuantificationTerm ) res = getDomainFiniteQuantificationTerm( term )
		else if ( term instanceof ComprehensionTerm ) res = getDomainComprehensionTerm( term )
		
		return res
	}
	
	public def static DefaultMutableTreeNode getDomainLetTerm( LetTerm term ){
		// fill variable
		Utility.fillVariableMap(term)
		return getDomainTermNode( term.body )
	}
	
	public def static DefaultMutableTreeNode getDomainComprehensionTerm( ComprehensionTerm term ){
		
		var DefaultMutableTreeNode res	
		
		if ( term instanceof SetCT ) res = getDomainSetCT( term )
		else if ( term instanceof MapCT ) res = getDomainMapCT( term )
		else if ( term instanceof SequenceCT ) res = getDomainSequenceCT( term )
		else if ( term instanceof BagCT ) res = getDomainBagCT( term )
		
		return res
	}
	
	public def static DefaultMutableTreeNode getDomainSetCT( SetCT term ){
	
		/* OLD VERSION
		 * 
		 * var ArrayList<Domain> list =  ArrayList
		 * var Domain dom = Utility.getCommonDomainFromList( term.ranges )
		 * if ( dom instanceof PowersetDomain ) return DomainTree.getNodeFromDomain(dom)
		 * list.add( dom )
		 * return DomainTree.getComposedNode( "Powerset", list )*/
		
		// List of all the domains in the Powerset
		var ArrayList<Domain> list =  new ArrayList
		
		// Add all the terms of the Powerset in the list
		for(var i=0; i<term.ranges.size; i++) {
			try {
				var DefaultMutableTreeNode domName = DomainCalculator.getDomainTerm(term.ranges.get(i)).model.root as DefaultMutableTreeNode
				var String domNameStr;
				
				if (domName !== null) {
					domNameStr = DomainTree.getCodeFromTree(domName)					
				}
				
				var Domain dom = Utility.getDomain(domNameStr)
				
				list.add(dom)
			} catch(Exception e) {}
		} 	
		
		// If there are more domains, the list must be considered as a Product
		if (list.size > 1) {
			var strName = "Prod("
			for(Domain d : list)
				strName += d.name + ","
			strName = strName.substring(0,strName.length-1) + ")"
			list.clear
			list.add(Utility.getDomain(strName))
		}
		
		return DomainTree.getComposedNode( "Powerset", list )
	}
	
	public def static DefaultMutableTreeNode getDomainMapCT( MapCT term ){
		// TODO da fare
		return null
	}
	
	public def static DefaultMutableTreeNode getDomainSequenceCT( SequenceCT term ){
		var Domain dom = Utility.getCommonDomainFromList( term.ranges )		
		if ( dom instanceof SequenceDomain ) return DomainTree.getNodeFromDomain(dom)
		
		var ArrayList<Domain> list =  new ArrayList
		list.add( dom )
	
		return DomainTree.getComposedNode( "Seq", list )
	}
	
	public def static DefaultMutableTreeNode getDomainBagCT( BagCT term ){
		
		var Domain dom = Utility.getCommonDomainFromList( term.ranges )
		if ( dom instanceof BagDomain ) return DomainTree.getNodeFromDomain(dom)		
		
		var ArrayList<Domain> list =  new ArrayList
		list.add( dom )
		return DomainTree.getComposedNode( "Bag", list )
	}
	
	public def static DefaultMutableTreeNode getDomainCollectionTerm( CollectionTerm term ){
		
		var DefaultMutableTreeNode res
		
		if ( term instanceof SetTerm ) res = getDomainSetTerm(term )
		else if ( term instanceof SequenceTerm ) res = getDomainSequenceTerm(term )
		else if ( term instanceof BagTerm ) res = getDomainBagTerm(term )
		else if ( term instanceof MapTerm ) res = getDomainMapTerm( term )
		
		return res
	}
	
	public def static DefaultMutableTreeNode getDomainSetTerm( SetTerm term ){	
		var ArrayList<DefaultMutableTreeNode> list =  new ArrayList
		
		if ( term.start_term === null ) return DomainTree.getNodeFromString( "Any_Powerset" )
		
		if ( term.start_term instanceof SetTerm) {
			var dom = getDomainTermNode( term.start_term )
			list.add(dom)
			var internalPowerset = DomainTree.getComposedNodeFromNodes("Powerset", list)
			list.clear
			list.add(internalPowerset)
			return DomainTree.getComposedNodeFromNodes("Powerset", list)
		}
		else if ( term.start_term instanceof SequenceTerm) {
			var dom = getDomainTermNode( term.start_term )
			list.add(dom)
			return DomainTree.getComposedNodeFromNodes("Seq", list)
		}
		else if ( term.start_term instanceof BagTerm) {
			var dom = getDomainTermNode( term.start_term )
			list.add(dom)
			return DomainTree.getComposedNodeFromNodes("Bag", list)
		}
		else if ( term.start_term instanceof MapTerm) {
			var dom = getDomainTermNode( term.start_term )
			list.add(dom)
			return DomainTree.getComposedNodeFromNodes("Map", list)
		}
		else {
			if (term.eContainer instanceof UpdateRule || term.eContainer instanceof TupleTerm) {
				var dom = getDomainTermNode( term.start_term )
				list.add(dom)
				return DomainTree.getComposedNodeFromNodes("Powerset", list)
			}
			else {
				return getDomainTermNode( term.start_term )
			}
		}
		//		else return getDomainTermNode( term.start_term )
		
		/*var ArrayList<DefaultMutableTreeNode> list =  ArrayList
		var dom = getDomainTermNode( term.start_term )
		list.add(dom)
		return DomainTree.getComposedNodeFromNodes("Powerset", list)*/
	}
	
	public def static DefaultMutableTreeNode getDomainSequenceTerm( SequenceTerm term ){	
		if ( term.start_term === null ) return DomainTree.getNodeFromString( "Any_Seq" )
		var dom = getDomainTermNode( term.start_term )
		var ArrayList<DefaultMutableTreeNode> list =  new ArrayList
		list.add(dom)
		return DomainTree.getComposedNodeFromNodes( "Seq", list )
	}
	
	public def static DefaultMutableTreeNode getDomainBagTerm( BagTerm term ){	
		
		//println("start of bag " + term.start_term)
		
		if ( term.start_term === null ) return DomainTree.getNodeFromString( "Any_Bag" )
		var dom = getDomainTermNode( term.start_term )
		var ArrayList<DefaultMutableTreeNode> list =  new ArrayList
		list.add(dom)
		return DomainTree.getComposedNodeFromNodes( "Bag", list )
	}
	
	public def static DefaultMutableTreeNode getDomainMapTerm( MapTerm term ){

		if ( term.term === null || term.term.size < 2 ) return DomainTree.getNodeFromString( "Any_Map" )

		var ArrayList<Term> sourceTerms =  new ArrayList
		var ArrayList<Term> targetTerms =  new ArrayList

		for ( var int i = 0 ; i < term.term.size ; i++ ) {
			sourceTerms.add( term.term.get(i) )
			i++
			targetTerms.add( term.term.get(i) )
		}

		var dom_source = Utility.getCommonDomainFromList( sourceTerms )
		var dom_target = Utility.getCommonDomainFromList( targetTerms )
		
		var ArrayList<Domain> list =  new ArrayList
		list.add( dom_source )
		list.add( dom_target )
		
		return DomainTree.getComposedNode( "Map", list )
		
		
	}
	
	public def static DefaultMutableTreeNode getDomainRuleAsTerm( RuleAsTerm term ){
		return DomainTree.getNodeFromString( "Rule" )
	}
	
	
}