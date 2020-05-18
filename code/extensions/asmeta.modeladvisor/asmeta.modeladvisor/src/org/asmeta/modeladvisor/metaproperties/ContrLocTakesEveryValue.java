package org.asmeta.modeladvisor.metaproperties;

import static java.lang.System.out;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.asmeta.modeladvisor.texpr.Expression;
import org.asmeta.modeladvisor.texpr.SometimeExpression;
import org.asmeta.modeladvisor.texpr.TemporalExpression;
import org.asmeta.nusmv.MapVisitor;
import org.asmeta.nusmv.util.Util;

public class ContrLocTakesEveryValue extends Checker {
	MapVisitor mv;
	private HashMap<String, Map<String, TemporalExpression>> contrLocTakesEveryValueSingle;
	private HashMap<String, Map<String, String>> contrLocTakesEveryValueSingleMap;
	public Set<String> initAndNeverUsedLocation;
	public Set<String> neverUsedLocation;
	Set<String> neverUsedFunction;
	Map<String, Set<String>> locUsedValues, locNotUsedValues, usedValuesFunc;
	public Map<String, List<Set<String>>> locCouldBeResized;
	public Map<String, List<Set<String>>> funcCouldBeResized;

	public ContrLocTakesEveryValue(MapVisitor mv) {
		this.mv = mv;
	}

	@Override
	public
	Set<Expression> createNuSmvProperties() {
		contrLocTakesEveryValue();
		//locationTakesEveryValueIndex = new HashMap<String, Integer>();
		contrLocTakesEveryValueSingleMap = new HashMap<String, Map<String, String>>();
		for(String loc: contrLocTakesEveryValueSingle.keySet()){
			//sometimes
			//DOPO L'INTRODUZIONE DI SOMETIMES E' ERRATA. DEVE ESSERE RIFORMULATA
			//smv.println("SPEC\t" + contrLocTakesEveryValue.get(loc) + ";");
			//changeValue.put(index, true);
			//locationTakesEveryValueIndex.put(loc, index);
			//index++;
			
			contrLocTakesEveryValueSingleMap.put(loc, new HashMap<String, String>());
			for(String value: contrLocTakesEveryValueSingle.get(loc).keySet()){
				//sometimes
				smvProp.add(contrLocTakesEveryValueSingle.get(loc).get(value));//deve cambiare valore
				contrLocTakesEveryValueSingleMap.get(loc).put(value, contrLocTakesEveryValueSingle.get(loc).get(value).getSMV());
			}
		}
		return smvProp;
	}

	/**
	 * Every controlled location can take any value in its domain
	 * 
	 * Costruisce per ogni locazione loc e per ogni suo valore v_i
	 * una proprieta' nella forma EF(loc = v_i). Tali proprieta' sono messe nella mappa
	 * locationTakesEveryValueSingle.
	 * Per ogni locazione, inoltre, viene costruita una proprieta' nella forma
	 * EF(loc = v_1) & .. & EF(loc = v_n). Questa proprieta' indica se la locazione assume
	 * tutti i valori del suo dominio.
	 */
	private void contrLocTakesEveryValue(){
		contrLocTakesEveryValueSingle = new HashMap<String,Map<String, TemporalExpression>>();
		//contrLocTakesEveryValue = new HashMap<String, String>();
		TemporalExpression property;
		List<TemporalExpression> props;
		Map<String, TemporalExpression> mapValueProp;
		//ciclo su tutte le locazioni controllate del modello
		for(String locName: mv.contrLocations) {
			//devono essere valutate solo quelle che sono usate.
			if(mv.env.usedLoc.contains(locName)){
				mapValueProp = new HashMap<String, TemporalExpression>();
				contrLocTakesEveryValueSingle.put(locName, mapValueProp);
				props = new ArrayList<TemporalExpression>();
				//ciclo sui valori del codominio della locazione
				for(String value: mv.domainSet.get(mv.locationDomain.get(locName))){
					//questa proprieta' verifica che ci sia almeno uno stato
					//in cui la locazione locName vale value
					//sometimes
					//"EF(" + locName + " = " + value + ")";
					//"AG(!(" + locName + " = " + value + "))";
					property = new SometimeExpression(locName + " = " + value);//deve cambiare valore
					mapValueProp.put(value, property);
					props.add(property);
				}
			}
		}
	}

