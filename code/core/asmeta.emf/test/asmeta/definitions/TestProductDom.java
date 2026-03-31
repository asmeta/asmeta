package asmeta.definitions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.DomainsFactory;
import asmeta.definitions.domains.ProductDomain;

class TestProductDom {
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
	@Test void product() {
		AbstractTd atd = DomainsFactory.eINSTANCE.createAbstractTd();
		atd.setName("A");
		ProductDomain pdm = DomainsFactory.eINSTANCE.createProductDomain();
		assertTrue(pdm.getDomains().add(atd));
		assertTrue(pdm.getDomains().add(atd));
		assertEquals(2, pdm.getDomains().size());
	}

}
