package org.asmeta.asm2code.main;

import asmeta.AsmCollection;
import asmeta.definitions.ControlledFunction;
import asmeta.definitions.Function;
import asmeta.definitions.RuleDeclaration;
import asmeta.definitions.StaticFunction;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumTd;
import asmeta.structure.Asm;
import asmeta.structure.DomainDefinition;
import asmeta.structure.DomainInitialization;
import asmeta.structure.FunctionDefinition;
import asmeta.structure.FunctionInitialization;
import asmeta.structure.ImportClause;
import asmeta.terms.basicterms.SetTerm;
import asmeta.terms.basicterms.Term;
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;
import asmeta.transitionrules.basictransitionrules.Rule;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.asmeta.asm2code.DomainToCpp;
import org.asmeta.asm2code.DomainToH;
import org.asmeta.asm2code.FindMonitoredInControlledFunct;
import org.asmeta.asm2code.FunctionToCpp;
import org.asmeta.asm2code.RuleToCpp;
import org.asmeta.asm2code.SeqRuleCollector;
import org.asmeta.asm2code.Util;
import org.asmeta.asm2code.formatter.Formatter;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;

/**
 * Generates .cpp ASM file
 */
@SuppressWarnings("all")
public class CppGenerator extends AsmToCGenerator {
  public static String Ext = ".cpp";

  private String initConrolledMonitored;

  public CppGenerator() {
    super();
  }

  public CppGenerator(final TranslatorOptions options) {
    super(options);
  }

  private List<Rule> seqCalledRules;

