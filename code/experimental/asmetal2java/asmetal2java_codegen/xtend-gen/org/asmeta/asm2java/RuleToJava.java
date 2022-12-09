package org.asmeta.asm2java;

import asmeta.structure.Asm;
import asmeta.terms.basicterms.Term;
import asmeta.transitionrules.basictransitionrules.BlockRule;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.Rule;
import org.asmeta.asm2java.main.TranslatorOptions;
import org.asmeta.simulator.RuleVisitor;
import org.eclipse.emf.common.util.EList;

@SuppressWarnings("all")
public class RuleToJava extends RuleVisitor<String> {
  private Asm res;

  private boolean seqBlock;

  private TranslatorOptions options;

  /**
   * seqBlock iff it is called in a seq rule
   */
  public RuleToJava(final Asm resource, final boolean seqBlock, final TranslatorOptions options) {
    this.res = resource;
    this.seqBlock = seqBlock;
    this.options = options;
  }

  @Override
  public String visit(final BlockRule object) {
    throw new Error("Unresolved compilation problems:"
      + "\nno viable alternative at input \'Â\'"
      + "\nextraneous input \'»\\r\\n\\t\\t}//endpar\'\'\'\' expecting \'}\'"
      + "\nThe method or field Â is undefined"
      + "\nUnreachable expression.");
  }

  private String printRules(final EList<Rule> rules) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; (i < rules.size()); i++) {
      sb.append(new RuleToJava(this.res, this.seqBlock, this.options).visit(rules.get(i)));
    }
    return sb.toString();
  }

  @Override
  public String visit(final MacroCallRule object) {
    throw new Error("Unresolved compilation problems:"
      + "\nno viable alternative at input \'Â\'"
      + "\nextraneous input \'»);\\r\\n\'\'\'\' expecting \'}\'"
      + "\nThe method or field methodNameÂ is undefined"
      + "\nThe method or field methodNameÂ is undefined"
      + "\nThe method or field Â is undefined"
      + "\nUnreachable expression.");
  }

  private String printListTerm(final EList<Term> term) {
    throw new Error("Unresolved compilation problems:"
      + "\nno viable alternative at input \'Â\'"
      + "\nmismatched input \'».value, \'\'\'\' expecting \'}\'"
      + "\nno viable alternative at input \'Â\'"
      + "\nmismatched input \'», \'\'\'\' expecting \'}\'"
      + "\nThe method or field Â is undefined"
      + "\nThe method or field i is undefined"
      + "\nThe method or field Â is undefined");
  }
}
