package atgt.testseqexport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import atgt.coverage.AsmTestSequence;
import atgt.specification.location.Location;
import atgt.specification.location.Variable;

public class toXmlStax2 extends TestSeqTrad {
	
	static String scenarioDTD;

	public toXmlStax2(File f, AsmTestSequence ts) throws FileNotFoundException {
		super(f, ts);
		scenarioDTD = "<!DOCTYPE test SYSTEM \"" + 
			new File("src/atgt/gui/tree/DTD_atgt.dtd").getAbsoluteFile().toString() +
			"\" >";
	}
	
	
	@Override
	public void saveToStream(){
		Iterator<Map<Location, String>> it = testSequence.allInstructions().iterator();
		try {			
			XMLOutputFactory factory = XMLOutputFactory.newInstance();
			XMLStreamWriter writer = factory.createXMLStreamWriter(out);

			/*Inizia la scrittura del docmento*/
			writer.writeStartDocument("UTF-8", "1.0");
			writer.writeDTD(scenarioDTD);
			writer.writeStartElement("test");
			/*Scorro la lista*/
			while(it.hasNext()){
				Map<Location, String> state = it.next();
				writer.writeStartElement("state");
				/*Scorro la mappa*/
				for( Entry<Location, String> p: state.entrySet()){
					Variable v = (Variable) p.getKey();
					String value = p.getValue();
					String varKind = null;

					if(v.isControlled()){
						varKind = "controlled";					
					}else if(v.isMonitored()){
						varKind = "monitored";
					}else varKind = "ERRORE";  //TODO scegliere nome stringa
					/*Dopo aver controllato il tipo di varibile inserisco il valore
					 * di id col metodo writeAttribute, col metodo writecharacters
					 * scrivo tra i due tag*/
					writer.writeStartElement(varKind);
					writer.writeAttribute("id", v.toString());
					writer.writeCharacters(value);
					writer.writeEndElement();
				}
				/*Chiudi state*/
				writer.writeEndElement();
			}
			/*Chiudi test, poi chiudi il writer*/
			writer.writeEndElement();
			writer.writeEndDocument();
			writer.flush();
			writer.close();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
