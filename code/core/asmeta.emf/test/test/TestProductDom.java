package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.DomainsFactory;
import asmeta.definitions.domains.ProductDomain;

public class TestProductDom {
	/**
	 * 
	 *#
	 * 
	 * Unique (default is true)
	 * 
	 * Unique only applies to multiplicity-many attributes, indicating that such
	 * an attribute may not contain multiple equal objects. References are
	 * always treated as unique.
	 * 
	 * 
	 * 
	 * unique --> EObjectResolvingEList not unique
	 * 
	 * @param args
	 */
	@Test
	public void testProduct() {
		AbstractTd atd = DomainsFactory.eINSTANCE.createAbstractTd();
		atd.setName("A");
		ProductDomain pdm = DomainsFactory.eINSTANCE.createProductDomain();
		System.out.println(pdm.getDomains().add(atd));
		System.out.println(pdm.getDomains().add(atd));
		assertEquals(2, pdm.getDomains().size());
	}

}
