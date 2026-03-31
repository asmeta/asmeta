package org.asmeta.xt.validation.checkers;

import java.util.HashMap;
import org.asmeta.xt.asmetal.AsmetalPackage;
import org.asmeta.xt.asmetal.BagDomain;
import org.asmeta.xt.asmetal.BasicTD;
import org.asmeta.xt.asmetal.Body;
import org.asmeta.xt.asmetal.CompassionConstraint;
import org.asmeta.xt.asmetal.ConcreteDomain;
import org.asmeta.xt.asmetal.CtlSpec;
import org.asmeta.xt.asmetal.Domain;
import org.asmeta.xt.asmetal.EnumElement;
import org.asmeta.xt.asmetal.EnumTD;
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
import org.asmeta.xt.asmetal.StructuredTD;
import org.asmeta.xt.asmetal.TypeDomain;
import org.asmeta.xt.validation.ErrorCode;
import org.asmeta.xt.validation.utility.ErrorType;
import org.asmeta.xt.validation.utility.Utility;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

@SuppressWarnings("all")
public class DefinitionChecker {
  public static ErrorType isDeclaredOnlyOnce(final Domain domain) {
    String message = null;
    String code = null;
    EStructuralFeature feature = AsmetalPackage.Literals.DOMAIN__NAME;
    EObject _eContainer = domain.eContainer();
    if ((_eContainer instanceof Signature)) {
      HashMap<String, String> names = new HashMap<String, String>();
      String name = null;
      EObject _eContainer_1 = domain.eContainer();
      Signature signature = ((Signature) _eContainer_1);
      EList<Domain> _domain = signature.getDomain();
      for (final Domain dom : _domain) {
        {
          name = dom.getName();
          if ((names.containsKey(name) && domain.equals(dom))) {
            message = (("Domain \'" + name) + "\' already declared");
            code = ErrorCode.SIGNATURE__DOMAIN_DEFINED_TWICE;
            return new ErrorType(message, feature, code);
          } else {
            names.put(name, dom.getName());
          }
        }
      }
    }
    return null;
  }

  public static ErrorType isDeclaredOnlyOnce(final Function function) {
    String message = null;
    String code = null;
    EStructuralFeature feature = AsmetalPackage.Literals.FUNCTION__NAME;
    EObject _eContainer = function.eContainer();
    if ((_eContainer instanceof Signature)) {
      HashMap<String, String> names = new HashMap<String, String>();
      String name = null;
      EObject _eContainer_1 = function.eContainer();
      Signature signature = ((Signature) _eContainer_1);
      EList<Function> _function = signature.getFunction();
      for (final Function func : _function) {
        {
          name = Utility.calculateFunctionCode(func);
          if ((names.containsKey(name) && function.equals(func))) {
            message = (("Function \'" + name) + "\' already declared");
            code = ErrorCode.SIGNATURE__FUNCTION_DEFINED_TWICE;
            return new ErrorType(message, feature, code);
          } else {
            names.put(name, func.getName());
          }
        }
      }
    }
    return null;
  }

  public static ErrorType isFunctionDomainOK(final Function function) {
    Domain _domain = function.getDomain();
    boolean _tripleEquals = (_domain == null);
    if (_tripleEquals) {
      return null;
    }
    return SharedCheckers.returnErrorDomain(function.getDomain(), AsmetalPackage.Literals.FUNCTION__DOMAIN);
  }

  public static ErrorType isFunctionCodomainOK(final Function function) {
    return SharedCheckers.returnErrorDomain(function.getCodomain(), AsmetalPackage.Literals.FUNCTION__CODOMAIN);
  }

  public static ErrorType isTypeDomainOK(final ConcreteDomain domain) {
    String message = null;
    String code = null;
    EStructuralFeature feature = AsmetalPackage.Literals.CONCRETE_DOMAIN__TYPE_DOMAIN;
    Domain _typeDomain = domain.getTypeDomain();
    if ((_typeDomain instanceof StructuredTD)) {
      return null;
    }
    ErrorType error = SharedCheckers.returnErrorDomain(domain.getTypeDomain(), feature);
    if ((error != null)) {
      return error;
    }
    String name = Utility.calculateDomainCode(domain.getTypeDomain());
    Domain dom = Utility.getDomain(name);
    if ((!(dom instanceof TypeDomain))) {
      message = "A concrete domain cannot be defined over a concrete domain";
      code = ErrorCode.CONCRETE_DOMAIN__INVALID_TYPE_DOMAIN;
      return new ErrorType(message, feature, code);
    }
    return null;
  }

