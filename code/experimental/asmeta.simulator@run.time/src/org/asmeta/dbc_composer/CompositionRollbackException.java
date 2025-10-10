package org.asmeta.dbc_composer;

/**
 * @author Michele Zenoni
 */

public class CompositionRollbackException extends CompositionException {
	private static final long serialVersionUID = 1L;
	public CompositionRollbackException(String message) {
		super(message);
	}
}

