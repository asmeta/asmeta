package org.asmeta.asm2java;

import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.structure.Asm;
import org.asmeta.parser.util.ReflectiveVisitor;

@SuppressWarnings("all")
public class DomainToJavaSigDef extends ReflectiveVisitor<String> {
  private Asm res;

  private boolean pointer;

  public DomainToJavaSigDef(final Asm resource) {
    this.res = resource;
    this.pointer = false;
  }

  public DomainToJavaSigDef(final Asm resource, final boolean pointer) {
    this.res = resource;
    this.pointer = pointer;
  }

  /**
   * Domain Signature
   */
  public String visit(final AbstractTd object) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field nameÂ is undefined for the type AbstractTd"
      + "\nThe method or field nameÂ is undefined for the type AbstractTd"
      + "\nThe method or field nameÂ is undefined for the type AbstractTd"
      + "\nThe method or field nameÂ is undefined for the type AbstractTd"
      + "\nThe method or field nameÂ is undefined for the type AbstractTd"
      + "\nThe method or field nameÂ is undefined for the type AbstractTd"
      + "\nThe method or field nameÂ is undefined for the type AbstractTd"
      + "\nThe method or field nameÂ is undefined for the type AbstractTd"
      + "\nThe method or field nameÂ is undefined for the type AbstractTd"
      + "\nThe method or field nameÂ is undefined for the type AbstractTd"
      + "\nThe method or field nameÂ is undefined for the type AbstractTd"
      + "\nThe method or field nameÂ is undefined for the type AbstractTd"
      + "\nThe method or field nameÂ is undefined for the type AbstractTd"
      + "\nThe method or field nameÂ is undefined for the type AbstractTd"
      + "\nThe method or field nameÂ is undefined for the type AbstractTd"
      + "\nThe method or field nameÂ is undefined for the type AbstractTd"
      + "\nThe method or field nameÂ is undefined for the type AbstractTd"
      + "\nThe method or field nameÂ is undefined for the type AbstractTd"
      + "\nThe method or field nameÂ is undefined for the type AbstractTd"
      + "\nThe method or field nameÂ is undefined for the type AbstractTd");
  }

  public String visit(final ConcreteDomain object) {
    throw new Error("Unresolved compilation problems:"
      + "\nno viable alternative at input \'Â\'"
      + "\nmismatched input \'»> elems = new ArrayList<Â«\' expecting \'}\'"
      + "\nmismatched input \'»>();\\r\\n\\t\\t\\t      \\r\\n\\t\\t\\t      Â«\' expecting \'}\'"
      + "\nThe method or field nameÂ is undefined for the type ConcreteDomain"
      + "\nThe method or field Â is undefined"
      + "\nThe method or field Â is undefined");
  }

  public DomainToJavaSigDef(final /* res */Object __unknown__) {
    throw new Error("Unresolved compilation problems:"
      + "\nmismatched input \'.\' expecting \'{\'");
  }

  public DomainToJavaSigDef(final /* res */Object __unknown__) {
    throw new Error("Unresolved compilation problems:"
      + "\nmismatched input \'.\' expecting \'{\'");
  }

  public DomainToJavaSigDef(final /* res */Object __unknown__) {
    throw new Error("Unresolved compilation problems:"
      + "\nmismatched input \'.\' expecting \'{\'");
  }

  public DomainToJavaSigDef(final /* res */Object __unknown__) {
    throw new Error("Unresolved compilation problems:"
      + "\nmismatched input \'.\' expecting \'{\'");
  }
}
