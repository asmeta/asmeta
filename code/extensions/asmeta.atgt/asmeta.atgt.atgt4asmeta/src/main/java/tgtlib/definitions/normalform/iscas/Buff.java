package tgtlib.definitions.normalform.iscas;

public class Buff extends Bit {
	private Var v;

	Buff(Var v) {
		super("buff_" + v.name);
		this.v = v;
		this.nameForIscas = "buff_" + v.name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if(o != null && o instanceof Buff) {
			return ((Buff)o).v.equals(v);
		}
		else {
			return false;
		}
	}
	
	@Override
	String toIscas() {
		return nameForIscas + " = " + "BUFF(" + v.nameForIscas + ")";
	}

	@Override
	public <T> T accept(BitVisitor<T> visitor) {
		return visitor.forBuff(this);
	}

	public Var getVar() {
		return v;
	}
}