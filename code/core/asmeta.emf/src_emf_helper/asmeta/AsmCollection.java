package asmeta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import asmeta.structure.Asm;

/** represents the Asm parsed by the parser in a single file
 * it has the main Asm (which has name equal to the file)
 * and all other Asm imported
 * 
 * @author garganti
 *
 */
public class AsmCollection implements Iterable<Asm> {

	List<Asm> allAsms;
	
	// build the collection of ASM
	public AsmCollection(Collection<Asm> values) {
		allAsms = new ArrayList<Asm>(values);
	}
	
	// add this one as first one
	public void addMain(Asm asm) {
	    allAsms.add(0, asm);			
	}

	/** return the main asm */
	public Asm getMain(){
		return allAsms.get(0);
	}

	@Override
	public Iterator<Asm> iterator() {
		return allAsms.iterator();
	}

}
