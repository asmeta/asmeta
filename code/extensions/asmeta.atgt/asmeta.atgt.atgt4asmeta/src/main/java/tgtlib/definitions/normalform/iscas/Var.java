package tgtlib.definitions.normalform.iscas;

import tgtlib.definitions.expression.IdExpression;

public class Var extends Bit {
	private String var;
	private IdExpression idExpression;

	public Var(String var) {
		super(var);
		this.var = var;
		this.nameForIscas = var;
	}

	Var(IdExpression idExpression) {
		this(idExpression.getID().getIdString());
		this.idExpression = idExpression;
	}

	public IdExpression getIdExpression() {
		return idExpression;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if(o != null && o instanceof Var) {
			return ((Var)o).var.equals(var);
		}
		else {
			return false;
		}
	}

	@Override
	String toIscas() {
		return "INPUT(" + nameForIscas + ")";
	}

	@Override
	public <T> T accept(BitVisitor<T> visitor) {
		return visitor.forVar(this);
	}
}
