package org.asmeta.visualdesigner.export;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.asmeta.visualdesigner.model.DiagramModel;
import org.asmeta.visualdesigner.model.DiagramNode;
import org.asmeta.visualdesigner.model.DomainSignature;
import org.asmeta.visualdesigner.model.FunctionSignature;
import org.asmeta.visualdesigner.model.RuleNode;
import org.asmeta.visualdesigner.model.RuleType;
import org.asmeta.visualdesigner.model.Transition;

public class AsmModelExporter {

    private static final String MAIN_DIAGRAM_NAME = "main";
    private static final String INDENT = "    ";

    private Map<String, DiagramModel> diagramsByName;

    public String export(String asmName, Map<String, DiagramModel> diagramsByName) throws AsmExportException {

        validateAsmName(asmName);
        validateDiagrams(diagramsByName);

        this.diagramsByName = diagramsByName;

        DiagramModel mainModel = diagramsByName.get(MAIN_DIAGRAM_NAME);

        StringBuilder asm = new StringBuilder();

        asm.append("asm ").append(asmName).append(System.lineSeparator()).append(System.lineSeparator());

        asm.append("import StandardLibrary").append(System.lineSeparator()).append(System.lineSeparator());

        appendSignature(asm, mainModel);
        appendDefinitions(asm, mainModel);
        appendCalledRules(asm);
        appendMainRule(asm, mainModel);

        return asm.toString();
    }

    private void validateAsmName(String asmName) throws AsmExportException {
        if (isBlank(asmName)) {
            throw new AsmExportException("The ASM name cannot be empty.");
        }

        if (!asmName.matches("[A-Za-z_][A-Za-z0-9_]*")) {
            throw new AsmExportException("Invalid ASM name: " + asmName);
        }
    }

    private void validateDiagrams(Map<String, DiagramModel> diagrams) throws AsmExportException {
        if (diagrams == null || !diagrams.containsKey(MAIN_DIAGRAM_NAME)) {
            throw new AsmExportException("The main diagram could not be found.");
        }
    }

    private void appendSignature(StringBuilder asm, DiagramModel mainModel) {
        asm.append("signature:").append(System.lineSeparator());
        appendDomains(asm, mainModel.getDomains());
        appendFunctions(asm, mainModel.getFunctions());
        asm.append(System.lineSeparator());
    }

    private void appendDomains(StringBuilder asm, List<DomainSignature> domains) {
        for (DomainSignature domain : domains) {
            if (!isBlank(domain.getName())) {
                appendDomain(asm, domain);
            }
        }
    }

    private void appendDomain(StringBuilder asm, DomainSignature domain) {
        String name = domain.getName().trim();
        String type = safeText(domain.getType());
        String values = safeText(domain.getValues());

        if (!values.isEmpty()) {
            asm.append(INDENT)
                    .append("enum domain ")
                    .append(name)
                    .append(" = {")
                    .append(values)
                    .append("}")
                    .append(System.lineSeparator());
        } else if ("abstract".equalsIgnoreCase(type)) {
            asm.append(INDENT)
                    .append("abstract domain ")
                    .append(name)
                    .append(System.lineSeparator());
        } else if (!type.isEmpty()) {
            if (domain.isDynamic()) {
                asm.append(INDENT)
                        .append("dynamic ");
            } else {
                asm.append(INDENT);
            }

            asm.append("domain ")
                    .append(name)
                    .append(" subsetof ")
                    .append(type)
                    .append(System.lineSeparator());
        }
    }

    private void appendFunctions(StringBuilder asm, List<FunctionSignature> functions) {
        for (FunctionSignature function : functions) {
            if (!isBlank(function.getName()) && !isBlank(function.getCodomain())) {
                appendFunction(asm, function);
            }
        }
    }

    private void appendFunction(StringBuilder asm, FunctionSignature function) {
        String type = safeText(function.getType());
        String domain = safeText(function.getDomain());
        String codomain = function.getCodomain().trim();

        asm.append(INDENT);

        if (!type.isEmpty()) {
            asm.append(type).append(" ");
        }

        asm.append(function.getName().trim()).append(": ");
        if (!domain.isEmpty()) {
            asm.append(domain).append(" -> ");
        }

        asm.append(codomain).append(System.lineSeparator());
    }

    private void appendDefinitions(StringBuilder asm, DiagramModel mainModel) {
        asm.append("definitions:").append(System.lineSeparator());

        boolean hasDefinitions = false;

        for (FunctionSignature function : mainModel.getFunctions()) {
            if (!isBlank(function.getDefinition())) {
                asm.append(INDENT).append("function ")
                        .append(function.getDefinition().trim())
                        .append(System.lineSeparator());
                hasDefinitions = true;
            }
        }

        if (hasDefinitions) {
            asm.append(System.lineSeparator());
        }
    }

