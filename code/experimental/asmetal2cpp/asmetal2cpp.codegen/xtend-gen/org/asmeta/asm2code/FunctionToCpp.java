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
            _builder_1.append("for(const auto& �new TermToCpp(asm).visit(object.initialization.get(0).variable.get(i))� : �new ToString(asm).visit(object.initialization.get(0).variable.get(i).domain)�::elems){");
            _builder_1.newLine();
            sb.append(_builder_1);
          } else {
            StringConcatenation _builder_2 = new StringConcatenation();
            _builder_2.append("for(auto const& �new TermToCpp(asm).visit(object.initialization.get(0).variable.get(i))� : �new ToString(asm).visit(object.initialization.get(0).variable.get(i).domain)�_elems){");
            _builder_2.newLine();
            sb.append(_builder_2);
          }
        }
      }
      if (((this.options != null) && ((!this.options.useMaps) || (!this.options.initMapsWithInsert)))) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("�object.name�[0][�printVariables(object.initialization.get(0).variable)�] = �new TermToCpp(asm).visit(object.initialization.get(0).body)�;");
        _builder.newLine();
        sb.append(_builder);
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("�object.name�[1][�printVariables(object.initialization.get(0).variable)�] = �new TermToCpp(asm).visit(object.initialization.get(0).body)�;");
        _builder_1.newLine();
        sb.append(_builder_1);
      } else {
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("�object.name�[0].insert({�printVariables(object.initialization.get(0).variable)�,�new TermToCpp(asm).visit(object.initialization.get(0).body)�});");
        _builder_2.newLine();
        sb.append(_builder_2);
        StringConcatenation _builder_3 = new StringConcatenation();
        _builder_3.append("�object.name�[1].insert({�printVariables(object.initialization.get(0).variable)�,�new TermToCpp(asm).visit(object.initialization.get(0).body)�});");
        _builder_3.newLine();
        sb.append(_builder_3);
      }
      for (int i = 0; (i < object.getInitialization().get(0).getVariable().size()); i++) {
        StringConcatenation _builder_4 = new StringConcatenation();
        _builder_4.append("}");
        sb.append(_builder_4);
      }
    } else {
      StringConcatenation _builder_4 = new StringConcatenation();
      _builder_4.append("�object.name�[0] = �object.name�[1] = �new TermToCpp(asm).visit(object.initialization.get(0).body)�;");
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
          _builder.append("for(const auto& �new TermToCpp(asm).visit(object.initialization.get(0).variable.get(i))� : �new ToString(asm).visit(object.initialization.get(0).variable.get(i).domain)�::elems)");
          _builder.newLine();
          sb.append(_builder);
        } else {
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append("for(auto const& �new TermToCpp(asm).visit(object.initialization.get(0).variable.get(i))� : �new ToString(asm).visit(object.initialization.get(0).variable.get(i).domain)�_elems)");
          _builder_1.newLine();
          sb.append(_builder_1);
        }
      }
      if (((this.options != null) && ((!this.options.useMaps) || (!this.options.initMapsWithInsert)))) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("�object.name�[�printVariables(object.initialization.get(0).variable)�] = �new TermToCpp(asm).visit(object.initialization.get(0).body)�;");
        _builder.newLine();
        sb.append(_builder);
      } else {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("�object.name�.insert({�printVariables(object.initialization.get(0).variable)�,�new TermToCpp(asm).visit(object.initialization.get(0).body)�});");
        _builder_1.newLine();
        sb.append(_builder_1);
      }
    } else {
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append("�object.name� = �new TermToCpp(asm).visit(object.initialization.get(0).body)�;");
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
        _builder.append("�new ToString(asm).visit(object.codomain)� �asm.name�::�object.name�(�new Util().adaptRuleParam(object.definition.variable,asm)�){�new TermToCpp(asm).visit(object.definition.body)�;}");
        sb.append(_builder);
      } else {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("�new ToString(asm).visit(object.codomain)� �asm.name�::�object.name�(�new Util().adaptRuleParam(object.definition.variable,asm)�){return �new TermToCpp(asm).visit(object.definition.body)�;}");
        sb.append(_builder_1);
      }
    } else {
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append("�new ToString(asm).visit(object.codomain)� �asm.name�::�object.name�(){return �new TermToCpp(asm).visit(object.definition.body)�;}");
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
      _builder.append("�new ToString(asm).visit(object.codomain)� �asm.name�::�object.name�(�new Util().adaptRuleParam(object.definition.variable,asm)�){return �new TermToCpp(asm).visit(object.definition.body)�;}");
      sb.append(_builder);
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("�new ToString(asm).visit(object.codomain)� �asm.name�::�object.name�(){return �new TermToCpp(asm).visit(object.definition.body)�;}");
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
      _builder.append("�new TermToCpp(asm).visit(list.get(i))�");
      sb.append(_builder);
      return sb.toString();
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("make_tuple(");
      sb.append(_builder_1);
      for (int i = 0; (i < list.size()); i++) {
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("�new TermToCpp(asm).visit(list.get(i))�,");
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
