package tgtlib.preferences;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ChoicePreferenceEnumTest {

	enum tempEnum {a,b,c;}

	static ChoicePreferenceEnum<tempEnum> cp;

	@BeforeAll
	static void testChoicePreferenceEnum() {
		PreferenceBundle pb = new PreferenceBundle("PROVA");
		cp = new ChoicePreferenceEnum<tempEnum>("provaEnum","pref di prova enum",tempEnum.a);
		pb.add(cp);
		assertNotNull(cp);
		assertEquals(tempEnum.a,cp.getValueAsEnum());
	}

	@Test void setValueT() {
		cp.setValue(tempEnum.b);
		assertEquals(tempEnum.b, cp.getValueAsEnum());
		assertEquals(tempEnum.b.name(), cp.getValue());
	}


}
