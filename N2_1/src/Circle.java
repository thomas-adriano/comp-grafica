import javax.media.opengl.GL;

public final class Circle {
	private double menorX;
	private double menorY;
	private double maiorX;
	private double maiorY;
	private final Point2D centro;

	public Circle() {
		this(0, 0, 0, 0);
	}

	public Circle(double smallerX, double smallerY, double greaterX, double greaterY) {
		this.menorX = smallerX;
		this.menorY = smallerY;
		this.maiorX = greaterX;
		this.maiorY = greaterY;

		double x = (maiorX + menorX) / 2;
		double y = (maiorY + menorY) / 2;
		centro = new Point2D(x, y);
	}

	public void desenharCirculo(GL gl) {
		gl.glColor3f(0.0f, 0.0f, 1.0f);

		gl.glPointSize(2f);

		double degreeToRad = Math.PI / 180;
		double raio = 100.0;
		double interval = 360 / 72;

		for (double i = 0.0; i < 360; i += interval) {
			double degInRad = i * degreeToRad;
			gl.glBegin(GL.GL_POINTS);
			gl.glVertex2d(Math.cos(degInRad) * raio, Math.sin(degInRad) * raio);
			gl.glEnd();
		}
	}

	/// Obter menor valor X da BBox.
	public double obterMenorX() {
		return menorX;
	}

	/// Obter menor valor Y da BBox.
	public double obterMenorY() {
		return menorY;
	}

	/// Obter maior valor X da BBox.
	public double obterMaiorX() {
		return maiorX;
	}

	/// Obter maior valor Y da BBox.
	public double obterMaiorY() {
		return maiorY;
	}

	/// Obter ponto do centro da BBox.
	public Point2D obterCentro() {
		return centro;
	}

}
