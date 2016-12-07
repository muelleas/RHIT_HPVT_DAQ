package version_1_;

import javax.swing.JFrame;

public class Clock implements Runnable {
	public final static int CLOCK_PERIOD_MS = 10;
	private JFrame frame;

	public Clock(JFrame frame){
		this.frame = frame;
	}
	

	@Override
	public void run() {
		while (true) {
			this.frame.repaint();
			try {
				Thread.sleep(CLOCK_PERIOD_MS);
			} catch (InterruptedException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}

}

