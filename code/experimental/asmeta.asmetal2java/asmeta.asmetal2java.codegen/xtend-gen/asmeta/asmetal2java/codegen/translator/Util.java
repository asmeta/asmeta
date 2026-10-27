package asmeta.asmetal2java.codegen.translator;

import asmeta.definitions.ControlledFunction;
import asmeta.definitions.Function;
import asmeta.definitions.OutFunction;
import asmeta.definitions.domains.BagDomain;
import asmeta.definitions.domains.BooleanDomain;
import asmeta.definitions.domains.CharDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.IntegerDomain;
import asmeta.definitions.domains.MapDomain;
import asmeta.definitions.domains.NaturalDomain;
import asmeta.definitions.domains.PowersetDomain;
import asmeta.definitions.domains.RealDomain;
import asmeta.definitions.domains.SequenceDomain;
import asmeta.definitions.domains.StringDomain;
import asmeta.structure.Asm;
import asmeta.terms.basicterms.VariableTerm;
import java.util.Objects;
import javax.lang.model.SourceVersion;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class Util {
  private int counter;

  private Object c;

  private int i;

  public Util() {
  }

  public static String getElemsSetName(final String domainName) {
    return (domainName + "_elems");
  }

  public static String getExtendSetName(final String domainName) {
    return (domainName + "_extend");
  }

  /**
   * Returns a legal Java identifier while preserving ordinary ASM names.
   */
  public static String javaIdentifier(final String name) {
    String identifier = name.replaceAll("[^A-Za-z0-9_$]", "_");
    if ((identifier.isEmpty() || (!Character.isJavaIdentifierStart(identifier.charAt(0))))) {
      identifier = ("_" + identifier);
    }
    boolean _isKeyword = SourceVersion.isKeyword(identifier);
    if (_isKeyword) {
      identifier = ("_" + identifier);
    }
    return identifier;
  }

  public String adaptRuleParam(final EList<VariableTerm> variables, final Asm res) {
    StringBuffer paramDef = new StringBuffer();
    paramDef.append("");
    for (int i = 0; (i < variables.size()); i++) {
      StringConcatenation _builder = new StringConcatenation();
      String _javaType = Util.javaType(variables.get(i).getDomain(), res);
      _builder.append(_javaType);
      _builder.append(" ");
      String _name = variables.get(i).getName();
      _builder.append(_name);
      _builder.append(", ");
      paramDef.append(_builder);
    }
    int _length = paramDef.length();
    int _minus = (_length - 2);
    return paramDef.substring(0, _minus);
  }

  /**
   * Returns the Java declaration type for a Domain.
   */
  public static String javaType(final Domain domain, final Asm res) {
    if ((domain instanceof SequenceDomain)) {
      String _javaType = Util.javaType(((SequenceDomain)domain).getDomain(), res);
      String _plus = ("List<" + _javaType);
      return (_plus + ">");
    }
    if ((domain instanceof PowersetDomain)) {
      String _javaType_1 = Util.javaType(((PowersetDomain)domain).getBaseDomain(), res);
      String _plus_1 = ("Set<" + _javaType_1);
      return (_plus_1 + ">");
    }
    if ((domain instanceof BagDomain)) {
      String _javaType_2 = Util.javaType(((BagDomain)domain).getDomain(), res);
      String _plus_2 = ("Bag<" + _javaType_2);
      return (_plus_2 + ">");
    }
    if ((domain instanceof MapDomain)) {
      String _javaType_3 = Util.javaType(((MapDomain)domain).getSourceDomain(), res);
      String _plus_3 = ("Map<" + _javaType_3);
      String _plus_4 = (_plus_3 + ", ");
      String _javaType_4 = Util.javaType(((MapDomain)domain).getTargetDomain(), res);
      String _plus_5 = (_plus_4 + _javaType_4);
      return (_plus_5 + ">");
    }
    return new DomainToJavaString(res).visit(domain).trim();
  }

  public String parseFunction(final String s) {
    boolean _equals = s.equals("and");
    if (_equals) {
      return "&";
    } else {
      boolean _equals_1 = s.equals("or");
      if (_equals_1) {
        return "|";
      } else {
        boolean _equals_2 = s.equals("not");
        if (_equals_2) {
          return "!";
        } else {
          boolean _equals_3 = s.equals("plus");
          if (_equals_3) {
            return "+";
          } else {
            boolean _equals_4 = s.equals("minus");
            if (_equals_4) {
              return "-";
            } else {
              boolean _equals_5 = s.equals("mult");
              if (_equals_5) {
                return "*";
              } else {
                boolean _equals_6 = s.equals("div");
                if (_equals_6) {
                  return "/";
                } else {
                  boolean _equals_7 = s.equals("gt");
                  if (_equals_7) {
                    return ">";
                  } else {
                    boolean _equals_8 = s.equals("ge");
                    if (_equals_8) {
                      return ">=";
                    } else {
                      boolean _equals_9 = s.equals("lt");
                      if (_equals_9) {
                        return "<";
                      } else {
                        boolean _equals_10 = s.equals("le");
                        if (_equals_10) {
                          return "<=";
                        } else {
                          boolean _equals_11 = s.equals("eq");
                          if (_equals_11) {
                            return "=";
                          } else {
                            boolean _equals_12 = s.equals("neq");
                            if (_equals_12) {
                              return "!=";
                            } else {
                              boolean _equals_13 = s.equals("pwr");
                              if (_equals_13) {
                                return "^";
                              } else {
                                return s;
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  public String equals(final String left, final String right) {
    return this.setPars(((left + " == ") + right));
  }

  public Boolean isNumber(final String str) {
    try {
      Integer.parseInt(str);
      return Boolean.valueOf(true);
    } catch (final Throwable _t) {
      if (_t instanceof NumberFormatException) {
        return Boolean.valueOf(false);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }

  public String notEquals(final String left, final String right) {
    return this.setPars(((left + " != ") + right));
  }

  public String setPars(final String expr) {
    boolean _hasFirstLastPars = this.hasFirstLastPars(expr);
    if (_hasFirstLastPars) {
      return expr;
    } else {
      return (("(" + expr) + ")");
    }
  }

  public boolean hasFirstLastPars(final String str) {
    if ((str.startsWith("(") && str.endsWith(")"))) {
      this.counter = 1;
      for (this.i = 1; (this.i < (str.length() - 1)); this.i++) {
        {
          this.c = Character.valueOf(str.charAt(this.i));
          boolean _equals = Objects.equals(this.c, "(");
          if (_equals) {
            this.counter++;
          } else {
            boolean _equals_1 = Objects.equals(this.c, ")");
            if (_equals_1) {
              this.counter--;
            }
          }
          if ((this.counter == 0)) {
            return false;
          }
        }
      }
      this.counter--;
      return (this.counter == 0);
    } else {
      return false;
    }
  }

  public static boolean isNotNumerable(final Domain domain) {
    if (((((((domain instanceof StringDomain) || (domain instanceof CharDomain)) || (domain instanceof IntegerDomain)) || 
      (domain instanceof RealDomain)) || (domain instanceof NaturalDomain)) || (domain instanceof BooleanDomain))) {
      return true;
    } else {
      return false;
    }
  }

  public static boolean isControlledOrOut(final Function function) {
    return ((function instanceof ControlledFunction) || (function instanceof OutFunction));
  }
}
