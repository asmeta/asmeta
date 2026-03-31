package org.asmeta.xt.validation.validators;

import org.asmeta.xt.asmetal.BagCT;
import org.asmeta.xt.asmetal.BagTerm;
import org.asmeta.xt.asmetal.BinaryOperation;
import org.asmeta.xt.asmetal.CaseTerm;
import org.asmeta.xt.asmetal.ConditionalTerm;
import org.asmeta.xt.asmetal.EnumTerm;
import org.asmeta.xt.asmetal.Expression;
import org.asmeta.xt.asmetal.FiniteQuantificationTerm;
import org.asmeta.xt.asmetal.FunctionTerm;
import org.asmeta.xt.asmetal.LetTerm;
import org.asmeta.xt.asmetal.MapCT;
import org.asmeta.xt.asmetal.MapTerm;
import org.asmeta.xt.asmetal.RuleAsTerm;
import org.asmeta.xt.asmetal.SequenceCT;
import org.asmeta.xt.asmetal.SequenceTerm;
import org.asmeta.xt.asmetal.SetCT;
import org.asmeta.xt.asmetal.SetTerm;
import org.asmeta.xt.validation.checkers.TermChecker;
import org.asmeta.xt.validation.utility.ErrorType;
import org.asmeta.xt.validation.utility.Utility;

@SuppressWarnings("all")
public class TermValidator {
  public static ErrorType checkError(final BinaryOperation operation) {
    ErrorType error = null;
    error = TermChecker.isFunctionOK(operation);
    if ((error != null)) {
      return error;
    }
    return error;
  }

  public static ErrorType checkError(final Expression operation) {
    if (((operation.getOp() == null) || (operation instanceof BinaryOperation))) {
      return null;
    }
    ErrorType error = null;
    error = TermChecker.isFunctionOK(operation);
    if ((error != null)) {
      return error;
    }
    return error;
  }

  public static ErrorType checkError(final SetTerm set_term) {
    ErrorType error = null;
    error = TermChecker.isAnyDomainOK(set_term);
    if ((error != null)) {
      return error;
    }
    error = TermChecker.isDeclaredOnlyOnce(set_term);
    if ((error != null)) {
      return error;
    }
    error = TermChecker.isColletionTermSameDomain(set_term);
    if ((error != null)) {
      return error;
    }
    error = TermChecker.isStepOK(set_term);
    if ((error != null)) {
      return error;
    }
    return null;
  }

  public static ErrorType checkError(final BagTerm bag_term) {
    ErrorType error = null;
    error = TermChecker.isAnyDomainOK(bag_term);
    if ((error != null)) {
      return error;
    }
    error = TermChecker.isColletionTermSameDomain(bag_term);
    if ((error != null)) {
      return error;
    }
    error = TermChecker.isStepOK(bag_term);
    if ((error != null)) {
      return error;
    }
    return null;
  }

  public static ErrorType checkError(final SequenceTerm sequence_term) {
    ErrorType error = null;
    error = TermChecker.isAnyDomainOK(sequence_term);
    if ((error != null)) {
      return error;
    }
    error = TermChecker.isColletionTermSameDomain(sequence_term);
    if ((error != null)) {
      return error;
    }
    error = TermChecker.isStepOK(sequence_term);
    if ((error != null)) {
      return error;
    }
    return null;
  }

  public static ErrorType checkError(final MapTerm map_term) {
    ErrorType error = null;
    error = TermChecker.isAnyDomainOK(map_term);
    if ((error != null)) {
      return error;
    }
    error = TermChecker.isColletionTermSameDomain(map_term);
    if ((error != null)) {
      return error;
    }
    return null;
  }

  public static ErrorType checkError(final FunctionTerm function) {
    ErrorType error = null;
    error = TermChecker.isAnyDomainOK(function);
    if ((error != null)) {
      return error;
    }
    error = TermChecker.isFunctionOK(function);
    if ((error != null)) {
      return error;
    }
    return error;
  }

