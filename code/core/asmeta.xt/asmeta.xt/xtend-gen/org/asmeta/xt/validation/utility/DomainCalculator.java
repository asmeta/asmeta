package org.asmeta.xt.validation.utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import org.asmeta.xt.asmetal.AgentDomain;
import org.asmeta.xt.asmetal.AsmetalFactory;
import org.asmeta.xt.asmetal.BagCT;
import org.asmeta.xt.asmetal.BagDomain;
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
import org.asmeta.xt.asmetal.LocationTerm;
import org.asmeta.xt.asmetal.MapCT;
import org.asmeta.xt.asmetal.MapTerm;
import org.asmeta.xt.asmetal.NaturalTerm;
import org.asmeta.xt.asmetal.RealTerm;
import org.asmeta.xt.asmetal.RuleAsTerm;
import org.asmeta.xt.asmetal.SequenceCT;
import org.asmeta.xt.asmetal.SequenceDomain;
import org.asmeta.xt.asmetal.SequenceTerm;
import org.asmeta.xt.asmetal.SetCT;
import org.asmeta.xt.asmetal.SetTerm;
import org.asmeta.xt.asmetal.StringTerm;
import org.asmeta.xt.asmetal.StructuredTD;
import org.asmeta.xt.asmetal.Term;
import org.asmeta.xt.asmetal.TupleTerm;
import org.asmeta.xt.asmetal.UndefTerm;
import org.asmeta.xt.asmetal.UpdateRule;
import org.asmeta.xt.asmetal.VariableBindingTerm;
import org.asmeta.xt.asmetal.VariableTerm;
import org.asmeta.xt.asmetal.basicExpr;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class DomainCalculator {
  public static DomainTree getDomainTerm(final Term term) {
    DefaultMutableTreeNode _root = DomainCalculator.getDomainTermNode(term);
    DomainTree tree = new DomainTree(_root);
    return tree;
  }

  public static DefaultMutableTreeNode getDomainTermNode(final Term term) {
    DefaultMutableTreeNode res = null;
    if ((term instanceof FunctionTerm)) {
      Domain _domain = Utility.getDomain(((FunctionTerm)term).getFunctionName());
      boolean _tripleNotEquals = (_domain != null);
      if (_tripleNotEquals) {
        return DomainTree.getNodeFromDomain(Utility.getDomain(((FunctionTerm)term).getFunctionName()));
      }
    }
    if ((term instanceof Expression)) {
      res = DomainCalculator.getDomainExpression(((Expression)term));
    } else {
      if ((term instanceof ExtendedTerm)) {
        res = DomainCalculator.getDomainExtendedTerm(((ExtendedTerm)term));
      } else {
        if ((term instanceof Domain)) {
          res = DomainTree.getNodeFromDomain(((Domain)term));
        } else {
          res = null;
        }
      }
    }
    return res;
  }

  public static DefaultMutableTreeNode getDomainExpression(final Expression term) {
    DefaultMutableTreeNode res = null;
    if ((term instanceof BinaryOperation)) {
      res = DomainCalculator.getDomainBinaryOperation(((BinaryOperation)term));
    } else {
      if ((term instanceof basicExpr)) {
        res = DomainCalculator.getDomainbasicExpr(((basicExpr)term));
      } else {
        res = DomainCalculator.getDomainUnaryOperation(term);
      }
    }
    return res;
  }

  public static DefaultMutableTreeNode getDomainBinaryOperation(final BinaryOperation term) {
    List<String> boolean_operators = Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("or", "xor", "iff", "implies", "and", "=", "!=", ">", ">=", "<", "<="));
    for (final String operator : boolean_operators) {
      boolean _equals = term.getOp().equals(operator);
      if (_equals) {
        return DomainTree.getNodeFromString("Boolean");
      }
    }
    DefaultMutableTreeNode dom_left = DomainCalculator.getDomainTermNode(term.getLeft());
    DefaultMutableTreeNode dom_right = DomainCalculator.getDomainTermNode(term.getRight());
    if (((((dom_left != null) && (dom_right != null)) && (dom_left.getUserObject() != null)) && (dom_right.getUserObject() != null))) {
      String _operandFunctionName = Utility.getOperandFunctionName(term.getOp());
      String _plus = (_operandFunctionName + "(Prod(");
      String _string = dom_left.getUserObject().toString();
      String _plus_1 = (_plus + _string);
      String _plus_2 = (_plus_1 + ",");
      String _string_1 = dom_right.getUserObject().toString();
      String _plus_3 = (_plus_2 + _string_1);
      String functionName = (_plus_3 + "))");
      Function functionDomain = Utility.getFunction(functionName);
      if ((functionDomain != null)) {
        return DomainTree.getNodeFromDomain(functionDomain.getCodomain());
      }
    }
    Domain common = Utility.getCommonDomain(dom_left, dom_right);
    return DomainTree.getNodeFromDomain(common);
  }

  public static DefaultMutableTreeNode getDomainUnaryOperation(final Expression term) {
    boolean _equals = term.getOp().equals("not");
    if (_equals) {
      return DomainTree.getNodeFromString("Boolean");
    }
    return DomainCalculator.getDomainTermNode(term.getOperand());
  }

  public static DefaultMutableTreeNode getDomainbasicExpr(final basicExpr term) {
    DefaultMutableTreeNode res = null;
    if ((term instanceof BasicTerm)) {
      res = DomainCalculator.getDomainBasicTerm(((BasicTerm)term));
    } else {
      if ((term instanceof FiniteQuantificationTerm)) {
        res = DomainCalculator.getDomainFiniteQuantificationTerm(((FiniteQuantificationTerm)term));
      }
    }
    return res;
  }

  public static DefaultMutableTreeNode getDomainBasicTerm(final BasicTerm term) {
    DefaultMutableTreeNode res = null;
    if ((term instanceof FunctionTerm)) {
      res = DomainCalculator.getDomainFunctionTerm(((FunctionTerm)term));
    } else {
      if ((term instanceof LocationTerm)) {
        res = DomainCalculator.getDomainLocationTerm(((LocationTerm)term));
      } else {
        if ((term instanceof ConstantTerm)) {
          res = DomainCalculator.getDomainConstantTerm(((ConstantTerm)term));
        } else {
          if ((term instanceof VariableTerm)) {
            res = DomainCalculator.getDomainVariableTerm(((VariableTerm)term));
          }
        }
      }
    }
    return res;
  }

  public static Function getFunctionFromFunctionTerm(final FunctionTerm term) {
    String complete_function_name = null;
    Function function = null;
    TupleTerm _arguments = term.getArguments();
    boolean _tripleEquals = (_arguments == null);
    if (_tripleEquals) {
      complete_function_name = term.getFunctionName();
      function = Utility.getFunctionByName(complete_function_name);
    } else {
      DefaultMutableTreeNode domain_arguments = DomainCalculator.getDomainTupleTerm(term.getArguments());
      String _functionName = term.getFunctionName();
      String _plus = (_functionName + "(");
      String _codeFromTree = DomainTree.getCodeFromTree(domain_arguments);
      String _plus_1 = (_plus + _codeFromTree);
      String _plus_2 = (_plus_1 + ")");
      complete_function_name = _plus_2;
      function = Utility.getFunction(complete_function_name);
      if ((function == null)) {
        DomainTree tree = new DomainTree(domain_arguments);
        ArrayList<Function> list = Utility.getListOfPossibleFunction(term.getFunctionName());
        if ((list == null)) {
          return null;
        }
        function = Utility.searchForMostCompatible(list, tree);
      }
    }
    return function;
  }

  public static Function getFunctionFromFunctionTerm(final LocationTerm term) {
    String complete_function_name = null;
    Function function = null;
    TupleTerm _arguments = term.getArguments();
    boolean _tripleEquals = (_arguments == null);
    if (_tripleEquals) {
      complete_function_name = term.getFunctionName();
      function = Utility.getFunctionByName(complete_function_name);
    } else {
      DefaultMutableTreeNode domain_arguments = DomainCalculator.getDomainTupleTerm(term.getArguments());
      String _functionName = term.getFunctionName();
      String _plus = (_functionName + "(");
      String _codeFromTree = DomainTree.getCodeFromTree(domain_arguments);
      String _plus_1 = (_plus + _codeFromTree);
      String _plus_2 = (_plus_1 + ")");
      complete_function_name = _plus_2;
      function = Utility.getFunction(complete_function_name);
      if ((function == null)) {
        DomainTree tree = new DomainTree(domain_arguments);
        ArrayList<Function> list = Utility.getListOfPossibleFunction(term.getFunctionName());
        if ((list == null)) {
          return null;
        }
        function = Utility.searchForMostCompatible(list, tree);
      }
    }
    return function;
  }

  public static ArrayList<Function> getAllFunctionsFromFunctionTerm(final FunctionTerm term) {
    String complete_function_name = null;
    Function function = null;
    ArrayList<Function> listFunctions = new ArrayList<Function>();
    TupleTerm _arguments = term.getArguments();
    boolean _tripleEquals = (_arguments == null);
    if (_tripleEquals) {
      complete_function_name = term.getFunctionName();
      function = Utility.getFunctionByName(complete_function_name);
    } else {
      DefaultMutableTreeNode domain_arguments = DomainCalculator.getDomainTupleTerm(term.getArguments());
      String _functionName = term.getFunctionName();
      String _plus = (_functionName + "(");
      String _codeFromTree = DomainTree.getCodeFromTree(domain_arguments);
      String _plus_1 = (_plus + _codeFromTree);
      String _plus_2 = (_plus_1 + ")");
      complete_function_name = _plus_2;
      function = Utility.getFunction(complete_function_name);
      if ((function == null)) {
        DomainTree tree = new DomainTree(domain_arguments);
        ArrayList<Function> list = Utility.getListOfPossibleFunction(term.getFunctionName());
        if ((list == null)) {
          return null;
        }
        listFunctions = list;
      }
    }
    return listFunctions;
  }

  public static ArrayList<Function> getAllFunctionsFromFunctionTerm(final LocationTerm term) {
    String complete_function_name = null;
    Function function = null;
    ArrayList<Function> listFunctions = new ArrayList<Function>();
    TupleTerm _arguments = term.getArguments();
    boolean _tripleEquals = (_arguments == null);
    if (_tripleEquals) {
      complete_function_name = term.getFunctionName();
      function = Utility.getFunctionByName(complete_function_name);
    } else {
      DefaultMutableTreeNode domain_arguments = DomainCalculator.getDomainTupleTerm(term.getArguments());
      String _functionName = term.getFunctionName();
      String _plus = (_functionName + "(");
      String _codeFromTree = DomainTree.getCodeFromTree(domain_arguments);
      String _plus_1 = (_plus + _codeFromTree);
      String _plus_2 = (_plus_1 + ")");
      complete_function_name = _plus_2;
      function = Utility.getFunction(complete_function_name);
      if ((function == null)) {
        DomainTree tree = new DomainTree(domain_arguments);
        ArrayList<Function> list = Utility.getListOfPossibleFunction(term.getFunctionName());
        if ((list == null)) {
          return null;
        }
        listFunctions = list;
      }
    }
    return listFunctions;
  }

  public static DefaultMutableTreeNode getDomainFunctionTerm(final FunctionTerm term) {
    Domain domain_term = null;
    domain_term = Utility.getDomain(term.getFunctionName());
    if ((domain_term != null)) {
      return DomainTree.getNodeFromDomain(domain_term);
    }
    Function function = DomainCalculator.getFunctionFromFunctionTerm(term);
    if ((function == null)) {
      return null;
    }
    boolean _equals = function.getName().equals("self");
    if (_equals) {
      AgentDomain anyAgent = AsmetalFactory.eINSTANCE.createAgentDomain();
      anyAgent.setName("AnyAgent");
      Utility.signature_domain_map.put("AnyAgent", anyAgent);
      return DomainTree.getNodeFromDomain(anyAgent);
    }
    DefaultMutableTreeNode res = null;
    String domain_code = Utility.calculateDomainCode(function.getCodomain());
    Domain dom = Utility.getDomain(domain_code);
    if ((dom == null)) {
      for (final Domain d : BasicDomains.basic_domain_list) {
        boolean _equals_1 = d.getName().equals(domain_code);
        if (_equals_1) {
          dom = d;
          Utility.imported_all_domain_map.put(domain_code, dom);
        }
      }
    }
    boolean _isFromAnyDomain = Utility.isFromAnyDomain(dom);
    if (_isFromAnyDomain) {
      res = DomainTree.getNodeFromDomain(dom);
    } else {
      res = DomainTree.getNodeFromDomain(dom);
    }
    return res;
  }

  public static DefaultMutableTreeNode getDomainLocationTerm(final LocationTerm term) {
    Domain domain_term = null;
    domain_term = Utility.getDomain(term.getFunctionName());
    if ((domain_term != null)) {
      return DomainTree.getNodeFromDomain(domain_term);
    }
    Function function = DomainCalculator.getFunctionFromFunctionTerm(term);
    if ((function == null)) {
      return null;
    }
    DefaultMutableTreeNode res = null;
    String domain_code = Utility.calculateDomainCode(function.getCodomain());
    Domain dom = Utility.getDomain(domain_code);
    if ((dom == null)) {
      for (final Domain d : BasicDomains.basic_domain_list) {
        boolean _equals = d.getName().equals(domain_code);
        if (_equals) {
          dom = d;
          Utility.imported_all_domain_map.put(domain_code, dom);
        }
      }
    }
    boolean _isFromAnyDomain = Utility.isFromAnyDomain(dom);
    if (_isFromAnyDomain) {
      res = DomainTree.getNodeFromDomain(dom);
    } else {
      res = DomainTree.getNodeFromDomain(dom);
    }
    return res;
  }

  public static DefaultMutableTreeNode getDomainConstantTerm(final ConstantTerm term) {
    if ((term instanceof EnumTerm)) {
      Domain dom = Utility.getEnumParent(((EnumTerm)term).getSymbol());
      return DomainTree.getNodeFromDomain(dom);
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
    return DomainTree.getNodeFromString(domain_name);
  }

  public static DefaultMutableTreeNode getDomainVariableTerm(final VariableTerm term) {
    Domain dom = Utility.getDomainFromVariable(term.getName());
    DefaultMutableTreeNode node = DomainTree.getNodeFromDomain(dom);
    if (((dom instanceof StructuredTD) && (term.eContainer() instanceof BinaryOperation))) {
      return node.getFirstLeaf();
    }
    return node;
  }

  public static DefaultMutableTreeNode getDomainFiniteQuantificationTerm(final FiniteQuantificationTerm term) {
    return DomainTree.getNodeFromString("Boolean");
  }

  public static DefaultMutableTreeNode getDomainExtendedTerm(final ExtendedTerm term) {
    DefaultMutableTreeNode res = null;
    if ((term instanceof ConditionalTerm)) {
      res = DomainCalculator.getDomainConditionalTerm(((ConditionalTerm)term));
    } else {
      if ((term instanceof CaseTerm)) {
        res = DomainCalculator.getDomainCaseTerm(((CaseTerm)term));
      } else {
        if ((term instanceof TupleTerm)) {
          res = DomainCalculator.getDomainTupleTerm(((TupleTerm)term));
        } else {
          if ((term instanceof VariableBindingTerm)) {
            res = DomainCalculator.getDomainVariableBindingTerm(((VariableBindingTerm)term));
          } else {
            if ((term instanceof CollectionTerm)) {
              res = DomainCalculator.getDomainCollectionTerm(((CollectionTerm)term));
            } else {
              if ((term instanceof RuleAsTerm)) {
                res = DomainCalculator.getDomainRuleAsTerm(((RuleAsTerm)term));
              }
            }
          }
        }
      }
    }
    return res;
  }

  public static DefaultMutableTreeNode getDomainConditionalTerm(final ConditionalTerm term) {
    Term _elseTerm = term.getElseTerm();
    boolean _tripleEquals = (_elseTerm == null);
    if (_tripleEquals) {
      return DomainCalculator.getDomainTermNode(term.getThenTerm());
    }
    DefaultMutableTreeNode root_then = new DefaultMutableTreeNode();
    DefaultMutableTreeNode dom_then = DomainCalculator.getDomainTermNode(term.getThenTerm());
    DefaultMutableTreeNode root_else = new DefaultMutableTreeNode();
    DefaultMutableTreeNode dom_else = DomainCalculator.getDomainTermNode(term.getElseTerm());
    Domain dom = Utility.getCommonDomain(dom_then, dom_else);
    return DomainTree.getNodeFromDomain(dom);
  }

  public static DefaultMutableTreeNode getDomainCaseTerm(final CaseTerm term) {
    Domain dom = Utility.getCommonDomainFromList(term.getResultTerms());
    return DomainTree.getNodeFromDomain(dom);
  }

  public static DefaultMutableTreeNode getDomainTupleTerm(final TupleTerm term) {
    int _size = term.getTerms().size();
    boolean _tripleEquals = (_size == 0);
    if (_tripleEquals) {
      return null;
    }
    int _size_1 = term.getTerms().size();
    boolean _tripleEquals_1 = (_size_1 == 1);
    if (_tripleEquals_1) {
      DefaultMutableTreeNode res = DomainCalculator.getDomainTermNode(term.getTerms().get(0));
      return res;
    }
    ArrayList<DefaultMutableTreeNode> nodes = new ArrayList<DefaultMutableTreeNode>();
    DefaultMutableTreeNode node = null;
    EList<Term> _terms = term.getTerms();
    for (final Term t : _terms) {
      {
        node = DomainCalculator.getDomainTermNode(t);
        nodes.add(node);
      }
    }
    return DomainTree.getComposedNodeFromNodes("Prod", nodes);
  }

  public static DefaultMutableTreeNode getDomainVariableBindingTerm(final VariableBindingTerm term) {
    DefaultMutableTreeNode res = null;
    if ((term instanceof LetTerm)) {
      res = DomainCalculator.getDomainLetTerm(((LetTerm)term));
    } else {
      if ((term instanceof FiniteQuantificationTerm)) {
        res = DomainCalculator.getDomainFiniteQuantificationTerm(((FiniteQuantificationTerm)term));
      } else {
        if ((term instanceof ComprehensionTerm)) {
          res = DomainCalculator.getDomainComprehensionTerm(((ComprehensionTerm)term));
        }
      }
    }
    return res;
  }

  public static DefaultMutableTreeNode getDomainLetTerm(final LetTerm term) {
    Utility.fillVariableMap(term);
    return DomainCalculator.getDomainTermNode(term.getBody());
  }

  public static DefaultMutableTreeNode getDomainComprehensionTerm(final ComprehensionTerm term) {
    DefaultMutableTreeNode res = null;
    if ((term instanceof SetCT)) {
      res = DomainCalculator.getDomainSetCT(((SetCT)term));
    } else {
      if ((term instanceof MapCT)) {
        res = DomainCalculator.getDomainMapCT(((MapCT)term));
      } else {
        if ((term instanceof SequenceCT)) {
          res = DomainCalculator.getDomainSequenceCT(((SequenceCT)term));
        } else {
          if ((term instanceof BagCT)) {
            res = DomainCalculator.getDomainBagCT(((BagCT)term));
          }
        }
      }
    }
    return res;
  }

  public static DefaultMutableTreeNode getDomainSetCT(final SetCT term) {
    ArrayList<Domain> list = new ArrayList<Domain>();
    for (int i = 0; (i < term.getRanges().size()); i++) {
      try {
        Object _root = DomainCalculator.getDomainTerm(term.getRanges().get(i)).getModel().getRoot();
        DefaultMutableTreeNode domName = ((DefaultMutableTreeNode) _root);
        String domNameStr = null;
        if ((domName != null)) {
          domNameStr = DomainTree.getCodeFromTree(domName);
        }
        Domain dom = Utility.getDomain(domNameStr);
        list.add(dom);
      } catch (final Throwable _t) {
        if (_t instanceof Exception) {
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
    }
    int _size = list.size();
    boolean _greaterThan = (_size > 1);
    if (_greaterThan) {
      String strName = "Prod(";
      for (final Domain d : list) {
        String _strName = strName;
        String _name = d.getName();
        String _plus = (_name + ",");
        strName = (_strName + _plus);
      }
      int _length = strName.length();
      int _minus = (_length - 1);
      String _substring = strName.substring(0, _minus);
      String _plus_1 = (_substring + ")");
      strName = _plus_1;
      list.clear();
      list.add(Utility.getDomain(strName));
    }
    return DomainTree.getComposedNode("Powerset", list);
  }

  public static DefaultMutableTreeNode getDomainMapCT(final MapCT term) {
    return null;
  }

  public static DefaultMutableTreeNode getDomainSequenceCT(final SequenceCT term) {
    Domain dom = Utility.getCommonDomainFromList(term.getRanges());
    if ((dom instanceof SequenceDomain)) {
      return DomainTree.getNodeFromDomain(dom);
    }
    ArrayList<Domain> list = new ArrayList<Domain>();
    list.add(dom);
    return DomainTree.getComposedNode("Seq", list);
  }

  public static DefaultMutableTreeNode getDomainBagCT(final BagCT term) {
    Domain dom = Utility.getCommonDomainFromList(term.getRanges());
    if ((dom instanceof BagDomain)) {
      return DomainTree.getNodeFromDomain(dom);
    }
    ArrayList<Domain> list = new ArrayList<Domain>();
    list.add(dom);
    return DomainTree.getComposedNode("Bag", list);
  }

  public static DefaultMutableTreeNode getDomainCollectionTerm(final CollectionTerm term) {
    DefaultMutableTreeNode res = null;
    if ((term instanceof SetTerm)) {
      res = DomainCalculator.getDomainSetTerm(((SetTerm)term));
    } else {
      if ((term instanceof SequenceTerm)) {
        res = DomainCalculator.getDomainSequenceTerm(((SequenceTerm)term));
      } else {
        if ((term instanceof BagTerm)) {
          res = DomainCalculator.getDomainBagTerm(((BagTerm)term));
        } else {
          if ((term instanceof MapTerm)) {
            res = DomainCalculator.getDomainMapTerm(((MapTerm)term));
          }
        }
      }
    }
    return res;
  }

  public static DefaultMutableTreeNode getDomainSetTerm(final SetTerm term) {
    ArrayList<DefaultMutableTreeNode> list = new ArrayList<DefaultMutableTreeNode>();
    Term _start_term = term.getStart_term();
    boolean _tripleEquals = (_start_term == null);
    if (_tripleEquals) {
      return DomainTree.getNodeFromString("Any_Powerset");
    }
    Term _start_term_1 = term.getStart_term();
    if ((_start_term_1 instanceof SetTerm)) {
      DefaultMutableTreeNode dom = DomainCalculator.getDomainTermNode(term.getStart_term());
      list.add(dom);
      DefaultMutableTreeNode internalPowerset = DomainTree.getComposedNodeFromNodes("Powerset", list);
      list.clear();
      list.add(internalPowerset);
      return DomainTree.getComposedNodeFromNodes("Powerset", list);
    } else {
      Term _start_term_2 = term.getStart_term();
      if ((_start_term_2 instanceof SequenceTerm)) {
        DefaultMutableTreeNode dom_1 = DomainCalculator.getDomainTermNode(term.getStart_term());
        list.add(dom_1);
        return DomainTree.getComposedNodeFromNodes("Seq", list);
      } else {
        Term _start_term_3 = term.getStart_term();
        if ((_start_term_3 instanceof BagTerm)) {
          DefaultMutableTreeNode dom_2 = DomainCalculator.getDomainTermNode(term.getStart_term());
          list.add(dom_2);
          return DomainTree.getComposedNodeFromNodes("Bag", list);
        } else {
          Term _start_term_4 = term.getStart_term();
          if ((_start_term_4 instanceof MapTerm)) {
            DefaultMutableTreeNode dom_3 = DomainCalculator.getDomainTermNode(term.getStart_term());
            list.add(dom_3);
            return DomainTree.getComposedNodeFromNodes("Map", list);
          } else {
            if (((term.eContainer() instanceof UpdateRule) || (term.eContainer() instanceof TupleTerm))) {
              DefaultMutableTreeNode dom_4 = DomainCalculator.getDomainTermNode(term.getStart_term());
              list.add(dom_4);
              return DomainTree.getComposedNodeFromNodes("Powerset", list);
            } else {
              return DomainCalculator.getDomainTermNode(term.getStart_term());
            }
          }
        }
      }
    }
  }

  public static DefaultMutableTreeNode getDomainSequenceTerm(final SequenceTerm term) {
    Term _start_term = term.getStart_term();
    boolean _tripleEquals = (_start_term == null);
    if (_tripleEquals) {
      return DomainTree.getNodeFromString("Any_Seq");
    }
    DefaultMutableTreeNode dom = DomainCalculator.getDomainTermNode(term.getStart_term());
    ArrayList<DefaultMutableTreeNode> list = new ArrayList<DefaultMutableTreeNode>();
    list.add(dom);
    return DomainTree.getComposedNodeFromNodes("Seq", list);
  }

  public static DefaultMutableTreeNode getDomainBagTerm(final BagTerm term) {
    BasicTerm _start_term = term.getStart_term();
    boolean _tripleEquals = (_start_term == null);
    if (_tripleEquals) {
      return DomainTree.getNodeFromString("Any_Bag");
    }
    DefaultMutableTreeNode dom = DomainCalculator.getDomainTermNode(term.getStart_term());
    ArrayList<DefaultMutableTreeNode> list = new ArrayList<DefaultMutableTreeNode>();
    list.add(dom);
    return DomainTree.getComposedNodeFromNodes("Bag", list);
  }

  public static DefaultMutableTreeNode getDomainMapTerm(final MapTerm term) {
    if (((term.getTerm() == null) || (term.getTerm().size() < 2))) {
      return DomainTree.getNodeFromString("Any_Map");
    }
    ArrayList<Term> sourceTerms = new ArrayList<Term>();
    ArrayList<Term> targetTerms = new ArrayList<Term>();
    for (int i = 0; (i < term.getTerm().size()); i++) {
      {
        sourceTerms.add(term.getTerm().get(i));
        i++;
        targetTerms.add(term.getTerm().get(i));
      }
    }
    Domain dom_source = Utility.getCommonDomainFromList(sourceTerms);
    Domain dom_target = Utility.getCommonDomainFromList(targetTerms);
    ArrayList<Domain> list = new ArrayList<Domain>();
    list.add(dom_source);
    list.add(dom_target);
    return DomainTree.getComposedNode("Map", list);
  }

  public static DefaultMutableTreeNode getDomainRuleAsTerm(final RuleAsTerm term) {
    return DomainTree.getNodeFromString("Rule");
  }
}
