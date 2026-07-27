package atgt.generator.testsuite.ordering;

import static atgt.preferences.ATGToolPreferences.CollectTPS;
import static atgt.preferences.ATGToolPreferences.TP_ORDERING;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import atgt.preferences.ATGToolPreferences.OrderKind;
import atgt.specification.ASMSpecification;
import atgt.specification.location.Variable;
import tgtlib.definitions.expression.IdExpressionCreator;
import tgtlib.definitions.expression.type.EnumConst;
import tgtlib.definitions.expression.type.EnumType;

public class PreferNoveltyCollectTest {

	@BeforeClass
	static public void activatelog() {
		Logger.getLogger(PreferNoveltyCollectTest.class).setLevel(Level.DEBUG);
	}

	@Test
	public void testAsTAPExample() {
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
		Variable v1 = new Variable(IdExpressionCreator.createNewIdExpression("A"), type, null);
		Variable v2 = new Variable(IdExpressionCreator.createNewIdExpression("B"), type, null);
		Variable v3 = new Variable(IdExpressionCreator.createNewIdExpression("C"), type, null);
		Variable v4 = new Variable(IdExpressionCreator.createNewIdExpression("D"), type, null);
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
		atgt.combinatorial.Util.findBestTestSuite(spec, 1, true, 2);
	}
}