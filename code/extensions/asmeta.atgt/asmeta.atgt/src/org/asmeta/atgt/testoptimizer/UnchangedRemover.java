package org.asmeta.atgt.testoptimizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import atgt.coverage.AsmTestSequence;
import atgt.specification.location.Location;

// removes values that are unchanged wrt the previous state
// both monitored and controlled
public class UnchangedRemover extends TestOptimizer {	
	
	// only monitored
	public static UnchangedRemover eInstance = new UnchangedRemover(false);
	
	// monitored and controlled
	public static UnchangedRemover eInstanceAll = new UnchangedRemover(true);

	private boolean removeAlsoControlled;
	
	private UnchangedRemover(boolean b) {
		this.removeAlsoControlled = b;
	}
	
	@Override
	public void optimize(AsmTestSequence asmTest){
		// 
//		Map<Location,String> lastvalues = new HashMap<>();
//		List<Location> toRemove = new ArrayList<>();
//		for(Map<Location, String> state: asmTest.allInstructions()) {			
//			// check the values that do not change
//			for (Iterator<Entry<Location, String>> iterator = state.entrySet().iterator(); iterator.hasNext();) {
//				Entry<Location, String> assignemnt = iterator.next();
//				Location loc = assignemnt.getKey();
//				String val = assignemnt.getValue();
//				// remove only if controlled if it is asked to remove all
//				if (lastvalues.get(loc) != null && lastvalues.get(loc).equals(val) && (loc.isControlled() || removeAlsoControlled)) {
//					//remove this entry loc and val
//					toRemove.add(loc);
//				} else {
//					lastvalues.put(loc, val);
//				}				
//			}
//			//
//			System.out.println("pre" +state);
//			for(Location l: toRemove) 
//				state.remove(l);
// USING THE STRINGS INSTEAD		
		Map<String,String> lastvalues = new HashMap<>();
		List<Location> toRemove = new ArrayList<>();
		for(Map<Location, String> state: asmTest.allInstructions()) {			
			// check the values that do not change
			for (Iterator<Entry<Location, String>> iterator = state.entrySet().iterator(); iterator.hasNext();) {
				Entry<Location, String> assignemnt = iterator.next();
				Location loc = assignemnt.getKey();
				String val = assignemnt.getValue();
				// remove only if controlled if it is asked to remove all
				if (lastvalues.get(loc.toString()) != null && lastvalues.get(loc.toString()).equals(val) && (loc.isControlled() || removeAlsoControlled)) {
					//remove this entry loc and val
					toRemove.add(loc);
				} else {
					lastvalues.put(loc.toString(), val);
				}				
			}
			//
			for(Location l: toRemove) 
				state.remove(l);
			toRemove.clear();
		}
	}

}
