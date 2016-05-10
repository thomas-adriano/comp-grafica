package br.furb;

public class Point4D {

	private int x;
	private int y;
	private int z;
	private int w;

	public Point4D(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Point4D minus(Point4D b) {
		return new Point4D(getX() - b.getX(), getY() - b.getY());
	}

	public Point4D plus(Point4D b) {
		return new Point4D(getX() + b.getX(), getY() + b.getY());
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	@Override
	public String toString() {
		return "Ponto4D [x=" + x + ", y=" + y + "]";
	}

	public static Point4D mouseCoordinatesToCartesianCoordinates(Point4D mouseCoord) {
		int x = 0;
		int y = 0;
		int middleX = Frame.X / 2;
		int middleY = Frame.Y / 2;
		int mouseX = mouseCoord.getX();
		int mouseY = mouseCoord.getY();

		x = mouseX - middleX;
		y = middleY - mouseY;

		return new Point4D(x, y);
	}

}
