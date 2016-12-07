package version_1_;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

public class AverageTemp extends JComponent {

	GUI_controller controller;

	public AverageTemp(GUI_controller controller) {
		this.controller = controller;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawString("Inside Temp", 25, 25);
		g2.drawString(Double.toString(this.controller.getTemp()), 25, 35);
	}
}
