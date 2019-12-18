package gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class FrameCampoMinado extends JFrame {
	
	private final int FRAME_LARGURA = 500;
	private final int FRAME_ALTURA = 600;
	
	public FrameCampoMinado() {
		
		PainelTabuleiro painelTabuleiro = new PainelTabuleiro();
		this.add(painelTabuleiro);
		
	    //propriedades do frame
		setLayout(null);
	    setSize(FRAME_LARGURA, FRAME_ALTURA);
	    setResizable(true);
	    setTitle("Campo Minado, versão Spielberg Liso");
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setVisible(true);
	    
	    //centraliza o frame no centro da tela
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) dimension.width/2 - FRAME_LARGURA/2;
	    int y = (int) dimension.height/2 - FRAME_ALTURA/2;
	    setLocation(x, y);
	    
	    //adicionando o tabuleiro
	    painelTabuleiro.setSize(painelTabuleiro.getTabuleiro().getNumLinhas() * painelTabuleiro.getTamanhoQuadrado() + 1, 
	    		painelTabuleiro.getTabuleiro().getNumColunas() * painelTabuleiro.getTamanhoQuadrado() + 1);
	    painelTabuleiro.setLocation(0, 0);	
	    getContentPane().add(painelTabuleiro);
	}

}
