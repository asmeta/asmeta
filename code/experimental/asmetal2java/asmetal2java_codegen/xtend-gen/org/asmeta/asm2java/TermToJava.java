package org.asmeta.asm2java;

import asmeta.structure.Asm;
import asmeta.terms.basicterms.BooleanTerm;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.terms.furtherterms.ConditionalTerm;
import asmeta.terms.furtherterms.EnumTerm;
import asmeta.terms.furtherterms.IntegerTerm;
import asmeta.terms.furtherterms.NaturalTerm;
import asmeta.terms.furtherterms.StringTerm;
import org.asmeta.parser.util.ReflectiveVisitor;

@SuppressWarnings("all")
public class TermToJava extends ReflectiveVisitor<String> {
  Integer numStaticParam;

  private Asm res;

  private boolean leftHandSide;

  public TermToJava(final Asm resource) {
    this(resource, false);
  }

  public TermToJava(final Asm resource, final boolean leftHandSide) {
    this.res = resource;
    this.leftHandSide = leftHandSide;
  }

  public String visit(final VariableTerm term) {
    return term.getName();
  }

  public String visit(final IntegerTerm term) {
    return term.getSymbol();
  }

  public String visit(final NaturalTerm term) {
    String _symbol = term.getSymbol();
    int _length = term.getSymbol().length();
    int _minus = (_length - 1);
    return _symbol.substring(0, _minus);
  }

  public String visit(final StringTerm term) {
    String supp = new String("\"");
    String _symbol = term.getSymbol();
    String _plus = (supp + _symbol);
    return (_plus + supp);
  }

  public String visit(final BooleanTerm term) {
    return term.getSymbol();
  }

  public String visit(final EnumTerm term) {
    String _name = term.getDomain().getName();
    String _plus = (_name + ".");
    String _symbol = term.getSymbol();
    return (_plus + _symbol);
  }

  public String visit(final ConditionalTerm object) {
    throw new Error("Unresolved compilation problems:"
      + "\nno viable alternative at input \'Â\'"
      + "\nmismatched input \'»)\\r\\n\\t\\t\\t\\t?\\r\\n\\t\\t\\t\\t\\tÂ«\' expecting \'}\'"
      + "\nThe method or field Â is undefined"
      + "\nUnreachable expression.");
  }
}
