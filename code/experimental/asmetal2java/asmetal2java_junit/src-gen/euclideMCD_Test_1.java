import static org.junit.Assert.*;
import org.junit.Test;

public class euclideMCD_Test_1 {
	@Test
	public void euclideMCD_Test() {
		euclideMCD euclidemcd = new euclideMCD();
		assertNotNull(euclidemcd);
		//Exec
		euclidemcd.numA.newValue = 24;
		euclidemcd.fireUpdateSet();
		//Exec
		euclidemcd.numB.newValue = 6;
		euclidemcd.fireUpdateSet();
		//Step Until
		while (euclidemcd.numA.oldValue != euclidemcd.numB.newValue) {
			euclidemcd.UpdateASM();
		}
		//Check
		assertEquals(euclidemcd.numA.oldValue, Integer.valueOf(6));
	}
}