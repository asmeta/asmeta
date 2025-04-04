package org.asmeta.modeladvisor.metaproperties;

import static java.lang.System.out;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.asmeta.modeladvisor.texpr.AlwaysExpression;
import org.asmeta.modeladvisor.texpr.Expression;
import org.asmeta.modeladvisor.texpr.TemporalExpression;
import org.asmeta.nusmv.InconsistentUpdateException;
import org.asmeta.nusmv.UpdateMap;
import org.asmeta.nusmv.main.AsmetaSMV;

/**
 * Verifica degli update inconsistenti
 *
 */
public class InconsistentUpdate extends Checker {
	private UpdateMap updateMap;
	private Map<String, String> nusmvNameToLocation;
	private Map<String, Map<String[], String>> smvPropMap;
	// location 
	public Map<String, List<String[]>> inconUpdate;
	private Set<String> funcNamesForMP1;

	public InconsistentUpdate(UpdateMap updateMap, Map<String, String> nusmvNameToLocation,
			Set<String> funcNamesForMP1) {
		this.updateMap = updateMap;
		this.nusmvNameToLocation = nusmvNameToLocation;
		this.funcNamesForMP1 = funcNamesForMP1;
	}
	// build the inconsistent update when the update is already discovered
	public InconsistentUpdate() {		
	}
	
	@Override
	public Set<Expression> createNuSmvProperties() {
		TemporalExpression property;
		Map<String, String> map;
		Map<String[], String> condsMap;
		Object[] conds;
		String[] condsValues;
		smvPropMap = new TreeMap<String, Map<String[], String>>();
		System.out.println("updateMap " + updateMap.keySet());
		for (String location : updateMap.keySet()) {
			if (funcNamesForMP1 != null) {
				boolean found = false;
				for (String f : funcNamesForMP1) {
					if (location.equals(f) || location.startsWith(f + "_")) {
						found = true;
						break;
					}
				}
				if (!found) {
					continue;
				}
			}
			condsMap = new HashMap<String[], String>();
			smvPropMap.put(location, condsMap);
			map = updateMap.get(location);
			conds = map.keySet().toArray();
			for (int i = 0; i < conds.length; i++) {
				for (int j = i + 1; j < conds.length; j++) {
					condsValues = new String[4];
					condsValues[0] = (String) conds[i];
					condsValues[2] = map.get(conds[i]);
					condsValues[1] = (String) conds[j];
					condsValues[3] = map.get(conds[j]);

					// if the updating values are equal, the property
					// is true in any case and so there is no need to check it
					if (!condsValues[2].equals(condsValues[3])) {
						// System.err.println(condsValues[2] + "\t" + condsValues[3]);
						// Always
						/*
						 * "AG(((" + condsValues[0] + ") & (" + condsValues[1] + ")) -> ((" +
						 * condsValues[2] + ") = (" + condsValues[3] + ")))";
						 */
						property = new AlwaysExpression("((" + condsValues[0] + ") & (" + condsValues[1] + ")) -> (("
								+ condsValues[2] + ") = (" + condsValues[3] + "))");
						condsMap.put(condsValues, property.getSMV());
						smvProp.add(property);
						// System.out.println(property.getSMV());
					}
				}
			}
		}
		return smvProp;
	}

	/**
	 * Stampa gli update inconsistenti; sono quelli che non soddisfano la
	 * metaproprieta' di assenza di update inconsistente.
	 */
	@Override
	public void evaluation(Map<String, Boolean> results) {
		Set<String> locations = smvPropMap.keySet();
		assert results != null;
		inconUpdate = new TreeMap<String, List<String[]>>();
		Map<String[], String> map;
		// System.out.println(results);
		for (String location : locations) {
			map = smvPropMap.get(location);
			for (String[] condsValues : smvPropMap.get(location).keySet()) {
				String key = map.get(condsValues);
				assert key != null : key;
				assert results.containsKey(key) : key;
				if (!results.get(key)) {
					if (inconUpdate.containsKey(location)) {
						inconUpdate.get(location).add(condsValues);
					} else {
						List<String[]> l = new ArrayList<String[]>();
						l.add(condsValues);
						inconUpdate.put(location, l);
					}
				}
			}
		}
	}

	@Override
	public void printResults() {
		out.println("MP1: No inconsistent update is ever performed");
		boolean noViolation = true;
		for (String location : inconUpdate.keySet()) {
			for (String[] condsValues : inconUpdate.get(location)) {
				String cond1 = AsmetaSMV.replaceVarsWithLocations(condsValues[0], nusmvNameToLocation);
				String cond2 = AsmetaSMV.replaceVarsWithLocations(condsValues[1], nusmvNameToLocation);
				String val1 = AsmetaSMV.replaceVarsWithLocations(condsValues[2], nusmvNameToLocation);
				String val2 = AsmetaSMV.replaceVarsWithLocations(condsValues[3], nusmvNameToLocation);
				String location2 = nusmvNameToLocation.get(location);
				out.println("Location " + location2 + " is updated to values " + val1 + " and " + val2
				+ " when are satisfied simultaneously the conditions\n\t" + cond1 + "\n\tand\n\t" + cond2 + ".");
				noViolation = false;
			}
		}
		if (noViolation) {
			out.println("NONE");
		}
		out.println();
	}

	public void printResults(InconsistentUpdateException e) {
		inconUpdate = new TreeMap<String, List<String[]>>();
		List<String[]> l = new ArrayList<String[]>();
		l.add(new String[] {e.condition});
		inconUpdate.put(e.location, l);
		//
		out.println("MP1: No inconsistent update is ever performed");
		out.println("Location " + e.location + " is updated to values " + e.val1 + " and " + e.val2
		+ " when the condition " + e.condition + " is true.");
		
	}

}