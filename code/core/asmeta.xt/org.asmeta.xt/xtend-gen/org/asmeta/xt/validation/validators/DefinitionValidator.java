package org.asmeta.xt.validation.validators;

import org.asmeta.xt.asmetal.BagDomain;
import org.asmeta.xt.asmetal.BasicTD;
import org.asmeta.xt.asmetal.CompassionConstraint;
import org.asmeta.xt.asmetal.ConcreteDomain;
import org.asmeta.xt.asmetal.CtlSpec;
import org.asmeta.xt.asmetal.Domain;
import org.asmeta.xt.asmetal.EnumElement;
import org.asmeta.xt.asmetal.FairnessConstraint;
import org.asmeta.xt.asmetal.Function;
import org.asmeta.xt.asmetal.Invariant;
import org.asmeta.xt.asmetal.InvariantConstraint;
import org.asmeta.xt.asmetal.InvariantElement;
import org.asmeta.xt.asmetal.JusticeConstraint;
import org.asmeta.xt.asmetal.LocalFunction;
import org.asmeta.xt.asmetal.LtlSpec;
import org.asmeta.xt.asmetal.MapDomain;
import org.asmeta.xt.asmetal.PowersetDomain;
import org.asmeta.xt.asmetal.ProductDomain;
import org.asmeta.xt.asmetal.RuleDeclaration;
import org.asmeta.xt.asmetal.RuleDomain;
import org.asmeta.xt.asmetal.SequenceDomain;
import org.asmeta.xt.asmetal.Signature;
import org.asmeta.xt.asmetal.TemporalProperty;
import org.asmeta.xt.validation.checkers.DefinitionChecker;
import org.asmeta.xt.validation.utility.ErrorType;
import org.asmeta.xt.validation.utility.Utility;
import org.eclipse.emf.ecore.EObject;

@SuppressWarnings("all")
public class DefinitionValidator {
  public static ErrorType checkError(final Domain domain) {
    ErrorType error = null;
    EObject _eContainer = domain.eContainer();
    if ((_eContainer instanceof Signature)) {
      error = DefinitionChecker.isDeclaredOnlyOnce(domain);
      if ((error != null)) {
        return error;
      }
    }
    return null;
  }

  public static ErrorType checkError(final Function function) {
    ErrorType error = null;
    EObject _eContainer = function.eContainer();
    if ((_eContainer instanceof Signature)) {
      error = DefinitionChecker.isDeclaredOnlyOnce(function);
      if ((error != null)) {
        return error;
      }
      error = DefinitionChecker.isFunctionDomainOK(function);
      if ((error != null)) {
        return error;
      }
      error = DefinitionChecker.isFunctionCodomainOK(function);
      if ((error != null)) {
        return error;
      }
    }
    return null;
  }

  public static ErrorType checkError(final LocalFunction function) {
    ErrorType error = null;
    error = DefinitionChecker.isParentOK(function);
    if ((error != null)) {
      return error;
    }
    return null;
  }

  public static ErrorType checkError(final ConcreteDomain domain) {
    ErrorType error = null;
    EObject _eContainer = domain.eContainer();
    if ((_eContainer instanceof Signature)) {
      error = DefinitionChecker.isTypeDomainOK(domain);
      if ((error != null)) {
        return error;
      }
    }
    return null;
  }

  public static ErrorType checkError(final BasicTD domain) {
    ErrorType error = null;
    EObject _eContainer = domain.eContainer();
    if ((_eContainer instanceof Signature)) {
      error = DefinitionChecker.isDomainNameOK(domain);
      if ((error != null)) {
        return error;
      }
    }
    return null;
  }

  public static ErrorType checkError(final BagDomain domain) {
    ErrorType error = null;
    error = DefinitionChecker.isCoreOK(domain);
    if ((error != null)) {
      return error;
    }
    return null;
  }

  public static ErrorType checkError(final SequenceDomain domain) {
    ErrorType error = null;
    error = DefinitionChecker.isCoreOK(domain);
    if ((error != null)) {
      return error;
    }
    return null;
  }

  public static ErrorType checkError(final PowersetDomain domain) {
    ErrorType error = null;
    error = DefinitionChecker.isCoreOK(domain);
    if ((error != null)) {
      return error;
    }
    return null;
  }

