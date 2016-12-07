package version_1_;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

public class AccelGraph extends JComponent{
	
	GUI_controller controller;
	private Graph graph;

	public AccelGraph(GUI_controller controller) {
		this.controller = controller;
		this.graph = new Graph();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		double[] data = this.controller.getAccelData();
		g2.setColor(Color.black);
		g2.drawString("accelZ: " + data[data.length-1], 25, 25);
		this.graph.draw(data, g2);
	}

}
