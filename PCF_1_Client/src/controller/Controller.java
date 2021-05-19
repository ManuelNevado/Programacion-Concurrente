package controller;

import view.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.sun.jdi.Type;

import model.*;


public class Controller {
	static OyenteServidor os;
	public static void main( String args[]) {
		View view = new View("cmd");
		Boolean exit = false;
		view.start();
		view.notify_good("Introduce id del cliente");
		String id = view.getString();
		try {
			ArrayList<String> files = new ArrayList<String>();
			view.notify_good("Introduce el nombre de los ficheros que tienes (x3)");
			for( int i=0;i<3;i++) {
				String s = view.getString();
				files.add(s);
			}
			view.notify_good("Introduce el puerto");
			int port = 5000;
			Client client = new Client(files,port,id);
			os = new OyenteServidor(view,client);
			new Thread(os).start();
		} catch (IOException e) {
			view.raiseException("Error al crear el cliente/n"+e.getStackTrace());
		}
	}
			/*
			client.init();//Comunicacion de la lista de ficheros del servidor con el cliente
			String msg = null;
			String ans = null;
			
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
					 }else if(msg.contentEquals("descargar")) {
						 String filename = view.getString();
						 client.writeUTF(filename);//Enviamos el nombre del fichero que queremos descargar
						 ans = client.readUTF();
						 prepareDownload(ans);
					 }
				}
			}
			
			
		} catch (IOException e) {
			view.raiseException("Error al crear el cliente/n"+e.getStackTrace());
		}
		view.exit();
		*/
	
	
	public HashMap<String,ArrayList<String>> toMap(String s){
		Gson g = new Gson();
		HashMap<String,ArrayList<String>> map = g.fromJson(s,new TypeToken<HashMap<String,ArrayList<String>>>(){}.getType());
		return map;
	}
}
