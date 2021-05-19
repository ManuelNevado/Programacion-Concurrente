package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server{
	private ServerSocket server = null;
	private Socket sc = null;
	private DataInputStream in;
	private DataOutputStream out;
 	private int PUERTO = 5000;
 	private List<OyenteCliente> users;
 	
 	
	public Server() throws IOException{
		server = new ServerSocket(PUERTO);
		users = new ArrayList<OyenteCliente>();
	}
	
	public void wait4clients() throws IOException{
		while(true) {
			OyenteCliente oc=null;
			sc = server.accept();
			oc = new OyenteCliente(sc);
			new Thread(oc).start();
			users.add(oc);
			PUERTO +=1;
			server = new ServerSocket(PUERTO);
		}
	}
	
	public void quitClient() throws IOException {
		out.writeUTF("Adios!");
		sc.close();
	}
	
	public void talk(String s) throws IOException{
		out.writeUTF(s);
	}
	
	public void usersInfo() {
		for(OyenteCliente oc : users) {
			for(String s : oc.showFiles()) {
				System.out.println(s+" ");
			}
			System.out.println('\n');
		}
	}
	

}
 