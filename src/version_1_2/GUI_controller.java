package version_1_2;

import java.util.Arrays;

public class GUI_controller {
	
	static int dataLength = 100;

	String[] value_split;
	private double[][] storedData = new double[Constents.DATATOSTORE.length][100];
	Graph graph;

	public GUI_controller() {
		this.value_split = null;
		
		for (int i = 0; i<Constents.DATATOSTORE.length; i++){
			Arrays.fill(this.storedData[i], 0);
		}
		
		this.graph = new Graph();
	}

	public void readData(String data) {
		this.value_split = data.split("\\|");
		
		for (int i = 0; i<Constents.DATATOSTORE.length; i++){
			shiftArray(this.storedData[i]);
			this.storedData[i][dataLength-1] = Double.parseDouble(this.value_split[Constents.DATATOSTORE[i]]);
		}
	}

	public void shiftArray(double[] array) {
		for (int i = 1; i < array.length; i++) {
			array[i - 1] = array[i];
		}
	}
	
	public double[] getDataArray(int i){
		return this.storedData[i];
	}

	public double getDataDouble(int dataID) {
		if (this.value_split != null) {
			return Double.parseDouble(this.value_split[dataID]);
		}
		return 0;
	}
}
