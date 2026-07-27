/*******************************************************************************
 * Copyright (c) 2008 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Angelo Gargantini - initial API and implementation
 ******************************************************************************/
package atgt.preferences;

import tgtlib.preferences.CheckedPreference;
import tgtlib.preferences.ChoicePreferenceEnum;
import tgtlib.preferences.FlagPreference;
import tgtlib.preferences.IntegerPreference;
import tgtlib.preferences.PreferenceBundle;
import tgtlib.preferences.StringPreference;
import tgtlib.preferences.TGLibPreferences;

/**
 * to store the preferences for ATGT.
 * 
 * @author garganti
 */
public class ATGToolPreferences extends TGLibPreferences {

	// generic properties

	/** The WORK dir. */
	public static StringPreference WORK_DIR = new StringPreference("work_directory", System.getProperty("user.home") + "/.atgt","work directory");

	/** The FONT size. */
	public static IntegerPreference FONT_SIZE = new IntegerPreference("font_size", 12,"font size");

	/** The TIME cmd. */
	public static StringPreference TIME_CMD = new StringPreference("time_command", "time","time os command");

	/** The VIE w_ rul e_ graphic. */
	public static FlagPreference VIEW_RULE_GRAPHIC = new FlagPreference("GRAPHIC", true, "view rule in graphic mode:");

	/** the degree of combinatorial testing */
	public static IntegerPreference COMB_DEGREE = new IntegerPreference("n-wise-degree",3,"degree of combinatorial testing");

	/** The GENERI c_ prefs. */
	public static PreferenceBundle GENERIC_PREFS = new PreferenceBundle("GENERIC", "ATGT");

	
	
	static {
		GENERIC_PREFS.add(WORK_DIR);
		GENERIC_PREFS.add(FONT_SIZE);
		// GENERIC_PREFS.add(TEMP_DIR);
		// GENERIC_PREFS.add(DELETE_TMP);
		GENERIC_PREFS.add(TIME_CMD);
		GENERIC_PREFS.add(VIEW_RULE_GRAPHIC);
		GENERIC_PREFS.add(COMB_DEGREE);
	};

	//
	// (generic) preferences about SPIN
	//
	/** The SPIN program. */
	public static StringPreference SPIN_PROGRAM = new StringPreference("spin_program","spin","spin program");
	
	/** The minus i lower case. */
	public static FlagPreference minusILowerCase = new FlagPreference("-i", false, "Search for shortest path (-i)");

	/** The minus i upper case. */
	public static FlagPreference minusIUpperCase = new FlagPreference("-I", false, "-I like -i, faster");

	/** The MAXDEPTH. */
	public static CheckedPreference MAXDEPTH = CheckedPreference.createCheckedIntPreference("-m", 1000000, "Max Depth");

	/** The DIMHASHTABLE. */
	public static CheckedPreference DIMHASHTABLE = CheckedPreference.createCheckedIntPreference("-w", 25, "Dim of HashTable");

	// for traslate rule to spin in flatting mode
	/** The FLATTING. */
	public static FlagPreference FLATTING = new FlagPreference("-FLAT", false, "Traslate rule planning");

	
	/** The d step. */
	public static FlagPreference USE_D_STEP = new FlagPreference("d_step", true, "Use d _ step in Spin translation");

	/** The atomic. */
	public static FlagPreference USE_ATOMIC = new FlagPreference("atomic", true, "Use atomic in Spin translation");

	/** The SPINOPTION. */
	public static PreferenceBundle SPINOPTION = new PreferenceBundle("Spin", "ATGT");
	static {
		SPINOPTION.add(SPIN_PROGRAM);
		SPINOPTION.add(minusILowerCase);
		SPINOPTION.add(minusIUpperCase);
		SPINOPTION.add(MAXDEPTH);
		SPINOPTION.add(DIMHASHTABLE);
		SPINOPTION.add(FLATTING);
		SPINOPTION.add(USE_D_STEP);
		SPINOPTION.add(USE_ATOMIC);
	};

	// ATTENZIONE THE KEY MUST BE EQUAL TO THE COMPILER OPTION
	/** The MEMLIM. */
	public static CheckedPreference MEMLIM = CheckedPreference.createCheckedIntPreference("-DMEMLIM", 200, "Memory used");

