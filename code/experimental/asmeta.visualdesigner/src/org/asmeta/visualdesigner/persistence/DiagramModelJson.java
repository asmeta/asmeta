package org.asmeta.visualdesigner.persistence;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.asmeta.visualdesigner.model.DiagramModel;
import org.asmeta.visualdesigner.model.DiagramNode;
import org.asmeta.visualdesigner.model.RuleNode;
import org.asmeta.visualdesigner.model.RuleType;
import org.asmeta.visualdesigner.model.Transition;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import org.asmeta.visualdesigner.model.DomainSignature;
import org.asmeta.visualdesigner.model.FunctionSignature;

public class DiagramModelJson {

    private static final int FORMAT_VERSION = 2;
    private static final String MAIN_DIAGRAM_NAME = "main";
    private static final String START_NODE_ID = "start";

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void save(
            Map<String, DiagramModel> diagrams,
            Path path
    ) throws IOException {

        ProjectData projectData = new ProjectData();
        projectData.formatVersion = FORMAT_VERSION;

        for (Map.Entry<String, DiagramModel> entry : diagrams.entrySet()) {
            projectData.diagrams.put(entry.getKey(), createDiagramData(entry.getValue()));
        }

        try (Writer writer = Files.newBufferedWriter(path)) {
            gson.toJson(projectData, writer);
        }
    }

    public Map<String, DiagramModel> load(Path path) throws IOException {
        ProjectData projectData;

        try (Reader reader = Files.newBufferedReader(path)) {
            projectData = gson.fromJson(reader, ProjectData.class);
        } catch (JsonParseException exception) {
            throw new IOException(
                    "The selected file does not contain valid JSON.",
                    exception
            );
        }

        validateProject(projectData);

        Map<String, DiagramModel> diagrams = new LinkedHashMap<>();

        for (Map.Entry<String, DiagramData> entry  : projectData.diagrams.entrySet()) {

            validateDiagram(entry.getKey(), entry.getValue());

            diagrams.put(entry.getKey(),createDiagramModel(entry.getValue()));
        }

        return diagrams;
    }

    private DiagramData createDiagramData(DiagramModel model) {
        DiagramData diagramData = new DiagramData();

        DiagramNode startNode = model.getStartNode();

        diagramData.startNode = new StartNodeData();
        diagramData.startNode.x = startNode.getX();
        diagramData.startNode.y = startNode.getY();
        diagramData.startNode.width = startNode.getWidth();
        diagramData.startNode.height = startNode.getHeight();

        Map<DiagramNode, String> nodeIds = new IdentityHashMap<>();
        nodeIds.put(startNode, START_NODE_ID);

        for (int i = 0; i < model.getRules().size(); i++) {
            RuleNode rule = model.getRules().get(i);
            String ruleId = "rule-" + i;

            nodeIds.put(rule, ruleId);
            diagramData.rules.add(createRuleData(rule, ruleId));
        }

        for (Transition transition : model.getTransitions()) {
            TransitionData transitionData = new TransitionData();

            transitionData.source = nodeIds.get(transition.getSource());
            transitionData.target = nodeIds.get(transition.getTarget());
            transitionData.label = transition.getLabel();

            diagramData.transitions.add(transitionData);
        }

        for (DomainSignature domain : model.getDomains()) {
            DomainData domainData = new DomainData();

            domainData.name = domain.getName();
            domainData.type = domain.getType();
            domainData.dynamic = domain.isDynamic();
            domainData.values = domain.getValues();

            diagramData.domains.add(domainData);
        }

        for (FunctionSignature function : model.getFunctions()) {
            FunctionData functionData = new FunctionData();

            functionData.name = function.getName();
            functionData.type = function.getType();
            functionData.domain = function.getDomain();
            functionData.codomain = function.getCodomain();
            functionData.definition = function.getDefinition();

            diagramData.functions.add(functionData);
        }

        diagramData.customDomainTypes.addAll(
                model.getCustomDomainTypes()
        );
        
        return diagramData;
    }

    private RuleData createRuleData(RuleNode rule, String ruleId) {
        RuleData ruleData = new RuleData();

        ruleData.id = ruleId;
        ruleData.name = rule.getName();
        ruleData.type = rule.getType().name();
        ruleData.x = rule.getX();
        ruleData.y = rule.getY();
        ruleData.width = rule.getWidth();
        ruleData.height = rule.getHeight();
        ruleData.condition = rule.getCondition();
        ruleData.calledRuleName = rule.getCalledRuleName();
        ruleData.parameters = rule.getParameters();

        return ruleData;
    }

