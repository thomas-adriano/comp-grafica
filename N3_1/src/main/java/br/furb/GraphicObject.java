package br.furb;

import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GL2;

public class GraphicObject {

	private final long id;
	private List<Point4D> points = new ArrayList<Point4D>();
	private List<GraphicObject> childs = new ArrayList<GraphicObject>();
	private BBox bbox;
	private Transformacao transformacao;
	private float[] cor;
	private double primitiva;
	private boolean finished = false;
	private Color bboxColor = new Color(0, 0, 0);

	public GraphicObject(long id) {
		this.id = id;
	}

	public void setBboxColor(Color c) {
		this.bboxColor = c;
	}

	public void drawBBox(GL2 g) {
		bbox.draw(bboxColor, g);
	}

	public void removeLastVertex() {
		points.remove(points.size() - 1);
	}

	public void draw(GL2 gl) {
		if (!points.isEmpty()) {

			gl.glBegin(GL2.GL_LINE_STRIP);
			for (Point4D p : points) {
				gl.glVertex2f((float) p.getX() / (float) (Frame.X / 2), (float) p.getY() / (float) (Frame.Y / 2));
			}

			if (finished) {
				gl.glEnd();

				for (GraphicObject o : childs) {
					o.draw(gl);
				}
			}

		}
	}

	public boolean hasAnyUnfinishedObject() {
		if (!finished) {
			return true;
		}

		for (GraphicObject o : childs) {
			return o.hasAnyUnfinishedObject();
		}

		return false;

	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished() {
		if (!finished) {
			this.finished = true;
			// childs.add(new GraphicObject(World.generateNewObjectId()));
			return;
		} else {
			for (GraphicObject o : childs) {
				o.setFinished();
			}
		}
	}

	public void trocarPrimitiva() {

	}

	public void trocarCor() {

	}

	public void addPonto(Point4D p) {
		if (!finished) {
			points.add(p);
		}
	}

	public void addChild(GraphicObject o) {
		this.childs.add(o);
	}

	public boolean hasPoints() {
		if (!points.isEmpty()) {
			return true;
		} else {
			for (GraphicObject o : childs) {
				return o.hasPoints();
			}
		}
		return false;
	}

	public void addObject(GraphicObject o) {
		this.childs.add(o);
	}

	public GraphicObject getSelectedObject(Point4D point) {
		if (isInsideBBox(point)) {
			return this;
		} else {
			GraphicObject selected = null;
			for (GraphicObject o : childs) {
				selected = o.getSelectedObject(point);
				if (selected != null) {
					return selected;
				}
			}
		}
		return null;
	}

	public boolean isInsideBBox(Point4D p) {
		bbox = getBBox();
		return bbox != null && bbox.isInside(p);
	}

	public BBox getBBox() {
		if (points.isEmpty()) {
			return null;
		}

		int minX = points.get(0).getX();
		int minY = points.get(0).getY();
		int maxX = points.get(0).getX();
		int maxY = points.get(0).getY();
		for (int i = 1; i < points.size(); i++) {
			Point4D p = points.get(i);

			if (p.getX() < minX) {
				minX = p.getX();
			}
			if (p.getY() < minY) {
				minY = p.getY();
			}

			if (p.getX() > maxX) {
				maxX = p.getX();
			}
			if (p.getY() > maxY) {
				maxY = p.getY();
			}

		}

		return new BBox(minX, minY, maxX, maxY);
	}

	@Override
	public String toString() {
		return "GraphicObject [pontos=" + points + ", objetos=" + childs + ", finished=" + finished + "]";
	}

	public String getInfos() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("ID: " + id);
		sb.append("	Points: " + points.size());
		sb.append("	Childs: " + childs.size());
		sb.append("	Finished: " + finished);
		sb.append("}");

		for (GraphicObject g : childs) {
			sb.append(g.getInfos());
		}

		return sb.toString();
	}

}
