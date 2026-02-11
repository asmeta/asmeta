package org.asmeta.animator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.asmeta.parser.util.AsmetaTermPrinter;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.InvalidInvariantException;
import org.asmeta.simulator.InvalidValueException;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.TermEvaluator;
import org.asmeta.simulator.UpdateClashException;
import org.asmeta.simulator.UpdateSet;
import org.asmeta.simulator.main.AsmModelNotFoundException;
import org.asmeta.simulator.main.MainRuleNotFoundException;
import org.asmeta.simulator.main.Simulator;
import org.asmeta.simulator.value.Value;
import org.eclipse.swt.widgets.Text;

import asmeta.AsmCollection;

public class SimulatorForAnimator extends Simulator {

	VisualizationSimulationI t;

	public SimulatorForAnimator(String modelName, AsmCollection asmp, Environment env, VisualizationSimulationI t)
			throws AsmModelNotFoundException, MainRuleNotFoundException {
		super(modelName, asmp, env);
		this.t = t;
	}

	@Override
	public UpdateSet run(int ntimes) {
		// set the lazyness
		TermEvaluator.setAllowLazyEval(environment.supportsLazyTermEval());
		// get the update set
		UpdateSet updateSet = new UpdateSet();
		boolean exception = false;
		try {
			//
			Set<Location> locationsAlreadySet = getCurrentState().getContrLocs().keySet();
			// do the steps
			updateSet = runNoCatchInv(ntimes);
			// get the previous state
			Map<Location, Value> locationsPrevSet2 = previousState.getContrLocs();
			System.err.println(locationsPrevSet2);
			ArrayList<Location> locationsPrevSet = new ArrayList<>(previousState.getContrLocs().keySet());
			// is there any difference
			locationsPrevSet.removeAll(locationsAlreadySet);
			// System.out.println("SET " + locationsAlreadySet);
			// System.out.println("INITREM " + locationsPrevSet);
			Map<Location, Value> locationsPrevNotSet = new HashMap<Location, Value>();
			for (Location x : locationsPrevSet) {
				Value value = locationsPrevSet2.get(x);
				locationsPrevNotSet.put(x, value);
				//System.err.println(x + " ccc "+ value);
				//System.err.println(x.getClass() + " " + x.getSignature());
			}
			t.setInitValues(locationsPrevNotSet);
		} catch (InvalidInvariantException e) {
			// if no check, ignore the message
			if (checkInvariants != InvariantTreament.NO_CHECK) {
				AsmetaTermPrinter tp = AsmetaTermPrinter.getAsmetaTermPrinter(false);
				String visit = tp.visit(e.getInvariant().getBody());
				t.setInvalidIvariantText("Invariant:\n"+ visit);
			}
			if (checkInvariants == InvariantTreament.CHECK_CONTINUE || checkInvariants == InvariantTreament.NO_CHECK)
				updateSet = e.us;
			exception = true;
		} catch (InvalidValueException ive) {
			t.setInvalidIvariantText(ive.getMessage());
			exception = true;
		} catch (UpdateClashException uce) {
			t.setInvalidIvariantText(uce.getMessage());
			exception = true;
		} catch (Exception e) {
			t.setInvalidIvariantText("Exception "+ e.getClass() + " "+ e.getMessage());
		}
		if (checkInvariants == InvariantTreament.CHECK_STOP)
			t.stopAnimation();		
		// reset the lazyness
		TermEvaluator.recoverAllowLazyEval();
		return updateSet;
	}

}
