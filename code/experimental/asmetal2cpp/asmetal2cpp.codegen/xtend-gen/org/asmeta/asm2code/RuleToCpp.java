package org.asmeta.asm2code;

import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.PowersetDomain;
import asmeta.definitions.domains.StringDomain;
import asmeta.structure.Asm;
import asmeta.terms.basicterms.Term;
import asmeta.transitionrules.basictransitionrules.BlockRule;
import asmeta.transitionrules.basictransitionrules.ChooseRule;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.ExtendRule;
import asmeta.transitionrules.basictransitionrules.ForallRule;
import asmeta.transitionrules.basictransitionrules.LetRule;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.basictransitionrules.SkipRule;
import asmeta.transitionrules.basictransitionrules.TermAsRule;
import asmeta.transitionrules.basictransitionrules.UpdateRule;
import asmeta.transitionrules.derivedtransitionrules.CaseRule;
import asmeta.transitionrules.derivedtransitionrules.IterativeWhileRule;
import asmeta.transitionrules.turbotransitionrules.IterateRule;
import asmeta.transitionrules.turbotransitionrules.SeqRule;
import java.util.ArrayList;
import org.asmeta.asm2code.main.TranslatorOptions;
import org.asmeta.simulator.RuleVisitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class RuleToCpp extends RuleVisitor<String> {
  private Asm res;

  private boolean seqBlock;

  private TranslatorOptions options;

  /**
   * seqBlock iff it is called in a seq rule
   */
  public RuleToCpp(final Asm resource, final boolean seqBlock, final TranslatorOptions options) {
    this.res = resource;
    this.seqBlock = seqBlock;
    this.options = options;
  }

  @Override
  public String visit(final BlockRule object) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{ //par");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("�new RuleToCpp(res,false,options).printRules(object.getRules())�");
    _builder.newLine();
    _builder.append("}//endpar");
    return _builder.toString();
  }

  private String printRules(final EList<Rule> rules) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; (i < rules.size()); i++) {
      sb.append(new RuleToCpp(this.res, this.seqBlock, this.options).visit(rules.get(i)));
    }
    return sb.toString();
  }

  @Override
  public String visit(final MacroCallRule object) {
    String methodName = object.getCalledMacro().getName();
    if (this.seqBlock) {
      String _methodName = methodName;
      methodName = (_methodName + "_seq");
    }
    int _size = object.getParameters().size();
    boolean _equals = (_size == 0);
    if (_equals) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("�methodName�();");
      _builder.newLine();
      return _builder.toString();
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("�methodName�(�printListTerm(object.parameters)�);");
      _builder_1.newLine();
      return _builder_1.toString();
    }
  }

  private String printListTerm(final EList<Term> term) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; (i < term.size()); i++) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("�new TermToCpp(res).visit(term.get(i))�, ");
      sb.append(_builder);
    }
    int _length = sb.length();
    int _minus = (_length - 2);
    return sb.substring(0, _minus);
  }

  /**
   * if (singleParam.get(i).id !== null)
   * 	throw new RuntimeException("IdDomain null in rule definition not allowed")
   * if (singleParam.get(i).domain !== null)
   *  throw new RuntimeException("Domain null in rule definition not allowed")
   * }
   * return paramDef.substring(0, paramDef.length - 2) + ")"
   * }
   */
  @Override
  public String visit(final SeqRule object) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{//seq");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("�new RuleToCpp(res,true,options).printRules(object.rules)�");
    _builder.newLine();
    _builder.append("}//endseq");
    _builder.newLine();
    return _builder.toString();
  }

  /**
   * override String visit(SeqNext object) {
   * 		return '''
   * 			{//seqnext
   * 				�new RuleDefinitionIno(res,true).printRules(object.rules)�
   * 			}//endseqnext
   * 		'''
   * }
   */
  @Override
  public String visit(final UpdateRule object) {
    StringBuffer result = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("�new TermToCpp(res,true).visit(object.location)� = �new TermToCpp(res,false).visit(object.updatingTerm)�;");
    _builder.newLine();
    result.append(_builder);
    if (this.seqBlock) {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("�new TermToCpp(res,false).visit(object.location)� = �new TermToCpp(res,true).visit(object.location)�;");
      result.append(_builder_1);
    }
    return result.toString();
  }

  @Override
  public String visit(final SkipRule object) {
    return "; \n";
  }

  @Override
  public String visit(final CaseRule object) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; (i < object.getCaseBranches().size()); i++) {
      if ((i == 0)) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("if(�compareTerms(object.getTerm,object.getCaseTerm.get(i))�){");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("�new RuleToCpp(res,seqBlock,options).visit(object.getCaseBranches.get(i))�");
        _builder.newLine();
        _builder.append("}");
        sb.append(_builder);
      } else {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("else if(�compareTerms(object.getTerm,object.getCaseTerm.get(i))�){");
        _builder_1.newLine();
        _builder_1.append("\t");
        _builder_1.append("�new RuleToCpp(res,seqBlock,options).visit(object.getCaseBranches().get(i))�");
        _builder_1.newLine();
        _builder_1.append("}");
        sb.append(_builder_1);
      }
    }
    Rule _otherwiseBranch = object.getOtherwiseBranch();
    boolean _tripleNotEquals = (_otherwiseBranch != null);
    if (_tripleNotEquals) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("else{ ");
      _builder.newLine();
      _builder.append(" \t");
      _builder.append("�new RuleToCpp(res,seqBlock,options).visit(object.getOtherwiseBranch())�");
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      sb.append(_builder);
    }
    return sb.toString();
  }

  public String compareTerms(final Term leftTerm, final Term rightTerm) {
    int _compareTo = leftTerm.getDomain().toString().compareTo(rightTerm.getDomain().toString());
    boolean _notEquals = (_compareTo != 0);
    if (_notEquals) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("Impossible to compare Terms");
      return _builder.toString();
    } else {
      if ((leftTerm instanceof StringDomain)) {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("�new TermToCpp(res).visit(leftTerm)�.compare(�new TermToCpp(res).visit(rightTerm)�)==0");
        return _builder_1.toString();
      } else {
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("�new TermToCpp(res).visit(leftTerm)�==�new TermToCpp(res).visit(rightTerm)�");
        return _builder_2.toString();
      }
    }
  }

  @Override
  public String visit(final ChooseRule object) {
    int counter = 0;
    StringBuffer sb = new StringBuffer();
    for (int i = 0; (i < object.getRanges().size()); i++) {
      Domain _domain = object.getRanges().get(i).getDomain();
      if ((_domain instanceof PowersetDomain)) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("vector<const �new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)�*> point�i�;");
        _builder.newLine();
        sb.append(_builder);
      } else {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("NOT IMPLEMENTED IN C++ (RuleToCpp line 264)");
        _builder_1.newLine();
        sb.append(_builder_1);
      }
    }
    for (int i = 0; (i < object.getRanges().size()); i++) {
      Domain _domain = object.getRanges().get(i).getDomain();
      if ((_domain instanceof PowersetDomain)) {
        Domain _domain_1 = object.getRanges().get(i).getDomain();
        boolean _isNotNumerable = new Util().isNotNumerable(((PowersetDomain) _domain_1).getBaseDomain());
        if (_isNotNumerable) {
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("�new DomainToH(res).visit(object.getRanges.get(i).domain)� �new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)+i�_elems = �new TermToCpp(res).visit(object.getRanges.get(i))�;");
          _builder.newLine();
          sb.append(_builder);
          counter = (counter + 1);
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append("for(auto const& �new TermToCpp(res).visit(object.getVariable.get(i))� : �new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)+i�_elems){");
          _builder_1.newLine();
          sb.append(_builder_1);
        } else {
          Domain _domain_2 = object.getRanges().get(i).getDomain();
          Domain _baseDomain = ((PowersetDomain) _domain_2).getBaseDomain();
          if ((_baseDomain instanceof AbstractTd)) {
            StringConcatenation _builder_2 = new StringConcatenation();
            _builder_2.append("for(const auto& �new TermToCpp(res).visit(object.getVariable.get(i))� : �new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)�::elems)");
            _builder_2.newLine();
            sb.append(_builder_2);
          } else {
            StringConcatenation _builder_3 = new StringConcatenation();
            _builder_3.append("for(auto const& �new TermToCpp(res).visit(object.getVariable.get(i))� : �new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)�_elems)");
            _builder_3.newLine();
            sb.append(_builder_3);
          }
        }
      } else {
        StringConcatenation _builder_4 = new StringConcatenation();
        _builder_4.append("//NOT IMPLEMENTED IN C++ (RuleToCpp line 283)\t");
        _builder_4.newLine();
        sb.append(_builder_4);
      }
    }
    Term _guard = object.getGuard();
    boolean _tripleNotEquals = (_guard != null);
    if (_tripleNotEquals) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("if(�new TermToCpp(res).visit(object.getGuard)�){");
      _builder.newLine();
      sb.append(_builder);
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("{");
      _builder_1.newLine();
      sb.append(_builder_1);
    }
    ArrayList<String> pointerTerms = new ArrayList<String>();
    for (int i = 0; (i < object.getVariable().size()); i++) {
      Domain _domain = object.getRanges().get(i).getDomain();
      Domain _baseDomain = ((PowersetDomain) _domain).getBaseDomain();
      if ((_baseDomain instanceof AbstractTd)) {
        String termName = new TermToCpp(this.res).visit(object.getVariable().get(i));
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("point�i�.push_back(&(*�termName�));");
        _builder_2.newLine();
        sb.append(_builder_2);
        pointerTerms.add(termName);
      } else {
        StringConcatenation _builder_3 = new StringConcatenation();
        _builder_3.append("point�i�.push_back(&�new TermToCpp(res).visit(object.getVariable.get(i))�);");
        _builder_3.newLine();
        sb.append(_builder_3);
      }
    }
    for (int i = 0; (i < counter); i++) {
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append("}");
      _builder_2.newLine();
      sb.append(_builder_2);
    }
    StringConcatenation _builder_2 = new StringConcatenation();
    _builder_2.append("}");
    _builder_2.newLine();
    sb.append(_builder_2);
    if (this.options.shuffleRandom) {
      StringConcatenation _builder_3 = new StringConcatenation();
      _builder_3.append("int rndm = rand() % point0.size();");
      _builder_3.newLine();
      sb.append(_builder_3);
    } else {
      StringConcatenation _builder_4 = new StringConcatenation();
      _builder_4.append("int rndm = 0;");
      _builder_4.newLine();
      sb.append(_builder_4);
    }
    StringConcatenation _builder_5 = new StringConcatenation();
    _builder_5.append("{");
    _builder_5.newLine();
    sb.append(_builder_5);
    for (int i = 0; (i < object.getVariable().size()); i++) {
      StringConcatenation _builder_6 = new StringConcatenation();
      _builder_6.append("auto �new TermToCpp(res).visit(object.getVariable.get(i))� = *point�i�[rndm];");
      _builder_6.newLine();
      sb.append(_builder_6);
    }
    Rule _ifnone = object.getIfnone();
    boolean _tripleNotEquals_1 = (_ifnone != null);
    if (_tripleNotEquals_1) {
      String doRule = this.visit(object.getDoRule());
      for (int j = 0; (j < pointerTerms.size()); j++) {
        {
          String pointerName = pointerTerms.get(j);
          doRule = doRule.replace(pointerName, ("&" + pointerName));
        }
      }
      StringConcatenation _builder_6 = new StringConcatenation();
      _builder_6.append("  ");
      _builder_6.append("if(point0.size()>0){");
      _builder_6.newLine();
      _builder_6.append("\t");
      _builder_6.append("�doRule�");
      _builder_6.newLine();
      _builder_6.append("\t ");
      _builder_6.append("}else{");
      _builder_6.newLine();
      _builder_6.append("\t \t");
      _builder_6.append("�visit(object.getIfnone)�");
      _builder_6.newLine();
      _builder_6.append("\t ");
      _builder_6.append("}");
      _builder_6.newLine();
      _builder_6.append("}");
      sb.append(_builder_6);
    } else {
      String doRule_1 = this.visit(object.getDoRule());
      for (int j = 0; (j < pointerTerms.size()); j++) {
        {
          String pointerName = pointerTerms.get(j);
          doRule_1 = doRule_1.replace(pointerName, ("&" + pointerName));
        }
      }
      StringConcatenation _builder_7 = new StringConcatenation();
      _builder_7.append("  ");
      _builder_7.append("if(point0.size()>0){");
      _builder_7.newLine();
      _builder_7.append("\t");
      _builder_7.append("�doRule�");
      _builder_7.newLine();
      _builder_7.append("\t ");
      _builder_7.append("}");
      _builder_7.newLine();
      _builder_7.append("}");
      sb.append(_builder_7);
    }
    return sb.toString();
  }

  @Override
  public String visit(final ForallRule object) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; (i < object.getRanges().size()); i++) {
      Domain _domain = object.getRanges().get(i).getDomain();
      Domain _baseDomain = ((PowersetDomain) _domain).getBaseDomain();
      if ((_baseDomain instanceof AbstractTd)) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("for(auto �new TermToCpp(res).visit(object.getVariable.get(i))� : �new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)�::elems)");
        _builder.newLine();
        sb.append(_builder);
      } else {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("for(auto �new TermToCpp(res).visit(object.getVariable.get(i))� : �new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)�_elems)");
        _builder_1.newLine();
        sb.append(_builder_1);
      }
    }
    Term _guard = object.getGuard();
    boolean _tripleNotEquals = (_guard != null);
    if (_tripleNotEquals) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("�\"\"�");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("if(�new TermToCpp(res).visit(object.getGuard)�){\t");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("�visit(object.getDoRule)�");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("}");
      _builder.newLine();
      sb.append(_builder);
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("{");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("�visit(object.getDoRule)�");
      _builder_1.newLine();
      _builder_1.append("}");
      _builder_1.newLine();
      sb.append(_builder_1);
    }
    return sb.toString();
  }

  @Override
  public String visit(final LetRule object) {
    StringBuffer let = new StringBuffer();
    let.append("{\n");
    for (int i = 0; (i < object.getVariable().size()); i++) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("auto �new TermToCpp(res).visit(object.getVariable.get(i))� = �new TermToCpp(res).visit(object.getInitExpression.get(i))�;");
      _builder.newLine();
      let.append(_builder);
    }
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("�new RuleToCpp(res,seqBlock,options).visit(object.getInRule)�");
    _builder.newLine();
    _builder.append("}");
    let.append(_builder);
    return let.toString();
  }

  public String visit(final IterativeWhileRule object) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("while (�new TermToCpp(res,false).visit(object.guard)�){");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("�new RuleToCpp(res,true,options).visit(object.rule)�");
    _builder.newLine();
    _builder.append("}");
    return _builder.toString();
  }

  /**
   * TODO: iterateRule
   */
  public String visit(final IterateRule object) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("//#iterate rule not yet implemented");
    _builder.newLine();
    _builder.newLine();
    return _builder.toString();
  }

  @Override
  public String visit(final ConditionalRule object) {
    Rule _elseRule = object.getElseRule();
    boolean _tripleEquals = (_elseRule == null);
    if (_tripleEquals) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("if (�new TermToCpp(res).visit(object.guard)�){ ");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("�new RuleToCpp(res,seqBlock,options).visit(object.thenRule)�");
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      return _builder.toString();
    } else {
      Rule _elseRule_1 = object.getElseRule();
      if ((_elseRule_1 instanceof ConditionalRule)) {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("if (�new TermToCpp(res).visit(object.getGuard)�){ ");
        _builder_1.newLine();
        _builder_1.append("\t\t");
        _builder_1.append("�new RuleToCpp(res,seqBlock,options).visit(object.thenRule)�");
        _builder_1.newLine();
        _builder_1.append("} else �new RuleToCpp(res,seqBlock,options).visit(object.elseRule)�");
        _builder_1.newLine();
        return _builder_1.toString();
      } else {
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("\t");
        _builder_2.append("if (�new TermToCpp(res).visit(object.getGuard)�){ ");
        _builder_2.newLine();
        _builder_2.append("\t");
        _builder_2.append("�new RuleToCpp(res,seqBlock,options).visit(object.thenRule)�");
        _builder_2.newLine();
        _builder_2.append("\t");
        _builder_2.append("}else{");
        _builder_2.newLine();
        _builder_2.append("\t");
        _builder_2.append("�new RuleToCpp(res,seqBlock,options).visit(object.elseRule)�");
        _builder_2.newLine();
        _builder_2.append("}");
        _builder_2.newLine();
        return _builder_2.toString();
      }
    }
  }

  /**
   * '''
   * 	#ifdef ARDUINOCOMPILER
   * 	Serial.print(" + new ToString(res).visit(object.term) + ");
   * 	  #endif
   * '''
   * }
   */
  @Override
  public String visit(final TermAsRule rule) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }

  @Override
  public String visit(final ExtendRule rule) {
    StringBuffer string = new StringBuffer();
    for (int i = 0; (i < rule.getBoundVar().size()); i++) {
      String _visit = new DomainToH(this.res, true).visit(rule.getExtendedDomain());
      String _plus = (_visit + " ");
      String _visit_1 = new TermToCpp(this.res).visit(rule.getBoundVar().get(i));
      String _plus_1 = (_plus + _visit_1);
      String _plus_2 = (_plus_1 + " = new ");
      String _visit_2 = new DomainToH(this.res).visit(rule.getExtendedDomain());
      String _plus_3 = (_plus_2 + _visit_2);
      String _plus_4 = (_plus_3 + "();\n");
      string.append(_plus_4);
    }
    String _string = string.toString();
    String _visit = new RuleToCpp(this.res, this.seqBlock, this.options).visit(rule.getDoRule());
    return (_string + _visit);
  }
}
