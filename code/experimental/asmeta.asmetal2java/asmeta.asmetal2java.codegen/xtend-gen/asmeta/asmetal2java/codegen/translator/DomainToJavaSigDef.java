package asmeta.asmetal2java.codegen.translator;

import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.BagDomain;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.MapDomain;
import asmeta.definitions.domains.PowersetDomain;
import asmeta.definitions.domains.ProductDomain;
import asmeta.definitions.domains.RuleDomain;
import asmeta.definitions.domains.SequenceDomain;
import asmeta.definitions.domains.TypeDomain;
import asmeta.structure.Asm;
import asmeta.structure.DomainDefinition;
import asmeta.structure.DomainInitialization;
import asmeta.terms.basicterms.SetTerm;
import asmeta.terms.basicterms.Term;
import java.util.function.Function;
import org.asmeta.parser.util.ReflectiveVisitor;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * Translates the signature and the definition of the domains
 */
@SuppressWarnings("all")
public class DomainToJavaSigDef extends ReflectiveVisitor<String> {
  protected Asm res;

  public DomainToJavaSigDef(final Asm resource) {
    this.res = resource;
  }

  /**
   * Create an instance of the {@code DomainToJavaString} object.
   */
  protected DomainToJavaString createDomainToJavaString(final Asm resource) {
    return new DomainToJavaString(resource);
  }

  /**
   * if this is an instance of {@code DomainToJavaSigDef} it returns an empty string
   * because the field should not be private
   */
  protected String isPrivate() {
    return "";
  }

  public String visit(final ProductDomain object) {
    final Function<Domain, String> _function = (Domain domain) -> {
      return this.createDomainToJavaString(this.res).visit(domain);
    };
    return ProductToJava.type(object, _function);
  }

  public String visit(final DomainDefinition object) {
    if (((object.getDefinedDomain() instanceof ConcreteDomain) && 
      (((ConcreteDomain) object.getDefinedDomain()).getTypeDomain() instanceof PowersetDomain))) {
      Term _body = object.getBody();
      if ((_body instanceof SetTerm)) {
        Term _body_1 = object.getBody();
        final Function1<Term, String> _function = (Term it) -> {
          String _visit = new TermToJava(this.res).visit(it);
          String _plus = ("new HashSet<>(Arrays.asList" + _visit);
          return (_plus + ")");
        };
        String _join = IterableExtensions.join(ListExtensions.<Term, String>map(((SetTerm) _body_1).getTerm(), _function), ", ");
        String _plus = ("(" + _join);
        return (_plus + ")");
      }
      return new TermToJava(this.res).visit(object.getBody());
    }
    return new TermToJava(this.res).visit(object.getBody());
  }

  public String visit(final DomainInitialization object) {
    return new TermToJava(this.res).visit(object.getBody());
  }

  public String visit(final RuleDomain object) {
    throw new RuntimeException("RuleDomain not supported");
  }

  public String visit(final SequenceDomain object) {
    StringBuffer sb = new StringBuffer();
    String _xifexpression = null;
    Domain _domain = object.getDomain();
    if ((_domain instanceof SequenceDomain)) {
      Domain _domain_1 = object.getDomain();
      String _trim = this.visit(((SequenceDomain) _domain_1)).trim();
      _xifexpression = ("List" + _trim);
    } else {
      String _xifexpression_1 = null;
      Domain _domain_2 = object.getDomain();
      if ((_domain_2 instanceof PowersetDomain)) {
        Domain _domain_3 = object.getDomain();
        String _trim_1 = this.visit(((PowersetDomain) _domain_3)).trim();
        _xifexpression_1 = ("Set" + _trim_1);
      } else {
        _xifexpression_1 = this.createDomainToJavaString(this.res).visit(object.getDomain());
      }
      _xifexpression = _xifexpression_1;
    }
    final String elementType = _xifexpression;
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<");
    _builder.append(elementType);
    _builder.append("> ");
    sb.append(_builder);
    return sb.toString();
  }

