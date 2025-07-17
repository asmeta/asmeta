package org.asmeta.asm2code;

import asmeta.definitions.ControlledFunction;
import asmeta.definitions.DerivedFunction;
import asmeta.definitions.Function;
import asmeta.definitions.MonitoredFunction;
import asmeta.definitions.OutFunction;
import asmeta.definitions.StaticFunction;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.PowersetDomain;
import asmeta.structure.Asm;
import asmeta.terms.basicterms.BooleanTerm;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.LocationTerm;
import asmeta.terms.basicterms.SetTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.TupleTerm;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.terms.furtherterms.CaseTerm;
import asmeta.terms.furtherterms.ConditionalTerm;
import asmeta.terms.furtherterms.EnumTerm;
import asmeta.terms.furtherterms.ExistTerm;
import asmeta.terms.furtherterms.ForallTerm;
import asmeta.terms.furtherterms.IntegerTerm;
import asmeta.terms.furtherterms.LetTerm;
import asmeta.terms.furtherterms.MapTerm;
import asmeta.terms.furtherterms.NaturalTerm;
import asmeta.terms.furtherterms.RealTerm;
import asmeta.terms.furtherterms.SequenceTerm;
import asmeta.terms.furtherterms.SetCt;
import asmeta.terms.furtherterms.StringTerm;
import java.util.Arrays;
import java.util.Objects;
import org.asmeta.parser.util.ReflectiveVisitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;

@SuppressWarnings("all")
public class TermToCpp extends ReflectiveVisitor<String> {
  Integer numStaticParam;

  private Asm res;

  private boolean leftHandSide;

  public TermToCpp(final Asm resource) {
    this(resource, false);
  }

  public TermToCpp(final Asm resource, final boolean leftHandSide) {
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
    String _symbol = term.getSymbol();
    String _plus = ("\"" + _symbol);
    return (_plus + "\"");
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
    return term.getSymbol();
  }

