package org.asmeta.modeladvisor.metaproperties;

import static java.lang.System.out;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.asmeta.modeladvisor.texpr.Expression;
import org.asmeta.modeladvisor.texpr.SometimeExpression;
import org.asmeta.nuxmv.util.Util;

public class StatDerIsUsed extends Checker {
	private Map<String, Set<String>> smvPropMap;
	private Map<String, Set<String>> derFuncLocations, statFuncLocations;
	private Map<String, String> nusmvNameToLocation;
	Map<String, List<String>> statDerReachabilityConds;
	private Map<String, Set<String>> usedStatDerInDer;
	public Set<String> derLocNotUsed;
	public Set<String> derLocUsedInUnreachCode;
	public Set<String> derFuncNeverUsed;
	public Set<String> derFuncUsedInUnreachCode;
	public Set<String> statLocNotUsed;
	public Set<String> statLocUsedInUnreachCode;
	public Set<String> statFuncNeverUsed;
	public Set<String> statFuncUsedInUnreachCode;

	public StatDerIsUsed(Map<String, Set<String>> derFuncLocations,
					Map<String, Set<String>> statFuncLocations,
					Map<String, String> nusmvNameToLocation,
					Map<String, List<String>> statDerReachabilityConds,
					Map<String, Set<String>> usedStatDerInDer) {
		this.derFuncLocations = derFuncLocations;
		this.statFuncLocations = statFuncLocations;
		this.nusmvNameToLocation = nusmvNameToLocation;
		this.statDerReachabilityConds = statDerReachabilityConds;
		this.usedStatDerInDer = usedStatDerInDer;
	}

	@Override
	public
	Set<Expression> createNuSmvProperties() {
		Expression property;
		Set<String> propertySet;
		smvPropMap = new HashMap<String, Set<String>>();
		Map<String, Expression> smvPropMapReach = new HashMap<String, Expression>();
		//locazioni statiche o controllate che sono state raggiunte nelle regole
		for(String location: statDerReachabilityConds.keySet()){
			//sometimes
			//property = "EF(" +Util.or(statDerReachabilityConds.get(location))+ ")";
			//property = "AG(!(" +Util.or(statDerReachabilityConds.get(location))+ "))";
			property = new SometimeExpression(Util.or(statDerReachabilityConds.get(location)));
			smvProp.add(property);
			propertySet = new HashSet<String>();
			propertySet.add(property.getSMV());
			smvPropMap.put(location, propertySet);
			smvPropMapReach.put(location, property);
		}
		//System.out.println(usedStatDerInDer);
		//System.out.println(statDerReachabilityConds);
		for(String statDer: statDerReachabilityConds.keySet()) {
			//TODO: usedStatDerInDer.get(statDer) should not be null
			if(usedStatDerInDer.get(statDer) != null) {
				for(String loc: usedStatDerInDer.get(statDer)) {
					property = smvPropMapReach.get(statDer);
					if(smvPropMapReach.containsKey(loc)) {
						smvPropMap.get(loc).add(property.getSMV());
					}
					else {
						propertySet = new HashSet<String>();
						propertySet.add(property.getSMV());
						smvPropMap.put(loc, propertySet);
					}				
				}
			}
		}
		//System.err.println(smvPropMap);
		return smvProp;
	}

