package org.asmeta.asm2code;

import asmeta.definitions.domains.EnumTd;
import asmeta.structure.Asm;
import asmeta.structure.DomainDefinition;
import asmeta.structure.DomainInitialization;
import asmeta.terms.basicterms.SetTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.furtherterms.IntegerTerm;
import org.asmeta.parser.util.ReflectiveVisitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.xbase.lib.Conversions;

@SuppressWarnings("all")
public class DomainToCpp extends ReflectiveVisitor<String> {
  private Asm asm;

  private int i;

  private String elem;

  public DomainToCpp(final Asm asm) {
    this.asm = asm;
  }

  public String visit(final EnumTd object) {
    int _length = ((Object[])Conversions.unwrapArray(object.getElement(), Object.class)).length;
    boolean _notEquals = (_length != 0);
    if (_notEquals) {
      this.elem = "{";
      for (this.i = 0; (this.i < ((Object[])Conversions.unwrapArray(object.getElement(), Object.class)).length); this.i++) {
        String _elem = this.elem;
        String _symbol = object.getElement().get(this.i).getSymbol();
        String _plus = (_symbol + ",");
        this.elem = (_elem + _plus);
      }
      int _length_1 = this.elem.length();
      int _minus = (_length_1 - 1);
      String _substring = this.elem.substring(0, _minus);
      return (_substring + "}");
    } else {
      String _name = object.getName();
      String _plus = ("No elements defined in " + _name);
      return (_plus + " domain");
    }
  }

  public String visit(final EnumTd object, final boolean arduino) {
    if ((arduino == true)) {
      int _length = ((Object[])Conversions.unwrapArray(object.getElement(), Object.class)).length;
      boolean _notEquals = (_length != 0);
      if (_notEquals) {
        this.elem = "{";
        for (this.i = 0; (this.i < ((Object[])Conversions.unwrapArray(object.getElement(), Object.class)).length); this.i++) {
          String _elem = this.elem;
          String _symbol = object.getElement().get(this.i).getSymbol();
          String _plus = (_symbol + ",");
          this.elem = (_elem + _plus);
        }
        int _length_1 = this.elem.length();
        int _minus = (_length_1 - 1);
        String _substring = this.elem.substring(0, _minus);
        return (_substring + ";};");
      } else {
        String _name = object.getName();
        String _plus = ("No elements defined in " + _name);
        return (_plus + " domain");
      }
    }
    return null;
  }

  public String visitArduino(final SetTerm object) {
    StringBuffer type = new StringBuffer("");
    type.append(new DomainToH(this.asm).visit(object.getDomain()));
    String s = "";
    String _s = s;
    s = (_s + "{");
    if (((object.getTerm() != null) && (object.getTerm().size() > 0))) {
      EList<Term> _term = object.getTerm();
      for (final Term l : _term) {
        {
          IntegerTerm i = ((IntegerTerm) l);
          String _s_1 = s;
          String _symbol = i.getSymbol();
          String _plus = (_symbol + ",");
          s = (_s_1 + _plus);
        }
      }
      int _length = s.length();
      int _minus = (_length - 1);
      s = s.substring(0, _minus);
    }
    String _s_1 = s;
    s = (_s_1 + "}");
    int _length_1 = s.length();
    int _minus_1 = (_length_1 - 1);
    String _substring = s.substring(0, _minus_1);
    return (_substring + ";}");
  }

  public String visit(final DomainDefinition object) {
    return new TermToCpp(this.asm).visit(object.getBody());
  }

  public String visit(final DomainInitialization object) {
    return new TermToCpp(this.asm).visit(object.getBody());
  }
}
