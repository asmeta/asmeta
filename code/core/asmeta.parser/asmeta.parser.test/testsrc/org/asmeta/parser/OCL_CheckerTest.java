package org.asmeta.parser;

import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.AnyDomain;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.DomainsFactory;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.NaturalDomain;

public class OCL_CheckerTest {

	static DomainsFactory df;
	static AbstractTd atd;
	static ConcreteDomain cd;
	
	@BeforeClass
	static public void setupDomains(){
		df = DomainsFactory.eINSTANCE;
		atd = df.createAbstractTd();
		atd.setName("A");
		cd = df.createConcreteDomain();
		cd.setTypeDomain(atd);
		cd.setName("C");
		
	}

	@Test
	public void testCompatibleSingleDomain() {
		assertTrue(OCL_Checker.compatible(atd, cd));
		assertTrue(OCL_Checker.compatible(cd, atd));
	}

	@Test
	public void testCompatibleDomainList() {
		List<AbstractTd> l1 = Collections.singletonList(atd);
		List<ConcreteDomain> l2 = Collections.singletonList(cd);
		assertTrue(OCL_Checker.compatible(l1,l2));
		assertTrue(OCL_Checker.compatible(l2,l1));
	}

	@Test
	public void testCompatibleNaturalConcrete() {
		NaturalDomain nd = df.createNaturalDomain();
		nd.setName("Natural");
		ConcreteDomain cn = df.createConcreteDomain();
		cn.setName("ConcreteNatural");
		cn.setTypeDomain(nd);
		assertTrue(OCL_Checker.compatible(atd, cd));
		assertTrue(OCL_Checker.compatible(cd, atd));
		List<NaturalDomain> l1 = Collections.singletonList(nd);
		List<ConcreteDomain> l2 = Collections.singletonList(cn);
		assertTrue(OCL_Checker.compatible(l1,l2));
		assertTrue(OCL_Checker.compatible(l2,l1));
	}

	@Test
	public void testCompatibleAny() {
		AnyDomain any = df.createAnyDomain(OCL_Checker.SUPER_ANYDOMAIN);
		EnumTd enumDom = df.createEnumTd();
		enumDom.setName("Colors");		
		assertTrue(OCL_Checker.compatible(any, enumDom));
		// with a different name
		AnyDomain myany = df.createAnyDomain("MyAnyDomain");
		assertTrue(OCL_Checker.compatible(myany, enumDom));
		// if subset of any 
		ConcreteDomain anysubset = df.createConcreteDomain();
		anysubset.setTypeDomain(any);
		anysubset.setName("MyAnyDomainSubset");
		assertTrue(OCL_Checker.compatible(anysubset, enumDom));
		
	}

}
