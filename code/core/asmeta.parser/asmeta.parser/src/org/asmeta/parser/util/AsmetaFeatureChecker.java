package org.asmeta.parser.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.function.Predicate;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;

import asmeta.structure.Asm;

// check if an ASM (or more) contains a feature - given as predicate over EObject 
public class AsmetaFeatureChecker {

	private static final Logger LOGGER = Logger.getLogger(AsmetaFeatureChecker.class);

	// the feature to checl
	private Predicate<EObject> pred;

	public AsmetaFeatureChecker(Predicate<EObject> feature) {
		pred = feature;
	}

	// check this ASM
	public boolean checkFeature(Asm main) {
		return check(getContentExtended(main));
	}

	// recurse in the content
	private boolean check(Iterator<? extends EObject> e) {
		while (e.hasNext()) {
			EObject next = e.next();
			LOGGER.debug("checking " + next);
			if (pred.test(next))
				return true;
			if (check(getContentExtended(next))) {
				return true;
			}
		}
		return false;
	}

	// eAllContents returns only the actual content - sometimes we need the
	// association
	private Iterator<? extends EObject> getContentExtended(EObject obj) {
		// ruls contained in a macro declaration are not contained
		if (obj instanceof asmeta.transitionrules.basictransitionrules.MacroDeclaration macro) {
			return Collections.singletonList(macro.getRuleBody()).iterator();
		}
		if (obj instanceof asmeta.transitionrules.basictransitionrules.BlockRule block) {
			return block.getRules().iterator();
		}
		return obj.eAllContents();
	}

}
