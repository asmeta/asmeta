package org.asmeta.modeladvisor.metaproperties;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.asmeta.modeladvisor.texpr.Expression;

public abstract class Checker {
	Set<Expression> smvProp;

	Checker() {
		smvProp = new HashSet<Expression>();
	}

	abstract Set<Expression> createNuSmvProperties();
	public abstract void evaluation(Map<String, Boolean> results);
	public abstract void printResults();
}