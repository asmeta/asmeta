import static org.junit.Assert.*;
import org.junit.Test;

public class Rubrica_Test_1 {

	 @Test
	 public void Rubrica_Test(){

		 Rubrica rub = new Rubrica();
		 assertNotNull(rub);

		 //Set
		 rub.selectedService.Value = rub.selectedService.Value.DEL;
		 //Step
		 rub.UpdateASM();
		 //Check
