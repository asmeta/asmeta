package org.asmeta.xt.validation.checkers;

import java.util.ArrayList;
import java.util.HashMap;
import org.asmeta.xt.asmetal.AgentInitialization;
import org.asmeta.xt.asmetal.Asm;
import org.asmeta.xt.asmetal.AsmetalPackage;
import org.asmeta.xt.asmetal.Body;
import org.asmeta.xt.asmetal.ConcreteDomain;
import org.asmeta.xt.asmetal.DerivedFunction;
import org.asmeta.xt.asmetal.Domain;
import org.asmeta.xt.asmetal.DomainDefinition;
import org.asmeta.xt.asmetal.DomainInitialization;
import org.asmeta.xt.asmetal.DynamicFunction;
import org.asmeta.xt.asmetal.ExportClause;
import org.asmeta.xt.asmetal.Function;
import org.asmeta.xt.asmetal.FunctionDefinition;
import org.asmeta.xt.asmetal.FunctionInitialization;
import org.asmeta.xt.asmetal.ImportClause;
import org.asmeta.xt.asmetal.Initialization;
import org.asmeta.xt.asmetal.RuleDeclaration;
import org.asmeta.xt.asmetal.SetTerm;
import org.asmeta.xt.asmetal.Signature;
import org.asmeta.xt.asmetal.StaticFunction;
import org.asmeta.xt.asmetal.Term;
import org.asmeta.xt.asmetal.importData;
import org.asmeta.xt.validation.ErrorCode;
import org.asmeta.xt.validation.utility.DomainCalculator;
import org.asmeta.xt.validation.utility.DomainTree;
import org.asmeta.xt.validation.utility.ErrorType;
import org.asmeta.xt.validation.utility.MessageType;
import org.asmeta.xt.validation.utility.Utility;
import org.asmeta.xt.validation.utility.WarningType;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

@SuppressWarnings("all")
public class StructureChecker {
  /**
   * constraint: asm.name == file name
   * example:
   * 		asm ATMcheck
   * 		signature
   * 		...
   * the name of the file must be ATMcheck.asm
   */
  public static ErrorType isAsmNameOK(final Asm asm) {
    String path = asm.eResource().getURI().toFileString();
    if ((path == null)) {
      return null;
    }
    boolean _contains = path.contains("\\");
    if (_contains) {
      path = path.replace("\\", "/");
    }
    String code = ErrorCode.ASM__INVALID_NAME;
    EStructuralFeature feature = AsmetalPackage.Literals.ASM__NAME;
    String[] path_split = path.split("/");
    int _length = path_split.length;
    int _minus = (_length - 1);
    String file_name = path_split[_minus];
    int _length_1 = file_name.length();
    int _minus_1 = (_length_1 - 4);
    String name_no_extension = file_name.substring(0, _minus_1);
    String _name = asm.getName();
    String _plus = ((("The file name \"" + name_no_extension) + "\" is not equal to the asm name \"") + _name);
    String message = (_plus + "\"");
    boolean _equals = name_no_extension.equals(asm.getName());
    boolean _not = (!_equals);
    if (_not) {
      return new ErrorType(message, feature, code);
    } else {
      return null;
    }
  }

  /**
   * OCL constraint A1
   *  constraint: mainrule->notEmpty() implies mainrule.arity=0
   */
  public static ErrorType isMainRuleArityOK(final Asm asm) {
    String message = "The arity of the main rule must be 0";
    String code = ErrorCode.ASM__INVALID_MAINRULE_ARITY;
    EStructuralFeature feature = AsmetalPackage.Literals.ASM__MAINRULE;
    Integer _calculateRuleArity = Utility.calculateRuleArity(asm.getMainrule());
    boolean _notEquals = ((_calculateRuleArity).intValue() != 0);
    if (_notEquals) {
      return new ErrorType(message, feature, code);
    } else {
      return null;
    }
  }

