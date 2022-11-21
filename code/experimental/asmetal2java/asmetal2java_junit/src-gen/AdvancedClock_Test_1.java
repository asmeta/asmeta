import static org.junit.Assert.*;
import org.junit.Test;

public class AdvancedClock_Test_1 {
	@Test
	public void AdvancedClock_Test() {
		AdvancedClock adv = new AdvancedClock();
		assertNotNull(adv);
		//Exec
		adv.hours.newValue.value = 2;
		adv.fireUpdateSet();
		//Exec
		adv.minutes.newValue.value = 1;
		adv.fireUpdateSet();
		//Exec
		adv.seconds.newValue.value = 59;
		adv.fireUpdateSet();
		//Step
		adv.UpdateASM();
		//Check
		assertEquals(adv.hours.oldValue.value, Integer.valueOf(2));
		//Check
		assertEquals(adv.minutes.oldValue.value, Integer.valueOf(2));
		//Check
		assertEquals(adv.seconds.oldValue.value, Integer.valueOf(0));
		//Exec
		adv.hours.newValue.value = 0;
		adv.fireUpdateSet();
		//Exec
		adv.minutes.newValue.value = 0;
		adv.fireUpdateSet();
		//Exec
		adv.seconds.newValue.value = 0;
		adv.fireUpdateSet();
		//Step Until
		while (adv.hours.oldValue.value != 3) {
			adv.UpdateASM();
		}
	}
}