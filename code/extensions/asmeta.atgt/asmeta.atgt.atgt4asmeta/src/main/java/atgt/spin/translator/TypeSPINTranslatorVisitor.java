/*******************************************************************************
 * Copyright (c) 2010 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Angelo Gargantini - initial API and implementation
 ******************************************************************************/
package atgt.spin.translator;

import tgtlib.definitions.expression.type.BoolType;
import tgtlib.definitions.expression.type.BoundType;
import tgtlib.definitions.expression.type.EnumType;
import tgtlib.definitions.expression.type.IntegerType;
import tgtlib.definitions.expression.type.TypeVisitorI;

/**
 * This class convert a type name for a declaration in SPIN. For example. A type
 * in ASMGopher of kind: <BR>
 * 
 * <PRE>
 *  &lt;B&gt;data&lt;/B&gt; Switch = On | Off
 * </PRE>
 * 
 * will become:
 * 
 * <PRE>
 *  #define On 0
 * <BR>
 * #define Off 1
 * <BR>
 * </PRE>
 * 
 * A variable declaration will become :
 * 
 * <PRE>
 *  byte reset = On
 * </PRE>
 * 
 * In this way, the enumeration type in Gopher are modelled with byte constants
 * 
 * @author Sax Rinzivillo, Sergio Galati
 */

public class TypeSPINTranslatorVisitor implements TypeVisitorI<String> {

	private static final int MAX_SHORT_SPIN = +32767;
	private static final int MIN_SHORT_SPIN = -32767;

	public static TypeSPINTranslatorVisitor instance = new TypeSPINTranslatorVisitor();

	private TypeSPINTranslatorVisitor(){
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.type.TypeVisitorI#forEnumType(atgt.specification.type
	 * .EnumType)
	 */
	@Override
	public String forEnumType(EnumType c) {
		// get cardinality
		int size = c.range();
		assert (size < 255);
		// TODO Improve with subtypes of int !!! (like char...)???
		if (size == 2)
			return "bool";
		else
			return "byte";
	}

	@Override
	public String forBoolType(BoolType boolType) {
		return "bool";
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.type.TypeVisitorI#forBoundType(atgt.specification.
	 * type.BoundType)
	 */
	@Override
	public String forBoundType(BoundType c) {
		// can be a byte?
		if ((c.getLow() >= 0) && (c.getUp() <= 255))
			return "byte";
		// can be short?
		if ((c.getLow() >= MIN_SHORT_SPIN) && (c.getUp() <= MAX_SHORT_SPIN))
			return "short";
		return "int";
	}

	@Override
	public String forIntegerType(IntegerType intType) {
		throw new RuntimeException(
				"Integer types cannot be tralsate to SPin !!!");
	}
}