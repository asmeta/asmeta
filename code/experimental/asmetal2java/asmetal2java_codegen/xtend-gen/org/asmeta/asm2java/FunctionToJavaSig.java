package org.asmeta.asm2java;

import asmeta.definitions.StaticFunction;
import asmeta.structure.Asm;
import org.asmeta.parser.util.ReflectiveVisitor;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class FunctionToJavaSig extends ReflectiveVisitor<String> {
  private Asm res;

  public FunctionToJavaSig(final Asm resource) {
    this.res = resource;
  }

  public String visit(final StaticFunction object) {
    throw new Error("Unresolved compilation problems:"
      + "\nno viable alternative at input \'�\'"
      + "\nmismatched input \'� «\' expecting \'}\'"
      + "\nmismatched input \'�;\\r\\n\\t\\t\\t\\t\'\'\'\' expecting \'}\'"
      + "\nThe method returnDomain(Domain, boolean) is undefined"
      + "\nThe method or field � is undefined"
      + "\nThe method or field name� is undefined for the type StaticFunction");
  }

  private /* Procedure1<? super object.codomain> */Object ProductDomain;
}
