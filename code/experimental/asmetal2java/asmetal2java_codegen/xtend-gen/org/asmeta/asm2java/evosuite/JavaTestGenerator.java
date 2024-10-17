package org.asmeta.asm2java.evosuite;

import asmeta.definitions.RuleDeclaration;
import asmeta.structure.Asm;
import asmeta.transitionrules.basictransitionrules.Rule;
import org.asmeta.asm2java.Util;
import org.asmeta.asm2java.main.JavaGenerator;
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
      String _visit = new RuleToJavaEvosuite(asm, false, this.options, this.rules).visit(((Rule) _ruleBody));
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
      String _visit_1 = new RuleToJavaEvosuite(asm, false, this.options, this.rules).visit(r.getRuleBody());
      _builder_1.append(_visit_1, "\t");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("}");
      _builder_1.newLine();
      _builder_1.newLine();
      return _builder_1.toString();
    }
  }
}
