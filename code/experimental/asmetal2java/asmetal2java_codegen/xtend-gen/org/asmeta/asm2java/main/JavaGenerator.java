package org.asmeta.asm2java.main;

import asmeta.structure.Asm;
import asmeta.transitionrules.basictransitionrules.Rule;
import java.util.List;
import org.junit.Assert;

/**
 * Generates .cpp ASM file
 */
@SuppressWarnings("all")
public class JavaGenerator extends AsmToJavaGenerator {
  private String initConrolledMonitored;

  public void compileAndWrite(final Asm asm, final String writerPath, final TranslatorOptions userOptions) {
    Assert.assertTrue(writerPath.endsWith(".java"));
    this.compileAndWrite(asm, writerPath, "JAVA", userOptions);
  }

  private List<Rule> seqCalledRules;

  private String supp;

  @Override
  public String compileAsm(final Asm asm) {
    throw new Error("Unresolved compilation problems:"
      + "\nno viable alternative at input \'�\'"
      + "\nmismatched input \'�\\r\\n\\t\\t\\t\\t\\t«\' expecting \'}\'"
      + "\nThe method functionSignature(Asm) is undefined"
      + "\nThe method or field asmName� is undefined"
      + "\nThe method or field asmName� is undefined"
      + "\nThe method abstractClassDef(Asm) is undefined"
      + "\nThe method or field � is undefined"
      + "\nUnreachable expression.");
  }
}
