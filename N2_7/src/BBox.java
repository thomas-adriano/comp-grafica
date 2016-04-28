import javax.media.opengl.GL;

public class BBox {

	private Point2D bottomLeft;
	private Point2D topLeft;
	private Point2D topRight;
	private Point2D bottomRight;
	private Color color = new Color(1, 0, 1);
	
	private GL gl;
	
	public void setCoordinatesAndGL(GL gl, Point2D bottomLeft, Point2D topLeft, Point2D topRight, Point2D bottomRight) {
		this.bottomLeft = bottomLeft;
		this.topLeft = topLeft;
		this.topRight = topRight;
		this.bottomRight = bottomRight;
		this.gl = gl;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void draw() {		
		gl.glBegin(gl.GL_LINE_LOOP);
		gl.glColor3f(color.getRed(), color.getGreen(), color.getBlue());
		gl.glVertex2d(bottomLeft.getX(), bottomLeft.getY());
		gl.glVertex2d(topLeft.getX(), topLeft.getY());
		gl.glVertex2d(topRight.getX(), topRight.getY());
		gl.glVertex2d(bottomRight.getX(), bottomRight.getY());
		gl.glEnd();
	}

	public boolean isInside(Point2D p) {
		return (p.getX() <= this.topRight.getX() && p.getX() >= this.topLeft.getX()) &&
				(p.getY() >= this.bottomRight.getY() && p.getY() <= this.topRight.getY());
	}
	
}
