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
import static org.asmeta.nusmv.util.Util.TRUE_STRING;
import static org.asmeta.nusmv.util.Util.xor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;


public class UtilTest {

	@Test
	public void orTest() {
		AsmetaSMVOptions.simplify = true;
		List<String> conds = new ArrayList<String>();
		conds.add(Util.falseString);
		conds.add("b");
		conds.add(Util.falseString);
		String or = Util.or(conds);

		assertEquals("b", or);
	}

	@Test
	public void orTest1() {
		AsmetaSMVOptions.simplify = true;
		List<String> conds = new ArrayList<String>();
		conds.add(TRUE_STRING);
		conds.add("b");
		conds.add(falseString);
		assertEquals(TRUE_STRING, or(conds));
	}

	@Test
	public void orTest2() {
		AsmetaSMVOptions.simplify = true;
		List<String> conds = new ArrayList<String>();
		conds.add(falseString);
		conds.add(falseString);
		conds.add(falseString);
		assertEquals(falseString, or(conds));
	}

	@Test
	public void orTest3() {
		AsmetaSMVOptions.simplify = true;
		List<String> conds = new ArrayList<String>();
		conds.add("b");
		conds.add(falseString);
		conds.add("foo_AA");
		assertEquals("(b | foo_AA)", or(conds));
	}

	@Test
	public void orTest4() {
		AsmetaSMVOptions.simplify = true;
		List<String> conds = new ArrayList<String>();
		conds.add("b");
		conds.add(falseString);
		conds.add("foo_AA");
		conds.add("foo_BB");
		assertEquals("(b | foo_AA | foo_BB)", or(conds));
	}

	@Test
	public void orTest5() {
		AsmetaSMVOptions.simplify = true;
		assertEquals(falseString, or(falseString, falseString));
		assertEquals(TRUE_STRING, or(TRUE_STRING, falseString));
		assertEquals(TRUE_STRING, or(falseString, TRUE_STRING));
		assertEquals(TRUE_STRING, or(TRUE_STRING, TRUE_STRING));
		assertEquals("foo", or(falseString, "foo"));
		assertEquals("foo", or("foo", falseString));
		assertEquals(TRUE_STRING, or(TRUE_STRING, "foo"));
		assertEquals(TRUE_STRING, or("foo", TRUE_STRING));
		assertEquals("(foo | fooA)", or("foo", "fooA"));
		assertEquals(TRUE_STRING, or("!(foo)", "foo"));
	}

