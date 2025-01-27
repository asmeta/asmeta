package org.asmeta.toyices;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

public class Prova {
	
	static public void test(String asmFile) throws Exception {
		PrintStream standardOut = System.out;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    PrintStream ps = new PrintStream(baos);
		System.setOut(ps);
		SMTbasedASMsimulator visitor = new SMTbasedASMsimulator(asmFile);
		assertTrue(visitor.visitASM());
		assertTrue(visitor.assertRules());
		System.setOut(standardOut);
		String output = baos.toString();
		System.out.println(output);
		baos.close();
		ps.close();
		if(output.contains("A fatal error has been detected by the Java Runtime Environment")) {
			fail();
		}
	}



}
