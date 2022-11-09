import static org.junit.Assert.*;
import org.junit.Test;
import static coffeVendingMachine.Product.TEA;
public class coffeeVendingMachine_Test_1 {

	 @Test
	 public void coffeeVendingMachine_Test(){

		 coffeeVendingMachine cof = new coffeeVendingMachine();
		 assertNotNull(cof);

		 //Check
		 assertEquals(cof.available.oldValues.get(cof.Product_lista.TEA),10);
		 //Check
		 assertEquals(cof.coins.oldValue.value,0);
		 //Set
		 cof.insertedCoin.Value = cof.insertedCoin.Value.ONE;
		 //Step
		 cof.UpdateASM();
		 //Check
		 assertEquals(cof.available(TEA).oldValue.value,9);
		 //Check
		 assertEquals(cof.coins.oldValue.value,1);
		 //Check
		 assertEquals(cof.available(MILK).oldValue.value,10);
		 //Set
		 cof.insertedCoin.Value = cof.insertedCoin.Value.HALF;
		 //Step
		 cof.UpdateASM();
		 //Check
		 assertEquals(cof.available(TEA).oldValue.value,9);
		 //Check
		 assertEquals(cof.coins.oldValue.value,2);
		 //Check
		 assertEquals(cof.available(MILK).oldValue.value,9);
		 //Check
		 assertEquals(cof.available(COFFEE).oldValue.value,10);
		 //Set
		 cof.insertedCoin.Value = cof.insertedCoin.Value.ONE;
		 //Step
		 cof.UpdateASM();
		 //Check
		 assertEquals(cof.available(TEA).oldValue.value,9);
		 //Check
		 assertEquals(cof.coins.oldValue.value,3);
		 //Check
		 assertEquals(cof.available(MILK).oldValue.value,9);
		 //Check
		 assertEquals(cof.available(COFFEE).oldValue.value,9);
		 //Set
		 cof.insertedCoin.Value = cof.insertedCoin.Value.HALF;
		 //Step
		 cof.UpdateASM();
		 //Check
		 assertEquals(cof.available(TEA).oldValue.value,9);
		 //Check
		 assertEquals(cof.coins.oldValue.value,4);
		 //Check
		 assertEquals(cof.available(MILK).oldValue.value,8);
		 //Check
		 assertEquals(cof.available(COFFEE).oldValue.value,9);
		 //Set
		 cof.insertedCoin.Value = cof.insertedCoin.Value.ONE;
		 //Step
		 cof.UpdateASM();
		 //Check
		 assertEquals(cof.available(TEA).oldValue.value,9);
		 //Check
		 assertEquals(cof.coins.oldValue.value,5);
		 //Check
		 assertEquals(cof.available(MILK).oldValue.value,8);
		 //Check
		 assertEquals(cof.available(COFFEE).oldValue.value,8);
		 //Set
		 cof.insertedCoin.Value = cof.insertedCoin.Value.ONE;
		 //Step
		 cof.UpdateASM();
		 //Check
		 assertEquals(cof.available(TEA).oldValue.value,9);
		 //Check
		 assertEquals(cof.coins.oldValue.value,6);
		 //Check
		 assertEquals(cof.available(MILK).oldValue.value,8);
		 //Check
		 assertEquals(cof.available(COFFEE).oldValue.value,7);
		 //Set
		 cof.insertedCoin.Value = cof.insertedCoin.Value.HALF;
		 //Step
		 cof.UpdateASM();
		 //Check
		 assertEquals(cof.available(TEA).oldValue.value,9);
		 //Check
		 assertEquals(cof.coins.oldValue.value,7);
		 //Check
		 assertEquals(cof.available(MILK).oldValue.value,7);
		 //Check
		 assertEquals(cof.available(COFFEE).oldValue.value,7);
		 //Set
		 cof.insertedCoin.Value = cof.insertedCoin.Value.HALF;
		 //Step
		 cof.UpdateASM();
		 //Check
		 assertEquals(cof.available(TEA).oldValue.value,9);
		 //Check
		 assertEquals(cof.coins.oldValue.value,8);
		 //Check
		 assertEquals(cof.available(MILK).oldValue.value,6);
		 //Check
		 assertEquals(cof.available(COFFEE).oldValue.value,7);
		 //Set
		 cof.insertedCoin.Value = cof.insertedCoin.Value.HALF;
		 //Step
		 cof.UpdateASM();
		 //Check
		 assertEquals(cof.available(TEA).oldValue.value,9);
		 //Check
		 assertEquals(cof.coins.oldValue.value,9);
		 //Check
		 assertEquals(cof.available(MILK).oldValue.value,5);
		 //Check
		 assertEquals(cof.available(COFFEE).oldValue.value,7);
		 //Set
		 cof.insertedCoin.Value = cof.insertedCoin.Value.HALF;
		 //Step
		 cof.UpdateASM();
		 //Check
		 assertEquals(cof.available(TEA).oldValue.value,9);
		 //Check
		 assertEquals(cof.coins.oldValue.value,10);
		 //Check
		 assertEquals(cof.available(MILK).oldValue.value,4);
		 //Check
		 assertEquals(cof.available(COFFEE).oldValue.value,7);
		 //Step
		 cof.UpdateASM();

	 }
}