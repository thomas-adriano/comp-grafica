package br.furb;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

public class Renderer implements GLEventListener {
	private GL gl;
	private GLAutoDrawable glDrawable;
	private Mundo mundo = new Mundo();

	public Mundo getMundo() {
		return this.mundo;
	}

	// "render" feito logo apos a inicializacao do contexto OpenGL.
	@Override
	public void init(GLAutoDrawable drawable) {
		System.out.println("INIT");
		glDrawable = drawable;
		gl = drawable.getGL();
		glDrawable.setGL(new DebugGL(gl));

		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		mundo.setDrawable(glDrawable);

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

		mundo.draw(gl);

		gl.glFlush();
	}

	@Override
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
	}

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
	}


}
