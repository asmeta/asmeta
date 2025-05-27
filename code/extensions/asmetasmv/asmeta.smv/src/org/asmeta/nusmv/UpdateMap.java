package org.asmeta.nusmv;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.asmeta.nusmv.util.AsmNotSupportedException;
import org.asmeta.nusmv.util.Util;

//
public class UpdateMap {
	
	static final Logger log = Logger.getLogger(UpdateMap.class);
	
	private SortedMap<String, LinkedHashMap<String, String>> updateMap;

	UpdateMap() {
		updateMap = new TreeMap<String, LinkedHashMap<String, String>>();
	}

	public Set<String> keySet() {
		return updateMap.keySet();
	}

	public void put(String location) {
		updateMap.put(location, new LinkedHashMap<String, String>());
	}

	public String get(String location, String cond) {
		return updateMap.get(location).get(cond);
	}

	public Map<String, String> get(String location) {
		return updateMap.get(location);
	}

	/**
	 * Update.
	 * 
	 * @param location
	 *            the location
	 * @param cond
	 *            the cond
	 * @param value
	 *            the value
	 * 
	 * @throws Exception
	 *             the exception
	 */
	private void update(String location, String cond, String value) {
		// System.out.println(updateMap);
		// System.out.println("location " + location + " cond " + cond +
		// " value " + value);
		if (!cond.equals(Util.falseString)) {
			// updateMap.get(location).put(cond, value);
			// System.out.println("location "+location+" updateMap.get(location)
			// "+updateMap.get(location));
			Map<String, String> map = updateMap.get(location);
			// assert map != null : location + " does not have an update map";
			if (map == null) {
				//System.out.println("Location " + location + " should not exist in the ASM model. Check that it is never used. It is not exported to NuSMV.");
				return;
			}
			if (map.keySet().contains(cond)) {
				if (!map.get(cond).equals(value)) {
					// throw new
					// AsmNotSupportedException("Update inconsistente: la locazione "+location+
					// " viene aggiornata sia al valore "+map.get(cond)+" sia al valore "+value+"
					// sotto la condizione "+cond);
				}
			}
			String alreadySetVal = map.get(cond);
			if (alreadySetVal != null) {
				if (!alreadySetVal.equals(value)) {
					log.fatal("inconsistent update");
					throw new InconsistentUpdateException(cond,location,value,alreadySetVal);
				}
			}
			map.put(cond, value);
		}
	}

	/**
	 * Update.
	 * 
	 * @param location
	 *            the location
	 * @param conds
	 *            the conds
	 * @param value
	 *            the value
	 * 
	 * @throws Exception
	 *             the exception
	 */
	void update(String location, List<String> conds, String value) {
		update(location, Util.and(conds), value);
	}

	/**
	 * Update.
	 * 
	 * @param location
	 *            the location
	 * @param map
	 *            the map
	 * 
	 * @throws Exception
	 *             the exception
	 */
	void update(String location, Map<List<String>, String> map) {
		for (Entry<List<String>, String> cond : map.entrySet()) {
			update(location, cond.getKey(), cond.getValue());
		}
	}

	void update(Map<String, Map<List<String>, String>> map) {
		for (Entry<String, Map<List<String>, String>> location : map.entrySet()) {
			update(location.getKey(), location.getValue());
		}
	}

	int getSize() {
		return updateMap.size();
	}

	// non finito
	/*
	 * void compactUpdateMap(){ Map<String, String> map; Map<String, Map<String,
	 * String>> newUpdateMap = new TreeMap<String, Map<String, String>>();
	 * Map<String, String> newMap; List<String> values; List<String> valuesTemp;
	 * for(String location: updateMap.keySet()){ map = updateMap.get(location);
	 * newMap = new HashMap<String, String>(); values = new
	 * ArrayList<String>(map.values()); Collections.sort(values); valuesTemp = new
	 * ArrayList<String>(values); for(String cond: valuesTemp){
	 * while(values.remove(cond)); values.add(cond); } for(String value: values){
	 * for(String cond: map.keySet()){
	 * 
	 * } } } }
	 */
}
