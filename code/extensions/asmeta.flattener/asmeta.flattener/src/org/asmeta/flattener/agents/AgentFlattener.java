package org.asmeta.flattener.agents;

import java.util.*;
import java.util.function.Consumer;

import org.asmeta.flattener.rule.AsmetaFlattener;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.*;

import asmeta.definitions.*;
import asmeta.definitions.domains.*;
import asmeta.structure.*;
import asmeta.terms.basicterms.*;
import asmeta.transitionrules.basictransitionrules.*;

/**
 * NP 28/10/2026
 * Generated with Codex for the EvoAvalla ICST 2027 experiments.
 * Tested on a few simple cases, but not thoroughly reviewed.
 * Use with care outside the experiments.
 * 
 * Lowers fixed agent programs to ordinary macro calls.
 * Direct, non-dynamic Agent subdomains become abstract domains with the same name.
 * Generic Agent values and agent creation and unsupported.
 * A global forall over Agent is supported when it only runs programs.
 */
public final class AgentFlattener implements AsmetaFlattener {
    private final Asm asm;
    private boolean flattened;
    private final Map<Domain, AbstractTd> domains = new LinkedHashMap<>();
    private final Map<Domain, MacroCallRule> programs = new LinkedHashMap<>();
    private final Set<EObject> contextual = identitySet();
    private final Map<EObject, Domain> contexts = new IdentityHashMap<>();
    private final Map<EObject, VariableTerm> selves = new IdentityHashMap<>();

    public AgentFlattener(Asm asm) { this.asm = asm; }
    @Override public String getCode() { return "AG"; }

    // Choice recording must still refer to the source signature, without self.
    private static final class SourceRuleInfo extends AdapterImpl {
        final String signature;
        SourceRuleInfo(String signature) { this.signature = signature; }
    }
    private static final class SourceDomainInfo extends AdapterImpl {
        final String name;
        SourceDomainInfo(String name) { this.name = name; }
    }
    public static String originalRuleSignature(RuleDeclaration rule) {
        return rule.eAdapters().stream().filter(a -> a instanceof SourceRuleInfo)
                .map(a -> ((SourceRuleInfo) a).signature).findFirst().orElse(null);
    }
    public static String originalDomainName(VariableTerm variable) {
        return variable.eAdapters().stream().filter(a -> a instanceof SourceDomainInfo)
                .map(a -> ((SourceDomainInfo) a).name).findFirst().orElse(variable.getDomain().getName());
    }

