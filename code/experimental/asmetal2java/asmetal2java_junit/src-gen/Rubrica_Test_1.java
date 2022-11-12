import static org.junit.Assert.*;
import org.junit.Test;
public class Rubrica_Test_1{
@Test
public void Rubrica_Test(){
Rubrica rub = new Rubrica();
assertNotNull(rub);
//Set
rub.selectedService.Value = rub.selectedService.Value.DEL;
//Step
rub.UpdateASM();
//Check
assertEquals(rub.rubricaState.oldValue, rub.rubricaState.newValue);
//Check
assertEquals(rub.outmex.oldValue, rub.outmex.newValue);
//Set
rub.selectedService.Value = rub.selectedService.Value.DEL;
//Step
rub.UpdateASM();
//Check
assertEquals(rub.rubricaState.oldValue, rub.rubricaState.newValue);
//Check
assertEquals(rub.outmex.oldValue, rub.outmex.newValue);
//Set
rub.selectedService.Value = rub.selectedService.Value.INS;
//Step
rub.UpdateASM();
//Check
assertEquals(rub.rubricaState.oldValue, rub.rubricaState.newValue);
//Check
assertEquals(rub.outmex.oldValue, rub.outmex.newValue);
//Set
rub.selectedService.Value = rub.selectedService.Value.DEL;
//Step
rub.UpdateASM();
//Check
assertEquals(rub.rubricaState.oldValue, rub.rubricaState.newValue);
//Check
assertEquals(rub.outmex.oldValue, rub.outmex.newValue);
//Set
rub.selectedService.Value = rub.selectedService.Value.INS;
//Step
rub.UpdateASM();
//Check
assertEquals(rub.rubricaState.oldValue, rub.rubricaState.newValue);
//Check
assertEquals(rub.outmex.oldValue, rub.outmex.newValue);
//Step
rub.UpdateASM();
}
}