    private void appendCalledRules(StringBuilder asm) throws AsmExportException {
        for (Map.Entry<String, DiagramModel> entry : diagramsByName.entrySet()) {
            String diagramName = entry.getKey();
            if (!MAIN_DIAGRAM_NAME.equals(diagramName)) {
                appendRuleDeclaration(asm, diagramName, entry.getValue());
            }
        }
    }

    private void appendRuleDeclaration(StringBuilder asm, String ruleName, DiagramModel model) throws AsmExportException {
        RuleNode firstRule = getFirstRule(ruleName, model);
        asm.append(INDENT).append("rule ")
                .append(normalizeRuleName(ruleName))
                .append(" =")
                .append(System.lineSeparator());
        appendRuleNode(asm, model, firstRule, 2, new HashSet<>());
        asm.append(System.lineSeparator());
    }

    private void appendMainRule(StringBuilder asm, DiagramModel mainModel) throws AsmExportException {

        RuleNode firstRule = getFirstRule(MAIN_DIAGRAM_NAME, mainModel);

        asm.append(INDENT).append("main rule r_Main =").append(System.lineSeparator());
        appendRuleNode(asm, mainModel, firstRule, 2, new HashSet<>());
    }

    private RuleNode getFirstRule(String diagramName, DiagramModel model) throws AsmExportException {

        List<Transition> transitions = model.getOutgoingTransitions(model.getStartNode());
        if (transitions.isEmpty()) {
            throw new AsmExportException("Diagram '" + diagramName + "' has no transition from its starting point.");
        }

        if (transitions.size() > 1) {
            throw new AsmExportException(
                    "Diagram '" + diagramName + "' has more than one transition " + "from its starting point");
        }

        DiagramNode target = transitions.get(0).getTarget();
        if (!(target instanceof RuleNode)) {
            throw new AsmExportException("The starting point of diagram '" + diagramName + "' does not point to a rule");
        }

        return (RuleNode) target;
    }

    private void appendRuleNode(StringBuilder asm, DiagramModel model, RuleNode rule, int indentation, Set<RuleNode> currentPath) throws AsmExportException {

        if (currentPath.contains(rule)) {
            throw new AsmExportException("A cycle was detected at rule '" + rule.getName() + "'. Cycles are not supported yet.");
        }

        currentPath.add(rule);

        switch (rule.getType()) {
            case CONDITIONAL:
                appendConditional(asm, model, rule, indentation, currentPath);
                break;

            case CALL:
                appendCall(asm, model, rule, indentation, currentPath);
                break;

            case CHOOSE:
                appendChoose(asm, model, rule, indentation, currentPath);
                break;
                
            case FORALL:
                appendForall(asm, model, rule, indentation, currentPath);
                break;

            case PAR:
                appendPar(asm, model, rule, indentation, currentPath);
                break;

            case UPDATE:
                appendUpdate(asm, model, rule, indentation, currentPath);
                break;

            default:
                throw new AsmExportException("Rule type '" + rule.getType() + "' is not supported yet. Rule: " + rule.getName());
        }

        currentPath.remove(rule);
    }
    
    private void appendForall(StringBuilder asm, DiagramModel model, RuleNode rule, int indentation, Set<RuleNode> currentPath) throws AsmExportException {

        if (isBlank(rule.getForall())) {
            throw new AsmExportException("Forall rule '" + rule.getName() + "' has no forall expression.");
        }

        Transition doTransition = findTransition(model, rule, "do");

        if (doTransition == null) {
            throw new AsmExportException("Forall rule '" + rule.getName() + "' has no do branch.");
        }

        appendIndentation(asm, indentation);

        asm.append("forall ").append(rule.getForall().trim()).append(" do").append(System.lineSeparator());

        appendTransitionTarget(asm, model, doTransition, indentation + 1, new HashSet<>(currentPath));
    }

    private void appendConditional(StringBuilder asm, DiagramModel model, RuleNode rule, int indentation, Set<RuleNode> currentPath) throws AsmExportException {

        if (isBlank(rule.getCondition())) {
            throw new AsmExportException("Conditional rule '" + rule.getName() + "' has no condition.");
        }

        Transition trueTransition = findTransition(model, rule, "true");

        Transition falseTransition = findTransition(model, rule, "false");

        if (trueTransition == null) {
            throw new AsmExportException("Conditional rule '" + rule.getName() + "' has no true branch.");
        }

        appendIndentation(asm, indentation);
        asm.append("if ").append(rule.getCondition().trim()).append(" then")
                .append(System.lineSeparator());

        appendTransitionTarget(asm, model, trueTransition, indentation + 1,new HashSet<>(currentPath));

        if (falseTransition != null) {
            appendIndentation(asm, indentation);
            asm.append("else").append(System.lineSeparator());

            appendTransitionTarget(asm, model, falseTransition, indentation + 1, new HashSet<>(currentPath));
        }

        appendIndentation(asm, indentation);
        asm.append("endif").append(System.lineSeparator());
    }

