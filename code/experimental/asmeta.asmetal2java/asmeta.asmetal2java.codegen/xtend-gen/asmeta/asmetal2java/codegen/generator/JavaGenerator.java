package asmeta.asmetal2java.codegen.generator;

import asmeta.asmetal2java.codegen.config.TranslatorOptions;
import asmeta.asmetal2java.codegen.translator.DomainToJavaSigDef;
import asmeta.asmetal2java.codegen.translator.DomainToJavaString;
import asmeta.asmetal2java.codegen.translator.FindMonitoredInControlledFunct;
import asmeta.asmetal2java.codegen.translator.FunctionClassDef;
import asmeta.asmetal2java.codegen.translator.FunctionToJavaDef;
import asmeta.asmetal2java.codegen.translator.FunctionToJavaSig;
import asmeta.asmetal2java.codegen.translator.RuleToJava;
import asmeta.asmetal2java.codegen.translator.SeqRuleCollector;
import asmeta.asmetal2java.codegen.translator.Util;
import asmeta.definitions.ControlledFunction;
import asmeta.definitions.DerivedFunction;
import asmeta.definitions.Function;
import asmeta.definitions.RuleDeclaration;
import asmeta.definitions.StaticFunction;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.ProductDomain;
import asmeta.structure.Asm;
import asmeta.structure.DomainDefinition;
import asmeta.structure.DomainInitialization;
import asmeta.structure.FunctionDefinition;
import asmeta.structure.FunctionInitialization;
import asmeta.transitionrules.basictransitionrules.Rule;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.junit.Assert;

/**
 * Generates the translation of an Asm specification to a java class from an ASMeta specification.
 */
@SuppressWarnings("all")
public class JavaGenerator extends AsmToJavaGenerator {
  protected String initConrolledMonitored;

  public void compileAndWrite(final Asm asm, final String writerPath, final TranslatorOptions userOptions) {
    Assert.assertTrue(writerPath.endsWith(".java"));
    this.compileAndWrite(asm, writerPath, "JAVA", userOptions);
  }

  protected List<Rule> seqCalledRules;

  protected String supp;

  private final FindMonitoredInControlledFunct findMonitoredInControlledFunct = new FindMonitoredInControlledFunct();

  /**
   * Create an instance of the {@code DomainToJavaSigDef} object.
   */
  protected DomainToJavaSigDef createDomainToJavaSigDef(final Asm resource) {
    return new DomainToJavaSigDef(resource);
  }

  /**
   * Create an instance of the {@code ToString} object.
   */
  protected DomainToJavaString createToString(final Asm resource) {
    return new DomainToJavaString(resource);
  }

  /**
   * Create an instance of the {@code FunctionToJavaSig} object.
   */
  protected FunctionToJavaSig createFunctionToJavaSig(final Asm resource) {
    return new FunctionToJavaSig(resource);
  }

  /**
   * Create an instance of the {@code FunctionToJavaDef} object.
   */
  protected FunctionToJavaDef createFunctionToJavaDef(final Asm asm) {
    return new FunctionToJavaDef(asm);
  }

