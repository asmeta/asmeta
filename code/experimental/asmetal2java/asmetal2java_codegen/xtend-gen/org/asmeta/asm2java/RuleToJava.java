package org.asmeta.asm2java;

import asmeta.structure.Asm;
import asmeta.transitionrules.basictransitionrules.BlockRule;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.basictransitionrules.SkipRule;
import asmeta.transitionrules.turbotransitionrules.SeqRule;
import org.asmeta.asm2java.main.TranslatorOptions;
import org.asmeta.simulator.RuleVisitor;
import org.eclipse.emf.common.util.EList;

@SuppressWarnings("all")
public class RuleToJava extends RuleVisitor<String> {
  private Asm res;

  private boolean seqBlock;

  private TranslatorOptions options;

  /**
   * SeqBlock iff it is called in a seq rule
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
      + "\nextraneous input \'»\\r\\n\\t\\t//} //endpar\'\'\'\' expecting \'}\'"
      + "\nThe method getRules() is undefined for the type BlockRule"
      + "\nThe method or field Â is undefined"
      + "\nUnreachable expression.");
  }

  private String printRules(final EList<Rule> rules, final boolean addFire) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; (i < rules.size()); i++) {
      {
        sb.append(new RuleToJava(this.res, this.seqBlock, this.options).visit(rules.get(i)));
        if (addFire) {
          sb.append("\nfireUpdateSet();\n");
        }
      }
    }
    return sb.toString();
  }

  @Override
  public String visit(final SkipRule object) {
    return "// Empty rule \n";
  }

  @Override
  public String visit(final SeqRule object) {
    throw new Error("Unresolved compilation problems:"
      + "\nno viable alternative at input \'Â\'"
      + "\nextraneous input \'»\\r\\n\\t\\t\\t//} //endseq\\r\\n\\t\\t\'\'\'\' expecting \'}\'"
      + "\nThe method or field rules is undefined for the type SeqRule"
      + "\nThe method or field Â is undefined"
      + "\nUnreachable expression.");
  }

  @Override
  public String visit(final ConditionalRule object) {
    throw new Error("Unresolved compilation problems:"
      + "\nno viable alternative at input \'Â\'"
      + "\nmismatched input \'»)){ \\r\\n\\t\\t\\t\\t\\tÂ«\' expecting \'}\'"
      + "\nThe method or field Â is undefined");
  }

  public RuleToJava(final /* res */Object __unknown__, final /* seqBlock */Object __unknown___1, final /* options */Object __unknown___2) {
    throw new Error("Unresolved compilation problems:"
      + "\nmismatched input \'.\' expecting \'{\'");
  }

  public RuleToJava(final /* res */Object __unknown__) {
    throw new Error("Unresolved compilation problems:"
      + "\nmismatched input \'.\' expecting \'{\'");
  }

  public RuleToJava(final /* res */Object __unknown__, final /* seqBlock */Object __unknown___1, final /* options */Object __unknown___2) {
    throw new Error("Unresolved compilation problems:"
      + "\nmismatched input \'.\' expecting \'{\'");
  }

  public RuleToJava(final /* res */Object __unknown__, final /* seqBlock */Object __unknown___1, final /* options */Object __unknown___2) {
    throw new Error("Unresolved compilation problems:"
      + "\nmismatched input \'.\' expecting \'{\'");
  }
}
