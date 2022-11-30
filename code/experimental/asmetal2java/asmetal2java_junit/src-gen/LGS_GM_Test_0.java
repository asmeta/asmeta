import static org.junit.Assert.*;
import org.junit.Test;

public class LGS_GM_Test_0 {
	@Test
	public void LGS_GM_Test() {
		LGS_GM lgs_gm = new LGS_GM();
		assertNotNull(lgs_gm);
		//Check
		assertEquals(lgs_gm.doors.oldValue, lgs_gm.doors.oldValue.CLOSED);
		//Check
		assertEquals(lgs_gm.gears.oldValue, lgs_gm.gears.oldValue.EXTENDED);
		//Set
		lgs_gm.handle.Value = lgs_gm.handle.Value.DOWN;
		//Step
		lgs_gm.UpdateASM();
		//Check
		assertEquals(lgs_gm.doors.oldValue, lgs_gm.doors.oldValue.CLOSED);
		//Check
		assertEquals(lgs_gm.gears.oldValue, lgs_gm.gears.oldValue.EXTENDED);
		//Set
		lgs_gm.handle.Value = lgs_gm.handle.Value.UP;
		//Step
		lgs_gm.UpdateASM();
		//Check
		assertEquals(lgs_gm.gears.oldValue, lgs_gm.gears.oldValue.EXTENDED);
		//Check
		assertEquals(lgs_gm.doors.oldValue, lgs_gm.doors.oldValue.OPENING);
		//Set
		lgs_gm.handle.Value = lgs_gm.handle.Value.UP;
		//Step
		lgs_gm.UpdateASM();
		//Check
		assertEquals(lgs_gm.doors.oldValue, lgs_gm.doors.oldValue.OPEN);
		//Check
		assertEquals(lgs_gm.gears.oldValue, lgs_gm.gears.oldValue.EXTENDED);
		//Step
		lgs_gm.UpdateASM();
	}
}