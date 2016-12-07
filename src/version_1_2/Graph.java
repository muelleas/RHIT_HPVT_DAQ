package version_1_2;

import java.awt.Graphics2D;

public class Graph {

	private int xUnit = 1;
	private int yUnit = 50;
	private int xAxis = 50;
	private int yAxis = 200;

	public Graph() {

	}

	public void draw(double[] array, Graphics2D g2) {
		g2.translate(this.xAxis, this.yAxis);
		for (int i = 0; i < array.length; i++) {
			double height = this.yUnit * array[i];
			if (height > 0) {
				g2.translate(0,-height);
				g2.drawRect(i * this.xUnit, 0, this.xUnit, (int) (height));
				g2.translate(0,height);

			} else{
				g2.drawRect( i * this.xUnit, 0, this.xUnit, (int) (-height));
			}
		}
		g2.translate(-this.xAxis, -this.yAxis);

	}
}