  public static ErrorType isDomainNameOK(final BasicTD domain) {
    String _name = domain.getName();
    String _plus = ("A basic domain " + _name);
    String message = (_plus + " not allowed has been declared.");
    String code = ErrorCode.BASIC_DOMAIN__INVALID_NAME;
    EStructuralFeature feature = AsmetalPackage.Literals.DOMAIN__NAME;
    String[] allowed_name = { "Integer", "Natural", "Real", "Complex", "String", "Char", "Boolean", "Undef" };
    boolean okName = false;
    for (int i = 0; (i < allowed_name.length); i++) {
      if (((!okName) && domain.getName().equals(allowed_name[i]))) {
        okName = true;
      }
    }
    if ((!okName)) {
      return new ErrorType(message, feature, code);
    } else {
      return null;
    }
  }

  public static ErrorType isCoreOK(final BagDomain domain) {
    return SharedCheckers.returnErrorDomain(domain.getDomain(), AsmetalPackage.Literals.BAG_DOMAIN__DOMAIN);
  }

  public static ErrorType isCoreOK(final PowersetDomain domain) {
    return SharedCheckers.returnErrorDomain(domain.getBaseDomain(), AsmetalPackage.Literals.POWERSET_DOMAIN__BASE_DOMAIN);
  }

  public static ErrorType isCoreOK(final SequenceDomain domain) {
    return SharedCheckers.returnErrorDomain(domain.getDomain(), AsmetalPackage.Literals.SEQUENCE_DOMAIN__DOMAIN);
  }

  public static ErrorType isCoreOK(final MapDomain domain) {
    ErrorType error = SharedCheckers.returnErrorDomain(domain.getSourceDomain(), AsmetalPackage.Literals.MAP_DOMAIN__SOURCE_DOMAIN);
    if ((error != null)) {
      return error;
    }
    return SharedCheckers.returnErrorDomain(domain.getTargetDomain(), AsmetalPackage.Literals.MAP_DOMAIN__TARGET_DOMAIN);
  }

  public static ErrorType isCoreOK(final ProductDomain domain) {
    ErrorType error = null;
    EList<Domain> _domains = domain.getDomains();
    for (final Domain dom : _domains) {
      {
        error = SharedCheckers.returnErrorDomain(dom, AsmetalPackage.Literals.DOMAIN__NAME);
        if ((error != null)) {
          return error;
        }
      }
    }
    return null;
  }

  public static ErrorType isCoreOK(final RuleDomain domain) {
    ErrorType error = null;
    EList<Domain> _domains = domain.getDomains();
    for (final Domain dom : _domains) {
      {
        error = SharedCheckers.returnErrorDomain(dom, AsmetalPackage.Literals.DOMAIN__NAME);
        if ((error != null)) {
          return error;
        }
      }
    }
    return null;
  }

