package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RedesController {

	public RedesController(){
		super();
	}
	public String os = System.getProperty("os.name");
	public void IP() {
		System.out.println(os);
		String Processo = "";
		if(os.contains("Windows")) {
			Processo = "ipconfig";
		}else if(os.contains("Linux")){
			Processo = "ifconfig";
		}
		try {
			Process executar = Runtime.getRuntime().exec(Processo);
			InputStream fluxo = executar.getInputStream();
			InputStreamReader leitor = new InputStreamReader(fluxo);
			BufferedReader buffer = new BufferedReader(leitor);
			String linha = buffer.readLine();
			while (linha != null) {
				if(linha.contains("Ethernet")){
					System.out.println(linha);
					linha = buffer.readLine();
					while (!linha.contains("Adaptador")){
						if (linha.contains("IPv4")) {
							System.out.println(linha);		
						}
						linha = buffer.readLine();
					}
				} else {
					linha = buffer.readLine();
				}
			}
			buffer.close();
			leitor.close();
			fluxo.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void ping() {
		String Processo = "";
		if(os.contains("Windows")) {
			Processo = "ping -n 10 www.google.com.br";
		}else if(os.contains("Linux")){
			Processo = "ping -c 10 google.com.br";
		}
		try {
			Process executar = Runtime.getRuntime().exec(Processo);
			InputStream fluxo = executar.getInputStream();
			InputStreamReader leitor = new InputStreamReader(fluxo);
			BufferedReader buffer = new BufferedReader(leitor);
			String linha = buffer.readLine();
			while (linha != null) {
				if(linha.contains("Resposta")){
					linha = linha.trim();
					String[] vetor = linha.split(" ");
					System.out.println(vetor[4]);		
				}else 
					if(linha.contains("milissegundos")){
						linha = buffer.readLine();
						linha = linha.trim();
						String[] vetor = linha.split(",");
						System.out.println(vetor[2].trim());
					}
				linha = buffer.readLine();
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}

