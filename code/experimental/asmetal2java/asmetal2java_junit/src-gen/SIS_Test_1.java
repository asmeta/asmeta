import static org.junit.Assert.*;
import org.junit.Test;

public class SIS_Test_1 {
	@Test
	public void SIS_Test() {
		SIS sis = new SIS();
		assertNotNull(sis);
		//Check
		assertEquals(sis.waterpressure.oldValue.value, Integer.valueOf(3));
		//Check
		assertEquals(sis.pressure.oldValue, sis.pressure.oldValue.TOOLOW);
		//Check
		assertFalse(sis.overridden.oldValue);
		//Set
		sis.reset.Value = sis.reset.Value.OFF;
		//Set
		sis.block.Value = sis.block.Value.OFF;
		//Set
		sis.delta.Value = new SIS_sig.Delta();
		sis.delta.Value.value = -5;
		//Step
		sis.UpdateASM();
		//Check
		assertEquals(sis.waterpressure.oldValue.value, Integer.valueOf(0));
		//Check
		assertEquals(sis.pressure.oldValue, sis.pressure.oldValue.TOOLOW);
		//Check
		assertFalse(sis.overridden.oldValue);
		//Check
		assertEquals(sis.safetyInjection.oldValue, sis.safetyInjection.oldValue.ON);
		//Set
		sis.reset.Value = sis.reset.Value.ON;
		//Set
		sis.block.Value = sis.block.Value.ON;
		//Set
		sis.delta.Value = new SIS_sig.Delta();
		sis.delta.Value.value = -1;
		//Step
		sis.UpdateASM();
		//Check
		assertEquals(sis.waterpressure.oldValue.value, Integer.valueOf(0));
		//Check
		assertEquals(sis.pressure.oldValue, sis.pressure.oldValue.TOOLOW);
		//Check
		assertFalse(sis.overridden.oldValue);
		//Check
		assertEquals(sis.safetyInjection.oldValue, sis.safetyInjection.oldValue.ON);
		//Set
		sis.reset.Value = sis.reset.Value.OFF;
		//Set
		sis.block.Value = sis.block.Value.ON;
		//Set
		sis.delta.Value = new SIS_sig.Delta();
		sis.delta.Value.value = -5;
		//Step
		sis.UpdateASM();
		//Check
		assertEquals(sis.waterpressure.oldValue.value, Integer.valueOf(0));
		//Check
		assertEquals(sis.pressure.oldValue, sis.pressure.oldValue.TOOLOW);
		//Check
		assertTrue(sis.overridden.oldValue);
		//Check
		assertEquals(sis.safetyInjection.oldValue, sis.safetyInjection.oldValue.ON);
		//Set
		sis.reset.Value = sis.reset.Value.OFF;
		//Set
		sis.block.Value = sis.block.Value.ON;
		//Set
		sis.delta.Value = new SIS_sig.Delta();
		sis.delta.Value.value = 2;
		//Step
		sis.UpdateASM();
		//Check
		assertEquals(sis.waterpressure.oldValue.value, Integer.valueOf(2));
		//Check
		assertEquals(sis.pressure.oldValue, sis.pressure.oldValue.TOOLOW);
		//Check
		assertTrue(sis.overridden.oldValue);
		//Check
		assertEquals(sis.safetyInjection.oldValue, sis.safetyInjection.oldValue.OFF);
		//Set
		sis.reset.Value = sis.reset.Value.OFF;
		//Set
		sis.block.Value = sis.block.Value.ON;
		//Set
		sis.delta.Value = new SIS_sig.Delta();
		sis.delta.Value.value = -3;
		//Step
		sis.UpdateASM();
		//Check
		assertEquals(sis.waterpressure.oldValue.value, Integer.valueOf(0));
		//Check
		assertEquals(sis.pressure.oldValue, sis.pressure.oldValue.TOOLOW);
		//Check
		assertTrue(sis.overridden.oldValue);
		//Check
		assertEquals(sis.safetyInjection.oldValue, sis.safetyInjection.oldValue.OFF);
		//Set
		sis.reset.Value = sis.reset.Value.OFF;
		//Set
		sis.block.Value = sis.block.Value.ON;
		//Set
		sis.delta.Value = new SIS_sig.Delta();
		sis.delta.Value.value = -2;
		//Step
		sis.UpdateASM();
		//Check
		assertEquals(sis.waterpressure.oldValue.value, Integer.valueOf(0));
		//Check
		assertEquals(sis.pressure.oldValue, sis.pressure.oldValue.TOOLOW);
		//Check
		assertTrue(sis.overridden.oldValue);
		//Check
		assertEquals(sis.safetyInjection.oldValue, sis.safetyInjection.oldValue.OFF);
		//Set
		sis.reset.Value = sis.reset.Value.OFF;
		//Set
		sis.block.Value = sis.block.Value.ON;
		//Set
		sis.delta.Value = new SIS_sig.Delta();
		sis.delta.Value.value = 3;
		//Step
		sis.UpdateASM();
		//Check
		assertEquals(sis.waterpressure.oldValue.value, Integer.valueOf(3));
		//Check
		assertEquals(sis.pressure.oldValue, sis.pressure.oldValue.TOOLOW);
		//Check
		assertTrue(sis.overridden.oldValue);
		//Check
		assertEquals(sis.safetyInjection.oldValue, sis.safetyInjection.oldValue.OFF);
		//Set
		sis.reset.Value = sis.reset.Value.ON;
		//Set
		sis.block.Value = sis.block.Value.OFF;
		//Set
		sis.delta.Value = new SIS_sig.Delta();
		sis.delta.Value.value = 5;
		//Step
		sis.UpdateASM();
		//Check
		assertEquals(sis.waterpressure.oldValue.value, Integer.valueOf(8));
		//Check
		assertEquals(sis.pressure.oldValue, sis.pressure.oldValue.TOOLOW);
		//Check
		assertFalse(sis.overridden.oldValue);
		//Check
		assertEquals(sis.safetyInjection.oldValue, sis.safetyInjection.oldValue.OFF);
		//Set
		sis.reset.Value = sis.reset.Value.ON;
		//Set
		sis.block.Value = sis.block.Value.ON;
		//Set
		sis.delta.Value = new SIS_sig.Delta();
		sis.delta.Value.value = 2;
		//Step
		sis.UpdateASM();
		//Check
		assertEquals(sis.waterpressure.oldValue.value, Integer.valueOf(10));
		//Check
		assertEquals(sis.pressure.oldValue, sis.pressure.oldValue.TOOLOW);
		//Check
		assertFalse(sis.overridden.oldValue);
		//Check
		assertEquals(sis.safetyInjection.oldValue, sis.safetyInjection.oldValue.ON);
		//Set
		sis.reset.Value = sis.reset.Value.OFF;
		//Set
		sis.block.Value = sis.block.Value.ON;
		//Set
		sis.delta.Value = new SIS_sig.Delta();
		sis.delta.Value.value = 2;
		//Step
		sis.UpdateASM();
		//Check
		assertEquals(sis.waterpressure.oldValue.value, Integer.valueOf(12));
		//Check
		assertEquals(sis.pressure.oldValue, sis.pressure.oldValue.NORMAL);
		//Check
		assertTrue(sis.overridden.oldValue);
		//Check
		assertEquals(sis.safetyInjection.oldValue, sis.safetyInjection.oldValue.ON);
		//Set
		sis.reset.Value = sis.reset.Value.OFF;
		//Set
		sis.block.Value = sis.block.Value.ON;
		//Set
		sis.delta.Value = new SIS_sig.Delta();
		sis.delta.Value.value = 4;
		//Step
		sis.UpdateASM();
		//Check
		assertEquals(sis.waterpressure.oldValue.value, Integer.valueOf(16));
		//Check
		assertEquals(sis.pressure.oldValue, sis.pressure.oldValue.HIGH);
		//Check
		assertFalse(sis.overridden.oldValue);
		//Check
		assertEquals(sis.safetyInjection.oldValue, sis.safetyInjection.oldValue.ON);
		//Set
		sis.reset.Value = sis.reset.Value.ON;
		//Set
		sis.block.Value = sis.block.Value.ON;
		//Set
		sis.delta.Value = new SIS_sig.Delta();
		sis.delta.Value.value = -3;
		//Step
		sis.UpdateASM();
		//Check
		assertEquals(sis.waterpressure.oldValue.value, Integer.valueOf(13));
		//Check
		assertEquals(sis.pressure.oldValue, sis.pressure.oldValue.HIGH);
		//Check
		assertFalse(sis.overridden.oldValue);
		//Check
		assertEquals(sis.safetyInjection.oldValue, sis.safetyInjection.oldValue.ON);
		//Step
		sis.UpdateASM();
	}
}