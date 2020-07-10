package org.asmeta.codegenerator.arduino;

import java.util.ArrayList;
import java.util.List;

import org.asmeta.codegenerator.ConfigurationMode;
import org.asmeta.codegenerator.arduino.ArduinoPinID;
import org.asmeta.codegenerator.arduino.ArduinoVersion;

public class ArduinoBoard {
	List<ArduinoPin> pins = new ArrayList<ArduinoPin>();
	List<ArduinoSerial> serials = new ArrayList<ArduinoSerial>();
	ArduinoVersion arduinoVersion;

	public List<ArduinoPin> getPins() {
		return pins;
	}

	public ArduinoBoard(String av) {
		this(ArduinoVersion.fromString(av));
	}

	public ArduinoBoard(ArduinoVersion av) {
		this.arduinoVersion = av;
		switch (av) {
		case DIECIMILA:
		case DUEMILANOVE:
		case LILYPAD:
		case UNO:
			pins.add(new ArduinoPin(ArduinoPinID.D0, ArduinoPinFeature.DIGITAL,ArduinoPinFeature.SERIAL));
			pins.add(new ArduinoPin(ArduinoPinID.D1, ArduinoPinFeature.DIGITAL,ArduinoPinFeature.SERIAL));
			pins.add(new ArduinoPin(ArduinoPinID.D2, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.D3, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.PWM8));
			pins.add(new ArduinoPin(ArduinoPinID.D4, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.D5, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.PWM8));
			pins.add(new ArduinoPin(ArduinoPinID.D6, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.PWM8));
			pins.add(new ArduinoPin(ArduinoPinID.D7, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.D8, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.D9, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.PWM8));
			pins.add(new ArduinoPin(ArduinoPinID.D10, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.PWM8));
			pins.add(new ArduinoPin(ArduinoPinID.D11, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.PWM8));
			pins.add(new ArduinoPin(ArduinoPinID.D12, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.D13, ArduinoPinFeature.DIGITAL));

			pins.add(new ArduinoPin(ArduinoPinID.A0, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.ANALOGIN10));
			pins.add(new ArduinoPin(ArduinoPinID.A1, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.ANALOGIN10));
			pins.add(new ArduinoPin(ArduinoPinID.A2, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.ANALOGIN10));
			pins.add(new ArduinoPin(ArduinoPinID.A3, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.ANALOGIN10));
			pins.add(new ArduinoPin(ArduinoPinID.A4, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.ANALOGIN10));
			pins.add(new ArduinoPin(ArduinoPinID.A5, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.ANALOGIN10));
			serials.add(new ArduinoSerial("TX0", ArduinoPinID.D0,ArduinoPinID.D1));
			break;
		case MEGA:
		case MEGA2560:
			pins.add(new ArduinoPin(ArduinoPinID.D0, ArduinoPinFeature.DIGITAL,ArduinoPinFeature.SERIAL));
			pins.add(new ArduinoPin(ArduinoPinID.D1, ArduinoPinFeature.DIGITAL,ArduinoPinFeature.SERIAL));
			pins.add(new ArduinoPin(ArduinoPinID.D2, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.PWM8));
			pins.add(new ArduinoPin(ArduinoPinID.D3, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.PWM8));
			pins.add(new ArduinoPin(ArduinoPinID.D4, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.PWM8));
			pins.add(new ArduinoPin(ArduinoPinID.D5, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.PWM8));
			pins.add(new ArduinoPin(ArduinoPinID.D6, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.PWM8));
			pins.add(new ArduinoPin(ArduinoPinID.D7, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.PWM8));
			pins.add(new ArduinoPin(ArduinoPinID.D8, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.PWM8));
			pins.add(new ArduinoPin(ArduinoPinID.D9, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.PWM8));
			pins.add(new ArduinoPin(ArduinoPinID.D10, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.PWM8));
			pins.add(new ArduinoPin(ArduinoPinID.D11, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.PWM8));
			pins.add(new ArduinoPin(ArduinoPinID.D12, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.PWM8));
			pins.add(new ArduinoPin(ArduinoPinID.D13, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.PWM8));
			pins.add(new ArduinoPin(ArduinoPinID.D14, ArduinoPinFeature.DIGITAL,ArduinoPinFeature.SERIAL));
			pins.add(new ArduinoPin(ArduinoPinID.D15, ArduinoPinFeature.DIGITAL,ArduinoPinFeature.SERIAL));
			pins.add(new ArduinoPin(ArduinoPinID.D16, ArduinoPinFeature.DIGITAL,ArduinoPinFeature.SERIAL));
			pins.add(new ArduinoPin(ArduinoPinID.D17, ArduinoPinFeature.DIGITAL,ArduinoPinFeature.SERIAL));
			pins.add(new ArduinoPin(ArduinoPinID.D18, ArduinoPinFeature.DIGITAL,ArduinoPinFeature.SERIAL));
			pins.add(new ArduinoPin(ArduinoPinID.D19, ArduinoPinFeature.DIGITAL,ArduinoPinFeature.SERIAL));
			pins.add(new ArduinoPin(ArduinoPinID.D20, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.D21, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.D22, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.D23, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.D24, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.D25, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.D26, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.D27, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.D28, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.D29, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.D30, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.D31, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.D32, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.D33, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.D34, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.D35, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.D36, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.D37, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.D38, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.D39, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.D40, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.D41, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.D42, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.D43, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.D44, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.PWM8));
			pins.add(new ArduinoPin(ArduinoPinID.D45, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.PWM8));
			pins.add(new ArduinoPin(ArduinoPinID.D46, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.PWM8));
			pins.add(new ArduinoPin(ArduinoPinID.D47, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.D48, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.D49, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.D50, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.D51, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.D52, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.D53, ArduinoPinFeature.DIGITAL));

			pins.add(new ArduinoPin(ArduinoPinID.A0, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.ANALOGIN10));
			pins.add(new ArduinoPin(ArduinoPinID.A1, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.ANALOGIN10));
			pins.add(new ArduinoPin(ArduinoPinID.A2, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.ANALOGIN10));
			pins.add(new ArduinoPin(ArduinoPinID.A3, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.ANALOGIN10));
			pins.add(new ArduinoPin(ArduinoPinID.A4, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.ANALOGIN10));
			pins.add(new ArduinoPin(ArduinoPinID.A6, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.ANALOGIN10));
			pins.add(new ArduinoPin(ArduinoPinID.A7, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.ANALOGIN10));
			pins.add(new ArduinoPin(ArduinoPinID.A8, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.ANALOGIN10));
			pins.add(new ArduinoPin(ArduinoPinID.A9, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.ANALOGIN10));
			pins.add(new ArduinoPin(ArduinoPinID.A10, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.ANALOGIN10));
			pins.add(new ArduinoPin(ArduinoPinID.A11, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.ANALOGIN10));
			
			serials.add(new ArduinoSerial("TX0", ArduinoPinID.D0,ArduinoPinID.D1));
			serials.add(new ArduinoSerial("TX1", ArduinoPinID.D19,ArduinoPinID.D18));
			serials.add(new ArduinoSerial("TX2", ArduinoPinID.D17,ArduinoPinID.D16));
			serials.add(new ArduinoSerial("TX3", ArduinoPinID.D16,ArduinoPinID.D15));
			
			break;
		case ZERO:
			pins.add(new ArduinoPin(ArduinoPinID.D0, ArduinoPinFeature.DIGITAL,ArduinoPinFeature.SERIAL));
			pins.add(new ArduinoPin(ArduinoPinID.D1, ArduinoPinFeature.DIGITAL,ArduinoPinFeature.SERIAL));
			pins.add(new ArduinoPin(ArduinoPinID.D2, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.D3, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.PWM8));
			pins.add(new ArduinoPin(ArduinoPinID.D4, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.PWM8));
			pins.add(new ArduinoPin(ArduinoPinID.D5, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.PWM8));
			pins.add(new ArduinoPin(ArduinoPinID.D6, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.PWM8));
			pins.add(new ArduinoPin(ArduinoPinID.D7, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.D8, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.PWM8));
			pins.add(new ArduinoPin(ArduinoPinID.D9, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.PWM8));
			pins.add(new ArduinoPin(ArduinoPinID.D10, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.PWM8));
			pins.add(new ArduinoPin(ArduinoPinID.D11, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.PWM8));
			pins.add(new ArduinoPin(ArduinoPinID.D12, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.PWM8));
			pins.add(new ArduinoPin(ArduinoPinID.D13, ArduinoPinFeature.DIGITAL, ArduinoPinFeature.PWM8));

			pins.add(new ArduinoPin(ArduinoPinID.A0, ArduinoPinFeature.ANALOGIN12, ArduinoPinFeature.ANALOGOUT10,
					ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.A1, ArduinoPinFeature.ANALOGIN12, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.A2, ArduinoPinFeature.ANALOGIN12, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.A3, ArduinoPinFeature.ANALOGIN12, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.A4, ArduinoPinFeature.ANALOGIN12, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.A6, ArduinoPinFeature.ANALOGIN12, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.A7, ArduinoPinFeature.ANALOGIN12, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.A8, ArduinoPinFeature.ANALOGIN12, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.A9, ArduinoPinFeature.ANALOGIN12, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.A10, ArduinoPinFeature.ANALOGIN12, ArduinoPinFeature.DIGITAL));
			pins.add(new ArduinoPin(ArduinoPinID.A11, ArduinoPinFeature.ANALOGIN12, ArduinoPinFeature.DIGITAL));
			serials.add(new ArduinoSerial("TX0", ArduinoPinID.D0,ArduinoPinID.D1));
			break;
		default:
			throw new RuntimeException("Error: Arduino version not supported");
		}
	}

