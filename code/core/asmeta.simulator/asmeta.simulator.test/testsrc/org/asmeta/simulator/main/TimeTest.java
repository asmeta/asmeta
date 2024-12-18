package org.asmeta.simulator.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.Environment.TimeMngt;
import org.asmeta.simulator.readers.MonFuncReader;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.RuleEvaluator;
import org.asmeta.simulator.State;
import org.asmeta.simulator.TermEvaluator;
import org.asmeta.simulator.value.IntegerValue;
import org.asmeta.simulator.value.Value;
import org.junit.BeforeClass;
import org.junit.Test;

import asmeta.definitions.Function;

public class TimeTest extends BaseTest {

	@BeforeClass
	static public void setLogger() {
		Logger.getLogger("org.asmeta.parser").setLevel(Level.OFF);
		Logger.getLogger(RuleEvaluator.class).setLevel(Level.OFF);
		Logger.getLogger(TermEvaluator.class).setLevel(Level.OFF);
	}

	@Test
	public void test_1_jt() throws Exception {
		// one monitored variable with time seconds
		sim = Simulator.createSimulator(ASM_EXAMPLES + "test/simulator/time/time1.asm");
		Environment.timeMngt = TimeMngt.use_java_time;
		sim.run(1);
		State state = sim.getCurrentState();
		assertEquals("0", getFunctionValue("time", state));
		// wait one seconds
		TimeUnit.SECONDS.sleep(1);
		sim.run(1);
		assertEquals("1", getFunctionValue("time", sim.getCurrentState()));
	}

	@Test
	public void test_2_jt() throws Exception {
		// 2 monitored variables
		sim = Simulator.createSimulator(ASM_EXAMPLES + "test/simulator/time/time2.asm");
		Environment.timeMngt = TimeMngt.use_java_time;
		Instant startFrom = Instant.now();
		TimeUnit.MILLISECONDS.sleep(3);
		sim.run(1);
		long mills = startFrom.until(Instant.now(), ChronoUnit.MILLIS);
		State state = sim.getCurrentState();
		String t1 = getFunctionValue("time1", state);
		String t2 = getFunctionValue("time2", state);
		assertEquals(t1, t2);
		assertTrue("time2 is " + Integer.parseInt(t2) + " millis " + mills, Integer.parseInt(t2) > 0);
		// wait one seconds
		TimeUnit.SECONDS.sleep(1);
		sim.run(1);
		state = sim.getCurrentState();
		t1 = getFunctionValue("time1", state);
		t2 = getFunctionValue("time2", state);
		assertTrue("time2 is " + Integer.parseInt(t2) + " millis " + mills, Integer.parseInt(t2) > 0);
	}

	@Test
	public void test_mix1_jt() throws Exception {
		sim = Simulator.createSimulator(ASM_EXAMPLES + "test/simulator/time/mixedtime1.asm");
		Environment.timeMngt = TimeMngt.use_java_time;
		// Environment.currentTimeUnit = TimeUnit.MILLISECONDS;
		sim.run(1);
		State state = sim.getCurrentState();
		String t1 = getFunctionValue("timeS", state);
		String t2 = getFunctionValue("timeMS", state);
		assertEquals(Double.parseDouble(t1), Double.parseDouble(t2) / 1000, 10);
		// System.out.println( t1 + "*** " + t2);
		// wait two seconds
		TimeUnit.SECONDS.sleep(2);
		sim.run(1);
		t1 = getFunctionValue("timeS", state);
		t2 = getFunctionValue("timeMS", state);
		assertEquals(Double.parseDouble(t1), Double.parseDouble(t2) / 1000, 10);
		// System.out.println( t1 + "*** " + t2);
	}

