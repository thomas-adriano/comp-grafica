

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;


public class Main implements GLEventListener, KeyListener {
	private float ortho2D_minX = -400.0f, ortho2D_maxX = 400.0f, ortho2D_minY = -400.0f, ortho2D_maxY = 400.0f;
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;
	private double dAngulo;
	private double dRaio;
	private Point v1;
	private static final int tam = 10;
	//private Point v2;

	public void init(GLAutoDrawable drawable) {
		dAngulo = 45;
		dRaio = 100;
		v1 = new Point(0, 0);
		//v2 = new Point(50, 50);
		System.out.println(" --- init ---");
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));
		System.out.println("Espaco de desenho com tamanho: " + drawable.getWidth() + " x " + drawable.getHeight());
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
	}

	// exibicaoPrincipal
	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		glu.gluOrtho2D(ortho2D_minX, ortho2D_maxX, ortho2D_minY, ortho2D_maxY);

		SRU();

		gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glLineWidth(3.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex2d(v1.x, v1.y);
		gl.glVertex2d(dRaio * Math.cos(Math.PI * dAngulo / 180.0) + v1.x,
				dRaio * Math.sin(Math.PI * dAngulo / 180.0) + v1.y);
		gl.glEnd();
		gl.glFlush();
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_R:
			System.out.println(ortho2D_maxX);
			if (ortho2D_maxX > 100 && ortho2D_minX < -100) {
				ortho2D_minX += 10;
				ortho2D_maxX -= 10;
				ortho2D_minY += 10;
				ortho2D_maxY -= 10;
			}
			glDrawable.display();
			break;
		case KeyEvent.VK_F:
			if ((ortho2D_maxX + Math.abs(ortho2D_minX)) < 1400) {
				ortho2D_minX -= 10;
				ortho2D_maxX += 10;
				ortho2D_minY -= 10;
				ortho2D_maxY += 10;
			}
			glDrawable.display();
			break;
		case KeyEvent.VK_T:
			ortho2D_minX = -400;
			ortho2D_maxX = 400;
			ortho2D_minY = -400;
			ortho2D_maxY = 400;
			glDrawable.display();
			break;
		case KeyEvent.VK_E:
			if (ortho2D_minX < 0) {
				ortho2D_minX += tam;
				ortho2D_maxX += tam;
			}
			glDrawable.display();
			break;
		case KeyEvent.VK_D:
			if (ortho2D_maxX > 0) {
				ortho2D_minX -= tam;
				ortho2D_maxX -= tam;
			}
			glDrawable.display();
			break;
		case KeyEvent.VK_C:
			if (ortho2D_maxY > 0) {
				ortho2D_minY -= tam;
				ortho2D_maxY -= tam;
			}
			;
			glDrawable.display();
			break;
		case KeyEvent.VK_V:
			if (ortho2D_minY < 0) {
				ortho2D_minY += tam;
				ortho2D_maxY += tam;
			}
			glDrawable.display();
			break;
		case KeyEvent.VK_Q:
				v1.x -= tam;
			glDrawable.display();
			break;
		case KeyEvent.VK_W:
				v1.x += tam;
			glDrawable.display();
			break;
		case KeyEvent.VK_A:
			dRaio -= tam;
			glDrawable.display();
			break;
		case KeyEvent.VK_S:
				dRaio += tam;
			glDrawable.display();
			break;
		case KeyEvent.VK_Z:
				dAngulo -= tam;
			glDrawable.display();
			break;
		case KeyEvent.VK_X:
				dAngulo += tam;
			glDrawable.display();
			break;
		}
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		System.out.println(" --- reshape ---");
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glViewport(0, 0, width, height);
	}

	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		System.out.println(" --- displayChanged ---");
	}

	public void keyReleased(KeyEvent arg0) {
		System.out.println(" --- keyReleased ---");
	}

	public void keyTyped(KeyEvent arg0) {
		System.out.println(" --- keyTyped ---");
	}

	public void SRU() {
		// gl.glDisable(gl.GL_TEXTURE_2D);
		// gl.glDisableClientState(gl.GL_TEXTURE_COORD_ARRAY);
		// gl.glDisable(gl.GL_LIGHTING); //TODO: [D] FixMe: check if lighting
		// and texture is enabled

		// eixo x
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glLineWidth(1.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex2f(-200.0f, 0.0f);
		gl.glVertex2f(200.0f, 0.0f);
		gl.glEnd();
		// eixo y
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex2f(0.0f, -200.0f);
		gl.glVertex2f(0.0f, 200.0f);
		gl.glEnd();
	}

}
