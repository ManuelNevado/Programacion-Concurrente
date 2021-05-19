package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

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
		//Locks
		server.addInfo(clientID, files);
		//unlock
		
	}
	
	public void procesar(String s) throws IOException {
		switch(s) {
		case "download":
			//DescargarFichero msg = new DescargarFichero("",false);
			//msg = msg.toObj(s);
			//indeedDownload(msg);
		case "infoserver":
			getInfoServer();
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
		//Semaforo
		String s = trans.toJson(server.getInfo());
		//Salida de semaforo
		out.writeUTF(s);
	}

	private void indeedDownload(Msg msg) {
		// TODO Auto-generated method stub
		
	}

}
