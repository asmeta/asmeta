package asm2code_inoproject.handlers;

import java.awt.Desktop;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import org.asmeta.asm2code.main.AsmToCGenerator;
import org.asmeta.codegenerator.InoGenerator;
import org.asmeta.codegenerator.JsonGenerator;
import org.asmeta.codegenerator.arduino.ArduinoVersion;
import org.asmeta.codegenerator.configuration.HWConfiguration;
import org.asmeta.parser.ASMParser;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PlatformUI;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import asm2code_inoproject.asm2code;
import asmeta.structure.Asm;

import org.eclipse.ui.part.FileEditorInput;

public class GeneratorHandler extends AbstractHandler implements IHandler {
	private asm2code uasm2code;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// Set look and feel according to the system
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		IEditorInput input = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()
				.getEditorInput();
		// Geth path from opened ASM file
		File asmFile = new File(((FileEditorInput) input).getPath().toPortableString());
		assert asmFile.exists();
		// Parse the ASM file
		Asm model = null;
		try {
			model = ASMParser.setUpReadAsm(asmFile).getMain();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		// If U2C file doesn't exist create it
		File u2cFile = new File(
				asmFile.getPath().substring(0, asmFile.getPath().lastIndexOf(AsmToCGenerator.Ext)) + JsonGenerator.Ext);
		if (!u2cFile.exists()) {
			System.out.println("U2C file not found: I automatically generate it and save it in the asm folder.");
			ArduinoVersion av = showSelectArduinoVersionDialog();
			JsonGenerator jsonGen = new JsonGenerator(av);
			jsonGen.generate(model, u2cFile.getPath());
		}
		// Show the folder chooser dialog
		DirectoryDialog dialog = new DirectoryDialog(new Shell());
		dialog.setText("Set the Arduino project folder");
		dialog.setMessage("Select the Arduino project folder where you want to save the model conversion. "
				+ "If the folder is a valid Arduino folder, after the conversion I will compile it."
				+ "Otherwise I will skip the compile step");
		dialog.setFilterPath(ResourcesPlugin.getWorkspace().getRoot().getLocation().toString());
		String path = dialog.open();
		String destName = "";
		// If destination folder hasn't been selected I use the asm file folder
		if (path != null) {
			path = path.replace("\\\\", "\\").replace("\\", "/") + "/";
			destName = asmFile.getName().replace(AsmToCGenerator.Ext, "");

		} else {
			path = asmFile.getParent().replace("\\\\", "\\").replace("\\", "/") + "/";
			destName = asmFile.getName().replace(AsmToCGenerator.Ext, "");
		}
		// Perform the u2cFile reading and asm conversion in Arduino project files
		HWConfiguration config = readJSON(u2cFile.getPath());
		uasm2code = new asm2code(config);
		// Check if .ino file already exists, in case I ask to proceed or cancel the
		// operation
		if (Files.exists(Paths.get(path + destName + InoGenerator.Ext))) {
			int r = JOptionPane.showOptionDialog(null,
					"I found a .ino file in the directory. I will overwrite it with the generated one. Do you want to proceed?",
					"Overwriting .ino file", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					new String[] { "Yes", "No" }, "Yes");
			if (r != 0) {
				System.out.println("Operation canceled!");
				return null;
			}
		}
		uasm2code.generateAll(model, path, destName);
		// compile the arduino prj with make
		// TODO:

		// operation completed
		JOptionPane.showOptionDialog(null,
				"The operation is completed. Check out the folder you selected to see the generated files.",
				"Operation Completed", JOptionPane.YES_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
				new String[] { "Ok" }, "Ok");

		return null;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public static HWConfiguration readJSON(String jsonPath) {
		HWConfiguration config;
		Gson gson = new Gson();
		JsonReader reader;
		try {
			reader = new JsonReader(new FileReader(jsonPath));
			config = gson.fromJson(reader, HWConfiguration.class);
		} catch (JsonIOException e) {
			e.printStackTrace();
			throw new RuntimeException("Problem reading JSON file: " + jsonPath);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw new RuntimeException("Problem with sintax of JSON file");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("JSON file not found! " + jsonPath);
		}
		return config;
	}

	public ArduinoVersion showSelectArduinoVersionDialog() {
		JDialog dialog = new JDialog();
		Point p = new Point(400, 400);
		dialog.setLocation(p.x, p.y);

		final JComboBox<ArduinoVersion> versions = new JComboBox<>(ArduinoVersion.values());
		versions.setSelectedItem(ArduinoVersion.MEGA2560);
		String[] options = { "OK", "Cancel", "Help" };
		int selection = JOptionPane.showOptionDialog(null, versions, "Set Arduino Version", JOptionPane.PLAIN_MESSAGE,
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

		switch (selection) {
		case 0: // OK
			System.out.println("OK: " + versions.getSelectedItem());
			if (versions.getSelectedItem() != null)
				return ArduinoVersion.fromString(versions.getSelectedItem().toString());
			return null;
		case 1: // Cancel
			System.out.println("Operation canceled");
			return null;
		case 2: // Help
			System.out.println("Help, opening https://www.arduino.cc/en/products/compare");
			try {
				if (Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
					Desktop.getDesktop().browse(new URI("https://www.arduino.cc/en/products/compare"));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		default:
			return null;
		}
	}

}
