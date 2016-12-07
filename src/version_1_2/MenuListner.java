package version_1_2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;

public class MenuListner implements ActionListener {

	panelInterface panel;
	boolean checked;
	
	public MenuListner(int i) {
		this.panel = Main.panels[i];
		this.checked = true;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("action");
		if (this.checked){
			this.checked = false;
			this.panel.setToNullSize();
		} else {
			this.checked = true;
			this.panel.setToDefaultSize();
		}
		System.out.println("F");
	}

}
