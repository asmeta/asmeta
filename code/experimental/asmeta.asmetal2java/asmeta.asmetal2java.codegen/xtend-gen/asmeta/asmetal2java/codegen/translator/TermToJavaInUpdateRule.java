package asmeta.asmetal2java.codegen.translator;

import asmeta.definitions.Function;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.MapDomain;
import asmeta.definitions.domains.ProductDomain;
import asmeta.definitions.domains.SequenceDomain;
import asmeta.structure.Asm;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.TupleTerm;
import org.asmeta.parser.util.Defs;

/**
 * This class is used in Update Rules
 */
@SuppressWarnings("all")
public class TermToJavaInUpdateRule extends TermToJava {
  public TermToJavaInUpdateRule(final Asm resource) {
    this(resource, false);
  }

  public TermToJavaInUpdateRule(final Asm resource, final boolean leftHandSide) {
    super(resource, leftHandSide, "");
  }

  public TermToJavaInUpdateRule(final Asm resource, final boolean leftHandSide, final String varName) {
    super(resource, leftHandSide, varName);
  }

  @Override
  public String visit(final FunctionTerm term) {
    StringBuffer functionTerm = new StringBuffer();
    String name = new Util().parseFunction(term.getFunction().getName());
    if ((((term.getArguments() == null) || (!Defs.getAsmName(term.getFunction()).equals("StandardLibrary"))) || 
      (!ExpressionToJava.hasEvaluateVisitor(name)))) {
      if (((Util.isControlledOrOut(term.getFunction()) && (term.getDomain() instanceof ConcreteDomain)) && 
        (!(term.getFunction().getDomain() instanceof ProductDomain)))) {
        functionTerm.append(this.caseFunctionTermSupp(term.getFunction(), term));
      }
      if (((Util.isControlledOrOut(term.getFunction()) && (term.getFunction().getDomain() instanceof ProductDomain)) && 
        (term.getDomain() instanceof ConcreteDomain))) {
        functionTerm.append(this.caseFunctionTermSupp(term.getFunction(), term));
      }
      if ((Util.isControlledOrOut(term.getFunction()) && (term.getDomain() instanceof MapDomain))) {
        functionTerm.append(this.caseFunctionTermSupp(term.getFunction(), term));
      }
      if ((Util.isControlledOrOut(term.getFunction()) && (term.getDomain() instanceof SequenceDomain))) {
        functionTerm.append(this.caseFunctionTermSupp(term.getFunction(), term));
      }
      return functionTerm.toString();
    }
    return null;
  }

  @Override
  public String caseFunctionTermSupp(final Function fd, final FunctionTerm ft) {
    StringBuffer functionTerm = new StringBuffer();
    Domain _domain = fd.getDomain();
    if ((_domain instanceof ProductDomain)) {
      String _name = fd.getName();
      String _plus = (_name + ".set(");
      String _name_1 = fd.getName();
      String _plus_1 = (_plus + _name_1);
      String _plus_2 = (_plus_1 + "_elem, sup);\n");
      functionTerm.append(_plus_2);
    }
    Domain _domain_1 = fd.getDomain();
    if ((_domain_1 instanceof SequenceDomain)) {
      String _name_2 = fd.getName();
      String _plus_3 = (_name_2 + ".set(");
      String _name_3 = fd.getName();
      String _plus_4 = (_plus_3 + _name_3);
      String _plus_5 = (_plus_4 + "_elem);\n");
      functionTerm.append(_plus_5);
    }
    Domain _domain_2 = ft.getDomain();
    if ((_domain_2 instanceof MapDomain)) {
    }
    TupleTerm _arguments = ft.getArguments();
    boolean _tripleEquals = (_arguments == null);
    if (_tripleEquals) {
      Domain _domain_3 = ft.getDomain();
      if ((_domain_3 instanceof ConcreteDomain)) {
        if ((!this.leftHandSide)) {
          String _name_4 = fd.getName();
          String _plus_6 = (_name_4 + ".set(");
          String _name_5 = ft.getDomain().getName();
          String _plus_7 = (_plus_6 + _name_5);
          String _plus_8 = (_plus_7 + this.varName);
          String _plus_9 = (_plus_8 + "_s);");
          functionTerm.append(_plus_9);
        }
      }
      return functionTerm.toString();
    }
    TupleTerm _arguments_1 = ft.getArguments();
    boolean _tripleNotEquals = (_arguments_1 != null);
    if (_tripleNotEquals) {
      int _size = ft.getArguments().getTerms().size();
      boolean _equals = (_size == 1);
      if (_equals) {
        Domain _domain_4 = ft.getDomain();
        if ((_domain_4 instanceof ConcreteDomain)) {
          if ((!this.leftHandSide)) {
            String _name_6 = fd.getName();
            String _plus_10 = (_name_6 + ".set(");
            String _visit = new TermToJava(this.res).visit(ft.getArguments().getTerms().get(0));
            String _plus_11 = (_plus_10 + _visit);
            String _plus_12 = (_plus_11 + ", ");
            String _name_7 = ft.getDomain().getName();
            String _plus_13 = (_plus_12 + _name_7);
            String _plus_14 = (_plus_13 + this.varName);
            String _plus_15 = (_plus_14 + 
              "_s);");
            functionTerm.append(_plus_15);
          } else {
          }
        }
      }
      return functionTerm.toString();
    }
    return null;
  }
}
