import static org.junit.Assert.*;
import org.junit.Test;
public class SIS_Test_1{
@Test
public void SIS_Test(){
SIS sis = new SIS();
assertNotNull(sis);
//Check
assertTrue(sis.waterpressure.get().value == 3);
//Check
assertEquals(sis.pressure.oldValue, sis.pressure.newValue);
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
assertTrue(sis.waterpressure.get().value == 0);
//Check
assertEquals(sis.pressure.oldValue, sis.pressure.newValue);
//Check
assertFalse(sis.overridden.oldValue);
//Check
assertEquals(sis.safetyInjection.oldValue, sis.safetyInjection.newValue);
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
assertTrue(sis.waterpressure.get().value == 0);
//Check
assertEquals(sis.pressure.oldValue, sis.pressure.newValue);
//Check
assertFalse(sis.overridden.oldValue);
//Check
assertEquals(sis.safetyInjection.oldValue, sis.safetyInjection.newValue);
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
assertTrue(sis.waterpressure.get().value == 0);
//Check
assertEquals(sis.pressure.oldValue, sis.pressure.newValue);
//Check
assertTrue(sis.overridden.oldValue);
//Check
assertEquals(sis.safetyInjection.oldValue, sis.safetyInjection.newValue);
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
assertTrue(sis.waterpressure.get().value == 2);
//Check
assertEquals(sis.pressure.oldValue, sis.pressure.newValue);
//Check
assertTrue(sis.overridden.oldValue);
//Check
assertEquals(sis.safetyInjection.oldValue, sis.safetyInjection.newValue);
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
assertTrue(sis.waterpressure.get().value == 0);
//Check
assertEquals(sis.pressure.oldValue, sis.pressure.newValue);
//Check
assertTrue(sis.overridden.oldValue);
//Check
assertEquals(sis.safetyInjection.oldValue, sis.safetyInjection.newValue);
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
assertTrue(sis.waterpressure.get().value == 0);
//Check
assertEquals(sis.pressure.oldValue, sis.pressure.newValue);
//Check
assertTrue(sis.overridden.oldValue);
//Check
assertEquals(sis.safetyInjection.oldValue, sis.safetyInjection.newValue);
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
assertTrue(sis.waterpressure.get().value == 3);
//Check
assertEquals(sis.pressure.oldValue, sis.pressure.newValue);
//Check
assertTrue(sis.overridden.oldValue);
//Check
assertEquals(sis.safetyInjection.oldValue, sis.safetyInjection.newValue);
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
assertTrue(sis.waterpressure.get().value == 8);
//Check
assertEquals(sis.pressure.oldValue, sis.pressure.newValue);
//Check
assertFalse(sis.overridden.oldValue);
//Check
assertEquals(sis.safetyInjection.oldValue, sis.safetyInjection.newValue);
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
assertTrue(sis.waterpressure.get().value == 10);
//Check
assertEquals(sis.pressure.oldValue, sis.pressure.newValue);
//Check
assertFalse(sis.overridden.oldValue);
//Check
assertEquals(sis.safetyInjection.oldValue, sis.safetyInjection.newValue);
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
assertTrue(sis.waterpressure.get().value == 12);
//Check
assertEquals(sis.pressure.oldValue, sis.pressure.newValue);
//Check
assertTrue(sis.overridden.oldValue);
//Check
assertEquals(sis.safetyInjection.oldValue, sis.safetyInjection.newValue);
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
assertTrue(sis.waterpressure.get().value == 16);
//Check
assertEquals(sis.pressure.oldValue, sis.pressure.newValue);
//Check
assertFalse(sis.overridden.oldValue);
//Check
assertEquals(sis.safetyInjection.oldValue, sis.safetyInjection.newValue);
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
assertTrue(sis.waterpressure.get().value == 13);
//Check
assertEquals(sis.pressure.oldValue, sis.pressure.newValue);
//Check
assertFalse(sis.overridden.oldValue);
//Check
assertEquals(sis.safetyInjection.oldValue, sis.safetyInjection.newValue);
//Step
sis.UpdateASM();
}
}