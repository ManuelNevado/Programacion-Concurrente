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
 	private HashMap<String, Socket> usuario_socket;
 	
	public Server() throws IOException{
		server = new ServerSocket(PUERTO);
		info = new HashMap<String, ArrayList<String>>();
		usuario_socket = new HashMap<String,Socket>();
	}
	
	public void wait4clients() throws IOException{
		while(true) {
			OyenteCliente oc=null;
			sc = server.accept();
			oc = new OyenteCliente(sc,this);
			new Thread(oc).start();
			//PUERTO +=1;
			//server = new ServerSocket(PUERTO);
			
		}
	}
	
	public void addInfo(String id, ArrayList<String> files) {
		info.put(id, files);
	}
	
	public void addUser(String id, Socket s) {
		usuario_socket.put(id, s);
	}
	
	public HashMap<String, ArrayList<String>> getInfo() {
		return info;
	}
	
	public HashMap<String,Socket> getUsers(){
		return usuario_socket;
	}
	
	public Socket getSocketFromUserID(String ID) {
		return usuario_socket.get(ID);
	}

}
 