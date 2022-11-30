import static org.junit.Assert.*;
import org.junit.Test;

public class ascensore_Test_1 {
	@Test
	public void ascensore_Test() {
		ascensore ascensore = new ascensore();
		assertNotNull(ascensore);
		//Check
		assertEquals(ascensore.statoPiano.oldValue.value, Integer.valueOf(1));
		//Set
		ascensore.signalPorta.Value = true;
		//Step
		ascensore.UpdateASM();
		//Check
		assertEquals(ascensore.statoPorta.oldValue, ascensore.statoPorta.oldValue.APERTA);
		//Check
		assertEquals(ascensore.statoPiano.oldValue.value, Integer.valueOf(1));
		//Set
		ascensore.signalPorta.Value = true;
		//Step
		ascensore.UpdateASM();
		//Check
		assertEquals(ascensore.statoPiano.oldValue.value, Integer.valueOf(1));
		//Check
		assertEquals(ascensore.statoPorta.oldValue, ascensore.statoPorta.oldValue.APERTA);
		//Set
		ascensore.signalPorta.Value = false;
		//Step
		ascensore.UpdateASM();
		//Check
		assertEquals(ascensore.statoPiano.oldValue.value, Integer.valueOf(2));
		//Check
		assertEquals(ascensore.statoPorta.oldValue, ascensore.statoPorta.oldValue.CHIUSA);
		//Step
		ascensore.UpdateASM();
	}
}