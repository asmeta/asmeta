package asmeta.evotest.junit2avalla.javascenario;

/**
 * Enum representing different types of parsers. Each parser type has an
 * associated string value.
 */
public enum ParserType {

	/**
	 * Represents the javaParser.
	 */
	JAVA_PARSER("javaParser"),

	/**
	 * Represents a custom parser implementation.
	 */
	CUSTOM_PARSER("customParser");

	/**
	 * The string representation of the parser type.
	 */
	private final String type;

	/**
	 * Constructs a {@code ParserType} with the specified string value.
	 *
	 * @param value the string representation of the parser type
	 */
	ParserType(String value) {
		this.type = value;
	}

	/**
	 * Retrieves the string value associated with the parser type.
	 *
	 * @return the string representation of the parser type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Returns the corresponding {@code ParserType} for the given string value. The
	 * comparison is case-insensitive.
	 *
	 * @param value the string representation of the parser type
	 * @return the corresponding {@code ParserType}
	 * @throws IllegalArgumentException if no matching type is found
	 */
	public static ParserType fromValue(String value) {
		for (ParserType type : ParserType.values()) {
			// case-insensitive
			if (type.getType().equalsIgnoreCase(value)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Unknown parser type: " + value);
	}
	
	/**
	 * Generate a description of the parser types.
	 * 
	 * @return String containing the description of parsing types.
	 */
	public static String getDescrition() {
		StringBuilder sb = new StringBuilder();
		sb.append("The type of the parser, available options are: ");
		for(ParserType type : ParserType.values()) {
			sb.append(type.getType());
			sb.append(", ");
		}
		sb.setLength(sb.length() - 2);
		return sb.toString();
	}

}
