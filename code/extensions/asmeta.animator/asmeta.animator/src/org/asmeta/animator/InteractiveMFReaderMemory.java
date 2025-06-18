package org.asmeta.animator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.asmeta.animator.dialog.DialogGenerator;
import org.asmeta.animator.dialog.AskMonDialog;
import org.asmeta.animator.dialog.MyDialogError;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.State;
import org.asmeta.simulator.readers.InteractiveMFReader;
import org.asmeta.simulator.util.InputMismatchException;
import org.asmeta.simulator.util.Parser;
import org.asmeta.simulator.value.EnumValue;
import org.asmeta.simulator.value.ReserveValue;
import org.asmeta.simulator.value.Value;
import org.eclipse.swt.widgets.Shell;
import asmeta.definitions.Function;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.EnumTd;

public class InteractiveMFReaderMemory extends InteractiveMFReader {

	private static VisualizationSimulation v;

	public InteractiveMFReaderMemory(InputStream in, PrintStream out, VisualizationSimulation v) {
		super(getIS(v.getLastMonitoredInteractiveValue()), out);
		InteractiveMFReaderMemory.v = v;
	}

	static BufferedReader getIS(String in) {
		Reader inputString = new StringReader("");
		return new BufferedReader(inputString) {
			@Override
			public String readLine() throws IOException {
				String text = v.getLastMonitoredInteractiveValue();
				return (text);
			}
		};
	} 

	public Map<Location, Value> values = new HashMap<Location, Value>();
 
	@Override
	public Value readValue(Location location, State state) {
		
		Value value = null;
		Function func = location.getSignature();
		// build the right dialog
		DialogGenerator dg = new DialogGenerator(v.getShell(), domainPrinter.visit(func.getCodomain()), location.toString());
		AskMonDialog dialog = dg.doSwitch(func.getCodomain());		

		while (value == null) {
			String input = dialog.open();			
			if (input != null) {
				// User clicked OK; set the text into the label
				v.showMonitoredInteractiveSimulation(input, location.toString());
				value = visit(func.getCodomain());
			} 
			// TODO: se voglio terminare non riesco pi� --> far s� che si possa interrompere
			// l'esecuzione
		}
		return value;
	}

/*	private ArrayList<String> extractEnumValues(String domainPrinterLocal) {
		ArrayList<String> listElements = new ArrayList<>();
		String elements = domainPrinterLocal.substring(domainPrinterLocal.indexOf("[")+1, domainPrinterLocal.indexOf("]"));
		//System.out.println(elements);
		while (elements.contains(",")==true) {
		//	System.out.println(elements.substring(0, elements.indexOf(",")));
			listElements.add(elements.substring(0, elements.indexOf(",")));
			elements=elements.substring(elements.indexOf(",")+2,elements.length());
		//	System.out.println(elements);
		}
		listElements.add(elements);
		return listElements;
	}*/

	@Override
	public void readLine() {
		setLine(v.getLastMonitoredInteractiveValue());
	}

	@Override
	public ReserveValue visit(AbstractTd domain) {
		ReserveValue value = null;
		for (int i = 0; i == 0; i++) {
			try {
				readLine();
				value = new Parser(getLine()).visit(domain);
			} catch (InputMismatchException e) {
				getOut().println(e.getMessage());
				String error = e.getMessage();
				MyDialogError err = new MyDialogError(v.getShell(), error);
				err.open();
				continue;
			}
			if (!checkAbstract(domain, value)) {
				/*v.setTextError(
						"The constant " + value.getValue() + " doesn't belong to abstract domain " + domain.getName());*/
				//dialog.setLabelErrorText("The constant " + value.getValue() + " doesn't belong to abstract domain " + domain.getName());
				String error = "The constant " + value.getValue() + " doesn't belong to abstract domain " + domain.getName();
				MyDialogError err = new MyDialogError(v.getShell(), error);
				err.open();
				value = null;
				continue;
			}
			break;
		}
		return value;
	}

	@Override
	public EnumValue visit(EnumTd domain) {
		EnumValue value = null;
		for (int i = 0; i == 0; i++) { 
			try {
				readLine();
				value = new Parser(getLine()).visit(domain);
			} catch (InputMismatchException e) {
				getOut().println(e.getMessage());
				String error = e.getMessage();
				MyDialogError err = new MyDialogError(v.getShell(), error);
				err.open();
				continue;
			}
			if (!checkEnum(domain, value)) {
				/*v.setTextError(
						"The constant " + value.getValue() + " doesn't belong to enum domain " + domain.getName());*/
				String error = "The constant " + value.getValue() + " doesn't belong to enum domain " + domain.getName();
				MyDialogError err = new MyDialogError(v.getShell(), error);
				err.open();
				value = null;
				continue;
			}
			break;
		}
		return value;
	}

	@Override
	public Value visit(ConcreteDomain domain) {
		Value value = null;
		for (int i = 0; i == 0; i++) {
			try {
				readLine();
				value = new Parser(getLine()).visit(domain);
			} catch (InputMismatchException e) {
				getOut().println(e.getMessage());
				String error = e.getMessage();
				MyDialogError err = new MyDialogError(v.getShell(), error);
				err.open();
				continue;
			}
			if (!checkConcrete(domain, value)) {
				/*v.setTextError("The constant " + value + " doesn't belong to concrete domain " + domain.getName());*/
				String error ="The constant " + value + " doesn't belong to concrete domain " + domain.getName();
				MyDialogError err = new MyDialogError(v.getShell(), error);
				err.open();
				value = null;
				continue;
			}
			break;
		}
		return value;
	}

}
