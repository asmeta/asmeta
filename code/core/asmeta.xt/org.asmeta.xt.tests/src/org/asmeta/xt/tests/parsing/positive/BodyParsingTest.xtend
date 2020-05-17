package org.asmeta.xt.tests.parsing.positive

import com.google.inject.Inject
import org.asmeta.xt.asmetal.Asm
import org.asmeta.xt.asmetal.Expression
import org.asmeta.xt.asmetal.Function
import org.asmeta.xt.asmetal.Invariant
import org.asmeta.xt.asmetal.impl.AbstractTDImpl
import org.asmeta.xt.asmetal.impl.BinaryOperationImpl
import org.asmeta.xt.asmetal.impl.CaseTermImpl
import org.asmeta.xt.asmetal.impl.CompassionConstraintImpl
import org.asmeta.xt.asmetal.impl.ConcreteDomainImpl
import org.asmeta.xt.asmetal.impl.CtlSpecImpl
import org.asmeta.xt.asmetal.impl.DerivedFunctionImpl
import org.asmeta.xt.asmetal.impl.EnumTermImpl
import org.asmeta.xt.asmetal.impl.ExpressionImpl
import org.asmeta.xt.asmetal.impl.FunctionTermImpl
import org.asmeta.xt.asmetal.impl.IntegerTermImpl
import org.asmeta.xt.asmetal.impl.InvariantConstraintImpl
import org.asmeta.xt.asmetal.impl.InvariantImpl
import org.asmeta.xt.asmetal.impl.JusticeConstraintImpl
import org.asmeta.xt.asmetal.impl.LtlSpecImpl
import org.asmeta.xt.asmetal.impl.SetTermImpl
import org.asmeta.xt.asmetal.impl.StaticFunctionImpl
import org.asmeta.xt.asmetal.impl.VariableTermImpl
import org.asmeta.xt.tests.AsmetaLInjectorProvider
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(XtextRunner)
@InjectWith(AsmetaLInjectorProvider)
class BodyParsingTest {
	
	@Inject	ParseHelper<Asm> parseHelper
	@Inject extension ValidationTestHelper
	
