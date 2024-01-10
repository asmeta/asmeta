package asmeta.fmvclib.model;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.asmeta.parser.ASMParser;
import org.asmeta.parser.util.ReflectiveVisitor;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.Environment.TimeMngt;
import org.asmeta.simulator.InvalidInvariantException;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.State;
import org.asmeta.simulator.UpdateSet;
import org.asmeta.simulator.main.AsmetaSimulatorWR;
import org.asmeta.simulator.main.Simulator;
import org.asmeta.simulator.value.BooleanValue;
import org.asmeta.simulator.value.CharValue;
import org.asmeta.simulator.value.EnumValue;
import org.asmeta.simulator.value.IntegerValue;
import org.asmeta.simulator.value.RealValue;
import org.asmeta.simulator.value.ReserveValue;
import org.asmeta.simulator.value.StringValue;
import org.asmeta.simulator.value.Value;

import asmeta.AsmCollection;
import asmeta.definitions.Function;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.IntegerDomain;
import asmeta.fmvclib.annotations.AsmetaMonitoredLocation;
import asmeta.fmvclib.annotations.AsmetaMonitoredLocations;
import asmeta.fmvclib.annotations.LocationType;
import asmeta.fmvclib.controller.ButtonColumn;
import asmeta.structure.FunctionInitialization;
import asmeta.structure.Initialization;

@SuppressWarnings("deprecation")
public class AsmetaFMVCModel extends Observable {

	/**
	 * The ASMETA simulator
	 */
	//private Simulator sim;
	private AsmetaSimulatorWR sim;

	/**
	 * The reader
	 */
	private ViewReader reader;

	/**
	 * The ASM environment
	 */
	private Environment environment;

	/**
	 * The path of the ASM model
	 */
	public static String ASM_PATH;

	/**
	 * The assignments for all the controlled locations
	 */
	private HashMap<String, List<Entry<String, String>>> controlledAssignments;

	/**
	 * Creates the AsmetaFMVCModel instance for a given ASMETA model
	 * 
	 * @param asmPath the path of the ASMETA model
	 * @throws Exception
	 */
	public AsmetaFMVCModel(String asmPath) throws Exception {
		ASM_PATH = new File(asmPath).getParent();
		reader = new ViewReader();
		environment = new Environment(reader);
		//sim = Simulator.createSimulator(asmPath, environment);
		sim = AsmetaSimulatorWR.createSimulator(asmPath, environment);
		Environment.timeMngt = TimeMngt.use_java_time;
		controlledAssignments = new HashMap<String, List<Entry<String, String>>>();
	}

	/**
	 * Compute the value (as a String) of a location in the current state
	 * 
	 * @param locationName the name of the location
	 * @param keyType      the type of the value
	 */
	@SuppressWarnings({ "rawtypes" })
	public void computeValue(String locationName, LocationType keyType) {
		State s = sim.getCurrentState();
		Boolean assigned = false;
		SortedMap<String, String> resultStr = new TreeMap<>();
		SortedMap<Long, String> resultInt = new TreeMap<>();
		SortedMap<Float, String> resultFloat = new TreeMap<>();
		SortedMap<Boolean, String> resultBoolean = new TreeMap<>();

		for (Entry<Location, Value> x : s.getContrLocs(true).entrySet()) {
			if (x.getKey().getSignature().getName().equals(locationName)) {
				if (keyType == LocationType.UNDEF || keyType == LocationType.STRING || keyType == LocationType.ENUM
						|| keyType == LocationType.CHAR)
					resultStr.put(Arrays.toString(x.getKey().getElements()), x.getValue().toString());
				else if (keyType == LocationType.INTEGER)
					resultInt.put(
							(x.getKey().getElements().length > 0 ? (Long) (x.getKey().getElements()[0].getValue()) : 0),
							x.getValue().toString());
				else if (keyType == LocationType.REAL)
					resultFloat
							.put((x.getKey().getElements().length > 0 ? (Float) (x.getKey().getElements()[0].getValue())
									: 0), x.getValue().toString());
				else if (keyType == LocationType.BOOLEAN)
					resultBoolean.put(
							(x.getKey().getElements().length > 0 ? (Boolean) (x.getKey().getElements()[0].getValue())
									: true),
							x.getValue().toString());
				assigned = true;
			}
		}

		// Keep the right map
		String strResult = "";
		if (keyType == LocationType.UNDEF || keyType == LocationType.STRING || keyType == LocationType.ENUM
				|| keyType == LocationType.CHAR)
			strResult = resultStr.toString().replace("{", "").replace("}", "");
		else if (keyType == LocationType.INTEGER)
			strResult = resultInt.toString().replace("{", "").replace("}", "");
		else if (keyType == LocationType.REAL)
			strResult = resultFloat.toString().replace("{", "").replace("}", "");
		else if (keyType == LocationType.BOOLEAN)
			strResult = resultBoolean.toString().replace("{", "").replace("}", "");

		// Store the updated result
		if (assigned)
			updateCurrentAssignments(locationName, strResult);
	}

