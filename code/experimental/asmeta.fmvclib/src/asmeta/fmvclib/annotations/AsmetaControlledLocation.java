package asmeta.fmvclib.annotations;

/**
 * Field annotated with this annotation are used to show the value of the
 * controlled variables
 * 
 * @author Andrea Bombarda
 *
 */

public @interface AsmetaControlledLocation {

	/**
	 * The name of the location in the ASM
	 * 
	 * @return the name of the location in the ASM
	 */
	String asmLocationName();

	/**
	 * The type of the location in the ASM
	 * 
	 * @return the type of the location in the ASM
	 */
	LocationType asmLocationType();

}
