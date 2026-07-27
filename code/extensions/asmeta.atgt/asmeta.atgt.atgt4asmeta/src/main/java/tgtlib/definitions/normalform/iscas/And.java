package tgtlib.definitions.normalform.iscas;

public class And extends BinBit {
	
	private static long counter = 0L;

	And(Bit left, Bit right) {
		super("and_" + left.name + "_" + right.name);
		this.left = left;
		this.right = right;
		this.nameForIscas = "and" + counter;
		counter++;
	}

	@Override
	String toIscas() {
		return nameForIscas + " = " + "AND(" + left.nameForIscas + ", " + right.nameForIscas + ")";
	}

	@Override
	public <T> T accept(BitVisitor<T> visitor) {
		return visitor.forAnd(this);
	}

	public static void resetCounter() {
		counter = 0;
	}
}