  public static ErrorType isParentOK(final LocalFunction function) {
    ErrorType _xblockexpression = null;
    {
      String message = "An ASM signature can\'t declare local functions";
      String code = ErrorCode.LOCAL_FUNCTION__INVALID_DECLARATION;
      EStructuralFeature feature = AsmetalPackage.Literals.FUNCTION__NAME;
      ErrorType _xifexpression = null;
      EObject _eContainer = function.eContainer();
      if ((_eContainer instanceof Signature)) {
        _xifexpression = new ErrorType(message, feature, code);
      } else {
        return null;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }

  public static ErrorType isDeclaredOnlyOnce(final EnumElement element) {
    String message = null;
    String code = ErrorCode.ENUM_DOMAIN_ALREADY_DECLARED;
    EStructuralFeature feature = AsmetalPackage.Literals.ENUM_ELEMENT__SYMBOL;
    HashMap<String, String> names = new HashMap<String, String>();
    EObject _eContainer = element.eContainer();
    EnumTD domain = ((EnumTD) _eContainer);
    EObject _eContainer_1 = domain.eContainer();
    Signature signature = ((Signature) _eContainer_1);
    EList<Domain> _domain = signature.getDomain();
    for (final Domain dom : _domain) {
      if ((dom instanceof EnumTD)) {
        EList<EnumElement> _element = ((EnumTD)dom).getElement();
        for (final EnumElement el : _element) {
          if ((names.containsKey(el.getSymbol()) && element.equals(el))) {
            String _symbol = el.getSymbol();
            String _plus = ("Element \'" + _symbol);
            String _plus_1 = (_plus + "\' already declared");
            message = _plus_1;
            return new ErrorType(message, feature, code);
          } else {
            names.put(el.getSymbol(), el.getSymbol());
          }
        }
      }
    }
    return null;
  }

  /**
   * Check if the rule is declared multiple times
   * <ul>
   * 	<li>Check rule name</li>
   * 	<li>Check partial overload (only the number of the arguments of the rule)</li>
   * </ul>
   */
  public static ErrorType isDefineOnceOK(final RuleDeclaration rule_declaration) {
    EObject _eContainer = rule_declaration.eContainer();
    if ((_eContainer instanceof Body)) {
      String rule_string = Utility.getRuleaAsString(rule_declaration);
      String message = (("The rule " + rule_string) + " has been declared twice.");
      String code = ErrorCode.RULE_DECLARATION__DEFINED_TWICE;
      EStructuralFeature feature = AsmetalPackage.Literals.RULE_DECLARATION__NAME;
      EObject _eContainer_1 = rule_declaration.eContainer();
      Body body = ((Body) _eContainer_1);
      HashMap<String, String> rules_string = new HashMap<String, String>();
      EList<RuleDeclaration> _ruleDeclaration = body.getRuleDeclaration();
      for (final RuleDeclaration rule : _ruleDeclaration) {
        {
          String r_string = Utility.getRuleaAsString(rule);
          boolean _containsKey = rules_string.containsKey(r_string);
          if (_containsKey) {
            return new ErrorType(message, feature, code);
          } else {
            rules_string.put(r_string, r_string);
          }
        }
      }
    }
    return null;
  }

  public static ErrorType isVariableListOK(final RuleDeclaration rule_declaration) {
    String message = null;
    String code = ErrorCode.RULE_DECLARATION__INVALID_VARIABLE;
    EStructuralFeature feature = AsmetalPackage.Literals.RULE_DECLARATION__NAME;
    HashMap<String, String> names = new HashMap<String, String>();
    EList<String> _variables = rule_declaration.getVariables();
    for (final String variable : _variables) {
      boolean _containsKey = names.containsKey(variable);
      if (_containsKey) {
        message = (("The variable " + variable) + " cannot be used as parameter. It is already used.");
        return new ErrorType(message, feature, code);
      } else {
        names.put(variable, variable);
      }
    }
    return null;
  }

  public static ErrorType isDomainListOK(final RuleDeclaration rule_declaration) {
    EStructuralFeature feature = AsmetalPackage.Literals.RULE_DECLARATION__NAME;
    ErrorType error = null;
    EList<Domain> _domain = rule_declaration.getDomain();
    for (final Domain domain : _domain) {
      {
        error = SharedCheckers.returnErrorDomain(domain, feature);
        if ((error != null)) {
          return error;
        }
      }
    }
    return null;
  }

  public static ErrorType isConstrainedElementOK(final InvariantElement invariant_element) {
    EStructuralFeature feature = null;
    String code = null;
    String message = null;
    String name = "";
    if (((invariant_element.getDomainList() == null) || (invariant_element.getDomainList().size() == 0))) {
      name = invariant_element.getConstrainedName();
    } else {
      String _constrainedName = invariant_element.getConstrainedName();
      String _plus = (_constrainedName + 
        "(");
      name = _plus;
    }
    String _calculateDomainCodeFromList = Utility.calculateDomainCodeFromList(invariant_element.getDomainList());
    /* (_calculateDomainCodeFromList + ")"); */
    boolean _isInMaps = Utility.isInMaps(name);
    boolean _not = (!_isInMaps);
    if (_not) {
      feature = AsmetalPackage.Literals.INVARIANT_ELEMENT__CONSTRAINED_NAME;
      String _constrainedName_1 = invariant_element.getConstrainedName();
      String _plus_1 = ("Could\'t find \'" + _constrainedName_1);
      String _plus_2 = (_plus_1 + "\'");
      message = _plus_2;
      code = ErrorCode.INVARINT__NOT_FOUND;
      return new ErrorType(message, feature, code);
    }
    EObject _eContainer = invariant_element.eContainer();
    Invariant parent = ((Invariant) _eContainer);
    HashMap<String, String> names = new HashMap<String, String>();
    EList<InvariantElement> _invar_list = parent.getInvar_list();
    for (final InvariantElement el : _invar_list) {
      if ((names.containsKey(el.getConstrainedName()) && el.getConstrainedName().equals(invariant_element.getConstrainedName()))) {
        feature = AsmetalPackage.Literals.INVARIANT_ELEMENT__CONSTRAINED_NAME;
        String _constrainedName_2 = invariant_element.getConstrainedName();
        String _plus_3 = ("\'" + _constrainedName_2);
        String _plus_4 = (_plus_3 + "\' called twice");
        message = _plus_4;
        code = ErrorCode.INVARINT__CALLED_TWICE;
        return new ErrorType(message, feature, code);
      } else {
        names.put(el.getConstrainedName(), el.getConstrainedName());
      }
    }
    return null;
  }

  public static ErrorType hasUniqueName(final CtlSpec spec) {
    String code = ErrorCode.CTL_SPEC__DUPLICATE_NAME;
    return SharedCheckers.returnHasUniqueNameProprierty(spec, "CTL", code);
  }

  public static ErrorType hasUniqueName(final LtlSpec spec) {
    String code = ErrorCode.LTL_SPEC__DUPLICATE_NAME;
    return SharedCheckers.returnHasUniqueNameProprierty(spec, "LTL", code);
  }

  public static ErrorType isInvariantDomainOK(final Invariant inv) {
    EStructuralFeature feature = AsmetalPackage.Literals.PROPERTY__NAME;
    return SharedCheckers.returnErrorNotBoolean(inv.getBody(), feature, "an invariant");
  }

  public static ErrorType isInvariantDomainOK(final CtlSpec spec) {
    EStructuralFeature feature = AsmetalPackage.Literals.PROPERTY__NAME;
    return SharedCheckers.returnErrorNotBoolean(spec.getBody(), feature, "a CTL property");
  }

  public static ErrorType isInvariantDomainOK(final LtlSpec spec) {
    EStructuralFeature feature = AsmetalPackage.Literals.PROPERTY__NAME;
    return SharedCheckers.returnErrorNotBoolean(spec.getBody(), feature, "a LTL property");
  }

  public static ErrorType isInvariantDomainOK(final JusticeConstraint justice) {
    EStructuralFeature feature = AsmetalPackage.Literals.JUSTICE_CONSTRAINT__BODY;
    return SharedCheckers.returnErrorNotBoolean(justice.getBody(), feature, "a JUSTICE constraint");
  }

  public static ErrorType isInvariantDomainOK(final CompassionConstraint inv) {
    EStructuralFeature feature = null;
    String part_msg = "a COMPASSION constraint";
    feature = AsmetalPackage.Literals.COMPASSION_CONSTRAINT__P;
    ErrorType error = SharedCheckers.returnErrorNotBoolean(inv.getP(), feature, part_msg);
    if ((error != null)) {
      return error;
    }
    feature = AsmetalPackage.Literals.COMPASSION_CONSTRAINT__Q;
    return SharedCheckers.returnErrorNotBoolean(inv.getQ(), feature, part_msg);
  }

  public static ErrorType isInvariantDomainOK(final InvariantConstraint inv) {
    EStructuralFeature feature = AsmetalPackage.Literals.INVARIANT_CONSTRAINT__BODY;
    return SharedCheckers.returnErrorNotBoolean(inv.getBody(), feature, "an INVAR constraint");
  }
}
