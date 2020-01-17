package controladores;

import java.util.ArrayList;
import java.util.List;

import dominio.Coordenada;
import dominio.Tabuleiro;
import dominio.TipoCasa;
import gui.FrameCampoMinado;
import gui.MenuPrincipal;
import observer.IObservado;
import observer.IObservador;

public class ControladorCampoMinado implements IObservado {
	List<IObservador> listaObservadores = new ArrayList<IObservador>();
	static ControladorCampoMinado controlador = null;

	Tabuleiro tabuleiro;
	private boolean perdeu = false;
	
	MenuPrincipal menuPrincipal;
	FrameCampoMinado frameCampoMinado;
	
	boolean venceu = false;
	
	ArrayList<Coordenada> casasAbertas = new ArrayList<>();
	int quantidadeCasasAbertas = 0;

	private ControladorCampoMinado() {

	}

	public static ControladorCampoMinado getControladorCampoMinado() {
		if (controlador == null)
			controlador = new ControladorCampoMinado();

		return controlador;
	}

	public void campoMinadoClicado(int coluna, int linha) {
		casasAbertas.clear();
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
			quantidadeCasasAbertas++;
			casasAbertas.add(new Coordenada(coluna, linha));
			if(tabuleiro.getQntBombasAoRedor(coluna, linha) == 0) {
				abreTodasAsCasaAoRedor(coluna, linha);
			}
		}
		
		if(verificaSeVenceu()) {
			venceu = true;
		}

		for (IObservador observador : listaObservadores) {
			observador.notify(this);
		}
	}
	
	//algoritmo preguiçoso. muito codigo repetido. baseado no metodo "removeUmaBombaAoRedorDasCasaEmVolta" na classe Tabuleiro
	public void abreTodasAsCasaAoRedor(int coluna, int linha) {
		int linhaAux = linha + 1;
		int colunaAux = coluna - 1;
		if(colunaAux >= 0) {
			abreTodasAsCasaAoRedorAux(colunaAux, linha);
			if(linhaAux < tabuleiro.getNumLinhas()) {
				abreTodasAsCasaAoRedorAux(colunaAux, linhaAux);
			}
			linhaAux = linha - 1;
			if(linhaAux >= 0) {
				abreTodasAsCasaAoRedorAux(colunaAux, linhaAux);
			}
		}
		linhaAux = linha + 1;
		colunaAux = coluna + 1;
		if(colunaAux < tabuleiro.getNumColunas()) {
			abreTodasAsCasaAoRedorAux(colunaAux, linha);
			if(linhaAux < tabuleiro.getNumLinhas()) {
				abreTodasAsCasaAoRedorAux(colunaAux, linhaAux);
			}
			linhaAux = linha - 1;
			if(linhaAux >= 0) {
				abreTodasAsCasaAoRedorAux(colunaAux, linhaAux);
			}
		}
		linhaAux = linha + 1;
		if(linhaAux < tabuleiro.getNumLinhas()) {
			abreTodasAsCasaAoRedorAux(coluna, linhaAux);
		}
		linhaAux = linha - 1;
		if(linhaAux >= 0) {
			abreTodasAsCasaAoRedorAux(coluna, linhaAux);
		}
	}
			
	/**
	 * Abre a casa, dada sua coluna e linha, se ela nao possuir mina.
	 * Caso não possua mina alguma ao redor, chama "abreTodasAsCasaAoRedor".
	 * 
	 * @param coluna - coluna da casa a ser aberta
	 * @param linha - linha da casa a ser aberta
	 */
	private void abreTodasAsCasaAoRedorAux(int coluna, int linha) {
		if(tabuleiro.getMatrizTabuleiro()[coluna][linha] == TipoCasa.casaIntactaSemMina) {
			tabuleiro.getMatrizTabuleiro()[coluna][linha] = TipoCasa.casaSemMinaAtirada;
			quantidadeCasasAbertas++;
			casasAbertas.add(new Coordenada(coluna, linha));
			if(tabuleiro.getQntBombasAoRedor(coluna, linha) == 0) {
				abreTodasAsCasaAoRedor(coluna,linha);
			}
		}
	}
	
	private boolean verificaSeVenceu() {
		return quantidadeCasasAbertas == tabuleiro.getQuantidadeDeCasasSemMina();
	}

	public void ModoFacil() {
		ModoDeJogo(6, 6, 32);
	}
	
	public void ModoMedio() {
		ModoDeJogo(10, 10, 2);
	}
	
	public void ModoDificil() {
		ModoDeJogo(20, 20, 2);
	}
	
	private void ModoDeJogo(int numColunas, int numLinhas, int quantidadeDeCasasSemMina) {
		tabuleiro = new Tabuleiro(numColunas, numLinhas, quantidadeDeCasasSemMina);
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
		} else if (i == 3) {
			return casasAbertas;
		}
		return null;
	}

}
