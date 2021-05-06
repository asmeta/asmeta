package org.asmeta.modeladvisor.metaproperties;

import static java.lang.System.out;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;

import org.asmeta.modeladvisor.texpr.Expression;
import org.asmeta.modeladvisor.texpr.SometimeExpression;
import org.asmeta.nuxmv.GetFunctionDomains;
import org.asmeta.nuxmv.MapVisitor;
import org.asmeta.nuxmv.util.Util;
import org.asmeta.simulator.value.Value;

import asmeta.definitions.Function;
import asmeta.definitions.domains.Domain;

public class DomainAllUsed extends Checker {
	private HashMap<String, Map<String, Set<String>>> valueInFunctionCodomain;
	private TreeMap<String, Map<String, Set<String>>> valueInFunctionDomain;
	private MapVisitor mv;
	private HashMap<String, Map<String, List<Expression>>> domainAllUsed;
	private HashMap<String, Map<String, List<String>>> smvPropMap;
	public Map<String, Set<String>> usedVals;
	public Map<String, Set<String>> notUsedVals;
	private Map<String, Set<String>> usedValsInDomains;
	private Map<String, Set<String>> notUsedValsInDomains;
	private Map<String, Set<String>> usedValsInCodomains;
	private Map<String, Set<String>> notUsedValsInCodomains;
	private LocationCouldBeRemoved locCouldBeRemoved;

	public DomainAllUsed(MapVisitor mv) {
		this.mv = mv;
		locCouldBeRemoved = new LocationCouldBeRemoved(mv);
	}

	@Override
	public
	Set<Expression> createNuSmvProperties() {
		smvProp.addAll(locCouldBeRemoved.createNuSmvProperties());
		Map<String, List<Expression>> map;
		smvPropMap = new HashMap<String, Map<String, List<String>>>();
		Map<String, List<String>> currentMap;
		getValuesInDomainAndCodomain();
		for(String domain: domainAllUsed.keySet()){
			map = domainAllUsed.get(domain);
			currentMap = new HashMap<String, List<String>>();
			smvPropMap.put(domain, currentMap);
			for(Entry<String, List<Expression>> mapValueProperty: map.entrySet()){
				currentMap.put(mapValueProperty.getKey(), new ArrayList<String>());
				for(Expression property: mapValueProperty.getValue()){
					//sometimes
					smvProp.add(property);
					//currentMap.get(value).add(property);
					currentMap.get(mapValueProperty.getKey()).add(property.getSMV());
				}
			}
		}
		return smvProp;
	}

