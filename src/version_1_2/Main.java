package version_1_2;

import java.awt.Dimension;
import java.util.Scanner;

import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

public class Main {
	
	public static JMenuBar menuBar;
	
	public static TimingSystem timingSystem;	
	public static AccelGraph accelGraph1;
	public static AccelGraph accelGraph2;
	public static AccelGraph accelGraph3;
	public static AverageTemp averageTemp;
	public static RPMDisplay frontRPMDisplay;
	public static RPMDisplay rearRPMDisplay;
	public static panelInterface [] panels = {timingSystem, accelGraph1, accelGraph2, accelGraph3, averageTemp, frontRPMDisplay, rearRPMDisplay};
	
	public static void main(String[] args) throws Exception {
		
		GUI_controller controller = new GUI_controller();
		JFrame mainFrame = new JFrame("DAQ");
		JPanel panel = new JPanel();
		MenuHandler menu = new MenuHandler();
		menuBar = menu.createMenuBar();

		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);

		timingSystem = new TimingSystem(mainFrame);

		accelGraph1 = new AccelGraph(controller, Constents.ZDATA1);
		accelGraph2 = new AccelGraph(controller, Constents.ZDATA2);
		accelGraph3 = new AccelGraph(controller, Constents.ZDATA3);

		averageTemp = new AverageTemp(controller);

		frontRPMDisplay = new RPMDisplay(controller, Constents.FRONTWHEELSPEED);
		rearRPMDisplay = new RPMDisplay(controller, Constents.REARWHEELSPEED);
						
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(accelGraph1, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(accelGraph2, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(accelGraph3, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(layout.createSequentialGroup()
						.addComponent(timingSystem, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(averageTemp, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(layout.createSequentialGroup()
						.addComponent(frontRPMDisplay, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(rearRPMDisplay, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(accelGraph1)
						.addComponent(accelGraph2).addComponent(accelGraph3))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(timingSystem)
						.addComponent(averageTemp))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(frontRPMDisplay)
						.addComponent(rearRPMDisplay)));


		
		Serial main = new Serial(controller);
		Scanner reader = new Scanner(System.in); // Reading from System.in
		/*while (true) {
			System.out.println("Enter a COM port ");
			String ID = reader.next();
			if (main.initialize(ID)) {
				break;
			}
		}*/
		
		main.initialize("f");
		reader.close();

		Thread t = new Thread() {
			public void run() {
				// the following line will keep this app alive for 1000 seconds,
				// waiting for events to occur and responding to them (printing
				// incoming messages to console).
				try {
					Thread.sleep(1000000);
				} catch (InterruptedException ie) {
				}
			}
		};
		Runnable r = new RedrawClock(mainFrame);
		Thread tclock = new Thread(r);
		tclock.start();

		t.start();

		mainFrame.add(panel);
		mainFrame.setJMenuBar(menuBar);


		mainFrame.pack();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		System.out.println("Started");

		
	}
}
