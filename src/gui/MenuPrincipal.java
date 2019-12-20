package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import controladores.ControladorCampoMinado;

@SuppressWarnings("serial")
public class MenuPrincipal extends JFrame implements ActionListener {
	
	JLabel instrucao = new JLabel("ESCOLHA A DIFICULDADE DO JOGO");
	JButton botaoFacil = new JButton("Facil");
	JButton botaoMedio = new JButton("Medio");
	JButton botaoDificil = new JButton("Dificil");
	
	private final int FRAME_LARGURA = 386;
	private final int FRAME_ALTURA = 232;
	
	public MenuPrincipal() {
		// propriedades do frame
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setSize(FRAME_LARGURA, FRAME_ALTURA);
		setResizable(false);
		setTitle("Campo Minado, versão Spielberg Liso");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		// centraliza o frame na tela
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = dimension.width / 2 - getSize().width / 2;
		int y = dimension.height / 2 - getSize().height / 2;
		setLocation(x, y);
		
		// posiciona o JLabel de instrucao
		add(Box.createVerticalStrut(20));
		instrucao.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(instrucao);
		
		// posiciona os botoes
		addBotao(botaoFacil);
		addBotao(botaoMedio);
		addBotao(botaoDificil);
	}
	
	private void addBotao(JButton botao) {
		botao.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(Box.createVerticalStrut(20));
		botao.addActionListener(this);
		this.add(botao);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == botaoFacil) {
			ControladorCampoMinado.getControladorCampoMinado().ModoFacil();
		} else if(e.getSource() == botaoMedio) {
			ControladorCampoMinado.getControladorCampoMinado().ModoMedio();
		} else if(e.getSource() == botaoDificil) {
			ControladorCampoMinado.getControladorCampoMinado().ModoDificil();
		}
	}
}
