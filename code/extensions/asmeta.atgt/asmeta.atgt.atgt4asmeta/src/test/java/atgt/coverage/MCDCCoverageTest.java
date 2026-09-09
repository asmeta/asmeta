package atgt.coverage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import atgt.specification.statement.ConditionalRule;

import org.junit.jupiter.api.Test;
import atgt.specification.statement.Skip;
import tgtlib.definitions.NamedTerm;

class MCDCCoverageTest extends RuleTest{

	/*	
		oblems with this one (AGO 2010)
	par
		if mode = OFF  			then mode := INACTIVE endif
			if mode = OVERRIDE and not fast and  not brake and (lever = ACTIVATE or lever = RESUME) then
				mode := CRUISE
				endif
	endpar
	*/
	@Test void forDoStatement() {		
		MCDCCoverage mcdc = MCDCCoverage.getCoverage();
		List<NamedTerm> cov = mcdc.forDoStatement(par);
		System.out.println(cov.toString());
		assertEquals(8,cov.size());
		for (NamedTerm tc : cov) {
			System.out.println("tc -> " + tc.toString() + ":" + tc.getCondition());
		}
		assertEquals("[TT1, FT1, TT1, TF1, TF2, FF2, FT2, FF2]",NamedTerm.getNames(cov));
	}

	@Test void forIfThenElseConditionalRule(){
		MCDCCoverage mcdc = MCDCCoverage.getCoverage();
		List<NamedTerm> cov = mcdc.forIfThenElse(if_woelse);
		assertEquals(4,cov.size());
		assertEquals("[TF, FF, FT, FF]",NamedTerm.getNames(cov));
		// with else
		cov = mcdc.forIfThenElse(if_wemptyelse);
		assertEquals(4,cov.size());
		assertEquals("[TT, FT, TT, TF]",NamedTerm.getNames(cov));
	}

	@Test void forNestedConditionalRule(){
		// both simple
		MCDCCoverage mcdc = MCDCCoverage.getCoverage();
		ConditionalRule c_in = new ConditionalRule(A, Skip.SKIP);
		ConditionalRule c_out = new ConditionalRule(B, c_in);
		List<NamedTerm> cov = c_out.accept(mcdc);
		assertEquals(4,cov.size());
		assertEquals("[T, F, T_T, T_F]",NamedTerm.getNames(cov));
		assertEquals("B", cov.get(0).getCondition().toString());		
		assertEquals("not B", cov.get(1).getCondition().toString());		
		assertEquals("B and A", cov.get(2).getCondition().toString());		
		assertEquals("B and not A", cov.get(3).getCondition().toString());		
		// nested complex
		// with an and in c_in
		c_in = new ConditionalRule(aANDb, Skip.SKIP);
		c_out = new ConditionalRule(B, c_in);
		cov = mcdc.forIfThenElse(c_out);
		assertEquals(6,cov.size());
		assertEquals("[T, F, T_TT, T_FT, T_TT, T_TF]",NamedTerm.getNames(cov));
		assertEquals("B", cov.get(0).getCondition().toString());		
		assertEquals("not B", cov.get(1).getCondition().toString());		
		assertEquals("B and (A and B)", cov.get(2).getCondition().toString());		
		assertEquals("B and (not A and B)", cov.get(3).getCondition().toString());		
		assertEquals("B and (A and B)", cov.get(4).getCondition().toString());		
		assertEquals("B and (A and not B)", cov.get(5).getCondition().toString());
		// with an or in c_out
		c_in = new ConditionalRule(B, Skip.SKIP);
		c_out = new ConditionalRule(aORb, c_in);
		cov = mcdc.forIfThenElse(c_out);
		assertEquals(6,cov.size());
		assertEquals("[TF, FF, FT, FF, T_T, T_F]",NamedTerm.getNames(cov));
		assertEquals("A and not B", cov.get(0).getCondition().toString());		
		assertEquals("not A and not B", cov.get(1).getCondition().toString());		
		assertEquals("not A and B", cov.get(2).getCondition().toString());		
		assertEquals("not A and not B", cov.get(3).getCondition().toString());		
		assertEquals("(A or B) and B", cov.get(4).getCondition().toString());		
		assertEquals("(A or B) and not B", cov.get(5).getCondition().toString());
		// with else
		c_in = new ConditionalRule(A, Skip.SKIP);
		c_out = new ConditionalRule(B, Skip.SKIP,c_in);
		cov = mcdc.forIfThenElse(c_out);
		assertEquals(4,cov.size());
		assertEquals("[T, F, F_T, F_F]",NamedTerm.getNames(cov));
	}
}
