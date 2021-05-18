package org.asmeta.modeladvisor.metaproperties;

import static java.lang.System.out;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.asmeta.modeladvisor.texpr.Expression;
import org.asmeta.modeladvisor.texpr.SometimeExpression;
import org.asmeta.modeladvisor.texpr.TemporalExpression;
import org.asmeta.nusmv.MapVisitor;
import org.asmeta.nusmv.util.Util;

public class LocationCouldBeRemoved extends Checker {
	private SortedSet<String> contrLocations;
	private Map<String, Set<String>> smvPropMap;
	private Map<String, List<String>> locationReachabilityConds;
	private SortedSet<String> monLocations;
	private List<String> usedLoc;
	private Map<String, String> nusmvNameToLocation;
	private TreeSet<String> controlledLocationInitialized;
	public ArrayList<String> neverUsedMonitoredLocation;
	public ArrayList<String> initButNeverUsedControlledLocation;
	public ArrayList<String> initButUsedInUnreachContrLoc;
	public ArrayList<String> neverUsedControlledLocation;
	private StatDerIsUsed statDerIsUsed;
	private MapVisitor mv;
	public ArrayList<String> usedInUnreachMonLoc;
	public ArrayList<String> usedInUnreachContrLoc;
	private HashSet<String> monUsedInDer;
	private HashSet<String> contrUsedInDer;

	public LocationCouldBeRemoved(MapVisitor mv) {
		this.mv = mv;
		this.locationReachabilityConds = mv.locationReachabilityConds;
		this.contrLocations = mv.contrLocations;
		this.monLocations = mv.monLocations;
		this.usedLoc = mv.env.usedLoc;
		this.nusmvNameToLocation = mv.nusmvNameToLocation;
		this.controlledLocationInitialized = mv.controlledLocationInitialized;
		//verbose = true;
	}

	@Override
	public
	Set<Expression> createNuSmvProperties() {
		TemporalExpression property;
		Set<String> propertySet;
		smvPropMap = new HashMap<String, Set<String>>();
		for(String location: locationReachabilityConds.keySet()) {
			//Sometimes
			//"EF("+ Util.or(locationReachabilityConds.get(location)) +")"
			//"AG(!("+ Util.or(locationReachabilityConds.get(location)) +"))"
			property = new SometimeExpression(Util.or(locationReachabilityConds.get(location)));
			smvProp.add(property);

			propertySet = new HashSet<String>();
			propertySet.add(property.getSMV());
			smvPropMap.put(location, propertySet);
		}
		statDerIsUsed = new StatDerIsUsed(mv.derFuncLocations,
											mv.statFuncLocations,
											mv.nusmvNameToLocation,
											mv.statDerReachabilityConds,
											mv.usedStatDerInDer);
		smvProp.addAll(statDerIsUsed.createNuSmvProperties());
		return smvProp;
	}

