import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

public class ControlCircle implements MouseMotionListener, MouseListener {

	private Circle circle;
	private CenterPoint centerPoint;
	private Point2D pos;
	private Point2D center;
	private GLAutoDrawable dr;
	private Point2D mousePosition;
	private GL gl;
	private float raio = 60;

	public ControlCircle() {
	}

	public void setComponents(GLAutoDrawable dr, GL gl, Point2D center) {
		if (pos == null) {
			this.pos = new Point2D(center.getX(), center.getY());
			this.center = new Point2D(center.getX(), center.getY());
		}
		this.dr = dr;
		this.circle = new Circle(gl);
		this.centerPoint = new CenterPoint(gl);
	}

	public void draw() {
		circle.drawCircle(raio, pos);
		centerPoint.drawCenterPoint(pos);
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		int x = arg0.getX() - mousePosition.getX();
		int y = arg0.getY() - mousePosition.getY();
		if (!OuterCircle.isCtrlPointOutsideOuterCircle) {
			pos = new Point2D(pos.getX() + x, pos.getY() - y);

			mousePosition = new Point2D(arg0.getX(), arg0.getY());

		}
		Renderer.atualMousePos = new Point2D(pos.getX() + x, pos.getY() - y);

		System.out.println(pos);

		System.out.println(OuterCircle.isCtrlPointOutsideOuterCircle);
		dr.display();
	}

	public Point2D getCenterPoint() {
		return pos;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("pressed");
		mousePosition = new Point2D(e.getX(), e.getY());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		pos = center;
		dr.display();
		System.out.println("released");
		OuterCircle.isCtrlPointOutsideOuterCircle = false;
		dr.display();
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
