package asmeta.evotest.junit2avalla.model.terms;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a Java assertion term used to compare an actual value with an
 * expected value in the Java context.
 * <p>
 * This class is part of the Java-specific term hierarchy and is used to define
 * assertions where an actual result is compared to an expected result. It
 * provides methods to retrieve and set both the actual and expected values.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
public class JavaAssertionTerm extends JavaTerm {

	/**
	 * The expected value to compare against the actual value in the assertion.
	 */
	private String expected;

	/**
	 * The actual value to be compared in the assertion.
	 */
	private String actual;

	/**
	 * The type of the actual value. (String, Number --> Primitive)
	 */
	private boolean primitive;

}
