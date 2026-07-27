package tgtlib.definitions.normalform.iscas;

public class NotVar extends Not {//Bit {
	//private Var negVar;

	NotVar(Var negVar) {
		//super("not_" + negVar.name);
		//this.negVar = negVar;
		super(negVar);
		this.nameForIscas = name;
	}

	@Override
	public <T> T accept(BitVisitor<T> visitor) {
		return visitor.forNotVar(this);
	}
	@Override
	public Var getNegated(){
		return (Var) v;
	}

	
	/*@Override
	public boolean equals(Object o) {
		if(o != null && o instanceof NotVar) {
			return ((NotVar)o).negVar.equals(negVar);
		}
		else {
			return false;
		}
	}

	@Override
	String toIscas() {
		return nameForIscas + " = " + "NOT(" + negVar.nameForIscas + ")";
	}*/
}
