package org.asmeta.modeladvisor.metaproperties;

import static java.lang.System.out;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.asmeta.modeladvisor.texpr.Expression;
import org.asmeta.modeladvisor.texpr.SometimeExpression;
import org.asmeta.modeladvisor.texpr.TemporalExpression;
import org.asmeta.nuxmv.UpdateMap;
import org.asmeta.nuxmv.util.Util;

/**
 * Metaproprieta' "Ogni locazione viene aggiornata."
 *
 */
public class ContrLocIsUpdated extends Checker {
	private Set<String> contrLocations;
	private UpdateMap updateMap;
	private Map<String, String> smvPropMap;
	private Map<String, String> nusmvNameToLocation;
	public Set<String> neverUpdatedLocation;

	public ContrLocIsUpdated(Set<String> contrLocations, UpdateMap updateMap, Map<String, String> nusmvNameToLocation) {
		this.contrLocations = contrLocations;
		this.updateMap = updateMap;
		this.nusmvNameToLocation = nusmvNameToLocation;
	}

	@Override
	public
	Set<Expression> createNuSmvProperties() {
		Map<String, String> localMap;
		TemporalExpression property;
		smvPropMap = new HashMap<String, String>();
		for (String location: contrLocations) {
			localMap = updateMap.get(location);
			if(localMap.size() > 0) {
				//sometimes
				//"EF("+Util.or(localMap.keySet())+ ")";
				//"AG(!(" +Util.or(localMap.keySet())+ "))";
				property = new SometimeExpression(Util.or(localMap.keySet()));//il risulatato deve essere invertito
				smvProp.add(property);
				smvPropMap.put(location, property.getSMV());
			}
		}
		return smvProp;
	}

	@Override
	public
	void evaluation(Map<String, Boolean> results){
		neverUpdatedLocation = new TreeSet<String>();
		//ciclo su tutte le locazioni controllate
		for(String location: contrLocations){
			//Se la locazione non e' presente in contrLocIsUpdatedIndex vuol
			//dire che la locazione non e' presente in alcuna update rule.
			//Se e' presente in una o piu' update rule bisogna controllare
			//il risultato della proprieta' temporale che controlla che tali
			//update rule siano prima o poi raggiunte.
			//System.out.println(location);
			//System.out.println(smvPropMap);
			//System.out.println(results);
			if(!smvPropMap.containsKey(location) ||
				results.get(smvPropMap.get(location))) {
				neverUpdatedLocation.add(location);
			}
		}
	}

	@Override
	public void printResults() {
		out.println("MP7: a controlled location is never updated");
		boolean noViolation = true;
		if(neverUpdatedLocation.size() > 0) {
			for(String location: neverUpdatedLocation){
				out.println("Location " + nusmvNameToLocation.get(location) +
														" is never updated.");
				noViolation = false;
			}
		}
		if(noViolation) {
			out.println("NONE");
		}
		out.println();
	}
}