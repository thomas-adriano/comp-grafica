package br.furb;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

public class Mundo implements MouseListener, MouseMotionListener{

	private Camera2D camera;
	private ObjetoGrafico obj = new ObjetoGrafico();
	private Ponto4D mousePoint;
	private long lastClick = 0;
	private boolean initiated = false;
	private GLAutoDrawable glDrawable;

	public Camera2D getCamera() {
		return camera;
	}

	public void setCamera(Camera2D camera) {
		this.camera = camera;
	}

	public ObjetoGrafico getObjeto() {
		return obj;
	}
	
	public void setDrawable(GLAutoDrawable d) {
		this.glDrawable = d;
	}

	public void draw(GL gl) {
		
		if (obj.hasPoints()) {
			obj.atribuirGL(gl);
			obj.desenhar();

			if (obj.hasAnyUnfinishedObject()) {

				if (mousePoint != null) {
					gl.glVertex2f((float) mousePoint.getX() / (float) (Frame.X / 2),
							(float) mousePoint.getY() / (float) (Frame.Y / 2));
				}

				gl.glEnd();
			}

			VertexSelectionBox v = obj.getSelectedVertice();
			if (v != null) {
				v.draw(gl);
			}
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		Ponto4D p = Ponto4D.mouseCoordinatesToCartesianCoordinates(new Ponto4D(e.getX(), e.getY()));
		obj.setSelectedVertex(p);
		
		initiated = true;
		long now = System.currentTimeMillis();
		long elapsed = now - lastClick;
		lastClick = now;
		System.out.println("clicked: " + lastClick + " / now: " + now);
		if (lastClick != 0 && elapsed <= 300) {
			System.out.println("lastclick");
			obj.setFinished();
			initiated = false;
			mousePoint = null;// essa linha corrige o erro do rastro (mouseMoved)
								// iniciar a partir do ultimo objetoGrafico
								// terminado. PQ?!
			return;
		}

		obj.addPonto(p);
		if (glDrawable != null) {
			glDrawable.display();
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		if (initiated) {
			mousePoint = Ponto4D.mouseCoordinatesToCartesianCoordinates(new Ponto4D(arg0.getX(), arg0.getY()));

			if (glDrawable != null) {
				glDrawable.display();
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		

	}

	@Override
	public void mouseExited(MouseEvent e) {
		

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		

	}


	public void addObjeto(ObjetoGrafico objeto) {
		if (this.obj == null) {
			this.obj = new ObjetoGrafico();
		} else {
			this.obj.addObject(objeto);
		}
	}

	@Override
	public String toString() {
		return "Mundo [camera=" + camera + ", obj=" + obj + ", mousePoint=" + mousePoint + ", lastClick=" + lastClick
				+ ", initiated=" + initiated + ", glDrawable=" + glDrawable + "]";
	}



}