  /**
   * If the asm is a module, it should't have a main rule
   */
  public static WarningType isModuleMainRuleOK(final Asm asm) {
    WarningType _xblockexpression = null;
    {
      String _name = asm.getName();
      String _plus = ("\"" + _name);
      String message = (_plus + "\" is a module. It cannot have a Main Rule");
      String code = ErrorCode.ASM__MODULE_MAINRULE;
      EStructuralFeature feature = AsmetalPackage.Literals.ASM__MAINRULE;
      boolean _equals = asm.getType().equals("asm");
      if (_equals) {
        return null;
      }
      WarningType _xifexpression = null;
      if ((asm.getType().equals("module") && (asm.getMainrule() != null))) {
        _xifexpression = new WarningType(message, feature, code);
      } else {
        return null;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }

  /**
   * If the asm is a module, it should't have a default initial state
   */
  public static WarningType isModuleDefaultInitialStateOK(final Asm asm) {
    WarningType _xblockexpression = null;
    {
      String _name = asm.getName();
      String _plus = ("\"" + _name);
      String message = (_plus + "\" is a module. It cannot have any initial state");
      String code = ErrorCode.ASM__MODULE_DEF_INITIAL_STATE;
      EStructuralFeature feature = AsmetalPackage.Literals.ASM__DEFAULT_INITIAL_STATE;
      boolean _equals = asm.getType().equals("asm");
      if (_equals) {
        return null;
      }
      WarningType _xifexpression = null;
      if ((asm.getType().equals("module") && (asm.getDefaultInitialState() != null))) {
        _xifexpression = new WarningType(message, feature, code);
      } else {
        return null;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }

  /**
   * If the asm is a module, it should't have any initial state
   */
  public static WarningType isModuleInitialStateOK(final Asm asm) {
    WarningType _xblockexpression = null;
    {
      String _name = asm.getName();
      String _plus = ("\"" + _name);
      String message = (_plus + "\" is a module. It cannot have any initial state");
      String code = ErrorCode.ASM__MODULE_INITIAL_STATE;
      EStructuralFeature feature = AsmetalPackage.Literals.ASM__INITIAL_STATE;
      boolean _equals = asm.getType().equals("asm");
      if (_equals) {
        return null;
      }
      WarningType _xifexpression = null;
      if ((asm.getType().equals("module") && (asm.getInitialState().size() != 0))) {
        _xifexpression = new WarningType(message, feature, code);
      } else {
        return null;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }

  /**
   * Check if the imported file exist
   */
  public static ErrorType isImportedClauseModuleNameOK(final ImportClause importClause) {
    String dir = Utility.getBaseDir(importClause);
    String absolutePath = Utility.getAbsoluteAddressAsm(importClause.getModuleName(), dir);
    if ((absolutePath == null)) {
      String _moduleName = importClause.getModuleName();
      String _plus = ("File " + _moduleName);
      String _plus_1 = (_plus + " not found in ");
      String message = (_plus_1 + dir);
      String code = ErrorCode.IMPORT_CLAUSE__FILE_NOT_FOUND;
      EStructuralFeature feature = AsmetalPackage.Literals.IMPORT_CLAUSE__MODULE_NAME;
      return new ErrorType(message, feature, code);
    } else {
      return null;
    }
  }

  public static ErrorType isImportedListOK(final ImportClause importClause) {
    String message = null;
    String code = null;
    EStructuralFeature feature = null;
    if (((importClause.getImportedList() != null) || (importClause.getImportedList().size() > 0))) {
      feature = AsmetalPackage.Literals.IMPORT_CLAUSE__MODULE_NAME;
      HashMap<String, String> names = new HashMap<String, String>();
      String s = null;
      EList<importData> _importedList = importClause.getImportedList();
      for (final importData data : _importedList) {
        {
          s = data.getName();
          boolean _containsKey = names.containsKey(s);
          if (_containsKey) {
            message = (("Found duplicate value \'" + s) + "\'");
            code = ErrorCode.IMPORT_CLAUSE__CALLED_TWICE;
            return new ErrorType(message, feature, code, data);
          } else {
            names.put(s, s);
          }
          boolean _isInImportedMap = Utility.isInImportedMap(s);
          boolean _not = (!_isInImportedMap);
          if (_not) {
            message = (("Could\'t find \'" + s) + "\'");
            code = ErrorCode.IMPORT_CLAUSE__NOT_FOUND;
            return new ErrorType(message, feature, code, data);
          }
        }
      }
    }
    return null;
  }

  public static WarningType haveSomethingToImport(final ImportClause importClause) {
    String dir = Utility.getBaseDir(importClause);
    Asm asm = Utility.getImportedAsm(importClause.getModuleName(), dir);
    String _name = asm.getName();
    String _plus = ("The ASM \'" + _name);
    String message = (_plus + 
      "\' does not export any function or rule. It cannot be imported by this ASM");
    String code = ErrorCode.IMPORT_CLAUSE__NOTHING_TO_IMPORT;
    EStructuralFeature feature = AsmetalPackage.Literals.IMPORT_CLAUSE__MODULE_NAME;
    ExportClause _exportClause = asm.getHeaderSection().getExportClause();
    boolean _tripleEquals = (_exportClause == null);
    if (_tripleEquals) {
      return new WarningType(message, feature, code);
    }
    boolean _isExportAll = asm.getHeaderSection().getExportClause().isExportAll();
    if (_isExportAll) {
      return null;
    }
    int _size = asm.getHeaderSection().getExportClause().getExportedList().size();
    boolean _tripleEquals_1 = (_size == 0);
    if (_tripleEquals_1) {
      return new WarningType(message, feature, code);
    }
    Utility.fillImportedMap(asm.getHeaderSection(), importClause);
    Utility.fillAllImportedMap(asm.getHeaderSection(), Boolean.valueOf(false));
    EList<String> _exportedList = asm.getHeaderSection().getExportClause().getExportedList();
    for (final String s : _exportedList) {
      if (((Utility.isFunctionName(s) || Utility.isRule(s)) || Utility.isDomainName(s))) {
        return null;
      }
    }
    return new WarningType(message, feature, code);
  }

  public static MessageType isExportedListOK(final ExportClause exportClause, final Signature signature, final Body body) {
    String message = null;
    String code = null;
    EStructuralFeature feature = null;
    feature = AsmetalPackage.Literals.EXPORT_CLAUSE__EXPORTED_LIST;
    Utility.fillSignatureMap(signature, Boolean.valueOf(false));
    Utility.fillBodyMap(body);
    boolean okExport = false;
    boolean _isExportAll = exportClause.isExportAll();
    if (_isExportAll) {
      okExport = ((((signature.getFunction() != null) && (signature.getFunction().size() > 0)) || ((body.getRuleDeclaration() != null) && (body.getRuleDeclaration().size() > 0))) || ((signature.getDomain() != null) && (signature.getDomain().size() > 0)));
      if (((!okExport) && (body.getAsm().getMainrule() != null))) {
        message = "Exporting * from a spec with only a main ";
        code = "";
        MessageType m = new WarningType(message, feature, code);
        return new WarningType(message, feature, code);
      }
    } else {
      HashMap<String, String> names = new HashMap<String, String>();
      EList<String> _exportedList = exportClause.getExportedList();
      for (final String s : _exportedList) {
        {
          boolean _containsKey = names.containsKey(s);
          if (_containsKey) {
            message = (("Found duplicate value \'" + s) + "\'");
            code = ErrorCode.EXPORT_CLAUSE__CALLED_TWICE;
            return new ErrorType(message, feature, code);
          } else {
            names.put(s, s);
          }
          boolean _isInSignatureMap = Utility.isInSignatureMap(s);
          boolean _not = (!_isInSignatureMap);
          if (_not) {
            message = (("Could\'t find \'" + s) + "\'");
            code = ErrorCode.EXPORT_CLAUSE__NOT_FOUND;
            return new ErrorType(message, feature, code);
          }
          if (((!okExport) && ((Utility.isFunctionName(s) || Utility.isRule(s)) || Utility.isDomainName(s)))) {
            okExport = true;
          }
        }
      }
    }
    if ((!okExport)) {
      message = "Error: An export clause must contain at least one Domain or one function or one rule";
      code = ErrorCode.EXPORT_CLAUSE__NOTHING_TO_EXPORT;
      return new ErrorType(message, feature, code);
    }
    return null;
  }

  public static ErrorType isDeclaredDomainOK(final String domain_name) {
    ErrorType error = null;
    String message = null;
    String code = null;
    EAttribute feature = AsmetalPackage.Literals.DOMAIN_DEFINITION__DEFINED_DOMAIN_NAME;
    error = SharedCheckers.returnErrorDomainString(domain_name, feature);
    if ((error != null)) {
      return error;
    }
    Domain dom = Utility.getDomain(domain_name);
    if ((!(dom instanceof ConcreteDomain))) {
      message = (("The domain \'" + domain_name) + "\' is not a concrete-domain. It cannot be defined.");
      code = ErrorCode.DOMAIN_DEFINITION__DOMAIN_NOT_CONCRETE;
      return new ErrorType(message, feature, code);
    }
    ConcreteDomain concrete_domain = ((ConcreteDomain) dom);
    boolean _isDynamic = concrete_domain.isDynamic();
    if (_isDynamic) {
      message = "Dynamic domains must be initialized, not defined";
      code = ErrorCode.DOMAIN_DEFINITION__IS_DYNAMIC_DOMAIN;
      return new ErrorType(message, feature, code);
    }
    return null;
  }

  public static ErrorType isFunctionListOK(final Signature signature) {
    String message = null;
    String code = null;
    EStructuralFeature feature = AsmetalPackage.Literals.SIGNATURE__FUNCTION;
    HashMap<String, String> names = new HashMap<String, String>();
    int i = 0;
    String name = null;
    Utility.fillOnlyDomainMap(signature);
    EList<Function> _function = signature.getFunction();
    for (final Function function : _function) {
      {
        name = Utility.calculateFunctionCode(function);
        boolean _containsKey = names.containsKey(name);
        if (_containsKey) {
          message = (("Function \'" + name) + "\' already declared");
          code = ErrorCode.SIGNATURE__FUNCTION_DEFINED_TWICE;
          return new ErrorType(message, feature, code, function);
        } else {
          names.put(name, function.getName());
        }
        i++;
      }
    }
    return null;
  }

  public static ErrorType isDefinedOnceOK(final DomainDefinition domain_definition) {
    String _definedDomainName = domain_definition.getDefinedDomainName();
    String _plus = ("The domain \'" + _definedDomainName);
    String message = (_plus + "\' has been defined twice");
    String code = ErrorCode.DOMAIN_DEFINITION__DEFINED_TWICE;
    EStructuralFeature feature = AsmetalPackage.Literals.DOMAIN_DEFINITION__DEFINED_DOMAIN_NAME;
    EObject _eContainer = domain_definition.eContainer();
    Body body = ((Body) _eContainer);
    HashMap<String, String> names = new HashMap<String, String>();
    EList<DomainDefinition> _domainDefinition = body.getDomainDefinition();
    for (final DomainDefinition definitions : _domainDefinition) {
      if ((names.containsKey(definitions.getDefinedDomainName()) && 
        definitions.getDefinedDomainName().equals(domain_definition.getDefinedDomainName()))) {
        return new ErrorType(message, feature, code);
      } else {
        names.put(definitions.getDefinedDomainName(), definitions.getDefinedDomainName());
      }
    }
    return null;
  }

  public static ErrorType isBodyOK(final DomainDefinition domain_definition) {
    Term body = domain_definition.getBody();
    EStructuralFeature feature = AsmetalPackage.Literals.DOMAIN_DEFINITION__BODY;
    String message = null;
    String code = ErrorCode.DOMAIN_DEFINITION__ILL_FORMED_BODY;
    if ((!(body instanceof SetTerm))) {
      message = "The type-domain of the body of the domain definition must be a powerset";
      return new ErrorType(message, feature, code);
    }
    SetTerm set_term_body = ((SetTerm) body);
    Term _start_term = set_term_body.getStart_term();
    boolean _tripleEquals = (_start_term == null);
    if (_tripleEquals) {
      return null;
    }
    DomainTree body_domain = DomainCalculator.getDomainTerm(body);
    Domain dom = Utility.getDomain(domain_definition.getDefinedDomainName());
    boolean _isCompatible = Utility.isCompatible(body_domain, DomainTree.getTreeFromDomain(dom));
    boolean _not = (!_isCompatible);
    if (_not) {
      message = "The type-domain of the body of the domain definition is different from the domain";
      return new ErrorType(message, feature, code);
    }
    return null;
  }

  public static ErrorType isDefinedOnceOK(final FunctionDefinition function_definition) {
    String _definedFunctionName = function_definition.getDefinedFunctionName();
    String _plus = ("The function \'" + _definedFunctionName);
    String message = (_plus + "\' has been defined twice");
    String code = ErrorCode.FUNCTION_DEFINITION__DEFINED_TWICE;
    EStructuralFeature feature = AsmetalPackage.Literals.FUNCTION_DEFINITION__DEFINED_FUNCTION_NAME;
    EObject _eContainer = function_definition.eContainer();
    Body body = ((Body) _eContainer);
    HashMap<String, String> names = new HashMap<String, String>();
    ArrayList<FunctionDefinition> conflicts = new ArrayList<FunctionDefinition>();
    String body_function_code = "";
    String function_code = function_definition.getDefinedFunctionName();
    EList<FunctionDefinition> _functionDefinition = body.getFunctionDefinition();
    for (final FunctionDefinition definitions : _functionDefinition) {
      {
        body_function_code = definitions.getDefinedFunctionName();
        Utility.fillFunctionDefinitionMap(definitions);
        if (((function_code.equals(body_function_code) && names.containsKey(body_function_code)) && 
          (!definitions.equals(function_definition)))) {
          conflicts.add(definitions);
        } else {
          names.put(body_function_code, body_function_code);
        }
      }
    }
    names.clear();
    function_code = Utility.calculateFunctionCode(function_definition);
    int _size = conflicts.size();
    boolean _equals = (_size == 0);
    if (_equals) {
      return null;
    }
    conflicts.add(function_definition);
    for (final FunctionDefinition definitions_1 : conflicts) {
      {
        body_function_code = Utility.calculateFunctionCode(definitions_1);
        if ((function_code.equals(body_function_code) && names.containsKey(body_function_code))) {
          return new ErrorType(message, feature, code);
        } else {
          names.put(body_function_code, body_function_code);
        }
      }
    }
    return null;
  }

  public static ErrorType isVariableListOK(final FunctionDefinition function_definition) {
    String message = null;
    String code = ErrorCode.FUNCTION_DEFINITION__INVALID_VARIABLE;
    EStructuralFeature feature = AsmetalPackage.Literals.FUNCTION_DEFINITION__DEFINED_FUNCTION_NAME;
    HashMap<String, String> names = new HashMap<String, String>();
    EList<String> _variables = function_definition.getVariables();
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

  public static ErrorType isDomainListOK(final FunctionDefinition function_definition) {
    EStructuralFeature feature = AsmetalPackage.Literals.FUNCTION_DEFINITION__DEFINED_FUNCTION_NAME;
    ErrorType error = null;
    EList<Domain> _domain = function_definition.getDomain();
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

  public static ErrorType isDefinedFunctionOK(final FunctionDefinition function_definition) {
    String function_code = Utility.calculateFunctionCode(function_definition);
    EStructuralFeature feature = AsmetalPackage.Literals.FUNCTION_DEFINITION__DEFINED_FUNCTION_NAME;
    ErrorType error = SharedCheckers.returnErrorFunctionString(function_code, feature);
    if ((error != null)) {
      return error;
    }
    Function func = Utility.getFunction(function_code);
    if ((func == null)) {
      func = Utility.getFunctionByName(function_code);
    }
    if (((!(func instanceof DerivedFunction)) && (!(func instanceof StaticFunction)))) {
      String message = "Only static and derived functions can be defined";
      String code = ErrorCode.FUNCTION_DEFINITION__FUNCTION_NOT_STATIC_DERIVED;
      return new ErrorType(message, feature, code);
    }
    return null;
  }

  public static ErrorType isBodyOK(final FunctionDefinition function_definition) {
    EStructuralFeature feature = AsmetalPackage.Literals.FUNCTION_DEFINITION__DEFINED_FUNCTION_NAME;
    String message = null;
    String code = ErrorCode.FUNCTION_DEFINITION__ILL_FORMED_BODY;
    Term body = function_definition.getBody();
    DomainTree body_domain = DomainCalculator.getDomainTerm(body);
    String function_code = Utility.calculateFunctionCode(function_definition);
    Function func = Utility.getFunction(function_code);
    if ((func == null)) {
      func = Utility.getFunctionByName(function_code);
    }
    Domain function_domain = Utility.getDomain(Utility.calculateDomainCode(func.getCodomain()));
    DomainTree tree = DomainTree.getTreeFromDomain(function_domain);
    boolean _isCompatible = Utility.isCompatible(body_domain, tree);
    boolean _not = (!_isCompatible);
    if (_not) {
      Object _root = body_domain.getModel().getRoot();
      String _plus = ("The domain of the function body (" + _root);
      String _plus_1 = (_plus + 
        ") must be compatible with the function codomain (");
      Object _root_1 = tree.getModel().getRoot();
      String _plus_2 = (_plus_1 + _root_1);
      String _plus_3 = (_plus_2 + ")");
      message = _plus_3;
      return new ErrorType(message, feature, code);
    }
    return null;
  }

  public static ErrorType isDefineOnceOK(final DomainInitialization domain_init) {
    String message = "Within an initial state a concrete domain can be initialized only once";
    String code = ErrorCode.DOMAIN_INIZIALIZATION__DEFINED_TWICE;
    EStructuralFeature feature = AsmetalPackage.Literals.DOMAIN_INITIALIZATION__INITIALIZED_DOMAIN;
    EObject _eContainer = domain_init.eContainer();
    Initialization init_state = ((Initialization) _eContainer);
    HashMap<String, String> names = new HashMap<String, String>();
    EList<DomainInitialization> _domainInitialization = init_state.getDomainInitialization();
    for (final DomainInitialization init : _domainInitialization) {
      if ((names.containsKey(init.getInitializedDomain()) && 
        init.getInitializedDomain().equals(domain_init.getInitializedDomain()))) {
        return new ErrorType(message, feature, code);
      } else {
        names.put(init.getInitializedDomain(), init.getInitializedDomain());
      }
    }
    return null;
  }

  public static ErrorType isInizializedDomainOK(final String domain_name) {
    ErrorType error = null;
    String message = null;
    String code = null;
    EAttribute feature = AsmetalPackage.Literals.DOMAIN_INITIALIZATION__INITIALIZED_DOMAIN;
    error = SharedCheckers.returnErrorDomainString(domain_name, feature);
    if ((error != null)) {
      return error;
    }
    Domain dom = Utility.getDomain(domain_name);
    if ((!(dom instanceof ConcreteDomain))) {
      message = "Static domains can\'t be initialized";
      code = ErrorCode.DOMAIN_INIZIALIZATION__IS_STATIC;
      return new ErrorType(message, feature, code);
    }
    ConcreteDomain concrete_domain = ((ConcreteDomain) dom);
    boolean _isDynamic = concrete_domain.isDynamic();
    boolean _not = (!_isDynamic);
    if (_not) {
      message = "Static domains can\'t be initialized";
      code = ErrorCode.DOMAIN_INIZIALIZATION__IS_STATIC;
      return new ErrorType(message, feature, code);
    }
    return null;
  }

  public static ErrorType isBodyOK(final DomainInitialization domain_definition) {
    Term body = domain_definition.getBody();
    EStructuralFeature feature = AsmetalPackage.Literals.DOMAIN_INITIALIZATION__BODY;
    String message = null;
    String code = ErrorCode.DOMAIN_INIZIALIZATION__ILL_FORMED_BODY;
    if ((!(body instanceof SetTerm))) {
      message = "The type-domain of the body of the domain initialization must be a powerset";
      return new ErrorType(message, feature, code);
    }
    SetTerm set_term_body = ((SetTerm) body);
    Term _start_term = set_term_body.getStart_term();
    boolean _tripleEquals = (_start_term == null);
    if (_tripleEquals) {
      return null;
    }
    DomainTree body_domain = DomainCalculator.getDomainTerm(body);
    Domain dom = Utility.getDomain(domain_definition.getInitializedDomain());
    boolean _isCompatible = Utility.isCompatible(body_domain, DomainTree.getTreeFromDomain(dom));
    boolean _not = (!_isCompatible);
    if (_not) {
      message = "The type-domain of the body of the domain initialization is different from the domain";
      return new ErrorType(message, feature, code);
    }
    return null;
  }

  public static ErrorType isDefinedOnceOK(final FunctionInitialization function_init) {
    String _inizializedFunctionName = function_init.getInizializedFunctionName();
    String _plus = ("The function \'" + _inizializedFunctionName);
    String message = (_plus + "\' has been defined twice");
    String code = ErrorCode.FUNCTION_INIZIALIZATION__DEFINED_TWICE;
    EStructuralFeature feature = AsmetalPackage.Literals.FUNCTION_INITIALIZATION__INIZIALIZED_FUNCTION_NAME;
    Utility.fillFunctionInitializationMap(function_init);
    EObject _eContainer = function_init.eContainer();
    Initialization init = ((Initialization) _eContainer);
    HashMap<String, String> names = new HashMap<String, String>();
    ArrayList<FunctionInitialization> conflicts = new ArrayList<FunctionInitialization>();
    String init_function_code = "";
    String function_code = function_init.getInizializedFunctionName();
    EList<FunctionInitialization> _functionInitialization = init.getFunctionInitialization();
    for (final FunctionInitialization in : _functionInitialization) {
      {
        init_function_code = in.getInizializedFunctionName();
        if (((function_code.equals(init_function_code) && names.containsKey(init_function_code)) && 
          (!in.equals(function_init)))) {
          conflicts.add(in);
        } else {
          names.put(init_function_code, init_function_code);
        }
      }
    }
    names.clear();
    function_code = Utility.calculateFunctionCode(function_init);
    int _size = conflicts.size();
    boolean _equals = (_size == 0);
    if (_equals) {
      return null;
    }
    conflicts.add(function_init);
    for (final FunctionInitialization inits : conflicts) {
      {
        init_function_code = Utility.calculateFunctionCode(inits);
        if ((function_code.equals(init_function_code) && names.containsKey(init_function_code))) {
          return new ErrorType(message, feature, code);
        } else {
          names.put(init_function_code, init_function_code);
        }
      }
    }
    return null;
  }

  public static ErrorType isVariableListOK(final FunctionInitialization function_init) {
    String message = null;
    String code = ErrorCode.FUNCTION_INIZIALIZATION__INVALID_VARIABLE;
    EStructuralFeature feature = AsmetalPackage.Literals.FUNCTION_INITIALIZATION__INIZIALIZED_FUNCTION_NAME;
    HashMap<String, String> names = new HashMap<String, String>();
    EList<String> _variables = function_init.getVariables();
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

  public static ErrorType isDomainListOK(final FunctionInitialization function_definition) {
    EStructuralFeature feature = AsmetalPackage.Literals.FUNCTION_INITIALIZATION__INIZIALIZED_FUNCTION_NAME;
    ErrorType error = null;
    EList<Domain> _domain = function_definition.getDomain();
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

  public static ErrorType isInizializedFunctionOK(final FunctionInitialization function_init) {
    String function_code = Utility.calculateFunctionCode(function_init);
    String message = null;
    String code = null;
    EStructuralFeature feature = AsmetalPackage.Literals.FUNCTION_INITIALIZATION__INIZIALIZED_FUNCTION_NAME;
    ErrorType error = SharedCheckers.returnErrorFunctionString(function_code, feature);
    if ((error != null)) {
      return error;
    }
    Function func = Utility.getFunction(function_code);
    if ((func == null)) {
      func = Utility.getFunctionByName(function_code);
    }
    if ((!(func instanceof DynamicFunction))) {
      message = (("The function " + function_code) + " is not a dynamic function. It cannot be initialized");
      code = ErrorCode.FUNCTION_INIZIALIZATION__FUNCTION_NOT_DYNAMIC;
      return new ErrorType(message, feature, code);
    }
    return null;
  }

  /**
   * Check if the domain of the body is compatible with the domain of the function
   */
  public static ErrorType isBodyOK(final FunctionInitialization function_definition) {
    EStructuralFeature feature = AsmetalPackage.Literals.FUNCTION_INITIALIZATION__INIZIALIZED_FUNCTION_NAME;
    String message = null;
    String code = ErrorCode.FUNCTION_INIZIALIZATION__ILL_FORMED_BODY;
    Term body = function_definition.getBody();
    DomainTree body_domain = DomainCalculator.getDomainTerm(body);
    boolean _equals = body_domain.getCodeFromTree().equals("Undef");
    if (_equals) {
      return null;
    }
    String function_code = Utility.calculateFunctionCode(function_definition);
    Function func = Utility.getFunction(function_code);
    if ((func == null)) {
      func = Utility.getFunctionByName(function_code);
    }
    Domain function_domain = Utility.getDomain(Utility.calculateDomainCode(func.getCodomain()));
    DomainTree merged_domains = DomainTree.getTreeFromDomain(function_domain);
    boolean _isCompatible = Utility.isCompatible(body_domain, merged_domains);
    boolean _not = (!_isCompatible);
    if (_not) {
      message = "The domain of the function body must be compatible with the function codomain";
      return new ErrorType(message, feature, code);
    }
    return null;
  }

  public static ErrorType isDomainOK(final AgentInitialization agent_init) {
    EStructuralFeature feature = AsmetalPackage.Literals.AGENT_INITIALIZATION__DOMAIN_NAME;
    ErrorType error = null;
    error = SharedCheckers.returnErrorDomainString(agent_init.getDomainName(), feature);
    if ((error != null)) {
      return error;
    }
    Domain agent_domain = Utility.getDomain("Agent");
    if ((agent_domain == null)) {
      String message = " The AgentDomain is not defined";
      String code = ErrorCode.AGENT_INIZIALIZATION__AGENT_DOMAIN_NOT_DECLARED;
      return new ErrorType(message, feature, code);
    }
    boolean _isAgentDomain = Utility.isAgentDomain(agent_init.getDomainName());
    boolean _not = (!_isAgentDomain);
    if (_not) {
      String _domainName = agent_init.getDomainName();
      String _plus = ("The domain " + _domainName);
      String message_1 = (_plus + " is not the Agent Domain or a subset of it.");
      String code_1 = ErrorCode.AGENT_INIZIALIZATION__INVALID_DOMAIN;
      return new ErrorType(message_1, feature, code_1);
    }
    return null;
  }

  public static ErrorType isProgramOK(final AgentInitialization agent_init) {
    String rule_name = agent_init.getProgram().getName();
    ArrayList<RuleDeclaration> program = Utility.getRule(rule_name);
    if (((program == null) || (program.size() == 0))) {
      EStructuralFeature feature = AsmetalPackage.Literals.AGENT_INITIALIZATION__PROGRAM;
      String message = (("Rule \'" + rule_name) + "\' not found");
      String code = ErrorCode.AGENT_INIZIALIZATION__PROGRAM_NOT_FOUND;
      return new ErrorType(message, feature, code);
    }
    return null;
  }
}
