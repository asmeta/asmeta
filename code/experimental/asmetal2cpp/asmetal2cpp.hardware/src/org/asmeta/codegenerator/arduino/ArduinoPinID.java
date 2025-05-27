package org.asmeta.codegenerator.arduino;

import org.asmeta.codegenerator.ConfigurationMode;

public enum ArduinoPinID {

	D0, D1, D2, D3, D4, D5, D6, D7, D8, D9, D10, D11, D12, D13, D14, D15, D16, D17, D18, D19, D20, D21, D22, D23, D24, D25, D26, D27, D28, D29, D30, D31, D32, D33, D34, D35, D36, D37, D38, D39, D40, D41, D42, D43, D44, D45, D46, D47, D48, D49, D50, D51, D52, D53,

	A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15;

	public static ArduinoPinID getPinByNumber(int code) {
		return values()[code];
	}

	public boolean isDigital() {
		return this.name().startsWith("D");
	}

	public boolean isAnalog() {
		return this.name().startsWith("A");
	}

	@Override
	public String toString() {
		return this.name();
	}

	public static ArduinoPinID fromString(String pin) {
		for (ArduinoPinID id : ArduinoPinID.values()) {
			if (id.name().equals(pin))
				return id;
		}
		return null;
	}

}