	@Test
	public void xorTest() throws Exception {
		AsmetaSMVOptions.simplify = true;
		assertEquals(falseString, xor(falseString, falseString));
		assertEquals(TRUE_STRING, xor(TRUE_STRING, falseString));
		assertEquals(TRUE_STRING, xor(falseString, TRUE_STRING));
		assertEquals(falseString, xor(TRUE_STRING, TRUE_STRING));

		assertEquals("condB", xor(falseString, "condB"));
		assertEquals("!(condB)", xor(TRUE_STRING, "condB"));
		assertEquals("condB", xor("condB", falseString));
		assertEquals("!(condB)", xor("condB", TRUE_STRING));

		List<String> conds = new ArrayList<String>();
		conds.add(falseString);
		conds.add(falseString);
		conds.add(falseString);
		assertEquals(falseString, xor(conds));
		conds.clear();
		conds.add(falseString);
		conds.add(falseString);
		conds.add(TRUE_STRING);
		assertEquals(TRUE_STRING, xor(conds));
		conds.clear();
		conds.add(falseString);
		conds.add(TRUE_STRING);
		conds.add(TRUE_STRING);
		assertEquals(falseString, xor(conds));

		conds.clear();
		conds.add(TRUE_STRING);
		conds.add(TRUE_STRING);
		conds.add(TRUE_STRING);
		assertEquals(TRUE_STRING, xor(conds));

		conds.clear();
		conds.add(falseString);
		conds.add("condB");
		assertEquals("condB", xor(conds));

		conds.clear();
		conds.add(TRUE_STRING);
		conds.add("condB");
		assertEquals("!(condB)", xor(conds));

		conds.clear();
		conds.add(TRUE_STRING);
		conds.add(falseString);
		conds.add("condB");
		assertEquals("!(condB)", xor(conds));

		conds.clear();
		conds.add(falseString);
		conds.add(TRUE_STRING);
		conds.add("condB");
		assertEquals("!(condB)", xor(conds));

		conds.clear();
		conds.add(falseString);
		conds.add(falseString);
		conds.add("condB");
		assertEquals("condB", xor(conds));

		conds.clear();
		conds.add(TRUE_STRING);
		conds.add(TRUE_STRING);
		conds.add("condB");
		assertEquals("condB", xor(conds));

		conds.clear();
		conds.add(TRUE_STRING);
		conds.add(TRUE_STRING);
		conds.add(falseString);
		conds.add("condB");
		assertEquals("condB", xor(conds));

		conds.clear();
		conds.add(TRUE_STRING);
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
		conds.add(TRUE_STRING);
		conds.add(TRUE_STRING);
		conds.add(TRUE_STRING);
		conds.add("condB");
		assertEquals("!(condB)", xor(conds));

		conds.clear();
		conds.add(TRUE_STRING);
		conds.add(TRUE_STRING);
		conds.add(TRUE_STRING);
		conds.add(TRUE_STRING);
		assertEquals(falseString, xor(conds));

		conds.clear();
		conds.add(TRUE_STRING);
		conds.add(TRUE_STRING);
		conds.add(TRUE_STRING);
		conds.add(falseString);
		assertEquals(TRUE_STRING, xor(conds));

		conds.clear();
		conds.add(TRUE_STRING);
		conds.add(TRUE_STRING);
		conds.add(falseString);
		conds.add(falseString);
		assertEquals(falseString, xor(conds));

		conds.clear();
		conds.add(TRUE_STRING);
		conds.add(falseString);
		conds.add(falseString);
		conds.add(falseString);
		assertEquals(TRUE_STRING, xor(conds));

		conds.clear();
		conds.add(falseString);
		conds.add(falseString);
		conds.add(falseString);
		conds.add(falseString);
		assertEquals(falseString, xor(conds));

		conds.clear();
		conds.add(TRUE_STRING);
		conds.add(TRUE_STRING);
		conds.add(TRUE_STRING);
		conds.add(TRUE_STRING);
		conds.add("condB");
		assertEquals("condB", xor(conds));

		conds.clear();
		conds.add(TRUE_STRING);
		conds.add(TRUE_STRING);
		conds.add(TRUE_STRING);
		conds.add(falseString);
		conds.add("condB");
		assertEquals("!(condB)", xor(conds));

		conds.clear();
		conds.add(TRUE_STRING);
		conds.add(TRUE_STRING);
		conds.add(falseString);
		conds.add(falseString);
		conds.add("condB");
		assertEquals("condB", xor(conds));

		conds.clear();
		conds.add(TRUE_STRING);
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
		conds.add(TRUE_STRING);
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
		AsmetaSMVOptions.simplify = true;
		List<String> conds = new ArrayList<String>();
		conds.add(TRUE_STRING);
		conds.add(falseString);
		assertEquals(falseString, and(conds));
	}

	@Test
	public void andTest2() {
		AsmetaSMVOptions.simplify = true;
		List<String> conds = new ArrayList<String>();
		conds.add(TRUE_STRING);
		conds.add(TRUE_STRING);
		conds.add(TRUE_STRING);
		assertEquals(TRUE_STRING, and(conds));
	}

	@Test
	public void andTest3() {
		AsmetaSMVOptions.simplify = true;
		List<String> conds = new ArrayList<String>();
		conds.add("b");
		conds.add(falseString);
		conds.add("foo_AA");
		assertEquals(falseString, and(conds));
	}

