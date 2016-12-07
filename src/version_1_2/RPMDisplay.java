package version_1_2;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

public class RPMDisplay extends JComponent implements panelInterface {
	
	private GUI_controller controller;
	private int dataID;

	public RPMDisplay(GUI_controller controller, int dataID) {
		this.controller = controller;
		this.dataID = dataID;
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
		double RPM = this.controller.getDataDouble(this.dataID);
		g2.drawString("RPM: " + Double.toString(RPM), 25, 25);
		g2.drawString("KPH: " + Double.toString(RPM*60*Constents.WHEELCIRCUMFERENCE/1000), 25, 50);
		g2.drawString("MPH: " + Double.toString(RPM*60*Constents.WHEELCIRCUMFERENCE/1000*Constents.KPH2MPH), 25, 75);
		
	}
}