	@Test
	def void testDomainFunctionDefinitions() {
		
		var result = parseHelper.parse('''
			asm domain_function
			
			import StandardLibrary( Integer, Any )
			
			signature:
				abstract domain Philosopher
				domain NumOfCherries subsetof Integer
			
				derived leftNeighbour: Philosopher -> Philosopher
				derived rightNeighbour: Philosopher -> Philosopher
				
				static philo1: Philosopher
				static philo2: Philosopher
				static philo3: Philosopher
				static philo4: Philosopher
				static philo5: Philosopher
			
			definitions:
				domain NumOfCherries = {0 .. 3}
			
				function leftNeighbour($p in Philosopher) =
					switch($p)
						case philo1: philo5
						case philo2: philo1
						case philo3: philo2
						case philo4: philo3
						case philo5: philo4
					endswitch
		''')
		result.assertNoErrors
		
		// TODO manca controllo della rule declaration
		
		var i = 0
		
		// SIGNATURE
		
		Assert.assertEquals( 2, result.headerSection.signature.domain.size)	
		Assert.assertEquals( 7, result.headerSection.signature.function.size)
		
		// abstract domain Philosopher
		var abstr_dom = result.headerSection.signature.domain.get(i)
		Assert.assertEquals( "Philosopher", abstr_dom.name )
		Assert.assertEquals( typeof(AbstractTDImpl), abstr_dom.class )
		
		// domain NumOfCherries subsetof Integer
		i++
		var conc_dom = result.headerSection.signature.domain.get(i)
		Assert.assertEquals( "NumOfCherries", conc_dom.name )
		Assert.assertEquals( typeof(ConcreteDomainImpl), conc_dom.class )
		// Integer
		Assert.assertEquals( "Integer", (conc_dom as ConcreteDomainImpl).typeDomain.name )
	
		i = 0
		var Function func 
		
		//derived leftNeighbour: Philosopher -> Philosopher
		func = result.headerSection.signature.function.get(i)
		Assert.assertEquals( typeof(DerivedFunctionImpl), func.class )
		Assert.assertEquals( "leftNeighbour", func.name )
		Assert.assertEquals( "Philosopher", func.codomain.name )
		Assert.assertEquals( "Philosopher", func.domain.name )
		
		// derived rightNeighbour: Philosopher -> Philosopher
		i++
		func = result.headerSection.signature.function.get(i)
		Assert.assertEquals( typeof(DerivedFunctionImpl), func.class )
		Assert.assertEquals( "rightNeighbour", func.name )
		Assert.assertEquals( "Philosopher", func.codomain.name )
		Assert.assertEquals( "Philosopher", func.domain.name )
		
		// static philo1: Philosopher
		i++
		func = result.headerSection.signature.function.get(i)
		Assert.assertEquals( typeof(StaticFunctionImpl), func.class )
		Assert.assertEquals( "philo1", func.name )
		Assert.assertEquals( "Philosopher", func.codomain.name )
		Assert.assertEquals( null, func.domain )
		
		// static philo2: Philosopher
		i++
		func = result.headerSection.signature.function.get(i)
		Assert.assertEquals( typeof(StaticFunctionImpl), func.class )
		Assert.assertEquals( "philo2", func.name )
		Assert.assertEquals( "Philosopher", func.codomain.name )
		Assert.assertEquals( null, func.domain )
		
		// static philo3: Philosopher
		i++
		func = result.headerSection.signature.function.get(i)
		Assert.assertEquals( typeof(StaticFunctionImpl), func.class )
		Assert.assertEquals( "philo3", func.name )
		Assert.assertEquals( "Philosopher", func.codomain.name )
		Assert.assertEquals( null, func.domain )
		
		// static philo4: Philosopher
		i++
		func = result.headerSection.signature.function.get(i)
		Assert.assertEquals( typeof(StaticFunctionImpl), func.class )
		Assert.assertEquals( "philo4", func.name )
		Assert.assertEquals( "Philosopher", func.codomain.name )
		Assert.assertEquals( null, func.domain )
		
		// static philo5: Philosopher
		i++
		func = result.headerSection.signature.function.get(i)
		Assert.assertEquals( typeof(StaticFunctionImpl), func.class )
		Assert.assertEquals( "philo5", func.name )
		Assert.assertEquals( "Philosopher", func.codomain.name )
		Assert.assertEquals( null, func.domain )
		
		// BODY

		Assert.assertEquals( 1, result.bodySection.domainDefinition.size )
		Assert.assertEquals( 1, result.bodySection.functionDefinition.size )	
		
		i = 0
		// domain NumOfCherries = {0 .. 3}
		var dom_def = result.bodySection.domainDefinition.get(0)
		Assert.assertEquals( "NumOfCherries", dom_def.definedDomainName )
		Assert.assertEquals( typeof(SetTermImpl), dom_def.body.class )
		// {0 .. 3}
		var seq_term = dom_def.body as SetTermImpl
		Assert.assertEquals( typeof(IntegerTermImpl), seq_term.start_term.class )
		/*var val_term = seq_term.start_term as IntegerTermImpl
		Assert.assertEquals( "0", val_term.symbol )*/
		Assert.assertEquals( typeof(IntegerTermImpl), seq_term.end_term.class )
		var val_term = seq_term.end_term as IntegerTermImpl
		Assert.assertEquals( "3", val_term.symbol )

		i = 0
		// function leftNeighbour($p in Philosopher) = ...
		var func_def = result.bodySection.functionDefinition.get(i)
		Assert.assertEquals( "leftNeighbour", func_def.definedFunctionName )
		Assert.assertEquals( 1, func_def.variables.size )
		Assert.assertEquals( "$p", func_def.variables.get(0) )
		Assert.assertEquals( 1, func_def.domain.size )
		Assert.assertEquals( "Philosopher", func_def.domain.get(0).name )
		// switch($p)
		Assert.assertEquals( typeof(CaseTermImpl), func_def.body.class )
		var switch_term = func_def.body as CaseTermImpl

		Assert.assertEquals( typeof(VariableTermImpl), switch_term.comparedTerm.class )
		Assert.assertEquals( "$p", (switch_term.comparedTerm as VariableTermImpl).name )
		Assert.assertEquals( 5, switch_term.comparingTerm.size )
		Assert.assertEquals( 5, switch_term.resultTerms.size )
		Assert.assertEquals( null, switch_term.otherwiseTerm )
		// case philo1: philo5
		Assert.assertEquals( typeof(FunctionTermImpl), switch_term.comparingTerm.get(0).class )
		var func_term = switch_term.comparingTerm.get(0) as FunctionTermImpl
		Assert.assertEquals( "philo1", func_term.functionName )
		Assert.assertEquals( typeof(FunctionTermImpl), switch_term.comparingTerm.get(0).class )
		func_term = switch_term.resultTerms.get(0) as FunctionTermImpl
		Assert.assertEquals( "philo5", func_term.functionName )

	}
		
