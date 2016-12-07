package version_1_2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

public class AccelGraph extends JComponent implements panelInterface {
	
	GUI_controller controller;
	private Graph graph;
	int dataID;

	public AccelGraph(GUI_controller controller, int dataID) {
		this.controller = controller;
		this.graph = new Graph();
		this.dataID = dataID;
		this.setToDefaultSize();
	}
	
	public void setToDefaultSize(){
		this.setPreferredSize(Constents.standardlargeSize);
	}
	
	public void setToNullSize() {
		this.setPreferredSize(Constents.nullSize);		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		g2.drawRect(0, 0, this.getWidth(), this.getHeight());
		
		double[] data = null;
		data = this.controller.getDataArray(this.dataID);
		g2.setColor(Color.black);
		g2.drawString("accel graph channel: " + this.dataID, 25, 25);
		g2.drawString("accelZ: " + data[data.length-1], 25, 50);
		this.graph.draw(data, g2);
	}
}
