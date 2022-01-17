
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.fazecast.jSerialComm.SerialPort;

public class LockerOpen {

	public static boolean OpenLocker(String doorname) {
		if (doorname.equals("LOC1")) {
			doorname = "door1";
		}
		if (doorname.equals("LOC2")) {
			doorname = "door2";
		}
		if (doorname.equals("LOC3")) {
			doorname = "door3";
		}
		if (doorname.equals("LOC4")) {
			doorname = "door4";
		}
		if (doorname.equals("LOC5")) {
			doorname = "door5";
		}
		if (doorname.equals("LOC6")) {
			doorname = "door6";
		}
		if (doorname.equals("LOC7")) {
			doorname = "door7";
		}
		if (doorname.equals("LOC8")) {
			doorname = "door8";
		}
		byte[] b = doorname.getBytes();
		byte[] b2 = doorname.getBytes(Charset.forName("UTF-8"));
		byte[] b3 = doorname.getBytes(StandardCharsets.UTF_8); // Java 7+ only
		SerialPort sp = SerialPort.getCommPort("/dev/ttyACM0"); // device name TODO: must be changed
		sp.setComPortParameters(9600, 8, 1, 0); // default connection settings for Arduino
		sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0); // block until bytes can be written
		System.out.println(b3);
		if (sp.openPort()) {
		} else {
			return false;
		}

		sp.writeBytes(b3, 10);
		return true;

	}
}
