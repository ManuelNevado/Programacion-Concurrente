package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server{
	private ServerSocket server = null;
	private Socket sc = null;
	private DataInputStream in;
	private DataOutputStream out;
 	private int PUERTO = 5000;
 	private HashMap<String,ArrayList<String>> info;
 	
	public Server() throws IOException{
		server = new ServerSocket(PUERTO);
		info = new HashMap<String, ArrayList<String>>();
	}
	
	public void wait4clients() throws IOException{
		while(true) {
			OyenteCliente oc=null;
			sc = server.accept();
			oc = new OyenteCliente(sc,this);
			new Thread(oc).start();
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
	
	public void addInfo(String id, ArrayList<String> files) {
		info.put(id, files);
	}
	
	public HashMap<String, ArrayList<String>> getInfo() {
		return info;
	}
	

}
 