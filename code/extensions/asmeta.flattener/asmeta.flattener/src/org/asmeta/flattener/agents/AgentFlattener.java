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
import asmeta.terms.furtherterms.*;
import asmeta.transitionrules.basictransitionrules.*;
import asmeta.transitionrules.turbotransitionrules.*;

/**
 * Erases Agent to an ordinary abstract identity domain. Subdomain membership and
 * programs become ordinary controlled functions, preserving generic values,
 * domain ranges and dynamic creation. self becomes an explicit rule/function
 * parameter. Modifies the supplied module-flattened model in place.
 */
public final class AgentFlattener implements AsmetaFlattener {
    private final Asm asm;
    private final Set<Domain> family = new LinkedHashSet<>();
    private final Set<Domain> programClosures = identitySet();
    private final Map<Domain, Domain> parents = new IdentityHashMap<>();
    private final Map<Term, Domain> types = new IdentityHashMap<>();
    private final Map<EObject, Domain> ranges = new IdentityHashMap<>();
    private final Map<EObject, Domain> extensions = new IdentityHashMap<>();
    private final Map<Domain, ConcreteDomain> reserves = new IdentityHashMap<>();
    private final Map<Domain, ControlledFunction> members = new LinkedHashMap<>();
    private final Map<Domain, DerivedFunction> domainRanges = new IdentityHashMap<>();
    private final Map<Domain, ControlledFunction> selected = new LinkedHashMap<>();
    private final Map<StaticFunction, Domain> constants = new LinkedHashMap<>();
    private final Set<RuleDeclaration> contextualRules = identitySet();
    private final Set<Function> contextualFunctions = identitySet();
    private final Map<EObject, VariableTerm> selves = new IdentityHashMap<>();
    private final List<Function> library = new ArrayList<>();
    private final List<AgentInitialization> assignments = new ArrayList<>();
    private final Map<AgentInitialization, Integer> programIds = new IdentityHashMap<>();
    private final Set<String> names = new HashSet<>();
    private AbstractTd identities;
    private ControlledFunction program;
    private MacroDeclaration dispatch;
    private Domain booleans, integers;
    private boolean flattened;

    public AgentFlattener(Asm asm) { this.asm = asm; }
    @Override public String getCode() { return "AG"; }

    /** Keeps synthetic state out of the test interface and preserves argument domains. */
    private static final class InterfaceInfo extends AdapterImpl {
        Set<String> arguments;
        boolean internal;
    }
    private static final class ReserveInfo extends AdapterImpl {
        final AbstractTd identities;
        ReserveInfo(AbstractTd identities) { this.identities = identities; }
    }
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
    public static boolean isReserveDomain(Domain domain) {
        return domain.eAdapters().stream().anyMatch(a -> a instanceof ReserveInfo);
    }
    public static String reserveJavaType(Domain domain) {
        return domain.eAdapters().stream().filter(a -> a instanceof ReserveInfo)
                .map(a -> ((ReserveInfo) a).identities.getName()).findFirst().orElse(domain.getName());
    }
    public static boolean isInternal(Function function) {
        for (var adapter : function.eAdapters())
            if (adapter instanceof InterfaceInfo) return ((InterfaceInfo) adapter).internal;
        return false;
    }
    public static boolean acceptsArgument(Function function, Function constant) {
        for (var adapter : function.eAdapters()) {
            if (adapter instanceof InterfaceInfo) {
                Set<String> accepted = ((InterfaceInfo) adapter).arguments;
                return accepted == null || accepted.contains(constant.getName());
            }
        }
        return true;
    }

