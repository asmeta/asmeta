package asmeta.asmetal2java.codegen.config;

/**
 * Controls how translated {@code choose} rules select and record their values.
 */
public enum ChooseMode {

	/** Select a random value without recording choices. */
	FLAKY("flaky"),

	/** Always select the first available value and do not record choices. */
	NO_SHUFFLE("noShuffle"),

	/** Select a random value and record it for deterministic Avalla scenarios. */
	PICK("pick");

	private final String value;

	ChooseMode(String value) {
		this.value = value;
	}

	public static ChooseMode fromValue(String value) {
		for (ChooseMode mode : values()) {
			if (mode.value.equalsIgnoreCase(value)) {
				return mode;
			}
		}
		throw new IllegalArgumentException("Unsupported chooseMode: " + value
				+ ". Expected one of: flaky, noShuffle, pick.");
	}

	public String getValue() {
		return value;
	}

	public boolean recordsChoices() {
		return this == PICK;
	}
}
