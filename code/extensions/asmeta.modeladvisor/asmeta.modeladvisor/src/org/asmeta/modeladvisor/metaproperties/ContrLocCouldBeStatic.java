package org.asmeta.modeladvisor.metaproperties;

import static java.lang.System.out;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.asmeta.modeladvisor.texpr.AlwaysExpression;
import org.asmeta.modeladvisor.texpr.Expression;
import org.asmeta.modeladvisor.texpr.TemporalExpression;

/**
 * Verifica se una locazione/funzione controllata puo' essere dichiarata statica
 *
 */
public class ContrLocCouldBeStatic extends Checker {
	private List<String> usedLoc;
	private Map<String, String> initMap;
	private Map<String, String> smvPropMap;
	private Map<String, Set<String>> contrFuncLocations;
	public Set<String> locCouldBeStatic;
	public Set<String> funcCouldBeStatic;

	public ContrLocCouldBeStatic(List<String> usedLoc, Map<String, String> initMap, Map<String, Set<String>> contrFuncLocations) {
		this.usedLoc = usedLoc;
		this.initMap = initMap;
		this.contrFuncLocations = contrFuncLocations;
	}

	@Override
	public
	Set<Expression> createNuSmvProperties() {
		TemporalExpression property;
		smvPropMap = new HashMap<String, String>();
		//solo le locazioni inizializzate possono aspirare a diventare
		//locazioni statiche
		for(String location: initMap.keySet()){
			//se viene inizializzata, ma non viene mai letta, dovrebbe
			//essere eliminata. Non viene neanche segnalato, quindi, che
			//potrebbe essere statica. Giusto? L'utente potrebbe essere
			//disorientato?
			if(usedLoc.contains(location)){
				//la locazione mantiene il valore dello stato iniziale
				//"AG(" + location + " = " + initMap.get(location) + ")"
				property = new AlwaysExpression(location + " = " + initMap.get(location));
				smvProp.add(property);
				smvPropMap.put(location, property.getSMV());
			}
		}
		return smvProp;
	}

	@Override
	public
	/**
	 * Valuta se una locazione controllata puo' essere dichiarata statica.
	 * Se una locazione viene inizializzata e letta, ma non aggiornata, puo' essere
	 * dichiarata statica.
	 * Consideriamo solo quelle che sono usate (mv.env.usedLocation),
	 * perche' quelle che non sono usate devono essere rimosse dal modello.
	 * Per poter essere dichiarata statica deve essere vera la proprieta'
	 * AG(location = initValue), dove initValue e' il valore a cui e' stata
	 * inizializzata la locazione location.
	 */
	void evaluation(Map<String, Boolean> results){
		locCouldBeStatic = new TreeSet<String>();
		funcCouldBeStatic = new TreeSet<String>();
		for(String location: smvPropMap.keySet()){
			if(results.get(smvPropMap.get(location))){	
				locCouldBeStatic.add(location);
			}
		}
		boolean result;
		for(String function: contrFuncLocations.keySet()) {
			result = true;
			for(String location: contrFuncLocations.get(function)){
				if(smvPropMap.containsKey(location)){
					if(!results.get(smvPropMap.get(location))){
						result = false;
						break;
					}
				}
				else {
					result = false;
					break;
				}
			}
			if(result) {
				funcCouldBeStatic.add(function);
			}
		}
	}

	@Override
	public
	void printResults() {
		out.println("MP7: a controlled location could be static");
		boolean noViolation = true;
		if(locCouldBeStatic.size() > 0 || funcCouldBeStatic.size() > 0) {
			for(String location: locCouldBeStatic) {
				out.println("Controlled location " + location + " could be " +
				"defined static. It's initialized to " + initMap.get(location) +
				" and never changes its value.");
				noViolation = false;
			}
			for(String function: funcCouldBeStatic) {
				out.println("Controlled function " + function + " could be " +
				"defined static. All its locations are initialized and never " +
				"change their values.");
				noViolation = false;
			}
		}
		if(noViolation) {
			out.println("NONE");
		}
		out.println();
	}
}