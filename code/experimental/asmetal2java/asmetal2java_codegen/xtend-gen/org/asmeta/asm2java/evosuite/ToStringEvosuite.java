package org.asmeta.asm2java.evosuite;

import asmeta.structure.Asm;
import org.asmeta.asm2java.translator.ToString;

@SuppressWarnings("all")
public class ToStringEvosuite extends ToString {
  public ToStringEvosuite(final Asm resource) {
    super(resource);
  }

  /**
   * Create an instance of the {@code DomainToJavaSigDef} object.
   */
  @Override
  public DomainToJavaEvosuiteSigDef createDomainSigDef(final Asm resource) {
    return new DomainToJavaEvosuiteSigDef(resource);
  }
}
