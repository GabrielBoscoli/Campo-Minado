package gui;

import javax.swing.*;

import observer.IObservado;
import observer.IObservador;
import dominio.Tabuleiro;

import java.awt.*;
import java.awt.geom.*;

/**
 * Classe responsável pelo desenho do tabuleiro
 * @author Gabriel Boscoli
 *
 */
@SuppressWarnings("serial")
public class PainelTabuleiro extends JPanel implements IObservador {

	private int tamanhoQuadrado = 25;
	Tabuleiro tabuleiro = new Tabuleiro();
	
	public PainelTabuleiro() {
		this.setLayout(null);
		this.setDoubleBuffered(true);
		
		Dimension dimensao = new Dimension(tamanhoQuadrado * tabuleiro.getNumColunas(), tamanhoQuadrado * tabuleiro.getNumLinhas());
		setPreferredSize(dimensao);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		int linha, coluna;
		
		//desenhando o tabuleiro
		for (coluna=0; coluna < tabuleiro.getNumColunas(); coluna++){
			for (linha=0; linha < tabuleiro.getNumLinhas(); linha++){
				Rectangle2D retangulo = new Rectangle2D.Double(tamanhoQuadrado * coluna, tamanhoQuadrado * linha, tamanhoQuadrado, tamanhoQuadrado);
				g2d.setPaint(tabuleiro.getMatrizCor()[coluna][linha]);
				g2d.fill(retangulo);
				g2d.setPaint(Color.black);
				g2d.draw(retangulo);
			}
		}
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

	@Override
	public void notify(IObservado observado) {
		repaint();
	}
}