    @Override public Asm flattenASM() {
        if (flattened) return asm;
        List<EObject> nodes = new ArrayList<>();
        walk(asm, nodes::add);
        for (EObject node : nodes) {
            if (node instanceof Term) types.put((Term) node, ((Term) node).getDomain());
            forEachValue(node, value -> { if (value instanceof Domain) discover((Domain) value); });
        }
        if (family.isEmpty()) return asm;
        // A nullary Rule value constructed solely with program(a) is a captured
        // agent identity. General Rule literals remain the responsibility of the
        // backend's higher-order rule support.
        if (nodes.stream().noneMatch(n -> n instanceof RuleAsTerm)) {
            boolean hasProgram = nodes.stream().anyMatch(n -> n instanceof FunctionTerm
                    && builtin(((FunctionTerm) n).getFunction(), "program"));
            if (hasProgram) for (EObject node : nodes) forEachValue(node, value -> {
                if (value instanceof RuleDomain && ((RuleDomain) value).getDomains().isEmpty()) programClosures.add((Domain) value);
            });
        }
        for (ImportClause clause : asm.getHeaderSection().getImportClause()) library.addAll(clause.getImportedFunction());
        booleans = library.stream().filter(f -> f.getName().equals("isDef")).findFirst().orElseThrow().getCodomain();
        integers = library.stream().filter(f -> f.getName().equals("plus") && f.getCodomain() instanceof IntegerDomain)
                .findFirst().orElseThrow().getCodomain();
        var signature = asm.getHeaderSection().getSignature();
        for (Domain domain : signature.getDomain()) names.add(domain.getName());
        for (Function function : signature.getFunction()) names.add(function.getName());
        for (RuleDeclaration rule : asm.getBodySection().getRuleDeclaration()) names.add(rule.getName());
        identities = DomainsFactory.eINSTANCE.createAbstractTd();
        identities.setName(fresh("AgentIdentity"));
        identities.setIsDynamic(true);
        List<Function> functions = new ArrayList<>(signature.getFunction());
        List<RuleDeclaration> rules = new ArrayList<>(asm.getBodySection().getRuleDeclaration());
        for (RuleDeclaration rule : rules) {
            String parameters = String.join(",", rule.getVariable().stream().map(v -> v.getDomain().getName()).toList());
            rule.eAdapters().add(new SourceRuleInfo(rule.getName() + (parameters.isEmpty() ? "" : "(" + parameters + ")")));
        }
        for (EObject node : nodes) {
            if (node instanceof VariableTerm && ((VariableTerm) node).getDomain() != null)
                node.eAdapters().add(new SourceDomainInfo(((VariableTerm) node).getDomain().getName()));
        }
        for (Function function : functions) {
            if (function instanceof StaticFunction && function.getDomain() == null && function.getDefinition() == null
                    && family.contains(function.getCodomain()))
                constants.put((StaticFunction) function, function.getCodomain());
        }
        for (Function function : functions) {
            if (programClosures.contains(function.getCodomain())) {
                // Program closures have no ordinary abstract-value representation
                // in Avalla's public test interface. Their effects remain observable.
                InterfaceInfo info = new InterfaceInfo(); info.internal = true;
                function.eAdapters().add(info);
            }
            if (family.contains(function.getDomain())) {
                InterfaceInfo info = new InterfaceInfo();
                info.arguments = new LinkedHashSet<>();
                constants.forEach((constant, type) -> {
                    if (isSubset(type, function.getDomain())) info.arguments.add(constant.getName());
                });
                function.eAdapters().add(info);
            }
        }
        for (Domain domain : family) {
            if (!(domain instanceof AgentDomain)) members.put(domain, controlled("member_" + domain.getName(), identities, booleans));
            selected.put(domain, controlled("selected_" + domain.getName(), null, integers));
        }
        for (var entry : members.entrySet()) {
            DerivedFunction range = DefinitionsFactory.eINSTANCE.createDerivedFunction();
            range.setName(fresh("agent__range_" + entry.getKey().getName()));
            range.setCodomain(powerset()); range.setArity(0);
            FunctionDefinition definition = StructureFactory.eINSTANCE.createFunctionDefinition();
            definition.setDefinedFunction(range);
            VariableTerm value = variable("$agentRange", identities);
            definition.setBody(setComprehension(value, domainRange(), term(entry.getValue(), value)));
            signature.getFunction().add(range);
            asm.getBodySection().getFunctionDefinition().add(definition);
            domainRanges.put(entry.getKey(), range);
        }
        program = controlled("program", identities, integers);
        dispatch = BasictransitionrulesFactory.eINSTANCE.createMacroDeclaration();
        dispatch.setName(fresh("r_agentProgram"));
        dispatch.setArity(1);
        dispatch.getVariable().add(variable("$agent", identities));
        for (Initialization init : asm.getInitialState()) {
            for (AgentInitialization assignment : init.getAgentInitialization()) {
                programIds.put(assignment, assignments.size());
                assignments.add(assignment);
            }
        }
        boolean changed;
        do {
            int size = contextualRules.size() + contextualFunctions.size();
            for (RuleDeclaration rule : rules)
                walk(rule.getRuleBody(), node -> { if (contextual(node)) contextualRules.add(rule); });
            for (Function function : functions)
                if (function.getDefinition() != null)
                    walk(function.getDefinition().getBody(), node -> { if (contextual(node)) contextualFunctions.add(function); });
            changed = size != contextualRules.size() + contextualFunctions.size();
        } while (changed);
        for (RuleDeclaration rule : rules) {
            if (contextualRules.contains(rule)) {
                VariableTerm self = freshSelf(rule);
                selves.put(rule, self);
                if (rule != asm.getMainrule()) {
                    rule.getVariable().add(self);
                    rule.setArity(rule.getVariable().size());
                }
            }
        }
        for (Function function : functions) {
            if (contextualFunctions.contains(function)) {
                VariableTerm self = freshSelf(function.getDefinition());
                selves.put(function, self);
                function.getDefinition().getVariable().add(self);
                function.setDomain(parameterDomain(function.getDefinition().getVariable()));
                function.setArity(function.getDefinition().getVariable().size());
            }
        }
        // Distinct agent parameter types become the same identity type. Keep
        // overloaded methods distinct while their references still identify the
        // original declaration (choice traces retain SourceRuleInfo).
        Set<String> methodSignatures = new HashSet<>();
        for (Function function : functions) {
            if (function.getDefinition() != null && (function instanceof StaticFunction || function instanceof DerivedFunction)) {
                String key = function.getName() + parameterKey(function.getDefinition().getVariable());
                if (!methodSignatures.add(key)) function.setName(fresh(function.getName() + "__agentOverload"));
            }
        }
        Set<String> ruleSignatures = new HashSet<>();
        for (RuleDeclaration rule : rules) {
            String key = rule.getName() + parameterKey(rule.getVariable());
            if (!ruleSignatures.add(key)) rule.setName(fresh(rule.getName() + "__agentOverload"));
        }
        // Record range identities before changing shared structured domains.
        for (EObject node : nodes) {
            if (node instanceof DomainTerm && ((Term) node).getDomain() instanceof PowersetDomain)
                ranges.put(node, ((PowersetDomain) ((Term) node).getDomain()).getBaseDomain());
            if (node instanceof ExtendRule) extensions.put(node, ((ExtendRule) node).getExtendedDomain());
        }
        for (EObject node : nodes) remap(node);
        // Newly created function parameter products were not in the original graph.
        for (Function function : functions)
            if (contextualFunctions.contains(function)) walk(function.getDomain(), this::remap);
        signature.getDomain().removeIf(domain -> family.contains(domain) || domain == identities);
        signature.getDomain().add(0, identities);
        for (Domain domain : new LinkedHashSet<>(extensions.values())) {
            if (!family.contains(domain)) continue;
            ConcreteDomain reserve = DomainsFactory.eINSTANCE.createConcreteDomain();
            reserve.setName(domain.getName()); reserve.setIsDynamic(true); reserve.setTypeDomain(identities);
            reserve.eAdapters().add(new ReserveInfo(identities));
            reserves.put(domain, reserve);
            signature.getDomain().add(reserve);
        }
        asm.getBodySection().getDomainDefinition().removeIf(d -> family.contains(d.getDefinedDomain()) || d.getDefinedDomain() == identities);
        for (RuleDeclaration rule : rules) {
            Rewriter rewriter = new Rewriter(selves.get(rule));
            for (VariableTerm variable : rule.getVariable()) rewriter.copies.put(variable, variable);
            Rule body = (Rule) rewriter.copy(rule.getRuleBody());
            if (rule == asm.getMainrule() && selves.containsKey(rule)) {
                StaticFunction implicit = DefinitionsFactory.eINSTANCE.createStaticFunction();
                implicit.setName(fresh("agentMain"));
                implicit.setCodomain(identities);
                implicit.setArity(0);
                signature.getFunction().add(implicit);
                LetRule let = BasictransitionrulesFactory.eINSTANCE.createLetRule();
                let.getVariable().add(selves.get(rule));
                let.getInitExpression().add(term(implicit));
                let.setInRule(body);
                body = let;
            }
            rule.setRuleBody(body);
        }
        for (Function function : functions) {
            if (function.getDefinition() != null) {
                Rewriter rewriter = new Rewriter(selves.get(function));
                for (VariableTerm variable : function.getDefinition().getVariable()) rewriter.copies.put(variable, variable);
                function.getDefinition().setBody((Term) rewriter.copy(function.getDefinition().getBody()));
            }
        }
        for (Property property : asm.getBodySection().getProperty()) {
            for (EStructuralFeature feature : property.eClass().getEAllStructuralFeatures())
                if (writable(feature) && property.eGet(feature) instanceof Term)
                    property.eSet(feature, new Rewriter(null).copy((Term) property.eGet(feature)));
        }
        for (Initialization init : asm.getInitialState()) initialise(init);
        buildDispatch();
        int mainIndex = asm.getBodySection().getRuleDeclaration().indexOf(asm.getMainrule());
        asm.getBodySection().getRuleDeclaration().add(mainIndex < 0 ? rules.size() : mainIndex, dispatch);
        flattened = true;
        return asm;
    }

