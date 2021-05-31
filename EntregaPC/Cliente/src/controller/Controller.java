package controller;

import view.*;

import java.io.File;
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
			ArrayList<Integer> files = new ArrayList<Integer>();
			view.notify_good("Introduce el nombre de los ficheros que tienes (x3)");
			for( int i=0;i<3;i++) {
				int s = view.getInt();
				files.add(s);
			}
			view.notify_good("Introduce el puerto del servidor de descarga. Debe ser unico");
			Scanner scanner = new Scanner(System.in);
			int port = scanner.nextInt();
			Client client = new Client(files,port,id);
			os = new OyenteServidor(view,client);
			Thread t = new Thread(os,client.getId());
			t.start();
		} catch (IOException e) {
			e.getStackTrace();
		}
	}
	
	
	public HashMap<String,ArrayList<String>> toMap(String s){
		Gson g = new Gson();
		HashMap<String,ArrayList<String>> map = g.fromJson(s,new TypeToken<HashMap<String,ArrayList<String>>>(){}.getType());
		return map;
	}
}
