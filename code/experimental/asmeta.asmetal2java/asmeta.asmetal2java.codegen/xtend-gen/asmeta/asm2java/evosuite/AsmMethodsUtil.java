package asmeta.asm2java.evosuite;

import asmeta.asm2java.translator.DomainToJavaString;
import asmeta.definitions.Function;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumTd;
import asmeta.structure.Asm;
import java.util.Arrays;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class AsmMethodsUtil {
  public static final String BOOLEAN = "Boolean";

  public static final String INTEGER = "Integer";

  public static final String REAL = "Real";

  public static final String DOUBLE = "Double";

  public static final String STRING = "String";

  public static final String CHAR = "Char";

  public static final String CHARACTER = "Character";

  public static final List<String> basicTdList = Arrays.<String>asList(AsmMethodsUtil.BOOLEAN, AsmMethodsUtil.INTEGER, AsmMethodsUtil.REAL, AsmMethodsUtil.STRING, AsmMethodsUtil.CHAR, AsmMethodsUtil.DOUBLE, AsmMethodsUtil.CHARACTER);

  /**
   * Get the specific domain type under consideration.
   */
  protected static String getConcreteDomainType(final Asm asm, final Function fd, final String concreteDomName) {
    EList<Domain> _domain = asm.getHeaderSection().getSignature().getDomain();
    for (final Domain to : _domain) {
      if ((to instanceof ConcreteDomain)) {
        boolean _equals = ((ConcreteDomain)to).getName().equals(concreteDomName);
        if (_equals) {
          String type = new DomainToJavaString(asm).visit(((ConcreteDomain)to).getTypeDomain());
          boolean _contains = AsmMethodsUtil.basicTdList.contains(type);
          boolean _not = (!_contains);
          if (_not) {
            type = asm.getName().concat(".").concat(type);
          }
          return type;
        }
      }
    }
    return null;
  }

  /**
   * Get the specific basic domain type under consideration.
   */
  protected static String getBasicTdType(final String domainType) {
    String type = domainType;
    if (type != null) {
      switch (type) {
        case AsmMethodsUtil.BOOLEAN:
          type = "boolean";
          break;
        case AsmMethodsUtil.INTEGER:
          type = "int";
          break;
        case AsmMethodsUtil.REAL:
          type = "double";
          break;
        case AsmMethodsUtil.CHAR:
          type = "char";
          break;
        case AsmMethodsUtil.STRING:
          type = "String";
          break;
      }
    }
    return type;
  }

  /**
   * Get the specific wrapper object of the basic domain type under consideration.
   */
  protected static String getWrapperBasicTdType(final String domainType) {
    String type = domainType;
    if (type != null) {
      switch (type) {
        case AsmMethodsUtil.BOOLEAN:
          type = "Boolean";
          break;
        case AsmMethodsUtil.INTEGER:
          type = "Integer";
          break;
        case AsmMethodsUtil.REAL:
          type = "Double";
          break;
        case AsmMethodsUtil.CHAR:
          type = "Character";
          break;
        case AsmMethodsUtil.STRING:
          type = "String";
          break;
        case AsmMethodsUtil.DOUBLE:
          type = "Double";
          break;
        case AsmMethodsUtil.CHARACTER:
          type = "Character";
          break;
      }
    }
    return type;
  }

  /**
   * Get the specific parsing method for the argument type.
   */
  protected static String getParsingMethod(final String domainType) {
    String type = domainType;
    if (type != null) {
      switch (type) {
        case AsmMethodsUtil.BOOLEAN:
          type = "Boolean::parseBoolean";
          break;
        case AsmMethodsUtil.INTEGER:
          type = "Integer::parseInt";
          break;
        case AsmMethodsUtil.REAL:
          type = "Double::parseDouble";
          break;
        case AsmMethodsUtil.DOUBLE:
          type = "Double::parseDouble";
          break;
        case AsmMethodsUtil.CHAR:
          type = "e -> e.charAt(0)";
          break;
        case AsmMethodsUtil.CHARACTER:
          type = "e -> e.charAt(0)";
          break;
        case AsmMethodsUtil.STRING:
          type = "e -> e";
          break;
      }
    }
    return type;
  }

  /**
   * Generate and return the method signature for getter functions
   */
  protected static String getMethodSignature(final String asmName, final String methodGetterSignature, final String codomain) {
    String _xblockexpression = null;
    {
      String methodDeclaration = "\t";
      String _xifexpression = null;
      boolean _equals = codomain.equals(AsmMethodsUtil.INTEGER);
      if (_equals) {
        String _methodDeclaration = methodDeclaration;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("public Integer ");
        _builder.append(methodGetterSignature);
        _builder.append("(){");
        _xifexpression = methodDeclaration = (_methodDeclaration + _builder);
      } else {
        String _xifexpression_1 = null;
        boolean _equals_1 = codomain.equals(AsmMethodsUtil.REAL);
        if (_equals_1) {
          String _methodDeclaration_1 = methodDeclaration;
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append("public Double ");
          _builder_1.append(methodGetterSignature);
          _builder_1.append("(){");
          _xifexpression_1 = methodDeclaration = (_methodDeclaration_1 + _builder_1);
        } else {
          String _xifexpression_2 = null;
          boolean _equals_2 = codomain.equals(AsmMethodsUtil.BOOLEAN);
          if (_equals_2) {
            String _methodDeclaration_2 = methodDeclaration;
            StringConcatenation _builder_2 = new StringConcatenation();
            _builder_2.append("public Boolean ");
            _builder_2.append(methodGetterSignature);
            _builder_2.append("(){");
            _xifexpression_2 = methodDeclaration = (_methodDeclaration_2 + _builder_2);
          } else {
            String _xifexpression_3 = null;
            boolean _equals_3 = codomain.equals(AsmMethodsUtil.STRING);
            if (_equals_3) {
              String _methodDeclaration_3 = methodDeclaration;
              StringConcatenation _builder_3 = new StringConcatenation();
              _builder_3.append("public String ");
              _builder_3.append(methodGetterSignature);
              _builder_3.append("(){");
              _xifexpression_3 = methodDeclaration = (_methodDeclaration_3 + _builder_3);
            } else {
              String _xifexpression_4 = null;
              boolean _equals_4 = codomain.equals(AsmMethodsUtil.CHAR);
              if (_equals_4) {
                String _methodDeclaration_4 = methodDeclaration;
                StringConcatenation _builder_4 = new StringConcatenation();
                _builder_4.append("public Character ");
                _builder_4.append(methodGetterSignature);
                _builder_4.append("(){");
                _xifexpression_4 = methodDeclaration = (_methodDeclaration_4 + _builder_4);
              } else {
                String _methodDeclaration_5 = methodDeclaration;
                StringConcatenation _builder_5 = new StringConcatenation();
                _builder_5.append("public ");
                _builder_5.append(asmName);
                _builder_5.append(".");
                _builder_5.append(codomain);
                _builder_5.append(" ");
                _builder_5.append(methodGetterSignature);
                _builder_5.append("(){");
                _xifexpression_4 = methodDeclaration = (_methodDeclaration_5 + _builder_5);
              }
              _xifexpression_3 = _xifexpression_4;
            }
            _xifexpression_2 = _xifexpression_3;
          }
          _xifexpression_1 = _xifexpression_2;
        }
        _xifexpression = _xifexpression_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }

  /**
   * Generates and returns the getter for a function with sequence codomain.
   */
  protected static String genSequenceGetter(final String functionName, final String type, final String toStringDef) {
    StringBuffer sb = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("public String get_");
    _builder.append(functionName);
    _builder.append("(){");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("java.util.List<");
    _builder.append(type, "\t");
    _builder.append("> list = this.execution.");
    _builder.append(functionName, "\t");
    _builder.append(".get();");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("if(list == null || list.isEmpty()){");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return \"[]\";");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("return \"[\" + ");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("list.stream().");
    _builder.newLine();
    _builder.append("\t    ");
    _builder.append("map(");
    _builder.append(toStringDef, "\t    ");
    _builder.append(").");
    _builder.newLineIfNotEmpty();
    _builder.append("\t    ");
    _builder.append("collect(java.util.stream.Collectors.joining(\", \")) ");
    _builder.newLine();
    _builder.append("\t    ");
    _builder.append("+ \"]\";");
    _builder.newLine();
    _builder.append("\t    ");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    sb.append(_builder);
    return sb.toString();
  }

  /**
   * Generates and returns the setter for a function with sequence codomain.
   */
  protected static String genSequenceSetter(final String functionName, final String type, final String parsingMethod) {
    StringBuffer sb = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("public void set_sequence_");
    _builder.append(functionName);
    _builder.append("(String ");
    _builder.append(functionName);
    _builder.append(") {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("java.util.List<");
    _builder.append(type, "\t");
    _builder.append("> list = ");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("java.util.Arrays.stream(");
    _builder.append(functionName, "\t\t");
    _builder.append(".replaceAll(\"[\\\\[\\\\]]\", \"\").split(\",\"))");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t");
    _builder.append(".map(");
    _builder.append(parsingMethod, "\t\t\t");
    _builder.append(")");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t");
    _builder.append(".collect(java.util.stream.Collectors.toList());");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("this.execution.");
    _builder.append(functionName, "\t");
    _builder.append(".set(list);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("System.out.println(\"Set ");
    _builder.append(functionName, "\t");
    _builder.append(" = \" + ");
    _builder.append(functionName, "\t");
    _builder.append(");");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    sb.append(_builder);
    return sb.toString();
  }

  /**
   * Generates the private method that covers the outputs
   */
  protected static String genCoverOutputMethod(final Function fd, final String enumState, final Asm asm) {
    StringBuffer sb = new StringBuffer();
    sb.append(System.lineSeparator());
    StringBuffer _append = sb.append("\t");
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("private void cover_");
    String _name = fd.getName();
    _builder.append(_name);
    _builder.append("_fromDomain_");
    _builder.append(enumState);
    _builder.append("(){");
    _append.append(_builder);
    sb.append(System.lineSeparator());
    StringBuffer _append_1 = sb.append("\t\t");
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("if(this.get_");
    String _name_1 = fd.getName();
    _builder_1.append(_name_1);
    _builder_1.append("_fromDomain_");
    _builder_1.append(enumState);
    _builder_1.append("() == null){");
    _append_1.append(_builder_1);
    sb.append(System.lineSeparator());
    StringBuffer _append_2 = sb.append("\t\t\t");
    StringConcatenation _builder_2 = new StringConcatenation();
    _builder_2.append("return;");
    _append_2.append(_builder_2);
    sb.append(System.lineSeparator());
    StringBuffer _append_3 = sb.append("\t\t");
    StringConcatenation _builder_3 = new StringConcatenation();
    _builder_3.append("}");
    _append_3.append(_builder_3);
    sb.append(System.lineSeparator());
    StringBuffer _append_4 = sb.append("\t\t");
    StringConcatenation _builder_4 = new StringConcatenation();
    _builder_4.append("switch(this.get_");
    String _name_2 = fd.getName();
    _builder_4.append(_name_2);
    _builder_4.append("_fromDomain_");
    _builder_4.append(enumState);
    _builder_4.append("()){");
    _append_4.append(_builder_4);
    EList<Domain> _domain = asm.getHeaderSection().getSignature().getDomain();
    for (final Domain ddd : _domain) {
      boolean _equals = ddd.equals(fd.getCodomain());
      if (_equals) {
        if ((ddd instanceof EnumTd)) {
          for (int j = 0; (j < ((EnumTd)ddd).getElement().size()); j++) {
            {
              String output = new DomainToJavaStringEvosuite(asm).visit(((EnumTd)ddd).getElement().get(j));
              sb.append(System.lineSeparator());
              StringBuffer _append_5 = sb.append("\t\t\t");
              StringConcatenation _builder_5 = new StringConcatenation();
              _builder_5.append("case ");
              _builder_5.append(output);
              _builder_5.append(" :");
              _builder_5.newLineIfNotEmpty();
              _builder_5.append("\t\t\t\t\t");
              _builder_5.append("System.out.println(\"Branch ");
              String _name_3 = fd.getDomain().getName();
              _builder_5.append(_name_3, "\t\t\t\t\t");
              _builder_5.append(" -> ");
              String _name_4 = fd.getCodomain().getName();
              _builder_5.append(_name_4, "\t\t\t\t\t");
              _builder_5.append(" ");
              _builder_5.append(output, "\t\t\t\t\t");
              _builder_5.append(" covered\");");
              _builder_5.newLineIfNotEmpty();
              _builder_5.append("\t\t\t\t\t");
              _builder_5.append("break;");
              _append_5.append(_builder_5);
              sb.append(System.lineSeparator());
            }
          }
        }
      }
    }
    sb.append("\t\t\t");
    StringConcatenation _builder_5 = new StringConcatenation();
    _builder_5.append("}");
    sb.append(_builder_5);
    sb.append(System.lineSeparator());
    sb.append("\t\t");
    StringConcatenation _builder_6 = new StringConcatenation();
    _builder_6.append("}");
    sb.append(_builder_6);
    sb.append(System.lineSeparator());
    return sb.toString();
  }
}
