import static org.junit.Assert.*;
import org.junit.Test;
public class ascensore_Test_1{
@Test
public void ascensore_Test(){
ascensore asc = new ascensore();
assertNotNull(asc);
//Check
assertTrue(asc.statoPiano.get().value == 1);
//Check
assertEquals(asc.statoPorta.oldValue, asc.statoPorta.newValue);
//Set
asc.signalPorta.Value = false;
//Step
asc.UpdateASM();
//Check
assertTrue(asc.statoPiano.get().value == 2);
//Check
assertEquals(asc.statoPorta.oldValue, asc.statoPorta.newValue);
//Exec
asc.statoPorta.set(asc.statoPorta.newValue.APERTA);
asc.fireUpdateSet();//Set
asc.signalPorta.Value = false;
//Check
assertEquals(asc.statoPorta.oldValue, asc.statoPorta.newValue);
//Step
asc.UpdateASM();
//Check
assertTrue(asc.statoPiano.get().value == 1);
//Check
assertEquals(asc.statoPorta.oldValue, asc.statoPorta.newValue);
}
}