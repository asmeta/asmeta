package asmeta.asm2java.evosuite;

import asmeta.asm2java.translator.DomainToJavaString;
import asmeta.definitions.Function;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
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

  public static final String STRING = "String";

  public static final String CHAR = "Char";

  public static final List<String> basicTdList = Arrays.<String>asList(AsmMethodsUtil.BOOLEAN, AsmMethodsUtil.INTEGER, AsmMethodsUtil.REAL, AsmMethodsUtil.STRING, AsmMethodsUtil.CHAR);

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
}
