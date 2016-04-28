import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

public class OuterCircle {

	private Circle thisCircle;
	private ControlCircle ctrlCircle = new ControlCircle();
	private float raio = 150;
	private BBox box = new BBox();
	private Point2D center;
	public static boolean isCtrlPointOutsideOuterCircle = false;

	public void draw(GLAutoDrawable dr, GL gl, Point2D center, Point2D actualMousePos) {
		this.center = center;
		thisCircle = new Circle(gl);
		ctrlCircle.setComponents(dr, gl, center);
		drawBBox(gl, center);
		thisCircle.drawCircle(raio, center);
		ctrlCircle.draw();
		if (!box.isInside(ctrlCircle.getCenterPoint())) {
			box.setColor(new Color(1, 1, 0));
			if (isCtrlPointOutsideCircle(actualMousePos)) {
				box.setColor(new Color(0, 0, 1));
			}
		} else {
			box.setColor(new Color(1, 0, 1));
		}
	}

	// dist((x, y), (a, b)) = (x - a)² + (y - b)²
	public boolean isCtrlPointOutsideCircle(Point2D p) {
		double circleBorderX = calcX(center.getX(), raio, degreeToRad(45));
		double circleBorderY = calcY(center.getY(), raio, degreeToRad(45));
		
		double centerToCircleBorderDistance = Math.pow(center.getX() - circleBorderX, 2) + Math.pow(center.getY() - circleBorderY, 2);
		double centerToCtrlPointDistance = Math.pow(center.getX() - p.getX(), 2) + Math.pow(center.getY() - p.getY(), 2);
		isCtrlPointOutsideOuterCircle = centerToCtrlPointDistance > centerToCircleBorderDistance;
		return isCtrlPointOutsideOuterCircle;
	}

	public ControlCircle getCtrlCircle() {
		return ctrlCircle;
	}

	public void drawBBox(GL gl, Point2D center) {
		gl.glPointSize(2f);

		int boxDegreePoint = 45;
		int boxTopRightX = calcX(center.getX(), raio, degreeToRad(boxDegreePoint));
		int boxTopRightY = calcY(center.getY(), raio, degreeToRad(boxDegreePoint));

		boxDegreePoint += 90;
		int boxTopLeftX = calcX(center.getX(), raio, degreeToRad(boxDegreePoint));
		int boxTopLeftY = calcY(center.getY(), raio, degreeToRad(boxDegreePoint));

		boxDegreePoint += 90;
		int boxBotLeftX = calcX(center.getX(), raio, degreeToRad(boxDegreePoint));
		int boxBotLeftY = calcY(center.getY(), raio, degreeToRad(boxDegreePoint));

		boxDegreePoint += 90;
		int boxBotRightX = calcX(center.getX(), raio, degreeToRad(boxDegreePoint));
		int boxBotRightY = calcY(center.getY(), raio, degreeToRad(boxDegreePoint));

		box.setCoordinatesAndGL(gl, new Point2D(boxBotLeftX, boxBotLeftY), new Point2D(boxTopLeftX, boxTopLeftY),
				new Point2D(boxTopRightX, boxTopRightY), new Point2D(boxBotRightX, boxBotRightY));

		box.draw();
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