    @Override public Asm flattenASM() {
    	// Skip already flattened ASMs
        if (flattened) return asm;
        // Collect all direct Agent subdomains
        List<EObject> nodes = nodes(asm);
        for (EObject node : nodes) forEachValue(node, value -> {
            if (value instanceof ConcreteDomain && isAgent((Domain) value)) {
                ConcreteDomain domain = (ConcreteDomain) value;
                if (!(domain.getTypeDomain() instanceof AgentDomain) || Boolean.TRUE.equals(domain.getIsDynamic()))
                    throw unsupported("dynamic or nested agent domain " + domain.getName());
                domains.computeIfAbsent(domain, d -> abstractDomain(d.getName()));
            }
        });
        // Skip flattening if there are no direct Agent subdomains
        boolean hasAgent = nodes.stream().anyMatch(n -> {
            final boolean[] found = {false};
            forEachValue(n, v -> { if (v instanceof AgentDomain) found[0] = true; });
            return found[0];
        });
        if (domains.isEmpty() && !hasAgent) return asm;
        if (asm.getInitialState().size() != 1)
            throw unsupported("agent models require a single initial state");
        Initialization init = asm.getInitialState().get(0);
        for (AgentInitialization assignment : init.getAgentInitialization()) {
            if (!assignment.getProgram().getParameters().isEmpty())
                throw unsupported("parameterised agent initialisations");
            programs.put(assignment.getDomain(), assignment.getProgram());
        }
        // With no subdomains, Agent itself can be one ordinary abstract domain.
        if (domains.isEmpty()) {
            for (Domain domain : programs.keySet())
                if (domain instanceof AgentDomain) domains.put(domain, abstractDomain("AgentIdentity"));
        }
        Set<EObject> globalRanges = identitySet();
        Set<EObject> globalVariables = identitySet();
        for (EObject node : nodes) {
            if (node instanceof ExtendRule && isAgent(((ExtendRule) node).getExtendedDomain()))
                throw unsupported("creation of agents with extend");
            if (node instanceof RuleAsTerm)
                throw unsupported("program as rule value");
            if (node instanceof DomainDefinition && domains.containsKey(((DomainDefinition) node).getDefinedDomain())
                    || node instanceof DomainInitialization && domains.containsKey(((DomainInitialization) node).getInitializedDomain()))
                throw unsupported("explicit agent domain definitions or initialisations");
            if (node instanceof ForallRule && globalForall((ForallRule) node)) {
                ForallRule rule = (ForallRule) node;
                globalRanges.add(rule.getRanges().get(0).getDomain());
                globalVariables.add(rule.getVariable().get(0));
            }
        }
        // Validate declared types, not the parser's inferred tuple types for self.
        for (EObject node : nodes) {
            if (node instanceof Function) {
                Function function = (Function) node;
                if (generic(function.getDomain()) || generic(function.getCodomain()))
                    throw unsupported("generic Agent values in " + function.getName());
            }
            if (node instanceof VariableTerm && generic(((VariableTerm) node).getDomain()) && !globalVariables.contains(node))
                throw unsupported("generic Agent variables outside a simple forall/program");
            if (node instanceof DomainTerm && generic(((DomainTerm) node).getDomain())
                    && !globalRanges.contains(((DomainTerm) node).getDomain()))
                throw unsupported("generic Agent ranges outside a simple forall/program");
        }
        for (EObject node : nodes) {
            if (node instanceof FunctionTerm && builtin(((FunctionTerm) node).getFunction(), "program")) {
                if (nodes.stream().noneMatch(n -> n instanceof TermAsRule && ((TermAsRule) n).getTerm() == node))
                    throw unsupported("programs stored or passed as values");
            }
            if (node instanceof FunctionTerm && (builtin(((FunctionTerm) node).getFunction(), "id")
                    || builtin(((FunctionTerm) node).getFunction(), "getAgent")))
                throw unsupported("agent identity lookup");
        }
        List<RuleDeclaration> rules = new ArrayList<>(asm.getBodySection().getRuleDeclaration());
        List<Function> functions = new ArrayList<>(asm.getHeaderSection().getSignature().getFunction());
        boolean changed;
        do {
            int size = contextual.size();
            for (RuleDeclaration rule : rules)
                if (nodes(rule.getRuleBody()).stream().anyMatch(this::needsSelf)) contextual.add(rule);
            for (Function function : functions)
                if (function.getDefinition() != null && nodes(function.getDefinition().getBody()).stream().anyMatch(this::needsSelf))
                    contextual.add(function);
            changed = size != contextual.size();
        } while (changed);
        for (FunctionInitialization value : init.getFunctionInitialization())
            if (nodes(value).stream().anyMatch(this::needsSelf)) throw unsupported("self in an initialisation");
        for (Property property : asm.getBodySection().getProperty())
            if (nodes(property).stream().anyMatch(this::needsSelf)) throw unsupported("self in a property");
        for (var entry : programs.entrySet()) assignContext(entry.getValue().getCalledMacro(), entry.getKey());
        if (contextual.contains(asm.getMainrule())) throw unsupported("self in the main rule");
        for (EObject declaration : contextual) {
            Domain domain = contexts.get(declaration);
            if (domain == null) throw unsupported("self without a statically known agent domain");
            Set<String> names = new HashSet<>();
            walk(declaration, n -> { if (n instanceof VariableTerm) names.add(((VariableTerm) n).getName()); });
            String name = "$agentSelf";
            while (names.contains(name)) name += "_";
            VariableTerm self = BasictermsFactory.eINSTANCE.createVariableTerm();
            self.setName(name); self.setDomain(domain); self.setKind(VariableKind.LOGICAL_VAR);
            selves.put(declaration, self);
        }
        for (RuleDeclaration rule : rules) {
            String signature = rule.getName() + (rule.getVariable().isEmpty() ? "" : "(" + String.join(",",
                    rule.getVariable().stream().map(v -> v.getDomain().getName()).toList()) + ")");
            rule.eAdapters().add(new SourceRuleInfo(signature));
            VariableTerm self = selves.get(rule);
            if (self != null) { rule.getVariable().add(self); rule.setArity(rule.getVariable().size()); }
            Rewriter rewriter = new Rewriter(self);
            for (VariableTerm variable : rule.getVariable()) rewriter.copies.put(variable, variable);
            rule.setRuleBody((Rule) rewriter.copy(rule.getRuleBody()));
        }
        for (Function function : functions) if (function.getDefinition() != null) {
            VariableTerm self = selves.get(function);
            if (self != null) {
                function.getDefinition().getVariable().add(self);
                List<VariableTerm> parameters = function.getDefinition().getVariable();
                if (parameters.size() == 1) function.setDomain(self.getDomain());
                else {
                    ProductDomain product = DomainsFactory.eINSTANCE.createProductDomain();
                    for (VariableTerm parameter : parameters) product.getDomains().add(parameter.getDomain());
                    product.setName("Prod(" + String.join(",", product.getDomains().stream().map(Domain::getName).toList()) + ")");
                    function.setDomain(product);
                }
                function.setArity(parameters.size());
            }
            Rewriter rewriter = new Rewriter(self);
            for (VariableTerm variable : function.getDefinition().getVariable()) rewriter.copies.put(variable, variable);
            function.getDefinition().setBody((Term) rewriter.copy(function.getDefinition().getBody()));
        }
        init.getAgentInitialization().clear();
        for (EObject node : nodes(asm)) remap(node);
        for (AbstractTd domain : domains.values())
            if (!asm.getHeaderSection().getSignature().getDomain().contains(domain))
                asm.getHeaderSection().getSignature().getDomain().add(domain);
        flattened = true;
        return asm;
    }

