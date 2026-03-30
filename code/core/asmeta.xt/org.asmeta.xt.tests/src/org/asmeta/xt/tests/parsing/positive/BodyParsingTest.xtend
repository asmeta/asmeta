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
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.asmeta.xt.tests.AsmParseHelper
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.junit.jupiter.api.^extension.ExtendWith
import org.junit.jupiter.api.Tag

@ExtendWith(InjectionExtension)
@InjectWith(AsmetaLInjectorProvider)
class BodyParsingTest {
	
	@Inject	AsmParseHelper parseHelper
	@Inject extension ValidationTestHelper
	
	@Test@Tag("TestToMavenSkip")
	def void testDomainFunctionDefinitions() {
		
		var result = parseHelper.parse('''
			asm domain_function
			
			//import StandardLibrary(Integer, Any)
			
			signature:
				basic domain Integer
				anydomain Any
	
				abstract domain Philosopher
				domain NumOfCherries subsetof Integer
			
				static leftNeighbour: Philosopher -> Philosopher
				static rightNeighbour: Philosopher -> Philosopher
				
				static philo1: Philosopher
				static philo2: Philosopher
				static philo3: Philosopher
				static philo4: Philosopher
				static philo5: Philosopher
			
			definitions:
				domain NumOfCherries = {0 : 3}
			
				function leftNeighbour($p in Philosopher) =
					switch($p)
						case philo1: philo5
						case philo2: philo1
						case philo3: philo2
						case philo4: philo3
						case philo5: philo4
					endswitch
		''',"domain_function")
		result.assertNoErrors
		
		// TODO manca controllo della rule declaration
		
		var i = 2
		
		// SIGNATURE
		
		Assertions.assertEquals( 4, result.headerSection.signature.domain.size)	
		Assertions.assertEquals( 7, result.headerSection.signature.function.size)
		
		// abstract domain Philosopher
		var abstr_dom = result.headerSection.signature.domain.get(i)
		Assertions.assertEquals( "Philosopher", abstr_dom.name )
		Assertions.assertEquals( typeof(AbstractTDImpl), abstr_dom.class )
		
		// domain NumOfCherries subsetof Integer
		i++
		var conc_dom = result.headerSection.signature.domain.get(i)
		Assertions.assertEquals( "NumOfCherries", conc_dom.name )
		Assertions.assertEquals( typeof(ConcreteDomainImpl), conc_dom.class )
		// Integer
		Assertions.assertEquals( "Integer", (conc_dom as ConcreteDomainImpl).typeDomain.name )
	
		i = 0
		var Function func 
		
		//static leftNeighbour: Philosopher -> Philosopher
		func = result.headerSection.signature.function.get(i)
		Assertions.assertEquals( typeof(DerivedFunctionImpl), func.class )
		Assertions.assertEquals( "leftNeighbour", func.name )
		Assertions.assertEquals( "Philosopher", func.codomain.name )
		Assertions.assertEquals( "Philosopher", func.domain.name )
		
		// static rightNeighbour: Philosopher -> Philosopher
		i++
		func = result.headerSection.signature.function.get(i)
		Assertions.assertEquals( typeof(DerivedFunctionImpl), func.class )
		Assertions.assertEquals( "rightNeighbour", func.name )
		Assertions.assertEquals( "Philosopher", func.codomain.name )
		Assertions.assertEquals( "Philosopher", func.domain.name )
		
		// static philo1: Philosopher
		i++
		func = result.headerSection.signature.function.get(i)
		Assertions.assertEquals( typeof(StaticFunctionImpl), func.class )
		Assertions.assertEquals( "philo1", func.name )
		Assertions.assertEquals( "Philosopher", func.codomain.name )
		Assertions.assertEquals( null, func.domain )
		
		// static philo2: Philosopher
		i++
		func = result.headerSection.signature.function.get(i)
		Assertions.assertEquals( typeof(StaticFunctionImpl), func.class )
		Assertions.assertEquals( "philo2", func.name )
		Assertions.assertEquals( "Philosopher", func.codomain.name )
		Assertions.assertEquals( null, func.domain )
		
		// static philo3: Philosopher
		i++
		func = result.headerSection.signature.function.get(i)
		Assertions.assertEquals( typeof(StaticFunctionImpl), func.class )
		Assertions.assertEquals( "philo3", func.name )
		Assertions.assertEquals( "Philosopher", func.codomain.name )
		Assertions.assertEquals( null, func.domain )
		
		// static philo4: Philosopher
		i++
		func = result.headerSection.signature.function.get(i)
		Assertions.assertEquals( typeof(StaticFunctionImpl), func.class )
		Assertions.assertEquals( "philo4", func.name )
		Assertions.assertEquals( "Philosopher", func.codomain.name )
		Assertions.assertEquals( null, func.domain )
		
		// static philo5: Philosopher
		i++
		func = result.headerSection.signature.function.get(i)
		Assertions.assertEquals( typeof(StaticFunctionImpl), func.class )
		Assertions.assertEquals( "philo5", func.name )
		Assertions.assertEquals( "Philosopher", func.codomain.name )
		Assertions.assertEquals( null, func.domain )
		
		// BODY

		Assertions.assertEquals( 1, result.bodySection.domainDefinition.size )
		Assertions.assertEquals( 1, result.bodySection.functionDefinition.size )	
		
		i = 0
		// domain NumOfCherries = {0 .. 3}
		var dom_def = result.bodySection.domainDefinition.get(0)
		Assertions.assertEquals( "NumOfCherries", dom_def.definedDomainName )
		Assertions.assertEquals( typeof(SetTermImpl), dom_def.body.class )
		// {0 .. 3}
		var seq_term = dom_def.body as SetTermImpl
		Assertions.assertEquals( typeof(IntegerTermImpl), seq_term.start_term.class )
		/*var val_term = seq_term.start_term as IntegerTermImpl
		Assertions.assertEquals( "0", val_term.symbol )*/
		Assertions.assertEquals( typeof(IntegerTermImpl), seq_term.end_term.class )
		var val_term = seq_term.end_term as IntegerTermImpl
		Assertions.assertEquals( "3", val_term.symbol )

		i = 0
		// function leftNeighbour($p in Philosopher) = ...
		var func_def = result.bodySection.functionDefinition.get(i)
		Assertions.assertEquals( "leftNeighbour", func_def.definedFunctionName )
		Assertions.assertEquals( 1, func_def.variables.size )
		Assertions.assertEquals( "$p", func_def.variables.get(0) )
		Assertions.assertEquals( 1, func_def.domain.size )
		Assertions.assertEquals( "Philosopher", func_def.domain.get(0).name )
		// switch($p)
		Assertions.assertEquals( typeof(CaseTermImpl), func_def.body.class )
		var switch_term = func_def.body as CaseTermImpl

		Assertions.assertEquals( typeof(VariableTermImpl), switch_term.comparedTerm.class )
		Assertions.assertEquals( "$p", (switch_term.comparedTerm as VariableTermImpl).name )
		Assertions.assertEquals( 5, switch_term.comparingTerm.size )
		Assertions.assertEquals( 5, switch_term.resultTerms.size )
		Assertions.assertEquals( null, switch_term.otherwiseTerm )
		// case philo1: philo5
		Assertions.assertEquals( typeof(FunctionTermImpl), switch_term.comparingTerm.get(0).class )
		var func_term = switch_term.comparingTerm.get(0) as FunctionTermImpl
		Assertions.assertEquals( "philo1", func_term.functionName )
		Assertions.assertEquals( typeof(FunctionTermImpl), switch_term.comparingTerm.get(0).class )
		func_term = switch_term.resultTerms.get(0) as FunctionTermImpl
		Assertions.assertEquals( "philo5", func_term.functionName )

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
			''', "invariant_asm")
		result.assertNoErrors
		
		var i = 0
		
		Assertions.assertEquals( 1, result.bodySection.property.size )	
		
		// --- invariant over position: position(GO)=position(CA) implies position(GO)=position(FERRYMAN)
		// invariant over positio
		Assertions.assertEquals( typeof(InvariantImpl), result.bodySection.property.get(i).class )	
		var inv = result.bodySection.property.get(i) as Invariant
		Assertions.assertEquals( null, inv.name )	
		Assertions.assertEquals( 1, inv.invar_list.size )	
		Assertions.assertEquals( "position", inv.invar_list.get(0).constrainedName )	
		// position(GO)=position(CA) implies position(GO)=position(FERRYMAN)
		var e1 = inv.body as BinaryOperationImpl
		Assertions.assertEquals( "implies", e1.op )	
		Assertions.assertEquals( typeof(BinaryOperationImpl), e1.right.class )
		Assertions.assertEquals( typeof(BinaryOperationImpl), e1.left.class )
		// left 	=> position(GO)=position(CA)
		var e1_left = e1.left as BinaryOperationImpl
		Assertions.assertEquals( "=", e1_left.op )
		Assertions.assertEquals( typeof(FunctionTermImpl), e1_left.left.class )
		var func = e1_left.left as FunctionTermImpl
		Assertions.assertEquals( "position", func.functionName )
		Assertions.assertEquals( 1, func.arguments.terms.size )
		Assertions.assertEquals( typeof(EnumTermImpl), func.arguments.terms.get(0).class )
		var enum = func.arguments.terms.get(0) as EnumTermImpl
		Assertions.assertEquals( "GO", enum.symbol )
		func = e1_left.right as FunctionTermImpl
		Assertions.assertEquals( "position", func.functionName )
		Assertions.assertEquals( 1, func.arguments.terms.size )
		Assertions.assertEquals( typeof(EnumTermImpl), func.arguments.terms.get(0).class )
		enum = func.arguments.terms.get(0) as EnumTermImpl
		Assertions.assertEquals( "CA", enum.symbol )
		// right 	=> position(GO)=position(FERRYMAN)
		var e1_right = e1.right as BinaryOperationImpl
		Assertions.assertEquals( "=", e1_right.op )
		Assertions.assertEquals( typeof(FunctionTermImpl), e1_left.left.class )
		func = e1_right.left as FunctionTermImpl
		Assertions.assertEquals( "position", func.functionName )
		Assertions.assertEquals( 1, func.arguments.terms.size )
		Assertions.assertEquals( typeof(EnumTermImpl), func.arguments.terms.get(0).class )
		enum = func.arguments.terms.get(0) as EnumTermImpl
		Assertions.assertEquals( "GO", enum.symbol )
		func = e1_right.right as FunctionTermImpl
		Assertions.assertEquals( "position", func.functionName )
		Assertions.assertEquals( 1, func.arguments.terms.size )
		Assertions.assertEquals( typeof(EnumTermImpl), func.arguments.terms.get(0).class )
		enum = func.arguments.terms.get(0) as EnumTermImpl
		Assertions.assertEquals( "FERRYMAN", enum.symbol )
		
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
		
		Assertions.assertEquals( 2, result.bodySection.property.size )	
		
		// LTLSPEC g( not( (ccState(a1, 1) = EXCLUSIVE) and (ccState(a2, 1)=EXCLUSIVE) ) )
		var spec = result.bodySection.property.get(i) 
		Assertions.assertEquals( null, spec.name )
		Assertions.assertEquals( typeof(LtlSpecImpl), spec.class )
		Assertions.assertEquals( typeof(FunctionTermImpl), spec.body.class )
		var func = spec.body as FunctionTermImpl
		// g( not( (ccState(a1, 1) = EXCLUSIVE) and (ccState(a2, 1)=EXCLUSIVE) ) )
		// ^
		Assertions.assertEquals( "g", func.functionName )
		Assertions.assertEquals( 1, func.arguments.terms.size )
		Assertions.assertEquals( typeof(ExpressionImpl), func.arguments.terms.get(0).class )
		// g( not( (ccState(a1, 1) = EXCLUSIVE) and (ccState(a2, 1)=EXCLUSIVE) ) )
		//     ^
		var expr =  func.arguments.terms.get(0) as Expression
		Assertions.assertEquals( "not", expr.op )
		Assertions.assertEquals( typeof(BinaryOperationImpl), expr.operand.class )
		// g( not( (ccState(a1, 1) = EXCLUSIVE) and (ccState(a2, 1)=EXCLUSIVE) ) )
		//                                       ^
		var func_bin = expr.operand as BinaryOperationImpl
		Assertions.assertEquals( "and", func_bin.op )
		Assertions.assertEquals( typeof(BinaryOperationImpl), func_bin.left.class )
		Assertions.assertEquals( typeof(BinaryOperationImpl), func_bin.right.class )
		// (ccState(a1, 1) = EXCLUSIVE)
		var func_bin_left = func_bin.left as BinaryOperationImpl
		Assertions.assertEquals( "=", func_bin_left.op )
		Assertions.assertEquals( typeof(FunctionTermImpl), func_bin_left.left.class )
		Assertions.assertEquals( typeof(EnumTermImpl), func_bin_left.right.class )
		// ccState(a1, 1)
		var func_bin_left_left = func_bin_left.left as FunctionTermImpl
		// ccState(a1, 1)
		Assertions.assertEquals( "ccState", func_bin_left_left.functionName )
		Assertions.assertEquals( 2, func_bin_left_left.arguments.terms.size )
		Assertions.assertEquals( typeof(EnumTermImpl), func_bin_left.right.class )
		// a1
		Assertions.assertEquals( typeof(FunctionTermImpl), func_bin_left_left.arguments.terms.get(0).class )	
		func = func_bin_left_left.arguments.terms.get(0) as FunctionTermImpl
		Assertions.assertEquals( "a1", func.functionName )		
		// 1
		Assertions.assertEquals( typeof(IntegerTermImpl), func_bin_left_left.arguments.terms.get(1).class )
		var number = func_bin_left_left.arguments.terms.get(1) as IntegerTermImpl
		Assertions.assertEquals( "1", number.symbol )
		// EXCLUSIVE
		var func_bin_left_right = func_bin_left.right as EnumTermImpl
		Assertions.assertEquals( "EXCLUSIVE", func_bin_left_right.symbol )
		// ccState(a2, 1)=EXCLUSIVE
		var func_bin_right = func_bin.right as BinaryOperationImpl
		Assertions.assertEquals( "=", func_bin_right.op )
		Assertions.assertEquals( typeof(FunctionTermImpl), func_bin_right.left.class )
		Assertions.assertEquals( typeof(EnumTermImpl), func_bin_right.right.class )
		// ccState(a1, 1)
		var func_bin_right_left = func_bin_right.left as FunctionTermImpl
		// ccState(a1, 1)
		Assertions.assertEquals( "ccState", func_bin_right_left.functionName )
		Assertions.assertEquals( 2, func_bin_right_left.arguments.terms.size )
		Assertions.assertEquals( typeof(EnumTermImpl), func_bin_right.right.class )
		// a2
		Assertions.assertEquals( typeof(FunctionTermImpl), func_bin_right_left.arguments.terms.get(0).class )	
		func = func_bin_right_left.arguments.terms.get(0) as FunctionTermImpl
		Assertions.assertEquals( "a2", func.functionName )		
		// 1
		Assertions.assertEquals( typeof(IntegerTermImpl), func_bin_right_left.arguments.terms.get(1).class )
		number = func_bin_right_left.arguments.terms.get(1) as IntegerTermImpl
		Assertions.assertEquals( "1", number.symbol )
		// EXCLUSIVE
		var func_bin_right_right = func_bin_right.right as EnumTermImpl
		Assertions.assertEquals( "EXCLUSIVE", func_bin_right_right.symbol )
		
		// CTLSPEC ag(ccState(a1, 1)=INVALID and ccState(a2, 1)=INVALID)
		i++
		spec = result.bodySection.property.get(i) 
		Assertions.assertEquals( null, spec.name )
		Assertions.assertEquals( typeof(CtlSpecImpl), spec.class )

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
		''',"fairness")
		result.assertNoErrors	
	
