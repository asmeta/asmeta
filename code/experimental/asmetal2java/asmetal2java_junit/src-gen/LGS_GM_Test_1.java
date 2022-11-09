import static org.junit.Assert.*;
import org.junit.Test;

public class LGS_GM_Test_1 {

	 @Test
	 public void LGS_GM_Test(){

		 LGS_GM lgs = new LGS_GM();
		 assertNotNull(lgs);

		 //Check
		 assertEquals(lgs.gears.oldValue,lgs.gears.newValue.EXTENDED);
		 //Check
		 assertEquals(lgs.doors.oldValue,lgs.doors.newValue.CLOSED);
		 //Set
		 lgs.handle.Value = lgs.handle.Value.DOWN;
		 //Step
		 lgs.UpdateASM();
		 //Set
		 lgs.handle.Value = lgs.handle.Value.UP;
		 //Step
		 lgs.UpdateASM();
		 //Check
		 assertEquals(lgs.doors.oldValue,lgs.doors.newValue.OPENING);
		 //Set
		 lgs.handle.Value = lgs.handle.Value.UP;
		 //Step
		 lgs.UpdateASM();
		 //Check
		 assertEquals(lgs.doors.oldValue,lgs.doors.newValue.OPEN);
		 //Set
		 lgs.handle.Value = lgs.handle.Value.UP;
		 //Step
		 lgs.UpdateASM();
		 //Check
		 assertEquals(lgs.gears.oldValue,lgs.gears.newValue.RETRACTING);
		 //Set
		 lgs.handle.Value = lgs.handle.Value.UP;
		 //Step
		 lgs.UpdateASM();

	 }
}