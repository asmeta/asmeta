package asmeta.asmetal2java.codegen.translator;

import asmeta.definitions.ControlledFunction;
import asmeta.definitions.DerivedFunction;
import asmeta.definitions.Function;
import asmeta.definitions.MonitoredFunction;
import asmeta.definitions.OutFunction;
import asmeta.definitions.StaticFunction;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.Domain;
import asmeta.structure.Asm;
import asmeta.terms.basicterms.BooleanTerm;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.LocationTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.TupleTerm;
import asmeta.terms.furtherterms.CaseTerm;
import asmeta.terms.furtherterms.CharTerm;
import asmeta.terms.furtherterms.ConditionalTerm;
import asmeta.terms.furtherterms.EnumTerm;
import asmeta.terms.furtherterms.IntegerTerm;
import asmeta.terms.furtherterms.NaturalTerm;
import asmeta.terms.furtherterms.RealTerm;
import asmeta.terms.furtherterms.StringTerm;
import java.util.Arrays;
import org.asmeta.parser.util.Defs;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;

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
  public String visit(final RealTerm term) {
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
  public String visit(final CharTerm term) {
    String _visit = super.visit(term);
    return (" = " + _visit);
  }

  @Override
  public String visit(final BooleanTerm term) {
    String _visit = super.visit(term);
    return (" = " + _visit);
  }

  @Override
  public String visit(final ConditionalTerm term) {
    String _visit = new TermToJava(this.res).visit(term);
    return (" = " + _visit);
  }

  @Override
  public String visit(final EnumTerm term) {
    String _visit = super.visit(term);
    return (" = " + _visit);
  }

  @Override
  public String visit(final CaseTerm term) {
    String _visit = new TermToJava(this.res).visit(term);
    return (" = " + _visit);
  }

  @Override
  public String visit(final LocationTerm term) {
    return this.visit(((FunctionTerm) term));
  }

  @Override
  public String visit(final FunctionTerm term) {
    StringBuffer functionTerm = new StringBuffer();
    String name = new Util().parseFunction(term.getFunction().getName());
    if ((((term.getArguments() != null) && Defs.getAsmName(term.getFunction()).equals("StandardLibrary")) && 
      ExpressionToJava.hasEvaluateVisitor(name))) {
      String _evaluateFunction = new ExpressionToJava(this.res).evaluateFunction(name, term.getArguments().getTerms());
      return ("=" + _evaluateFunction);
    } else {
      if (((term.getFunction() instanceof StaticFunction) && Defs.getAsmName(term.getFunction()).equals("StandardLibrary"))) {
        String _name = term.getFunction().getName();
        String _plus = ("StandardLibrary function \'" + _name);
        String _plus_1 = (_plus + 
          "\' is not supported by the Java generator");
        throw new InvalidFunctionException(_plus_1);
      }
      if ((!this.leftHandSide)) {
        functionTerm.append(" = ");
      }
      functionTerm.append(term.getFunction().getName());
      functionTerm.append(this.caseFunctionTermSupp(term.getFunction(), term));
      return functionTerm.toString();
    }
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
    if (this.leftHandSide) {
      return "";
    }
    TupleTerm _arguments = ft.getArguments();
    boolean _tripleEquals = (_arguments == null);
    if (_tripleEquals) {
      return ".get()";
    }
    int _size = ft.getArguments().getTerms().size();
    boolean _equals = (_size == 1);
    if (_equals) {
      String _visit = new TermToJava(this.res).visit(ft.getArguments().getTerms().get(0));
      String _plus = (".get(" + _visit);
      return (_plus + ")");
    }
    final java.util.function.Function<Term, String> _function = (Term term) -> {
      return new TermToJava(this.res).visit(term);
    };
    final String tuple = ProductToJava.value(ft.getArguments(), _function);
    return ((".get(" + tuple) + ")");
  }

  @Override
  protected String _caseFunctionTermSupp(final StaticFunction fd, final FunctionTerm ft) {
    StringBuffer functionTerm = new StringBuffer();
    TupleTerm _arguments = ft.getArguments();
    boolean _tripleNotEquals = (_arguments != null);
    if (_tripleNotEquals) {
      functionTerm.append("(");
      for (int i = 0; (i < ft.getArguments().getTerms().size()); i++) {
        String _visit = this.visit(ft.getArguments().getTerms().get(i));
        String _plus = (_visit + ", ");
        functionTerm.append(_plus);
      }
      int _length = functionTerm.length();
      int _minus = (_length - 2);
      String _substring = functionTerm.substring(0, _minus);
      String _plus = (_substring + ")");
      StringBuffer _stringBuffer = new StringBuffer(_plus);
      functionTerm = _stringBuffer;
    } else {
      Domain _domain = ft.getDomain();
      if ((_domain instanceof AbstractTd)) {
        functionTerm.append("");
      } else {
        functionTerm.append("()");
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
