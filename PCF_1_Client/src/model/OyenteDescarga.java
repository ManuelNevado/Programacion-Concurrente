package model;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class OyenteDescarga implements Runnable{
	
	private ServerSocket server;
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	private Client client;
	
	public OyenteDescarga(int PORT, Client client) throws IOException{
		server=new ServerSocket(PORT);
		this.client = client;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				System.out.println("Servidor de descarga creado con ip: " + server.getInetAddress()+ " y con puerto: "+server.getLocalPort());
				socket = server.accept();
				System.out.println("Cliente Aceptado!");
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
				int name = in.readInt();
				int file=-1;
				for(int i : client.getFiles()) {
					if(name == i) {
						file = i;
					}
				}
				out.writeInt(file);
				
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	

}
