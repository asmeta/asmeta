package asmeta.fmvclib.annotations;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Field annotated with this annotation are used to show the value of the
 * controlled variables
 * 
 * @author Andrea Bombarda
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Repeatable(AsmetaControlledLocations.class)
public @interface AsmetaControlledLocation {

	/**
	 * The name of the location in the ASM
	 * 
	 * @return the name of the location in the ASM
	 */
	String asmLocationName() default "";

}

@Retention(RetentionPolicy.RUNTIME)
@interface AsmetaControlledLocations {
	AsmetaControlledLocation[] value();
}
