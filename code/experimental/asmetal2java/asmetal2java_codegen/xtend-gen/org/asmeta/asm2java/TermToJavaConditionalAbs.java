package org.asmeta.asm2java;

import asmeta.structure.Asm;
import asmeta.terms.basicterms.BooleanTerm;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.terms.furtherterms.CaseTerm;
import asmeta.terms.furtherterms.ConditionalTerm;
import asmeta.terms.furtherterms.EnumTerm;
import asmeta.terms.furtherterms.IntegerTerm;
import asmeta.terms.furtherterms.NaturalTerm;
import asmeta.terms.furtherterms.StringTerm;
import org.asmeta.parser.util.ReflectiveVisitor;

@SuppressWarnings("all")
public class TermToJavaConditionalAbs extends ReflectiveVisitor<String> {
  Integer numStaticParam;

  private Asm res;

  private boolean leftHandSide;

  public TermToJavaConditionalAbs(final Asm resource) {
    this(resource, false);
  }

  public TermToJavaConditionalAbs(final Asm resource, final boolean leftHandSide) {
    this.res = resource;
    this.leftHandSide = leftHandSide;
  }

  public String visit(final VariableTerm term) {
    return term.getName();
  }

  /**
   * TODO: DELETE FOR COVERAGE
   * def String visit(ComplexTerm term) {
   * return "TODO"
   * }
   * 
   * def String visit(CharTerm term) {
   * return term.symbol
   * }
   * 
   * 
   * 
   * def String visit(RealTerm term) {
   * return term.symbol
   * }
   */
  public String visit(final IntegerTerm term) {
    String _symbol = term.getSymbol();
    return (" = " + _symbol);
  }

  public String visit(final NaturalTerm term) {
    String _symbol = term.getSymbol();
    int _length = term.getSymbol().length();
    int _minus = (_length - 1);
    String _substring = _symbol.substring(0, _minus);
    return (" = " + _substring);
  }

  public String visit(final StringTerm term) {
    String _symbol = term.getSymbol();
    return (" = " + _symbol);
  }

  public String visit(final BooleanTerm term) {
    String _symbol = term.getSymbol();
    return (" = " + _symbol);
  }

  /**
   * TODO: DELETE FOR COVERAGE 	def String visit(UndefTerm term) {
   * throw new Exception("Undefined term not supported");
   * }
   */
  public String visit(final EnumTerm term) {
    String _name = term.getDomain().getName();
    String _plus = (" = " + _name);
    String _plus_1 = (_plus + ".");
    String _symbol = term.getSymbol();
    return (_plus_1 + _symbol);
  }

  public String visit(final ConditionalTerm object) {
    return null;
  }

  public String visit(final CaseTerm object) {
    throw new Error("Unresolved compilation problems:"
      + "\nno viable alternative at input \'Â\'"
      + "\nmismatched input \'»\\tif(Â«\' expecting \'}\'"
      + "\nmismatched input \'».ToString(Â«\' expecting \'}\'"
      + "\nmismatched input \'»).equals(\"Â«\' expecting \'}\'"
      + "\nThis expression is not allowed in this context, since it doesn\'t cause any side effects."
      + "\nThis expression is not allowed in this context, since it doesn\'t cause any side effects.");
  }

  private Object Â;
}