    private void assignContext(EObject declaration, Domain domain) {
        if (!contextual.contains(declaration)) return;
        Domain previous = contexts.putIfAbsent(declaration, domain);
        if (previous != null) {
            if (previous != domain) throw unsupported("self-dependent helper shared by different agent domains");
            return;
        }
        EObject body = declaration instanceof RuleDeclaration ? ((RuleDeclaration) declaration).getRuleBody()
                : ((Function) declaration).getDefinition().getBody();
        walk(body, node -> {
            if (node instanceof MacroCallRule) assignContext(((MacroCallRule) node).getCalledMacro(), domain);
            if (node instanceof FunctionTerm) assignContext(((FunctionTerm) node).getFunction(), domain);
        });
    }
    private boolean needsSelf(EObject node) {
        return isSelf(node) || node instanceof MacroCallRule && contextual.contains(((MacroCallRule) node).getCalledMacro())
                || node instanceof FunctionTerm && contextual.contains(((FunctionTerm) node).getFunction());
    }
    private boolean globalForall(ForallRule rule) {
        if (rule.getVariable().size() != 1 || !(rule.getVariable().get(0).getDomain() instanceof AgentDomain)
                || domains.containsKey(rule.getVariable().get(0).getDomain()) || rule.getRanges().size() != 1
                || !(rule.getRanges().get(0) instanceof DomainTerm) || !(rule.getGuard() instanceof BooleanTerm)
                || !"true".equals(((BooleanTerm) rule.getGuard()).getSymbol()) || !(rule.getDoRule() instanceof TermAsRule)) return false;
        Term term = ((TermAsRule) rule.getDoRule()).getTerm();
        return term instanceof FunctionTerm && builtin(((FunctionTerm) term).getFunction(), "program")
                && ((FunctionTerm) term).getArguments().getTerms().get(0) == rule.getVariable().get(0);
    }
    private final class Rewriter {
        final Map<EObject, EObject> copies = new IdentityHashMap<>();
        final VariableTerm self;
        Rewriter(VariableTerm self) { this.self = self; }
        EObject copy(EObject source) {
            if (source == null) return null;
            if (copies.containsKey(source)) return copies.get(source);
            if (isSelf(source)) { requireSelf(); return self; }
            if (!(source instanceof Term || source instanceof Rule)) return source;
            if (source instanceof ForallRule && globalForall((ForallRule) source)) {
                ForallRule original = (ForallRule) source;
                BlockRule parallel = BasictransitionrulesFactory.eINSTANCE.createBlockRule();
                for (Domain domain : domains.keySet()) {
                    Rewriter branch = new Rewriter(self);
                    VariableTerm variable = (VariableTerm) branch.copy(original.getVariable().get(0));
                    variable.setDomain(domain);
                    Set<String> used = new HashSet<>();
                    walk(asm, n -> { if (n instanceof VariableTerm) used.add(((VariableTerm) n).getName()); });
                    String name = variable.getName() + "__" + domain.getName();
                    while (used.contains(name)) name += "_";
                    variable.setName(name);
                    ForallRule loop = BasictransitionrulesFactory.eINSTANCE.createForallRule();
                    loop.getVariable().add(variable);
                    PowersetDomain set = DomainsFactory.eINSTANCE.createPowersetDomain();
                    set.setBaseDomain(domain); set.setName("Powerset(" + domain.getName() + ")");
                    DomainTerm range = BasictermsFactory.eINSTANCE.createDomainTerm(); range.setDomain(set);
                    loop.getRanges().add(range); loop.setGuard((Term) branch.copy(original.getGuard()));
                    loop.setDoRule((Rule) branch.copy(original.getDoRule())); parallel.getRules().add(loop);
                }
                return parallel;
            }
            if (source instanceof TermAsRule) {
                Term term = ((TermAsRule) source).getTerm();
                if (!(term instanceof FunctionTerm) || !builtin(((FunctionTerm) term).getFunction(), "program"))
                    throw unsupported("first-class rule execution");
                Term agent = (Term) copy(((FunctionTerm) term).getArguments().getTerms().get(0));
                MacroCallRule program = programs.get(agent.getDomain());
                if (program == null) throw unsupported("no fixed program for domain " + agent.getDomain().getName());
                MacroCallRule call = BasictransitionrulesFactory.eINSTANCE.createMacroCallRule();
                call.setCalledMacro(program.getCalledMacro());
                if (contextual.contains(call.getCalledMacro())) call.getParameters().add(agent);
                return call;
            }
            EObject result = source.eClass().getEPackage().getEFactoryInstance().create(source.eClass());
            copies.put(source, result);
            if (source instanceof VariableTerm) result.eAdapters().add(new SourceDomainInfo(((VariableTerm) source).getDomain().getName()));
            for (EStructuralFeature feature : source.eClass().getEAllStructuralFeatures()) {
                if (!writable(feature) || !source.eIsSet(feature)) continue;
                Object value = source.eGet(feature);
                if (feature.isMany()) {
                    List<Object> values = new ArrayList<>();
                    for (Object item : (List<?>) value) values.add(item instanceof EObject ? copy((EObject) item) : item);
                    result.eSet(feature, values);
                } else result.eSet(feature, value instanceof EObject ? copy((EObject) value) : value);
            }
            if (result instanceof TupleTerm) {
                TupleTerm tuple = (TupleTerm) result;
                if (tuple.getTerms().size() == 1) tuple.setDomain(tuple.getTerms().get(0).getDomain());
                else if (!tuple.getTerms().isEmpty()) {
                    ProductDomain product = DomainsFactory.eINSTANCE.createProductDomain();
                    for (Term term : tuple.getTerms()) product.getDomains().add(term.getDomain());
                    product.setName("Prod(" + String.join(",", product.getDomains().stream().map(Domain::getName).toList()) + ")");
                    tuple.setDomain(product);
                }
            }
            if (source instanceof MacroCallRule && contextual.contains(((MacroCallRule) source).getCalledMacro())) {
                requireSelf(); ((MacroCallRule) result).getParameters().add(self);
            }
            if (source instanceof FunctionTerm && contextual.contains(((FunctionTerm) source).getFunction())) {
                requireSelf();
                FunctionTerm call = (FunctionTerm) result;
                TupleTerm arguments = call.getArguments();
                if (arguments == null) { arguments = BasictermsFactory.eINSTANCE.createTupleTerm(); call.setArguments(arguments); }
                arguments.getTerms().add(self); arguments.setArity(arguments.getTerms().size());
            }
            return result;
        }
        private void requireSelf() { if (self == null) throw unsupported("self-dependent expression outside its agent program"); }
    }
    private void remap(EObject node) {
        for (EStructuralFeature feature : node.eClass().getEAllStructuralFeatures()) {
            if (!writable(feature) || !node.eIsSet(feature)) continue;
            Object value = node.eGet(feature);
            if (feature.isMany()) {
                @SuppressWarnings("unchecked") List<Object> values = (List<Object>) value;
                for (int i = 0; i < values.size(); i++) if (domains.containsKey(values.get(i))) values.set(i, domains.get(values.get(i)));
            } else if (domains.containsKey(value)) node.eSet(feature, domains.get(value));
        }
    }
    private boolean generic(Domain domain) {
        if (domain == null || domains.containsKey(domain)) return false;
        if (domain instanceof AgentDomain) return true;
        if (!(domain instanceof StructuredTd)) return false;
        final boolean[] found = {false};
        forEachValue(domain, value -> { if (value instanceof Domain && generic((Domain) value)) found[0] = true; });
        return found[0];
    }
    private static AbstractTd abstractDomain(String name) {
        AbstractTd domain = DomainsFactory.eINSTANCE.createAbstractTd(); domain.setName(name); domain.setIsDynamic(false); return domain;
    }
    private static boolean isAgent(Domain domain) {
        return domain instanceof AgentDomain || domain instanceof ConcreteDomain && isAgent(((ConcreteDomain) domain).getTypeDomain());
    }
    private static UnsupportedOperationException unsupported(String detail) {
        return new UnsupportedOperationException("Simple agent flattening does not support " + detail);
    }
    private static List<EObject> nodes(EObject root) {
        List<EObject> nodes = new ArrayList<>(); walk(root, nodes::add); return nodes;
    }
    private static boolean builtin(Function function, String name) {
        return function.getName().equals(name) && function.getSignature() != null
                && function.getSignature().getHeaderSection().getAsm().getName().equals("StandardLibrary");
    }
    private static boolean isSelf(EObject node) {
        return node instanceof FunctionTerm && builtin(((FunctionTerm) node).getFunction(), "self");
    }
    private static boolean writable(EStructuralFeature feature) {
        if (!feature.isChangeable() || feature.isDerived()) return false;
        if (feature instanceof EReference) {
            EReference ref = (EReference) feature;
            if (ref.isContainer() || !ref.isContainment() && ref.getEOpposite() != null && !ref.getName().equals("term")) return false;
        }
        return true;
    }
    private static void forEachValue(EObject node, Consumer<Object> action) {
        for (EStructuralFeature feature : node.eClass().getEAllStructuralFeatures()) {
            if (!writable(feature) || !node.eIsSet(feature)) continue;
            Object value = node.eGet(feature);
            if (feature.isMany()) new ArrayList<>((List<?>) value).forEach(action); else action.accept(value);
        }
    }
    private static void walk(EObject root, Consumer<EObject> action) { walk(root, action, identitySet()); }
    private static void walk(EObject node, Consumer<EObject> action, Set<EObject> seen) {
        if (node == null || !seen.add(node)) return;
        action.accept(node);
        forEachValue(node, value -> {
            if (value instanceof EObject) {
                EObject child = (EObject) value;
                if (child.eContainer() == node || child instanceof Term || child instanceof Rule || child instanceof StructuredTd)
                    walk(child, action, seen);
            }
        });
    }
    private static <T> Set<T> identitySet() { return Collections.newSetFromMap(new IdentityHashMap<T, Boolean>()); }
}

