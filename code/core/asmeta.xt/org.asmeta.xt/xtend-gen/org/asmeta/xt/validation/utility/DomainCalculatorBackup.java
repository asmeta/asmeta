package org.asmeta.xt.validation.utility;

import java.util.Collections;
import java.util.List;
import org.asmeta.xt.asmetal.AnyDomain;
import org.asmeta.xt.asmetal.BagCT;
import org.asmeta.xt.asmetal.BagTerm;
import org.asmeta.xt.asmetal.BasicTerm;
import org.asmeta.xt.asmetal.BinaryOperation;
import org.asmeta.xt.asmetal.BooleanTerm;
import org.asmeta.xt.asmetal.CaseTerm;
import org.asmeta.xt.asmetal.CharTerm;
import org.asmeta.xt.asmetal.CollectionTerm;
import org.asmeta.xt.asmetal.ComplexTerm;
import org.asmeta.xt.asmetal.ComprehensionTerm;
import org.asmeta.xt.asmetal.ConditionalTerm;
import org.asmeta.xt.asmetal.ConstantTerm;
import org.asmeta.xt.asmetal.Domain;
import org.asmeta.xt.asmetal.EnumTerm;
import org.asmeta.xt.asmetal.Expression;
import org.asmeta.xt.asmetal.ExtendedTerm;
import org.asmeta.xt.asmetal.FiniteQuantificationTerm;
import org.asmeta.xt.asmetal.Function;
import org.asmeta.xt.asmetal.FunctionTerm;
import org.asmeta.xt.asmetal.IntegerTerm;
import org.asmeta.xt.asmetal.LetTerm;
import org.asmeta.xt.asmetal.MapCT;
import org.asmeta.xt.asmetal.NaturalTerm;
import org.asmeta.xt.asmetal.RealTerm;
import org.asmeta.xt.asmetal.RuleAsTerm;
import org.asmeta.xt.asmetal.SequenceCT;
import org.asmeta.xt.asmetal.SequenceTerm;
import org.asmeta.xt.asmetal.SetCT;
import org.asmeta.xt.asmetal.SetTerm;
import org.asmeta.xt.asmetal.StringTerm;
import org.asmeta.xt.asmetal.Term;
import org.asmeta.xt.asmetal.TupleTerm;
import org.asmeta.xt.asmetal.UndefTerm;
import org.asmeta.xt.asmetal.VariableBindingTerm;
import org.asmeta.xt.asmetal.VariableTerm;
import org.asmeta.xt.asmetal.basicExpr;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;

@SuppressWarnings("all")
public class DomainCalculatorBackup {
  public static Domain getDomainTerm(final Term term) {
    Domain res = null;
    if ((term instanceof Expression)) {
      res = DomainCalculatorBackup.getDomainExpression(((Expression)term));
    } else {
      if ((term instanceof ExtendedTerm)) {
        res = DomainCalculatorBackup.getDomainExtendedTerm(((ExtendedTerm)term));
      } else {
        res = null;
      }
    }
    return res;
  }

  public static Domain getDomainExpression(final Expression term) {
    Domain res = null;
    if ((term instanceof BinaryOperation)) {
      res = DomainCalculatorBackup.getDomainBinaryOperation(((BinaryOperation)term));
    } else {
      if ((term instanceof basicExpr)) {
        res = DomainCalculatorBackup.getDomainbasicExpr(((basicExpr)term));
      } else {
        res = DomainCalculatorBackup.getDomainUnaryOperation(term);
      }
    }
    return res;
  }

