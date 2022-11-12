import static org.junit.Assert.*;
import org.junit.Test;
public class Contatore_U_DA_H_Test_1{
@Test
public void Contatore_U_DA_H_Test(){
Contatore_U_DA_H con = new Contatore_U_DA_H();
assertNotNull(con);
//Set
con.click.Value = false;
//Step
con.UpdateASM();
//Check
assertTrue(con.unit.get().value == 0);
//Set
con.click.Value = true;
//Step
con.UpdateASM();
//Check
assertTrue(con.unit.get().value == 1);
//Set
con.click.Value = false;
//Step
con.UpdateASM();
//Check
assertTrue(con.unit.get().value == 1);
//Set
con.click.Value = true;
//Step
con.UpdateASM();
//Check
assertTrue(con.unit.get().value == 2);
//Set
con.click.Value = false;
//Step
con.UpdateASM();
//Check
assertTrue(con.unit.get().value == 2);
//Set
con.click.Value = true;
//Step
con.UpdateASM();
//Check
assertTrue(con.unit.get().value == 3);
//Set
con.click.Value = true;
//Step
con.UpdateASM();
//Check
assertTrue(con.unit.get().value == 4);
//Set
con.click.Value = false;
//Step
con.UpdateASM();
//Check
assertTrue(con.unit.get().value == 4);
//Set
con.click.Value = false;
//Step
con.UpdateASM();
//Check
assertTrue(con.unit.get().value == 4);
//Set
con.click.Value = true;
//Step
con.UpdateASM();
//Check
assertTrue(con.unit.get().value == 5);
//Step
con.UpdateASM();
}
}