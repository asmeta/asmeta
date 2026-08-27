package tgtlib.generator;
import tgtlib.definitions.TestPredicate;

/**
 * Interfaccia per un filtro da usare per l'esecuzione della verifica dei casi
 * di test. Se un test condition e' accettato dal filtro, allora viene eseguita
 * la verifica attraverso il model checker. In caso contrario viene ignorato.
 * 
 * @author Sax Rinzivillo, Angelo Gargantini
 */

public interface TestConditionFilter<T extends TestPredicate<?,?>> {

	/**
	 * Accept.
	 * 
	 * @param tc
	 *            the tc
	 * 
	 * @return true, if successful
	 */
	public boolean accept(T tc);
}
