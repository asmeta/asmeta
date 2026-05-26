package org.asmeta.xt.validation.checkers;

import org.asmeta.xt.asmetal.AbstractTD;
import org.asmeta.xt.asmetal.AsmetalPackage;
import org.asmeta.xt.asmetal.BasicTerm;
import org.asmeta.xt.asmetal.CaseRule;
import org.asmeta.xt.asmetal.ChooseRule;
import org.asmeta.xt.asmetal.ConcreteDomain;
import org.asmeta.xt.asmetal.ConditionalRule;
import org.asmeta.xt.asmetal.Domain;
import org.asmeta.xt.asmetal.DynamicFunction;
import org.asmeta.xt.asmetal.ExtendRule;
import org.asmeta.xt.asmetal.ForallRule;
import org.asmeta.xt.asmetal.Function;
import org.asmeta.xt.asmetal.FunctionTerm;
import org.asmeta.xt.asmetal.LetRule;
import org.asmeta.xt.asmetal.LocationTerm;
import org.asmeta.xt.asmetal.MonitoredFunction;
import org.asmeta.xt.asmetal.RuleDomain;
import org.asmeta.xt.asmetal.Term;
import org.asmeta.xt.asmetal.TermAsRule;
import org.asmeta.xt.asmetal.TurboDerivedRule;
import org.asmeta.xt.asmetal.UpdateRule;
import org.asmeta.xt.asmetal.VariableTerm;
import org.asmeta.xt.validation.ErrorCode;
import org.asmeta.xt.validation.utility.DomainCalculator;
import org.asmeta.xt.validation.utility.DomainTree;
import org.asmeta.xt.validation.utility.ErrorType;
import org.asmeta.xt.validation.utility.Utility;
import org.asmeta.xt.validation.utility.VariableKind;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;

@SuppressWarnings("all")
public class RuleChecker {
  public static ErrorType isGuardOK(final ConditionalRule cond_rule) {
    EStructuralFeature feature = AsmetalPackage.Literals.CONDITIONAL_RULE__GUARD;
    return SharedCheckers.returnErrorGuardNotBoolean(cond_rule.getGuard(), feature);
  }

  public static ErrorType isUpdateCompatibleOK(final UpdateRule update_rule) {
    String message = "In an update rule, the term on the right-hand side must be compatible with the one on the left-hand side";
    String code = ErrorCode.UPDATE_RULE__DOMAINS_NOT_COMPATIBLE;
    EStructuralFeature feature = AsmetalPackage.Literals.UPDATE_RULE__UPDATING_TERM;
    DomainTree location_domain = DomainCalculator.getDomainTerm(update_rule.getLocation());
    DomainTree update_domain = DomainCalculator.getDomainTerm(update_rule.getUpdatingTerm());
    boolean _equals = update_domain.getCodeFromTree().equals("Undef");
    if (_equals) {
      return null;
    }
    boolean _isCompatible = Utility.isCompatible(update_domain, location_domain);
    boolean res = (!_isCompatible);
    if (res) {
      res = (Utility.existOneCompatible(update_rule.getLocation(), update_rule.getUpdatingTerm())).booleanValue();
      if ((!res)) {
        Object _root = location_domain.getModel().getRoot();
        String _plus = ((message + " (") + _root);
        String _plus_1 = (_plus + "-");
        Object _root_1 = update_domain.getModel().getRoot();
        String _plus_2 = (_plus_1 + _root_1);
        String _plus_3 = (_plus_2 + ")");
        return new ErrorType(_plus_3, feature, code);
      }
    }
    return null;
  }

