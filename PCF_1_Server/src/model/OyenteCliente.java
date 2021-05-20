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
	private Semaphore mutex;
	private Bakery_lock lock;
	
	public OyenteCliente(Socket sc, Server server, Semaphore mutex, Bakery_lock lock) throws IOException{
		this.sc = sc;
		in = new DataInputStream(sc.getInputStream());
		out = new DataOutputStream(sc.getOutputStream());
		trans = new Gson();
		end = true;
		this.server = server;
		this.mutex = mutex;
		this.lock = lock;
	}
	
	
	
	private void init() throws IOException {
		String list = in.readUTF();
		files = trans.fromJson(list,new TypeToken<ArrayList<Integer>>(){}.getType());
		clientID = in.readUTF();
		int port = in.readInt();
		//coger semaforo
		try {
			mutex.acquire();
			server.addInfo(clientID, files);
			mutex.release();
		}catch(Exception e) {
			e.printStackTrace();
		}
		//soltar semaforo
		
		//lock
		int idlock = 0;
		if(Thread.currentThread().getName().contentEquals("paco")) idlock = 1;
		lock.lock(idlock);
		server.addUser(clientID, sc,port);
		lock.unlock(idlock);
		//unlock
		
	}
	
	public void procesar(String s) throws IOException {
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
		}
		
	}
	
	@Override
	public void run(){
		try {
			init();//Lee los ficheros del cliente
			while(end) {
				String line = in.readUTF();
				procesar(line);
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Integer> showFiles() {
		return files;
	}
	
	private void getInfoServer() throws IOException {		
		String s = trans.toJson(server.getInfo());
		out.writeUTF(s);
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
			mutex.acquire();
			server.addInfo(clientID, files);
			mutex.release();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
