package view;

import javax.swing.JOptionPane;

import controller.RedesController;

public class Main {

	public static void main(String[] args) {
		RedesController pc = new RedesController();
		int resposta = 0;
		while (resposta != 3){
			resposta = Integer.parseInt(JOptionPane.showInputDialog(null,"Digite: IP-1 / Ping-2 / Finalizar-3"));
			if(resposta == 1) {
			pc.IP();
			}else
				if(resposta==2){
					pc.ping();
			}
		}
	}
}
