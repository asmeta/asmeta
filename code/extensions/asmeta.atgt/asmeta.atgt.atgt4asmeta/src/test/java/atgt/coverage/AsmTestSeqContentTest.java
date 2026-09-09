package atgt.coverage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import atgt.specification.location.Variable;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.IdExpressionCreator;
import tgtlib.definitions.expression.type.IntegerType;

public class AsmTestSeqContentTest {

	private static final IdExpressionCreator iec = new IdExpressionCreator();
	private static final IdExpression aID = iec.createIdExpression("a", IntegerType.INTEGER_TYPE);
	static Variable a = new Variable(aID, null);

	public static Collection<Object[]> option() {
		return Arrays.asList(new Object[][] { { true }, { false } });
	}

	public void initAsmTestSeqContentTest(boolean b) {
		AsmTestSeqContent.addOnlyChangeValues = b;
	}

	// errore: non posso udare questo metodo
	@MethodSource("option") @ParameterizedTest
	public void testAddAssignmentError1_notype(boolean b) {
		initAsmTestSeqContentTest(b);
		AsmTestSeqContent content = new AsmTestSeqContent();
		assertThrows(Exception.class, () ->
			content.addAssignment("a", "b"));
	}

	// new state must be initiated
	@MethodSource("option") @ParameterizedTest
	public void testAddAssignmentError_nonewstate(boolean b) {
		initAsmTestSeqContentTest(b);
		AsmTestSeqContent content = new AsmTestSeqContent();
		assertThrows(AssertionError.class, () ->
			content.addAssignment(a, "0"));
	}

	// update inconsistenti - dovrei avere un errore (per ora solo messaggio)
	@MethodSource("option") @ParameterizedTest
	public void testAddAssignmentError_IncosistentUpdate(boolean b) {
		initAsmTestSeqContentTest(b);
		AsmTestSeqContent content = new AsmTestSeqContent();
		content.addState();
		content.addAssignment(a, "0");
		content.addAssignment(a, "6");
	}

	// corretti
	// aggiungo uno stato semplice
	@MethodSource("option") @ParameterizedTest
	public void testAddAssignment1(boolean b) {
		initAsmTestSeqContentTest(b);
		AsmTestSeqContent content = new AsmTestSeqContent();
		content.addState();
		content.addAssignment(a, "0");
		assertEquals(1, content.allInstructions().size());
		content.close();
		assertEquals(1, content.allInstructions().size());
	}

	// aggiungo uno stato vuoto alla fine, senza aggiunta di variabili, viene
	// cancellato
	// gli stati vuori finali se vuoti vengono cancellati (per evitare di avere code vuote)
	@MethodSource("option") @ParameterizedTest
	public void testAddAssignment2(boolean b) {
		initAsmTestSeqContentTest(b);
		AsmTestSeqContent content = new AsmTestSeqContent();
		content.addState();
		content.addAssignment(a, "0");
		assertEquals(1, content.allInstructions().size());
		content.addState(); // empty state
		content.close();
		if (AsmTestSeqContent.addOnlyChangeValues) assertEquals(1, content.allInstructions().size());
		else assertEquals(2, content.allInstructions().size());
	}

	// aggiunto uno stato vero e proprio
	@MethodSource("option") @ParameterizedTest
	public void testAddAssignment3(boolean b) {
		initAsmTestSeqContentTest(b);
		AsmTestSeqContent content = new AsmTestSeqContent();
		content.addState();
		content.addAssignment(a, "0");
		assertEquals(1, content.allInstructions().size());
		content.addState();
		content.addAssignment(a, "2");
		content.close();
		assertEquals(2, content.allInstructions().size());
	}

	@MethodSource("option") @ParameterizedTest
	public void testAddAssignmentUseless(boolean b) {
		initAsmTestSeqContentTest(b);
		AsmTestSeqContent content = new AsmTestSeqContent();
		content.addState();
		content.addAssignment(a, "0");
		assertEquals(1, content.allInstructions().size());
		content.addState();
		content.addAssignment(a, "0");
		assertEquals(2, content.allInstructions().size());
		// terzo stato
		content.addState();
		// però il secondo stato è empty
		System.out.println(content.allInstructions().get(1));
		if (AsmTestSeqContent.addOnlyChangeValues) 		assertTrue(content.allInstructions().get(1).isEmpty());		
		else assertEquals(1, content.allInstructions().get(1).size());
	}

	@MethodSource("option") @ParameterizedTest
	public void testAddAssignment4(boolean b) {
		initAsmTestSeqContentTest(b);
		AsmTestSeqContent content = new AsmTestSeqContent();
		content.addState();
		content.addAssignment(a, "0");
		assertEquals(1, content.allInstructions().size());
		content.addState();
		// committo soloi cambiamenti
		assertNull(content.allInstructions().get(1).get(a));
		assertEquals(2, content.allInstructions().size());
		content.addState();
		content.addAssignment(a, "2");
		content.close();
		assertEquals(3, content.allInstructions().size());
	}
}
