package org.asmeta.asm2java.generator;

import asmeta.definitions.DerivedFunction;
import asmeta.definitions.Function;
import asmeta.definitions.RuleDeclaration;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.Domain;
import asmeta.structure.Asm;
import asmeta.structure.DomainDefinition;
import asmeta.structure.DomainInitialization;
import asmeta.structure.FunctionDefinition;
import asmeta.transitionrules.basictransitionrules.Rule;
import java.util.ArrayList;
import java.util.List;
import org.asmeta.asm2java.evosuite.DomainToJavaEvosuiteSigDef;
import org.asmeta.asm2java.evosuite.FunctionToJavaEvosuiteSig;
import org.asmeta.asm2java.evosuite.JavaRule;
import org.asmeta.asm2java.evosuite.RuleToJavaEvosuite;
import org.asmeta.asm2java.evosuite.RulesAdder;
import org.asmeta.asm2java.translator.SeqRuleCollector;
import org.asmeta.asm2java.translator.Util;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;

/**
 * This generator creates a translated version of the Java class for testing purposes only,
 *  in fact it creates the class that the _Atg class queries.
 */
@SuppressWarnings("all")
public class JavaTestGenerator extends JavaGenerator {
  private RulesAdder rules;

  public JavaTestGenerator(final RulesAdder rules) {
    super();
    this.rules = rules;
  }

