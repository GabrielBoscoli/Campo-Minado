package gui;

import javax.imageio.ImageIO;
import javax.swing.*;

import controladores.ControladorCampoMinado;
import observer.IObservado;
import observer.IObservador;
import dominio.Coordenada;
import dominio.Tabuleiro;
import dominio.TipoCasa;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe responsável pelo desenho do tabuleiro
 * 
 * @author Gabriel Boscoli
 *
 */
@SuppressWarnings("serial")
public class PainelTabuleiro extends JPanel implements IObservador, MouseListener {

	private int tamanhoQuadrado = 25;
	Tabuleiro tabuleiro = (Tabuleiro) ControladorCampoMinado.getControladorCampoMinado().get(0);

	private boolean perdeu = false;
	Image imagemMina;
	
	//ultimas coordenadas clicadas
	int colunaClicada = 0;
	int linhaClicada = 0;

	public PainelTabuleiro() {
		this.setLayout(null);
		this.setDoubleBuffered(true);

		Dimension dimensao = new Dimension(tamanhoQuadrado * tabuleiro.getNumColunas(),
				tamanhoQuadrado * tabuleiro.getNumLinhas());
		setPreferredSize(dimensao);

		this.addMouseListener(this);
		ControladorCampoMinado.getControladorCampoMinado().add(this);

		BufferedImage img = null;
		try {
			img = ImageIO.read(getClass().getResourceAsStream("/minesweeper.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		imagemMina = img;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		TipoCasa tipoCasa = null;
		int x = 0;
		int y = 0;

		Graphics2D g2d = (Graphics2D) g;
		int linha, coluna;

		// desenhando o tabuleiro
		for (coluna = 0; coluna < tabuleiro.getNumColunas(); coluna++) {
			x = tamanhoQuadrado * coluna;
			for (linha = 0; linha < tabuleiro.getNumLinhas(); linha++) {
				y = tamanhoQuadrado * linha;
				tipoCasa = tabuleiro.getMatrizTabuleiro()[coluna][linha];
				Rectangle2D retangulo = new Rectangle2D.Double(x - 1 /*"- 1" adicionado por conta do border layout*/, y, tamanhoQuadrado, tamanhoQuadrado);
				g2d.setPaint(tipoCasaToColor(tipoCasa));
				g2d.fill(retangulo);
				g2d.setPaint(Color.black);
				g2d.draw(retangulo);
			}
		}
		if (perdeu) {
			desenhaMinas(g); // nao gostei de fazer dois loops, um para pintar e outro para colocar as minas.
		}
	}

	private void desenhaMinas(Graphics g) {
		int linha, coluna;
		TipoCasa tipoCasa;

		for (coluna = 0; coluna < tabuleiro.getNumColunas(); coluna++) {
			for (linha = 0; linha < tabuleiro.getNumLinhas(); linha++) {
				tipoCasa = tabuleiro.getMatrizTabuleiro()[coluna][linha];
				if (tipoCasa == TipoCasa.casaIntactaComMina || tipoCasa == TipoCasa.casaComMinaAtirada) {
					g.drawImage(imagemMina, tamanhoQuadrado * coluna, tamanhoQuadrado * linha, 25, 25, null);
				}
			}
		}
	}
	
	private void colocaLabelQuantidadeDeBombasAoRedor(int coluna, int linha) {
		int x = tamanhoQuadrado * coluna;
		String numBombasAoRedor = Integer.toString(tabuleiro.getQntBombasAoRedor(coluna, linha));
		
		if(numBombasAoRedor.equals("0")) {
			numBombasAoRedor = "";
		}
		
		JLabel label = new JLabel("   " + numBombasAoRedor); // roubei bonito aqui. ver como fazer da melhor forma
		label.setSize(tamanhoQuadrado, tamanhoQuadrado);
		label.setLocation(x, tamanhoQuadrado * linha);
		this.add(label);
	}

	public int getTamanhoQuadrado() {
		return tamanhoQuadrado;
	}

	public Tabuleiro getTabuleiro() {
		return tabuleiro;
	}

	public void setTabuleiro(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
	}

	private Color tipoCasaToColor(TipoCasa tipoCasa) {
		if (tipoCasa == TipoCasa.casaIntactaComMina || tipoCasa == TipoCasa.casaIntactaSemMina) {
			return Color.cyan;
		} else if (tipoCasa == TipoCasa.casaSemMinaAtirada) {
			return Color.white;
		} else if (tipoCasa == TipoCasa.casaComMinaAtirada) {
			return Color.red;
		} else {
			return null;
		}
	}

	@Override
	public void notify(IObservado observado) {
		perdeu = (boolean) ControladorCampoMinado.getControladorCampoMinado().get(1);
		@SuppressWarnings("unchecked")
		ArrayList<Coordenada> casasAbertas = (ArrayList<Coordenada>) ControladorCampoMinado.getControladorCampoMinado().get(3);
		
		for(int i = 0; i < casasAbertas.size(); i++) {
			colocaLabelQuantidadeDeBombasAoRedor(casasAbertas.get(i).getColuna(), casasAbertas.get(i).getLinha());
		}
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		colunaClicada = e.getX() / tamanhoQuadrado;
		linhaClicada = e.getY() / tamanhoQuadrado;

		ControladorCampoMinado.getControladorCampoMinado().campoMinadoClicado(colunaClicada, linhaClicada);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}