package org.asmeta.asm2java;

import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.BagDomain;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.MapDomain;
import asmeta.definitions.domains.PowersetDomain;
import asmeta.definitions.domains.ProductDomain;
import asmeta.definitions.domains.RuleDomain;
import asmeta.definitions.domains.SequenceDomain;
import asmeta.structure.Asm;
import asmeta.structure.DomainDefinition;
import asmeta.structure.DomainInitialization;
import org.asmeta.parser.util.ReflectiveVisitor;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class DomainToJavaSigDef extends ReflectiveVisitor<String> {
  private Asm res;
  
  private boolean pointer;
  
  public DomainToJavaSigDef(final Asm resource) {
    this.res = resource;
    this.pointer = false;
  }
  
  public DomainToJavaSigDef(final Asm resource, final boolean pointer) {
    this.res = resource;
    this.pointer = pointer;
  }
  
  /**
   * Domain Signature
   */
  public String visit(final AbstractTd object) {
    StringBuffer sb = new StringBuffer();
    Boolean _isDynamic = object.getIsDynamic();
    if ((_isDynamic).booleanValue()) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("static class ");
      String _name = object.getName();
      _builder.append(_name);
      _builder.append(" {");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("static List<");
      String _name_1 = object.getName();
      _builder.append(_name_1);
      _builder.append("> elems = new ArrayList<");
      String _name_2 = object.getName();
      _builder.append(_name_2);
      _builder.append(">();");
      _builder.newLineIfNotEmpty();
      _builder.append("List<String> val = new ArrayList<String>();");
      _builder.newLine();
      _builder.newLine();
      String _name_3 = object.getName();
      _builder.append(_name_3);
      _builder.append(" (String a)");
      _builder.newLineIfNotEmpty();
      _builder.append("{");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("elems.add(this);");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("val.add(a);");
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      _builder.newLine();
      _builder.append("String ToString(");
      String _name_4 = object.getName();
      _builder.append(_name_4);
      _builder.append(" a)");
      _builder.newLineIfNotEmpty();
      _builder.append("{");
      _builder.newLine();
      _builder.append(" ");
      _builder.append("if(elems.contains(a))");
      _builder.newLine();
      _builder.append(" ");
      _builder.append("{");
      _builder.newLine();
      _builder.append("   ");
      _builder.append("return val.get(elems.lastIndexOf(a));");
      _builder.newLine();
      _builder.append(" ");
      _builder.append("}");
      _builder.newLine();
      _builder.newLine();
      _builder.append("else return null;");
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      _builder.newLine();
      String _name_5 = object.getName();
      _builder.append(_name_5);
      _builder.append(" get(String a)");
      _builder.newLineIfNotEmpty();
      _builder.append("{");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("if(val.contains(a))");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("{");
      _builder.newLine();
      _builder.append(" ");
      _builder.newLine();
      _builder.append("  \t");
      _builder.append("return elems.get(val.lastIndexOf(a));");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("} ");
      _builder.newLine();
      _builder.append(" ");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("else return null;");
      _builder.newLine();
      _builder.append(" ");
      _builder.append("}");
      _builder.newLine();
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      _builder.newLine();
      _builder.append("List<String> ");
      String _name_6 = object.getName();
      _builder.append(_name_6);
      _builder.append("_lista = new ArrayList<String>();");
      _builder.newLineIfNotEmpty();
      _builder.append("List<");
      String _name_7 = object.getName();
      _builder.append(_name_7);
      _builder.append("> ");
      String _name_8 = object.getName();
      _builder.append(_name_8);
      _builder.append("_Class = new ArrayList<");
      String _name_9 = object.getName();
      _builder.append(_name_9);
      _builder.append(">();");
      _builder.newLineIfNotEmpty();
      sb.append(_builder);
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("static class ");
      String _name_10 = object.getName();
      _builder_1.append(_name_10);
      _builder_1.append(" {");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("\t");
      _builder_1.newLine();
      _builder_1.append("static List<");
      String _name_11 = object.getName();
      _builder_1.append(_name_11);
      _builder_1.append("> elems = new ArrayList<");
      String _name_12 = object.getName();
      _builder_1.append(_name_12);
      _builder_1.append(">();");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("static List<String> val = new ArrayList<String>();");
      _builder_1.newLine();
      _builder_1.newLine();
      String _name_13 = object.getName();
      _builder_1.append(_name_13);
      _builder_1.append(" (String a)");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("{");
      _builder_1.newLine();
      _builder_1.append("    ");
      _builder_1.append("elems.add(this);");
      _builder_1.newLine();
      _builder_1.append("    ");
      _builder_1.append("val.add(a);");
      _builder_1.newLine();
      _builder_1.append("}");
      _builder_1.newLine();
      _builder_1.newLine();
      _builder_1.append("String ToString(");
      String _name_14 = object.getName();
      _builder_1.append(_name_14);
      _builder_1.append(" a)");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("{");
      _builder_1.newLine();
      _builder_1.append(" ");
      _builder_1.append("if(elems.contains(a))");
      _builder_1.newLine();
      _builder_1.append(" ");
      _builder_1.append("{");
      _builder_1.newLine();
      _builder_1.append("   ");
      _builder_1.append("return val.get(elems.lastIndexOf(a));");
      _builder_1.newLine();
      _builder_1.append(" ");
      _builder_1.append("}");
      _builder_1.newLine();
      _builder_1.newLine();
      _builder_1.append("else return null;");
      _builder_1.newLine();
      _builder_1.append("}");
      _builder_1.newLine();
      _builder_1.newLine();
      _builder_1.append("static ");
      String _name_15 = object.getName();
      _builder_1.append(_name_15);
      _builder_1.append(" get(String a)");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("{");
      _builder_1.newLine();
      _builder_1.append("  ");
      _builder_1.append("if(val.contains(a))");
      _builder_1.newLine();
      _builder_1.append("  ");
      _builder_1.append("{");
      _builder_1.newLine();
      _builder_1.append(" ");
      _builder_1.newLine();
      _builder_1.append("  \t");
      _builder_1.append("return elems.get(val.lastIndexOf(a));");
      _builder_1.newLine();
      _builder_1.append("  ");
      _builder_1.append("} ");
      _builder_1.newLine();
      _builder_1.append(" ");
      _builder_1.newLine();
      _builder_1.append("  ");
      _builder_1.append("else return null;");
      _builder_1.newLine();
      _builder_1.append(" ");
      _builder_1.append("}");
      _builder_1.newLine();
      _builder_1.newLine();
      _builder_1.append("}");
      _builder_1.newLine();
      _builder_1.newLine();
      _builder_1.append("List<String> ");
      String _name_16 = object.getName();
      _builder_1.append(_name_16);
      _builder_1.append("_lista = new ArrayList<String>();");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("List<");
      String _name_17 = object.getName();
      _builder_1.append(_name_17);
      _builder_1.append("> ");
      String _name_18 = object.getName();
      _builder_1.append(_name_18);
      _builder_1.append("_Class = new ArrayList<");
      String _name_19 = object.getName();
      _builder_1.append(_name_19);
      _builder_1.append(">();");
      _builder_1.newLineIfNotEmpty();
      sb.append(_builder_1);
    }
    return sb.toString();
  }
  
  public String visit(final ConcreteDomain object) {
    StringBuffer sb = new StringBuffer();
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
      String _visit = new ToString(this.res).visit(object.getTypeDomain());
      _builder.append(_visit);
      _builder.append("> elems = new ArrayList<");
      String _visit_1 = new ToString(this.res).visit(object.getTypeDomain());
      _builder.append(_visit_1);
      _builder.append(">();");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      String _visit_2 = new ToString(this.res).visit(object.getTypeDomain());
      _builder.append(_visit_2);
      _builder.append(" value;");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("\t\t\t\t  ");
      String _name_1 = object.getName();
      _builder.append(_name_1, "\t\t\t\t  ");
      _builder.append("(");
      String _visit_3 = new ToString(this.res).visit(object.getTypeDomain());
      _builder.append(_visit_3, "\t\t\t\t  ");
      _builder.append(" i) { value = i; }");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      _builder.newLine();
      _builder.append("List<");
      String _visit_4 = new ToString(this.res).visit(object.getTypeDomain());
      _builder.append(_visit_4);
      _builder.append("> ");
      String _name_2 = object.getName();
      _builder.append(_name_2);
      _builder.append("_elems = new ArrayList<");
      String _visit_5 = new ToString(this.res).visit(object.getTypeDomain());
      _builder.append(_visit_5);
      _builder.append(">();");
      _builder.newLineIfNotEmpty();
      sb.append(_builder);
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("static class  ");
      String _name_3 = object.getName();
      _builder_1.append(_name_3);
      _builder_1.append("{");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("\t\t\t\t");
      _builder_1.newLine();
      _builder_1.append("\t\t\t      ");
      _builder_1.append("static List<");
      String _visit_6 = new ToString(this.res).visit(object.getTypeDomain());
      _builder_1.append(_visit_6, "\t\t\t      ");
      _builder_1.append("> elems = new ArrayList<");
      String _visit_7 = new ToString(this.res).visit(object.getTypeDomain());
      _builder_1.append(_visit_7, "\t\t\t      ");
      _builder_1.append(">();");
      _builder_1.newLineIfNotEmpty();
      _builder_1.newLine();
      _builder_1.append("                  ");
      String _visit_8 = new ToString(this.res).visit(object.getTypeDomain());
      _builder_1.append(_visit_8, "                  ");
      _builder_1.append(" value;");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("\t\t\t\t");
      _builder_1.append("}");
      _builder_1.newLine();
      _builder_1.append("\t\t\t\t");
      _builder_1.newLine();
      _builder_1.append("\t\t\t\t");
      String _name_4 = object.getName();
      _builder_1.append(_name_4, "\t\t\t\t");
      _builder_1.append(" ");
      String _name_5 = object.getName();
      _builder_1.append(_name_5, "\t\t\t\t");
      _builder_1.append("_elem = new ");
      String _name_6 = object.getName();
      _builder_1.append(_name_6, "\t\t\t\t");
      _builder_1.append("();");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("\t\t\t\t");
      _builder_1.newLine();
      _builder_1.append("\t\t\t\t");
      _builder_1.append("List<");
      String _visit_9 = new ToString(this.res).visit(object.getTypeDomain());
      _builder_1.append(_visit_9, "\t\t\t\t");
      _builder_1.append("> ");
      String _name_7 = object.getName();
      _builder_1.append(_name_7, "\t\t\t\t");
      _builder_1.append("_elems = new ArrayList<");
      String _visit_10 = new ToString(this.res).visit(object.getTypeDomain());
      _builder_1.append(_visit_10, "\t\t\t\t");
      _builder_1.append(">();");
      _builder_1.newLineIfNotEmpty();
      sb.append(_builder_1);
    }
    return sb.toString();
  }
  
  public String visit(final EnumTd object) {
    StringBuffer sb = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("static enum ");
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
        String _visit = new ToString(this.res).visit(object.getElement().get(i));
        _builder_1.append(_visit);
        _builder_1.append(", ");
        sb.append(_builder_1);
      } else {
        StringConcatenation _builder_2 = new StringConcatenation();
        String _visit_1 = new ToString(this.res).visit(object.getElement().get(i));
        _builder_2.append(_visit_1);
        _builder_2.append("}");
        _builder_2.newLineIfNotEmpty();
        _builder_2.append("\t\t\t");
        _builder_2.newLine();
        sb.append(_builder_2);
      }
    }
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("List<");
    String _name_1 = object.getName();
    _builder_1.append(_name_1);
    _builder_1.append("> ");
    String _name_2 = object.getName();
    _builder_1.append(_name_2);
    _builder_1.append("_lista = new ArrayList<");
    String _name_3 = object.getName();
    _builder_1.append(_name_3);
    _builder_1.append(">();");
    _builder_1.newLineIfNotEmpty();
    sb.append(_builder_1);
    return sb.toString();
  }
  
  public String visit(final ProductDomain object) {
    StringBuffer sb = new StringBuffer();
    int _size = object.getDomains().size();
    switch (_size) {
      case 2:
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("Pair<");
        sb.append(_builder);
        for (int i = 0; (i < object.getDomains().size()); i++) {
          StringConcatenation _builder_1 = new StringConcatenation();
          String _visit = new ToString(this.res).visit(object.getDomains().get(i));
          _builder_1.append(_visit);
          _builder_1.append(", ");
          sb.append(_builder_1);
        }
        break;
      case 3:
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("Triplet<");
        sb.append(_builder_1);
        for (int i = 0; (i < object.getDomains().size()); i++) {
          StringConcatenation _builder_2 = new StringConcatenation();
          String _visit = new ToString(this.res).visit(object.getDomains().get(i));
          _builder_2.append(_visit);
          _builder_2.append(", ");
          sb.append(_builder_2);
        }
        break;
      case 4:
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("Quartet<");
        sb.append(_builder_2);
        for (int i = 0; (i < object.getDomains().size()); i++) {
          StringConcatenation _builder_3 = new StringConcatenation();
          String _visit = new ToString(this.res).visit(object.getDomains().get(i));
          _builder_3.append(_visit);
          _builder_3.append(", ");
          sb.append(_builder_3);
        }
        break;
      case 5:
        StringConcatenation _builder_3 = new StringConcatenation();
        _builder_3.append("Quintet<");
        sb.append(_builder_3);
        for (int i = 0; (i < object.getDomains().size()); i++) {
          StringConcatenation _builder_4 = new StringConcatenation();
          String _visit = new ToString(this.res).visit(object.getDomains().get(i));
          _builder_4.append(_visit);
          _builder_4.append(", ");
          sb.append(_builder_4);
        }
        break;
      case 6:
        StringConcatenation _builder_4 = new StringConcatenation();
        _builder_4.append("Sextet<");
        sb.append(_builder_4);
        for (int i = 0; (i < object.getDomains().size()); i++) {
          StringConcatenation _builder_5 = new StringConcatenation();
          String _visit = new ToString(this.res).visit(object.getDomains().get(i));
          _builder_5.append(_visit);
          _builder_5.append(", ");
          sb.append(_builder_5);
        }
        break;
      case 7:
        StringConcatenation _builder_5 = new StringConcatenation();
        _builder_5.append("Septet<");
        sb.append(_builder_5);
        for (int i = 0; (i < object.getDomains().size()); i++) {
          StringConcatenation _builder_6 = new StringConcatenation();
          String _visit = new ToString(this.res).visit(object.getDomains().get(i));
          _builder_6.append(_visit);
          _builder_6.append(", ");
          sb.append(_builder_6);
        }
        break;
      case 8:
        StringConcatenation _builder_6 = new StringConcatenation();
        _builder_6.append("Octet<");
        sb.append(_builder_6);
        for (int i = 0; (i < object.getDomains().size()); i++) {
          StringConcatenation _builder_7 = new StringConcatenation();
          String _visit = new ToString(this.res).visit(object.getDomains().get(i));
          _builder_7.append(_visit);
          _builder_7.append(", ");
          sb.append(_builder_7);
        }
        break;
      case 9:
        StringConcatenation _builder_7 = new StringConcatenation();
        _builder_7.append("Ennead<");
        sb.append(_builder_7);
        for (int i = 0; (i < object.getDomains().size()); i++) {
          StringConcatenation _builder_8 = new StringConcatenation();
          String _visit = new ToString(this.res).visit(object.getDomains().get(i));
          _builder_8.append(_visit);
          _builder_8.append(", ");
          sb.append(_builder_8);
        }
        break;
      case 10:
        StringConcatenation _builder_8 = new StringConcatenation();
        _builder_8.append("Decade<");
        sb.append(_builder_8);
        for (int i = 0; (i < object.getDomains().size()); i++) {
          StringConcatenation _builder_9 = new StringConcatenation();
          String _visit = new ToString(this.res).visit(object.getDomains().get(i));
          _builder_9.append(_visit);
          _builder_9.append(", ");
          sb.append(_builder_9);
        }
        break;
    }
    String _string = sb.toString();
    int _length = sb.length();
    int _minus = (_length - 2);
    return _string.substring(0, _minus).concat(">");
  }
  
  public String visit(final SequenceDomain object) {
    StringBuffer sb = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<");
    String _visit = new ToString(this.res).visit(object.getDomain());
    _builder.append(_visit);
    _builder.append("> ");
    sb.append(_builder);
    return sb.toString();
  }
  
  public String visit(final PowersetDomain object) {
    StringBuffer sb = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<");
    String _visit = new ToString(this.res).visit(object.getBaseDomain());
    _builder.append(_visit);
    _builder.append("> ");
    sb.append(_builder);
    return sb.toString();
  }
  
  public String visit(final BagDomain object) {
    StringBuffer sb = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<");
    String _visit = new ToString(this.res).visit(object.getDomain());
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
    String _visit = new ToString(this.res).visit(object.getSourceDomain());
    _builder_1.append(_visit);
    _builder_1.append(",");
    sb.append(_builder_1);
    StringConcatenation _builder_2 = new StringConcatenation();
    String _visit_1 = new ToString(this.res).visit(object.getTargetDomain());
    _builder_2.append(_visit_1);
    sb.append(_builder_2);
    return sb.toString().substring(0, sb.length()).concat(">");
  }
  
  public String visit(final DomainDefinition object) {
    return new TermToJava(this.res).visit(object.getBody());
  }
  
  public String visit(final DomainInitialization object) {
    return new TermToJava(this.res).visit(object.getBody());
  }
  
  public String visit(final RuleDomain object) {
    StringBuffer sb = new StringBuffer();
    sb.append("Caso relativo ai RuleDomain");
    return sb.toString();
  }
}
