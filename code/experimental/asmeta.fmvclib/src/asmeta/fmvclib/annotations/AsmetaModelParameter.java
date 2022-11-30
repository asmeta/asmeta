package asmeta.fmvclib.annotations;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Field annotated with this annotation are used to gather the monitored value of
 * the corresponding ASM location
 * 
 * @author Andrea Bombarda
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Repeatable(AsmetaModelParameters.class)
public @interface AsmetaModelParameter {

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