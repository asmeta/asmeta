package org.asmeta.flattener.statistics;

import java.util.HashMap;
import java.util.LinkedHashMap;

import asmeta.structure.Asm;

public final class Statistics {
	private static Statistics INSTANCE = new Statistics();
	private LinkedHashMap<String, Integer> infoMap = new LinkedHashMap<String, Integer>();
	public LinkedHashMap<String, Integer> getInfoMap() {
		return infoMap;
	}

	private boolean enabled = false;

	private Statistics() {
		infoMap = new LinkedHashMap<String, Integer>();
		infoMap.put("MCR", 0);
		infoMap.put("FR", 0);
		infoMap.put("ChR", 0);
		infoMap.put("AR", 0);
		infoMap.put("LR", 0);
		infoMap.put("CaR", 0);
		infoMap.put("TS", 0);
		infoMap.put("RS", 0);
	}

	public static void resetStats() {
		INSTANCE = new Statistics();
	}

	public static Statistics getInstance() {
		return INSTANCE;
	}

	/*
	 * public HashMap<String, Integer> getInfoMap() { return infoMap; }
	 */

	public void increaseValue(String s) {
		Integer i = infoMap.get(s);
		if (i == null) {
			assert false: s;
			infoMap.put(s, 1);
		} else {
			infoMap.put(s, i + 1);
		}
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void getPlainStatistics(Asm asm, Asm asmRefactored) {
		StatisticsVisitor statisticsVisitor = new StatisticsVisitor(asm);
		statisticsVisitor.visitAsm();
		Integer initialNes = statisticsVisitor.getMaxNestingLevel();
		statisticsVisitor = new StatisticsVisitor(asmRefactored);
		statisticsVisitor.visitAsm();
		Integer finalNes = statisticsVisitor.getMaxNestingLevel();
		System.out.print(asm.getName());
		for (String s : infoMap.keySet()) {
			if(!s.equals("TS") && !s.equals("RS")) {
				System.out.print("\t" + infoMap.get(s));
			}
		}
		System.out.println("\t" + (initialNes - finalNes));
	}

	public void getStatistics(Asm asm, Asm asmRefactored) {
		if (enabled) {
			float initialRules = 0.0f;
			float finalRules = 0.0f;
			float initialNes = 0.0f;
			float finalNes = 0.0f;

			System.out.println("------before-flattening-----");
			StatisticsVisitor statisticsVisitor = new StatisticsVisitor(asm);
			statisticsVisitor.visitAsm();
			initialNes = statisticsVisitor.getMaxNestingLevel();
			HashMap<String, Integer> rulesMap = statisticsVisitor.getRulesMap();
			System.out.println("max nesting level: " + initialNes);
			System.out.println("------------------------");
			System.out.println("# of rules: ");
			for (String s : rulesMap.keySet()) {
				System.out.println(s + " : " + rulesMap.get(s));
				// if (!s.equals("updateRule"))
				initialRules += rulesMap.get(s);
			}
			System.out.println("------------------------");

			System.out.println("-------FLATTENING-------");
			// how many times flatteners has been used
			for (String s : infoMap.keySet()) {
				System.out.println(s + " : " + infoMap.get(s));
			}
			System.out.println("------------------------");

			System.out.println("------after-flattening-----");
			statisticsVisitor = new StatisticsVisitor(asmRefactored);
			statisticsVisitor.visitAsm();
			finalNes = statisticsVisitor.getMaxNestingLevel();
			rulesMap = statisticsVisitor.getRulesMap();
			System.out.println("max nesting level: " + finalNes);
			System.out.println("------------------------");
			System.out.println("# of rules: ");
			for (String s : rulesMap.keySet()) {
				System.out.println(s + " : " + rulesMap.get(s));
				// if (!s.equals("updateRule"))
				finalRules += rulesMap.get(s);
			}
			System.out.println("------------------------");
			System.out.println("Increase of rules: " + ((finalRules - initialRules) / initialRules) * 100 + "%");
			System.out.println("------------------------");
			System.out.println("Increase of nesting: " + ((finalNes - initialNes) / initialNes) * 100 + "%");
		}
	}

	public static void resetMap() {
		INSTANCE.infoMap = new LinkedHashMap<>();
	}
}
