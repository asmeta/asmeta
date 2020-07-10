package org.asmeta.codegenerator

import org.asmeta.codegenerator.arduino.ArduinoPinID
import asmeta.structure.Asm

class Util {

	def static String arduinoPinToString(ArduinoPinID id) {
		return arduinoPinToString(id.name)
	}
	
	def static String arduinoPinToString(String id) {
		if (id.matches("\\bD[0-9]{1,2}\\b")) //e.g. D1 D12 returns only 1 or 12
			return id.substring(1)
		else
			return id
	}
	def static getAsmClassName(Asm asm){
    return asm.name.toFirstUpper
  }
}
