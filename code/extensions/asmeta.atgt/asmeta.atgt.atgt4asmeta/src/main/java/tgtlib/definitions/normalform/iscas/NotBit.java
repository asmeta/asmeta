package tgtlib.definitions.normalform.iscas;

public class NotBit extends Not {///Bit {
	
	private static long counter = 0l;
	
	//private Bit v;

	public NotBit(Bit v) {
		//super("not_" + v.name);
		//this.v = v;
		super(v);
		this.nameForIscas = "not" + counter;
		counter++;
	}

	@Override
	public <T> T accept(BitVisitor<T> visitor) {
		return visitor.forNotBit(this);
	}

	/*@Override
	public boolean equals(Object o) {
		if(o != null && o instanceof NotBit) {
			return ((NotBit)o).v.equals(v);
		}
		else {
			return false;
		}
	}*/
	
	/*public Bit getIn(){
		return v;
	}*/

	/*@Override
	String toIscas() {
		return nameForIscas + " = " + "NOT(" + v.nameForIscas + ")";
	}*/
}