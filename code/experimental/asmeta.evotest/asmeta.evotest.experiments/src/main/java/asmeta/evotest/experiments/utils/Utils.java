package asmeta.evotest.experiments.utils;

public class Utils {
	
	/**
	 * Formats the numeric counter as a zero-padded three-digit string
	 * (e.g., 0 → "000", 7 → "007", 42 → "042").
	 *
	 * @param counter a non-negative integer
	 * @return the formatted string
	 */
	public static String formatCounter(int coutner) {
		String countString = String.valueOf(coutner);
		if (coutner < 10)
			countString = "00" + coutner;
		else if (coutner < 100)
			countString = "0" + coutner;
		return countString;
	}

}
