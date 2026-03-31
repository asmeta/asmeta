package org.asmeta.xt.tests.parsing.positive;

import com.google.inject.Inject;
import org.asmeta.xt.asmetal.AbstractTD;
import org.asmeta.xt.asmetal.AnyDomain;
import org.asmeta.xt.asmetal.Asm;
import org.asmeta.xt.asmetal.ConcreteDomain;
import org.asmeta.xt.asmetal.Domain;
import org.asmeta.xt.asmetal.EnumTD;
import org.asmeta.xt.asmetal.Function;
import org.asmeta.xt.asmetal.ProductDomain;
import org.asmeta.xt.asmetal.impl.AbstractTDImpl;
import org.asmeta.xt.asmetal.impl.AnyDomainImpl;
import org.asmeta.xt.asmetal.impl.BagDomainImpl;
import org.asmeta.xt.asmetal.impl.ConcreteDomainImpl;
import org.asmeta.xt.asmetal.impl.ControlledFunctionImpl;
import org.asmeta.xt.asmetal.impl.DerivedFunctionImpl;
import org.asmeta.xt.asmetal.impl.EnumTDImpl;
import org.asmeta.xt.asmetal.impl.MapDomainImpl;
import org.asmeta.xt.asmetal.impl.MonitoredFunctionImpl;
import org.asmeta.xt.asmetal.impl.OutFunctionImpl;
import org.asmeta.xt.asmetal.impl.PowersetDomainImpl;
import org.asmeta.xt.asmetal.impl.ProductDomainImpl;
import org.asmeta.xt.asmetal.impl.RuleDomainImpl;
import org.asmeta.xt.asmetal.impl.SequenceDomainImpl;
import org.asmeta.xt.asmetal.impl.SharedFunctionImpl;
import org.asmeta.xt.asmetal.impl.StaticFunctionImpl;
import org.asmeta.xt.tests.AsmParseHelper;
import org.asmeta.xt.tests.AsmetaLInjectorProvider;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.extensions.InjectionExtension;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(InjectionExtension.class)
@InjectWith(AsmetaLInjectorProvider.class)
@SuppressWarnings("all")
public class HeaderParsingTest {
  @Inject
  private AsmParseHelper parseHelper;

  @Inject
  @Extension
  private ValidationTestHelper _validationTestHelper;

