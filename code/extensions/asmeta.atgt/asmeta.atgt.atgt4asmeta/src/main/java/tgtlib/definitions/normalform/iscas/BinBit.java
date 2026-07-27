package tgtlib.definitions.normalform.iscas;

/** binary bits
 * 
 * @author garganti
 *
 */
abstract class BinBit extends Bit {

	protected Bit left, right;

	
	BinBit(String name) {
		super(name);
	}

	public Bit getLeft() {
		return left;		
	}

	public Bit getRight() {
		return right;		
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		// if the operator is different, return 
		if (this.getClass() != o.getClass()) return false;
		if(o != null && o instanceof BinBit) {
			BinBit binBit = (BinBit)o;
			return (binBit.left.equals(left) && binBit.right.equals(right)) ||
					(binBit.left.equals(right) && binBit.right.equals(left));
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return left.hashCode() + right.hashCode();
	}
}
