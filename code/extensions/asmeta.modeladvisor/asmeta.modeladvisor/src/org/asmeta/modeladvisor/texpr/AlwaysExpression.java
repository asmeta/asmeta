package org.asmeta.modeladvisor.texpr;

public class AlwaysExpression extends TemporalExpression {

	public AlwaysExpression(String cond) {
		super(cond);
	}

	@Override
	public String getSMV() {
		return "AG(" + prop + ")";
	}

}
