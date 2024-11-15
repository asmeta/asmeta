package org.asmeta.asm2java.evosuite;

import asmeta.structure.Asm;
import org.asmeta.asm2java.translator.ToString;

/**
 * Redefinition of the {@link ToString} class by adding
 * specific methods for the Evosuite tool
 */
@SuppressWarnings("all")
public class ToStringEvosuite extends ToString {
  public ToStringEvosuite(final Asm resource) {
    super(resource);
  }

  /**
   * Create an instance of the {@code DomainToJavaSigDef} object.
   */
  @Override
  public DomainToJavaEvosuiteSigDef createDomainToJavaSigDef(final Asm resource) {
    return new DomainToJavaEvosuiteSigDef(resource);
  }
}