  /**
   * Create an instance of the {@code RuleToJava} object.
   */
  protected RuleToJava createRuleToJava(final Asm resource, final boolean seqBlock, final TranslatorOptions translatorOptions) {
    return new RuleToJava(resource, seqBlock, translatorOptions);
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
    String _imports = this.getImports();
    _builder.append(_imports);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("abstract class ");
    _builder.append(asmName);
    _builder.append("Sig {");
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
    _builder.append("//Support methods for implementing controlled functions");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    String _fun0CtrlClass = FunctionClassDef.getFun0CtrlClass();
    _builder.append(_fun0CtrlClass, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    String _funNCtrlClass = FunctionClassDef.getFunNCtrlClass();
    _builder.append(_funNCtrlClass, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("//Support methods for the implementation of non-controlled functions");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    String _fun0Class = FunctionClassDef.getFun0Class();
    _builder.append(_fun0Class, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    String _funNClass = FunctionClassDef.getFunNClass();
    _builder.append(_funNClass, "\t");
    _builder.newLineIfNotEmpty();
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
    _builder.append("////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/// RULE DEFINITION");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/////////////////////////////////////////////////");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* Rule definition here */");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    String _ruleDefinition = this.ruleDefinition(asm);
    _builder.append(_ruleDefinition, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.append("class ");
    _builder.append(asmName);
    _builder.append(" extends ");
    _builder.append(asmName);
    _builder.append("Sig {");
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
    String _updateASM = this.getUpdateASM(asm);
    _builder.append(_updateASM, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public static void main(String[] args) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("// TODO: auto-generated main method by Asmeta2Java ");
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

  protected String getImports() {
    StringConcatenation _builder = new StringConcatenation();
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
    return _builder.toString();
  }

  protected String getUpdateASM(final Asm asm) {
    StringConcatenation _builder = new StringConcatenation();
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
    return _builder.toString();
  }

  public String abstractClassDef(final Asm asm) {
    StringBuffer sb = new StringBuffer();
    EList<Domain> _domain = asm.getHeaderSection().getSignature().getDomain();
    for (final Domain dd : _domain) {
      if ((dd instanceof AbstractTd)) {
        String _visit = this.createDomainToJavaSigDef(asm).visit(((AbstractTd)dd));
        String _plus = (("//Variabile di tipo astratto" + "\n\n") + _visit);
        String _plus_1 = (_plus + "\n");
        sb.append(_plus_1);
      }
    }
    return sb.toString();
  }

  public String domainSignature(final Asm asm) {
    StringBuffer sb = new StringBuffer();
    EList<Domain> _domain = asm.getHeaderSection().getSignature().getDomain();
    for (final Domain dd : _domain) {
      if (((dd instanceof AbstractTd) == false)) {
        String _visit = this.createDomainToJavaSigDef(asm).visit(dd);
        String _plus = (("//Variabile di tipo Concreto o Enumerativo" + "\n\n") + _visit);
        String _plus_1 = (_plus + 
          "\n");
        sb.append(_plus_1);
      }
    }
    return sb.toString();
  }

  public String functionSignature(final Asm asm) {
    StringBuffer sb = new StringBuffer();
    EList<Function> _function = asm.getHeaderSection().getSignature().getFunction();
    for (final Function fd : _function) {
      if ((fd instanceof DerivedFunction)) {
        EList<FunctionDefinition> _functionDefinition = asm.getBodySection().getFunctionDefinition();
        for (final FunctionDefinition fDef : _functionDefinition) {
          boolean _equals = fDef.getDefinedFunction().getName().equals(((DerivedFunction)fd).getName());
          if (_equals) {
            String _visit = this.createFunctionToJavaSig(asm).visit(((DerivedFunction)fd));
            String _plus = (_visit + "\n");
            sb.append(_plus);
          }
        }
      } else {
        String _visit_1 = this.createFunctionToJavaSig(asm).visit(fd);
        String _plus_1 = (_visit_1 + "\n");
        sb.append(_plus_1);
      }
    }
    return sb.toString();
  }

  public String ruleDefinition(final Asm asm) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; (i < asm.getBodySection().getRuleDeclaration().size()); i++) {
      sb.append(this.ruleDeclaration(asm.getBodySection().getRuleDeclaration().get(i), asm));
    }
    return sb.toString();
  }

  public String ruleDeclaration(final RuleDeclaration r, final Asm asm) {
    Set<Boolean> bb = Collections.<Boolean>unmodifiableSet(CollectionLiterals.<Boolean>newHashSet(Boolean.valueOf(true), Boolean.valueOf(false)));
    StringBuffer result = new StringBuffer();
    if (((this.seqCalledRules == null) || this.seqCalledRules.contains(r.getRuleBody()))) {
      String _name = r.getName();
      String _plus = (_name + "_seq");
      result.append(this.ruleTranslationSig(r, _plus, asm));
    }
    result.append(this.ruleTranslationSig(r, r.getName(), asm));
    return result.toString();
  }

  protected String ruleTranslationSig(final RuleDeclaration r, final String methodName, final Asm asm) {
    Integer _arity = r.getArity();
    boolean _equals = ((_arity).intValue() == 0);
    if (_equals) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("abstract void ");
      _builder.append(methodName);
      _builder.append("();");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      return _builder.toString();
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("abstract void ");
      _builder_1.append(methodName);
      _builder_1.append(" (");
      String _replaceAll = new Util().adaptRuleParam(r.getVariable(), asm).replaceAll("\\$", "_");
      _builder_1.append(_replaceAll);
      _builder_1.append(");");
      _builder_1.newLineIfNotEmpty();
      _builder_1.newLine();
      return _builder_1.toString();
    }
  }

  public String initialStaticDomainDefinition(final Asm asm) {
    StringBuffer initial = new StringBuffer();
    if (((asm.getBodySection() != null) && (asm.getBodySection().getDomainDefinition() != null))) {
      EList<DomainDefinition> _domainDefinition = asm.getBodySection().getDomainDefinition();
      for (final DomainDefinition dd : _domainDefinition) {
        {
          final String sig = this.createDomainToJavaSigDef(asm).visit(dd);
          String _name = dd.getDefinedDomain().getName();
          String _plus = (_name + ".elems = Collections.unmodifiableList(Arrays.asList");
          String _plus_1 = (_plus + sig);
          String _plus_2 = (_plus_1 + ");\n");
          initial.append(_plus_2);
          String _name_1 = dd.getDefinedDomain().getName();
          String _plus_3 = (_name_1 + "_elems = Collections.unmodifiableList(Arrays.asList");
          String _plus_4 = (_plus_3 + sig);
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

  public String initialStaticEnumDomainDefinition(final Asm asm) {
    StringBuffer initial = new StringBuffer();
    if (((asm.getBodySection() != null) && (asm.getBodySection().getDomainDefinition() != null))) {
      EList<Domain> _domain = asm.getHeaderSection().getSignature().getDomain();
      for (final Domain dd : _domain) {
        if ((dd instanceof EnumTd)) {
          String _name = ((EnumTd)dd).getName();
          String _plus = (_name + "_elemsList = Collections.unmodifiableList(Arrays.asList(");
          initial.append(_plus);
          for (int i = 0; (i < ((EnumTd)dd).getElement().size()); i++) {
            int _size = ((EnumTd)dd).getElement().size();
            int _minus = (_size - 1);
            boolean _notEquals = (i != _minus);
            if (_notEquals) {
              StringConcatenation _builder = new StringConcatenation();
              String _name_1 = ((EnumTd)dd).getName();
              _builder.append(_name_1);
              _builder.append(".");
              String _visit = this.createToString(asm).visit(((EnumTd)dd).getElement().get(i));
              _builder.append(_visit);
              _builder.append(", ");
              initial.append(_builder);
            } else {
              StringConcatenation _builder_1 = new StringConcatenation();
              String _name_2 = ((EnumTd)dd).getName();
              _builder_1.append(_name_2);
              _builder_1.append(".");
              String _visit_1 = this.createToString(asm).visit(((EnumTd)dd).getElement().get(i));
              _builder_1.append(_visit_1);
              _builder_1.append(")");
              initial.append(_builder_1);
            }
          }
          initial.append(");\n");
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

  public String initialDynamicDomainDefinition(final Asm asm) {
    StringBuffer initial = new StringBuffer();
    if (((asm.getDefaultInitialState() != null) && (asm.getDefaultInitialState().getDomainInitialization() != null))) {
      EList<DomainInitialization> _domainInitialization = asm.getDefaultInitialState().getDomainInitialization();
      for (final DomainInitialization dd : _domainInitialization) {
        {
          final String domaintojava = this.createDomainToJavaSigDef(asm).visit(dd);
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

  public String functionAbstractDom(final Asm asm) {
    StringBuffer sb = new StringBuffer();
    EList<Function> _function = asm.getHeaderSection().getSignature().getFunction();
    for (final Function fd : _function) {
      if ((((fd instanceof StaticFunction) && (fd.getCodomain() instanceof AbstractTd)) && (fd.getDomain() == null))) {
        String _string = new String("\"");
        this.supp = _string;
        String _name = fd.getName();
        String _plus = (_name + " = new ");
        String _name_1 = fd.getCodomain().getName();
        String _plus_1 = (_plus + _name_1);
        String _plus_2 = (_plus_1 + "(");
        String _plus_3 = (_plus_2 + this.supp);
        String _name_2 = fd.getName();
        String _plus_4 = (_plus_3 + _name_2);
        String _plus_5 = (_plus_4 + this.supp);
        String _plus_6 = (_plus_5 + ")");
        String _plus_7 = (_plus_6 + ";\n");
        sb.append(_plus_7);
        String _name_3 = fd.getCodomain().getName();
        String _plus_8 = (_name_3 + "_elemsList.add(");
        String _plus_9 = (_plus_8 + this.supp);
        String _name_4 = fd.getName();
        String _plus_10 = (_plus_9 + _name_4);
        String _plus_11 = (_plus_10 + this.supp);
        String _plus_12 = (_plus_11 + ");\n");
        sb.append(_plus_12);
        String _name_5 = fd.getCodomain().getName();
        String _plus_13 = (_name_5 + "_Class.add(");
        String _name_6 = fd.getName();
        String _plus_14 = (_plus_13 + _name_6);
        String _plus_15 = (_plus_14 + ");\n");
        sb.append(_plus_15);
      }
    }
    return sb.toString();
  }

  public String functionInitialization(final Asm asm) {
    StringBuffer initial = new StringBuffer();
    StringBuffer initialMonitored = new StringBuffer();
    final FunctionToJavaDef functionToJavaDef = this.createFunctionToJavaDef(asm);
    if (((asm.getDefaultInitialState() != null) && (asm.getDefaultInitialState().getFunctionInitialization() != null))) {
      EList<FunctionInitialization> _functionInitialization = asm.getDefaultInitialState().getFunctionInitialization();
      for (final FunctionInitialization fd : _functionInitialization) {
        {
          final String trans = functionToJavaDef.visit(fd.getInitializedFunction());
          boolean containsMonitored = (this.findMonitoredInControlledFunct.visit(fd.getBody())).booleanValue();
          if ((containsMonitored == false)) {
            initial.append(trans);
          } else {
            initialMonitored.append(trans);
          }
        }
      }
    }
    this.initConrolledMonitored = initialMonitored.toString().replaceAll("\\$", "_");
    return initial.toString().replaceAll("\\$", "_");
  }

  public String functionDefinition(final Asm asm) {
    StringBuffer sb = new StringBuffer();
    if (((asm.getBodySection() != null) && (asm.getBodySection().getFunctionDefinition() != null))) {
      EList<FunctionDefinition> _functionDefinition = asm.getBodySection().getFunctionDefinition();
      for (final FunctionDefinition fd : _functionDefinition) {
        {
          StringConcatenation _builder = new StringConcatenation();
          String _visit = this.createFunctionToJavaDef(asm).visit(fd.getDefinedFunction());
          _builder.append(_visit);
          _builder.newLineIfNotEmpty();
          sb.append(_builder);
          Domain pd = fd.getDefinedFunction().getDomain();
          if ((pd instanceof ProductDomain)) {
            Domain _get = ((ProductDomain)pd).getDomains().get(0);
            if ((_get instanceof ConcreteDomain)) {
              int firstIndex = sb.toString().indexOf("$u");
              if ((firstIndex != (-1))) {
                String before = sb.substring(0, (firstIndex + 2));
                String after = sb.substring((firstIndex + 2));
                after = after.replaceAll("\\$u", "\\$u.value");
                StringBuffer _stringBuffer = new StringBuffer((before + after));
                sb = _stringBuffer;
              }
            }
            Domain _get_1 = ((ProductDomain)pd).getDomains().get(1);
            if ((_get_1 instanceof ConcreteDomain)) {
              int firstIndex_1 = sb.toString().indexOf("$p");
              if ((firstIndex_1 != (-1))) {
                String before_1 = sb.substring(0, (firstIndex_1 + 2));
                String after_1 = sb.substring((firstIndex_1 + 2));
                after_1 = after_1.replaceAll("\\$p", "\\$p.value");
                StringBuffer _stringBuffer_1 = new StringBuffer((before_1 + after_1));
                sb = _stringBuffer_1;
              }
            }
          }
        }
      }
      return sb.toString().replaceAll("\\$", "_");
    }
    return "";
  }

  public String ruleDefinitions(final Asm e) {
    StringBuffer sb = new StringBuffer();
    EList<RuleDeclaration> _ruleDeclaration = e.getBodySection().getRuleDeclaration();
    for (final RuleDeclaration r : _ruleDeclaration) {
      sb.append(this.ruleImplementation(r, e).replaceAll("\\$", "_"));
    }
    return sb.toString();
  }

  public String ruleImplementation(final RuleDeclaration r, final Asm asm) {
    StringBuffer result = new StringBuffer();
    if (((this.seqCalledRules == null) || this.seqCalledRules.contains(r.getRuleBody()))) {
      String _name = r.getName();
      String _plus = (_name + "_seq");
      result.append(this.ruleTranslationDef(r, _plus, asm));
    }
    result.append(this.ruleTranslationDef(r, r.getName(), asm));
    return result.toString();
  }

  /**
   * Method to translate rule body definition in Java.
   */
  public String ruleTranslationDef(final RuleDeclaration r, final String methodName, final Asm asm) {
    Integer _arity = r.getArity();
    boolean _equals = ((_arity).intValue() == 0);
    if (_equals) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("@Override");
      _builder.newLine();
      _builder.append("void ");
      _builder.append(methodName);
      _builder.append("(){");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      Rule _ruleBody = r.getRuleBody();
      String _visit = this.createRuleToJava(asm, false, this.options).visit(((Rule) _ruleBody));
      _builder.append(_visit, "\t");
      _builder.newLineIfNotEmpty();
      _builder.append("}");
      _builder.newLine();
      _builder.newLine();
      return _builder.toString();
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("@Override");
      _builder_1.newLine();
      _builder_1.append("void ");
      _builder_1.append(methodName);
      _builder_1.append(" (");
      String _adaptRuleParam = new Util().adaptRuleParam(r.getVariable(), asm);
      _builder_1.append(_adaptRuleParam);
      _builder_1.append("){");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("\t");
      String _visit_1 = this.createRuleToJava(asm, false, this.options).visit(r.getRuleBody());
      _builder_1.append(_visit_1, "\t");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("}");
      _builder_1.newLine();
      _builder_1.newLine();
      return _builder_1.toString();
    }
  }

  public String updateSet(final Asm asm) {
    StringBuffer updateset = new StringBuffer();
    EList<Function> _function = asm.getHeaderSection().getSignature().getFunction();
    for (final Function cf : _function) {
      if (((cf instanceof ControlledFunction) && (cf.getDomain() != null))) {
        StringConcatenation _builder = new StringConcatenation();
        String _name = cf.getName();
        _builder.append(_name);
        _builder.append(".update();");
        _builder.newLineIfNotEmpty();
        updateset.append(_builder);
      } else {
        if (((cf instanceof ControlledFunction) && (cf.getDomain() == null))) {
          StringConcatenation _builder_1 = new StringConcatenation();
          String _name_1 = cf.getName();
          _builder_1.append(_name_1);
          _builder_1.append(".update();");
          _builder_1.newLineIfNotEmpty();
          updateset.append(_builder_1);
        }
      }
    }
    return updateset.toString();
  }
}
