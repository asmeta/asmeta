package org.asmeta.xt.validation.checkers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import org.asmeta.xt.asmetal.AsmetalPackage;
import org.asmeta.xt.asmetal.Body;
import org.asmeta.xt.asmetal.Domain;
import org.asmeta.xt.asmetal.Function;
import org.asmeta.xt.asmetal.LocationTerm;
import org.asmeta.xt.asmetal.Property;
import org.asmeta.xt.asmetal.Rule;
import org.asmeta.xt.asmetal.RuleDeclaration;
import org.asmeta.xt.asmetal.SetTerm;
import org.asmeta.xt.asmetal.StructuredTD;
import org.asmeta.xt.asmetal.TemporalProperty;
import org.asmeta.xt.asmetal.Term;
import org.asmeta.xt.asmetal.VariableTerm;
import org.asmeta.xt.validation.ErrorCode;
import org.asmeta.xt.validation.utility.BasicDomains;
import org.asmeta.xt.validation.utility.DomainCalculator;
import org.asmeta.xt.validation.utility.DomainTree;
import org.asmeta.xt.validation.utility.ErrorType;
import org.asmeta.xt.validation.utility.Utility;
import org.asmeta.xt.validation.utility.VariableKind;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

@SuppressWarnings("all")
public class SharedCheckers {
  public static ErrorType returnIsAnyDomainOK(final EStructuralFeature feature, final String code) {
    Domain dom = Utility.getDomain("Any");
    if ((dom == null)) {
      String message = "The AnyDomain is not defined";
      return new ErrorType(message, feature, code);
    } else {
      return null;
    }
  }

  public static ErrorType returnIsReserveDomainOK(final EStructuralFeature feature, final String code) {
    Domain dom = Utility.getDomain("Reserve");
    if ((dom == null)) {
      String message = "The domain Reserve has not been declared";
      return new ErrorType(message, feature, code);
    } else {
      return null;
    }
  }

  public static ErrorType returnHasUniqueNameProprierty(final TemporalProperty spec, final String name_of_data, final String code) {
    HashMap<String, String> names = new HashMap<String, String>();
    String _name = spec.getName();
    boolean _tripleEquals = (_name == null);
    if (_tripleEquals) {
      return null;
    }
    EStructuralFeature feature = AsmetalPackage.Literals.PROPERTY__NAME;
    String message = (("Two " + name_of_data) + " specifications can not have the same name.");
    EObject _eContainer = spec.eContainer();
    Body body = ((Body) _eContainer);
    EList<Property> _property = body.getProperty();
    for (final Property prop : _property) {
      if (((((prop.getName() != null) && names.containsKey(prop.getName())) && (!prop.equals(spec))) && Objects.equals(prop.getClass(), spec.getClass()))) {
        return new ErrorType(message, feature, code);
      } else {
        names.put(prop.getName(), prop.getName());
      }
    }
    return null;
  }

  public static ErrorType returnErrorNotBoolean(final Term body, final EStructuralFeature feature, final String name_of_data) {
    String message = (("The expression specifying " + name_of_data) + " must be a term whose associated type-domain is Boolean");
    String code = ErrorCode.CONSTRAINT_DOMAIN_BODY_NOT_BOOLEAN;
    boolean _isBoolean = Utility.isBoolean(body);
    boolean _not = (!_isBoolean);
    if (_not) {
      return new ErrorType(message, feature, code);
    } else {
      return null;
    }
  }

  public static ErrorType returnErrorGuardNotBoolean(final Term guard, final EStructuralFeature feature) {
    String message = "The type-domain associated to the guard term must be the boolean domain.";
    String code = ErrorCode.TERM__DOMAIN_GUARD_NOT_BOOLEAN;
    if (((guard != null) && (!Utility.isBoolean(guard)))) {
      return new ErrorType(message, feature, code);
    } else {
      return null;
    }
  }

  public static ErrorType returnErrorDomain(final Domain domain, final EStructuralFeature feature) {
    if ((domain instanceof StructuredTD)) {
      return null;
    }
    String name = Utility.calculateDomainCode(domain);
    return SharedCheckers.returnErrorDomainString(name, feature);
  }

  public static ErrorType returnErrorDomainString(final String name, final EStructuralFeature feature) {
    String asm_origin = Utility.getOriginalAsm(name);
    Domain dom = Utility.getDomain(name);
    if ((dom == null)) {
      for (final Domain d : BasicDomains.basic_domain_list) {
        boolean _equals = d.getName().equals(name);
        if (_equals) {
          dom = d;
          Utility.imported_all_domain_map.put(name, dom);
        }
      }
    }
    String message = null;
    String code = null;
    if ((dom == null)) {
      boolean _isDomainDeclaredPrivate = Utility.isDomainDeclaredPrivate(name);
      if (_isDomainDeclaredPrivate) {
        message = (((("The domain \'" + name) + "\' is not exported by the ASM \'") + asm_origin) + "\'. It cannot be imported by this ASM");
        code = ErrorCode.SIGNATURE__DOMAIN_NOT_IMPORTED;
      } else {
        message = (("\'" + name) + "\' domain is not declared");
        code = ErrorCode.SIGNATURE__DOMAIN_NOT_FOUND;
      }
      return new ErrorType(message, feature, code);
    }
    return null;
  }

