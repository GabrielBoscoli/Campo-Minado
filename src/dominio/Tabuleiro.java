package dominio;

import java.util.Random;

public class Tabuleiro {

	private int numColunas;
	private int numLinhas;
	private TipoCasa[][] matrizTabuleiro;
	private int[][] numBombasAoRedor;
	private int quantidadeDeCasasSemMina = 2;

	public Tabuleiro(int numColunas, int numLinhas) {
		this.numColunas = numColunas;
		this.numLinhas = numLinhas;
		matrizTabuleiro = new TipoCasa[numColunas][numLinhas];
		inicializaMatrizes();
		
		for (int i = 0; i < quantidadeDeCasasSemMina; i++) {
			int coluna;
			int linha;
			Random colunaSemMina = new Random();
			coluna = colunaSemMina.nextInt(numColunas);
			Random linhaSemMina = new Random();
			linha = linhaSemMina.nextInt(numLinhas);

			if (matrizTabuleiro[coluna][linha] == TipoCasa.casaIntactaSemMina) {
				i--;
			} else {
				removeUmaBombaAoRedorDasCasaEmVolta(coluna, linha);
			}

			matrizTabuleiro[coluna][linha] = TipoCasa.casaIntactaSemMina;
		}

	}
	
	//esse algoritmo está preguiçoso, tem casas que são reescritas muitas vezes.
	private void inicializaMatrizes() {
		numBombasAoRedor = new int[numColunas][numLinhas];
		
		//inicializa a matriz com a condicao das casas
		for (int i = 0; i < numColunas; i++) {
			for (int j = 0; j < numLinhas; j++) {
				matrizTabuleiro[i][j] = TipoCasa.casaIntactaComMina;
				numBombasAoRedor[i][j] = 8;
			}
		}
		
		//define a quantidade de bombas nas casas das linhas na extremidade
		int linha = 0;
		for(int j = 0; j < 2; j++) {
			for(int i = 0; i < numColunas; i++) {
				numBombasAoRedor[i][linha] = 5;
			}
			linha = numLinhas - 1;
		}
		
		//define a quantidade de bombas nas casas das colunas na extremidade
		int coluna = 0;
		for(int j = 0; j < 2; j++) {
			for(int i = 0; i < numLinhas; i++) {
				numBombasAoRedor[coluna][i] = 5;
			}
			coluna = numColunas - 1;
		}
		
		//define a quantidade de bombas das casas na quina
		numBombasAoRedor[0][0] = 3;
		numBombasAoRedor[numBombasAoRedor.length - 1][0] = 3;
		numBombasAoRedor[0][numBombasAoRedor.length - 1] = 3;
		numBombasAoRedor[numBombasAoRedor.length - 1][numBombasAoRedor.length - 1] = 3;
	}
	
	//algoritmo preguiçoso. muito codigo repetido
	private void removeUmaBombaAoRedorDasCasaEmVolta(int coluna, int linha) {
		int linhaAux = linha + 1;
		int colunaAux = coluna - 1;
		if(colunaAux >= 0) {
			numBombasAoRedor[colunaAux][linha]--;
			if(linhaAux < numLinhas) {
				numBombasAoRedor[colunaAux][linhaAux]--;
			}
			linhaAux = linha - 1;
			if(linhaAux >= 0) {
				numBombasAoRedor[colunaAux][linhaAux]--;
			}
		}
		linhaAux = linha + 1;
		colunaAux = coluna + 1;
		if(colunaAux < numColunas) {
			numBombasAoRedor[colunaAux][linha]--;
			if(linhaAux < numLinhas) {
				numBombasAoRedor[colunaAux][linhaAux]--;
			}
			linhaAux = linha - 1;
			if(linhaAux >= 0) {
				numBombasAoRedor[colunaAux][linhaAux]--;
			}
		}
		linhaAux = linha + 1;
		if(linhaAux < numLinhas) {
			numBombasAoRedor[coluna][linhaAux]--;
		}
		linhaAux = linha - 1;
		if(linhaAux >= 0) {
			numBombasAoRedor[coluna][linhaAux]--;
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
	public boolean casaNaoTemMina(int coluna, int linha) {
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
	public void tiraMinaDaCasa(int coluna, int linha) {
		if (linha >= numLinhas || linha < 0 || coluna >= numColunas || coluna < 0) {
			return;
		}

		matrizTabuleiro[coluna][linha] = TipoCasa.casaIntactaSemMina;
	}

	public void resetaTabuleiro() {
		for (int i = 0; i < numColunas; i++) {
			for (int j = 0; j < numLinhas; j++) {
				tiraMinaDaCasa(i, j);
			}
		}
	}
	
	public int getQntBombasAoRedor(int coluna, int linha) {
		return numBombasAoRedor[coluna][linha];
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
	
	public int getQuantidadeDeCasasSemMina() {
		return quantidadeDeCasasSemMina;
	}
	
	public int getQuantidadeDeCasaTotais() {
		return numColunas * numLinhas;
	}

}