package asmeta.mutation;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;

import asmeta.AsmCollection;
import asmeta.definitions.RuleDeclaration;
import asmeta.structure.Asm;
import asmeta.transitionrules.basictransitionrules.Rule;

public class MutationApplier {

	static private Logger logger = Logger.getLogger(MutationApplier.class);
	
	private AsmCollection asm;

	RuleMutator rmut = new RuleMutator();
	
	public MutationApplier(AsmCollection asm) {
		this.asm = asm;
	}

	public void mutate() {
		// mutate all the rules declared in the ASM
		EList<RuleDeclaration> rules = asm.getMain().getBodySection().getRuleDeclaration();
		for(RuleDeclaration rd: rules ) {
			logger.debug("mutating rule in " + rd.getName());
			Rule tobemutated = rd.getRuleBody();
			Iterator<Rule> it = rmut.visit(tobemutated);
		}
		
	}
	
	
	

}
