package atgt.coverage;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import atgt.specification.location.Variable;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.IdExpressionCreator;
import tgtlib.definitions.expression.type.IntegerType;

@RunWith(Parameterized.class)
public class AsmTestSeqContentTest {

	private static final IdExpressionCreator iec = new IdExpressionCreator();
	private static final IdExpression aID = iec.createIdExpression("a", IntegerType.INTEGER_TYPE);
	static Variable a = new Variable(aID, null);

	@Parameters
	public static Collection<Object[]> option() {
		return Arrays.asList(new Object[][] { { true }, { false } });
	}

	public AsmTestSeqContentTest(boolean b) {
		AsmTestSeqContent.addOnlyChangeValues = b;
	}

	// errore: non posso udare questo metodo
	@Test(expected = Exception.class)
	public void testAddAssignmentError1_notype() {
		AsmTestSeqContent content = new AsmTestSeqContent();
		content.addAssignment("a", "b");
	}

	// new state must be initiated
	@Test(expected = AssertionError.class)
	public void testAddAssignmentError_nonewstate() {
		AsmTestSeqContent content = new AsmTestSeqContent();
		content.addAssignment(a, "0");
	}

	// update inconsistenti - dovrei avere un errore (per ora solo messaggio)
	@Test
	public void testAddAssignmentError_IncosistentUpdate() {
		AsmTestSeqContent content = new AsmTestSeqContent();
		content.addState();
		content.addAssignment(a, "0");
		content.addAssignment(a, "6");
	}

	// corretti
	// aggiungo uno stato semplice
	@Test
	public void testAddAssignment1() {
		AsmTestSeqContent content = new AsmTestSeqContent();
		content.addState();
		content.addAssignment(a, "0");
		assertEquals(content.allInstructions().size(), 1);
		content.close();
		assertEquals(content.allInstructions().size(), 1);
	}

	// aggiungo uno stato vuoto alla fine, senza aggiunta di variabili, viene
	// cancellato
	// gli stati vuori finali se vuoti vengono cancellati (per evitare di avere code vuote)
	@Test
	public void testAddAssignment2() {
		AsmTestSeqContent content = new AsmTestSeqContent();
		content.addState();
		content.addAssignment(a, "0");
		assertEquals(content.allInstructions().size(), 1);
		content.addState(); // empty state
		content.close();
		if (AsmTestSeqContent.addOnlyChangeValues) assertEquals(content.allInstructions().size(), 1);
		else assertEquals(content.allInstructions().size(), 2);
	}

	// aggiunto uno stato vero e proprio
	@Test
	public void testAddAssignment3() {
		AsmTestSeqContent content = new AsmTestSeqContent();
		content.addState();
		content.addAssignment(a, "0");
		assertEquals(content.allInstructions().size(), 1);
		content.addState();
		content.addAssignment(a, "2");
		content.close();
		assertEquals(content.allInstructions().size(), 2);
	}

	@Test
	public void testAddAssignmentUseless() {
		AsmTestSeqContent content = new AsmTestSeqContent();
		content.addState();
		content.addAssignment(a, "0");
		assertEquals(content.allInstructions().size(), 1);
		content.addState();
		content.addAssignment(a, "0");
		assertEquals(content.allInstructions().size(), 2);
		// terzo stato
		content.addState();
		// però il secondo stato è empty
		System.out.println(content.allInstructions().get(1));
		if (AsmTestSeqContent.addOnlyChangeValues) 		assertTrue(content.allInstructions().get(1).isEmpty());		
		else assertTrue(content.allInstructions().get(1).size() == 1);
	}

	@Test
	public void testAddAssignment4() {
		AsmTestSeqContent content = new AsmTestSeqContent();
		content.addState();
		content.addAssignment(a, "0");
		assertEquals(content.allInstructions().size(), 1);
		content.addState();
		// committo soloi cambiamenti
		assertNull(content.allInstructions().get(1).get(a));
		assertEquals(content.allInstructions().size(), 2);
		content.addState();
		content.addAssignment(a, "2");
		content.close();
		assertEquals(content.allInstructions().size(), 3);
	}
}
