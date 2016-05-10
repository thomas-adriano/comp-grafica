package br.furb;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import br.furb.GraphicObject;
import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

public class World implements MouseListener, MouseMotionListener, KeyListener {

	private List<GraphicObject> objList = new ArrayList<GraphicObject>();
	/**
	 * coordenada do ultimo click dado
	 */
	private Point4D lastClickPoint;
	/**
	 * coordenada o ponteiro do mouse se encontra atualmente (atualizado pelo
	 * mouseMoved)
	 **/
	private Point4D mousePoint;
	/**
	 * momento do ultimo click
	 */
	private long lastClickTime = 0;
	private long lastElapsedClickTime = 0;
	private GraphicObject effectivelySelectedObject;
	private GraphicObject lastSelectedObject;
	private GraphicObject objectBeingDrawn;
	private Modes mode = Modes.SELECTION;
	private GLAutoDrawable glDrawable;
	private Camera2D camera;
	private static long lastObjectId = 0;
	private static final Color COLOR_BLACK = new Color(0, 0, 0);
	private static final Color COLOR_PURPLE = new Color(1, 0, 1);
	private Color bboxColor = COLOR_BLACK;

	public Camera2D getCamera() {
		return camera;
	}

	public void setCamera(Camera2D camera) {
		this.camera = camera;
	}

	public void setDrawable(GLAutoDrawable d) {
		this.glDrawable = d;
	}

	public void draw(GL2 gl) {
		if (hasLastSelectedObject()) {
			lastSelectedObject.drawBBox(gl);
		}
		for (GraphicObject obj : objList) {
			obj.draw(gl);

			if (obj.hasAnyUnfinishedObject()) {

				if (mousePoint != null) {
					gl.glVertex2f((float) mousePoint.getX() / (float) (Frame.X / 2),
							(float) mousePoint.getY() / (float) (Frame.Y / 2));
				}

				gl.glEnd();
			}
		}
	}

	private boolean hasLastSelectedObject() {
		return lastSelectedObject != null;
	}

	private boolean hasEffectivelySelectedObject() {
		return effectivelySelectedObject != null;
	}

	public void refreshClickTimes() {
		long now = System.currentTimeMillis();
		lastElapsedClickTime = now - lastClickTime;
		lastClickTime = now;
	}

	public void setModeToSelection() {
		if (mode != Modes.SELECTION) {
			mode = Modes.SELECTION;
			objectBeingDrawn.setFinished();
			objectBeingDrawn = null;
			mousePoint = null;// essa linha corrige o erro do rastro
								// (mouseMoved)
								// iniciar a partir do ultimo objetoGrafico
								// terminado. PQ?!
		}
	}

	public static long generateNewObjectId() {
		return ++lastObjectId;
	}

	public boolean hasSomeObjectBeeingDrawn() {
		return objectBeingDrawn != null;
	}

	public boolean isGoingToSelectionMode() {
		return lastClickTime != 0 && lastElapsedClickTime <= 300;
	}

	public void printWorldInfos() {
		for (GraphicObject g : objList) {
			System.out.println(g.getInfos());
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		lastClickPoint = Point4D.mouseCoordinatesToCartesianCoordinates(new Point4D(e.getX(), e.getY()));
		GraphicObject actualSelectedObject = getObjectAtClickedAreaIfAny();
		if (!hasLastSelectedObject() || actualSelectedObject != null) {
			lastSelectedObject = getObjectAtClickedAreaIfAny();
		}

		refreshClickTimes();
		if (isGoingToSelectionMode()) {
			setModeToSelection();
			callDisplay();
			printWorldInfos();
			return;
		}

		// cai aqui quando a primeira acao logo apos entrar no selection mode
		// é selecionar um objeto. Ou seja, nao deve adicionar nenhum vertice
		// novo
		if (getObjectAtClickedAreaIfAny() != null) {
			callDisplay();
			printWorldInfos();
			return;
		}

		mode = Modes.DRAW;

		if (hasEffectivelySelectedObject()) {
			// trata situacao onde objeto sendo desenhado é proveniente de
			// objeto selecionado
			GraphicObject child = new GraphicObject(World.generateNewObjectId());
			child.addPonto(lastClickPoint);
			effectivelySelectedObject.addChild(child);
			effectivelySelectedObject = null;
			objectBeingDrawn = child;
		} else {
			// trata situaao onde objeto proveio do mundo ou vertice atual não é
			// o primeiro logo apos
			// confirmar a selecao de um poligono origem
			if (objectBeingDrawn == null) {
				objectBeingDrawn = new GraphicObject(World.generateNewObjectId());
				objList.add(objectBeingDrawn);
			}
			objectBeingDrawn.addPonto(lastClickPoint);
		}

		printWorldInfos();
		callDisplay();
	}

	private void callDisplay() {
		if (glDrawable != null) {
			glDrawable.display();
		}
	}

	private GraphicObject getObjectAtClickedAreaIfAny() {
		if (mode != Modes.SELECTION) {
			return null;
		}

		GraphicObject selected = null;

		for (GraphicObject o : objList) {
			selected = o.getSelectedObject(lastClickPoint);
			if (selected != null) {
				return selected;
			}
		}

		return null;
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		if (mode == Modes.DRAW) {
			mousePoint = Point4D.mouseCoordinatesToCartesianCoordinates(new Point4D(arg0.getX(), arg0.getY()));
			callDisplay();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	public void addObjeto(GraphicObject objeto) {
		this.objList.add(objeto);
	}

	@Override
	public String toString() {
		return "Mundo [lastClickPoint=" + lastClickPoint + ", lastClickTime=" + lastClickTime
				+ ", lastElapsedClickTime=" + lastElapsedClickTime + ", lastSelectedObject=" + lastSelectedObject
				+ ", objectBeingDrawn=" + objectBeingDrawn + ", mode=" + mode + "]";
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
			effectivelySelectedObject = lastSelectedObject;
			effectivelySelectedObject.setBboxColor(COLOR_PURPLE);
			System.out.println("Selected Object: "
					+ (effectivelySelectedObject != null ? effectivelySelectedObject.getInfos() : "null"));
		}
		glDrawable.display();
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
