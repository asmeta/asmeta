package org.asmeta.asm2java;

import asmeta.definitions.ControlledFunction;
import asmeta.definitions.Function;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.MapDomain;
import asmeta.definitions.domains.ProductDomain;
import asmeta.definitions.domains.SequenceDomain;
import asmeta.structure.Asm;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.LocationTerm;
import asmeta.terms.basicterms.TupleTerm;
import asmeta.terms.basicterms.VariableTerm;
import org.asmeta.asm2java.ExpressionToJava;
import org.asmeta.asm2java.Util;
import org.asmeta.parser.util.ReflectiveVisitor;

@SuppressWarnings("all")
public class TermToJavaSupportoProdMap extends ReflectiveVisitor<String> {
  Integer numStaticParam;
  
  private Asm res;
  
  private boolean leftHandSide;
  
  public TermToJavaSupportoProdMap(final Asm resource) {
    this(resource, false);
  }
  
  public TermToJavaSupportoProdMap(final Asm resource, final boolean leftHandSide) {
    this.res = resource;
    this.leftHandSide = leftHandSide;
  }
  
  public String visit(final VariableTerm term) {
    return term.getName();
  }
  
  /**
   * TODO: DELETE FOR COVERAGE
   * def String visit(ComplexTerm term) {
   * return "TODO"
   * }
   * 
   * def String visit(CharTerm term) {
   * return term.symbol
   * }
   * 
   * 
   * 
   * def String visit(RealTerm term) {
   * return term.symbol
   * }
   */
  public String visit(final LocationTerm term) {
    return this.visit(((FunctionTerm) term));
  }
  
  public String visit(final FunctionTerm term) {
    Object _xblockexpression = null;
    {
      StringBuffer functionTerm = new StringBuffer();
      String name = new Util().parseFunction(term.getFunction().getName());
      Object _xifexpression = null;
      boolean _hasEvaluateVisitor = ExpressionToJava.hasEvaluateVisitor(name);
      if (_hasEvaluateVisitor) {
        _xifexpression = null;
      } else {
        if ((((term.getFunction() instanceof ControlledFunction) && (term.getDomain() instanceof ConcreteDomain)) && (!(term.getFunction().getDomain() instanceof ProductDomain)))) {
          functionTerm.append(this.caseFunctionTermSupp(term.getFunction(), term));
        }
        if ((((term.getFunction() instanceof ControlledFunction) && (term.getFunction().getDomain() instanceof ProductDomain)) && (term.getDomain() instanceof ConcreteDomain))) {
          functionTerm.append(this.caseFunctionTermSupp(term.getFunction(), term));
        }
        if (((term.getFunction() instanceof ControlledFunction) && (term.getDomain() instanceof MapDomain))) {
          functionTerm.append(this.caseFunctionTermSupp(term.getFunction(), term));
        }
        if (((term.getFunction() instanceof ControlledFunction) && (term.getDomain() instanceof SequenceDomain))) {
          functionTerm.append(this.caseFunctionTermSupp(term.getFunction(), term));
        }
        return functionTerm.toString();
      }
      _xblockexpression = _xifexpression;
    }
    return ((String)_xblockexpression);
  }
  
