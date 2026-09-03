package asmeta.asmetal2java.codegen.translator;

import asmeta.definitions.ControlledFunction;
import asmeta.definitions.DerivedFunction;
import asmeta.definitions.DynamicFunction;
import asmeta.definitions.MonitoredFunction;
import asmeta.definitions.OutFunction;
import asmeta.definitions.StaticFunction;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.BagDomain;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.MapDomain;
import asmeta.definitions.domains.PowersetDomain;
import asmeta.definitions.domains.ProductDomain;
import asmeta.definitions.domains.SequenceDomain;
import asmeta.structure.Asm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.terms.furtherterms.CaseTerm;
import asmeta.terms.furtherterms.ForallTerm;
import asmeta.terms.furtherterms.SequenceTerm;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;
import org.asmeta.parser.util.ReflectiveVisitor;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class FunctionToJavaDef extends ReflectiveVisitor<String> {
  private Asm asm;

  private int i;

  private final List<String> declaredDomainIninit = new ArrayList<String>();

  public FunctionToJavaDef(final Asm asm) {
    this.asm = asm;
  }

  private String controlledInitializationKey(final DynamicFunction object, final int index) {
    final VariableTerm variable = object.getInitialization().get(0).getVariable().get(index);
    Domain _domain = variable.getDomain();
    if ((_domain instanceof ConcreteDomain)) {
      String _name = variable.getDomain().getName();
      return (_name + "_elem");
    }
    return new TermToJava(this.asm).visit(variable);
  }

  private String monitoredInitializationKey(final DynamicFunction object, final int index) {
    final VariableTerm variable = object.getInitialization().get(0).getVariable().get(index);
    final String variableName = new TermToJava(this.asm).visit(variable);
    Domain _domain = variable.getDomain();
    if ((_domain instanceof ConcreteDomain)) {
      return (variableName + "Val");
    }
    return variableName;
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
    StringBuffer sb = new StringBuffer();
    if (((object.getCodomain() instanceof SequenceDomain) || (object.getDomain() instanceof SequenceDomain))) {
      StringConcatenation _builder = new StringConcatenation();
      String _name = object.getName();
      _builder.append(_name);
      _builder.append(".init(new ArrayList<>(Arrays.asList(");
      String _visit = this.visit(object.getInitialization().get(0).getBody());
      _builder.append(_visit);
      _builder.append(")));");
      _builder.newLineIfNotEmpty();
      sb.append(_builder);
    } else {
      if ((((object.getCodomain() instanceof PowersetDomain) && (!(object.getCodomain() instanceof ConcreteDomain))) || ((object.getDomain() instanceof PowersetDomain) && (!(object.getDomain() instanceof ConcreteDomain))))) {
        StringConcatenation _builder_1 = new StringConcatenation();
        String _name_1 = object.getName();
        _builder_1.append(_name_1);
        _builder_1.append(".init(new HashSet<>(Arrays.asList");
        String _visit_1 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getBody());
        _builder_1.append(_visit_1);
        _builder_1.append("));");
        _builder_1.newLineIfNotEmpty();
        sb.append(_builder_1);
      } else {
        Domain _domain = object.getDomain();
        boolean _tripleNotEquals = (_domain != null);
        if (_tripleNotEquals) {
          for (int i = 0; (i < object.getInitialization().get(0).getVariable().size()); i++) {
            {
              boolean _isNotNumerable = Util.isNotNumerable(object.getInitialization().get(0).getVariable().get(i).getDomain());
              if (_isNotNumerable) {
                StringConcatenation _builder_2 = new StringConcatenation();
                _builder_2.append("//NOT IMPLEMENTED IN Java (FunctionToCpp line 50)");
                _builder_2.newLine();
                sb.append(_builder_2);
                return sb.toString();
              }
              Domain _domain_1 = object.getInitialization().get(0).getVariable().get(i).getDomain();
              if ((_domain_1 instanceof ConcreteDomain)) {
                StringConcatenation _builder_3 = new StringConcatenation();
                _builder_3.newLine();
                _builder_3.append("for(");
                Domain _domain_2 = object.getInitialization().get(0).getVariable().get(i).getDomain();
                String _visit_2 = new DomainToJavaString(this.asm).visit(((ConcreteDomain) _domain_2).getTypeDomain());
                _builder_3.append(_visit_2);
                _builder_3.append(" ");
                String _visit_3 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(i));
                _builder_3.append(_visit_3);
                _builder_3.append(": ");
                String _visit_4 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
                _builder_3.append(_visit_4);
                _builder_3.append(".elems){");
                _builder_3.newLineIfNotEmpty();
                _builder_3.append("\t");
                _builder_3.newLine();
                _builder_3.append("\t");
                _builder_3.newLine();
                _builder_3.append("\t");
                String _visit_5 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
                _builder_3.append(_visit_5, "\t");
                _builder_3.append("_elem.value = ");
                String _visit_6 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(i));
                _builder_3.append(_visit_6, "\t");
                _builder_3.append(";");
                _builder_3.newLineIfNotEmpty();
                _builder_3.newLine();
                sb.append(_builder_3);
              } else {
                Domain _domain_3 = object.getInitialization().get(0).getVariable().get(i).getDomain();
                if ((_domain_3 instanceof AbstractTd)) {
                  StringConcatenation _builder_4 = new StringConcatenation();
                  _builder_4.append("for(");
                  String _visit_7 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
                  _builder_4.append(_visit_7);
                  _builder_4.append(" ");
                  String _visit_8 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(i));
                  _builder_4.append(_visit_8);
                  _builder_4.append(": ");
                  String _visit_9 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
                  _builder_4.append(_visit_9);
                  _builder_4.append(".elems){");
                  _builder_4.newLineIfNotEmpty();
                  sb.append(_builder_4);
                } else {
                  Domain _domain_4 = object.getInitialization().get(0).getVariable().get(i).getDomain();
                  if ((_domain_4 instanceof EnumTd)) {
                    StringConcatenation _builder_5 = new StringConcatenation();
                    _builder_5.append("for(");
                    String _visit_10 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
                    _builder_5.append(_visit_10);
                    _builder_5.append(" ");
                    String _visit_11 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(i));
                    _builder_5.append(_visit_11);
                    _builder_5.append(": ");
                    String _visit_12 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
                    _builder_5.append(_visit_12);
                    _builder_5.append(".values()){");
                    _builder_5.newLineIfNotEmpty();
                    sb.append(_builder_5);
                  } else {
                    StringConcatenation _builder_6 = new StringConcatenation();
                    _builder_6.append("for(");
                    String _visit_13 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
                    _builder_6.append(_visit_13);
                    _builder_6.append(" ");
                    String _visit_14 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(i));
                    _builder_6.append(_visit_14);
                    _builder_6.append(": ");
                    String _visit_15 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
                    _builder_6.append(_visit_15);
                    _builder_6.append(".elems){");
                    _builder_6.newLineIfNotEmpty();
                    sb.append(_builder_6);
                  }
                }
              }
            }
          }
          Domain _codomain = object.getCodomain();
          if ((_codomain instanceof AbstractTd)) {
            String a = new TermToJava(this.asm).visit(object.getInitialization().get(0).getBody());
            String b = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(this.i));
            boolean _equals = a.equals(b);
            if (_equals) {
              StringConcatenation _builder_2 = new StringConcatenation();
              String _name_2 = object.getName();
              _builder_2.append(_name_2);
              _builder_2.append(".init(");
              String _controlledInitializationKey = this.controlledInitializationKey(object, this.i);
              _builder_2.append(_controlledInitializationKey);
              _builder_2.append(",");
              String _visit_2 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getBody());
              _builder_2.append(_visit_2);
              _builder_2.append(");");
              _builder_2.newLineIfNotEmpty();
              sb.append(_builder_2);
            } else {
              Term _body = object.getInitialization().get(0).getBody();
              if ((_body instanceof CaseTerm)) {
                StringConcatenation _builder_3 = new StringConcatenation();
                String _name_3 = object.getName();
                _builder_3.append(_name_3);
                _builder_3.append(".init(");
                String _controlledInitializationKey_1 = this.controlledInitializationKey(object, this.i);
                _builder_3.append(_controlledInitializationKey_1);
                _builder_3.append(",new Function<Void,");
                String _visit_3 = new DomainToJavaString(this.asm).visit(object.getCodomain());
                _builder_3.append(_visit_3);
                _builder_3.append(">(){@Override public ");
                String _visit_4 = new DomainToJavaString(this.asm).visit(object.getCodomain());
                _builder_3.append(_visit_4);
                _builder_3.append(" apply(Void input) {return ");
                String _visit_5 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getBody());
                _builder_3.append(_visit_5);
                _builder_3.append(";}}.apply(null));");
                _builder_3.newLineIfNotEmpty();
                sb.append(_builder_3);
              } else {
                StringConcatenation _builder_4 = new StringConcatenation();
                String _visit_6 = new DomainToJavaString(this.asm).visit(object.getCodomain());
                _builder_4.append(_visit_6);
                _builder_4.append(" ");
                String _visit_7 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getBody());
                _builder_4.append(_visit_7);
                _builder_4.append(" = new ");
                String _visit_8 = new DomainToJavaString(this.asm).visit(object.getCodomain());
                _builder_4.append(_visit_8);
                _builder_4.append("(\"");
                String _visit_9 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getBody());
                _builder_4.append(_visit_9);
                _builder_4.append("\");");
                sb.append(_builder_4);
                StringConcatenation _builder_5 = new StringConcatenation();
                String _name_4 = object.getName();
                _builder_5.append(_name_4);
                _builder_5.append(".init(");
                String _controlledInitializationKey_2 = this.controlledInitializationKey(object, this.i);
                _builder_5.append(_controlledInitializationKey_2);
                _builder_5.append(",");
                String _visit_10 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getBody());
                _builder_5.append(_visit_10);
                _builder_5.append(");");
                _builder_5.newLineIfNotEmpty();
                sb.append(_builder_5);
              }
            }
          } else {
            if (((this.controllo(object.getCodomain().getName())).booleanValue() || (object.getCodomain() instanceof EnumTd))) {
              if (((object.getDomain() instanceof ConcreteDomain) && (this.controllo(object.getCodomain().getName())).booleanValue())) {
                StringConcatenation _builder_6 = new StringConcatenation();
                String _visit_11 = new DomainToJavaString(this.asm).visit(object.getCodomain());
                _builder_6.append(_visit_11);
                _builder_6.append(" a ");
                String _visit_12 = new TermToJavaInAssignments(this.asm).visit(object.getInitialization().get(0).getBody());
                _builder_6.append(_visit_12);
                _builder_6.append(";");
                sb.append(_builder_6);
                StringConcatenation _builder_7 = new StringConcatenation();
                String _name_5 = object.getName();
                _builder_7.append(_name_5);
                _builder_7.append(".init(");
                String _name_6 = object.getDomain().getName();
                _builder_7.append(_name_6);
                _builder_7.append("_elem,a);");
                _builder_7.newLineIfNotEmpty();
                sb.append(_builder_7);
              } else {
                StringConcatenation _builder_8 = new StringConcatenation();
                String _visit_13 = new DomainToJavaString(this.asm).visit(object.getCodomain());
                _builder_8.append(_visit_13);
                _builder_8.append(" a ");
                String _visit_14 = new TermToJavaInAssignments(this.asm).visit(object.getInitialization().get(0).getBody());
                _builder_8.append(_visit_14);
                _builder_8.append(";");
                sb.append(_builder_8);
                StringConcatenation _builder_9 = new StringConcatenation();
                String _name_7 = object.getName();
                _builder_9.append(_name_7);
                _builder_9.append(".init(");
                String _controlledInitializationKey_3 = this.controlledInitializationKey(object, this.i);
                _builder_9.append(_controlledInitializationKey_3);
                _builder_9.append(",a);");
                _builder_9.newLineIfNotEmpty();
                sb.append(_builder_9);
              }
            } else {
              StringConcatenation _builder_10 = new StringConcatenation();
              String _visit_15 = new DomainToJavaString(this.asm).visit(object.getCodomain());
              _builder_10.append(_visit_15);
              _builder_10.append(" a = new ");
              String _visit_16 = new DomainToJavaString(this.asm).visit(object.getCodomain());
              _builder_10.append(_visit_16);
              _builder_10.append("();");
              _builder_10.newLineIfNotEmpty();
              _builder_10.append("\t\t\t    ");
              _builder_10.newLine();
              _builder_10.append("\t\t\t           ");
              _builder_10.append("a.value ");
              String _visit_17 = new TermToJavaInAssignments(this.asm).visit(object.getInitialization().get(0).getBody());
              _builder_10.append(_visit_17, "\t\t\t           ");
              _builder_10.append(";");
              _builder_10.newLineIfNotEmpty();
              sb.append(_builder_10);
              Domain _domain_1 = object.getDomain();
              if ((_domain_1 instanceof ProductDomain)) {
                Domain _domain_2 = object.getDomain();
                final IntFunction<String> _function = (int index) -> {
                  String _visit_18 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(index).getDomain());
                  return (_visit_18 + "_elem");
                };
                final String productKey = ProductToJava.value(((ProductDomain) _domain_2), _function);
                String _name_8 = object.getName();
                String _plus = (" " + _name_8);
                String _plus_1 = (_plus + "_elem = ");
                String _plus_2 = (_plus_1 + productKey);
                String _plus_3 = (_plus_2 + ";\n");
                sb.append(_plus_3);
                StringConcatenation _builder_11 = new StringConcatenation();
                String _name_9 = object.getName();
                _builder_11.append(_name_9);
                _builder_11.append(".init(");
                String _name_10 = object.getName();
                _builder_11.append(_name_10);
                _builder_11.append("_elem,a);");
                _builder_11.newLineIfNotEmpty();
                sb.append(_builder_11);
              } else {
                StringConcatenation _builder_12 = new StringConcatenation();
                String _name_11 = object.getName();
                _builder_12.append(_name_11);
                _builder_12.append(".init(");
                String _controlledInitializationKey_4 = this.controlledInitializationKey(object, this.i);
                _builder_12.append(_controlledInitializationKey_4);
                _builder_12.append(",a);");
                _builder_12.newLineIfNotEmpty();
                sb.append(_builder_12);
              }
            }
          }
          for (int i = 0; (i < object.getInitialization().get(0).getVariable().size()); i++) {
            StringConcatenation _builder_13 = new StringConcatenation();
            _builder_13.append("}");
            sb.append(_builder_13);
          }
        } else {
          if (((this.controllo(object.getCodomain().getName())).booleanValue() || (object.getCodomain() instanceof EnumTd))) {
            StringConcatenation _builder_13 = new StringConcatenation();
            String _name_12 = object.getName();
            _builder_13.append(_name_12);
            _builder_13.append(".init(");
            String _visit_18 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getBody());
            _builder_13.append(_visit_18);
            _builder_13.append(");");
            _builder_13.newLineIfNotEmpty();
            sb.append(_builder_13);
          } else {
            Domain _codomain_1 = object.getCodomain();
            if ((_codomain_1 instanceof ProductDomain)) {
              StringConcatenation _builder_14 = new StringConcatenation();
              String _name_13 = object.getName();
              _builder_14.append(_name_13);
              _builder_14.append(".init(");
              String _visit_19 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getBody());
              _builder_14.append(_visit_19);
              _builder_14.append(");");
              _builder_14.newLineIfNotEmpty();
              sb.append(_builder_14);
            } else {
              Domain _codomain_2 = object.getCodomain();
              if ((_codomain_2 instanceof AbstractTd)) {
                StringConcatenation _builder_15 = new StringConcatenation();
                String _name_14 = object.getName();
                _builder_15.append(_name_14);
                _builder_15.append(".init(");
                String _name_15 = object.getCodomain().getName();
                _builder_15.append(_name_15);
                _builder_15.append(".get(\"");
                String _visit_20 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getBody());
                _builder_15.append(_visit_20);
                _builder_15.append("\"));");
                _builder_15.newLineIfNotEmpty();
                sb.append(_builder_15);
              } else {
                boolean dec = this.declaredDomainIninit.contains(object.getCodomain().getName());
                if ((!dec)) {
                  StringConcatenation _builder_16 = new StringConcatenation();
                  String _name_16 = object.getCodomain().getName();
                  _builder_16.append(_name_16);
                  _builder_16.append("  ");
                  String _name_17 = object.getCodomain().getName();
                  _builder_16.append(_name_17);
                  _builder_16.append("_elem = new  ");
                  String _name_18 = object.getCodomain().getName();
                  _builder_16.append(_name_18);
                  _builder_16.append("();");
                  sb.append(_builder_16);
                  this.declaredDomainIninit.add(object.getCodomain().getName());
                } else {
                  StringConcatenation _builder_17 = new StringConcatenation();
                  String _name_19 = object.getCodomain().getName();
                  _builder_17.append(_name_19);
                  _builder_17.append("_elem = new  ");
                  String _name_20 = object.getCodomain().getName();
                  _builder_17.append(_name_20);
                  _builder_17.append("();");
                  sb.append(_builder_17);
                }
                if (((object.getCodomain() instanceof ConcreteDomain) && 
                  (((ConcreteDomain) object.getCodomain()).getTypeDomain() instanceof PowersetDomain))) {
                  StringConcatenation _builder_18 = new StringConcatenation();
                  String _name_21 = object.getCodomain().getName();
                  _builder_18.append(_name_21);
                  _builder_18.append("_elem.value = new HashSet<>(Arrays.asList");
                  String _visit_21 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getBody());
                  _builder_18.append(_visit_21);
                  _builder_18.append(");");
                  sb.append(_builder_18);
                } else {
                  StringConcatenation _builder_19 = new StringConcatenation();
                  String _name_22 = object.getCodomain().getName();
                  _builder_19.append(_name_22);
                  _builder_19.append("_elem.value = ");
                  String _visit_22 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getBody());
                  _builder_19.append(_visit_22);
                  _builder_19.append(";");
                  sb.append(_builder_19);
                }
                StringConcatenation _builder_20 = new StringConcatenation();
                String _name_23 = object.getName();
                _builder_20.append(_name_23);
                _builder_20.append(".init(");
                String _name_24 = object.getCodomain().getName();
                _builder_20.append(_name_24);
                _builder_20.append("_elem);");
                sb.append(_builder_20);
              }
            }
          }
        }
      }
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
        if ((_domain_1 instanceof ConcreteDomain)) {
          StringConcatenation _builder = new StringConcatenation();
          _builder.newLine();
          _builder.append("for(");
          Domain _domain_2 = object.getInitialization().get(0).getVariable().get(i).getDomain();
          String _visit = new DomainToJavaString(this.asm).visit(((ConcreteDomain) _domain_2).getTypeDomain());
          _builder.append(_visit);
          _builder.append(" ");
          String _visit_1 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(i));
          _builder.append(_visit_1);
          _builder.append(": ");
          String _visit_2 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
          _builder.append(_visit_2);
          _builder.append(".elems){");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.newLine();
          _builder.append("\t");
          String _visit_3 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
          _builder.append(_visit_3, "\t");
          _builder.append(" ");
          String _visit_4 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(i));
          _builder.append(_visit_4, "\t");
          _builder.append("Val = new ");
          String _visit_5 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
          _builder.append(_visit_5, "\t");
          _builder.append("();");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          String _visit_6 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(i));
          _builder.append(_visit_6, "\t");
          _builder.append("Val.value = ");
          String _visit_7 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(i));
          _builder.append(_visit_7, "\t");
          _builder.append(";");
          _builder.newLineIfNotEmpty();
          _builder.newLine();
          sb.append(_builder);
        } else {
          Domain _domain_3 = object.getInitialization().get(0).getVariable().get(i).getDomain();
          if ((_domain_3 instanceof AbstractTd)) {
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.append("for(");
            String _visit_8 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
            _builder_1.append(_visit_8);
            _builder_1.append(" ");
            String _visit_9 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(i));
            _builder_1.append(_visit_9);
            _builder_1.append(": ");
            String _visit_10 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
            _builder_1.append(_visit_10);
            _builder_1.append(".elems){");
            _builder_1.newLineIfNotEmpty();
            sb.append(_builder_1);
          } else {
            Domain _domain_4 = object.getInitialization().get(0).getVariable().get(i).getDomain();
            if ((_domain_4 instanceof EnumTd)) {
              StringConcatenation _builder_2 = new StringConcatenation();
              _builder_2.append("for(");
              String _visit_11 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
              _builder_2.append(_visit_11);
              _builder_2.append(" ");
              String _visit_12 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(i));
              _builder_2.append(_visit_12);
              _builder_2.append(": ");
              String _visit_13 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
              _builder_2.append(_visit_13);
              _builder_2.append(".values()){");
              _builder_2.newLineIfNotEmpty();
              sb.append(_builder_2);
            } else {
              StringConcatenation _builder_3 = new StringConcatenation();
              _builder_3.append("for(");
              String _visit_14 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
              _builder_3.append(_visit_14);
              _builder_3.append(" ");
              String _visit_15 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(i));
              _builder_3.append(_visit_15);
              _builder_3.append(": ");
              String _visit_16 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
              _builder_3.append(_visit_16);
              _builder_3.append(".elems){");
              _builder_3.newLineIfNotEmpty();
              sb.append(_builder_3);
            }
          }
        }
      }
      Domain _codomain = object.getCodomain();
      if ((_codomain instanceof AbstractTd)) {
        String a = new TermToJava(this.asm).visit(object.getInitialization().get(0).getBody());
        String b = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(this.i));
        boolean _equals = a.equals(b);
        if (_equals) {
          StringConcatenation _builder = new StringConcatenation();
          String _name = object.getName();
          _builder.append(_name);
          _builder.append(".values.put(");
          String _monitoredInitializationKey = this.monitoredInitializationKey(object, this.i);
          _builder.append(_monitoredInitializationKey);
          _builder.append(",");
          String _visit = new TermToJava(this.asm).visit(object.getInitialization().get(0).getBody());
          _builder.append(_visit);
          _builder.append(");");
          _builder.newLineIfNotEmpty();
          sb.append(_builder);
        } else {
          StringConcatenation _builder_1 = new StringConcatenation();
          String _visit_1 = new DomainToJavaString(this.asm).visit(object.getCodomain());
          _builder_1.append(_visit_1);
          _builder_1.append(" ");
          String _visit_2 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getBody());
          _builder_1.append(_visit_2);
          _builder_1.append(" = new ");
          String _visit_3 = new DomainToJavaString(this.asm).visit(object.getCodomain());
          _builder_1.append(_visit_3);
          _builder_1.append("(\"");
          String _visit_4 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getBody());
          _builder_1.append(_visit_4);
          _builder_1.append("\");");
          _builder_1.newLineIfNotEmpty();
          _builder_1.append("\t\t\t\t");
          _builder_1.newLine();
          _builder_1.append("\t\t\t\t      ");
          _builder_1.newLine();
          _builder_1.newLine();
          _builder_1.append("\t\t\t\t      ");
          _builder_1.newLine();
          _builder_1.append("\t\t\t\t      ");
          String _name_1 = object.getName();
          _builder_1.append(_name_1, "\t\t\t\t      ");
          _builder_1.append(".values.put(");
          String _monitoredInitializationKey_1 = this.monitoredInitializationKey(object, this.i);
          _builder_1.append(_monitoredInitializationKey_1, "\t\t\t\t      ");
          _builder_1.append(",");
          String _visit_5 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getBody());
          _builder_1.append(_visit_5, "\t\t\t\t      ");
          _builder_1.append(");");
          _builder_1.newLineIfNotEmpty();
          sb.append(_builder_1);
        }
      } else {
        if (((this.controllo(object.getCodomain().getName())).booleanValue() || (object.getCodomain() instanceof EnumTd))) {
          StringConcatenation _builder_2 = new StringConcatenation();
          String _visit_6 = new DomainToJavaString(this.asm).visit(object.getCodomain());
          _builder_2.append(_visit_6);
          _builder_2.append(" a ");
          String _visit_7 = new TermToJavaInAssignments(this.asm).visit(object.getInitialization().get(0).getBody());
          _builder_2.append(_visit_7);
          _builder_2.append(";");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("\t\t\t\t");
          _builder_2.newLine();
          _builder_2.append("\t\t\t\t      ");
          _builder_2.newLine();
          _builder_2.newLine();
          _builder_2.append("\t\t\t\t      ");
          _builder_2.newLine();
          _builder_2.append("\t\t\t\t      ");
          String _name_2 = object.getName();
          _builder_2.append(_name_2, "\t\t\t\t      ");
          _builder_2.append(".values.put(");
          String _monitoredInitializationKey_2 = this.monitoredInitializationKey(object, this.i);
          _builder_2.append(_monitoredInitializationKey_2, "\t\t\t\t      ");
          _builder_2.append(",a);");
          _builder_2.newLineIfNotEmpty();
          sb.append(_builder_2);
        } else {
          StringConcatenation _builder_3 = new StringConcatenation();
          String _visit_8 = new DomainToJavaString(this.asm).visit(object.getCodomain());
          _builder_3.append(_visit_8);
          _builder_3.append(" a = new ");
          String _visit_9 = new DomainToJavaString(this.asm).visit(object.getCodomain());
          _builder_3.append(_visit_9);
          _builder_3.append("();");
          _builder_3.newLineIfNotEmpty();
          _builder_3.append("\t\t\t    ");
          _builder_3.newLine();
          _builder_3.append("\t\t\t           ");
          _builder_3.append("a.value ");
          String _visit_10 = new TermToJavaInAssignments(this.asm).visit(object.getInitialization().get(0).getBody());
          _builder_3.append(_visit_10, "\t\t\t           ");
          _builder_3.append(";");
          _builder_3.newLineIfNotEmpty();
          sb.append(_builder_3);
          Domain _domain_1 = object.getDomain();
          if ((_domain_1 instanceof ProductDomain)) {
            Domain _domain_2 = object.getDomain();
            final IntFunction<String> _function = (int index) -> {
              String _visit_11 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(index));
              return (_visit_11 + "Val");
            };
            final String productKey = ProductToJava.value(((ProductDomain) _domain_2), _function);
            String _name_3 = object.getName();
            String _plus = (" " + _name_3);
            String _plus_1 = (_plus + "_elem = ");
            String _plus_2 = (_plus_1 + productKey);
            String _plus_3 = (_plus_2 + ";\n");
            sb.append(_plus_3);
            StringConcatenation _builder_4 = new StringConcatenation();
            String _name_4 = object.getName();
            _builder_4.append(_name_4);
            _builder_4.append(".values.put(");
            String _name_5 = object.getName();
            _builder_4.append(_name_5);
            _builder_4.append("_elem,a);");
            _builder_4.newLineIfNotEmpty();
            sb.append(_builder_4);
          }
          StringConcatenation _builder_5 = new StringConcatenation();
          String _name_6 = object.getName();
          _builder_5.append(_name_6);
          _builder_5.append(".values.put(");
          String _monitoredInitializationKey_3 = this.monitoredInitializationKey(object, this.i);
          _builder_5.append(_monitoredInitializationKey_3);
          _builder_5.append(",a);");
          _builder_5.newLineIfNotEmpty();
          sb.append(_builder_5);
        }
      }
      for (int i = 0; (i < object.getInitialization().get(0).getVariable().size()); i++) {
        StringConcatenation _builder_6 = new StringConcatenation();
        _builder_6.append("}");
        sb.append(_builder_6);
      }
    } else {
      if (((this.controllo(object.getCodomain().getName())).booleanValue() || (object.getCodomain() instanceof EnumTd))) {
        StringConcatenation _builder_6 = new StringConcatenation();
        String _name_7 = object.getName();
        _builder_6.append(_name_7);
        _builder_6.append(".value = ");
        String _visit_11 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getBody());
        _builder_6.append(_visit_11);
        _builder_6.append(";");
        _builder_6.newLineIfNotEmpty();
        sb.append(_builder_6);
      } else {
        StringConcatenation _builder_7 = new StringConcatenation();
        _builder_7.newLine();
        String _name_8 = object.getCodomain().getName();
        _builder_7.append(_name_8);
        _builder_7.append("_elem.value = ");
        String _visit_12 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getBody());
        _builder_7.append(_visit_12);
        _builder_7.append(";");
        _builder_7.newLineIfNotEmpty();
        _builder_7.newLine();
        String _name_9 = object.getName();
        _builder_7.append(_name_9);
        _builder_7.append(".value = ");
        String _name_10 = object.getName();
        _builder_7.append(_name_10);
        _builder_7.append(".value = ");
        String _name_11 = object.getCodomain().getName();
        _builder_7.append(_name_11);
        _builder_7.append("_elem;");
        _builder_7.newLineIfNotEmpty();
        _builder_7.newLine();
        sb.append(_builder_7);
      }
    }
    return sb.toString();
  }

  public String visit(final DerivedFunction object) {
    StringBuffer sb = new StringBuffer();
    Domain _domain = object.getDomain();
    boolean _tripleNotEquals = (_domain != null);
    if (_tripleNotEquals) {
      Term _body = object.getDefinition().getBody();
      if ((_body instanceof ForallTerm)) {
        StringConcatenation _builder = new StringConcatenation();
        String _visit = new DomainToJavaString(this.asm).visit(object.getCodomain());
        _builder.append(_visit);
        _builder.append(" ");
        String _name = object.getName();
        _builder.append(_name);
        _builder.append("( return ");
        String _adaptRuleParam = new Util().adaptRuleParam(object.getDefinition().getVariable(), this.asm);
        _builder.append(_adaptRuleParam);
        _builder.append("){ ");
        String _visit_1 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
        _builder.append(_visit_1);
        _builder.append("}");
        sb.append(_builder);
      } else {
        Domain _codomain = object.getCodomain();
        if ((_codomain instanceof ConcreteDomain)) {
          StringConcatenation _builder_1 = new StringConcatenation();
          String _visit_2 = new DomainToJavaString(this.asm).visit(object.getCodomain());
          _builder_1.append(_visit_2);
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
          String _visit_3 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
          _builder_1.append(_visit_3, "\t\t\t\t\t");
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
          String _visit_4 = new DomainToJavaString(this.asm).visit(object.getCodomain());
          _builder_2.append(_visit_4);
          _builder_2.append(" ");
          String _name_4 = object.getName();
          _builder_2.append(_name_4);
          _builder_2.append("(");
          String _adaptRuleParam_2 = new Util().adaptRuleParam(object.getDefinition().getVariable(), this.asm);
          _builder_2.append(_adaptRuleParam_2);
          _builder_2.append("){ return ");
          String _visit_5 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
          _builder_2.append(_visit_5);
          _builder_2.append(";}");
          sb.append(_builder_2);
        }
      }
    } else {
      Term _body_1 = object.getDefinition().getBody();
      if ((_body_1 instanceof ForallTerm)) {
        StringConcatenation _builder_3 = new StringConcatenation();
        String _visit_6 = new DomainToJavaString(this.asm).visit(object.getCodomain());
        _builder_3.append(_visit_6);
        _builder_3.append(" ");
        String _name_5 = object.getName();
        _builder_3.append(_name_5);
        _builder_3.append("(){ return ");
        String _visit_7 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
        _builder_3.append(_visit_7);
        _builder_3.append("}");
        sb.append(_builder_3);
      } else {
        Domain _codomain_1 = object.getCodomain();
        if ((_codomain_1 instanceof ConcreteDomain)) {
          StringConcatenation _builder_4 = new StringConcatenation();
          String _visit_8 = new DomainToJavaString(this.asm).visit(object.getCodomain());
          _builder_4.append(_visit_8);
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
          String _visit_9 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
          _builder_4.append(_visit_9, "\t\t\t\t\t");
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
          String _visit_10 = new DomainToJavaString(this.asm).visit(object.getCodomain());
          _builder_5.append(_visit_10);
          _builder_5.append(" ");
          String _name_9 = object.getName();
          _builder_5.append(_name_9);
          _builder_5.append("(){ return ");
          String _visit_11 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
          _builder_5.append(_visit_11);
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
        String _visit = new DomainToJavaString(this.asm).visit(object.getCodomain());
        _builder.append(_visit);
        _builder.append(" ");
        String _name = object.getName();
        _builder.append(_name);
        _builder.append("( return ");
        String _adaptRuleParam = new Util().adaptRuleParam(object.getDefinition().getVariable(), this.asm);
        _builder.append(_adaptRuleParam);
        _builder.append("){ ");
        String _visit_1 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
        _builder.append(_visit_1);
        _builder.append("}");
        sb.append(_builder);
      } else {
        Domain _codomain = object.getCodomain();
        if ((_codomain instanceof ConcreteDomain)) {
          StringConcatenation _builder_1 = new StringConcatenation();
          String _visit_2 = new DomainToJavaString(this.asm).visit(object.getCodomain());
          _builder_1.append(_visit_2);
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
          String _visit_3 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
          _builder_1.append(_visit_3, "\t\t\t\t\t");
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
            String _javaType = Util.javaType(object.getCodomain(), this.asm);
            _builder_2.append(_javaType);
            _builder_2.append(" ");
            String _name_4 = object.getName();
            _builder_2.append(_name_4);
            _builder_2.append("(");
            String _adaptRuleParam_2 = new Util().adaptRuleParam(object.getDefinition().getVariable(), this.asm);
            _builder_2.append(_adaptRuleParam_2);
            _builder_2.append("){return ");
            String _visit_4 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
            _builder_2.append(_visit_4);
            _builder_2.append(";}");
            sb.append(_builder_2);
          } else {
            StringConcatenation _builder_3 = new StringConcatenation();
            String _visit_5 = new DomainToJavaString(this.asm).visit(object.getCodomain());
            _builder_3.append(_visit_5);
            _builder_3.append(" ");
            String _name_5 = object.getName();
            _builder_3.append(_name_5);
            _builder_3.append("(");
            String _adaptRuleParam_3 = new Util().adaptRuleParam(object.getDefinition().getVariable(), this.asm);
            _builder_3.append(_adaptRuleParam_3);
            _builder_3.append("){ return ");
            String _visit_6 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
            _builder_3.append(_visit_6);
            _builder_3.append(";}");
            sb.append(_builder_3);
          }
        }
      }
    } else {
      Term _body_1 = object.getDefinition().getBody();
      if ((_body_1 instanceof ForallTerm)) {
        StringConcatenation _builder_4 = new StringConcatenation();
        String _visit_7 = new DomainToJavaString(this.asm).visit(object.getCodomain());
        _builder_4.append(_visit_7);
        _builder_4.append(" ");
        String _name_6 = object.getName();
        _builder_4.append(_name_6);
        _builder_4.append("(){ return ");
        String _visit_8 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
        _builder_4.append(_visit_8);
        _builder_4.append("}");
        sb.append(_builder_4);
      } else {
        Domain _codomain_1 = object.getCodomain();
        if ((_codomain_1 instanceof ConcreteDomain)) {
          StringConcatenation _builder_5 = new StringConcatenation();
          String _visit_9 = new DomainToJavaString(this.asm).visit(object.getCodomain());
          _builder_5.append(_visit_9);
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
          String _visit_10 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
          _builder_5.append(_visit_10, "\t\t\t\t\t");
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
            String _javaType_1 = Util.javaType(object.getCodomain(), this.asm);
            _builder_6.append(_javaType_1);
            _builder_6.append(" ");
            String _name_10 = object.getName();
            _builder_6.append(_name_10);
            _builder_6.append("(){ return ");
            String _visit_11 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
            _builder_6.append(_visit_11);
            _builder_6.append(";}");
            sb.append(_builder_6);
          } else {
            StringConcatenation _builder_7 = new StringConcatenation();
            String _visit_12 = new DomainToJavaString(this.asm).visit(object.getCodomain());
            _builder_7.append(_visit_12);
            _builder_7.append(" ");
            String _name_11 = object.getName();
            _builder_7.append(_name_11);
            _builder_7.append("(){ return ");
            String _visit_13 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
            _builder_7.append(_visit_13);
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