  public static ErrorType returnErrorFunctionString(final String name, final EStructuralFeature feature) {
    Function func = Utility.getFunction(name);
    if ((func == null)) {
      func = Utility.getFunctionByName(name);
    }
    String message = null;
    String code = null;
    if ((func == null)) {
      boolean _isFunctionDeclaredPrivate = Utility.isFunctionDeclaredPrivate(name);
      if (_isFunctionDeclaredPrivate) {
        String asm_origin = Utility.getOriginalAsm(name);
        message = (((("The function \'" + name) + "\' is not exported by the ASM \'") + asm_origin) + "\'. It cannot be imported by this ASM");
        code = ErrorCode.SIGNATURE__FUNCTION_NOT_IMPORTED;
      } else {
        message = (("\'" + name) + "\' function is not declared");
        code = ErrorCode.SIGNATURE__FUNCTION_NOT_FOUND;
      }
      return new ErrorType(message, feature, code);
    }
    return null;
  }

  public static ErrorType returnErrorRuleString(final String name, final EStructuralFeature feature) {
    String asm_origin = Utility.getOriginalAsm(name);
    ArrayList<RuleDeclaration> func = Utility.getRule(name);
    String message = null;
    String code = null;
    if ((func == null)) {
      boolean _isFunctionDeclaredPrivate = Utility.isFunctionDeclaredPrivate(name);
      if (_isFunctionDeclaredPrivate) {
        message = (((("The rule \'" + name) + "\' is not exported by the ASM \'") + asm_origin) + "\'. It cannot be imported by this ASM");
        code = ErrorCode.BODY__RULE_NOT_IMPORTED;
      } else {
        message = (("\'" + name) + "\' rule is not declared");
        code = ErrorCode.BODY__RULE_NOT_FOUND;
      }
      return new ErrorType(message, feature, code);
    }
    return null;
  }

  public static ErrorType returnErrorVariableDeclared(final String term_name, final EStructuralFeature feature, final String code, final List<VariableTerm> list, final EObject parent) {
    String message = null;
    boolean errorOK = false;
    String error_var = "";
    for (final VariableTerm v : list) {
      if ((!errorOK)) {
        boolean _isVariableDeclaredOnceByMe = Utility.isVariableDeclaredOnceByMe(v.getName(), parent);
        boolean _not = (!_isVariableDeclaredOnceByMe);
        if (_not) {
          error_var = v.getName();
          if ((parent instanceof Rule)) {
            v.setKind(VariableKind.RULE_VAR);
          } else {
            if ((parent instanceof LocationTerm)) {
              v.setKind(VariableKind.LOCATION_VAR);
            } else {
              v.setKind(VariableKind.LOGICAL_VAR);
            }
          }
          errorOK = true;
        }
      }
    }
    if (errorOK) {
      message = (((("The variable \"" + error_var) + "\" cannot be bound to a value in the \"") + term_name) + "\". It is already used.");
      return new ErrorType(message, feature, code);
    }
    return null;
  }

  public static ErrorType returnErrorPowersetRanges(final List<Term> ranges_list, final List<VariableTerm> variables_list, final EStructuralFeature feature, final String code1, final String code2) {
    String message = null;
    DomainTree term_dom = null;
    DomainTree var_dom = null;
    Term range = null;
    VariableTerm variable = null;
    for (int i = 0; (i < ranges_list.size()); i++) {
      {
        range = ranges_list.get(i);
        variable = variables_list.get(i);
        if ((((range instanceof SetTerm) && (((SetTerm) range).getStart_term() != null)) || (!(range instanceof SetTerm)))) {
          boolean _isPowerSetDomain = Utility.isPowerSetDomain(range);
          boolean _not = (!_isPowerSetDomain);
          if (_not) {
            message = "The domain of the variables ranges must be a PowersetDomain";
            return new ErrorType(message, feature, code1);
          }
          term_dom = DomainCalculator.getDomainTerm(range);
          var_dom = DomainCalculator.getDomainTerm(variable);
          boolean _isCompatible = Utility.isCompatible(term_dom, var_dom);
          boolean _not_1 = (!_isCompatible);
          if (_not_1) {
            message = " The domain of the variables ranges must be a PowersetDomain whose base domain must be equal to the variable type domain";
            return new ErrorType(message, feature, code2);
          }
        }
      }
    }
    return null;
  }
}