		var i = 0
	
		Assertions.assertEquals( 1, result.bodySection.invariantConstraint.size )	
		Assertions.assertEquals( 2, result.bodySection.fairnessConstraint.size )	
		
		Assertions.assertEquals( typeof(InvariantConstraintImpl), result.bodySection.invariantConstraint.get(i).class )
		var invar = result.bodySection.invariantConstraint.get(i) as InvariantConstraintImpl
		
		i = 0
		Assertions.assertEquals( typeof(JusticeConstraintImpl), result.bodySection.fairnessConstraint.get(i).class )
		var justice = result.bodySection.fairnessConstraint.get(i) as JusticeConstraintImpl
			
		i++
		Assertions.assertEquals( typeof(CompassionConstraintImpl), result.bodySection.fairnessConstraint.get(i).class )
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
		
		Assertions.assertEquals( 3, result.bodySection.property.size )	
		
		// --- invariant over gate: (gate = CLOSING or gate = CLOSED or gate = OPENING) implies light = FLASHING
		Assertions.assertEquals( typeof(InvariantImpl), result.bodySection.property.get(i).class )	
		var inv = result.bodySection.property.get(i) as Invariant
		Assertions.assertEquals( null, inv.name )	
		Assertions.assertEquals( 1, inv.inv_list.size )	
		Assertions.assertEquals( "gate", inv.inv_list.get(0) )	
		// -- (gate = CLOSING or gate = CLOSED or gate = OPENING) implies light = FLASHING
		Assertions.assertEquals( typeof(BinaryOperationImpl), inv.body.class )
		var e1 = inv.body as BinaryOperationImpl
		Assertions.assertEquals( "implies", e1.op )	
		Assertions.assertEquals( typeof(BinaryOperationImpl), e1.right.class )
		Assertions.assertEquals( typeof(BinaryOperationImpl), e1.left.class )
		// gate = CLOSING or gate = CLOSED or gate = OPENING
		var e1_left = e1.left as BinaryOperationImpl
		Assertions.assertEquals( typeof(BinaryOperationImpl), e1_left.left.class )
		//		gate = CLOSING or gate = CLOSED
		var e1_left_left = e1_left.left as BinaryOperationImpl
		Assertions.assertEquals( typeof(BinaryOperationImpl), e1_left_left.right.class )
		Assertions.assertEquals( typeof(BinaryOperationImpl), e1_left_left.left.class )
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
		Assertions.assertEquals( typeof(InvariantImpl), result.bodySection.property.get(i).class )	
		inv = result.bodySection.property.get(i) as Invariant
		Assertions.assertEquals( null, inv.name )	
		Assertions.assertEquals( 1, inv.inv_list.size )	
		Assertions.assertEquals( "gate", inv.inv_list.get(0) )	
		Assertions.assertEquals( typeof(FunctionTermImpl),  inv.body.class )	
		var e2 = inv.body as FunctionTermImpl
		Assertions.assertEquals( "gateStatusUpdateOk", e2.function.name )	
		
