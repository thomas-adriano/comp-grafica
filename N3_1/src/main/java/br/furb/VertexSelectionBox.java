package br.furb;

import javax.media.opengl.GL;

public class VertexSelectionBox {

	private final Ponto4D topRight;
	private final Ponto4D topLeft;
	private final Ponto4D botLeft;
	private final Ponto4D botRight;
	private boolean isSelected = false;

	public VertexSelectionBox(Ponto4D anchorPoint) {
		this.topRight = new Ponto4D(anchorPoint.getX() + 5, anchorPoint.getY() + 5);
		this.topLeft = new Ponto4D(anchorPoint.getX() - 5, anchorPoint.getY() + 5);

		this.botLeft = new Ponto4D(anchorPoint.getX() - 5, anchorPoint.getY() - 5);

		this.botRight = new Ponto4D(anchorPoint.getX() + 5, anchorPoint.getY() - 5);

	}

	public void draw(GL gl) {
		gl.glBegin(GL.GL_LINE_LOOP);
		
		gl.glVertex2f((float) topRight.getX() / (float) (Frame.X / 2), (float) topRight.getY() / (float) (Frame.Y / 2));
		gl.glVertex2f((float) topLeft.getX() / (float) (Frame.X / 2), (float) topLeft.getY() / (float) (Frame.Y / 2));
		gl.glVertex2f((float) botLeft.getX() / (float) (Frame.X / 2), (float) botLeft.getY() / (float) (Frame.Y / 2));
		gl.glVertex2f((float) botRight.getX() / (float) (Frame.X / 2), (float) botRight.getY() / (float) (Frame.Y / 2));
		gl.glEnd();
	}

	public boolean isSelected() {
		return isSelected;
	}
	
	public void setSelected(Ponto4D p) {
		isSelected = (p.getX() <= topRight.getX() && p.getX() >= topLeft.getX())
				&& (p.getY() <= topRight.getY() && p.getY() >= botRight.getY());
	}

	@Override
	public String toString() {
		return "VertexSelectionBox [topRight=" + topRight + ", topLeft=" + topLeft + ", botLeft=" + botLeft
				+ ", botRight=" + botRight + "]";
	}

}
