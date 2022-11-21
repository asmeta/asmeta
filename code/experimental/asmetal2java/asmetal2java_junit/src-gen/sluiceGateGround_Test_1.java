import static org.junit.Assert.*;
import org.junit.Test;

public class sluiceGateGround_Test_1 {
	@Test
	public void sluiceGateGround_Test() {
		sluiceGateGround slu = new sluiceGateGround();
		assertNotNull(slu);
		//Check
		assertEquals(slu.phase.oldValue, slu.phase.oldValue.FULLYCLOSED);
		//Set
		slu.passed.set(slu.openPeriod(), false);
		
		//Step
		slu.UpdateASM();
		//Check
		assertEquals(slu.phase.oldValue, slu.phase.oldValue.FULLYCLOSED);
		//Set
		slu.passed.set(slu.openPeriod(), false);
		//Step
		slu.UpdateASM();
		//Check
		assertEquals(slu.phase.oldValue, slu.phase.oldValue.FULLYCLOSED);
		//Set
		slu.passed.set(slu.openPeriod(), false);
		//Step
		slu.UpdateASM();
		//Check
		assertEquals(slu.phase.oldValue, slu.phase.oldValue.FULLYCLOSED);
		//Set
		slu.passed.set(slu.openPeriod(), true);
		//Step
		slu.UpdateASM();
		//Check
		assertEquals(slu.phase.oldValue, slu.phase.oldValue.FULLYOPEN);
		//Set
		slu.passed.set(slu.openPeriod(), true);
		//Set
		slu.passed.set(slu.openPeriod(), false);
		//Step
		slu.UpdateASM();
		//Check
		assertEquals(slu.phase.oldValue, slu.phase.oldValue.FULLYOPEN);
		//Set
		slu.passed.set(slu.openPeriod(), true);
		//Set
		slu.passed.set(slu.openPeriod(), true);
		//Step
		slu.UpdateASM();
		//Check
		assertEquals(slu.phase.oldValue, slu.phase.oldValue.FULLYCLOSED);
		//Step
		slu.UpdateASM();
	}
}