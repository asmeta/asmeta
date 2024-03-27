package org.asmeta.asm2java.main;

import asmeta.structure.Asm;
import asmeta.transitionrules.basictransitionrules.Rule;
import java.util.List;
import org.junit.Assert;

@SuppressWarnings("all")
public class JavaWindowGenerator extends AsmToJavaGenerator {
  public void compileAndWrite(final Asm asm, final String writerPath, final TranslatorOptions userOptions) {
    Assert.assertTrue(writerPath.endsWith(".java"));
    this.compileAndWrite(asm, writerPath, "JAVA", userOptions);
  }

  private List<Rule> seqCalledRules;

  private String supp;

  private int NvarC = 1;

  private int NvarM = 1;

  @Override
  public String compileAsm(final Asm asm) {
    throw new Error("Unresolved compilation problems:"
      + "\nno viable alternative at input \'�\'"
      + "\nmismatched input \'�\\r\\n\\t\\t\\t\\t\\t\\t\\r\\n\\t\\t\\t\\t\\t\\t«\' expecting \'}\'"
      + "\nThe method or field name� is undefined for the type Asm"
      + "\nThe method or field name� is undefined for the type Asm"
      + "\nThe method or field name� is undefined for the type Asm"
      + "\nThe method or field name� is undefined for the type Asm"
      + "\nThe method or field name� is undefined for the type Asm"
      + "\nThe method or field name� is undefined for the type Asm"
      + "\nThe method or field name� is undefined for the type Asm"
      + "\nThe method ControlledDeclaration(Asm) is undefined"
      + "\nThe method or field � is undefined"
      + "\nUnreachable expression.");
  }
}
