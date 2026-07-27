package tgtlib.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import tgtlib.specification.ParseException;

/**
 * object to build new Projects
 * 
 * @author garganti
 * 
 * @param <P>
 */
public abstract class ProjectFactory<P extends Project<?, ?, ?,?>> {

	/** created a new empty project iwthoyut a name
	 * 
	 * @return
	 */
	protected abstract P createNewEmptyProject();

	/**
	 * load a new project in a zip file: the specification (as file), 
	 * load the tsg (from .tsg files). 
	 * It does not compute the test tree, since only the tools know how to do that
	 * 
	 * @param fileZip
	 *            the file zip (with zip extension) which contains the project
	 * @return the project
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ZipException
	 *             the zip exception
	 * @throws ParseException 
	 */

	public P load(String fileZip) throws IOException, ParseException {
		// the name is the name of the zip file - ".zip"
		assert fileZip.endsWith(".zip");
		String name = fileZip.substring(0, fileZip.length() - 4);
		
		P p = createNewEmptyProject();
		p.name = name;

		File sourceZipFile = new File(fileZip);
		if (!sourceZipFile.exists())
			throw new FileNotFoundException();

		// Open Zip file for reading
		ZipFile zipFile = new ZipFile(sourceZipFile);

		// find the spec
		for (Enumeration<?> ze = zipFile.entries(); ze.hasMoreElements();) {
			ZipEntry z = (ZipEntry) ze.nextElement();
			if (p.isSpecLoadable(z.getName())) {
				p.readSpec(new java.io.InputStreamReader(zipFile.getInputStream(z)));
				break;
			}
		}
		//
		// FIXME read the tests without the test tree???
		
		// read the tsg files
		for (Enumeration<?> ze = zipFile.entries(); ze.hasMoreElements();) {
			ZipEntry z = (ZipEntry) ze.nextElement();
			if (z.getName().endsWith(".tsg")) {
				p.readTest(new java.io.InputStreamReader(zipFile
						.getInputStream(z)));
			}
		}
		zipFile.close();
		return p;
	}
}