	public ArduinoPin getPinFromId(ArduinoPinID id) {
		for (ArduinoPin pin : pins)
			if (pin.getId() == id)
				return pin;
		return null;
	}

	public int getAnalogInResolution() {
		ArduinoPin A0 = getPinFromId(ArduinoPinID.A0);
		if (A0.supportFeature(ArduinoPinFeature.ANALOGIN12))
			return 12;
		if (A0.supportFeature(ArduinoPinFeature.ANALOGIN10))
			return 10;
		
		return 1;
	}
	
	public boolean supportFeature(ArduinoPinFeature feature){
		for(ArduinoPin pin: pins)
			if(pin.supportFeature(feature))
				return true;
		return false;
	}

	public ArduinoPinFeature getCorrespondingFeatureForMode(ConfigurationMode mode) {
		switch (mode) {

		case DIGITAL:
			return ArduinoPinFeature.DIGITAL;

		case DIGITALINVERTED:
			return ArduinoPinFeature.DIGITAL;

		case ANALOGLINEARIN:
			if (arduinoVersion == ArduinoVersion.ZERO)
				return ArduinoPinFeature.ANALOGIN12;
			else
				return ArduinoPinFeature.ANALOGIN10;

		case ANALOGLINEAROUT:
			if (arduinoVersion == ArduinoVersion.ZERO)
				return ArduinoPinFeature.ANALOGOUT10;
			else
				return null;

		case PWM:
			return ArduinoPinFeature.PWM8;
		
		case USERDEFINED:
			return null; 	// userdefined gives no information about features to
							// be used

		}
		return null;
	}

}