	@Test
	def void testInvariant() {
		var result = parseHelper.parse('''
			asm invariant_asm
			
			import StandardLibrary
			
			signature:
				enum domain Side = {LEFT | RIGHT}
				enum domain Actors = {FERRYMAN | GO | CA | WO}
				dynamic controlled position: Actors -> Side
			
			definitions:
			
				//Se la capra (GO) e il cavolo (CA) sono sulla stessa sponda, allora deve essere presente anche il FERRYMAN
				invariant over position: position(GO)=position(CA) implies position(GO)=position(FERRYMAN)
			''')
		result.assertNoErrors
		
		var i = 0
		
		Assert.assertEquals( 1, result.bodySection.property.size )	
		
		// --- invariant over position: position(GO)=position(CA) implies position(GO)=position(FERRYMAN)
		// invariant over positio
		Assert.assertEquals( typeof(InvariantImpl), result.bodySection.property.get(i).class )	
		var inv = result.bodySection.property.get(i) as Invariant
		Assert.assertEquals( null, inv.name )	
		Assert.assertEquals( 1, inv.invar_list.size )	
		Assert.assertEquals( "position", inv.invar_list.get(0).constrainedName )	
		// position(GO)=position(CA) implies position(GO)=position(FERRYMAN)
		var e1 = inv.body as BinaryOperationImpl
		Assert.assertEquals( "implies", e1.op )	
		Assert.assertEquals( typeof(BinaryOperationImpl), e1.right.class )
		Assert.assertEquals( typeof(BinaryOperationImpl), e1.left.class )
		// left 	=> position(GO)=position(CA)
		var e1_left = e1.left as BinaryOperationImpl
		Assert.assertEquals( "=", e1_left.op )
		Assert.assertEquals( typeof(FunctionTermImpl), e1_left.left.class )
		var func = e1_left.left as FunctionTermImpl
		Assert.assertEquals( "position", func.functionName )
		Assert.assertEquals( 1, func.arguments.terms.size )
		Assert.assertEquals( typeof(EnumTermImpl), func.arguments.terms.get(0).class )
		var enum = func.arguments.terms.get(0) as EnumTermImpl
		Assert.assertEquals( "GO", enum.symbol )
		func = e1_left.right as FunctionTermImpl
		Assert.assertEquals( "position", func.functionName )
		Assert.assertEquals( 1, func.arguments.terms.size )
		Assert.assertEquals( typeof(EnumTermImpl), func.arguments.terms.get(0).class )
		enum = func.arguments.terms.get(0) as EnumTermImpl
		Assert.assertEquals( "CA", enum.symbol )
		// right 	=> position(GO)=position(FERRYMAN)
		var e1_right = e1.right as BinaryOperationImpl
		Assert.assertEquals( "=", e1_right.op )
		Assert.assertEquals( typeof(FunctionTermImpl), e1_left.left.class )
		func = e1_right.left as FunctionTermImpl
		Assert.assertEquals( "position", func.functionName )
		Assert.assertEquals( 1, func.arguments.terms.size )
		Assert.assertEquals( typeof(EnumTermImpl), func.arguments.terms.get(0).class )
		enum = func.arguments.terms.get(0) as EnumTermImpl
		Assert.assertEquals( "GO", enum.symbol )
		func = e1_right.right as FunctionTermImpl
		Assert.assertEquals( "position", func.functionName )
		Assert.assertEquals( 1, func.arguments.terms.size )
		Assert.assertEquals( typeof(EnumTermImpl), func.arguments.terms.get(0).class )
		enum = func.arguments.terms.get(0) as EnumTermImpl
		Assert.assertEquals( "FERRYMAN", enum.symbol )
		
	}
	
