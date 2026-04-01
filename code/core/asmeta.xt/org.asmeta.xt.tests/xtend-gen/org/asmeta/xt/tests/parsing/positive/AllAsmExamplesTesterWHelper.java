package org.asmeta.xt.tests.parsing.positive;

import com.google.inject.Inject;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import org.asmeta.xt.asmetal.Asm;
import org.asmeta.xt.tests.AsmParseHelper;
import org.asmeta.xt.tests.AsmetaLInjectorProvider;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * test for all the examples in asm_examples
 * 
 * NOT WORKING because import is not working
 */
@RunWith(XtextRunner.class)
@InjectWith(AsmetaLInjectorProvider.class)
@SuppressWarnings("all")
public class AllAsmExamplesTesterWHelper {
  @Inject
  private AsmParseHelper parseHelper;

  @Inject
  @Extension
  private ValidationTestHelper _validationTestHelper;

  @Test
  public void testAllExamples() {
    try {
      final Path examplePath = Paths.get("../../../../asm_examples/examples/");
      Assert.assertTrue(Files.isDirectory(examplePath));
      final Iterator<Path> files = Files.walk(examplePath).iterator();
      while (files.hasNext()) {
        {
          Path fileToRead = files.next();
          if ((fileToRead.toString().endsWith(".asm") && (!fileToRead.toString().contains("deleteme")))) {
            this.testAsmetaXtFile(fileToRead);
          }
        }
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
  public void testSingleExample() {
    this.testAsmetaXtFile(Paths.get("../../../../asm_examples/PillBox/Level0/pillbox_0.asm"));
  }

  protected void testAsmetaXtFile(final Path fileToRead) {
    try {
      System.out.print(("reading " + fileToRead));
      Asm result = this.parseHelper.parse(fileToRead);
      List<Issue> validate = this._validationTestHelper.validate(result);
      final Function1<Issue, Boolean> _function = (Issue s) -> {
        Severity _severity = s.getSeverity();
        return Boolean.valueOf(Objects.equals(_severity, Severity.ERROR));
      };
      boolean realError = IterableExtensions.<Issue>exists(validate, _function);
      if (realError) {
        System.err.println(" error");
        System.out.println(validate.toString());
      } else {
        System.out.println(" ok");
      }
      System.out.println();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
