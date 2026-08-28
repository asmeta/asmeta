package atgt.testseqexport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import atgt.coverage.AsmTestSequence;
import atgt.specification.location.Location;
import atgt.specification.location.Variable;

public class toXmlSerialTime extends TestSeqTrad {

	/* Variabile che conterrà l'indirizzo del dtd */
	static String scenarioDTD;

	public toXmlSerialTime(File f, AsmTestSequence ts) throws FileNotFoundException {
		super(f, ts);
		scenarioDTD = new File("src/atgt/gui/tree/DTD_atgt.dtd").getAbsoluteFile().toString();
	}

	/* metodo che salva il file in xml */
	@Override
	public void saveToStream() {
		long start = System.currentTimeMillis();
//			for(int i=0; i<=1000; i++){
		PrintStream dst = new PrintStream(out);
		Iterator<Map<Location, String>> it = testSequence.allInstructions().iterator();
		/* Intestazione del file xml con collegamento al dtd */
		dst.println("<?xml version=" + '"' + "1.0" + '"' + " encoding=" + '"' + "ISO-8859-1" + '"' + "?>");
		dst.println("<!DOCTYPE DTD_atgt SYSTEM " + '"' + scenarioDTD + '"' + ">");
		dst.println("<test>");

		/* Scorrimento della lista contenete gli stati */
//				boolean test = true;
//				int j = 100;
//				while(it.hasNext() || test){
//					j--;					
//					if(!it.hasNext() && test){
//						it = testSequence.allInstructions().iterator();
//					}
		while (it.hasNext()) {
			Map<Location, String> state = it.next();
			dst.println("  <state>");
			/*
			 * Scorrimento della lista contenete le variabili che si modificano nel cambio
			 * di stato
			 */
			for (Entry<Location, String> p : state.entrySet()) {
				Location v = p.getKey();
				String value = p.getValue();

				/* Verifica del tipo della variabile per poter definire il giusto tag */
				if (v.isControlled()) {
					dst.println("    <controlled id=\"" + v.toString() + "\">" + value + "</controlled>");
				} else if (v.isMonitored()) {
					dst.println("    <monitored id=\"" + v.toString() + "\">" + value + "</monitored>");

				} else
					dst.println("ERRORE" + v.toString());
			}
			/* chiusura tag */
			dst.println("  </state>");
//					if(j == 0) test = false;
		}
		/* chiusura tag e PrintStream */
		dst.println("</test>");
		dst.close();

//			}
		long time = System.currentTimeMillis() - start;
		System.out.print(time);
	}
}