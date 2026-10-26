package atgt.testseqexport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Map;

import org.xml.sax.ContentHandler;
import org.xml.sax.helpers.AttributesImpl;

//import com.sun.org.apache.xml.internal.serialize.OutputFormat;
//import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

import atgt.coverage.AsmTestSequence;
import atgt.specification.location.Location;
import atgt.specification.location.Variable;

public class toXmlSAX extends TestSeqTrad {

	static String scenarioDTD;
	
	public toXmlSAX(File f, AsmTestSequence ts) throws FileNotFoundException {
		super(f, ts);
		scenarioDTD = new File("src/atgt/gui/tree/DTD_atgt.dtd").getAbsoluteFile().toString();
	}
	
	@Override
	public void saveToStream(){
//		try {
//			// SEE DOM
//			/*Creazione iteratore sulla lista di map*/
//			Iterator<Map <Location, String>> it = testSequence.allInstructions().iterator();
//			/*Impostazione output format*/
//			OutputFormat of = new OutputFormat("XML", "ISO-8859-1", true);
//			of.setIndent(1);
//			of.setIndenting(true);
//			of.setDoctype(null, scenarioDTD);
//			
//			XMLSerializer serializer = new XMLSerializer(out, of);
//			ContentHandler hd = serializer.asContentHandler();
//			hd.startDocument();
//			/*Creazione elemento attributi*/
//			AttributesImpl atts = new AttributesImpl();
//			/*Apertura primo tag*/
//			hd.startElement("", "", "test", atts);
//			//Scorro la mappa
//			while(it.hasNext()){
//				Map<Location, String> state = it.next();
//				atts.clear();
//				hd.startElement("", "", "state", atts);
//				for( Map.Entry<Location, String> p: state.entrySet()){
//					Location v = p.getKey();
//					String value = p.getValue();
//					String varKind = null;
//					/*Clear di atts per eliminare gli attributi assegnati nel ciclo precedente*/
//					atts.clear();
//					/*Modifica dell'elemento che contiene gli attributi per inserire l'attributo
//					 * id all'interno del XML*/
//					atts.addAttribute("", "", "id", "CDATA", v.toString());					
//					if(v.isControlled()){
//						varKind = "controlled";					
//					}else if(v.isMonitored()){
//						varKind = "monitored";
//					}else varKind = "ERRORE";  //TODO scegliere nome stringa
//					/*Apertura elemento controlled o monitored in base all'esito dell'if*/
//					hd.startElement("", "", varKind, atts);
//					/*Scrittura testo tra il tag aperto e la sua chiusura*/
//					hd.characters(value.toCharArray(), 0, value.length());
//					/*Chiusura del tag*/
//					hd.endElement("", "", varKind);
//				}
//				hd.endElement("", "", "state");
//			}
//			/*Chiusura tag test e chiusura FileOutputStream*/
//			hd.endElement("", "", "test");
//			hd.endDocument();
//			out.close();
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
	}

}
