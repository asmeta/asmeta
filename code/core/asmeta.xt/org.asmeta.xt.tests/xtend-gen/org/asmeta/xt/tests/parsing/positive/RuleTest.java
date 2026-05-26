package org.asmeta.xt.tests.parsing.positive;

import com.google.inject.Inject;
import org.asmeta.xt.asmetal.Asm;
import org.asmeta.xt.asmetal.ConditionalRule;
import org.asmeta.xt.asmetal.MacroDeclaration;
import org.asmeta.xt.asmetal.Rule;
import org.asmeta.xt.asmetal.UpdateRule;
import org.asmeta.xt.tests.AsmetaLInjectorProvider;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(XtextRunner.class)
@InjectWith(AsmetaLInjectorProvider.class)
@SuppressWarnings("all")
public class RuleTest {
  @Inject
  private ParseHelper<Asm> parseHelper;

  @Inject
  @Extension
  private ValidationTestHelper _validationTestHelper;

  @Test
  public void testUpdateRule() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("asm temp");
      _builder.newLine();
      _builder.newLine();
      _builder.append("import STDL/StandardLibrary");
      _builder.newLine();
      _builder.newLine();
      _builder.append("signature:");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("controlled a: Integer");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("monitored b: Integer    \t\t\t");
      _builder.newLine();
      _builder.append("definitions:    ");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t");
      _builder.append("main rule r_Main = ");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("if b = 10 then ");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("a:= 20");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("endif");
      _builder.newLine();
      _builder.newLine();
      _builder.append("default init s0:");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("function a = 0");
      _builder.newLine();
      Asm result = this.parseHelper.parse(_builder);
      this._validationTestHelper.assertNoErrors(result);
      Assert.assertEquals(Boolean.valueOf(false), Boolean.valueOf(result.isIsAsynchr()));
      Assert.assertEquals("temp", result.getName());
      Assert.assertEquals(1, result.getHeaderSection().getImportClause().size());
      Assert.assertEquals(null, result.getHeaderSection().getExportClause());
      Assert.assertEquals(0, result.getHeaderSection().getSignature().getDomain().size());
      Assert.assertEquals(0, result.getHeaderSection().getSignature().getDomain().size());
      Assert.assertEquals(0, result.getBodySection().getDomainDefinition().size());
      Assert.assertEquals(0, result.getBodySection().getFunctionDefinition().size());
      Assert.assertEquals(0, result.getBodySection().getRuleDeclaration().size());
      Assert.assertEquals(0, result.getBodySection().getInvariantConstraint().size());
      Assert.assertEquals(0, result.getBodySection().getFairnessConstraint().size());
      Assert.assertEquals(0, result.getBodySection().getProperty().size());
      MacroDeclaration _mainrule = result.getMainrule();
      Assert.assertTrue((_mainrule instanceof MacroDeclaration));
      MacroDeclaration _mainrule_1 = result.getMainrule();
      Rule rule = ((MacroDeclaration) _mainrule_1).getRuleBody();
      Assert.assertTrue((rule instanceof ConditionalRule));
      Rule update = ((ConditionalRule) rule).getThenRule();
      Assert.assertTrue((update instanceof UpdateRule));
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