	/**
	 * Costruisce le mappe valueInFunctionCodomain e valueInFunctionDomain.
	 * La mappa valueInFunctionCodomain associa ad ogni valore di un dominio
	 * un insieme di locazioni che possono assumere quel valore.
	 * La mappa valueInFunctionDomain associa ad ogni valore di un dominio
	 * un insieme di locazioni che sono determinate (il valore e' argomento
	 * della funzione) tramite quel valore.
	 * A partire dalla mappa valueInFunctionCodomain, viene creata la mappa
	 * domainAllUsed che associa ad ogni valore v di un dominio una proprieta'
	 * NuSMV che afferma che almeno una delle locazioni che possono assumere
	 * come valore prima o poi lo assumono.
	 */
	private void getValuesInDomainAndCodomain(){
		valueInFunctionCodomain = new HashMap<String, Map<String,Set<String>>>();
		valueInFunctionDomain = new TreeMap<String, Map<String,Set<String>>>();
		Map<String, Set<String>> mapCodomain, mapDomain;
		String domainName;
		//cicliamo su tutti i domini dichiarati nell'header e creiamo delle
		//mappe per tutti i valori
		for(Domain domain: mv.domains) {
			domainName = Util.getDomainName(domain);
			mapCodomain = new HashMap<String,Set<String>>();
			mapDomain = new HashMap<String, Set<String>>();
			valueInFunctionCodomain.put(domainName, mapCodomain);
			valueInFunctionDomain.put(domainName, mapDomain);
			//cicliamo sui valori del dominio ed aggiungiamo una entry nella
			//mappa per ogni valore
			for(String value: mv.domainSet.get(domainName)){
				mapCodomain.put(value, new HashSet<String>());
				mapDomain.put(value, new HashSet<String>());
			}
		}
		//cicliamo su tutte le locazioni usate e, per ogni valore del loro
		//codominio, le aggiungiamo alla mappa
		for(String location: mv.env.usedLoc) {
			domainName = mv.locationDomain.get(location);
			mapCodomain = valueInFunctionCodomain.get(domainName);
			//nel caso in cui il codominio sia boolean, non sara' presente in
			//valueInFunctionCodomain. Il boolean non puo' essere eliminato
			//o ridotto.
			if(mapCodomain != null) {
				for(Entry<String, Set<String>> entrySetMapCodomain: mapCodomain.entrySet()) {
					entrySetMapCodomain.getValue().add(location);
				}
			}
		}
		domainAllUsed = new HashMap<String, Map<String,List<Expression>>>();
		Map<String, List<Expression>> propMap;//mappa da valore a proprieta'
		List<Expression> properties;//lista di proprieta' per il singolo valore
		for(String domain: valueInFunctionCodomain.keySet()) {
			propMap = new HashMap<String, List<Expression>>();
			mapCodomain = valueInFunctionCodomain.get(domain);
			for(Entry<String, Set<String>> entryValueLocation: mapCodomain.entrySet()) {
				properties = new ArrayList<Expression>();
				for(String location: entryValueLocation.getValue()) {
					//location vale almeno una volta value
					
					//deve diventare sometimes
					//properties.add("EF(" + location + " = " + value + ")");
					//properties.add("AG(!(" + location + " = " + value + "))");
					properties.add(new SometimeExpression(location + " = " + entryValueLocation.getKey()));
				}
				if(properties.size() > 0) {
					//il valore e' utile se viene utilizzato almeno una volta
					//in un codominio di una locazione
					propMap.put(entryValueLocation.getKey(), properties);
				}
			}
			//la mappa deve essere inserita se c'e' almeno una locazione che 
			//ha come codominio il dominio domain
			//Se in fase di valutazione dei risultati si vede che un dominio
			//non ha la mappa vuol dire che, per quanto riguarda il suo utilizzo
			//nei codomini delle funzioni, puo' essere eliminato.
			if(propMap.size() > 0) {
				domainAllUsed.put(domain, propMap);
			}
		}
		Domain domain;
		ArrayList<Value[]> result;
		List<Domain> domains;
		String locationName, value;
		List<String> locations;
		for(Function func: mv.functions) {
			domain = func.getDomain();
			//nel caso di arieta' > 0
			if(domain != null) {
				result = new ArrayList<Value[]>();
				//in domains c'e' la lista dei singoli domini che formano domain
				domains = GetFunctionDomains.getFuncDomains(domain);
				//in result vengono messe tutte le tuple di valori ottenibili
				//dalla combinazione dei valori dei domini di domains
				mv.combineValues(domains, 0, result, new Stack<Value>());
				for(Value[] values: result){
					//la locazione che si ottiene con i valori attuali
					locationName = Util.getLocationName(func, values);
					
					locations = new ArrayList<String>(mv.env.usedLoc);
					locations.addAll(mv.env.usedDerLoc);
					//solo le locazioni che sono utilizzate
					//forse si potrebbe utilizzare la metaproprieta'
					//execLocationCouldBeRemoved. Qui no perche' non e' stata
					//ancora fatta la verifica delle proprieta': bisogna farlo
					//in evaluation.
					if(mv.env.usedLoc.contains(locationName)) {
					//if(neverUsedControlledLocation.contains(locationName)){
						//la locazione deve essere aggiunta alla mappa di tutti
						//i valori che la specificano.
						for(int i=0; i < domains.size(); i++) {
							domainName = Util.getDomainName(domains.get(i));
							//necessario: il dominio boolean non viene considerato
							//e potrebbe essere nel codominio di una funzione
							if(valueInFunctionDomain.containsKey(domainName)){
								value = values[i].toString().toUpperCase();
								valueInFunctionDomain.get(domainName).get(value).add(locationName);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Valuta se tutti gli elementi di un dominio sono utilizzati (nel dominio
	 * e/o nel codominio di qualche funzione) o se e' possibile ridurre le
	 * dimensioni del dominio eliminando alcuni valori.
	 */
	@Override
	public
	void evaluation(Map<String, Boolean> results) {
		locCouldBeRemoved.evaluation(results);

		usedVals = new HashMap<String, Set<String>>();
		notUsedVals = new HashMap<String, Set<String>>();
		usedValsInDomains = new HashMap<String, Set<String>>();
		notUsedValsInDomains = new HashMap<String, Set<String>>();
		usedValsInCodomains = new HashMap<String, Set<String>>();
		notUsedValsInCodomains = new HashMap<String, Set<String>>();

		Set<String> usedValues, notUsedValues;//contengono i valori usati e quelli non usati
		Set<String> usedValuesInDomains, notUsedValuesInDomains;
		Set<String> usedValuesInCodomains,notUsedValuesInCodomains;
		Map<String, Set<String>> mapDomain;
		Map<String, List<String>> currentMap;
		String value;
		boolean domainOfFuncCodomain, valueUsedInFuncDomain, valueUsedInFuncCodomain;
		//ciclo su tutti i domini valutati (potevo ciclare ugualmente su
		//valueInFunctionDomain).
		//Non posso ciclare su domainAllUsed perche' questo non contiene
		//i domini che non sono utilizzati come codominio di una funzione.
		for(String domain: valueInFunctionDomain.keySet()){
			//out.println(domain);
			mapDomain = valueInFunctionDomain.get(domain);
			usedValues = new HashSet<String>();
			notUsedValues = new HashSet<String>();
			usedValuesInDomains = new HashSet<String>();
			notUsedValuesInDomains = new HashSet<String>();
			usedValuesInCodomains = new HashSet<String>();
			notUsedValuesInCodomains = new HashSet<String>();
			currentMap = smvPropMap.get(domain);
			domainOfFuncCodomain  = (currentMap != null);
			//ciclo sui valori del dominio
			for(Entry<String, Set<String>> entryValueLocation: mapDomain.entrySet()) {
				value = entryValueLocation.getKey();
				valueUsedInFuncDomain = false;
				for(String location: entryValueLocation.getValue()) {
					if(!locCouldBeRemoved.neverUsedControlledLocation.contains(location) ||
					(!locCouldBeRemoved.neverUsedMonitoredLocation.contains(location) &&
					!locCouldBeRemoved.initButNeverUsedControlledLocation.contains(location))) {
						valueUsedInFuncDomain = true;
						break;
					}
					
				}
				valueUsedInFuncCodomain = false;
				if(domainOfFuncCodomain) {
					for(String property: currentMap.get(value)) {
						valueUsedInFuncCodomain = !results.get(property);
						if(valueUsedInFuncCodomain) {
							break;
						}
					}
				}
				if(valueUsedInFuncDomain) {
					usedValuesInDomains.add(value);
					usedValues.add(value);
				}
				else {
					notUsedValuesInDomains.add(value);
				}
				if(valueUsedInFuncCodomain) {
					usedValuesInCodomains.add(value);
					usedValues.add(value);
				}
				else {
					notUsedValuesInCodomains.add(value);
				}
				if(!valueUsedInFuncDomain && !valueUsedInFuncCodomain) {
					notUsedValues.add(value);
				}
			}
			usedVals.put(domain, usedValues);
			notUsedVals.put(domain, notUsedValues);
			usedValsInDomains.put(domain, usedValuesInDomains);
			notUsedValsInDomains.put(domain, notUsedValuesInDomains);
			usedValsInCodomains.put(domain, usedValuesInCodomains);
			notUsedValsInCodomains.put(domain, notUsedValuesInCodomains);
		}
	}

	@Override
	public
	void printResults() {
		out.println("MP5: For every domain element e, there exists a location which has value e");
		Set<String> notUsedValues, usedValues;
		boolean noViolation = true;
		for(String domain: valueInFunctionDomain.keySet()){
			notUsedValues = notUsedVals.get(domain);
			usedValues = usedVals.get(domain);
			//mi interessano solo i domini per cui qualche valore non viene
			//utilizzato.
			if(notUsedValues.size() > 0) {
				//tutti i valori non sono utilizzati
				if(usedValues.size() == 0) {
					out.println("Domain " + domain + " is unuseful. All its values" +
					" are neither used in a domain nor in a codomain.");
					noViolation = false;
				}
				else {
					out.println("Domain "+domain+ " could be reduced in size."
					+" Elements "+Util.asSet(notUsedValues)+" could be removed.");
					noViolation = false;
				}
			}
		}
		if(noViolation) {
			out.println("NONE");
		}
		out.println();
	}
}