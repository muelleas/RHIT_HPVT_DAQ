package version_1_2;

import java.awt.Dimension;

public class Constents {
	public static int ZDATA1 = 0;  //do not repeat numbers
	public static int ZDATA2 = 1;
	public static int ZDATA3 = 2;
	public static int AVGTEMP = 3;
	public static int FRONTWHEELSPEED = 4;
	public static int REARWHEELSPEED = 5;
	
	
	public static int[] DATATOSTORE = {ZDATA1, ZDATA2, ZDATA3};
	
	
	public static double WHEELCIRCUMFERENCE =.660*2*Math.PI;  // in meters
	public static double KPH2MPH = .621;
	
	public static Dimension standardlargeSize = new Dimension(200, 300);
	public static Dimension standardSmallSize = new Dimension(100, 100);
	public static Dimension nullSize = new Dimension(1, 1);
}