  public static Domain getDomainBinaryOperation(final BinaryOperation term) {
    List<String> boolean_operators = Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("or", "xor", "iff", "implies", "and", "=", "!=", ">", ">=", "<", "<="));
    for (final String operator : boolean_operators) {
      boolean _equals = term.getOp().equals(operator);
      if (_equals) {
        return Utility.getDomain("Boolean");
      }
    }
    Domain dom_left = DomainCalculatorBackup.getDomainTerm(term.getLeft());
    Domain dom_right = DomainCalculatorBackup.getDomainTerm(term.getRight());
    return Utility.getCommonDomain(dom_left, dom_right);
  }

  public static Domain getDomainUnaryOperation(final Expression term) {
    boolean _equals = term.getOp().equals("not");
    if (_equals) {
      return Utility.getDomain("Boolean");
    }
    return DomainCalculatorBackup.getDomainTerm(term.getOperand());
  }

  public static Domain getDomainbasicExpr(final basicExpr term) {
    Domain res = null;
    if ((term instanceof BasicTerm)) {
      res = DomainCalculatorBackup.getDomainBasicTerm(((BasicTerm)term));
    } else {
      if ((term instanceof FiniteQuantificationTerm)) {
        res = DomainCalculatorBackup.getDomainFiniteQuantificationTerm(((FiniteQuantificationTerm)term));
      }
    }
    return res;
  }

  public static Domain getDomainBasicTerm(final BasicTerm term) {
    Domain res = null;
    if ((term instanceof FunctionTerm)) {
      res = DomainCalculatorBackup.getDomainFunctionTerm(((FunctionTerm)term));
    } else {
      if ((term instanceof ConstantTerm)) {
        res = DomainCalculatorBackup.getDomainConstantTerm(((ConstantTerm)term));
      } else {
        if ((term instanceof VariableTerm)) {
          res = DomainCalculatorBackup.getDomainVariableTerm(((VariableTerm)term));
        }
      }
    }
    return res;
  }

  public static Domain getDomainFunctionTerm(final FunctionTerm term) {
    Domain domain_term = Utility.getDomain(term.getFunctionName());
    if ((domain_term != null)) {
      return domain_term;
    }
    Function function = Utility.getFunctionByName(term.getFunctionName());
    if ((function == null)) {
      return null;
    }
    String domain_code = Utility.calculateDomainCode(function.getCodomain());
    Domain dom = Utility.getDomain(domain_code);
    return dom;
  }

  public static Domain getDomainConstantTerm(final ConstantTerm term) {
    if ((term instanceof EnumTerm)) {
      return Utility.getEnumParent(((EnumTerm)term).getSymbol());
    }
    String domain_name = "";
    if ((term instanceof BooleanTerm)) {
      domain_name = "Boolean";
    } else {
      if ((term instanceof UndefTerm)) {
        domain_name = "Undef";
      } else {
        if ((term instanceof ComplexTerm)) {
          boolean _contains = ((ComplexTerm)term).getSymbol().contains("i");
          if (_contains) {
            domain_name = "Complex";
          } else {
            domain_name = "Integer";
          }
        } else {
          if ((term instanceof RealTerm)) {
            domain_name = "Real";
          } else {
            if ((term instanceof NaturalTerm)) {
              domain_name = "Natural";
            } else {
              if ((term instanceof IntegerTerm)) {
                domain_name = "Integer";
              } else {
                if ((term instanceof CharTerm)) {
                  domain_name = "Char";
                } else {
                  if ((term instanceof StringTerm)) {
                    domain_name = "String";
                  }
                }
              }
            }
          }
        }
      }
    }
    return Utility.getDomain(domain_name);
  }

  public static Domain getDomainVariableTerm(final VariableTerm term) {
    return Utility.getDomainFromVariable(term.getName());
  }

  public static Domain getDomainFiniteQuantificationTerm(final FiniteQuantificationTerm term) {
    return Utility.getDomain("Boolean");
  }

  public static Domain getDomainExtendedTerm(final ExtendedTerm term) {
    Domain res = null;
    if ((term instanceof ConditionalTerm)) {
      res = DomainCalculatorBackup.getDomainConditionalTerm(((ConditionalTerm)term));
    } else {
      if ((term instanceof CaseTerm)) {
        res = DomainCalculatorBackup.getDomainCaseTerm(((CaseTerm)term));
      } else {
        if ((term instanceof TupleTerm)) {
          res = DomainCalculatorBackup.getDomainTupleTerm(((TupleTerm)term));
        } else {
          if ((term instanceof VariableBindingTerm)) {
            res = DomainCalculatorBackup.getDomainVariableBindingTerm(((VariableBindingTerm)term));
          } else {
            if ((term instanceof CollectionTerm)) {
              res = DomainCalculatorBackup.getDomainCollectionTerm(((CollectionTerm)term));
            } else {
              if ((term instanceof RuleAsTerm)) {
                res = DomainCalculatorBackup.getDomainRuleAsTerm(((RuleAsTerm)term));
              }
            }
          }
        }
      }
    }
    return res;
  }

  public static Domain getDomainConditionalTerm(final ConditionalTerm term) {
    Term _elseTerm = term.getElseTerm();
    boolean _tripleEquals = (_elseTerm == null);
    if (_tripleEquals) {
      return DomainCalculatorBackup.getDomainTerm(term.getThenTerm());
    }
    Domain dom_then = DomainCalculatorBackup.getDomainTerm(term.getThenTerm());
    Domain dom_else = DomainCalculatorBackup.getDomainTerm(term.getElseTerm());
    return Utility.getCommonDomain(dom_then, dom_else);
  }

  public static Domain getDomainCaseTerm(final CaseTerm term) {
    return Utility.getCommonDomainFromList(term.getResultTerms());
  }

  public static Domain getDomainTupleTerm(final TupleTerm term) {
    int _size = term.getTerms().size();
    boolean _tripleEquals = (_size == 0);
    if (_tripleEquals) {
      return null;
    }
    int _size_1 = term.getTerms().size();
    boolean _tripleEquals_1 = (_size_1 == 1);
    if (_tripleEquals_1) {
      Domain dom = DomainCalculatorBackup.getDomainTerm(term.getTerms().get(0));
      return Utility.getDomain(Utility.calculateDomainCode(dom));
    }
    String res = "Prod(";
    Domain dom_1 = null;
    EList<Term> _terms = term.getTerms();
    for (final Term t : _terms) {
      {
        dom_1 = DomainCalculatorBackup.getDomainTerm(t);
        if ((dom_1 == null)) {
          return null;
        }
        if ((dom_1 instanceof AnyDomain)) {
          return Utility.getDomain("Any");
        }
        String _res = res;
        String _name = dom_1.getName();
        String _plus = (_name + ",");
        res = (_res + _plus);
      }
    }
    int _length = res.length();
    int _minus = (_length - 1);
    res = res.substring(0, _minus);
    String _res = res;
    res = (_res + ")");
    return Utility.getDomain(res);
  }

  public static Domain getDomainVariableBindingTerm(final VariableBindingTerm term) {
    Domain res = null;
    if ((term instanceof LetTerm)) {
      res = DomainCalculatorBackup.getDomainLetTerm(((LetTerm)term));
    } else {
      if ((term instanceof FiniteQuantificationTerm)) {
        res = DomainCalculatorBackup.getDomainFiniteQuantificationTerm(((FiniteQuantificationTerm)term));
      } else {
        if ((term instanceof ComprehensionTerm)) {
          res = DomainCalculatorBackup.getDomainComprehensionTerm(((ComprehensionTerm)term));
        }
      }
    }
    return res;
  }

  public static Domain getDomainLetTerm(final LetTerm term) {
    return DomainCalculatorBackup.getDomainTerm(term.getBody());
  }

  public static Domain getDomainComprehensionTerm(final ComprehensionTerm term) {
    Domain res = null;
    if ((term instanceof SetCT)) {
      res = DomainCalculatorBackup.getDomainSetCT(((SetCT)term));
    } else {
      if ((term instanceof MapCT)) {
        res = DomainCalculatorBackup.getDomainMapCT(((MapCT)term));
      } else {
        if ((term instanceof SequenceCT)) {
          res = DomainCalculatorBackup.getDomainSequenceCT(((SequenceCT)term));
        } else {
          if ((term instanceof BagCT)) {
            res = DomainCalculatorBackup.getDomainBagCT(((BagCT)term));
          }
        }
      }
    }
    return res;
  }

  public static Domain getDomainSetCT(final SetCT term) {
    Domain dom = Utility.getCommonDomainFromList(term.getRanges());
    String _name = dom.getName();
    String _plus = ("Powerset(" + _name);
    String res = (_plus + ")");
    Domain r = Utility.getDomain(res);
    return r;
  }

  public static Domain getDomainMapCT(final MapCT term) {
    return null;
  }

  public static Domain getDomainSequenceCT(final SequenceCT term) {
    Domain dom = Utility.getCommonDomainFromList(term.getRanges());
    String _name = dom.getName();
    String _plus = ("Seq(" + _name);
    String res = (_plus + ")");
    return Utility.getDomain(res);
  }

  public static Domain getDomainBagCT(final BagCT term) {
    Domain dom = Utility.getCommonDomainFromList(term.getRanges());
    String _name = dom.getName();
    String _plus = ("Bag(" + _name);
    String res = (_plus + ")");
    return Utility.getDomain(res);
  }

  public static Domain getDomainCollectionTerm(final CollectionTerm term) {
    Domain res = null;
    if ((term instanceof SetTerm)) {
      res = DomainCalculatorBackup.getDomainSetTerm(((SetTerm)term));
    } else {
      if ((term instanceof SequenceTerm)) {
        res = DomainCalculatorBackup.getDomainSequenceTerm(((SequenceTerm)term));
      } else {
        if ((term instanceof BagTerm)) {
          res = DomainCalculatorBackup.getDomainBagTerm(((BagTerm)term));
        }
      }
    }
    return res;
  }

  public static Domain getDomainSetTerm(final SetTerm term) {
    Term _start_term = term.getStart_term();
    boolean _tripleEquals = (_start_term == null);
    if (_tripleEquals) {
      return Utility.getDomain("Any");
    }
    return DomainCalculatorBackup.getDomainTerm(term.getStart_term());
  }

  public static Domain getDomainSequenceTerm(final SequenceTerm term) {
    Term _start_term = term.getStart_term();
    boolean _tripleEquals = (_start_term == null);
    if (_tripleEquals) {
      return Utility.getDomain("Any");
    }
    Domain dom = DomainCalculatorBackup.getDomainTerm(term.getStart_term());
    String _name = dom.getName();
    String _plus = ("Seq(" + _name);
    String res = (_plus + ")");
    return Utility.getDomain(res);
  }

  public static Domain getDomainBagTerm(final BagTerm term) {
    BasicTerm _start_term = term.getStart_term();
    boolean _tripleEquals = (_start_term == null);
    if (_tripleEquals) {
      return Utility.getDomain("Any");
    }
    Domain dom = DomainCalculatorBackup.getDomainTerm(term.getStart_term());
    String _name = dom.getName();
    String _plus = ("Bag(" + _name);
    String res = (_plus + ")");
    return Utility.getDomain(res);
  }

  public static Domain getDomainRuleAsTerm(final RuleAsTerm term) {
    return Utility.getDomain("Rule");
  }
}
