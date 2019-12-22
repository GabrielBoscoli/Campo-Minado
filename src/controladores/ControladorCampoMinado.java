package controladores;

import java.util.ArrayList;
import java.util.List;

import dominio.Tabuleiro;
import dominio.TipoCasa;
import gui.FrameCampoMinado;
import gui.MenuPrincipal;
import observer.IObservado;
import observer.IObservador;

public class ControladorCampoMinado implements IObservado {
	List<IObservador> listaObservadores = new ArrayList<IObservador>();
	static ControladorCampoMinado controlador = null;

	Tabuleiro tabuleiro = new Tabuleiro(20, 20);
	private boolean perdeu = false;
	
	MenuPrincipal menuPrincipal;
	FrameCampoMinado frameCampoMinado;
	
	int quantidadeDeCasasAcertadas = 0;
	boolean venceu = false;

	private ControladorCampoMinado() {

	}

	public static ControladorCampoMinado getControladorCampoMinado() {
		if (controlador == null)
			controlador = new ControladorCampoMinado();

		return controlador;
	}

	public void campoMinadoClicado(int coluna, int linha) {
		if (perdeu) {
			return;
		}

		TipoCasa[][] matrizTabuleiro = tabuleiro.getMatrizTabuleiro();
		TipoCasa tipoCasa = matrizTabuleiro[coluna][linha];
		if (tipoCasa == TipoCasa.casaIntactaComMina) {
			matrizTabuleiro[coluna][linha] = TipoCasa.casaComMinaAtirada;
			perdeu = true;
		} else if (tipoCasa == TipoCasa.casaIntactaSemMina) {
			matrizTabuleiro[coluna][linha] = TipoCasa.casaSemMinaAtirada;
			quantidadeDeCasasAcertadas++;
		}
		
		if(verificaSeVenceu()) {
			venceu = true;
		}

		for (IObservador observador : listaObservadores) {
			observador.notify(this);
		}
	}
	
	private boolean verificaSeVenceu() {
		return quantidadeDeCasasAcertadas == tabuleiro.getQuantidadeDeCasasSemMina();
	}

	public void ModoFacil() {
		ModoDeJogo(6, 6);
	}
	
	public void ModoMedio() {
		ModoDeJogo(10, 10);
	}
	
	public void ModoDificil() {
		ModoDeJogo(20, 20);
	}
	
	private void ModoDeJogo(int numColunas, int numLinhas) {
		tabuleiro = new Tabuleiro(numColunas, numLinhas);
		menuPrincipal.setVisible(false);
		frameCampoMinado = new FrameCampoMinado();
	}

	public void resetar() {
		controlador = null;
	}

	public void iniciar() {
		menuPrincipal = new MenuPrincipal();
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
		if (i == 0) {
			return tabuleiro;
		} else if (i == 1) {
			return perdeu;
		} else if (i == 2) {
			return venceu;
		}
		return null;
	}

}