  public static ErrorType isLocationOK(final UpdateRule update_rule) {
    String message = null;
    String code = null;
    EStructuralFeature feature = AsmetalPackage.Literals.UPDATE_RULE__LOCATION;
    BasicTerm _location = update_rule.getLocation();
    if ((_location instanceof FunctionTerm)) {
      BasicTerm _location_1 = update_rule.getLocation();
      FunctionTerm f_term = ((FunctionTerm) _location_1);
      if ((f_term == null)) {
        return SharedCheckers.returnErrorFunctionString(f_term.getFunctionName(), feature);
      }
      Function function = DomainCalculator.getFunctionFromFunctionTerm(f_term);
      if ((function instanceof DynamicFunction)) {
        code = ErrorCode.UPDATE_RULE__INVALID_LOCATION;
        message = "In an update rule, the left-hand side must be either a location term (with a non monitored function) or a location variable term";
        if ((function instanceof MonitoredFunction)) {
          return new ErrorType(message, feature, code);
        }
      } else {
        code = ErrorCode.LOCATION_TERM__NOT_DYNAMIC;
        message = "In a location term, the leftmost function must be dynamic";
        return new ErrorType(message, feature, code);
      }
    } else {
      BasicTerm _location_2 = update_rule.getLocation();
      if ((_location_2 instanceof LocationTerm)) {
        BasicTerm _location_3 = update_rule.getLocation();
        LocationTerm f_term_1 = ((LocationTerm) _location_3);
        if ((f_term_1 == null)) {
          return SharedCheckers.returnErrorFunctionString(f_term_1.getFunctionName(), feature);
        }
        Function function_1 = DomainCalculator.getFunctionFromFunctionTerm(f_term_1);
        if ((function_1 instanceof DynamicFunction)) {
          code = ErrorCode.UPDATE_RULE__INVALID_LOCATION;
          message = "In an update rule, the left-hand side must be either a location term (with a non monitored function) or a location variable term";
          if ((function_1 instanceof MonitoredFunction)) {
            return new ErrorType(message, feature, code);
          }
        } else {
          code = ErrorCode.LOCATION_TERM__NOT_DYNAMIC;
          message = "In a location term, the leftmost function must be dynamic";
          return new ErrorType(message, feature, code);
        }
      } else {
        BasicTerm _location_4 = update_rule.getLocation();
        if ((_location_4 instanceof VariableTerm)) {
          BasicTerm _location_5 = update_rule.getLocation();
          VariableTerm v_term = ((VariableTerm) _location_5);
          Domain domain = Utility.getDomainFromVariable(v_term.getName());
          v_term.setKind(VariableKind.LOCATION_VAR);
          if ((domain == null)) {
            code = ErrorCode.VARIABLE_TERM__NOT_DECLARED;
            String _name = v_term.getName();
            String _plus = ("The definition of  variable is not allowed and " + _name);
            String _plus_1 = (_plus + " variable occurs for the first time");
            message = _plus_1;
            return new ErrorType(message, feature, code);
          }
          code = ErrorCode.UPDATE_RULE__INVALID_VARIABLE;
          message = "In an Updating rule, the variable to update must be a location variable, not a rule variable";
        }
      }
    }
    return null;
  }

  public static ErrorType isExtendedDomainOK(final ExtendRule extend_rule) {
    EStructuralFeature feature = AsmetalPackage.Literals.EXTEND_RULE__EXTENDED_DOMAIN;
    String code = ErrorCode.EXTENDED_RULE__INVALID_DOMAIN;
    String message = "In an extend rule, the domain to extend must be dynamic, and it must be an abstract TD or concrete domain subsetting an abstract TD";
    boolean isError = false;
    Domain dom = Utility.getDomain(extend_rule.getExtendedDomain().getName());
    if ((dom == null)) {
      SharedCheckers.returnErrorDomainString(extend_rule.getExtendedDomain().getName(), feature);
    }
    if ((dom instanceof ConcreteDomain)) {
      boolean _isDynamic = ((ConcreteDomain)dom).isDynamic();
      if (_isDynamic) {
        String domain_code = Utility.calculateDomainCode(((ConcreteDomain)dom).getTypeDomain());
        Domain type_dom = Utility.getDomain(domain_code);
        if ((!(type_dom instanceof AbstractTD))) {
          isError = true;
        }
      } else {
        isError = true;
      }
    } else {
      boolean _not = (!((dom instanceof AbstractTD) && dom.getIsDynamic()));
      if (_not) {
        isError = true;
      }
    }
    if (isError) {
      return new ErrorType(message, feature, code);
    }
    return null;
  }

  public static ErrorType isReserveDomainOK(final ExtendRule extend_rule) {
    EStructuralFeature feature = AsmetalPackage.Literals.EXTEND_RULE__EXTENDED_DOMAIN;
    String code = ErrorCode.EXTENDED_RULE__RESERVE_DOMAIN_NOT_DECLARED;
    return SharedCheckers.returnIsReserveDomainOK(feature, code);
  }

  public static ErrorType isVariableListOK(final ExtendRule extend_rule) {
    EStructuralFeature feature = AsmetalPackage.Literals.EXTEND_RULE__BOUND_VAR;
    String code = ErrorCode.EXTENDED_RULE__VARIABLE_ALREADY_USED;
    return SharedCheckers.returnErrorVariableDeclared("ExtendRule", feature, code, extend_rule.getBoundVar(), extend_rule);
  }

  public static ErrorType isGuardOK(final ChooseRule cond_rule) {
    EStructuralFeature feature = AsmetalPackage.Literals.CHOOSE_RULE__GUARD;
    return SharedCheckers.returnErrorGuardNotBoolean(cond_rule.getGuard(), feature);
  }