  public String visit(final ConditionalTerm object) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("/*conditionalTerm*/");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("(�visit(object.guard)�)");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("?");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("�visit(object.thenTerm)�");
    _builder.newLine();
    _builder.append("\t");
    _builder.append(":");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("�visit(object.elseTerm)�");
    _builder.newLine();
    return _builder.toString();
  }

  public String visit(final CaseTerm object) {
    StringBuffer sb = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("[&](){      /*<--- caseTerm*/ ");
    _builder.newLine();
    sb.append(_builder);
    for (int i = 0; (i < object.getComparingTerm().size()); i++) {
      if ((i == 0)) {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("�\"\"�\tif(�visit(object.comparedTerm)�==�visit(object.comparingTerm.get(i))�) ");
        _builder_1.newLine();
        _builder_1.append("\t\t");
        _builder_1.append("return �visit(object.resultTerms.get(i))�;");
        _builder_1.newLine();
        sb.append(_builder_1);
      } else {
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("�\"\"�\telse if(�visit(object.comparedTerm)�==�visit(object.comparingTerm.get(i))�)");
        _builder_2.newLine();
        _builder_2.append("\t\t");
        _builder_2.append("return �visit(object.resultTerms.get(i))�;");
        _builder_2.newLine();
        sb.append(_builder_2);
      }
    }
    Term _otherwiseTerm = object.getOtherwiseTerm();
    boolean _tripleNotEquals = (_otherwiseTerm != null);
    if (_tripleNotEquals) {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("�\"\"�\telse return �visit(object.otherwiseTerm)�; ");
      _builder_1.newLine();
      sb.append(_builder_1);
    }
    StringConcatenation _builder_2 = new StringConcatenation();
    _builder_2.append("�\"\"�   }()");
    sb.append(_builder_2);
    return sb.toString();
  }

  public String visit(final TupleTerm object) {
    int _size = object.getTerms().size();
    boolean _equals = (_size == 0);
    if (_equals) {
      throw new RuntimeException("Error: a tuple term with size 0 has been found... why?? **BUG** ");
    }
    int _size_1 = object.getTerms().size();
    boolean _equals_1 = (_size_1 == 1);
    if (_equals_1) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("(�visit(object.terms.get(0))�)");
      return _builder.toString();
    }
    StringBuffer initial = new StringBuffer("make_tuple(");
    for (int i = 0; (i < object.getTerms().size()); i++) {
      String _visit = this.visit(object.getTerms().get(i));
      String _plus = (_visit + ", ");
      initial.append(_plus);
    }
    int _length = initial.length();
    int _minus = (_length - 2);
    String _substring = initial.substring(0, _minus);
    return (_substring + ")");
  }

  public String visit(final SequenceTerm object) {
    StringBuffer type = new StringBuffer("");
    type.append(new DomainToH(this.res).visit(object.getDomain()));
    StringBuffer list = new StringBuffer("{");
    for (int i = 0; (i < object.getTerms().size()); i++) {
      String _visit = this.visit(object.getTerms().get(i));
      String _plus = (_visit + ", ");
      list.append(_plus);
    }
    String s = null;
    int _length = list.length();
    boolean _equals = (_length == 1);
    if (_equals) {
      return (type + "");
    } else {
      int _length_1 = list.length();
      int _minus = (_length_1 - 2);
      String _substring = list.substring(0, _minus);
      String _plus = (_substring + "}");
      s = _plus;
    }
    return (type + s);
  }

  public String visit(final SetTerm object) {
    StringBuffer type = new StringBuffer("");
    type.append(new DomainToH(this.res).visit(object.getDomain()));
    String s = "";
    String _s = s;
    s = (_s + "{");
    if (((object.getTerm() != null) && (object.getTerm().size() > 0))) {
      EList<Term> _term = object.getTerm();
      for (final Term l : _term) {
        String _s_1 = s;
        String _visit = this.visit(l);
        String _plus = (_visit + ", ");
        s = (_s_1 + _plus);
      }
      int _length = s.length();
      int _minus = (_length - 2);
      s = s.substring(0, _minus);
    }
    String _s_2 = s;
    s = (_s_2 + "}");
    return (type + s);
  }

  /**
   * TODO: DELETE FOR COVERAGE 	def String visit(BagTerm object) {
   * var StringBuffer type = new StringBuffer("")
   * type.append(new DomainToH(res).visit(object.domain))
   * var StringBuffer bag = new StringBuffer("{")
   * for (var i = 0; i < object.term.size; i++)
   * bag.append(visit(object.term.get(i)) + ", ")
   * var s = bag.substring(0, bag.length - 2) + "}"
   * return type+s
   * return '''
   * �""�
   * 
   * [](){
   * auto v = �s�;
   * multiset<decltype(�visit(object.term.get(0))�)> ms(v.begin(), v.end());
   * return ms;
   * }()'''
   * }
   */
  public String visit(final MapTerm object) {
    StringBuffer map = new StringBuffer("{");
    for (int i = 0; (i < object.getPair().size()); i++) {
      String _visit = this.visit(object.getPair().get(i).getTerms().get(0));
      String _plus = ("make_pair(" + _visit);
      String _plus_1 = (_plus + ",");
      String _visit_1 = this.visit(object.getPair().get(i).getTerms().get(1));
      String _plus_2 = (_plus_1 + _visit_1);
      String _plus_3 = (_plus_2 + "), ");
      map.append(_plus_3);
    }
    int _length = map.length();
    int _minus = (_length - 2);
    String _substring = map.substring(0, _minus);
    String s = (_substring + "}");
    StringBuffer domain = new StringBuffer();
    for (int i = 0; (i < object.getPair().get(0).getTerms().size()); i++) {
      String _visit = new ToString(this.res).visit(object.getPair().get(0).getTerms().get(i).getDomain());
      String _plus = (_visit + ", ");
      domain.append(_plus);
    }
    int _size = object.getPair().size();
    boolean _equals = (_size == 0);
    if (_equals) {
      throw new RuntimeException("Empty map is not yet implemented");
    } else {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("�\"\"�");
      _builder.newLine();
      _builder.newLine();
      _builder.append("  ");
      _builder.append("[&](){");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("map<�domain.substring(0,domain.length-2)�> v = �s�;");
      _builder.newLine();
      _builder.append("   ");
      _builder.append("return v;");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("}()");
      return _builder.toString();
    }
  }

  public String visit(final ExistTerm object) {
    StringBuffer sb = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
    _builder.append("�\"\"�  [&]()->bool{ /*<--- ExistsTerm*/");
    _builder.newLine();
    sb.append(_builder);
    for (int i = 0; (i < object.getVariable().size()); i++) {
      Domain _domain = object.getRanges().get(i).getDomain();
      Domain _baseDomain = ((PowersetDomain) _domain).getBaseDomain();
      if ((_baseDomain instanceof AbstractTd)) {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("�\"\"�\tfor(auto �visit(object.variable.get(i))� : �new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)�::elems)");
        _builder_1.newLine();
        sb.append(_builder_1);
      } else {
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("�\"\"�\tfor(auto �visit(object.variable.get(i))� : �new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)�_elems)");
        _builder_2.newLine();
        sb.append(_builder_2);
      }
    }
    Term _guard = object.getGuard();
    boolean _tripleNotEquals = (_guard != null);
    if (_tripleNotEquals) {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("�\"\"�\tif(�visit(object.guard)�)");
      _builder_1.newLine();
      _builder_1.append("\t\t\t");
      _builder_1.append("return true;");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("return false;");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("}()");
      _builder_1.newLine();
      sb.append(_builder_1);
    }
    return sb.toString();
  }

  /**
   * TODO: DELETE FOR COVERAGE def String visit(ExistUniqueTerm object) {
   * return "TODO ExistTerm"
   * }
   */
  public String visit(final ForallTerm object) {
    StringBuffer sb = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
    _builder.append("�\"\"�  [&]()->bool{ /*<--- forAllTerm*/");
    _builder.newLine();
    sb.append(_builder);
    for (int i = 0; (i < object.getVariable().size()); i++) {
      Domain _domain = object.getRanges().get(i).getDomain();
      Domain _baseDomain = ((PowersetDomain) _domain).getBaseDomain();
      if ((_baseDomain instanceof AbstractTd)) {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("�\"\"�\tfor(auto �visit(object.variable.get(i))� : �new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)�::elems)");
        _builder_1.newLine();
        sb.append(_builder_1);
      } else {
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("�\"\"�\tfor(auto �visit(object.variable.get(i))� : �new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)�_elems)");
        _builder_2.newLine();
        sb.append(_builder_2);
      }
    }
    Term _guard = object.getGuard();
    boolean _tripleNotEquals = (_guard != null);
    if (_tripleNotEquals) {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("�\"\"�\tif(!(�visit(object.guard)�))");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("return false;");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("return true;");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("}()");
      _builder_1.newLine();
      sb.append(_builder_1);
    }
    return sb.toString();
  }

  /**
   * TODO: DELETE FOR COVERAGE 	def String visit(LetTerm object) {
   * var StringBuffer let = new StringBuffer
   * "		let.append(
   * '''
   * �"\n"�
   * [&](){    **<--- letTerm
   * ''')
   * for (var int i = 0; i < object.variable.size; i++) {
   * let.append('''	auto �visit(object.variable.get(i))� = �visit(object.assignmentTerm.get(i))�;
   * ''')
   * }
   * let.append(
   * '''
   * return �visit(object.body)�;
   * }()
   * ''')
   * 
   * return let.toString
   * }
   */
  public String visit(final SetCt term) {
    try {
      throw new Exception("SetCt not implemented");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  /**
   * TODO: DELETE FOR COVERAGE
   * def String visit(RuleAsTerm term) {
   * throw new Exception("RuleAsTerm not implemented");
   * }
   */
  public String visit(final LocationTerm term) {
    return this.visit(((FunctionTerm) term));
  }

  public String visit(final FunctionTerm term) {
    try {
      StringBuffer functionTerm = new StringBuffer();
      String name = new Util().parseFunction(term.getFunction().getName());
      boolean _hasEvaluateVisitor = ExpressionToCpp.hasEvaluateVisitor(name);
      if (_hasEvaluateVisitor) {
        return new ExpressionToCpp(this.res).evaluateFunction(name, term.getArguments().getTerms());
      } else {
        String _name = term.getFunction().getName();
        boolean _equals = Objects.equals(_name, "iton");
        if (_equals) {
          functionTerm.append("(unsigned int)");
          functionTerm.append(this.caseFunctionTermSupp(term.getFunction(), term));
        } else {
          String _name_1 = term.getFunction().getName();
          boolean _equals_1 = Objects.equals(_name_1, "length");
          if (_equals_1) {
            functionTerm.append(this.caseFunctionTermSupp(term.getFunction(), term));
            functionTerm.append(".size()");
          } else {
            String _name_2 = term.getFunction().getName();
            boolean _equals_2 = Objects.equals(_name_2, "at");
            if (_equals_2) {
              functionTerm.append(this.visit(term.getArguments().getTerms().get(0)));
              String _visit = this.visit(term.getArguments().getTerms().get(1));
              String _plus = ("[" + _visit);
              String _plus_1 = (_plus + "]");
              functionTerm.append(_plus_1);
            } else {
              String _name_3 = term.getFunction().getName();
              boolean _equals_3 = Objects.equals(_name_3, "rtoi");
              if (_equals_3) {
                functionTerm.append("(int)");
                functionTerm.append(this.caseFunctionTermSupp(term.getFunction(), term));
              } else {
                String _name_4 = term.getFunction().getName();
                boolean _equals_4 = Objects.equals(_name_4, "itor");
                if (_equals_4) {
                  functionTerm.append("(double)");
                  functionTerm.append(this.caseFunctionTermSupp(term.getFunction(), term));
                } else {
                  functionTerm.append(term.getFunction().getName());
                  functionTerm.append(this.caseFunctionTermSupp(term.getFunction(), term));
                }
              }
            }
          }
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
  protected String _caseFunctionTermSupp(final OutFunction fd, final FunctionTerm ft) {
    throw new RuntimeException("out funvtions not traslated yet");
  }

  protected String _caseFunctionTermSupp(final ControlledFunction fd, final FunctionTerm ft) {
    StringBuffer functionTerm = new StringBuffer();
    if (this.leftHandSide) {
      functionTerm.append("[1]");
    } else {
      functionTerm.append("[0]");
    }
    TupleTerm _arguments = ft.getArguments();
    boolean _tripleNotEquals = (_arguments != null);
    if (_tripleNotEquals) {
      int _size = ft.getArguments().getTerms().size();
      boolean _equals = (_size == 1);
      if (_equals) {
        String _visit = this.visit(ft.getArguments().getTerms().get(0));
        String _plus = ("[" + _visit);
        String _plus_1 = (_plus + "]");
        functionTerm.append(_plus_1);
      } else {
        functionTerm.append("[make_tuple(");
        for (int i = 0; (i < ft.getArguments().getTerms().size()); i++) {
          String _visit_1 = this.visit(ft.getArguments().getTerms().get(i));
          String _plus_2 = (_visit_1 + ", ");
          functionTerm.append(_plus_2);
        }
        int _length = functionTerm.length();
        int _minus = (_length - 2);
        String _substring = functionTerm.substring(0, _minus);
        String _plus_2 = (_substring + ")]");
        StringBuffer _stringBuffer = new StringBuffer(_plus_2);
        functionTerm = _stringBuffer;
      }
    }
    return functionTerm.toString();
  }

  protected String _caseFunctionTermSupp(final MonitoredFunction fd, final FunctionTerm ft) {
    StringBuffer functionTerm = new StringBuffer();
    TupleTerm _arguments = ft.getArguments();
    boolean _tripleNotEquals = (_arguments != null);
    if (_tripleNotEquals) {
      int _size = ft.getArguments().getTerms().size();
      boolean _equals = (_size == 1);
      if (_equals) {
        String _visit = this.visit(ft.getArguments().getTerms().get(0));
        String _plus = ("[" + _visit);
        String _plus_1 = (_plus + "]");
        functionTerm.append(_plus_1);
      } else {
        functionTerm.append("[make_tuple(");
        for (int i = 0; (i < ft.getArguments().getTerms().size()); i++) {
          String _visit_1 = this.visit(ft.getArguments().getTerms().get(i));
          String _plus_2 = (_visit_1 + ", ");
          functionTerm.append(_plus_2);
        }
        int _length = functionTerm.length();
        int _minus = (_length - 2);
        String _substring = functionTerm.substring(0, _minus);
        String _plus_2 = (_substring + ")]");
        StringBuffer _stringBuffer = new StringBuffer(_plus_2);
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
      }
    }
    return functionTerm.toString();
  }

  /**
   * def String visit(LetTerm term) {
   * var StringBuffer letTerm = new StringBuffer
   * letTerm.append("//  use a local variable \n")
   * letTerm.append("int ").append(visit(term.variable.get(0))).append("\n");
   * letTerm.append(visit(term.body))
   * return letTerm.toString
   * }
   */
  public String visit(final LetTerm term) {
    StringBuffer letTerm = new StringBuffer();
    for (int i = 0; (i < ((Object[])Conversions.unwrapArray(term.getVariable(), Object.class)).length); i++) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(" ");
      _builder.append("auto �visit(term.variable.get(i))� = �visit(term.assignmentTerm.get(i))�; ");
      _builder.newLine();
      letTerm.append(_builder);
    }
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("return �visit(term.body)� ");
    letTerm.append(_builder);
    return letTerm.toString();
  }

  public String visit(final RealTerm rt) {
    return rt.getSymbol();
  }

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
