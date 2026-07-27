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
package atgt.generator;

import static atgt.preferences.ATGToolPreferences.CollectTPS;
import static atgt.preferences.ATGToolPreferences.ConsiderInitNext;
import static atgt.preferences.ATGToolPreferences.SAL_BMCdepth;
import static atgt.preferences.ATGToolPreferences.SAL_PROGRAM;
import static atgt.preferences.ATGToolPreferences.ShuffleSAL;
import static atgt.preferences.ATGToolPreferences.TP_ORDERING;

import java.util.Map;

import atgt.preferences.ATGToolPreferences.OrderKind;
import atgt.specification.location.Location;
import atgt.specification.location.Variable;

/**
 * The Class SALGenerationUtil.
 */
public class SALGenerationUtil {

	public enum WHICH_MC {
		sal_smc, sal_bmc;
	}

	public enum TP_ORDERING {
		AS_GENERATED, RANDOM, NOVELTY, ANTIDIAGONAL;
	}

	
	/**
	 * Sets the parameters.
	 *
	 * @param collect
	 *            the collect
	 * @param random
	 *            apply random
	 * @param novelty
	 *            the novelty
	 * @param antidiagonal
	 *            the antidiagonal
	 * @param shuffle
	 *            the shuffle
	 * @param initNext
	 *            consider the init next
	 * @param which_mc
	 *            the which model checker to use
	 *
	 * @return the string
	 */
	static public String setParameters(boolean collect,
			TP_ORDERING order, boolean shuffle,
			boolean initNext) {

		String option = "[collect:" + collect + "] [ordering:" + order
				+ "] [shuffle:" + shuffle + "] [next&init:" + initNext + "]";
		CollectTPS.setChecked(collect);
		switch (order) {
		case AS_GENERATED:
			TP_ORDERING.setValue(OrderKind.AS_GENERATED);
			break;
		case RANDOM:
			TP_ORDERING.setValue(OrderKind.RANDOM);
			break;
		case NOVELTY:
			TP_ORDERING.setValue(OrderKind.NOVELTY);
			break;
		case ANTIDIAGONAL:
			TP_ORDERING.setValue(OrderKind.ANTIDIAGONAL);
			break;
		default:
			throw new RuntimeException("CHE ORDINE ???");
		}
		ShuffleSAL.setChecked(shuffle);
		ConsiderInitNext.setChecked(initNext);
		return option;
	}



	public static String setModelChecker(WHICH_MC which_mc) {
		String option;
		if (which_mc == WHICH_MC.sal_bmc) {
			SAL_PROGRAM.setValue("sal-bmc");
			SAL_BMCdepth.setChecked(true);
			// do not set to 1, otherwise it won't work !!!
			SAL_BMCdepth.setValue("2");
			option = "[mc: BMC] ";
		} else {
			atgt.preferences.ATGToolPreferences.SAL_PROGRAM.setValue(
					"sal-smc");
			option = "[mc: SMC] ";
		}
		return option;
	}
	
	

	/** return the value of a String given a variable */	
	static public String getValue(String var, Map<Location, String> test){
		for (Map.Entry<Location, String> i: test.entrySet()){
			if (i.getKey().getName().equals(var)) return i.getValue();			
		}
		return null;
	}

}
