package atgt.testseqexport;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import atgt.coverage.AsmTestSequence;
import atgt.specification.location.Location;
import atgt.specification.location.Variable;

public class toXmlSerial extends TestSeqTrad {
	
	/*Variabile che conterrà l'indirizzo del dtd*/
	static String scenarioDTD;
	
	public toXmlSerial(File f, AsmTestSequence ts){
		super(f, ts);
		scenarioDTD = new File("src/atgt/gui/tree/DTD_atgt.dtd").getAbsoluteFile().toString();
	}
	
	
	/*metodo che salva il file in xml*/
	@Override
	public void saveToStream(){
			PrintStream dst = new PrintStream(out);
			Iterator<Map <Location, String>> it = testSequence.allInstructions().iterator();
			/*Intestazione del file xml con collegamento al dtd*/
			dst.println("<?xml version=" + '"' + "1.0" + '"' + " encoding=" + '"' + "ISO-8859-1" 
					+ '"' + "?>");
			dst.println("<!DOCTYPE DTD_atgt SYSTEM " + '"' + scenarioDTD + '"' + ">");
			dst.println("<test>");
			
			/*Scorrimento della lista contenete gli stati*/
			while(it.hasNext()){
				Map<Location, String> state = it.next();
				dst.println("  <state>");
				/*Scorrimento della lista contenete le variabili che si modificano nel cambio di stato*/
				for( Entry<Location, String> p: state.entrySet()){
					Variable v = (Variable) p.getKey();
					String value = p.getValue();
					
					/*Verifica del tipo della variabile per poter definire il giusto tag*/
					if(v.isControlled()){
						dst.println("    <controlled id=\"" + v.toString() + "\">" 
								+ value + "</controlled>");
					}else if(v.isMonitored()){
						dst.println("    <monitored id=\"" + v.toString() + "\">" 
								+ value + "</monitored>");

					}else dst.println("ERRORE" + v.toString());
				}
				/*chiusura tag*/
				dst.println("  </state>");
			}
			/*chiusura tag e PrintStream*/
			dst.println("</test>");
			dst.close();
	}
}