  public static ErrorType checkError(final MapDomain domain) {
    ErrorType error = null;
    error = DefinitionChecker.isCoreOK(domain);
    if ((error != null)) {
      return error;
    }
    return null;
  }

  public static ErrorType checkError(final RuleDomain domain) {
    ErrorType error = null;
    error = DefinitionChecker.isCoreOK(domain);
    if ((error != null)) {
      return error;
    }
    return null;
  }

  public static ErrorType checkError(final EnumElement element) {
    ErrorType error = null;
    error = DefinitionChecker.isDeclaredOnlyOnce(element);
    if ((error != null)) {
      return error;
    }
    return null;
  }

  public static ErrorType checkError(final ProductDomain domain) {
    ErrorType error = null;
    error = DefinitionChecker.isCoreOK(domain);
    if ((error != null)) {
      return error;
    }
    return null;
  }

  public static ErrorType checkError(final RuleDeclaration rule_declaration) {
    Utility.fillVariableMap(rule_declaration);
    ErrorType error = null;
    error = DefinitionChecker.isDefineOnceOK(rule_declaration);
    if ((error != null)) {
      return error;
    }
    error = DefinitionChecker.isVariableListOK(rule_declaration);
    if ((error != null)) {
      return error;
    }
    error = DefinitionChecker.isDomainListOK(rule_declaration);
    if ((error != null)) {
      return error;
    }
    return null;
  }

  public static ErrorType checkError(final Invariant invariant) {
    Utility.resetVariableMap();
    ErrorType error = null;
    error = DefinitionChecker.isInvariantDomainOK(invariant);
    if ((error != null)) {
      return error;
    }
    return null;
  }

  public static ErrorType checkError(final InvariantElement invariant_element) {
    ErrorType error = null;
    error = DefinitionChecker.isConstrainedElementOK(invariant_element);
    if ((error != null)) {
      return error;
    }
    return null;
  }

  public static ErrorType checkError(final TemporalProperty spec) {
    Utility.resetVariableMap();
    if ((spec instanceof CtlSpec)) {
      return DefinitionValidator.checkError(((CtlSpec)spec));
    } else {
      if ((spec instanceof LtlSpec)) {
        return DefinitionValidator.checkError(((LtlSpec)spec));
      } else {
        return null;
      }
    }
  }

  public static ErrorType checkError(final CtlSpec ctl_spec) {
    ErrorType error = null;
    error = DefinitionChecker.hasUniqueName(ctl_spec);
    if ((error != null)) {
      return error;
    }
    error = DefinitionChecker.isInvariantDomainOK(ctl_spec);
    if ((error != null)) {
      return error;
    }
    return null;
  }

  public static ErrorType checkError(final LtlSpec ltl_spec) {
    ErrorType error = null;
    error = DefinitionChecker.hasUniqueName(ltl_spec);
    if ((error != null)) {
      return error;
    }
    error = DefinitionChecker.isInvariantDomainOK(ltl_spec);
    if ((error != null)) {
      return error;
    }
    return null;
  }

  public static ErrorType checkError(final FairnessConstraint spec) {
    if ((spec instanceof JusticeConstraint)) {
      return DefinitionValidator.checkError(((JusticeConstraint)spec));
    } else {
      if ((spec instanceof CompassionConstraint)) {
        return DefinitionValidator.checkError(((CompassionConstraint)spec));
      } else {
        return null;
      }
    }
  }

  public static ErrorType checkError(final JusticeConstraint justice) {
    ErrorType error = null;
    error = DefinitionChecker.isInvariantDomainOK(justice);
    if ((error != null)) {
      return error;
    }
    return null;
  }

  public static ErrorType checkError(final CompassionConstraint compassion) {
    ErrorType error = null;
    error = DefinitionChecker.isInvariantDomainOK(compassion);
    if ((error != null)) {
      return error;
    }
    return null;
  }

  public static ErrorType checkError(final InvariantConstraint invar) {
    Utility.resetVariableMap();
    ErrorType error = null;
    error = DefinitionChecker.isInvariantDomainOK(invar);
    if ((error != null)) {
      return error;
    }
    return null;
  }
}