	@Test
	public void andTest4() {
		AsmetaSMVOptions.simplify = true;
		assertEquals(falseString, and(falseString, falseString));
		assertEquals(falseString, and(TRUE_STRING, falseString));
		assertEquals(falseString, and(falseString, TRUE_STRING));
		assertEquals(TRUE_STRING, and(TRUE_STRING, TRUE_STRING));
		assertEquals(falseString, and(falseString, "foo"));
		assertEquals(falseString, and("foo", falseString));
		assertEquals("foo", and(TRUE_STRING, "foo"));
		assertEquals("foo", and("foo", TRUE_STRING));
		assertEquals("(foo & fooA)", and("foo", "fooA"));
		assertEquals(falseString, and("foo", "!(foo)"));
	}

	@Test
	public void notTest() {
		AsmetaSMVOptions.simplify = true;
		assertEquals(falseString, not(TRUE_STRING));
		assertEquals(TRUE_STRING, not(falseString));
		assertEquals(TRUE_STRING, not(not(TRUE_STRING)));
		assertEquals(falseString, not(not(falseString)));
		assertEquals(falseString, not(not(not(TRUE_STRING))));
		assertEquals(TRUE_STRING, not(not(not(falseString))));
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
		AsmetaSMVOptions.simplify = true;
		assertEquals("(a = b)", Util.equals("a", "b"));
		assertEquals(TRUE_STRING, Util.equals("a", "a"));
		assertEquals(TRUE_STRING, Util.equals("AA", "AA"));
		assertEquals(falseString, Util.equals("AA", "BB"));
		assertEquals("(AA = bb)", Util.equals("AA", "bb"));
		assertEquals(TRUE_STRING, Util.equals("1", "1"));
		assertEquals(falseString, Util.equals("1", "2"));
		assertEquals("(1 = foo_AA)", Util.equals("1", "foo_AA"));
	}

	@Test
	public void notEqualsTest() {
		AsmetaSMVOptions.simplify = true;
		assertEquals("(a != b)", notEquals("a", "b"));
		assertEquals(falseString, notEquals("a", "a"));
		assertEquals(falseString, notEquals("AA", "AA"));
		assertEquals(TRUE_STRING, notEquals("AA", "BB"));
		assertEquals("(AA != bb)", notEquals("AA", "bb"));
		assertEquals(falseString, notEquals("1", "1"));
		assertEquals(TRUE_STRING, notEquals("1", "2"));
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
		AsmetaSMVOptions.simplify = true;
		assertEquals(TRUE_STRING, implies(falseString, falseString));
		assertEquals(TRUE_STRING, implies(falseString, TRUE_STRING));
		assertEquals(falseString, implies(TRUE_STRING, falseString));
		assertEquals(TRUE_STRING, implies(TRUE_STRING, TRUE_STRING));
		assertEquals(TRUE_STRING, implies(falseString, "foo"));
		assertEquals("!(foo)", implies("foo", falseString));
		assertEquals("foo", implies(TRUE_STRING, "foo"));
		assertEquals(TRUE_STRING, implies("foo", TRUE_STRING));
		assertEquals(TRUE_STRING, implies("foo", "foo"));
		assertEquals("(fooA -> fooB)", implies("fooA", "fooB"));
	}

	@Test
	public void iffTest() {
		AsmetaSMVOptions.simplify = true;
		assertEquals(TRUE_STRING, iff(falseString, falseString));
		assertEquals(falseString, iff(falseString, TRUE_STRING));
		assertEquals(falseString, iff(TRUE_STRING, falseString));
		assertEquals(TRUE_STRING, iff(TRUE_STRING, TRUE_STRING));
		assertEquals("!(foo)", iff(falseString, "foo"));
		assertEquals("!(foo)", iff("foo", falseString));
		assertEquals("foo", iff(TRUE_STRING, "foo"));
		assertEquals("foo", iff("foo", TRUE_STRING));
		assertEquals(TRUE_STRING, iff("foo", "foo"));
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
