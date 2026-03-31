package org.asmeta.xt.validation.utility;

import com.google.common.collect.Iterators;
import com.google.inject.Injector;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import org.asmeta.parser.AsmetaParserUtility;
import org.asmeta.xt.AsmetaLStandaloneSetup;
import org.asmeta.xt.asmetal.AbstractTD;
import org.asmeta.xt.asmetal.AgentDomain;
import org.asmeta.xt.asmetal.AnyDomain;
import org.asmeta.xt.asmetal.Asm;
import org.asmeta.xt.asmetal.AsmetalFactory;
import org.asmeta.xt.asmetal.AsmetalPackage;
import org.asmeta.xt.asmetal.BagDomain;
import org.asmeta.xt.asmetal.BagTerm;
import org.asmeta.xt.asmetal.BasicTerm;
import org.asmeta.xt.asmetal.BinaryOperation;
import org.asmeta.xt.asmetal.Body;
import org.asmeta.xt.asmetal.BooleanDomain;
import org.asmeta.xt.asmetal.BooleanTerm;
import org.asmeta.xt.asmetal.ChooseRule;
import org.asmeta.xt.asmetal.ComprehensionTerm;
import org.asmeta.xt.asmetal.ConcreteDomain;
import org.asmeta.xt.asmetal.ConstantTerm;
import org.asmeta.xt.asmetal.Domain;
import org.asmeta.xt.asmetal.DomainDefinition;
import org.asmeta.xt.asmetal.DomainTerm;
import org.asmeta.xt.asmetal.EnumElement;
import org.asmeta.xt.asmetal.EnumTD;
import org.asmeta.xt.asmetal.ExportClause;
import org.asmeta.xt.asmetal.Expression;
import org.asmeta.xt.asmetal.ExtendRule;
import org.asmeta.xt.asmetal.FiniteQuantificationTerm;
import org.asmeta.xt.asmetal.ForallRule;
import org.asmeta.xt.asmetal.ForallTerm;
import org.asmeta.xt.asmetal.Function;
import org.asmeta.xt.asmetal.FunctionDefinition;
import org.asmeta.xt.asmetal.FunctionInitialization;
import org.asmeta.xt.asmetal.FunctionTerm;
import org.asmeta.xt.asmetal.Header;
import org.asmeta.xt.asmetal.ImportClause;
import org.asmeta.xt.asmetal.LetRule;
import org.asmeta.xt.asmetal.LetTerm;
import org.asmeta.xt.asmetal.LocalFunction;
import org.asmeta.xt.asmetal.LocationTerm;
import org.asmeta.xt.asmetal.MapDomain;
import org.asmeta.xt.asmetal.PowersetDomain;
import org.asmeta.xt.asmetal.ProductDomain;
import org.asmeta.xt.asmetal.ReserveDomain;
import org.asmeta.xt.asmetal.RuleDeclaration;
import org.asmeta.xt.asmetal.RuleDomain;
import org.asmeta.xt.asmetal.SequenceDomain;
import org.asmeta.xt.asmetal.SetTerm;
import org.asmeta.xt.asmetal.Signature;
import org.asmeta.xt.asmetal.StructuredTD;
import org.asmeta.xt.asmetal.Term;
import org.asmeta.xt.asmetal.TupleTerm;
import org.asmeta.xt.asmetal.VariableTerm;
import org.asmeta.xt.asmetal.importData;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EObjectEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;

@SuppressWarnings("all")
public class Utility {
  public static HashMap<String, Domain> imported_all_domain_map = new HashMap<String, Domain>();

  public static HashMap<String, Function> imported_all_function_map = new HashMap<String, Function>();

  public static HashMap<String, ArrayList<RuleDeclaration>> imported_all_rule_map = new HashMap<String, ArrayList<RuleDeclaration>>();

  public static HashMap<Function, FunctionDefinition> function_declarations_map = new HashMap<Function, FunctionDefinition>();

  public static HashMap<Function, FunctionInitialization> function_initialization_map = new HashMap<Function, FunctionInitialization>();

  public static HashMap<Domain, DomainDefinition> domain_declarations_map = new HashMap<Domain, DomainDefinition>();

  public static HashMap<FunctionInitialization, FunctionDefinition> function_init_to_def = new HashMap<FunctionInitialization, FunctionDefinition>();

  public static HashMap<String, String> imported_asm_data = new HashMap<String, String>();

  public static HashMap<String, Domain> imported_domain_map = new HashMap<String, Domain>();

  public static HashMap<String, Function> imported_function_map = new HashMap<String, Function>();

  public static HashMap<String, Function> imported_name_function_map = new HashMap<String, Function>();

  public static HashMap<String, ArrayList<Function>> imported_list_function_map = new HashMap<String, ArrayList<Function>>();

  public static HashMap<String, ArrayList<RuleDeclaration>> imported_rule_map = new HashMap<String, ArrayList<RuleDeclaration>>();

  public static HashMap<String, Domain> signature_domain_map = new HashMap<String, Domain>();

  public static HashMap<String, Domain> signature_function_domain_map = new HashMap<String, Domain>();

  public static HashMap<String, Domain> signature_enum_domain_map = new HashMap<String, Domain>();

  public static HashMap<String, Function> signature_function_map = new HashMap<String, Function>();

  public static HashMap<String, Function> signature_name_function_map = new HashMap<String, Function>();

  public static HashMap<String, ArrayList<Function>> signature_list_function_map = new HashMap<String, ArrayList<Function>>();

  public static HashMap<String, ArrayList<RuleDeclaration>> body_rule_map = new HashMap<String, ArrayList<RuleDeclaration>>();

  public static HashMap<String, Domain> variable_domain_map = new HashMap<String, Domain>();

  public static HashMap<String, ArrayList<EObject>> variable_origin_map = new HashMap<String, ArrayList<EObject>>();

  public static HashMap<BinaryOperation, FunctionTerm> binary_operation_conversion_map = new HashMap<BinaryOperation, FunctionTerm>();

  public static HashMap<Expression, FunctionTerm> expression_conversion_map = new HashMap<Expression, FunctionTerm>();

  /**
   * relative_path: path given in the ASM file (like STDL/StandardLibrary) or other formats
   * resource_abs_path: absolute path of the file containing the import
   * return the absolute address or null if does not exists
   */
  public static String getAbsoluteAddressAsm(final String relative_path, final String resource_abs_path) {
    boolean _exists = new File(relative_path).exists();
    if (_exists) {
      return relative_path;
    }
    String res = null;
    boolean _endsWith = relative_path.endsWith(AsmetaParserUtility.ASM_EXTENSION);
    if (_endsWith) {
      res = relative_path;
    } else {
      res = (relative_path + AsmetaParserUtility.ASM_EXTENSION);
    }
    Path basePath = Paths.get(resource_abs_path);
    Path resolvedPath = basePath.resolve(res);
    boolean _exists_1 = Files.exists(resolvedPath);
    if (_exists_1) {
      return resolvedPath.normalize().toString();
    }
    return null;
  }

