package org.asmeta.asm2java;

import asmeta.definitions.Function;
import asmeta.structure.Asm;
import asmeta.terms.basicterms.FunctionTerm;

/**
 * This class is used in Update Rules
 */
@SuppressWarnings("all")
public class TermToJavaInUpdateRule extends TermToJava {
  public TermToJavaInUpdateRule(final Asm resource) {
    this(resource, false);
  }

  public TermToJavaInUpdateRule(final Asm resource, final boolean leftHandSide) {
    super(resource, leftHandSide, "");
  }

  public TermToJavaInUpdateRule(final Asm resource, final boolean leftHandSide, final String varName) {
    super(resource, leftHandSide, varName);
  }

  @Override
  public String visit(final FunctionTerm term) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method parseFunction(String) is undefined for the type Util");
  }

  @Override
  public String caseFunctionTermSupp(final Function fd, final FunctionTerm ft) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field terms is undefined for the type TupleTerm"
      + "\nThe method or field terms is undefined for the type TupleTerm"
      + "\nsize cannot be resolved"
      + "\n== cannot be resolved"
      + "\nget cannot be resolved");
  }
}
