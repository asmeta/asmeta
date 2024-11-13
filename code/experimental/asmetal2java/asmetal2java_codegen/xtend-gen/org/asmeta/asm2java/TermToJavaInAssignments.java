package org.asmeta.asm2java;

import asmeta.structure.Asm;
import asmeta.terms.basicterms.BooleanTerm;
import asmeta.terms.furtherterms.CaseTerm;
import asmeta.terms.furtherterms.EnumTerm;
import asmeta.terms.furtherterms.IntegerTerm;
import asmeta.terms.furtherterms.NaturalTerm;
import asmeta.terms.furtherterms.StringTerm;

/**
 * This class is used to translate Asmeta Terms in assignments
 */
@SuppressWarnings("all")
public class TermToJavaInAssignments extends TermToJava {
  public TermToJavaInAssignments(final Asm resource) {
    this(resource, false);
  }

  public TermToJavaInAssignments(final Asm resource, final boolean leftHandSide) {
    super(resource, leftHandSide);
  }

  @Override
  public String visit(final IntegerTerm term) {
    String _visit = super.visit(term);
    return (" = " + _visit);
  }

  @Override
  public String visit(final NaturalTerm term) {
    String _visit = super.visit(term);
    return (" = " + _visit);
  }

  @Override
  public String visit(final StringTerm term) {
    String _visit = super.visit(term);
    return (" = " + _visit);
  }

  @Override
  public String visit(final BooleanTerm term) {
    String _visit = super.visit(term);
    return (" = " + _visit);
  }

  @Override
  public String visit(final EnumTerm term) {
    String _visit = super.visit(term);
    return (" = " + _visit);
  }

  @Override
  public String visit(final CaseTerm object) {
    throw new Error("Unresolved compilation problems:"
      + "\nno viable alternative at input \'Â\'"
      + "\nmismatched input \'»\\tif(Â«\' expecting \'}\'"
      + "\nmismatched input \'».toString(Â«\' expecting \'}\'"
      + "\nmismatched input \'»).equals(\"Â«\' expecting \'}\'"
      + "\nThe method or field comparingTerm is undefined for the type CaseTerm"
      + "\nThis expression is not allowed in this context, since it doesn\'t cause any side effects."
      + "\nThis expression is not allowed in this context, since it doesn\'t cause any side effects."
      + "\nsize cannot be resolved");
  }

  private Object Â;
}
