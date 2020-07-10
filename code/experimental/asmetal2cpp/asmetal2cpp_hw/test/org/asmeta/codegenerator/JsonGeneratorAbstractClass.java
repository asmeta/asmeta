package org.asmeta.codegenerator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.text.DateFormatter;

import org.asmeta.codegenerator.arduino.ArduinoVersion;
import org.asmeta.parser.ASMParser;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.junit4.util.ParseHelper;

import com.google.inject.Inject;

import asmeta.AsmCollection;
import asmeta.structure.Asm;

public abstract class JsonGeneratorAbstractClass {

	@Inject
	private ParseHelper<asmeta.structure.Asm> parseHelper;
	
	private DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
	
	void testGenerator(String uasmFilePath, boolean export, boolean folder) throws IOException, Exception {
		File uasmFile= new File(uasmFilePath);
		
		if(!uasmFile.exists())
			throw new FileNotFoundException("File "+uasmFilePath+" not found");
		
		try {		
			//final Asm model = parseAsm(uasmFilePath);
			//final Asm model = ASMParser.setUpReadAsm(uasmFile).getMain();
			AsmCollection model = ASMParser.setUpReadAsm(uasmFile);
			//Resource res = model.eResource();
			JsonGenerator jsonGen = new JsonGenerator(ArduinoVersion.MEGA2560);
			//jsonGen.getFolderPath(model,uasmFile.getPath());
			jsonGen.getFolderPath(model.getMain(),uasmFile.getPath());
			//System.out.println(jsonGen.compile(model,true));
			//Check if the U2C file has to be exported
			if (export) {
				String filePath = uasmFile.getPath().replace(".asm", ".u2c");
				int index = filePath.lastIndexOf("\\") + 1;
				filePath = filePath.substring(0, index) + dateFormat.format(new Date()) + "_" + filePath.substring(index, filePath.length());
				System.out.println(filePath);
				//jsonGen.generate(model, filePath, folder);
				jsonGen.generate(model, filePath, true);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	void testGenerator(String uasmFilePath, boolean export) throws IOException, Exception {
		testGenerator(uasmFilePath,export,false);
	}
	
	
	
	
	protected Asm parseAsm(String specName) throws IOException, Exception {
		//
		// PARSE THE SPECIFICATION (UASM)
		//
		CharSequence fromFile = fromFile(specName);
		final Asm model = this.parseHelper.parse(fromFile);
		Resource _eResource = model.eResource();

		for (int i = 0; i < _eResource.getErrors().size(); i++)
			System.out.println("Error: line " + _eResource.getErrors().get(i).getLine() + " - "
					+ _eResource.getErrors().get(i).getMessage());

		return model;
	}

	public static CharSequence fromFile(String filename) throws IOException {
		FileInputStream fis = new FileInputStream(filename);
		FileChannel fc = fis.getChannel();

		// Create a read-only CharBuffer on the file
		ByteBuffer bbuf = fc.map(FileChannel.MapMode.READ_ONLY, 0, (int) fc.size());
		CharBuffer cbuf = Charset.forName("8859_1").newDecoder().decode(bbuf);
		return cbuf;
	}
}