  /**
   * TODO: DELETE FOR COVERAGE
   * def dispatch String caseFunctionTermSupp(FunctionDefinition fd, FunctionTerm ft) {
   * println("Warning: Function Definition not handled! function name: " + fd.definedFunction.name)
   * return ""
   * }
   * 
   * 
   * 
   * def dispatch String caseFunctionTermSupp(OutFunction fd, FunctionTerm ft) {
   * var StringBuffer functionTerm = new StringBuffer
   * if (ft.arguments !== null) {
   * if (ft.arguments.terms.size == 1)
   * functionTerm.append("[" + visit(ft.arguments.terms.get(0)) + "]")
   * else {
   * functionTerm.append("[make_tuple(")
   * for (var i = 0; i < ft.arguments.terms.size; i++)
   * functionTerm.append(visit(ft.arguments.terms.get(i)) + ", ")
   * 
   * functionTerm = new StringBuffer(functionTerm.substring(0, functionTerm.length - 2) + ")]")
   * }
   * }
   * return functionTerm.toString
   * }
   * 
   * def dispatch String caseFunctionTermSupp(SharedFunction fd, FunctionTerm ft) {
   * throw new RuntimeException("Shared Functions not yet supported")
   * }
   */
  public String caseFunctionTermSupp(final Function fd, final FunctionTerm ft) {
    StringBuffer functionTerm = new StringBuffer();
    Domain _domain = fd.getDomain();
    if ((_domain instanceof ProductDomain)) {
      String _name = fd.getName();
      String _plus = (_name + ".set(");
      String _name_1 = fd.getName();
      String _plus_1 = (_plus + _name_1);
      String _plus_2 = (_plus_1 + "_elem, sup);\n");
      functionTerm.append(_plus_2);
    }
    Domain _domain_1 = fd.getDomain();
    if ((_domain_1 instanceof SequenceDomain)) {
      String _name_2 = fd.getName();
      String _plus_3 = (_name_2 + ".set(");
      String _name_3 = fd.getName();
      String _plus_4 = (_plus_3 + _name_3);
      String _plus_5 = (_plus_4 + "_elem);\n");
      functionTerm.append(_plus_5);
    }
    Domain _domain_2 = ft.getDomain();
    if ((_domain_2 instanceof MapDomain)) {
      String _name_4 = fd.getName();
      String _plus_6 = (_name_4 + ".set(supporto);\n");
      functionTerm.append(_plus_6);
    }
    Domain _domain_3 = ft.getDomain();
    if ((_domain_3 instanceof SequenceDomain)) {
      String _name_5 = fd.getName();
      String _plus_7 = (_name_5 + ".set(");
      String _name_6 = fd.getName();
      String _plus_8 = (_plus_7 + _name_6);
      String _plus_9 = (_plus_8 + "_elem);\n");
      functionTerm.append(_plus_9);
    }
    TupleTerm _arguments = ft.getArguments();
    boolean _tripleEquals = (_arguments == null);
    if (_tripleEquals) {
      Domain _domain_4 = ft.getDomain();
      if ((_domain_4 instanceof ConcreteDomain)) {
        if ((!this.leftHandSide)) {
          String _name_7 = fd.getName();
          String _plus_10 = (_name_7 + ".set(");
          String _name_8 = ft.getDomain().getName();
          String _plus_11 = (_plus_10 + _name_8);
          String _plus_12 = (_plus_11 + "_s);");
          functionTerm.append(_plus_12);
        }
      }
      return functionTerm.toString();
    }
    TupleTerm _arguments_1 = ft.getArguments();
    boolean _tripleNotEquals = (_arguments_1 != null);
    if (_tripleNotEquals) {
      int _size = ft.getArguments().getTerms().size();
      boolean _equals = (_size == 1);
      if (_equals) {
        Domain _domain_5 = ft.getDomain();
        if ((_domain_5 instanceof ConcreteDomain)) {
          if ((!this.leftHandSide)) {
            String _name_9 = fd.getName();
            String _plus_13 = (_name_9 + ".set(");
            String _visit = this.visit(ft.getArguments().getTerms().get(0));
            String _plus_14 = (_plus_13 + _visit);
            String _plus_15 = (_plus_14 + ", ");
            String _name_10 = ft.getDomain().getName();
            String _plus_16 = (_plus_15 + _name_10);
            String _plus_17 = (_plus_16 + "_s);");
            functionTerm.append(_plus_17);
          } else {
          }
        }
      }
      return functionTerm.toString();
    }
    return null;
  }
  
  public Boolean controllo(final Domain dom) {
    if ((dom instanceof ConcreteDomain)) {
      return Boolean.valueOf(true);
    } else {
      return Boolean.valueOf(false);
    }
  }
}
