package version_1_2;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class KeyBoardListener implements KeyListener {

	private TimingSystem timeingSystem;

	public KeyBoardListener(TimingSystem timingSystem) {
		this.timeingSystem = timingSystem;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		int key = arg0.getKeyCode();

		if (key == KeyEvent.VK_SPACE) {
			if (this.timeingSystem.started) {
				this.timeingSystem.lapIncrease();
			}
		} else if (key == KeyEvent.VK_S) {
			if (!this.timeingSystem.started) {
				this.timeingSystem.startClock();
				this.timeingSystem.started = true;
			}
		} else if (key == KeyEvent.VK_Q) {
			this.timeingSystem.started = false;
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
