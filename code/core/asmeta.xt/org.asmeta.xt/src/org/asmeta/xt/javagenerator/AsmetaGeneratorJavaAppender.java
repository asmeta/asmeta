package org.asmeta.xt.javagenerator;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.emf.mwe2.runtime.workflow.IWorkflowContext;
import org.eclipse.xtext.generator.GeneratorComponent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AsmetaGeneratorJavaAppender extends GeneratorComponent {
	@Override
	public void preInvoke() {

	}

	@Override
	public void invoke(IWorkflowContext ctx) {

	}
	// read all the 
	private File[] readAllExt() {
		File extDir = new File("ext");
		assert extDir.exists() && extDir.isDirectory();
		return extDir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".ext");
			}
		});				
	}
	
	private Optional<File> findJavaFile(String name) {
		File srcgen = new File("src-gen");
		return findJavaFile(name, srcgen);
	}

	private Optional<File> findJavaFile(String name, File dir) {
		assert (dir.isDirectory());
		for (File f: dir.listFiles()) {
			if (f.isDirectory()) {
				Optional<File> partial = findJavaFile(name,f);
				if (partial.isPresent()) return partial;
			}
			if (f.getName().contentEquals(name)) return Optional.of(f);
		}
		return  Optional.empty();
	}
	
	private void processExt() throws IOException {
		File[] extFiles = readAllExt();
		for(File f: extFiles) {
			String javaFileName = f.getName().replaceFirst(".ext", ".java");
			Optional<File> jfile = findJavaFile(javaFileName);
			if (!jfile.isPresent())
				System.out.println("Java file not found for the ext file " + f.toString());
			else
				appendExtTojavaFile(f.toString(),jfile.get().toString());
		}
	}
	
	
	@Override
	public void postInvoke() {
		System.out.println("********\nADDING CUSTOM METHODS\n********");
		try {
			processExt();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void usingXML() {
		try {

			File fXmlFile = new File("ext/ExternalFunctions.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			String extFileName = "";
			String classFileName = "";

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("Function");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
				extFileName = "";
				classFileName = "";
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					extFileName = eElement.getElementsByTagName("FileName").item(0).getTextContent();
					classFileName = eElement.getElementsByTagName("ClassFile").item(0).getTextContent();

					try {

						appendExtTojavaFile(extFileName, classFileName);

					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void appendExtTojavaFile(String extFileName, String classFileName) throws IOException {
		System.out.println("appending " + extFileName + " to " + classFileName);
		List<String> listClassFile = Files.readAllLines(Paths.get(classFileName));
		List<String> listExtFile = Files.readAllLines(Paths.get(extFileName));
		for (String s : listExtFile) {
			listClassFile.add(listClassFile.size() - 1, s);
		}
		Files.write(Paths.get(classFileName), listClassFile);
	}

	public static void main(String[] args) throws IOException {
		AsmetaGeneratorJavaAppender a = new AsmetaGeneratorJavaAppender();
		//a.postInvoke();
		a.processExt();
	}
}
