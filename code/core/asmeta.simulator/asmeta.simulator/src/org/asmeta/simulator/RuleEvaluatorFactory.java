package org.asmeta.simulator;

class RuleEvaluatorFactory{
	
	private RuleEvaluatorFactory(){}
	
	static RuleEvaluatorFactory RULE_EVAL_FACT = new RuleEvaluatorFactory();

	public RuleEvaluator createRuleEvaluator(State state, Environment environment, ValueAssignment assignment) {
		return new RuleEvaluator(state, environment, assignment);
	}
	
}