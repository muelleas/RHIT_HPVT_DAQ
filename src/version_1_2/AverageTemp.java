package version_1_2;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import org.omg.CORBA.CODESET_INCOMPATIBLE;

public class AverageTemp extends JComponent implements panelInterface {

	GUI_controller controller;

	public AverageTemp(GUI_controller controller) {
		this.controller = controller;
		this.setToDefaultSize();
	}
	
	public void setToDefaultSize(){
		this.setPreferredSize(Constents.standardSmallSize);
	}
	
	public void setToNullSize() {
		this.setPreferredSize(Constents.nullSize);		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		g2.drawRect(0, 0, this.getWidth(), this.getHeight());
		g2.drawString("Inside Temp", 25, 25);
		g2.drawString(Double.toString(this.controller.getDataDouble(Constents.AVGTEMP)), 25, 50);
	}
}
