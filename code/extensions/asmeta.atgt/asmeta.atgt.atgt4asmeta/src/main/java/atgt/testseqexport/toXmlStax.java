package atgt.testseqexport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import atgt.coverage.AsmTestSequence;
import atgt.specification.location.Location;
import atgt.specification.location.Variable;

public class toXmlStax extends TestSeqTrad {
	
	static String scenarioDTD;

	public toXmlStax(File f, AsmTestSequence ts) {
		super(f, ts);
		scenarioDTD = "<!DOCTYPE test SYSTEM \"" + 
			new File("src/atgt/gui/tree/DTD_atgt.dtd").getAbsoluteFile().toString() +
			"\" >";
	}
	
	
	@Override
	public void saveToStream(){
		Iterator<Map <Location, String>> it = testSequence.allInstructions().iterator();

		// Create a XMLOutputFactory
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		// Create XMLEventWriter
		XMLEventWriter eventWriter;
		try {
			/*Con questa istruzione possiamo collegare l'eventwriter all'output
			 * per poter effettivamente scrivere su un file*/
			eventWriter = factory.createXMLEventWriter(out);
			XMLEventFactory eventFactory = XMLEventFactory.newInstance();
			
			XMLEvent dtd = eventFactory.createDTD(scenarioDTD);
			XMLEvent end = eventFactory.createDTD("\n");
	        XMLEvent tab = eventFactory.createDTD("  ");

			// Create and write Start Tag
			StartDocument startDocument = eventFactory.createStartDocument();
			eventWriter.add(startDocument); 
			eventWriter.add(end);
			eventWriter.add(dtd);
			eventWriter.add(end);

			StartElement sElement = eventFactory.createStartElement("", "", "test");
			eventWriter.add(sElement);
			eventWriter.add(end);
			EndElement eElement = null;
			while(it.hasNext()){
				Map<Location, String> state = it.next();
				sElement = eventFactory.createStartElement("", "", "state");
				eventWriter.add(tab);
				eventWriter.add(sElement);
				eventWriter.add(end);
							
				for( Entry<Location, String> p: state.entrySet()){
					Variable v = (Variable) p.getKey();
					String value = p.getValue();
					String varKind = null;
					if(v.isControlled()){
						varKind = "controlled";					
					}else if(v.isMonitored()){
						varKind = "monitored";
					}else varKind = "ERRORE";  //TODO scegliere nome stringa
					Vector <Attribute> attr = new Vector<Attribute>();
					attr.add(eventFactory.createAttribute("id", v.toString()));
					sElement = eventFactory.createStartElement("", "", varKind, attr.iterator(), null);
					eventWriter.add(tab);
					eventWriter.add(tab);
					eventWriter.add(sElement);
					Characters characters = eventFactory.createCharacters(value);
					eventWriter.add(characters);
					eElement = eventFactory.createEndElement("", "", varKind);
					eventWriter.add(eElement);
					eventWriter.add(end);
				}
				
				eElement = eventFactory.createEndElement("", "", "state");
				eventWriter.add(tab);
				eventWriter.add(eElement);
				eventWriter.add(end);
			}
			eElement = eventFactory.createEndElement("", "", "test");
			eventWriter.add(eElement);
			eventWriter.close();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