	/**
	 * Updates the current assignments
	 * 
	 * @param locationName the name of the location
	 * @param strResult    the string containing the assignment
	 */
	public void updateCurrentAssignments(String locationName, String strResult) {
		if (controlledAssignments.get(locationName) == null) {
			throw new RuntimeException("This should never happen");
		} else {
			if (controlledAssignments.get(locationName).size() > 1)
				updateNAryFunction(locationName, strResult);
			else
				update0AryFunction(locationName, strResult);
		}
	}

	/**
	 * Updates a N-ary function
	 * 
	 * @param locationName the name of the location
	 * @param strResult    the string containing the assignment
	 */
	public void updateNAryFunction(String locationName, String strResult) {
		for (String str : strResult.split(",")) {
			ArrayList<Entry<String, String>> locationValues = ((ArrayList<Entry<String, String>>) controlledAssignments
					.get(locationName));
			for (Entry<String, String> value : locationValues) {
				if (value.getKey().equals(locationName + "_" + str.split("=")[0].trim())) {
					value.setValue(str.split("=")[1]);
				}
			}
		}
	}

	/**
	 * Updates a 0-ary function
	 * 
	 * @param locationName the name of the location
	 * @param strResult    the string containing the assignment
	 */
	private void update0AryFunction(String locationName, String strResult) {
		if (strResult.contains("="))
			((ArrayList<Entry<String, String>>) controlledAssignments.get(locationName)).set(0,
					new MutablePair<String, String>(locationName, strResult.split("=")[1]));
		else
			((ArrayList<Entry<String, String>>) controlledAssignments.get(locationName)).set(0,
					new MutablePair<String, String>(locationName, strResult));
	}

	/**
	 * Runs the simulator for a single step
	 * 
	 * @return
	 */
	public UpdateSet runSimulator() {
		return this.runSimulator(1);
	}

