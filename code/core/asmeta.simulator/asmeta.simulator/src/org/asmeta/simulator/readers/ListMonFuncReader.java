package org.asmeta.simulator.readers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.asmeta.simulator.Location;
import org.asmeta.simulator.State;
import org.asmeta.simulator.util.InputMismatchException;
import org.asmeta.simulator.util.Parser;
import org.asmeta.simulator.value.Value;

import asmeta.definitions.Function;

/**
 * AP
 *
 */
public class ListMonFuncReader extends MonFuncReader {
	//private ArrayList<Map<Location, String>> in;
	//private Iterator<Map<Location, String>> inIterator;
	//private Map<Location, String> currentMap;
	//private int index;
	
	Map<Location, ArrayList<String>> newMap;
	
	public ListMonFuncReader(ArrayList<Map<Location, String>> list) {
		//in = list;
		//inIterator = in.iterator();
		//index = 0;
		//System.out.println("list "+list);
		//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ elementi "+list.size());
		
		newMap = new HashMap<Location, ArrayList<String>>();
		for(Map<Location, String> map: list) {
			for(Entry<Location, String> entryLocValue: map.entrySet()) {
				if(!newMap.containsKey(entryLocValue.getKey())) {
					newMap.put(entryLocValue.getKey(), new ArrayList<String>());
				}
				newMap.get(entryLocValue.getKey()).add(entryLocValue.getValue());
			}
		}
		//System.out.println("newMap " + newMap);
	}

	@Override
	public Value readValue(Location location, State state) {
		Function func = location.getSignature();
		ArrayList<String> locList = newMap.get(location);
		String value = locList.remove(0);
		try {
			return new Parser(value).visit(func.getCodomain());
		} catch (InputMismatchException e) {
			throw new RuntimeException(e);
		}
	}

	/* @Override
	public Value readValue(Location location, State state) {
		//if(currentMap == null) {
		if(currentMap == null || (index == currentMap.size())) {
			//System.out.println("################################ change");
			index = 0;
			currentMap = this.readLine();
			//System.out.println("currentMap " + currentMap);
		}
		Function func = location.getSignature();
		try {
			index++;
			//System.out.println("location " + location);
			//System.out.println(location +" ** "+new Parser(currentMap.get(location)).visit(func.getCodomain()));
			return new Parser(currentMap.get(location)).visit(func.getCodomain());
		} catch (InputMismatchException e) {
			throw new RuntimeException(e);
		}
	}

	private Map<Location, String> readLine() {
		return inIterator.next();
	}*/
}