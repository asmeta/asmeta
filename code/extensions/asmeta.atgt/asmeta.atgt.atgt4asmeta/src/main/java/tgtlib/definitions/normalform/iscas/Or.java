package tgtlib.definitions.normalform.iscas;

public class Or extends BinBit {
	private static long counter = 0l;

	public Or(Bit left, Bit right) {
		super("or_" + left.name + "_" + right.name);
		this.left = left;
		this.right = right;
		this.nameForIscas = "or" + counter;
		counter++;
	}

	@Override
	String toIscas() {
		return nameForIscas + " = " + "OR(" + left.nameForIscas + ", " + right.nameForIscas + ")";
	}

	@Override
	public <T> T accept(BitVisitor<T> visitor) {
		return visitor.forOr(this);
	}

	public static void resetCounter() {
		counter = 0;
	}
}