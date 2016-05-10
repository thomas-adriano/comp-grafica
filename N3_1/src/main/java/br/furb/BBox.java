package br.furb;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

public class BBox {

	private final int minX;
	private final int minY;
	private final int maxX;
	private final int maxY;

	public BBox(int minX, int minY, int maxX, int maxY) {
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
	}

	public BBox(Point4D anchorPoint, int radio) {
		this.maxY = anchorPoint.getY() + radio;
		this.maxX = anchorPoint.getX() + radio;
		this.minY = anchorPoint.getY() - radio;
		this.minX = anchorPoint.getX() - radio;
	}

	public void draw(Color c, GL2 gl) {
		gl.glColor3f(c.getR(), c.getG(), c.getB());

		Point4D topRight = new Point4D(maxX, maxY);
		Point4D topLeft = new Point4D(minX, maxY);
		Point4D botLeft = new Point4D(minX, minY);
		Point4D botRight = new Point4D(maxX, minY);

		gl.glBegin(GL.GL_LINE_LOOP);

		gl.glVertex2f((float) topRight.getX() / (float) (Frame.X / 2), (float) topRight.getY() / (float) (Frame.Y / 2));
		gl.glVertex2f((float) topLeft.getX() / (float) (Frame.X / 2), (float) topLeft.getY() / (float) (Frame.Y / 2));
		gl.glVertex2f((float) botLeft.getX() / (float) (Frame.X / 2), (float) botLeft.getY() / (float) (Frame.Y / 2));
		gl.glVertex2f((float) botRight.getX() / (float) (Frame.X / 2), (float) botRight.getY() / (float) (Frame.Y / 2));
		gl.glEnd();
	}

	public boolean isInside(Point4D p) {
		return (p.getX() <= maxX && p.getX() >= minX) && (p.getY() <= maxY && p.getY() >= minY);
	}

	@Override
	public String toString() {
		return "BBox [minX=" + minX + ", minY=" + minY + ", maxX=" + maxX + ", maxY=" + maxY + "]";
	}

}
