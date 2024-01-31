package org.asmeta.asm2java;

import asmeta.definitions.domains.AnyDomain;
import asmeta.definitions.domains.BooleanDomain;
import asmeta.definitions.domains.IntegerDomain;
import asmeta.definitions.domains.NaturalDomain;
import asmeta.definitions.domains.PowersetDomain;
import asmeta.definitions.domains.StringDomain;
import asmeta.structure.Asm;
import org.asmeta.parser.util.ReflectiveVisitor;

@SuppressWarnings("all")
public class ToString extends ReflectiveVisitor<String> {
  Integer numStaticParam;

  private Asm res;

  private boolean leftHandSide;

  private boolean seqBlock;

  private boolean pointer;

  public ToString(final Asm resource) {
    this(resource, false, false);
  }

  public ToString(final Asm resource, final boolean leftHandSide, final boolean seqBlock) {
    this.res = resource;
    this.leftHandSide = leftHandSide;
    this.seqBlock = seqBlock;
    this.pointer = false;
  }

  public ToString(final Asm asm, final boolean pointer) {
    this.res = asm;
    this.pointer = pointer;
  }

  public String visit(final StringDomain domain) {
    return "String";
  }

  public String visit(final BooleanDomain domain) {
    return "Boolean";
  }

  public String visit(final IntegerDomain domain) {
    return "Integer";
  }

  public String visit(final NaturalDomain domain) {
    return "Integer";
  }

  public String visit(final AnyDomain object) {
    return "Object";
  }

  public String visit(final PowersetDomain object) {
    throw new Error("Unresolved compilation problems:"
      + "\nno viable alternative at input \'Â\'"
      + "\nmismatched input \'»\'\'\'\' expecting \'}\'"
      + "\nThe method or field Â is undefined");
  }
}
