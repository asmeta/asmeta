import static org.junit.Assert.*;
import org.junit.Test;

public class ascensore_Test_0 {
	@Test
	public void ascensore_Test() {
		ascensore ascensore = new ascensore();
		assertNotNull(ascensore);
		//Check
		assertEquals(ascensore.statoPiano.oldValue.value, Integer.valueOf(1));
		//Set
		ascensore.signalPorta.Value = false;
		//Step
		ascensore.UpdateASM();
		//Check
		assertEquals(ascensore.statoPorta.oldValue, ascensore.statoPorta.oldValue.CHIUSA);
		//Check
		assertEquals(ascensore.statoPiano.oldValue.value, Integer.valueOf(2));
		//Set
		ascensore.signalPorta.Value = false;
		//Step
		ascensore.UpdateASM();
		//Check
		assertEquals(ascensore.statoPorta.oldValue, ascensore.statoPorta.oldValue.CHIUSA);
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
		//Step
		ascensore.UpdateASM();
	}
}