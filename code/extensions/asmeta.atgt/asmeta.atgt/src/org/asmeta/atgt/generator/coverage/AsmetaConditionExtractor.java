package org.asmeta.atgt.generator.coverage;

import java.util.List;
import java.util.Vector;

import org.asmeta.parser.util.ReflectiveVisitor;
import org.asmeta.simulator.IRuleVisitor;

import asmeta.transitionrules.basictransitionrules.BlockRule;
import asmeta.transitionrules.basictransitionrules.ExtendRule;
import asmeta.transitionrules.basictransitionrules.ForallRule;
import asmeta.transitionrules.basictransitionrules.LetRule;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.basictransitionrules.SkipRule;
import asmeta.transitionrules.basictransitionrules.TermAsRule;
import asmeta.transitionrules.derivedtransitionrules.CaseRule;
import asmeta.transitionrules.turbotransitionrules.SeqRule;
import extgt.coverage.mcdc.ShallowExpressionNegator;
import tgtlib.definitions.NamedTerm;
import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.Expression;

public class AsmetaConditionExtractor extends  ReflectiveVisitor<List<NamedTerm>> implements IRuleVisitor<List<NamedTerm>>{
	
	
	AsmetaToExprTrans translator = new AsmetaToExprTrans(); 

	@Override
	public List<NamedTerm> visit(Rule rule) {
		return visit((Object) rule);
	}

	@Override
	public List<NamedTerm> visit(SkipRule rule) {
		return new Vector<NamedTerm>();
	}

	@Override
	public List<NamedTerm> visit(asmeta.transitionrules.basictransitionrules.UpdateRule rule) {
		return new Vector<NamedTerm>();
	}

	@Override
	public List<NamedTerm> visit(TermAsRule rule) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public List<NamedTerm> visit(BlockRule block) {
		return addResults(this,block);
	}

	@Override
	public List<NamedTerm> visit(SeqRule seq) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public List<NamedTerm> visit(asmeta.transitionrules.basictransitionrules.ConditionalRule rule) {
		return distributeOverConditional(rule);
	}

	@Override
	public List<NamedTerm> visit(ExtendRule rule) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public List<NamedTerm> visit(LetRule rule) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public List<NamedTerm> visit(asmeta.transitionrules.basictransitionrules.ChooseRule rule) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public List<NamedTerm> visit(ForallRule rule) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public List<NamedTerm> visit(MacroCallRule rule) throws Exception {
		assert rule.getParameters().size() == 0;
		return visit(rule.getCalledMacro().getRuleBody());
	}

	@Override
	public List<NamedTerm> visit(CaseRule rule) {
		throw new RuntimeException("not implemented yet");
	}
	
	
	/** calls the visitor for every rule in the do statement and returns the results
	 * 
	 * @param d
	 * @param visitor
	 * @param block 
	 * @return
	 */
	private List<NamedTerm> addResults(IRuleVisitor<List<NamedTerm>> visitor, BlockRule block) {
		Vector<NamedTerm> list = new Vector<NamedTerm>();
		int i = 1;
		for (Rule r : block.getRules()) {
			System.out.println("rule " + r.getClass());
			List<NamedTerm> l = visitor.visit(r);
			// add the name
			for (NamedTerm ne : l) {
				ne.setName(ne.getName() + Integer.valueOf(i));
			}
			list.addAll(l);
			i++;
		}
		return list;
	}

	/** distribute the visitor over the two conditions
	 * 
	 * @param ite
	 * @param ignoreElse: ignore the else (also if else is not null). if not ignore, consider also if it si null
	 * @param visitor
	 * @return
	 */
	protected List<NamedTerm> distributeOverConditional(asmeta.transitionrules.basictransitionrules.ConditionalRule ite) {
		List<NamedTerm> result = new Vector<NamedTerm>();

		// get the guard
		Expression guard = translator.visit(ite.getGuard());
		System.out.println("guard  "+ guard);
		List<NamedTerm> then_list = this.visit(ite.getThenRule());
		if (!then_list.isEmpty()) {
			for (NamedTerm tc : then_list) {
				NamedTerm tc_i = new NamedTerm("T" + tc.getName(),
						new AndExpression(guard, tc.getCondition()));
				result.add(tc_i);
			}
		} else {
			// if the then part does not contain extra rules, just add the guard
			result.add(new NamedTerm("T", guard));
		}
		// for else part: get the decisions in the else part
		Rule elseR = ite.getElseRule();
		if (elseR != null) {
			// else 
			Expression notGuard = guard.accept(ShallowExpressionNegator.negate);
			List<NamedTerm> else_list = null;
			// if contains else
			if (elseR != null) {
				else_list = this.visit(elseR);
				for (NamedTerm tc : else_list) {
					// add not guard and tc
					NamedTerm tc_i = new NamedTerm("F" + tc.getName(),
						new AndExpression(notGuard, tc.getCondition()));
					result.add(tc_i);
				}
				
			} 
			// if else list was empty because 
			// if else was something but gave an empty list 
			if (else_list == null || else_list.isEmpty()) {
				result.add(new NamedTerm("F", notGuard));
			}
		}
		return result;
	}
}