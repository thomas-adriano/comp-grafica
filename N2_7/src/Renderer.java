import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Arrays;
import java.util.List;

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
	private final Point2D MIN_POINT = new Point2D(100, 100);
	private final Point2D MAX_POINT = new Point2D(200, 200);

	private float sruMinX = -200;
	private float sruMaxX = 200;
	private float sruMinY = -200;
	private float sruMaxY = 200;
	

	private float orthoLeftAndBottom = -400;
	private float orthoRightAndTop = 400;

	private float sruXLineYAxis = 0.0f;
	private float sruYLineXAxis = 0.0f;

	private int circleXCenter = 200;
	private int circleYCenter = 200;
	
	private Point2D joystickCenter = new Point2D(circleXCenter, circleYCenter);

	private OuterCircle outCircle = new OuterCircle();
	
	// private final float canvasWidth;
	// private final float canvasHeigth;

	
	public List<MouseMotionListener> getMouseMotionListeners() {
		return Arrays.asList(((MouseMotionListener)outCircle.getCtrlCircle()));
	}
	
	public List<MouseListener> getMouseListeners() {
		return Arrays.asList(((MouseListener)outCircle.getCtrlCircle()));
	}
	

	@Override
	public void init(GLAutoDrawable drawable) {
		System.out.println(" --- init ---");
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));
		
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		

	}

	// exibicaoPrincipal
	public static Point2D atualMousePos = null;
	@Override
	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();

		glu.gluOrtho2D(orthoLeftAndBottom, orthoRightAndTop, orthoLeftAndBottom, orthoRightAndTop);

		drawSRU();

		outCircle.draw(glDrawable, gl, joystickCenter, atualMousePos);

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

}
