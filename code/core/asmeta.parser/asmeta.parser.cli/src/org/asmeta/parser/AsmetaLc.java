package org.asmeta.parser;

import java.io.File;
import java.io.IOException;

import org.asmeta.parser.util.AsmXMIVisitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.Option;

import asmeta.AsmCollection;
import asmeta.cli.AsmetaCLI;
import asmeta.structure.Asm;

public class AsmetaLc extends AsmetaCLI {

	@Option(name = "-xmi", usage = "save as xmi")
	public boolean saveXMI;

	@Option(name = "-asmetaL", usage = "save as asmetaL")
	public boolean saveAsmetaL;

	/**
	 * Entry point TO DO: impostare l'opzione -log come da main nel simulatore,
	 * usando un file log4j.properties invece della classe ParserResultLogger.
	 * 
	 */
	public static void main(String[] args) {
		AsmetaLc asmetalC = new AsmetaLc();
		asmetalC.run(args);
	}

	@Override
	protected void runWith(File f) throws CmdLineException {
		ASMFileFilter filter = new ASMFileFilter();
		if (filter.accept(f)) {
			AsmCollection allAsms;
			try {
				allAsms = ASMParser.setUpReadAsm(f);

				if (this.saveXMI) {
					System.out.println("Saving in XMI " + f.getCanonicalPath()
							+ "...");

					// Asm asm = allAsms.getMain();

					// Creazione di un resource set
					ResourceSet resourceSet = new ResourceSetImpl();
					// Inizializzazione del resource set.
					resourceSet
							.getResourceFactoryRegistry()
							.getExtensionToFactoryMap()
							.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
									new XMIResourceFactoryImpl());
					// Viene creato l'URI per il file.
					int i = f.getPath().lastIndexOf(".asm");
					// String s = new String (f.getPath().substring(0,i));
					String s = f.getPath().substring(0, i);
					URI fileURI = URI.createFileURI(new File(s + ".xmi")
							.getCanonicalPath());

					// Creazione di una resource a cui andranno associati gli
					// oggetti da serializzare
					Resource resource = resourceSet.createResource(fileURI);

					// Gli oggetti da serializzare vengono associati alla
					// resource
					// resource.getContents().add(asm);
					AsmXMIVisitor visitor = new AsmXMIVisitor();
					for (Asm m : allAsms) {
						// resource.getContents().add(m);
						visitor.visit(m, resource.getContents());
					}
					// Il file con gli oggetti serializzati vengono salvati nel
					// workspace del progetto
					// resource.save(Collections.EMPTY_MAP);
					resource.save(null);
					System.out.println("DONE.");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (this.saveAsmetaL) {

			// Create a resource set
			ResourceSet resourceSet = new ResourceSetImpl();
			// Viene creato l'URI per il file.
			URI fileURI;
			try {
				// Get the URI of the model file.
				fileURI = URI.createURI(f.getCanonicalPath());
				resourceSet
						.getResourceFactoryRegistry()
						.getExtensionToFactoryMap()
						.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
								new XMIResourceFactoryImpl());

				// Demand load the resource for this file.
				Resource resource = resourceSet.getResource(fileURI, true);
				// Print the contents of the resource to System.out.
				// resource.save(System.out, Collections.EMPTY_MAP);
			}

			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			throw new CmdLineException("Error:  " + f.toString()
					+ " is not an asm file.");
		}
	}
}
