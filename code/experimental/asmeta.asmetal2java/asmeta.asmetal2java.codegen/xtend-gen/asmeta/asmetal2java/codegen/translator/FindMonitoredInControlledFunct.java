package asmeta.asmetal2java.codegen.translator;

import asmeta.definitions.Function;
import asmeta.definitions.MonitoredFunction;
import asmeta.terms.basicterms.BooleanTerm;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.LocationTerm;
import asmeta.terms.basicterms.SetTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.TupleTerm;
import asmeta.terms.basicterms.UndefTerm;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.terms.furtherterms.CaseTerm;
import asmeta.terms.furtherterms.CharTerm;
import asmeta.terms.furtherterms.ConditionalTerm;
import asmeta.terms.furtherterms.EnumTerm;
import asmeta.terms.furtherterms.IntegerTerm;
import asmeta.terms.furtherterms.NaturalTerm;
import asmeta.terms.furtherterms.RealTerm;
import asmeta.terms.furtherterms.SequenceTerm;
import asmeta.terms.furtherterms.StringTerm;
import org.asmeta.parser.util.ReflectiveVisitor;
import org.eclipse.emf.common.util.EList;

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

  public boolean visit(final CharTerm term) {
    return false;
  }

  public boolean visit(final UndefTerm term) {
    return false;
  }

  public boolean visit(final IntegerTerm term) {
    return false;
  }

  public boolean visit(final RealTerm term) {
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
    boolean found = false;
    TupleTerm _arguments = term.getArguments();
    boolean _tripleEquals = (_arguments == null);
    if (_tripleEquals) {
      Function _function = term.getFunction();
      if ((_function instanceof MonitoredFunction)) {
        return true;
      } else {
        return false;
      }
    } else {
      EList<Term> _terms = term.getArguments().getTerms();
      for (final Term sterm : _terms) {
        found = (found || (this.visit(sterm)).booleanValue());
      }
    }
    return found;
  }

  public boolean visit(final CaseTerm term) {
    boolean found = false;
    EList<Term> _comparingTerm = term.getComparingTerm();
    for (final Term comparing : _comparingTerm) {
      found = (found || (this.visit(comparing)).booleanValue());
    }
    EList<Term> _resultTerms = term.getResultTerms();
    for (final Term result : _resultTerms) {
      found = (found || (this.visit(result)).booleanValue());
    }
    found = (found || (this.visit(term.getComparedTerm())).booleanValue());
    Term _otherwiseTerm = term.getOtherwiseTerm();
    boolean _tripleNotEquals = (_otherwiseTerm != null);
    if (_tripleNotEquals) {
      found = (found || (this.visit(term.getOtherwiseTerm())).booleanValue());
    }
    return found;
  }

  public boolean visit(final ConditionalTerm term) {
    boolean found = false;
    found = (found || (this.visit(term.getThenTerm())).booleanValue());
    found = (found || (this.visit(term.getElseTerm())).booleanValue());
    return found;
  }

  public boolean visit(final SetTerm term) {
    boolean found = false;
    EList<Term> _term = term.getTerm();
    for (final Term comparing : _term) {
      found = (found || (this.visit(comparing)).booleanValue());
    }
    return found;
  }

  public boolean visit(final SequenceTerm term) {
    boolean found = false;
    EList<Term> _terms = term.getTerms();
    for (final Term comparing : _terms) {
      found = (found || (this.visit(comparing)).booleanValue());
    }
    return found;
  }

  public boolean visit(final TupleTerm term) {
    boolean found = false;
    EList<Term> _terms = term.getTerms();
    for (final Term comparing : _terms) {
      found = (found || (this.visit(comparing)).booleanValue());
    }
    return found;
  }
}
