package org.asmeta.nusmv.util;

import static org.asmeta.nusmv.util.Util.and;
import static org.asmeta.nusmv.util.Util.falseString;
import static org.asmeta.nusmv.util.Util.hasFirstLastPars;
import static org.asmeta.nusmv.util.Util.iff;
import static org.asmeta.nusmv.util.Util.implies;
import static org.asmeta.nusmv.util.Util.isEnum;
import static org.asmeta.nusmv.util.Util.isNumber;
import static org.asmeta.nusmv.util.Util.not;
import static org.asmeta.nusmv.util.Util.notEquals;
import static org.asmeta.nusmv.util.Util.or;
import static org.asmeta.nusmv.util.Util.printFormatted;
import static org.asmeta.nusmv.util.Util.setPars;
import static org.asmeta.nusmv.util.Util.simplify;
import static org.asmeta.nusmv.util.Util.trueString;
import static org.asmeta.nusmv.util.Util.xor;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class UtilTest {

	@Test
	public void orTest() {
		Util.simplify = true;
		List<String> conds = new ArrayList<String>();
		conds.add(Util.falseString);
		conds.add("b");
		conds.add(Util.falseString);
		String or = Util.or(conds);

		assertEquals("b", or);
	}

	@Test
	public void orTest1() {
		Util.simplify = true;
		List<String> conds = new ArrayList<String>();
		conds.add(trueString);
		conds.add("b");
		conds.add(falseString);
		assertEquals(trueString, or(conds));
	}

	@Test
	public void orTest2() {
		simplify = true;
		List<String> conds = new ArrayList<String>();
		conds.add(falseString);
		conds.add(falseString);
		conds.add(falseString);
		assertEquals(falseString, or(conds));
	}

	@Test
	public void orTest3() {
		simplify = true;
		List<String> conds = new ArrayList<String>();
		conds.add("b");
		conds.add(falseString);
		conds.add("foo_AA");
		assertEquals("(b | foo_AA)", or(conds));
	}

	@Test
	public void orTest4() {
		simplify = true;
		List<String> conds = new ArrayList<String>();
		conds.add("b");
		conds.add(falseString);
		conds.add("foo_AA");
		conds.add("foo_BB");
		assertEquals("(b | foo_AA | foo_BB)", or(conds));
	}

	@Test
	public void orTest5() {
		simplify = true;
		assertEquals(falseString, or(falseString, falseString));
		assertEquals(trueString, or(trueString, falseString));
		assertEquals(trueString, or(falseString, trueString));
		assertEquals(trueString, or(trueString, trueString));
		assertEquals("foo", or(falseString, "foo"));
		assertEquals("foo", or("foo", falseString));
		assertEquals(trueString, or(trueString, "foo"));
		assertEquals(trueString, or("foo", trueString));
		assertEquals("(foo | fooA)", or("foo", "fooA"));
		assertEquals(trueString, or("!(foo)", "foo"));
	}

	@Test
	public void xorTest() throws Exception {
		simplify = true;
		assertEquals(falseString, xor(falseString, falseString));
		assertEquals(trueString, xor(trueString, falseString));
		assertEquals(trueString, xor(falseString, trueString));
		assertEquals(falseString, xor(trueString, trueString));

		assertEquals("condB", xor(falseString, "condB"));
		assertEquals("!(condB)", xor(trueString, "condB"));
		assertEquals("condB", xor("condB", falseString));
		assertEquals("!(condB)", xor("condB", trueString));

		List<String> conds = new ArrayList<String>();
		conds.add(falseString);
		conds.add(falseString);
		conds.add(falseString);
		assertEquals(falseString, xor(conds));
		conds.clear();
		conds.add(falseString);
		conds.add(falseString);
		conds.add(trueString);
		assertEquals(trueString, xor(conds));
		conds.clear();
		conds.add(falseString);
		conds.add(trueString);
		conds.add(trueString);
		assertEquals(falseString, xor(conds));

		conds.clear();
		conds.add(trueString);
		conds.add(trueString);
		conds.add(trueString);
		assertEquals(trueString, xor(conds));

		conds.clear();
		conds.add(falseString);
		conds.add("condB");
		assertEquals("condB", xor(conds));

		conds.clear();
		conds.add(trueString);
		conds.add("condB");
		assertEquals("!(condB)", xor(conds));

		conds.clear();
		conds.add(trueString);
		conds.add(falseString);
		conds.add("condB");
		assertEquals("!(condB)", xor(conds));

		conds.clear();
		conds.add(falseString);
		conds.add(trueString);
		conds.add("condB");
		assertEquals("!(condB)", xor(conds));

		conds.clear();
		conds.add(falseString);
		conds.add(falseString);
		conds.add("condB");
		assertEquals("condB", xor(conds));

		conds.clear();
		conds.add(trueString);
		conds.add(trueString);
		conds.add("condB");
		assertEquals("condB", xor(conds));

		conds.clear();
		conds.add(trueString);
		conds.add(trueString);
		conds.add(falseString);
		conds.add("condB");
		assertEquals("condB", xor(conds));

		conds.clear();
		conds.add(trueString);
		conds.add(falseString);
		conds.add(falseString);
		conds.add("condB");
		assertEquals("!(condB)", xor(conds));

		conds.clear();
		conds.add(falseString);
		conds.add(falseString);
		conds.add(falseString);
		conds.add("condB");
		assertEquals("condB", xor(conds));

		conds.clear();
		conds.add(trueString);
		conds.add(trueString);
		conds.add(trueString);
		conds.add("condB");
		assertEquals("!(condB)", xor(conds));

		conds.clear();
		conds.add(trueString);
		conds.add(trueString);
		conds.add(trueString);
		conds.add(trueString);
		assertEquals(falseString, xor(conds));

		conds.clear();
		conds.add(trueString);
		conds.add(trueString);
		conds.add(trueString);
		conds.add(falseString);
		assertEquals(trueString, xor(conds));

		conds.clear();
		conds.add(trueString);
		conds.add(trueString);
		conds.add(falseString);
		conds.add(falseString);
		assertEquals(falseString, xor(conds));

		conds.clear();
		conds.add(trueString);
		conds.add(falseString);
		conds.add(falseString);
		conds.add(falseString);
		assertEquals(trueString, xor(conds));

		conds.clear();
		conds.add(falseString);
		conds.add(falseString);
		conds.add(falseString);
		conds.add(falseString);
		assertEquals(falseString, xor(conds));

		conds.clear();
		conds.add(trueString);
		conds.add(trueString);
		conds.add(trueString);
		conds.add(trueString);
		conds.add("condB");
		assertEquals("condB", xor(conds));

		conds.clear();
		conds.add(trueString);
		conds.add(trueString);
		conds.add(trueString);
		conds.add(falseString);
		conds.add("condB");
		assertEquals("!(condB)", xor(conds));

		conds.clear();
		conds.add(trueString);
		conds.add(trueString);
		conds.add(falseString);
		conds.add(falseString);
		conds.add("condB");
		assertEquals("condB", xor(conds));

		conds.clear();
		conds.add(trueString);
		conds.add(falseString);
		conds.add(falseString);
		conds.add(falseString);
		conds.add("condB");
		assertEquals("!(condB)", xor(conds));

		conds.clear();
		conds.add(falseString);
		conds.add(falseString);
		conds.add(falseString);
		conds.add(falseString);
		conds.add("condB");
		assertEquals("condB", xor(conds));

		conds.clear();
		conds.add(trueString);
		conds.add(falseString);
		conds.add(falseString);
		conds.add(falseString);
		conds.add("condB");
		conds.add("condC");
		assertEquals("(!(condB) xor condC)", xor(conds));

		conds.clear();
		conds.add(falseString);
		conds.add(falseString);
		conds.add(falseString);
		conds.add(falseString);
		conds.add("condB");
		conds.add("condC");
		assertEquals("(condB xor condC)", xor(conds));
	}

	@Test
	public void andTest1() {
		simplify = true;
		List<String> conds = new ArrayList<String>();
		conds.add(trueString);
		conds.add(falseString);
		assertEquals(falseString, and(conds));
	}

	@Test
	public void andTest2() {
		simplify = true;
		List<String> conds = new ArrayList<String>();
		conds.add(trueString);
		conds.add(trueString);
		conds.add(trueString);
		assertEquals(trueString, and(conds));
	}

	@Test
	public void andTest3() {
		simplify = true;
		List<String> conds = new ArrayList<String>();
		conds.add("b");
		conds.add(falseString);
		conds.add("foo_AA");
		assertEquals(falseString, and(conds));
	}

	@Test
	public void andTest4() {
		simplify = true;
		assertEquals(falseString, and(falseString, falseString));
		assertEquals(falseString, and(trueString, falseString));
		assertEquals(falseString, and(falseString, trueString));
		assertEquals(trueString, and(trueString, trueString));
		assertEquals(falseString, and(falseString, "foo"));
		assertEquals(falseString, and("foo", falseString));
		assertEquals("foo", and(trueString, "foo"));
		assertEquals("foo", and("foo", trueString));
		assertEquals("(foo & fooA)", and("foo", "fooA"));
		assertEquals(falseString, and("foo", "!(foo)"));
	}

	@Test
	public void notTest() {
		simplify = true;
		assertEquals(falseString, not(trueString));
		assertEquals(trueString, not(falseString));
		assertEquals(trueString, not(not(trueString)));
		assertEquals(falseString, not(not(falseString)));
		assertEquals(falseString, not(not(not(trueString))));
		assertEquals(trueString, not(not(not(falseString))));
		assertEquals("!(foo_AA)", not("foo_AA"));
		assertEquals("foo_AA", not("!(foo_AA)"));
		assertEquals("foo_AA", not(not("foo_AA")));
		assertEquals("!(foo_AA)", not(not(not("foo_AA"))));
		assertEquals("foo_AA", not(not(not(not("foo_AA")))));
	}

	//il metodo removeNext non e' piu' necessario 
	/*@Test
	public void removeNextTest() {
		assertEquals("foo || foo_AA && foo", removeNext("foo || next(foo_AA) && next(foo)"));
		assertEquals("foo || (foo_AA) && foo", removeNext("foo || (next(foo_AA)) && next(foo)"));
		assertEquals(
		"(((state_CL1 = TRAVELLING) & (travelLength_CL1 = 1)) & ((state_CL1 = IDLE) & (calltaxi_CL1))) -> ((IDLE) = (CALLTAXI))",
		removeNext("(((state_CL1 = TRAVELLING) & (travelLength_CL1 = 1)) & ((state_CL1 = IDLE) & (next(calltaxi_CL1)))) -> ((IDLE) = (CALLTAXI))"));
		assertEquals("isnext(foo)", removeNext("isnext(foo)"));
		assertEquals("foo", removeNext("next(foo)"));
		assertEquals("(foo)", removeNext("(next(foo))"));
		assertEquals(" foo", removeNext(" next(foo)"));
		assertEquals(" (foo(AA))", removeNext(" next((foo(AA)))"));
		assertEquals(" (foo(AA or isnext(GT2) ))", removeNext(" next((foo(AA or isnext(GT2) )))"));
	}*/

	@Test
	public void setParenthesesTest() {
		assertEquals("(foo)", setPars("foo"));
		assertEquals("(foo and fooA )", setPars("foo and fooA "));
		assertEquals("(foo)", setPars("(foo)"));
		assertEquals("(foo and (fooG))", setPars("(foo and (fooG))"));
	}
	
	@Test
	public void setParenthesesTest2() {
		assertEquals("((a) -> (b))", setPars(implies("(a)", "(b)")));
		assertEquals("(((a) & (b)) -> (b))", setPars(implies("((a) & (b))", "(b)")));
	}
	
	@Test
	public void equalsTest() {
		simplify = true;
		assertEquals("(a = b)", Util.equals("a", "b"));
		assertEquals(trueString, Util.equals("a", "a"));
		assertEquals(trueString, Util.equals("AA", "AA"));
		assertEquals(falseString, Util.equals("AA", "BB"));
		assertEquals("(AA = bb)", Util.equals("AA", "bb"));
		assertEquals(trueString, Util.equals("1", "1"));
		assertEquals(falseString, Util.equals("1", "2"));
		assertEquals("(1 = foo_AA)", Util.equals("1", "foo_AA"));
	}

	@Test
	public void notEqualsTest() {
		simplify = true;
		assertEquals("(a != b)", notEquals("a", "b"));
		assertEquals(falseString, notEquals("a", "a"));
		assertEquals(falseString, notEquals("AA", "AA"));
		assertEquals(trueString, notEquals("AA", "BB"));
		assertEquals("(AA != bb)", notEquals("AA", "bb"));
		assertEquals(falseString, notEquals("1", "1"));
		assertEquals(trueString, notEquals("1", "2"));
		assertEquals("(1 != foo_AA)", notEquals("1", "foo_AA"));
	}

	@Test
	public void isEnumTest() {
		assertTrue(isEnum("AA"));
		assertTrue(isEnum("AA_GG_TT"));
		assertFalse(isEnum("1"));
		assertFalse(isEnum("fOO"));
		assertFalse(isEnum("$AA"));
	}

	@Test
	public void isNumberTest() {
		assertTrue(isNumber("3"));
		assertTrue(isNumber("-3"));
		assertTrue(isNumber("0"));
		assertTrue(isNumber("3243"));
		assertFalse(isNumber("foo"));
		assertFalse(isNumber("$AA"));
		assertFalse(isNumber("1foo"));
	}

	@Test
	public void impliesTest() {
		simplify = true;
		assertEquals(trueString, implies(falseString, falseString));
		assertEquals(trueString, implies(falseString, trueString));
		assertEquals(falseString, implies(trueString, falseString));
		assertEquals(trueString, implies(trueString, trueString));
		assertEquals(trueString, implies(falseString, "foo"));
		assertEquals("!(foo)", implies("foo", falseString));
		assertEquals("foo", implies(trueString, "foo"));
		assertEquals(trueString, implies("foo", trueString));
		assertEquals(trueString, implies("foo", "foo"));
		assertEquals("(fooA -> fooB)", implies("fooA", "fooB"));
	}

	@Test
	public void iffTest() {
		simplify = true;
		assertEquals(trueString, iff(falseString, falseString));
		assertEquals(falseString, iff(falseString, trueString));
		assertEquals(falseString, iff(trueString, falseString));
		assertEquals(trueString, iff(trueString, trueString));
		assertEquals("!(foo)", iff(falseString, "foo"));
		assertEquals("!(foo)", iff("foo", falseString));
		assertEquals("foo", iff(trueString, "foo"));
		assertEquals("foo", iff("foo", trueString));
		assertEquals(trueString, iff("foo", "foo"));
		assertEquals("(fooA <-> fooB)", iff("fooA", "fooB"));
	}

	@Test
	public void hasFirstLastParenthesesTest() {
		assertTrue(hasFirstLastPars("(foo)"));
		assertTrue(hasFirstLastPars("(foo and foo_GG)"));
		assertTrue(hasFirstLastPars("((foo) or fooA)"));
		assertTrue(hasFirstLastPars("((foo) or (fooA))"));
		assertTrue(hasFirstLastPars("(((foo) or (fooA)))"));
		assertFalse(hasFirstLastPars("foo"));
		assertFalse(hasFirstLastPars("foo and false"));
		assertFalse(hasFirstLastPars("foo or (fooA or false)"));
	}

	@Test
	public void printFormattedTest() {
		assertEquals("asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf\nssss vvvv", printFormatted("asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf ssss vvvv"));
		assertEquals("pippo\npaperino", printFormatted("pippo\npaperino"));
		assertEquals("pippo\npaperino\npluto", printFormatted("pippo\npaperino\npluto"));
	}
}
