import static org.junit.Assert.*;
import org.junit.Test;

public class SIS_Test_1 {

	 @Test
	 public void SIS_Test(){

		 SIS sis = new SIS();
		 assertNotNull(sis);

		 //Check
		 assertEquals(sis.waterpressure.oldValue.value,3);
		 //Check
		 assertEquals(sis.pressure.oldValue,sis.pressure.newValue.TOOLOW);
		 //Check
		 assertEquals(sis.overridden.oldValue,false);
		 //Set
		 sis.reset.Value = sis.reset.Value.OFF;
		 //Set
		 sis.block.Value = sis.block.Value.OFF;
		 //Set
		 //sis.delta.
		 //creare un delta non è un intero -5
		 //sis.delta.set(-5);
		 //SIS_sig.Delta delta = new SIS_sig.Delta();
		 //delta.value = -5;
		 sis.delta.Value = new SIS_sig.Delta();
		 sis.delta.Value.value = -5;
		 //Step
		 sis.UpdateASM();


	 }
}