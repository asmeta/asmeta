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
package atgt.specification.constraints;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// TODO: Auto-generated Javadoc
/**
 * The Class ConstraintCatcherTest.
 */
public class ConstraintCatcherTest {

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		Pattern pattern = Pattern.compile("next\\Q(\\E\\w+\\Q)\\E");

		Matcher matcher = pattern.matcher("next(Xddw)");

		boolean found = false;
		while (matcher.find()) {
			System.out.printf("I found the text \"%s\" starting at "
					+ "index %d and ending at index %d.%n", matcher.group(),
					matcher.start(), matcher.end());
			found = true;
		}
		if (!found) {
			System.out.println("No match found.%n");
		}
	}
}
