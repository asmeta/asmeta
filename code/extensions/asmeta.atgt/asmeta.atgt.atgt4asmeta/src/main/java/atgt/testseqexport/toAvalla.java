package atgt.testseqexport;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import atgt.coverage.AsmTestSequence;
import atgt.specification.location.Location;
/** export to avalla*/
public class toAvalla extends TestSeqTrad {

	static protected String nomeScenario;
	static protected String fOpened;

	public toAvalla(File f, AsmTestSequence ts, File fOpened) {
		this(f, ts, fOpened.getName());
	}

	/**
	 * Instantiates a new to avalla.
	 *
	 * @param f       the file where the file is written
	 * @param ts      the ts
	 * @param fOpened the f opened
	 */
	public toAvalla(File f, AsmTestSequence ts, String asmFile) {
		super(f, ts);
		// remove extension from file name
		nomeScenario =  f.getName().replaceFirst("[.][^.]+$", "");
		toAvalla.fOpened = asmFile;
	}
	/**
	 * 
	 * @param out output strema where to save scenario
	 * @param ts scenario
	 * @param asmFile asm, load in avalla
	 * @param scenarioName first line in avalla, name of the scenario (no extension)
	 */
	public toAvalla(OutputStream out, AsmTestSequence ts, String asmFile, String scenarioName) {
		super(out,ts);
		nomeScenario = scenarioName;
		toAvalla.fOpened = asmFile;
	}
	
	
	@Override
	public void saveToStream() {
		PrintStream dst = new PrintStream(out);
		List<Map<Location, String>> instrList = testSequence.allInstructions();
		Iterator<Map<Location, String>> it = instrList.iterator();
		dst.println("scenario " + nomeScenario + "\n" + "\n" + "load ./" + fOpened + "\n");
		// write as comment the test goal
		dst.println("//// test name " + testSequence.getName());
		dst.println("//// generated for (test goal): " + testSequence.getGeneratedFor().toString());
		
		while (it.hasNext()) {
			Map<Location, String> state = it.next();
			// first all the checks for controlled
			for (Entry<Location, String> p : state.entrySet()) {
				Location v = p.getKey();
				String value = p.getValue();
				assert (v.isControlled() || v.isMonitored());
				if (v.isControlled()) {
					dst.println("check " + v.toString() + " = " + value + ";");
				} 
			}
			// all the monitored
			for (Entry<Location, String> p : state.entrySet()) {
				Location v = p.getKey();
				String value = p.getValue();
				if (v.isMonitored()) {
					dst.println("set " + v.toString() + " := " + value + ";");
				} 
			}
			//if (it.hasNext())
			dst.println("step");
		}
		dst.close();
	}
}
