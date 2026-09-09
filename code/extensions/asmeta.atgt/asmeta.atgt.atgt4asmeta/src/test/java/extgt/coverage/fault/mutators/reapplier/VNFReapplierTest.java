package extgt.coverage.fault.mutators.reapplier;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import tgtlib.definitions.expression.Expression;

import org.junit.jupiter.api.Test;
import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.definitions.expression.parser.ParseException;
import tgtlib.util.Pair;
import extgt.coverage.fault.mutators.ExpressionMutator;
import extgt.coverage.fault.mutators.foms.AssociativeShiftFault;
import extgt.coverage.fault.mutators.foms.VariableNegationFault;
import extgt.coverage.fault.mutators.foms.VariableReferenceFault;

class VNFReapplierTest {

	static MutationReapplier vnfReappl = new MutationReapplier();

	@Test void reapply() throws Exception {		
		testGenAndReapply("not a");
		testGenAndReapply("a and b");
		testGenAndReapply("not a");
		testGenAndReapply("a or b");
		testGenAndReapply("not b or c");
		testGenAndReapply("a and (not b or c)");
		// asf
		testGenAndReapply("a and (b or c)");
		testGenAndReapply("not (a and (b or c))");
	}

	private void testGenAndReapply(String string) throws ParseException {
		Expression e = ExpressionParser.parseAsNewBooleanExpression(string);
		// for VNF  
		ExpressionMutator vnf = VariableNegationFault.VNF.getExpressionMutator();
		checkExprMuts(e, vnf);
		// VRF
		ExpressionMutator vrf = VariableReferenceFault.VRF.getExpressionMutator(e);
		checkExprMuts(e, vrf);	
		// ASF
		ExpressionMutator asf = AssociativeShiftFault.ASF.getExpressionMutator();
		checkExprMuts(e, asf);	
		
	}

	private void checkExprMuts(Expression e, ExpressionMutator vnf) {
		List<Pair<Integer, Expression>> muts = vnf.getMutations(e);
		for(Pair<Integer, Expression> mut:muts){
			// 
			System.out.println(mut.toString());
			Expression reapp = MutationReapplier.reapply(mut, e);
			System.out.println(reapp.toString());
			assertEquals(mut.getSecond(), reapp);
		}
	}

	
	
	
}