    private void discover(Domain domain) {
        if (domain instanceof AgentDomain) { family.add(domain); return; }
        if (domain instanceof ConcreteDomain && isAgent(((ConcreteDomain) domain).getTypeDomain(), identitySet())) {
            Domain parent = ((ConcreteDomain) domain).getTypeDomain();
            discover(parent);
            parents.put(domain, parent);
            family.add(domain);
        }
    }
    private static boolean isAgent(Domain domain, Set<Domain> seen) {
        if (domain == null || !seen.add(domain)) return false;
        return domain instanceof AgentDomain || domain instanceof ConcreteDomain
                && isAgent(((ConcreteDomain) domain).getTypeDomain(), seen);
    }
    private boolean isSubset(Domain child, Domain parent) {
        while (child != null) { if (child == parent) return true; child = parents.get(child); }
        return false;
    }
    private boolean contextual(EObject node) {
        return isSelf(node) || node instanceof MacroCallRule && contextualRules.contains(((MacroCallRule) node).getCalledMacro())
                || node instanceof FunctionTerm && contextualFunctions.contains(((FunctionTerm) node).getFunction());
    }
    private String parameterKey(List<VariableTerm> variables) {
        return "(" + String.join(",", variables.stream().map(v -> {
            Domain domain = v.getDomain();
            if (family.contains(domain)) return identities.getName();
            if (domain instanceof SequenceDomain) return "List";
            if (domain instanceof PowersetDomain) return "Set";
            if (domain instanceof BagDomain) return "Bag";
            if (domain instanceof MapDomain) return "Map";
            if (domain instanceof ProductDomain) return "Tuple" + ((ProductDomain) domain).getDomains().size();
            return domain.getName();
        }).toList()) + ")";
    }
    private void initialise(Initialization init) {
        List<FunctionInitialization> original = new ArrayList<>(init.getFunctionInitialization());
        Map<Domain, AgentInitialization> programs = new IdentityHashMap<>();
        // Domain references in AgentInitialization have already been erased; use their saved identity.
        for (AgentInitialization assignment : assignments)
            if (assignment.getInitialState() == init) programs.put(assignmentDomains.get(assignment), assignment);
        List<FunctionInitialization> added = new ArrayList<>();
        for (Domain domain : family) {
            AgentInitialization assignment = programs.get(domain);
            added.add(initialisation(selected.get(domain), null, integer(assignment == null ? -1 : programIds.get(assignment))));
        }
        for (var entry : members.entrySet()) {
            Domain domain = entry.getKey();
            VariableTerm value = variable("$agentMember", identities);
            Term body = bool(false);
            for (var constant : constants.entrySet())
                if (isSubset(constant.getValue(), domain)) body = binary("or", body, binary("eq", value, term(constant.getKey())));
            Term explicit = definitions.get(domain);
            for (DomainInitialization domainInit : init.getDomainInitialization())
                if (initialisedDomains.get(domainInit) == domain) explicit = domainInit.getBody();
            if (explicit != null) body = binary("contains", (Term) new Rewriter(null).copy(explicit), value);
            added.add(initialisation(entry.getValue(), value, body));
        }
        VariableTerm agent = variable("$agentProgram", identities);
        Term tag = integer(-1);
        for (var entry : constants.entrySet())
            tag = conditional(binary("eq", agent, term(entry.getKey())), term(selected.get(entry.getValue())), tag);
        added.add(initialisation(program, agent, tag));
        for (FunctionInitialization functionInit : original) {
            Rewriter rewriter = new Rewriter(null);
            Term guard = bool(true);
            for (VariableTerm value : functionInit.getVariable()) {
                rewriter.copies.put(value, value);
                Domain originalType = types.get(value);
                if (members.containsKey(originalType)) guard = binary("and", guard, term(members.get(originalType), value));
            }
            Term body = (Term) rewriter.copy(functionInit.getBody());
            functionInit.setBody(guard instanceof BooleanTerm ? body : conditional(guard, body, undef(body.getDomain())));
        }
        init.getDomainInitialization().removeIf(initialisedDomains::containsKey);
        init.getFunctionInitialization().addAll(0, added);
        init.getAgentInitialization().clear();
    }
    private void buildDispatch() {
        VariableTerm agent = dispatch.getVariable().get(0);
        Rule body = BasictransitionrulesFactory.eINSTANCE.createSkipRule();
        for (int i = assignments.size() - 1; i >= 0; i--) {
            AgentInitialization assignment = assignments.get(i);
            MacroCallRule call = BasictransitionrulesFactory.eINSTANCE.createMacroCallRule();
            call.setCalledMacro(assignment.getProgram().getCalledMacro());
            Rewriter rewriter = new Rewriter(agent);
            for (Term argument : assignment.getProgram().getParameters()) call.getParameters().add((Term) rewriter.copy(argument));
            if (contextualRules.contains(call.getCalledMacro())) call.getParameters().add(agent);
            ConditionalRule branch = BasictransitionrulesFactory.eINSTANCE.createConditionalRule();
            branch.setGuard(binary("eq", term(program, agent), integer(i)));
            branch.setThenRule(call); branch.setElseRule(body); body = branch;
        }
        dispatch.setRuleBody(body);
    }

