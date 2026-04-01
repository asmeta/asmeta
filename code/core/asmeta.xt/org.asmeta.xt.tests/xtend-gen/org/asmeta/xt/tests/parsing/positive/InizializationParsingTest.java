package org.asmeta.xt.tests.parsing.positive;

import com.google.inject.Inject;
import org.asmeta.xt.asmetal.Asm;
import org.asmeta.xt.tests.AsmetaLInjectorProvider;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(XtextRunner.class)
@InjectWith(AsmetaLInjectorProvider.class)
@SuppressWarnings("all")
public class InizializationParsingTest {
  @Inject
  private ParseHelper<Asm> parseHelper;

  @Inject
  @Extension
  private ValidationTestHelper _validationTestHelper;

  @Test
  public void testInitialization() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("asm prova");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("import StandardLibrary");
      _builder.newLine();
      _builder.newLine();
      _builder.append("signature: ");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("dynamic domain Prova1 subsetof String");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("dynamic domain Prova2 subsetof String");
      _builder.newLine();
      _builder.append("definitions: ");
      _builder.newLine();
      _builder.append("default init prova : ");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("domain Prova1 = { \"a\", \"b\", \"c\" }");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("domain Prova2 = { \"a\", \"b\", \"c\" }");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("//function prova1 = \'a\'");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("//function prova2($a in Prova1, $b in Prova2) = a");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("//function Prova = \"testo con spazi\"");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("//agent Agente : r_a[]");
      _builder.newLine();
      Asm result = this.parseHelper.parse(_builder);
      this._validationTestHelper.assertNoErrors(result);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
