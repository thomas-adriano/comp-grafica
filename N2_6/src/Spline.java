import javax.media.opengl.GL;

public class Spline {

	private final GL gl;
	private char activeControlPoint;
	private Point2D activeControlPointCoordinates;

	public Spline(GL gl) {
		this.gl = gl;
	}

	public void drawSpline(Point2D p0, Point2D p1, Point2D p2, Point2D p3, float splinePoints,
			Point2D activeControlPointCoordinates, char activeControlPoint) {
		this.activeControlPoint = activeControlPoint;
		this.activeControlPointCoordinates = activeControlPointCoordinates;
		drawControlPoint(activeControlPointCoordinates);
		drawControlPoliedros(p0, p1, p2, p3);
		doDrawSpline(p0, p1, p2, p3, splinePoints);
	}

	public void drawControlPoint(Point2D c) {
		gl.glColor3f(1, 0, 0);
		gl.glPointSize(6f);

		gl.glBegin(GL.GL_POINTS);
		gl.glVertex2f(c.getX(), c.getY());
		gl.glEnd();

	}

	public void drawControlPoliedros(Point2D p0, Point2D p1, Point2D p2, Point2D p3) {
		gl.glColor3f(0, 1, 1);

		gl.glLineWidth(2);

		gl.glBegin(GL.GL_LINE_STRIP);
		if (activeControlPoint == 'a') {
			gl.glVertex2f(activeControlPointCoordinates.getX(), activeControlPointCoordinates.getY());
		} else {
			gl.glVertex2f(p0.getX(), p0.getY());
		}

		if (activeControlPoint == 'b') {
			gl.glVertex2f(activeControlPointCoordinates.getX(), activeControlPointCoordinates.getY());
		} else {
			gl.glVertex2f(p1.getX(), p1.getY());
		}

		if (activeControlPoint == 'c') {
			gl.glVertex2f(activeControlPointCoordinates.getX(), activeControlPointCoordinates.getY());
		} else {
			gl.glVertex2f(p2.getX(), p2.getY());
		}

		if (activeControlPoint == 'd') {
			gl.glVertex2f(activeControlPointCoordinates.getX(), activeControlPointCoordinates.getY());
		} else {
			gl.glVertex2f(p3.getX(), p3.getY());
		}
		gl.glEnd();

	}

	public void doDrawSpline(Point2D p0, Point2D p1, Point2D p2, Point2D p3, float splinePoints) {
		gl.glColor3f(1, 1, 0);

		System.out.println("draw spline activeControlPoint: "+activeControlPoint);
		System.out.println("draw spline activeControlPointCoordinates: "+activeControlPointCoordinates);
		
		float tInterval = 1f / splinePoints;

		gl.glBegin(GL.GL_LINE_STRIP);
		for (float t = 0.0f; t <= 1; t += tInterval) {

			if (activeControlPoint == 'a') {
				float x = bezier(t, activeControlPointCoordinates.getX(), p1.getX(), p2.getX(), p3.getX());
				float y = bezier(t, activeControlPointCoordinates.getY(), p1.getY(), p2.getY(), p3.getY());
				gl.glVertex2f(x, y);
			}

			if (activeControlPoint == 'b') {
				float x = bezier(t, p0.getX(), activeControlPointCoordinates.getX(), p2.getX(), p3.getX());
				float y = bezier(t, p0.getY(), activeControlPointCoordinates.getY(), p2.getY(), p3.getY());
				gl.glVertex2f(x, y);
			}

			if (activeControlPoint == 'c') {
				float x = bezier(t, p0.getX(), p1.getX(), activeControlPointCoordinates.getX(), p3.getX());
				float y = bezier(t, p0.getY(), p1.getY(), activeControlPointCoordinates.getY(), p3.getY());
				gl.glVertex2f(x, y);
			}

			if (activeControlPoint == 'd') {
				float x = bezier(t, p0.getX(), p1.getX(), p2.getX(), activeControlPointCoordinates.getX());
				float y = bezier(t, p0.getY(), p1.getY(), p2.getY(), activeControlPointCoordinates.getY());
				gl.glVertex2f(x, y);
			}
		}
		if (activeControlPoint == 'd') {
			gl.glVertex2f(activeControlPointCoordinates.getX(), activeControlPointCoordinates.getY());
		} else {
			gl.glVertex2f(p3.getX(), p3.getY());
		}

		gl.glEnd();

	}

	private float bezier(float t, float p0, float p1, float p2, float p3) {
		return (float) (bezierP0(t, p0) + bezierP1(t, p1) + bezierP2(t, p2) + bezierP3(t, p3));
	}

	private double bezierP0(float t, float p0) {
		return Math.pow(1 - t, 3) * p0;
	}

	private double bezierP1(float t, float p1) {
		return 3 * t * Math.pow(1 - t, 2) * p1;
	}

	private double bezierP2(float t, float p2) {
		return 3 * Math.pow(t, 2) * (1 - t) * p2;
	}

	private double bezierP3(float t, float p3) {
		return Math.pow(t, 3) * p3;
	}

}
