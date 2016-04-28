package br.furb;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

public class ObjetoGrafico {

	private List<Ponto4D> pontos = new ArrayList<Ponto4D>();
	private List<VertexSelectionBox> vertexSelections = new ArrayList<VertexSelectionBox>();
	private List<ObjetoGrafico> objetos = new ArrayList<ObjetoGrafico>();
	private BBox bbox;
	private Transformacao transformacao;
	private float[] cor;
	private double primitiva;
	private GL gl;
	private boolean finished = false;

	public void atribuirGL(GL gl) {
		this.gl = gl;
	}

	public VertexSelectionBox getSelectedVertice() {
		for (VertexSelectionBox a : vertexSelections) {
			if (a.isSelected()) {
				return a;
			}
		}
		return null;
	}

	public void desenhar() {
		if (!pontos.isEmpty()) {
			System.out.println("desenhando objeto...");
			gl.glBegin(GL.GL_LINE_STRIP);
			for (Ponto4D p : pontos) {
				gl.glVertex2f((float) p.getX() / (float) (Frame.X / 2), (float) p.getY() / (float) (Frame.Y / 2));
			}

			if (finished) {
				gl.glEnd();

				for (ObjetoGrafico o : objetos) {
					o.atribuirGL(gl);
					o.desenhar();
				}
			}

		}
	}

	public boolean hasAnyUnfinishedObject() {
		if (!finished) {
			return true;
		}

		for (ObjetoGrafico o : objetos) {
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
			objetos.add(new ObjetoGrafico());
			return;
		} else {
			for (ObjetoGrafico o : objetos) {
				o.setFinished();
			}
		}
	}

	public void trocarPrimitiva() {

	}

	public void trocarCor() {

	}

	public void setSelectedVertex(Ponto4D p) {
		if (!hasAnyUnfinishedObject()) {
			for (VertexSelectionBox v : vertexSelections) {
				v.setSelected(p);
			}
		}
	}

	public void addPonto(Ponto4D p) {
		if (!finished) {
			pontos.add(p);
			vertexSelections.add(new VertexSelectionBox(p));

		} else {
			for (ObjetoGrafico o : objetos) {
				o.addPonto(p);
			}
		}
	}

	public boolean hasPoints() {
		if (!pontos.isEmpty()) {
			return true;
		} else {
			for (ObjetoGrafico o : objetos) {
				return o.hasPoints();
			}
		}
		return false;
	}

	public void addObject(ObjetoGrafico o) {
		this.objetos.add(o);
	}
	// TODO FALTAM DOIS METODOS

}