  @Test
  @Tag("TestToMavenSkip")
  public void testOnlyImports() {
    try {
      int i = 0;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("\t");
      _builder.append("asyncr asm only_imports");
      _builder.newLine();
      _builder.append("//\timport moduloprova1.asm");
      _builder.newLine();
      _builder.append("//\timport ../moduloprova2.asm(r_a)");
      _builder.newLine();
      _builder.append("//\timport ../moduloprova3.asm(r_a, ProvaDominio, a)");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("import ../STDL/StandardLibrary");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("import STDL/StandardLibrary");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("import StandardLibrary.asm (Integer, eq)");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("signature:");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("definitions:");
      _builder.newLine();
      Asm result = this.parseHelper.parse(_builder, "only_imports");
      Assertions.assertEquals(3, result.getHeaderSection().getImportClause().size());
      Assertions.assertEquals("../STDL/StandardLibrary", result.getHeaderSection().getImportClause().get(i).getModuleName());
      Assertions.assertEquals(0, result.getHeaderSection().getImportClause().get(i).getImportedList().size());
      i++;
      Assertions.assertEquals("STDL/StandardLibrary", result.getHeaderSection().getImportClause().get(i).getModuleName());
      i++;
      Assertions.assertEquals("StandardLibrary.asm", result.getHeaderSection().getImportClause().get(i).getModuleName());
      Assertions.assertEquals(2, result.getHeaderSection().getImportClause().get(i).getImportedList().size());
      Assertions.assertEquals("Integer", result.getHeaderSection().getImportClause().get(i).getImportedList().get(0).getName());
      Assertions.assertEquals("eq", result.getHeaderSection().getImportClause().get(i).getImportedList().get(1).getName());
      Assertions.assertEquals(result.getHeaderSection().getExportClause(), null);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
  public void testExportAll() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("asyncr asm export_all");
      _builder.newLine();
      _builder.append("export *");
      _builder.newLine();
      _builder.append("signature:");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("anydomain Prova");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("controlled provafunzione1 : Prova -> Prova\t");
      _builder.newLine();
      _builder.append("definitions:");
      _builder.newLine();
      Asm result = this.parseHelper.parse(_builder, "export_all");
      this._validationTestHelper.assertNoErrors(result);
      Assertions.assertEquals(0, result.getHeaderSection().getImportClause().size());
      Assertions.assertEquals(0, result.getHeaderSection().getExportClause().getExportedList().size());
      Assertions.assertEquals(Boolean.valueOf(true), Boolean.valueOf(result.getHeaderSection().getExportClause().isExportAll()));
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
  public void testSingleExport() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("asyncr asm single_export");
      _builder.newLine();
      _builder.append("export provafunzione");
      _builder.newLine();
      _builder.append("signature:");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("anydomain Prova");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("controlled provafunzione : Prova -> Prova\t\t");
      _builder.newLine();
      _builder.append("definitions:");
      _builder.newLine();
      Asm result = this.parseHelper.parse(_builder, "single_export");
      this._validationTestHelper.assertNoErrors(result);
      Assertions.assertEquals(result.getHeaderSection().getImportClause().size(), 0);
      Assertions.assertEquals(1, result.getHeaderSection().getExportClause().getExportedList().size());
      Assertions.assertEquals("provafunzione", result.getHeaderSection().getExportClause().getExportedList().get(0));
      Assertions.assertEquals(Boolean.valueOf(false), Boolean.valueOf(result.getHeaderSection().getExportClause().isExportAll()));
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
  @Tag("TestToMavenSkip")
  public void testMultipleExport() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("asm multiple_export");
      _builder.newLine();
      _builder.append("export provafunzione1, Prova");
      _builder.newLine();
      _builder.append("signature:");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("anydomain Prova");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("controlled provafunzione1 : Prova -> Prova\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("controlled provafunzione2 : Prova -> Prova\t");
      _builder.newLine();
      _builder.newLine();
      _builder.append("definitions:");
      _builder.newLine();
      Asm result = this.parseHelper.parse(_builder);
      this._validationTestHelper.assertNoErrors(result);
      Assertions.assertEquals(result.getHeaderSection().getImportClause().size(), 0);
      Assertions.assertEquals(2, result.getHeaderSection().getExportClause().getExportedList().size());
      Assertions.assertEquals("provafunzione1", result.getHeaderSection().getExportClause().getExportedList().get(0));
      Assertions.assertEquals("Prova", result.getHeaderSection().getExportClause().getExportedList().get(1));
      Assertions.assertEquals(Boolean.valueOf(false), Boolean.valueOf(result.getHeaderSection().getExportClause().isExportAll()));
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
  @Tag("TestToMavenSkip")
  public void testConcreteDomain() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("asm concrete_domain");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t");
      _builder.append("import StandardLibrary");
      _builder.newLine();
      _builder.newLine();
      _builder.append("signature:");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("dynamic domain Prova1 subsetof Integer");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("domain Book subsetof Prod( String, String, Prova1 )");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("domain Prova2 subsetof Integer");
      _builder.newLine();
      _builder.append("definitions:");
      _builder.newLine();
      Asm result = this.parseHelper.parse(_builder);
      this._validationTestHelper.assertNoErrors(result);
      int i = 0;
      Assertions.assertEquals(3, result.getHeaderSection().getSignature().getDomain().size());
      Assertions.assertEquals(0, result.getHeaderSection().getSignature().getFunction().size());
      Domain dom = result.getHeaderSection().getSignature().getDomain().get(i);
      Assertions.assertEquals(ConcreteDomainImpl.class, dom.getClass());
      ConcreteDomain temp = ((ConcreteDomain) dom);
      Assertions.assertEquals(Boolean.valueOf(true), Boolean.valueOf(temp.isDynamic()));
      Assertions.assertEquals("Prova1", temp.getName());
      Assertions.assertEquals("Integer", temp.getTypeDomain().getName());
      i++;
      dom = result.getHeaderSection().getSignature().getDomain().get(i);
      Assertions.assertEquals(ConcreteDomainImpl.class, dom.getClass());
      temp = ((ConcreteDomain) dom);
      Assertions.assertEquals(Boolean.valueOf(false), Boolean.valueOf(temp.isDynamic()));
      Assertions.assertEquals("Book", temp.getName());
      Assertions.assertEquals(ProductDomainImpl.class, temp.getTypeDomain().getClass());
      Domain _typeDomain = temp.getTypeDomain();
      ProductDomain tem_type = ((ProductDomain) _typeDomain);
      Assertions.assertEquals("String", tem_type.getDomains().get(0).getName());
      Assertions.assertEquals("String", tem_type.getDomains().get(1).getName());
      Assertions.assertEquals("Prova1", tem_type.getDomains().get(2).getName());
      i++;
      dom = result.getHeaderSection().getSignature().getDomain().get(i);
      Assertions.assertEquals(ConcreteDomainImpl.class, dom.getClass());
      temp = ((ConcreteDomain) dom);
      Assertions.assertEquals(Boolean.valueOf(false), Boolean.valueOf(temp.isDynamic()));
      Assertions.assertEquals("Prova2", temp.getName());
      Assertions.assertEquals("Integer", temp.getTypeDomain().getName());
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
  @Tag("TestToMavenSkip")
  public void testTypeDomain() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("asm type_domain");
      _builder.newLine();
      _builder.append("signature:");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("anydomain Prova1");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("enum domain Prova2 = { AA }");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("enum domain Prova3 = { CC | BB }");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("dynamic abstract domain Prova4");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("abstract domain Prova5");
      _builder.newLine();
      _builder.append("definitions:");
      _builder.newLine();
      Asm result = this.parseHelper.parse(_builder);
      this._validationTestHelper.assertNoErrors(result);
      int i = 0;
      Assertions.assertEquals(5, result.getHeaderSection().getSignature().getDomain().size());
      Assertions.assertEquals(0, result.getHeaderSection().getSignature().getFunction().size());
      Domain dom = result.getHeaderSection().getSignature().getDomain().get(i);
      Assertions.assertEquals(AnyDomainImpl.class, dom.getClass());
      AnyDomain temp1 = ((AnyDomain) dom);
      Assertions.assertEquals("Prova1", temp1.getName());
      i++;
      dom = result.getHeaderSection().getSignature().getDomain().get(i);
      Assertions.assertEquals(EnumTDImpl.class, dom.getClass());
      EnumTD temp2 = ((EnumTD) dom);
      Assertions.assertEquals("Prova2", temp2.getName());
      Assertions.assertEquals(1, temp2.getElement().size());
      Assertions.assertEquals("AA", temp2.getElement().get(0).getSymbol());
      i++;
      dom = result.getHeaderSection().getSignature().getDomain().get(i);
      Assertions.assertEquals(EnumTDImpl.class, dom.getClass());
      EnumTD temp3 = ((EnumTD) dom);
      Assertions.assertEquals("Prova3", temp3.getName());
      Assertions.assertEquals(2, temp3.getElement().size());
      Assertions.assertEquals("CC", temp3.getElement().get(0).getSymbol());
      Assertions.assertEquals("BB", temp3.getElement().get(1).getSymbol());
      i++;
      dom = result.getHeaderSection().getSignature().getDomain().get(i);
      Assertions.assertEquals(AbstractTDImpl.class, dom.getClass());
      AbstractTD temp4 = ((AbstractTD) dom);
      Assertions.assertEquals(Boolean.valueOf(true), Boolean.valueOf(temp4.isDynamic()));
      Assertions.assertEquals("Prova4", temp4.getName());
      i++;
      dom = result.getHeaderSection().getSignature().getDomain().get(i);
      Assertions.assertEquals(AbstractTDImpl.class, dom.getClass());
      AbstractTD temp5 = ((AbstractTD) dom);
      Assertions.assertEquals(Boolean.valueOf(false), Boolean.valueOf(temp5.isDynamic()));
      Assertions.assertEquals("Prova5", temp5.getName());
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
  @Tag("TestToMavenSkip")
  public void testStructuredDomain() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("asm structured_domain");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t");
      _builder.append("import StandardLibrary");
      _builder.newLine();
      _builder.newLine();
      _builder.append("signature:");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("domain Prova1 subsetof Prod( Integer, Complex )");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("domain Prova2 subsetof Prod( Integer, Integer, Complex )");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("domain Prova3 subsetof Seq( Complex )");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("domain Prova4 subsetof Powerset( Integer )");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("domain Prova5 subsetof Bag( Complex )");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("domain Prova6 subsetof Map( String, String )");
      _builder.newLine();
      _builder.append("definitions:");
      _builder.newLine();
      Asm result = this.parseHelper.parse(_builder, "structured_domain");
      this._validationTestHelper.assertNoErrors(result);
      int i = 0;
      Assertions.assertEquals(6, result.getHeaderSection().getSignature().getDomain().size());
      Assertions.assertEquals(0, result.getHeaderSection().getSignature().getFunction().size());
      Domain conc_dom = result.getHeaderSection().getSignature().getDomain().get(i);
      Assertions.assertEquals("Prova1", conc_dom.getName());
      Assertions.assertEquals(ConcreteDomainImpl.class, conc_dom.getClass());
      Domain dom1 = ((ConcreteDomainImpl) conc_dom).getTypeDomain();
      Assertions.assertEquals(ProductDomainImpl.class, dom1.getClass());
      ProductDomain temp1 = ((ProductDomain) dom1);
      Assertions.assertEquals(2, temp1.getDomains().size());
      Assertions.assertEquals("Integer", temp1.getDomains().get(0).getName());
      Assertions.assertEquals("Complex", temp1.getDomains().get(1).getName());
      i++;
      conc_dom = result.getHeaderSection().getSignature().getDomain().get(i);
      Assertions.assertEquals("Prova2", conc_dom.getName());
      Assertions.assertEquals(ConcreteDomainImpl.class, conc_dom.getClass());
      dom1 = ((ConcreteDomainImpl) conc_dom).getTypeDomain();
      Assertions.assertEquals(ProductDomainImpl.class, dom1.getClass());
      temp1 = ((ProductDomain) dom1);
      Assertions.assertEquals(3, temp1.getDomains().size());
      Assertions.assertEquals("Integer", temp1.getDomains().get(0).getName());
      Assertions.assertEquals("Integer", temp1.getDomains().get(1).getName());
      Assertions.assertEquals("Complex", temp1.getDomains().get(2).getName());
      i++;
      conc_dom = result.getHeaderSection().getSignature().getDomain().get(i);
      Assertions.assertEquals("Prova3", conc_dom.getName());
      Assertions.assertEquals(ConcreteDomainImpl.class, conc_dom.getClass());
      Domain dom2 = ((ConcreteDomainImpl) conc_dom).getTypeDomain();
      Assertions.assertEquals(SequenceDomainImpl.class, dom2.getClass());
      SequenceDomainImpl temp2 = ((SequenceDomainImpl) dom2);
      Assertions.assertEquals("Complex", temp2.getDomain().getName());
      i++;
      conc_dom = result.getHeaderSection().getSignature().getDomain().get(i);
      Assertions.assertEquals("Prova4", conc_dom.getName());
      Assertions.assertEquals(ConcreteDomainImpl.class, conc_dom.getClass());
      Domain dom3 = ((ConcreteDomainImpl) conc_dom).getTypeDomain();
      Assertions.assertEquals(PowersetDomainImpl.class, dom3.getClass());
      PowersetDomainImpl temp3 = ((PowersetDomainImpl) dom3);
      Assertions.assertEquals("Integer", temp3.getBaseDomain().getName());
      i++;
      conc_dom = result.getHeaderSection().getSignature().getDomain().get(i);
      Assertions.assertEquals("Prova5", conc_dom.getName());
      Assertions.assertEquals(ConcreteDomainImpl.class, conc_dom.getClass());
      Domain dom4 = ((ConcreteDomainImpl) conc_dom).getTypeDomain();
      Assertions.assertEquals(BagDomainImpl.class, dom4.getClass());
      BagDomainImpl temp4 = ((BagDomainImpl) dom4);
      Assertions.assertEquals("Complex", temp4.getDomain().getName());
      i++;
      conc_dom = result.getHeaderSection().getSignature().getDomain().get(i);
      Assertions.assertEquals("Prova6", conc_dom.getName());
      Assertions.assertEquals(ConcreteDomainImpl.class, conc_dom.getClass());
      Domain dom5 = ((ConcreteDomainImpl) conc_dom).getTypeDomain();
      Assertions.assertEquals(MapDomainImpl.class, dom5.getClass());
      MapDomainImpl temp5 = ((MapDomainImpl) dom5);
      Assertions.assertEquals("String", temp5.getSourceDomain().getName());
      Assertions.assertEquals("String", temp5.getTargetDomain().getName());
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
  @Tag("TestToMavenSkip")
  public void testMultipleStructuredDomain() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("asm multi_structured_domain");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t");
      _builder.append("import StandardLibrary");
      _builder.newLine();
      _builder.newLine();
      _builder.append("signature:");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("domain Prova1 subsetof Prod( Bag( String ), Integer )");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("domain Prova2 subsetof Seq( Powerset( Complex ) )");
      _builder.newLine();
      _builder.append("definitions:");
      _builder.newLine();
      Asm result = this.parseHelper.parse(_builder);
      this._validationTestHelper.assertNoErrors(result);
      int i = 0;
      Assertions.assertEquals(2, result.getHeaderSection().getSignature().getDomain().size());
      Assertions.assertEquals(0, result.getHeaderSection().getSignature().getFunction().size());
      Domain conc_dom = result.getHeaderSection().getSignature().getDomain().get(i);
      Assertions.assertEquals("Prova1", conc_dom.getName());
      Assertions.assertEquals(ConcreteDomainImpl.class, conc_dom.getClass());
      Domain dom1 = ((ConcreteDomainImpl) conc_dom).getTypeDomain();
      Assertions.assertEquals(ProductDomainImpl.class, dom1.getClass());
      ProductDomainImpl temp1 = ((ProductDomainImpl) dom1);
      Assertions.assertEquals(2, temp1.getDomains().size());
      Domain dom1_child1 = temp1.getDomains().get(0);
      Assertions.assertEquals(BagDomainImpl.class, dom1_child1.getClass());
      BagDomainImpl temp1_child1 = ((BagDomainImpl) dom1_child1);
      Assertions.assertEquals("String", temp1_child1.getDomain().getName());
      Assertions.assertEquals("Integer", temp1.getDomains().get(1).getName());
      i++;
      conc_dom = result.getHeaderSection().getSignature().getDomain().get(i);
      Assertions.assertEquals("Prova2", conc_dom.getName());
      Assertions.assertEquals(ConcreteDomainImpl.class, conc_dom.getClass());
      dom1 = ((ConcreteDomainImpl) conc_dom).getTypeDomain();
      Assertions.assertEquals(SequenceDomainImpl.class, dom1.getClass());
      SequenceDomainImpl temp2 = ((SequenceDomainImpl) dom1);
      dom1_child1 = temp2.getDomain();
      Assertions.assertEquals(PowersetDomainImpl.class, dom1_child1.getClass());
      PowersetDomainImpl temp2_child1 = ((PowersetDomainImpl) dom1_child1);
      Assertions.assertEquals("Complex", temp2_child1.getBaseDomain().getName());
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
  public void testStaticFunction() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("asm static_function");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("import StandardLibrary");
      _builder.newLine();
      _builder.newLine();
      _builder.append("signature:");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("dynamic domain Prova1 subsetof Integer");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("static prova1 : Integer");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("static prova2 : Integer -> Prova1");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("static prova3 : Prod(String, Integer)");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("static prova4 : Prod(Integer, Natural) -> Bag( Integer )");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("static prova5 : Prod( Seq(Integer), Natural) -> Bag( Integer )");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("static program: Agent -> Rule\t");
      _builder.newLine();
      _builder.append("definitions:");
      _builder.newLine();
      Asm result = this.parseHelper.parse(_builder, "static_function");
      this._validationTestHelper.assertNoErrors(result);
      int i = 0;
      Assertions.assertEquals(1, result.getHeaderSection().getSignature().getDomain().size());
      Assertions.assertEquals(6, result.getHeaderSection().getSignature().getFunction().size());
      Function func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assertions.assertEquals(StaticFunctionImpl.class, func.getClass());
      Assertions.assertEquals("prova1", func.getName());
      Assertions.assertEquals(null, func.getDomain());
      Assertions.assertEquals("Integer", func.getCodomain().getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assertions.assertEquals(StaticFunctionImpl.class, func.getClass());
      Assertions.assertEquals("prova2", func.getName());
      Assertions.assertEquals("Integer", func.getDomain().getName());
      Assertions.assertEquals("Prova1", func.getCodomain().getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assertions.assertEquals(StaticFunctionImpl.class, func.getClass());
      Assertions.assertEquals("prova3", func.getName());
      Assertions.assertEquals(null, func.getDomain());
      Assertions.assertEquals(ProductDomainImpl.class, func.getCodomain().getClass());
      Domain _codomain = func.getCodomain();
      ProductDomainImpl cod1 = ((ProductDomainImpl) _codomain);
      Assertions.assertEquals(2, cod1.getDomains().size());
      Assertions.assertEquals("String", cod1.getDomains().get(0).getName());
      Assertions.assertEquals("Integer", cod1.getDomains().get(1).getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assertions.assertEquals(StaticFunctionImpl.class, func.getClass());
      Assertions.assertEquals("prova4", func.getName());
      Assertions.assertEquals(ProductDomainImpl.class, func.getDomain().getClass());
      Domain _domain = func.getDomain();
      ProductDomainImpl dom1 = ((ProductDomainImpl) _domain);
      Assertions.assertEquals(2, dom1.getDomains().size());
      Assertions.assertEquals("Integer", dom1.getDomains().get(0).getName());
      Assertions.assertEquals("Natural", dom1.getDomains().get(1).getName());
      Assertions.assertEquals(BagDomainImpl.class, func.getCodomain().getClass());
      Domain _codomain_1 = func.getCodomain();
      BagDomainImpl cod2 = ((BagDomainImpl) _codomain_1);
      Assertions.assertEquals("Integer", cod2.getDomain().getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assertions.assertEquals(StaticFunctionImpl.class, func.getClass());
      Assertions.assertEquals("prova5", func.getName());
      Assertions.assertEquals(ProductDomainImpl.class, func.getDomain().getClass());
      Domain _domain_1 = func.getDomain();
      dom1 = ((ProductDomainImpl) _domain_1);
      Assertions.assertEquals(2, dom1.getDomains().size());
      Assertions.assertEquals(SequenceDomainImpl.class, dom1.getDomains().get(0).getClass());
      Domain _get = dom1.getDomains().get(0);
      SequenceDomainImpl dom1_child = ((SequenceDomainImpl) _get);
      Assertions.assertEquals("Integer", dom1_child.getDomain().getName());
      Assertions.assertEquals("Natural", dom1.getDomains().get(1).getName());
      Assertions.assertEquals(BagDomainImpl.class, func.getCodomain().getClass());
      Domain _codomain_2 = func.getCodomain();
      cod2 = ((BagDomainImpl) _codomain_2);
      Assertions.assertEquals("Integer", cod2.getDomain().getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assertions.assertEquals(StaticFunctionImpl.class, func.getClass());
      Assertions.assertEquals("program", func.getName());
      Assertions.assertEquals("Agent", func.getDomain().getName());
      Assertions.assertEquals(RuleDomainImpl.class, func.getCodomain().getClass());
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
  @Tag("TestToMavenSkip")
  public void testDynamicFunction() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("asm dynamic_function");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t");
      _builder.append("import StandardLibrary");
      _builder.newLine();
      _builder.newLine();
      _builder.append("signature:");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("dynamic domain Prova1 subsetof Integer");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t");
      _builder.append("/*dynamic local prova11 : String");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("dynamic local prova12 : Prod(String, Complex) -> Bag( Integer )");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("local prova13 : Integer");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("local prova14 : Complex -> Integer*/");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("dynamic controlled prova21 : String");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("dynamic controlled prova22 : Integer -> String");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("controlled prova23 : String");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("controlled prova24 : Integer -> Complex");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("dynamic shared prova31 : String");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("dynamic shared prova32 : Integer -> String");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("shared prova33 : Complex");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("shared prova34 : String -> Integer");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("dynamic monitored prova41 : String");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("dynamic monitored prova42 : Prova1 -> String");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("monitored prova43 : String");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("monitored prova44 : String -> Complex");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("dynamic out prova51 : Complex");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("dynamic out prova52 : String -> String");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("out prova53 : String");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("out prova54 : Integer -> String\t");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t\t\t\t\t");
      _builder.newLine();
      _builder.append("definitions:");
      _builder.newLine();
      Asm result = this.parseHelper.parse(_builder);
      this._validationTestHelper.assertNoErrors(result);
      int i = 0;
      Assertions.assertEquals(1, result.getHeaderSection().getSignature().getDomain().size());
      Assertions.assertEquals(16, result.getHeaderSection().getSignature().getFunction().size());
      Function func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assertions.assertEquals(ControlledFunctionImpl.class, func.getClass());
      ControlledFunctionImpl con_func = ((ControlledFunctionImpl) func);
      Assertions.assertEquals(Boolean.valueOf(true), Boolean.valueOf(con_func.isDynamic()));
      Assertions.assertEquals("prova21", con_func.getName());
      Assertions.assertEquals(null, con_func.getDomain());
      Assertions.assertEquals("String", con_func.getCodomain().getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assertions.assertEquals(ControlledFunctionImpl.class, func.getClass());
      con_func = ((ControlledFunctionImpl) func);
      Assertions.assertEquals(Boolean.valueOf(true), Boolean.valueOf(con_func.isDynamic()));
      Assertions.assertEquals("prova22", con_func.getName());
      Assertions.assertEquals("Integer", con_func.getDomain().getName());
      Assertions.assertEquals("String", con_func.getCodomain().getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assertions.assertEquals(ControlledFunctionImpl.class, func.getClass());
      con_func = ((ControlledFunctionImpl) func);
      Assertions.assertEquals(Boolean.valueOf(false), Boolean.valueOf(con_func.isDynamic()));
      Assertions.assertEquals("prova23", con_func.getName());
      Assertions.assertEquals(null, con_func.getDomain());
      Assertions.assertEquals("String", con_func.getCodomain().getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assertions.assertEquals(ControlledFunctionImpl.class, func.getClass());
      con_func = ((ControlledFunctionImpl) func);
      Assertions.assertEquals(Boolean.valueOf(false), Boolean.valueOf(con_func.isDynamic()));
      Assertions.assertEquals("prova24", con_func.getName());
      Assertions.assertEquals("Integer", con_func.getDomain().getName());
      Assertions.assertEquals("Complex", con_func.getCodomain().getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assertions.assertEquals(SharedFunctionImpl.class, func.getClass());
      SharedFunctionImpl shared_fun = ((SharedFunctionImpl) func);
      Assertions.assertEquals(Boolean.valueOf(true), Boolean.valueOf(shared_fun.isDynamic()));
      Assertions.assertEquals("prova31", shared_fun.getName());
      Assertions.assertEquals(null, shared_fun.getDomain());
      Assertions.assertEquals("String", shared_fun.getCodomain().getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assertions.assertEquals(SharedFunctionImpl.class, func.getClass());
      shared_fun = ((SharedFunctionImpl) func);
      Assertions.assertEquals(Boolean.valueOf(true), Boolean.valueOf(shared_fun.isDynamic()));
      Assertions.assertEquals("prova32", shared_fun.getName());
      Assertions.assertEquals("Integer", shared_fun.getDomain().getName());
      Assertions.assertEquals("String", shared_fun.getCodomain().getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assertions.assertEquals(SharedFunctionImpl.class, func.getClass());
      shared_fun = ((SharedFunctionImpl) func);
      Assertions.assertEquals(Boolean.valueOf(false), Boolean.valueOf(shared_fun.isDynamic()));
      Assertions.assertEquals("prova33", shared_fun.getName());
      Assertions.assertEquals(null, shared_fun.getDomain());
      Assertions.assertEquals("Complex", shared_fun.getCodomain().getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assertions.assertEquals(SharedFunctionImpl.class, func.getClass());
      shared_fun = ((SharedFunctionImpl) func);
      Assertions.assertEquals(Boolean.valueOf(false), Boolean.valueOf(shared_fun.isDynamic()));
      Assertions.assertEquals("prova34", shared_fun.getName());
      Assertions.assertEquals("String", shared_fun.getDomain().getName());
      Assertions.assertEquals("Integer", shared_fun.getCodomain().getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assertions.assertEquals(MonitoredFunctionImpl.class, func.getClass());
      MonitoredFunctionImpl mon_func = ((MonitoredFunctionImpl) func);
      Assertions.assertEquals(Boolean.valueOf(true), Boolean.valueOf(mon_func.isDynamic()));
      Assertions.assertEquals("prova41", mon_func.getName());
      Assertions.assertEquals(null, mon_func.getDomain());
      Assertions.assertEquals("String", mon_func.getCodomain().getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assertions.assertEquals(MonitoredFunctionImpl.class, func.getClass());
      mon_func = ((MonitoredFunctionImpl) func);
      Assertions.assertEquals(Boolean.valueOf(true), Boolean.valueOf(mon_func.isDynamic()));
      Assertions.assertEquals("prova42", mon_func.getName());
      Assertions.assertEquals("Prova1", mon_func.getDomain().getName());
      Assertions.assertEquals("String", mon_func.getCodomain().getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assertions.assertEquals(MonitoredFunctionImpl.class, func.getClass());
      mon_func = ((MonitoredFunctionImpl) func);
      Assertions.assertEquals(Boolean.valueOf(false), Boolean.valueOf(mon_func.isDynamic()));
      Assertions.assertEquals("prova43", mon_func.getName());
      Assertions.assertEquals(null, mon_func.getDomain());
      Assertions.assertEquals("String", mon_func.getCodomain().getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assertions.assertEquals(MonitoredFunctionImpl.class, func.getClass());
      mon_func = ((MonitoredFunctionImpl) func);
      Assertions.assertEquals(Boolean.valueOf(false), Boolean.valueOf(mon_func.isDynamic()));
      Assertions.assertEquals("prova44", mon_func.getName());
      Assertions.assertEquals("String", mon_func.getDomain().getName());
      Assertions.assertEquals("Complex", mon_func.getCodomain().getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assertions.assertEquals(OutFunctionImpl.class, func.getClass());
      OutFunctionImpl fun_out = ((OutFunctionImpl) func);
      Assertions.assertEquals(Boolean.valueOf(true), Boolean.valueOf(fun_out.isDynamic()));
      Assertions.assertEquals("prova51", fun_out.getName());
      Assertions.assertEquals(null, fun_out.getDomain());
      Assertions.assertEquals("Complex", fun_out.getCodomain().getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assertions.assertEquals(OutFunctionImpl.class, func.getClass());
      fun_out = ((OutFunctionImpl) func);
      Assertions.assertEquals(Boolean.valueOf(true), Boolean.valueOf(fun_out.isDynamic()));
      Assertions.assertEquals("prova52", fun_out.getName());
      Assertions.assertEquals("String", fun_out.getDomain().getName());
      Assertions.assertEquals("String", fun_out.getCodomain().getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assertions.assertEquals(OutFunctionImpl.class, func.getClass());
      fun_out = ((OutFunctionImpl) func);
      Assertions.assertEquals(Boolean.valueOf(false), Boolean.valueOf(fun_out.isDynamic()));
      Assertions.assertEquals("prova53", fun_out.getName());
      Assertions.assertEquals(null, fun_out.getDomain());
      Assertions.assertEquals("String", fun_out.getCodomain().getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assertions.assertEquals(OutFunctionImpl.class, func.getClass());
      fun_out = ((OutFunctionImpl) func);
      Assertions.assertEquals(Boolean.valueOf(false), Boolean.valueOf(fun_out.isDynamic()));
      Assertions.assertEquals("prova54", fun_out.getName());
      Assertions.assertEquals("Integer", fun_out.getDomain().getName());
      Assertions.assertEquals("String", fun_out.getCodomain().getName());
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
  @Tag("TestToMavenSkip")
  public void testDerivedFunction() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("asm derived_function");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("import StandardLibrary");
      _builder.newLine();
      _builder.newLine();
      _builder.append("signature:");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("dynamic domain Prova1 subsetof Integer");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t");
      _builder.append("derived prova1 : String");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("derived prova2 : Prova1 -> String");
      _builder.newLine();
      _builder.append("definitions:");
      _builder.newLine();
      Asm result = this.parseHelper.parse(_builder);
      this._validationTestHelper.assertNoErrors(result);
      int i = 0;
      Assertions.assertEquals(1, result.getHeaderSection().getSignature().getDomain().size());
      Assertions.assertEquals(2, result.getHeaderSection().getSignature().getFunction().size());
      Function func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assertions.assertEquals(DerivedFunctionImpl.class, func.getClass());
      Assertions.assertEquals("prova1", func.getName());
      Assertions.assertEquals(null, func.getDomain());
      Assertions.assertEquals("String", func.getCodomain().getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assertions.assertEquals(DerivedFunctionImpl.class, func.getClass());
      Assertions.assertEquals("prova2", func.getName());
      Assertions.assertEquals("Prova1", func.getDomain().getName());
      Assertions.assertEquals("String", func.getCodomain().getName());
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
  @Tag("TestToMavenSkip")
  public void testImportsAbsPathWin() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("asm __tempAsmetaV7859023612479832841");
      _builder.newLine();
      _builder.append("import D:\\\\AgHome\\\\progettidaSVNGIT\\\\asmeta\\\\asmeta_based_applications\\\\fMVC\\\\AMAN\\\\model\\\\StandardLibrary");
      _builder.newLine();
      _builder.append("import D:\\\\AgHome\\\\progettidaSVNGIT\\\\asmeta\\\\asmeta_based_applications\\\\fMVC\\\\AMAN\\\\model\\\\CTLlibrary");
      _builder.newLine();
      _builder.append("import D:\\\\AgHome\\\\progettidaSVNGIT\\\\asmeta\\\\asmeta_based_applications\\\\fMVC\\\\AMAN\\\\model\\\\TimeLibrary");
      _builder.newLine();
      _builder.append("signature:");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("definitions:");
      _builder.newLine();
      Asm result = this.parseHelper.parse(_builder);
      this._validationTestHelper.assertNoErrors(result);
      Assertions.assertEquals(3, result.getHeaderSection().getImportClause().size());
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
  @Tag("TestToMavenSkip")
  public void testPathImports() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("asm __tempAsmetaV7859023612479832841");
      _builder.newLine();
      _builder.append("import a/b");
      _builder.newLine();
      _builder.append("import \\\\AgHome\\\\progettidaSVNGIT\\\\asmeta\\\\asmeta_based_applications\\\\fMVC\\\\AMAN\\\\model\\\\CTLlibrary");
      _builder.newLine();
      _builder.append("signature:");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("definitions:");
      _builder.newLine();
      Asm result = this.parseHelper.parse(_builder, "__tempAsmetaV7859023612479832841");
      this._validationTestHelper.assertNoErrors(result);
      Assertions.assertEquals(3, result.getHeaderSection().getImportClause().size());
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
  @Tag("TestToMavenSkip")
  public void testRelative() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("asm prova");
      _builder.newLine();
      _builder.append("import ../a/b");
      _builder.newLine();
      _builder.append("signature:");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("definitions:");
      _builder.newLine();
      Asm result = this.parseHelper.parse(_builder);
      this._validationTestHelper.assertNoErrors(result);
      Assertions.assertEquals(3, result.getHeaderSection().getImportClause().size());
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
