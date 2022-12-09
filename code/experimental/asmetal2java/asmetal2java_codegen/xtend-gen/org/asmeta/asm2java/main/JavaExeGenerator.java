package org.asmeta.asm2java.main;

import asmeta.structure.Asm;
import asmeta.transitionrules.basictransitionrules.Rule;
import java.util.List;
import org.junit.Assert;

@SuppressWarnings("all")
public class JavaExeGenerator extends AsmToJavaGenerator {
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
      + "\nmismatched input \'»\\r\\n\\t\\t\\t\\t\\t\\r\\n\\t\\t\\t\\t\\t}\\r\\n\\t\\t\\t\\t\\t\\r\\n\\t\\t\\t\\tstatic void askMonitored(Â«\' expecting \'}\'"
      + "\nThe method or field asmNameÂ is undefined"
      + "\nThe method or field asmNameÂ is undefined"
      + "\nThe method or field asmNameÂ is undefined"
      + "\nThe method printControlled(Asm) is undefined"
      + "\nThe method or field Â is undefined"
      + "\nUnreachable expression.");
  }
}
