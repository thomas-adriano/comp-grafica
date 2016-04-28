import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class Renderer implements GLEventListener, MouseListener, MouseMotionListener {

	// desenhar um círculo no centro do Sistema de Referência do Universo (SRU)

	/*
	 * raio de valor 100 72 pontos simetricamente distribuídos sobre o perímetro
	 * do círculo
	 * 
	 * OBS.: desenhe os eixos x e y, cada um com comprimento igual a 400 (-200 à
	 * 200);
	 * 
	 * utilize as funções sin(ang) e cos(ang) da biblioteca math.h (ver material
	 * apoio no final). '=
	 * 
	 * Lembre-se que essas funções recebem o ângulos em radianos e não em graus;
	 * 
	 * não é permitido usar o comando circle do OpenGL e nem outra implementação
	 * que não use as funções sin(ang) e cos(ang) disponíveis abaixo.
	 * 
	 * Lembre que: radiano = grau * PI / 180; Então:
	 * 
	 * public double RetornaX(double angulo, double raio) { return (raio *
	 * Math.cos(Math.PI * angulo / 180.0)); }
	 * 
	 * public double RetornaY(double angulo, double raio) { return (raio *
	 * Math.sin(Math.PI * angulo / 180.0)); }
	 *
	 */

	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;
	private final Point2D MIN_POINT = new Point2D(100f, 100f);
	private final Point2D MAX_POINT = new Point2D(200f, 200f);
	private Spline spline;
	private float raio = 100;
	private float sruMinX = -200;
	private float sruMaxX = 200;
	private float sruMinY = -200;
	private float sruMaxY = 200;

	private float orthoLeftAndBottom = -400;
	private float orthoRightAndTop = 400;

	private float sruXLineYAxis = 0.0f;
	private float sruYLineXAxis = 0.0f;

	private float oldY;
	private float oldX;
	private float actualY = -100;
	private float actualX = -100;
	private char activeControlPoint = 'a'; // indicador de ponto de controle inicial = a
	private Point2D p0 = new Point2D(-100, -100);
	private Point2D p1 = new Point2D(-100, 100);
	private Point2D p2 = new Point2D(100, 100);
	private Point2D p3 = new Point2D(100, -100);
	private Point2D activeControlPointCoordinates = p0;

	private ControlPointChangerKeyListener zoomListener = new ControlPointChangerKeyListener();

	public ControlPointChangerKeyListener getControlPointChangerKeyListener() {
		return zoomListener;
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		System.out.println(" --- init ---");
		glDrawable = drawable;
		gl = drawable.getGL();
		spline = new Spline(gl);
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));
		System.out.println("Espaco de desenho com tamanho: " + drawable.getWidth() + " x " + drawable.getHeight());
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

	}

	// exibicaoPrincipal
	@Override
	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();

		glu.gluOrtho2D(orthoLeftAndBottom, orthoRightAndTop, orthoLeftAndBottom, orthoRightAndTop);

		drawSRU();

		spline.drawSpline(p0, p1, p2, p3, 10, activeControlPointCoordinates, activeControlPoint);

		gl.glFlush();
	}

	public void drawSRU() {
		gl.glDisable(gl.GL_TEXTURE_2D);
		gl.glDisableClientState(gl.GL_TEXTURE_COORD_ARRAY);
		gl.glDisable(gl.GL_LIGHTING); // TODO: [D] FixMe: check if lighting
		// and texture is enabled

		gl.glLineWidth(1.0f);

		// eixo x
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex2f(sruMinX, sruXLineYAxis);
		gl.glVertex2f(sruMaxX, sruXLineYAxis);
		gl.glEnd();

		// eixo y
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex2f(sruYLineXAxis, sruMinY);
		gl.glVertex2f(sruYLineXAxis, sruMaxY);
		gl.glEnd();

	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		System.out.println(" --- reshape ---");
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glViewport(0, 0, width, height);
	}

	@Override
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		System.out.println(" --- displayChanged ---");
	}

	public class ControlPointChangerKeyListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent ev) {
			System.out.println(ev.getKeyCode() == KeyEvent.VK_1);
			switch (ev.getKeyCode()) {
			case KeyEvent.VK_1:
				activeControlPoint = 'a';
				activeControlPointCoordinates = p0;
				glDrawable.display();
				break;
			case KeyEvent.VK_2:
				activeControlPoint = 'b';
				activeControlPointCoordinates = p1;
				glDrawable.display();
				break;
			case KeyEvent.VK_3:
				activeControlPoint = 'c';
				activeControlPointCoordinates = p2;
				glDrawable.display();
				break;
			case KeyEvent.VK_4:
				activeControlPoint = 'd';
				activeControlPointCoordinates = p3;
				glDrawable.display();
				break;
			}

		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		oldY = arg0.getY();
		oldX = arg0.getX();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		switch(activeControlPoint) {
		case 'a' :
			p0 = activeControlPointCoordinates;
			break;
		case 'b' :
			p1 = activeControlPointCoordinates;
			break;
		case 'c' :
			p2 = activeControlPointCoordinates;
			break;
		case 'd' :
			p3 = activeControlPointCoordinates;
			break;
		}

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		float movtoX = e.getX() - oldX;
		float movtoY = e.getY() - oldY;
		
		float actualX = activeControlPointCoordinates.getX();
		float actualY = activeControlPointCoordinates.getY();
		
		actualX += movtoX;
		actualY -= movtoY;
		
		activeControlPointCoordinates = new Point2D(actualX, actualY);

		oldX = e.getX();
		oldY = e.getY();

		glDrawable.display();

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
