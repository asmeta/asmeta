package org.asmeta.visualdesigner.export;

import org.asmeta.visualdesigner.model.RuleNode;

public class RuleSourceRange {

    private final String diagramName;
    private final RuleNode rule;
    private final int startLine;
    private final int endLine;

    public RuleSourceRange(String diagramName, RuleNode rule, int startLine, int endLine) {
        this.diagramName = diagramName;
        this.rule = rule;
        this.startLine = startLine;
        this.endLine = endLine;
    }

    public String getDiagramName() {
        return diagramName;
    }

    public RuleNode getRule() {
        return rule;
    }

    public int getStartLine() {
        return startLine;
    }

    public int getEndLine() {
        return endLine;
    }

    public boolean contains(int line) {
        return line >= startLine && line <= endLine;
    }

    public int getLength() {
        return endLine - startLine;
    }
}