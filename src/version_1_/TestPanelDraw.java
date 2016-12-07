package version_1_;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

public class TestPanelDraw extends JComponent {

	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		System.out.println("drawing");
		g2.drawString("Testing pannel", 25, 25);
		g2.setColor(Color.pink);
		g2.fillRect(50, 50, 500, 500);
	}
}
