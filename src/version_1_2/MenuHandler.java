package version_1_2;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.swing.MenuSelectionManager;
import javax.swing.plaf.MenuItemUI;

public class MenuHandler extends JMenuBar{
	public MenuHandler() {
	}
	
	public JMenuBar createMenuBar(){
		JMenuBar menuBar = new JMenuBar();
		
		// Build the first menu.
		JMenu menu = new JMenu("A Menu");
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");
		menuBar.add(menu);

		// a group of JMenuItems
		JMenuItem menuItem = new JMenuItem("A text-only menu item", KeyEvent.VK_T);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");

		menu.add(menuItem);

		menuItem = new JMenuItem("Both text and icon", new ImageIcon("images/middle.gif"));
		menuItem.setMnemonic(KeyEvent.VK_B);
		menu.add(menuItem);

		menuItem = new JMenuItem(new ImageIcon("images/middle.gif"));
		menuItem.setMnemonic(KeyEvent.VK_D);
		menu.add(menuItem);

		// a group of radio button menu items
		menu.addSeparator();
		ButtonGroup group = new ButtonGroup();
		JRadioButtonMenuItem rbMenuItem = new JRadioButtonMenuItem("A radio button menu item");
		rbMenuItem.setSelected(true);
		rbMenuItem.setMnemonic(KeyEvent.VK_R);
		group.add(rbMenuItem);
		menu.add(rbMenuItem);

		rbMenuItem = new JRadioButtonMenuItem("Another one");
		rbMenuItem.setMnemonic(KeyEvent.VK_O);
		group.add(rbMenuItem);
		menu.add(rbMenuItem);

		// a group of check box menu items
		menu.addSeparator();
		JCheckBoxMenuItem cbMenuItem = new JCheckBoxMenuItem("A check box menu item");
		cbMenuItem.setMnemonic(KeyEvent.VK_C);
		menu.add(cbMenuItem);

		cbMenuItem = new JCheckBoxMenuItem("Another one");
		cbMenuItem.setMnemonic(KeyEvent.VK_H);
		menu.add(cbMenuItem);

		// a submenu
		menu.addSeparator();
		JMenu submenu = new JMenu("Panel Select");
		submenu.setMnemonic(KeyEvent.VK_S);

		menuItem = new JCheckBoxMenuItem("Timer", true);
		menuItem.addActionListener(new MenuListner(0));
		submenu.add(menuItem);
		
		menuItem = new JCheckBoxMenuItem("Accel1", true);
		menuItem.addActionListener(new MenuListner(1));
		submenu.add(menuItem);
		
		menuItem = new JCheckBoxMenuItem("Accel2", true);
		menuItem.addActionListener(new MenuListner(2));
		submenu.add(menuItem);
		
		menuItem = new JCheckBoxMenuItem("Accel3", true);
		menuItem.addActionListener(new MenuListner(3));
		submenu.add(menuItem);
		
		menuItem = new JCheckBoxMenuItem("Temp", true);
		menuItem.addActionListener(new MenuListner(4));
		submenu.add(menuItem);
		
		menuItem = new JCheckBoxMenuItem("WheelSpeed1", true);
		menuItem.addActionListener(new MenuListner(5));
		submenu.add(menuItem);
		
		menuItem = new JCheckBoxMenuItem("WheelSpeed2", true);
		menuItem.addActionListener(new MenuListner(6));
		submenu.add(menuItem);
		
		
		menu.add(submenu);
		
		
		
		//Build second menu in the menu bar.
		menu = new JMenu("Another Menu");
		menu.setMnemonic(KeyEvent.VK_N);
		menu.getAccessibleContext().setAccessibleDescription(
		        "This menu does nothing");
		menuBar.add(menu);
		
		return menuBar;
	}

}
