import javax.media.opengl.GL;

public final class Circle {
	private GL gl;

	public Circle(GL gl) {
		this.gl = gl;
	}

	public void desenharCirculo(double raio, float xOffset, float yOffset) {
		gl.glColor3f(0.0f, 0.0f, 0.0f);

		gl.glPointSize(2f);

		double degreeToRad = Math.PI / 180;
		double interval = 360 / 72;
		
		for (double i = 0.0; i < 360; i += interval) {
			double degInRad = i * degreeToRad;
			gl.glBegin(GL.GL_POINTS);
			gl.glVertex2d((Math.cos(degInRad) * raio) + xOffset, (Math.sin(degInRad) * raio) + yOffset);
			gl.glEnd();
		}
	}

}
