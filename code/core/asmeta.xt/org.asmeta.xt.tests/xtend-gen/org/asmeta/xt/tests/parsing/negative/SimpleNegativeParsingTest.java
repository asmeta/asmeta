package org.asmeta.xt.tests.parsing.negative;

import com.google.inject.Inject;
import org.asmeta.xt.asmetal.Asm;
import org.asmeta.xt.asmetal.AsmetalPackage;
import org.asmeta.xt.tests.AsmetaLInjectorProvider;
import org.asmeta.xt.validation.ErrorCode;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * A class for validation test (negative test)
 */
@RunWith(XtextRunner.class)
@InjectWith(AsmetaLInjectorProvider.class)
@SuppressWarnings("all")
public class SimpleNegativeParsingTest {
  @Inject
  private ParseHelper<Asm> parseHelper;

  @Inject
  @Extension
  private ValidationTestHelper _validationTestHelper;

  @Test
  public void blanktest() {
  }

  @Test
  public void testAsm() {
    try {
      Asm result = null;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("asm prova");
      _builder.newLine();
      _builder.append("signature:");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("domain NumOfCherries subsetof Integer");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("abstract domain Philosopher");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("controlled cherriesInPlate: Philosopher -> NumOfCherries");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("definitions:");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t");
      _builder.append("rule r_philoRule($p in Philosopher) =");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("if(cherriesInPlate($p) > 0) then");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("cherriesInPlate($p) := cherriesInPlate($p) - 1");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("endif");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("main rule r_main($p in Philosopher) = ");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("choose $p in Philosopher with true do");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("r_philoRule[$p]");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.newLine();
      _builder.append("default init prova : ");
      _builder.newLine();
      result = this.parseHelper.parse(_builder);
      this._validationTestHelper.assertError(result, AsmetalPackage.Literals.ASM, ErrorCode.ASM__INVALID_MAINRULE_ARITY);
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("module prova");
      _builder_1.newLine();
      _builder_1.append("signature:");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("domain NumOfCherries subsetof Integer");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("abstract domain Philosopher");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("controlled cherriesInPlate: Philosopher -> NumOfCherries");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.newLine();
      _builder_1.append("definitions:");
      _builder_1.newLine();
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("rule r_philoRule($p in Philosopher) =");
      _builder_1.newLine();
      _builder_1.append("\t\t\t");
      _builder_1.append("cherriesInPlate($p) := cherriesInPlate($p) - 1");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("main rule r_main = ");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("choose $p in Prova with true do");
      _builder_1.newLine();
      _builder_1.append("\t\t\t");
      _builder_1.append("r_philoRule[$p]");
      _builder_1.newLine();
      _builder_1.append("\t\t\t");
      _builder_1.newLine();
      result = this.parseHelper.parse(_builder_1);
      this._validationTestHelper.assertWarning(result, AsmetalPackage.Literals.ASM, ErrorCode.ASM__MODULE_MAINRULE);
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append("module prova");
      _builder_2.newLine();
      _builder_2.append("signature:");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("domain NumOfCherries subsetof Integer");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("abstract domain Philosopher");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("controlled cherriesInPlate: Philosopher -> NumOfCherries");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.newLine();
      _builder_2.append("definitions:");
      _builder_2.newLine();
      _builder_2.newLine();
      _builder_2.append("default init s0:");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("function cherriesInPlate($p in Philosopher) = 3");
      _builder_2.newLine();
      result = this.parseHelper.parse(_builder_2);
      this._validationTestHelper.assertWarning(result, AsmetalPackage.Literals.ASM, ErrorCode.ASM__MODULE_DEF_INITIAL_STATE);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