  public String visit(final PowersetDomain object) {
    StringBuffer sb = new StringBuffer();
    String _xifexpression = null;
    Domain _baseDomain = object.getBaseDomain();
    if ((_baseDomain instanceof PowersetDomain)) {
      Domain _baseDomain_1 = object.getBaseDomain();
      String _trim = this.visit(((PowersetDomain) _baseDomain_1)).trim();
      _xifexpression = ("Set" + _trim);
    } else {
      String _xifexpression_1 = null;
      Domain _baseDomain_2 = object.getBaseDomain();
      if ((_baseDomain_2 instanceof SequenceDomain)) {
        Domain _baseDomain_3 = object.getBaseDomain();
        String _trim_1 = this.visit(((SequenceDomain) _baseDomain_3)).trim();
        _xifexpression_1 = ("List" + _trim_1);
      } else {
        _xifexpression_1 = this.createDomainToJavaString(this.res).visit(object.getBaseDomain());
      }
      _xifexpression = _xifexpression_1;
    }
    final String elementType = _xifexpression;
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<");
    _builder.append(elementType);
    _builder.append("> ");
    sb.append(_builder);
    return sb.toString();
  }

  public String visit(final BagDomain object) {
    StringBuffer sb = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<");
    String _visit = this.createDomainToJavaString(this.res).visit(object.getDomain());
    _builder.append(_visit);
    _builder.append("> ");
    sb.append(_builder);
    return sb.toString();
  }

  public String visit(final MapDomain object) {
    StringBuffer sb = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<");
    sb.append(_builder);
    StringConcatenation _builder_1 = new StringConcatenation();
    String _visit = this.createDomainToJavaString(this.res).visit(object.getSourceDomain());
    _builder_1.append(_visit);
    _builder_1.append(",");
    sb.append(_builder_1);
    StringConcatenation _builder_2 = new StringConcatenation();
    String _visit_1 = this.createDomainToJavaString(this.res).visit(object.getTargetDomain());
    _builder_2.append(_visit_1);
    sb.append(_builder_2);
    return sb.toString().substring(0, sb.length()).concat(">");
  }

  public String visit(final EnumTd object) {
    StringBuffer sb = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("enum ");
    String _name = object.getName();
    _builder.append(_name);
    _builder.append(" {");
    sb.append(_builder);
    for (int i = 0; (i < object.getElement().size()); i++) {
      int _size = object.getElement().size();
      int _minus = (_size - 1);
      boolean _notEquals = (i != _minus);
      if (_notEquals) {
        StringConcatenation _builder_1 = new StringConcatenation();
        String _visit = this.createDomainToJavaString(this.res).visit(object.getElement().get(i));
        _builder_1.append(_visit);
        _builder_1.append(", ");
        sb.append(_builder_1);
      } else {
        StringConcatenation _builder_2 = new StringConcatenation();
        String _visit_1 = this.createDomainToJavaString(this.res).visit(object.getElement().get(i));
        _builder_2.append(_visit_1);
        _builder_2.append("}");
        _builder_2.newLineIfNotEmpty();
        sb.append(_builder_2);
      }
    }
    sb.append(System.lineSeparator());
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("List<");
    String _name_1 = object.getName();
    _builder_1.append(_name_1);
    _builder_1.append("> ");
    String _name_2 = object.getName();
    _builder_1.append(_name_2);
    _builder_1.append("_elemsList = new ArrayList<>();");
    sb.append(_builder_1);
    return sb.toString();
  }

