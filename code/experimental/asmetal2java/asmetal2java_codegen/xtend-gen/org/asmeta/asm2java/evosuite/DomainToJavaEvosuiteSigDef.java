package org.asmeta.asm2java.evosuite;

import asmeta.structure.Asm;
import org.asmeta.asm2java.translator.DomainToJavaSigDef;

@SuppressWarnings("all")
public class DomainToJavaEvosuiteSigDef extends DomainToJavaSigDef {
  public DomainToJavaEvosuiteSigDef(final Asm resource) {
    super(resource);
  }

  /**
   * Create an instance of the {@code ToStringEvosuite} object.
   */
  @Override
  public ToStringEvosuite createToString(final Asm resource) {
    return new ToStringEvosuite(resource);
  }

  /**
   * if this is an instance of {@code DomainToJavaEvosuiteSigDef} return private
   * because this way Evosuite cannot access static fields.
   */
  @Override
  public String isPrivate() {
    return "private ";
  }
}
