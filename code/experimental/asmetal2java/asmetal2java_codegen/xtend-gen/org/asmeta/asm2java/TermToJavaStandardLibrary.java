package org.asmeta.asm2java;

import asmeta.definitions.ControlledFunction;
import asmeta.definitions.DerivedFunction;
import asmeta.definitions.Function;
import asmeta.definitions.MonitoredFunction;
import asmeta.definitions.StaticFunction;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.BasicTd;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.MapDomain;
import asmeta.definitions.domains.ProductDomain;
import asmeta.definitions.domains.StructuredTd;
import asmeta.structure.Asm;
import asmeta.terms.basicterms.ConstantTerm;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.LocationTerm;
import asmeta.terms.basicterms.TupleTerm;
import java.util.Arrays;
import org.eclipse.emf.ecore.EObject;

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
          functionTerm.append("");
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
      int _size = ft.getArguments().getTerms().size();
      boolean _equals = (_size == 1);
      if (_equals) {
        Domain _domain_2 = ft.getDomain();
        if ((_domain_2 instanceof ConcreteDomain)) {
          if ((!this.leftHandSide)) {
            String _visit = this.visit(ft.getArguments().getTerms().get(0));
            String _plus = (".get(" + _visit);
            String _plus_1 = (_plus + ")");
            functionTerm.append(_plus_1);
          }
        } else {
          if (this.leftHandSide) {
            this.leftHandSide = false;
            String _visit_1 = this.visit(ft.getArguments().getTerms().get(0));
            String _plus_2 = (".set(" + _visit_1);
            String _plus_3 = (_plus_2 + ", ");
            functionTerm.append(_plus_3);
          } else {
            if (((ft.getArguments().getTerms().get(0) instanceof ConstantTerm) && (!(((LocationTerm) ft.getArguments().eContainer()).getFunction().getDomain() instanceof EnumTd)))) {
              EObject _eContainer = ft.getArguments().eContainer();
              String _name = ((LocationTerm) _eContainer).getFunction().getDomain().getName();
              String _plus_4 = (".get(" + _name);
              String _plus_5 = (_plus_4 + ".valueOf(");
              String _visit_2 = this.visit(ft.getArguments().getTerms().get(0));
              String _plus_6 = (_plus_5 + _visit_2);
              String _plus_7 = (_plus_6 + "))");
              functionTerm.append(_plus_7);
            } else {
              if (((fd.getDomain() instanceof ConcreteDomain) && (((ConcreteDomain) fd.getDomain()).getTypeDomain() instanceof BasicTd))) {
                String visitedFunction = this.visit(ft.getArguments().getTerms().get(0));
                String _name_1 = fd.getDomain().getName();
                String _plus_8 = (".get(" + _name_1);
                String _plus_9 = (_plus_8 + ".valueOf(");
                String _plus_10 = (_plus_9 + visitedFunction);
                String _plus_11 = (_plus_10 + "))");
                functionTerm.append(_plus_11);
              } else {
                String _visit_3 = this.visit(ft.getArguments().getTerms().get(0));
                String _plus_12 = (".get(" + _visit_3);
                String _plus_13 = (_plus_12 + ")");
                functionTerm.append(_plus_13);
              }
            }
          }
        }
      } else {
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
        functionTerm.append(".get()");
      }
    }
    TupleTerm _arguments_1 = ft.getArguments();
    boolean _tripleNotEquals = (_arguments_1 != null);
    if (_tripleNotEquals) {
      int _size = ft.getArguments().getTerms().size();
      boolean _equals = (_size == 1);
      if (_equals) {
        if (this.leftHandSide) {
          this.leftHandSide = false;
          String _visit = this.visit(ft.getArguments().getTerms().get(0));
          String _plus = (".set(" + _visit);
          String _plus_1 = (_plus + ", ");
          functionTerm.append(_plus_1);
        } else {
          String _visit_1 = this.visit(ft.getArguments().getTerms().get(0));
          String _plus_2 = (".get(" + _visit_1);
          String _plus_3 = (_plus_2 + ")");
          functionTerm.append(_plus_3);
        }
      } else {
        functionTerm.append("[make_tuple(");
        for (int i = 0; (i < ft.getArguments().getTerms().size()); i++) {
          String _visit_2 = this.visit(ft.getArguments().getTerms().get(i));
          String _plus_4 = (_visit_2 + ", ");
          functionTerm.append(_plus_4);
        }
        int _length = functionTerm.length();
        int _minus = (_length - 2);
        String _substring = functionTerm.substring(0, _minus);
        String _plus_4 = (_substring + ")]");
        StringBuffer _stringBuffer = new StringBuffer(_plus_4);
        functionTerm = _stringBuffer;
      }
    }
    return functionTerm.toString();
  }

  @Override
  protected String _caseFunctionTermSupp(final StaticFunction fd, final FunctionTerm ft) {
    StringBuffer functionTerm = new StringBuffer();
    TupleTerm _arguments = ft.getArguments();
    boolean _tripleNotEquals = (_arguments != null);
    if (_tripleNotEquals) {
      functionTerm.append("(");
      for (int i = 0; (i < ft.getArguments().getTerms().size()); i++) {
        {
          String param = this.visit(ft.getArguments().getTerms().get(i));
          Domain _domain = ft.getFunction().getDomain();
          if ((_domain instanceof StructuredTd)) {
            Domain _domain_1 = ft.getFunction().getDomain();
            if ((_domain_1 instanceof ProductDomain)) {
              Domain _domain_2 = ft.getFunction().getDomain();
              boolean _equals = ((ProductDomain) _domain_2).getDomains().get(i).equals(ft.getArguments().getTerms().get(i).getDomain());
              if (_equals) {
                param = param.replaceAll("\\.value", "");
              }
            }
          } else {
            boolean _equals_1 = ft.getDomain().equals(ft.getArguments().getTerms().get(i).getDomain());
            if (_equals_1) {
              param = param.replaceAll("\\.value", "");
            }
          }
          functionTerm.append((param + ", "));
        }
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
  public String caseFunctionTermSupp(final Function fd, final FunctionTerm ft) {
    if (fd instanceof ControlledFunction) {
      return _caseFunctionTermSupp((ControlledFunction)fd, ft);
    } else if (fd instanceof MonitoredFunction) {
      return _caseFunctionTermSupp((MonitoredFunction)fd, ft);
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
