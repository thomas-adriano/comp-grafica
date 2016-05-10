package br.furb;

import com.jogamp.opengl.DebugGL2;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;

public class Renderer implements GLEventListener {
	private GL2 gl;
	private GLAutoDrawable glDrawable;
	private World mundo = new World();

	public World getMundo() {
		return this.mundo;
	}

	// "render" feito logo apos a inicializacao do contexto OpenGL.
	@Override
	public void init(GLAutoDrawable drawable) {
		System.out.println("INIT");

		gl = (GL2) drawable.getGL();
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

		glDrawable = drawable;
		glDrawable.setGL(new DebugGL2(gl));

		mundo.setDrawable(glDrawable);

	}

	@Override
	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
		gl.glLoadIdentity();

		gl.glLineWidth(1.0f);
		gl.glColor3f(0.0f, 0.0f, 0.0f);

		mundo.draw(gl);

		gl.glFlush();
	}

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {

	}

}
