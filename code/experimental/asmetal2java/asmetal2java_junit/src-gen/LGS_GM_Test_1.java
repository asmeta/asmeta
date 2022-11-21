import static org.junit.Assert.*;
import org.junit.Test;

public class LGS_GM_Test_1 {
	@Test
	public void LGS_GM_Test() {
		LGS_GM lgs_gm = new LGS_GM();
		assertNotNull(lgs_gm);
		//Check
		assertEquals(lgs_gm.gears.oldValue, lgs_gm.gears.oldValue.EXTENDED);
		//Check
		assertEquals(lgs_gm.doors.oldValue, lgs_gm.doors.oldValue.CLOSED);
		//Set
		lgs_gm.handle.Value = lgs_gm.handle.Value.DOWN;
		//Step
		lgs_gm.UpdateASM();
		//Set
		lgs_gm.handle.Value = lgs_gm.handle.Value.UP;
		//Step
		lgs_gm.UpdateASM();
		//Check
		assertEquals(lgs_gm.doors.oldValue, lgs_gm.doors.oldValue.OPENING);
		//Set
		lgs_gm.handle.Value = lgs_gm.handle.Value.UP;
		//Step
		lgs_gm.UpdateASM();
		//Check
		assertEquals(lgs_gm.doors.oldValue, lgs_gm.doors.oldValue.OPEN);
		//Set
		lgs_gm.handle.Value = lgs_gm.handle.Value.UP;
		//Step
		lgs_gm.UpdateASM();
		//Check
		assertEquals(lgs_gm.gears.oldValue, lgs_gm.gears.oldValue.RETRACTING);
		//Set
		lgs_gm.handle.Value = lgs_gm.handle.Value.UP;
		//Step
		lgs_gm.UpdateASM();
	}
}