	// TODO ritestare dopo i controlli sugli import
	//@Test
	def void testTemporalProperty() {
		var result = parseHelper.parse('''
			asm temporal_pro
			import LTLlibrary
			import CTLlibrary
			
			signature:		
				domain AgentDomain subsetof Agent
				domain AgentDomainEnv subsetof Agent
				domain Line subsetof Integer
				enum domain State = { EXCLUSIVE | SHARED | INVALID}
			
				static l1: Line
			
				controlled ccState: Prod(AgentDomain, Line) -> State
			
				static a1: AgentDomain
				static a2: AgentDomain
				static ee: AgentDomainEnv
			
			definitions:
				domain Line = {1}
				domain AgentDomain = {a1, a2}
				function a1 = a1
				function a2 = a2

				//proprieta' LTL
				LTLSPEC g(not( (ccState(a1, 1) = EXCLUSIVE) and (ccState(a2, 1)=EXCLUSIVE) ))
				CTLSPEC ag(ccState(a1, 1)=INVALID and ccState(a2, 1)=INVALID)	
			''')
		result.assertNoErrors
		
		var i = 0
		
		Assert.assertEquals( 2, result.bodySection.property.size )	
		
		// LTLSPEC g( not( (ccState(a1, 1) = EXCLUSIVE) and (ccState(a2, 1)=EXCLUSIVE) ) )
		var spec = result.bodySection.property.get(i) 
		Assert.assertEquals( null, spec.name )
		Assert.assertEquals( typeof(LtlSpecImpl), spec.class )
		Assert.assertEquals( typeof(FunctionTermImpl), spec.body.class )
		var func = spec.body as FunctionTermImpl
		// g( not( (ccState(a1, 1) = EXCLUSIVE) and (ccState(a2, 1)=EXCLUSIVE) ) )
		// ^
		Assert.assertEquals( "g", func.functionName )
		Assert.assertEquals( 1, func.arguments.terms.size )
		Assert.assertEquals( typeof(ExpressionImpl), func.arguments.terms.get(0).class )
		// g( not( (ccState(a1, 1) = EXCLUSIVE) and (ccState(a2, 1)=EXCLUSIVE) ) )
		//     ^
		var expr =  func.arguments.terms.get(0) as Expression
		Assert.assertEquals( "not", expr.op )
		Assert.assertEquals( typeof(BinaryOperationImpl), expr.operand.class )
		// g( not( (ccState(a1, 1) = EXCLUSIVE) and (ccState(a2, 1)=EXCLUSIVE) ) )
		//                                       ^
		var func_bin = expr.operand as BinaryOperationImpl
		Assert.assertEquals( "and", func_bin.op )
		Assert.assertEquals( typeof(BinaryOperationImpl), func_bin.left.class )
		Assert.assertEquals( typeof(BinaryOperationImpl), func_bin.right.class )
		// (ccState(a1, 1) = EXCLUSIVE)
		var func_bin_left = func_bin.left as BinaryOperationImpl
		Assert.assertEquals( "=", func_bin_left.op )
		Assert.assertEquals( typeof(FunctionTermImpl), func_bin_left.left.class )
		Assert.assertEquals( typeof(EnumTermImpl), func_bin_left.right.class )
		// ccState(a1, 1)
		var func_bin_left_left = func_bin_left.left as FunctionTermImpl
		// ccState(a1, 1)
		Assert.assertEquals( "ccState", func_bin_left_left.functionName )
		Assert.assertEquals( 2, func_bin_left_left.arguments.terms.size )
		Assert.assertEquals( typeof(EnumTermImpl), func_bin_left.right.class )
		// a1
		Assert.assertEquals( typeof(FunctionTermImpl), func_bin_left_left.arguments.terms.get(0).class )	
		func = func_bin_left_left.arguments.terms.get(0) as FunctionTermImpl
		Assert.assertEquals( "a1", func.functionName )		
		// 1
		Assert.assertEquals( typeof(IntegerTermImpl), func_bin_left_left.arguments.terms.get(1).class )
		var number = func_bin_left_left.arguments.terms.get(1) as IntegerTermImpl
		Assert.assertEquals( "1", number.symbol )
		// EXCLUSIVE
		var func_bin_left_right = func_bin_left.right as EnumTermImpl
		Assert.assertEquals( "EXCLUSIVE", func_bin_left_right.symbol )
		// ccState(a2, 1)=EXCLUSIVE
		var func_bin_right = func_bin.right as BinaryOperationImpl
		Assert.assertEquals( "=", func_bin_right.op )
		Assert.assertEquals( typeof(FunctionTermImpl), func_bin_right.left.class )
		Assert.assertEquals( typeof(EnumTermImpl), func_bin_right.right.class )
		// ccState(a1, 1)
		var func_bin_right_left = func_bin_right.left as FunctionTermImpl
		// ccState(a1, 1)
		Assert.assertEquals( "ccState", func_bin_right_left.functionName )
		Assert.assertEquals( 2, func_bin_right_left.arguments.terms.size )
		Assert.assertEquals( typeof(EnumTermImpl), func_bin_right.right.class )
		// a2
		Assert.assertEquals( typeof(FunctionTermImpl), func_bin_right_left.arguments.terms.get(0).class )	
		func = func_bin_right_left.arguments.terms.get(0) as FunctionTermImpl
		Assert.assertEquals( "a2", func.functionName )		
		// 1
		Assert.assertEquals( typeof(IntegerTermImpl), func_bin_right_left.arguments.terms.get(1).class )
		number = func_bin_right_left.arguments.terms.get(1) as IntegerTermImpl
		Assert.assertEquals( "1", number.symbol )
		// EXCLUSIVE
		var func_bin_right_right = func_bin_right.right as EnumTermImpl
		Assert.assertEquals( "EXCLUSIVE", func_bin_right_right.symbol )
		
		// CTLSPEC ag(ccState(a1, 1)=INVALID and ccState(a2, 1)=INVALID)
		i++
		spec = result.bodySection.property.get(i) 
		Assert.assertEquals( null, spec.name )
		Assert.assertEquals( typeof(CtlSpecImpl), spec.class )

	}
		