    private DiagramModel createDiagramModel(
            DiagramData diagramData
    ) throws IOException {

        DiagramModel model = new DiagramModel();
        
        for (DomainData domainData : diagramData.domains) {
            model.getDomains().add(new DomainSignature(
                            			safeText(domainData.name),
                            			safeText(domainData.type),
                            			domainData.dynamic,
                            			safeText(domainData.values)
                    				));
        }

        for (FunctionData functionData : diagramData.functions) {
            model.getFunctions().add(
                    new FunctionSignature(
                            safeText(functionData.name),
                            safeText(functionData.type),
                            safeText(functionData.domain),
                            safeText(functionData.codomain),
                            safeText(functionData.definition)
                    )
            );
        }

        for (String customType : diagramData.customDomainTypes) {
            if (customType != null && !customType.isBlank()) {
                model.getCustomDomainTypes().add(customType);
            }
        }

        model.getStartNode().setLayout(diagramData.startNode.x, diagramData.startNode.y, diagramData.startNode.width,diagramData.startNode.height);

        Map<String, DiagramNode> nodesById = new LinkedHashMap<>();
        nodesById.put(START_NODE_ID, model.getStartNode());

        for (RuleData ruleData : diagramData.rules) {
            RuleNode rule = new RuleNode(
                    safeText(ruleData.name),
                    ruleData.x,
                    ruleData.y,
                    ruleData.width,
                    ruleData.height,
                    parseRuleType(ruleData.type)
            );

            rule.setCondition(ruleData.condition);
            rule.setCalledRuleName(ruleData.calledRuleName);
            rule.setParameters(ruleData.parameters);

            model.addRule(rule);
            nodesById.put(ruleData.id, rule);
        }

        for (TransitionData transitionData : diagramData.transitions) {
            DiagramNode source = nodesById.get(transitionData.source);
            DiagramNode target = nodesById.get(transitionData.target);

            if (source == null || target == null) {
                throw new IOException(
                        "A transition references an unknown node."
                );
            }

            Transition transition = new Transition(source,target, transitionData.label);

            model.addTransitionAt(model.getTransitions().size(),transition);
        }

        return model;
    }

    private RuleType parseRuleType(String value) {
        RuleType type = RuleType.UNKNOWN;

        if (value != null) {
            try {
                type = RuleType.valueOf(value);
            } catch (IllegalArgumentException exception) {
                type = RuleType.UNKNOWN;
            }
        }

        return type;
    }

    private void validateProject(ProjectData projectData)
            throws IOException {

        if (projectData == null) {
            throw new IOException("The selected file is empty.");
        }

        if (projectData.formatVersion != FORMAT_VERSION) {
            throw new IOException(
                    "Unsupported format version: "
                    + projectData.formatVersion
            );
        }

        if (projectData.diagrams == null || !projectData.diagrams.containsKey(MAIN_DIAGRAM_NAME)) {

            throw new IOException(
                    "The file does not contain the main diagram."
            );
        }
    }

    private void validateDiagram(
            String name,
            DiagramData diagramData
    ) throws IOException {

        if (diagramData == null) {
            throw new IOException(
                    "Diagram '" + name + "' does not contain data."
            );
        }

        if (diagramData.startNode == null) {
            throw new IOException(
                    "Diagram '" + name
                    + "' does not contain a starting point."
            );
        }

        if (diagramData.rules == null) {
            diagramData.rules = new ArrayList<>();
        }

        if (diagramData.transitions == null) {
            diagramData.transitions = new ArrayList<>();
        }
        
        if (diagramData.domains == null) {
            diagramData.domains = new ArrayList<>();
        }

        if (diagramData.functions == null) {
            diagramData.functions = new ArrayList<>();
        }

        if (diagramData.customDomainTypes == null) {
            diagramData.customDomainTypes = new ArrayList<>();
        }
    }

    private String safeText(String value) {
        return value != null ? value : "";
    }

    private static class ProjectData {
        private int formatVersion;
        private Map<String, DiagramData> diagrams =
                new LinkedHashMap<>();
    }

    private static class DiagramData {
        private StartNodeData startNode;
        private List<RuleData> rules = new ArrayList<>();
        private List<TransitionData> transitions = new ArrayList<>();
        private List<DomainData> domains = new ArrayList<>();
        private List<FunctionData> functions = new ArrayList<>();
        private List<String> customDomainTypes = new ArrayList<>();
    }
    
    private static class DomainData {
        private String name;
        private String type;
        private boolean dynamic;
        private String values;
    }

    private static class FunctionData {
        private String name;
        private String type;
        private String domain;
        private String codomain;
        private String definition;
    }

    private static class StartNodeData {
        private int x;
        private int y;
        private int width;
        private int height;
    }

    private static class RuleData {
        private String id;
        private String name;
        private String type;
        private int x;
        private int y;
        private int width;
        private int height;
        private String condition;
        private String calledRuleName;
        private String parameters;
    }

    private static class TransitionData {
        private String source;
        private String target;
        private String label;
    }
}