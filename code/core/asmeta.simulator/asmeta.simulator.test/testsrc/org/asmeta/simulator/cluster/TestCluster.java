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
package org.asmeta.simulator.cluster;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.asmeta.simulator.InvalidInvariantException;
import org.asmeta.simulator.main.Simulator;
import org.asmeta.simulator.main.Simulator.InvariantTreament;
import org.asmeta.simulator.readers.FileMonFuncReader;
import org.asmeta.simulator.Environment;

public class TestCluster {

	static Random rnd = new Random();

	static final int around = 2;

	static final int grid = 10;

	static final String d = "";

	static final Point[] centerpoints = { new Point(0, 0), new Point(100, 0)};//,
			//new Point(50, 50) };

	// NUMERO DI PUNTI PER OGNI CLUSTER INIZIALE
	static final int NPoint = 2;
	// numers of steps
	static final int steps = 100;
	// number of runs
	static final int n_runs = 3;

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws Exception 
	 */
	static public void main(String args[]) throws Exception {

		// //
//		StdlEvaluator.registerFuntionEvaluator("cluster_java",
//				ClusterFunctions.class);

		// //

		long tclJ = 0, tcl = 0, tclA = 0, tclAJ = 0;

		for (int i = 0; i < n_runs; i++) {

			// build a cluster

			List<Point> allPoints = new ArrayList<Point>(centerpoints.length
					* NPoint);
			for (Point p : centerpoints) {
				allPoints.addAll(pointsAround(p, NPoint));
			}
			Collections.shuffle(allPoints);

			String cl = toString(allPoints, NPoint).toString();
			System.out.println(cl);
			Simulator.checkInvariants = InvariantTreament.NO_CHECK;
			tcl += runClusterModel(cl, "cluster.asm");
			tclJ += runClusterModel(cl, "cluster_java.asm");
			Simulator.checkInvariants = InvariantTreament.CHECK_CONTINUE;
			tclAJ += runClusterModel(cl, "cluster_java.asm");
			tclA += runClusterModel(cl, "cluster.asm");
		}
		System.out.println("ASM     " + tcl + " average " + tcl / n_runs);
		System.out.println("JAVA    " + tclJ + " average " + tclJ / n_runs);
		System.out.println("ASM Ax  " + tclA + " average " + tclA / n_runs);
		System.out.println("ASM AxJ " + tclAJ + " average " + tclAJ / n_runs);
	}

	private static long runClusterModel(String input, String spec)
			throws Exception {
		StringReader read = new StringReader(input);
		Environment env = new Environment(new FileMonFuncReader(read));
		Simulator sim = Simulator.createSimulator("../../../asm_examples/" + "rpns/r/" + spec, env);
		sim.setShuffleFlag(true);
		// System.out.println(sim.getCurrentState());
		long start = System.currentTimeMillis();
		try {
			sim.run(steps);
			String finalRes = sim.getCurrentState().toString();
			// System.out.println(finalRes);
			String clusters = finalRes.substring(finalRes
					.indexOf("clusters={{"));
			System.out.println(clusters);
			long timetaken = System.currentTimeMillis() - start;
			System.out.println(timetaken);
			return timetaken;
		} catch (InvalidInvariantException e) {
			System.out.println(e);
			System.out.println("AXIOM VIOLATED: " + e.getInvariant().getName());
			System.out.println("UPDATE SET: " + e.us);
			throw e;
		}
	}

	/**
	 * restituisce un array di n punti 8arry id due elementi) attorno ad un
	 * punto.
	 * 
	 * @param n
	 *            the nmber of points
	 * @param o
	 *            the o
	 * 
	 * @return the double[][]
	 */
	static List<Point> pointsAround(Point o, int n) {
		List<Point> result = new ArrayList<Point>(n);
		for (int i = 0; i < n; i++) {
			Point toadd = new Point(o.x + rnd.nextInt(around * grid)
					/ (double) grid, o.y + rnd.nextInt(around * grid)
					/ (double) grid);
			result.add(toadd);
		}
		return result;
	}

	static StringBuilder toString(List<Point> cluster, int stopEvery) {
		StringBuilder result = new StringBuilder("{{");
		for (int i = 0; i < cluster.size();) {
			result.append("(").append(cluster.get(i).x).append(",").append(
					cluster.get(i).y).append(")");
			if ((++i) < cluster.size())
				if (i % stopEvery == 0) {
					result.append("},{");
				} else {
					result.append(",");
				}
		}
		return result.append("}}");
	}

//	@Test
	public void testClusterASMwithAXIOMs() throws Exception {

		runSimpleCluster("cluster.asm");

	}
	
//	@Test
	public void testClusterNossrASMwithAXIOMs() throws Exception {

		runSimpleCluster("cluster_nossr.asm");

	}

//	@Test
	public void testClusterASMwithJavaAXIOMs() throws Exception {
		// //
//		StdlEvaluator.registerFuntionEvaluator("cluster_java",
//				ClusterFunctions.class);
		runSimpleCluster("cluster_java.asm");

	}

	private void runSimpleCluster(String string) throws Exception {
		int NPoint = 3;

		List<Point> allPoints = new ArrayList<Point>(centerpoints.length
				* NPoint);
		for (Point p : centerpoints) {
			allPoints.addAll(pointsAround(p, NPoint));
		}
		Collections.shuffle(allPoints);

		String cl = toString(allPoints, NPoint).toString();
		System.out.println(cl);
		Simulator.checkInvariants = InvariantTreament.CHECK_STOP;
		runClusterModel(cl, string);
	}

}

class Point {
	double x;
	double y;

	Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
}
