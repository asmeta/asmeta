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
package tgtlib.definitions.expression.type;

/**
 * The Class BoolType.
 */
public final class BoolType extends ElementsType{

	public static final String FALSE_STR = "false";

	public static final String TRUE_STR = "true";

	/** The Constant BOOLTYPE. */
	static final public BoolType BOOLTYPE = new BoolType();

	public static class BoolConst extends EnumConst{
		BoolConst(String id) {
			super(id);
		}
	}
	
	/** The Constant falseConst. */
	static final public BoolType.BoolConst FALSE_CONST = new BoolType.BoolConst(FALSE_STR);
	/** The Constant trueConst. */
	static final public BoolType.BoolConst TRUE_CONST = new BoolType.BoolConst(TRUE_STR);

	static {
		// add as first so false has 0
		BOOLTYPE.addElement(FALSE_CONST);
		// add as second so true has 1
		BOOLTYPE.addElement(TRUE_CONST);
		
	}

	public static BoolConst not(BoolConst b) {
		if(b.equals(FALSE_CONST)) {
			return TRUE_CONST;
		}
		else {
			return FALSE_CONST;
		}
	}

	/**
	 * Instantiates a new bool type.
	 */
	private BoolType() {
		super("Bool");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.type.EnumType#range()
	 */
	@Override
	public int range() {
		return 2;
	}

	/**
	 * A method for visitor pattern.
	 * 
	 * @param ask
	 *            the ask
	 * 
	 * @return the T
	 */
	@Override
	public <T> T accept(TypeVisitorI<T> ask) {
		return ask.forBoolType(this);
	}	
}