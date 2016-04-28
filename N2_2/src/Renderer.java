import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class Renderer implements GLEventListener {

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
	private final Point2D MIN_POINT = new Point2D(100.0, 100.0);
	private final Point2D MAX_POINT = new Point2D(200.0, 200.0);
	private Circle circle;
	private float raio = 100;
	private float sruMinX = -200;
	private float sruMaxX = 200;
	private float sruMinY = -200;
	private float sruMaxY = 200;

	private float orthoLeftAndBottom = -400;
	private float orthoRightAndTop = 400;

	private float sruXLineYAxis = 0.0f;
	private float sruYLineXAxis = 0.0f;

	private float circleXOffset = 0;
	private float circleYOffset = 0;

	// private final float canvasWidth;
	// private final float canvasHeigth;

	private ZoomKeyListener zoomListener = new ZoomKeyListener();

	public ZoomKeyListener getZoomKeyListener() {
		return zoomListener;
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		System.out.println(" --- init ---");
		glDrawable = drawable;
		gl = drawable.getGL();
		circle = new Circle(gl);
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

		circle.desenharCirculo(raio, circleXOffset, circleYOffset);

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

	public class ZoomKeyListener implements KeyListener {
		double raioRatio = raio * 0.1;
		double offsetRatio = sruMaxX * 0.1;
		float orthoVal = 10;

		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_I:

				if (orthoLeftAndBottom <= -150) {
					orthoLeftAndBottom += orthoVal;
					orthoRightAndTop -= orthoVal;

					glDrawable.display();
				}
				break;
			case KeyEvent.VK_O:

				if (orthoLeftAndBottom > -700) {
					orthoLeftAndBottom -= orthoVal;
					orthoRightAndTop += orthoVal;

					System.out.println("orthoLeftAndBottom: " + orthoLeftAndBottom);
					System.out.println("orthoRightAndTop: " + orthoRightAndTop);

					glDrawable.display();
				}
				break;

			case KeyEvent.VK_E:
				circleXOffset -= offsetRatio;

				sruMinX -= offsetRatio;
				sruMaxX -= offsetRatio;

				sruYLineXAxis -= offsetRatio;

				glDrawable.display();
				break;

			case KeyEvent.VK_D:
				circleXOffset += offsetRatio;

				sruMinX += offsetRatio;
				sruMaxX += offsetRatio;

				sruYLineXAxis += offsetRatio;

				glDrawable.display();
				break;

			case KeyEvent.VK_C:
				circleYOffset += offsetRatio;

				sruXLineYAxis += offsetRatio;

				sruMinY += offsetRatio;
				sruMaxY += offsetRatio;

				glDrawable.display();

				break;

			case KeyEvent.VK_B:
				circleYOffset -= offsetRatio;

				sruXLineYAxis -= offsetRatio;

				sruMinY -= offsetRatio;
				sruMaxY -= offsetRatio;

				glDrawable.display();
				break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent e) {

		}

	}

}