  public Path generate(final AsmCollection model, final String path) {
    try {
      Path _xblockexpression = null;
      {
        if ((this.options == null)) {
          throw new Exception("TranslationOptions not inizialized");
        }
        String compiled = this.compileAsm(model);
        if (this.options.formatter) {
          compiled = Formatter.formatCode(compiled);
        }
        _xblockexpression = Files.write(Paths.get(path), compiled.getBytes());
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  public String compileAsm(final AsmCollection asmCol) {
    Asm asm = asmCol.getMain();
    if (this.options.optimizeSeqMacroRule) {
      ArrayList<Rule> _arrayList = new ArrayList<Rule>();
      this.seqCalledRules = _arrayList;
      EList<RuleDeclaration> _ruleDeclaration = asm.getBodySection().getRuleDeclaration();
      for (final RuleDeclaration r : _ruleDeclaration) {
        this.seqCalledRules.addAll(new SeqRuleCollector(false).visit(r));
      }
    }
    final String asmName = asm.getName();
    this.functionSignature(asm);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("\t");
    _builder.append("/* ");
    _builder.append(asmName, "\t");
    _builder.append(".cpp automatically generated from ASM2CODE */");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("#include \"");
    _builder.append(asmName, "\t");
    _builder.append(".h\"");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("using namespace ");
    _builder.append(asmName, "\t");
    _builder.append("namespace;");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* Conversion of ASM rules in C++ methods */");
    _builder.newLine();
    _builder.append("\t");
    String _ruleDefinitions = this.ruleDefinitions(asm);
    _builder.append(_ruleDefinitions, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* Static function definition */");
    _builder.newLine();
    _builder.append("\t");
    String _functionDefinition = this.functionDefinition(asm);
    _builder.append(_functionDefinition, "\t");
    _builder.newLineIfNotEmpty();
    {
      boolean _equals = Objects.equals(this.options.compilerType, TranslatorOptions.CompilerType.ArduinoCompiler);
      if (_equals) {
        _builder.append("\t");
        _builder.newLine();
        _builder.append("\t");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("/* Function and domain initialization */");
        _builder.newLine();
        _builder.append("\t");
        _builder.append(asmName, "\t");
        _builder.append("::");
        _builder.append(asmName, "\t");
        _builder.append("()");
        _builder.append("{");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        String _initialStaticDomainArduino = this.initialStaticDomainArduino(asm);
        _builder.append(_initialStaticDomainArduino, "\t");
        _builder.append(" //MOD");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        String _initialDynamicDomainDefinition = this.initialDynamicDomainDefinition(asm);
        _builder.append(_initialDynamicDomainDefinition, "\t");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("\t");
        _builder.append("/* Function and domain initialization */");
        _builder.newLine();
        _builder.append("\t");
        _builder.append(asmName, "\t");
        _builder.append("::");
        _builder.append(asmName, "\t");
        _builder.append("()");
        String _initialStaticDomain = this.initialStaticDomain(asm);
        _builder.append(_initialStaticDomain, "\t");
        _builder.append("{");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        String _initialDynamicDomainDefinition_1 = this.initialDynamicDomainDefinition(asm);
        _builder.append(_initialDynamicDomainDefinition_1, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("/* Init static functions Abstract domain */");
    _builder.newLine();
    _builder.append("\t");
    String _functionAbstractDom = this.functionAbstractDom(asm);
    _builder.append(_functionAbstractDom, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("/* Function initialization */");
    _builder.newLine();
    _builder.append("\t");
    String _functionInitialization = this.functionInitialization(asm);
    _builder.append(_functionInitialization, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* initialize controlled functions that contains monitored functions in the init term */");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("void ");
    _builder.append(asmName, "\t");
    _builder.append("::initControlledWithMonitored(){");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append(this.initConrolledMonitored, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* Apply the update set */");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("void ");
    _builder.append(asmName, "\t");
    _builder.append("::fireUpdateSet(){");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    String _updateSet = this.updateSet(asmCol);
    _builder.append(_updateSet, "\t\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* init static functions and elements of abstract domains */");
    _builder.newLine();
    _builder.append("\t");
    String _initStatic = this.initStatic(asm);
    _builder.append(_initStatic, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    return _builder.toString();
  }

  @Override
  public String compileAsm(final Asm asm) {
    if (this.options.optimizeSeqMacroRule) {
      ArrayList<Rule> _arrayList = new ArrayList<Rule>();
      this.seqCalledRules = _arrayList;
      EList<RuleDeclaration> _ruleDeclaration = asm.getBodySection().getRuleDeclaration();
      for (final RuleDeclaration r : _ruleDeclaration) {
        this.seqCalledRules.addAll(new SeqRuleCollector(false).visit(r));
      }
    }
    final String asmName = asm.getName();
    this.functionSignature(asm);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("\t");
    _builder.append("/* ");
    _builder.append(asmName, "\t");
    _builder.append(".cpp automatically generated from ASM2CODE */");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("#include \"");
    _builder.append(asmName, "\t");
    _builder.append(".h\"");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("using namespace ");
    _builder.append(asmName, "\t");
    _builder.append("namespace;");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* Conversion of ASM rules in C++ methods */");
    _builder.newLine();
    _builder.append("\t");
    String _ruleDefinitions = this.ruleDefinitions(asm);
    _builder.append(_ruleDefinitions, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* Static function definition */");
    _builder.newLine();
    _builder.append("\t");
    String _functionDefinition = this.functionDefinition(asm);
    _builder.append(_functionDefinition, "\t");
    _builder.newLineIfNotEmpty();
    {
      boolean _equals = Objects.equals(this.options.compilerType, TranslatorOptions.CompilerType.ArduinoCompiler);
      if (_equals) {
        _builder.append("\t");
        _builder.newLine();
        _builder.append("\t");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("/* Function and domain initialization */");
        _builder.newLine();
        _builder.append("\t");
        _builder.append(asmName, "\t");
        _builder.append("::");
        _builder.append(asmName, "\t");
        _builder.append("()");
        _builder.append("{");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        String _initialStaticDomainArduino = this.initialStaticDomainArduino(asm);
        _builder.append(_initialStaticDomainArduino, "\t");
        _builder.append(" //MOD");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        String _initialDynamicDomainDefinition = this.initialDynamicDomainDefinition(asm);
        _builder.append(_initialDynamicDomainDefinition, "\t");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("\t");
        _builder.append("/* Function and domain initialization */");
        _builder.newLine();
        _builder.append("\t");
        _builder.append(asmName, "\t");
        _builder.append("::");
        _builder.append(asmName, "\t");
        _builder.append("()");
        String _initialStaticDomain = this.initialStaticDomain(asm);
        _builder.append(_initialStaticDomain, "\t");
        _builder.append("{");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        String _initialDynamicDomainDefinition_1 = this.initialDynamicDomainDefinition(asm);
        _builder.append(_initialDynamicDomainDefinition_1, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("/* Init static functions Abstract domain */");
    _builder.newLine();
    _builder.append("\t");
    String _functionAbstractDom = this.functionAbstractDom(asm);
    _builder.append(_functionAbstractDom, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("/* Function initialization */");
    _builder.newLine();
    _builder.append("\t");
    String _functionInitialization = this.functionInitialization(asm);
    _builder.append(_functionInitialization, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* initialize controlled functions that contains monitored functions in the init term */");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("void ");
    _builder.append(asmName, "\t");
    _builder.append("::initControlledWithMonitored(){");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append(this.initConrolledMonitored, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* Apply the update set */");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("void ");
    _builder.append(asmName, "\t");
    _builder.append("::fireUpdateSet(){");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    String _updateSet = this.updateSet(asm);
    _builder.append(_updateSet, "\t\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* init static functions and elements of abstract domains */");
    _builder.newLine();
    _builder.append("\t");
    String _initStatic = this.initStatic(asm);
    _builder.append(_initStatic, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    return _builder.toString();
  }

  public String initStatic(final Asm asm) {
    StringBuffer sb = new StringBuffer();
    EList<Domain> _domain = asm.getHeaderSection().getSignature().getDomain();
    for (final Domain dd : _domain) {
      if ((dd instanceof AbstractTd)) {
        String domain = new DomainToH(asm).visit(((AbstractTd)dd));
        sb.append((((((this.options.stdNamespacePrefix + "set< ") + domain) + "*>") + domain) + "::elems;\n"));
      }
    }
    EList<Function> _function = asm.getHeaderSection().getSignature().getFunction();
    for (final Function fd : _function) {
      if (((fd instanceof StaticFunction) && (fd.getCodomain() instanceof AbstractTd))) {
        String _visit = new DomainToH(asm).visit(fd.getCodomain());
        String _plus = (_visit + "*");
        String _name = asm.getName();
        String _plus_1 = (_plus + _name);
        String _plus_2 = (_plus_1 + "::");
        String _name_1 = fd.getName();
        String _plus_3 = (_plus_2 + _name_1);
        String _plus_4 = (_plus_3 + ";\n");
        sb.append(_plus_4);
      }
    }
    return sb.toString();
  }

  public String functionAbstractDom(final Asm asm) {
    StringBuffer sb = new StringBuffer();
    EList<Function> _function = asm.getHeaderSection().getSignature().getFunction();
    for (final Function fd : _function) {
      if (((fd instanceof StaticFunction) && (fd.getCodomain() instanceof AbstractTd))) {
        String _name = fd.getName();
        String _plus = (_name + " = new ");
        String _visit = new DomainToH(asm).visit(fd.getCodomain());
        String _plus_1 = (_plus + _visit);
        String _plus_2 = (_plus_1 + ";\n");
        sb.append(_plus_2);
      }
    }
    return sb.toString();
  }

  public Object functionSignature(final Asm asm) {
    return null;
  }

  /**
   * TODO
   * funzioni statiche implementazione
   * regole implementazione
   * term implementazione
   */
  public String updateSet(final Asm asm) {
    StringBuffer updateset = new StringBuffer();
    EList<Function> _function = asm.getHeaderSection().getSignature().getFunction();
    for (final Function cf : _function) {
      if ((cf instanceof ControlledFunction)) {
        StringConcatenation _builder = new StringConcatenation();
        String _name = ((ControlledFunction)cf).getName();
        _builder.append(_name);
        _builder.append("[0] = ");
        String _name_1 = ((ControlledFunction)cf).getName();
        _builder.append(_name_1);
        _builder.append("[1];");
        _builder.newLineIfNotEmpty();
        updateset.append(_builder);
      }
    }
    boolean _contains = asm.getName().contains("main");
    if (_contains) {
      EList<ImportClause> _importClause = asm.getHeaderSection().getImportClause();
      for (final ImportClause imp : _importClause) {
        boolean _contains_1 = imp.getModuleName().contains("StandardLibrary");
        boolean _not = (!_contains_1);
        if (_not) {
          String[] buffer = imp.getModuleName().split("/");
          int _length = buffer.length;
          int _minus = (_length - 1);
          String name = buffer[_minus];
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append(name);
          _builder_1.append("::fireUpdateSet();");
          updateset.append(_builder_1);
        }
      }
    }
    return updateset.toString();
  }

  public String updateSet(final AsmCollection asmCol) {
    StringBuffer updateset = new StringBuffer();
    Asm asm = asmCol.getMain();
    MacroDeclaration _mainrule = asm.getMainrule();
    boolean _tripleNotEquals = (_mainrule != null);
    if (_tripleNotEquals) {
      for (final Asm asm1 : asmCol) {
        boolean _contains = asm1.getName().contains("StandardLibrary");
        boolean _not = (!_contains);
        if (_not) {
          EList<Function> _function = asm1.getHeaderSection().getSignature().getFunction();
          for (final Function cf : _function) {
            if ((cf instanceof ControlledFunction)) {
              StringConcatenation _builder = new StringConcatenation();
              String _name = ((ControlledFunction)cf).getName();
              _builder.append(_name);
              _builder.append("[0] = ");
              String _name_1 = ((ControlledFunction)cf).getName();
              _builder.append(_name_1);
              _builder.append("[1];");
              _builder.newLineIfNotEmpty();
              updateset.append(_builder);
            }
          }
        }
      }
    }
    return updateset.toString();
  }

  public String initialStaticDomainDefinition(final Asm asm) {
    StringBuffer initial = new StringBuffer();
    if (((asm.getBodySection() != null) && (asm.getBodySection().getDomainDefinition() != null))) {
      EList<DomainDefinition> _domainDefinition = asm.getBodySection().getDomainDefinition();
      for (final DomainDefinition dd : _domainDefinition) {
        {
          String n = dd.getDefinedDomain().getName();
          String _elemsSetName = Util.getElemsSetName(dd.getDefinedDomain().getName());
          String _visit = new DomainToCpp(asm).visit(dd);
          String _xifexpression = null;
          boolean _equals = Objects.equals(this.options.compilerType, TranslatorOptions.CompilerType.ArduinoCompiler);
          if (_equals) {
            _xifexpression = (("{&var" + n) + "_");
          } else {
            _xifexpression = (("{var" + n) + "_");
          }
          String _replace = _visit.replace("{", _xifexpression);
          String _xifexpression_1 = null;
          boolean _equals_1 = Objects.equals(this.options.compilerType, TranslatorOptions.CompilerType.ArduinoCompiler);
          if (_equals_1) {
            _xifexpression_1 = ((",&var" + n) + "_");
          } else {
            _xifexpression_1 = ((",var" + n) + "_");
          }
          String _replace_1 = _replace.replace(",", _xifexpression_1);
          String _plus = ("(" + _replace_1);
          String _replaceAll = (_plus + "),").replaceAll("\\s+", "");
          String _plus_1 = (_elemsSetName + _replaceAll);
          String _plus_2 = (_plus_1 + "\n");
          initial.append(_plus_2);
        }
      }
    }
    if ((((asm.getHeaderSection() != null) && (asm.getHeaderSection().getSignature() != null)) && 
      (asm.getHeaderSection().getSignature().getDomain() != null))) {
      EList<Domain> _domain = asm.getHeaderSection().getSignature().getDomain();
      for (final Domain ed : _domain) {
        if ((ed instanceof EnumTd)) {
          String n = ((EnumTd)ed).getName();
          String _elemsSetName = Util.getElemsSetName(((EnumTd)ed).getName());
          String _plus = (_elemsSetName + "(");
          String _visit = new DomainToCpp(asm).visit(((EnumTd)ed));
          String _xifexpression = null;
          boolean _equals = Objects.equals(this.options.compilerType, TranslatorOptions.CompilerType.ArduinoCompiler);
          if (_equals) {
            _xifexpression = (("{&var" + n) + "_");
          } else {
            _xifexpression = "{var_";
          }
          String _replace = _visit.replace(
            "{", _xifexpression);
          String _xifexpression_1 = null;
          boolean _equals_1 = Objects.equals(this.options.compilerType, TranslatorOptions.CompilerType.ArduinoCompiler);
          if (_equals_1) {
            _xifexpression_1 = ((",&var_" + n) + "_");
          } else {
            _xifexpression_1 = ",var_";
          }
          String _replace_1 = _replace.replace(",", _xifexpression_1);
          String _plus_1 = (_plus + _replace_1);
          String _plus_2 = (_plus_1 + "\n\t\t\t\t\t), \n");
          initial.append(_plus_2);
        }
      }
    }
    int _length = initial.toString().length();
    boolean _notEquals = (_length != 0);
    if (_notEquals) {
      String _string = initial.toString();
      int _length_1 = initial.toString().length();
      int _minus = (_length_1 - 3);
      String _substring = _string.substring(0, _minus);
      String _plus_3 = ((": \n" + "// Static domain initialization \n") + _substring);
      return (_plus_3 + "\n");
    } else {
      return "";
    }
  }

  public String initialStaticDomainArduino(final Asm asm) {
    StringBuffer initial = new StringBuffer();
    if (((asm.getBodySection() != null) && (asm.getBodySection().getDomainDefinition() != null))) {
      EList<DomainDefinition> _domainDefinition = asm.getBodySection().getDomainDefinition();
      for (final DomainDefinition dd : _domainDefinition) {
        if (((dd.getBody() instanceof SetTerm) && dd.getBody().getDomain().getName().contains("Integer"))) {
          Term _body = dd.getBody();
          SetTerm s = ((SetTerm) _body);
          String domain = s.getDomain().getName();
          InputOutput.<String>println((("is set term." + " Domain ") + domain));
          String _elemsSetName = Util.getElemsSetName(dd.getDefinedDomain().getName());
          String _plus = (_elemsSetName + ":");
          String _visitArduino = new DomainToCpp(asm).visitArduino(s);
          String _plus_1 = (_visitArduino + "; \n");
          String _plus_2 = (_plus + _plus_1);
          initial.append(_plus_2);
        } else {
          String _elemsSetName_1 = Util.getElemsSetName(dd.getDefinedDomain().getName());
          String _plus_3 = (_elemsSetName_1 + ":");
          String _visit = new DomainToCpp(asm).visit(dd);
          String _plus_4 = (_visit + "; \n");
          String _plus_5 = (_plus_3 + _plus_4);
          initial.append(_plus_5);
        }
      }
    }
    if ((((asm.getHeaderSection() != null) && (asm.getHeaderSection().getSignature() != null)) && (asm.getHeaderSection().getSignature().getDomain() != null))) {
      EList<Domain> _domain = asm.getHeaderSection().getSignature().getDomain();
      for (final Domain ed : _domain) {
        if ((ed instanceof EnumTd)) {
          String _elemsSetName_2 = Util.getElemsSetName(((EnumTd)ed).getName());
          String _plus_6 = ("\n" + _elemsSetName_2);
          String _plus_7 = (_plus_6 + ":");
          String _visit_1 = new DomainToCpp(asm).visit(((EnumTd)ed), true);
          String _plus_8 = (_plus_7 + _visit_1);
          String _plus_9 = (_plus_8 + " \n");
          initial.append(_plus_9);
        }
      }
    }
    int _length = initial.toString().length();
    boolean _notEquals = (_length != 0);
    if (_notEquals) {
      String _string = initial.toString();
      int _length_1 = initial.toString().length();
      int _minus = (_length_1 - 2);
      String _substring = _string.substring(0, _minus);
      String _plus_10 = ("//Static domain initialization \n" + _substring);
      return (_plus_10 + "\n");
    } else {
      return "";
    }
  }

  public String initialStaticDomain(final Asm asm) {
    StringBuffer initial = new StringBuffer();
    if (((asm.getBodySection() != null) && (asm.getBodySection().getDomainDefinition() != null))) {
      EList<DomainDefinition> _domainDefinition = asm.getBodySection().getDomainDefinition();
      for (final DomainDefinition dd : _domainDefinition) {
        String _elemsSetName = Util.getElemsSetName(dd.getDefinedDomain().getName());
        String _plus = (_elemsSetName + "(");
        String _visit = new DomainToCpp(asm).visit(dd);
        String _plus_1 = (_plus + _visit);
        String _plus_2 = (_plus_1 + "), \n");
        initial.append(_plus_2);
      }
    }
    if ((((asm.getHeaderSection() != null) && (asm.getHeaderSection().getSignature() != null)) && (asm.getHeaderSection().getSignature().getDomain() != null))) {
      EList<Domain> _domain = asm.getHeaderSection().getSignature().getDomain();
      for (final Domain ed : _domain) {
        if ((ed instanceof EnumTd)) {
          String _elemsSetName_1 = Util.getElemsSetName(((EnumTd)ed).getName());
          String _plus_3 = (_elemsSetName_1 + "(");
          String _visit_1 = new DomainToCpp(asm).visit(((EnumTd)ed));
          String _plus_4 = (_plus_3 + _visit_1);
          String _plus_5 = (_plus_4 + "), \n");
          initial.append(_plus_5);
        }
      }
    }
    int _length = initial.toString().length();
    boolean _notEquals = (_length != 0);
    if (_notEquals) {
      String _string = initial.toString();
      int _length_1 = initial.toString().length();
      int _minus = (_length_1 - 3);
      String _substring = _string.substring(0, _minus);
      String _plus_6 = ((": \n" + "//Static domain initialization \n") + _substring);
      return (_plus_6 + "\n");
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
          final String domaintocpp = new DomainToCpp(asm).visit(dd);
          String _elemsSetName = Util.getElemsSetName(dd.getInitializedDomain().getName());
          String _plus = (_elemsSetName + "=");
          String _plus_1 = (_plus + domaintocpp);
          String _plus_2 = (_plus_1 + ";\n");
          initial.append(_plus_2);
        }
      }
    }
    int _length = initial.length();
    boolean _notEquals = (_length != 0);
    if (_notEquals) {
      String _string = initial.toString();
      return ("// Dynamic domain initialization \n" + _string);
    } else {
      return "";
    }
  }

  public void initControlled(final Asm asm) {
    if (((asm.getDefaultInitialState() != null) && (asm.getDefaultInitialState().getFunctionInitialization() != null))) {
      EList<FunctionInitialization> _functionInitialization = asm.getDefaultInitialState().getFunctionInitialization();
      for (final FunctionInitialization fd : _functionInitialization) {
      }
    }
  }

  public String functionInitialization(final Asm asm) {
    StringBuffer initial = new StringBuffer();
    StringBuffer initialMonitored = new StringBuffer();
    boolean containsMonitored = false;
    if (((asm.getDefaultInitialState() != null) && (asm.getDefaultInitialState().getFunctionInitialization() != null))) {
      EList<FunctionInitialization> _functionInitialization = asm.getDefaultInitialState().getFunctionInitialization();
      for (final FunctionInitialization fd : _functionInitialization) {
        {
          containsMonitored = (new FindMonitoredInControlledFunct(asm).visit(fd.getBody())).booleanValue();
          if ((containsMonitored == false)) {
            StringConcatenation _builder = new StringConcatenation();
            String _visit = new FunctionToCpp(asm, this.options).visit(fd.getInitializedFunction());
            _builder.append(_visit);
            _builder.newLineIfNotEmpty();
            initial.append(_builder);
          } else {
            StringConcatenation _builder_1 = new StringConcatenation();
            String _visit_1 = new FunctionToCpp(asm, this.options).visit(fd.getInitializedFunction());
            _builder_1.append(_visit_1);
            _builder_1.newLineIfNotEmpty();
            initialMonitored.append(_builder_1);
          }
        }
      }
    }
    this.initConrolledMonitored = initialMonitored.toString().replaceAll("\\$", "_");
    return initial.toString().replaceAll("\\$", "_");
  }

  public String ruleDefinitions(final Asm e) {
    StringBuffer sb = new StringBuffer();
    EList<RuleDeclaration> _ruleDeclaration = e.getBodySection().getRuleDeclaration();
    for (final RuleDeclaration r : _ruleDeclaration) {
      sb.append(this.ruleImplementation(r, e).replaceAll("\\$", "_"));
    }
    return sb.toString();
  }

  public String functionDefinition(final Asm asm) {
    StringBuffer sb = new StringBuffer();
    if (((asm.getBodySection() != null) && (asm.getBodySection().getFunctionDefinition() != null))) {
      EList<FunctionDefinition> _functionDefinition = asm.getBodySection().getFunctionDefinition();
      for (final FunctionDefinition fd : _functionDefinition) {
        StringConcatenation _builder = new StringConcatenation();
        String _visit = new FunctionToCpp(asm).visit(fd.getDefinedFunction());
        _builder.append(_visit);
        _builder.newLineIfNotEmpty();
        sb.append(_builder);
      }
      return sb.toString().replaceAll("\\$", "_");
    }
    return "";
  }

  public String possibleValueOfStaticDomain(final Asm asm) {
    StringBuffer initial = new StringBuffer();
    if (((asm.getBodySection() != null) && (asm.getBodySection().getDomainDefinition() != null))) {
      EList<DomainDefinition> _domainDefinition = asm.getBodySection().getDomainDefinition();
      for (final DomainDefinition dd : _domainDefinition) {
        String _elemsSetName = Util.getElemsSetName(dd.getDefinedDomain().getName());
        String _plus = (_elemsSetName + "(");
        String _visit = new DomainToCpp(asm).visit(dd);
        String _plus_1 = (_plus + _visit);
        String _plus_2 = (_plus_1 + 
          ") \n");
        initial.append(_plus_2);
      }
    }
    if ((((asm.getHeaderSection() != null) && (asm.getHeaderSection().getSignature() != null)) && 
      (asm.getHeaderSection().getSignature().getDomain() != null))) {
      EList<Domain> _domain = asm.getHeaderSection().getSignature().getDomain();
      for (final Domain ed : _domain) {
        if ((ed instanceof EnumTd)) {
          String _elemsSetName_1 = Util.getElemsSetName(((EnumTd)ed).getName());
          String _plus_3 = (_elemsSetName_1 + "(");
          String _visit_1 = new DomainToCpp(asm).visit(((EnumTd)ed));
          String _plus_4 = (_plus_3 + _visit_1);
          String _plus_5 = (_plus_4 + ") \n");
          initial.append(_plus_5);
        }
      }
    }
    int _length = initial.toString().length();
    boolean _notEquals = (_length != 0);
    if (_notEquals) {
      String _string = initial.toString();
      int _length_1 = initial.toString().length();
      int _minus = (_length_1 - 3);
      String listAllDomain = _string.substring(0, _minus).replace("_elems", "");
      String[] listDomain = listAllDomain.split("\n");
      StringBuffer _stringBuffer = new StringBuffer();
      initial = _stringBuffer;
      for (final String en : listDomain) {
        {
          int indexName = en.indexOf("(");
          String name = en.substring(0, indexName);
          String enumName = en.substring(0, en.indexOf("("));
          int index = en.indexOf("{");
          while ((index < (en.indexOf("}") - 1))) {
            {
              int lastIndex = (index + 1);
              boolean _contains = en.substring((index + 1)).contains(",");
              if (_contains) {
                lastIndex = en.indexOf(",", (index + 1));
              } else {
                lastIndex = en.indexOf("}", (index + 1));
              }
              String _trim = en.substring((index + 1), lastIndex).trim();
              String _plus_6 = ((((enumName + " var") + name) + "_") + _trim);
              String _plus_7 = (_plus_6 + " = ");
              String _substring = en.substring((index + 1), lastIndex);
              String _plus_8 = (_plus_7 + _substring);
              String _plus_9 = (_plus_8 + ";\n");
              initial.append(_plus_9);
              index = lastIndex;
            }
          }
        }
      }
      return initial.toString().substring(0, initial.toString().length());
    } else {
      return "";
    }
  }

  public String ruleImplementation(final RuleDeclaration r, final Asm asm) {
    StringBuffer result = new StringBuffer();
    if (((this.seqCalledRules == null) || this.seqCalledRules.contains(r.getRuleBody()))) {
      String _name = r.getName();
      String _plus = (_name + "_seq");
      result.append(this.foo(r, _plus, asm));
    }
    result.append(this.foo(r, r.getName(), asm));
    return result.toString();
  }

  public String foo(final RuleDeclaration r, final String methodName, final Asm asm) {
    Integer _arity = r.getArity();
    boolean _equals = ((_arity).intValue() == 0);
    if (_equals) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("void ");
      String _name = asm.getName();
      _builder.append(_name);
      _builder.append("::");
      _builder.append(methodName);
      _builder.append("(){");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      Rule _ruleBody = r.getRuleBody();
      String _visit = new RuleToCpp(asm, false, this.options).visit(((Rule) _ruleBody));
      _builder.append(_visit, "\t");
      _builder.newLineIfNotEmpty();
      _builder.append("}");
      _builder.newLine();
      return _builder.toString();
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("void ");
      String _name_1 = asm.getName();
      _builder_1.append(_name_1);
      _builder_1.append("::");
      _builder_1.append(methodName);
      _builder_1.append(" (");
      String _adaptRuleParam = new Util().adaptRuleParam(r.getVariable(), asm);
      _builder_1.append(_adaptRuleParam);
      _builder_1.append("){");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("\t");
      String _visit_1 = new RuleToCpp(asm, false, this.options).visit(r.getRuleBody());
      _builder_1.append(_visit_1, "\t");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("}");
      _builder_1.newLine();
      return _builder_1.toString();
    }
  }

  @Override
  public void doGenerate(final Resource input, final IFileSystemAccess fsa) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
}
