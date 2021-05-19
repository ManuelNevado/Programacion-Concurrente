package controller;

import view.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.*;
import model.*;


public class Controller {
	public static void main( String args[]) {
		View view = new View("cmd");
		Boolean exit = true;
		view.start();
		try {
			ArrayList<String> files = new ArrayList<String>();
			for( int i=0;i<10;i++) {
				String s = String.valueOf(i);
				files.add(s);
			}
			view.notify_good("Introduce el puerto");
			 // Create a Scanner object
			int port = view.getInt();
			Client client = new Client(files,port);
			client.init();//Comunicacion de la lista de ficheros del servidor con el cliente
			while(true) {
				view.notify_good(client.readUTF());
			}
		} catch (IOException e) {
			view.raiseException("Error al crear el cliente/n"+e.getStackTrace());
		}
		view.exit();
	}
}
