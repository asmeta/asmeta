package org.asmeta.asm2java;

import asmeta.definitions.ControlledFunction;
import asmeta.structure.Asm;
import org.asmeta.parser.util.ReflectiveVisitor;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class FunctionToJavaDef extends ReflectiveVisitor<String> {
  private Asm asm;

  private int i;

  public FunctionToJavaDef(final Asm asm) {
    this.asm = asm;
  }

  public String visit(final ControlledFunction object) {
    throw new Error("Unresolved compilation problems:"
      + "\nno viable alternative at input \'Â\'"
      + "\nmismatched input \'»=0; Â«\' expecting \'}\'"
      + "\nmismatched input \'» < Â«\' expecting \'}\'"
      + "\nmismatched input \'».elems.size(); Â«\' expecting \'}\'"
      + "\nmismatched input \'»++ ){\\r\\n\\t\\t\\t\\t\\t    \\t\\r\\n\\t\\t\\t\\t\\t    \\t\\r\\n\\t\\t\\t\\t\\t    \\tÂ«\' expecting \'}\'"
      + "\nThe method isNotNumerable(Domain) is undefined for the type Util"
      + "\nThe method or field Â is undefined"
      + "\nThe method or field Â is undefined"
      + "\nThe method or field Â is undefined"
      + "\nThe method or field Â is undefined");
  }

  public FunctionToJavaDef(final /* asm */Object __unknown__) {
    throw new Error("Unresolved compilation problems:"
      + "\nmismatched input \'.\' expecting \'{\'");
  }

  private /* Procedure1<? super i> */Object domain;
}
