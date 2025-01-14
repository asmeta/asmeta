import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;

public class Integer_ATG_ESTest {

  @Test(timeout = 4000)
  public void test00()  throws Throwable  {
	  Numbers_ATG numbers_ATG0 = new Numbers_ATG();
      
	  assertEquals(20.5, (double)numbers_ATG0.get_double());
	  assertEquals(-15.8, (double)numbers_ATG0.get_negative());
	  int int0 = (-3188);
	  numbers_ATG0.set_insertedPin(int0);
	  
	  numbers_ATG0.step();
      
      int int3 = numbers_ATG0.get_balance_card3();
      assertEquals(548, int3);
      numbers_ATG0.set_insertMoneySizeOther(int3);
      assertEquals(548, int3); // this check must be ignored
      
      numbers_ATG0.step();
      
      Integer int2 = numbers_ATG0.get_balance_card2();
      assertEquals(3, (int)numbers_ATG0.get_insertedPin());
      assertEquals(-3188, (int)int2);
      numbers_ATG0.set_insertedPin(5.5);
  }
}