package annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Field annoted with this annotation are used to gather the monitored value of
 * the corresponding ASM location
 * 
 * @author Andrea Bombarda
 *
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface AsmetaModelParameter {

	enum LocationType {
		INTEGER, ENUM, REAL, STRING, BOOLEAN, CHAR, BAG, SEQ, SET, TUPLE, MAP, UNDEF
	}

	/**
	 * The name of the location in the ASM
	 * 
	 * @return the name of the location in the ASM
	 */
	public String asmLocationName() default "";

	/**
	 * The type of the location in the ASM
	 * 
	 * @return the type of the location in the ASM
	 */
	public LocationType asmLocationType() default LocationType.INTEGER;

	/**
	 * The value of the location in the ASM
	 * 
	 * @return the value of the location in the ASM
	 */
	public String asmLocationValue() default "";
}
