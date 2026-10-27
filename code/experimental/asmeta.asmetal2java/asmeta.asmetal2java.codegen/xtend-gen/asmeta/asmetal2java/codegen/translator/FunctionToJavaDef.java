package asmeta.asmetal2java.codegen.translator;

import asmeta.definitions.ControlledFunction;
import asmeta.definitions.DerivedFunction;
import asmeta.definitions.DynamicFunction;
import asmeta.definitions.MonitoredFunction;
import asmeta.definitions.OutFunction;
import asmeta.definitions.StaticFunction;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.BagDomain;
import asmeta.definitions.domains.BooleanDomain;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.MapDomain;
import asmeta.definitions.domains.PowersetDomain;
import asmeta.definitions.domains.ProductDomain;
import asmeta.definitions.domains.SequenceDomain;
import asmeta.structure.Asm;
import asmeta.structure.FunctionInitialization;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.terms.furtherterms.ForallTerm;
import asmeta.terms.furtherterms.SequenceTerm;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.IntFunction;
import org.asmeta.parser.util.ReflectiveVisitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class FunctionToJavaDef extends ReflectiveVisitor<String> {
  private Asm asm;

  public FunctionToJavaDef(final Asm asm) {
    this.asm = asm;
  }

  public String visit(final SequenceTerm object) {
    StringBuffer sb = new StringBuffer();
    for (int index = 0; (index < object.getTerms().size()); index++) {
      {
        if ((index > 0)) {
          sb.append(",");
        }
        final Term term = object.getTerms().get(index);
        if ((term instanceof SequenceTerm)) {
          String _visit = this.visit(((SequenceTerm)term));
          String _plus = ("new ArrayList<>(Arrays.asList(" + _visit);
          String _plus_1 = (_plus + "))");
          sb.append(_plus_1);
        } else {
          sb.append(new TermToJava(this.asm).visit(term));
        }
      }
    }
    return sb.toString();
  }

  public String visit(final ControlledFunction object) {
    return this.visitControlledOrOutputFunction(object);
  }

  public String visit(final OutFunction object) {
    return this.visitControlledOrOutputFunction(object);
  }

  private String visitControlledOrOutputFunction(final DynamicFunction object) {
    return this.dynamicInitialization(object);
  }

  public String visit(final MonitoredFunction object) {
    return this.dynamicInitialization(object);
  }

  /**
   * Initialize dynamic functions using their declared key and value types.
   */
  private String dynamicInitialization(final DynamicFunction function) {
    final FunctionInitialization initialization = function.getInitialization().get(0);
    final TermToJava translator = new TermToJava(this.asm);
    final StringBuilder result = new StringBuilder();
    final List<VariableTerm> variables = initialization.getVariable();
    final Set<String> names = new HashSet<String>();
    for (final VariableTerm variable : variables) {
      names.add(translator.visit(variable));
    }
    int loopCount = 0;
    for (final VariableTerm variable_1 : variables) {
      {
        boolean _isEnumerableInitializationDomain = this.isEnumerableInitializationDomain(variable_1.getDomain());
        boolean _not = (!_isEnumerableInitializationDomain);
        if (_not) {
          String _name = variable_1.getDomain().getName();
          String _plus = ("// Function initialization over " + _name);
          return (_plus + " is not enumerable.\n");
        }
        int _loopCount = loopCount;
        int _bindInitializationVariable = this.bindInitializationVariable(variable_1.getDomain(), translator.visit(variable_1), names, result);
        loopCount = (_loopCount + _bindInitializationVariable);
      }
    }
    final String value = ArgumentToJava.value(initialization.getBody(), function.getCodomain(), translator);
    String key = "";
    boolean _isEmpty = variables.isEmpty();
    boolean _not = (!_isEmpty);
    if (_not) {
      int _size = variables.size();
      boolean _equals = (_size == 1);
      if (_equals) {
        key = ArgumentToJava.value(variables.get(0), function.getDomain(), translator);
      } else {
        Domain _domain = function.getDomain();
        final ProductDomain product = ((ProductDomain) _domain);
        final IntFunction<String> _function = (int index) -> {
          return ArgumentToJava.value(variables.get(index), product.getDomains().get(index), translator);
        };
        key = ProductToJava.value(product, _function);
      }
    }
    result.append(function.getName());
    if ((function instanceof MonitoredFunction)) {
      String _xifexpression = null;
      boolean _isEmpty_1 = variables.isEmpty();
      if (_isEmpty_1) {
        _xifexpression = (".value = " + value);
      } else {
        _xifexpression = ((((".values.put(" + key) + ", ") + value) + ")");
      }
      result.append(_xifexpression);
    } else {
      StringBuilder _append = result.append(".init(");
      String _xifexpression_1 = null;
      boolean _isEmpty_2 = variables.isEmpty();
      if (_isEmpty_2) {
        _xifexpression_1 = value;
      } else {
        _xifexpression_1 = ((key + ", ") + value);
      }
      _append.append(_xifexpression_1).append(")");
    }
    result.append(";\n");
    result.append("}\n".repeat(loopCount));
    return result.toString();
  }

  private boolean isEnumerableInitializationDomain(final Domain domain) {
    if ((domain instanceof ProductDomain)) {
      final Function1<Domain, Boolean> _function = (Domain it) -> {
        return Boolean.valueOf(this.isEnumerableInitializationDomain(it));
      };
      return IterableExtensions.<Domain>forall(((ProductDomain)domain).getDomains(), _function);
    }
    return ((((domain instanceof ConcreteDomain) || (domain instanceof AbstractTd)) || 
      (domain instanceof EnumTd)) || (domain instanceof BooleanDomain));
  }

  /**
   * Open nested loops and bind a variable to each value of a finite domain.
   */
  private int bindInitializationVariable(final Domain domain, final String name, final Set<String> names, final StringBuilder result) {
    if ((domain instanceof ProductDomain)) {
      final List<String> components = new ArrayList<String>();
      int loops = 0;
      EList<Domain> _domains = ((ProductDomain)domain).getDomains();
      for (final Domain component : _domains) {
        {
          int _size = components.size();
          String _plus = ((name + "_component") + Integer.valueOf(_size));
          final String componentName = this.freshInitializationName(_plus, names);
          components.add(componentName);
          int _loops = loops;
          int _bindInitializationVariable = this.bindInitializationVariable(component, componentName, names, result);
          loops = (_loops + _bindInitializationVariable);
        }
      }
      final IntFunction<String> _function = (int index) -> {
        return components.get(index);
      };
      result.append(Util.javaType(domain, this.asm)).append(" ").append(name).append(" = ").append(ProductToJava.value(((ProductDomain)domain), _function)).append(";\n");
      return loops;
    }
    Domain _xifexpression = null;
    if ((domain instanceof ConcreteDomain)) {
      _xifexpression = ((ConcreteDomain)domain).getTypeDomain();
    } else {
      _xifexpression = domain;
    }
    final Domain elementType = _xifexpression;
    String _xifexpression_1 = null;
    if ((domain instanceof EnumTd)) {
      String _name = ((EnumTd)domain).getName();
      _xifexpression_1 = (_name + ".values()");
    } else {
      String _xifexpression_2 = null;
      if ((domain instanceof BooleanDomain)) {
        _xifexpression_2 = "java.util.Arrays.asList(false, true)";
      } else {
        String _name_1 = domain.getName();
        _xifexpression_2 = (_name_1 + ".elems");
      }
      _xifexpression_1 = _xifexpression_2;
    }
    final String elements = _xifexpression_1;
    String _xifexpression_3 = null;
    if ((domain instanceof ConcreteDomain)) {
      _xifexpression_3 = this.freshInitializationName((name + "_initValue"), names);
    } else {
      _xifexpression_3 = name;
    }
    final String loopVariable = _xifexpression_3;
    result.append("for (").append(Util.javaType(elementType, this.asm)).append(" ").append(loopVariable).append(" : ").append(elements).append(") {\n");
    if ((domain instanceof ConcreteDomain)) {
      result.append(((ConcreteDomain)domain).getName()).append(" ").append(name).append(" = ").append(((ConcreteDomain)domain).getName()).append(".valueOf(").append(loopVariable).append(");\n");
    }
    return 1;
  }

  private String freshInitializationName(final String baseName, final Set<String> names) {
    String name = baseName;
    while ((!names.add(name))) {
      String _name = name;
      name = (_name + "_");
    }
    return name;
  }

  public String visit(final DerivedFunction object) {
    StringBuffer sb = new StringBuffer();
    Domain _domain = object.getDomain();
    boolean _tripleNotEquals = (_domain != null);
    if (_tripleNotEquals) {
      Term _body = object.getDefinition().getBody();
      if ((_body instanceof ForallTerm)) {
        StringConcatenation _builder = new StringConcatenation();
        String _javaType = Util.javaType(object.getCodomain(), this.asm);
        _builder.append(_javaType);
        _builder.append(" ");
        String _name = object.getName();
        _builder.append(_name);
        _builder.append("(");
        String _adaptRuleParam = new Util().adaptRuleParam(object.getDefinition().getVariable(), this.asm);
        _builder.append(_adaptRuleParam);
        _builder.append("){ return ");
        String _visit = new TermToJava(this.asm).visit(object.getDefinition().getBody());
        _builder.append(_visit);
        _builder.append(";}");
        sb.append(_builder);
      } else {
        Domain _codomain = object.getCodomain();
        if ((_codomain instanceof ConcreteDomain)) {
          StringConcatenation _builder_1 = new StringConcatenation();
          String _visit_1 = new DomainToJavaString(this.asm).visit(object.getCodomain());
          _builder_1.append(_visit_1);
          _builder_1.append(" ");
          String _name_1 = object.getName();
          _builder_1.append(_name_1);
          _builder_1.append("(");
          String _adaptRuleParam_1 = new Util().adaptRuleParam(object.getDefinition().getVariable(), this.asm);
          _builder_1.append(_adaptRuleParam_1);
          _builder_1.append("){");
          _builder_1.newLineIfNotEmpty();
          _builder_1.append("\t\t\t\t\t");
          _builder_1.newLine();
          _builder_1.append("\t\t\t\t\t");
          String _name_2 = object.getCodomain().getName();
          _builder_1.append(_name_2, "\t\t\t\t\t");
          _builder_1.append(" supp = new ");
          String _name_3 = object.getCodomain().getName();
          _builder_1.append(_name_3, "\t\t\t\t\t");
          _builder_1.append("();");
          _builder_1.newLineIfNotEmpty();
          _builder_1.append("\t\t\t\t\t");
          _builder_1.append("supp.value = ");
          String _visit_2 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
          _builder_1.append(_visit_2, "\t\t\t\t\t");
          _builder_1.append(";");
          _builder_1.newLineIfNotEmpty();
          _builder_1.append("\t\t\t\t\t");
          _builder_1.newLine();
          _builder_1.append("\t\t\t\t\t");
          _builder_1.append("return supp;");
          _builder_1.newLine();
          _builder_1.append("\t\t\t\t");
          _builder_1.append("}");
          sb.append(_builder_1);
        } else {
          StringConcatenation _builder_2 = new StringConcatenation();
          String _javaType_1 = Util.javaType(object.getCodomain(), this.asm);
          _builder_2.append(_javaType_1);
          _builder_2.append(" ");
          String _name_4 = object.getName();
          _builder_2.append(_name_4);
          _builder_2.append("(");
          String _adaptRuleParam_2 = new Util().adaptRuleParam(object.getDefinition().getVariable(), this.asm);
          _builder_2.append(_adaptRuleParam_2);
          _builder_2.append("){ return ");
          String _visit_3 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
          _builder_2.append(_visit_3);
          _builder_2.append(";}");
          sb.append(_builder_2);
        }
      }
    } else {
      Term _body_1 = object.getDefinition().getBody();
      if ((_body_1 instanceof ForallTerm)) {
        StringConcatenation _builder_3 = new StringConcatenation();
        String _javaType_2 = Util.javaType(object.getCodomain(), this.asm);
        _builder_3.append(_javaType_2);
        _builder_3.append(" ");
        String _name_5 = object.getName();
        _builder_3.append(_name_5);
        _builder_3.append("(){ return ");
        String _visit_4 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
        _builder_3.append(_visit_4);
        _builder_3.append(";}");
        sb.append(_builder_3);
      } else {
        Domain _codomain_1 = object.getCodomain();
        if ((_codomain_1 instanceof ConcreteDomain)) {
          StringConcatenation _builder_4 = new StringConcatenation();
          String _visit_5 = new DomainToJavaString(this.asm).visit(object.getCodomain());
          _builder_4.append(_visit_5);
          _builder_4.append(" ");
          String _name_6 = object.getName();
          _builder_4.append(_name_6);
          _builder_4.append("(){");
          _builder_4.newLineIfNotEmpty();
          _builder_4.append("\t\t\t\t\t");
          _builder_4.newLine();
          _builder_4.append("\t\t\t\t\t");
          String _name_7 = object.getCodomain().getName();
          _builder_4.append(_name_7, "\t\t\t\t\t");
          _builder_4.append("_elem.value = ");
          String _visit_6 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
          _builder_4.append(_visit_6, "\t\t\t\t\t");
          _builder_4.append(";");
          _builder_4.newLineIfNotEmpty();
          _builder_4.append("\t\t\t\t\t");
          _builder_4.newLine();
          _builder_4.append("\t\t\t\t\t");
          _builder_4.append("return ");
          String _name_8 = object.getCodomain().getName();
          _builder_4.append(_name_8, "\t\t\t\t\t");
          _builder_4.append("_elem;");
          _builder_4.newLineIfNotEmpty();
          _builder_4.append("\t\t\t\t");
          _builder_4.append("}");
          sb.append(_builder_4);
        } else {
          StringConcatenation _builder_5 = new StringConcatenation();
          String _javaType_3 = Util.javaType(object.getCodomain(), this.asm);
          _builder_5.append(_javaType_3);
          _builder_5.append(" ");
          String _name_9 = object.getName();
          _builder_5.append(_name_9);
          _builder_5.append("(){ return ");
          String _visit_7 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
          _builder_5.append(_visit_7);
          _builder_5.append(";}");
          sb.append(_builder_5);
        }
      }
    }
    return sb.toString();
  }

  public String visit(final StaticFunction object) {
    StringBuffer sb = new StringBuffer();
    Domain _domain = object.getDomain();
    boolean _tripleNotEquals = (_domain != null);
    if (_tripleNotEquals) {
      Term _body = object.getDefinition().getBody();
      if ((_body instanceof ForallTerm)) {
        StringConcatenation _builder = new StringConcatenation();
        String _javaType = Util.javaType(object.getCodomain(), this.asm);
        _builder.append(_javaType);
        _builder.append(" ");
        String _name = object.getName();
        _builder.append(_name);
        _builder.append("(");
        String _adaptRuleParam = new Util().adaptRuleParam(object.getDefinition().getVariable(), this.asm);
        _builder.append(_adaptRuleParam);
        _builder.append("){ return ");
        String _visit = new TermToJava(this.asm).visit(object.getDefinition().getBody());
        _builder.append(_visit);
        _builder.append(";}");
        sb.append(_builder);
      } else {
        Domain _codomain = object.getCodomain();
        if ((_codomain instanceof ConcreteDomain)) {
          StringConcatenation _builder_1 = new StringConcatenation();
          String _visit_1 = new DomainToJavaString(this.asm).visit(object.getCodomain());
          _builder_1.append(_visit_1);
          _builder_1.append(" ");
          String _name_1 = object.getName();
          _builder_1.append(_name_1);
          _builder_1.append("(");
          String _adaptRuleParam_1 = new Util().adaptRuleParam(object.getDefinition().getVariable(), this.asm);
          _builder_1.append(_adaptRuleParam_1);
          _builder_1.append("){");
          _builder_1.newLineIfNotEmpty();
          _builder_1.append("\t\t\t\t\t");
          _builder_1.newLine();
          _builder_1.append("\t\t\t\t\t");
          String _name_2 = object.getCodomain().getName();
          _builder_1.append(_name_2, "\t\t\t\t\t");
          _builder_1.append(" supp = new ");
          String _name_3 = object.getCodomain().getName();
          _builder_1.append(_name_3, "\t\t\t\t\t");
          _builder_1.append("();");
          _builder_1.newLineIfNotEmpty();
          _builder_1.append("\t\t\t\t\t");
          _builder_1.append("supp.value = ");
          String _visit_2 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
          _builder_1.append(_visit_2, "\t\t\t\t\t");
          _builder_1.append(";");
          _builder_1.newLineIfNotEmpty();
          _builder_1.append("\t\t\t\t\t");
          _builder_1.newLine();
          _builder_1.append("\t\t\t\t\t");
          _builder_1.append("return supp;");
          _builder_1.newLine();
          _builder_1.append("\t\t\t\t");
          _builder_1.append("}");
          sb.append(_builder_1);
        } else {
          if (((((object.getCodomain() instanceof SequenceDomain) || (object.getCodomain() instanceof PowersetDomain)) || 
            (object.getCodomain() instanceof BagDomain)) || (object.getCodomain() instanceof MapDomain))) {
            StringConcatenation _builder_2 = new StringConcatenation();
            String _javaType_1 = Util.javaType(object.getCodomain(), this.asm);
            _builder_2.append(_javaType_1);
            _builder_2.append(" ");
            String _name_4 = object.getName();
            _builder_2.append(_name_4);
            _builder_2.append("(");
            String _adaptRuleParam_2 = new Util().adaptRuleParam(object.getDefinition().getVariable(), this.asm);
            _builder_2.append(_adaptRuleParam_2);
            _builder_2.append("){return ");
            String _visit_3 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
            _builder_2.append(_visit_3);
            _builder_2.append(";}");
            sb.append(_builder_2);
          } else {
            StringConcatenation _builder_3 = new StringConcatenation();
            String _javaType_2 = Util.javaType(object.getCodomain(), this.asm);
            _builder_3.append(_javaType_2);
            _builder_3.append(" ");
            String _name_5 = object.getName();
            _builder_3.append(_name_5);
            _builder_3.append("(");
            String _adaptRuleParam_3 = new Util().adaptRuleParam(object.getDefinition().getVariable(), this.asm);
            _builder_3.append(_adaptRuleParam_3);
            _builder_3.append("){ return ");
            String _visit_4 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
            _builder_3.append(_visit_4);
            _builder_3.append(";}");
            sb.append(_builder_3);
          }
        }
      }
    } else {
      Term _body_1 = object.getDefinition().getBody();
      if ((_body_1 instanceof ForallTerm)) {
        StringConcatenation _builder_4 = new StringConcatenation();
        String _javaType_3 = Util.javaType(object.getCodomain(), this.asm);
        _builder_4.append(_javaType_3);
        _builder_4.append(" ");
        String _name_6 = object.getName();
        _builder_4.append(_name_6);
        _builder_4.append("(){ return ");
        String _visit_5 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
        _builder_4.append(_visit_5);
        _builder_4.append(";}");
        sb.append(_builder_4);
      } else {
        Domain _codomain_1 = object.getCodomain();
        if ((_codomain_1 instanceof ConcreteDomain)) {
          StringConcatenation _builder_5 = new StringConcatenation();
          String _visit_6 = new DomainToJavaString(this.asm).visit(object.getCodomain());
          _builder_5.append(_visit_6);
          _builder_5.append(" ");
          String _name_7 = object.getName();
          _builder_5.append(_name_7);
          _builder_5.append("(){");
          _builder_5.newLineIfNotEmpty();
          _builder_5.append("\t\t\t\t\t");
          _builder_5.newLine();
          _builder_5.append("\t\t\t\t\t");
          String _name_8 = object.getCodomain().getName();
          _builder_5.append(_name_8, "\t\t\t\t\t");
          _builder_5.append(" supp = new ");
          String _name_9 = object.getCodomain().getName();
          _builder_5.append(_name_9, "\t\t\t\t\t");
          _builder_5.append("();");
          _builder_5.newLineIfNotEmpty();
          _builder_5.append("\t\t\t\t\t");
          _builder_5.newLine();
          _builder_5.append("\t\t\t\t\t");
          _builder_5.append("supp.value = ");
          String _visit_7 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
          _builder_5.append(_visit_7, "\t\t\t\t\t");
          _builder_5.append(";");
          _builder_5.newLineIfNotEmpty();
          _builder_5.append("\t\t\t\t\t");
          _builder_5.newLine();
          _builder_5.append("\t\t\t\t\t");
          _builder_5.append("return supp;");
          _builder_5.newLine();
          _builder_5.append("\t\t\t\t");
          _builder_5.append("}");
          sb.append(_builder_5);
        } else {
          if (((((object.getCodomain() instanceof SequenceDomain) || (object.getCodomain() instanceof PowersetDomain)) || 
            (object.getCodomain() instanceof BagDomain)) || (object.getCodomain() instanceof MapDomain))) {
            StringConcatenation _builder_6 = new StringConcatenation();
            String _javaType_4 = Util.javaType(object.getCodomain(), this.asm);
            _builder_6.append(_javaType_4);
            _builder_6.append(" ");
            String _name_10 = object.getName();
            _builder_6.append(_name_10);
            _builder_6.append("(){ return ");
            String _visit_8 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
            _builder_6.append(_visit_8);
            _builder_6.append(";}");
            sb.append(_builder_6);
          } else {
            StringConcatenation _builder_7 = new StringConcatenation();
            String _javaType_5 = Util.javaType(object.getCodomain(), this.asm);
            _builder_7.append(_javaType_5);
            _builder_7.append(" ");
            String _name_11 = object.getName();
            _builder_7.append(_name_11);
            _builder_7.append("(){ return ");
            String _visit_9 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
            _builder_7.append(_visit_9);
            _builder_7.append(";}");
            sb.append(_builder_7);
          }
        }
      }
    }
    return sb.toString();
  }

  public Boolean controllo(final String domain) {
    if (((((domain.equals("Integer") || domain.equals("Natural")) || domain.equals("String")) || 
      domain.equals("Boolean")) || domain.equals("Real"))) {
      return Boolean.valueOf(true);
    } else {
      return Boolean.valueOf(false);
    }
  }
}
