package asmeta.asmeta_zeromq.parser;

import asmeta.asmeta_zeromq.ast.*;

public class CompositionParser {

    // It checks that the formula is not empty and eliminates all spaces
    public ISimulationNode parse(String formula) {
        if (formula == null || formula.trim().isEmpty()) throw new IllegalArgumentException("Empty formula.");
        return parseRecursive(formula.replaceAll("\\s+", ""));
    }

    private ISimulationNode parseRecursive(String expression) {

        while (expression.startsWith("(") && expression.endsWith(")") && isBalanced(expression.substring(1, expression.length() - 1))) {
            expression = expression.substring(1, expression.length() - 1);
        }

        // base case
        if (!containsOperatorsOutsideParentheses(expression)) {
            return new ModelNode(expression);
        }

        // find main operator 
        int mainOpIndex = -1;
        String mainOp = "";
        int nesting = 0;

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (c == '(') nesting++;
            else if (c == ')') nesting--;
            else if (nesting == 0) {
                String op = operatorAt(expression, i);
                if (op != null) {
                    mainOpIndex = i;
                    mainOp = op;
                    break;
                }
            }
        }

        if (mainOpIndex != -1) {
            ISimulationNode left = parseRecursive(expression.substring(0, mainOpIndex));
            ISimulationNode right = parseRecursive(expression.substring(mainOpIndex + mainOp.length()));

            switch (mainOp) {
                case "|":    return new PipeNode(left, right);
                case "||":   return new ParallelNode(left, right);
                case "<|>":  return new HalfBiPipeNode(left, right);
                case "<||>": return new FullBiPipeNode(left, right);
            }
        }
        throw new IllegalStateException("Syntax error: " + expression);
    }

/*
*Single point of recognition for composition operators. If an operator begins at position i in the expression, it returns
*the string of that operator; otherwise, it returns null.
*The check proceeds from the longest to the shortest because the operators are prefixes of one another ( | is inside ||, || is inside <||>, and <|> contains | ).
*/
    private String operatorAt(String expr, int i) {
        // Full-Duplex BiPipe <||>
        if (expr.startsWith("<||>", i)) return "<||>";
        // Half-Duplex BiPipe <|>
        if (expr.startsWith("<|>", i)) return "<|>";
        // Fork-Join ||
        if (expr.startsWith("||", i) && (i == 0 || expr.charAt(i - 1) != '<')) return "||";
        // Simplex Pipe | 
        if (expr.charAt(i) == '|'
                && (i == 0 || (expr.charAt(i - 1) != '<' && expr.charAt(i - 1) != '|'))
                && (i == expr.length() - 1 || expr.charAt(i + 1) != '|')) {
            return "|";
        }
        return null;
    }

    // check that the parentheses in a string are balanced
    private boolean isBalanced(String str) {
        int count = 0;
        for (char c : str.toCharArray()) {
            if (c == '(') count++;
            if (c == ')') count--;
            if (count < 0) return false; // invalid syntax
        }
        return count == 0; // all open brackets were closed
    }

    private boolean containsOperatorsOutsideParentheses(String expr) {
        int nesting = 0;
        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);
            if (c == '(') nesting++;
            else if (c == ')') nesting--;
            else if (nesting == 0 && operatorAt(expr, i) != null) {
                return true;
            }
        }
        return false;
    }
}