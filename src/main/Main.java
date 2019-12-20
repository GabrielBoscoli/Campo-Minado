package main;

import controladores.ControladorCampoMinado;
import util.MailUtil;

public class Main {

	public static void main(String[] args) throws Exception {
		//MailUtil.sendMail("vacaflutuante@gmail.com");
		ControladorCampoMinado.getControladorCampoMinado().iniciar();
	}

}
