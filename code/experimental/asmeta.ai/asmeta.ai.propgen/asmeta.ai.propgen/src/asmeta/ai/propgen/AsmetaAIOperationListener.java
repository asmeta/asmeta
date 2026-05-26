package asmeta.ai.propgen;

/**
 * Receives progress and debug messages emitted while an AsmetaAI operation runs.
 */
public interface AsmetaAIOperationListener {

	AsmetaAIOperationListener NO_OP = new AsmetaAIOperationListener() {
		@Override
		public void onProgress(String message) {
			// No operation.
		}

		@Override
		public void onDebug(String message) {
			// No operation.
		}
	};

	void onProgress(String message);

	void onDebug(String message);
}
