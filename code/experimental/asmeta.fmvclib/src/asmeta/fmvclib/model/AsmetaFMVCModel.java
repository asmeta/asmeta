package asmeta.fmvclib.model;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.Environment.TimeMngt;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.State;
import org.asmeta.simulator.main.Simulator;
import org.asmeta.simulator.value.BooleanValue;
import org.asmeta.simulator.value.CharValue;
import org.asmeta.simulator.value.EnumValue;
import org.asmeta.simulator.value.IntegerValue;
import org.asmeta.simulator.value.RealValue;
import org.asmeta.simulator.value.StringValue;
import org.asmeta.simulator.value.Value;

import asmeta.fmvclib.annotations.AsmetaModelParameter;
import asmeta.fmvclib.annotations.AsmetaModelParameters;
import asmeta.fmvclib.annotations.LocationType;

@SuppressWarnings("deprecation")
public class AsmetaFMVCModel extends Observable {

	/**
	 * The ASMETA simulator
	 */
	private Simulator sim;

	/**
	 * The reader
	 */
	private ViewReader reader;

	/**
	 * Creates the AsmetaFMVCModel instance for a given ASMETA model
	 * 
	 * @param asmPath the path of the ASMETA model
	 * @throws Exception
	 */
	public AsmetaFMVCModel(String asmPath) throws Exception {
		reader = new ViewReader();
		sim = Simulator.createSimulator(asmPath, new Environment(reader));
		Environment.timeMngt = TimeMngt.use_java_time;
	}

	/**
	 * Gets the value (as a String) of a location in the current state
	 * 
	 * @param locationName the name of the location
	 * @return the value of the specified location in the current state
	 */
	@SuppressWarnings("rawtypes")
	public String getValue(String locationName, LocationType keyType) {
		State s = sim.getCurrentState();
		SortedMap<String, String> resultStr = new TreeMap<>();
		SortedMap<Long, String> resultInt = new TreeMap<>();
		SortedMap<Float, String> resultFloat = new TreeMap<>();
		SortedMap<Boolean, String> resultBoolean = new TreeMap<>();

		for (Entry<Location, Value> x : s.getContrLocs().entrySet()) {
			if (x.getKey().getSignature().getName().equals(locationName)) {
				if (keyType == LocationType.UNDEF || keyType == LocationType.STRING || keyType == LocationType.ENUM
						|| keyType == LocationType.CHAR)
					resultStr.put(Arrays.toString(x.getKey().getElements()), x.getValue().toString());
				else if (keyType == LocationType.INTEGER)
					resultInt.put((x.getKey().getElements().length > 0 ? (Long)(x.getKey().getElements()[0].getValue()) : 0), x.getValue().toString());
				else if (keyType == LocationType.REAL)
					resultFloat.put((x.getKey().getElements().length > 0 ? (Float)(x.getKey().getElements()[0].getValue()) : 0), x.getValue().toString());
				else if (keyType == LocationType.BOOLEAN)
					resultBoolean.put((x.getKey().getElements().length > 0 ? (Boolean)(x.getKey().getElements()[0].getValue()) : true), x.getValue().toString());
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

		// If it has a single value only
		if (strResult.split("=").length == 2)
			strResult = strResult.split("=")[1];
		return strResult;
	}

	/**
	 * Runs the simulator for a single step
	 */
	public void runSimulator() {
		this.runSimulator(1);
	}

	/**
	 * Runs the simulator for nStep steps
	 * 
	 * @param nStep the number of steps
	 */
	public void runSimulator(int nStep) {
		try{ 
			sim.run(nStep);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setChanged();
		notifyObservers();
	}

	/**
	 * 
	 * @param obj the annotated obj
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public void updateMonitored(Object obj, Object source) throws IllegalArgumentException, IllegalAccessException {
		reader.locationMemory.clear();
		analyzeSingleAnnotations(obj, source);
		analyzeMultipleAnnotations(obj, source);
		System.out.println("LOCATION MEMORY: " + reader.locationMemory);
	}

	/**
	 * Analyzes fields with single annotation
	 * @param obj the object
	 * @param source the source of the run event
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("rawtypes")
	private void analyzeSingleAnnotations(Object obj, Object source) throws IllegalAccessException {
		List<Field> fieldList = FieldUtils.getFieldsListWithAnnotation(obj.getClass(), AsmetaModelParameter.class);
		for (Field f : fieldList) {
			f.setAccessible(true);
			// First, get the value
			String value = "";
			if (!f.getAnnotation(AsmetaModelParameter.class).asmLocationValue().equals("")) {
				if (f.get(obj).equals(source))
					value = f.getAnnotation(AsmetaModelParameter.class).asmLocationValue();
			} else {
				if (f.get(obj) instanceof JTable) {
					JTable guiTable = (JTable)f.get(obj);
					int selectedRow = guiTable.getSelectedRow();
					int selectedColumn = guiTable.getSelectedColumn();
					if (selectedRow != -1 && selectedColumn != -1) {
						Object selectedValue = guiTable.getModel().getValueAt(selectedRow, selectedColumn);
						value = (selectedValue == null || selectedValue.toString().equals("")) ? "undef" : selectedValue.toString();
					} else
						value = "undef";
				}
				else
					value = getValueFromSingleField(f, obj);
				if (value == null)
					throw new RuntimeException("This type of component is not yet managed by the fMVC framework: "
							+ f.get(obj).getClass());
			}

			// Now add the value to the location map
			LocationType locationType = f.getAnnotation(AsmetaModelParameter.class).asmLocationType();
			Value val = getValueFromString(value, locationType);
			String loc = f.getAnnotation(AsmetaModelParameter.class).asmLocationName();
			if (!reader.locationMemory.containsKey(loc) && !value.equals(""))
				reader.addValue(loc, val);
		}
	}
	
	/**
	 * Analyzes fields with multiple annotations
	 * @param obj the object
	 * @param source the source of the run event
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("rawtypes")
	private void analyzeMultipleAnnotations(Object obj, Object source) throws IllegalAccessException {
		List<Field> fieldList = FieldUtils.getFieldsListWithAnnotation(obj.getClass(), AsmetaModelParameters.class);		
		for (Field f : fieldList) {
			f.setAccessible(true);
			for (AsmetaModelParameter f1 : f.getAnnotation(AsmetaModelParameters.class).value()) {
				// First, get the value
				String value = "";
				if (!f1.asmLocationValue().equals("")) {
					if (f.get(obj).equals(source))
						value = f1.asmLocationValue();
				} else {
					if (f.get(obj) instanceof JTable) {
						JTable guiTable = (JTable)f.get(obj);
						int selectedRow = guiTable.getSelectedRow();
						int selectedColumn = guiTable.getSelectedColumn();
						if (selectedRow != -1 && selectedColumn != -1) {
							Object selectedValue = guiTable.getModel().getValueAt(selectedRow, selectedColumn);
							value = (selectedValue == null || selectedValue.toString().equals("")) ? "undef" : selectedValue.toString();
						} else
							value = "undef";
					}
					else
						value = getValueFromSingleField(f, obj);
					if (value == null)
						throw new RuntimeException("This type of component is not yet managed by the fMVC framework: "
								+ f.get(obj).getClass());
				}
	
				// Now add the value to the location map
				LocationType locationType = f1.asmLocationType();
				Value val = getValueFromString(value, locationType);
				String loc = f1.asmLocationName();
				if (!reader.locationMemory.containsKey(loc) && !value.equals(""))
					reader.addValue(loc, val);
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
		default:
			throw new RuntimeException("Location type not yet managed by the fMVC framework");
		}
		return val;
	}
}
