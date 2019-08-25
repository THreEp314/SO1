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
			if(Processo == "ipconfig"){
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
			}else
				if(Processo == "ifconfig"){
					while (linha != null) {
						if(linha.contains("flags")){
							linha = linha.trim();
							String[] vetorNomeIP = linha.split("=");
							linha = buffer.readLine();
								if(linha.contains("inet") && !linha.contains("inet6")){
									linha = linha.trim();
									String[] vetorIP = linha.split(" ");
									linha = buffer.readLine();
									while (!linha.contains("TX errors")){
										if(linha.contains("Ethernet")){
											System.out.println(vetorNomeIP[1]);
											System.out.println(vetorIP[0] + " " + vetorIP[1]);
										}
										linha = buffer.readLine();
									}
								}
								else{
									linha = buffer.readLine();
								}
						}else{
							linha = buffer.readLine();
						}
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
			if(os.contains("Windows")){
				while (linha != null) {
					if(linha.contains("Resposta")){
						linha = linha.trim();
						String[] vetorPing = linha.split(" ");
						System.out.println(vetorPing[4]);		
					}else 
						if(linha.contains("milissegundos")){
							linha = buffer.readLine();
							linha = linha.trim();
							String[] vetorPing = linha.split(",");
							System.out.println(vetorPing[2].trim());
						}
					linha = buffer.readLine();
				}
			}else
				if(os.contains("Linux")){
					while (linha != null) {
						if(linha.contains("icmp")){
							linha = linha.trim();
							String[] vetorPing = linha.split(" ");
							System.out.println(vetorPing[7]);
						}else
							if(linha.contains("rtt")){
								linha = linha.trim();
								String[] vetorPing = linha.split("/");
								System.out.println("Média " + vetorPing[4]);
							}
						linha = buffer.readLine();
					}
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}

