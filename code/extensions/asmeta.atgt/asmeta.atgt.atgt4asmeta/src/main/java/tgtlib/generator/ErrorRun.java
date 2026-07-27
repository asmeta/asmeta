package tgtlib.generator;

/**Classe che estende Exception. Serve per lanciare l'eccezione nel caso
 * in cui il programma Hysat o Yices rileva un errore nel modello tradotto.
 * La classe fa un overridden del metodo toString della classe padre, per poter
 * modificare il testo del messaggio dell'eccezione.
 */

public class ErrorRun extends RuntimeException {

	private final static long serialVersionUID = 1;

	protected String error;

	/**Costruttore della classe.
	 * @param error contiene la stringa con la descrizione dettagliata dell'errore.
	 */
	public ErrorRun (final String error) {
		/*Il costruttore di Exception chiamato inizializza
		 * la variabile privata message.*/
		super ("Errore nel modello." + error);
		this.error = error;
	}

	/**Override del metodo toString della classe Exception.
	 * Serve per creare il messaggio finale di errore.
	 * @return ritorna la stringa del messaggio di errore.
	 */
	/*public String toString () {
		return getMessage() + ": " + error;
	}*/
}