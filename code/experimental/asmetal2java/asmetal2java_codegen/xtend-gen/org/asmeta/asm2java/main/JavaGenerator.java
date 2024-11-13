package org.asmeta.asm2java.main;

import asmeta.structure.Asm;
import asmeta.transitionrules.basictransitionrules.Rule;
import java.util.List;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
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
      + "\nno viable alternative at input \'Â\'"
      + "\nmismatched input \'»\\r\\n\\t\\t\\t\\tÂ«\' expecting \'}\'"
      + "\nThe method or field ruleDeclaration is undefined for the type Body"
      + "\nThe method updateSet(Asm) is undefined"
      + "\nThe method functionSignature(Asm) is undefined"
      + "\nThe method or field asmNameÂ is undefined"
      + "\nThe method or field asmNameÂ is undefined"
      + "\nThe method abstractClassDef(Asm) is undefined"
      + "\nThe method or field Â is undefined"
      + "\nUnreachable expression.");
  }

  private /* Procedure1<? super initConrolledMonitored.length> */Object initConrolledMonitored;
}
