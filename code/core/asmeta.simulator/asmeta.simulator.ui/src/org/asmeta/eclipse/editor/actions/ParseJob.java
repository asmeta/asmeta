package org.asmeta.eclipse.editor.actions;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;

import org.apache.log4j.Appender;
import org.apache.log4j.SimpleLayout;
import org.apache.log4j.WriterAppender;
import org.asmeta.eclipse.AsmeeActivator;
import org.asmeta.eclipse.AsmetaUtility;
import org.asmeta.parser.ASMParser;
import org.asmeta.parser.ParseException;
import org.asmeta.parser.ParserResultLogger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.preference.IPreferenceStore;

import asmeta.AsmCollection;
/**
 * Parse Job. To be called when parsing
 * 
 * @author garganti
 *
 */
public class ParseJob extends Job {

	public static ParserResultLogger parseResult = null;

	private static OutputStream out;

	private IFile file;

	private org.eclipse.ui.console.IOConsole console;

	private static String JOB_NAME = "parsing ";

	private AsmCollection asm;

	public ParseJob(IFile doc, org.eclipse.ui.console.IOConsole console) {
		super(JOB_NAME + doc.getName());
		this.file = doc;
		this.console = console;
	}

	@Override
	public IStatus run(IProgressMonitor monitor) {

		try {
			IPreferenceStore store = AsmeeActivator.getDefault().getPreferenceStore();
			AsmetaUtility.setUpLogger(store);
			// 
			// transferring content
			// runJob.beginTask("exporting to XMI", doc.getNumberOfLines());
			// runJob.subTask("Reading asmm specification");
			monitor.worked(0);

			if (monitor.isCanceled()) {
				asm = null;
				return Status.CANCEL_STATUS;
			}
			// call the asm2xmi with the file
			// from IFile to File
			File jFile = file.getLocation().toFile();
			monitor.beginTask("READING " + jFile.getAbsolutePath(), 10);
			// if the first time, initialize the parserresult
			if (parseResult == null) {
				parseResult = ASMParser.getResultLogger();
				// set the outstream
				out = console.newOutputStream();
				Appender consoleApp = new WriterAppender(new SimpleLayout(),out);
				parseResult.addAppender(consoleApp);
			}
			// forse parseResult dovrebbe restituire un logger???
			// false: do not export to xmi
			asm = ASMParser.setUpReadAsm(jFile);
			// done and cleaning
			monitor.done();
			out.flush();
			// DO NOT CLOSE; IT WILL BE REUSED
			// out.close();
			return Status.OK_STATUS;
		} catch (ParseException t) {
			parseResult = ASMParser.getResultLogger();
			new PrintStream(out).print("Error in parsing: " + t.getMessage());
			// TODO metti lo status con errore
			asm = null;
			return Status.CANCEL_STATUS;			
		} catch (Exception t) {
			// generic exception
			parseResult = ASMParser.getResultLogger();
			t.printStackTrace(new PrintStream(out));
			// TODO metti lo status con errore
			asm = null;
			return Status.CANCEL_STATUS;
		}
	}

	/**
	 * returns the last asm parsed, null, in case of error or cancel
	 * 
	 * @return the asm
	 */
	public AsmCollection getAsm() {
		return asm;
	}

}
