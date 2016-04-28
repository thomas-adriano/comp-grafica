package br.furb;

import java.util.List;

public class Mundo {

	private List<ObjetoGrafico> objetos;
	private Camera2D camera;
	private ObjetoGrafico objetoSelecionado;

	public void addObjeto(ObjetoGrafico o) {
		objetos.add(o);
	}
	
	public void removeObjeto(ObjetoGrafico o) {
		objetos.remove(o);
	}
	
}
