package asmeta.mutation;

import java.util.Iterator;
import java.util.List;

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

	public IteratorOfIterator<AsmCollection> mutate() {
		// iterate over all the rule declarations
		Iterator<RuleDeclaration> x = asm.getMain().getBodySection().getRuleDeclaration().iterator();
		Iterator<Iterator<AsmCollection>> ioi = new Iterator<Iterator<AsmCollection>>() {
			@Override
			public boolean hasNext() {
				return x.hasNext();
			}
			@Override
			public Iterator<AsmCollection> next() {
				RuleDeclaration currentRDecl = x.next();
				logger.debug("mutating rule in " + currentRDecl.getName());
				Rule tobemutated = currentRDecl.getRuleBody();
				Iterator<Rule> it = rmut.visit(tobemutated);
				return new Iterator<AsmCollection>() {
					@Override
					public boolean hasNext() {
						return it.hasNext();
					}
					@Override
					public AsmCollection next() {
						Rule newrule = it.next();
						currentRDecl.setRuleBody(newrule);
						return asm;
					}
				};
			}
		};
		return new IteratorOfIterator<AsmCollection>(ioi);
	}

	
	
}