	@Override
	public
	void evaluation(Map<String, Boolean> results) {
		locUsedValues = new HashMap<String, Set<String>>();
		locNotUsedValues = new HashMap<String, Set<String>>();
		usedValuesFunc = new HashMap<String, Set<String>>();
		
		funcCouldBeResized = new TreeMap<String, List<Set<String>>>();
		locCouldBeResized = new TreeMap<String, List<Set<String>>>();
		initAndNeverUsedLocation = new TreeSet<String>();
		neverUsedLocation = new TreeSet<String>();
		neverUsedFunction = new TreeSet<String>();
		Set<String> usedValues, notUsedValues, usedValuesFunction, notUsedValuesFunction;
		Map<String, String> localMap;
		boolean usedFunction;
		for (String function: mv.contrFuncLocations.keySet()) {
			usedValuesFunction = new TreeSet<String>();
			notUsedValuesFunction = new TreeSet<String>();
			usedFunction = false;
			for (String loc: mv.contrFuncLocations.get(function)) {
				//Se e' in contrLocTakesEveryValueSingleIndex vuol dire che e'
				//una used location.
				if(contrLocTakesEveryValueSingleMap.containsKey(loc)) {
					//usedFunction = true;
					usedValues = new TreeSet<String>();
					notUsedValues = new TreeSet<String>();
					localMap = contrLocTakesEveryValueSingleMap.get(loc);
					for(Entry<String, String> entryValueResult: localMap.entrySet()){
						//if(results.get(localMap.get(value))){
						if(!results.get(entryValueResult.getValue())) {
							usedFunction = true;
							usedValues.add(entryValueResult.getKey());
						}
						else {
							notUsedValues.add(entryValueResult.getKey());
						}
					}
					usedValuesFunction.addAll(usedValues);
					notUsedValuesFunction.addAll(notUsedValues);
					locUsedValues.put(loc, usedValues);
					locNotUsedValues.put(loc, notUsedValues);
					if (notUsedValues.size() > 0) {
						if (usedValues.size() > 0) {
							List<Set<String>> usedNotUsed = new ArrayList<Set<String>>();
							usedNotUsed.add(usedValues);
							usedNotUsed.add(notUsedValues);							
							locCouldBeResized.put(loc,usedNotUsed);
						}
						//per le controllate e le monitorate non dovrebbe mai entrare nell'else...
						else {
							/*assert false: "ERRORE: non dovrebbe mai entrare in questo codice";
							out.println("Location " + mv.nusmvNameToLocation.get(loc) +
										" does not take any value of its domain "
										+ Util.asSet(notUsedValues)+ ".");*/
							neverUsedLocation.add(loc);
						}
					}
				}
				else {
					if(mv.initMap.containsKey(loc)){
						initAndNeverUsedLocation.add(loc);
					}
					else {
						neverUsedLocation.add(loc);
					}
				}
			}
			
			if(usedFunction) {
				usedValuesFunc.put(function, usedValuesFunction);
				if(usedValuesFunction.size()==0) {
					assert false: "ERRORE: non dovrebbe mai entrare in questo codice";
					out.println("Function " + function +
					" does not take any value of its codomain.");
				}
				else if(usedValuesFunction.size()<mv.domainSet.get(mv.functionDomain.get(function)).size()){
					notUsedValuesFunction = new TreeSet<String>();
					for(String value: mv.domainSet.get(mv.functionDomain.get(function))){
						if(!usedValuesFunction.contains(value)) {
							notUsedValuesFunction.add(value);
						}
					}
					List<Set<String>> usedNotUsed = new ArrayList<Set<String>>();
					usedNotUsed.add(usedValuesFunction);
					usedNotUsed.add(notUsedValuesFunction);
					
					funcCouldBeResized.put(function, usedNotUsed);
				}
			}
			else {
				neverUsedFunction.add(function);
			}
		}
	}

	@Override
	public void printResults() {
		out.println("MP6: Every controlled location can take any value in its codomain");
		Set<String> ignoreLocation = new HashSet<String>();
		boolean noViolation = true;
		for(String function: neverUsedFunction) {
			ignoreLocation.addAll(mv.contrFuncLocations.get(function));
			out.println("Function " + function + " should be removed, because it's never used.");
			noViolation = false;
		}
		for(String loc: neverUsedLocation) {
			if(!ignoreLocation.contains(loc)) {
				out.println("Location " + mv.nusmvNameToLocation.get(loc) +
				" should be removed, because it's never used.");
				noViolation = false;
			}
		}
		for(String loc: initAndNeverUsedLocation) {
			if(!ignoreLocation.contains(loc)) {
				out.println("Location " + mv.nusmvNameToLocation.get(loc) +
				" should be removed, because it' initialized and then it's never used.");
				noViolation = false;
			}
		}
		ignoreLocation.clear();
		for(String function: funcCouldBeResized.keySet()) {
			ignoreLocation.addAll(mv.contrFuncLocations.get(function));
			out.println("Function " + function + " does not take the values "
					+ Util.asSet(funcCouldBeResized.get(function).get(1)) 
					+ " of its domain. It could be defined over the smaller domain "
					+ Util.asSet(funcCouldBeResized.get(function).get(0))+ ".");
			noViolation = false;
		}
		for(String loc: locCouldBeResized.keySet()) {
			if(!ignoreLocation.contains(loc)) {
				out.println("Location " + mv.nusmvNameToLocation.get(loc) +
					" does not take the values "
					+ Util.asSet(locCouldBeResized.get(loc).get(1)) +
					" of its domain. It could be defined over the smaller domain "
					+ Util.asSet(locCouldBeResized.get(loc).get(0)) + ".");
				noViolation = false;
			}
		}
		if(noViolation) {
			out.println("NONE");
		}
		out.println();
	}
}