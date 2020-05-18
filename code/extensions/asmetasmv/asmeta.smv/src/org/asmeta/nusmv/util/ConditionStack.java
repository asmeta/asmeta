package org.asmeta.nusmv.util;

import java.util.List;
import java.util.Stack;

public class ConditionStack {

	/**
	 * Restore condition.
	 * 
	 * @param stack
	 *            the stack
	 */
	public static void restoreCondition(Stack<String> stack) {
		stack.pop();
	}

	/**
	 * Update condition.
	 * 
	 * @param stack
	 *            the stack
	 * @param cond
	 *            the cond
	 */
	public static void updateCondition(Stack<String> stack, String cond) {
		stack.push(cond);
	}

	/**
	 * Update condition.
	 * 
	 * @param stack
	 *            the stack
	 * @param conds
	 *            the conds
	 */
	public static void updateCondition(Stack<String> stack, List<String> conds) {
		for (String cond : conds) {
			updateCondition(stack, cond);
		}
	}

	/**
	 * Restore condition.
	 * 
	 * @param stack
	 *            the stack
	 * @param conds
	 *            the conds
	 */
	public static void restoreCondition(Stack<String> stack, List<String> conds) {
		for (String cond : conds) {
			restoreCondition(stack);
		}
	}

}
