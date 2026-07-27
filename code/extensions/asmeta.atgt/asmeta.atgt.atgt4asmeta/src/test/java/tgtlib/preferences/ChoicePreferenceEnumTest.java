package tgtlib.preferences;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;

public class ChoicePreferenceEnumTest {

	enum tempEnum {a,b,c;}

	static ChoicePreferenceEnum<tempEnum> cp;
	
	@BeforeClass
	public static void testChoicePreferenceEnum() {
		PreferenceBundle pb = new PreferenceBundle("PROVA");
		cp = new ChoicePreferenceEnum<tempEnum>("provaEnum","pref di prova enum",tempEnum.a);
		pb.add(cp);
		assertNotNull(cp);
		assertEquals(tempEnum.a,cp.getValueAsEnum());
	}
	
	@Test
	public void testSetValueT() {
		cp.setValue(tempEnum.b);
		assertEquals(tempEnum.b, cp.getValueAsEnum());
		assertEquals(tempEnum.b.name(), cp.getValue());
	}


}
