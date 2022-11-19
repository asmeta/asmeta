import static org.junit.Assert.*;
import org.junit.Test;

public class ascensore_Test_1 {
	@Test
	public void ascensore_Test() {
		ascensore asc = new ascensore();
		assertNotNull(asc);
		//Check
		assertEquals(asc.statoPiano.oldValue.value, Integer.valueOf(1));
		//Check
		assertEquals(asc.statoPorta.oldValue, asc.statoPorta.oldValue.CHIUSA);
		//Set
		asc.signalPorta.Value = false;
		//Step
		asc.UpdateASM();
		//Check
		assertEquals(asc.statoPiano.oldValue.value, Integer.valueOf(2));
		//Check
		assertEquals(asc.statoPorta.oldValue, asc.statoPorta.oldValue.CHIUSA);
		//Exec
		asc.statoPorta.newValue = asc.statoPorta.newValue.APERTA;
		asc.fireUpdateSet();
		//Set
		asc.signalPorta.Value = false;
		//Check
		assertEquals(asc.statoPorta.oldValue, asc.statoPorta.oldValue.APERTA);
		//Step
		asc.UpdateASM();
		//Check
		assertEquals(asc.statoPiano.oldValue.value, Integer.valueOf(1));
		//Check
		assertEquals(asc.statoPorta.oldValue, asc.statoPorta.oldValue.CHIUSA);
	}
}