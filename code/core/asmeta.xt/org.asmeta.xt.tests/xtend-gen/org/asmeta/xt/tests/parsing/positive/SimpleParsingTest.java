package org.asmeta.xt.tests.parsing.positive;

import java.io.File;
import org.asmeta.xt.asmetal.Asm;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("all")
public class SimpleParsingTest extends ParserTest {
  @Test
  public void testBlankAsm() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("asm blankpage");
    _builder.newLine();
    _builder.append("signature: ");
    _builder.newLine();
    _builder.append("definitions: ");
    _builder.newLine();
    Asm result = this.test(_builder.toString(), "blankpage");
    Assert.assertEquals(Boolean.valueOf(false), Boolean.valueOf(result.isIsAsynchr()));
    Assert.assertEquals("blankpage", result.getName());
    Assert.assertEquals(0, result.getHeaderSection().getImportClause().size());
    Assert.assertEquals(null, result.getHeaderSection().getExportClause());
    Assert.assertEquals(0, result.getHeaderSection().getSignature().getDomain().size());
    Assert.assertEquals(0, result.getHeaderSection().getSignature().getDomain().size());
    Assert.assertEquals(0, result.getBodySection().getDomainDefinition().size());
    Assert.assertEquals(0, result.getBodySection().getFunctionDefinition().size());
    Assert.assertEquals(0, result.getBodySection().getRuleDeclaration().size());
    Assert.assertEquals(0, result.getBodySection().getInvariantConstraint().size());
    Assert.assertEquals(0, result.getBodySection().getFairnessConstraint().size());
    Assert.assertEquals(0, result.getBodySection().getProperty().size());
    Assert.assertEquals(null, result.getMainrule());
    Assert.assertEquals(0, result.getInitialState().size());
  }

  @Test
  public void testImport() {
    final File f = new File("../../../../asm_examples/STDL/StandardLibrary.asm");
    Assert.assertTrue(f.exists());
    String asmlib = f.getPath().replaceAll("\\\\", "/");
    int _length = asmlib.length();
    int _minus = (_length - 4);
    asmlib = asmlib.substring(0, _minus);
    this.testImport(asmlib);
    this.testImport((("\"" + asmlib) + "\""));
  }

  public void testImport(final String toimport) {
    InputOutput.<String>println(("*** testing with " + toimport));
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("asm import1");
    _builder.newLine();
    _builder.append("import \"../");
    String _plus = (_builder.toString() + toimport);
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("\" ");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("signature: ");
    _builder_1.newLine();
    _builder_1.append("definitions: ");
    _builder_1.newLine();
    final String string = (_plus + _builder_1);
    InputOutput.<String>println(string);
    Asm result = this.test(string, "import1");
  }
}
