package atgt.combinatorial.yices;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import atgt.combinatorial.NWiseEqTestCondition;
import atgt.coverage.AsmTestCondition;
import atgt.coverage.AsmTestSequence;
import atgt.specification.ASMSpecification;
import atgt.specification.location.Variable;
import extgt.coverage.combinatorial.MonitoredData;
import tgtlib.definitions.expression.IdExpressionCreator;
import tgtlib.definitions.expression.type.BoolType;
import tgtlib.definitions.expression.type.EnumConst;
import tgtlib.generator.MCAnalysisResult;
import tgtlib.generator.MCExecutionResultReader;
import tgtlib.generator.ParsingModel;

/**Classe di test per la verifica del funzionamento dell'eccezione di errore del modello.*/

public class ErroreModelloYices {

	/*Dichiarazione delle variabili utilizzate nella classe di test.*/

	private static IdExpressionCreator ecc = new IdExpressionCreator();
	
	/*monitoredData --> contiene le variabili da controllare.*/
	private static MonitoredData monitoredData;
	/*tp --> contiene i test predicate.*/
	private static AsmTestCondition tp;
	/*asm --> contiene le specifiche ASM.*/
	private static ASMSpecification asm;
	
	private static Variable x;
	private static Variable y;
		
	/**Metodo per il settaggio dei valori iniziali delle variabili che saranno utilizzate
	 * nel test.*/
	@BeforeClass
	public static void setup() {

		monitoredData = new MonitoredData();
		asm = new ASMSpecification();
		
		x = new Variable(ecc.createIdExpression("x", null), BoolType.BOOLTYPE, null);
		y = new Variable(ecc.createIdExpression("y", null), BoolType.BOOLTYPE, null);
		
		monitoredData.add(x);
		monitoredData.add(y);
		
		asm.addVariable(x);
		asm.addVariable(y);
		
		/*Test predicate*/
		/*Dichiatazione di vars, che deve contenere le variabili contenute nel test predicate.*/
		Variable[] vars = {x, y};
		/*Dichiarazione dei valori che le variabili assumono.*/
		EnumConst[] vals = {BoolType.FALSE_CONST, BoolType.FALSE_CONST};
		/*Assegno a tp il test predicate che si sta creando. Assegno un nome, le variabili
		 * e i valori delle variabili.*/
		tp = new NWiseEqTestCondition("tp", Arrays.asList(vars), Arrays.asList(vals));
				
	}
	
	/**Metodo che testa la funzionalità del metodo analyses. Viene testata la lettura dell'output
	 * di Yices. In questo test viene fornito un modello con sintassi scorretta, quindi il metodo
	 * di analisi deve lanciare l'eccezione di errore del modello di yices.*/
	@Test(expected=ParsingModel.class)
	public void testAnalysesUnSat () {
		
		List<String> cmds = new ArrayList<String>();
		
		/*Costruzione della stringa che contiene i comandi, al suo interno viene
		 * volutamente inserito un errore, viene omesso un ;*/
		/*Contiene un errore.*/
		cmds.add("(x = true)");
		
		YicesModelGen yices = new YicesModelGenExec(asm.getVariables(),asm.getAxiom(),AsmTestSequence.factory);
		
		/*Richiamo Yices, cioè eseguo da linea di comando il programma per ottenere
		 * il risultato.*/
		StringBuffer result = yices.getModelFor(cmds);
		
		StringReader input = new StringReader(result.toString());
		
		System.out.println("resultt: " + result);
		
		/*Creo il test con il tp definito.*/
		AsmTestSequence test = new AsmTestSequence(tp);
		
		
		/*Analizzo il risultato di Yices.*/
		MCAnalysisResult analys = yices.analyses(new MCExecutionResultReader(input), test);
		
		Assert.assertNull(analys);
		
	}
}