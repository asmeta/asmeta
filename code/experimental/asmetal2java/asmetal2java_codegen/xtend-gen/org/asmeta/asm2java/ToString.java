package org.asmeta.asm2java;

import asmeta.definitions.domains.AgentDomain;
import asmeta.definitions.domains.AnyDomain;
import asmeta.definitions.domains.BooleanDomain;
import asmeta.definitions.domains.CharDomain;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.EnumElement;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.IntegerDomain;
import asmeta.definitions.domains.NaturalDomain;
import asmeta.definitions.domains.PowersetDomain;
import asmeta.definitions.domains.RealDomain;
import asmeta.definitions.domains.StringDomain;
import asmeta.definitions.domains.UndefDomain;
import asmeta.structure.Asm;
import org.asmeta.parser.util.ReflectiveVisitor;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class ToString extends ReflectiveVisitor<String> {
  Integer numStaticParam;

  private Asm res;

  public ToString(final Asm resource) {
    this.res = resource;
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

  public String visit(final ConcreteDomain domain) {
    return domain.getName();
  }

  public String visit(final EnumElement elem) {
    return elem.getSymbol();
  }

  public String visit(final EnumTd elem) {
    return elem.getName();
  }

  public String visit(final CharDomain domain) {
    return "Character";
  }

  public String visit(final AgentDomain domain) {
    try {
      throw new Exception("Agent domain not supported");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  public String visit(final UndefDomain domain) {
    try {
      throw new Exception("Undefined domain not supported");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  public String visit(final RealDomain domain) {
    return "Double";
  }

  public String visit(final PowersetDomain object) {
    throw new Error("Unresolved compilation problems:"
      + "\nno viable alternative at input \'Â\'"
      + "\nmismatched input \'»\'\'\'\' expecting \'}\'"
      + "\nThe method or field Â is undefined");
  }
}
