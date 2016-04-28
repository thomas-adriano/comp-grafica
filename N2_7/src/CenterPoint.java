import javax.media.opengl.GL;

public class CenterPoint {

	private final GL gl;
	private Point2D coords;
	
	public CenterPoint(GL gl) {
		this.gl = gl;
	}
	
	public Point2D getPoint() {
		return coords;
	}
	
	public void drawCenterPoint(Point2D coordinates) {
		this.coords = coordinates;
		gl.glPointSize(4);
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		
		gl.glBegin(gl.GL_POINTS);
		gl.glVertex2d(coordinates.getX(), coordinates.getY());
		gl.glEnd();
	}
	
}
