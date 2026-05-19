package asmeta.asmeta_zeromq.parser;

import asmeta.asmeta_zeromq.ast.*;

public class CompositionParser {
	
//It checks that the formula is not empty and eliminates all spaces
    public ISimulationNode parse(String formula) {
        if (formula == null || formula.trim().isEmpty()) throw new IllegalArgumentException("Empty formula.");
        return parseRecursive(formula.replaceAll("\\s+", "")); 
    }

    private ISimulationNode parseRecursive(String expression) {
        while (expression.startsWith("(") && expression.endsWith(")") && isBalanced(expression.substring(1, expression.length() - 1))) {
            expression = expression.substring(1, expression.length() - 1);
        }
        
// Creates and returns a ModelNode
        if (!containsOperatorsOutsideParentheses(expression)) {
            return new ModelNode(expression);
        }

        int mainOpIndex = -1;
        String mainOp = "";
        int nesting = 0;

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (c == '(') nesting++;
            else if (c == ')') nesting--;
            else if (nesting == 0) {
                // Full-Duplex BiPipe <||>
                if (expression.startsWith("<||>", i)) {
                    mainOpIndex = i; mainOp = "<||>"; break;
                }
                //  Half-Duplex BiPipe <|>
                else if (expression.startsWith("<|>", i)) {
                    mainOpIndex = i; mainOp = "<|>"; break;
                }
                // Fork-Join
                else if (expression.startsWith("||", i) && (i == 0 || expression.charAt(i-1) != '<')) {
                    mainOpIndex = i; mainOp = "||"; break;
                }
                //Simplex Pipe 
                else if (c == '|' && 
                        (i == 0 || expression.charAt(i-1) != '<' && expression.charAt(i-1) != '|') && 
                        (i == expression.length()-1 || expression.charAt(i+1) != '|')) {
                    mainOpIndex = i; mainOp = "|"; break;
                }
            }
        }

        if (mainOpIndex != -1) {
            ISimulationNode left = parseRecursive(expression.substring(0, mainOpIndex));
            ISimulationNode right = parseRecursive(expression.substring(mainOpIndex + mainOp.length()));

            switch(mainOp) {
                case "|": return new PipeNode(left, right);
                case "||": return new ParallelNode(left, right);
                case "<|>": return new HalfBiPipeNode(left, right);
                case "<||>": return new FullBiPipeNode(left, right);
            }
        }
        throw new IllegalStateException("Errore di sintassi: " + expression);
    }

    //check that the parentheses in a string are balanced
    private boolean isBalanced(String str) {
        int count = 0;
        for (char c : str.toCharArray()) {
            if (c == '(') count++;
            if (c == ')') count--;
            if (count < 0) return false; // invalid syntax
        }
        return count == 0; //all open brackets were closed
    }

    private boolean containsOperatorsOutsideParentheses(String expr) {
        int nesting = 0;
        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);
            if (c == '(') nesting++;
            else if (c == ')') nesting--;
            else if (nesting == 0) {
                if (expr.startsWith("<||>", i) || expr.startsWith("<|>", i)) return true;
                if (expr.startsWith("||", i) && (i == 0 || expr.charAt(i-1) != '<')) return true;
                if (c == '|' && (i == 0 || expr.charAt(i-1) != '<' && expr.charAt(i-1) != '|') && (i == expr.length()-1 || expr.charAt(i+1) != '|')) return true;
            }
        }
        return false;
    }
}