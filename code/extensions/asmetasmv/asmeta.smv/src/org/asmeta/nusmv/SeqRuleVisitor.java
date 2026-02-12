package org.asmeta.nusmv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;

import org.asmeta.nusmv.util.ConditionStack;
import org.asmeta.nusmv.util.Util;
import org.asmeta.parser.util.Defs;

import asmeta.transitionrules.basictransitionrules.BlockRule;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.turbotransitionrules.SeqRule;

class SeqRuleVisitor {
	private MapVisitor mv;
	//AsmetaTermPrinter termTran;
	private Environment env;
	private Map<String, Map<List<String>,String>> seqRuleUpdateMap;//tutti gli aggiornamenti della seq rule
	private Map<String, List<UpdatedValue>> seqRuleCurrentRuleUpdateMap;//tutti gli aggiornamenti della singola rule nella seq rule
	private Map<String, Integer> currentRuleSeqRuleUsedLocation;

	//TODO: capire perche' sono dichiarati statici
	private static Map<String, Stack<String>> pastConds;
	private static Map<String, String> locationValues;
	private String lastCondition;

	public SeqRuleVisitor(MapVisitor mapVisitor, TermVisitorToSMV termPrinter, Environment environment){
		mv = mapVisitor;
		//termTran = termPrinter;
		env = environment;
	}
	
	/**
	 * Visit.
	 * 
	 * @param seqRule the seq rule
	 * 
	 * @throws Exception the exception
	 */
	public void visit(SeqRule seqRule){
		boolean oldInSeqRule = env.inSeqRule;//conservo l'informazione per sapere da dove arrivo
		env.inSeqRule = true;//per evitare che vengano memorizzati gli aggiornamenti in updateMap
		if(!oldInSeqRule) {//creo la mappa globale solo nel primo seq
			locationValues = new HashMap<String, String>();//valore delle location precedenti
			pastConds = new HashMap<String, Stack<String>>();//condizioni sulle location precedenti
		}
		seqRuleUpdateMap = new HashMap<String, Map<List<String>,String>>();
		seqRuleCurrentRuleUpdateMap = new HashMap<String, List<UpdatedValue>>();
		for(Rule rule: seqRule.getRules()) {
			visitRule(rule);
			updateSeqRuleUpdateMap();
		}
		env.inSeqRule = oldInSeqRule;//torno alla situazione precedente
		//se e' l'ultima seqRule si puo' aggiornare l'updateMap
		if(!env.inSeqRule) {
			mv.updateMap.update(seqRuleUpdateMap);
		}
	}
	
	/**
	 * Visit rule.
	 * 
	 * @param rule the rule
	 */
	public void visitRule(Rule rule) {
		//contiene gli aggiornamenti della singola regola della seqRule
		//se all'interno della regola l'aggiornamento avviene senza condizioni
		if(seqRuleUpdateMap.size()==0) {
			//se non si sono condizioni sulle regole precedenti posso visitare
			//la regola una sola volta
			visitSingleRuleSeqRule(rule);
		}
		else {
			//altrimenti bisogna visitare la regola per ogni possibile
			//combinazione dei valori delle locazioni modificate nelle regole
			//precedenti (i valori che hanno assunto in tutte le possibili
			//esecuzioni precedenti)
			List<LocationsValues> locValues = new ArrayList<LocationsValues>();
			combineValuesMap(seqRuleUpdateMap,locValues, 0, new LocationsValues());
			for(LocationsValues values: locValues) {
				for(LocationValue loc: values.asList()) {
					if(!pastConds.containsKey(loc.location)) {
						pastConds.put(loc.location, new Stack<String>());
					}
					ConditionStack.updateCondition(pastConds.get(loc.location), loc.conds);
					locationValues.put(loc.location, loc.value);
				}
				//out.println("pastconds "+pastConds+" locationValues "+locationValues);
				visitSingleRuleSeqRule(rule);
				for(LocationValue loc: values.asList()) {
					ConditionStack.restoreCondition(pastConds.get(loc.location), loc.conds);
				}
				locationValues.clear();
			}
		}
	}

	/*public void combineValuesMap(Map<String, List<UpdatedValue>> map, List<LocationsValues> locationValues,
			int index, LocationsValues temp){
		Object[] locations = map.keySet().toArray();
		String location = (String)locations[index];
		List<UpdatedValue> localMap = map.get(location);
		for (UpdatedValue v : localMap) {
			temp.add(location,v.conds, v.value);
			if(locations.length == index+1){
				locationValues.add(new LocationsValues(temp));
			}
			else{
				combineValuesMap(map, locationValues, index+1, temp);
			}
			temp.removeLast();
		}
	}*/
	
	/**
	 * Combine values map.
	 * 
	 * @param map the map
	 * @param locationValues the location values
	 * @param index the index
	 * @param temp the temp
	 */
	public void combineValuesMap(Map<String, Map<List<String>, String>> map, List<LocationsValues> locationValues,
			int index, LocationsValues temp) {
		Object[] locations = map.keySet().toArray();
		String location = (String)locations[index];
		Map<List<String>, String> localMap = map.get(location);
		for (Entry<List<String>, String> localMapEntrySet: localMap.entrySet()) {
			temp.add(location, localMapEntrySet.getKey(), localMapEntrySet.getValue());
			//se siamo arrivati con la visita all'ultima locazione la possiamo
			//aggiungere alla lista locationValues
			if(locations.length == index+1) {
				locationValues.add(new LocationsValues(temp));
			}
			else {
				//altrimenti bisogna continuare sulla locazione successiva
				combineValuesMap(map, locationValues, index+1, temp);
			}
			temp.removeLast();//usciti dalla visita bisogna eliminare l'ultimo elemento
		}
	}

