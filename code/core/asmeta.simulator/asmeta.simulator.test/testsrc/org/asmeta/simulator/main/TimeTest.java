package org.asmeta.simulator.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.Environment.TimeMngt;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.State;
import org.asmeta.simulator.value.Value;
import org.junit.BeforeClass;
import org.junit.Test;

import asmeta.definitions.Function;

public class TimeTest extends BaseTest {

	@BeforeClass
	static public void setLogger() {
		Logger.getLogger("org.asmeta.parser").setLevel(Level.OFF);
	}

	@Test
	public void test01() throws Exception {
		// one montored variable with time seconds
		sim = Simulator.createSimulator(BASE + "test/simulator/time/time1.asm");
		Simulator.checkInvariants = true;
		Environment.timeMngt = TimeMngt.use_java_time;
		sim.run(1);
		State state = sim.getCurrentState();
		assertEquals("0", getFunctionValue("time",state)); 
		// wait one seconds
		TimeUnit.SECONDS.sleep(1);
		sim.run(1);
		assertEquals("1", getFunctionValue("time",sim.getCurrentState())); 
	}

	
	@Test
	public void test02() throws Exception {
		// 2 monitored variables 
		sim = Simulator.createSimulator(BASE + "test/simulator/time/time2.asm");
		Simulator.checkInvariants = true;
		Environment.timeMngt = TimeMngt.use_java_time;
		Environment.currentTimeUnit = null;
		Instant startFrom = Instant.now();
		sim.run(1);
		State state = sim.getCurrentState();
		String t1 = getFunctionValue("time1",state); 
		String t2 = getFunctionValue("time2",state);
		assertEquals(t1, t2);
		
		long mills = startFrom.until(Instant.now(), ChronoUnit.MILLIS);
		assertTrue(Integer.parseInt(t2) > mills);
		// wait one seconds
		TimeUnit.SECONDS.sleep(1);
		sim.run(1);
	}

	@Test(expected = RuntimeException.class)
	public void test03_err() throws Exception {
		sim = Simulator.createSimulator(BASE + "test/simulator/time/mixedtime1.asm");
		Environment.currentTimeUnit = null;
		Simulator.checkInvariants = true;
		Environment.timeMngt = TimeMngt.use_java_time;
		sim.run(1);
	}	
	
	@Test
	public void test03() throws Exception {
		sim = Simulator.createSimulator(BASE + "test/simulator/time/mixedtime1.asm");
		Simulator.checkInvariants = true;		
		Environment.timeMngt = TimeMngt.use_java_time;
		Environment.currentTimeUnit = TimeUnit.MILLISECONDS;
		sim.run(1);
		State state = sim.getCurrentState();
		String t1 = getFunctionValue("timeS",state); 
		String t2 = getFunctionValue("timeMS",state);
		assertEquals(Double.parseDouble(t1), Double.parseDouble(t2)/1000,10);
		//System.out.println( t1 + "*** " + t2);
		// wait two seconds
		TimeUnit.SECONDS.sleep(2);
		sim.run(1);
		t1 = getFunctionValue("timeS",state); 
		t2 = getFunctionValue("timeMS",state);
		assertEquals(Double.parseDouble(t1), Double.parseDouble(t2)/1000,10);
		//System.out.println( t1 + "*** " + t2);
	}
	
	@Test(expected = RuntimeException.class)
	public void test04() throws Exception {
		// 2 monitored variables 
		sim = Simulator.createSimulator(BASE + "test/simulator/time/mixedtime2.asm");
		Simulator.checkInvariants = true;
		Environment.timeMngt = TimeMngt.use_java_time;
		Environment.currentTimeUnit = null;
		sim.run(1);
		State state = sim.getCurrentState();
		String t1 = getFunctionValue("timeS",state); 
		String t2 = getFunctionValue("timeMS",state);
		assertEquals(Double.parseDouble(t1), 0,0);
		assertEquals(t2, "undef");
		// wait one seconds
		TimeUnit.SECONDS.sleep(1);
		sim.run(2);
	}

	
	
	String getFunctionValue(String fname, State state) {
		Function func = searchFunction(fname);
		Value value = state.read(new Location(func, new Value[] {}));
		return value.toString();
	}

}
