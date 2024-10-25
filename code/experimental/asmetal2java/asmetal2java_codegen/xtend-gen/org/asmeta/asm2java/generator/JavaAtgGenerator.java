package org.asmeta.asm2java.generator;

import asmeta.definitions.RuleDeclaration;
import asmeta.structure.Asm;
import asmeta.transitionrules.basictransitionrules.Rule;
import java.util.ArrayList;
import java.util.List;
import org.asmeta.asm2java.SeqRuleCollector;
import org.asmeta.asm2java.config.TranslatorOptions;
import org.asmeta.asm2java.evosuite.AsmMethods;
import org.asmeta.asm2java.evosuite.CoverOutputs;
import org.asmeta.asm2java.evosuite.CoverRules;
import org.asmeta.asm2java.evosuite.RulesGetter;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.junit.Assert;

@SuppressWarnings("all")
public class JavaAtgGenerator extends AsmToJavaGenerator {
  private RulesGetter rules;

  public JavaAtgGenerator(final RulesGetter rules) {
    super();
    this.rules = rules;
  }

  public void compileAndWrite(final Asm asm, final String writerPath, final TranslatorOptions userOptions) {
    Assert.assertTrue(writerPath.endsWith(".java"));
    this.compileAndWrite(asm, writerPath, "JAVA", userOptions);
  }

  private List<Rule> seqCalledRules;

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
    StringBuffer sb = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
    _builder.append("// ");
    _builder.append(asmName);
    _builder.append("_ATG.java automatically generated from ASM2CODE");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("import java.util.Scanner;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/**");
    _builder.newLine();
    _builder.append("* This class is designed to simulate the behavior of an abstract state machine in an automated way.");
    _builder.newLine();
    _builder.append("*");
    _builder.newLine();
    _builder.append("* <p>");
    _builder.newLine();
    _builder.append("* It has been optimized to be used with evosuite in order to automatically generate test scenarios.");
    _builder.newLine();
    _builder.append("* </p>");
    _builder.newLine();
    _builder.append("*/");
    _builder.newLine();
    _builder.append("class ");
    _builder.append(asmName);
    _builder.append("_ATG {");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/** Instance of the asmeta specification translated into a java class.*/");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("private final ");
    _builder.append(asmName, "\t");
    _builder.append(" execution;");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/** current state. */");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("private int state;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("   ");
    _builder.append("/**");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("* Constructor of the {@code ");
    _builder.append(asmName, "    ");
    _builder.append("_ATG} class. Creates a private instance of the asm");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("* {@link ");
    _builder.append(asmName, "    ");
    _builder.append("} and sets the initial state of the state machine to 0.");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public ");
    _builder.append(asmName, "\t");
    _builder.append("_ATG(){");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("this.execution = new ");
    _builder.append(asmName, "\t\t");
    _builder.append("();");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("this.state = 0;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/** The step function allows to perform a step of the asm by incrementing the state.");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public void step(){");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("System.out.println(\"<State \"+ state +\" (controlled)>\");");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("printControlled();");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("this.execution.updateASM();");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("System.out.println(\"</State \"+ state +\" (controlled)>\");");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("System.out.println(\"\\n<Current status>\");");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("printControlled();");
    sb.append(_builder);
    boolean _coverRules = this.options.getCoverRules();
    if (_coverRules) {
      sb.append(System.lineSeparator());
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("// Cover the rules");
      _builder_1.newLine();
      _builder_1.append("coverRules();");
      sb.append(_builder_1);
      sb.append(System.lineSeparator());
    }
    boolean _coverOutputs = this.options.getCoverOutputs();
    if (_coverOutputs) {
      sb.append(System.lineSeparator());
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append("// Cover the outputs");
      _builder_2.newLine();
      _builder_2.append("coverOutputs();");
      sb.append(_builder_2);
      sb.append(System.lineSeparator());
    }
    StringBuffer _append = sb.append("\t\t\t\t\t\t");
    StringConcatenation _builder_3 = new StringConcatenation();
    _builder_3.append("state++;");
    _builder_3.newLine();
    _builder_3.append("\t\t\t\t");
    _builder_3.append("}");
    _append.append(_builder_3);
    boolean _coverRules_1 = this.options.getCoverRules();
    if (_coverRules_1) {
      sb.append(System.lineSeparator());
      StringBuffer _append_1 = sb.append("\t");
      StringConcatenation _builder_4 = new StringConcatenation();
      _builder_4.append("/* Cover the Rules */");
      _append_1.append(_builder_4);
      sb.append(System.lineSeparator());
      sb.append(System.lineSeparator());
      sb.append(System.lineSeparator());
      sb.append(CoverRules.coverRulesFunction(this.rules));
      sb.append(System.lineSeparator());
      sb.append(System.lineSeparator());
      sb.append(CoverRules.coverAllRules(this.rules));
      sb.append(System.lineSeparator());
    }
    boolean _coverOutputs_1 = this.options.getCoverOutputs();
    if (_coverOutputs_1) {
      sb.append(System.lineSeparator());
      StringBuffer _append_2 = sb.append("\t");
      StringConcatenation _builder_5 = new StringConcatenation();
      _builder_5.append("/* Cover the Outputs */");
      _append_2.append(_builder_5);
      sb.append(System.lineSeparator());
      StringBuffer _append_3 = sb.append("\t");
      StringConcatenation _builder_6 = new StringConcatenation();
      _builder_6.append("// Monitored getters");
      _append_3.append(_builder_6);
      sb.append(CoverOutputs.monitoredGetter(asm));
      sb.append(System.lineSeparator());
      StringBuffer _append_4 = sb.append("\t");
      StringConcatenation _builder_7 = new StringConcatenation();
      _builder_7.append("// Cover functions");
      _append_4.append(_builder_7);
      sb.append(System.lineSeparator());
      sb.append(System.lineSeparator());
      sb.append(CoverOutputs.coverOutputs(asm));
      sb.append(System.lineSeparator());
      sb.append(System.lineSeparator());
      sb.append(System.lineSeparator());
      sb.append(CoverOutputs.coverOutputBranches(asm));
    }
    sb.append(System.lineSeparator());
    StringConcatenation _builder_8 = new StringConcatenation();
    _builder_8.append("/* ASM Methods */");
    sb.append(_builder_8);
    sb.append(System.lineSeparator());
    sb.append(System.lineSeparator());
    StringBuffer _append_5 = sb.append("\t\t");
    StringConcatenation _builder_9 = new StringConcatenation();
    _builder_9.append("// Print controlled");
    _append_5.append(_builder_9);
    sb.append(System.lineSeparator());
    sb.append(AsmMethods.printControlled(asm));
    sb.append(System.lineSeparator());
    StringBuffer _append_6 = sb.append("\t\t");
    StringConcatenation _builder_10 = new StringConcatenation();
    _builder_10.append("// Controlled getters");
    _append_6.append(_builder_10);
    sb.append(System.lineSeparator());
    sb.append(AsmMethods.controlledGetter(asm));
    sb.append(System.lineSeparator());
    StringBuffer _append_7 = sb.append("\t\t");
    StringConcatenation _builder_11 = new StringConcatenation();
    _builder_11.append("// Monitored setters");
    _append_7.append(_builder_11);
    sb.append(System.lineSeparator());
    sb.append(AsmMethods.monitoredSetters(asm));
    sb.append(System.lineSeparator());
    StringConcatenation _builder_12 = new StringConcatenation();
    _builder_12.append("}");
    sb.append(_builder_12);
    return sb.toString();
  }
}
