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
package atgt.translator;

import atgt.specification.type.DummyType;
import tgtlib.definitions.expression.type.BoolType;
import tgtlib.definitions.expression.type.BoundType;
import tgtlib.definitions.expression.type.ElementsType;
import tgtlib.definitions.expression.type.EnumConst;
import tgtlib.definitions.expression.type.EnumType;
import tgtlib.definitions.expression.type.IntegerType;
import tgtlib.definitions.expression.type.TypeVisitorI;

/**
 * The Class TypeToSPINVisitor return the string for a type in SPIN (similar to
 * C)
 */
public class TypeToSPINVisitor implements TypeVisitorI<String> {

	/** translate the boolean type? not necessary in spin */
	private static final boolean TRANS_BOOL = false;

	public String forElementsType(ElementsType c) {
		String result;
		int counter = 0;
		result = "/* Translation for " + c.getName() + "*/ \n";
		for (EnumConst e : c.allElements()) {
			result += "#define " + e.toString() + " " + (counter++) + "\n";
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.type.TypeVisitorI#forBoundType(atgt.specification.type
	 * .BoundType)
	 */
	@Override
	public String forBoundType(BoundType c) {
		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.type.TypeVisitorI#forDummyType(atgt.specification.type
	 * .DummyType)
	 */
	public String forDummyType(DummyType d) {
		return "";
	}

	@Override
	public String forIntegerType(IntegerType intType) {
		throw new RuntimeException("visitor not defined yet");
	}

	@Override
	public String forEnumType(EnumType enumType) {
		return forElementsType(enumType);
	}

	@Override
	public String forBoolType(BoolType boolType) {
		if (!TRANS_BOOL) {
			return "";
		}
		else {
			return forElementsType(boolType);
		}
	}
}
