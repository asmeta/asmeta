package atgt.generator.testsuite.ordering;

import static atgt.preferences.ATGToolPreferences.CollectTPS;
import static atgt.preferences.ATGToolPreferences.TP_ORDERING;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import atgt.preferences.ATGToolPreferences.OrderKind;
import atgt.specification.ASMSpecification;
import atgt.specification.location.Variable;
import tgtlib.definitions.expression.IdExpressionCreator;
import tgtlib.definitions.expression.type.EnumConst;
import tgtlib.definitions.expression.type.EnumConstCreator;
import tgtlib.definitions.expression.type.EnumType;

class PreferNoveltyCollectTest {

	@BeforeAll
	static void activatelog() {
		Logger.getLogger(PreferNoveltyCollectTest.class).setLevel(Level.DEBUG);
	}

	@Test void asTAPExample() {
		EnumConstCreator icc = new EnumConstCreator();
		// build a spec
		ASMSpecification spec = new ASMSpecification();
		//
		EnumType type = new EnumType("THREE");
		type.addElement("a");
		type.addElement("b");
		type.addElement("c");
		EnumConst a = type.value(0);
		EnumConst b = type.value(1);
		EnumConst c = type.value(2);
		assertNotNull(a);
		assertNotNull(b);
		assertNotNull(c);
		// add type
		spec.addType(type);
		Variable v1 = new Variable(icc.createIdExpression("A", type), type, null);
		Variable v2 = new Variable(icc.createIdExpression("B", type), type, null);
		Variable v3 = new Variable(icc.createIdExpression("C", type), type, null);
		Variable v4 = new Variable(icc.createIdExpression("D", type), type, null);
		// add the variables
		spec.addVariable(v1);
		spec.addVariable(v2);
		spec.addVariable(v3);
		spec.addVariable(v4);
		assertTrue(spec.allTypes().contains(type));
		//set the rpef
		CollectTPS.setChecked(true);
		TP_ORDERING.setValue(OrderKind.NOVELTY);
		// run the model chcker
		// combinatorial is diabled for now
		// atgt.combinatorial.Util.findBestTestSuite(spec, 1, true, 2);
	}
}