	@Test
	def void testInvarFairness() {
		// TODO trovare degli esempi validi
		var result = parseHelper.parse('''
			asm fairness
			import StandardLibrary
			signature: 
				
			definitions: 
			
				INVAR 5 > 6
				JUSTICE 5 > 6
				COMPASSION(  5 > 6, 5 > 6 )
		''')
		result.assertNoErrors	
	
		var i = 0
	
		Assert.assertEquals( 1, result.bodySection.invariantConstraint.size )	
		Assert.assertEquals( 2, result.bodySection.fairnessConstraint.size )	
		
		Assert.assertEquals( typeof(InvariantConstraintImpl), result.bodySection.invariantConstraint.get(i).class )
		var invar = result.bodySection.invariantConstraint.get(i) as InvariantConstraintImpl
		
		i = 0
		Assert.assertEquals( typeof(JusticeConstraintImpl), result.bodySection.fairnessConstraint.get(i).class )
		var justice = result.bodySection.fairnessConstraint.get(i) as JusticeConstraintImpl
			
		i++
		Assert.assertEquals( typeof(CompassionConstraintImpl), result.bodySection.fairnessConstraint.get(i).class )
		var compassion = result.bodySection.fairnessConstraint.get(i) as CompassionConstraintImpl

		
	}
		