		// invariant prova over board, board1: abs(size(chosenCells(NOUGHT)) - size(chosenCells(CROSS))) <= 1 
		i++
		Assertions.assertEquals( typeof(InvariantImpl), result.bodySection.property.get(i).class )	
		inv = result.bodySection.property.get(i) as Invariant
		Assertions.assertEquals( "prova", inv.name )	
		Assertions.assertEquals( 2, inv.inv_list.size )	
		Assertions.assertEquals( "board", inv.inv_list.get(0) )	
		Assertions.assertEquals( "board1", inv.inv_list.get(1) )
		// abs(size(chosenCells(NOUGHT)) - size(chosenCells(CROSS))) <= 1 
		e1 = inv.body as BinaryOperationImpl
		Assertions.assertEquals( "<=", e1.op )	
		// abs( size(chosenCells(NOUGHT)) - size(chosenCells(CROSS)) )
		Assertions.assertEquals( typeof(FunctionTermImpl), e1.left.class )
		var func = e1.left as FunctionTermImpl
		Assertions.assertEquals( "abs", func.function.name )
		Assertions.assertEquals( 1, func.arguments.terms.size )
		Assertions.assertEquals( typeof(BinaryOperationImpl), func.arguments.terms.get(0).class )
		var func_term = func.arguments.terms.get(0) as BinaryOperationImpl
		Assertions.assertEquals( "-", func_term.op )
		// size(  hosenCells(NOUGHT) )
		Assertions.assertEquals( typeof(FunctionTermImpl), func_term.left.class )
		var func_term_left = func_term.left as FunctionTermImpl
		Assertions.assertEquals( "size", func_term_left.function.name )
		Assertions.assertEquals( 1, func_term_left.arguments.terms.size )
		var func_term_left_term = func_term_left.arguments.terms.get(0) as FunctionTermImpl
		Assertions.assertEquals( "chosenCells", func_term_left_term.function.name )
		Assertions.assertEquals( 1, func_term_left_term.arguments.terms.size )
		Assertions.assertEquals( "NOUGHT", ( func_term_left_term.arguments.terms.get(0) as EnumTermImpl).symbol ) 
		// size(  hosenCells(CROSS) ) -> uguale
		// 1
		Assertions.assertEquals( typeof(IntegerTermImpl), e1.right.class )
		Assertions.assertEquals( "1", ( e1.right as IntegerTermImpl).symbol )
		
	}
	
	def void checkExpression( String[] expected, Class[] data_type, BinaryOperationImpl espressione ) {

		Assertions.assertEquals( data_type.get(0), espressione.left.class )
		Assertions.assertEquals( data_type.get(1), espressione.right.class )

		Assertions.assertEquals( expected.get(1), espressione.op )	
		if ( data_type.get(0) !== null ) {	
			if (data_type.get(0) == FunctionTermImpl)  
				Assertions.assertEquals( expected.get(0), (espressione.left as FunctionTermImpl).function.name )
			else 
				Assertions.assertEquals( expected.get(0), (espressione.left as EnumTermImpl).symbol )
		}
		if ( data_type.get(1) !== null ) {
				if (data_type.get(1) == FunctionTermImpl)  
				Assertions.assertEquals( expected.get(2), (espressione.right as FunctionTermImpl).function.name )
			else 
				Assertions.assertEquals( expected.get(2), (espressione.right as EnumTermImpl).symbol )
		} 

	}*/
	
}