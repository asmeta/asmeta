package org.asmeta.xt.validation.utility;

import java.util.ArrayList;

import org.asmeta.xt.asmetal.AsmetalFactory;
import org.asmeta.xt.asmetal.Domain;
import org.asmeta.xt.asmetal.StandardDomain;

public class BasicDomains {

	public static ArrayList<Domain> basic_domain_list = new ArrayList<Domain>();
	
	static {
		String names[] = {"Integer", "Real", "Complex", "Undef", "Boolean", "Char", "Natural", "String"};
		
		for (String name : names) {
			StandardDomain sd = AsmetalFactory.eINSTANCE.createStandardDomain();
			sd.setName(name);
			basic_domain_list.add(sd);
		}
		
	}
	
}