	//@Test
	/*def void testInvariant() {
		var result = parseHelper.parse('''
			asm prova
			signature: 
				enum domain LightState = {FLASHING | OFF}
				enum domain GateState = {CLOSED | OPENED | CLOSING | OPENING}
				dynamic controlled gate: GateState
				dynamic controlled light: LightState
			definitions: 
			
				invariant over gate: (gate = CLOSING or gate = CLOSED or gate = OPENING) implies light = FLASHING

				//The gate cannot be "closed" after "opening"
				invariant over gate: gateStatusUpdateOk
		
				invariant prova over board, board1: abs(size(chosenCells(NOUGHT)) - size(chosenCells(CROSS))) <= 1 
			default init prova :
		''')
		result.assertNoErrors
		
		var i = 0
		var String[] expected
		var Class[] classes
		
		Assert.assertEquals( 3, result.bodySection.property.size )	
		
		// --- invariant over gate: (gate = CLOSING or gate = CLOSED or gate = OPENING) implies light = FLASHING
		Assert.assertEquals( typeof(InvariantImpl), result.bodySection.property.get(i).class )	
		var inv = result.bodySection.property.get(i) as Invariant
		Assert.assertEquals( null, inv.name )	
		Assert.assertEquals( 1, inv.inv_list.size )	
		Assert.assertEquals( "gate", inv.inv_list.get(0) )	
		// -- (gate = CLOSING or gate = CLOSED or gate = OPENING) implies light = FLASHING
		Assert.assertEquals( typeof(BinaryOperationImpl), inv.body.class )
		var e1 = inv.body as BinaryOperationImpl
		Assert.assertEquals( "implies", e1.op )	
		Assert.assertEquals( typeof(BinaryOperationImpl), e1.right.class )
		Assert.assertEquals( typeof(BinaryOperationImpl), e1.left.class )
		// gate = CLOSING or gate = CLOSED or gate = OPENING
		var e1_left = e1.left as BinaryOperationImpl
		Assert.assertEquals( typeof(BinaryOperationImpl), e1_left.left.class )
		//		gate = CLOSING or gate = CLOSED
		var e1_left_left = e1_left.left as BinaryOperationImpl
		Assert.assertEquals( typeof(BinaryOperationImpl), e1_left_left.right.class )
		Assert.assertEquals( typeof(BinaryOperationImpl), e1_left_left.left.class )
		var e1_left_left_left = e1_left_left.left as BinaryOperationImpl
		expected = #["gate", "=", "CLOSING"]
		classes = #[FunctionTermImpl, EnumTermImpl]
		checkExpression( expected, classes, e1_left_left_left )
		var e1_left_left_right = e1_left_left.right as BinaryOperationImpl
		expected = #["gate", "=", "CLOSED"]
		classes = #[FunctionTermImpl, EnumTermImpl]
		checkExpression( expected, classes, e1_left_left_right )
		// 		gate = OPENING
		var e1_left_right = e1_left.right as BinaryOperationImpl
		expected = #["gate", "=", "OPENING"]
		classes = #[FunctionTermImpl, EnumTermImpl]
		checkExpression( expected, classes, e1_left_right )
		// light = FLASHING
		var e1_right = e1.right as BinaryOperationImpl
		expected = #["light", "=", "FLASHING"]
		classes = #[FunctionTermImpl, EnumTermImpl]
		checkExpression( expected, classes, e1_right )
		
		// invariant over gate: gateStatusUpdateOk
		i++
		Assert.assertEquals( typeof(InvariantImpl), result.bodySection.property.get(i).class )	
		inv = result.bodySection.property.get(i) as Invariant
		Assert.assertEquals( null, inv.name )	
		Assert.assertEquals( 1, inv.inv_list.size )	
		Assert.assertEquals( "gate", inv.inv_list.get(0) )	
		Assert.assertEquals( typeof(FunctionTermImpl),  inv.body.class )	
		var e2 = inv.body as FunctionTermImpl
		Assert.assertEquals( "gateStatusUpdateOk", e2.function.name )	
		
		// invariant prova over board, board1: abs(size(chosenCells(NOUGHT)) - size(chosenCells(CROSS))) <= 1 
		i++
		Assert.assertEquals( typeof(InvariantImpl), result.bodySection.property.get(i).class )	
		inv = result.bodySection.property.get(i) as Invariant
		Assert.assertEquals( "prova", inv.name )	
		Assert.assertEquals( 2, inv.inv_list.size )	
		Assert.assertEquals( "board", inv.inv_list.get(0) )	
		Assert.assertEquals( "board1", inv.inv_list.get(1) )
		// abs(size(chosenCells(NOUGHT)) - size(chosenCells(CROSS))) <= 1 
		e1 = inv.body as BinaryOperationImpl
		Assert.assertEquals( "<=", e1.op )	
		// abs( size(chosenCells(NOUGHT)) - size(chosenCells(CROSS)) )
		Assert.assertEquals( typeof(FunctionTermImpl), e1.left.class )
		var func = e1.left as FunctionTermImpl
		Assert.assertEquals( "abs", func.function.name )
		Assert.assertEquals( 1, func.arguments.terms.size )
		Assert.assertEquals( typeof(BinaryOperationImpl), func.arguments.terms.get(0).class )
		var func_term = func.arguments.terms.get(0) as BinaryOperationImpl
		Assert.assertEquals( "-", func_term.op )
		// size(  hosenCells(NOUGHT) )
		Assert.assertEquals( typeof(FunctionTermImpl), func_term.left.class )
		var func_term_left = func_term.left as FunctionTermImpl
		Assert.assertEquals( "size", func_term_left.function.name )
		Assert.assertEquals( 1, func_term_left.arguments.terms.size )
		var func_term_left_term = func_term_left.arguments.terms.get(0) as FunctionTermImpl
		Assert.assertEquals( "chosenCells", func_term_left_term.function.name )
		Assert.assertEquals( 1, func_term_left_term.arguments.terms.size )
		Assert.assertEquals( "NOUGHT", ( func_term_left_term.arguments.terms.get(0) as EnumTermImpl).symbol ) 
		// size(  hosenCells(CROSS) ) -> uguale
		// 1
		Assert.assertEquals( typeof(IntegerTermImpl), e1.right.class )
		Assert.assertEquals( "1", ( e1.right as IntegerTermImpl).symbol )
		
	}
	
	def void checkExpression( String[] expected, Class[] data_type, BinaryOperationImpl espressione ) {

		Assert.assertEquals( data_type.get(0), espressione.left.class )
		Assert.assertEquals( data_type.get(1), espressione.right.class )

		Assert.assertEquals( expected.get(1), espressione.op )	
		if ( data_type.get(0) !== null ) {	
			if (data_type.get(0) == FunctionTermImpl)  
				Assert.assertEquals( expected.get(0), (espressione.left as FunctionTermImpl).function.name )
			else 
				Assert.assertEquals( expected.get(0), (espressione.left as EnumTermImpl).symbol )
		}
		if ( data_type.get(1) !== null ) {
				if (data_type.get(1) == FunctionTermImpl)  
				Assert.assertEquals( expected.get(2), (espressione.right as FunctionTermImpl).function.name )
			else 
				Assert.assertEquals( expected.get(2), (espressione.right as EnumTermImpl).symbol )
		} 

	}*/
	
}