package version_1_;

import java.util.Arrays;

import javax.swing.JFrame;

public class GUI_controller {

	String[] value_split;
	private JFrame frame;
	protected double[] accelZData = new double[100];
	Graph graph;

	public GUI_controller() {
		this.value_split = null;
		Arrays.fill(this.accelZData, 0);
		this.graph = new Graph();
	}

	public void readData(String data) {
		this.value_split = data.split("\\|");
		shiftArray(this.accelZData);
		this.accelZData[99] = Double.parseDouble(this.value_split[2]);
	}

	public void shiftArray(double[] array) {
		for (int i = 1; i < array.length; i++) {
			array[i - 1] = array[i];
		}
	}

	public double[] getAccelData() {
		return this.accelZData;
	}

	public double getTemp() {
		if (this.value_split != null) {
			return Double.parseDouble(this.value_split[6]);
		}
		return 0;
	}
}