	/**
	 * Valuta se una locazione controllata o monitorata puo' essere rimossa.
	 * Quando una locazione non viene mai letta o aggiornata,
	 * anche se e' stata inizializzata, puo' essere rimossa.
	 * Viene segnalato anche se una locazione e' contenuta solo in porzioni
	 * irraggiungibili di codice.
	 */
	@Override
	public void evaluation(Map<String, Boolean> results) {
		statDerIsUsed.evaluation(results);
		neverUsedMonitoredLocation = new ArrayList<String>();
		usedInUnreachMonLoc = new ArrayList<String>();
		initButNeverUsedControlledLocation = new ArrayList<String>();
		initButUsedInUnreachContrLoc = new ArrayList<String>();
		
		neverUsedControlledLocation = new ArrayList<String>();
		usedInUnreachContrLoc = new ArrayList<String>();
		//solo le locazioni controllate e monitorate
		List<String> locations = new ArrayList<String>(contrLocations);
		locations.addAll(monLocations);
		for(String location: locations) {
			//loc = nusmvNameToLocation.get(location);//notazione AsmetaL
			//se non e' presente in usedLocation e' una locazione che non viene
			//mai usata. Potrebbe, pero', essere stata inizializzata.
			if(!usedLoc.contains(location)){
				if(monLocations.contains(location)){
					neverUsedMonitoredLocation.add(location);
				}
				else if(controlledLocationInitialized.contains(location)){
					initButNeverUsedControlledLocation.add(location);
				}
				else {
					neverUsedControlledLocation.add(location);
				}
			}
			else {
				//anche se e' in usedLocation, potrebbe essere in un pezzo di 
				//codice irraggiungibile e quindi cancellata.
				//index = locCouldBeRemovedIndex.get(location);
				if(smvPropMap.containsKey(location)) {
					boolean r = false;
					for(String prop: smvPropMap.get(location)) {
						assert results.get(prop) != null: prop + "\n" + results;
						if(results.get(prop)) {
							r = true;
							break;
						}
					}
					if(r) {
						if(monLocations.contains(location)){
							usedInUnreachMonLoc.add(location);
						}
						else if(controlledLocationInitialized.contains(location)){
							initButUsedInUnreachContrLoc.add(location);
						}
						else {
							usedInUnreachContrLoc.add(location);
						}
					}
				}
			}
		}
		monUsedInDer = new HashSet<String>();
		contrUsedInDer = new HashSet<String>();
		for(String location: locations) {
			if(monLocations.contains(location)) {
				for(String statDer: statDerIsUsed.statDerReachabilityConds.keySet()) {
					if((!statDerIsUsed.derLocNotUsed.contains(statDer) &&
						!statDerIsUsed.derLocUsedInUnreachCode.contains(statDer) &&
						!statDerIsUsed.statLocNotUsed.contains(statDer) &&
						!statDerIsUsed.statLocUsedInUnreachCode.contains(statDer)) &&
						mv.usedContrMonInDer.get(statDer).contains(location)) {
						monUsedInDer.add(location);

						usedInUnreachMonLoc.remove(location);
						neverUsedMonitoredLocation.remove(location);
					} 
				}
			}
			else {
				for(String statDer: statDerIsUsed.statDerReachabilityConds.keySet()) {
					if((!statDerIsUsed.derLocNotUsed.contains(statDer) &&
							!statDerIsUsed.derLocUsedInUnreachCode.contains(statDer) &&
							!statDerIsUsed.statLocNotUsed.contains(statDer) &&
							!statDerIsUsed.statLocUsedInUnreachCode.contains(statDer)) &&
						mv.usedContrMonInDer.get(statDer).contains(location)) {
						contrUsedInDer.add(location);

						initButNeverUsedControlledLocation.remove(location);
						initButUsedInUnreachContrLoc.remove(location);
						neverUsedControlledLocation.remove(location);
					} 
				}
			}
		}
	}

	@Override
	public
	void printResults() {
		out.println("MP7: a location could be removed");
		List<String> locations = new ArrayList<String>(contrLocations);
		locations.addAll(monLocations);
		String location;
		boolean noViolation = true;
		for(String loc: locations) {
			location = nusmvNameToLocation.get(loc);//notazione AsmetaL
			if(monLocations.contains(location)) {
				if(!monUsedInDer.contains(location)) {
					if(neverUsedMonitoredLocation.contains(location)) {
						out.println("Monitored location " + location+ " is never used. It could be removed.");
						noViolation = false;
					}
					else if (usedInUnreachMonLoc.contains(location)) {
						out.println("Monitored location " + location + " it's used only in unreachable code fragment(s).");
						noViolation = false;
					}
				}
			}
			else {
				if(neverUsedControlledLocation.contains(location)) {
					out.println("Controlled location " + location+ " is never used. It could be removed.");
					noViolation = false;
				}
				else if (usedInUnreachContrLoc.contains(location)) {
					out.println("Monitored location " + location + " it's used only in unreachable code fragment(s).");
					noViolation = false;
				}
				else if(initButNeverUsedControlledLocation.contains(location)) {
					out.println("Controlled location " + location + " is initialized but then it's used only in unreachable code fragment(s).");
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