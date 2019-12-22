package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import controladores.ControladorCampoMinado;
import observer.IObservado;
import observer.IObservador;

@SuppressWarnings("serial")
public class FrameCampoMinado extends JFrame implements IObservador, ActionListener {

	Timer timerDeEsperaParaMostrarMensagemDeDerrota = new Timer(750, this);

	public FrameCampoMinado() {

		PainelTabuleiro painelTabuleiro = new PainelTabuleiro();
		this.add(painelTabuleiro);

		// propriedades do frame
		setLayout(null);
		getContentPane().setPreferredSize(new Dimension(painelTabuleiro.getPreferredSize().width - 11,
				painelTabuleiro.getPreferredSize().height - 10));
		pack();
		setResizable(false);
		setTitle("Campo Minado, versão Spielberg Liso");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		// centraliza o frame na tela
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = dimension.width / 2 - getSize().width / 2;
		int y = dimension.height / 2 - getSize().height / 2;
		setLocation(x, y);

		// adicionando o tabuleiro
		painelTabuleiro.setSize(
				painelTabuleiro.getTabuleiro().getNumColunas() * painelTabuleiro.getTamanhoQuadrado() + 1,
				painelTabuleiro.getTabuleiro().getNumLinhas() * painelTabuleiro.getTamanhoQuadrado() + 1);
		painelTabuleiro.setLocation(-1, 0);

		ControladorCampoMinado.getControladorCampoMinado().add(this);
	}

	@Override
	public void notify(IObservado observado) {
		boolean perdeu = (boolean) ControladorCampoMinado.getControladorCampoMinado().get(1);
		if (perdeu) {
			timerDeEsperaParaMostrarMensagemDeDerrota.setRepeats(false);
			timerDeEsperaParaMostrarMensagemDeDerrota.start();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(this, "kkkk perdeu! burrao...");
		ControladorCampoMinado.getControladorCampoMinado().resetar();
		ControladorCampoMinado.getControladorCampoMinado().iniciar();
		this.setVisible(false);
	}

}