  public String visit(final AbstractTd object) {
    StringBuffer sb = new StringBuffer();
    Boolean _isDynamic = object.getIsDynamic();
    boolean _not = (!(_isDynamic).booleanValue());
    if (_not) {
    }
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("static class ");
    String _name = object.getName();
    _builder.append(_name);
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    String _isPrivate = this.isPrivate();
    _builder.append(_isPrivate, "\t");
    _builder.append("static List<");
    String _name_1 = object.getName();
    _builder.append(_name_1, "\t");
    _builder.append("> elems = new ArrayList<>();");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    String _isPrivate_1 = this.isPrivate();
    _builder.append(_isPrivate_1, "\t");
    _builder.append("static List<String> val = new ArrayList<>();");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    String _name_2 = object.getName();
    _builder.append(_name_2, "\t");
    _builder.append(" (String a) {");
    _builder.newLineIfNotEmpty();
    _builder.append("    \t");
    _builder.append("elems.add(this);");
    _builder.newLine();
    _builder.append("    \t");
    _builder.append("val.add(a);");
    _builder.newLine();
    _builder.append("    \t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("      ");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public String toString() {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("if (elems.contains(this)) {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("return val.get(elems.lastIndexOf(this));");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("} else");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("return null;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t  ");
    _builder.append("static ");
    String _name_3 = object.getName();
    _builder.append(_name_3, "\t  ");
    _builder.append(" get(String a) {");
    _builder.newLineIfNotEmpty();
    _builder.append("      \t");
    _builder.append("if(val.contains(a)) {");
    _builder.newLine();
    _builder.append("      \t\t");
    _builder.append("return elems.get(val.lastIndexOf(a));");
    _builder.newLine();
    _builder.append("      \t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("      \t\t");
    _builder.append("else return null;");
    _builder.newLine();
    _builder.append("      \t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("      ");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("List<String> ");
    String _name_4 = object.getName();
    _builder.append(_name_4, "      ");
    _builder.append("_elemsList = new ArrayList<>();");
    _builder.newLineIfNotEmpty();
    _builder.append("      ");
    _builder.append("List<");
    String _name_5 = object.getName();
    _builder.append(_name_5, "      ");
    _builder.append("> ");
    String _name_6 = object.getName();
    _builder.append(_name_6, "      ");
    _builder.append("_Class = new ArrayList<>();");
    _builder.newLineIfNotEmpty();
    sb.append(_builder);
    return sb.toString();
  }

  public String visit(final ConcreteDomain object) {
    StringBuffer sb = new StringBuffer();
    String _xifexpression = null;
    TypeDomain _typeDomain = object.getTypeDomain();
    if ((_typeDomain instanceof SequenceDomain)) {
      String _trim = this.createDomainToJavaString(this.res).visit(object.getTypeDomain()).trim();
      _xifexpression = ("List" + _trim);
    } else {
      String _xifexpression_1 = null;
      TypeDomain _typeDomain_1 = object.getTypeDomain();
      if ((_typeDomain_1 instanceof PowersetDomain)) {
        String _trim_1 = this.createDomainToJavaString(this.res).visit(object.getTypeDomain()).trim();
        _xifexpression_1 = ("Set" + _trim_1);
      } else {
        _xifexpression_1 = this.createDomainToJavaString(this.res).visit(object.getTypeDomain());
      }
      _xifexpression = _xifexpression_1;
    }
    final String type = _xifexpression;
    Boolean _isDynamic = object.getIsDynamic();
    if ((_isDynamic).booleanValue()) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("class  ");
      String _name = object.getName();
      _builder.append(_name);
      _builder.append("{");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("List<");
      _builder.append(type);
      _builder.append("> elems = new ArrayList<>();");
      _builder.newLineIfNotEmpty();
      _builder.append(type);
      _builder.append(" value;");
      _builder.newLineIfNotEmpty();
      String _name_1 = object.getName();
      _builder.append(_name_1);
      _builder.append("(");
      _builder.append(type);
      _builder.append(" i) {");
      _builder.newLineIfNotEmpty();
      _builder.append("   ");
      _builder.append("value = i;");
      _builder.newLine();
      _builder.append("   ");
      _builder.append("}");
      _builder.newLine();
      _builder.append("   ");
      _builder.append("}");
      _builder.newLine();
      _builder.append("   ");
      _builder.newLine();
      _builder.append("   ");
      _builder.append("List<");
      _builder.append(type, "   ");
      _builder.append("> ");
      String _name_2 = object.getName();
      _builder.append(_name_2, "   ");
      _builder.append("_elems = new ArrayList<>();");
      _builder.newLineIfNotEmpty();
      sb.append(_builder);
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("static class  ");
      String _name_3 = object.getName();
      _builder_1.append(_name_3);
      _builder_1.append(" {");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("\t\t\t\t");
      String _isPrivate = this.isPrivate();
      _builder_1.append(_isPrivate, "\t\t\t\t");
      _builder_1.append("static List<");
      _builder_1.append(type, "\t\t\t\t");
      _builder_1.append("> elems = new ArrayList<>();");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("                ");
      _builder_1.append(type, "                ");
      _builder_1.append(" value;");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("                ");
      _builder_1.newLine();
      _builder_1.append("                ");
      _builder_1.append("static ");
      String _name_4 = object.getName();
      _builder_1.append(_name_4, "                ");
      _builder_1.append(" valueOf(");
      _builder_1.append(type, "                ");
      _builder_1.append(" val) {");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("                \t");
      _builder_1.append("if(elems.contains(val)){");
      _builder_1.newLine();
      _builder_1.append("                \t\t");
      String _name_5 = object.getName();
      _builder_1.append(_name_5, "                \t\t");
      _builder_1.append(" n = new ");
      String _name_6 = object.getName();
      _builder_1.append(_name_6, "                \t\t");
      _builder_1.append("();");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("                \t\t");
      _builder_1.append("n.value = elems.get(elems.indexOf(val));");
      _builder_1.newLine();
      _builder_1.append("                \t\t");
      _builder_1.append("return n;");
      _builder_1.newLine();
      _builder_1.append("                \t");
      _builder_1.append("}");
      _builder_1.newLine();
      _builder_1.append("                \t");
      _builder_1.append("return null;");
      _builder_1.newLine();
      _builder_1.append("\t\t\t\t");
      _builder_1.append("}");
      _builder_1.newLine();
      _builder_1.append("\t\t\t\t");
      _builder_1.newLine();
      _builder_1.append("\t\t\t\t");
      _builder_1.append("static ");
      String _name_7 = object.getName();
      _builder_1.append(_name_7, "\t\t\t\t");
      _builder_1.append(" valueOf(");
      String _name_8 = object.getName();
      _builder_1.append(_name_8, "\t\t\t\t");
      _builder_1.append(" val) {");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("\t\t\t\t                \t");
      _builder_1.append("return val;");
      _builder_1.newLine();
      _builder_1.append("\t\t\t\t");
      _builder_1.append("}");
      _builder_1.newLine();
      _builder_1.append("\t\t\t\t");
      _builder_1.newLine();
      _builder_1.append("\t\t\t\t");
      _builder_1.append("@Override");
      _builder_1.newLine();
      _builder_1.append("\t\t\t\t\t");
      _builder_1.append("public boolean equals(Object obj) {");
      _builder_1.newLine();
      _builder_1.append("\t\t\t\t\t\t");
      _builder_1.append("if (!(obj instanceof ");
      String _name_9 = object.getName();
      _builder_1.append(_name_9, "\t\t\t\t\t\t");
      _builder_1.append(")) return false;");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("\t\t\t\t\t\t");
      _builder_1.append("return value.equals(((");
      String _name_10 = object.getName();
      _builder_1.append(_name_10, "\t\t\t\t\t\t");
      _builder_1.append(")obj).value);");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("\t\t\t\t\t");
      _builder_1.append("}");
      _builder_1.newLine();
      _builder_1.append("\t\t\t\t");
      _builder_1.newLine();
      _builder_1.append("\t\t\t\t");
      _builder_1.newLine();
      _builder_1.append("\t\t\t\t");
      _builder_1.append("@Override");
      _builder_1.newLine();
      _builder_1.append("\t\t\t\t\t\t");
      _builder_1.append("public int hashCode() {");
      _builder_1.newLine();
      _builder_1.append("\t\t\t\t\t\t\t");
      _builder_1.append("return value.hashCode();");
      _builder_1.newLine();
      _builder_1.append("\t\t\t\t\t\t");
      _builder_1.append("}");
      _builder_1.newLine();
      _builder_1.append("\t\t\t\t\t\t");
      _builder_1.newLine();
      _builder_1.append("\t\t\t\t");
      _builder_1.append("}");
      _builder_1.newLine();
      _builder_1.append("\t\t\t\t");
      _builder_1.newLine();
      _builder_1.append("\t\t\t\t");
      _builder_1.append("// TODO do not use this object to init - remove this line in the translation");
      _builder_1.newLine();
      _builder_1.append("\t\t\t\t");
      String _name_11 = object.getName();
      _builder_1.append(_name_11, "\t\t\t\t");
      _builder_1.append(" ");
      String _name_12 = object.getName();
      _builder_1.append(_name_12, "\t\t\t\t");
      _builder_1.append("_elem = new ");
      String _name_13 = object.getName();
      _builder_1.append(_name_13, "\t\t\t\t");
      _builder_1.append("();");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("\t\t\t\t");
      _builder_1.append("List<");
      _builder_1.append(type, "\t\t\t\t");
      _builder_1.append("> ");
      String _name_14 = object.getName();
      _builder_1.append(_name_14, "\t\t\t\t");
      _builder_1.append("_elems = new ArrayList<>();");
      _builder_1.newLineIfNotEmpty();
      sb.append(_builder_1);
    }
    return sb.toString();
  }
}
