package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class OyenteCliente implements Runnable{
	
	private Socket sc;
	private DataInputStream in;
	private DataOutputStream out;
	private ArrayList<Integer> files;
	private Gson trans;
	public Boolean end = null;
	public String clientID;
	private Server server;
	private Semaphore mutex1;
	private Semaphore mutex2;
	private Lock lock;
	private int contador;
	
	public OyenteCliente(Socket sc, Server server, Semaphore mutex1, Semaphore mutex2, int contador, Lock lock) throws IOException{
		this.sc = sc;
		in = new DataInputStream(sc.getInputStream());
		out = new DataOutputStream(sc.getOutputStream());
		trans = new Gson();
		end = false;
		this.server = server;
		this.mutex1 = mutex1;
		this.mutex2 = mutex2;
		this.lock = lock;
		this.contador = contador;
	}
	
	
	
	private void init() throws IOException {
		String list = in.readUTF();
		files = trans.fromJson(list,new TypeToken<ArrayList<Integer>>(){}.getType());
		clientID = in.readUTF();
		int port = in.readInt();
		//Escritura
		try {
			mutex2.acquire();
			server.addInfo(clientID, files);
			server.addUser(clientID, sc,port);
			mutex2.release();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		

		
	}
	
	public void procesar(String s) throws IOException, InterruptedException {
		switch(s) {
		case "download":
			int fn = in.readInt();
			indeedDownload(fn);
			break;
		case "infoserver":
			getInfoServer();
			break;
		case "update":
			indeedUpdate();
			break;
		case "exit":
			exit();
			end = true;
			break;
		}
		
	}
	
	@Override
	public void run(){
		try {
			init();//Lee los ficheros del cliente
			while(!end) {
				String line = in.readUTF();
				procesar(line);
			}
			
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Integer> showFiles() {
		return files;
	}
	
	private void getInfoServer() throws IOException, InterruptedException {		
		String s = trans.toJson(server.getInfo());
		//Lectura
		mutex1.acquire();
		contador++;
		if(contador == 1) {
			mutex2.acquire();
		}
		mutex1.release();
		//****
		out.writeUTF(s);
		//****
		mutex1.acquire();
		contador--;
		if(contador==0)
			mutex2.release();
		mutex1.release();
	}

	private void indeedDownload(int inte) throws IOException {
		String user="";
		HashMap<String, ArrayList<Integer>> map = server.getInfo();
		for(String id : map.keySet()) {
			for(Integer i : map.get(id)) {
				if(i == inte) {
					user = id;
				}
			}
		}
		try {
			if(!user.contentEquals(clientID)) {
				InetAddress ip = server.getSocketFromUserID(user).getInetAddress();
				String IP = trans.toJson(ip);
				out.writeUTF(IP);
				int port = server.getPortFromUserID(user);
				out.writeInt(port);
			}else {
				out.writeUTF("me");
				out.writeUTF("");
			}
		}catch(NullPointerException e) {
			out.writeUTF("null");
			out.writeUTF("");
		}
	}
	
	public synchronized void indeedUpdate() throws IOException {//monitor
		String list = in.readUTF();
		files = trans.fromJson(list,new TypeToken<ArrayList<Integer>>(){}.getType());
		clientID = in.readUTF();
		try {
			//Escritura
			mutex2.acquire();
			server.addInfo(clientID, files);
			mutex2.release();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void exit() throws IOException, InterruptedException {
		//Los lock estan para que solo un thread ejecute esta accion
		lock.lock();
		String id = in.readUTF();
		//no puede borrarse y solicitarse informacion a la vez
		//NO-Escritura
		//Un borrado no deja de ser un tipo de escritura asi que es como si tuviera dos escritores 
		//en el problema lectores escritores
		mutex2.acquire();
		server.removeUserFromServer(id);
		mutex2.release();
		lock.unlock();
	}

}
