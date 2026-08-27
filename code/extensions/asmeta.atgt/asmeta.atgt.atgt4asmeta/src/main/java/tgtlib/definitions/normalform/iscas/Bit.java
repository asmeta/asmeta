package tgtlib.definitions.normalform.iscas;

public abstract class Bit {
	//"name" is still necessary for the the hashCode 
	protected String name;
	//"name" cannot be used as bit name in ISCAS because it could be very long.
	//If the bit identifier is too long, the Nflsat parser can raise a buffer exception.
	//"nameForIscas" is usually built in the following way: operatorName + counter
	protected String nameForIscas;

	public String iscasName(){
		return nameForIscas;
	} 
	
	
	Bit(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	abstract String toIscas();

	abstract public <T> T accept(BitVisitor<T> visitor);
}