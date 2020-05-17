package test;

import static org.junit.Assert.assertSame;

import org.junit.Test;

import asmeta.definitions.domains.AnyDomain;
import asmeta.definitions.domains.DomainsFactory;


public class TestAnyDomain {
	
	
	@Test(expected=RuntimeException.class)
	public void testAnyWithoutName(){
		DomainsFactory.eINSTANCE.createAnyDomain();
	} 

	
	@Test
	public void testAnyWithSameName(){
		AnyDomain ad1 = DomainsFactory.eINSTANCE.createAnyDomain("A");
		AnyDomain ad2 = DomainsFactory.eINSTANCE.getAnyDomain("A");
		assertSame(ad1, ad2);
	} 

}
