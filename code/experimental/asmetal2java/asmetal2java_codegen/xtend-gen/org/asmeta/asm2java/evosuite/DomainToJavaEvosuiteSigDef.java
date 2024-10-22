package org.asmeta.asm2java.evosuite;

import asmeta.definitions.domains.AbstractTd;
import asmeta.structure.Asm;
import org.asmeta.asm2java.DomainToJavaSigDef;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class DomainToJavaEvosuiteSigDef extends DomainToJavaSigDef {
  public DomainToJavaEvosuiteSigDef(final Asm resource) {
    super(resource);
  }

  @Override
  public String visit(final AbstractTd object) {
    StringBuffer sb = new StringBuffer();
    String isStatic = "";
    Boolean _isDynamic = object.getIsDynamic();
    boolean _not = (!(_isDynamic).booleanValue());
    if (_not) {
      isStatic = "static";
    }
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("static class ");
    String _name = object.getName();
    _builder.append(_name);
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("static List<");
    String _name_1 = object.getName();
    _builder.append(_name_1, "\t");
    _builder.append("> elems = new ArrayList<>();");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append((isStatic + " "), "\t");
    _builder.append("List<String> val = new ArrayList<>();");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    String _name_2 = object.getName();
    _builder.append(_name_2);
    _builder.append(" (String a) {");
    _builder.newLineIfNotEmpty();
    _builder.append("      ");
    _builder.append("elems.add(this);");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("val.add(a);");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("      ");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("static String toString(");
    String _name_3 = object.getName();
    _builder.append(_name_3, "      ");
    _builder.append(" a) {");
    _builder.newLineIfNotEmpty();
    _builder.append("      ");
    _builder.append("if(elems.contains(a)) {");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("return val.get(elems.lastIndexOf(a));");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("else return null;");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("      ");
    _builder.newLine();
    _builder.append("\t  ");
    String _name_4 = object.getName();
    _builder.append(_name_4, "\t  ");
    _builder.append(" get(String a) {");
    _builder.newLineIfNotEmpty();
    _builder.append("      ");
    _builder.append("if(val.contains(a)) {");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("return elems.get(val.lastIndexOf(a));");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("else return null;");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("      ");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("List<String> ");
    String _name_5 = object.getName();
    _builder.append(_name_5, "      ");
    _builder.append("_elemsList = new ArrayList<>();");
    _builder.newLineIfNotEmpty();
    _builder.append("      ");
    _builder.append("List<");
    String _name_6 = object.getName();
    _builder.append(_name_6, "      ");
    _builder.append("> ");
    String _name_7 = object.getName();
    _builder.append(_name_7, "      ");
    _builder.append("_Class = new ArrayList<>();");
    _builder.newLineIfNotEmpty();
    sb.append(_builder);
    return sb.toString();
  }
}
