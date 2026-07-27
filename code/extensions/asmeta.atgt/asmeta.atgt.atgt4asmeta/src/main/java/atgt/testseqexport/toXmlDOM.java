package atgt.testseqexport;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

//import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
//import com.sun.org.apache.xml.internal.serialize.OutputFormat;
//import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import atgt.coverage.AsmTestSequence;
import atgt.specification.location.Location;
import atgt.specification.location.Variable;

public class toXmlDOM extends TestSeqTrad {

	static String scenarioDTD;

	public toXmlDOM(File f, AsmTestSequence ts) {
		super(f, ts);
		scenarioDTD = new File("src/atgt/gui/tree/DTD_atgt.dtd").getAbsoluteFile().toString();
	}

	@Override
	public void saveToStream() {

		Iterator<Map<Location, String>> it = testSequence.allInstructions().iterator();
		Element e = null;
		Element e1 = null;
		Node n = null;

		DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

		DocumentBuilder documentBuilder;
		try {
			documentBuilder = documentFactory.newDocumentBuilder();

			Document xmlDoc = documentBuilder.newDocument();

			Element root = xmlDoc.createElement("test");
			// Scorro la mappa
			while (it.hasNext()) {
				Map<Location, String> state = it.next();
				e = xmlDoc.createElementNS(null, "state");

				for (Entry<Location, String> p : state.entrySet()) {
					Variable v = (Variable) p.getKey();
					String value = p.getValue();
					String varKind = null;

					if (v.isControlled()) {
						varKind = "controlled";
					} else if (v.isMonitored()) {
						varKind = "monitored";
					} else
						varKind = "ERRORE"; // TODO scegliere nome stringa

					e1 = xmlDoc.createElementNS(null, varKind);
					e1.setAttributeNS(null, "id", v.toString());
					n = xmlDoc.createTextNode(value);
					e1.appendChild(n);
					e.appendChild(e1);
				}
				root.appendChild(e);
			}
			xmlDoc.appendChild(root);
			//
			// salvataggio su file
			//
			// create the xml file
			// transform the DOM Object to an XML File
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(xmlDoc);
			StreamResult streamResult = new StreamResult(out);

			// If you use
			// StreamResult result = new StreamResult(System.out);
			// the output will be pushed to the standard output ...
			// You can use that for debugging

			transformer.transform(domSource, streamResult);

			/*
			 * OutputFormat of = new OutputFormat("XML", "ISO-8859-1", true);
			 * of.setIndent(1); of.setIndenting(true); of.setDoctype(null, scenarioDTD);
			 * XMLSerializer serializer = new XMLSerializer(out, of);
			 * serializer.asDOMSerializer();
			 * serializer.serialize(xmlDoc.getDocumentElement()); out.close();
			 */
		} catch (TransformerConfigurationException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (TransformerException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (ParserConfigurationException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}


	}

}
