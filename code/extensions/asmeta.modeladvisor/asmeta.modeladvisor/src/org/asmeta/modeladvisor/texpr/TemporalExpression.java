package org.asmeta.modeladvisor.texpr;

/**
 * represents a temporal expression
 * 
 * @author garganti
 * 
 */
public abstract class TemporalExpression extends Expression {
	protected String prop;

	public TemporalExpression(String cond) {
		prop = cond;
	}
	
	@Override
	public int hashCode() {
		// TODO: aggiusta in modo piu' efficiente
		return (this.getClass().getName() + prop).hashCode();
	}
}