	@Test
	public void test_mix2_jt() throws Exception {
		// 2 monitored variables
		sim = Simulator.createSimulator(ASM_EXAMPLES + "test/simulator/time/mixedtime2.asm");
		Environment.timeMngt = TimeMngt.use_java_time;
		sim.run(1);
		State state = sim.getCurrentState();
		String t1 = getFunctionValue("timeS", state);
		String t2 = getFunctionValue("timeMS", state);
		assertEquals(Double.parseDouble(t1), 0, 0);
		assertEquals(t2, "undef");
		// wait one seconds
		TimeUnit.MILLISECONDS.sleep(2100);
		sim.run(2);
		t1 = getFunctionValue("timeS", state);
		t2 = getFunctionValue("timeMS", state);
		assertEquals(Double.parseDouble(t1), Double.parseDouble(t2) / 1000, 10);
		assertTrue(Integer.parseInt(t2) > 2100);
	}
	// ask the user
	// with a simulated user - TODO check that asks only once
	class SimulatedUser extends MonFuncReader{
		int count = 0;
		Map<Integer,ChronoUnit> asked = new HashMap<>();
		@Override
		public Value readValue(Location location, State state) {
			assert !asked.keySet().contains(count) : 
				"asking " + location.toString() + " " + asked.get(count);
			// TODO Auto-generated method stub
			System.out.println("asking for " + location.toString());
			if (location.toString().equals("mCurrTimeSecs")) {
				asked.put(count, ChronoUnit.SECONDS);
				return new IntegerValue(count);
			}
			if (location.toString().equals("mCurrTimeMillisecs")) {
				asked.put(count, ChronoUnit.MILLIS);
				return new IntegerValue(count * 1000);
			}
			fail(location.toString());
			return null;
		}
		void inctime() {count++;}
	}

	// base case with auto mode
	@Test
	public void test_1_au_ok() throws Exception {
		Environment.currentTimeUnit = null;
		Environment.timeMngt = TimeMngt.ask_user;
		SimulatedUser monFuncReader = new SimulatedUser();
		sim = Simulator.createSimulator(ASM_EXAMPLES + "test/simulator/time/time1.asm", new Environment(monFuncReader));
		sim.run(1);
		assertEquals("0", getFunctionValue("time", sim.getCurrentState()));
		monFuncReader.inctime();sim.run(1);
		assertEquals("1", getFunctionValue("time", sim.getCurrentState()));
		assertEquals("[Seconds, Seconds]",monFuncReader.asked.values().toString());
	}

	@Test // asking milliseconds
	public void test_1_au_okms() throws Exception {
		Environment.currentTimeUnit = ChronoUnit.MILLIS;
		Environment.timeMngt = TimeMngt.ask_user;
		SimulatedUser monFuncReader = new SimulatedUser();
		sim = Simulator.createSimulator(ASM_EXAMPLES + "test/simulator/time/time1.asm", new Environment(monFuncReader));
		// warning !!!
		sim.run(1);
		assertEquals("0", getFunctionValue("time", sim.getCurrentState()));
		monFuncReader.inctime();sim.run(1);
		assertEquals("1", getFunctionValue("time", sim.getCurrentState()));
		assertEquals("[Millis, Millis]",monFuncReader.asked.values().toString());
	}

	
	// two times but asked only once
	@Test
	public void test_2_au_ok() throws Exception {
		Environment.currentTimeUnit = null; // auto mode
		Environment.timeMngt = TimeMngt.ask_user;
		SimulatedUser user = new SimulatedUser();
		sim = Simulator.createSimulator(ASM_EXAMPLES + "test/simulator/time/time2.asm", new Environment(user));
		sim.run(1);
		// check that it asks only once
		assertEquals("0", getFunctionValue("time1", sim.getCurrentState()));
		assertEquals("0", getFunctionValue("time2", sim.getCurrentState()));
		user.inctime();
		sim.run(1);
		assertEquals("1000", getFunctionValue("time1", sim.getCurrentState()));
		assertEquals("1000", getFunctionValue("time2", sim.getCurrentState()));
		assertEquals("[Millis, Millis]",user.asked.values().toString());
	}

