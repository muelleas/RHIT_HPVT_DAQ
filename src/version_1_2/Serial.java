package version_1_2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.util.Enumeration;

public class Serial implements SerialPortEventListener {
	SerialPort serialPort;
	/** The port we're normally going to use. */
	private static final String PORT_NAMES[] = { "/dev/tty.usbserial-A9007UX1", // Mac
			"/dev/ttyACM0", // Raspberry Pi
			"/dev/ttyUSB0", // Linux
			"COM4", // Windows
	};
	/**
	 * A BufferedReader which will be fed by a InputStreamReader converting the
	 * bytes into characters making the displayed results codepage independent
	 */
	private BufferedReader input;
	private OutputStream output;
	/** The output stream to the port */
	private static final int TIME_OUT = 2000;
	/** Milliseconds to block while waiting for port open */
	private static final int DATA_RATE = 9600;
	
	private GUI_controller controller;

	public Serial(GUI_controller controller) {
		this.controller = controller;
		
		
	}

	/** Default bits per second for COM port. */

	public boolean initialize(String ID) {
		CommPortIdentifier portId = null;

		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
		//CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
		/*if (currPortId.getName().equals(ID)) {
			portId = currPortId;
		}*/
		
		while (portEnum.hasMoreElements()) {// First, Find an instance of serial
											// port as set in PORT_NAMES.
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				}
			}
		}
		if (portId == null) {
			System.out.println("Could not find COM port.");
			return false;
		}

		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);

			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			// open the streams
			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			output = serialPort.getOutputStream();

			// add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
		return true;
	}

	/**
	 * This should be called when you stop using the port. This will prevent
	 * port locking on platforms like Linux.
	 */
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	/**
	 * Handle an event on the serial port. Read the data and print it.
	 */
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				String inputLine = input.readLine();

				
				this.controller.readData(inputLine);
				//String[] value_split = inputLine.split("\\|");

				//System.out.println(value_split[1]);

				// System.out.println(inputLine);
			} catch (Exception e) {
				// System.err.println(e.toString()); //added
			}
		}
	}
}