package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class OyenteCliente implements Runnable{
	
	private Socket sc;
	private DataInputStream in;
	private DataOutputStream out;
	private ArrayList<String> files;
	private Gson trans;
	public Boolean end = null;
	public String clientID;
	private Server server;
	
	public OyenteCliente(Socket sc, Server server) throws IOException{
		this.sc = sc;
		in = new DataInputStream(sc.getInputStream());
		out = new DataOutputStream(sc.getOutputStream());
		trans = new Gson();
		end = true;
		this.server = server;
	}
	
	
	
	private void init() throws IOException {
		String list = in.readUTF();
		files = trans.fromJson(list,new TypeToken<ArrayList<String>>(){}.getType());
		clientID = in.readUTF();
		//Monitor
		server.addInfo(clientID, files);
		//Monitor
		
		//lock
		server.addUser(clientID, sc);
		//unlock
		
	}
	
	public void procesar(String s) throws IOException {
		switch(s) {
		case "download":
			String fn = in.readUTF();
			indeedDownload(fn);
			break;
		case "infoserver":
			getInfoServer();
			break;
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
	
	public ArrayList<String> showFiles() {
		return files;
	}
	
	private void getInfoServer() throws IOException {
		
		String s = trans.toJson(server.getInfo());
		
		out.writeUTF(s);
	}

	private void indeedDownload(String filename) throws IOException {
		String user="";
		HashMap<String, ArrayList<String>> map = server.getInfo();
		for(String id : map.keySet()) {
			for(String fn : map.get(id)) {
				if(filename.contentEquals(fn)) {
					user = id;
				}
			}
		}
		try {
			if(user.contentEquals(clientID)) {
				InetAddress ip = server.getSocketFromUserID(user).getInetAddress();
				String IP = trans.toJson(ip);
				out.writeUTF(IP);
			}else {
				out.writeUTF("me");
			}
		}catch(NullPointerException e) {
			out.writeUTF("null");
		}
	}

}
