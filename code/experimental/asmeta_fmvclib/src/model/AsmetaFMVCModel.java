package model;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;

import javax.swing.JTextField;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.asmeta.simulator.Environment;
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

import annotations.AsmetaModelParameter;
import annotations.AsmetaModelParameter.LocationType;

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
	}

	/**
	 * Gets the value (as a String) of a location in the current state
	 * 
	 * @param locationName the name of the location
	 * @return the value of the specified location in the current state
	 */
	@SuppressWarnings("rawtypes")
	public String getValue(String locationName) {
		State s = sim.getCurrentState();
		String result = "";
		for (Entry<Location, Value> x : s.getContrLocs().entrySet()) {
			if (x.getKey().getSignature().getName().equals(locationName))
				return x.getValue().toString();
		}
		return result;
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
		sim.run(nStep);
		setChanged();
		notifyObservers();
	}

	/**
	 * 
	 * @param obj the annotated obj
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	@SuppressWarnings("rawtypes")
	public void updateMonitored(Object obj) throws IllegalArgumentException, IllegalAccessException {
		List<Field> fieldList = FieldUtils.getFieldsListWithAnnotation(obj.getClass(), AsmetaModelParameter.class);
		for (Field f : fieldList) {
			f.setAccessible(true);
			// First, get the value
			String value;
			if (!f.getAnnotation(AsmetaModelParameter.class).asmLocationValue().equals("")) {
				value = f.getAnnotation(AsmetaModelParameter.class).asmLocationValue();
			} else {
				if (f.get(obj) instanceof JTextField) {
					value = ((JTextField) (f.get(obj))).getText();
				} else {
					throw new RuntimeException("This type of component is not yet managed by the fMVC framework");
				}
			}

			// Now add the value to the location map
			LocationType locationType = f.getAnnotation(AsmetaModelParameter.class).asmLocationType();
			Value val = getValueFromString(value, locationType);
			Location loc = getLocationByName(f.getAnnotation(AsmetaModelParameter.class).asmLocationName());
			reader.addValue(loc, val);
		}
	}

	/**
	 * Returns a location object given its name
	 * 
	 * @param asmLocationName the name of the asm location
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private Location getLocationByName(String asmLocationName) {
		Map<Location, Value> monitored = sim.getCurrentState().getMonLocs();
		for (Entry<Location, Value> entry : monitored.entrySet()) {
			if (entry.getKey().getSignature().getName().equals(asmLocationName))
				return entry.getKey();
		}
		return null;
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
