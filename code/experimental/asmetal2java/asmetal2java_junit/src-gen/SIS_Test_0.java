import static org.junit.Assert.*;
import org.junit.Test;

public class SIS_Test_0 {
	@Test
	public void SIS_Test() {
		SIS sis = new SIS();
		assertNotNull(sis);
		//Check
		assertEquals(sis.pressure.oldValue, sis.pressure.oldValue.TOOLOW);
		//Check
		assertEquals(sis.waterpressure.oldValue.value, Integer.valueOf(3));
		//Check
		assertFalse(sis.overridden.oldValue);
		//Set
		sis.delta.Value = new SIS_sig.Delta();
		sis.delta.Value.value = -4;
		//Set
		sis.reset.Value = sis.reset.Value.OFF;
		//Set
		sis.block.Value = sis.block.Value.ON;
		//Step
		sis.UpdateASM();
		//Check
		assertEquals(sis.safetyInjection.oldValue, sis.safetyInjection.oldValue.ON);
		//Check
		assertEquals(sis.waterpressure.oldValue.value, Integer.valueOf(0));
		//Check
		assertEquals(sis.pressure.oldValue, sis.pressure.oldValue.TOOLOW);
		//Check
		assertTrue(sis.overridden.oldValue);
		//Set
		sis.reset.Value = sis.reset.Value.ON;
		//Set
		sis.block.Value = sis.block.Value.ON;
		//Set
		sis.delta.Value = new SIS_sig.Delta();
		sis.delta.Value.value = 4;
		//Step
		sis.UpdateASM();
		//Check
		assertEquals(sis.pressure.oldValue, sis.pressure.oldValue.TOOLOW);
		//Check
		assertFalse(sis.overridden.oldValue);
		//Check
		assertEquals(sis.waterpressure.oldValue.value, Integer.valueOf(4));
		//Check
		assertEquals(sis.safetyInjection.oldValue, sis.safetyInjection.oldValue.OFF);
		//Set
		sis.block.Value = sis.block.Value.ON;
		//Set
		sis.reset.Value = sis.reset.Value.ON;
		//Set
		sis.delta.Value = new SIS_sig.Delta();
		sis.delta.Value.value = 4;
		//Step
		sis.UpdateASM();
		//Check
		assertEquals(sis.pressure.oldValue, sis.pressure.oldValue.TOOLOW);
		//Check
		assertEquals(sis.waterpressure.oldValue.value, Integer.valueOf(8));
		//Check
		assertEquals(sis.safetyInjection.oldValue, sis.safetyInjection.oldValue.ON);
		//Check
		assertFalse(sis.overridden.oldValue);
		//Step
		sis.UpdateASM();
	}
}