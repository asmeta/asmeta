package org.asmeta.xt.tests.parsing.positive;

import com.google.inject.Inject;
import org.asmeta.xt.asmetal.Asm;
import org.asmeta.xt.tests.AsmParseHelper;
import org.asmeta.xt.tests.AsmetaLInjectorProvider;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.extensions.InjectionExtension;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(InjectionExtension.class)
@InjectWith(AsmetaLInjectorProvider.class)
@SuppressWarnings("all")
public class ChooseBoolean {
  @Inject
  private AsmParseHelper parseHelper;

  @Inject
  @Extension
  private ValidationTestHelper _validationTestHelper;

  @Test
  public void testChooseBoolean() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("asm blankpage");
      _builder.newLine();
      _builder.append("import STDL/StandardLibrary");
      _builder.newLine();
      _builder.append("signature: ");
      _builder.newLine();
      _builder.append("definitions:");
      _builder.newLine();
      _builder.append("main rule r_main = choose $sendMsg in Boolean with true do //skip");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("if $sendMsg then skip endif ");
      _builder.newLine();
      Asm result = this.parseHelper.parse(_builder);
      this._validationTestHelper.assertNoErrors(result);
      Assertions.assertNotNull(result.getMainrule());
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
  public void testChooseBooleanNoGuard() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("asm blankpage");
      _builder.newLine();
      _builder.append("import STDL/StandardLibrary");
      _builder.newLine();
      _builder.append("signature: ");
      _builder.newLine();
      _builder.append("definitions:");
      _builder.newLine();
      _builder.append("main rule r_main = choose $sendMsg in Boolean do //skip");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("if $sendMsg then skip endif ");
      _builder.newLine();
      Asm result = this.parseHelper.parse(_builder);
      this._validationTestHelper.assertNoErrors(result);
      Assertions.assertNotNull(result.getMainrule());
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
