package org.asmeta.xt.tests.parsing.negative;

import com.google.inject.Inject;
import org.asmeta.xt.asmetal.Asm;
import org.asmeta.xt.asmetal.AsmetalPackage;
import org.asmeta.xt.tests.AsmParseHelper;
import org.asmeta.xt.tests.AsmetaLInjectorProvider;
import org.asmeta.xt.validation.ErrorCode;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
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
public class HeaderNegativeParsingTest {
  @Inject
  private AsmParseHelper parseHelper;

  @Inject
  @Extension
  private ValidationTestHelper _validationTestHelper;

  @Test
  public void testImport() {
    try {
      Asm result = null;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("asm prova");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t");
      _builder.append("import StandardLibrarykjfjajda");
      _builder.newLine();
      _builder.newLine();
      _builder.append("signature:");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("abstract domain Philosopher");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("definitions:");
      _builder.newLine();
      result = this.parseHelper.parse(_builder);
      this._validationTestHelper.assertError(result, AsmetalPackage.Literals.IMPORT_CLAUSE, ErrorCode.IMPORT_CLAUSE__FILE_NOT_FOUND);
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("asm prova");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("import StandardLibrary ( Domains )");
      _builder_1.newLine();
      _builder_1.append("signature:");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("abstract domain Philosopher");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.newLine();
      _builder_1.append("definitions:");
      _builder_1.newLine();
      result = this.parseHelper.parse(_builder_1);
      this._validationTestHelper.assertError(result, AsmetalPackage.Literals.IMPORT_CLAUSE, ErrorCode.IMPORT_CLAUSE__NOT_FOUND);
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append("asm prova");
      _builder_2.newLine();
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("import StandardLibrary ( Integer, Integer )");
      _builder_2.newLine();
      _builder_2.newLine();
      _builder_2.append("signature:");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("abstract domain Philosopher");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.newLine();
      _builder_2.append("definitions:");
      _builder_2.newLine();
      result = this.parseHelper.parse(_builder_2);
      this._validationTestHelper.assertError(result, AsmetalPackage.Literals.IMPORT_CLAUSE, ErrorCode.IMPORT_CLAUSE__CALLED_TWICE);
      StringConcatenation _builder_3 = new StringConcatenation();
      _builder_3.append("asm prova");
      _builder_3.newLine();
      _builder_3.newLine();
      _builder_3.append("\t");
      _builder_3.append("import ");
      _builder_3.newLine();
      _builder_3.newLine();
      _builder_3.append("signature:");
      _builder_3.newLine();
      _builder_3.append("\t");
      _builder_3.append("abstract domain Philosopher");
      _builder_3.newLine();
      _builder_3.append("\t");
      _builder_3.newLine();
      _builder_3.append("definitions:\t\t\t ");
      _builder_3.newLine();
      result = this.parseHelper.parse(_builder_3);
      StringConcatenation _builder_4 = new StringConcatenation();
      _builder_4.append("asm prova");
      _builder_4.newLine();
      _builder_4.newLine();
      _builder_4.append("\t");
      _builder_4.append("import StandardLibrary ( Integer, eq, Integer )");
      _builder_4.newLine();
      _builder_4.newLine();
      _builder_4.append("signature:");
      _builder_4.newLine();
      _builder_4.append("\t");
      _builder_4.append("abstract domain Philosopher");
      _builder_4.newLine();
      _builder_4.append("\t");
      _builder_4.newLine();
      _builder_4.append("definitions:");
      _builder_4.newLine();
      result = this.parseHelper.parse(_builder_4);
      this._validationTestHelper.assertError(result, AsmetalPackage.Literals.IMPORT_CLAUSE, ErrorCode.IMPORT_CLAUSE__CALLED_TWICE);
      StringConcatenation _builder_5 = new StringConcatenation();
      _builder_5.append("asm prova");
      _builder_5.newLine();
      _builder_5.newLine();
      _builder_5.append("\t");
      _builder_5.append("import EmptyLibrary1");
      _builder_5.newLine();
      _builder_5.newLine();
      _builder_5.append("signature:");
      _builder_5.newLine();
      _builder_5.append("\t");
      _builder_5.append("abstract domain Philosopher");
      _builder_5.newLine();
      _builder_5.append("\t");
      _builder_5.newLine();
      _builder_5.append("definitions:");
      _builder_5.newLine();
      result = this.parseHelper.parse(_builder_5);
      this._validationTestHelper.assertWarning(result, AsmetalPackage.Literals.IMPORT_CLAUSE, ErrorCode.IMPORT_CLAUSE__NOTHING_TO_IMPORT);
      StringConcatenation _builder_6 = new StringConcatenation();
      _builder_6.append("asm prova");
      _builder_6.newLine();
      _builder_6.newLine();
      _builder_6.append("\t");
      _builder_6.append("import EmptyLibrary2");
      _builder_6.newLine();
      _builder_6.newLine();
      _builder_6.append("signature:");
      _builder_6.newLine();
      _builder_6.append("\t");
      _builder_6.append("abstract domain Philosopher");
      _builder_6.newLine();
      _builder_6.append("\t");
      _builder_6.newLine();
      _builder_6.append("definitions:");
      _builder_6.newLine();
      result = this.parseHelper.parse(_builder_6);
      this._validationTestHelper.assertWarning(result, AsmetalPackage.Literals.IMPORT_CLAUSE, ErrorCode.IMPORT_CLAUSE__NOTHING_TO_IMPORT);
      StringConcatenation _builder_7 = new StringConcatenation();
      _builder_7.append("asm prova");
      _builder_7.newLine();
      _builder_7.newLine();
      _builder_7.append("\t");
      _builder_7.append("import EmptyLibrary2 ( D )");
      _builder_7.newLine();
      _builder_7.newLine();
      _builder_7.append("signature:");
      _builder_7.newLine();
      _builder_7.append("\t");
      _builder_7.append("abstract domain Philosopher");
      _builder_7.newLine();
      _builder_7.append("\t");
      _builder_7.newLine();
      _builder_7.append("definitions:");
      _builder_7.newLine();
      result = this.parseHelper.parse(_builder_7);
      this._validationTestHelper.assertWarning(result, AsmetalPackage.Literals.IMPORT_CLAUSE, ErrorCode.IMPORT_CLAUSE__NOTHING_TO_IMPORT);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
  public void testExport() {
    try {
      Asm result = null;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("asm prova");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t");
      _builder.append("export Philosopher");
      _builder.newLine();
      _builder.newLine();
      _builder.append("signature:");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("abstract domain Philosopher");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("definitions:\t");
      _builder.newLine();
      result = this.parseHelper.parse(_builder);
      this._validationTestHelper.assertError(result, AsmetalPackage.Literals.EXPORT_CLAUSE, ErrorCode.EXPORT_CLAUSE__NOTHING_TO_EXPORT);
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("asm prova");
      _builder_1.newLine();
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("export Prova");
      _builder_1.newLine();
      _builder_1.newLine();
      _builder_1.append("signature:");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("abstract domain Philosopher");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.newLine();
      _builder_1.append("definitions:\t");
      _builder_1.newLine();
      result = this.parseHelper.parse(_builder_1);
      this._validationTestHelper.assertError(result, AsmetalPackage.Literals.EXPORT_CLAUSE, ErrorCode.EXPORT_CLAUSE__NOT_FOUND);
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append("asm prova");
      _builder_2.newLine();
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("export eq, Philosopher, eq");
      _builder_2.newLine();
      _builder_2.newLine();
      _builder_2.append("signature:");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("abstract domain Philosopher");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("controlled eq : Philosopher -> Philosopher");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.newLine();
      _builder_2.append("definitions:\t");
      _builder_2.newLine();
      result = this.parseHelper.parse(_builder_2);
      this._validationTestHelper.assertError(result, AsmetalPackage.Literals.EXPORT_CLAUSE, ErrorCode.EXPORT_CLAUSE__CALLED_TWICE);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
  public void testSignature() {
    try {
      Asm result = null;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("asm prova");
      _builder.newLine();
      _builder.newLine();
      _builder.append("signature:");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("abstract domain Philosopher");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("abstract domain Philosopher");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("definitions:\t");
      _builder.newLine();
      result = this.parseHelper.parse(_builder);
      this._validationTestHelper.assertError(result, AsmetalPackage.Literals.DOMAIN, ErrorCode.SIGNATURE__DOMAIN_DEFINED_TWICE);
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("asm prova");
      _builder_1.newLine();
      _builder_1.newLine();
      _builder_1.append("signature:");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("abstract domain Philosopher");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("controlled doppio : Philosopher");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("controlled doppio : Philosopher");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.newLine();
      _builder_1.append("definitions:\t\t\t\t ");
      _builder_1.newLine();
      result = this.parseHelper.parse(_builder_1);
      this._validationTestHelper.assertError(result, AsmetalPackage.Literals.FUNCTION, ErrorCode.SIGNATURE__FUNCTION_DEFINED_TWICE);
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append("asm prova");
      _builder_2.newLine();
      _builder_2.newLine();
      _builder_2.append("signature:");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("abstract domain Philosopher");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("controlled doppio : Prod(Philosopher, Philosopher) -> Philosopher");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("controlled doppio : Prod(Philosopher, Philosopher) -> Philosopher");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.newLine();
      _builder_2.append("definitions:\t");
      _builder_2.newLine();
      result = this.parseHelper.parse(_builder_2);
      this._validationTestHelper.assertError(result, AsmetalPackage.Literals.FUNCTION, ErrorCode.SIGNATURE__FUNCTION_DEFINED_TWICE);
      StringConcatenation _builder_3 = new StringConcatenation();
      _builder_3.append("asm prova");
      _builder_3.newLine();
      _builder_3.newLine();
      _builder_3.append("signature:");
      _builder_3.newLine();
      _builder_3.append("\t");
      _builder_3.newLine();
      _builder_3.append("\t");
      _builder_3.append("abstract domain Philosopher");
      _builder_3.newLine();
      _builder_3.append("\t");
      _builder_3.append("abstract domain Cherries");
      _builder_3.newLine();
      _builder_3.append("\t");
      _builder_3.newLine();
      _builder_3.append("\t");
      _builder_3.append("controlled doppio : Philosopher");
      _builder_3.newLine();
      _builder_3.append("\t");
      _builder_3.append("controlled doppio : Cherries");
      _builder_3.newLine();
      _builder_3.append("\t");
      _builder_3.newLine();
      _builder_3.append("definitions:\t\t\t");
      _builder_3.newLine();
      result = this.parseHelper.parse(_builder_3);
      this._validationTestHelper.assertNoErrors(result);
      StringConcatenation _builder_4 = new StringConcatenation();
      _builder_4.append("asm prova");
      _builder_4.newLine();
      _builder_4.newLine();
      _builder_4.append("signature:");
      _builder_4.newLine();
      _builder_4.append("\t");
      _builder_4.newLine();
      _builder_4.append("\t");
      _builder_4.append("abstract domain Philosopher");
      _builder_4.newLine();
      _builder_4.append("\t");
      _builder_4.append("abstract domain Cherries");
      _builder_4.newLine();
      _builder_4.append("\t");
      _builder_4.newLine();
      _builder_4.append("\t");
      _builder_4.append("controlled doppio : Prod(Philosopher, Philosopher) -> Philosopher");
      _builder_4.newLine();
      _builder_4.append("\t");
      _builder_4.append("controlled doppio : Prod(Cherries, Cherries) -> Cherries");
      _builder_4.newLine();
      _builder_4.append("\t");
      _builder_4.newLine();
      _builder_4.append("definitions:\t");
      _builder_4.newLine();
      result = this.parseHelper.parse(_builder_4);
      this._validationTestHelper.assertNoErrors(result);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
  public void testConcreteDomain() {
    try {
      Asm result = null;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("asm prova");
      _builder.newLine();
      _builder.newLine();
      _builder.append("import StandardLibrary");
      _builder.newLine();
      _builder.newLine();
      _builder.append("signature:");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("domain Cherries subsetof Integer");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("domain Prova subsetof Cherries");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("definitions:\t\t\t\t ");
      _builder.newLine();
      result = this.parseHelper.parse(_builder);
      this._validationTestHelper.assertError(result, AsmetalPackage.Literals.CONCRETE_DOMAIN, ErrorCode.CONCRETE_DOMAIN__INVALID_TYPE_DOMAIN);
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("asm prova");
      _builder_1.newLine();
      _builder_1.newLine();
      _builder_1.append("signature:");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("domain Cherries subsetof Prova");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.newLine();
      _builder_1.append("definitions:\t");
      _builder_1.newLine();
      result = this.parseHelper.parse(_builder_1);
      this._validationTestHelper.assertError(result, AsmetalPackage.Literals.CONCRETE_DOMAIN, ErrorCode.SIGNATURE__DOMAIN_NOT_FOUND);
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append("asm prova");
      _builder_2.newLine();
      _builder_2.newLine();
      _builder_2.append("import StandardLibrary ( eq )");
      _builder_2.newLine();
      _builder_2.newLine();
      _builder_2.append("signature:");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("domain Cherries subsetof Integer");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.newLine();
      _builder_2.append("definitions:\t");
      _builder_2.newLine();
      result = this.parseHelper.parse(_builder_2);
      this._validationTestHelper.assertError(result, AsmetalPackage.Literals.CONCRETE_DOMAIN, ErrorCode.SIGNATURE__DOMAIN_NOT_IMPORTED);
      StringConcatenation _builder_3 = new StringConcatenation();
      _builder_3.append("asm prova");
      _builder_3.newLine();
      _builder_3.newLine();
      _builder_3.append("import SmallLibrary");
      _builder_3.newLine();
      _builder_3.newLine();
      _builder_3.append("signature:");
      _builder_3.newLine();
      _builder_3.append("\t");
      _builder_3.newLine();
      _builder_3.append("\t");
      _builder_3.append("domain Cherries subsetof Integer");
      _builder_3.newLine();
      _builder_3.append("\t");
      _builder_3.newLine();
      _builder_3.append("definitions:\t");
      _builder_3.newLine();
      result = this.parseHelper.parse(_builder_3);
      this._validationTestHelper.assertError(result, AsmetalPackage.Literals.CONCRETE_DOMAIN, ErrorCode.SIGNATURE__DOMAIN_NOT_IMPORTED);
      StringConcatenation _builder_4 = new StringConcatenation();
      _builder_4.append("asm prova");
      _builder_4.newLine();
      _builder_4.newLine();
      _builder_4.append("import StandardLibrary");
      _builder_4.newLine();
      _builder_4.newLine();
      _builder_4.append("signature:");
      _builder_4.newLine();
      _builder_4.append("\t");
      _builder_4.newLine();
      _builder_4.append("\t");
      _builder_4.append("domain Cherries subsetof Seq(Integer)");
      _builder_4.newLine();
      _builder_4.append("\t");
      _builder_4.newLine();
      _builder_4.append("definitions:\t\t\t");
      _builder_4.newLine();
      result = this.parseHelper.parse(_builder_4);
      this._validationTestHelper.assertNoErrors(result);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
  public void testBasicDomain() {
    try {
      Asm result = null;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("asm prova");
      _builder.newLine();
      _builder.newLine();
      _builder.append("import StandardLibrary");
      _builder.newLine();
      _builder.newLine();
      _builder.append("signature:");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("basic domain Prova");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("definitions:\t\t");
      _builder.newLine();
      result = this.parseHelper.parse(_builder);
      this._validationTestHelper.assertError(result, AsmetalPackage.Literals.DOMAIN, ErrorCode.BASIC_DOMAIN__INVALID_NAME);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
  public void testStructuredDomainsNotFound() {
    try {
      Asm result = null;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("asm prova");
      _builder.newLine();
      _builder.append("signature:");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("domain Cherries subsetof Bag(Prova)\t\t\t");
      _builder.newLine();
      _builder.append("definitions:\t\t ");
      _builder.newLine();
      result = this.parseHelper.parse(_builder);
      this._validationTestHelper.assertError(result, AsmetalPackage.Literals.DOMAIN, ErrorCode.SIGNATURE__DOMAIN_NOT_FOUND);
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("asm prova");
      _builder_1.newLine();
      _builder_1.append("signature:\t");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("domain Cherries subsetof Seq(Prova)\t\t\t\t");
      _builder_1.newLine();
      _builder_1.append("definitions:\t\t\t");
      _builder_1.newLine();
      result = this.parseHelper.parse(_builder_1);
      this._validationTestHelper.assertError(result, AsmetalPackage.Literals.DOMAIN, ErrorCode.SIGNATURE__DOMAIN_NOT_FOUND);
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append("asm prova");
      _builder_2.newLine();
      _builder_2.append("signature:\t\t\t\t");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("domain Cherries subsetof Powerset(Prova)\t\t\t\t");
      _builder_2.newLine();
      _builder_2.append("definitions:\t\t");
      _builder_2.newLine();
      result = this.parseHelper.parse(_builder_2);
      this._validationTestHelper.assertError(result, AsmetalPackage.Literals.DOMAIN, ErrorCode.SIGNATURE__DOMAIN_NOT_FOUND);
      StringConcatenation _builder_3 = new StringConcatenation();
      _builder_3.append("asm prova");
      _builder_3.newLine();
      _builder_3.append("signature:\t\t\t\t");
      _builder_3.newLine();
      _builder_3.append("\t");
      _builder_3.append("domain Cherries subsetof Prod( Seq(Integer), Prova )\t\t\t\t");
      _builder_3.newLine();
      _builder_3.append("definitions:\t\t");
      _builder_3.newLine();
      result = this.parseHelper.parse(_builder_3);
      this._validationTestHelper.assertError(result, AsmetalPackage.Literals.DOMAIN, ErrorCode.SIGNATURE__DOMAIN_NOT_FOUND);
      StringConcatenation _builder_4 = new StringConcatenation();
      _builder_4.append("asm prova");
      _builder_4.newLine();
      _builder_4.append("signature:\t\t\t");
      _builder_4.newLine();
      _builder_4.append("\t");
      _builder_4.append("anydomain Prova1\t\t\t\t");
      _builder_4.newLine();
      _builder_4.append("\t");
      _builder_4.append("domain Cherries subsetof Prod( Seq(Prova), Prova1 )\t\t\t\t");
      _builder_4.newLine();
      _builder_4.append("definitions:\t\t");
      _builder_4.newLine();
      result = this.parseHelper.parse(_builder_4);
      this._validationTestHelper.assertError(result, AsmetalPackage.Literals.DOMAIN, ErrorCode.SIGNATURE__DOMAIN_NOT_FOUND);
      StringConcatenation _builder_5 = new StringConcatenation();
      _builder_5.append("asm prova");
      _builder_5.newLine();
      _builder_5.append("signature:\t\t\t");
      _builder_5.newLine();
      _builder_5.append("\t");
      _builder_5.append("anydomain Prova1\t\t\t\t");
      _builder_5.newLine();
      _builder_5.append("\t");
      _builder_5.append("domain Cherries subsetof Prod( Seq(Prova1), Prova )\t\t\t\t");
      _builder_5.newLine();
      _builder_5.append("definitions:\t\t");
      _builder_5.newLine();
      result = this.parseHelper.parse(_builder_5);
      this._validationTestHelper.assertError(result, AsmetalPackage.Literals.DOMAIN, ErrorCode.SIGNATURE__DOMAIN_NOT_FOUND);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
  public void testStructuredDomainsNotImported() {
    try {
      Asm result = null;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("asm prova");
      _builder.newLine();
      _builder.append("import SmallLibrary");
      _builder.newLine();
      _builder.append("signature:");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("domain Cherries subsetof Bag(Integer)\t\t\t");
      _builder.newLine();
      _builder.append("definitions:\t\t");
      _builder.newLine();
      result = this.parseHelper.parse(_builder);
      this._validationTestHelper.assertError(result, AsmetalPackage.Literals.DOMAIN, ErrorCode.SIGNATURE__DOMAIN_NOT_IMPORTED);
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("asm prova");
      _builder_1.newLine();
      _builder_1.append("import SmallLibrary");
      _builder_1.newLine();
      _builder_1.append("signature:\t");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("domain Cherries subsetof Seq(Integer)\t\t\t\t");
      _builder_1.newLine();
      _builder_1.append("definitions:\t\t\t");
      _builder_1.newLine();
      result = this.parseHelper.parse(_builder_1);
      this._validationTestHelper.assertError(result, AsmetalPackage.Literals.DOMAIN, ErrorCode.SIGNATURE__DOMAIN_NOT_IMPORTED);
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append("asm prova");
      _builder_2.newLine();
      _builder_2.append("import SmallLibrary");
      _builder_2.newLine();
      _builder_2.append("signature:\t\t\t\t");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("domain Cherries subsetof Powerset(Integer)\t\t\t\t");
      _builder_2.newLine();
      _builder_2.append("definitions:\t\t\t");
      _builder_2.newLine();
      result = this.parseHelper.parse(_builder_2);
      this._validationTestHelper.assertError(result, AsmetalPackage.Literals.DOMAIN, ErrorCode.SIGNATURE__DOMAIN_NOT_IMPORTED);
      StringConcatenation _builder_3 = new StringConcatenation();
      _builder_3.append("asm prova");
      _builder_3.newLine();
      _builder_3.append("import SmallLibrary");
      _builder_3.newLine();
      _builder_3.append("signature:\t\t\t\t");
      _builder_3.newLine();
      _builder_3.append("\t");
      _builder_3.append("domain Cherries subsetof Prod( Seq(Char), Integer )\t\t\t\t");
      _builder_3.newLine();
      _builder_3.append("definitions:\t\t\t");
      _builder_3.newLine();
      result = this.parseHelper.parse(_builder_3);
      this._validationTestHelper.assertError(result, AsmetalPackage.Literals.DOMAIN, ErrorCode.SIGNATURE__DOMAIN_NOT_IMPORTED);
      StringConcatenation _builder_4 = new StringConcatenation();
      _builder_4.append("asm prova");
      _builder_4.newLine();
      _builder_4.append("import SmallLibrary");
      _builder_4.newLine();
      _builder_4.append("signature:\t\t\t");
      _builder_4.newLine();
      _builder_4.append("\t");
      _builder_4.append("anydomain Prova1\t\t\t\t");
      _builder_4.newLine();
      _builder_4.append("\t");
      _builder_4.append("domain Cherries subsetof Prod( Seq(Integer), Char )\t\t\t\t");
      _builder_4.newLine();
      _builder_4.append("definitions:\t\t\t");
      _builder_4.newLine();
      result = this.parseHelper.parse(_builder_4);
      this._validationTestHelper.assertError(result, AsmetalPackage.Literals.DOMAIN, ErrorCode.SIGNATURE__DOMAIN_NOT_IMPORTED);
      StringConcatenation _builder_5 = new StringConcatenation();
      _builder_5.append("asm prova");
      _builder_5.newLine();
      _builder_5.append("import SmallLibrary");
      _builder_5.newLine();
      _builder_5.append("signature:\t\t\t");
      _builder_5.newLine();
      _builder_5.append("\t");
      _builder_5.append("anydomain Prova1\t\t\t\t");
      _builder_5.newLine();
      _builder_5.append("\t");
      _builder_5.append("domain Cherries subsetof Prod( Seq(Prova1), Integer )\t\t\t\t");
      _builder_5.newLine();
      _builder_5.append("definitions:\t\t");
      _builder_5.newLine();
      result = this.parseHelper.parse(_builder_5);
      this._validationTestHelper.assertError(result, AsmetalPackage.Literals.DOMAIN, ErrorCode.SIGNATURE__DOMAIN_NOT_IMPORTED);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
  public void testEnum() {
    try {
      Asm result = null;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("asm prova");
      _builder.newLine();
      _builder.append("signature:");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("enum domain Prova = { AA | BB | CC | DD | CC }\t\t");
      _builder.newLine();
      _builder.append("definitions:\t\t\t");
      _builder.newLine();
      result = this.parseHelper.parse(_builder);
      this._validationTestHelper.assertError(result, AsmetalPackage.Literals.ENUM_ELEMENT, ErrorCode.ENUM_DOMAIN_ALREADY_DECLARED);
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("asm prova");
      _builder_1.newLine();
      _builder_1.append("signature:");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("enum domain Prova1 = { AA | BB | CC | DD }\t\t");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("enum domain Prova2 = { AAA | BBB | CC | DDD }\t");
      _builder_1.newLine();
      _builder_1.append("definitions:\t\t\t");
      _builder_1.newLine();
      result = this.parseHelper.parse(_builder_1);
      this._validationTestHelper.assertError(result, AsmetalPackage.Literals.ENUM_ELEMENT, ErrorCode.ENUM_DOMAIN_ALREADY_DECLARED);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
  public void testFunctionDomainNotFound() {
    try {
      Asm result = null;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("asm prova");
      _builder.newLine();
      _builder.append("import StandardLibrary");
      _builder.newLine();
      _builder.append("signature:");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("controlled mancante : Prova\t\t");
      _builder.newLine();
      _builder.append("definitions:\t\t\t");
      _builder.newLine();
      result = this.parseHelper.parse(_builder);
      this._validationTestHelper.assertError(result, AsmetalPackage.Literals.FUNCTION, ErrorCode.SIGNATURE__DOMAIN_NOT_FOUND);
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("asm prova");
      _builder_1.newLine();
      _builder_1.append("import StandardLibrary");
      _builder_1.newLine();
      _builder_1.append("signature:");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("controlled mancante : Prova\t-> Integer\t");
      _builder_1.newLine();
      _builder_1.append("definitions:\t\t\t");
      _builder_1.newLine();
      result = this.parseHelper.parse(_builder_1);
      this._validationTestHelper.assertError(result, AsmetalPackage.Literals.FUNCTION, ErrorCode.SIGNATURE__DOMAIN_NOT_FOUND);
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append("asm prova");
      _builder_2.newLine();
      _builder_2.append("import StandardLibrary");
      _builder_2.newLine();
      _builder_2.append("signature:");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("controlled mancante : Integer -> Prova\t");
      _builder_2.newLine();
      _builder_2.append("definitions:\t\t");
      _builder_2.newLine();
      result = this.parseHelper.parse(_builder_2);
      this._validationTestHelper.assertError(result, AsmetalPackage.Literals.FUNCTION, ErrorCode.SIGNATURE__DOMAIN_NOT_FOUND);
      StringConcatenation _builder_3 = new StringConcatenation();
      _builder_3.append("asm prova");
      _builder_3.newLine();
      _builder_3.append("import StandardLibrary");
      _builder_3.newLine();
      _builder_3.append("signature:");
      _builder_3.newLine();
      _builder_3.append("\t");
      _builder_3.append("controlled mancante : Prova\t-> Prova");
      _builder_3.newLine();
      _builder_3.append("definitions:\t\t");
      _builder_3.newLine();
      result = this.parseHelper.parse(_builder_3);
      this._validationTestHelper.assertError(result, AsmetalPackage.Literals.FUNCTION, ErrorCode.SIGNATURE__DOMAIN_NOT_FOUND);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
  public void testFunctionDomainNotImported() {
    try {
      Asm result = null;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("asm prova");
      _builder.newLine();
      _builder.append("import SmallLibrary");
      _builder.newLine();
      _builder.append("signature:");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("controlled mancante : Integer\t\t");
      _builder.newLine();
      _builder.append("definitions:\t\t\t\t\t\t\t\t");
      _builder.newLine();
      _builder.append("default init prova  ");
      _builder.newLine();
      result = this.parseHelper.parse(_builder);
      this._validationTestHelper.assertError(result, AsmetalPackage.Literals.FUNCTION, ErrorCode.SIGNATURE__DOMAIN_NOT_IMPORTED);
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("asm prova");
      _builder_1.newLine();
      _builder_1.append("import SmallLibrary");
      _builder_1.newLine();
      _builder_1.append("signature:");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("controlled mancante : Char\t-> Integer\t");
      _builder_1.newLine();
      _builder_1.append("definitions:\t\t\t");
      _builder_1.newLine();
      result = this.parseHelper.parse(_builder_1);
      this._validationTestHelper.assertError(result, AsmetalPackage.Literals.FUNCTION, ErrorCode.SIGNATURE__DOMAIN_NOT_IMPORTED);
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append("asm prova");
      _builder_2.newLine();
      _builder_2.append("import SmallLibrary");
      _builder_2.newLine();
      _builder_2.append("signature:");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("controlled mancante : Integer -> Char\t");
      _builder_2.newLine();
      _builder_2.append("definitions:\t\t");
      _builder_2.newLine();
      result = this.parseHelper.parse(_builder_2);
      this._validationTestHelper.assertError(result, AsmetalPackage.Literals.FUNCTION, ErrorCode.SIGNATURE__DOMAIN_NOT_IMPORTED);
      StringConcatenation _builder_3 = new StringConcatenation();
      _builder_3.append("asm prova");
      _builder_3.newLine();
      _builder_3.append("import SmallLibrary");
      _builder_3.newLine();
      _builder_3.append("signature:");
      _builder_3.newLine();
      _builder_3.append("\t");
      _builder_3.append("controlled mancante : Integer\t-> Integer");
      _builder_3.newLine();
      _builder_3.append("definitions:\t\t");
      _builder_3.newLine();
      result = this.parseHelper.parse(_builder_3);
      this._validationTestHelper.assertError(result, AsmetalPackage.Literals.FUNCTION, ErrorCode.SIGNATURE__DOMAIN_NOT_IMPORTED);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
  public void testFunction() {
    try {
      Asm result = null;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("asm prova");
      _builder.newLine();
      _builder.append("signature:");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("anydomain D");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("local errata : D\t\t");
      _builder.newLine();
      _builder.append("definitions:\t\t ");
      _builder.newLine();
      result = this.parseHelper.parse(_builder);
      this._validationTestHelper.assertError(result, AsmetalPackage.Literals.FUNCTION, ErrorCode.LOCAL_FUNCTION__INVALID_DECLARATION);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
