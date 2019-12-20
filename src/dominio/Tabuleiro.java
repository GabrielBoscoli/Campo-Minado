package dominio;

import java.util.Random;

public class Tabuleiro {

	private int numColunas;
	private int numLinhas;
	private TipoCasa[][] matrizTabuleiro;
	private int quantidadeDeCasasSemMina = 2;

	public Tabuleiro(int numColunas, int numLinhas) {
		this.numColunas = numColunas;
		this.numLinhas = numLinhas;
		matrizTabuleiro = new TipoCasa[numColunas][numLinhas];
		
		for (int i = 0; i < numColunas; i++) {
			for (int j = 0; j < numLinhas; j++) {
				matrizTabuleiro[i][j] = TipoCasa.casaIntactaComMina;
			}
		}

		for (int i = 0; i < quantidadeDeCasasSemMina; i++) {
			int coluna;
			int linha;
			Random colunaSemMina = new Random();
			coluna = colunaSemMina.nextInt(numColunas);
			Random linhaSemMina = new Random();
			linha = linhaSemMina.nextInt(numLinhas);

			if (matrizTabuleiro[coluna][linha] == TipoCasa.casaIntactaSemMina) {
				i--;
			}

			matrizTabuleiro[coluna][linha] = TipoCasa.casaIntactaSemMina;
		}

	}

	/**
	 * Verifica se a casa da linha e coluna especificada possui mina
	 * 
	 * @param coluna - coluna da casa
	 * @param linha  - linha da casa
	 * @return true, se a casa tiver mina. false, se não estiver ou se o tabuleiro
	 *         nao possui casa na coordenada recebida.
	 */
	public boolean CasaNaoTemMina(int coluna, int linha) {
		if (linha >= numLinhas || linha < 0 || coluna >= numColunas || coluna < 0) {
			return false;
		}

		if (matrizTabuleiro[coluna][linha] == TipoCasa.casaIntactaSemMina) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Tira a mina da casa. Se a casa não tiver mina, não faz nada.
	 * 
	 * @param coluna - coluna da casa
	 * @param linha  - linha da casa
	 */
	public void TiraMinaDaCasa(int coluna, int linha) {
		if (linha >= numLinhas || linha < 0 || coluna >= numColunas || coluna < 0) {
			return;
		}

		matrizTabuleiro[coluna][linha] = TipoCasa.casaIntactaSemMina;
	}

	public void ResetaTabuleiro() {
		for (int i = 0; i < numColunas; i++) {
			for (int j = 0; j < numLinhas; j++) {
				TiraMinaDaCasa(i, j);
			}
		}
	}

	public int getNumColunas() {
		return numColunas;
	}

	public void setNumColunas(int numColunas) {
		this.numColunas = numColunas;
	}

	public int getNumLinhas() {
		return numLinhas;
	}

	public void setNumLinhas(int numLinhas) {
		this.numLinhas = numLinhas;
	}

	public TipoCasa[][] getMatrizTabuleiro() {
		return matrizTabuleiro;
	}

	public void setMatrizTabuleiro(TipoCasa[][] matrizTabuleiro) {
		this.matrizTabuleiro = matrizTabuleiro;
	}

}