    private final class Rewriter {
        final Map<EObject, EObject> copies = new IdentityHashMap<>();
        final VariableTerm self;
        Rewriter(VariableTerm self) { this.self = self; }
        EObject copy(EObject source) {
            if (source == null) return null;
            if (erased(source)) return identities;
            if (copies.containsKey(source)) return copies.get(source);
            if (isSelf(source)) { requireSelf(); return self; }
            if (!(source instanceof Term || source instanceof Rule)) return source;
            if (source instanceof DomainTerm && members.containsKey(ranges.get(source))) return range(ranges.get(source));
            if (source instanceof TermAsRule) {
                TermAsRule rule = (TermAsRule) source;
                if (rule.getTerm() instanceof FunctionTerm && builtin(((FunctionTerm) rule.getTerm()).getFunction(), "program")) {
                    if (assignments.isEmpty()) throw new IllegalArgumentException("Agent flattening: no program has been initialised");
                    MacroCallRule call = BasictransitionrulesFactory.eINSTANCE.createMacroCallRule();
                    call.setCalledMacro(dispatch);
                    call.getParameters().add((Term) copy(((FunctionTerm) rule.getTerm()).getArguments().getTerms().get(0)));
                    return call;
                }
                if (programClosures.contains(types.get(rule.getTerm()))) {
                    MacroCallRule call = BasictransitionrulesFactory.eINSTANCE.createMacroCallRule();
                    call.setCalledMacro(dispatch);
                    call.getParameters().add((Term) copy(rule.getTerm()));
                    return call;
                }
            }
            if (source instanceof FunctionTerm && builtin(((FunctionTerm) source).getFunction(), "program"))
                return copy(((FunctionTerm) source).getArguments().getTerms().get(0));
            if (source instanceof ExtendRule && family.contains(extensions.get(source))) {
                ExtendRule original = (ExtendRule) source;
                Domain domain = extensions.get(source);
                ExtendRule extended = BasictransitionrulesFactory.eINSTANCE.createExtendRule();
                copies.put(source, extended); extended.setExtendedDomain(reserves.get(domain));
                BlockRule setup = BasictransitionrulesFactory.eINSTANCE.createBlockRule();
                for (VariableTerm variable : original.getBoundVar()) {
                    VariableTerm value = (VariableTerm) copy(variable);
                    extended.getBoundVar().add(value);
                    for (var entry : members.entrySet())
                        if (isSubset(domain, entry.getKey())) setup.getRules().add(update(term(entry.getValue(), value), bool(true)));
                    setup.getRules().add(update(term(program, value), term(selected.get(domain))));
                }
                SeqRule sequence = TurbotransitionrulesFactory.eINSTANCE.createSeqRule();
                sequence.getRules().add(setup);
                sequence.getRules().add((Rule) copy(original.getDoRule()));
                extended.setDoRule(sequence); return extended;
            }
            if (source instanceof FunctionTerm && builtin(((FunctionTerm) source).getFunction(), "id"))
                return term(standard("toString", null), (Term) copy(((FunctionTerm) source).getArguments().getTerms().get(0)));
            if (source instanceof FunctionTerm && builtin(((FunctionTerm) source).getFunction(), "getAgent")) {
                VariableTerm value = variable(fresh("$agentById"), identities);
                SetCt matches = setComprehension(value, domainRange(), binary("eq", term(standard("toString", null), value),
                        (Term) copy(((FunctionTerm) source).getArguments().getTerms().get(0))));
                VariableTerm found = variable(fresh("$agentMatches"), matches.getDomain());
                FunctionTerm result = term(standard("chooseone", null), found);
                result.setDomain(identities);
                LetTerm let = FurthertermsFactory.eINSTANCE.createLetTerm();
                let.setDomain(identities); let.getVariable().add(found); let.getAssignmentTerm().add(matches);
                let.setBody(conditional(term(standard("isEmpty", null), found), undef(identities), result));
                return let;
            }
            EObject result = source.eClass().getEPackage().getEFactoryInstance().create(source.eClass());
            copies.put(source, result);
            if (source instanceof VariableTerm)
                result.eAdapters().add(new SourceDomainInfo(originalDomainName((VariableTerm) source)));
            for (EStructuralFeature feature : source.eClass().getEAllStructuralFeatures()) {
                if (!writable(feature) || !source.eIsSet(feature)) continue;
                Object value = source.eGet(feature);
                if (feature.isMany()) {
                    List<Object> copied = new ArrayList<>();
                    for (Object item : (List<?>) value) copied.add(copyValue(item));
                    result.eSet(feature, copied);
                } else result.eSet(feature, copyValue(value));
            }
            if (source instanceof MacroCallRule && contextualRules.contains(((MacroCallRule) source).getCalledMacro())) {
                requireSelf(); ((MacroCallRule) result).getParameters().add(self);
            }
            if (source instanceof FunctionTerm && contextualFunctions.contains(((FunctionTerm) source).getFunction())) {
                requireSelf();
                FunctionTerm call = (FunctionTerm) result;
                List<Term> args = call.getArguments() == null ? new ArrayList<>() : new ArrayList<>(call.getArguments().getTerms());
                args.add(self); call.setArguments(tuple(args));
            }
            return result;
        }
        private void requireSelf() { if (self == null) throw new IllegalArgumentException("A self-dependent expression needs an agent context"); }
        private Object copyValue(Object value) { return value instanceof EObject ? copy((EObject) value) : value; }
    }

