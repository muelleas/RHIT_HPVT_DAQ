package version_1_2;

import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class TimingSystem extends JComponent implements panelInterface {

	private long startTime;
	private long previousLapTime;
	private long currentLapTime;
	private int lapCount;
	public boolean started;

	public TimingSystem(JFrame frame) {
		KeyListener keyBoardListener = new KeyBoardListener(this);
		frame.addKeyListener(keyBoardListener);
		this.lapCount = 0;
		this.previousLapTime = 0;
		this.currentLapTime = 0;
		this.started = false;
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
		
		g2.drawString("Lap Time - " + toHMS(this.currentLapTime - this.previousLapTime), 25, 25);
		g2.drawString("Lap count - " + this.lapCount, 25, 50);
		if (this.started) {
			g2.drawString("Total Time - " + toHMS(System.currentTimeMillis() - this.startTime), 25, 75);
		}

	}

	public String toHMS(Long time) {
		long millis = time % 1000;
		long second = (time / 1000) % 60;
		long minute = (time / (1000 * 60)) % 60;
		long hour = (time / (1000 * 60 * 60)) % 24;
		return String.format("%02d:%02d:%02d:%d", hour, minute, second, millis);
	}

	public void startClock() {
		this.startTime = System.currentTimeMillis();
		this.currentLapTime = this.startTime;
		this.previousLapTime = this.startTime;
	}

	public void lapIncrease() {
		this.lapCount++;
		this.previousLapTime = this.currentLapTime;
		this.currentLapTime = System.currentTimeMillis();
	}
}
