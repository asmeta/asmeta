package org.asmeta.asm2code.main;

import asmeta.definitions.Function;
import asmeta.definitions.RuleDeclaration;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.Domain;
import asmeta.structure.Asm;
import asmeta.structure.ImportClause;
import asmeta.transitionrules.basictransitionrules.Rule;
import com.google.common.base.Objects;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import org.asmeta.asm2code.DomainContainerToH;
import org.asmeta.asm2code.DomainToH;
import org.asmeta.asm2code.FunctionToH;
import org.asmeta.asm2code.ImportToH;
import org.asmeta.asm2code.SeqRuleCollector;
import org.asmeta.asm2code.Util;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function0;

/**
 * Generates .h ASM file
 */
@SuppressWarnings("all")
public class HeaderGenerator extends AsmToCGenerator {
  public static String Ext = ".h";
  
  private static final String copyright = new Function0<String>() {
    @Override
    public String apply() {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("/* this file is under copyright*/");
      _builder.newLine();
      return _builder.toString();
    }
  }.apply();
  
  private static final Logger LOGGER = Logger.getLogger(HeaderGenerator.class.getName());
  
  private List<Rule> seqCalledRules;
  
  public HeaderGenerator() {
    super();
  }
  
  public HeaderGenerator(final TranslatorOptions options) {
    super(options);
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
    String _xifexpression = null;
    boolean _equals = Objects.equal(this.options.compilerType, TranslatorOptions.CompilerType.ArduinoCompiler);
    if (_equals) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("#define ARDUINOCOMPILER");
      _builder.newLine();
      _builder.append("#include <Arduino.h>");
      _builder.newLine();
      _builder.append("// The following two libs have to be installed into your Arduino Sketchbook");
      _builder.newLine();
      _builder.append("#include <ArduinoSTL.h>");
      _builder.newLine();
      _builder.append("#include <boost_1_51_0.h>");
      _builder.newLine();
      _builder.append("#include <string.h>\t\t\t\t");
      _builder.newLine();
      _builder.append("#include <iostream> ");
      _builder.newLine();
      _builder.append("#include <vector> ");
      _builder.newLine();
      _builder.append("#include <set>");
      _builder.newLine();
      _builder.append("#include <map>");
      _builder.newLine();
      _builder.append("#include <list>");
      _builder.newLine();
      _builder.append("#include <boost/tuple/tuple.hpp>");
      _builder.newLine();
      _builder.append("#include <LiquidCrystal.h>");
      _builder.newLine();
      _builder.append("#include <LiquidCrystal_I2C.h>");
      _builder.newLine();
      _builder.append("#include <DS3231.h>");
      _builder.newLine();
      _builder.append("using namespace std;");
      _builder.newLine();
      _builder.append("/*Arduino.h uses WString instead... */");
      _builder.newLine();
      _xifexpression = _builder.toString();
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("/*Arduino.h uses WString instead... */");
      _builder_1.newLine();
      _builder_1.append("#include <string.h>\t\t\t\t");
      _builder_1.newLine();
      _builder_1.append("#include <iostream> ");
      _builder_1.newLine();
      _builder_1.append("#include <vector> ");
      _builder_1.newLine();
      _builder_1.append("#include <set>");
      _builder_1.newLine();
      _builder_1.append("#include <map>");
      _builder_1.newLine();
      _builder_1.append("#include <list>");
      _builder_1.newLine();
      _builder_1.append("//Andrea Belotti");
      _builder_1.newLine();
      _builder_1.append("#include <chrono>");
      _builder_1.newLine();
      _builder_1.append("//#include <tuple>");
      _builder_1.newLine();
      _builder_1.append("//#include <bits/stl_tree.h>");
      _builder_1.newLine();
      _builder_1.newLine();
      _builder_1.append("using namespace std;");
      _builder_1.newLine();
      _builder_1.newLine();
      _builder_1.append("//typedef std::string String;");
      _xifexpression = _builder_1.toString();
    }
    final String platformDependentHeaders = _xifexpression;
    StringConcatenation _builder_2 = new StringConcatenation();
    _builder_2.append(HeaderGenerator.copyright);
    _builder_2.newLineIfNotEmpty();
    _builder_2.append("\t");
    _builder_2.append("// ");
    _builder_2.append(asmName, "\t");
    _builder_2.append(".h automatically generated from ASMETA2CODE");
    _builder_2.newLineIfNotEmpty();
    _builder_2.append("\t");
    _builder_2.append("#ifndef ");
    String _upperCase = asmName.toUpperCase();
    _builder_2.append(_upperCase, "\t");
    _builder_2.append("_H");
    _builder_2.newLineIfNotEmpty();
    _builder_2.append("\t");
    _builder_2.append("#define ");
    String _upperCase_1 = asmName.toUpperCase();
    _builder_2.append(_upperCase_1, "\t");
    _builder_2.append("_H");
    _builder_2.newLineIfNotEmpty();
    _builder_2.append("\t");
    _builder_2.newLine();
    _builder_2.append("\t");
    _builder_2.append(platformDependentHeaders, "\t");
    _builder_2.newLineIfNotEmpty();
    _builder_2.append("\t");
    _builder_2.append("#define ANY String");
    _builder_2.newLine();
    _builder_2.append("\t");
    _builder_2.newLine();
    _builder_2.append("\t");
    _builder_2.newLine();
    _builder_2.append("\t");
    String _includeLibrary = this.includeLibrary(asm);
    _builder_2.append(_includeLibrary, "\t");
    _builder_2.newLineIfNotEmpty();
    _builder_2.append("\t");
    _builder_2.newLine();
    _builder_2.append("\t");
    _builder_2.newLine();
    _builder_2.append("/* DOMAIN DEFINITIONS */");
    _builder_2.newLine();
    _builder_2.append("namespace ");
    _builder_2.append(asmName);
    _builder_2.append("namespace{");
    _builder_2.newLineIfNotEmpty();
    _builder_2.append("\t");
    String _domainSignature = this.domainSignature(asm);
    _builder_2.append(_domainSignature, "\t");
    _builder_2.append(" //devo togliere questo in Timer perché non serve");
    _builder_2.newLineIfNotEmpty();
    _builder_2.append("\t");
    _builder_2.append("}");
    _builder_2.newLine();
    _builder_2.newLine();
    _builder_2.append("\t");
    _builder_2.newLine();
    _builder_2.append("\t");
    _builder_2.append("using namespace ");
    _builder_2.append(asmName, "\t");
    _builder_2.append("namespace;");
    _builder_2.newLineIfNotEmpty();
    _builder_2.append("\t");
    _builder_2.newLine();
    _builder_2.append("\t");
    String _abstractClassDef = this.abstractClassDef(asm);
    _builder_2.append(_abstractClassDef, "\t");
    _builder_2.append(" //devo togliere questo in Timer perché non serve");
    _builder_2.newLineIfNotEmpty();
    _builder_2.append("\t");
    _builder_2.newLine();
    _builder_2.append("\t");
    _builder_2.append("class ");
    _builder_2.append(asmName, "\t");
    _builder_2.append(" ");
    String _addExtension = this.addExtension(asm);
    _builder_2.append(_addExtension, "\t");
    _builder_2.append("{");
    _builder_2.newLineIfNotEmpty();
    _builder_2.append("\t  ");
    _builder_2.newLine();
    _builder_2.append("\t");
    _builder_2.append("/* DOMAIN CONTAINERS */");
    _builder_2.newLine();
    _builder_2.append("\t");
    String _domainContainer = this.domainContainer(asm);
    _builder_2.append(_domainContainer, "\t");
    _builder_2.newLineIfNotEmpty();
    _builder_2.append("\t");
    _builder_2.append("public:");
    _builder_2.newLine();
    _builder_2.append("\t");
    _builder_2.append("/* FUNCTIONS */");
    _builder_2.newLine();
    _builder_2.append("\t");
    String _functionSignature = this.functionSignature(asm);
    _builder_2.append(_functionSignature, "\t");
    _builder_2.newLineIfNotEmpty();
    _builder_2.append("\t");
    _builder_2.append("/* RULE DEFINITION */");
    _builder_2.newLine();
    _builder_2.append("\t");
    String _ruleDefinition = this.ruleDefinition(asm);
    _builder_2.append(_ruleDefinition, "\t");
    _builder_2.newLineIfNotEmpty();
    _builder_2.append("\t");
    _builder_2.newLine();
    _builder_2.append("\t");
    _builder_2.append(asmName, "\t");
    _builder_2.append("();");
    _builder_2.newLineIfNotEmpty();
    _builder_2.append("\t");
    _builder_2.newLine();
    _builder_2.append("\t");
    _builder_2.append("void initControlledWithMonitored();");
    _builder_2.newLine();
    _builder_2.append("\t");
    _builder_2.newLine();
    _builder_2.append("\t");
    _builder_2.append("void getInputs();");
    _builder_2.newLine();
    _builder_2.append("\t");
    _builder_2.newLine();
    _builder_2.append("\t");
    _builder_2.append("void setOutputs(); ");
    _builder_2.newLine();
    _builder_2.append("\t");
    _builder_2.newLine();
    _builder_2.append("\t");
    _builder_2.append("void fireUpdateSet();");
    _builder_2.newLine();
    _builder_2.newLine();
    _builder_2.append("\t");
    _builder_2.append("};");
    _builder_2.newLine();
    _builder_2.append("\t");
    _builder_2.append("#endif");
    _builder_2.newLine();
    return _builder_2.toString();
  }
  
  /**
   * def includeLibrary(Asm asm) {
   * 	var String sb = new String("")
   * 	for (def : asm.bodySection.domainDefinition) {
   * 		// TODO : change IncludeLibraries.xtend
   * 		var toappend = new IncludeLibraries(asm).visit(def)
   * 		if (toappend !== null)
   * 			sb += toappend
   * 	}
   * 	// ADD also the other definitions
   * 	sb = sb.replace("null", "")
   * 	var Set<String> includeset = new HashSet<String>(sb.toString.split("\n")) // eliminate duplicates
   * 	return includeset.reduce[i1, i2|i1 + "\n" + i2]
   * }
   */
  public String includeLibrary(final Asm asm) {
    StringBuffer sb = new StringBuffer();
    EList<ImportClause> _importClause = asm.getHeaderSection().getImportClause();
    for (final ImportClause i : _importClause) {
      if ((i != null)) {
        String s = new ImportToH(asm).visit(i);
        if ((((!s.contains("StandardLibrary")) && (!s.contains("CTLlibrary"))) && (!s.contains("LTLlibrary")))) {
          boolean _notEquals = (!Objects.equal(this.options.compilerType, TranslatorOptions.CompilerType.ArduinoCompiler));
          if (_notEquals) {
            String _visit = new ImportToH(asm).visit(i);
            String _plus = ("#include \"" + _visit);
            String _plus_1 = (_plus + ".h\" \n");
            sb.append(_plus_1);
          } else {
            String[] buffer = new ImportToH(asm).visit(i).split("/");
            final String[] _converted_buffer = (String[])buffer;
            int _size = ((List<String>)Conversions.doWrapArray(_converted_buffer)).size();
            int _minus = (_size - 1);
            String _get = buffer[_minus];
            String _plus_2 = ("#include \"" + _get);
            String _plus_3 = (_plus_2 + ".h\" \n");
            sb.append(_plus_3);
          }
        }
      }
    }
    return sb.toString();
  }
  
  public String addExtension(final Asm asm) {
    StringBuffer sb = new StringBuffer();
    boolean isFirst = true;
    EList<ImportClause> _importClause = asm.getHeaderSection().getImportClause();
    for (final ImportClause i : _importClause) {
      if ((i != null)) {
        String s = new ImportToH(asm).visit(i);
        if ((((!s.contains("StandardLibrary")) && (!s.contains("CTLlibrary"))) && (!s.contains("LTLlibrary")))) {
          String[] splitted = s.split("/");
          int _length = splitted.length;
          int _minus = (_length - 1);
          String s1 = splitted[_minus];
          if (isFirst) {
            isFirst = false;
            sb.append((": public virtual " + s1));
          } else {
            sb.append((" , public virtual " + s1));
          }
        }
      }
    }
    return sb.toString();
  }
  
  /**
   * DONE
   */
  public String domainSignature(final Asm asm) {
    StringBuffer sb = new StringBuffer();
    EList<Domain> _domain = asm.getHeaderSection().getSignature().getDomain();
    for (final Domain dd : _domain) {
      if ((dd instanceof AbstractTd)) {
        String _visit = new DomainToH(asm).visit(((AbstractTd)dd));
        String _plus = ("class " + _visit);
        String _plus_1 = (_plus + ";\n");
        sb.append(_plus_1);
      } else {
        String _visit_1 = new DomainToH(asm).visit(dd);
        String _plus_2 = (_visit_1 + "\n");
        sb.append(_plus_2);
      }
    }
    return sb.toString();
  }
  
  public String abstractClassDef(final Asm asm) {
    StringBuffer sb = new StringBuffer();
    EList<Domain> _domain = asm.getHeaderSection().getSignature().getDomain();
    for (final Domain dd : _domain) {
      if ((dd instanceof AbstractTd)) {
        String _name = asm.getName();
        String _plus = ("class " + _name);
        String _plus_1 = (_plus + "namespace::");
        String _visit = new DomainToH(asm).visit(((AbstractTd)dd));
        String _plus_2 = (_plus_1 + _visit);
        String _plus_3 = (_plus_2 + "{\n");
        String _defineElems = this.defineElems(((AbstractTd)dd));
        String _plus_4 = (_plus_3 + _defineElems);
        String _plus_5 = (_plus_4 + 
          "};\n");
        sb.append(_plus_5);
      }
    }
    return sb.toString();
  }
  
  /**
   * def domainSignature(Asm asm) {
   * 	var sb = new StringBuffer;
   * 	for (dd : asm.headerSection.signature.domain) {
   * 		if (dd instanceof AbstractTd)
   * 			sb.append("class " + new DomainToH(asm).visit(dd) + "{\n" + defineElems(dd) +"};\n")
   * 		else
   * 			sb.append(new DomainToH(asm).visit(dd) + "\n")
   * 	}
   * 	return sb.toString
   * }
   */
  public String defineElems(final AbstractTd td) {
    StringBuffer sb = new StringBuffer();
    sb.append("public:\n");
    String _name = td.getName();
    String _plus = ((("static " + this.options.stdNamespacePrefix) + "set<") + _name);
    String _plus_1 = (_plus + "*> elems;\n");
    sb.append(_plus_1);
    String _name_1 = td.getName();
    String _plus_2 = (_name_1 + "(){elems.insert(this);}\n");
    sb.append(_plus_2);
    return sb.toString();
  }
  
  public String domainContainer(final Asm asm) {
    StringBuffer sb = new StringBuffer();
    EList<Domain> _domain = asm.getHeaderSection().getSignature().getDomain();
    for (final Domain dd : _domain) {
      if ((!(dd instanceof AbstractTd))) {
        String _visit = new DomainContainerToH(asm).visit(dd);
        String _plus = (_visit + "\n");
        sb.append(_plus);
      }
    }
    return sb.toString();
  }
  
  /**
   * DONE
   */
  public String functionSignature(final Asm asm) {
    StringBuffer sb = new StringBuffer();
    EList<Function> _function = asm.getHeaderSection().getSignature().getFunction();
    for (final Function fd : _function) {
      String _visit = new FunctionToH(asm, this.options).visit(fd);
      String _plus = (_visit + "\n");
      sb.append(_plus);
    }
    return sb.toString();
  }
  
  /**
   * DONE
   */
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
      result.append(this.foo(r, _plus, asm));
    }
    result.append(this.foo(r, r.getName(), asm));
    return result.toString();
  }
  
  protected String foo(final RuleDeclaration r, final String methodName, final Asm asm) {
    Integer _arity = r.getArity();
    boolean _equals = ((_arity).intValue() == 0);
    if (_equals) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("void ");
      _builder.append(methodName);
      _builder.append("();");
      _builder.newLineIfNotEmpty();
      return _builder.toString();
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("void ");
      _builder_1.append(methodName);
      _builder_1.append(" (");
      String _replaceAll = new Util().adaptRuleParam(r.getVariable(), asm).replaceAll("\\$", "_");
      _builder_1.append(_replaceAll);
      _builder_1.append(");");
      _builder_1.newLineIfNotEmpty();
      return _builder_1.toString();
    }
  }
  
  @Override
  public void doGenerate(final Resource input, final IFileSystemAccess fsa) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
}
