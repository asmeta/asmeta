package org.asmeta.modeladvisor.texpr;

public class Implies extends Expression {
	private Expression left, right;

	public Implies(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public String getSMV() {
		return left.getSMV() + "->" + right.getSMV();
	}
}