  public static ErrorType checkError(final EnumTerm enum_term) {
    ErrorType error = null;
    error = TermChecker.isSymbolOK(enum_term);
    if ((error != null)) {
      return error;
    }
    return null;
  }

  public static ErrorType checkError(final LetTerm let_term) {
    ErrorType error = null;
    error = TermChecker.isVariableListOK(let_term);
    if ((error != null)) {
      return error;
    }
    Utility.fillVariableMap(let_term);
    return null;
  }

  public static ErrorType checkError(final FiniteQuantificationTerm finite_term) {
    ErrorType error = null;
    error = TermChecker.isVariableListOK(finite_term);
    if ((error != null)) {
      return error;
    }
    Utility.fillVariableMap(finite_term);
    error = TermChecker.isVariableDomainOK(finite_term);
    if ((error != null)) {
      return error;
    }
    error = TermChecker.isGuardOK(finite_term);
    if ((error != null)) {
      return error;
    }
    return null;
  }

  public static ErrorType checkError(final SetCT setct_term) {
    ErrorType error = null;
    error = TermChecker.isVariableListOK(setct_term);
    if ((error != null)) {
      return error;
    }
    Utility.fillVariableMap(setct_term);
    error = TermChecker.isVariableDomainOK(setct_term);
    if ((error != null)) {
      return error;
    }
    error = TermChecker.isGuardOK(setct_term);
    if ((error != null)) {
      return error;
    }
    return null;
  }

  public static ErrorType checkError(final BagCT bagct_term) {
    ErrorType error = null;
    error = TermChecker.isVariableListOK(bagct_term);
    if ((error != null)) {
      return error;
    }
    Utility.fillVariableMap(bagct_term);
    error = TermChecker.isVariableDomainOK(bagct_term);
    if ((error != null)) {
      return error;
    }
    error = TermChecker.isGuardOK(bagct_term);
    if ((error != null)) {
      return error;
    }
    return null;
  }

  public static ErrorType checkError(final SequenceCT sequencect_term) {
    ErrorType error = null;
    error = TermChecker.isVariableListOK(sequencect_term);
    if ((error != null)) {
      return error;
    }
    Utility.fillVariableMap(sequencect_term);
    error = TermChecker.isVariableDomainOK(sequencect_term);
    if ((error != null)) {
      return error;
    }
    error = TermChecker.isGuardOK(sequencect_term);
    if ((error != null)) {
      return error;
    }
    return null;
  }

  public static ErrorType checkError(final MapCT mapct_term) {
    ErrorType error = null;
    error = TermChecker.isVariableListOK(mapct_term);
    if ((error != null)) {
      return error;
    }
    Utility.fillVariableMap(mapct_term);
    error = TermChecker.isVariableDomainOK(mapct_term);
    if ((error != null)) {
      return error;
    }
    error = TermChecker.isGuardOK(mapct_term);
    if ((error != null)) {
      return error;
    }
    return null;
  }

  public static ErrorType checkError(final ConditionalTerm cond_term) {
    ErrorType error = null;
    error = TermChecker.isGuardOK(cond_term);
    if ((error != null)) {
      return error;
    }
    error = TermChecker.areBranchesOK(cond_term);
    if ((error != null)) {
      return error;
    }
    return null;
  }

  public static ErrorType checkError(final CaseTerm case_term) {
    ErrorType error = null;
    error = TermChecker.isComparingTermOK(case_term);
    if ((error != null)) {
      return error;
    }
    error = TermChecker.isResultTermsOK(case_term);
    if ((error != null)) {
      return error;
    }
    error = TermChecker.isOtherwiseTermOK(case_term);
    if ((error != null)) {
      return error;
    }
    return null;
  }

  public static ErrorType checkError(final RuleAsTerm ruleas_term) {
    ErrorType error = null;
    error = TermChecker.isRuleOK(ruleas_term);
    if ((error != null)) {
      return error;
    }
    return null;
  }
}
