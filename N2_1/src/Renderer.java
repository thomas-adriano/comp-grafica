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

	@Override
	public void init(GLAutoDrawable drawable) {
		System.out.println(" --- init ---");
		circle = new Circle(MIN_POINT.getX(), MIN_POINT.getY(), MAX_POINT.getX(), MAX_POINT.getY());
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));
		System.out.println("Espaco de desenho com tamanho: " + drawable.getWidth() + " x " + drawable.getHeight());
		gl.glClearColor(0.6f, 0.6f, 0.6f, 0.6f);

	}

	// exibicaoPrincipal
	@Override
	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		glu.gluOrtho2D(-400.0f, 400.0f, -400.0f, 400.0f);

		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();

		drawSRU();

		circle.desenharCirculo(gl);

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
