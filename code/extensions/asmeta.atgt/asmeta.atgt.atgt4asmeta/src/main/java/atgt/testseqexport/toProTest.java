package atgt.testseqexport;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;

import atgt.coverage.AsmTestSequence;
import atgt.specification.location.Location;

/**
 * it exports the test sequence into the ProTest format
 * @author marcoradavelli
 *
 */
public class toProTest extends TestSeqTrad {

	public final boolean printOnlyTransitions;
	
	@Deprecated
	public toProTest(File f, AsmTestSequence ts) {
		this(f, ts, true);
	}
	
	public toProTest(File f, AsmTestSequence ts, boolean printOnlyTransitions) {
		super(f, ts);
		this.printOnlyTransitions = printOnlyTransitions;
	}

	/** The method to actually be called */
	public String translate() {
		String res = "";
		for (Map<Location, String> state : testSequence.allInstructions()) {
			for( Entry<Location, String> p: state.entrySet()){
				Location v = p.getKey();
				String value = p.getValue();
				
				if(v.isMonitored() && (!printOnlyTransitions || "transition".equals(v.toString()))){
					res+=" "+ value;
				}
			}
		}
		res = res.toLowerCase();
		return res.equals("") ? res : res.substring(1);
	}
	
	@Override
	public void saveToStream() {
		PrintWriter fout = new PrintWriter(out);
		fout.print(translate());
		fout.close();
	}
}