    private final Map<AgentInitialization, Domain> assignmentDomains = new IdentityHashMap<>();
    private final Map<Domain, Term> definitions = new IdentityHashMap<>();
    private final Map<DomainInitialization, Domain> initialisedDomains = new IdentityHashMap<>();
    private void remap(EObject node) {
        if (node instanceof AgentInitialization)
            assignmentDomains.put((AgentInitialization) node, ((AgentInitialization) node).getDomain());
        if (node instanceof DomainDefinition && family.contains(((DomainDefinition) node).getDefinedDomain()))
            definitions.put(((DomainDefinition) node).getDefinedDomain(), ((DomainDefinition) node).getBody());
        if (node instanceof DomainInitialization && family.contains(((DomainInitialization) node).getInitializedDomain()))
            initialisedDomains.put((DomainInitialization) node, ((DomainInitialization) node).getInitializedDomain());
        for (EStructuralFeature feature : node.eClass().getEAllStructuralFeatures()) {
            if (!writable(feature) || !node.eIsSet(feature)) continue;
            Object value = node.eGet(feature);
            if (feature.isMany()) {
                @SuppressWarnings("unchecked") List<Object> values = (List<Object>) value;
                // Unique reference lists may merge several replaced domains.
                List<Object> replaced = new ArrayList<>();
                boolean changed = false;
                for (Object item : values) {
                    changed |= erased(item);
                    if (erased(item) && feature instanceof EReference
                            && !((EReference) feature).getEReferenceType().isInstance(identities)) continue;
                    replaced.add(erased(item) ? identities : item);
                }
                if (changed) { values.clear(); values.addAll(replaced); }
            } else if (erased(value)) node.eSet(feature, identities);
        }
    }
    private boolean erased(Object value) { return family.contains(value) || programClosures.contains(value); }
    private ControlledFunction controlled(String suffix, Domain argument, Domain result) {
        ControlledFunction function = DefinitionsFactory.eINSTANCE.createControlledFunction();
        function.setName(fresh("agent__" + suffix)); function.setDomain(argument);
        function.setCodomain(result); function.setArity(argument == null ? 0 : 1);
        InterfaceInfo info = new InterfaceInfo(); info.internal = true; function.eAdapters().add(info);
        asm.getHeaderSection().getSignature().getFunction().add(function); return function;
    }
    private FunctionInitialization initialisation(ControlledFunction function, VariableTerm variable, Term body) {
        FunctionInitialization init = StructureFactory.eINSTANCE.createFunctionInitialization();
        init.setInitializedFunction(function); if (variable != null) init.getVariable().add(variable);
        init.setBody(body); return init;
    }
    private Term range(Domain domain) {
        return term(domainRanges.get(domain));
    }
    private SetCt setComprehension(VariableTerm value, Term range, Term guard) {
        SetCt set = FurthertermsFactory.eINSTANCE.createSetCt();
        set.setDomain(powerset()); set.getVariable().add(value); set.getRanges().add(range);
        set.setGuard(guard); set.setTerm(value); return set;
    }
    private DomainTerm domainRange() {
        DomainTerm range = BasictermsFactory.eINSTANCE.createDomainTerm(); range.setDomain(powerset()); return range;
    }
    private PowersetDomain powerset() {
        PowersetDomain set = DomainsFactory.eINSTANCE.createPowersetDomain();
        set.setBaseDomain(identities); set.setName("Powerset(" + identities.getName() + ")");
        return set;
    }
    private Domain parameterDomain(List<VariableTerm> parameters) {
        if (parameters.size() == 1) return parameters.get(0).getDomain();
        ProductDomain product = DomainsFactory.eINSTANCE.createProductDomain();
        for (VariableTerm parameter : parameters) product.getDomains().add(parameter.getDomain());
        product.setName("Prod(" + String.join(",", product.getDomains().stream().map(Domain::getName).toList()) + ")");
        return product;
    }
    private VariableTerm freshSelf(EObject body) {
        Set<String> names = new HashSet<>();
        walk(body, node -> { if (node instanceof VariableTerm) names.add(((VariableTerm) node).getName()); });
        String name = "$agentSelf"; while (names.contains(name)) name += "_";
        return variable(name, identities);
    }
    private VariableTerm variable(String name, Domain domain) {
        VariableTerm value = BasictermsFactory.eINSTANCE.createVariableTerm();
        value.setName(name); value.setDomain(domain); value.setKind(VariableKind.LOGICAL_VAR); return value;
    }
    private FunctionTerm term(Function function, Term... args) {
        FunctionTerm value = function instanceof DynamicFunction ? BasictermsFactory.eINSTANCE.createLocationTerm()
                : BasictermsFactory.eINSTANCE.createFunctionTerm();
        value.setFunction(function); value.setDomain(function.getCodomain());
        if (args.length != 0) value.setArguments(tuple(Arrays.asList(args))); return value;
    }
    private TupleTerm tuple(List<Term> args) {
        TupleTerm value = BasictermsFactory.eINSTANCE.createTupleTerm();
        value.setArity(args.size()); value.getTerms().addAll(args); return value;
    }
    private Term binary(String name, Term left, Term right) {
        FunctionTerm value = term(standard(name, name.equals("contains") ? PowersetDomain.class : null), left, right);
        value.setDomain(booleans); return value;
    }
    private Function standard(String name, Class<?> firstArgument) {
        return library.stream().filter(f -> f.getName().equals(name))
                .filter(f -> firstArgument == null || f.getDomain() instanceof ProductDomain
                        && firstArgument.isInstance(((ProductDomain) f.getDomain()).getDomains().get(0)))
                .filter(f -> !name.equals("toString") || f.getDomain() instanceof AnyDomain)
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Missing StandardLibrary function " + name));
    }
    private ConditionalTerm conditional(Term guard, Term yes, Term no) {
        ConditionalTerm value = FurthertermsFactory.eINSTANCE.createConditionalTerm();
        value.setDomain(yes.getDomain()); value.setGuard(guard); value.setThenTerm(yes); value.setElseTerm(no); return value;
    }
    private IntegerTerm integer(int number) {
        IntegerTerm value = FurthertermsFactory.eINSTANCE.createIntegerTerm();
        value.setSymbol(Integer.toString(number)); value.setDomain(integers); return value;
    }
    private BooleanTerm bool(boolean flag) {
        BooleanTerm value = BasictermsFactory.eINSTANCE.createBooleanTerm(flag); value.setDomain(booleans); return value;
    }
    private UndefTerm undef(Domain domain) {
        UndefTerm value = BasictermsFactory.eINSTANCE.createUndefTerm(); value.setDomain(domain); return value;
    }
    private UpdateRule update(Term location, Term value) {
        UpdateRule rule = BasictransitionrulesFactory.eINSTANCE.createUpdateRule();
        rule.setLocation(location); rule.setUpdatingTerm(value); return rule;
    }
    private String fresh(String name) { while (!names.add(name)) name += "_"; return name; }
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

