package org.asmeta.asm2java;

import asmeta.definitions.domains.ProductDomain;
import asmeta.structure.Asm;
import org.asmeta.parser.util.ReflectiveVisitor;

/**
 * Translates the signature and the definition of the domains
 */
@SuppressWarnings("all")
public class DomainToJavaSigDef extends ReflectiveVisitor<String> {
  private Asm res;

  public DomainToJavaSigDef(final Asm resource) {
    this.res = resource;
  }

  public String visit(final ProductDomain object) {
    throw new Error("Unresolved compilation problems:"
      + "\nno viable alternative at input \'Â\'"
      + "\nmismatched input \'», \'\'\'\' expecting \'}\'"
      + "\nmismatched input \'case\' expecting \'}\'"
      + "\nThe method or field domains is undefined for the type ProductDomain"
      + "\nThe method or field domains is undefined for the type ProductDomain"
      + "\nThe method or field domains is undefined for the type ProductDomain"
      + "\nThe method or field Â is undefined"
      + "\nType mismatch: cannot convert from void to String"
      + "\nsize cannot be resolved"
      + "\nsize cannot be resolved"
      + "\nget cannot be resolved");
  }

  private int i = 0;

  private /* i<object.domains.size> */Object i;
}
