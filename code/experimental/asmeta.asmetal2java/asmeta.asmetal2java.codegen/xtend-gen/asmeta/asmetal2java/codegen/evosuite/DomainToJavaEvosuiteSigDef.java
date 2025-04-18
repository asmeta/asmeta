package asmeta.asmetal2java.codegen.evosuite;

import asmeta.asmetal2java.codegen.translator.DomainToJavaSigDef;
import asmeta.structure.Asm;

/**
 * Redefinition of the {@link DomainToJavaSigDef} class by adding
 * specific methods for the Evosuite tool
 */
@SuppressWarnings("all")
public class DomainToJavaEvosuiteSigDef extends DomainToJavaSigDef {
  public DomainToJavaEvosuiteSigDef(final Asm resource) {
    super(resource);
  }

  /**
   * Create an instance of the {@code ToStringEvosuite} object.
   */
  @Override
  public DomainToJavaStringEvosuite createDomainToJavaString(final Asm resource) {
    return new DomainToJavaStringEvosuite(resource);
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
