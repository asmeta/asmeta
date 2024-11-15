package org.asmeta.asm2java.evosuite;

import asmeta.structure.Asm;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.derivedtransitionrules.CaseRule;
import org.asmeta.asm2java.config.TranslatorOptions;
import org.asmeta.asm2java.translator.RuleToJava;
import org.asmeta.asm2java.translator.TermToJava;
import org.eclipse.xtend2.lib.StringConcatenation;

/**
 * Redefinition of the {@link JavaRule} class by adding
 * specific methods for the Evosuite tool
 */
@SuppressWarnings("all")
public class RuleToJavaEvosuite extends RuleToJava {
  /**
   * Current java rule in exam.
   */
  private JavaRule currRule;

  public RuleToJavaEvosuite(final Asm resource, final boolean seqBlock, final TranslatorOptions options, final JavaRule currRule) {
    super(resource, seqBlock, options);
    this.currRule = currRule;
  }

  /**
   * Create an instance of the {@code RuleToJava} object with the current JavaRule.
   */
  @Override
  public RuleToJava createRuleToJava(final Asm resource, final boolean seqBlock, final TranslatorOptions translatorOptions) {
    return new RuleToJavaEvosuite(resource, seqBlock, translatorOptions, this.currRule);
  }

  /**
   * Create an instance of the {@code DomainToJavaSigDef} object.
   */
  @Override
  public DomainToJavaEvosuiteSigDef createDomainToJavaSigDef(final Asm resource) {
    return new DomainToJavaEvosuiteSigDef(resource);
  }

  /**
   * Method translating the conditional rules. <br \>
   * When entering in a conditional block, add a new branch to the rule
   * and set the flag variable to {@code true}
   * 
   * @param object the ConditionalRule.
   */
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
      String _addNewBranch = this.currRule.addNewBranch();
      _builder.append(_addNewBranch, "\t");
      _builder.append(" = true;");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      String _visit_1 = this.createRuleToJava(this.res, this.seqBlock, this.options).visit(object.getThenRule());
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
      String _addNewBranch_1 = this.currRule.addNewBranch();
      _builder_1.append(_addNewBranch_1, "\t");
      _builder_1.append(" = true;");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("\t");
      String _visit_3 = this.createRuleToJava(this.res, this.seqBlock, this.options).visit(object.getThenRule());
      _builder_1.append(_visit_3, "\t");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("} else {");
      _builder_1.newLine();
      _builder_1.append("\t");
      String _addNewBranch_2 = this.currRule.addNewBranch();
      _builder_1.append(_addNewBranch_2, "\t");
      _builder_1.append(" = true;");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("\t");
      String _visit_4 = this.createRuleToJava(this.res, this.seqBlock, this.options).visit(object.getElseRule());
      _builder_1.append(_visit_4, "\t");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("}");
      _builder_1.newLine();
      return _builder_1.toString();
    }
  }

  /**
   * Method translating the CaseRules. <br \>
   * When entering in a conditional block, add a new branch to the rule
   * and set the flag variable to {@code true}
   * 
   * @param object the CaseRule.
   */
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
        String _addNewBranch = this.currRule.addNewBranch();
        _builder.append(_addNewBranch, "\t");
        _builder.append(" = true;");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        String _visit = this.createRuleToJava(this.res, this.seqBlock, this.options).visit(object.getCaseBranches().get(i));
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
        String _addNewBranch_1 = this.currRule.addNewBranch();
        _builder_1.append(_addNewBranch_1, "\t");
        _builder_1.append(" = true;");
        _builder_1.newLineIfNotEmpty();
        _builder_1.append("\t");
        String _visit_1 = this.createRuleToJava(this.res, this.seqBlock, this.options).visit(object.getCaseBranches().get(i));
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
      _builder.append("\t");
      String _addNewBranch = this.currRule.addNewBranch();
      _builder.append(_addNewBranch, "\t");
      _builder.append(" = true;");
      _builder.newLineIfNotEmpty();
      _builder.append(" \t");
      String _visit = this.createRuleToJava(this.res, this.seqBlock, this.options).visit(object.getOtherwiseBranch());
      _builder.append(_visit, " \t");
      _builder.newLineIfNotEmpty();
      _builder.append("}");
      _builder.newLine();
      sb.append(_builder);
    }
    return sb.toString();
  }
}
