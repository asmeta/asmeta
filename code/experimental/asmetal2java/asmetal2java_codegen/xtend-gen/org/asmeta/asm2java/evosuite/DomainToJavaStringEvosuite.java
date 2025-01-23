package org.asmeta.asm2java.evosuite;

import asmeta.structure.Asm;
import org.asmeta.asm2java.translator.DomainToJavaString;

/**
 * Redefinition of the {@link DomainToJavaString} class by adding
 * specific methods for the Evosuite tool
 */
@SuppressWarnings("all")
public class DomainToJavaStringEvosuite extends DomainToJavaString {
  public DomainToJavaStringEvosuite(final Asm resource) {
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
