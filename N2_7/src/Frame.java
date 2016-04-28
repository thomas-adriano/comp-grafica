import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Frame extends JFrame {

	private static final long serialVersionUID = -5151041547543472432L;

	//desenhar um círculo no centro do Sistema de Referência do Universo (SRU)
	
	/*
	 * defina as dimensões da janela com valores (xMin: -400, xMax: 400, yMin: -400, yMax: 400)
	 * 
	 */
	
	public static final int X = 400;
	public static final int Y = 400;
	public static final int LARGURA_BORDA_JANELA_WINDOWS = 22;
	public static final int LARGURA_JANELA = X;
	public static final int ALTURA_JANELA = Y;
	
	public static void main(String[] args) {
		new Frame().setVisible(true);
	}
	
	public Frame() {
		super("N2_2");
		setBounds(X, Y, LARGURA_JANELA, ALTURA_JANELA + LARGURA_BORDA_JANELA_WINDOWS);
	
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		
		/* Cria um objeto GLCapabilities para especificar 
		 * o numero de bits por pixel para RGBA
		 */
		GLCapabilities glCaps = new GLCapabilities();
		glCaps.setRedBits(8);
		glCaps.setBlueBits(8);
		glCaps.setGreenBits(8);
		glCaps.setAlphaBits(8); 

		/* Cria um canvas, adiciona ao frame e objeto "ouvinte" 
		 * para os eventos Gl, de mouse e teclado
		 */
		GLCanvas canvas = new GLCanvas(glCaps);
		Color c = new Color(0.86f, 0.86f, 0.86f);
		canvas.setBackground(c);
		
		Renderer renderer = new Renderer();
		canvas.addGLEventListener(renderer);
		
		for (MouseMotionListener m : renderer.getMouseMotionListeners()) {
			canvas.addMouseMotionListener(m);
		}
		
		for (MouseListener m : renderer.getMouseListeners()) {
			canvas.addMouseListener(m);
		}
		
		canvas.requestFocus();
		
		add(canvas,BorderLayout.CENTER);
	}
	
}