	/** The BITSTATE. */
	public static FlagPreference BITSTATE = new FlagPreference("-DBITSTATE", false, "BitState");

	/** The HC. */
	public static CheckedPreference HC = CheckedPreference.createCheckedIntPreference("-DHC", 2, "State vector compression mode_1");

	/** The COLLAPSE. */
	public static FlagPreference COLLAPSE = new FlagPreference("-DCOLLAPSE", false, "State vector compression mode_2");

	/** The REACH. */
	public static FlagPreference REACH = new FlagPreference("-DREACH", false, "guarantee absence of errors");

	/** The BFS. */
	public static FlagPreference BFS = new FlagPreference("-DBFS", false, "use breadth-first instead of DFS");

	/** The BFS. */
	public static FlagPreference SAFETY = new FlagPreference("-DSAFETY", true, "use SAFETY optimizations");

	/** The SPI n_ compil e_ option. */
	public static PreferenceBundle SPIN_COMPILE_OPTION = new PreferenceBundle("Spin cc option", "ATGT");
	static {
		SPIN_COMPILE_OPTION.add(MEMLIM);
		SPIN_COMPILE_OPTION.add(BITSTATE);
		SPIN_COMPILE_OPTION.add(HC);
		SPIN_COMPILE_OPTION.add(COLLAPSE);
		SPIN_COMPILE_OPTION.add(REACH);
		SPIN_COMPILE_OPTION.add(BFS);
		SPIN_COMPILE_OPTION.add(SAFETY);
	};

	// / FOR SAL

	public static final String SAL_SMC = "sal-smc";
	public static final String SAL_BMC = "sal-bmc";
	private static String[] SAL_MCS = { SAL_SMC, SAL_BMC };

	/** The SAL_ program. */
	// public static ChoicePreference SAL_PROGRAM = new
	// ChoicePreference("sal_mc","sal mc program",SAL_MCS);
	public static StringPreference SAL_PROGRAM = new StringPreference("sal_program", SAL_SMC,"sal mc program");

	/** The Shuffle sal. */
	public static FlagPreference ShuffleSAL = new FlagPreference("shuffle", true, "Shuffle domains");
	// public static FlagPreference depth1 = new FlagPreference("depth1", true,
	// "use depth 1 if possible");

	/** as generated order */
	public enum OrderKind {
		// as generated 
		AS_GENERATED,RANDOM,NOVELTY,
		// novelty improved
		NOVELTY_COL,
		// antidiagonal
		ANTIDIAGONAL}

	/** The Random. */
	public static ChoicePreferenceEnum<OrderKind> TP_ORDERING = new ChoicePreferenceEnum<ATGToolPreferences.OrderKind>("tp_oder", "test predicate ordering", ATGToolPreferences.OrderKind.RANDOM);

	/** The Integer. */
	//public static FlagPreference Integer = new FlagPreference("int_analyser", false, "analyze integer domains");

	/** The Collect tps. */
	public static FlagPreference CollectTPS = new FlagPreference("collect", true, "collect tps");

	/** The Consider init next. */
	public static FlagPreference ConsiderInitNext = new FlagPreference("cinitnext", true, "Consider init & next");

	/** depth of sal-bmc */
	public static CheckedPreference SAL_BMCdepth = CheckedPreference.createCheckedIntPreference("bmc_depth", 1, "--depth=");
	/** The SALOPTION. */
	public static PreferenceBundle SALOPTION = new PreferenceBundle("SAL", "ATGT");

	static {
		SALOPTION.add(SAL_PROGRAM);
		SALOPTION.add(ShuffleSAL);
		SALOPTION.add(TP_ORDERING);
		// AG_INT_VERSION 
		//SALOPTION.add(Integer);
		SALOPTION.add(CollectTPS);
		SALOPTION.add(ConsiderInitNext);
		SALOPTION.add(SAL_BMCdepth);
	};

	/**
	 * constructor.
	 */
	private ATGToolPreferences() {
		super("atgt");
		add(GENERIC_PREFS);
		add(SPINOPTION);
		add(SPIN_COMPILE_OPTION);
		add(SALOPTION);
	}

	/** The ATG t_ prefs. */
	public static ATGToolPreferences ATGT_PREFS = new ATGToolPreferences();

}
