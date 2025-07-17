package org.asmeta.asm2code;

import asmeta.definitions.ControlledFunction;
import asmeta.definitions.DerivedFunction;
import asmeta.definitions.MonitoredFunction;
import asmeta.definitions.StaticFunction;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.Domain;
import asmeta.structure.Asm;
import asmeta.terms.basicterms.VariableTerm;
import org.asmeta.asm2code.main.TranslatorOptions;
import org.asmeta.parser.util.ReflectiveVisitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class FunctionToCpp extends ReflectiveVisitor<String> {
  private Asm asm;

  private TranslatorOptions options;

  private int i;

  public FunctionToCpp(final Asm asm) {
    this.asm = asm;
    this.options = null;
  }

  public FunctionToCpp(final Asm asm, final TranslatorOptions options) {
    this.asm = asm;
    this.options = options;
  }

  /**
   * def String visit(ControlledFunction object) {
   * 	if (object.domain!==null)
   * 		return "map<" + new FunctionToH(asm).returnDomain(object.domain) +", " + new FunctionToH(asm).returnDomain(object.codomain) + ">{{" + new TermToCpp(asm).visit(object.initialization.get(0).body) + "}}"
   * 	else
   * 		return new TermToCpp(asm).visit(object.initialization.get(0).body)
   * }
   */
  public String visit(final ControlledFunction object) {
    StringBuffer sb = new StringBuffer();
    Domain _domain = object.getDomain();
    boolean _tripleNotEquals = (_domain != null);
    if (_tripleNotEquals) {
      for (int i = 0; (i < object.getInitialization().get(0).getVariable().size()); i++) {
        boolean _isNotNumerable = new Util().isNotNumerable(object.getInitialization().get(0).getVariable().get(i).getDomain());
        if (_isNotNumerable) {
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("//NOT IMPLEMENTED IN C++ (FunctionToCpp line 50)");
          _builder.newLine();
          sb.append(_builder);
        } else {
          Domain _domain_1 = object.getInitialization().get(0).getVariable().get(i).getDomain();
          if ((_domain_1 instanceof AbstractTd)) {
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.append("for(const auto& ");
            String _visit = new TermToCpp(this.asm).visit(object.getInitialization().get(0).getVariable().get(i));
            _builder_1.append(_visit);
            _builder_1.append(" : ");
            String _visit_1 = new ToString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
            _builder_1.append(_visit_1);
            _builder_1.append("::elems){");
            _builder_1.newLineIfNotEmpty();
            sb.append(_builder_1);
          } else {
            StringConcatenation _builder_2 = new StringConcatenation();
            _builder_2.append("for(auto const& ");
            String _visit_2 = new TermToCpp(this.asm).visit(object.getInitialization().get(0).getVariable().get(i));
            _builder_2.append(_visit_2);
            _builder_2.append(" : ");
            String _visit_3 = new ToString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
            _builder_2.append(_visit_3);
            _builder_2.append("_elems){");
            _builder_2.newLineIfNotEmpty();
            sb.append(_builder_2);
          }
        }
      }
      if (((this.options != null) && ((!this.options.useMaps) || (!this.options.initMapsWithInsert)))) {
        StringConcatenation _builder = new StringConcatenation();
        String _name = object.getName();
        _builder.append(_name);
        _builder.append("[0][");
        String _printVariables = this.printVariables(object.getInitialization().get(0).getVariable());
        _builder.append(_printVariables);
        _builder.append("] = ");
        String _visit = new TermToCpp(this.asm).visit(object.getInitialization().get(0).getBody());
        _builder.append(_visit);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        sb.append(_builder);
        StringConcatenation _builder_1 = new StringConcatenation();
        String _name_1 = object.getName();
        _builder_1.append(_name_1);
        _builder_1.append("[1][");
        String _printVariables_1 = this.printVariables(object.getInitialization().get(0).getVariable());
        _builder_1.append(_printVariables_1);
        _builder_1.append("] = ");
        String _visit_1 = new TermToCpp(this.asm).visit(object.getInitialization().get(0).getBody());
        _builder_1.append(_visit_1);
        _builder_1.append(";");
        _builder_1.newLineIfNotEmpty();
        sb.append(_builder_1);
      } else {
        StringConcatenation _builder_2 = new StringConcatenation();
        String _name_2 = object.getName();
        _builder_2.append(_name_2);
        _builder_2.append("[0].insert({");
        String _printVariables_2 = this.printVariables(object.getInitialization().get(0).getVariable());
        _builder_2.append(_printVariables_2);
        _builder_2.append(",");
        String _visit_2 = new TermToCpp(this.asm).visit(object.getInitialization().get(0).getBody());
        _builder_2.append(_visit_2);
        _builder_2.append("});");
        _builder_2.newLineIfNotEmpty();
        sb.append(_builder_2);
        StringConcatenation _builder_3 = new StringConcatenation();
        String _name_3 = object.getName();
        _builder_3.append(_name_3);
        _builder_3.append("[1].insert({");
        String _printVariables_3 = this.printVariables(object.getInitialization().get(0).getVariable());
        _builder_3.append(_printVariables_3);
        _builder_3.append(",");
        String _visit_3 = new TermToCpp(this.asm).visit(object.getInitialization().get(0).getBody());
        _builder_3.append(_visit_3);
        _builder_3.append("});");
        _builder_3.newLineIfNotEmpty();
        sb.append(_builder_3);
      }
      for (int i = 0; (i < object.getInitialization().get(0).getVariable().size()); i++) {
        StringConcatenation _builder_4 = new StringConcatenation();
        _builder_4.append("}");
        sb.append(_builder_4);
      }
    } else {
      StringConcatenation _builder_4 = new StringConcatenation();
      String _name_4 = object.getName();
      _builder_4.append(_name_4);
      _builder_4.append("[0] = ");
      String _name_5 = object.getName();
      _builder_4.append(_name_5);
      _builder_4.append("[1] = ");
      String _visit_4 = new TermToCpp(this.asm).visit(object.getInitialization().get(0).getBody());
      _builder_4.append(_visit_4);
      _builder_4.append(";");
      sb.append(_builder_4);
    }
    return sb.toString();
  }

  public String visit(final MonitoredFunction object) {
    StringBuffer sb = new StringBuffer();
    Domain _domain = object.getDomain();
    boolean _tripleNotEquals = (_domain != null);
    if (_tripleNotEquals) {
      for (int i = 0; (i < object.getInitialization().get(0).getVariable().size()); i++) {
        Domain _domain_1 = object.getInitialization().get(0).getVariable().get(i).getDomain();
        if ((_domain_1 instanceof AbstractTd)) {
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("for(const auto& ");
          String _visit = new TermToCpp(this.asm).visit(object.getInitialization().get(0).getVariable().get(i));
          _builder.append(_visit);
          _builder.append(" : ");
          String _visit_1 = new ToString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
          _builder.append(_visit_1);
          _builder.append("::elems)");
          _builder.newLineIfNotEmpty();
          sb.append(_builder);
        } else {
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append("for(auto const& ");
          String _visit_2 = new TermToCpp(this.asm).visit(object.getInitialization().get(0).getVariable().get(i));
          _builder_1.append(_visit_2);
          _builder_1.append(" : ");
          String _visit_3 = new ToString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
          _builder_1.append(_visit_3);
          _builder_1.append("_elems)");
          _builder_1.newLineIfNotEmpty();
          sb.append(_builder_1);
        }
      }
      if (((this.options != null) && ((!this.options.useMaps) || (!this.options.initMapsWithInsert)))) {
        StringConcatenation _builder = new StringConcatenation();
        String _name = object.getName();
        _builder.append(_name);
        _builder.append("[");
        String _printVariables = this.printVariables(object.getInitialization().get(0).getVariable());
        _builder.append(_printVariables);
        _builder.append("] = ");
        String _visit = new TermToCpp(this.asm).visit(object.getInitialization().get(0).getBody());
        _builder.append(_visit);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        sb.append(_builder);
      } else {
        StringConcatenation _builder_1 = new StringConcatenation();
        String _name_1 = object.getName();
        _builder_1.append(_name_1);
        _builder_1.append(".insert({");
        String _printVariables_1 = this.printVariables(object.getInitialization().get(0).getVariable());
        _builder_1.append(_printVariables_1);
        _builder_1.append(",");
        String _visit_1 = new TermToCpp(this.asm).visit(object.getInitialization().get(0).getBody());
        _builder_1.append(_visit_1);
        _builder_1.append("});");
        _builder_1.newLineIfNotEmpty();
        sb.append(_builder_1);
      }
    } else {
      StringConcatenation _builder_2 = new StringConcatenation();
      String _name_2 = object.getName();
      _builder_2.append(_name_2);
      _builder_2.append(" = ");
      String _visit_2 = new TermToCpp(this.asm).visit(object.getInitialization().get(0).getBody());
      _builder_2.append(_visit_2);
      _builder_2.append(";");
      sb.append(_builder_2);
    }
    return sb.toString();
  }

  public String visit(final DerivedFunction object) {
    StringBuffer sb = new StringBuffer();
    Domain _domain = object.getDomain();
    boolean _tripleNotEquals = (_domain != null);
    if (_tripleNotEquals) {
      String t = new TermToCpp(this.asm).visit(object.getDefinition().getBody());
      boolean _contains = t.contains(" auto $");
      if (_contains) {
        StringConcatenation _builder = new StringConcatenation();
        String _visit = new ToString(this.asm).visit(object.getCodomain());
        _builder.append(_visit);
        _builder.append(" ");
        String _name = this.asm.getName();
        _builder.append(_name);
        _builder.append("::");
        String _name_1 = object.getName();
        _builder.append(_name_1);
        _builder.append("(");
        String _adaptRuleParam = new Util().adaptRuleParam(object.getDefinition().getVariable(), this.asm);
        _builder.append(_adaptRuleParam);
        _builder.append("){");
        String _visit_1 = new TermToCpp(this.asm).visit(object.getDefinition().getBody());
        _builder.append(_visit_1);
        _builder.append(";}");
        sb.append(_builder);
      } else {
        StringConcatenation _builder_1 = new StringConcatenation();
        String _visit_2 = new ToString(this.asm).visit(object.getCodomain());
        _builder_1.append(_visit_2);
        _builder_1.append(" ");
        String _name_2 = this.asm.getName();
        _builder_1.append(_name_2);
        _builder_1.append("::");
        String _name_3 = object.getName();
        _builder_1.append(_name_3);
        _builder_1.append("(");
        String _adaptRuleParam_1 = new Util().adaptRuleParam(object.getDefinition().getVariable(), this.asm);
        _builder_1.append(_adaptRuleParam_1);
        _builder_1.append("){return ");
        String _visit_3 = new TermToCpp(this.asm).visit(object.getDefinition().getBody());
        _builder_1.append(_visit_3);
        _builder_1.append(";}");
        sb.append(_builder_1);
      }
    } else {
      StringConcatenation _builder_2 = new StringConcatenation();
      String _visit_4 = new ToString(this.asm).visit(object.getCodomain());
      _builder_2.append(_visit_4);
      _builder_2.append(" ");
      String _name_4 = this.asm.getName();
      _builder_2.append(_name_4);
      _builder_2.append("::");
      String _name_5 = object.getName();
      _builder_2.append(_name_5);
      _builder_2.append("(){return ");
      String _visit_5 = new TermToCpp(this.asm).visit(object.getDefinition().getBody());
      _builder_2.append(_visit_5);
      _builder_2.append(";}");
      sb.append(_builder_2);
    }
    return sb.toString();
  }

  public String visit(final StaticFunction object) {
    StringBuffer sb = new StringBuffer();
    Domain _domain = object.getDomain();
    boolean _tripleNotEquals = (_domain != null);
    if (_tripleNotEquals) {
      StringConcatenation _builder = new StringConcatenation();
      String _visit = new ToString(this.asm).visit(object.getCodomain());
      _builder.append(_visit);
      _builder.append(" ");
      String _name = this.asm.getName();
      _builder.append(_name);
      _builder.append("::");
      String _name_1 = object.getName();
      _builder.append(_name_1);
      _builder.append("(");
      String _adaptRuleParam = new Util().adaptRuleParam(object.getDefinition().getVariable(), this.asm);
      _builder.append(_adaptRuleParam);
      _builder.append("){return ");
      String _visit_1 = new TermToCpp(this.asm).visit(object.getDefinition().getBody());
      _builder.append(_visit_1);
      _builder.append(";}");
      sb.append(_builder);
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      String _visit_2 = new ToString(this.asm).visit(object.getCodomain());
      _builder_1.append(_visit_2);
      _builder_1.append(" ");
      String _name_2 = this.asm.getName();
      _builder_1.append(_name_2);
      _builder_1.append("::");
      String _name_3 = object.getName();
      _builder_1.append(_name_3);
      _builder_1.append("(){return ");
      String _visit_3 = new TermToCpp(this.asm).visit(object.getDefinition().getBody());
      _builder_1.append(_visit_3);
      _builder_1.append(";}");
      sb.append(_builder_1);
    }
    return sb.toString();
  }

  public String printVariables(final EList<VariableTerm> list) {
    StringBuffer sb = new StringBuffer();
    int _size = list.size();
    boolean _equals = (_size == 1);
    if (_equals) {
      StringConcatenation _builder = new StringConcatenation();
      String _visit = new TermToCpp(this.asm).visit(list.get(this.i));
      _builder.append(_visit);
      sb.append(_builder);
      return sb.toString();
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("make_tuple(");
      sb.append(_builder_1);
      for (int i = 0; (i < list.size()); i++) {
        StringConcatenation _builder_2 = new StringConcatenation();
        String _visit_1 = new TermToCpp(this.asm).visit(list.get(i));
        _builder_2.append(_visit_1);
        _builder_2.append(",");
        sb.append(_builder_2);
      }
      String _string = sb.toString();
      int _length = sb.length();
      int _minus = (_length - 1);
      String _substring = _string.substring(0, _minus);
      return (_substring + ")");
    }
  }
}
