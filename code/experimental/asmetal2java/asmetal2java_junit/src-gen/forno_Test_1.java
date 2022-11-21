import static org.junit.Assert.*;
import org.junit.Test;

public class forno_Test_1 {
	@Test
	public void forno_Test() {
		forno forno = new forno();
		assertNotNull(forno);
		//Check
		assertEquals(forno.statoForno.oldValue, forno.statoForno.oldValue.SPENTO);
		//Check
		assertEquals(forno.statoPorta.oldValue, forno.statoPorta.oldValue.CHIUSA);
		//Set
		forno.segnalePorta.Value = true;
		//Set
		forno.accendiForno.Value = false;
		//Step
		forno.UpdateASM();
		//Check
		assertEquals(forno.statoForno.oldValue, forno.statoForno.oldValue.SPENTO);
		//Check
		assertEquals(forno.statoPorta.oldValue, forno.statoPorta.oldValue.APERTA);
		//Set
		forno.segnalePorta.Value = true;
		//Set
		forno.accendiForno.Value = true;
		//Step
		forno.UpdateASM();
		//Check
		assertEquals(forno.statoForno.oldValue, forno.statoForno.oldValue.SPENTO);
		//Check
		assertEquals(forno.statoPorta.oldValue, forno.statoPorta.oldValue.APERTA);
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
		//Set
		forno.segnalePorta.Value = false;
		//Set
		forno.accendiForno.Value = true;
		//Step
		forno.UpdateASM();
		//Check
		assertEquals(forno.statoForno.oldValue, forno.statoForno.oldValue.ACCESO);
		//Check
		assertEquals(forno.statoPorta.oldValue, forno.statoPorta.oldValue.CHIUSA);
		//Set
		forno.accendiForno.Value = true;
		//Set
		forno.segnalePorta.Value = true;
		//Step
		forno.UpdateASM();
		//Check
		assertEquals(forno.statoForno.oldValue, forno.statoForno.oldValue.ACCESO);
		//Check
		assertEquals(forno.statoPorta.oldValue, forno.statoPorta.oldValue.CHIUSA);
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
	}
}