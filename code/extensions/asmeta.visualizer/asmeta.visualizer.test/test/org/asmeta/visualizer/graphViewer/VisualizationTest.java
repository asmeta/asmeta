package org.asmeta.visualizer.graphViewer;

import org.junit.Test;

public class VisualizationTest {

	private void visualize(String modelPath) {
		AsmGraphViewerVisualizer.showGraph(modelPath);
	}
	
	@Test
	public void testLGS_GM_basicView() {
		GraphEdgesAdder.detectSemanticPatterns = false;
		visualize("../../../../asm_examples/examples/landingGearSystem/LGS_GM.asm");
	}

	@Test
	public void testHemodialysis_GM_basicView() {
		GraphEdgesAdder.detectSemanticPatterns = false;
		visualize("../../../../asm_examples/examples/hemodialysisDevice/ABZ2016/HemodialysisGround.asm");
	}

	@Test
	public void testHemodialysis_GM_semanticView() {
		GraphEdgesAdder.detectSemanticPatterns = true;
		visualize("../../../../asm_examples/examples/hemodialysisDevice/HemodialysisGround.asm");
	}

	@Test
	public void testHemodialysis_ref1_basicView() {
		GraphEdgesAdder.detectSemanticPatterns = false;
		visualize("../../../../asm_examples/examples/hemodialysisDevice/HemodialysisRef1_MC.asm");
	}

	@Test
	public void testHemodialysis_ref1_semanticView() {
		GraphEdgesAdder.detectSemanticPatterns = true;
		// visualize("../../../../asm_examples/examples/hemodialysisDevice/HemodialysisRef1_MC.asm");
		visualize("../../../../asm_examples/examples/hemodialysisDevice/SCP2017/HemodialysisRef1.asm");
	}

	@Test
	public void testHemodialysis_ref2_basicView() {
		GraphEdgesAdder.detectSemanticPatterns = false;
		visualize("../../../../asm_examples/examples/hemodialysisDevice/HemodialysisRef2_MC.asm");
	}

	@Test
	public void testHemodialysis_ref2_semanticView() {
		GraphEdgesAdder.detectSemanticPatterns = true;
		// visualize("../../../../asm_examples/examples/hemodialysisDevice/HemodialysisRef2_MC.asm");
		visualize("../../../../asm_examples/examples/hemodialysisDevice/SCP2017/HemodialysisRef2.asm");
	}

	@Test
	public void testHemodialysisRef3_basicView() {
		GraphEdgesAdder.detectSemanticPatterns = false;
		visualize("../../../../asm_examples/examples/hemodialysisDevice/HemodialysisRef3_MC.asm");
	}

	@Test
	public void testHemodialysisRef3_semanticView() {
		GraphEdgesAdder.detectSemanticPatterns = true;
		visualize("../../../../asm_examples/examples/hemodialysisDevice/HemodialysisRef3_MC.asm");
	}

	@Test
	public void testStereoacuityTest() {
		GraphEdgesAdder.detectSemanticPatterns = false;
		visualize("../../../../asm_examples/stereoacuity/certifierRaff5.asm");
	}

	@Test
	public void testParRule() {
		GraphEdgesAdder.detectSemanticPatterns = false;
		visualize("models/parRule.asm");
	}

	@Test
	public void testConditionalRule_basicView() {
		GraphEdgesAdder.detectSemanticPatterns = false;
		visualize("models/conditionalRule.asm");
	}

	@Test
	public void testConditionalRule_semanticView() {
		GraphEdgesAdder.detectSemanticPatterns = true;
		visualize("models/conditionalRule.asm");
	}

	@Test
	public void testChooseRule() {
		GraphEdgesAdder.detectSemanticPatterns = false;
		visualize("models/chooseRule.asm");
	}

	@Test
	public void testCarWash() {
		GraphEdgesAdder.detectSemanticPatterns = false;
		visualize("models/CarWash.asm");
	}

	@Test
	public void testChooseRuleOtherwise() {
		GraphEdgesAdder.detectSemanticPatterns = false;
		visualize("models/chooseRuleOtherwise.asm");
	}

	@Test
	public void testForallRule() {
		GraphEdgesAdder.detectSemanticPatterns = false;
		visualize("models/forallRule.asm");
	}

	@Test
	public void testLetRule() {
		GraphEdgesAdder.detectSemanticPatterns = false;
		visualize("models/letRule.asm");
	}

	@Test
	public void testLetRule2() {
		GraphEdgesAdder.detectSemanticPatterns = false;
		visualize("models/letRule2.asm");
	}

	@Test
	public void testForallRule2() {
		GraphEdgesAdder.detectSemanticPatterns = false;
		visualize("models/forallRule2.asm");
	}

	@Test
	public void testForallRule3() {
		GraphEdgesAdder.detectSemanticPatterns = false;
		visualize("models/forallRule3.asm");
	}

	@Test
	public void testMacroCallRule() {
		GraphEdgesAdder.detectSemanticPatterns = false;
		visualize("models/macroCallRule.asm");
	}

	@Test
	public void testMacroCallRule2() {
		GraphEdgesAdder.detectSemanticPatterns = false;
		visualize("models/macroCallRule2.asm");
	}

	@Test
	public void testMultipleStateChangeV_1Basic() {
		GraphEdgesAdder.detectSemanticPatterns = false;
		visualize("models/multipleStateChangeV_1.asm");
	}

	@Test
	public void testMultipleStateChangeV_1Semantic() {
		GraphEdgesAdder.detectSemanticPatterns = true;
		visualize("models/multipleStateChangeV_1.asm");
	}

	@Test
	public void testMultipleStateChangeV_2Basic() {
		GraphEdgesAdder.detectSemanticPatterns = false;
		visualize("models/multipleStateChangeV_2.asm");
	}

	@Test
	public void testMultipleStateChangeV_2Semantic() {
		GraphEdgesAdder.detectSemanticPatterns = true;
		visualize("models/multipleStateChangeV_2.asm");
	}

	/*
	 * New DEMO
	 */

	@Test
	public void testHemodialysisRef3New_Semantic() {
		GraphEdgesAdder.detectSemanticPatterns = true;
		visualize("../../../../asm_examples/ABZ2016/New/HemodialysisRef3_MC.asm");
	}

	@Test
	public void testHemodialysisRef3New_Basic() {
		GraphEdgesAdder.detectSemanticPatterns = false;
		visualize("../../../../asm_examples/ABZ2016/New/HemodialysisRef3_MC.asm");
	}

	@Test
	public void testMutualExclusive() {
		GraphEdgesAdder.detectSemanticPatterns = true;
		visualize("models/mutualExclusive.asm");
	}

	@Test
	public void testTicTacToeSemantic() {
		GraphEdgesAdder.detectSemanticPatterns = true;
		visualize("../../../../asm_examples/examples/ticTacToe/ticTacToe_simulator.asm");
	}

	@Test
	public void testMorraBasic() {
		GraphEdgesAdder.detectSemanticPatterns = false;
		visualize("../../../../asm_examples/examples/simple_ex/MorraCinese/MorraCinese.asm");
	}
}
