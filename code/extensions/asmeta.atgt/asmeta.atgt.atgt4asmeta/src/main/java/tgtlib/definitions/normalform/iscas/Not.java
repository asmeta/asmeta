package tgtlib.definitions.normalform.iscas;

abstract class Not extends Bit {
	protected Bit v;

	public Not(Bit v) {
		super("not_" + v.name);
		this.v = v;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if(o != null && o instanceof Not) {
			return ((Not)o).v.equals(v);
		}
		else {
			return false;
		}
	}
	
	public Bit getNegated(){
		return v;
	}

	@Override
	String toIscas() {
		return nameForIscas + " = " + "NOT(" + v.nameForIscas + ")";
	}
}