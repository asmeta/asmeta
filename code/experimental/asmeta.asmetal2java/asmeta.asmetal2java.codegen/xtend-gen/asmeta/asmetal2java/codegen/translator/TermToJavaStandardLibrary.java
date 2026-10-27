package asmeta.asmetal2java.codegen.translator;

import asmeta.definitions.ControlledFunction;
import asmeta.definitions.DerivedFunction;
import asmeta.definitions.Function;
import asmeta.definitions.MonitoredFunction;
import asmeta.definitions.OutFunction;
import asmeta.definitions.StaticFunction;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.MapDomain;
import asmeta.structure.Asm;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.TupleTerm;
import java.util.Arrays;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;

/**
 * This class is used when implementing operations of the StandardLibrary
 */
@SuppressWarnings("all")
public class TermToJavaStandardLibrary extends TermToJava {
  public TermToJavaStandardLibrary(final Asm resource) {
    this(resource, false);
  }

  public TermToJavaStandardLibrary(final Asm resource, final boolean leftHandSide) {
    super(resource, leftHandSide, "");
  }

  @Override
  protected String _caseFunctionTermSupp(final ControlledFunction fd, final FunctionTerm ft) {
    return this.caseControlledOrOutputFunctionSupp(fd, ft);
  }

  @Override
  protected String _caseFunctionTermSupp(final OutFunction fd, final FunctionTerm ft) {
    return this.caseControlledOrOutputFunctionSupp(fd, ft);
  }

  private String caseControlledOrOutputFunctionSupp(final Function fd, final FunctionTerm ft) {
    StringBuffer functionTerm = new StringBuffer();
    TupleTerm _arguments = ft.getArguments();
    boolean _tripleEquals = (_arguments == null);
    if (_tripleEquals) {
      Domain _domain = ft.getDomain();
      if ((_domain instanceof ConcreteDomain)) {
        if ((!this.leftHandSide)) {
          functionTerm.append(".get()");
        }
      } else {
        Domain _domain_1 = ft.getDomain();
        if ((_domain_1 instanceof MapDomain)) {
          functionTerm.append(".get()");
        } else {
          if (this.leftHandSide) {
            functionTerm.append(".set(");
          } else {
            functionTerm.append(".get()");
          }
        }
      }
    }
    TupleTerm _arguments_1 = ft.getArguments();
    boolean _tripleNotEquals = (_arguments_1 != null);
    if (_tripleNotEquals) {
      TermToJava _termToJava = new TermToJava(this.res);
      final String arguments = ArgumentToJava.locationArguments(ft, _termToJava);
      if (this.leftHandSide) {
        this.leftHandSide = false;
        functionTerm.append(((".set(" + arguments) + ", "));
      } else {
        functionTerm.append(((".get(" + arguments) + ")"));
      }
    }
    return functionTerm.toString();
  }

  @Override
  protected String _caseFunctionTermSupp(final MonitoredFunction fd, final FunctionTerm ft) {
    StringBuffer functionTerm = new StringBuffer();
    TupleTerm _arguments = ft.getArguments();
    boolean _tripleEquals = (_arguments == null);
    if (_tripleEquals) {
      if (this.leftHandSide) {
        functionTerm.append(".set(");
      } else {
        Domain _codomain = fd.getCodomain();
        if ((_codomain instanceof ConcreteDomain)) {
          functionTerm.append(".get().value");
        } else {
          functionTerm.append(".get()");
        }
      }
    }
    TupleTerm _arguments_1 = ft.getArguments();
    boolean _tripleNotEquals = (_arguments_1 != null);
    if (_tripleNotEquals) {
      TermToJava _termToJava = new TermToJava(this.res);
      final String arguments = ArgumentToJava.locationArguments(ft, _termToJava);
      if (this.leftHandSide) {
        this.leftHandSide = false;
        functionTerm.append(((".set(" + arguments) + ", "));
      } else {
        functionTerm.append(((".get(" + arguments) + ")"));
      }
    }
    return functionTerm.toString();
  }

  @Override
  @XbaseGenerated
  public String caseFunctionTermSupp(final Function fd, final FunctionTerm ft) {
    if (fd instanceof ControlledFunction) {
      return _caseFunctionTermSupp((ControlledFunction)fd, ft);
    } else if (fd instanceof MonitoredFunction) {
      return _caseFunctionTermSupp((MonitoredFunction)fd, ft);
    } else if (fd instanceof OutFunction) {
      return _caseFunctionTermSupp((OutFunction)fd, ft);
    } else if (fd instanceof StaticFunction) {
      return _caseFunctionTermSupp((StaticFunction)fd, ft);
    } else if (fd instanceof DerivedFunction) {
      return _caseFunctionTermSupp((DerivedFunction)fd, ft);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(fd, ft).toString());
    }
  }
}
