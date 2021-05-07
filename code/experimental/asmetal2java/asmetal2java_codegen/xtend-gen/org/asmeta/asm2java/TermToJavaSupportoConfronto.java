package org.asmeta.asm2java;

import asmeta.definitions.ControlledFunction;
import asmeta.definitions.DerivedFunction;
import asmeta.definitions.Function;
import asmeta.definitions.MonitoredFunction;
import asmeta.definitions.StaticFunction;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.MapDomain;
import asmeta.structure.Asm;
import asmeta.terms.basicterms.BooleanTerm;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.LocationTerm;
import asmeta.terms.basicterms.TupleTerm;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.terms.furtherterms.EnumTerm;
import asmeta.terms.furtherterms.IntegerTerm;
import asmeta.terms.furtherterms.NaturalTerm;
import asmeta.terms.furtherterms.StringTerm;
import java.util.Arrays;
import org.asmeta.asm2java.ExpressionToJava;
import org.asmeta.asm2java.Util;
import org.asmeta.parser.util.ReflectiveVisitor;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class TermToJavaSupportoConfronto extends ReflectiveVisitor<String> {
  Integer numStaticParam;
  
  private Asm res;
  
  private boolean leftHandSide;
  
  public TermToJavaSupportoConfronto(final Asm resource) {
    this(resource, false);
  }
  
  public TermToJavaSupportoConfronto(final Asm resource, final boolean leftHandSide) {
    this.res = resource;
    this.leftHandSide = leftHandSide;
  }
  
  public String visit(final VariableTerm term) {
    Domain _domain = term.getDomain();
    if ((_domain instanceof ConcreteDomain)) {
      String _name = term.getName();
      return (_name + ".value");
    } else {
      return term.getName();
    }
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
  public String visit(final IntegerTerm term) {
    return term.getSymbol();
  }
  
  public String visit(final NaturalTerm term) {
    String _symbol = term.getSymbol();
    int _length = term.getSymbol().length();
    int _minus = (_length - 1);
    return _symbol.substring(0, _minus);
  }
  
  public String visit(final StringTerm term) {
    String supp = new String("\"");
    String _symbol = term.getSymbol();
    String _plus = (supp + _symbol);
    return (_plus + supp);
  }
  
  public String visit(final BooleanTerm term) {
    return term.getSymbol();
  }
  
  /**
   * TODO: DELETE FOR COVERAGE 	def String visit(UndefTerm term) {
   * throw new Exception("Undefined term not supported");
   * }
   */
  public String visit(final EnumTerm term) {
    String _name = term.getDomain().getName();
    String _plus = (_name + ".");
    String _symbol = term.getSymbol();
    return (_plus + _symbol);
  }
  
  public String visit(final LocationTerm term) {
    return this.visit(((FunctionTerm) term));
  }
  
  public String visit(final FunctionTerm term) {
    try {
      StringBuffer functionTerm = new StringBuffer();
      String name = new Util().parseFunction(term.getFunction().getName());
      boolean _hasEvaluateVisitor = ExpressionToJava.hasEvaluateVisitor(name);
      if (_hasEvaluateVisitor) {
        return new ExpressionToJava(this.res).evaluateFunction(name, term.getArguments().getTerms());
      } else {
        if (((term.getFunction() instanceof ControlledFunction) && (term.getDomain() instanceof ConcreteDomain))) {
          functionTerm.append(this.caseFunctionTermSuppCont(term.getFunction(), term));
        }
        if (((term.getFunction() instanceof ControlledFunction) && (term.getDomain() instanceof MapDomain))) {
          functionTerm.append(this.caseFunctionTermSuppCont(term.getFunction(), term));
        }
        functionTerm.append(term.getFunction().getName());
        functionTerm.append(this.caseFunctionTermSupp(term.getFunction(), term));
        if (((term.getFunction() instanceof ControlledFunction) && (term.getDomain() instanceof ConcreteDomain))) {
          functionTerm.append("\n");
        }
        return functionTerm.toString();
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
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
  public String caseFunctionTermSuppCont(final Function fd, final FunctionTerm ft) {
    StringBuffer functionTerm = new StringBuffer();
    TupleTerm _arguments = ft.getArguments();
    boolean _tripleEquals = (_arguments == null);
    if (_tripleEquals) {
      Domain _domain = ft.getDomain();
      if ((_domain instanceof ConcreteDomain)) {
        if (this.leftHandSide) {
          this.leftHandSide = false;
          String _name = ft.getDomain().getName();
          String _plus = (_name + " ");
          String _name_1 = ft.getDomain().getName();
          String _plus_1 = (_plus + _name_1);
          String _plus_2 = (_plus_1 + "_s = new ");
          String _name_2 = ft.getDomain().getName();
          String _plus_3 = (_plus_2 + _name_2);
          String _plus_4 = (_plus_3 + "();\n");
          functionTerm.append(_plus_4);
          String _name_3 = ft.getDomain().getName();
          String _plus_5 = (_name_3 + "_s.value = (//");
          functionTerm.append(_plus_5);
        }
      }
      Domain _domain_1 = ft.getDomain();
      if ((_domain_1 instanceof MapDomain)) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("@SuppressWarnings(\"serial\") //");
        functionTerm.append(_builder);
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
          if (this.leftHandSide) {
            this.leftHandSide = false;
            String _name_4 = ft.getDomain().getName();
            String _plus_6 = (_name_4 + " ");
            String _name_5 = ft.getDomain().getName();
            String _plus_7 = (_plus_6 + _name_5);
            String _plus_8 = (_plus_7 + "_s = new ");
            String _name_6 = ft.getDomain().getName();
            String _plus_9 = (_plus_8 + _name_6);
            String _plus_10 = (_plus_9 + "();\n");
            functionTerm.append(_plus_10);
            String _name_7 = ft.getDomain().getName();
            String _plus_11 = (_name_7 + "_s.value = (//");
            functionTerm.append(_plus_11);
          }
        }
      } else {
        if ((fd instanceof ControlledFunction)) {
          if (this.leftHandSide) {
            String _name_8 = ((ControlledFunction)fd).getName();
            String _plus_12 = (_name_8 + "_elem = null;\n");
            functionTerm.append(_plus_12);
            for (int i = 0; (i < ((ControlledFunction)fd).getInitialization().get(0).getVariable().size()); i++) {
              String _name_9 = ((ControlledFunction)fd).getInitialization().get(0).getVariable().get(i).getDomain().getName();
              String _plus_13 = (_name_9 + "_elem.value = ");
              String _visit = this.visit(ft.getArguments().getTerms().get(i));
              String _plus_14 = (_plus_13 + _visit);
              String _plus_15 = (_plus_14 + ";\n");
              functionTerm.append(_plus_15);
            }
            String _name_9 = ((ControlledFunction)fd).getName();
            String _plus_13 = (_name_9 + "_elem = new ");
            functionTerm.append(_plus_13);
            int _size_1 = ((ControlledFunction)fd).getInitialization().get(0).getVariable().size();
            switch (_size_1) {
              case 2:
                functionTerm.append("Pair<");
                break;
              case 3:
                functionTerm.append("Triplet<");
                break;
              case 4:
                functionTerm.append("Quartet<");
                break;
              case 5:
                functionTerm.append("Quintet<");
                break;
              case 6:
                functionTerm.append("Sextet<");
                break;
              case 7:
                functionTerm.append("Septet<");
                break;
              case 8:
                functionTerm.append("Octet<");
                break;
              case 9:
                functionTerm.append("Ennead<");
                break;
              case 10:
                functionTerm.append("Decade<");
                break;
            }
            for (int i = 0; (i < ((ControlledFunction)fd).getInitialization().get(0).getVariable().size()); i++) {
              int _size_2 = ((ControlledFunction)fd).getInitialization().get(0).getVariable().size();
              int _minus = (_size_2 - 1);
              boolean _notEquals = (i != _minus);
              if (_notEquals) {
                String _name_10 = ((ControlledFunction)fd).getInitialization().get(0).getVariable().get(i).getDomain().getName();
                String _plus_14 = (_name_10 + ",");
                functionTerm.append(_plus_14);
              } else {
                String _name_11 = ((ControlledFunction)fd).getInitialization().get(0).getVariable().get(i).getDomain().getName();
                String _plus_15 = (_name_11 + ">(");
                functionTerm.append(_plus_15);
              }
            }
            for (int i = 0; (i < ((ControlledFunction)fd).getInitialization().get(0).getVariable().size()); i++) {
              int _size_2 = ((ControlledFunction)fd).getInitialization().get(0).getVariable().size();
              int _minus = (_size_2 - 1);
              boolean _notEquals = (i != _minus);
              if (_notEquals) {
                String _name_10 = ((ControlledFunction)fd).getInitialization().get(0).getVariable().get(i).getDomain().getName();
                String _plus_14 = (_name_10 + "_elem,");
                functionTerm.append(_plus_14);
              } else {
                String _name_11 = ((ControlledFunction)fd).getInitialization().get(0).getVariable().get(i).getDomain().getName();
                String _plus_15 = (_name_11 + "_elem);\n");
                functionTerm.append(_plus_15);
              }
            }
            String _name_10 = ft.getDomain().getName();
            String _plus_14 = (_name_10 + " sup = new ");
            String _name_11 = ft.getDomain().getName();
            String _plus_15 = (_plus_14 + _name_11);
            String _plus_16 = (_plus_15 + "();\n");
            functionTerm.append(_plus_16);
            functionTerm.append("sup.value = (//");
          } else {
            functionTerm.append("true))\n");
            functionTerm.append("System.out.println();\n");
            String _name_12 = ((ControlledFunction)fd).getName();
            String _plus_17 = (_name_12 + "_elem = null;\n");
            functionTerm.append(_plus_17);
            for (int i = 0; (i < ((ControlledFunction)fd).getInitialization().get(0).getVariable().size()); i++) {
              String _name_13 = ((ControlledFunction)fd).getInitialization().get(0).getVariable().get(i).getDomain().getName();
              String _plus_18 = (_name_13 + "_elem.value = ");
              String _visit = this.visit(ft.getArguments().getTerms().get(i));
              String _plus_19 = (_plus_18 + _visit);
              String _plus_20 = (_plus_19 + ";\n");
              functionTerm.append(_plus_20);
            }
            String _name_13 = ((ControlledFunction)fd).getName();
            String _plus_18 = (_name_13 + "_elem = new ");
            functionTerm.append(_plus_18);
            int _size_2 = ((ControlledFunction)fd).getInitialization().get(0).getVariable().size();
            switch (_size_2) {
              case 2:
                functionTerm.append("Pair<");
                break;
              case 3:
                functionTerm.append("Triplet<");
                break;
              case 4:
                functionTerm.append("Quartet<");
                break;
              case 5:
                functionTerm.append("Quintet<");
                break;
              case 6:
                functionTerm.append("Sextet<");
                break;
              case 7:
                functionTerm.append("Septet<");
                break;
              case 8:
                functionTerm.append("Octet<");
                break;
              case 9:
                functionTerm.append("Ennead<");
                break;
              case 10:
                functionTerm.append("Decade<");
                break;
            }
            for (int i = 0; (i < ((ControlledFunction)fd).getInitialization().get(0).getVariable().size()); i++) {
              int _size_3 = ((ControlledFunction)fd).getInitialization().get(0).getVariable().size();
              int _minus = (_size_3 - 1);
              boolean _notEquals = (i != _minus);
              if (_notEquals) {
                String _name_14 = ((ControlledFunction)fd).getInitialization().get(0).getVariable().get(i).getDomain().getName();
                String _plus_19 = (_name_14 + ",");
                functionTerm.append(_plus_19);
              } else {
                String _name_15 = ((ControlledFunction)fd).getInitialization().get(0).getVariable().get(i).getDomain().getName();
                String _plus_20 = (_name_15 + ">(");
                functionTerm.append(_plus_20);
              }
            }
            for (int i = 0; (i < ((ControlledFunction)fd).getInitialization().get(0).getVariable().size()); i++) {
              int _size_3 = ((ControlledFunction)fd).getInitialization().get(0).getVariable().size();
              int _minus = (_size_3 - 1);
              boolean _notEquals = (i != _minus);
              if (_notEquals) {
                String _name_14 = ((ControlledFunction)fd).getInitialization().get(0).getVariable().get(i).getDomain().getName();
                String _plus_19 = (_name_14 + "_elem,");
                functionTerm.append(_plus_19);
              } else {
                String _name_15 = ((ControlledFunction)fd).getInitialization().get(0).getVariable().get(i).getDomain().getName();
                String _plus_20 = (_name_15 + "_elem);\n");
                functionTerm.append(_plus_20);
              }
            }
            String _name_14 = ((ControlledFunction)fd).getName();
            String _plus_19 = ("if((" + _name_14);
            String _plus_20 = (_plus_19 + ".get(");
            String _name_15 = ((ControlledFunction)fd).getName();
            String _plus_21 = (_plus_20 + _name_15);
            String _plus_22 = (_plus_21 + "_elem).value //");
            functionTerm.append(_plus_22);
          }
        }
      }
    }
    return functionTerm.toString();
  }
  
  protected String _caseFunctionTermSupp(final ControlledFunction fd, final FunctionTerm ft) {
    StringBuffer functionTerm = new StringBuffer();
    TupleTerm _arguments = ft.getArguments();
    boolean _tripleEquals = (_arguments == null);
    if (_tripleEquals) {
      Domain _domain = ft.getDomain();
      if ((_domain instanceof ConcreteDomain)) {
        if ((!this.leftHandSide)) {
          functionTerm.append(".get()");
          Boolean _controllo = this.controllo(fd.getCodomain());
          if ((_controllo).booleanValue()) {
            functionTerm.append(".value");
          }
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
            Boolean _controllo_1 = this.controllo(fd.getCodomain());
            if ((_controllo_1).booleanValue()) {
              functionTerm.append(".value");
            }
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
            Boolean _controllo_2 = this.controllo(fd.getCodomain());
            if ((_controllo_2).booleanValue()) {
              functionTerm.append(".value");
            }
          }
        } else {
          if (this.leftHandSide) {
            this.leftHandSide = false;
            String _visit_1 = this.visit(ft.getArguments().getTerms().get(0));
            String _plus_2 = (".set(" + _visit_1);
            String _plus_3 = (_plus_2 + ", ");
            functionTerm.append(_plus_3);
          } else {
            String _visit_2 = this.visit(ft.getArguments().getTerms().get(0));
            String _plus_4 = (".get(" + _visit_2);
            String _plus_5 = (_plus_4 + ")");
            functionTerm.append(_plus_5);
            Boolean _controllo_3 = this.controllo(fd.getCodomain());
            if ((_controllo_3).booleanValue()) {
              functionTerm.append(".value");
            }
          }
        }
      } else {
      }
    }
    return functionTerm.toString();
  }
  
  protected String _caseFunctionTermSupp(final MonitoredFunction fd, final FunctionTerm ft) {
    StringBuffer functionTerm = new StringBuffer();
    TupleTerm _arguments = ft.getArguments();
    boolean _tripleEquals = (_arguments == null);
    if (_tripleEquals) {
      if (this.leftHandSide) {
        functionTerm.append(".set(");
      } else {
        functionTerm.append(".get()");
        Boolean _controllo = this.controllo(fd.getCodomain());
        if ((_controllo).booleanValue()) {
          functionTerm.append(".value");
        }
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
          Boolean _controllo_1 = this.controllo(fd.getCodomain());
          if ((_controllo_1).booleanValue()) {
            functionTerm.append(".value");
          }
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
  
  protected String _caseFunctionTermSupp(final DerivedFunction fd, final FunctionTerm ft) {
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
        Domain _codomain = fd.getCodomain();
        if ((_codomain instanceof ConcreteDomain)) {
          functionTerm.append(".value");
        }
      }
    }
    return functionTerm.toString();
  }
  
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
        Domain _codomain = fd.getCodomain();
        if ((_codomain instanceof ConcreteDomain)) {
          functionTerm.append(".value");
        }
      }
    }
    return functionTerm.toString();
  }
  
  public Boolean controllo(final Domain dom) {
    if ((dom instanceof ConcreteDomain)) {
      return Boolean.valueOf(true);
    } else {
      return Boolean.valueOf(false);
    }
  }
  
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
