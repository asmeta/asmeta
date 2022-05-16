package org.asmeta.asm2java;

import asmeta.definitions.ControlledFunction;
import asmeta.definitions.DerivedFunction;
import asmeta.definitions.Function;
import asmeta.definitions.MonitoredFunction;
import asmeta.definitions.StaticFunction;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.MapDomain;
import asmeta.definitions.domains.PowersetDomain;
import asmeta.definitions.domains.SequenceDomain;
import asmeta.structure.Asm;
import asmeta.terms.basicterms.BooleanTerm;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.LocationTerm;
import asmeta.terms.basicterms.RuleAsTerm;
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
import asmeta.terms.furtherterms.SequenceCt;
import asmeta.terms.furtherterms.SequenceTerm;
import asmeta.terms.furtherterms.SetCt;
import asmeta.terms.furtherterms.StringTerm;
import java.util.Arrays;
import org.asmeta.parser.util.ReflectiveVisitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class TermToJava extends ReflectiveVisitor<String> {
  Integer numStaticParam;
  
  private Asm res;
  
  private boolean leftHandSide;
  
  public TermToJava(final Asm resource) {
    this(resource, false);
  }
  
  public TermToJava(final Asm resource, final boolean leftHandSide) {
    this.res = resource;
    this.leftHandSide = leftHandSide;
  }
  
  public String visit(final VariableTerm term) {
    return term.getName();
  }
  
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
  
  public String visit(final EnumTerm term) {
    String _name = term.getDomain().getName();
    String _plus = (_name + ".");
    String _symbol = term.getSymbol();
    return (_plus + _symbol);
  }
  
  public String visit(final ConditionalTerm object) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("/*conditionalTerm*/");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("(");
    String _visit = this.visit(object.getGuard());
    _builder.append(_visit, "\t");
    _builder.append(")");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("?");
    _builder.newLine();
    _builder.append("\t\t");
    String _visit_1 = this.visit(object.getThenTerm());
    _builder.append(_visit_1, "\t\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append(":");
    _builder.newLine();
    _builder.append("\t\t");
    String _visit_2 = this.visit(object.getElseTerm());
    _builder.append(_visit_2, "\t\t");
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }
  
  public String visit(final CaseTerm object) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; (i < object.getComparingTerm().size()); i++) {
      if ((i == 0)) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("\tif(");
        String _visit = this.visit(object.getComparedTerm());
        _builder.append(_visit);
        _builder.append("==");
        String _visit_1 = this.visit(object.getComparingTerm().get(i));
        _builder.append(_visit_1);
        _builder.append(") ");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append("return ");
        String _visit_2 = this.visit(object.getResultTerms().get(i));
        _builder.append(_visit_2, "\t\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        sb.append(_builder);
      } else {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("\telse if(");
        String _visit_3 = this.visit(object.getComparedTerm());
        _builder_1.append(_visit_3);
        _builder_1.append("==");
        String _visit_4 = this.visit(object.getComparingTerm().get(i));
        _builder_1.append(_visit_4);
        _builder_1.append(")");
        _builder_1.newLineIfNotEmpty();
        _builder_1.append("\t\t");
        _builder_1.append("return ");
        String _visit_5 = this.visit(object.getResultTerms().get(i));
        _builder_1.append(_visit_5, "\t\t");
        _builder_1.append(";");
        _builder_1.newLineIfNotEmpty();
        sb.append(_builder_1);
      }
    }
    Term _otherwiseTerm = object.getOtherwiseTerm();
    boolean _tripleNotEquals = (_otherwiseTerm != null);
    if (_tripleNotEquals) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("\telse return ");
      String _visit = this.visit(object.getOtherwiseTerm());
      _builder.append(_visit);
      _builder.append("; ");
      _builder.newLineIfNotEmpty();
      sb.append(_builder);
    }
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append(" ");
    _builder_1.append("return null;");
    _builder_1.newLine();
    _builder_1.append("\t\t");
    _builder_1.append("   ");
    sb.append(_builder_1);
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
      _builder.append("(");
      String _visit = this.visit(object.getTerms().get(0));
      _builder.append(_visit);
      _builder.append(")");
      return _builder.toString();
    }
    StringBuffer initial = new StringBuffer("make_tuple(");
    for (int i = 0; (i < object.getTerms().size()); i++) {
      String _visit_1 = this.visit(object.getTerms().get(i));
      String _plus = (_visit_1 + ", ");
      initial.append(_plus);
    }
    int _length = initial.length();
    int _minus = (_length - 2);
    String _substring = initial.substring(0, _minus);
    return (_substring + ")");
  }
  
  public String visit(final SequenceTerm object) {
    StringBuffer list = new StringBuffer("");
    for (int i = 0; (i < object.getTerms().size()); i++) {
      int _size = object.getTerms().size();
      int _minus = (_size - 1);
      boolean _equals = (i == _minus);
      if (_equals) {
        list.append(this.visit(object.getTerms().get(i)));
      } else {
        String _visit = this.visit(object.getTerms().get(i));
        String _plus = (_visit + ", ");
        list.append(_plus);
      }
    }
    list.append(")");
    return list.toString();
  }
  
  public String visit(final SetTerm object) {
    StringBuffer type = new StringBuffer("");
    String s = "";
    String _s = s;
    s = (_s + "(");
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
    s = (_s_2 + ")");
    return (type + s);
  }
  
  public String visit(final MapTerm object) {
    StringBuffer map = new StringBuffer("{{\n");
    for (int i = 0; (i < object.getPair().size()); i++) {
      String _visit = this.visit(object.getPair().get(i).getTerms().get(0));
      String _plus = ("put(" + _visit);
      String _plus_1 = (_plus + ",");
      String _visit_1 = this.visit(object.getPair().get(i).getTerms().get(1));
      String _plus_2 = (_plus_1 + _visit_1);
      String _plus_3 = (_plus_2 + ");\n     ");
      map.append(_plus_3);
    }
    int _length = map.length();
    int _minus = (_length - 2);
    String _substring = map.substring(0, _minus);
    String s = (_substring + "}}");
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
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("  ");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("Map<");
      int _length_1 = domain.length();
      int _minus_1 = (_length_1 - 2);
      String _substring_1 = domain.substring(0, _minus_1);
      _builder.append(_substring_1, "    ");
      _builder.append("> supporto = new HashMap<");
      int _length_2 = domain.length();
      int _minus_2 = (_length_2 - 2);
      String _substring_2 = domain.substring(0, _minus_2);
      _builder.append(_substring_2, "    ");
      _builder.append(">()");
      _builder.newLineIfNotEmpty();
      _builder.append("    ");
      _builder.append(s, "    ");
      _builder.append(";");
      _builder.newLineIfNotEmpty();
      _builder.append("   ");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("//");
      return _builder.toString();
    }
  }
  
  public String visit(final ExistTerm object) {
    StringBuffer sb = new StringBuffer();
    StringBuffer app = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    String _visit = this.visit(object.getGuard());
    _builder.append(_visit);
    app.append(_builder);
    int limiteS = app.toString().indexOf(")");
    int partenza = app.toString().indexOf("(");
    if ((partenza == 0)) {
      partenza = 1;
    } else {
      partenza = 0;
    }
    String valore = app.substring(partenza, (limiteS - 2));
    for (int i = 0; (i < object.getVariable().size()); i++) {
      Domain _domain = object.getRanges().get(i).getDomain();
      Domain _baseDomain = ((PowersetDomain) _domain).getBaseDomain();
      if ((_baseDomain instanceof AbstractTd)) {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("\t");
        Domain _domain_1 = object.getRanges().get(i).getDomain();
        String _visit_1 = new ToString(this.res).visit(((PowersetDomain) _domain_1).getBaseDomain());
        _builder_1.append(_visit_1);
        _builder_1.append(".elems.stream().anyMatch(c -> c.ToString(c).equals(");
        int _length = app.length();
        int _minus = (_length - 1);
        String _substring = app.substring(7, _minus);
        _builder_1.append(_substring);
        _builder_1.append(".ToString(c)))");
        _builder_1.newLineIfNotEmpty();
        sb.append(_builder_1);
      } else {
        Domain _domain_2 = object.getRanges().get(i).getDomain();
        Domain _baseDomain_1 = ((PowersetDomain) _domain_2).getBaseDomain();
        if ((_baseDomain_1 instanceof EnumTd)) {
          StringConcatenation _builder_2 = new StringConcatenation();
          _builder_2.append("\t");
          Domain _domain_3 = object.getRanges().get(i).getDomain();
          String _visit_2 = new ToString(this.res).visit(((PowersetDomain) _domain_3).getBaseDomain());
          _builder_2.append(_visit_2);
          _builder_2.append("_lista.stream().anyMatch(c -> ");
          _builder_2.append(valore);
          _builder_2.append("c))");
          _builder_2.newLineIfNotEmpty();
          sb.append(_builder_2);
        } else {
          StringConcatenation _builder_3 = new StringConcatenation();
          _builder_3.append("\t");
          Domain _domain_4 = object.getRanges().get(i).getDomain();
          String _visit_3 = new ToString(this.res).visit(((PowersetDomain) _domain_4).getBaseDomain());
          _builder_3.append(_visit_3);
          _builder_3.append(".elems.stream().anyMatch(c -> c.equals(");
          int _length_1 = app.length();
          int _minus_1 = (_length_1 - 1);
          String _substring_1 = app.substring(13, _minus_1);
          _builder_3.append(_substring_1);
          _builder_3.append("))");
          _builder_3.newLineIfNotEmpty();
          sb.append(_builder_3);
        }
      }
    }
    return sb.toString();
  }
  
  public String visit(final ForallTerm object) {
    StringBuffer sb = new StringBuffer();
    StringBuffer supp = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    String _visit = this.visit(object.getGuard());
    _builder.append(_visit);
    supp.append(_builder);
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.newLine();
    _builder_1.append("  /*<--- forAllTerm*/");
    _builder_1.newLineIfNotEmpty();
    sb.append(_builder_1);
    for (int i = 0; (i < object.getVariable().size()); i++) {
      Domain _domain = object.getRanges().get(i).getDomain();
      Domain _baseDomain = ((PowersetDomain) _domain).getBaseDomain();
      if ((_baseDomain instanceof AbstractTd)) {
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("\tfor(auto ");
        String _visit_1 = this.visit(object.getVariable().get(i));
        _builder_2.append(_visit_1);
        _builder_2.append(" : ");
        Domain _domain_1 = object.getRanges().get(i).getDomain();
        String _visit_2 = new ToString(this.res).visit(((PowersetDomain) _domain_1).getBaseDomain());
        _builder_2.append(_visit_2);
        _builder_2.append("::elems)");
        _builder_2.newLineIfNotEmpty();
        sb.append(_builder_2);
      } else {
        StringConcatenation _builder_3 = new StringConcatenation();
        _builder_3.append("\t");
        Domain _domain_2 = object.getRanges().get(i).getDomain();
        String _visit_3 = new ToString(this.res).visit(((PowersetDomain) _domain_2).getBaseDomain());
        _builder_3.append(_visit_3);
        _builder_3.append("_lista.stream().allMatch(c -> ");
        int _length = supp.length();
        int _minus = (_length - 3);
        String _substring = supp.substring(0, _minus);
        _builder_3.append(_substring);
        _builder_3.append("c));");
        _builder_3.newLineIfNotEmpty();
        sb.append(_builder_3);
      }
    }
    return sb.toString();
  }
  
  public String visit(final LetTerm object) {
    StringBuffer let = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("\n");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append("[&](){    **<--- letTerm**");
    _builder.newLine();
    let.append(_builder);
    for (int i = 0; (i < object.getVariable().size()); i++) {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("\t");
      _builder_1.append("auto ");
      String _visit = this.visit(object.getVariable().get(i));
      _builder_1.append(_visit, "\t");
      _builder_1.append(" = ");
      String _visit_1 = this.visit(object.getAssignmentTerm().get(i));
      _builder_1.append(_visit_1, "\t");
      _builder_1.append(";");
      _builder_1.newLineIfNotEmpty();
      let.append(_builder_1);
    }
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("return ");
    String _visit = this.visit(object.getBody());
    _builder_1.append(_visit);
    _builder_1.append("; ");
    _builder_1.newLineIfNotEmpty();
    _builder_1.append("}()");
    _builder_1.newLine();
    let.append(_builder_1);
    return let.toString();
  }
  
  public String visit(final SetCt term) {
    try {
      throw new Exception("SetCt not implemented");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public String visit(final SequenceCt object) {
    StringBuffer seq = new StringBuffer();
    seq.append("caso sequenza da trattare \n\n");
    return seq.toString();
  }
  
  public String visit(final RuleAsTerm term) {
    return "RuleAsRTerm";
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
        if ((_domain_1 instanceof SequenceDomain)) {
          functionTerm.append("_elem = Collections.unmodifiableList(Arrays.asList(");
        } else {
          Domain _domain_2 = ft.getDomain();
          if ((_domain_2 instanceof MapDomain)) {
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
    }
    TupleTerm _arguments_1 = ft.getArguments();
    boolean _tripleNotEquals = (_arguments_1 != null);
    if (_tripleNotEquals) {
      int _size = ft.getArguments().getTerms().size();
      boolean _equals = (_size == 1);
      if (_equals) {
        Domain _domain_3 = ft.getDomain();
        if ((_domain_3 instanceof ConcreteDomain)) {
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
