import static org.junit.Assert.*;
import org.junit.Test;

public class LGS_GM_Test_1 {
	@Test
	public void LGS_GM_Test() {
		LGS_GM lgs = new LGS_GM();
		assertNotNull(lgs);
		//Check
		assertEquals(lgs.gears.oldValue, lgs.gears.oldValue.EXTENDED);
		//Check
		assertEquals(lgs.doors.oldValue, lgs.doors.oldValue.CLOSED);
		//Set
		lgs.handle.Value = lgs.handle.Value.DOWN;
		//Step
		lgs.UpdateASM();
		//Set
		lgs.handle.Value = lgs.handle.Value.UP;
		//Step
		lgs.UpdateASM();
		//Check
		assertEquals(lgs.doors.oldValue, lgs.doors.oldValue.OPENING);
		//Set
		lgs.handle.Value = lgs.handle.Value.UP;
		//Step
		lgs.UpdateASM();
		//Check
		assertEquals(lgs.doors.oldValue, lgs.doors.oldValue.OPEN);
		//Set
		lgs.handle.Value = lgs.handle.Value.UP;
		//Step
		lgs.UpdateASM();
		//Check
		assertEquals(lgs.gears.oldValue, lgs.gears.oldValue.RETRACTING);
		//Set
		lgs.handle.Value = lgs.handle.Value.UP;
		//Step
		lgs.UpdateASM();
	}
}