package org.asmeta.eclipse.editor.preferences;
  
import org.apache.log4j.Level;
import org.asmeta.eclipse.AsmeeActivator;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.Environment.TimeMngt;
import org.asmeta.simulator.RuleEvaluator;
import org.asmeta.simulator.main.Simulator;
import org.asmeta.simulator.main.Simulator.InvariantTreament;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;


/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = AsmeeActivator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.P_SHUFFLE, true);
		RuleEvaluator.isShuffled = true;
		store.setDefault(PreferenceConstants.P_CHECK_AXIOMS, InvariantTreament.CHECK_STOP.toString());
		Simulator.checkInvariants = InvariantTreament.CHECK_STOP;
		store.setDefault(PreferenceConstants.P_STOP_UPDATESET_EMPTY, true);
		store.setDefault(PreferenceConstants.P_STOP_UPDATESET_TRIVIAL, true);
		store.setDefault(PreferenceConstants.P_ALLOW_UNDEF_MON, false);
		
		store.setDefault(PreferenceConstants.P_TIME_MNGT, TimeMngt.use_java_time.toString());
		Environment.timeMngt = TimeMngt.use_java_time;
		store.setDefault(PreferenceConstants.P_TIME_UNIT, PreferenceConstants.AUTO);
		Environment.currentTimeUnit = null;
		store.setDefault(PreferenceConstants.P_AUTO_DELTA, 1);
		

		store.setDefault(PreferenceConstants.P_DEBUG_PARSER, Level.INFO.toString());
		store.setDefault(PreferenceConstants.P_DEBUG_SIMULATOR, Level.INFO.toString());
		store.setDefault(PreferenceConstants.P_DEBUG_USE_EXTERNAL_FILE,	false);
	}
}
