package org.asmeta.refinement;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CloudApplicationRefinementProof extends ProofTest {

	@Before
	public void beforeTest() {
		basePath = "./refinement/cloudApplication/";
	}

	@Test
	public void testFirstRefinement() throws Exception {
		proveRefinement("ClientDisplayOutputNoMessageArrivedOneDevice.asm", "ClientDisplayOutputOneDevice.asm", true, true);
	}

	//synchronous multi-agent
	@Test
	public void testFirstRefinementMultipleDevices() throws Exception {
		proveRefinement("ClientDisplayOutputNoMessageArrived.asm", "ClientDisplayOutput.asm", true, true);
	}

	@Test
	public void testSecondRefinement() throws Exception {
		proveRefinement("ClientDisplayOutputOneDevice.asm", "ClientDisplayOutputWithCookieOneDevice.asm", true, true);
	}

	@Test
	public void testSecondRefinementMultipleDevices() throws Exception {
		proveRefinement("ClientDisplayOutput.asm", "ClientDisplayOutputWithCookie.asm", true, true);
	}

	@Test
	public void testThirdRefinement() throws Exception {
		proveRefinement("ClientDisplayOutputWithCookieOneDevice.asm", "ClientDisplayOutputWithCookieTagsOneDevice.asm", true, true);
	}

	//synchronous multi-agent
	//@Test
	public void testThirdRefinementMultipleDevices() throws Exception {
		proveRefinement("ClientDisplayOutputWithCookie.asm", "ClientDisplayOutputWithCookieTags.asm", true, true);
	}
}