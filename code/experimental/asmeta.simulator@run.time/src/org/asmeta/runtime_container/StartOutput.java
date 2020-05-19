package org.asmeta.runtime_container;

import java.io.Serializable;





// TODO: Auto-generated Javadoc
/**
 * The Class StartOutput.
 */
public class StartOutput implements Serializable {
	
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;


	
	/** The id. */
	private int id; // id che deve essere ritornatp
	
	/** The message. */
	private String reason = null; //il messaggio che deve essere printato
	
	/**
	 * Instantiates a new start output.
	 *
	 * @param id the id
	 * @param message the message
	 */
	public StartOutput(int id, String message) {
	   this.id = id;
	   this.reason = message;
	}
	
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("The id is: " + id + "\n");
		sb.append("Reason: " + reason + "\n");
		return sb.toString();
	}
	
      
}
