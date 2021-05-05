/*******************************************************************************
 * Copyright (c) 2009 .
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *      - initial API and implementation
 ******************************************************************************/
package org.asmeta.simulator.main;

import org.asmeta.simulator.Environment;
import org.asmeta.simulator.InvalidInvariantException;
import org.asmeta.simulator.UpdateClashException;
import org.asmeta.simulator.UpdateSet;
import org.asmeta.simulator.main.Simulator.InvariantTreament;
import org.asmeta.simulator.readers.RandomMFReader;

public class TestOneSpec {

	public static final String FILE_BASE = "../../../../asm_examples/";
	
	public static void main(String[] a) throws Exception {
		new TestOneSpec().testOneSpec();
	}

	// @Test
	public void testOneSpec() throws Exception {

		Environment env = new Environment(
				new RandomMFReader());
				//new InteractiveMFReader(System.in, System.out));
				

		Simulator sim;

		// Simulator sim = AsmetaSUtil.xxxx(AsmetaSFitTest.BASE
		// +"rpns/r/philosophers_with_res.asm");

		// sim = AsmetaSUtil.xxxx(AsmetaSFitTest.BASE +
		// "rpns/r/cluster.asm", AsmetaSFitTest.BASE + "rpns/r/cluster.env");

		// sim = AsmetaSUtil.xxxx(AsmetaSFitTest.BASE +
		// "rpns/r/cluster_test.asm");

		sim = Simulator.createSimulator(FILE_BASE +
				"test/simulator/MapDomain.asm",
//				"erinda/FMS_IO.asm",
//				"examples/Tictactoe_senzaSEQ.asm",
//				"examples/flashProtocol/flashProtocol_N2_L1_Q1.asm",
//				"examples/flashProtocol/old/flashProtocol_modificato_old.asm",
				// +"rpns/r/LocationVarChoose.asm",env);
				// +"rnp/logicalvar_macro.asm");
				//rpns/r/problema_sequenza.asm",
				//"rpns/r/problema_sequenza.asm",
				// "rpns/r/LocVarSeq.asm");
				// "rpns/r/cluster_nossr.asm", 
				//"rpns/r/cluster_nossr1Dint.asm", 
				//"examples/models/SwapSortMon.asm",
				//"examples/library/Library.asm",
				//"examples/models/wiper_abs2.asm",
				//"scheduler/clock/sched.asm", 
				//"rps_mono/models/ordersystem.asm", 
				//"rpns/r/BibliotecaASM.asm",
				//"rps_agents/oneWayTrafficLight_refined_with_agents_problem.asm",
				//AsmetaSFitTest.BASE + 
				//"rpns/r/cluster.env"
				//"rpns/r/cluster_int.env"
				//"rps_mono/models/oneWayTrafficLight.env"				
				env
				);
		System.out.println(sim.getCurrentState());
		try {
			Simulator.checkInvariants = InvariantTreament.CHECK_STOP;
			sim.setShuffleFlag(false);
			for (int i = 0; i < 100; i++){
				UpdateSet us = sim.run(1);
				System.out.println("UPDATE SET\n"+ us);
				System.out.println("CURRENT STATE\n"+ sim.getCurrentState().toString());
			}
			String finalRes = sim.getCurrentState().toString();
			System.out.println(finalRes);
			// System.out.println(finalRes.substring(finalRes.indexOf("clusters={{")));

		} catch (InvalidInvariantException e) {
			System.out.println(e);
			System.out.print("UPDATE SET " + e.us);
		} catch (UpdateClashException e) {
			System.out.println("****UpdateClashException***");
			System.out.println(e.loc);
			System.out.println(e.c1);
			System.out.println(e.c2);
		}
	}
}
