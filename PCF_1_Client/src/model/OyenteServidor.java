package model;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import view.View;

public class OyenteServidor implements Runnable{
	private Client client;
	private View view;
	private Scanner scanner;
	public OyenteServidor(View view, Client client)throws IOException{
		this.client = client;
		this.view = view;
		scanner = new Scanner(System.in);
	}
	@Override
	public void run(){
		// TODO Auto-generated method stub
		try {
			client.init();//Comunicacion de la lista de ficheros del servidor con el cliente
			String msg = null;
			String ans = null;
			Boolean exit = false;
			//Bucle principal
			while(!exit) {
				msg = view.displayMenu();
				client.writeUTF(msg);
				if(Objects.isNull(msg))exit=true;
				else {
					 //Si el tipo del mensaje ha sido un infoserver
					 if(msg.contentEquals("infoserver")) {
						 //Transformar el String a el hashmap
						 ans = client.readUTF();
						 HashMap<String,ArrayList<String>> map = toMap(ans);
						 view.printMap(map);
					 }else if(msg.contentEquals("download")) {
						 String name;
						 name = scanner.nextLine();
						 client.writeUTF(name);//Enviamos el nombre del fichero que queremos descargar
						 ans = client.readUTF();
						 prepareDownload(ans);
					 }
				}
			}
		}catch(IOException e) {
			view.raiseException("Error al crear el cliente/n"+e.getStackTrace());
		}
	}
	
	public HashMap<String,ArrayList<String>> toMap(String s){
		Gson g = new Gson();
		HashMap<String,ArrayList<String>> map = g.fromJson(s,new TypeToken<HashMap<String,ArrayList<String>>>(){}.getType());
		return map;
	}
	
	private void prepareDownload(String IP) throws IOException {
		if(IP.contentEquals("null"))view.notify_good("No file with that name");
		else if(IP.contentEquals("me"))view.notify_good("I already have that file");
		else {
			Gson g = new Gson();
			InetAddress ip = g.fromJson(IP, new TypeToken<InetAddress>() {}.getType());
			view.notify_good("Realizando conexion de descarga de archivos.......");
			Socket socket = new Socket(ip,8080);
			//Descarga.run()
		}
	}

}
