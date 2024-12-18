package org.asmeta.refinement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public abstract class ProofTest {

	class ProofResult {
		private String abstractModel;
		private String refinedModel;
		private boolean[] proofResult;
		private long time;
		private String link;
		private boolean[] expResults;

		public ProofResult(String abs, String ref, boolean[] result, long estimatedTime, boolean[] expResults) {
			if (abs.contains("../../../../asm_examples/")) {
				abstractModel = abs.replaceAll("../../../../asm_examples/", "");
				refinedModel = ref.replaceAll("../../../../asm_examples/", "");
				link = "https://sourceforge.net/p/asmeta/code/HEAD/tree/asm_examples/";
			} else {
				abstractModel = abs.replaceAll("./refinement/", "");
				refinedModel = ref.replaceAll("./refinement/", "");
				link = "https://sourceforge.net/p/asmeta/code/HEAD/tree/code/experimental/smtRepresentation/refinementProof/refinement/";
			}
			proofResult = result;
			this.time = estimatedTime;
			this.expResults = expResults;
		}

		@Override
		public String toString() {
			return abstractModel + " " + refinedModel + " "
					+ (proofResult == null ? "failed" : Arrays.toString(proofResult) + " " + time);
		}

		public String toHTML() {
			//
			StringBuffer result = new StringBuffer();
			// add abstract
			result.append("<tr>\n");
			result.append("<td><a href=\"" + link + abstractModel + "\">" + abstractModel + "</a></td>\n");
			//
			// result.append("<br> is correctly refined by? <br>");
			result.append("<td><a href=\"" + link + abstractModel + "\">" + refinedModel + "</a></td>");
			// result.append("<td>" + proofResult[0] + "</td><td>" +
			// proofResult[1] + "</td>\n");
			result.append(printCell(proofResult[0], expResults[0]) + printCell(proofResult[1], expResults[1]) + "\n");
			result.append("<td>" + time + "</td>\n");
			result.append("</tr>\n");
			return result.toString();
		}

		private String printCell(boolean result, boolean expResult) {
			return (result == expResult) ? ("<td bgcolor=\"green\">" + result + "</td>")
					: ("<td bgcolor=\"red\">" + result + "</td>");
		}

	}

	static List<ProofResult> summary;

	@BeforeClass
	static public void setupResults() {
		summary = new ArrayList<ProofResult>();
	}

	static String testName;

	@Before
	public void setupName() {
		testName = this.getClass().getSimpleName();
	}

	@AfterClass
	static public void printResults() throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter("experimentalresults" + "/" + testName + ".html"));
		bw.write("<table>\n");
		bw.write(
				"<tr><th>Abstract</th><th>Refined</th><th>Initial refinement</th><th>Step refinement</th><th>Time (ms)</th></tr>");
		for (ProofResult pr : summary) {
			bw.write(pr.toHTML());
		}
		bw.write("</table>");
		bw.close();
	}

	protected String basePath;

	protected void closeConsoleStream() {
		System.setOut(new PrintStream(new OutputStream() {
			@Override
			public void write(int b) throws IOException {
			}
		}));
	}

	private boolean[] proveRefinement(String abstractModel, String refinedModel, Set<String> functionsToCheck,
			boolean[] expResults) throws Exception {
		boolean[] result = null;
		long startTime = System.currentTimeMillis();
		try {
			RefinementProof proof = new RefinementProof(basePath + abstractModel, basePath + refinedModel,
					functionsToCheck);
			result = proof.buildProof();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			fail("Failed because of exception");
		} finally {
			long estimatedTime = System.currentTimeMillis() - startTime;
			summary.add(new ProofResult(basePath + abstractModel, basePath + refinedModel, result, estimatedTime,
					expResults));
		}
		return result;
	}

	protected void proveRefinement(String abstractModel, String refinedModel, boolean expectedInitialState,
			boolean expectedGenericStep) throws Exception {
		boolean[] result = proveRefinement(abstractModel, refinedModel, new HashSet<String>(),
				new boolean[] { expectedInitialState, expectedGenericStep });
		assertEquals(expectedInitialState, result[0]);
		assertEquals(expectedGenericStep, result[1]);
	}

	protected void proveRefinement(String abstractModel, String refinedModel, Set<String> functionsToCheck,
			boolean expectedInitialState, boolean expectedGenericStep) throws Exception {
		boolean[] result = proveRefinement(abstractModel, refinedModel, functionsToCheck,
				new boolean[] { expectedInitialState, expectedGenericStep });
		assertEquals(expectedInitialState, result[0]);
		assertEquals(expectedGenericStep, result[1]);
	}

}