  public static ErrorType isVariableListOK(final ChooseRule choose_rule) {
    EStructuralFeature feature = AsmetalPackage.Literals.CHOOSE_RULE__VARIABLE;
    String code = ErrorCode.CHOOSE_RULE__VARIABLE_ALREADY_USED;
    return SharedCheckers.returnErrorVariableDeclared("Chooserule", feature, code, choose_rule.getVariable(), choose_rule);
  }

  public static ErrorType isRangeListOK(final ChooseRule choose_rule) {
    EStructuralFeature feature = AsmetalPackage.Literals.CHOOSE_RULE__RANGES;
    String code1 = ErrorCode.CHOOSE_RULE__RANGE_NOT_POWERSET;
    String code2 = ErrorCode.CHOOSE_RULE__DOMAINS_NOT_COMPATIBLE;
    return SharedCheckers.returnErrorPowersetRanges(choose_rule.getRanges(), choose_rule.getVariable(), feature, code1, code2);
  }

  public static ErrorType isGuardOK(final ForallRule forall_rule) {
    Term _guard = forall_rule.getGuard();
    boolean _tripleEquals = (_guard == null);
    if (_tripleEquals) {
      return null;
    }
    EStructuralFeature feature = AsmetalPackage.Literals.FORALL_RULE__GUARD;
    return SharedCheckers.returnErrorGuardNotBoolean(forall_rule.getGuard(), feature);
  }

  public static ErrorType isVariableListOK(final ForallRule forall_rule) {
    EStructuralFeature feature = AsmetalPackage.Literals.FORALL_RULE__VARIABLE;
    String code = ErrorCode.FORALL_RULE__VARIABLE_ALREADY_USED;
    return SharedCheckers.returnErrorVariableDeclared("ForallRule", feature, code, forall_rule.getVariable(), forall_rule);
  }

  public static ErrorType isRangeListOK(final ForallRule forall_rule) {
    EStructuralFeature feature = AsmetalPackage.Literals.FORALL_RULE__RANGES;
    String code1 = ErrorCode.FORALL_RULE__RANGE_NOT_POWERSET;
    String code2 = ErrorCode.FORALL_RULE__DOMAINS_NOT_COMPATIBLE;
    return SharedCheckers.returnErrorPowersetRanges(forall_rule.getRanges(), forall_rule.getVariable(), feature, code1, code2);
  }

  public static ErrorType isVariableListOK(final LetRule choose_rule) {
    EStructuralFeature feature = AsmetalPackage.Literals.LET_RULE__VARIABLE;
    String code = ErrorCode.LET_RULE__VARIABLE_ALREADY_USED;
    return SharedCheckers.returnErrorVariableDeclared("LetRule", feature, code, choose_rule.getVariable(), choose_rule);
  }

  public static ErrorType areBranchesOK(final CaseRule case_rule) {
    DomainTree term_domain = DomainCalculator.getDomainTerm(case_rule.getTerm());
    DomainTree case_domain = null;
    EList<Term> _caseTerm = case_rule.getCaseTerm();
    for (final Term term : _caseTerm) {
      {
        case_domain = DomainCalculator.getDomainTerm(term);
        boolean _isCompatible = Utility.isCompatible(term_domain, case_domain);
        boolean _not = (!_isCompatible);
        if (_not) {
          EStructuralFeature feature = AsmetalPackage.Literals.CASE_RULE__TERM;
          String message = "In a case rule, every term of the case-clauses must be compatible to the main term";
          String code = ErrorCode.CASE_RULE__BRANCH_DOMAIN_NOT_COMPATIBLE;
          return new ErrorType(message, feature, code);
        }
      }
    }
    return null;
  }

  public static ErrorType isGuardOK(final TurboDerivedRule while_rule) {
    EStructuralFeature feature = AsmetalPackage.Literals.TURBO_DERIVED_RULE__GUARD;
    return SharedCheckers.returnErrorGuardNotBoolean(while_rule.getGuard(), feature);
  }

  public static ErrorType isRuleOk(final TermAsRule term_as_Rule) {
    EStructuralFeature feature = null;
    BasicTerm _term = term_as_Rule.getTerm();
    if ((_term instanceof VariableTerm)) {
      return null;
    }
    BasicTerm _term_1 = term_as_Rule.getTerm();
    if ((_term_1 instanceof FunctionTerm)) {
      BasicTerm _term_2 = term_as_Rule.getTerm();
      FunctionTerm ft = ((FunctionTerm) _term_2);
      if (((ft.getFunction() != null) && (ft.getFunction().getCodomain() != null))) {
        Domain _codomain = ft.getFunction().getCodomain();
        if ((_codomain instanceof RuleDomain)) {
          return null;
        } else {
          return new ErrorType("Error in TermAsRule term: only Rule can be used as Codomain", feature, ErrorCode.TERM_AS_RULE_CODOMAIN_NOT_RULE);
        }
      }
    }
    return null;
  }
}
