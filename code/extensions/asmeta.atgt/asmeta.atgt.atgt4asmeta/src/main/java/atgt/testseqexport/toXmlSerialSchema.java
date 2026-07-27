package atgt.testseqexport;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import atgt.coverage.AsmTestSequence;
import atgt.specification.location.Location;
import atgt.specification.location.Variable;

public class toXmlSerialSchema extends TestSeqTrad {

	/* TODO specificare nome stringa x dtd */
	static String scenarioXSD;

	public toXmlSerialSchema(File f, AsmTestSequence ts) {
		super(f, ts);
		scenarioXSD = new File("src/atgt/gui/tree/schema_atgt.xsd").getAbsoluteFile().toString();

	}

	/* metodo che salva il file in xml */
	@Override
	public void saveToStream() {
		PrintStream dst = new PrintStream(out);
		List<Map<Location, String>> instrList = testSequence.allInstructions();
		Iterator<Map<Location, String>> it = instrList.iterator();

		/* Scrittura intestazione più collegamento al file .xsd */
		dst.println("<?xml version=" + '"' + "1.0" + '"' + " encoding=" + '"' + "ISO-8859-1" + '"' + "?>");
		dst.println("<test" + '\n' + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" + '\n'
				+ "xsi:schemaLocation=\"" + scenarioXSD + "\">");
		// scorro la mappa
		while (it.hasNext()) {
			Map<Location, String> state = it.next();
			dst.println("  <state>");
			for (Map.Entry<Location, String> p : state.entrySet()) {
				Variable v = (Variable) p.getKey();
				String value = p.getValue();

				if (v.isControlled()) {
					dst.println("    <controlled id=\"" + v.toString() + "\">" + value + "</controlled>");
				} else if (v.isMonitored()) {
					dst.println("    <monitored id=\"" + v.toString() + "\">" + value + "</monitored>");

				} else
					dst.println("ERRORE" + v.toString());
			}
			dst.println("  </state>");
		}
		dst.println("</test>");
		dst.close();
	}
}