  /**
   * return null, if not found
   * 
   * relative_path --> path of the imported file (normally relative)
   * resource_abs_path --> path of asmeta containing the import command
   */
  public static Asm getImportedAsm(final String relative_path, final String resource_abs_path) {
    try {
      String address_str = Utility.getAbsoluteAddressAsm(relative_path, resource_abs_path);
      if ((address_str == null)) {
        return null;
      }
      Injector injector = new AsmetaLStandaloneSetup().createInjectorAndDoEMFRegistration();
      ResourceSet resourceSet = injector.<ResourceSet>getInstance(ResourceSet.class);
      Resource r = resourceSet.getResource(URI.createFileURI(address_str), true);
      r.load(null);
      Iterable<Asm> asm_list = IteratorExtensions.<Asm>toIterable(Iterators.<Asm>filter(r.getAllContents(), Asm.class));
      final Iterable<Asm> _converted_asm_list = (Iterable<Asm>)asm_list;
      Asm imported = ((Asm[])Conversions.unwrapArray(_converted_asm_list, Asm.class))[0];
      return imported;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  public static Integer calculateRuleArity(final RuleDeclaration rule) {
    if ((rule == null)) {
      return Integer.valueOf(0);
    }
    EList<String> _variables = rule.getVariables();
    boolean _tripleEquals = (_variables == null);
    if (_tripleEquals) {
      return Integer.valueOf(0);
    }
    return Integer.valueOf(rule.getVariables().size());
  }

  public static String calculateDomainCode(final Domain domain) {
    String res = null;
    if ((domain == null)) {
      res = "";
    } else {
      if ((domain instanceof StructuredTD)) {
        res = ((StructuredTD)domain).getName();
        if ((domain instanceof RuleDomain)) {
          int _size = ((RuleDomain)domain).getDomains().size();
          boolean _greaterThan = (_size > 0);
          if (_greaterThan) {
            String _res = res;
            res = (_res + "(");
            EList<Domain> _domains = ((RuleDomain)domain).getDomains();
            for (final Domain dom : _domains) {
              String _res_1 = res;
              String _calculateDomainCode = Utility.calculateDomainCode(dom);
              String _plus = (_calculateDomainCode + ",");
              res = (_res_1 + _plus);
            }
            int _length = res.length();
            int _minus = (_length - 1);
            res = res.substring(0, _minus);
            String _res_2 = res;
            res = (_res_2 + ")");
          }
        } else {
          if ((domain instanceof ProductDomain)) {
            String _res_3 = res;
            res = (_res_3 + "(");
            EList<Domain> _domains_1 = ((ProductDomain)domain).getDomains();
            for (final Domain dom_1 : _domains_1) {
              String _res_4 = res;
              String _calculateDomainCode_1 = Utility.calculateDomainCode(dom_1);
              String _plus_1 = (_calculateDomainCode_1 + ",");
              res = (_res_4 + _plus_1);
            }
            int _length_1 = res.length();
            int _minus_1 = (_length_1 - 1);
            res = res.substring(0, _minus_1);
            String _res_5 = res;
            res = (_res_5 + ")");
          } else {
            if ((domain instanceof SequenceDomain)) {
              String _res_6 = res;
              String _calculateDomainCode_2 = Utility.calculateDomainCode(((SequenceDomain)domain).getDomain());
              String _plus_2 = ("(" + _calculateDomainCode_2);
              String _plus_3 = (_plus_2 + ")");
              res = (_res_6 + _plus_3);
            } else {
              if ((domain instanceof BagDomain)) {
                String _res_7 = res;
                String _calculateDomainCode_3 = Utility.calculateDomainCode(((BagDomain)domain).getDomain());
                String _plus_4 = ("(" + _calculateDomainCode_3);
                String _plus_5 = (_plus_4 + ")");
                res = (_res_7 + _plus_5);
              } else {
                if ((domain instanceof PowersetDomain)) {
                  String _res_8 = res;
                  String _calculateDomainCode_4 = Utility.calculateDomainCode(((PowersetDomain)domain).getBaseDomain());
                  String _plus_6 = ("(" + _calculateDomainCode_4);
                  String _plus_7 = (_plus_6 + ")");
                  res = (_res_8 + _plus_7);
                } else {
                  if ((domain instanceof MapDomain)) {
                    String _res_9 = res;
                    String _calculateDomainCode_5 = Utility.calculateDomainCode(((MapDomain)domain).getSourceDomain());
                    String _plus_8 = ("(" + _calculateDomainCode_5);
                    String _plus_9 = (_plus_8 + ",");
                    String _calculateDomainCode_6 = Utility.calculateDomainCode(((MapDomain)domain).getTargetDomain());
                    String _plus_10 = (_plus_9 + _calculateDomainCode_6);
                    String _plus_11 = (_plus_10 + ")");
                    res = (_res_9 + _plus_11);
                  }
                }
              }
            }
          }
        }
      } else {
        res = domain.getName();
      }
    }
    return res;
  }

  public static String calculateDomainCodeFromList(final EList<Domain> domains) {
    String res = "";
    for (final Domain dom : domains) {
      String _res = res;
      String _calculateDomainCode = Utility.calculateDomainCode(dom);
      String _plus = (_calculateDomainCode + ",");
      res = (_res + _plus);
    }
    boolean _isEmpty = res.isEmpty();
    boolean _not = (!_isEmpty);
    if (_not) {
      int _length = res.length();
      int _minus = (_length - 1);
      res = res.substring(0, _minus);
    }
    int _size = domains.size();
    boolean _greaterThan = (_size > 1);
    if (_greaterThan) {
      res = (("Prod(" + res) + ")");
    }
    return res;
  }

  public static String calculateFunctionCode(final Function function) {
    String res = null;
    Domain _domain = function.getDomain();
    boolean _tripleEquals = (_domain == null);
    if (_tripleEquals) {
      String _name = function.getName();
      String _plus = (_name + "(");
      String _calculateDomainCode = Utility.calculateDomainCode(function.getCodomain());
      String _plus_1 = (_plus + _calculateDomainCode);
      String _plus_2 = (_plus_1 + ")");
      res = _plus_2;
    } else {
      String _name_1 = function.getName();
      String _plus_3 = (_name_1 + "(");
      String _calculateDomainCode_1 = Utility.calculateDomainCode(function.getDomain());
      String _plus_4 = (_plus_3 + _calculateDomainCode_1);
      String _plus_5 = (_plus_4 + ")");
      res = _plus_5;
    }
    return res;
  }

  public static String calculateFunctionCode(final FunctionInitialization function_init) {
    String res = "";
    int _size = function_init.getVariables().size();
    boolean _equals = (_size == 0);
    if (_equals) {
      res = function_init.getInizializedFunctionName();
    } else {
      int _size_1 = function_init.getVariables().size();
      boolean _equals_1 = (_size_1 == 1);
      if (_equals_1) {
        String _inizializedFunctionName = function_init.getInizializedFunctionName();
        String _plus = (_inizializedFunctionName + "(");
        String _calculateDomainCode = Utility.calculateDomainCode(function_init.getDomain().get(0));
        String _plus_1 = (_plus + _calculateDomainCode);
        String _plus_2 = (_plus_1 + ")");
        res = _plus_2;
      } else {
        String _inizializedFunctionName_1 = function_init.getInizializedFunctionName();
        String _plus_3 = (_inizializedFunctionName_1 + "(Prod(");
        res = _plus_3;
        EList<Domain> _domain = function_init.getDomain();
        for (final Domain domain : _domain) {
          String _res = res;
          String _calculateDomainCode_1 = Utility.calculateDomainCode(domain);
          String _plus_4 = (_calculateDomainCode_1 + ",");
          res = (_res + _plus_4);
        }
        int _length = res.length();
        int _minus = (_length - 1);
        res = res.substring(0, _minus);
        String _res_1 = res;
        res = (_res_1 + "))");
      }
    }
    return res;
  }

  public static String calculateFunctionCode(final FunctionDefinition function_definition) {
    String res = "";
    int _size = function_definition.getVariables().size();
    boolean _equals = (_size == 0);
    if (_equals) {
      res = function_definition.getDefinedFunctionName();
    } else {
      int _size_1 = function_definition.getVariables().size();
      boolean _equals_1 = (_size_1 == 1);
      if (_equals_1) {
        String _definedFunctionName = function_definition.getDefinedFunctionName();
        String _plus = (_definedFunctionName + "(");
        String _calculateDomainCode = Utility.calculateDomainCode(function_definition.getDomain().get(0));
        String _plus_1 = (_plus + _calculateDomainCode);
        String _plus_2 = (_plus_1 + ")");
        res = _plus_2;
      } else {
        String _definedFunctionName_1 = function_definition.getDefinedFunctionName();
        String _plus_3 = (_definedFunctionName_1 + "(Prod(");
        res = _plus_3;
        EList<Domain> _domain = function_definition.getDomain();
        for (final Domain domain : _domain) {
          String _res = res;
          String _calculateDomainCode_1 = Utility.calculateDomainCode(domain);
          String _plus_4 = (_calculateDomainCode_1 + ",");
          res = (_res + _plus_4);
        }
        int _length = res.length();
        int _minus = (_length - 1);
        res = res.substring(0, _minus);
        String _res_1 = res;
        res = (_res_1 + "))");
      }
    }
    return res;
  }

  public static boolean fillSignatureMap(final Signature signature, final Boolean reset) {
    if ((reset).booleanValue()) {
      Utility.signature_domain_map.clear();
      Utility.signature_enum_domain_map.clear();
      Utility.signature_function_map.clear();
      Utility.signature_name_function_map.clear();
      Utility.signature_list_function_map.clear();
    }
    String code = null;
    EList<Domain> _domain = signature.getDomain();
    for (final Domain domain : _domain) {
      {
        code = Utility.calculateDomainCode(domain);
        Utility.signature_domain_map.put(code, domain);
        if ((domain instanceof EnumTD)) {
          EList<EnumElement> _element = ((EnumTD)domain).getElement();
          for (final EnumElement el : _element) {
            Utility.signature_enum_domain_map.put(el.getSymbol(), domain);
          }
        }
      }
    }
    ArrayList<Function> temp = null;
    EList<Function> _function = signature.getFunction();
    for (final Function function : _function) {
      {
        code = Utility.calculateFunctionCode(function);
        Utility.putInFunctionDomainMapRec(function.getDomain());
        Utility.putInFunctionDomainMapRec(function.getCodomain());
        boolean _containsKey = Utility.signature_list_function_map.containsKey(function.getName());
        if (_containsKey) {
          temp = Utility.signature_list_function_map.get(function.getName());
          temp.add(function);
        } else {
          ArrayList<Function> _arrayList = new ArrayList<Function>();
          temp = _arrayList;
          temp.add(function);
          Utility.signature_list_function_map.put(function.getName(), temp);
        }
        Utility.signature_function_map.put(code, function);
        Utility.signature_name_function_map.put(function.getName(), function);
      }
    }
    return true;
  }

  public static boolean fillImportedEnumAndDomainMap(final Signature signature, final Boolean reset, final Asm curAsm, final ImportClause importClause) {
    if ((reset).booleanValue()) {
      Utility.imported_domain_map.clear();
    }
    String code = null;
    EList<Domain> _domain = signature.getDomain();
    for (final Domain domain : _domain) {
      {
        Boolean importDomain = Boolean.valueOf(false);
        code = Utility.calculateDomainCode(domain);
        EList<ImportClause> _importClause = curAsm.getHeaderSection().getImportClause();
        for (final ImportClause i : _importClause) {
          boolean _equals = Objects.equals(i, importClause);
          if (_equals) {
            if (((i.getImportedList() == null) || (i.getImportedList().size() == 0))) {
              importDomain = Boolean.valueOf(true);
            } else {
              EList<importData> _importedList = i.getImportedList();
              for (final importData id : _importedList) {
                boolean _equals_1 = id.getName().equals(code.split("\\(")[0]);
                if (_equals_1) {
                  importDomain = Boolean.valueOf(true);
                }
              }
            }
          }
        }
        if ((importDomain).booleanValue()) {
          Utility.imported_domain_map.put(code, domain);
          if ((domain instanceof EnumTD)) {
            EList<EnumElement> _element = ((EnumTD)domain).getElement();
            for (final EnumElement el : _element) {
              Utility.signature_enum_domain_map.put(el.getSymbol(), domain);
            }
          }
        }
      }
    }
    return true;
  }

  public static void putInFunctionDomainMapRec(final Domain domain) {
    if ((domain instanceof StructuredTD)) {
      Utility.signature_function_domain_map.put(Utility.calculateDomainCode(domain), domain);
      String type = "";
      if ((domain instanceof RuleDomain)) {
        int _size = ((RuleDomain)domain).getDomains().size();
        boolean _greaterThan = (_size > 0);
        if (_greaterThan) {
          EList<Domain> _domains = ((RuleDomain)domain).getDomains();
          for (final Domain dom : _domains) {
            Utility.putInFunctionDomainMapRec(dom);
          }
        }
      } else {
        if ((domain instanceof ProductDomain)) {
          type = "Prod(";
          ProductDomain pd = AsmetalFactory.eINSTANCE.createProductDomain();
          pd.setName("Prod");
          EList<Domain> _domains_1 = ((ProductDomain)domain).getDomains();
          for (final Domain dom_1 : _domains_1) {
            {
              Utility.putInFunctionDomainMapRec(dom_1);
              Domain _domain = Utility.getDomain(dom_1.getName());
              if ((_domain instanceof ConcreteDomain)) {
                Domain _domain_1 = Utility.getDomain(dom_1.getName());
                String _calculateDomainCode = Utility.calculateDomainCode(((ConcreteDomain) _domain_1).getTypeDomain());
                String _plus = (type + _calculateDomainCode);
                String _plus_1 = (_plus + ",");
                type = _plus_1;
                Domain tempDom = AsmetalFactory.eINSTANCE.createDomain();
                Domain _domain_2 = Utility.getDomain(dom_1.getName());
                tempDom.setName(Utility.calculateDomainCode(((ConcreteDomain) _domain_2).getTypeDomain()));
                pd.getDomains().add(tempDom);
              } else {
                String _calculateDomainCode_1 = Utility.calculateDomainCode(dom_1);
                String _plus_2 = (type + _calculateDomainCode_1);
                String _plus_3 = (_plus_2 + ",");
                type = _plus_3;
                Domain tempDom_1 = AsmetalFactory.eINSTANCE.createDomain();
                tempDom_1.setName(Utility.calculateDomainCode(dom_1));
                pd.getDomains().add(tempDom_1);
              }
            }
          }
          int _length = type.length();
          int _minus = (_length - 1);
          String _substring = type.substring(0, _minus);
          String _plus = (_substring + ")");
          type = _plus;
          boolean _isCompatible = Utility.isCompatible(pd, domain);
          boolean _not = (!_isCompatible);
          if (_not) {
            Utility.signature_function_domain_map.put(type, pd);
          } else {
            Utility.signature_function_domain_map.put(type, domain);
          }
        } else {
          if ((domain instanceof SequenceDomain)) {
            Utility.putInFunctionDomainMapRec(((SequenceDomain)domain).getDomain());
          } else {
            if ((domain instanceof BagDomain)) {
              Utility.putInFunctionDomainMapRec(((BagDomain)domain).getDomain());
            } else {
              if ((domain instanceof PowersetDomain)) {
                Utility.putInFunctionDomainMapRec(((PowersetDomain)domain).getBaseDomain());
              }
            }
          }
        }
      }
    } else {
      return;
    }
  }

  public static boolean fillBodyMap(final Body body) {
    Utility.body_rule_map.clear();
    String code = null;
    EList<RuleDeclaration> _ruleDeclaration = body.getRuleDeclaration();
    for (final RuleDeclaration rule : _ruleDeclaration) {
      {
        code = rule.getName();
        boolean _containsKey = Utility.body_rule_map.containsKey(code);
        if (_containsKey) {
          boolean _contains = Utility.body_rule_map.get(code).contains(rule);
          if (_contains) {
            return false;
          } else {
            Utility.body_rule_map.get(code).add(rule);
          }
        } else {
          ArrayList<RuleDeclaration> list = new ArrayList<RuleDeclaration>();
          list.add(rule);
          Utility.body_rule_map.put(code, list);
        }
      }
    }
    return false;
  }

  public static boolean fillOnlyDomainMap(final Signature signature) {
    Utility.signature_domain_map.clear();
    String code = null;
    EList<Domain> _domain = signature.getDomain();
    for (final Domain domain : _domain) {
      {
        code = Utility.calculateDomainCode(domain);
        Utility.signature_domain_map.put(code, domain);
      }
    }
    return true;
  }

  public static void fillAllImportedMap(final Header header, final Boolean reset) {
    if ((reset).booleanValue()) {
      Utility.imported_all_domain_map.clear();
      Utility.imported_all_function_map.clear();
      Utility.imported_all_rule_map.clear();
      Utility.imported_asm_data.clear();
      Utility.imported_domain_map.clear();
      Utility.imported_function_map.clear();
      Utility.imported_rule_map.clear();
      Utility.imported_list_function_map.clear();
    }
    Asm imported_asm = null;
    EList<ImportClause> _importClause = header.getImportClause();
    for (final ImportClause importClause : _importClause) {
      {
        String dir = Utility.getBaseDir(importClause);
        imported_asm = Utility.getImportedAsm(importClause.getModuleName(), dir);
        if (((imported_asm != null) && (!Utility.imported_asm_data.containsKey(imported_asm.getName())))) {
          Utility.imported_asm_data.put(imported_asm.getName(), imported_asm.getName());
          if ((((imported_asm.getHeaderSection() != null) && (imported_asm.getHeaderSection().getImportClause() != null)) && 
            (imported_asm.getHeaderSection().getImportClause().size() > 0))) {
            Utility.fillAllImportedMap(imported_asm.getHeaderSection(), Boolean.valueOf(false));
          }
          if (((imported_asm.getHeaderSection() != null) && (imported_asm.getHeaderSection().getSignature() != null))) {
            EObject _eContainer = header.eContainer();
            Utility.fillImportedEnumAndDomainMap(imported_asm.getHeaderSection().getSignature(), Boolean.valueOf(false), ((Asm) _eContainer), importClause);
          }
          if (((imported_asm.getHeaderSection() != null) && (imported_asm.getHeaderSection().getExportClause() != null))) {
            Utility.fillImportedRuleMap(imported_asm.getBodySection(), imported_asm.getHeaderSection().getExportClause());
          }
          Utility.fillImportedMap(imported_asm.getHeaderSection(), importClause);
        }
      }
    }
  }

  public static void fillImportedRuleMap(final Body body, final ExportClause exportClause) {
    String code = null;
    EList<RuleDeclaration> _ruleDeclaration = body.getRuleDeclaration();
    for (final RuleDeclaration rule : _ruleDeclaration) {
      {
        code = rule.getName();
        if ((exportClause.getExportedList().contains(code) || exportClause.isExportAll())) {
          boolean _containsKey = Utility.imported_rule_map.containsKey(code);
          boolean _not = (!_containsKey);
          if (_not) {
            ArrayList<RuleDeclaration> list = new ArrayList<RuleDeclaration>();
            list.add(rule);
            Utility.imported_rule_map.put(code, list);
          } else {
            boolean _contains = Utility.imported_rule_map.get(code).contains(rule);
            boolean _not_1 = (!_contains);
            if (_not_1) {
              Utility.imported_rule_map.get(code).add(rule);
            }
          }
        }
      }
    }
  }

  /**
   * return the base dir for an import clause
   */
  public static String getBaseDir(final ImportClause importClause) {
    final URI uri = importClause.eResource().getURI();
    String platformString = uri.toPlatformString(true);
    if ((platformString != null)) {
      try {
        ResourcesPlugin.getWorkspace();
        IWorkspaceRoot _root = ResourcesPlugin.getWorkspace().getRoot();
        org.eclipse.core.runtime.Path _path = new org.eclipse.core.runtime.Path(platformString);
        IFile myFile = _root.getFile(_path);
        return myFile.getLocation().toFile().getParent();
      } catch (final Throwable _t) {
        if (_t instanceof IllegalStateException) {
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
    }
    String _fileString = uri.toFileString();
    String res = new File(_fileString).getParent();
    if ((res != null)) {
      return res;
    }
    return new File(".").getAbsolutePath();
  }

  public static void fillImportedMap(final Header importedHeader, final ImportClause importClause) {
    EObject _eContainer = importedHeader.eContainer();
    Asm asm = ((Asm) _eContainer);
    String name = null;
    boolean exporting = false;
    boolean importing = false;
    HashMap<String, String> imported_list_map = new HashMap<String, String>();
    EList<importData> _importedList = importClause.getImportedList();
    for (final importData data : _importedList) {
      imported_list_map.put(data.getName(), data.getName());
    }
    EList<Domain> _domain = importedHeader.getSignature().getDomain();
    for (final Domain domain : _domain) {
      {
        name = Utility.calculateDomainCode(domain);
        Utility.imported_all_domain_map.put(name, domain);
        Utility.imported_asm_data.put(asm.getName(), asm.getName());
        ExportClause _exportClause = importedHeader.getExportClause();
        boolean _tripleNotEquals = (_exportClause != null);
        if (_tripleNotEquals) {
          exporting = (importedHeader.getExportClause().isExportAll() || 
            importedHeader.getExportClause().getExportedList().contains(domain.getName()));
          importing = (((importClause.getImportedList() == null) || (importClause.getImportedList().size() == 0)) || 
            imported_list_map.containsKey(domain.getName()));
          if (((importClause.getImportedList() != null) && (importClause.getImportedList().size() > 0))) {
            Boolean exists = Boolean.valueOf(false);
            EList<importData> _importedList_1 = importClause.getImportedList();
            for (final importData i : _importedList_1) {
              boolean _equals = i.getName().equals(name.split("\\(")[0]);
              if (_equals) {
                exists = Boolean.valueOf(true);
              }
            }
            importing = (importing && (exists).booleanValue());
          }
          if ((exporting && importing)) {
            Utility.imported_domain_map.put(name, domain);
          }
        }
      }
    }
    ArrayList<Function> temp = null;
    EList<Function> _function = importedHeader.getSignature().getFunction();
    for (final Function function : _function) {
      {
        name = Utility.calculateFunctionCode(function);
        Utility.imported_all_function_map.put(name, function);
        Utility.imported_asm_data.put(name, asm.getName());
        ExportClause _exportClause = importedHeader.getExportClause();
        boolean _tripleNotEquals = (_exportClause != null);
        if (_tripleNotEquals) {
          exporting = (importedHeader.getExportClause().isExportAll() || 
            importedHeader.getExportClause().getExportedList().contains(function.getName()));
          importing = (((importClause.getImportedList() == null) || (importClause.getImportedList().size() == 0)) || 
            imported_list_map.containsKey(function.getName()));
          if (((importClause.getImportedList() != null) && (importClause.getImportedList().size() > 0))) {
            Boolean exists = Boolean.valueOf(false);
            EList<importData> _importedList_1 = importClause.getImportedList();
            for (final importData i : _importedList_1) {
              boolean _equals = i.getName().equals(name.split("\\(")[0]);
              if (_equals) {
                exists = Boolean.valueOf(true);
              }
            }
            importing = (importing && (exists).booleanValue());
          }
          if ((exporting && importing)) {
            Utility.imported_name_function_map.put(function.getName(), function);
            Utility.imported_function_map.put(name, function);
            boolean _containsKey = Utility.imported_list_function_map.containsKey(function.getName());
            if (_containsKey) {
              temp = Utility.imported_list_function_map.get(function.getName());
              temp.add(function);
            } else {
              ArrayList<Function> _arrayList = new ArrayList<Function>();
              temp = _arrayList;
              temp.add(function);
              Utility.imported_list_function_map.put(function.getName(), temp);
            }
            Utility.putInFunctionDomainMapRec(function.getDomain());
            Utility.putInFunctionDomainMapRec(function.getCodomain());
          }
        }
      }
    }
  }

  public static boolean isInMaps(final String id) {
    return (Utility.isInImportedMap(id) || Utility.isInSignatureMap(id));
  }

  public static boolean isInImportedMap(final String id) {
    return ((Utility.imported_domain_map.containsKey(id) || Utility.imported_name_function_map.containsKey(id)) || 
      Utility.imported_rule_map.containsKey(id));
  }

  public static boolean isFunctionName(final String id) {
    return (Utility.imported_name_function_map.containsKey(id) || Utility.signature_name_function_map.containsKey(id));
  }

  public static boolean isRule(final String id) {
    return (Utility.imported_rule_map.containsKey(id) || Utility.body_rule_map.containsKey(id));
  }

  public static boolean isInSignatureMap(final String id) {
    return ((Utility.signature_domain_map.containsKey(id) || Utility.signature_name_function_map.containsKey(id)) || 
      Utility.body_rule_map.containsKey(id));
  }

  public static boolean isDomainDeclared(final Domain domain) {
    if ((domain == null)) {
      return false;
    }
    String name = Utility.calculateDomainCode(domain);
    return (Utility.imported_domain_map.containsKey(name) || Utility.signature_domain_map.containsKey(name));
  }

  public static boolean isDomainDeclaredPrivate(final String name) {
    return ((!Utility.imported_domain_map.containsKey(name)) && Utility.imported_all_domain_map.containsKey(name));
  }

  public static boolean isFunctionDeclaredPrivate(final String name) {
    return ((!Utility.imported_function_map.containsKey(name)) && Utility.imported_all_function_map.containsKey(name));
  }

  public static boolean isRuleDeclaredPrivate(final String name) {
    return ((!Utility.imported_rule_map.containsKey(name)) && Utility.imported_all_rule_map.containsKey(name));
  }

  public static Domain getDomain(final String id) {
    Domain domain = Utility.signature_domain_map.get(id);
    if ((domain != null)) {
      return domain;
    }
    domain = Utility.signature_function_domain_map.get(id);
    if ((domain != null)) {
      return domain;
    }
    domain = Utility.imported_domain_map.get(id);
    if ((domain != null)) {
      return domain;
    }
    domain = Utility.imported_all_domain_map.get(id);
    if ((domain != null)) {
      return domain;
    }
    domain = Utility.signature_enum_domain_map.get(id);
    if ((domain != null)) {
      return domain;
    }
    domain = Utility.variable_domain_map.get(id);
    if ((domain != null)) {
      return domain;
    }
    return null;
  }

  public static Domain getEnumParent(final String id) {
    return Utility.signature_enum_domain_map.get(id);
  }

  public static String getOriginalAsm(final String data) {
    return Utility.imported_asm_data.get(data);
  }

  public static Function getFunction(final String id) {
    Function function = Utility.signature_function_map.get(id);
    if ((function != null)) {
      return function;
    }
    String newName = null;
    newName = id.replaceAll("Seq\\(([^\\)]*)\\)", "Seq(D)");
    newName = newName.replaceAll("Powerset\\(([^\\)]*)\\)", "Powerset(D)");
    newName = newName.replaceAll("Bag\\(([^\\)]*)\\)", "Bag(D)");
    function = Utility.imported_function_map.get(newName);
    if ((function != null)) {
      return function;
    }
    boolean _contains = newName.contains("(");
    if (_contains) {
      newName = newName.split("\\(")[1];
      newName = newName.replaceAll("\\)", "");
      Domain _domain = Utility.getDomain(newName);
      if ((_domain instanceof ConcreteDomain)) {
        Domain _domain_1 = Utility.getDomain(newName);
        ConcreteDomain newDomain = ((ConcreteDomain) _domain_1);
        if ((newDomain != null)) {
          newName = id;
          String _name = newDomain.getTypeDomain().getName();
          String _plus = ("(" + _name);
          String _plus_1 = (_plus + ")");
          newName = newName.replaceAll("\\(([^\\)]*)\\)", _plus_1);
          function = Utility.imported_function_map.get(newName);
        }
      }
    }
    function = Utility.imported_function_map.get(newName);
    if ((function != null)) {
      return function;
    }
    newName = id.replaceAll("Seq\\(([^\\)]*)\\)", "Seq(D)");
    newName = newName.replaceAll("Powerset\\(([^\\)]*)\\)", "Powerset(D)");
    newName = newName.replaceAll("Bag\\(([^\\)]*)\\)", "Bag(D)");
    function = Utility.imported_function_map.get(newName);
    if ((function != null)) {
      return function;
    }
    boolean _contains_1 = newName.contains("(");
    if (_contains_1) {
      newName = newName.split("\\(")[1];
      newName = newName.replaceAll("\\)", "");
      Domain _domain_2 = Utility.getDomain(newName);
      if ((_domain_2 instanceof ConcreteDomain)) {
        Domain _domain_3 = Utility.getDomain(newName);
        ConcreteDomain newDomain_1 = ((ConcreteDomain) _domain_3);
        if ((newDomain_1 != null)) {
          newName = id;
          String _name_1 = newDomain_1.getTypeDomain().getName();
          String _plus_2 = ("(" + _name_1);
          String _plus_3 = (_plus_2 + ")");
          newName = newName.replaceAll("\\(([^\\)]*)\\)", _plus_3);
          function = Utility.imported_function_map.get(newName);
        }
      }
    }
    return Utility.imported_function_map.get(newName);
  }

  public static Function getFunctionByName(final String id) {
    Function function = Utility.signature_name_function_map.get(id);
    if ((function != null)) {
      return function;
    }
    function = Utility.imported_name_function_map.get(id);
    if ((function != null)) {
      return function;
    }
    function = Utility.imported_all_function_map.get(id);
    if ((function != null)) {
      return function;
    }
    return null;
  }

  public static ArrayList<RuleDeclaration> getRule(final String id) {
    ArrayList<RuleDeclaration> rule = Utility.body_rule_map.get(id);
    if ((rule != null)) {
      return rule;
    }
    rule = Utility.imported_rule_map.get(id);
    if ((rule != null)) {
      return rule;
    }
    return Utility.imported_all_rule_map.get(id);
  }

  public static void resetAllMap() {
    Utility.imported_all_domain_map.clear();
    Utility.imported_all_function_map.clear();
    Utility.imported_all_function_map.clear();
    Utility.imported_asm_data.clear();
    Utility.imported_domain_map.clear();
    Utility.imported_function_map.clear();
    Utility.imported_name_function_map.clear();
    Utility.imported_rule_map.clear();
    Utility.signature_domain_map.clear();
    Utility.signature_function_domain_map.clear();
    Utility.signature_function_map.clear();
    Utility.signature_name_function_map.clear();
    Utility.body_rule_map.clear();
  }

  /**
   * Calculate the term's domain and then check if it's compatible with <code>domain</code>
   */
  public static boolean isBoolean(final Term body) {
    if ((body instanceof BinaryOperation)) {
      String[] boolean_operation = { "or", "xor", "iff", "implies", "and", "=", "!=", ">", "<", ">=", "<=" };
      for (int i = 0; (i < boolean_operation.length); i++) {
        boolean _equals = ((BinaryOperation)body).getOp().equals(boolean_operation[i]);
        if (_equals) {
          return true;
        }
      }
      return false;
    } else {
      if ((body instanceof VariableTerm)) {
        Domain domain = Utility.getDomainFromVariable(((VariableTerm) body).getName());
        if ((domain == null)) {
          return false;
        }
        return (domain.getName().equals("Boolean") || (domain instanceof BooleanDomain));
      } else {
        if ((body instanceof FunctionTerm)) {
          Function func = Utility.getFunctionByName(((FunctionTerm)body).getFunctionName());
          if (((func == null) || (func.getCodomain() == null))) {
            return false;
          }
          return (func.getCodomain().getName().equals("Boolean") || (func.getCodomain() instanceof BooleanDomain));
        } else {
          if ((body instanceof FiniteQuantificationTerm)) {
            return true;
          } else {
            if ((body instanceof BooleanTerm)) {
              return true;
            } else {
              if ((body instanceof Expression)) {
                String _op = ((Expression)body).getOp();
                boolean _tripleEquals = (_op == null);
                if (_tripleEquals) {
                  return false;
                }
                return ((Expression)body).getOp().equals("not");
              } else {
                if ((body instanceof LetTerm)) {
                  Term _body = ((LetTerm)body).getBody();
                  if ((_body instanceof BooleanTerm)) {
                    return true;
                  }
                  return false;
                }
              }
            }
          }
        }
      }
    }
    return false;
  }

  public static String getBasicTermCode(final BasicTerm term) {
    String res = "";
    if ((term instanceof FunctionTerm)) {
      res = ((FunctionTerm)term).getFunctionName();
    } else {
      if ((term instanceof ConstantTerm)) {
        res = ((ConstantTerm)term).getSymbol();
      } else {
        if ((term instanceof VariableTerm)) {
          res = ((VariableTerm)term).getName();
        }
      }
    }
    return res;
  }

  public static boolean isFromAnyDomain(final Domain domain) {
    if ((domain instanceof StructuredTD)) {
      boolean res = true;
      if ((domain instanceof RuleDomain)) {
        int _size = ((RuleDomain)domain).getDomains().size();
        boolean _greaterThan = (_size > 0);
        if (_greaterThan) {
          EList<Domain> _domains = ((RuleDomain)domain).getDomains();
          for (final Domain dom : _domains) {
            res = (res && Utility.isFromAnyDomain(dom));
          }
        }
      } else {
        if ((domain instanceof ProductDomain)) {
          EList<Domain> _domains_1 = ((ProductDomain)domain).getDomains();
          for (final Domain dom_1 : _domains_1) {
            res = (res && Utility.isFromAnyDomain(dom_1));
          }
        } else {
          if ((domain instanceof SequenceDomain)) {
            res = Utility.isFromAnyDomain(((SequenceDomain)domain).getDomain());
          } else {
            if ((domain instanceof BagDomain)) {
              res = Utility.isFromAnyDomain(((BagDomain)domain).getDomain());
            } else {
              if ((domain instanceof PowersetDomain)) {
                res = Utility.isFromAnyDomain(((PowersetDomain)domain).getBaseDomain());
              }
            }
          }
        }
      }
      return res;
    } else {
      boolean _isFromTrueAnyDomain = Utility.isFromTrueAnyDomain(domain);
      if (_isFromTrueAnyDomain) {
        return true;
      }
    }
    return false;
  }

  public static boolean isFromTrueAnyDomain(final Domain domain) {
    if ((domain == null)) {
      return false;
    }
    Domain dom = Utility.getDomain(domain.getName());
    if ((dom instanceof AnyDomain)) {
      return true;
    }
    return false;
  }

  public static boolean isCompatible(final DomainTree origin_dom, final DomainTree destination_dom) {
    HashMap<String, DefaultMutableTreeNode> any_map = new HashMap<String, DefaultMutableTreeNode>();
    Object _root = origin_dom.getModel().getRoot();
    Object _root_1 = destination_dom.getModel().getRoot();
    boolean res = Utility.isCompatible(((DefaultMutableTreeNode) _root), 
      ((DefaultMutableTreeNode) _root_1), any_map);
    return res;
  }

  public static boolean isSubsetOfAbstractDomain(final String dom1, final String dom2) {
    Domain domain1 = Utility.getDomain(dom1);
    Domain domain2 = Utility.getDomain(dom2);
    if ((((!(domain1 instanceof ConcreteDomain)) && (!(domain2 instanceof ConcreteDomain))) && (!(domain2 instanceof AbstractTD)))) {
      return true;
    }
    if ((domain1 instanceof ConcreteDomain)) {
      while (((domain1 instanceof ConcreteDomain) && (!((ConcreteDomain)domain1).getName().equals(domain2.getName())))) {
        domain1 = Utility.getDomain(((ConcreteDomain)domain1).getTypeDomain().getName());
      }
      boolean _equals = domain1.getName().equals(domain2.getName());
      if (_equals) {
        return true;
      } else {
        return false;
      }
    }
    return true;
  }

  public static boolean isCompatible(final DefaultMutableTreeNode origin_node, final DefaultMutableTreeNode destination_node, final HashMap<String, DefaultMutableTreeNode> any_map) {
    if (((origin_node == null) || (destination_node == null))) {
      return false;
    }
    if ((origin_node.toString().equals("Undef") && (!destination_node.toString().equals("Undef")))) {
      return true;
    }
    if (((!origin_node.toString().equals("Undef")) && destination_node.toString().equals("Undef"))) {
      return true;
    }
    String name_origin = origin_node.toString();
    String name_destination = destination_node.toString();
    String complete_name_origin = DomainTree.getCodeFromTree(origin_node);
    String complete_name_destination = DomainTree.getCodeFromTree(destination_node);
    if ((name_origin.equals("D") || complete_name_destination.equals("Any"))) {
      return true;
    }
    if (((name_origin == null) || (name_destination == null))) {
      return false;
    }
    if ((name_origin.isEmpty() || name_destination.isEmpty())) {
      return false;
    }
    Domain dom_source = Utility.getDomain(complete_name_origin);
    Domain dom_target = Utility.getDomain(complete_name_destination);
    if (((dom_source instanceof AnyDomain) || (dom_target instanceof AnyDomain))) {
      return true;
    }
    if (((dom_source instanceof ConcreteDomain) && (dom_target instanceof StructuredTD))) {
      String domName = (Utility.calculateDomainCode(((StructuredTD) dom_target)).split("\\(")[1]).replace(")", "");
      Domain _domain = Utility.getDomain(domName);
      if ((_domain instanceof AnyDomain)) {
        return true;
      }
      Object _root = DomainTree.getTreeFromDomain(((ConcreteDomain) dom_source).getTypeDomain()).getModel().getRoot();
      Object _root_1 = DomainTree.getTreeFromDomain(dom_target).getModel().getRoot();
      return Utility.isCompatible(((DefaultMutableTreeNode) _root), ((DefaultMutableTreeNode) _root_1), any_map);
    }
    String[] check_empty = { "Map", "Powerset", "Seq", "Bag" };
    for (final String str : check_empty) {
      {
        String name1 = str;
        String name2 = ("Any_" + str);
        if ((name_destination.equals(name1) && name_origin.equals(name2))) {
          return true;
        }
        if ((name_origin.equals(name1) && name_destination.equals(name2))) {
          return true;
        }
        if ((name_destination.equals(name1) && 
          ("Any_" + name_origin.replaceAll("\\(\\)", "")).equals(name2))) {
          return true;
        }
        if ((name_origin.equals(name1) && 
          ("Any_" + name_destination.replaceAll("\\(\\)", "")).equals(name2))) {
          return true;
        }
      }
    }
    boolean _equals = complete_name_origin.equals(complete_name_destination);
    if (_equals) {
      return true;
    } else {
      boolean dom_origin_any = Utility.isFromTrueAnyDomain(dom_source);
      if (dom_origin_any) {
        boolean _containsKey = any_map.containsKey(complete_name_origin);
        if (_containsKey) {
          DefaultMutableTreeNode dom_mapped = any_map.get(complete_name_origin);
          return Utility.isCompatible(dom_mapped, destination_node, any_map);
        } else {
          any_map.put(complete_name_origin, destination_node);
          return true;
        }
      } else {
        if (((dom_source != null) && (dom_target != null))) {
          boolean r = Utility.isCompatible(dom_source, dom_target);
          if ((!r)) {
            if (((dom_target instanceof ConcreteDomain) && ((!(dom_source instanceof AbstractTD)) || Utility.isAgentDomain(dom_source.getName())))) {
              r = Utility.isCompatible(dom_source, ((ConcreteDomain) dom_target).getTypeDomain());
            }
          }
          return r;
        } else {
          if ((dom_target instanceof ConcreteDomain)) {
            Object _root_2 = DomainTree.getTreeFromDomain(((ConcreteDomain)dom_target).getTypeDomain()).getModel().getRoot();
            HashMap<String, DefaultMutableTreeNode> _hashMap = new HashMap<String, DefaultMutableTreeNode>();
            return Utility.isCompatible(origin_node, 
              ((DefaultMutableTreeNode) _root_2), _hashMap);
          }
        }
      }
    }
    if (((origin_node.getChildCount() != destination_node.getChildCount()) && (!Utility.isSpecialCase(dom_source, dom_target)))) {
      return false;
    }
    DefaultMutableTreeNode n1 = null;
    DefaultMutableTreeNode n2 = null;
    int i = 0;
    boolean res = true;
    for (Enumeration e = origin_node.children(); e.hasMoreElements();) {
      {
        Object _nextElement = e.nextElement();
        n1 = ((DefaultMutableTreeNode) _nextElement);
        int _childCount = destination_node.getChildCount();
        boolean _lessThan = (i < _childCount);
        if (_lessThan) {
          TreeNode _childAt = destination_node.getChildAt(i);
          n2 = ((DefaultMutableTreeNode) _childAt);
          res = (res && Utility.isCompatible(n1, n2, any_map));
          i++;
        }
      }
    }
    return res;
  }

  public static boolean isSpecialCase(final Domain dom1, final Domain dom2) {
    if ((dom1 instanceof PowersetDomain)) {
      return Utility.isCompatible(((PowersetDomain)dom1).getBaseDomain(), dom2);
    } else {
      if ((dom2 instanceof PowersetDomain)) {
        return Utility.isCompatible(dom1, ((PowersetDomain)dom2).getBaseDomain());
      } else {
        return false;
      }
    }
  }

  public static boolean isCompatible(final Domain dom2, final Domain dom1) {
    if (((Utility.isFromAnyDomain(dom1) || Utility.isFromAnyDomain(dom2)) && dom1.getClass().equals(dom2.getClass()))) {
      return true;
    }
    if (((dom1 == null) || (dom2 == null))) {
      return false;
    }
    String name1 = Utility.calculateDomainCode(dom1);
    String name2 = Utility.calculateDomainCode(dom2);
    if (((name1.equals("AnyAgent") && Utility.isAgentDomain(name2)) || (name2.equals("AnyAgent") && Utility.isAgentDomain(name1)))) {
      return true;
    }
    boolean _equals = name1.equals(name2);
    if (_equals) {
      return true;
    }
    if (((dom1 instanceof ConcreteDomain) && (dom2 instanceof ConcreteDomain))) {
      String type_name1 = Utility.calculateDomainCode(((ConcreteDomain) dom1).getTypeDomain());
      String type_name2 = Utility.calculateDomainCode(((ConcreteDomain) dom2).getTypeDomain());
      boolean _equals_1 = type_name1.equals(type_name2);
      if (_equals_1) {
        return true;
      }
    } else {
      if (((dom1 instanceof ConcreteDomain) && (!(Utility.getDomain(((ConcreteDomain) dom1).getTypeDomain().getName()) instanceof AbstractTD)))) {
        String type_name1_1 = Utility.calculateDomainCode(((ConcreteDomain) dom1).getTypeDomain());
        boolean _equals_2 = type_name1_1.equals(name2);
        if (_equals_2) {
          return true;
        }
      } else {
        if ((dom2 instanceof ConcreteDomain)) {
          String type_name2_1 = Utility.calculateDomainCode(((ConcreteDomain) dom2).getTypeDomain());
          boolean _equals_3 = name1.equals(type_name2_1);
          if (_equals_3) {
            return true;
          }
        } else {
          Domain domain1 = Utility.getDomain(dom1.getName());
          Domain domain2 = Utility.getDomain(dom2.getName());
          if (((domain1 != null) && (domain2 != null))) {
            if ((((domain1 instanceof ConcreteDomain) && (!(Utility.getDomain(((ConcreteDomain) domain1).getTypeDomain().getName()) instanceof AbstractTD))) || 
              (domain2 instanceof ConcreteDomain))) {
              boolean _isCompatible = Utility.isCompatible(domain1, domain2);
              if (_isCompatible) {
                return true;
              }
            }
          }
        }
      }
    }
    if (((dom1 instanceof PowersetDomain) && (dom2 instanceof PowersetDomain))) {
      PowersetDomain power_dom1 = ((PowersetDomain) dom1);
      PowersetDomain power_dom2 = ((PowersetDomain) dom2);
      return Utility.isCompatible(power_dom1.getBaseDomain(), power_dom2.getBaseDomain());
    } else {
      if ((dom1 instanceof PowersetDomain)) {
        Domain domain1_1 = Utility.getDomain(((PowersetDomain)dom1).getBaseDomain().getName());
        Domain domain2_1 = dom2;
        if ((domain1_1 != null)) {
          return Utility.isCompatible(domain1_1, domain2_1);
        } else {
          return Utility.isCompatible(((PowersetDomain)dom1).getBaseDomain(), dom2);
        }
      } else {
        if ((dom2 instanceof PowersetDomain)) {
          Domain domain1_2 = dom1;
          Domain domain2_2 = Utility.getDomain(((PowersetDomain)dom2).getBaseDomain().getName());
          if ((domain2_2 != null)) {
            return Utility.isCompatible(domain1_2, domain2_2);
          } else {
            return Utility.isCompatible(dom1, ((PowersetDomain)dom2).getBaseDomain());
          }
        }
      }
    }
    if (((dom1 instanceof ProductDomain) && (dom2 instanceof ProductDomain))) {
      ProductDomain product_dom1 = ((ProductDomain) dom1);
      ProductDomain product_dom2 = ((ProductDomain) dom2);
      int _size = product_dom1.getDomains().size();
      int _size_1 = product_dom2.getDomains().size();
      boolean _notEquals = (_size != _size_1);
      if (_notEquals) {
        return false;
      } else {
        Boolean isComp = Boolean.valueOf(true);
        for (int i = 0; (i < product_dom1.getDomains().size()); i++) {
          boolean _isCompatible_1 = Utility.isCompatible(product_dom1.getDomains().get(i), product_dom2.getDomains().get(i));
          boolean _not = (!_isCompatible_1);
          if (_not) {
            isComp = Boolean.valueOf(false);
          }
        }
        return (isComp).booleanValue();
      }
    } else {
      if ((dom1 instanceof PowersetDomain)) {
        return false;
      } else {
        if ((dom2 instanceof PowersetDomain)) {
          return false;
        }
      }
    }
    if ((dom2 instanceof BagDomain)) {
      return Utility.isCompatible(dom1, ((BagDomain)dom2).getDomain());
    }
    if (((dom1 instanceof SequenceDomain) && (dom2 instanceof SequenceDomain))) {
      SequenceDomain seq_dom1 = ((SequenceDomain) dom1);
      SequenceDomain seq_dom2 = ((SequenceDomain) dom2);
      return Utility.isCompatible(seq_dom1, seq_dom2);
    } else {
      if ((dom1 instanceof SequenceDomain)) {
        return Utility.isCompatible(((SequenceDomain)dom1).getDomain(), dom2);
      } else {
        if ((dom2 instanceof SequenceDomain)) {
          return Utility.isCompatible(dom1, ((SequenceDomain)dom2).getDomain());
        }
      }
    }
    return false;
  }

  public static boolean isCompatibleFunction(final Domain dom2, final Domain dom1) {
    if (((Utility.isFromAnyDomain(dom1) || Utility.isFromAnyDomain(dom2)) && dom1.getClass().equals(dom2.getClass()))) {
      return true;
    }
    if (((dom1 == null) || (dom2 == null))) {
      return false;
    }
    String name1 = Utility.calculateDomainCode(dom1);
    String name2 = Utility.calculateDomainCode(dom2);
    if (((name1.equals("AnyAgent") && Utility.isAgentDomain(name2)) || (name2.equals("AnyAgent") && Utility.isAgentDomain(name1)))) {
      return true;
    }
    boolean _equals = name1.equals(name2);
    if (_equals) {
      return true;
    }
    if (((dom1 instanceof ConcreteDomain) && (dom2 instanceof ConcreteDomain))) {
      String type_name1 = Utility.calculateDomainCode(((ConcreteDomain) dom1).getTypeDomain());
      String type_name2 = Utility.calculateDomainCode(((ConcreteDomain) dom2).getTypeDomain());
      boolean _equals_1 = type_name1.equals(type_name2);
      if (_equals_1) {
        return true;
      }
    } else {
      if (((dom1 instanceof ConcreteDomain) && (!(Utility.getDomain(((ConcreteDomain) dom1).getTypeDomain().getName()) instanceof AbstractTD)))) {
        String type_name1_1 = Utility.calculateDomainCode(((ConcreteDomain) dom1).getTypeDomain());
        boolean _equals_2 = type_name1_1.equals(name2);
        if (_equals_2) {
          return true;
        }
      } else {
        if (((dom2 instanceof ConcreteDomain) && (dom1 instanceof AbstractTD))) {
          return false;
        } else {
          if ((dom2 instanceof ConcreteDomain)) {
            String type_name2_1 = Utility.calculateDomainCode(((ConcreteDomain) dom2).getTypeDomain());
            boolean _equals_3 = name1.equals(type_name2_1);
            if (_equals_3) {
              return true;
            }
          } else {
            if ((dom1 instanceof ConcreteDomain)) {
              boolean _isCompatibleFunction = Utility.isCompatibleFunction(Utility.getDomain(((ConcreteDomain)dom1).getTypeDomain().getName()), dom2);
              if (_isCompatibleFunction) {
                return true;
              }
            }
          }
        }
      }
    }
    if (((dom1 instanceof PowersetDomain) && (dom2 instanceof PowersetDomain))) {
      PowersetDomain power_dom1 = ((PowersetDomain) dom1);
      PowersetDomain power_dom2 = ((PowersetDomain) dom2);
      return Utility.isCompatibleFunction(power_dom1.getBaseDomain(), power_dom2.getBaseDomain());
    } else {
      if ((dom1 instanceof PowersetDomain)) {
        Domain domain1 = Utility.getDomain(((PowersetDomain)dom1).getBaseDomain().getName());
        Domain domain2 = dom2;
        if ((domain1 != null)) {
          return Utility.isCompatibleFunction(domain1, domain2);
        } else {
          return Utility.isCompatibleFunction(((PowersetDomain)dom1).getBaseDomain(), dom2);
        }
      } else {
        if ((dom2 instanceof PowersetDomain)) {
          Domain domain1_1 = dom1;
          Domain domain2_1 = Utility.getDomain(((PowersetDomain)dom2).getBaseDomain().getName());
          if ((domain2_1 != null)) {
            return Utility.isCompatibleFunction(domain1_1, domain2_1);
          } else {
            return Utility.isCompatibleFunction(dom1, ((PowersetDomain)dom2).getBaseDomain());
          }
        }
      }
    }
    if (((dom1 instanceof ProductDomain) && (dom2 instanceof ProductDomain))) {
      ProductDomain product_dom1 = ((ProductDomain) dom1);
      ProductDomain product_dom2 = ((ProductDomain) dom2);
      int _size = product_dom1.getDomains().size();
      int _size_1 = product_dom2.getDomains().size();
      boolean _notEquals = (_size != _size_1);
      if (_notEquals) {
        return false;
      } else {
        Boolean isComp = Boolean.valueOf(true);
        for (int i = 0; (i < product_dom1.getDomains().size()); i++) {
          boolean _isCompatibleFunction_1 = Utility.isCompatibleFunction(product_dom1.getDomains().get(i), product_dom2.getDomains().get(i));
          boolean _not = (!_isCompatibleFunction_1);
          if (_not) {
            isComp = Boolean.valueOf(false);
          }
        }
        return (isComp).booleanValue();
      }
    } else {
      if ((dom1 instanceof PowersetDomain)) {
        return false;
      } else {
        if ((dom2 instanceof PowersetDomain)) {
          return false;
        }
      }
    }
    if ((dom2 instanceof BagDomain)) {
      return Utility.isCompatibleFunction(dom1, ((BagDomain)dom2).getDomain());
    }
    if (((dom1 instanceof SequenceDomain) && (dom2 instanceof SequenceDomain))) {
      SequenceDomain seq_dom1 = ((SequenceDomain) dom1);
      SequenceDomain seq_dom2 = ((SequenceDomain) dom2);
      return Utility.isCompatibleFunction(seq_dom1, seq_dom2);
    } else {
      if ((dom1 instanceof SequenceDomain)) {
        return Utility.isCompatibleFunction(((SequenceDomain)dom1).getDomain(), dom2);
      } else {
        if ((dom2 instanceof SequenceDomain)) {
          return Utility.isCompatibleFunction(dom1, ((SequenceDomain)dom2).getDomain());
        }
      }
    }
    return false;
  }

  public static boolean isCompatible(final Domain dom1, final ArrayList<Domain> dom) {
    if ((dom1 == null)) {
      return false;
    }
    int _size = dom.size();
    boolean _tripleEquals = (_size == 0);
    if (_tripleEquals) {
      return false;
    }
    int _size_1 = dom.size();
    boolean _tripleEquals_1 = (_size_1 == 1);
    if (_tripleEquals_1) {
      return Utility.isCompatible(dom1, dom.get(0));
    }
    if ((dom1 instanceof ProductDomain)) {
      boolean res = true;
      Domain true_dom = null;
      for (int i = 0; (i < ((ProductDomain)dom1).getDomains().size()); i++) {
        {
          true_dom = Utility.getDomain(((ProductDomain)dom1).getDomains().get(i).getName());
          res = (res && Utility.isCompatible(true_dom, dom.get(i)));
        }
      }
      return res;
    }
    return false;
  }

  public static Domain getCommonDomain(final DefaultMutableTreeNode node1, final DefaultMutableTreeNode node2) {
    Domain dom1 = Utility.getDomain(DomainTree.getCodeFromTree(node1));
    Domain dom2 = Utility.getDomain(DomainTree.getCodeFromTree(node2));
    if (((dom1 == null) || (dom2 == null))) {
      if (((((node2 != null) && (node2.getUserObject() != null)) && ((node1 != null) && (node1.getUserObject() != null))) && 
        node1.getUserObject().toString().equals(("Any_" + node2.getUserObject().toString())))) {
        return dom2;
      }
      if (((((node2 != null) && (node2.getUserObject() != null)) && ((node1 != null) && (node1.getUserObject() != null))) && 
        node2.getUserObject().toString().equals(("Any_" + node1.getUserObject().toString())))) {
        return dom1;
      }
      return null;
    }
    if ((dom1 == dom2)) {
      return dom2;
    }
    if (((dom1 instanceof AnyDomain) && (dom2 instanceof AnyDomain))) {
      return dom1;
    }
    if ((dom1 instanceof AnyDomain)) {
      return dom2;
    }
    if ((dom2 instanceof AnyDomain)) {
      return dom1;
    }
    Class<? extends Domain> _class = dom1.getClass();
    Class<? extends Domain> _class_1 = dom2.getClass();
    boolean _tripleEquals = (_class == _class_1);
    if (_tripleEquals) {
      if (((dom1 instanceof ConcreteDomain) && (dom2 instanceof ConcreteDomain))) {
        ConcreteDomain conc_dom1 = ((ConcreteDomain) dom1);
        ConcreteDomain conc_dom2 = ((ConcreteDomain) dom1);
        boolean _equals = conc_dom2.getName().equals(dom1.getName());
        if (_equals) {
          return dom1;
        }
        boolean _equals_1 = conc_dom1.getName().equals(dom2.getName());
        if (_equals_1) {
          return dom2;
        }
        boolean _equals_2 = conc_dom1.getName().equals(conc_dom2.getName());
        if (_equals_2) {
          return conc_dom1;
        }
      }
      if (((dom1 instanceof StructuredTD) && (dom2 instanceof StructuredTD))) {
        String sub_dom1 = Utility.calculateDomainCode(dom1).split("\\(")[1];
        String sub_dom2 = Utility.calculateDomainCode(dom2).split("\\(")[1];
        int _length = sub_dom1.length();
        int _minus = (_length - 1);
        sub_dom1 = sub_dom1.substring(0, _minus);
        int _length_1 = sub_dom2.length();
        int _minus_1 = (_length_1 - 1);
        sub_dom2 = sub_dom2.substring(0, _minus_1);
        Domain commonDomain = Utility.getCommonDomain(DomainTree.getNodeFromDomain(Utility.getDomain(sub_dom1)), 
          DomainTree.getNodeFromDomain(Utility.getDomain(sub_dom2)));
        ArrayList<DefaultMutableTreeNode> list = new ArrayList<DefaultMutableTreeNode>();
        list.add(DomainTree.getNodeFromDomain(commonDomain));
        if ((dom1 instanceof PowersetDomain)) {
          return Utility.getDomain(DomainTree.getCodeFromTree(DomainTree.getComposedNodeFromNodes("Powerset", list)));
        } else {
          if ((dom1 instanceof SequenceDomain)) {
            return Utility.getDomain(DomainTree.getCodeFromTree(DomainTree.getComposedNodeFromNodes("Seq", list)));
          } else {
            if ((dom1 instanceof BagDomain)) {
              return Utility.getDomain(DomainTree.getCodeFromTree(DomainTree.getComposedNodeFromNodes("Bag", list)));
            } else {
              if ((dom1 instanceof MapDomain)) {
                return Utility.getDomain(DomainTree.getCodeFromTree(DomainTree.getComposedNodeFromNodes("Map", list)));
              } else {
                if ((dom1 instanceof ProductDomain)) {
                  return Utility.getDomain(DomainTree.getCodeFromTree(DomainTree.getComposedNodeFromNodes("Prod", list)));
                }
              }
            }
          }
        }
      }
    } else {
      if ((dom1.getName().equals("Undef") && (!dom2.getName().equals("Undef")))) {
        return dom2;
      }
      if ((dom2.getName().equals("Undef") && (!dom1.getName().equals("Undef")))) {
        return dom1;
      }
      if ((dom1 instanceof ConcreteDomain)) {
        boolean _equals_3 = ((ConcreteDomain)dom1).getTypeDomain().getName().equals(dom2.getName());
        if (_equals_3) {
          return dom2;
        }
      }
      if ((dom2 instanceof ConcreteDomain)) {
        boolean _equals_4 = ((ConcreteDomain)dom2).getTypeDomain().getName().equals(dom1.getName());
        if (_equals_4) {
          return dom1;
        }
      }
    }
    return null;
  }

  public static Domain getCommonDomain(final Domain dom1, final Domain dom2) {
    if (((dom1 == null) || (dom2 == null))) {
      return null;
    }
    if ((dom1 == dom2)) {
      return dom2;
    }
    boolean _equals = dom1.getName().equals("Undef");
    if (_equals) {
      return dom2;
    }
    boolean _equals_1 = dom2.getName().equals("Undef");
    if (_equals_1) {
      return dom1;
    }
    Class<? extends Domain> _class = dom1.getClass();
    Class<? extends Domain> _class_1 = dom2.getClass();
    boolean _tripleEquals = (_class == _class_1);
    if (_tripleEquals) {
      if (((dom1 instanceof ConcreteDomain) && (dom2 instanceof ConcreteDomain))) {
        ConcreteDomain conc_dom1 = ((ConcreteDomain) dom1);
        ConcreteDomain conc_dom2 = ((ConcreteDomain) dom1);
        boolean _equals_2 = conc_dom2.getName().equals(dom1.getName());
        if (_equals_2) {
          return dom1;
        }
        boolean _equals_3 = conc_dom1.getName().equals(dom2.getName());
        if (_equals_3) {
          return dom2;
        }
        boolean _equals_4 = conc_dom1.getName().equals(conc_dom2.getName());
        if (_equals_4) {
          return conc_dom1;
        }
      }
    } else {
      if ((dom1 instanceof ConcreteDomain)) {
        boolean _equals_5 = ((ConcreteDomain)dom1).getTypeDomain().getName().equals(dom2.getName());
        if (_equals_5) {
          return dom2;
        }
      }
      if ((dom2 instanceof ConcreteDomain)) {
        boolean _equals_6 = ((ConcreteDomain)dom2).getTypeDomain().getName().equals(dom1.getName());
        if (_equals_6) {
          return dom1;
        }
      }
    }
    return null;
  }

  public static Domain getCommonDomainFromListBasic(final List<BasicTerm> basic_terms) {
    ArrayList<Term> terms = new ArrayList<Term>();
    for (final BasicTerm basic : basic_terms) {
      terms.add(basic);
    }
    return Utility.getCommonDomainFromList(terms);
  }

  public static Domain getCommonDomainFromList(final List<Term> terms) {
    DomainTree tree = null;
    int _size = terms.size();
    boolean _tripleEquals = (_size == 0);
    if (_tripleEquals) {
      return null;
    }
    int _size_1 = terms.size();
    boolean _tripleEquals_1 = (_size_1 == 1);
    if (_tripleEquals_1) {
      tree = DomainCalculator.getDomainTerm(terms.get(0));
      return Utility.getDomain(tree.getCodeFromTree());
    }
    tree = DomainCalculator.getDomainTerm(terms.get(0));
    Domain base = Utility.getDomain(tree.getCodeFromTree());
    Domain dom = null;
    for (int i = 1; (i < terms.size()); i++) {
      {
        tree = DomainCalculator.getDomainTerm(terms.get(i));
        dom = Utility.getDomain(tree.getCodeFromTree());
        base = Utility.getCommonDomain(base, dom);
      }
    }
    return base;
  }

  public static boolean isAgentDomain(final String id) {
    String name_of_agent = "Agent";
    boolean _equals = id.equals(name_of_agent);
    if (_equals) {
      return true;
    }
    Domain dom = Utility.getDomain(id);
    if ((dom instanceof ConcreteDomain)) {
      return ((((ConcreteDomain)dom).getTypeDomain().getName().equals(name_of_agent) || Utility.isAgentDomain(((ConcreteDomain)dom).getTypeDomain().getName())) || (dom instanceof AgentDomain));
    } else {
      return false;
    }
  }

  public static boolean isReserveDomain(final String id) {
    String name_of_agent = "Reserve";
    boolean _equals = id.equals(name_of_agent);
    if (_equals) {
      return true;
    }
    Domain dom = Utility.getDomain(id);
    if ((dom instanceof ConcreteDomain)) {
      return ((((ConcreteDomain)dom).getTypeDomain().getName().equals(name_of_agent) || Utility.isAgentDomain(((ConcreteDomain)dom).getTypeDomain().getName())) || (dom instanceof ReserveDomain));
    } else {
      return false;
    }
  }

  public static boolean isVariableDeclaredOnceByMe(final String id_variable, final EObject parent) {
    boolean _containsKey = Utility.variable_origin_map.containsKey(id_variable);
    boolean _not = (!_containsKey);
    if (_not) {
      return true;
    }
    ArrayList<EObject> array = Utility.variable_origin_map.get(id_variable);
    int _size = array.size();
    boolean _notEquals = (_size != 1);
    if (_notEquals) {
      return false;
    }
    return array.get(0).equals(parent);
  }

  public static Domain getDomainFromVariable(final String id_variable) {
    return Utility.variable_domain_map.get(id_variable);
  }

  public static FunctionDefinition fillVariableMap(final FunctionDefinition function_definition) {
    FunctionDefinition _xblockexpression = null;
    {
      Utility.fillVariableMap(function_definition.getVariables(), function_definition.getDomain(), function_definition, true);
      _xblockexpression = Utility.fillFunctionDefinitionMap(function_definition);
    }
    return _xblockexpression;
  }

  public static FunctionDefinition fillFunctionDefinitionMap(final FunctionDefinition definition) {
    return Utility.function_declarations_map.put(definition.getDefinedFunction(), definition);
  }

  public static FunctionInitialization fillFunctionInitializationMap(final FunctionInitialization initialization) {
    return Utility.function_initialization_map.put(initialization.getInitializedFunction(), initialization);
  }

  public static void fillVariableMap(final RuleDeclaration rule_declaration) {
    Utility.fillVariableMap(rule_declaration.getVariables(), rule_declaration.getDomain(), rule_declaration, true);
  }

  public static void fillVariableMap(final LetRule let_rule) {
    Utility.fillVariableMapWithTerm(let_rule.getVariable(), let_rule.getInitExpression(), let_rule, false, true);
  }

  public static void fillVariableMap(final ChooseRule choose_rule) {
    Utility.fillVariableMapWithTerm(choose_rule.getVariable(), choose_rule.getRanges(), choose_rule, false, false);
  }

  public static void fillVariableMap(final ForallRule forall_rule) {
    Utility.fillVariableMapWithTerm(forall_rule.getVariable(), forall_rule.getRanges(), forall_rule, false, false);
  }

  public static void fillVariableMap(final ComprehensionTerm compr_term) {
    Utility.fillVariableMapWithTerm(compr_term.getVariable(), compr_term.getRanges(), compr_term, false, true);
  }

  public static void fillVariableMap(final ExtendRule extend_rule) {
    String domain_code = extend_rule.getExtendedDomain().getName();
    Domain dom = Utility.getDomain(domain_code);
    EList<VariableTerm> _boundVar = extend_rule.getBoundVar();
    for (final VariableTerm variable : _boundVar) {
      {
        Utility.variable_domain_map.put(variable.getName(), dom);
        Utility.addToVariableOrigin(extend_rule, variable.getName());
        variable.setKind(VariableKind.RULE_VAR);
      }
    }
  }

  public static void addToVariableOrigin(final EObject parent, final String variable) {
    ArrayList<EObject> array = null;
    boolean _containsKey = Utility.variable_origin_map.containsKey(variable);
    boolean _not = (!_containsKey);
    if (_not) {
      ArrayList<EObject> _arrayList = new ArrayList<EObject>();
      array = _arrayList;
      array.add(parent);
      Utility.variable_origin_map.put(variable, array);
    } else {
      array = Utility.variable_origin_map.get(parent);
      if ((array == null)) {
        ArrayList<EObject> _arrayList_1 = new ArrayList<EObject>();
        array = _arrayList_1;
        array.add(parent);
      }
    }
  }

  public static void resetVariableMap() {
    Utility.variable_domain_map.clear();
    Utility.variable_origin_map.clear();
  }

  public static void fillVariableMap(final LetTerm let_term) {
    Utility.fillVariableMapWithTerm(let_term.getVariable(), let_term.getAssignmentTerm(), let_term, false, false);
  }

  public static void fillVariableMap(final FiniteQuantificationTerm finite_term) {
    Utility.fillVariableMapWithTerm(finite_term.getVariable(), finite_term.getRanges(), finite_term, false, false);
  }

  public static void fillVariableMap(final FunctionInitialization function_init) {
    Utility.fillVariableMap(function_init.getVariables(), function_init.getDomain(), function_init, true);
  }

  public static Function fillVariableMap(final LocalFunction function_init) {
    return Utility.signature_name_function_map.put(function_init.getName(), function_init);
  }

  public static void fillVariableMapWithTerm(final EList<VariableTerm> variable_list, final EList<Term> term_list, final EObject parent, final boolean reset, final boolean fromComprehensionTerm) {
    if (reset) {
      Utility.variable_domain_map.clear();
      Utility.variable_origin_map.clear();
    }
    String variable = null;
    Domain dom = null;
    DomainTree tree = null;
    String domainAsString = null;
    for (int i = 0; (i < variable_list.size()); i++) {
      {
        variable = variable_list.get(i).getName();
        tree = DomainCalculator.getDomainTerm(term_list.get(i));
        boolean toBeSplitted = true;
        if (((((parent instanceof ChooseRule) || (parent instanceof ForallRule)) || (parent instanceof ForallTerm)) || 
          (parent instanceof ComprehensionTerm))) {
          if ((parent instanceof ChooseRule)) {
            Term _get = ((ChooseRule)parent).getRanges().get(i);
            if ((_get instanceof Domain)) {
              toBeSplitted = false;
            }
          } else {
            if ((parent instanceof ForallRule)) {
              Term _get_1 = ((ForallRule)parent).getRanges().get(i);
              if ((_get_1 instanceof Domain)) {
                toBeSplitted = false;
              }
            } else {
              if ((parent instanceof ForallTerm)) {
                Term _get_2 = ((ForallTerm)parent).getRanges().get(i);
                if ((_get_2 instanceof Domain)) {
                  toBeSplitted = false;
                }
              } else {
                if ((parent instanceof ComprehensionTerm)) {
                  Term _get_3 = ((ComprehensionTerm)parent).getRanges().get(i);
                  if ((_get_3 instanceof Domain)) {
                    toBeSplitted = false;
                  }
                }
              }
            }
          }
          if (((toBeSplitted && (tree.getModel() != null)) && (tree.getModel().getRoot() != null))) {
            Object treeModel = tree.getModel().getRoot();
            boolean _equals = treeModel.toString().equals("Powerset");
            if (_equals) {
              treeModel = tree.getModel().getChild(treeModel, 0);
              if (((treeModel instanceof DefaultMutableTreeNode) && 
                (((DefaultMutableTreeNode) treeModel).getChildCount() > 0))) {
                ArrayList<DefaultMutableTreeNode> list = new ArrayList<DefaultMutableTreeNode>();
                for (int k = 0; (k < ((DefaultMutableTreeNode) treeModel).getChildCount()); k++) {
                  TreeNode _childAt = ((DefaultMutableTreeNode) treeModel).getChildAt(k);
                  list.add(((DefaultMutableTreeNode) _childAt));
                }
                DefaultMutableTreeNode new_root = DomainTree.getComposedNodeFromNodes("Powerset", list);
                DomainTree _domainTree = new DomainTree(new_root);
                tree = _domainTree;
              } else {
                tree = DomainCalculator.getDomainTerm(Utility.getDomain(treeModel.toString()));
              }
            } else {
              boolean _equals_1 = treeModel.toString().equals("Bag");
              if (_equals_1) {
                treeModel = tree.getModel().getChild(treeModel, 0);
                if (((treeModel instanceof DefaultMutableTreeNode) && 
                  (((DefaultMutableTreeNode) treeModel).getChildCount() > 0))) {
                  ArrayList<DefaultMutableTreeNode> list_1 = new ArrayList<DefaultMutableTreeNode>();
                  for (int k = 0; (k < ((DefaultMutableTreeNode) treeModel).getChildCount()); k++) {
                    TreeNode _childAt = ((DefaultMutableTreeNode) treeModel).getChildAt(k);
                    list_1.add(((DefaultMutableTreeNode) _childAt));
                  }
                  DefaultMutableTreeNode new_root_1 = DomainTree.getComposedNodeFromNodes("Bag", list_1);
                  DomainTree _domainTree_1 = new DomainTree(new_root_1);
                  tree = _domainTree_1;
                } else {
                  tree = DomainCalculator.getDomainTerm(Utility.getDomain(treeModel.toString()));
                }
              } else {
                if ((((treeModel != null) && (!treeModel.toString().equals("Seq"))) && (!treeModel.toString().equals("Bag")))) {
                  tree = DomainCalculator.getDomainTerm(Utility.getDomain(treeModel.toString()));
                }
              }
            }
          }
        }
        domainAsString = tree.getCodeFromTree();
        if (domainAsString != null) {
          switch (domainAsString) {
            case "Any_Seq":
              domainAsString = "Seq(D)";
              break;
            case "Any_Powerset":
              domainAsString = "Powerset(D)";
              break;
            case "Any_Bag":
              domainAsString = "Bag(D)";
              break;
          }
        }
        dom = Utility.getDomain(domainAsString);
        if ((((dom == null) && (tree.getModel() != null)) && (tree.getModel().getRoot() != null))) {
          HashSet<String> types = new HashSet<String>();
          for (int j = 0; (j < tree.getModel().getChildCount(tree.getModel().getRoot())); j++) {
            types.add(tree.getModel().getChild(tree.getModel().getRoot(), j).toString());
          }
          int j = 0;
          for (final String s : types) {
            {
              Object _xifexpression = null;
              if ((j == 0)) {
                _xifexpression = "";
              } else {
                _xifexpression = Integer.valueOf(j);
              }
              String _plus = ("D" + _xifexpression);
              domainAsString = domainAsString.replace(s, _plus);
              j++;
            }
          }
          dom = Utility.getDomain(domainAsString);
        }
        if (((fromComprehensionTerm && (dom instanceof SequenceDomain)) && (!domainAsString.equals("Seq(D)")))) {
          dom = Utility.getDomain((tree.getCodeFromTree().split("\\(")[1]).replace(")", ""));
        }
        if ((fromComprehensionTerm && ((dom == null) && domainAsString.startsWith("Powerset(")))) {
          dom = Utility.getDomain((tree.getCodeFromTree().split("\\(")[1]).replace(")", ""));
        }
        Utility.variable_domain_map.put(variable, dom);
        Utility.addToVariableOrigin(parent, variable);
      }
    }
  }

  public static void fillVariableMap(final EList<String> variable_list, final EList<Domain> domain_list, final EObject parent, final boolean reset) {
    if (reset) {
      Utility.variable_domain_map.clear();
      Utility.variable_origin_map.clear();
    }
    String variable = null;
    String domain_code = null;
    Domain dom = null;
    for (int i = 0; (i < variable_list.size()); i++) {
      {
        variable = variable_list.get(i);
        domain_code = Utility.calculateDomainCode(domain_list.get(i));
        dom = Utility.getDomain(domain_code);
        if (((((dom == null) && (domain_code != null)) && (!domain_code.equals(""))) && (domain_list.get(i) != null))) {
          Utility.signature_domain_map.put(domain_code, domain_list.get(i));
          dom = Utility.getDomain(domain_code);
        }
        Utility.variable_domain_map.put(variable, dom);
        Utility.addToVariableOrigin(parent, variable);
      }
    }
  }

  public static String getOperandFunctionName(final String op) {
    if (op != null) {
      switch (op) {
        case "+":
          return "plus";
        case "-":
          return "minus";
        case "*":
          return "mult";
        case "/":
          return "div";
        case ">":
          return "gt";
        case ">=":
          return "ge";
        case "<":
          return "lt";
        case "<=":
          return "le";
        case "^":
          return "pow";
        case "=":
          return "eq";
        case "!=":
          return "neq";
        default:
          return op;
      }
    } else {
      return op;
    }
  }

  public static String getRightDomainNameForBinary(final Domain domain) {
    String res = null;
    if ((domain == null)) {
      res = "null";
    } else {
      if ((domain instanceof ConcreteDomain)) {
        res = ((ConcreteDomain)domain).getTypeDomain().getName();
      } else {
        if ((domain instanceof PowersetDomain)) {
          res = ((PowersetDomain)domain).getBaseDomain().getName();
        } else {
          res = domain.getName();
        }
      }
    }
    return res;
  }

  public static Integer calculateFunctionArity(final Function function) {
    Domain domain = function.getDomain();
    if ((domain == null)) {
      return Integer.valueOf(0);
    }
    if ((domain instanceof ProductDomain)) {
      return Integer.valueOf(((ProductDomain)domain).getDomains().size());
    }
    return Integer.valueOf(1);
  }

  public static boolean symbolExist(final String symbol) {
    return Utility.signature_enum_domain_map.containsKey(symbol);
  }

  public static ArrayList<Function> getListOfPossibleFunction(final String function_name) {
    ArrayList<Function> imported = Utility.imported_list_function_map.get(function_name);
    ArrayList<Function> declared = Utility.signature_list_function_map.get(function_name);
    if ((imported == null)) {
      return declared;
    }
    if ((declared == null)) {
      return imported;
    }
    ArrayList<Function> result = new ArrayList<Function>();
    for (final Function f : imported) {
      result.add(f);
    }
    for (final Function f_1 : declared) {
      result.add(f_1);
    }
    return result;
  }

  public static Function searchForMostCompatible(final ArrayList<Function> list, final DomainTree function_domain_tree) {
    DomainTree domain_tree = null;
    for (final Function function : list) {
      {
        domain_tree = DomainTree.getTreeFromDomain(function.getDomain());
        boolean _isCompatible = Utility.isCompatible(function_domain_tree, domain_tree);
        if (_isCompatible) {
          Object _root = domain_tree.getModel().getRoot();
          Domain dom1 = Utility.getDomain(DomainTree.getCodeFromTree(((TreeNode) _root)));
          Object _root_1 = function_domain_tree.getModel().getRoot();
          Domain dom2 = Utility.getDomain(DomainTree.getCodeFromTree(((TreeNode) _root_1)));
          if ((((dom1 instanceof AbstractTD) || (dom1 instanceof ConcreteDomain)) && ((dom2 instanceof AbstractTD) || (dom2 instanceof ConcreteDomain)))) {
            boolean _isCompatibleFunction = Utility.isCompatibleFunction(dom1, dom2);
            if (_isCompatibleFunction) {
              return function;
            }
          } else {
            return function;
          }
        }
      }
    }
    return null;
  }

  public static RuleDeclaration searchForMostCompatibleRule(final ArrayList<RuleDeclaration> list, final List<Domain> domainList) {
    DomainTree domain_tree = null;
    for (final RuleDeclaration rule : list) {
      int _length = ((Object[])Conversions.unwrapArray(rule.getDomain(), Object.class)).length;
      int _length_1 = ((Object[])Conversions.unwrapArray(domainList, Object.class)).length;
      boolean _equals = (_length == _length_1);
      if (_equals) {
        boolean comp = true;
        for (int i = 0; (i < ((Object[])Conversions.unwrapArray(rule.getDomain(), Object.class)).length); i++) {
          DefaultMutableTreeNode _nodeFromDomain = DomainTree.getNodeFromDomain(domainList.get(i));
          DefaultMutableTreeNode _nodeFromDomain_1 = DomainTree.getNodeFromDomain(rule.getDomain().get(i));
          HashMap<String, DefaultMutableTreeNode> _hashMap = new HashMap<String, DefaultMutableTreeNode>();
          boolean _isCompatible = Utility.isCompatible(_nodeFromDomain, _nodeFromDomain_1, _hashMap);
          boolean _not = (!_isCompatible);
          if (_not) {
            comp = false;
          }
        }
        if (comp) {
          return rule;
        }
      }
    }
    return null;
  }

  public static String getStringFromTerm(final Term term) {
    if ((term instanceof BasicTerm)) {
      String res = null;
      if ((term instanceof FunctionTerm)) {
        res = ((FunctionTerm)term).getFunctionName();
      } else {
        if ((term instanceof ConstantTerm)) {
          boolean _endsWith = ((ConstantTerm)term).getSymbol().endsWith("n");
          if (_endsWith) {
            String _symbol = ((ConstantTerm)term).getSymbol();
            int _length = ((ConstantTerm)term).getSymbol().length();
            int _minus = (_length - 1);
            res = _symbol.substring(0, _minus);
          } else {
            res = ((ConstantTerm)term).getSymbol();
          }
        } else {
          if ((term instanceof VariableTerm)) {
            res = ((VariableTerm)term).getName();
          }
        }
      }
      return res;
    } else {
      return "null";
    }
  }

  public static boolean isPowerSetDomain(final Term range) {
    if ((range instanceof FunctionTerm)) {
      Domain domain = Utility.getDomain(((FunctionTerm)range).getFunctionName());
      if (((((FunctionTerm)range).getArguments() == null) && (domain != null))) {
        return true;
      }
      Function function = DomainCalculator.getFunctionFromFunctionTerm(((FunctionTerm)range));
      if ((function == null)) {
        return false;
      }
      Domain dom = Utility.getDomain(Utility.calculateDomainCode(function.getCodomain()));
      if ((dom == null)) {
        return false;
      }
      if ((dom instanceof ConcreteDomain)) {
        dom = ((ConcreteDomain)dom).getTypeDomain();
      }
      return ((dom instanceof AnyDomain) || (dom instanceof PowersetDomain));
    } else {
      if ((range instanceof VariableTerm)) {
        Domain dom_1 = Utility.getDomainFromVariable(((VariableTerm)range).getName());
        if ((dom_1 == null)) {
          return false;
        }
        return ((dom_1 instanceof AnyDomain) || (dom_1 instanceof PowersetDomain));
      } else {
        if ((((range instanceof SetTerm) || (range instanceof PowersetDomain)) || 
          (range instanceof Domain))) {
          return true;
        } else {
          return false;
        }
      }
    }
  }

  public static boolean isBagDomain(final Term range) {
    if ((range instanceof FunctionTerm)) {
      Domain domain = Utility.getDomain(((FunctionTerm)range).getFunctionName());
      if (((((FunctionTerm)range).getArguments() == null) && (domain != null))) {
        return true;
      }
      Function function = DomainCalculator.getFunctionFromFunctionTerm(((FunctionTerm)range));
      if ((function == null)) {
        return false;
      }
      Domain dom = Utility.getDomain(Utility.calculateDomainCode(function.getCodomain()));
      if ((dom == null)) {
        return false;
      }
      return ((dom instanceof AnyDomain) || (dom instanceof BagDomain));
    } else {
      if ((range instanceof VariableTerm)) {
        Domain dom_1 = Utility.getDomainFromVariable(((VariableTerm)range).getName());
        if ((dom_1 == null)) {
          return false;
        }
        return ((dom_1 instanceof AnyDomain) || (dom_1 instanceof BagDomain));
      } else {
        if ((range instanceof BagTerm)) {
          return true;
        } else {
          return false;
        }
      }
    }
  }

  public static boolean isSequenceDomain(final Term range) {
    if ((range instanceof FunctionTerm)) {
      Domain domain = Utility.getDomain(((FunctionTerm)range).getFunctionName());
      if (((((FunctionTerm)range).getArguments() == null) && (domain != null))) {
        return true;
      }
      Function function = DomainCalculator.getFunctionFromFunctionTerm(((FunctionTerm)range));
      if ((function == null)) {
        return false;
      }
      Domain dom = Utility.getDomain(Utility.calculateDomainCode(function.getCodomain()));
      if ((dom == null)) {
        return false;
      }
      return ((dom instanceof AnyDomain) || (dom instanceof SequenceDomain));
    } else {
      if ((range instanceof VariableTerm)) {
        Domain dom_1 = Utility.getDomainFromVariable(((VariableTerm)range).getName());
        if ((dom_1 == null)) {
          return false;
        }
        return ((dom_1 instanceof AnyDomain) || (dom_1 instanceof SequenceDomain));
      } else {
        if ((range instanceof SequenceDomain)) {
          return true;
        } else {
          return false;
        }
      }
    }
  }

  /**
   * Get the String representation of a rule
   * @return A string representation of the rule: {rule_name} '(' [domain ',' domain] )
   */
  public static String getRuleaAsString(final RuleDeclaration rule_declaration) {
    String domains_string = "";
    EList<Domain> _domain = rule_declaration.getDomain();
    for (final Domain d : _domain) {
      String _domains_string = domains_string;
      String _calculateDomainCode = Utility.calculateDomainCode(d);
      String _plus = (_calculateDomainCode + ",");
      domains_string = (_domains_string + _plus);
    }
    int _length = domains_string.length();
    boolean _greaterThan = (_length > 0);
    if (_greaterThan) {
      domains_string = domains_string.substring(0, domains_string.length());
    }
    String _name = rule_declaration.getName();
    String _plus_1 = (_name + "(");
    String _plus_2 = (_plus_1 + domains_string);
    return (_plus_2 + ")");
  }

  public static boolean isDomainName(final String s) {
    boolean res = false;
    if (((Utility.signature_domain_map.containsKey(s) || Utility.imported_all_domain_map.containsKey(s)) || 
      Utility.imported_domain_map.containsKey(s))) {
      res = true;
    }
    return res;
  }

  public static ArrayList<DomainTree> getDomainTreeListFromListOfFunctions(final ArrayList<Function> list) {
    ArrayList<DomainTree> treeList = new ArrayList<DomainTree>();
    DefaultMutableTreeNode res = null;
    String domain_code = null;
    Domain dom = null;
    for (final Function function : list) {
      {
        domain_code = Utility.calculateDomainCode(function.getCodomain());
        dom = Utility.getDomain(domain_code);
        boolean _isFromAnyDomain = Utility.isFromAnyDomain(dom);
        if (_isFromAnyDomain) {
          res = DomainTree.getNodeFromDomain(dom);
        } else {
          res = DomainTree.getNodeFromDomain(dom);
        }
        DomainTree _domainTree = new DomainTree(res);
        treeList.add(_domainTree);
      }
    }
    return treeList;
  }

  public static Boolean existOneCompatible(final Term location, final Term updatingTerm) {
    DomainTree location_domain = null;
    ArrayList<DomainTree> locationList = new ArrayList<DomainTree>();
    DomainTree update_domain = null;
    ArrayList<DomainTree> updateList = new ArrayList<DomainTree>();
    Boolean compatible = Boolean.valueOf(false);
    if ((!((location instanceof FunctionTerm) || (location instanceof LocationTerm)))) {
      location_domain = DomainCalculator.getDomainTerm(location);
      locationList.add(location_domain);
    } else {
      ArrayList<Function> tempList = new ArrayList<Function>();
      if ((location instanceof FunctionTerm)) {
        tempList = DomainCalculator.getAllFunctionsFromFunctionTerm(((FunctionTerm) location));
      } else {
        tempList = DomainCalculator.getAllFunctionsFromFunctionTerm(((LocationTerm) location));
      }
      if ((tempList == null)) {
        return Boolean.valueOf(false);
      }
      locationList = Utility.getDomainTreeListFromListOfFunctions(tempList);
    }
    if ((!((updatingTerm instanceof FunctionTerm) || (updatingTerm instanceof LocationTerm)))) {
      update_domain = DomainCalculator.getDomainTerm(updatingTerm);
      updateList.add(update_domain);
    } else {
      ArrayList<Function> tempList_1 = new ArrayList<Function>();
      if ((updatingTerm instanceof FunctionTerm)) {
        tempList_1 = DomainCalculator.getAllFunctionsFromFunctionTerm(((FunctionTerm) updatingTerm));
      } else {
        tempList_1 = DomainCalculator.getAllFunctionsFromFunctionTerm(((LocationTerm) updatingTerm));
      }
    }
    for (final DomainTree dom1 : locationList) {
      for (final DomainTree dom2 : updateList) {
        boolean _isCompatible = Utility.isCompatible(dom1, dom2);
        if (_isCompatible) {
          compatible = Boolean.valueOf(true);
        }
      }
    }
    return compatible;
  }

  public static FunctionTerm getFunctionTermFromBO(final BinaryOperation bo) {
    boolean _containsKey = Utility.binary_operation_conversion_map.containsKey(bo);
    if (_containsKey) {
      return Utility.binary_operation_conversion_map.get(bo);
    }
    FunctionTerm ft = AsmetalFactory.eINSTANCE.createFunctionTerm();
    String fn = Utility.getOperandFunctionName(bo.getOp());
    Function function = Utility.getFunctionByName(fn);
    ft.setFunction(function);
    TupleTerm tt = AsmetalFactory.eINSTANCE.createTupleTerm();
    Expression _left = bo.getLeft();
    boolean _tripleNotEquals = (_left != null);
    if (_tripleNotEquals) {
      tt.getTerms().add(bo.getLeft());
    }
    Expression _right = bo.getRight();
    boolean _tripleNotEquals_1 = (_right != null);
    if (_tripleNotEquals_1) {
      tt.getTerms().add(bo.getRight());
    }
    ft.setArguments(tt);
    Utility.binary_operation_conversion_map.put(bo, ft);
    return ft;
  }

  public static FunctionTerm getFunctionTermFromExpression(final Expression exp) {
    boolean _containsKey = Utility.expression_conversion_map.containsKey(exp);
    if (_containsKey) {
      return Utility.expression_conversion_map.get(exp);
    }
    FunctionTerm ft = AsmetalFactory.eINSTANCE.createFunctionTerm();
    String fn = Utility.getOperandFunctionName(exp.getOp());
    Function function = Utility.getFunctionByName(fn);
    ft.setFunction(function);
    TupleTerm tt = AsmetalFactory.eINSTANCE.createTupleTerm();
    tt.getTerms().add(exp.getOperand());
    ft.setArguments(tt);
    Utility.expression_conversion_map.put(exp, ft);
    return ft;
  }

  public static void changeFunctionsInDomains(final FiniteQuantificationTerm term) {
    InternalEList<Term> rangesTermList = new EObjectEList<Term>(Term.class, ((InternalEObject) term), 
      AsmetalPackage.FINITE_QUANTIFICATION_TERM__RANGES);
    EList<Term> _ranges = term.getRanges();
    for (final Term t : _ranges) {
      if (((!Utility.isFunctionName(Utility.getStringFromTerm(t))) && Utility.isDomainName(Utility.getStringFromTerm(t)))) {
        String name = Utility.getStringFromTerm(t);
        Domain d = Utility.getDomain(name);
        DomainTerm nt = null;
        nt = AsmetalFactory.eINSTANCE.createDomain();
        nt.setDomain(d);
        rangesTermList.add(nt);
      } else {
        rangesTermList.add(t);
      }
    }
    term.setRanges(rangesTermList);
  }

  public static void changeFunctionsInDomains(final ChooseRule term) {
    InternalEList<Term> rangesTermList = new EObjectEList<Term>(Term.class, ((InternalEObject) term), 
      AsmetalPackage.FINITE_QUANTIFICATION_TERM__RANGES);
    EList<Term> _ranges = term.getRanges();
    for (final Term t : _ranges) {
      if (((!Utility.isFunctionName(Utility.getStringFromTerm(t))) && Utility.isDomainName(Utility.getStringFromTerm(t)))) {
        String name = Utility.getStringFromTerm(t);
        Domain d = Utility.getDomain(name);
        DomainTerm nt = null;
        nt = AsmetalFactory.eINSTANCE.createDomain();
        nt.setDomain(d);
        rangesTermList.add(nt);
      } else {
        rangesTermList.add(t);
      }
    }
    term.setRanges(rangesTermList);
  }
}
