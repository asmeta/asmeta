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
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(XtextRunner.class)
@InjectWith(AsmetaLInjectorProvider.class)
@SuppressWarnings("all")
public class HeaderParsingTest {
  @Inject
  private AsmParseHelper parseHelper;

  @Inject
  @Extension
  private ValidationTestHelper _validationTestHelper;

  @Test
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
      Assert.assertEquals(3, result.getHeaderSection().getImportClause().size());
      Assert.assertEquals("../STDL/StandardLibrary", result.getHeaderSection().getImportClause().get(i).getModuleName());
      Assert.assertEquals(0, result.getHeaderSection().getImportClause().get(i).getImportedList().size());
      i++;
      Assert.assertEquals("STDL/StandardLibrary", result.getHeaderSection().getImportClause().get(i).getModuleName());
      i++;
      Assert.assertEquals("StandardLibrary.asm", result.getHeaderSection().getImportClause().get(i).getModuleName());
      Assert.assertEquals(2, result.getHeaderSection().getImportClause().get(i).getImportedList().size());
      Assert.assertEquals("Integer", result.getHeaderSection().getImportClause().get(i).getImportedList().get(0).getName());
      Assert.assertEquals("eq", result.getHeaderSection().getImportClause().get(i).getImportedList().get(1).getName());
      Assert.assertEquals(result.getHeaderSection().getExportClause(), null);
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
      Assert.assertEquals(0, result.getHeaderSection().getImportClause().size());
      Assert.assertEquals(0, result.getHeaderSection().getExportClause().getExportedList().size());
      Assert.assertEquals(Boolean.valueOf(true), Boolean.valueOf(result.getHeaderSection().getExportClause().isExportAll()));
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
      Assert.assertEquals(result.getHeaderSection().getImportClause().size(), 0);
      Assert.assertEquals(1, result.getHeaderSection().getExportClause().getExportedList().size());
      Assert.assertEquals("provafunzione", result.getHeaderSection().getExportClause().getExportedList().get(0));
      Assert.assertEquals(Boolean.valueOf(false), Boolean.valueOf(result.getHeaderSection().getExportClause().isExportAll()));
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
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
      Assert.assertEquals(result.getHeaderSection().getImportClause().size(), 0);
      Assert.assertEquals(2, result.getHeaderSection().getExportClause().getExportedList().size());
      Assert.assertEquals("provafunzione1", result.getHeaderSection().getExportClause().getExportedList().get(0));
      Assert.assertEquals("Prova", result.getHeaderSection().getExportClause().getExportedList().get(1));
      Assert.assertEquals(Boolean.valueOf(false), Boolean.valueOf(result.getHeaderSection().getExportClause().isExportAll()));
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
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
      Assert.assertEquals(3, result.getHeaderSection().getSignature().getDomain().size());
      Assert.assertEquals(0, result.getHeaderSection().getSignature().getFunction().size());
      Domain dom = result.getHeaderSection().getSignature().getDomain().get(i);
      Assert.assertEquals(ConcreteDomainImpl.class, dom.getClass());
      ConcreteDomain temp = ((ConcreteDomain) dom);
      Assert.assertEquals(Boolean.valueOf(true), Boolean.valueOf(temp.isDynamic()));
      Assert.assertEquals("Prova1", temp.getName());
      Assert.assertEquals("Integer", temp.getTypeDomain().getName());
      i++;
      dom = result.getHeaderSection().getSignature().getDomain().get(i);
      Assert.assertEquals(ConcreteDomainImpl.class, dom.getClass());
      temp = ((ConcreteDomain) dom);
      Assert.assertEquals(Boolean.valueOf(false), Boolean.valueOf(temp.isDynamic()));
      Assert.assertEquals("Book", temp.getName());
      Assert.assertEquals(ProductDomainImpl.class, temp.getTypeDomain().getClass());
      Domain _typeDomain = temp.getTypeDomain();
      ProductDomain tem_type = ((ProductDomain) _typeDomain);
      Assert.assertEquals("String", tem_type.getDomains().get(0).getName());
      Assert.assertEquals("String", tem_type.getDomains().get(1).getName());
      Assert.assertEquals("Prova1", tem_type.getDomains().get(2).getName());
      i++;
      dom = result.getHeaderSection().getSignature().getDomain().get(i);
      Assert.assertEquals(ConcreteDomainImpl.class, dom.getClass());
      temp = ((ConcreteDomain) dom);
      Assert.assertEquals(Boolean.valueOf(false), Boolean.valueOf(temp.isDynamic()));
      Assert.assertEquals("Prova2", temp.getName());
      Assert.assertEquals("Integer", temp.getTypeDomain().getName());
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
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
      Assert.assertEquals(5, result.getHeaderSection().getSignature().getDomain().size());
      Assert.assertEquals(0, result.getHeaderSection().getSignature().getFunction().size());
      Domain dom = result.getHeaderSection().getSignature().getDomain().get(i);
      Assert.assertEquals(AnyDomainImpl.class, dom.getClass());
      AnyDomain temp1 = ((AnyDomain) dom);
      Assert.assertEquals("Prova1", temp1.getName());
      i++;
      dom = result.getHeaderSection().getSignature().getDomain().get(i);
      Assert.assertEquals(EnumTDImpl.class, dom.getClass());
      EnumTD temp2 = ((EnumTD) dom);
      Assert.assertEquals("Prova2", temp2.getName());
      Assert.assertEquals(1, temp2.getElement().size());
      Assert.assertEquals("AA", temp2.getElement().get(0).getSymbol());
      i++;
      dom = result.getHeaderSection().getSignature().getDomain().get(i);
      Assert.assertEquals(EnumTDImpl.class, dom.getClass());
      EnumTD temp3 = ((EnumTD) dom);
      Assert.assertEquals("Prova3", temp3.getName());
      Assert.assertEquals(2, temp3.getElement().size());
      Assert.assertEquals("CC", temp3.getElement().get(0).getSymbol());
      Assert.assertEquals("BB", temp3.getElement().get(1).getSymbol());
      i++;
      dom = result.getHeaderSection().getSignature().getDomain().get(i);
      Assert.assertEquals(AbstractTDImpl.class, dom.getClass());
      AbstractTD temp4 = ((AbstractTD) dom);
      Assert.assertEquals(Boolean.valueOf(true), Boolean.valueOf(temp4.isDynamic()));
      Assert.assertEquals("Prova4", temp4.getName());
      i++;
      dom = result.getHeaderSection().getSignature().getDomain().get(i);
      Assert.assertEquals(AbstractTDImpl.class, dom.getClass());
      AbstractTD temp5 = ((AbstractTD) dom);
      Assert.assertEquals(Boolean.valueOf(false), Boolean.valueOf(temp5.isDynamic()));
      Assert.assertEquals("Prova5", temp5.getName());
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
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
      Assert.assertEquals(6, result.getHeaderSection().getSignature().getDomain().size());
      Assert.assertEquals(0, result.getHeaderSection().getSignature().getFunction().size());
      Domain conc_dom = result.getHeaderSection().getSignature().getDomain().get(i);
      Assert.assertEquals("Prova1", conc_dom.getName());
      Assert.assertEquals(ConcreteDomainImpl.class, conc_dom.getClass());
      Domain dom1 = ((ConcreteDomainImpl) conc_dom).getTypeDomain();
      Assert.assertEquals(ProductDomainImpl.class, dom1.getClass());
      ProductDomain temp1 = ((ProductDomain) dom1);
      Assert.assertEquals(2, temp1.getDomains().size());
      Assert.assertEquals("Integer", temp1.getDomains().get(0).getName());
      Assert.assertEquals("Complex", temp1.getDomains().get(1).getName());
      i++;
      conc_dom = result.getHeaderSection().getSignature().getDomain().get(i);
      Assert.assertEquals("Prova2", conc_dom.getName());
      Assert.assertEquals(ConcreteDomainImpl.class, conc_dom.getClass());
      dom1 = ((ConcreteDomainImpl) conc_dom).getTypeDomain();
      Assert.assertEquals(ProductDomainImpl.class, dom1.getClass());
      temp1 = ((ProductDomain) dom1);
      Assert.assertEquals(3, temp1.getDomains().size());
      Assert.assertEquals("Integer", temp1.getDomains().get(0).getName());
      Assert.assertEquals("Integer", temp1.getDomains().get(1).getName());
      Assert.assertEquals("Complex", temp1.getDomains().get(2).getName());
      i++;
      conc_dom = result.getHeaderSection().getSignature().getDomain().get(i);
      Assert.assertEquals("Prova3", conc_dom.getName());
      Assert.assertEquals(ConcreteDomainImpl.class, conc_dom.getClass());
      Domain dom2 = ((ConcreteDomainImpl) conc_dom).getTypeDomain();
      Assert.assertEquals(SequenceDomainImpl.class, dom2.getClass());
      SequenceDomainImpl temp2 = ((SequenceDomainImpl) dom2);
      Assert.assertEquals("Complex", temp2.getDomain().getName());
      i++;
      conc_dom = result.getHeaderSection().getSignature().getDomain().get(i);
      Assert.assertEquals("Prova4", conc_dom.getName());
      Assert.assertEquals(ConcreteDomainImpl.class, conc_dom.getClass());
      Domain dom3 = ((ConcreteDomainImpl) conc_dom).getTypeDomain();
      Assert.assertEquals(PowersetDomainImpl.class, dom3.getClass());
      PowersetDomainImpl temp3 = ((PowersetDomainImpl) dom3);
      Assert.assertEquals("Integer", temp3.getBaseDomain().getName());
      i++;
      conc_dom = result.getHeaderSection().getSignature().getDomain().get(i);
      Assert.assertEquals("Prova5", conc_dom.getName());
      Assert.assertEquals(ConcreteDomainImpl.class, conc_dom.getClass());
      Domain dom4 = ((ConcreteDomainImpl) conc_dom).getTypeDomain();
      Assert.assertEquals(BagDomainImpl.class, dom4.getClass());
      BagDomainImpl temp4 = ((BagDomainImpl) dom4);
      Assert.assertEquals("Complex", temp4.getDomain().getName());
      i++;
      conc_dom = result.getHeaderSection().getSignature().getDomain().get(i);
      Assert.assertEquals("Prova6", conc_dom.getName());
      Assert.assertEquals(ConcreteDomainImpl.class, conc_dom.getClass());
      Domain dom5 = ((ConcreteDomainImpl) conc_dom).getTypeDomain();
      Assert.assertEquals(MapDomainImpl.class, dom5.getClass());
      MapDomainImpl temp5 = ((MapDomainImpl) dom5);
      Assert.assertEquals("String", temp5.getSourceDomain().getName());
      Assert.assertEquals("String", temp5.getTargetDomain().getName());
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
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
      Assert.assertEquals(2, result.getHeaderSection().getSignature().getDomain().size());
      Assert.assertEquals(0, result.getHeaderSection().getSignature().getFunction().size());
      Domain conc_dom = result.getHeaderSection().getSignature().getDomain().get(i);
      Assert.assertEquals("Prova1", conc_dom.getName());
      Assert.assertEquals(ConcreteDomainImpl.class, conc_dom.getClass());
      Domain dom1 = ((ConcreteDomainImpl) conc_dom).getTypeDomain();
      Assert.assertEquals(ProductDomainImpl.class, dom1.getClass());
      ProductDomainImpl temp1 = ((ProductDomainImpl) dom1);
      Assert.assertEquals(2, temp1.getDomains().size());
      Domain dom1_child1 = temp1.getDomains().get(0);
      Assert.assertEquals(BagDomainImpl.class, dom1_child1.getClass());
      BagDomainImpl temp1_child1 = ((BagDomainImpl) dom1_child1);
      Assert.assertEquals("String", temp1_child1.getDomain().getName());
      Assert.assertEquals("Integer", temp1.getDomains().get(1).getName());
      i++;
      conc_dom = result.getHeaderSection().getSignature().getDomain().get(i);
      Assert.assertEquals("Prova2", conc_dom.getName());
      Assert.assertEquals(ConcreteDomainImpl.class, conc_dom.getClass());
      dom1 = ((ConcreteDomainImpl) conc_dom).getTypeDomain();
      Assert.assertEquals(SequenceDomainImpl.class, dom1.getClass());
      SequenceDomainImpl temp2 = ((SequenceDomainImpl) dom1);
      dom1_child1 = temp2.getDomain();
      Assert.assertEquals(PowersetDomainImpl.class, dom1_child1.getClass());
      PowersetDomainImpl temp2_child1 = ((PowersetDomainImpl) dom1_child1);
      Assert.assertEquals("Complex", temp2_child1.getBaseDomain().getName());
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
      Assert.assertEquals(1, result.getHeaderSection().getSignature().getDomain().size());
      Assert.assertEquals(6, result.getHeaderSection().getSignature().getFunction().size());
      Function func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assert.assertEquals(StaticFunctionImpl.class, func.getClass());
      Assert.assertEquals("prova1", func.getName());
      Assert.assertEquals(null, func.getDomain());
      Assert.assertEquals("Integer", func.getCodomain().getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assert.assertEquals(StaticFunctionImpl.class, func.getClass());
      Assert.assertEquals("prova2", func.getName());
      Assert.assertEquals("Integer", func.getDomain().getName());
      Assert.assertEquals("Prova1", func.getCodomain().getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assert.assertEquals(StaticFunctionImpl.class, func.getClass());
      Assert.assertEquals("prova3", func.getName());
      Assert.assertEquals(null, func.getDomain());
      Assert.assertEquals(ProductDomainImpl.class, func.getCodomain().getClass());
      Domain _codomain = func.getCodomain();
      ProductDomainImpl cod1 = ((ProductDomainImpl) _codomain);
      Assert.assertEquals(2, cod1.getDomains().size());
      Assert.assertEquals("String", cod1.getDomains().get(0).getName());
      Assert.assertEquals("Integer", cod1.getDomains().get(1).getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assert.assertEquals(StaticFunctionImpl.class, func.getClass());
      Assert.assertEquals("prova4", func.getName());
      Assert.assertEquals(ProductDomainImpl.class, func.getDomain().getClass());
      Domain _domain = func.getDomain();
      ProductDomainImpl dom1 = ((ProductDomainImpl) _domain);
      Assert.assertEquals(2, dom1.getDomains().size());
      Assert.assertEquals("Integer", dom1.getDomains().get(0).getName());
      Assert.assertEquals("Natural", dom1.getDomains().get(1).getName());
      Assert.assertEquals(BagDomainImpl.class, func.getCodomain().getClass());
      Domain _codomain_1 = func.getCodomain();
      BagDomainImpl cod2 = ((BagDomainImpl) _codomain_1);
      Assert.assertEquals("Integer", cod2.getDomain().getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assert.assertEquals(StaticFunctionImpl.class, func.getClass());
      Assert.assertEquals("prova5", func.getName());
      Assert.assertEquals(ProductDomainImpl.class, func.getDomain().getClass());
      Domain _domain_1 = func.getDomain();
      dom1 = ((ProductDomainImpl) _domain_1);
      Assert.assertEquals(2, dom1.getDomains().size());
      Assert.assertEquals(SequenceDomainImpl.class, dom1.getDomains().get(0).getClass());
      Domain _get = dom1.getDomains().get(0);
      SequenceDomainImpl dom1_child = ((SequenceDomainImpl) _get);
      Assert.assertEquals("Integer", dom1_child.getDomain().getName());
      Assert.assertEquals("Natural", dom1.getDomains().get(1).getName());
      Assert.assertEquals(BagDomainImpl.class, func.getCodomain().getClass());
      Domain _codomain_2 = func.getCodomain();
      cod2 = ((BagDomainImpl) _codomain_2);
      Assert.assertEquals("Integer", cod2.getDomain().getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assert.assertEquals(StaticFunctionImpl.class, func.getClass());
      Assert.assertEquals("program", func.getName());
      Assert.assertEquals("Agent", func.getDomain().getName());
      Assert.assertEquals(RuleDomainImpl.class, func.getCodomain().getClass());
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
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
      Assert.assertEquals(1, result.getHeaderSection().getSignature().getDomain().size());
      Assert.assertEquals(16, result.getHeaderSection().getSignature().getFunction().size());
      Function func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assert.assertEquals(ControlledFunctionImpl.class, func.getClass());
      ControlledFunctionImpl con_func = ((ControlledFunctionImpl) func);
      Assert.assertEquals(Boolean.valueOf(true), Boolean.valueOf(con_func.isDynamic()));
      Assert.assertEquals("prova21", con_func.getName());
      Assert.assertEquals(null, con_func.getDomain());
      Assert.assertEquals("String", con_func.getCodomain().getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assert.assertEquals(ControlledFunctionImpl.class, func.getClass());
      con_func = ((ControlledFunctionImpl) func);
      Assert.assertEquals(Boolean.valueOf(true), Boolean.valueOf(con_func.isDynamic()));
      Assert.assertEquals("prova22", con_func.getName());
      Assert.assertEquals("Integer", con_func.getDomain().getName());
      Assert.assertEquals("String", con_func.getCodomain().getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assert.assertEquals(ControlledFunctionImpl.class, func.getClass());
      con_func = ((ControlledFunctionImpl) func);
      Assert.assertEquals(Boolean.valueOf(false), Boolean.valueOf(con_func.isDynamic()));
      Assert.assertEquals("prova23", con_func.getName());
      Assert.assertEquals(null, con_func.getDomain());
      Assert.assertEquals("String", con_func.getCodomain().getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assert.assertEquals(ControlledFunctionImpl.class, func.getClass());
      con_func = ((ControlledFunctionImpl) func);
      Assert.assertEquals(Boolean.valueOf(false), Boolean.valueOf(con_func.isDynamic()));
      Assert.assertEquals("prova24", con_func.getName());
      Assert.assertEquals("Integer", con_func.getDomain().getName());
      Assert.assertEquals("Complex", con_func.getCodomain().getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assert.assertEquals(SharedFunctionImpl.class, func.getClass());
      SharedFunctionImpl shared_fun = ((SharedFunctionImpl) func);
      Assert.assertEquals(Boolean.valueOf(true), Boolean.valueOf(shared_fun.isDynamic()));
      Assert.assertEquals("prova31", shared_fun.getName());
      Assert.assertEquals(null, shared_fun.getDomain());
      Assert.assertEquals("String", shared_fun.getCodomain().getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assert.assertEquals(SharedFunctionImpl.class, func.getClass());
      shared_fun = ((SharedFunctionImpl) func);
      Assert.assertEquals(Boolean.valueOf(true), Boolean.valueOf(shared_fun.isDynamic()));
      Assert.assertEquals("prova32", shared_fun.getName());
      Assert.assertEquals("Integer", shared_fun.getDomain().getName());
      Assert.assertEquals("String", shared_fun.getCodomain().getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assert.assertEquals(SharedFunctionImpl.class, func.getClass());
      shared_fun = ((SharedFunctionImpl) func);
      Assert.assertEquals(Boolean.valueOf(false), Boolean.valueOf(shared_fun.isDynamic()));
      Assert.assertEquals("prova33", shared_fun.getName());
      Assert.assertEquals(null, shared_fun.getDomain());
      Assert.assertEquals("Complex", shared_fun.getCodomain().getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assert.assertEquals(SharedFunctionImpl.class, func.getClass());
      shared_fun = ((SharedFunctionImpl) func);
      Assert.assertEquals(Boolean.valueOf(false), Boolean.valueOf(shared_fun.isDynamic()));
      Assert.assertEquals("prova34", shared_fun.getName());
      Assert.assertEquals("String", shared_fun.getDomain().getName());
      Assert.assertEquals("Integer", shared_fun.getCodomain().getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assert.assertEquals(MonitoredFunctionImpl.class, func.getClass());
      MonitoredFunctionImpl mon_func = ((MonitoredFunctionImpl) func);
      Assert.assertEquals(Boolean.valueOf(true), Boolean.valueOf(mon_func.isDynamic()));
      Assert.assertEquals("prova41", mon_func.getName());
      Assert.assertEquals(null, mon_func.getDomain());
      Assert.assertEquals("String", mon_func.getCodomain().getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assert.assertEquals(MonitoredFunctionImpl.class, func.getClass());
      mon_func = ((MonitoredFunctionImpl) func);
      Assert.assertEquals(Boolean.valueOf(true), Boolean.valueOf(mon_func.isDynamic()));
      Assert.assertEquals("prova42", mon_func.getName());
      Assert.assertEquals("Prova1", mon_func.getDomain().getName());
      Assert.assertEquals("String", mon_func.getCodomain().getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assert.assertEquals(MonitoredFunctionImpl.class, func.getClass());
      mon_func = ((MonitoredFunctionImpl) func);
      Assert.assertEquals(Boolean.valueOf(false), Boolean.valueOf(mon_func.isDynamic()));
      Assert.assertEquals("prova43", mon_func.getName());
      Assert.assertEquals(null, mon_func.getDomain());
      Assert.assertEquals("String", mon_func.getCodomain().getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assert.assertEquals(MonitoredFunctionImpl.class, func.getClass());
      mon_func = ((MonitoredFunctionImpl) func);
      Assert.assertEquals(Boolean.valueOf(false), Boolean.valueOf(mon_func.isDynamic()));
      Assert.assertEquals("prova44", mon_func.getName());
      Assert.assertEquals("String", mon_func.getDomain().getName());
      Assert.assertEquals("Complex", mon_func.getCodomain().getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assert.assertEquals(OutFunctionImpl.class, func.getClass());
      OutFunctionImpl fun_out = ((OutFunctionImpl) func);
      Assert.assertEquals(Boolean.valueOf(true), Boolean.valueOf(fun_out.isDynamic()));
      Assert.assertEquals("prova51", fun_out.getName());
      Assert.assertEquals(null, fun_out.getDomain());
      Assert.assertEquals("Complex", fun_out.getCodomain().getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assert.assertEquals(OutFunctionImpl.class, func.getClass());
      fun_out = ((OutFunctionImpl) func);
      Assert.assertEquals(Boolean.valueOf(true), Boolean.valueOf(fun_out.isDynamic()));
      Assert.assertEquals("prova52", fun_out.getName());
      Assert.assertEquals("String", fun_out.getDomain().getName());
      Assert.assertEquals("String", fun_out.getCodomain().getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assert.assertEquals(OutFunctionImpl.class, func.getClass());
      fun_out = ((OutFunctionImpl) func);
      Assert.assertEquals(Boolean.valueOf(false), Boolean.valueOf(fun_out.isDynamic()));
      Assert.assertEquals("prova53", fun_out.getName());
      Assert.assertEquals(null, fun_out.getDomain());
      Assert.assertEquals("String", fun_out.getCodomain().getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assert.assertEquals(OutFunctionImpl.class, func.getClass());
      fun_out = ((OutFunctionImpl) func);
      Assert.assertEquals(Boolean.valueOf(false), Boolean.valueOf(fun_out.isDynamic()));
      Assert.assertEquals("prova54", fun_out.getName());
      Assert.assertEquals("Integer", fun_out.getDomain().getName());
      Assert.assertEquals("String", fun_out.getCodomain().getName());
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
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
      Assert.assertEquals(1, result.getHeaderSection().getSignature().getDomain().size());
      Assert.assertEquals(2, result.getHeaderSection().getSignature().getFunction().size());
      Function func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assert.assertEquals(DerivedFunctionImpl.class, func.getClass());
      Assert.assertEquals("prova1", func.getName());
      Assert.assertEquals(null, func.getDomain());
      Assert.assertEquals("String", func.getCodomain().getName());
      i++;
      func = result.getHeaderSection().getSignature().getFunction().get(i);
      Assert.assertEquals(DerivedFunctionImpl.class, func.getClass());
      Assert.assertEquals("prova2", func.getName());
      Assert.assertEquals("Prova1", func.getDomain().getName());
      Assert.assertEquals("String", func.getCodomain().getName());
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
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
      Assert.assertEquals(3, result.getHeaderSection().getImportClause().size());
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
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
      Assert.assertEquals(3, result.getHeaderSection().getImportClause().size());
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
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
      Assert.assertEquals(3, result.getHeaderSection().getImportClause().size());
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
