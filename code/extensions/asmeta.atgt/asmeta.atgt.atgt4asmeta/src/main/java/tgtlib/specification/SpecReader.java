package tgtlib.specification;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

public abstract class SpecReader<T extends Specification> {

	/**
	 * return if the loader cna load this file: now looks only to the extension.
	 * 
	 * @param f
	 *            the f
	 * 
	 * @return true, if checks if is file loadable
	 */
	public final boolean isFileLoadable(String f){
		// TODO check that is really a file ?
		return f.endsWith("."+getFileExtension());
	}
	
	
	/**
	 * Read the Asm spec in the file f.
	 * 
	 * @param in
	 *            the f
	 * 
	 * @return the aSM specification
	 * 
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws AsmParseException
	 *             the asm parse exception
	 */
	public abstract T read(Reader in) throws ParseException;

	/**
	 * Read the Asm spec in the file f. Use the reader instead if possible
	 * 
	 * @param in
	 *            the f
	 * 
	 * @return the aSM specification
	 * @throws FileNotFoundException 
	 * 
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws AsmParseException
	 *             the asm parse exception
	 */
	public T read(File f) throws ParseException, FileNotFoundException{
		return read(new FileReader(f));
	}

	
	/**
	 * return the file extension for the files this loader can load.
	 * 
	 * @return the file extension
	 */
	public abstract String getFileExtension();

	
}
