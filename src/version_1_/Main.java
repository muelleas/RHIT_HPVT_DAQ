package version_1_;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
	public static void main(String[] args) throws Exception {

		GUI_controller controller = new GUI_controller();
		JFrame mainFrame = new JFrame("DAQ");
		
		AccelGraph accelGraph = new AccelGraph(controller);
		accelGraph.setPreferredSize(new Dimension(200,300));
		AverageTemp averageTemp = new AverageTemp(controller);
		averageTemp.setPreferredSize(new Dimension(200,100));
		
		JPanel accelGraphPanel = new JPanel();
		accelGraphPanel.add(accelGraph);
		accelGraphPanel.setBorder(BorderFactory.createTitledBorder("Accel Data"));
		mainFrame.add(accelGraphPanel, BorderLayout.NORTH);

		JPanel AverageTempPanel = new JPanel();
		AverageTempPanel.add(averageTemp);
		AverageTempPanel.setBorder(BorderFactory.createTitledBorder("Temp Data"));
		mainFrame.add(AverageTempPanel, BorderLayout.SOUTH);

		Serial main = new Serial(controller);
		Scanner reader = new Scanner(System.in); // Reading from System.in
		while (true) {

			System.out.println("Enter a COM port ");
			String ID = reader.next();
			if (main.initialize(ID)) {
				break;
			}
		}
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
		Runnable r = new Clock(mainFrame);
		Thread tclock = new Thread(r);
		tclock.start();

		t.start();

		
		mainFrame.pack();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		System.out.println("Started");
	}
}






/*
JPanel accelGraphPanel = new JPanel();
accelGraphPanel.add(new accelGraph(controller));
accelGraphPanel.setLayout(new FlowLayout());
accelGraphPanel.setBorder(BorderFactory.createTitledBorder("Accel Data"));
mainFrame.add(accelGraphPanel, BorderLayout.SOUTH);

JPanel AverageTempPanel = new JPanel();
AverageTempPanel.add(new AverageTemp(controller));
AverageTempPanel.setLayout(new FlowLayout());
AverageTempPanel.setBorder(BorderFactory.createTitledBorder("Temp Data"));
mainFrame.add(AverageTempPanel, BorderLayout.NORTH);*/

/*Serial main = new Serial(controller);
Scanner reader = new Scanner(System.in); // Reading from System.in
while (true) {

	System.out.println("Enter a COM port ");
	String ID = reader.next();
	if (main.initialize(ID)) {
		break;
	}
}
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
Runnable r = new Clock(controller, mainFrame);
Thread tclock = new Thread(r);
tclock.start();

//t.start();*/