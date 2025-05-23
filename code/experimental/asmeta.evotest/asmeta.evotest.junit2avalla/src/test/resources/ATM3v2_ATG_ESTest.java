/*
 * This file was automatically generated by EvoSuite
 * Tue Jan 07 21:15:05 GMT 2025
 */


import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true, useJEE = true) 
public class ATM3v2_ATG_ESTest extends ATM3v2_ATG_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test00()  throws Throwable  {
      ATM3v2_ATG aTM3v2_ATG0 = new ATM3v2_ATG();
      assertEquals(ATM3v2.State.AWAITCARD, aTM3v2_ATG0.get_atmState());
      assertEquals(1000, aTM3v2_ATG0.get_moneyLeft());
      assertEquals(0, aTM3v2_ATG0.get_numOfBalanceChecks());
      assertNotNull(aTM3v2_ATG0);
      
      ATM3v2.MoneySizeSelection aTM3v2_MoneySizeSelection0 = ATM3v2.MoneySizeSelection.OTHER;
      aTM3v2_ATG0.set_standardOrOther(aTM3v2_MoneySizeSelection0);
      assertEquals(ATM3v2.State.AWAITCARD, aTM3v2_ATG0.get_atmState());
      assertEquals(1000, aTM3v2_ATG0.get_moneyLeft());
      assertEquals(0, aTM3v2_ATG0.get_numOfBalanceChecks());
      
      ATM3v2.Service aTM3v2_Service0 = ATM3v2.Service.WITHDRAWAL;
      aTM3v2_ATG0.set_selectedService(aTM3v2_Service0);
      assertEquals(ATM3v2.State.AWAITCARD, aTM3v2_ATG0.get_atmState());
      assertEquals(1000, aTM3v2_ATG0.get_moneyLeft());
      assertEquals(0, aTM3v2_ATG0.get_numOfBalanceChecks());
      
      aTM3v2_ATG0.set_insertedCard("card2");
      assertEquals(ATM3v2.State.AWAITCARD, aTM3v2_ATG0.get_atmState());
      assertEquals(1000, aTM3v2_ATG0.get_moneyLeft());
      assertEquals(0, aTM3v2_ATG0.get_numOfBalanceChecks());
      
      aTM3v2_ATG0.step();
      assertEquals(ATM3v2.State.AWAITPIN, aTM3v2_ATG0.get_atmState());
      assertEquals(1000, aTM3v2_ATG0.get_moneyLeft());
      assertEquals(0, aTM3v2_ATG0.get_numOfBalanceChecks());
      
      aTM3v2_ATG0.set_insertedPin(2);
      aTM3v2_ATG0.step();
      assertEquals(ATM3v2.State.AWAITPIN, aTM3v2_ATG0.get_atmState());
      assertEquals(1000, aTM3v2_ATG0.get_moneyLeft());
      assertEquals(0, aTM3v2_ATG0.get_numOfBalanceChecks());
      
      aTM3v2_ATG0.step();
      assertEquals(ATM3v2.State.CHOOSE, aTM3v2_ATG0.get_atmState());
      assertEquals(1000, aTM3v2_ATG0.get_moneyLeft());
      assertEquals(0, aTM3v2_ATG0.get_numOfBalanceChecks());
      
      aTM3v2_ATG0.step();
      assertEquals(ATM3v2.State.CHOOSEAMOUNT, aTM3v2_ATG0.get_atmState());
      assertEquals(1000, aTM3v2_ATG0.get_moneyLeft());
      assertEquals(0, aTM3v2_ATG0.get_numOfBalanceChecks());
      
      aTM3v2_ATG0.step();
      assertEquals(ATM3v2.State.OTHERAMOUNTSELECTION, aTM3v2_ATG0.get_atmState());
      assertEquals(1000, aTM3v2_ATG0.get_moneyLeft());
      assertEquals(0, aTM3v2_ATG0.get_numOfBalanceChecks());
      
