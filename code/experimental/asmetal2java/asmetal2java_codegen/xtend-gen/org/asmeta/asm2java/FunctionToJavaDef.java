package org.asmeta.asm2java;

import asmeta.definitions.ControlledFunction;
import asmeta.structure.Asm;
import asmeta.terms.furtherterms.SequenceTerm;
import org.asmeta.parser.util.ReflectiveVisitor;

@SuppressWarnings("all")
public class FunctionToJavaDef extends ReflectiveVisitor<String> {
  private Asm asm;

  private int i;

  public FunctionToJavaDef(final Asm asm) {
    this.asm = asm;
  }

  public String visit(final SequenceTerm object) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field terms is undefined for the type SequenceTerm");
  }

  public String visit(final ControlledFunction object) {
    throw new Error("Unresolved compilation problems:"
      + "\nno viable alternative at input \'Â\'"
      + "\nmismatched input \'»)));\\r\\n\\t\\t\\t\'\'\'\' expecting \'}\'"
      + "\nThe method or field nameÂ is undefined for the type ControlledFunction"
      + "\nThe method or field initialization is undefined for the type ControlledFunction"
      + "\nThe method or field Â is undefined"
      + "\nget cannot be resolved"
      + "\nbody cannot be resolved");
  }
}
