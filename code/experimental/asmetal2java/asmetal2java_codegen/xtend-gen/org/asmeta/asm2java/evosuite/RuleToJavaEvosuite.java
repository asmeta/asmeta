package org.asmeta.asm2java.evosuite;

import asmeta.structure.Asm;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import org.asmeta.asm2java.RuleToJava;
import org.asmeta.asm2java.main.TranslatorOptions;

@SuppressWarnings("all")
public class RuleToJavaEvosuite extends RuleToJava {
  public RuleToJavaEvosuite(final Asm resource, final boolean seqBlock, final TranslatorOptions options) {
    super(resource, seqBlock, options);
  }

  @Override
  public String visit(final ConditionalRule object) {
    return super.visit(object);
  }
}
