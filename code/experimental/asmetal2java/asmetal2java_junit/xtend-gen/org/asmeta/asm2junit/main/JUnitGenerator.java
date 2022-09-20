package org.asmeta.asm2junit.main;

import asmeta.structure.Asm;
import asmeta.transitionrules.basictransitionrules.Rule;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.junit.Assert;

@SuppressWarnings("all")
public class JUnitGenerator extends AvallaToJUnitGenerator {
  public void compileAndWrite(final Asm asm, final String writerPath, final TranslatorOptions userOptions) {
    Assert.assertTrue(writerPath.endsWith(".java"));
    this.compileAndWrite(asm, writerPath, "JAVA", userOptions);
  }

  private List<Rule> seqCalledRules;

  private String supp;

  @Override
  public String compileAsm(final Asm asm) {
    if (this.options.optimizeSeqMacroRule) {
      ArrayList<Rule> _arrayList = new ArrayList<Rule>();
      this.seqCalledRules = _arrayList;
    }
    final String asmName = asm.getName();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("// ");
    _builder.append(asmName);
    _builder.append(".java automatically generated from AVALLA2CODE");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("//Struttura Base");
    _builder.newLine();
    _builder.append("/* Import che potrebbero servire durante l\'esecuzione*/");
    _builder.newLine();
    _builder.newLine();
    _builder.append("import static org.junit.Assert.*;");
    _builder.newLine();
    _builder.append("import org.junit.Test;");
    _builder.newLine();
    _builder.append("import java.util.ArrayList;");
    _builder.newLine();
    _builder.append("import java.util.Arrays;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/*AC - PARTE MIA*/");
    _builder.newLine();
    _builder.append("public class ");
    _builder.append(asmName);
    _builder.append("_Test {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@Test");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public void Test{");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("///////////////////////////////");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("// PARTE CONTENENTE COSTRUTTORE");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("///////////////////////////////");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append(asmName, "\t\t");
    _builder.append(" v = new ");
    _builder.append(asmName, "\t\t");
    _builder.append("();");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("//Step -> updateAsm()");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("//valutare imple funzione oppure");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("assertNotNull(v);");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("///////////////////////////////////");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("// PARTE CONTENENTE CHECK - COMMAND");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("///////////////////////////////////");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("/* -> assertTrue <- */");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("/////////////////////////////////");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("// PARTE CONTENENTE SET - COMMAND");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("/////////////////////////////////");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("////////////////////////////////////");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("// PARTE CONTENENENTE STEP - COMMAND");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("////////////////////////////////////");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("//incremento contatore");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// applicazione dell\'aggiornamento del set");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("//Metodo per l\'aggiornamento dell\'asm");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("void UpdateASM()");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("r_Main();");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("fireUpdateSet();");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public static void main(String[] args) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    return _builder.toString();
  }

  public void checkCommand(final Asm asm) {
    System.out.println("CIAO");
  }

  public void setCommand(final Asm asm) {
    System.out.println("CIAO");
  }
}
