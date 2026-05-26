package asmeta.ai.propgen;

/**
 * Shared request data for AsmetaAI operations.
 */
public final class AsmetaAIOperationRequest {

	private final String asmPath;
	private final String input;
	private final PropertyType propertyType;
	private final int numberOfProperties;
	private final boolean removeComments;
	private final int maxRetries;
	private final AsmetaAIOperationListener listener;

	public AsmetaAIOperationRequest(String asmPath, String input, PropertyType propertyType, int numberOfProperties,
			boolean removeComments, int maxRetries, AsmetaAIOperationListener listener) {
		this.asmPath = asmPath;
		this.input = input == null ? "" : input;
		this.propertyType = propertyType;
		this.numberOfProperties = numberOfProperties;
		this.removeComments = removeComments;
		this.maxRetries = maxRetries;
		this.listener = listener == null ? AsmetaAIOperationListener.NO_OP : listener;
	}

	public String getAsmPath() {
		return asmPath;
	}

	public String getInput() {
		return input;
	}

	public PropertyType getPropertyType() {
		return propertyType;
	}

	public int getNumberOfProperties() {
		return numberOfProperties;
	}

	public boolean isRemoveComments() {
		return removeComments;
	}

	public int getMaxRetries() {
		return maxRetries;
	}

	public AsmetaAIOperationListener getListener() {
		return listener;
	}
}
