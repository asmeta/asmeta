import static org.junit.Assert.*;
import org.junit.Test;

public class Rubrica_Test_1 {
	@Test
	public void Rubrica_Test() {
		Rubrica rubrica = new Rubrica();
		assertNotNull(rubrica);
		//Set
		rubrica.selectedService.Value = rubrica.selectedService.Value.INS;
		//Step
		rubrica.UpdateASM();
		//Check
		assertEquals(rubrica.rubricaState.oldValue, rubrica.rubricaState.oldValue.SCELTA);
		//Check
		assertEquals(rubrica.outmex.oldValue, "Contact inserted!");
		//Set
		rubrica.selectedService.Value = rubrica.selectedService.Value.DEL;
		//Step
		rubrica.UpdateASM();
		//Check
		assertEquals(rubrica.rubricaState.oldValue, rubrica.rubricaState.oldValue.SCELTA);
		//Check
		assertEquals(rubrica.outmex.oldValue, "Contact deleted!");
		//Set
		rubrica.selectedService.Value = rubrica.selectedService.Value.DEL;
		//Step
		rubrica.UpdateASM();
		//Check
		assertEquals(rubrica.rubricaState.oldValue, rubrica.rubricaState.oldValue.SCELTA);
		//Check
		assertEquals(rubrica.outmex.oldValue, "Contact deleted!");
		//Set
		rubrica.selectedService.Value = rubrica.selectedService.Value.INS;
		//Step
		rubrica.UpdateASM();
		//Check
		assertEquals(rubrica.rubricaState.oldValue, rubrica.rubricaState.oldValue.SCELTA);
		//Check
		assertEquals(rubrica.outmex.oldValue, "Contact inserted!");
		//Step
		rubrica.UpdateASM();
	}
}