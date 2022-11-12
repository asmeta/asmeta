import static org.junit.Assert.*;
import org.junit.Test;

import static coffeeVendingMachine_sig.Product;
public class coffeeVendingMachine_Test_1{
@Test
public void coffeeVendingMachine_Test(){
coffeeVendingMachine cof = new coffeeVendingMachine();
assertNotNull(cof);
//Check

assertTrue(cof.available.get(Product.TEA ));
//Check
assertTrue(cof.coins.get().value == 0);
//Set
cof.insertedCoin.Value = cof.insertedCoin.Value.ONE;
//Step
cof.UpdateASM();
//Check
assertTrue(cof.available(TEA).get().value == 9);
//Check
assertTrue(cof.coins.get().value == 1);
//Check
assertTrue(cof.available(MILK).get().value == 10);
//Set
cof.insertedCoin.Value = cof.insertedCoin.Value.HALF;
//Step
cof.UpdateASM();
//Check
assertTrue(cof.available(TEA).get().value == 9);
//Check
assertTrue(cof.coins.get().value == 2);
//Check
assertTrue(cof.available(MILK).get().value == 9);
//Check
assertTrue(cof.available(COFFEE).get().value == 10);
//Set
cof.insertedCoin.Value = cof.insertedCoin.Value.ONE;
//Step
cof.UpdateASM();
//Check
assertTrue(cof.available(TEA).get().value == 9);
//Check
assertTrue(cof.coins.get().value == 3);
//Check
assertTrue(cof.available(MILK).get().value == 9);
//Check
assertTrue(cof.available(COFFEE).get().value == 9);
//Set
cof.insertedCoin.Value = cof.insertedCoin.Value.HALF;
//Step
cof.UpdateASM();
//Check
assertTrue(cof.available(TEA).get().value == 9);
//Check
assertTrue(cof.coins.get().value == 4);
//Check
assertTrue(cof.available(MILK).get().value == 8);
//Check
assertTrue(cof.available(COFFEE).get().value == 9);
//Set
cof.insertedCoin.Value = cof.insertedCoin.Value.ONE;
//Step
cof.UpdateASM();
//Check
assertTrue(cof.available(TEA).get().value == 9);
//Check
assertTrue(cof.coins.get().value == 5);
//Check
assertTrue(cof.available(MILK).get().value == 8);
//Check
assertTrue(cof.available(COFFEE).get().value == 8);
//Set
cof.insertedCoin.Value = cof.insertedCoin.Value.ONE;
//Step
cof.UpdateASM();
//Check
assertTrue(cof.available(TEA).get().value == 9);
//Check
assertTrue(cof.coins.get().value == 6);
//Check
assertTrue(cof.available(MILK).get().value == 8);
//Check
assertTrue(cof.available(COFFEE).get().value == 7);
//Set
cof.insertedCoin.Value = cof.insertedCoin.Value.HALF;
//Step
cof.UpdateASM();
//Check
assertTrue(cof.available(TEA).get().value == 9);
//Check
assertTrue(cof.coins.get().value == 7);
//Check
assertTrue(cof.available(MILK).get().value == 7);
//Check
assertTrue(cof.available(COFFEE).get().value == 7);
//Set
cof.insertedCoin.Value = cof.insertedCoin.Value.HALF;
//Step
cof.UpdateASM();
//Check
assertTrue(cof.available(TEA).get().value == 9);
//Check
assertTrue(cof.coins.get().value == 8);
//Check
assertTrue(cof.available(MILK).get().value == 6);
//Check
assertTrue(cof.available(COFFEE).get().value == 7);
//Set
cof.insertedCoin.Value = cof.insertedCoin.Value.HALF;
//Step
cof.UpdateASM();
//Check
assertTrue(cof.available(TEA).get().value == 9);
//Check
assertTrue(cof.coins.get().value == 9);
//Check
assertTrue(cof.available(MILK).get().value == 5);
//Check
assertTrue(cof.available(COFFEE).get().value == 7);
//Set
cof.insertedCoin.Value = cof.insertedCoin.Value.HALF;
//Step
cof.UpdateASM();
//Check
assertTrue(cof.available(TEA).get().value == 9);
//Check
assertTrue(cof.coins.get().value == 10);
//Check
assertTrue(cof.available(MILK).get().value == 4);
//Check
assertTrue(cof.available(COFFEE).get().value == 7);
//Step
cof.UpdateASM();
}
}