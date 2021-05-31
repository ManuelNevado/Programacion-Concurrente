package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Server{
	private ServerSocket server = null;
	private Socket sc = null;
 	private int PUERTO = 5000;
 	private HashMap<String,ArrayList<Integer>> info;
 	private HashMap<String, Socket> usuario_socket;
 	private HashMap<String, Integer> usuario_descarga;
 	private Semaphore mutex1;
 	private Semaphore mutex2;
 	private Lock lock;
 	private int contadorSem;
 	
	public Server() throws IOException{
		mutex1 = new Semaphore(1,true);
		mutex2 = new Semaphore(1,true);
		contadorSem = 0;
		server = new ServerSocket(PUERTO);
		info = new HashMap<String, ArrayList<Integer>>();
		usuario_descarga = new HashMap<String,Integer>();
		usuario_socket = new HashMap<String,Socket>();
		lock = new ReentrantLock(true);
	}
	
	public void wait4clients() throws IOException{
		while(true) {
			OyenteCliente oc=null;
			sc = server.accept();
			oc = new OyenteCliente(sc,this,mutex1,mutex2,contadorSem,lock);
			new Thread(oc).start();
			//PUERTO +=1;
			//server = new ServerSocket(PUERTO);
			
		}
	}
	
	public void addInfo(String id, ArrayList<Integer> files) {
		info.put(id, files);
	}
	
	public void addUser(String id, Socket s, int port) {
		usuario_socket.put(id, s);
		usuario_descarga.put(id, port);
	}
	
	public HashMap<String, ArrayList<Integer>> getInfo() {
		return info;
	}
	
	public HashMap<String,Socket> getUsers(){
		return usuario_socket;
	}
	
	public Socket getSocketFromUserID(String ID) {
		return usuario_socket.get(ID);
	}
	
	public int getPortFromUserID(String ID) {
		return usuario_descarga.get(ID);
	}
	
	public void removeUserFromServer(String ID) {
		info.remove(ID);
		usuario_socket.remove(ID);
		usuario_socket.remove(ID);
	}

}
 