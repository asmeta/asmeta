package org.asmeta.asm2java;

import asmeta.terms.basicterms.BooleanTerm;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.LocationTerm;
import asmeta.terms.basicterms.SetTerm;
import asmeta.terms.basicterms.TupleTerm;
import asmeta.terms.basicterms.UndefTerm;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.terms.furtherterms.CaseTerm;
import asmeta.terms.furtherterms.ConditionalTerm;
import asmeta.terms.furtherterms.EnumTerm;
import asmeta.terms.furtherterms.IntegerTerm;
import asmeta.terms.furtherterms.NaturalTerm;
import asmeta.terms.furtherterms.SequenceTerm;
import asmeta.terms.furtherterms.StringTerm;
import org.asmeta.parser.util.ReflectiveVisitor;

/**
 * Check if the init function term contains monitored functions
 */
@SuppressWarnings("all")
public class FindMonitoredInControlledFunct extends ReflectiveVisitor<Boolean> {
  public boolean visit(final LocationTerm object) {
    return this.visit(((FunctionTerm) object));
  }

  public boolean visit(final StringTerm term) {
    return false;
  }

  public boolean visit(final UndefTerm term) {
    return false;
  }

  public boolean visit(final IntegerTerm term) {
    return false;
  }

  public boolean visit(final NaturalTerm term) {
    return false;
  }

  public boolean visit(final BooleanTerm term) {
    return false;
  }

  public boolean visit(final EnumTerm term) {
    return false;
  }

  public boolean visit(final VariableTerm term) {
    return false;
  }

  public boolean visit(final FunctionTerm term) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field terms is undefined for the type TupleTerm");
  }

  public boolean visit(final CaseTerm term) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field comparingTerm is undefined for the type CaseTerm"
      + "\nThe method or field resultTerms is undefined for the type CaseTerm");
  }

  public boolean visit(final ConditionalTerm term) {
    boolean found = false;
    found = (found || (this.visit(term.getThenTerm())).booleanValue());
    found = (found || (this.visit(term.getElseTerm())).booleanValue());
    return found;
  }

  public boolean visit(final SetTerm term) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field term is undefined for the type SetTerm");
  }

  public boolean visit(final SequenceTerm term) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field terms is undefined for the type SequenceTerm");
  }

  public boolean visit(final TupleTerm term) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field terms is undefined for the type TupleTerm");
  }
}
