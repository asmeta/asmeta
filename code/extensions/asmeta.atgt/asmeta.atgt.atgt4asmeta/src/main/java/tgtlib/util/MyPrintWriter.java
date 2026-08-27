package tgtlib.util;

import static tgtlib.preferences.TGLibPreferences.REPORT_DIR;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;

/**
 * generic class for the object to which I can print strings and chars it can be
 * System.out - like a normal PrintWriter or can be a Windows where print the
 * results of some computation Normally it prints on the standard output. TODO
 * use logger instead
 * @author garganti
 * @version $Revision: 1.0 $
 */
public class MyPrintWriter {

	/** r */
	protected PrintStream out;

	/** r */
	public MyPrintWriter() {
		this(System.out);
	}

	/**
	 * r
	 * 
	 * @param out
	 */
	public MyPrintWriter(PrintStream out) {
		this.out = out;
	}

	/* this part to extend PrintWriter ???? */
	/**
	 * method to wite a string to this output
	 * 
	 * @param s
	 */
	public void print(String s) {
		if (out != null)
			out.print(s);
	}

	/**
	 * r
	 * 
	 * @param s
	 */
	public void print(StringBuffer s) {
		print(s.toString());
	}

	/**
	 * Terminate the current line by writing the line separator string. The line
	 * separator string is defined by the system property line.separator, and is
	 * not necessarily a single newline character ('\n')
	 */
	public void println() {
		print('\n');
	}

	/**
	 * v
	 * 
	 * @param s
	 */
	public void println(String s) {
		print(s);
		println();
	}

	/**
	 * Method println.
	 * @param s StringBuffer
	 */
	public void println(StringBuffer s) {
		print(s);
		println();
	}

	/**
	 * for intergers
	 * 
	 * @param c
	 */
	public void print(int c) {
		print(String.valueOf(c));
	}

	/**
	 * print the content of an InputStream
	 * 
	 * @param reader
	
	 * @throws java.io.IOException
	 * @throws IOException */
	public void printInputContent(Reader reader) throws java.io.IOException {
		printInputContent(reader);
	}

	/**
	 * print the content of an InputStream
	 * 
	 * @param in
	
	 * @throws java.io.IOException
	 * @throws IOException */
	public void printInputContent(InputStreamReader in)
			throws java.io.IOException {

		BufferedReader reader = new BufferedReader(in);

		String line;
		do {
			try {
				line = reader.readLine();
				println(line);
			} catch (EOFException e) {
				return;
			}

		} while (line != null);
	}

	/**
	 * Sets the number of characters to expand tabs to. This will be multiplied
	 * by the maximum advance for variable width fonts.
	 * @param size int
	 */
	public void setTabSize(int size) {
		//
		// TODO
	}

	/**
	 * Creates output filename
	 * 
	 * @param spec
	 *            Specification filename
	 * @param mc
	 *            Model checker name
	
	
	 * @param suffix String
	 * @param ext String
	 * @return Report filename * @throws IOException */
	public String createOutputFilename(String spec, String mc, String suffix,
			String ext) throws IOException {
		String reportDir = REPORT_DIR.getValue();
		// get the name of spec -- sis_red.sal
		String reportFileName = spec + "_" + mc + suffix + "." + ext;
		// create reports dir if does not exist
		if (!new File(reportDir).isDirectory())
			new File(reportDir).mkdir();
		// create the file mc_sis_red.txt
		reportFileName = reportDir + System.getProperty("file.separator")
				+ reportFileName;
		if (!new File(reportFileName).exists())
			new File(reportFileName).createNewFile();
		return reportFileName;
	}

}
