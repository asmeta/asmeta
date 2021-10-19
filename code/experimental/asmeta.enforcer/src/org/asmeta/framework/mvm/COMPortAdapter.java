import java.io.BufferedReader;
import java.io.InputStream;
import com.fazecast.jSerialComm.SerialPort;

public class COMPortAdapter {
	SerialPort comPort;

	public COMPortAdapter(String comName) {
		comPort = SerialPort.getCommPort(comName);
		comPort.setBaudRate(9600);
		if (!comPort.openPort())
			System.err.println(
					"Error in opening the COM port. \nAnother application may have opened it.");

	}

	/*
	 * public int readData() { if (comPort.bytesAvailable() == 0) return 0;
	 * 
	 * byte[] readBuffer = new byte[comPort.bytesAvailable()]; try {
	 * comPort.readBytes(readBuffer, readBuffer.length); } catch (Exception e) {
	 * e.printStackTrace(); }
	 * 
	 * return Byte.toUnsignedInt(readBuffer[0]); }
	 */

	public String readData() {
		InputStream inputStream = comPort.getInputStream();
		if (comPort.bytesAvailable() == 0)
			return "";

		byte[] readBuffer = new byte[comPort.bytesAvailable()];
		try {
			// comPort.readBytes(readBuffer, readBuffer.length);
			while (inputStream.available() > 0) {
				int numBytes = inputStream.read(readBuffer);
			}
			byte[] totalReadBuffer = readBuffer;
			//System.out.println("BUFFER + " + new String(readBuffer));
		} catch (Exception e) {
			e.printStackTrace();
		}

		//return Byte.toString(readBuffer[0]);
		return new String(readBuffer);
	}

	public void sendData(String value) {
		comPort.writeBytes(value.getBytes(), value.getBytes().length);
	}

	public void dispose() {
		comPort.closePort();
	}

}
