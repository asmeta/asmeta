import static org.junit.Assert.*;
import org.junit.Test;

public class forno_Test_0 {
	@Test
	public void forno_Test() {
		forno forno = new forno();
		assertNotNull(forno);
		//Check
		assertEquals(forno.statoPorta.oldValue, forno.statoPorta.oldValue.CHIUSA);
		//Check
		assertEquals(forno.statoForno.oldValue, forno.statoForno.oldValue.SPENTO);
		//Set
		forno.accendiForno.Value = false;
		//Set
		forno.segnalePorta.Value = true;
		//Step
		forno.UpdateASM();
		//Check
		assertEquals(forno.statoPorta.oldValue, forno.statoPorta.oldValue.APERTA);
		//Check
		assertEquals(forno.statoForno.oldValue, forno.statoForno.oldValue.SPENTO);
		//Set
		forno.segnalePorta.Value = false;
		//Set
		forno.accendiForno.Value = false;
		//Step
		forno.UpdateASM();
		//Check
		assertEquals(forno.statoPorta.oldValue, forno.statoPorta.oldValue.CHIUSA);
		//Check
		assertEquals(forno.statoForno.oldValue, forno.statoForno.oldValue.SPENTO);
		//Set
		forno.segnalePorta.Value = false;
		//Set
		forno.accendiForno.Value = false;
		//Step
		forno.UpdateASM();
		//Check
		assertEquals(forno.statoForno.oldValue, forno.statoForno.oldValue.SPENTO);
		//Check
		assertEquals(forno.statoPorta.oldValue, forno.statoPorta.oldValue.CHIUSA);
		//Step
		forno.UpdateASM();
	}
}