package org.asmeta.asm2java.generator;

import asmeta.definitions.RuleDeclaration;
import asmeta.structure.Asm;
import asmeta.transitionrules.basictransitionrules.Rule;
import java.util.List;
import org.asmeta.asm2java.Util;
import org.asmeta.asm2java.evosuite.JavaRule;
import org.asmeta.asm2java.evosuite.JavaRuleImpl;
import org.asmeta.asm2java.evosuite.RuleToJavaEvosuite;
import org.asmeta.asm2java.evosuite.RulesAdder;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class JavaTestGenerator extends JavaGenerator {
  private RulesAdder rules;

  public JavaTestGenerator(final RulesAdder rules) {
    super();
    this.rules = rules;
  }

  @Override
  public String foo2(final RuleDeclaration r, final String methodName, final Asm asm) {
    JavaRuleImpl rule = new JavaRuleImpl(methodName);
    StringBuffer sb = new StringBuffer();
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
