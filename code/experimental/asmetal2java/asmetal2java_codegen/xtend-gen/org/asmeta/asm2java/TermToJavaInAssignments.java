package org.asmeta.asm2java;

import asmeta.definitions.ControlledFunction;
import asmeta.definitions.DerivedFunction;
import asmeta.definitions.Function;
import asmeta.definitions.MonitoredFunction;
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
import asmeta.terms.furtherterms.EnumTerm;
import asmeta.terms.furtherterms.IntegerTerm;
import asmeta.terms.furtherterms.NaturalTerm;
import asmeta.terms.furtherterms.StringTerm;
import java.util.Arrays;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;

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
    StringBuffer sb = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("= null;");
    sb.append(_builder);
    Domain _domain = object.getComparedTerm().getDomain();
    if ((_domain instanceof AbstractTd)) {
      for (int i = 0; (i < object.getComparingTerm().size()); i++) {
        if ((i == 0)) {
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append("\t");
          _builder_1.newLine();
          _builder_1.append("\tif(");
          String _visit = this.visit(object.getComparedTerm());
          _builder_1.append(_visit);
          _builder_1.append(".toString(");
          String _visit_1 = this.visit(object.getComparedTerm());
          _builder_1.append(_visit_1);
          _builder_1.append(").equals(\"");
          String _visit_2 = this.visit(object.getComparingTerm().get(i));
          _builder_1.append(_visit_2);
          _builder_1.append("\"))");
          _builder_1.newLineIfNotEmpty();
          _builder_1.append("\t\t");
          _builder_1.append("a  ");
          String _visit_3 = this.visit(object.getResultTerms().get(i));
          _builder_1.append(_visit_3, "\t\t");
          _builder_1.append(";");
          _builder_1.newLineIfNotEmpty();
          sb.append(_builder_1);
        } else {
          StringConcatenation _builder_2 = new StringConcatenation();
          _builder_2.append("\telse if(");
          String _visit_4 = this.visit(object.getComparedTerm());
          _builder_2.append(_visit_4);
          _builder_2.append(".toString(");
          String _visit_5 = this.visit(object.getComparedTerm());
          _builder_2.append(_visit_5);
          _builder_2.append(").equals(\"");
          String _visit_6 = this.visit(object.getComparingTerm().get(i));
          _builder_2.append(_visit_6);
          _builder_2.append("\"))");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("\t\t");
          _builder_2.append("a  ");
          String _visit_7 = this.visit(object.getResultTerms().get(i));
          _builder_2.append(_visit_7, "\t\t");
          _builder_2.append(";");
          _builder_2.newLineIfNotEmpty();
          sb.append(_builder_2);
        }
      }
    } else {
      for (int i = 0; (i < object.getComparingTerm().size()); i++) {
        if ((i == 0)) {
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append("\t");
          _builder_1.newLine();
          _builder_1.append("\tif(");
          String _name = object.getComparedTerm().getDomain().getName();
          _builder_1.append(_name);
          _builder_1.append("_elem.value.equals(");
          String _substring = this.visit(object.getComparingTerm().get(i)).substring(3, this.visit(object.getComparingTerm().get(i)).length());
          _builder_1.append(_substring);
          _builder_1.append("))");
          _builder_1.newLineIfNotEmpty();
          _builder_1.append("\t\t");
          _builder_1.append("a  ");
          String _visit = this.visit(object.getResultTerms().get(i));
          _builder_1.append(_visit, "\t\t");
          _builder_1.append(";");
          _builder_1.newLineIfNotEmpty();
          sb.append(_builder_1);
        } else {
          StringConcatenation _builder_2 = new StringConcatenation();
          _builder_2.append("\telse if(");
          String _name_1 = object.getComparedTerm().getDomain().getName();
          _builder_2.append(_name_1);
          _builder_2.append("_elem.value.equals(");
          String _substring_1 = this.visit(object.getComparingTerm().get(i)).substring(3, this.visit(object.getComparingTerm().get(i)).length());
          _builder_2.append(_substring_1);
          _builder_2.append("))");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("\t\t");
          _builder_2.append("a  ");
          String _visit_1 = this.visit(object.getResultTerms().get(i));
          _builder_2.append(_visit_1, "\t\t");
          _builder_2.append(";");
          _builder_2.newLineIfNotEmpty();
          sb.append(_builder_2);
        }
      }
    }
    Term _otherwiseTerm = object.getOtherwiseTerm();
    boolean _tripleNotEquals = (_otherwiseTerm != null);
    if (_tripleNotEquals) {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("\telse return ");
      String _visit = this.visit(object.getOtherwiseTerm());
      _builder_1.append(_visit);
      _builder_1.append("; ");
      _builder_1.newLineIfNotEmpty();
      sb.append(_builder_1);
    }
    return sb.toString();
  }

  @Override
  public String visit(final LocationTerm term) {
    return this.visit(((FunctionTerm) term));
  }

  @Override
  public String visit(final FunctionTerm term) {
    try {
      StringBuffer functionTerm = new StringBuffer();
      String name = new Util().parseFunction(term.getFunction().getName());
      boolean _hasEvaluateVisitor = ExpressionToJava.hasEvaluateVisitor(name);
      if (_hasEvaluateVisitor) {
        String _evaluateFunction = new ExpressionToJava(this.res).evaluateFunction(name, term.getArguments().getTerms());
        return ("=" + _evaluateFunction);
      } else {
        functionTerm.append(term.getFunction().getName());
        functionTerm.append(this.caseFunctionTermSupp(term.getFunction(), term));
        return functionTerm.toString();
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Override
  protected String _caseFunctionTermSupp(final ControlledFunction fd, final FunctionTerm ft) {
    StringBuffer functionTerm = new StringBuffer();
    TupleTerm _arguments = ft.getArguments();
    boolean _tripleEquals = (_arguments == null);
    if (_tripleEquals) {
      if (this.leftHandSide) {
        functionTerm.append("");
      } else {
        functionTerm.append("");
      }
    }
    TupleTerm _arguments_1 = ft.getArguments();
    boolean _tripleNotEquals = (_arguments_1 != null);
    if (_tripleNotEquals) {
      int _size = ft.getArguments().getTerms().size();
      boolean _equals = (_size == 1);
      if (_equals) {
        if (this.leftHandSide) {
          functionTerm.append("");
        } else {
          functionTerm.append("");
        }
      } else {
        functionTerm.append("[make_tuple(");
        for (int i = 0; (i < ft.getArguments().getTerms().size()); i++) {
          String _visit = this.visit(ft.getArguments().getTerms().get(i));
          String _plus = (_visit + ", ");
          functionTerm.append(_plus);
        }
        int _length = functionTerm.length();
        int _minus = (_length - 2);
        String _substring = functionTerm.substring(0, _minus);
        String _plus = (_substring + ")]");
        StringBuffer _stringBuffer = new StringBuffer(_plus);
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
