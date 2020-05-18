package org.asmeta.modeladvisor.texpr;

public class Ef extends TemporalExpression {

	public Ef(String cond) {
		super(cond);
	}

	@Override
	public String getSMV() {
		return "EF(" + prop + ")";
	}

}
