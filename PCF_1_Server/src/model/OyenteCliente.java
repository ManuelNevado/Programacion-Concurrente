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
	
	public OyenteCliente(Socket sc) throws IOException{
		this.sc = sc;
		in = new DataInputStream(sc.getInputStream());
		out = new DataOutputStream(sc.getOutputStream());
		trans = new Gson();
		end = true;
	}
	
	
	
	private void init() throws IOException {
		String list = in.readUTF();
		files = trans.fromJson(list,new TypeToken<ArrayList<String>>(){}.getType());
		
	}
	
	public void procesar(String msg) {
		
	}
	
	@Override
	public void run(){
		try {
			init();//Lee los ficheros del cliente
			
			while(end) {
				String msg = in.readUTF();
				procesar(msg);
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> showFiles() {
		return files;
	}

}