      aTM3v2_ATG0.set_insertMoneySizeOther(-150.5);
      aTM3v2_ATG0.step();
      assertEquals(ATM3v2.State.OTHERAMOUNTSELECTION, aTM3v2_ATG0.get_atmState());
      assertEquals(1150.5, aTM3v2_ATG0.get_moneyLeft());
      assertEquals(0, aTM3v2_ATG0.get_numOfBalanceChecks());
      
  }

  @Test(timeout = 4000)
  public void test01()  throws Throwable  {
      ATM3v2_ATG aTM3v2_ATG0 = new ATM3v2_ATG();
      assertEquals(1000, aTM3v2_ATG0.get_moneyLeft());
      assertEquals(0, aTM3v2_ATG0.get_numOfBalanceChecks());
      assertEquals(ATM3v2.State.AWAITCARD, aTM3v2_ATG0.get_atmState());
      assertNotNull(aTM3v2_ATG0);
      
      ATM3v2.Service aTM3v2_Service0 = ATM3v2.Service.EXIT;
      aTM3v2_ATG0.set_selectedService(aTM3v2_Service0);
      assertEquals(1000, aTM3v2_ATG0.get_moneyLeft());
      assertEquals(0, aTM3v2_ATG0.get_numOfBalanceChecks());
      assertEquals(ATM3v2.State.AWAITCARD, aTM3v2_ATG0.get_atmState());
      
      aTM3v2_ATG0.set_insertedCard("card2");
      assertEquals(1000, aTM3v2_ATG0.get_moneyLeft());
      assertEquals(0, aTM3v2_ATG0.get_numOfBalanceChecks());
      assertEquals(ATM3v2.State.AWAITCARD, aTM3v2_ATG0.get_atmState());
      
      // Undeclared exception!
      try { 
        aTM3v2_ATG0.step();
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("ATM3v2_ATG", e);
      }
  }

  @Test(timeout = 4000)
  public void test02()  throws Throwable  {
      ATM3v2_ATG aTM3v2_ATG0 = new ATM3v2_ATG();
      assertEquals(0, aTM3v2_ATG0.get_numOfBalanceChecks());
      assertEquals(ATM3v2.State.AWAITCARD, aTM3v2_ATG0.get_atmState());
      assertEquals(-1000, aTM3v2_ATG0.get_moneyLeft());
      assertNotNull(aTM3v2_ATG0);
      
      ATM3v2.MoneySizeSelection aTM3v2_MoneySizeSelection0 = ATM3v2.MoneySizeSelection.OTHER;
      aTM3v2_ATG0.set_standardOrOther(aTM3v2_MoneySizeSelection0);
      assertEquals(0, aTM3v2_ATG0.get_numOfBalanceChecks());
      assertEquals(ATM3v2.State.AWAITCARD, aTM3v2_ATG0.get_atmState());
      assertEquals(1000, aTM3v2_ATG0.get_moneyLeft());
      
      ATM3v2.Service aTM3v2_Service0 = ATM3v2.Service.BALANCE;
      aTM3v2_ATG0.set_selectedService(aTM3v2_Service0);
      assertEquals(0, aTM3v2_ATG0.get_numOfBalanceChecks());
      assertEquals(ATM3v2.State.AWAITCARD, aTM3v2_ATG0.get_atmState());
      assertEquals(1000, aTM3v2_ATG0.get_moneyLeft());
      
      aTM3v2_ATG0.set_insertedCard("card2");
      assertEquals(0, aTM3v2_ATG0.get_numOfBalanceChecks());
      assertEquals(ATM3v2.State.AWAITCARD, aTM3v2_ATG0.get_atmState());
      assertEquals(1000, aTM3v2_ATG0.get_moneyLeft());
      
      aTM3v2_ATG0.step();
      assertEquals(ATM3v2.State.AWAITPIN, aTM3v2_ATG0.get_atmState());
      assertEquals(0, aTM3v2_ATG0.get_numOfBalanceChecks());
      assertEquals(1000, aTM3v2_ATG0.get_moneyLeft());
      
      aTM3v2_ATG0.step();
      assertEquals(0, aTM3v2_ATG0.get_numOfBalanceChecks());
      assertEquals(ATM3v2.State.AWAITCARD, aTM3v2_ATG0.get_atmState());
      assertEquals(1000, aTM3v2_ATG0.get_moneyLeft());
  }

  @Test(timeout = 4000)
  public void test03()  throws Throwable  {
      ATM3v2_ATG aTM3v2_ATG0 = new ATM3v2_ATG();
      assertEquals(1000, aTM3v2_ATG0.get_moneyLeft());
      assertEquals(0, aTM3v2_ATG0.get_numOfBalanceChecks());
      assertEquals(ATM3v2.State.AWAITCARD, aTM3v2_ATG0.get_atmState());
      assertNotNull(aTM3v2_ATG0);
      
      int int0 = aTM3v2_ATG0.get_balance_card3();
      assertEquals(548, int0);
      assertEquals(1000, aTM3v2_ATG0.get_moneyLeft());
      assertEquals(0, aTM3v2_ATG0.get_numOfBalanceChecks());
      assertEquals(ATM3v2.State.AWAITCARD, aTM3v2_ATG0.get_atmState());
      
      int int1 = 1;
      int int2 = int1;
      assertEquals(int1, int2);
  }

}