	/**
	 * Visit single rule seq rule.
	 * 
	 * @param rule the rule
	 */
	private void visitSingleRuleSeqRule(Rule rule) {
		lastCondition = mv.getConditions().lastElement();
		List<Rule> rules = new ArrayList<Rule>();
		if(Defs.isBlockRule(rule)) {
			rules.addAll(((BlockRule)rule).getRules());
		}
		else {
			rules.add(rule);
		}
		for(Rule r: rules) {
			currentRuleSeqRuleUsedLocation = new HashMap<String, Integer>();
			mv.getRv().visit(r);
		}
	}

	/**
	 * Seq rule update.
	 * 
	 * @param location the location
	 * @param value the value
	 */
	public void seqRuleUpdate(String location, String value) {
		List<String> conds = (List<String>) mv.getConditions().clone();
		//System.out.println("location "+location);
		boolean skip = currentRuleSeqRuleUsedLocation.containsKey(location) && currentRuleSeqRuleUsedLocation.get(location)==1;
		for(String pastLocation: pastConds.keySet()) {
			if(currentRuleSeqRuleUsedLocation.containsKey(pastLocation)) {
				if(pastLocation.equals(location) && skip) {
					continue;
				}
				conds.addAll(pastConds.get(pastLocation));
			}
		}
		if(!seqRuleCurrentRuleUpdateMap.containsKey(location)) {
			seqRuleCurrentRuleUpdateMap.put(location, new ArrayList<UpdatedValue>());
		}
		//dico se non si sono condizioni sull'aggiornamento corrente e non sono condizionato da location precedenti
		//TODO: e' giusto che ci sia l'uguaglianza tra riferimenti?
		if(lastCondition == mv.getConditions().lastElement()) {
			seqRuleCurrentRuleUpdateMap.get(location).add(new UpdatedValue(conds, value, true));
		}
		else {
			seqRuleCurrentRuleUpdateMap.get(location).add(new UpdatedValue(conds, value, false));
		}
	}

	/**
	 * Sets the used location.
	 * 
	 * @param location the new used location
	 */
	public void setUsedLocation(String location) {
		Integer value = currentRuleSeqRuleUsedLocation.get(location);
		if(value != null) {
			//FINDBUGS
			//Using new Integer(int) is guaranteed to always result in a new object whereas Integer.valueOf(int) allows caching of values to be done by the compiler, class library, or JVM. Using of cached values avoids object allocation and the code will be faster.
			//Values between -128 and 127 are guaranteed to have corresponding cached instances and using valueOf is approximately 3.5 times faster than using constructor. For values outside the constant range the performance of both styles is the same.
			//Unless the class must be compatible with JVMs predating Java 1.5, use either autoboxing or the valueOf() method when creating instances of Long, Integer, Short, Character, and Byte. 
			//currentRuleSeqRuleUsedLocation.put(location, new Integer(value.intValue()+1));
			
			//solution
			currentRuleSeqRuleUsedLocation.put(location, Integer.valueOf(value.intValue() + 1));
		}
		else {
			currentRuleSeqRuleUsedLocation.put(location, 1);
		}
	}

	/**
	 * Gets the location value.
	 * 
	 * @param locationName the location name
	 * 
	 * @return the location value
	 */
	public String getLocationValue(String locationName) {
		if(locationValues.containsKey(locationName)) {
			return locationValues.get(locationName);
		}
		else {
			return locationName;
		}
	}

	/**
	 * Update seq rule update map.
	 * 
	 * @throws Exception the exception
	 */
	public void updateSeqRuleUpdateMap(){
		Map<List<String>, String> map;
		List<UpdatedValue> localMap;
		Stack<String> conds = new Stack<String>();//stack per condizioni locali
		Set<List<String>> condKeySet;
		boolean remove;
		for(String location: seqRuleCurrentRuleUpdateMap.keySet()) {
			localMap = seqRuleCurrentRuleUpdateMap.get(location);
			remove = false;
			for(UpdatedValue s: localMap) {
				//se nella rule corrente c'e' un aggiornamento della
				//location certo (condizione=true), i precedenti
				//aggiornamenti della location possono essere cancellati
				//perche' non potranno mai essere eseguiti in modo esclusivo
				if(s.noConds) {
					remove = true;
					break;
				}
				else {
					conds.push(Util.notAnd(s.getConds()));
				}
			}
			map = seqRuleUpdateMap.get(location);//mappa globale
			if(map==null) {
				seqRuleUpdateMap.put(location, new HashMap<List<String>,String>());
				map = seqRuleUpdateMap.get(location);
				List<String> tempList = new ArrayList<String>();
				tempList.add(Util.trueString);
				map.put(tempList, location);
			}
			if(remove) {
				//out.println("remove "+map);
				map.clear();//se c'e' un aggiornamento certo posso cancellare i vecchi aggiornamenti
			}
			else {
				condKeySet = new HashSet<List<String>>(map.keySet());//vecchie condizioni
				for(List<String> l: condKeySet){
					ConditionStack.updateCondition(conds, l);
					if(!Util.and(conds).equals(Util.falseString)) {
						localMap.add(new UpdatedValue((List<String>) conds.clone(),map.get(l),false));//metto negli aggiornamenti correnti il vecchio aggiornamento opportunamente condizionato
					}
					map.remove(l);//rimuovo dalla mappa globale il vecchio valore
					ConditionStack.restoreCondition(conds, l);
				}
			}
			conds.clear();
			for(UpdatedValue u: localMap) {
				map.put(u.getConds(), u.getValue());//aggiorno la mappa globale
			}
		}
		seqRuleCurrentRuleUpdateMap.clear();
	}
}
