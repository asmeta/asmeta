package asmeta.definitions;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import asmeta.definitions.domains.AnyDomain;
import asmeta.definitions.domains.DomainsFactory;


class TestAnyDomain {


	@Test void anyWithoutName() {
		assertThrows(RuntimeException.class, () ->
			DomainsFactory.eINSTANCE.createAnyDomain());
	}


	@Test void anyWithSameName(){
		AnyDomain ad1 = DomainsFactory.eINSTANCE.createAnyDomain("A");
		AnyDomain ad2 = DomainsFactory.eINSTANCE.getAnyDomain("A");
		assertSame(ad1, ad2);
	} 

}
