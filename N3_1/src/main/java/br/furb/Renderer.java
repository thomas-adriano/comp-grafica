package br.furb;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class Renderer implements GLEventListener {
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;
	private MouseEventListener mouseListener = new MouseEventListener();
	private long lastClick = 0;

	// private ObjetoGrafico objeto = new ObjetoGrafico();
	// private ObjetoGrafico[] objetos = { new ObjetoGrafico(), new
	// ObjetoGrafico() };

	// "render" feito logo apos a inicializacao do contexto OpenGL.
	public void init(GLAutoDrawable drawable) {
		System.out.println("INIT");
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));

		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

		// for (byte i = 0; i < objetos.length; i++) {
		// objetos[i].atribuirGL(gl);
		// }

	}

	@Override
	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();

		gl.glLineWidth(1.0f);
		gl.glColor3f(0.0f, 0.0f, 0.0f);

		mouseListener.draw();

		gl.glFlush();
	}

	@Override
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub

	}

	public MouseListener getMouseListener() {
		return mouseListener;
	}

	public MouseMotionListener getMouseMotionListener() {
		return mouseListener;
	}

	public class MouseEventListener implements MouseListener, MouseMotionListener {

		private Ponto4D mousePoint;
		private long lastClick = 0;
		private boolean initiated = false;
		private ObjetoGrafico obj = new ObjetoGrafico();

		public void draw() {
			System.out.println("obj has points? " + obj.hasPoints());
			if (obj.hasPoints()) {
				obj.atribuirGL(gl);
				obj.desenhar();

				if (obj.hasAnyUnfinishedObject()) {

					if (mousePoint != null) {
						gl.glVertex2f((float) mousePoint.getX() / (float) (Frame.X / 2),
								(float) mousePoint.getY() / (float) (Frame.Y / 2));
					}

					// so e necessario chamar o end aqui se algum objeto ainda
					// estiver sendo desenhado
					gl.glEnd();
				}

				for (VertexSelectionBox v : obj.getSelectedVertices()) {
					v.draw(gl);
				}
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			initiated = true;
			long now = System.currentTimeMillis();
			long elapsed = now - lastClick;
			lastClick = now;
			System.out.println("clicked: " + lastClick + " / now: " + now);
			if (lastClick != 0 && elapsed <= 300) {
				System.out.println("lastclick");
				obj.setFinished();
				initiated = false;
				mousePoint = null;// isso corrige o erro do rastro (mouseMove)
									// iniciar a partir do ultimo objetoGrafico
									// terminado. PQ?
				return;
			}

			Ponto4D p = Ponto4D.mouseCoordinatesToCartesianCoordinates(new Ponto4D(e.getX(), e.getY()));
			obj.addPonto(p);
			glDrawable.display();
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			if (initiated) {
				System.out.println("moved..");
				mousePoint = Ponto4D.mouseCoordinatesToCartesianCoordinates(new Ponto4D(arg0.getX(), arg0.getY()));
				glDrawable.display();
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
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

}