	/**
	 * Runs the simulator for nStep steps
	 * 
	 * @param nStep the number of steps
	 * @return
	 */
	public UpdateSet runSimulator(int nStep) {
		UpdateSet updateSet = null;
		try {
			updateSet = sim.run(nStep);
			setChanged();
			notifyObservers();
		} catch (InvalidInvariantException e) {
			System.err.println("Invariant violation - rolling back");
			assert nStep == 1;
			sim.rollBack();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return updateSet;
	}

	/**
	 * Updates the monitored location using annotations
	 * 
	 * @param obj    the annotated obj
	 * @param source the source object
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public void updateMonitored(Object obj, Object source) throws IllegalArgumentException, IllegalAccessException {
		reader.locationMemory.clear();
		analyzeSingleAnnotations(obj, source);
		analyzeMultipleAnnotations(obj, source);
	}

	/**
	 * Analyzes fields with single annotation
	 * 
	 * @param obj    the object
	 * @param source the source of the run event
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("rawtypes")
	private void analyzeSingleAnnotations(Object obj, Object source) throws IllegalAccessException {
		List<Field> fieldList = FieldUtils.getFieldsListWithAnnotation(obj.getClass(), AsmetaMonitoredLocation.class);
		for (Field f : fieldList) {
			f.setAccessible(true);
			// First, get the value
			String value = "";
			if (!f.getAnnotation(AsmetaMonitoredLocation.class).asmLocationValue().equals("")) {
				if (f.get(obj).equals(source))
					value = f.getAnnotation(AsmetaMonitoredLocation.class).asmLocationValue();
			} else {
				if (f.get(obj) instanceof JTable) {
					JTable guiTable = (JTable) f.get(obj);
					int selectedRow = guiTable.getSelectedRow();
					int selectedColumn = guiTable.getSelectedColumn();					
					if (selectedRow != -1 && selectedColumn != -1) {
						Object selectedValue = guiTable.getModel().getValueAt(selectedRow, selectedColumn);
						value = (selectedValue == null || selectedValue.toString().equals("")) ? "undef"
								: selectedValue.toString();
					} else
						value = "undef";
				} else {
					value = getValueFromSingleField(f, obj);
				}
				if (value == null && !(f.get(obj) instanceof ButtonColumn))
					throw new RuntimeException("This type of component is not yet managed by the fMVC framework: "
							+ f.get(obj).getClass());
			}

			if (value != null) {
				// Look for the type of the monitored function in the ASM model
				LocationType locationType = null;
				ArrayList<Function> functions = new ArrayList<Function>();
				AsmCollection asms;
				try {
					asms = ASMParser.setUpReadAsm(new File(ASM_PATH + "/" + sim.getAsmModel().getName() + ASMParser.ASM_EXTENSION));
					asms.forEach(x -> x.getHeaderSection().getSignature().getFunction().stream()
							.filter(fn -> fn.getName().equals(f.getAnnotation(AsmetaMonitoredLocation.class).asmLocationName()))
							.forEach(y -> functions.add(y)));
					assert functions.size() == 1
							: "The function " + f.getAnnotation(AsmetaMonitoredLocation.class).asmLocationName() + " has not been found in the ASM";

					LocationTypeForDomain visitor = new LocationTypeForDomain();
					locationType = visitor.visit(functions.get(0).getCodomain());
					if (locationType == null) {
							throw new RuntimeException(
									"The type of Codomain " + functions.get(0).getCodomain().getClass().getSimpleName()
											+ " is not yet managed by the AsmetaFMVCLib");
					}
					// Now add the value to the location map
					Value val = getValueFromString(value, locationType);
					String loc = f.getAnnotation(AsmetaMonitoredLocation.class).asmLocationName();
					if (!reader.locationMemory.containsKey(loc) && !value.equals("")) {
						reader.addValue(loc, val);
						System.out.println("--- Taking " + val + " for " + loc);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}	
	
	/**
	 * Analyzes fields with multiple annotations
	 * 
	 * @param obj    the object
	 * @param source the source of the run event
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("rawtypes")
	private void analyzeMultipleAnnotations(Object obj, Object source) throws IllegalAccessException {
		List<Field> fieldList = FieldUtils.getFieldsListWithAnnotation(obj.getClass(), AsmetaMonitoredLocations.class);
		for (Field f : fieldList) {
			f.setAccessible(true);
			for (AsmetaMonitoredLocation f1 : f.getAnnotation(AsmetaMonitoredLocations.class).value()) {
				// First, get the value
				String value = "";
				if (!f1.asmLocationValue().equals("")) {
					if (f.get(obj).equals(source))
						value = f1.asmLocationValue();
				} else {
					if (f.get(obj) instanceof JTable) {
						JTable guiTable = (JTable) f.get(obj);
						int selectedRow = guiTable.getSelectedRow();
						int selectedColumn = guiTable.getSelectedColumn();
						if (selectedRow != -1 && selectedColumn != -1) {
							Object selectedValue = guiTable.getModel().getValueAt(selectedRow, selectedColumn);
							value = (selectedValue == null || selectedValue.toString().equals("")) ? "undef"
									: selectedValue.toString();
						} else
							value = "undef";
					} else
						value = getValueFromSingleField(f, obj);
					if (value == null && !(f.get(obj) instanceof ButtonColumn))
						throw new RuntimeException("This type of component is not yet managed by the fMVC framework: "
								+ f.get(obj).getClass());
				}

				if (value != null) {
					// Look for the type of the monitored function in the ASM model
					LocationType locationType = null;
					ArrayList<Function> functions = new ArrayList<Function>();
					AsmCollection asms;
					try {
						asms = ASMParser.setUpReadAsm(new File(ASM_PATH + "/" + sim.getAsmModel().getName() + ASMParser.ASM_EXTENSION));
						asms.forEach(x -> x.getHeaderSection().getSignature().getFunction().stream()
								.filter(fn -> fn.getName().equals(f1.asmLocationName()))
								.forEach(y -> functions.add(y)));
						assert functions.size() == 1
								: "The function " + f1.asmLocationName() + " has not been found in the ASM";

						switch (functions.get(0).getCodomain().getClass().getSimpleName()) {
							case "EnumTdImpl":
								locationType = LocationType.ENUM;
								break;
							case "AbstractTdImpl":
								locationType = LocationType.RESERVE;
								break;
							case "ConcreteDomainImpl":
								// TODO: Other types may be instead of integer
								locationType = LocationType.INTEGER;
								break;
							default:
								throw new RuntimeException(
										"The type of Codomain " + functions.get(0).getCodomain().getClass().getSimpleName()
												+ " is not yet managed by the AsmetaFMVCLib");
						}

						// Now add the value to the location map
						Value val = getValueFromString(value, locationType);
						String loc = f1.asmLocationName();
						if (!reader.locationMemory.containsKey(loc) && !value.equals(""))
							reader.addValue(loc, val);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * Returns the value of a field
	 * 
	 * @param f   the field
	 * @param obj the object annotated with the corresponding field
	 * @return the value of a fied
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public String getValueFromSingleField(Field f, Object obj) throws IllegalArgumentException, IllegalAccessException {
		String value = "";
		if (f.get(obj) instanceof JTextField)
			value = ((JTextField) (f.get(obj))).getText();
		else if (f.get(obj) instanceof JSlider)
			value = String.valueOf(((JSlider) (f.get(obj))).getValue());
		else if (f.get(obj) instanceof JToggleButton)
			value = String.valueOf(((JToggleButton) (f.get(obj))).isSelected());
		else if (f.get(obj) instanceof JSpinner)
			value = ((JSpinner) f.get(obj)).getValue().toString();
		else
			return null;

		return value;
	}

	/**
	 * Converts a string value in the corresponding ASMETA value based on the
	 * locationType
	 * 
	 * @param value        the string value
	 * @param locationType the type of the ASMETA location
	 * @return the correct ASMETA value
	 */
	@SuppressWarnings("rawtypes")
	private Value getValueFromString(String value, LocationType locationType) {
		Value val;
		switch (locationType) {
		case INTEGER:
			if (value.equals(""))
				value = "0";
			val = new IntegerValue(value);
			break;
		case STRING:
			val = new StringValue(value);
			break;
		case ENUM:
			val = new EnumValue(value);
			break;
		case BOOLEAN:
			val = BooleanValue.parserBooleanValue(value);
			break;
		case CHAR:
			val = new CharValue(value.charAt(0));
			break;
		case REAL:
			val = new RealValue(value);
			break;
		case RESERVE:
			val = new ReserveValue(value);
			break;
		default:
			throw new RuntimeException("Location type not yet managed by the fMVC framework");
		}
		return val;
	}

	/**
	 * Returns the simulator used by the model
	 * 
	 * @return the simulator used by the model
	 */
	public Simulator getSimulator() {
		return this.sim;
	}

	/**
	 * Returns the environment used by the model
	 * 
	 * @return the environment used by the model
	 */
	public Environment getEnvironment() {
		return this.environment;
	}

	/**
	 * Returns the reader used by the model
	 * 
	 * @return the reader used by the model
	 */
	public ViewReader getReader() {
		return this.reader;
	}

	/**
	 * Get the map of the controlled locations
	 * 
	 * @return the map of the controlled locations
	 */
	public HashMap<String, List<Entry<String, String>>> getControlledAssignments() {
		return controlledAssignments;
	}

	/**
	 * Initializes the controlledAssignments map with initial values
	 */
	public void initInitalState() {
		Simulator simulator = this.getSimulator();
		Initialization initialization = simulator.getAsmModel().getDefaultInitialState();
		InitialStateVisitor visitor = new InitialStateVisitor();
		// Visit the function initialization part
		for (FunctionInitialization init : initialization.getFunctionInitialization()) {
			visitor.visitInit(init);
			List<Entry<String, String>> assignments = new ArrayList<Entry<String, String>>();
			for (Entry<String, String> entry : visitor.initMap.entrySet()) {
				MutablePair<String, String> pair = new MutablePair<String, String>();
				pair.setLeft(entry.getKey());
				pair.setRight(entry.getValue());
				assignments.add(pair);
			}
			controlledAssignments.put(init.getInitializedFunction().getName(), assignments);
			visitor.initMap.clear();
		}
	}

	/**
	 * Returns the value of a controlled function in the current state
	 * 
	 * @param locationName the name of the location
	 * @return the value of a controlled function in the current state
	 */
	public List<Entry<String, String>> getValue(String locationName) {
		return controlledAssignments.get(locationName);
	}
	
	
	public class LocationTypeForDomain extends ReflectiveVisitor<LocationType>{

		public LocationType visit(EnumTd e){
			return LocationType.ENUM; 
		}

		public LocationType visit(AbstractTd object) {
			return LocationType.RESERVE;
		}

		public LocationType visit(ConcreteDomain object) {
			// assuming that it is an integer
			return LocationType.INTEGER;
		}
		
		public LocationType visit(IntegerDomain object) {
			return LocationType.INTEGER;
		}
		

	}

	
}



