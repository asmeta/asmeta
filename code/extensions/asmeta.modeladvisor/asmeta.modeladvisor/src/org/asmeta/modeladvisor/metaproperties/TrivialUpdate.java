package org.asmeta.modeladvisor.metaproperties;

import static java.lang.System.out;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.asmeta.modeladvisor.texpr.Expression;
import org.asmeta.modeladvisor.texpr.SometimeExpression;
import org.asmeta.nuxmv.AsmetaSMV;
import org.asmeta.nuxmv.UpdateMap;

/**
 * Verifica se un aggiornamento e' sempre banale
 *
 */
public class TrivialUpdate extends Checker {
	private UpdateMap updateMap;
	//private Map<String, Map<String, String>> smvPropMap;
	private Map<String, Map<String, String[]>> smvPropMap;
	public List<String[]> trivialUpdate;
	private Map<String, String> nusmvNameToLocation;

	public TrivialUpdate(UpdateMap updateMap, Map<String, String> nusmvNameToLocation) {
		this.updateMap = updateMap;
		this.nusmvNameToLocation = nusmvNameToLocation;
	}

	@Override
	public
	Set<Expression> createNuSmvProperties() {
		Expression property;
		//smvPropMap = new HashMap<String, Map<String,String>>();
		smvPropMap = new HashMap<String, Map<String, String[]>>();
		Map<String, String> updateMapLoc;
		//Map<String, String> condsMap;
		Map<String, String[]> condsMap;
		//ciclo sulle locazioni che sono aggiornate
		for(String location: updateMap.keySet()){
			//condsMap = new HashMap<String,String>();
			condsMap = new HashMap<String,String[]>();
			smvPropMap.put(location, condsMap);
			updateMapLoc = updateMap.get(location);//update map della locazione
			//ciclo su tutti gli aggiornamenti della locazione
			for(Entry<String, String> entrySetUpdateMapLoc: updateMapLoc.entrySet()) {
				//La proprieta' prova a verificare che esiste almeno uno
				//stato in cui l'aggiornamento non e' banale. La versione
				//AG sarebbe troppo restrittiva.
				//Si considera la tripla (cond, location, value).

				//Una prima versione della proprieta' era
				//EF(cond -> location != value). La proprieta' puo' essere
				//verificata anche in presenza di trivial update, nel
				//caso in cui ci sia uno stato in cui cond e' falsa.
				//property = "EF(" + cond+" -> ("+location+" != "+updateMapLoc.get(cond) + "))";

				//La nuova versione dice che l'update inconsistente deve
				//essere rilevato solo se cond e' soddisfatta almeno una
				//volta (EF(cond) -> ).
				//In tal caso, affinche' l'aggiornamento non sia sempre
				//banale, deve esserci uno stato in cui vale cond e in cui
				//la locazione viene aggiornata ad un valore diverso da
				//quello attuale (EF(cond & location != value)).
				/*property = new Implies(new Ef(entrySetUpdateMapLoc.getKey()), new Ef(entrySetUpdateMapLoc.getKey() + " & " + location + " != " + entrySetUpdateMapLoc.getValue()));
				smvProp.add(property);
				condsMap.put(entrySetUpdateMapLoc.getKey(), property.getSMV());*/

				//anche la versione precedente e' errata (a causa degli stati iniziali di NuSMV)
				SometimeExpression ruleCanFire = new SometimeExpression(entrySetUpdateMapLoc.getKey());
				SometimeExpression ruleIsNotTrivial = new SometimeExpression(entrySetUpdateMapLoc.getKey() + " & " + location + " != " + entrySetUpdateMapLoc.getValue());
				smvProp.add(ruleCanFire);
				smvProp.add(ruleIsNotTrivial);
				condsMap.put(entrySetUpdateMapLoc.getKey(), new String[]{ruleCanFire.getSMV(), ruleIsNotTrivial.getSMV()});
			}
		}
		return smvProp;
	}

	/**
	 * Valuta il risultato della verifica delle metaproprieta'
	 * riguardanti i trivial update.
	 */
	@Override
	public void evaluation(Map<String, Boolean> results) {
		trivialUpdate = new ArrayList<String[]>();
		for(String location: smvPropMap.keySet()) {
			/*Map<String, String> mapProp = smvPropMap.get(location);
			for(Entry<String, String> cond: mapProp.entrySet()) {
				//La proprieta' verificata e':
				//EF(cond) -> EF(cond & location!=value).
				//L'implicazione e' necessaria: nel caso in cui la condizione
				//cond non e' mai verificata, infatti, non deve essere segnalato
				//un trivial update; se non ci fosse l'implicazione verrebbe
				//segnalato.
				//Se e' falsa vuol dire che c'e' un update inconsistente.
				if(!results.get(cond.getValue())){
					String[] arr = {location, cond.getKey(), updateMap.get(location, cond.getKey())};
					trivialUpdate.add(arr);
				}
			}*/
			Map<String, String[]> mapProp = smvPropMap.get(location);
			for(Entry<String, String[]> cond: mapProp.entrySet()) {
				if(!results.get(cond.getValue()[0]) && results.get(cond.getValue()[1])) {
					String[] arr = {location, cond.getKey(), updateMap.get(location, cond.getKey())};
					trivialUpdate.add(arr);
				}
			}
		}
	}

	@Override
	public void printResults() {
		out.println("MP4: No assignment is always trivial");
		boolean noViolation = true;
		if(trivialUpdate.size() > 0) {
			for(String[] arr: trivialUpdate){
				out.println("Trivial update of location " + nusmvNameToLocation.get(arr[0]) +
				". When the condition is " + AsmetaSMV.replaceVarsWithLocations(arr[1], nusmvNameToLocation) + " its value is always " +
				"the same of the term " + AsmetaSMV.replaceVarsWithLocations(arr[2], nusmvNameToLocation) + ".");
				noViolation = false;
			}
		}
		if(noViolation) {
			out.println("NONE");
		}
		out.println();
	}
}