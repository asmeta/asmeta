import static org.junit.Assert.*;
import org.junit.Test;

public class AdvancedClock_Test_0 {
	@Test
	public void AdvancedClock_Test() {
		AdvancedClock advancedclock = new AdvancedClock();
		assertNotNull(advancedclock);
		//Check
		assertEquals(advancedclock.seconds.oldValue.value, Integer.valueOf(0));
		//Step
		advancedclock.UpdateASM();
		//Check
		assertEquals(advancedclock.seconds.oldValue.value, Integer.valueOf(1));
		//Step
		advancedclock.UpdateASM();
		//Check
		assertEquals(advancedclock.seconds.oldValue.value, Integer.valueOf(2));
		//Step
		advancedclock.UpdateASM();
		//Check
		assertEquals(advancedclock.seconds.oldValue.value, Integer.valueOf(3));
		//Step
		advancedclock.UpdateASM();
	}
}