package atgt.testseqexport;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import atgt.coverage.AsmTestSequence;
import atgt.specification.location.Location;
import atgt.testseqexport.toAvalla;

// generates the avalla with last set as action
public class ToAvallaLastAction extends toAvalla {

	private List<String> stepActions;

	public ToAvallaLastAction(OutputStream out, AsmTestSequence ts, String asmFile, String scenarioName,
			List<String> stepActions) {
		super(out, ts, asmFile, scenarioName);
		this.stepActions = stepActions;
	}

	@Override
	public void saveToStream() {
		// questo è copiato tranne una piccola parte
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
			// NEW 
			// all the monitored except the actions 
			for (Entry<Location, String> p : state.entrySet()) {
				Location v = p.getKey();
				String value = p.getValue();
				// monitored and (not action or (action and) undef) 
				if (v.isMonitored() && ! stepActions.contains(v.getIdExpression().getIdString()) || value.equals("undef")) {
					dst.println("set " + v.toString() + " := " + value + ";");
				} 
			}
			// THE ACTIONS!
			for (Entry<Location, String> p : state.entrySet()) {
				Location v = p.getKey();
				String value = p.getValue();
				// monitored and action and not undef 
				if (v.isMonitored() && stepActions.contains(v.getIdExpression().getIdString()) && ! value.equals("undef")) {
					dst.println("set " + v.toString() + " := " + value + ";");
				} 
			}			
			//if (it.hasNext())
			dst.println("step");
		}
		dst.close();
	}
	
	
}