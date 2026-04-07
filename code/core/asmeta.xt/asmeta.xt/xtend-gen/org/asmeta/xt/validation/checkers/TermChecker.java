package org.asmeta.xt.validation.checkers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import javax.swing.tree.DefaultMutableTreeNode;
import org.asmeta.xt.asmetal.AnyDomain;
import org.asmeta.xt.asmetal.AsmetalPackage;
import org.asmeta.xt.asmetal.BagCT;
import org.asmeta.xt.asmetal.BagTerm;
import org.asmeta.xt.asmetal.BasicTerm;
import org.asmeta.xt.asmetal.BinaryOperation;
import org.asmeta.xt.asmetal.CaseTerm;
import org.asmeta.xt.asmetal.ComprehensionTerm;
import org.asmeta.xt.asmetal.ConditionalTerm;
import org.asmeta.xt.asmetal.ConstantTerm;
import org.asmeta.xt.asmetal.EnumTerm;
import org.asmeta.xt.asmetal.ExistTerm;
import org.asmeta.xt.asmetal.ExistUniqueTerm;
import org.asmeta.xt.asmetal.Expression;
import org.asmeta.xt.asmetal.FiniteQuantificationTerm;
import org.asmeta.xt.asmetal.ForallTerm;
import org.asmeta.xt.asmetal.Function;
import org.asmeta.xt.asmetal.FunctionTerm;
import org.asmeta.xt.asmetal.LetTerm;
import org.asmeta.xt.asmetal.MapCT;
import org.asmeta.xt.asmetal.MapTerm;
import org.asmeta.xt.asmetal.RuleAsTerm;
import org.asmeta.xt.asmetal.RuleDeclaration;
import org.asmeta.xt.asmetal.SequenceCT;
import org.asmeta.xt.asmetal.SequenceTerm;
import org.asmeta.xt.asmetal.SetCT;
import org.asmeta.xt.asmetal.SetTerm;
import org.asmeta.xt.asmetal.StructuredTD;
import org.asmeta.xt.asmetal.Term;
import org.asmeta.xt.asmetal.TupleTerm;
import org.asmeta.xt.validation.ErrorCode;
import org.asmeta.xt.validation.utility.DomainCalculator;
import org.asmeta.xt.validation.utility.DomainTree;
import org.asmeta.xt.validation.utility.ErrorType;
import org.asmeta.xt.validation.utility.Utility;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class TermChecker {
  public static ErrorType isAnyDomainOK(final SetTerm term) {
    Term _start_term = term.getStart_term();
    boolean _tripleEquals = (_start_term == null);
    if (_tripleEquals) {
      EStructuralFeature feature = AsmetalPackage.Literals.SET_TERM__START_TERM;
      String code = ErrorCode.SET_TERM__ANY_DOMAIN_NOT_DECLARED;
      return SharedCheckers.returnIsAnyDomainOK(feature, code);
    }
    return null;
  }

  public static ErrorType isAnyDomainOK(final BagTerm term) {
    BasicTerm _start_term = term.getStart_term();
    boolean _tripleEquals = (_start_term == null);
    if (_tripleEquals) {
      EStructuralFeature feature = AsmetalPackage.Literals.BAG_TERM__START_TERM;
      String code = ErrorCode.BAG_TERM__ANY_DOMAIN_NOT_DECLARED;
      return SharedCheckers.returnIsAnyDomainOK(feature, code);
    }
    return null;
  }

  public static ErrorType isAnyDomainOK(final SequenceTerm term) {
    Term _start_term = term.getStart_term();
    boolean _tripleEquals = (_start_term == null);
    if (_tripleEquals) {
      EStructuralFeature feature = AsmetalPackage.Literals.SEQUENCE_TERM__START_TERM;
      String code = ErrorCode.SEQUENCE_TERM__ANY_DOMAIN_NOT_DECLARED;
      return SharedCheckers.returnIsAnyDomainOK(feature, code);
    }
    return null;
  }

  public static ErrorType isAnyDomainOK(final MapTerm term) {
    if (((term.getTerm() == null) || (term.getTerm().size() == 0))) {
      EStructuralFeature feature = AsmetalPackage.Literals.MAP_TERM__TERM;
      String code = ErrorCode.MAP_TERM__ANY_DOMAIN_NOT_DECLARED;
      return SharedCheckers.returnIsAnyDomainOK(feature, code);
    }
    return null;
  }

  public static ErrorType isDeclaredOnlyOnce(final SetTerm term) {
    Term _start_term = term.getStart_term();
    boolean _tripleNotEquals = (_start_term != null);
    if (_tripleNotEquals) {
      Term _start_term_1 = term.getStart_term();
      if ((_start_term_1 instanceof BasicTerm)) {
        Term _start_term_2 = term.getStart_term();
        String code_start = Utility.getBasicTermCode(((BasicTerm) _start_term_2));
        boolean errorOK = false;
        Term _end_term = term.getEnd_term();
        boolean _tripleNotEquals_1 = (_end_term != null);
        if (_tripleNotEquals_1) {
          String code_end = null;
          Term _end_term_1 = term.getEnd_term();
          if ((_end_term_1 instanceof BasicTerm)) {
            Term _end_term_2 = term.getEnd_term();
            code_end = Utility.getBasicTermCode(((BasicTerm) _end_term_2));
            errorOK = code_end.equals(code_start);
          }
        } else {
          HashMap<String, String> names = new HashMap<String, String>();
          String code = "";
          names.put(code_start, code_start);
          EList<Term> _other_terms = term.getOther_terms();
          for (final Term t : _other_terms) {
            {
              code = Utility.getBasicTermCode(((BasicTerm) t));
              if (((!errorOK) && names.containsKey(code))) {
                errorOK = true;
              } else {
                names.put(code, code);
              }
            }
          }
        }
        if ((errorOK && (term.getOther_terms().size() > 0))) {
          EStructuralFeature feature = AsmetalPackage.Literals.SET_TERM__START_TERM;
          String message = "Duplicates are not allowed in a set";
          String code_1 = ErrorCode.SET_TERM__ADDED_TWICE;
          return new ErrorType(message, feature, code_1);
        }
      } else {
        Term _start_term_3 = term.getStart_term();
        if ((_start_term_3 instanceof StructuredTD)) {
          return null;
        }
      }
    }
    return null;
  }

  public static ErrorType isColletionTermSameDomain(final SequenceTerm term) {
    Term _start_term = term.getStart_term();
    boolean _tripleNotEquals = (_start_term != null);
    if (_tripleNotEquals) {
      DomainTree domain_start = DomainCalculator.getDomainTerm(term.getStart_term());
      boolean errorOK = false;
      Term _end_term = term.getEnd_term();
      boolean _tripleNotEquals_1 = (_end_term != null);
      if (_tripleNotEquals_1) {
        DomainTree domain_end = DomainCalculator.getDomainTerm(term.getEnd_term());
        boolean _isCompatible = Utility.isCompatible(domain_start, domain_end);
        boolean _not = (!_isCompatible);
        errorOK = _not;
      } else {
        DomainTree tree = null;
        EList<Term> _other_terms = term.getOther_terms();
        for (final Term t : _other_terms) {
          {
            tree = DomainCalculator.getDomainTerm(t);
            if (((!errorOK) && (!Utility.isCompatible(domain_start, tree)))) {
              errorOK = true;
            }
          }
        }
      }
      if (errorOK) {
        EStructuralFeature feature = AsmetalPackage.Literals.SEQUENCE_TERM__START_TERM;
        String message = "Every element of a sequence must have the same type-domain";
        String code = ErrorCode.SEQUENCE_TERM__DIFFERENT_DOMAINS;
        return new ErrorType(message, feature, code);
      }
    }
    return null;
  }

  public static ErrorType isColletionTermSameDomain(final SetTerm term) {
    Term _start_term = term.getStart_term();
    boolean _tripleNotEquals = (_start_term != null);
    if (_tripleNotEquals) {
      DomainTree domain_start = DomainCalculator.getDomainTerm(term.getStart_term());
      boolean errorOK = false;
      Term _end_term = term.getEnd_term();
      boolean _tripleNotEquals_1 = (_end_term != null);
      if (_tripleNotEquals_1) {
        DomainTree domain_end = DomainCalculator.getDomainTerm(term.getEnd_term());
        boolean _isCompatible = Utility.isCompatible(domain_start, domain_end);
        boolean _not = (!_isCompatible);
        errorOK = _not;
      } else {
        DomainTree tree = null;
        EList<Term> _other_terms = term.getOther_terms();
        for (final Term t : _other_terms) {
          {
            tree = DomainCalculator.getDomainTerm(t);
            if (((!errorOK) && (!Utility.isCompatible(domain_start, tree)))) {
              errorOK = true;
            }
          }
        }
      }
      if (errorOK) {
        EStructuralFeature feature = AsmetalPackage.Literals.SET_TERM__START_TERM;
        String message = "Every element of a set must have the same type-domain";
        String code = ErrorCode.SET_TERM__DIFFERENT_DOMAINS;
        return new ErrorType(message, feature, code);
      }
    }
    return null;
  }

  public static ErrorType isColletionTermSameDomain(final BagTerm term) {
    BasicTerm _start_term = term.getStart_term();
    boolean _tripleNotEquals = (_start_term != null);
    if (_tripleNotEquals) {
      DomainTree domain_start = DomainCalculator.getDomainTerm(term.getStart_term());
      boolean errorOK = false;
      ConstantTerm _end_term = term.getEnd_term();
      boolean _tripleNotEquals_1 = (_end_term != null);
      if (_tripleNotEquals_1) {
        DomainTree domain_end = DomainCalculator.getDomainTerm(term.getEnd_term());
        boolean _isCompatible = Utility.isCompatible(domain_start, domain_end);
        boolean _not = (!_isCompatible);
        errorOK = _not;
      } else {
        DomainTree tree = null;
        EList<BasicTerm> _other_terms = term.getOther_terms();
        for (final Term t : _other_terms) {
          {
            tree = DomainCalculator.getDomainTerm(t);
            if (((!errorOK) && (!Utility.isCompatible(domain_start, tree)))) {
              errorOK = true;
            }
          }
        }
      }
      if (errorOK) {
        EStructuralFeature feature = AsmetalPackage.Literals.BAG_TERM__START_TERM;
        String message = "Every element of a bag must have the same type-domain";
        String code = ErrorCode.BAG_TERM__DIFFERENT_DOMAINS;
        return new ErrorType(message, feature, code);
      }
    }
    return null;
  }

  public static ErrorType isColletionTermSameDomain(final MapTerm term) {
    boolean errorOK = false;
    if (((term.getTerm() != null) && (term.getTerm().size() > 1))) {
      DomainTree base_source = DomainCalculator.getDomainTerm(term.getTerm().get(0));
      DomainTree base_target = DomainCalculator.getDomainTerm(term.getTerm().get(1));
      DomainTree tree = null;
      for (int i = 2; (i < term.getTerm().size()); i++) {
        if ((!errorOK)) {
          tree = DomainCalculator.getDomainTerm(term.getTerm().get(i));
          boolean _isCompatible = Utility.isCompatible(base_source, tree);
          boolean _not = (!_isCompatible);
          if (_not) {
            errorOK = true;
          }
          i++;
          tree = DomainCalculator.getDomainTerm(term.getTerm().get(i));
          boolean _isCompatible_1 = Utility.isCompatible(base_target, tree);
          boolean _not_1 = (!_isCompatible_1);
          if (_not_1) {
            errorOK = true;
          }
        }
      }
      if (errorOK) {
        EStructuralFeature feature = AsmetalPackage.Literals.MAP_TERM__TERM;
        String message = "Every pair of a map must be compatible with the type-domain for the second pair element. ";
        String code = ErrorCode.MAP_TERM__DIFFERENT_DOMAINS;
        return new ErrorType(message, feature, code);
      }
    }
    return null;
  }

  public static ErrorType isStepOK(final SetTerm term) {
    ConstantTerm _step = term.getStep();
    boolean _tripleNotEquals = (_step != null);
    if (_tripleNotEquals) {
      EStructuralFeature feature = AsmetalPackage.Literals.SET_TERM__STEP;
      String code1 = ErrorCode.SET_TERM__STEP_NEGATIVE;
      String code2 = ErrorCode.SET_TERM__STEP_NAN;
      return TermChecker.returnStepError(term.getStep(), code1, code2, feature);
    }
    return null;
  }

  public static ErrorType isStepOK(final BagTerm term) {
    ConstantTerm _step = term.getStep();
    boolean _tripleNotEquals = (_step != null);
    if (_tripleNotEquals) {
      EStructuralFeature feature = AsmetalPackage.Literals.BAG_TERM__STEP;
      String code1 = ErrorCode.BAG_TERM__STEP_NEGATIVE;
      String code2 = ErrorCode.BAG_TERM__STEP_NAN;
      return TermChecker.returnStepError(term.getStep(), code1, code2, feature);
    }
    return null;
  }

  public static ErrorType isStepOK(final SequenceTerm term) {
    ConstantTerm _step = term.getStep();
    boolean _tripleNotEquals = (_step != null);
    if (_tripleNotEquals) {
      EStructuralFeature feature = AsmetalPackage.Literals.SEQUENCE_TERM__STEP;
      String code1 = ErrorCode.SEQUENCE_TERM__STEP_NEGATIVE;
      String code2 = ErrorCode.SEQUENCE_TERM__STEP_NAN;
      return TermChecker.returnStepError(term.getStep(), code1, code2, feature);
    }
    return null;
  }

  public static ErrorType returnStepError(final Term step, final String code1, final String code2, final EStructuralFeature feature) {
    String message = null;
    try {
      String _stringFromTerm = Utility.getStringFromTerm(step);
      Double step_number = new Double(_stringFromTerm);
      if (((step_number).doubleValue() < 0)) {
        message = "The step must be a positive number";
        return new ErrorType(message, feature, code1);
      }
    } catch (final Throwable _t) {
      if (_t instanceof NumberFormatException) {
        message = "The step must be a number";
        return new ErrorType(message, feature, code2);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    return null;
  }

  public static ErrorType isFunctionOK(final BinaryOperation operation) {
    EStructuralFeature feature = AsmetalPackage.Literals.EXPRESSION__OP;
    String op_name = Utility.getOperandFunctionName(operation.getOp());
    DomainTree domain_right = DomainCalculator.getDomainTerm(operation.getRight());
    String domain_name_right = Utility.getRightDomainNameForBinary(Utility.getDomain(domain_right.getCodeFromTree()));
    DomainTree domain_left = DomainCalculator.getDomainTerm(operation.getLeft());
    String domain_name_left = Utility.getRightDomainNameForBinary(Utility.getDomain(domain_left.getCodeFromTree()));
    if (((domain_right instanceof AnyDomain) || (domain_left instanceof AnyDomain))) {
      return null;
    }
    String code = null;
    if (((op_name.equals("eq") || op_name.equals("neq")) && Utility.isCompatible(domain_right, domain_left))) {
      code = (op_name + "(Prod(D,D))");
    } else {
      code = (((((op_name + "(Prod(") + domain_name_left) + ",") + domain_name_right) + "))");
    }
    boolean error = false;
    Function func = Utility.getFunction(code);
    if ((func == null)) {
      ArrayList<DomainTree> list_domain = new ArrayList<DomainTree>();
      list_domain.add(domain_right);
      list_domain.add(domain_left);
      DomainTree tree = DomainTree.mergeTree(list_domain);
      ArrayList<Function> list = Utility.getListOfPossibleFunction(op_name);
      if ((list == null)) {
        error = true;
      } else {
        func = Utility.searchForMostCompatible(list, tree);
      }
      if ((func == null)) {
        error = true;
      }
    }
    if (error) {
      return SharedCheckers.returnErrorFunctionString(code, feature);
    } else {
      return null;
    }
  }

  public static ErrorType isFunctionOK(final Expression operation) {
    EStructuralFeature feature = AsmetalPackage.Literals.EXPRESSION__OP;
    DomainTree domain = DomainCalculator.getDomainTerm(operation.getOperand());
    String op_name = Utility.getOperandFunctionName(operation.getOp());
    String code = null;
    if ((domain == null)) {
      code = (op_name + "(null)");
    } else {
      String _codeFromTree = domain.getCodeFromTree();
      String _plus = ((op_name + "(") + _codeFromTree);
      String _plus_1 = (_plus + ")");
      code = _plus_1;
    }
    return SharedCheckers.returnErrorFunctionString(code, feature);
  }

  public static ErrorType isAnyDomainOK(final FunctionTerm term) {
    EStructuralFeature feature = AsmetalPackage.Literals.FUNCTION_TERM__FUNCTION_NAME;
    String code = ErrorCode.FUNCTION_TERM__ANY_DOMAIN_NOT_DECLARED;
    return SharedCheckers.returnIsAnyDomainOK(feature, code);
  }

  public static ErrorType isFunctionOK(final FunctionTerm function_term) {
    EStructuralFeature feature = AsmetalPackage.Literals.FUNCTION_TERM__FUNCTION_NAME;
    TupleTerm _arguments = function_term.getArguments();
    boolean _tripleEquals = (_arguments == null);
    if (_tripleEquals) {
      boolean _isFunctionName = Utility.isFunctionName(function_term.getFunctionName());
      boolean _not = (!_isFunctionName);
      if (_not) {
        boolean _isRule = Utility.isRule(function_term.getFunctionName());
        if (_isRule) {
          return null;
        } else {
          return SharedCheckers.returnErrorDomainString(function_term.getFunctionName(), feature);
        }
      }
    } else {
      boolean _isFunctionName_1 = Utility.isFunctionName(function_term.getFunctionName());
      if (_isFunctionName_1) {
        DefaultMutableTreeNode domain_arguments = DomainCalculator.getDomainTupleTerm(function_term.getArguments());
        DomainTree tree = new DomainTree(domain_arguments);
        ArrayList<Function> list = Utility.getListOfPossibleFunction(function_term.getFunctionName());
        if ((list == null)) {
          return null;
        }
        Function func = Utility.searchForMostCompatible(list, tree);
        if ((func == null)) {
          func = Utility.getFunctionByName(function_term.getFunctionName());
          Integer function_arity = Utility.calculateFunctionArity(func);
          int body_arity = 0;
          TupleTerm _arguments_1 = function_term.getArguments();
          boolean _tripleEquals_1 = (_arguments_1 == null);
          if (_tripleEquals_1) {
            body_arity = 0;
          } else {
            body_arity = DomainTree.getArity(tree);
          }
          if (((function_arity).intValue() == body_arity)) {
            String _functionName = function_term.getFunctionName();
            String _plus = (_functionName + "(");
            String _codeFromTree = DomainTree.getCodeFromTree(domain_arguments);
            String _plus_1 = (_plus + _codeFromTree);
            String res = (_plus_1 + ")");
            return SharedCheckers.returnErrorFunctionString(res, feature);
          } else {
            String message = null;
            String code = ErrorCode.FUNCTION_TERM__INVALID_ARITY_FOR_ZERO_ARITY;
            if (((function_arity).intValue() == 0)) {
              code = ErrorCode.FUNCTION_TERM__INVALID_ARITY_FOR_ZERO_ARITY;
              message = "The associated function arity is 0, but the function term specify arguments of the function application";
            } else {
              code = ErrorCode.FUNCTION_TERM__INVALID_ARITY_FOR_N_ARITY;
              message = "The associated function arity is greater than 0, but the function term doesn\'t specify the actual arguments of the function application";
            }
            return new ErrorType(message, feature, code);
          }
        }
      } else {
        boolean _isRule_1 = Utility.isRule(function_term.getFunctionName());
        if (_isRule_1) {
          return null;
        } else {
          return SharedCheckers.returnErrorFunctionString(function_term.getFunctionName(), feature);
        }
      }
    }
    return null;
  }

  public static ErrorType isSymbolOK(final EnumTerm enum_term) {
    boolean _symbolExist = Utility.symbolExist(enum_term.getSymbol());
    boolean _not = (!_symbolExist);
    if (_not) {
      EStructuralFeature feature = AsmetalPackage.Literals.CONSTANT_TERM__SYMBOL;
      String _symbol = enum_term.getSymbol();
      String _plus = ("The enumeration element \'" + _symbol);
      String message = (_plus + "\' is not defined");
      String code = ErrorCode.ENUM_TERM__SYMBOL_NOT_FOUND;
      return new ErrorType(message, feature, code);
    }
    return null;
  }

  public static ErrorType isGuardOK(final ConditionalTerm cond_term) {
    EStructuralFeature feature = AsmetalPackage.Literals.CONDITIONAL_TERM__GUARD;
    return SharedCheckers.returnErrorGuardNotBoolean(cond_term.getGuard(), feature);
  }

  public static ErrorType areBranchesOK(final ConditionalTerm cond_term) {
    Term _elseTerm = cond_term.getElseTerm();
    boolean _tripleEquals = (_elseTerm == null);
    if (_tripleEquals) {
      return null;
    }
    DomainTree dom_then = DomainCalculator.getDomainTerm(cond_term.getThenTerm());
    DomainTree dom_else = DomainCalculator.getDomainTerm(cond_term.getElseTerm());
    boolean _isCompatible = Utility.isCompatible(dom_then, dom_else);
    boolean _not = (!_isCompatible);
    if (_not) {
      EStructuralFeature feature = AsmetalPackage.Literals.CONDITIONAL_TERM__THEN_TERM;
      String message = "The conditional term isn\'t compatible to the \'else term\'";
      String code = ErrorCode.CONDITIONAL_TERM__IF_ELSE_DOMAIN_NOT_COMPATIBLE;
      return new ErrorType(message, feature, code);
    }
    return null;
  }

  /**
   * Controllo che quello che ritorno sia compatibile con il dominio del case
   */
  public static ErrorType isResultTermsOK(final CaseTerm case_term) {
    String code = ErrorCode.CASE_TERM__RESULT_TERM_DOMAIN_NOT_COMPATIBLE;
    EStructuralFeature feature = AsmetalPackage.Literals.CASE_TERM__COMPARED_TERM;
    DomainTree case_domain = DomainCalculator.getDomainTerm(case_term);
    DomainTree result_domain = DomainCalculator.getDomainTerm(case_term.getComparedTerm());
    EList<Term> _resultTerms = case_term.getResultTerms();
    for (final Term branches : _resultTerms) {
      {
        result_domain = DomainCalculator.getDomainTerm(branches);
        boolean _isCompatible = Utility.isCompatible(result_domain, case_domain);
        boolean _not = (!_isCompatible);
        if (_not) {
          Object _root = result_domain.getModel().getRoot();
          String _plus = ("The term to match (" + _root);
          String _plus_1 = (_plus + ") isn\'t compatible to the left-hand terms of the case-clauses (");
          Object _root_1 = case_domain.getModel().getRoot();
          String _plus_2 = (_plus_1 + _root_1);
          String message = (_plus_2 + ")");
          return new ErrorType(message, feature, code);
        }
      }
    }
    return null;
  }

  /**
   * Controllo che quello che c'è dentro lo switch abbia un dominio compatibile con quello che ho subito dopo il case
   */
  public static ErrorType isComparingTermOK(final CaseTerm case_term) {
    String code = ErrorCode.CASE_TERM__COMPARED_AND_COMPARING_DOMAIN_NOT_COMPATIBLE;
    EStructuralFeature feature = AsmetalPackage.Literals.CASE_TERM__COMPARED_TERM;
    DomainTree compared_domain = DomainCalculator.getDomainTerm(case_term.getComparedTerm());
    DomainTree comparing_domain = null;
    EList<Term> _comparingTerm = case_term.getComparingTerm();
    for (final Term branches : _comparingTerm) {
      {
        comparing_domain = DomainCalculator.getDomainTerm(branches);
        boolean _isCompatible = Utility.isCompatible(comparing_domain, compared_domain);
        boolean _not = (!_isCompatible);
        if (_not) {
          Object _root = comparing_domain.getModel().getRoot();
          String _plus = ("The term to match (" + _root);
          String _plus_1 = (_plus + ") isn\'t compatible to the left-hand terms of the case-clauses (");
          Object _root_1 = compared_domain.getModel().getRoot();
          String _plus_2 = (_plus_1 + _root_1);
          String message = (_plus_2 + ")");
          return new ErrorType(message, feature, code);
        }
      }
    }
    return null;
  }

  public static ErrorType isOtherwiseTermOK(final CaseTerm case_term) {
    Term _otherwiseTerm = case_term.getOtherwiseTerm();
    boolean _tripleEquals = (_otherwiseTerm == null);
    if (_tripleEquals) {
      return null;
    }
    String message = "A case term must be compatible to the term of the otherwise-clause";
    String code = ErrorCode.CASE_TERM__OTHERWISE_DOMAIN_NOT_COMPATIBLE;
    EStructuralFeature feature = AsmetalPackage.Literals.CASE_TERM__OTHERWISE_TERM;
    DomainTree domain_case = DomainCalculator.getDomainTerm(case_term);
    DomainTree domain_otherwise = DomainCalculator.getDomainTerm(case_term.getOtherwiseTerm());
    boolean _isCompatible = Utility.isCompatible(domain_case, domain_otherwise);
    boolean _not = (!_isCompatible);
    if (_not) {
      return new ErrorType(message, feature, code);
    }
    return null;
  }

  public static ErrorType isRuleOK(final RuleAsTerm ruleas_term) {
    String _name = ruleas_term.getName();
    String _plus = ("Rule " + _name);
    String message = (_plus + " not found");
    String code = ErrorCode.BODY__RULE_NOT_FOUND;
    EStructuralFeature feature = AsmetalPackage.Literals.RULE_AS_TERM__NAME;
    ArrayList<RuleDeclaration> ruleList = Utility.getRule(ruleas_term.getName());
    if (((ruleList == null) || (ruleList.size() == 0))) {
      return new ErrorType(message, feature, code);
    }
    RuleDeclaration rule = Utility.searchForMostCompatibleRule(ruleList, ruleas_term.getDomains());
    if ((rule == null)) {
      return new ErrorType(message, feature, code);
    }
    if (((ruleas_term.getDomains() == null) || (ruleas_term.getDomains().size() == 0))) {
      return null;
    } else {
      if (((rule.getDomain() == null) && Objects.equals(ruleas_term.getDomains(), null))) {
        return null;
      } else {
        int _size = ruleas_term.getDomains().size();
        int _size_1 = rule.getDomain().size();
        boolean _notEquals = (_size != _size_1);
        if (_notEquals) {
          return new ErrorType(message, feature, code);
        } else {
          int _size_2 = ruleas_term.getDomains().size();
          boolean _equals = (_size_2 == 1);
          if (_equals) {
            boolean _isCompatible = Utility.isCompatible(ruleas_term.getDomains().get(0), rule.getDomain().get(0));
            boolean _not = (!_isCompatible);
            if (_not) {
              return new ErrorType(message, feature, code);
            }
          } else {
            DefaultMutableTreeNode knownDomains = DomainTree.getComposedNode("Prod", ruleas_term.getDomains());
            DefaultMutableTreeNode newDomains = DomainTree.getComposedNode("Prod", rule.getDomain());
            DomainTree _domainTree = new DomainTree(knownDomains);
            DomainTree _domainTree_1 = new DomainTree(newDomains);
            boolean _isCompatible_1 = Utility.isCompatible(_domainTree, _domainTree_1);
            boolean _not_1 = (!_isCompatible_1);
            if (_not_1) {
              return new ErrorType(message, feature, code);
            }
          }
        }
      }
    }
    return null;
  }

  public static ErrorType isVariableListOK(final LetTerm let_term) {
    EStructuralFeature feature = AsmetalPackage.Literals.VARIABLE_BINDING_TERM__VARIABLE;
    String code = ErrorCode.LET_TERM__VARIABLE_ALREADY_USED;
    return SharedCheckers.returnErrorVariableDeclared("LetTerm", feature, code, let_term.getVariable(), let_term);
  }

  public static ErrorType isVariableListOK(final FiniteQuantificationTerm finite_term) {
    if ((finite_term instanceof ForallTerm)) {
      return TermChecker.isVariableListOK(((ForallTerm)finite_term));
    } else {
      if ((finite_term instanceof ExistTerm)) {
        return TermChecker.isVariableListOK(((ExistTerm)finite_term));
      } else {
        if ((finite_term instanceof ExistUniqueTerm)) {
          return TermChecker.isVariableListOK(((ExistUniqueTerm)finite_term));
        } else {
          return null;
        }
      }
    }
  }

  public static ErrorType isVariableListOK(final ForallTerm forall_term) {
    EStructuralFeature feature = AsmetalPackage.Literals.VARIABLE_BINDING_TERM__VARIABLE;
    String code = ErrorCode.FORALL_TERM__VARIABLE_ALREADY_USED;
    return SharedCheckers.returnErrorVariableDeclared("ForallTerm", feature, code, forall_term.getVariable(), forall_term);
  }

  public static ErrorType isVariableListOK(final ExistTerm exist_term) {
    EStructuralFeature feature = AsmetalPackage.Literals.VARIABLE_BINDING_TERM__VARIABLE;
    String code = ErrorCode.EXIST_TERM__VARIABLE_ALREADY_USED;
    return SharedCheckers.returnErrorVariableDeclared("ExistTerm", feature, code, exist_term.getVariable(), exist_term);
  }

  public static ErrorType isVariableListOK(final ExistUniqueTerm exist_unique_term) {
    EStructuralFeature feature = AsmetalPackage.Literals.VARIABLE_BINDING_TERM__VARIABLE;
    String code = ErrorCode.EXIST_UNIQUE_TERM__VARIABLE_ALREADY_USED;
    return SharedCheckers.returnErrorVariableDeclared("ExistUniqueTerm", feature, code, exist_unique_term.getVariable(), exist_unique_term);
  }

  public static ErrorType isVariableDomainOK(final FiniteQuantificationTerm finite_term) {
    EStructuralFeature feature = AsmetalPackage.Literals.VARIABLE_BINDING_TERM__VARIABLE;
    String message = "The domain of each term appearing in the variables ranges must be a powerset domain.";
    String code = ErrorCode.FINITE_QUANT_TERM__VARIABLE_DOMAIN_NOT_POWERSET;
    boolean errorOK = false;
    EList<Term> _ranges = finite_term.getRanges();
    for (final Term range : _ranges) {
      if ((!errorOK)) {
        if ((((!Utility.isPowerSetDomain(range)) && 
          (!Utility.imported_all_domain_map.containsKey(DomainCalculator.getDomainTerm(range).getCodeFromTree()))) && 
          (!Utility.isDomainName(DomainCalculator.getDomainTerm(range).getCodeFromTree())))) {
          errorOK = true;
        }
      }
    }
    if (errorOK) {
      return new ErrorType(message, feature, code);
    }
    return null;
  }

  public static ErrorType isGuardOK(final FiniteQuantificationTerm finite_term) {
    EStructuralFeature feature = AsmetalPackage.Literals.FINITE_QUANTIFICATION_TERM__GUARD;
    return SharedCheckers.returnErrorGuardNotBoolean(finite_term.getGuard(), feature);
  }

  public static ErrorType isGuardOK(final ComprehensionTerm compr_term) {
    Term _guard = compr_term.getGuard();
    boolean _tripleEquals = (_guard == null);
    if (_tripleEquals) {
      return null;
    }
    EStructuralFeature feature = AsmetalPackage.Literals.COMPREHENSION_TERM__GUARD;
    return SharedCheckers.returnErrorGuardNotBoolean(compr_term.getGuard(), feature);
  }

  public static ErrorType isVariableDomainOK(final SetCT setct_term) {
    EStructuralFeature feature = AsmetalPackage.Literals.COMPREHENSION_TERM__RANGES;
    String message = "In a SetCT, the domains of variables must be Powerset.";
    String code = ErrorCode.SET_CT_TERM__VARIABLE_DOMAIN_NOT_POWERSET;
    boolean errorOK = false;
    EList<Term> _ranges = setct_term.getRanges();
    for (final Term range : _ranges) {
      if ((!errorOK)) {
        boolean _isPowerSetDomain = Utility.isPowerSetDomain(range);
        boolean _not = (!_isPowerSetDomain);
        if (_not) {
          errorOK = true;
        }
      }
    }
    if (errorOK) {
      return new ErrorType(message, feature, code);
    }
    return null;
  }

  public static ErrorType isVariableDomainOK(final MapCT mapct_term) {
    EStructuralFeature feature = AsmetalPackage.Literals.COMPREHENSION_TERM__RANGES;
    String message = "In a MapCT, terms in ranges, Di, representing where variables vary, must be powersets..";
    String code = ErrorCode.MAP_CT_TERM__VARIABLE_DOMAIN_NOT_POWERSET;
    boolean errorOK = false;
    EList<Term> _ranges = mapct_term.getRanges();
    for (final Term range : _ranges) {
      if ((!errorOK)) {
        boolean _isPowerSetDomain = Utility.isPowerSetDomain(range);
        boolean _not = (!_isPowerSetDomain);
        if (_not) {
          errorOK = true;
        }
      }
    }
    if (errorOK) {
      return new ErrorType(message, feature, code);
    }
    return null;
  }

  public static ErrorType isVariableDomainOK(final BagCT bagct_term) {
    EStructuralFeature feature = AsmetalPackage.Literals.COMPREHENSION_TERM__RANGES;
    String message = "In a BagCT, terms in ranges, Bi, representing where variables vary, must be bags.";
    String code = ErrorCode.BAG_CT_TERM__VARIABLE_DOMAIN_NOT_BAG;
    boolean errorOK = false;
    EList<Term> _ranges = bagct_term.getRanges();
    for (final Term range : _ranges) {
      if ((!errorOK)) {
        boolean _isBagDomain = Utility.isBagDomain(range);
        boolean _not = (!_isBagDomain);
        if (_not) {
          errorOK = true;
        }
      }
    }
    if (errorOK) {
      return new ErrorType(message, feature, code);
    }
    return null;
  }

  public static ErrorType isVariableDomainOK(final SequenceCT sequencect_term) {
    EStructuralFeature feature = AsmetalPackage.Literals.COMPREHENSION_TERM__RANGES;
    String message = " In a SequenceCT, terms in ranges, Bi, representing where variables vary, must be sequences";
    String code = ErrorCode.SEQUENCE_CT_TERM__VARIABLE_DOMAIN_NOT_SEQUENCE;
    boolean errorOK = false;
    EList<Term> _ranges = sequencect_term.getRanges();
    for (final Term range : _ranges) {
      if ((!errorOK)) {
        if (((!Utility.isSequenceDomain(range)) && (!(range instanceof SequenceTerm)))) {
          errorOK = true;
        }
      }
    }
    if (errorOK) {
      return new ErrorType(message, feature, code);
    }
    return null;
  }

  public static ErrorType isVariableListOK(final SetCT setct_term) {
    EStructuralFeature feature = AsmetalPackage.Literals.VARIABLE_BINDING_TERM__VARIABLE;
    String code = ErrorCode.SET_CT_TERM__VARIABLE_ALREADY_USED;
    return SharedCheckers.returnErrorVariableDeclared("SetCT", feature, code, setct_term.getVariable(), setct_term);
  }

  public static ErrorType isVariableListOK(final MapCT setct_term) {
    EStructuralFeature feature = AsmetalPackage.Literals.VARIABLE_BINDING_TERM__VARIABLE;
    String code = ErrorCode.MAP_CT_TERM__VARIABLE_ALREADY_USED;
    return SharedCheckers.returnErrorVariableDeclared("MapCT", feature, code, setct_term.getVariable(), setct_term);
  }

  public static ErrorType isVariableListOK(final SequenceCT setct_term) {
    EStructuralFeature feature = AsmetalPackage.Literals.VARIABLE_BINDING_TERM__VARIABLE;
    String code = ErrorCode.SEQUENCE_CT_TERM__VARIABLE_ALREADY_USED;
    return SharedCheckers.returnErrorVariableDeclared("SequenceCT", feature, code, setct_term.getVariable(), setct_term);
  }

  public static ErrorType isVariableListOK(final BagCT setct_term) {
    EStructuralFeature feature = AsmetalPackage.Literals.VARIABLE_BINDING_TERM__VARIABLE;
    String code = ErrorCode.BAG_CT_TERM__VARIABLE_ALREADY_USED;
    return SharedCheckers.returnErrorVariableDeclared("BagCT", feature, code, setct_term.getVariable(), setct_term);
  }
}
