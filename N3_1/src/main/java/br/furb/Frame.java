package br.furb;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

public class Frame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7541728312795865667L;
	public static final int X = 400;
	public static final int Y = 400;
	public static final int LARGURA_BORDA_JANELA_WINDOWS = 22;
	public static final int LARGURA_JANELA = X;
	public static final int ALTURA_JANELA = Y;

	public Frame() {
		super("N3_1");
		setBounds(X, Y, LARGURA_JANELA, ALTURA_JANELA + LARGURA_BORDA_JANELA_WINDOWS);

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());

		/*
		 * Cria um objeto GLCapabilities para especificar o numero de bits por
		 * pixel para RGBA
		 */
		GLCapabilities glCaps = new GLCapabilities(GLProfile.getDefault());
		glCaps.setRedBits(8);
		glCaps.setBlueBits(8);
		glCaps.setGreenBits(8);
		glCaps.setAlphaBits(8);

		/*
		 * Cria um canvas, adiciona ao frame e objeto "ouvinte" para os eventos
		 * Gl, de mouse e teclado
		 */
		Renderer r = new Renderer();
		GLCanvas canvas = new GLCanvas(glCaps);
		add(canvas, BorderLayout.CENTER);
		canvas.addGLEventListener(r);

		canvas.addMouseListener(r.getMundo());
		canvas.addMouseMotionListener(r.getMundo());
		canvas.addKeyListener(r.getMundo());

		canvas.requestFocus();

	}

}
