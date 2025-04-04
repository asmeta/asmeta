package org.asmeta.nusmv;


/**
 * The Class InconsistentUpdateExceptionis thrown when an inconsistent update is found when building the asmeta file
 */
public class InconsistentUpdateException extends RuntimeException {

	private static final long serialVersionUID = 130561938124943292L;
	
	public final String location;
	public final String condition;

	public final String val1;
	public final String val2;

	/**
	 * Instantiates a new inconsistent update exception.
	 *
	 * @param cond the condition when this happens
	 * @param location the location
	 * @param value the value (first value)
	 * @param alreadySetVal the already set val (second value)
	 */
	public InconsistentUpdateException(String cond, String location, String value, String alreadySetVal) {
		super("location " + location + " is set to " + value + " but it was set to " + alreadySetVal 
				+ " under condition " + cond);
		assert ! value.equals(alreadySetVal);
		this.location = location;
		this.condition = cond;
		this.val1 = value;
		this.val2 = alreadySetVal;
	}
}
