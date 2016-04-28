
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class Main implements GLEventListener, KeyListener {
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;

	public void init(GLAutoDrawable drawable) {
		System.out.println(" --- init ---");
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));
		System.out.println("Espaco de desenho com tamanho: " + drawable.getWidth() + " x " + drawable.getHeight());
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);		
	}
	
	//exibicaoPrincipal
	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		glu.gluOrtho2D(-400.0f, 400.0f, -400.0f, 400.0f);

		SRU();
		 
		// seu desenho ...
		
    		gl.glColor3f(0.0f, 50.0f, 100.0f);
		gl.glLineWidth(2.0f);
		gl.glBegin(GL.GL_LINE_LOOP);
			gl.glVertex2d(100, 100);
			gl.glVertex2d(0, -100);
			gl.glVertex2d(-100, 100);
		gl.glEnd();
		gl.glFlush();
		
		int iRaio = 100;
		double dAngulo = 0, dIntervalo = 5;
		
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glLineWidth(2.0f);
		gl.glBegin(GL.GL_LINE_LOOP);
			for (int i = 0; i < 360 / dIntervalo; i++) {
				dAngulo += dIntervalo; 
				System.out.println(dAngulo);
				gl.glVertex2d(iRaio * Math.cos(Math.PI * dAngulo/ 180.0) -100, iRaio * Math.sin(Math.PI * dAngulo/ 180.0) + 100);
			}
		gl.glEnd();
		gl.glFlush();
		
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glLineWidth(2.0f);
		gl.glBegin(GL.GL_LINE_LOOP);
			for (int i = 0; i < 360 / dIntervalo; i++) {
				dAngulo += dIntervalo; 
				System.out.println(dAngulo);
				gl.glVertex2d(iRaio * Math.cos(Math.PI * dAngulo/ 180.0), iRaio * Math.sin(Math.PI * dAngulo/ 180.0) - 100);
			}
		gl.glEnd();
		gl.glFlush();
		
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glLineWidth(2.0f);
		gl.glBegin(GL.GL_LINE_LOOP);
			for (int i = 0; i < 360 / dIntervalo; i++) {
				dAngulo += dIntervalo; 
				System.out.println(dAngulo);
				gl.glVertex2d(iRaio * Math.cos(Math.PI * dAngulo/ 180.0) +100, iRaio * Math.sin(Math.PI * dAngulo/ 180.0) + 100);
			}
		gl.glEnd();
		gl.glFlush();
	}	

	public void keyPressed(KeyEvent e) {
		System.out.println(" --- keyPressed ---");
		switch (e.getKeyCode()) {
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
//		gl.glDisable(gl.GL_TEXTURE_2D);
//		gl.glDisableClientState(gl.GL_TEXTURE_COORD_ARRAY);
//		gl.glDisable(gl.GL_LIGHTING); //TODO: [D] FixMe: check if lighting and texture is enabled

		// eixo x
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glLineWidth(1.0f);
		gl.glBegin( GL.GL_LINES );
			gl.glVertex2f( -200.0f, 0.0f );
			gl.glVertex2f(  200.0f, 0.0f );
			gl.glEnd();
		// eixo y
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glBegin( GL.GL_LINES);
			gl.glVertex2f(  0.0f, -200.0f);
			gl.glVertex2f(  0.0f, 200.0f );
		gl.glEnd();
	}

}