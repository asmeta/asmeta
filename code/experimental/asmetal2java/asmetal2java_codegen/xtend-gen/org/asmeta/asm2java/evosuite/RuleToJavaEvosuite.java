package org.asmeta.asm2java.evosuite;

import asmeta.structure.Asm;
import asmeta.transitionrules.basictransitionrules.BlockRule;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.ExtendRule;
import asmeta.transitionrules.basictransitionrules.LetRule;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.derivedtransitionrules.CaseRule;
import asmeta.transitionrules.derivedtransitionrules.IterativeWhileRule;
import asmeta.transitionrules.turbotransitionrules.SeqRule;
import org.asmeta.asm2java.DomainToJavaSigDef;
import org.asmeta.asm2java.RuleToJava;
import org.asmeta.asm2java.TermToJava;
import org.asmeta.asm2java.main.TranslatorOptions;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class RuleToJavaEvosuite extends RuleToJava {
  private RulesAdder rules;

  public RuleToJavaEvosuite(final Asm resource, final boolean seqBlock, final TranslatorOptions options, final RulesAdder rules) {
    super(resource, seqBlock, options);
    this.rules = rules;
  }

  @Override
  public String visit(final BlockRule object) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("//{ //par");
    _builder.newLine();
    _builder.append("\t");
    String _printRules = new RuleToJavaEvosuite(this.res, false, this.options, this.rules).printRules(object.getRules(), false);
    _builder.append(_printRules, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("//} //endpar");
    return _builder.toString();
  }

  @Override
  public String visit(final SeqRule object) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("//{ //seq");
    _builder.newLine();
    _builder.append("\t");
    String _printRules = new RuleToJavaEvosuite(this.res, true, this.options, this.rules).printRules(object.getRules(), true);
    _builder.append(_printRules, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("//} //endseq");
    _builder.newLine();
    return _builder.toString();
  }

  @Override
  public String visit(final ConditionalRule object) {
    Rule _elseRule = object.getElseRule();
    boolean _tripleEquals = (_elseRule == null);
    if (_tripleEquals) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("if (Boolean.TRUE.equals(");
      String _visit = new TermToJava(this.res).visit(object.getGuard());
      _builder.append(_visit);
      _builder.append(")){ ");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      String _visit_1 = new RuleToJavaEvosuite(this.res, this.seqBlock, this.options, this.rules).visit(object.getThenRule());
      _builder.append(_visit_1, "\t");
      _builder.newLineIfNotEmpty();
      _builder.append("}");
      _builder.newLine();
      return _builder.toString();
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("if (Boolean.TRUE.equals(");
      String _visit_2 = new TermToJava(this.res).visit(object.getGuard());
      _builder_1.append(_visit_2);
      _builder_1.append(")){ ");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("\t");
      String _visit_3 = new RuleToJavaEvosuite(this.res, this.seqBlock, this.options, this.rules).visit(object.getThenRule());
      _builder_1.append(_visit_3, "\t");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("} else {");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      String _visit_4 = new RuleToJavaEvosuite(this.res, this.seqBlock, this.options, this.rules).visit(object.getElseRule());
      _builder_1.append(_visit_4, "\t\t");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("}");
      _builder_1.newLine();
      return _builder_1.toString();
    }
  }

  @Override
  public String visit(final CaseRule object) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; (i < object.getCaseBranches().size()); i++) {
      if ((i == 0)) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("if(");
        String _compareTerms = this.compareTerms(object.getTerm(), object.getCaseTerm().get(i));
        _builder.append(_compareTerms);
        _builder.append("){");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        String _visit = new RuleToJavaEvosuite(this.res, this.seqBlock, this.options, this.rules).visit(object.getCaseBranches().get(i));
        _builder.append(_visit, "\t");
        _builder.newLineIfNotEmpty();
        _builder.append("}");
        sb.append(_builder);
      } else {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("else if(");
        String _compareTerms_1 = this.compareTerms(object.getTerm(), object.getCaseTerm().get(i));
        _builder_1.append(_compareTerms_1);
        _builder_1.append("){");
        _builder_1.newLineIfNotEmpty();
        _builder_1.append("\t");
        String _visit_1 = new RuleToJavaEvosuite(this.res, this.seqBlock, this.options, this.rules).visit(object.getCaseBranches().get(i));
        _builder_1.append(_visit_1, "\t");
        _builder_1.newLineIfNotEmpty();
        _builder_1.append("}");
        sb.append(_builder_1);
      }
    }
    Rule _otherwiseBranch = object.getOtherwiseBranch();
    boolean _tripleNotEquals = (_otherwiseBranch != null);
    if (_tripleNotEquals) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("else{ ");
      _builder.newLine();
      _builder.append(" \t");
      String _visit = new RuleToJavaEvosuite(this.res, this.seqBlock, this.options, this.rules).visit(object.getOtherwiseBranch());
      _builder.append(_visit, " \t");
      _builder.newLineIfNotEmpty();
      _builder.append("}");
      _builder.newLine();
      sb.append(_builder);
    }
    return sb.toString();
  }

  @Override
  public String visit(final LetRule object) {
    StringBuffer let = new StringBuffer();
    let.append("{\n");
    for (int i = 0; (i < object.getVariable().size()); i++) {
      {
        object.getVariable().get(i).getDomain().getName();
        StringConcatenation _builder = new StringConcatenation();
        String _name = object.getVariable().get(i).getDomain().getName();
        String _plus = (_name + " ");
        String _visit = new TermToJava(this.res).visit(object.getVariable().get(i));
        String _plus_1 = (_plus + _visit);
        _builder.append(_plus_1);
        _builder.append(" = ");
        String _visit_1 = new TermToJava(this.res).visit(object.getInitExpression().get(i));
        _builder.append(_visit_1);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        let.append(_builder);
      }
    }
    StringConcatenation _builder = new StringConcatenation();
    String _visit = new RuleToJavaEvosuite(this.res, this.seqBlock, this.options, this.rules).visit(object.getInRule());
    _builder.append(_visit);
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    let.append(_builder);
    return let.toString();
  }

  @Override
  public String visit(final IterativeWhileRule object) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("while (");
    String _visit = new TermToJava(this.res, false).visit(object.getGuard());
    _builder.append(_visit);
    _builder.append("){");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    String _visit_1 = new RuleToJavaEvosuite(this.res, true, this.options, this.rules).visit(object.getRule());
    _builder.append(_visit_1, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    return _builder.toString();
  }

  @Override
  public String visit(final ExtendRule rule) {
    StringBuffer string = new StringBuffer();
    for (int i = 0; (i < rule.getBoundVar().size()); i++) {
      String _visit = new DomainToJavaSigDef(this.res).visit(rule.getExtendedDomain());
      String _plus = (_visit + " ");
      String _visit_1 = new TermToJava(this.res).visit(rule.getBoundVar().get(i));
      String _plus_1 = (_plus + _visit_1);
      String _plus_2 = (_plus_1 + " = new ");
      String _visit_2 = new DomainToJavaSigDef(this.res).visit(rule.getExtendedDomain());
      String _plus_3 = (_plus_2 + _visit_2);
      String _plus_4 = (_plus_3 + "();\n");
      string.append(_plus_4);
    }
    String _string = string.toString();
    String _visit = new RuleToJavaEvosuite(this.res, this.seqBlock, this.options, this.rules).visit(rule.getDoRule());
    return (_string + _visit);
  }
}
