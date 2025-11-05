package asmeta.evotest.experiments.model;

import java.util.HashMap;
import java.util.Map;

import org.asmeta.avallaxt.validation.RuleExtractorFromMacroDecl;
import org.asmeta.xt.validator.SimulatorWCov;

import asmeta.AsmCollection;
import asmeta.definitions.RuleDeclaration;
import asmeta.structure.Asm;
import asmeta.transitionrules.basictransitionrules.ChooseRule;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.ForallRule;
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.basictransitionrules.UpdateRule;

public class ModelDataCollector {
	
	/**
	 * Computes basic structural metrics for the main ASM in the given collection.
	 * The returned map includes counts of macro rules, update rules, forall rules,
	 * branch points, and total considered rules.
	 *
	 * @param asmCollection the ASM collection containing the main ASM
	 * @return a map with model-level metrics keyed by:
	 *         {@code asm}, {@code n_macro}, {@code n_update}, {@code n_forall},
	 *         {@code n_branch}, {@code n_rule}
	 */
	public static Map<String, String> collectModelData(AsmCollection asmCollection) {
		Map<String, String> modelsData = new HashMap<>();
		Asm asm = asmCollection.getMain();
		String asmName = asm.getName();
		int totMacroRules = asm.getBodySection().getRuleDeclaration().size();
		int totUpdateRules = 0;
		int totForallRules = 0;
		int totBranches = 0;
		int totRules = 0;
		for (RuleDeclaration rd : asm.getBodySection().getRuleDeclaration()) {
			for (Rule r : RuleExtractorFromMacroDecl.getAllContainedRules((MacroDeclaration) rd)) {
				if (SimulatorWCov.CONSIDERED_RULES.stream().anyMatch(ruleType -> ruleType.isInstance(r))) {
					totRules++;
					if (r instanceof ConditionalRule || r instanceof ForallRule || r instanceof ChooseRule) {
						totBranches++;
						if (r instanceof ForallRule)
							totForallRules++;
					}
					if (r instanceof UpdateRule)
						totUpdateRules++;
				}
			}
		}
		modelsData.put("asm", String.valueOf(asmName));
		modelsData.put("n_macro", String.valueOf(totMacroRules));
		modelsData.put("n_update", String.valueOf(totUpdateRules));
		modelsData.put("n_forall", String.valueOf(totForallRules));
		modelsData.put("n_branch", String.valueOf(totBranches));
		modelsData.put("n_rule", String.valueOf(totRules));
		return modelsData;
	}

}
