package controladores;

import java.util.ArrayList;
import java.util.List;

import dominio.Tabuleiro;
import dominio.TipoCasa;
import gui.FrameCampoMinado;
import observer.IObservado;
import observer.IObservador;

public class ControladorCampoMinado implements IObservado {
	List<IObservador> listaObservadores = new ArrayList<IObservador>();
	static ControladorCampoMinado controlador = null;
	
	Tabuleiro tabuleiro = new Tabuleiro();
	private boolean perdeu = false;
	
	private ControladorCampoMinado() {
		
	}
	
	public static ControladorCampoMinado getControladorCampoMinado() {
		if(controlador == null)
			controlador = new ControladorCampoMinado();
		
		return controlador;	
	}
	
	public void campoMinadoClicado(int coluna, int linha) {
		if(perdeu) {
			return;
		}
		
		TipoCasa[][] matrizTabuleiro = tabuleiro.getMatrizTabuleiro();
		TipoCasa tipoCasa = matrizTabuleiro[coluna][linha];
		if(tipoCasa == TipoCasa.casaIntactaComMina) {
			matrizTabuleiro[coluna][linha] = TipoCasa.casaComMinaAtirada;
			perdeu = true;
		} else if(tipoCasa == TipoCasa.casaIntactaSemMina) {
			matrizTabuleiro[coluna][linha] = TipoCasa.casaSemMinaAtirada;
		}
		
		for(IObservador observador : listaObservadores) {
			observador.notify(this);
		}
	}
	
	public void resetar() {
		controlador = null;
	}
	
	public void iniciar() {
		new FrameCampoMinado();
	}
	
	@Override
	public void add(IObservador observador) {
		listaObservadores.add(observador);
	}
	
	@Override
	public void remove(IObservador observador) {
		listaObservadores.remove(observador);
	}
	
	@Override
	public Object get(int i) {
		if(i == 0) {
			return tabuleiro;
		} else if (i == 1)
			return perdeu;
		return null;
	}
	
}
