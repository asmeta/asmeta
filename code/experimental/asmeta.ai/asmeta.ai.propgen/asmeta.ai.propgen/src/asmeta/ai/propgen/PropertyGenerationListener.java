package asmeta.ai.propgen;

/**
 * Receives progress and debug messages emitted during a property generation
 * session.
 */
public interface PropertyGenerationListener {

	PropertyGenerationListener NO_OP = new PropertyGenerationListener() {
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