    private void appendCall(StringBuilder asm, DiagramModel model, RuleNode rule, int indentation, Set<RuleNode> currentPath) throws AsmExportException {
        String calledRuleName = safeText(rule.getCalledRuleName());

        if (calledRuleName.isEmpty()) {
            throw new AsmExportException("Call rule '" + rule.getName() + "' has no called rule name.");
        }

        if (!diagramsByName.containsKey(calledRuleName)) {
            throw new AsmExportException("The diagram for called rule '" + calledRuleName + "' could not be found");
        }

        appendIndentation(asm, indentation);

        asm.append(normalizeRuleName(calledRuleName)).append("[");

        if (!isBlank(rule.getParameters())) {
            asm.append(rule.getParameters().trim());
        }
        asm.append("]").append(System.lineSeparator());

        appendNextRule(asm, model, rule, indentation, currentPath);
    }

    private void appendChoose(StringBuilder asm, DiagramModel model, RuleNode rule, int indentation, Set<RuleNode> currentPath) throws AsmExportException {

        if (isBlank(rule.getChoose())) {
            throw new AsmExportException("Choose rule '" + rule.getName() + "' has no choose expression.");
        }

        Transition doTransition = findTransition(model, rule, "do");

        Transition ifnoneTransition = findTransition(model, rule, "ifnone");

        if (doTransition == null) {
            throw new AsmExportException("Choose rule '" + rule.getName() + "' has no do branch.");
        }

        appendIndentation(asm, indentation);

        asm.append("choose ").append(rule.getChoose().trim()).append(" do").append(System.lineSeparator());

        appendTransitionTarget(asm, model, doTransition, indentation + 1, new HashSet<>(currentPath));

        if (ifnoneTransition != null) {
            appendIndentation(asm, indentation);
            asm.append("ifnone").append(System.lineSeparator());

            appendTransitionTarget(asm, model, ifnoneTransition, indentation + 1, new HashSet<>(currentPath));
        }
    }

    private void appendPar(StringBuilder asm, DiagramModel model, RuleNode rule, int indentation, Set<RuleNode> currentPath) throws AsmExportException {

        List<Transition> transitions = model.getOutgoingTransitions(rule);

        if (transitions.isEmpty()) {
            throw new AsmExportException("Par rule '" + rule.getName() + "' has no branches.");
        }

        appendIndentation(asm, indentation);
        asm.append("par").append(System.lineSeparator());

        for (Transition transition : transitions) {
            appendTransitionTarget(asm, model, transition, indentation + 1, new HashSet<>(currentPath));
        }

        appendIndentation(asm, indentation);
        asm.append("endpar").append(System.lineSeparator());
    }

    private void appendUpdate(StringBuilder asm, DiagramModel model, RuleNode rule, int indentation, Set<RuleNode> currentPath) throws AsmExportException {

        if (isBlank(rule.getAssignment())) {
            throw new AsmExportException("Update rule '" + rule.getName()+ "' has no assignment.");
        }

        appendIndentation(asm, indentation);
        asm.append(rule.getAssignment().trim()).append(System.lineSeparator());
        appendNextRule(asm, model, rule, indentation, currentPath);
    }

    private void appendNextRule(StringBuilder asm, DiagramModel model, RuleNode rule, int indentation, Set<RuleNode> currentPath) throws AsmExportException {

        List<Transition> transitions = model.getOutgoingTransitions(rule);

        if (transitions.size() > 1) {
            throw new AsmExportException("Rule '" + rule.getName() + "' has more than one outgoing transition.");
        }

        if (transitions.size() == 1) {
            appendTransitionTarget(asm, model, transitions.get(0), indentation, new HashSet<>(currentPath));
        }
    }

    private void appendTransitionTarget(StringBuilder asm, DiagramModel model, Transition transition, int indentation, Set<RuleNode> currentPath) throws AsmExportException {

        DiagramNode target = transition.getTarget();
        if (!(target instanceof RuleNode)) {
            throw new AsmExportException("A transition does not point to a rule.");
        }

        appendRuleNode(asm, model, (RuleNode) target, indentation, currentPath);
    }

    private Transition findTransition(DiagramModel model, RuleNode source, String label) {

        Transition result = null;
        for (Transition transition : model.getOutgoingTransitions(source)) {
            if (label.equalsIgnoreCase(safeText(transition.getLabel()))) {
                result = transition;
            }
        }

        return result;
    }

    private String normalizeRuleName(String name) {
        String normalizedName = name.trim();

        if (!normalizedName.startsWith("r_")) {
            normalizedName = "r_" + normalizedName;
        }

        return normalizedName;
    }

    private void appendIndentation(StringBuilder asm, int indentation) {

        for (int i = 0; i < indentation; i++) {
            asm.append(INDENT);
        }
    }

    private String safeText(String value) {
        String result = "";

        if (value != null) {
            result = value.trim();
        }

        return result;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}