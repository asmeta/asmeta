package org.asmeta.modeladvisor.texpr;

// sometimes expressions
public class SometimeExpression extends TemporalExpression {

	public SometimeExpression(String cond) {
		super(cond);
	}

	@Override
	public String getSMV() {
		return "AG(!(" + prop + "))";
	}

}