	// two different units - the finer is asked after some steps, so it must give some warning 
	// set current unit as minutes but then there are secs
	@Test
	public void test_mix2_au_warn() throws Exception {
		Environment.currentTimeUnit = null;
		Environment.timeMngt = TimeMngt.ask_user;
		SimulatedUser user = new SimulatedUser();
		sim = Simulator.createSimulator(ASM_EXAMPLES + "test/simulator/time/mixedtime2.asm", new Environment(user));
		user.inctime();sim.run(1);
		user.inctime();sim.run(1);
		user.inctime();sim.run(1);
		assertEquals("[Seconds, Seconds, Seconds]",user.asked.values().toString());
		assertEquals("3", getFunctionValue("timeS", sim.getCurrentState()));
		assertEquals("3000", getFunctionValue("timeMS", sim.getCurrentState()));
	}
	// two different units - ask only the millis 
	@Test
	public void test_mix1_au_warn() throws Exception {
		Environment.currentTimeUnit = null;
		Environment.timeMngt = TimeMngt.ask_user;
		SimulatedUser user = new SimulatedUser();
		sim = Simulator.createSimulator(ASM_EXAMPLES + "test/simulator/time/mixedtime1.asm", new Environment(user));
		user.inctime();sim.run(1);
		user.inctime();sim.run(1);
		user.inctime();sim.run(1);
		assertEquals("[Millis, Millis, Millis]",user.asked.values().toString());
		assertEquals("3", getFunctionValue("timeS", sim.getCurrentState()));
		assertEquals("3000", getFunctionValue("timeMS", sim.getCurrentState()));
	}

	// the same with millisec it works
	@Test
	public void test_mix2_fine() throws Exception {
		Environment.currentTimeUnit = ChronoUnit.MILLIS; // it should ask for milliseconds
		Environment.timeMngt = TimeMngt.ask_user;
		SimulatedUser u = new SimulatedUser();
		sim = Simulator.createSimulator(ASM_EXAMPLES + "test/simulator/time/mixedtime2.asm", new Environment(u));
		String t1 = getFunctionValue("timeS", sim.getCurrentState());
		String t2 = getFunctionValue("timeMS", sim.getCurrentState());
		assertEquals(Double.parseDouble(t1), 0, 0);
		assertEquals(t2, "undef");
		System.out.println("+++" + t1 + " " +t2);
		sim.run(1);
		t1 = getFunctionValue("timeS", sim.getCurrentState());
		t2 = getFunctionValue("timeMS", sim.getCurrentState());
		System.out.println("++++" + t1 + " " +t2);
		u.inctime();sim.run(1);
		t1 = getFunctionValue("timeS", sim.getCurrentState());
		t2 = getFunctionValue("timeMS", sim.getCurrentState());
		System.out.println("+++++" + t1 + " " +t2);
		u.inctime();sim.run(1);
		t1 = getFunctionValue("timeS", sim.getCurrentState());
		t2 = getFunctionValue("timeMS", sim.getCurrentState());
		System.out.println("++++++" + t1 + " " +t2);
		u.inctime();sim.run(1);
		t1 = getFunctionValue("timeS", sim.getCurrentState());
		t2 = getFunctionValue("timeMS", sim.getCurrentState());
		System.out.println("++++++" + t1 + " " +t2);
		assertEquals("[Millis, Millis, Millis, Millis]",u.asked.values().toString());
		assertEquals("3", getFunctionValue("timeS", sim.getCurrentState()));
		assertEquals("3000", getFunctionValue("timeMS", sim.getCurrentState()));
	}
	///////////////////////// auto increment
	@Test
	public void test_1_ai() throws Exception {
		// one monitored variable with time seconds
		sim = Simulator.createSimulator(ASM_EXAMPLES + "test/simulator/time/time1.asm");
		Environment.timeMngt = TimeMngt.auto_increment;
		Environment.currentTimeUnit = ChronoUnit.SECONDS;
		sim.run(1);
		State state = sim.getCurrentState();
		assertEquals("1", getFunctionValue("time", state));
		sim.run(1);
		assertEquals("2", getFunctionValue("time", sim.getCurrentState()));
	}

	

	String getFunctionValue(String fname, State state) {
		Function func = searchFunction(fname);
		Value value = state.read(new Location(func, new Value[] {}));
		return value.toString();
	}

}
