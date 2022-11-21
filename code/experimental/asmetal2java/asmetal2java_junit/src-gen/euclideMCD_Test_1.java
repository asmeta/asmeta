import static org.junit.Assert.*;
import org.junit.Test;

public class euclideMCD_Test_1 {
	@Test
	public void euclideMCD_Test() {
		euclideMCD euc = new euclideMCD();
		assertNotNull(euc);
		//Exec
		euc.numA.newValue = 24;
		euc.fireUpdateSet();
		//Exec
		euc.numB.newValue = 6;
		euc.fireUpdateSet();
		//Step
		while (euc.numA.oldValue != euc.numB.oldValue) {
			euc.UpdateASM();
		}
		//Check
		assertEquals(euc.numA.oldValue, Integer.valueOf(6));
	}
}