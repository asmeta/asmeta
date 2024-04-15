package org.asmeta.simulator.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.asmeta.simulator.Location;
import org.asmeta.simulator.State;
import org.asmeta.simulator.StdlEvaluator;
import org.asmeta.simulator.TermEvaluator;
import org.asmeta.simulator.UpdateSet;
import org.asmeta.simulator.main.BaseTest;
import org.asmeta.simulator.main.Simulator;
import org.asmeta.simulator.value.Value;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import asmeta.definitions.Function;

@RunWith(Parameterized.class)
public class StandardLibraryTest extends BaseTest {
	
	@Parameters
    public static Collection<Boolean> data() {
        return Arrays.asList(Boolean.TRUE, Boolean.FALSE);
    }

    public StandardLibraryTest(boolean allowLazyEval) {
        TermEvaluator.allowLazyEval = allowLazyEval;
    }
	
    private static boolean allowLazyEvalOLD;
    
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//AsmParserTest.setUpLogger();
		allowLazyEvalOLD = TermEvaluator.allowLazyEval;
	}

	@AfterClass
	public static void ripristina() throws Exception {
		//AsmParserTest.setUpLogger();
		TermEvaluator.allowLazyEval = allowLazyEvalOLD;
	}

	
	@Test
	public void testAppend() throws Exception {
		// Scrivi caso di test per la library
		String ex = "test/simulator/STDL/test_append.asm";
		Simulator sim = Simulator.createSimulator(ASM_EXAMPLES + ex);
		UpdateSet us = sim.run(1);
		System.out.println("UPDATE SET\n"+ us);
		assertEquals("succ_fibo=[1,1,4]", us.toString());		
		System.out.println("CURRENT STATE\n"+ sim.getCurrentState().toString());
	}

	@Test
	public void testContainsString() throws Exception {
		String ex = "test/simulator/STDL/containsString.asm";
		sim = Simulator.createSimulator(ASM_EXAMPLES + ex);
		sim.run(1);
		State s = sim.getCurrentState();
		Function foo1 = searchFunction("foo1");
		Value v1 = s.read(new Location(foo1, new Value[0]));
		assertEquals("a", v1.toString());
		Function foo2 = searchFunction("foo2");
		Value v2 = s.read(new Location(foo2, new Value[0]));
		assertEquals("b", v2.toString());
		Function foo3 = searchFunction("foo3");
		Value v3 = s.read(new Location(foo3, new Value[0]));
		assertEquals("c", v3.toString());
	}

	@Test
	public void testConcatString() throws Exception {
		String ex = "test/simulator/STDL/StringConcat.asm";
		sim = Simulator.createSimulator(ASM_EXAMPLES + ex);
		sim.run(1);
		State s = sim.getCurrentState();
		Function foo1 = searchFunction("str1");
		Value v1 = s.read(new Location(foo1, new Value[0]));
		assertEquals("aa", v1.toString());
		Function foo2 = searchFunction("str2");
		Value v2 = s.read(new Location(foo2, new Value[0]));
		assertEquals("aabb", v2.toString());

		sim.run(1);
		s = sim.getCurrentState();
		foo1 = searchFunction("str1");
		v1 = s.read(new Location(foo1, new Value[0]));
		assertEquals("aa", v1.toString());
		foo2 = searchFunction("str2");
		v2 = s.read(new Location(foo2, new Value[0]));
		assertEquals("aaaabb", v2.toString());
	}

	@Test
	public void testAddString() throws Exception {
		String ex = "test/simulator/STDL/StringAdd.asm";
		sim = Simulator.createSimulator(ASM_EXAMPLES + ex);
		sim.run(1);
		State s = sim.getCurrentState();
		Function foo1 = searchFunction("str1");
		Value v1 = s.read(new Location(foo1, new Value[0]));
		assertEquals("aa", v1.toString());
		Function foo2 = searchFunction("str2");
		Value v2 = s.read(new Location(foo2, new Value[0]));
		assertEquals("aabb", v2.toString());

		sim.run(1);
		s = sim.getCurrentState();
		foo1 = searchFunction("str1");
		v1 = s.read(new Location(foo1, new Value[0]));
		assertEquals("aa", v1.toString());
		foo2 = searchFunction("str2");
		v2 = s.read(new Location(foo2, new Value[0]));
		assertEquals("aaaabb", v2.toString());
	}

	@Test
	public void testToLowerString() throws Exception {
		String ex = "test/simulator/STDL/StringToLower.asm";
		sim = Simulator.createSimulator(ASM_EXAMPLES + ex);
		sim.run(1);
		State s = sim.getCurrentState();
		Function foo1 = searchFunction("str1");
		Value v1 = s.read(new Location(foo1, new Value[0]));
		assertEquals("AbC", v1.toString());
		Function foo2 = searchFunction("str2");
		Value v2 = s.read(new Location(foo2, new Value[0]));
		assertEquals("abc", v2.toString());

		sim.run(1);
		s = sim.getCurrentState();
		foo1 = searchFunction("str1");
		v1 = s.read(new Location(foo1, new Value[0]));
		assertEquals("AbC", v1.toString());
		foo2 = searchFunction("str2");
		v2 = s.read(new Location(foo2, new Value[0]));
		assertEquals("abc", v2.toString());
	}

	@Test
	public void testToUpperString() throws Exception {
		String ex = "test/simulator/STDL/StringToUpper.asm";
		sim = Simulator.createSimulator(ASM_EXAMPLES + ex);
		sim.run(1);
		State s = sim.getCurrentState();
		Function foo1 = searchFunction("str1");
		Value v1 = s.read(new Location(foo1, new Value[0]));
		assertEquals("AbC", v1.toString());
		Function foo2 = searchFunction("str2");
		Value v2 = s.read(new Location(foo2, new Value[0]));
		assertEquals("ABC", v2.toString());

		sim.run(1);
		s = sim.getCurrentState();
		foo1 = searchFunction("str1");
		v1 = s.read(new Location(foo1, new Value[0]));
		assertEquals("AbC", v1.toString());
		foo2 = searchFunction("str2");
		v2 = s.read(new Location(foo2, new Value[0]));
		assertEquals("ABC", v2.toString());
	}

	@Test
	public void testSplitString() throws Exception {
		String ex = "test/simulator/STDL/splitString.asm";
		sim = Simulator.createSimulator(ASM_EXAMPLES + ex);
		sim.run(1);
		State s = sim.getCurrentState();
		Function seqStr = searchFunction("seqStr");
		Value seqStrV = s.read(new Location(seqStr, new Value[0]));
		assertEquals("[This,is,a,sentence]", seqStrV.toString());

		Function seqStr1 = searchFunction("seqStr1");
		Value seqStr1V = s.read(new Location(seqStr1, new Value[0]));
		System.out.println(seqStr1V);
	}

	@Test
	public void testSizeString() throws Exception {
		String ex = "test/simulator/STDL/sizeString.asm";
		sim = Simulator.createSimulator(ASM_EXAMPLES + ex);
		sim.run(1);
		State s = sim.getCurrentState();
		Function sizeStr1 = searchFunction("sizeStr1");
		Value sizeStr1V = s.read(new Location(sizeStr1, new Value[0]));
		assertEquals("2", sizeStr1V.toString());
		Function sizeStr2 = searchFunction("sizeStr2");
		Value sizeStr2V = s.read(new Location(sizeStr2, new Value[0]));
		assertEquals("1", sizeStr2V.toString());
		Function sizeStr3 = searchFunction("sizeStr3");
		Value sizeStr3V = s.read(new Location(sizeStr3, new Value[0]));
		assertEquals("0", sizeStr3V.toString());
	}

	@Test
	public void testAppendSequence() throws Exception {
		String ex = "test/simulator/STDL/appendSequence.asm";
		sim = Simulator.createSimulator(ASM_EXAMPLES + ex);
		sim.run(1);
		State s = sim.getCurrentState();
		Function sequence = searchFunction("sequence");
		Value sequenceC = s.read(new Location(sequence, new Value[0]));
		assertEquals("[undef]", sequenceC.toString());

		sim.run(1);
		sequenceC = s.read(new Location(sequence, new Value[0]));
		assertEquals("[undef,0]", sequenceC.toString());

		sim.run(1);
		sequenceC = s.read(new Location(sequence, new Value[0]));
		assertEquals("[undef,0,1]", sequenceC.toString());
	}

	@Test
	public void testSTDLfuncs() throws Exception {
		Simulator sim = Simulator.createSimulator(ASM_EXAMPLES + "test/simulator/STDL/STDL_funcs.asm");
		UpdateSet us = sim.run(1);
		assertTrue(true);
	}

	@Test
	public void testSTDLUndefLogicalOp() throws Exception {
		Simulator sim = Simulator.createSimulator(ASM_EXAMPLES + "test/simulator/STDL/STDL_funcsUndef.asm");
		UpdateSet us = sim.run(1);
		assertEquals("undef",getVal(us,"c1"));
		// AND
		assertEquals("undef",getVal(us,"c2"));
		assertEquals("undef",getVal(us,"c3"));
		assertEquals("false",getVal(us,"c4"));
		assertEquals("false",getVal(us,"c5"));
		assertEquals("undef",getVal(us,"c6"));
		// OR
		assertEquals("true",getVal(us,"d1"));
		assertEquals("true",getVal(us,"d2"));
		assertEquals("undef",getVal(us,"d3"));
		assertEquals("undef",getVal(us,"d4"));
		assertEquals("undef",getVal(us,"d5"));
		// NOT undef
		assertEquals("undef",getVal(us,"d6"));
	}
	
	@Test
	public void testSTDLUndefEq() throws Exception {
		Simulator sim = Simulator.createSimulator(ASM_EXAMPLES + "test/simulator/STDL/STDL_funcsUndefEQ.asm");
		UpdateSet us = sim.run(1);
		assertEquals("false",getVal(us,"c1"));
		assertEquals("false",getVal(us,"c2"));
		assertEquals("false",getVal(us,"c3"));
		assertEquals("false",getVal(us,"c4"));
	}

	private String getVal(UpdateSet us, String location) {
		Map<Location, Value> locationMap = us.getLocationMap();
		for( Entry<Location, Value> e: locationMap.entrySet()) {
			if (e.getKey().toString().equals(location)) return e.getValue().toString();
		}
		fail(location + " not found");
		return null;
	}


}
