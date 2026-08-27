package tgtlib.definitions.normalform.iscas;

public class Xor extends BinBit {
	private static long counter = 0l;
	
	Xor(Bit left, Bit right) {
		super("xor_" + left.name + "_" + right.name);
		this.left = left;
		this.right = right;
		this.nameForIscas = "xor" + counter;
		counter++;
	}

	@Override
	String toIscas() {
		return nameForIscas + " = " + "XOR(" + left.nameForIscas + ", " + right.nameForIscas + ")";
	}

	@Override
	public <T> T accept(BitVisitor<T> visitor) {
		return visitor.forXor(this);
	}

	public static void resetCounter() {
		counter = 0;
	}
}