  @Override
  public String compileAsm(final Asm asm) {
    boolean _optimizeSeqMacroRule = this.options.getOptimizeSeqMacroRule();
    if (_optimizeSeqMacroRule) {
      ArrayList<Rule> _arrayList = new ArrayList<Rule>();
      this.seqCalledRules = _arrayList;
      EList<RuleDeclaration> _ruleDeclaration = asm.getBodySection().getRuleDeclaration();
      for (final RuleDeclaration r : _ruleDeclaration) {
        this.seqCalledRules.addAll(new SeqRuleCollector(false).visit(r));
      }
    }
    final String asmName = asm.getName();
    String updateASMText = this.updateSet(asm);
    this.functionSignature(asm);
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
    _builder.append("// ");
    _builder.append(asmName);
    _builder.append(".java automatically generated from ASM2CODE");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("import java.util.ArrayList;");
    _builder.newLine();
    _builder.append("import java.util.Arrays;");
    _builder.newLine();
    _builder.append("import java.util.Collections;");
    _builder.newLine();
    _builder.append("import java.util.HashMap;");
    _builder.newLine();
    _builder.append("import java.util.HashSet;");
    _builder.newLine();
    _builder.append("import java.util.Map;");
    _builder.newLine();
    _builder.append("import java.util.Set;");
    _builder.newLine();
    _builder.append("import java.util.List;");
    _builder.newLine();
    _builder.append("import java.util.Scanner;");
    _builder.newLine();
    _builder.append("import org.apache.commons.collections4.bag.HashBag;");
    _builder.newLine();
    _builder.append("import org.apache.commons.collections4.Bag;");
    _builder.newLine();
    _builder.append("import java.util.concurrent.ThreadLocalRandom;");
    _builder.newLine();
    _builder.append("import java.util.function.Function;");
    _builder.newLine();
    _builder.append("import java.util.stream.Collectors;");
    _builder.newLine();
    _builder.append("import org.javatuples.Decade;");
    _builder.newLine();
    _builder.append("import org.javatuples.Ennead;");
    _builder.newLine();
    _builder.append("import org.javatuples.Octet;");
    _builder.newLine();
    _builder.append("import org.javatuples.Pair;");
    _builder.newLine();
    _builder.append("import org.javatuples.Quartet;");
    _builder.newLine();
    _builder.append("import org.javatuples.Quintet;");
    _builder.newLine();
    _builder.append("import org.javatuples.Septet;");
    _builder.newLine();
    _builder.append("import org.javatuples.Sextet;");
    _builder.newLine();
    _builder.append("import org.javatuples.Triplet;");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.append("class ");
    _builder.append(asmName);
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/// DOMAIN CONTAINERS");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* Domain containers here */");
    _builder.newLine();
    _builder.append("\t");
    String _abstractClassDef = this.abstractClassDef(asm);
    _builder.append(_abstractClassDef, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    String _domainSignature = this.domainSignature(asm);
    _builder.append(_domainSignature, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("//Metodi di supporto per l\'implementazione delle funzioni controlled");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("class Fun0Ctrl<D> {");
    _builder.newLine();
    _builder.append("\t   ");
    _builder.newLine();
    _builder.append("\t   ");
    _builder.append("D oldValue;");
    _builder.newLine();
    _builder.append("\t   ");
    _builder.append("D newValue;");
    _builder.newLine();
    _builder.append("\t   ");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("void set(D d) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("newValue = d;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("D get() {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("return oldValue;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("static class FunNCtrl<D, C> {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("Map<D, C> oldValues = new HashMap<>();");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("Map<D, C> newValues = new HashMap<>();");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("void set(D d, C c) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("newValues.put(d, c);");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("C get(D d) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("return oldValues.get(d);");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("//Metodi di supporto per l\'implementazione delle funzioni non controlled");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("class Fun0<D> {");
    _builder.newLine();
    _builder.append("\t   ");
    _builder.newLine();
    _builder.append("\t   ");
    _builder.append("D value;");
    _builder.newLine();
    _builder.append("\t   ");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("void set(D d) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("value = d;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("D get() {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("return value;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("class FunN<D, C> {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("Map<D, C> values = new HashMap<>();");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("void set(D d, C c) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("values.put(d, c);");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("C get(D d) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("return values.get(d);");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}\t\t\t\t\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/// FUNCTIONS");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("\t");
    String _functionSignature = this.functionSignature(asm);
    _builder.append(_functionSignature, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// Inizializzazione di funzioni e domini");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append(asmName, "\t");
    _builder.append("(){");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("//Definizione iniziale dei domini statici");
    _builder.newLine();
    _builder.append("\t    ");
    _builder.newLine();
    _builder.append("\t ");
    String _initialStaticDomainDefinition = this.initialStaticDomainDefinition(asm);
    _builder.append(_initialStaticDomainDefinition, "\t ");
    _builder.newLineIfNotEmpty();
    _builder.append("\t ");
    String _initialStaticEnumDomainDefinition = this.initialStaticEnumDomainDefinition(asm);
    _builder.append(_initialStaticEnumDomainDefinition, "\t ");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t ");
    _builder.append("//Definizione iniziale dei domini dinamici");
    _builder.newLine();
    _builder.append("\t ");
    _builder.newLine();
    _builder.append("\t ");
    String _initialDynamicDomainDefinition = this.initialDynamicDomainDefinition(asm);
    _builder.append(_initialDynamicDomainDefinition, "\t ");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t ");
    _builder.append("//Definizione iniziale dei domini astratti con funzini statiche");
    _builder.newLine();
    _builder.append("\t ");
    _builder.newLine();
    _builder.append("\t ");
    String _functionAbstractDom = this.functionAbstractDom(asm);
    _builder.append(_functionAbstractDom, "\t ");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t ");
    _builder.append("//Inizializzazione delle funzioni");
    _builder.newLine();
    _builder.append("\t ");
    _builder.newLine();
    _builder.append("\t ");
    String _functionInitialization = this.functionInitialization(asm);
    _builder.append(_functionInitialization, "\t ");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t   ");
    _builder.append("// Definizione delle funzioni statiche");
    _builder.newLine();
    _builder.append("\t");
    String _functionDefinition = this.functionDefinition(asm);
    _builder.append(_functionDefinition, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// Conversione delle regole ASM in metodi java");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    String _ruleDefinitions = this.ruleDefinitions(asm);
    _builder.append(_ruleDefinitions, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// inizializazzione delle funzioni controllate che contengono metodi monitorati nei temini iniziali");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("void initControlledWithMonitored(){");
    _builder.newLine();
    _builder.append("\t  ");
    String _xifexpression = null;
    int _length = this.initConrolledMonitored.length();
    boolean _equals = (_length == 0);
    if (_equals) {
      _xifexpression = "// No controlled functions initialized with monitored ones have been found";
    } else {
      _xifexpression = this.initConrolledMonitored;
    }
    _builder.append(_xifexpression, "\t  ");
    _builder.newLineIfNotEmpty();
    _builder.append("\t   ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// applicazione dell\'aggiornamento del set");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("void fireUpdateSet(){");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t ");
    String _xifexpression_1 = null;
    int _length_1 = updateASMText.length();
    boolean _equals_1 = (_length_1 == 0);
    if (_equals_1) {
      _xifexpression_1 = "// No update set has been found";
    } else {
      _xifexpression_1 = updateASMText;
    }
    _builder.append(_xifexpression_1, "\t\t ");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("//Metodo per l\'aggiornamento dell\'asm");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("void updateASM()");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t\t");
    String _name = asm.getMainrule().getName();
    _builder.append(_name, "\t\t");
    _builder.append("();");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("fireUpdateSet();");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("initControlledWithMonitored();");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    return _builder.toString();
  }

  @Override
  public String abstractClassDef(final Asm asm) {
    StringBuffer sb = new StringBuffer();
    EList<Domain> _domain = asm.getHeaderSection().getSignature().getDomain();
    for (final Domain dd : _domain) {
      if ((dd instanceof AbstractTd)) {
        String _visit = new DomainToJavaEvosuiteSigDef(asm).visit(((AbstractTd)dd));
        String _plus = (("//Variabile di tipo astratto" + "\n\n") + _visit);
        String _plus_1 = (_plus + "\n");
        sb.append(_plus_1);
      }
    }
    return sb.toString();
  }

  @Override
  public String domainSignature(final Asm asm) {
    StringBuffer sb = new StringBuffer();
    EList<Domain> _domain = asm.getHeaderSection().getSignature().getDomain();
    for (final Domain dd : _domain) {
      if (((dd instanceof AbstractTd) == false)) {
        String _visit = new DomainToJavaEvosuiteSigDef(asm).visit(dd);
        String _plus = (("//Variabile di tipo Concreto o Enumerativo" + "\n\n") + _visit);
        String _plus_1 = (_plus + 
          "\n");
        sb.append(_plus_1);
      }
    }
    return sb.toString();
  }

  @Override
  public String initialDynamicDomainDefinition(final Asm asm) {
    StringBuffer initial = new StringBuffer();
    if (((asm.getDefaultInitialState() != null) && (asm.getDefaultInitialState().getDomainInitialization() != null))) {
      EList<DomainInitialization> _domainInitialization = asm.getDefaultInitialState().getDomainInitialization();
      for (final DomainInitialization dd : _domainInitialization) {
        {
          final String domaintojava = new DomainToJavaEvosuiteSigDef(asm).visit(dd);
          String _elemsSetName = Util.getElemsSetName(dd.getInitializedDomain().getName());
          String _plus = (_elemsSetName + "=");
          String _plus_1 = (_plus + domaintojava);
          String _plus_2 = (_plus_1 + ";\n");
          initial.append(_plus_2);
        }
      }
    }
    int _length = initial.length();
    boolean _notEquals = (_length != 0);
    if (_notEquals) {
      String _string = initial.toString();
      return (_string + "\n");
    } else {
      return "";
    }
  }

  @Override
  public String initialStaticDomainDefinition(final Asm asm) {
    StringBuffer initial = new StringBuffer();
    if (((asm.getBodySection() != null) && (asm.getBodySection().getDomainDefinition() != null))) {
      EList<DomainDefinition> _domainDefinition = asm.getBodySection().getDomainDefinition();
      for (final DomainDefinition dd : _domainDefinition) {
        {
          String _name = dd.getDefinedDomain().getName();
          String _plus = (_name + ".elems = Collections.unmodifiableList(Arrays.asList");
          String _visit = new DomainToJavaEvosuiteSigDef(asm).visit(dd);
          String _plus_1 = (_plus + _visit);
          String _plus_2 = (_plus_1 + ");\n");
          initial.append(_plus_2);
          String _name_1 = dd.getDefinedDomain().getName();
          String _plus_3 = (_name_1 + "_elems = Collections.unmodifiableList(Arrays.asList");
          String _visit_1 = new DomainToJavaEvosuiteSigDef(asm).visit(dd);
          String _plus_4 = (_plus_3 + _visit_1);
          String _plus_5 = (_plus_4 + ");\n");
          initial.append(_plus_5);
        }
      }
    }
    int _length = initial.toString().length();
    boolean _notEquals = (_length != 0);
    if (_notEquals) {
      String _string = initial.toString();
      return (_string + "\n");
    } else {
      return "";
    }
  }

  @Override
  public String functionSignature(final Asm asm) {
    StringBuffer sb = new StringBuffer();
    EList<Function> _function = asm.getHeaderSection().getSignature().getFunction();
    for (final Function fd : _function) {
      if ((fd instanceof DerivedFunction)) {
        EList<FunctionDefinition> _functionDefinition = asm.getBodySection().getFunctionDefinition();
        for (final FunctionDefinition fDef : _functionDefinition) {
          boolean _equals = fDef.getDefinedFunction().getName().equals(((DerivedFunction)fd).getName());
          if (_equals) {
            String _visit = new FunctionToJavaEvosuiteSig(asm).visit(((DerivedFunction)fd));
            String _plus = (_visit + "\n");
            sb.append(_plus);
          }
        }
      } else {
        String _visit_1 = new FunctionToJavaEvosuiteSig(asm).visit(fd);
        String _plus_1 = (_visit_1 + "\n");
        sb.append(_plus_1);
      }
    }
    return sb.toString();
  }

  @Override
  public String foo(final RuleDeclaration r, final String methodName, final Asm asm) {
    return "";
  }

  @Override
  public String ruleTranslation(final RuleDeclaration r, final String methodName, final Asm asm) {
    JavaRule rule = new JavaRule(methodName);
    StringBuffer sb = new StringBuffer();
    Integer _arity = r.getArity();
    boolean _equals = ((_arity).intValue() == 0);
    if (_equals) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("void ");
      _builder.append(methodName);
      _builder.append("(){");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      String _addNewBranch = rule.addNewBranch();
      _builder.append(_addNewBranch, "\t");
      _builder.append(" = true;");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      Rule _ruleBody = r.getRuleBody();
      String _visit = new RuleToJavaEvosuite(asm, false, this.options, rule).visit(((Rule) _ruleBody));
      _builder.append(_visit, "\t");
      _builder.newLineIfNotEmpty();
      _builder.append("}");
      _builder.newLine();
      _builder.newLine();
      sb.append(_builder);
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("void ");
      _builder_1.append(methodName);
      _builder_1.append(" (");
      String _adaptRuleParam = new Util().adaptRuleParam(r.getVariable(), asm);
      _builder_1.append(_adaptRuleParam);
      _builder_1.append("){");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("\t");
      String _addNewBranch_1 = rule.addNewBranch();
      _builder_1.append(_addNewBranch_1, "\t");
      _builder_1.append(" = true;");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("\t");
      String _visit_1 = new RuleToJavaEvosuite(asm, false, this.options, rule).visit(r.getRuleBody());
      _builder_1.append(_visit_1, "\t");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("}");
      _builder_1.newLine();
      _builder_1.newLine();
      sb.append(_builder_1);
    }
    String flagInit = this.coverBranchesFlagInit(rule);
    sb.insert(0, flagInit);
    this.rules.addRule(rule.getName(), rule);
    return sb.toString();
  }

  public String coverBranchesFlagInit(final JavaRule rule) {
    final StringBuffer sb = new StringBuffer();
    List<String> _branches = rule.getBranches();
    for (final String branch : _branches) {
      {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("boolean ");
        _builder.append(branch);
        _builder.append(" = false;");
        sb.append(_builder);
        sb.append(System.lineSeparator());
      }
    }
    return sb.toString();
  }
}
