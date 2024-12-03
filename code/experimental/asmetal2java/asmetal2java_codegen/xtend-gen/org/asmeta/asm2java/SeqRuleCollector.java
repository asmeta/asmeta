package org.asmeta.asm2java;

import asmeta.transitionrules.basictransitionrules.BlockRule;
import asmeta.transitionrules.basictransitionrules.ChooseRule;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.ExtendRule;
import asmeta.transitionrules.basictransitionrules.ForallRule;
import asmeta.transitionrules.basictransitionrules.LetRule;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.basictransitionrules.SkipRule;
import asmeta.transitionrules.basictransitionrules.TermAsRule;
import asmeta.transitionrules.basictransitionrules.UpdateRule;
import asmeta.transitionrules.derivedtransitionrules.CaseRule;
import asmeta.transitionrules.turbotransitionrules.SeqRule;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.asmeta.simulator.RuleVisitor;
import org.eclipse.emf.common.util.EList;

@SuppressWarnings("all")
public class SeqRuleCollector extends RuleVisitor<List<Rule>> {
  private boolean seqBlock;

  /**
   * SeqBlock iff it is called in a seq rule
   */
  public SeqRuleCollector(final boolean seqBlock) {
    this.seqBlock = seqBlock;
  }

  @Override
  public List<Rule> visit(final BlockRule object) {
    return this.listRules(object.getRules());
  }

  private List<Rule> listRules(final EList<Rule> rules) {
    List<Rule> sb = new ArrayList<Rule>();
    for (int i = 0; (i < rules.size()); i++) {
      sb.addAll(new SeqRuleCollector(this.seqBlock).visit(rules.get(i)));
    }
    return sb;
  }

  @Override
  public List<Rule> visit(final MacroCallRule object) {
    if (this.seqBlock) {
      return Collections.<Rule>singletonList(object.getCalledMacro().getRuleBody());
    } else {
      return Collections.<Rule>emptyList();
    }
  }

  public List<Rule> visit(final MacroDeclaration object) {
    return Collections.<Rule>emptyList();
  }

  @Override
  public List<Rule> visit(final SeqRule object) {
    return new SeqRuleCollector(true).listRules(object.getRules());
  }

  @Override
  public List<Rule> visit(final UpdateRule object) {
    return Collections.<Rule>emptyList();
  }

  @Override
  public List<Rule> visit(final SkipRule object) {
    return Collections.<Rule>emptyList();
  }

  @Override
  public List<Rule> visit(final CaseRule object) {
    List<Rule> _listRules = this.listRules(object.getCaseBranches());
    List<Rule> list = new ArrayList<Rule>(_listRules);
    Rule _otherwiseBranch = object.getOtherwiseBranch();
    boolean _tripleNotEquals = (_otherwiseBranch != null);
    if (_tripleNotEquals) {
      list.addAll(this.visit(object.getOtherwiseBranch()));
    }
    return list;
  }

  @Override
  public List<Rule> visit(final TermAsRule rule) {
    throw new UnsupportedOperationException("TermAsRule not implemented");
  }

  @Override
  public List<Rule> visit(final ExtendRule rule) {
    return this.visit(rule.getDoRule());
  }

  @Override
  public List<Rule> visit(final ConditionalRule rule) {
    List<Rule> list = new ArrayList<Rule>();
    list.addAll(this.visit(rule.getThenRule()));
    Rule _elseRule = rule.getElseRule();
    boolean _tripleNotEquals = (_elseRule != null);
    if (_tripleNotEquals) {
      list.addAll(this.visit(rule.getElseRule()));
    }
    return list;
  }

  @Override
  public List<Rule> visit(final LetRule rule) {
    return this.visit(rule.getInRule());
  }

  @Override
  public List<Rule> visit(final ChooseRule rule) {
    List<Rule> list = new ArrayList<Rule>();
    list.addAll(this.visit(rule.getDoRule()));
    Rule _ifnone = rule.getIfnone();
    boolean _tripleNotEquals = (_ifnone != null);
    if (_tripleNotEquals) {
      list.addAll(this.visit(rule.getIfnone()));
    }
    return list;
  }

  @Override
  public List<Rule> visit(final ForallRule rule) {
    return this.visit(rule.getDoRule());
  }
}
