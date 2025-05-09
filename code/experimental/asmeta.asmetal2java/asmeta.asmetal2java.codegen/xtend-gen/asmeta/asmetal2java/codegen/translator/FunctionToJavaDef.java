package asmeta.asmetal2java.codegen.translator;

import asmeta.definitions.ControlledFunction;
import asmeta.definitions.DerivedFunction;
import asmeta.definitions.MonitoredFunction;
import asmeta.definitions.StaticFunction;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.ProductDomain;
import asmeta.definitions.domains.SequenceDomain;
import asmeta.structure.Asm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.furtherterms.CaseTerm;
import asmeta.terms.furtherterms.ForallTerm;
import asmeta.terms.furtherterms.SequenceTerm;
import java.util.ArrayList;
import java.util.List;
import org.asmeta.parser.util.ReflectiveVisitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class FunctionToJavaDef extends ReflectiveVisitor<String> {
  private Asm asm;

  private int i;

  private final List<String> declaredDomainIninit = new ArrayList<String>();

  public FunctionToJavaDef(final Asm asm) {
    this.asm = asm;
  }

  public String visit(final SequenceTerm object) {
    StringBuffer sb = new StringBuffer();
    EList<Term> _terms = object.getTerms();
    for (final Term t : _terms) {
      String _visit = new TermToJava(this.asm).visit(t);
      String _plus = (_visit + ",");
      sb.append(_plus);
    }
    String _string = sb.toString();
    int _length = sb.toString().length();
    int _minus = (_length - 1);
    return _string.substring(0, _minus);
  }

  public String visit(final ControlledFunction object) {
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
      Domain _domain = object.getDomain();
      boolean _tripleNotEquals = (_domain != null);
      if (_tripleNotEquals) {
        for (int i = 0; (i < object.getInitialization().get(0).getVariable().size()); i++) {
          {
            boolean _isNotNumerable = new Util().isNotNumerable(object.getInitialization().get(0).getVariable().get(i).getDomain());
            if (_isNotNumerable) {
              StringConcatenation _builder_1 = new StringConcatenation();
              _builder_1.append("//NOT IMPLEMENTED IN Java (FunctionToCpp line 50)");
              _builder_1.newLine();
              sb.append(_builder_1);
              return sb.toString();
            }
            Domain _domain_1 = object.getInitialization().get(0).getVariable().get(i).getDomain();
            if ((_domain_1 instanceof ConcreteDomain)) {
              StringConcatenation _builder_2 = new StringConcatenation();
              _builder_2.newLine();
              _builder_2.append("for(int ");
              String _visit_1 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(i));
              _builder_2.append(_visit_1);
              _builder_2.append("=0; ");
              String _visit_2 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(i));
              _builder_2.append(_visit_2);
              _builder_2.append(" < ");
              String _visit_3 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
              _builder_2.append(_visit_3);
              _builder_2.append(".elems.size(); ");
              String _visit_4 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(i));
              _builder_2.append(_visit_4);
              _builder_2.append("++ ){");
              _builder_2.newLineIfNotEmpty();
              _builder_2.append("\t");
              _builder_2.newLine();
              _builder_2.append("\t");
              _builder_2.newLine();
              _builder_2.append("\t");
              String _visit_5 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
              _builder_2.append(_visit_5, "\t");
              _builder_2.append("_elem.value = ");
              String _visit_6 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
              _builder_2.append(_visit_6, "\t");
              _builder_2.append(".elems.get(");
              String _visit_7 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(i));
              _builder_2.append(_visit_7, "\t");
              _builder_2.append(");");
              _builder_2.newLineIfNotEmpty();
              _builder_2.newLine();
              sb.append(_builder_2);
            } else {
              Domain _domain_2 = object.getInitialization().get(0).getVariable().get(i).getDomain();
              if ((_domain_2 instanceof AbstractTd)) {
                StringConcatenation _builder_3 = new StringConcatenation();
                _builder_3.append("for(");
                String _visit_8 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
                _builder_3.append(_visit_8);
                _builder_3.append(" ");
                String _visit_9 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(i));
                _builder_3.append(_visit_9);
                _builder_3.append(": ");
                String _visit_10 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
                _builder_3.append(_visit_10);
                _builder_3.append(".elems){");
                _builder_3.newLineIfNotEmpty();
                sb.append(_builder_3);
              } else {
                Domain _domain_3 = object.getInitialization().get(0).getVariable().get(i).getDomain();
                if ((_domain_3 instanceof EnumTd)) {
                  StringConcatenation _builder_4 = new StringConcatenation();
                  _builder_4.append("for(");
                  String _visit_11 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
                  _builder_4.append(_visit_11);
                  _builder_4.append(" ");
                  String _visit_12 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(i));
                  _builder_4.append(_visit_12);
                  _builder_4.append(": ");
                  String _visit_13 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
                  _builder_4.append(_visit_13);
                  _builder_4.append(".values()){");
                  _builder_4.newLineIfNotEmpty();
                  sb.append(_builder_4);
                } else {
                  StringConcatenation _builder_5 = new StringConcatenation();
                  _builder_5.append("for(");
                  String _visit_14 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
                  _builder_5.append(_visit_14);
                  _builder_5.append(" ");
                  String _visit_15 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(i));
                  _builder_5.append(_visit_15);
                  _builder_5.append(": ");
                  String _visit_16 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
                  _builder_5.append(_visit_16);
                  _builder_5.append(".elems){");
                  _builder_5.newLineIfNotEmpty();
                  sb.append(_builder_5);
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
            StringConcatenation _builder_1 = new StringConcatenation();
            String _name_1 = object.getName();
            _builder_1.append(_name_1);
            _builder_1.append(".init(");
            String _visit_1 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(this.i));
            _builder_1.append(_visit_1);
            _builder_1.append(",");
            String _visit_2 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getBody());
            _builder_1.append(_visit_2);
            _builder_1.append(");");
            _builder_1.newLineIfNotEmpty();
            sb.append(_builder_1);
          } else {
            Term _body = object.getInitialization().get(0).getBody();
            if ((_body instanceof CaseTerm)) {
              StringConcatenation _builder_2 = new StringConcatenation();
              String _name_2 = object.getName();
              _builder_2.append(_name_2);
              _builder_2.append(".init(");
              String _visit_3 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(this.i));
              _builder_2.append(_visit_3);
              _builder_2.append(",new Function<Void,");
              String _visit_4 = new DomainToJavaString(this.asm).visit(object.getCodomain());
              _builder_2.append(_visit_4);
              _builder_2.append(">(){@Override public ");
              String _visit_5 = new DomainToJavaString(this.asm).visit(object.getCodomain());
              _builder_2.append(_visit_5);
              _builder_2.append(" apply(Void input) {");
              String _visit_6 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getBody());
              _builder_2.append(_visit_6);
              _builder_2.append("}}.apply(null));");
              _builder_2.newLineIfNotEmpty();
              sb.append(_builder_2);
            } else {
              StringConcatenation _builder_3 = new StringConcatenation();
              String _visit_7 = new DomainToJavaString(this.asm).visit(object.getCodomain());
              _builder_3.append(_visit_7);
              _builder_3.append(" ");
              String _visit_8 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getBody());
              _builder_3.append(_visit_8);
              _builder_3.append(" = new ");
              String _visit_9 = new DomainToJavaString(this.asm).visit(object.getCodomain());
              _builder_3.append(_visit_9);
              _builder_3.append("(\"");
              String _visit_10 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getBody());
              _builder_3.append(_visit_10);
              _builder_3.append("\");");
              sb.append(_builder_3);
              StringConcatenation _builder_4 = new StringConcatenation();
              String _name_3 = object.getName();
              _builder_4.append(_name_3);
              _builder_4.append(".init(");
              String _visit_11 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(this.i));
              _builder_4.append(_visit_11);
              _builder_4.append(",");
              String _visit_12 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getBody());
              _builder_4.append(_visit_12);
              _builder_4.append(");");
              _builder_4.newLineIfNotEmpty();
              sb.append(_builder_4);
            }
          }
        } else {
          if (((this.controllo(object.getCodomain().getName())).booleanValue() || (object.getCodomain() instanceof EnumTd))) {
            if (((object.getDomain() instanceof ConcreteDomain) && (this.controllo(object.getCodomain().getName())).booleanValue())) {
              StringConcatenation _builder_5 = new StringConcatenation();
              String _visit_13 = new DomainToJavaString(this.asm).visit(object.getCodomain());
              _builder_5.append(_visit_13);
              _builder_5.append(" a ");
              String _visit_14 = new TermToJavaInAssignments(this.asm).visit(object.getInitialization().get(0).getBody());
              _builder_5.append(_visit_14);
              _builder_5.append(";");
              sb.append(_builder_5);
              StringConcatenation _builder_6 = new StringConcatenation();
              String _name_4 = object.getName();
              _builder_6.append(_name_4);
              _builder_6.append(".init(");
              String _name_5 = object.getDomain().getName();
              _builder_6.append(_name_5);
              _builder_6.append("_elem,a);");
              _builder_6.newLineIfNotEmpty();
              sb.append(_builder_6);
            } else {
              StringConcatenation _builder_7 = new StringConcatenation();
              String _visit_15 = new DomainToJavaString(this.asm).visit(object.getCodomain());
              _builder_7.append(_visit_15);
              _builder_7.append(" a ");
              String _visit_16 = new TermToJavaInAssignments(this.asm).visit(object.getInitialization().get(0).getBody());
              _builder_7.append(_visit_16);
              _builder_7.append(";");
              sb.append(_builder_7);
              StringConcatenation _builder_8 = new StringConcatenation();
              String _name_6 = object.getName();
              _builder_8.append(_name_6);
              _builder_8.append(".init(");
              String _visit_17 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(this.i));
              _builder_8.append(_visit_17);
              _builder_8.append(",a);");
              _builder_8.newLineIfNotEmpty();
              sb.append(_builder_8);
            }
          } else {
            StringConcatenation _builder_9 = new StringConcatenation();
            String _visit_18 = new DomainToJavaString(this.asm).visit(object.getCodomain());
            _builder_9.append(_visit_18);
            _builder_9.append(" a = new ");
            String _visit_19 = new DomainToJavaString(this.asm).visit(object.getCodomain());
            _builder_9.append(_visit_19);
            _builder_9.append("();");
            _builder_9.newLineIfNotEmpty();
            _builder_9.append("\t\t\t    ");
            _builder_9.newLine();
            _builder_9.append("\t\t\t           ");
            _builder_9.append("a.value ");
            String _visit_20 = new TermToJavaInAssignments(this.asm).visit(object.getInitialization().get(0).getBody());
            _builder_9.append(_visit_20, "\t\t\t           ");
            _builder_9.append(";");
            _builder_9.newLineIfNotEmpty();
            sb.append(_builder_9);
            Domain _domain_1 = object.getDomain();
            if ((_domain_1 instanceof ProductDomain)) {
              StringConcatenation _builder_10 = new StringConcatenation();
              _builder_10.append(" ");
              String _name_7 = object.getName();
              _builder_10.append(_name_7, " ");
              _builder_10.append("_elem = new ");
              sb.append(_builder_10);
              int _size = object.getInitialization().get(0).getVariable().size();
              switch (_size) {
                case 2:
                  StringConcatenation _builder_11 = new StringConcatenation();
                  _builder_11.append("Pair<");
                  sb.append(_builder_11);
                  break;
                case 3:
                  StringConcatenation _builder_12 = new StringConcatenation();
                  _builder_12.append("Triplet<");
                  sb.append(_builder_12);
                  break;
                case 4:
                  StringConcatenation _builder_13 = new StringConcatenation();
                  _builder_13.append("Quartet<");
                  sb.append(_builder_13);
                  break;
                case 5:
                  StringConcatenation _builder_14 = new StringConcatenation();
                  _builder_14.append("Quintet<");
                  sb.append(_builder_14);
                  break;
                case 6:
                  StringConcatenation _builder_15 = new StringConcatenation();
                  _builder_15.append("Sextet<");
                  sb.append(_builder_15);
                  break;
                case 7:
                  StringConcatenation _builder_16 = new StringConcatenation();
                  _builder_16.append("Septet<");
                  sb.append(_builder_16);
                  break;
                case 8:
                  StringConcatenation _builder_17 = new StringConcatenation();
                  _builder_17.append("Octet<");
                  sb.append(_builder_17);
                  break;
                case 9:
                  StringConcatenation _builder_18 = new StringConcatenation();
                  _builder_18.append("Ennead<");
                  sb.append(_builder_18);
                  break;
                case 10:
                  StringConcatenation _builder_19 = new StringConcatenation();
                  _builder_19.append("Decade<");
                  sb.append(_builder_19);
                  break;
              }
              for (int i = 0; (i < object.getInitialization().get(0).getVariable().size()); i++) {
                int _size_1 = object.getInitialization().get(0).getVariable().size();
                int _minus = (_size_1 - 1);
                boolean _notEquals = (i != _minus);
                if (_notEquals) {
                  StringConcatenation _builder_20 = new StringConcatenation();
                  String _visit_21 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
                  _builder_20.append(_visit_21);
                  _builder_20.append(",");
                  sb.append(_builder_20);
                } else {
                  StringConcatenation _builder_21 = new StringConcatenation();
                  String _visit_22 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
                  _builder_21.append(_visit_22);
                  _builder_21.append(">(");
                  sb.append(_builder_21);
                }
              }
              for (int i = 0; (i < object.getInitialization().get(0).getVariable().size()); i++) {
                int _size_1 = object.getInitialization().get(0).getVariable().size();
                int _minus = (_size_1 - 1);
                boolean _notEquals = (i != _minus);
                if (_notEquals) {
                  StringConcatenation _builder_20 = new StringConcatenation();
                  String _visit_21 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
                  _builder_20.append(_visit_21);
                  _builder_20.append("_elem,");
                  sb.append(_builder_20);
                } else {
                  StringConcatenation _builder_21 = new StringConcatenation();
                  String _visit_22 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
                  _builder_21.append(_visit_22);
                  _builder_21.append("_elem);");
                  _builder_21.newLineIfNotEmpty();
                  sb.append(_builder_21);
                }
              }
              StringConcatenation _builder_20 = new StringConcatenation();
              String _name_8 = object.getName();
              _builder_20.append(_name_8);
              _builder_20.append(".init(");
              String _name_9 = object.getName();
              _builder_20.append(_name_9);
              _builder_20.append("_elem,a);");
              _builder_20.newLineIfNotEmpty();
              sb.append(_builder_20);
            } else {
              StringConcatenation _builder_21 = new StringConcatenation();
              String _name_10 = object.getName();
              _builder_21.append(_name_10);
              _builder_21.append(".init(");
              String _visit_21 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(this.i));
              _builder_21.append(_visit_21);
              _builder_21.append(",a);");
              _builder_21.newLineIfNotEmpty();
              sb.append(_builder_21);
            }
          }
        }
        for (int i = 0; (i < object.getInitialization().get(0).getVariable().size()); i++) {
          StringConcatenation _builder_22 = new StringConcatenation();
          _builder_22.append("}");
          sb.append(_builder_22);
        }
      } else {
        if (((this.controllo(object.getCodomain().getName())).booleanValue() || (object.getCodomain() instanceof EnumTd))) {
          StringConcatenation _builder_22 = new StringConcatenation();
          String _name_11 = object.getName();
          _builder_22.append(_name_11);
          _builder_22.append(".init(");
          String _visit_22 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getBody());
          _builder_22.append(_visit_22);
          _builder_22.append(");");
          _builder_22.newLineIfNotEmpty();
          sb.append(_builder_22);
        } else {
          Domain _codomain_1 = object.getCodomain();
          if ((_codomain_1 instanceof AbstractTd)) {
            StringConcatenation _builder_23 = new StringConcatenation();
            String _name_12 = object.getName();
            _builder_23.append(_name_12);
            _builder_23.append(".init(");
            String _name_13 = object.getCodomain().getName();
            _builder_23.append(_name_13);
            _builder_23.append(".get(\"");
            String _visit_23 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getBody());
            _builder_23.append(_visit_23);
            _builder_23.append("\"));");
            _builder_23.newLineIfNotEmpty();
            sb.append(_builder_23);
          } else {
            boolean dec = this.declaredDomainIninit.contains(object.getCodomain().getName());
            if ((!dec)) {
              StringConcatenation _builder_24 = new StringConcatenation();
              String _name_14 = object.getCodomain().getName();
              _builder_24.append(_name_14);
              _builder_24.append("  ");
              String _name_15 = object.getCodomain().getName();
              _builder_24.append(_name_15);
              _builder_24.append("_elem = new  ");
              String _name_16 = object.getCodomain().getName();
              _builder_24.append(_name_16);
              _builder_24.append("();");
              sb.append(_builder_24);
              this.declaredDomainIninit.add(object.getCodomain().getName());
            } else {
              StringConcatenation _builder_25 = new StringConcatenation();
              String _name_17 = object.getCodomain().getName();
              _builder_25.append(_name_17);
              _builder_25.append("_elem = new  ");
              String _name_18 = object.getCodomain().getName();
              _builder_25.append(_name_18);
              _builder_25.append("();");
              sb.append(_builder_25);
            }
            StringConcatenation _builder_26 = new StringConcatenation();
            String _name_19 = object.getCodomain().getName();
            _builder_26.append(_name_19);
            _builder_26.append("_elem.value = ");
            String _visit_24 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getBody());
            _builder_26.append(_visit_24);
            _builder_26.append(";");
            sb.append(_builder_26);
            StringConcatenation _builder_27 = new StringConcatenation();
            String _name_20 = object.getName();
            _builder_27.append(_name_20);
            _builder_27.append(".init(");
            String _name_21 = object.getCodomain().getName();
            _builder_27.append(_name_21);
            _builder_27.append("_elem);");
            sb.append(_builder_27);
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
          _builder.append("for(int ");
          String _visit = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(i));
          _builder.append(_visit);
          _builder.append("=0; ");
          String _visit_1 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(i));
          _builder.append(_visit_1);
          _builder.append(" < ");
          String _visit_2 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
          _builder.append(_visit_2);
          _builder.append(".elems.size()-1; ");
          String _visit_3 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(i));
          _builder.append(_visit_3);
          _builder.append("++ ){");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.newLine();
          _builder.append("\t");
          String _visit_4 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
          _builder.append(_visit_4, "\t");
          _builder.append(" ");
          String _visit_5 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(i));
          _builder.append(_visit_5, "\t");
          _builder.append("Val = new ");
          String _visit_6 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
          _builder.append(_visit_6, "\t");
          _builder.append("();");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          String _visit_7 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(i));
          _builder.append(_visit_7, "\t");
          _builder.append("Val.value = ");
          String _visit_8 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
          _builder.append(_visit_8, "\t");
          _builder.append(".elems.get(");
          String _visit_9 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(i));
          _builder.append(_visit_9, "\t");
          _builder.append(");");
          _builder.newLineIfNotEmpty();
          _builder.newLine();
          sb.append(_builder);
        } else {
          Domain _domain_2 = object.getInitialization().get(0).getVariable().get(i).getDomain();
          if ((_domain_2 instanceof AbstractTd)) {
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.append("for(");
            String _visit_10 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
            _builder_1.append(_visit_10);
            _builder_1.append(" ");
            String _visit_11 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(i));
            _builder_1.append(_visit_11);
            _builder_1.append(": ");
            String _visit_12 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
            _builder_1.append(_visit_12);
            _builder_1.append(".elems){");
            _builder_1.newLineIfNotEmpty();
            sb.append(_builder_1);
          } else {
            Domain _domain_3 = object.getInitialization().get(0).getVariable().get(i).getDomain();
            if ((_domain_3 instanceof EnumTd)) {
              StringConcatenation _builder_2 = new StringConcatenation();
              _builder_2.append("for(");
              String _visit_13 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
              _builder_2.append(_visit_13);
              _builder_2.append(" ");
              String _visit_14 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(i));
              _builder_2.append(_visit_14);
              _builder_2.append(": ");
              String _visit_15 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
              _builder_2.append(_visit_15);
              _builder_2.append(".values()){");
              _builder_2.newLineIfNotEmpty();
              sb.append(_builder_2);
            } else {
              StringConcatenation _builder_3 = new StringConcatenation();
              _builder_3.append("for(");
              String _visit_16 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
              _builder_3.append(_visit_16);
              _builder_3.append(" ");
              String _visit_17 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(i));
              _builder_3.append(_visit_17);
              _builder_3.append(": ");
              String _visit_18 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
              _builder_3.append(_visit_18);
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
          String _visit = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(this.i));
          _builder.append(_visit);
          _builder.append(",");
          String _visit_1 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getBody());
          _builder.append(_visit_1);
          _builder.append(");");
          _builder.newLineIfNotEmpty();
          sb.append(_builder);
        } else {
          StringConcatenation _builder_1 = new StringConcatenation();
          String _visit_2 = new DomainToJavaString(this.asm).visit(object.getCodomain());
          _builder_1.append(_visit_2);
          _builder_1.append(" ");
          String _visit_3 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getBody());
          _builder_1.append(_visit_3);
          _builder_1.append(" = new ");
          String _visit_4 = new DomainToJavaString(this.asm).visit(object.getCodomain());
          _builder_1.append(_visit_4);
          _builder_1.append("(\"");
          String _visit_5 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getBody());
          _builder_1.append(_visit_5);
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
          String _visit_6 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(this.i));
          _builder_1.append(_visit_6, "\t\t\t\t      ");
          _builder_1.append(",");
          String _visit_7 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getBody());
          _builder_1.append(_visit_7, "\t\t\t\t      ");
          _builder_1.append(");");
          _builder_1.newLineIfNotEmpty();
          sb.append(_builder_1);
        }
      } else {
        if (((this.controllo(object.getCodomain().getName())).booleanValue() || (object.getCodomain() instanceof EnumTd))) {
          StringConcatenation _builder_2 = new StringConcatenation();
          String _visit_8 = new DomainToJavaString(this.asm).visit(object.getCodomain());
          _builder_2.append(_visit_8);
          _builder_2.append(" a ");
          String _visit_9 = new TermToJavaInAssignments(this.asm).visit(object.getInitialization().get(0).getBody());
          _builder_2.append(_visit_9);
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
          String _visit_10 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(this.i));
          _builder_2.append(_visit_10, "\t\t\t\t      ");
          _builder_2.append(",a);");
          _builder_2.newLineIfNotEmpty();
          sb.append(_builder_2);
        } else {
          StringConcatenation _builder_3 = new StringConcatenation();
          String _visit_11 = new DomainToJavaString(this.asm).visit(object.getCodomain());
          _builder_3.append(_visit_11);
          _builder_3.append(" a = new ");
          String _visit_12 = new DomainToJavaString(this.asm).visit(object.getCodomain());
          _builder_3.append(_visit_12);
          _builder_3.append("();");
          _builder_3.newLineIfNotEmpty();
          _builder_3.append("\t\t\t    ");
          _builder_3.newLine();
          _builder_3.append("\t\t\t           ");
          _builder_3.append("a.value ");
          String _visit_13 = new TermToJavaInAssignments(this.asm).visit(object.getInitialization().get(0).getBody());
          _builder_3.append(_visit_13, "\t\t\t           ");
          _builder_3.append(";");
          _builder_3.newLineIfNotEmpty();
          sb.append(_builder_3);
          Domain _domain_1 = object.getDomain();
          if ((_domain_1 instanceof ProductDomain)) {
            StringConcatenation _builder_4 = new StringConcatenation();
            _builder_4.append(" ");
            String _name_3 = object.getName();
            _builder_4.append(_name_3, " ");
            _builder_4.append("_elem = new ");
            sb.append(_builder_4);
            int _size = object.getInitialization().get(0).getVariable().size();
            switch (_size) {
              case 2:
                StringConcatenation _builder_5 = new StringConcatenation();
                _builder_5.append("Pair<");
                sb.append(_builder_5);
                break;
              case 3:
                StringConcatenation _builder_6 = new StringConcatenation();
                _builder_6.append("Triplet<");
                sb.append(_builder_6);
                break;
              case 4:
                StringConcatenation _builder_7 = new StringConcatenation();
                _builder_7.append("Quartet<");
                sb.append(_builder_7);
                break;
              case 5:
                StringConcatenation _builder_8 = new StringConcatenation();
                _builder_8.append("Quintet<");
                sb.append(_builder_8);
                break;
              case 6:
                StringConcatenation _builder_9 = new StringConcatenation();
                _builder_9.append("Sextet<");
                sb.append(_builder_9);
                break;
              case 7:
                StringConcatenation _builder_10 = new StringConcatenation();
                _builder_10.append("Septet<");
                sb.append(_builder_10);
                break;
              case 8:
                StringConcatenation _builder_11 = new StringConcatenation();
                _builder_11.append("Octet<");
                sb.append(_builder_11);
                break;
              case 9:
                StringConcatenation _builder_12 = new StringConcatenation();
                _builder_12.append("Ennead<");
                sb.append(_builder_12);
                break;
              case 10:
                StringConcatenation _builder_13 = new StringConcatenation();
                _builder_13.append("Decade<");
                sb.append(_builder_13);
                break;
            }
            for (int i = 0; (i < object.getInitialization().get(0).getVariable().size()); i++) {
              int _size_1 = object.getInitialization().get(0).getVariable().size();
              int _minus = (_size_1 - 1);
              boolean _notEquals = (i != _minus);
              if (_notEquals) {
                StringConcatenation _builder_14 = new StringConcatenation();
                String _visit_14 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
                _builder_14.append(_visit_14);
                _builder_14.append(",");
                sb.append(_builder_14);
              } else {
                StringConcatenation _builder_15 = new StringConcatenation();
                String _visit_15 = new DomainToJavaString(this.asm).visit(object.getInitialization().get(0).getVariable().get(i).getDomain());
                _builder_15.append(_visit_15);
                _builder_15.append(">(");
                sb.append(_builder_15);
              }
            }
            for (int i = 0; (i < object.getInitialization().get(0).getVariable().size()); i++) {
              int _size_1 = object.getInitialization().get(0).getVariable().size();
              int _minus = (_size_1 - 1);
              boolean _notEquals = (i != _minus);
              if (_notEquals) {
                StringConcatenation _builder_14 = new StringConcatenation();
                String _visit_14 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(i));
                _builder_14.append(_visit_14);
                _builder_14.append("Val,");
                sb.append(_builder_14);
              } else {
                StringConcatenation _builder_15 = new StringConcatenation();
                String _visit_15 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(i));
                _builder_15.append(_visit_15);
                _builder_15.append("Val);");
                _builder_15.newLineIfNotEmpty();
                sb.append(_builder_15);
              }
            }
            StringConcatenation _builder_14 = new StringConcatenation();
            String _name_4 = object.getName();
            _builder_14.append(_name_4);
            _builder_14.append(".values.put(");
            String _name_5 = object.getName();
            _builder_14.append(_name_5);
            _builder_14.append("_elem,a);");
            _builder_14.newLineIfNotEmpty();
            sb.append(_builder_14);
          }
          StringConcatenation _builder_15 = new StringConcatenation();
          String _name_6 = object.getName();
          _builder_15.append(_name_6);
          _builder_15.append(".values.put(");
          String _visit_14 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getVariable().get(this.i));
          _builder_15.append(_visit_14);
          _builder_15.append(",a);");
          _builder_15.newLineIfNotEmpty();
          sb.append(_builder_15);
        }
      }
      for (int i = 0; (i < object.getInitialization().get(0).getVariable().size()); i++) {
        StringConcatenation _builder_16 = new StringConcatenation();
        _builder_16.append("}");
        sb.append(_builder_16);
      }
    } else {
      if (((this.controllo(object.getCodomain().getName())).booleanValue() || (object.getCodomain() instanceof EnumTd))) {
        StringConcatenation _builder_16 = new StringConcatenation();
        String _name_7 = object.getName();
        _builder_16.append(_name_7);
        _builder_16.append(".value = ");
        String _visit_15 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getBody());
        _builder_16.append(_visit_15);
        _builder_16.append(";");
        _builder_16.newLineIfNotEmpty();
        sb.append(_builder_16);
      } else {
        StringConcatenation _builder_17 = new StringConcatenation();
        _builder_17.newLine();
        String _name_8 = object.getCodomain().getName();
        _builder_17.append(_name_8);
        _builder_17.append("_elem.value = ");
        String _visit_16 = new TermToJava(this.asm).visit(object.getInitialization().get(0).getBody());
        _builder_17.append(_visit_16);
        _builder_17.append(";");
        _builder_17.newLineIfNotEmpty();
        _builder_17.newLine();
        String _name_9 = object.getName();
        _builder_17.append(_name_9);
        _builder_17.append(".value = ");
        String _name_10 = object.getName();
        _builder_17.append(_name_10);
        _builder_17.append(".value = ");
        String _name_11 = object.getCodomain().getName();
        _builder_17.append(_name_11);
        _builder_17.append("_elem;");
        _builder_17.newLineIfNotEmpty();
        _builder_17.newLine();
        sb.append(_builder_17);
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
      if ((_body instanceof CaseTerm)) {
        StringConcatenation _builder = new StringConcatenation();
        String _visit = new DomainToJavaString(this.asm).visit(object.getCodomain());
        _builder.append(_visit);
        _builder.append(" ");
        String _name = object.getName();
        _builder.append(_name);
        _builder.append("(");
        String _adaptRuleParam = new Util().adaptRuleParam(object.getDefinition().getVariable(), this.asm);
        _builder.append(_adaptRuleParam);
        _builder.append("){ ");
        String _visit_1 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
        _builder.append(_visit_1);
        _builder.append("}");
        sb.append(_builder);
      } else {
        Term _body_1 = object.getDefinition().getBody();
        if ((_body_1 instanceof ForallTerm)) {
          StringConcatenation _builder_1 = new StringConcatenation();
          String _visit_2 = new DomainToJavaString(this.asm).visit(object.getCodomain());
          _builder_1.append(_visit_2);
          _builder_1.append(" ");
          String _name_1 = object.getName();
          _builder_1.append(_name_1);
          _builder_1.append("( return ");
          String _adaptRuleParam_1 = new Util().adaptRuleParam(object.getDefinition().getVariable(), this.asm);
          _builder_1.append(_adaptRuleParam_1);
          _builder_1.append("){ ");
          String _visit_3 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
          _builder_1.append(_visit_3);
          _builder_1.append("}");
          sb.append(_builder_1);
        } else {
          Domain _codomain = object.getCodomain();
          if ((_codomain instanceof ConcreteDomain)) {
            StringConcatenation _builder_2 = new StringConcatenation();
            String _visit_4 = new DomainToJavaString(this.asm).visit(object.getCodomain());
            _builder_2.append(_visit_4);
            _builder_2.append(" ");
            String _name_2 = object.getName();
            _builder_2.append(_name_2);
            _builder_2.append("(");
            String _adaptRuleParam_2 = new Util().adaptRuleParam(object.getDefinition().getVariable(), this.asm);
            _builder_2.append(_adaptRuleParam_2);
            _builder_2.append("){");
            _builder_2.newLineIfNotEmpty();
            _builder_2.append("\t\t\t\t\t");
            _builder_2.newLine();
            _builder_2.append("\t\t\t\t\t");
            String _name_3 = object.getCodomain().getName();
            _builder_2.append(_name_3, "\t\t\t\t\t");
            _builder_2.append(" supp = new ");
            String _name_4 = object.getCodomain().getName();
            _builder_2.append(_name_4, "\t\t\t\t\t");
            _builder_2.append("();");
            _builder_2.newLineIfNotEmpty();
            _builder_2.append("\t\t\t\t\t");
            _builder_2.append("supp.value = ");
            String _visit_5 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
            _builder_2.append(_visit_5, "\t\t\t\t\t");
            _builder_2.append(";");
            _builder_2.newLineIfNotEmpty();
            _builder_2.append("\t\t\t\t\t");
            _builder_2.newLine();
            _builder_2.append("\t\t\t\t\t");
            _builder_2.append("return supp;");
            _builder_2.newLine();
            _builder_2.append("\t\t\t\t");
            _builder_2.append("}");
            sb.append(_builder_2);
          } else {
            StringConcatenation _builder_3 = new StringConcatenation();
            String _visit_6 = new DomainToJavaString(this.asm).visit(object.getCodomain());
            _builder_3.append(_visit_6);
            _builder_3.append(" ");
            String _name_5 = object.getName();
            _builder_3.append(_name_5);
            _builder_3.append("(");
            String _adaptRuleParam_3 = new Util().adaptRuleParam(object.getDefinition().getVariable(), this.asm);
            _builder_3.append(_adaptRuleParam_3);
            _builder_3.append("){return ");
            String _visit_7 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
            _builder_3.append(_visit_7);
            _builder_3.append(";}");
            sb.append(_builder_3);
          }
        }
      }
    } else {
      Term _body_2 = object.getDefinition().getBody();
      if ((_body_2 instanceof CaseTerm)) {
        StringConcatenation _builder_4 = new StringConcatenation();
        String _visit_8 = new DomainToJavaString(this.asm).visit(object.getCodomain());
        _builder_4.append(_visit_8);
        _builder_4.append(" ");
        String _name_6 = object.getName();
        _builder_4.append(_name_6);
        _builder_4.append("(){ ");
        String _visit_9 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
        _builder_4.append(_visit_9);
        _builder_4.append("}");
        sb.append(_builder_4);
      } else {
        Term _body_3 = object.getDefinition().getBody();
        if ((_body_3 instanceof ForallTerm)) {
          StringConcatenation _builder_5 = new StringConcatenation();
          String _visit_10 = new DomainToJavaString(this.asm).visit(object.getCodomain());
          _builder_5.append(_visit_10);
          _builder_5.append(" ");
          String _name_7 = object.getName();
          _builder_5.append(_name_7);
          _builder_5.append("(){ return ");
          String _visit_11 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
          _builder_5.append(_visit_11);
          _builder_5.append("}");
          sb.append(_builder_5);
        } else {
          Domain _codomain_1 = object.getCodomain();
          if ((_codomain_1 instanceof ConcreteDomain)) {
            StringConcatenation _builder_6 = new StringConcatenation();
            String _visit_12 = new DomainToJavaString(this.asm).visit(object.getCodomain());
            _builder_6.append(_visit_12);
            _builder_6.append(" ");
            String _name_8 = object.getName();
            _builder_6.append(_name_8);
            _builder_6.append("(){");
            _builder_6.newLineIfNotEmpty();
            _builder_6.append("\t\t\t\t\t");
            _builder_6.newLine();
            _builder_6.append("\t\t\t\t\t");
            String _name_9 = object.getCodomain().getName();
            _builder_6.append(_name_9, "\t\t\t\t\t");
            _builder_6.append("_elem.value = ");
            String _visit_13 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
            _builder_6.append(_visit_13, "\t\t\t\t\t");
            _builder_6.append(";");
            _builder_6.newLineIfNotEmpty();
            _builder_6.append("\t\t\t\t\t");
            _builder_6.newLine();
            _builder_6.append("\t\t\t\t\t");
            _builder_6.append("return ");
            String _name_10 = object.getCodomain().getName();
            _builder_6.append(_name_10, "\t\t\t\t\t");
            _builder_6.append("_elem;");
            _builder_6.newLineIfNotEmpty();
            _builder_6.append("\t\t\t\t");
            _builder_6.append("}");
            sb.append(_builder_6);
          } else {
            StringConcatenation _builder_7 = new StringConcatenation();
            String _visit_14 = new DomainToJavaString(this.asm).visit(object.getCodomain());
            _builder_7.append(_visit_14);
            _builder_7.append(" ");
            String _name_11 = object.getName();
            _builder_7.append(_name_11);
            _builder_7.append("(){return ");
            String _visit_15 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
            _builder_7.append(_visit_15);
            _builder_7.append(";}");
            sb.append(_builder_7);
          }
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
      if ((_body instanceof CaseTerm)) {
        StringConcatenation _builder = new StringConcatenation();
        String _visit = new DomainToJavaString(this.asm).visit(object.getCodomain());
        _builder.append(_visit);
        _builder.append(" ");
        String _name = object.getName();
        _builder.append(_name);
        _builder.append("(");
        String _adaptRuleParam = new Util().adaptRuleParam(object.getDefinition().getVariable(), this.asm);
        _builder.append(_adaptRuleParam);
        _builder.append("){ ");
        String _visit_1 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
        _builder.append(_visit_1);
        _builder.append("}");
        sb.append(_builder);
      } else {
        Term _body_1 = object.getDefinition().getBody();
        if ((_body_1 instanceof ForallTerm)) {
          StringConcatenation _builder_1 = new StringConcatenation();
          String _visit_2 = new DomainToJavaString(this.asm).visit(object.getCodomain());
          _builder_1.append(_visit_2);
          _builder_1.append(" ");
          String _name_1 = object.getName();
          _builder_1.append(_name_1);
          _builder_1.append("( return ");
          String _adaptRuleParam_1 = new Util().adaptRuleParam(object.getDefinition().getVariable(), this.asm);
          _builder_1.append(_adaptRuleParam_1);
          _builder_1.append("){ ");
          String _visit_3 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
          _builder_1.append(_visit_3);
          _builder_1.append("}");
          sb.append(_builder_1);
        } else {
          Domain _codomain = object.getCodomain();
          if ((_codomain instanceof ConcreteDomain)) {
            StringConcatenation _builder_2 = new StringConcatenation();
            String _visit_4 = new DomainToJavaString(this.asm).visit(object.getCodomain());
            _builder_2.append(_visit_4);
            _builder_2.append(" ");
            String _name_2 = object.getName();
            _builder_2.append(_name_2);
            _builder_2.append("(");
            String _adaptRuleParam_2 = new Util().adaptRuleParam(object.getDefinition().getVariable(), this.asm);
            _builder_2.append(_adaptRuleParam_2);
            _builder_2.append("){");
            _builder_2.newLineIfNotEmpty();
            _builder_2.append("\t\t\t\t\t");
            _builder_2.newLine();
            _builder_2.append("\t\t\t\t\t");
            String _name_3 = object.getCodomain().getName();
            _builder_2.append(_name_3, "\t\t\t\t\t");
            _builder_2.append(" supp = new ");
            String _name_4 = object.getCodomain().getName();
            _builder_2.append(_name_4, "\t\t\t\t\t");
            _builder_2.append("();");
            _builder_2.newLineIfNotEmpty();
            _builder_2.append("\t\t\t\t\t");
            _builder_2.append("supp.value = ");
            String _visit_5 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
            _builder_2.append(_visit_5, "\t\t\t\t\t");
            _builder_2.append(";");
            _builder_2.newLineIfNotEmpty();
            _builder_2.append("\t\t\t\t\t");
            _builder_2.newLine();
            _builder_2.append("\t\t\t\t\t");
            _builder_2.append("return supp;");
            _builder_2.newLine();
            _builder_2.append("\t\t\t\t");
            _builder_2.append("}");
            sb.append(_builder_2);
          } else {
            Domain _codomain_1 = object.getCodomain();
            if ((_codomain_1 instanceof SequenceDomain)) {
              StringConcatenation _builder_3 = new StringConcatenation();
              _builder_3.append("ArrayList");
              String _visit_6 = new DomainToJavaString(this.asm).visit(object.getCodomain());
              _builder_3.append(_visit_6);
              _builder_3.append(" ");
              String _name_5 = object.getName();
              _builder_3.append(_name_5);
              _builder_3.append("(ArrayList");
              String _adaptRuleParam_3 = new Util().adaptRuleParam(object.getDefinition().getVariable(), this.asm);
              _builder_3.append(_adaptRuleParam_3);
              _builder_3.append("){return ");
              String _visit_7 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
              _builder_3.append(_visit_7);
              _builder_3.append(";}");
              sb.append(_builder_3);
            } else {
              StringConcatenation _builder_4 = new StringConcatenation();
              String _visit_8 = new DomainToJavaString(this.asm).visit(object.getCodomain());
              _builder_4.append(_visit_8);
              _builder_4.append(" ");
              String _name_6 = object.getName();
              _builder_4.append(_name_6);
              _builder_4.append("(");
              String _adaptRuleParam_4 = new Util().adaptRuleParam(object.getDefinition().getVariable(), this.asm);
              _builder_4.append(_adaptRuleParam_4);
              _builder_4.append("){return ");
              String _visit_9 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
              _builder_4.append(_visit_9);
              _builder_4.append(";}");
              sb.append(_builder_4);
            }
          }
        }
      }
    } else {
      Term _body_2 = object.getDefinition().getBody();
      if ((_body_2 instanceof CaseTerm)) {
        StringConcatenation _builder_5 = new StringConcatenation();
        String _visit_10 = new DomainToJavaString(this.asm).visit(object.getCodomain());
        _builder_5.append(_visit_10);
        _builder_5.append(" ");
        String _name_7 = object.getName();
        _builder_5.append(_name_7);
        _builder_5.append("(){ ");
        String _visit_11 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
        _builder_5.append(_visit_11);
        _builder_5.append("}");
        sb.append(_builder_5);
      } else {
        Term _body_3 = object.getDefinition().getBody();
        if ((_body_3 instanceof ForallTerm)) {
          StringConcatenation _builder_6 = new StringConcatenation();
          String _visit_12 = new DomainToJavaString(this.asm).visit(object.getCodomain());
          _builder_6.append(_visit_12);
          _builder_6.append(" ");
          String _name_8 = object.getName();
          _builder_6.append(_name_8);
          _builder_6.append("(){ return ");
          String _visit_13 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
          _builder_6.append(_visit_13);
          _builder_6.append("}");
          sb.append(_builder_6);
        } else {
          Domain _codomain_2 = object.getCodomain();
          if ((_codomain_2 instanceof ConcreteDomain)) {
            StringConcatenation _builder_7 = new StringConcatenation();
            String _visit_14 = new DomainToJavaString(this.asm).visit(object.getCodomain());
            _builder_7.append(_visit_14);
            _builder_7.append(" ");
            String _name_9 = object.getName();
            _builder_7.append(_name_9);
            _builder_7.append("(){");
            _builder_7.newLineIfNotEmpty();
            _builder_7.append("\t\t\t\t\t");
            _builder_7.newLine();
            _builder_7.append("\t\t\t\t\t");
            String _name_10 = object.getCodomain().getName();
            _builder_7.append(_name_10, "\t\t\t\t\t");
            _builder_7.append(" supp = new ");
            String _name_11 = object.getCodomain().getName();
            _builder_7.append(_name_11, "\t\t\t\t\t");
            _builder_7.append("();");
            _builder_7.newLineIfNotEmpty();
            _builder_7.append("\t\t\t\t\t");
            _builder_7.newLine();
            _builder_7.append("\t\t\t\t\t");
            _builder_7.append("supp.value = ");
            String _visit_15 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
            _builder_7.append(_visit_15, "\t\t\t\t\t");
            _builder_7.append(";");
            _builder_7.newLineIfNotEmpty();
            _builder_7.append("\t\t\t\t\t");
            _builder_7.newLine();
            _builder_7.append("\t\t\t\t\t");
            _builder_7.append("return supp;");
            _builder_7.newLine();
            _builder_7.append("\t\t\t\t");
            _builder_7.append("}");
            sb.append(_builder_7);
          } else {
            StringConcatenation _builder_8 = new StringConcatenation();
            String _visit_16 = new DomainToJavaString(this.asm).visit(object.getCodomain());
            _builder_8.append(_visit_16);
            _builder_8.append(" ");
            String _name_12 = object.getName();
            _builder_8.append(_name_12);
            _builder_8.append("(){return ");
            String _visit_17 = new TermToJava(this.asm).visit(object.getDefinition().getBody());
            _builder_8.append(_visit_17);
            _builder_8.append(";}");
            sb.append(_builder_8);
          }
        }
      }
    }
    return sb.toString();
  }

  public Boolean controllo(final String domain) {
    if ((((domain.equals("Integer") || domain.equals("String")) || domain.equals("Boolean")) || domain.equals("Real"))) {
      return Boolean.valueOf(true);
    } else {
      return Boolean.valueOf(false);
    }
  }
}
