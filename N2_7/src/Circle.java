import javax.media.opengl.GL;

public final class Circle {
	private GL gl;
	private BBox box = new BBox();

	public Circle(GL gl) {
		this.gl = gl;
	}

	public void drawCircle(float raio, Point2D center) {
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		
		double degreeInterval = 360 / 72;

		gl.glBegin(GL.GL_LINE_LOOP);
		for (double i = 0.0; i < 360; i += degreeInterval) {
			double rad = degreeToRad(i);
			double x = calcX(center.getX(), raio, rad);
			double y = calcY(center.getY(), raio, rad);
			gl.glVertex2d(x, y);
		}
		gl.glEnd();
	}

	private static int calcX(int xCenter, float raio, double rad) {
		double x = Math.cos(rad) * raio;
		return ((int) x) + xCenter;
	}

	private static int calcY(int yCenter, float raio, double rad) {
		double x = Math.sin(rad) * raio;
		return ((int) x) + yCenter;
	}

	private static double degreeToRad(double degree) {
		double degreeToRad = Math.PI / 180; // quanto um grau vale em radiano
		return degree * degreeToRad;
	}

}
