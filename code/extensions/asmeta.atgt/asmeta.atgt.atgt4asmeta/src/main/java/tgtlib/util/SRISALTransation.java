/*******************************************************************************
 * Copyright (c) 2008, 2010 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Angelo Gargantini - initial API and implementation
 ******************************************************************************/
package tgtlib.util;
/** some utilities to translate to SRISA * @author garganti
 * @version $Revision: 1.0 $
L*/
public class SRISALTransation {

	
	/**
	 * given a variable with type integer bewteen two bounds
	 * 
	 * @param var the variable
	 * @param typeName the name
	 * @param low the low
	 * @param up the up
	 * @param maxVariation the max variation
	 * @param changeForced TODO
	
	 * @return the translation to SRI TRANS */
	public static StringBuffer toSriSalTrans(String var, String typeName, int low, int up,int maxVariation, boolean changeForced) {
		StringBuffer result;
		if (maxVariation == 0)
			result = new StringBuffer("{x: "+ typeName +"|" + 
					low + " <= x AND x <= " + up );
		else
			result = new StringBuffer("{x: "+ typeName +"| "+
					"( IF "+var+" -" + maxVariation + " < "+low+" THEN "+low+
							" ELSE "+var+" -"+maxVariation+" ENDIF)<= x AND x <= "+
					"( IF "+var+" +" + maxVariation + " > " +up+" THEN "+ up + 
							" ELSE "+var+" +" + maxVariation+ " ENDIF)");
		// if changeforced
		if (changeForced) result.append(" AND x/=" + var);
		result.append('}');
		return result;
	}

}
