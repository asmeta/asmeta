package org.asmeta.modeladvisor.texpr;

public class SimpleExpression extends TemporalExpression {

	public SimpleExpression(String cond) {
		super(cond);
	}

	@Override
	public String getSMV() {
		return prop;
	}
	
}