	@Override
	public
	void evaluation(Map<String, Boolean> results) {
		//System.err.println(results);
		derLocNotUsed = new TreeSet<String>();
		derLocUsedInUnreachCode = new TreeSet<String>();
		derFuncNeverUsed = new TreeSet<String>();
		derFuncUsedInUnreachCode = new TreeSet<String>();
		statLocNotUsed = new TreeSet<String>();
		statLocUsedInUnreachCode = new TreeSet<String>();
		statFuncNeverUsed = new TreeSet<String>();
		statFuncUsedInUnreachCode = new TreeSet<String>();

		int notUsedCounter;//numero di locazioni usate in una funzione
		int notReachedCounter, notReadCounter, numLocations;
		//ciclo su tutte le derived functions
		for(String function: derFuncLocations.keySet()) {
			numLocations = derFuncLocations.get(function).size();
			notUsedCounter = 0;
			notReachedCounter = 0;
			notReadCounter = 0;
			for(String location: derFuncLocations.get(function)) {
				if(!smvPropMap.containsKey(location)) {
					notUsedCounter++;
					notReadCounter++;
					derLocNotUsed.add(location);
					//System.err.println(location + " not used");
				}
				else {
					boolean r = false;
					for(String prop: smvPropMap.get(location)) {
						assert results.containsKey(prop): "property: " + prop + "\nis not contained in:\n" + results;
						if(results.get(prop)) {
							r = true;
						}
					}
					if(r) {
						notUsedCounter++;
						notReachedCounter++;
						derLocUsedInUnreachCode.add(location);
						//System.err.println(location + " unreach code");
					}
				}
			}
			if(numLocations == notReadCounter) {
				derFuncNeverUsed.add(function);
			}
			else if(numLocations == notReachedCounter) {
				derFuncUsedInUnreachCode.add(function);
			}
		}
		//ciclo su tutte le static functions
		for(String function: statFuncLocations.keySet()) {
			numLocations = statFuncLocations.get(function).size();
			notUsedCounter = notReachedCounter = notReadCounter = 0;
			for(String location: statFuncLocations.get(function)){
				if(!smvPropMap.containsKey(location)) {
					statLocNotUsed.add(location);
					notUsedCounter++;
					notReadCounter++;
					statLocNotUsed.add(location);
				}
				else {
					boolean r = false;
					for(String prop: smvPropMap.get(location)) {
						if(results.get(prop)) {
							r = true;
						}
					}
					if(r) {
						statLocUsedInUnreachCode.add(location);
						notUsedCounter++;
						notReachedCounter++;
						statLocUsedInUnreachCode.add(location);
					}
				}
			}
			if(numLocations == notReadCounter) {
				statFuncNeverUsed.add(function);
			}
			else if(numLocations == notReachedCounter) {
				statFuncUsedInUnreachCode.add(function);
			}
		}
	}

	@Override
	public
	void printResults() {
		out.println("MP: Derived/Static location/function is used.");
		Set<String> locsToIgnore = new HashSet<String>();
		boolean noViolation = true;
		for(String function: derFuncNeverUsed) {
			out.println("Derived function " + function + " is never used.");
			locsToIgnore.addAll(derFuncLocations.get(function));
			noViolation = false;
		}
		for(String location: derLocNotUsed) {
			if(!locsToIgnore.contains(location)) {
				out.println("Derived location " +
						nusmvNameToLocation.get(location) + " is never used.");
				noViolation = false;
			}
		}
		locsToIgnore = new HashSet<String>();
		for(String function: derFuncUsedInUnreachCode) {
			out.println("Derived function " + function
					+ " is used only in unreachable code fragments.");
			locsToIgnore.addAll(derFuncLocations.get(function));
			noViolation = false;
		}
		for(String location: derLocUsedInUnreachCode) {
			if(!locsToIgnore.contains(location)) {
				out.println("Derived location " +
						nusmvNameToLocation.get(location) +
						" is used only in unreachable code fragments.");
				noViolation = false;
			}
		}
		locsToIgnore = new HashSet<String>();
		for(String function: statFuncNeverUsed) {
			out.println("Static function " + function + " is never used.");
			locsToIgnore.addAll(statFuncLocations.get(function));
			noViolation = false;
		}
		for(String location: statLocNotUsed) {
			if(!locsToIgnore.contains(location)) {
				out.println("Static location " +
						nusmvNameToLocation.get(location) + " is never used.");
				noViolation = false;
			}
		}
		locsToIgnore = new HashSet<String>();
		for(String function: statFuncUsedInUnreachCode) {
			out.println("Static function " + function
					+ " is used only in unreachable code fragments.");
			locsToIgnore.addAll(statFuncLocations.get(function));
			noViolation = false;
		}
		for(String location: statLocUsedInUnreachCode) {
			if(!locsToIgnore.contains(location)) {
				out.println("Static location " +
						nusmvNameToLocation.get(location) +
						" is used only in unreachable code fragments.");
				noViolation = false;
			}
		}
		if(noViolation) {
			out.println("NONE.");
		}
		out.println();
	}
}