package org.asmeta.modeladvisor.texpr;

public abstract class Expression {

	/**
	 * returns the translation to NuSMV as CTL
	 * 
	 * @return
	 */
	abstract public String getSMV();

	@Override
	public int hashCode() {
		return (this.getClass().getName()).hashCode();
	}
	
	@Override
	public String toString() {
		return getSMV();
	}
}
