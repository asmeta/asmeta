package org.asmeta.atgt.generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/** formats how to save the tests */
public enum FormatsEnum {
	
	XML("XML"), 
	AVALLA("Avalla");
	
	FormatsEnum(String name) {
		this.name=name;
	}
	
	public String name;
	
	
	// ******** UTILITIES *************
	public static List<String> toListOfString(FormatsEnum... criteria) {
		List<String> res = new ArrayList<>();
		if (criteria==null || criteria.length==0) return res;
		for (FormatsEnum c : criteria) res.add(c.name());
		return res;
	}
	public static List<String> toListOfString(Collection<FormatsEnum> criteria) {
		return toListOfString(criteria.toArray(new FormatsEnum[0]));
	}
	
	public static List<FormatsEnum> toListOfFormatEnum(String... criteria) {
		List<FormatsEnum> res = new ArrayList<>();
		if (criteria==null || criteria.length==0) return res;
		for (String c : criteria) res.add(FormatsEnum.valueOf(c));
		return res;
	}
	public static List<FormatsEnum> toListOfFormatsEnum(Collection<String> criteria) {
		return toListOfFormatEnum(criteria.toArray(new String[0]));
	}
}
