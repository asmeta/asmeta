package org.asmeta.asm2java;

import asmeta.structure.Asm;
import asmeta.terms.basicterms.VariableTerm;
import org.eclipse.emf.common.util.EList;

@SuppressWarnings("all")
public class Util {
  private int counter;

  private Object c;

  private int i;

  public Util() {
  }

  public static String getElemsSetName(final String domainName) {
    return (domainName + "_elems");
  }

  public static String getExtendSetName(final String domainName) {
    return (domainName + "_extend");
  }

  public String adaptRuleParam(final EList<VariableTerm> variables, final Asm res) {
    throw new Error("Unresolved compilation problems:"
      + "\nno viable alternative at input \'�\'"
      + "\nmismatched input \'� «\' expecting \'}\'"
      + "\nmismatched input \'�, \'\'\'\' expecting \'}\'"
      + "\nThe method or field � is undefined"
      + "\nThe method or field name� is undefined for the type VariableTerm");
  }
}
