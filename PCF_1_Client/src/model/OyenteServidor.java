package model;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import view.View;

public class OyenteServidor implements Runnable{
	private Client client;
	private View view;
	private Scanner scanner;
	//private Semaphore mutex;
	
	public OyenteServidor(View view, Client client)throws IOException{
		this.client = client;
		this.view = view;
		scanner = new Scanner(System.in);
		//this.mutex = mutex;
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
				//Si el tipo del mensaje ha sido un infoserver
				 if(msg.contentEquals("infoserver")) {
					 client.writeUTF(msg);
					 //Transformar el String a el hashmap
					 ans = client.readUTF();
					 HashMap<String,ArrayList<Integer>> map = toMap(ans);
					 view.printMap(map);
				 }else if(msg.contentEquals("download")) {
					 client.writeUTF(msg);
					 int name;
					 name = scanner.nextInt();
					 client.writeInt(name);//Enviamos el nombre del fichero que queremos descargar
					 String ip = client.readUTF();//leer ip
					 int port = client.readInt();//leer puerto
					 prepareDownload(ip,port,name);
				}else if( msg.contentEquals("exit")) {
					exit = true;
					client.exit();
				}
			}
		}catch(IOException e) {
			e.getStackTrace();
		}
	}
	
	public HashMap<String,ArrayList<Integer>> toMap(String s){
		Gson g = new Gson();
		HashMap<String,ArrayList<Integer>> map = g.fromJson(s,new TypeToken<HashMap<String,ArrayList<Integer>>>(){}.getType());
		return map;
	}
	
	private void prepareDownload(String IP, int port,int name) throws IOException {
		if(IP.contentEquals("null"))view.notify_good("No file with that name");
		else if(IP.contentEquals("me"))view.notify_good("I already have that file");
		else {
			Gson g = new Gson();
			InetAddress ip = g.fromJson(IP, new TypeToken<InetAddress>() {}.getType());
			view.notify_good("Realizando conexion de descarga de archivos.......");
			System.out.println("Realizando conexion al cliente con ip: "+IP+" y puerto: "+port);
			Socket socket = new Socket(ip,port);
			System.out.println("Socket creado!");
			//Descarga.run()
			descargarFichero(socket,name);
		}
	}
	private void descargarFichero(Socket socket, int name) throws IOException{
		DataInputStream din = new DataInputStream(socket.getInputStream());
		DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
		dout.writeInt(name);
		int download = din.readInt();
		view.notify_good("Fichero "+download +" descargado");
		//Solo hay un oyente servidor por cliente asi que 
		client.updateFileList(download);
		//update msg
		Gson g = new Gson();
		client.writeUTF("update");
		String msg = g.toJson(client.getFiles());
    	client.writeUTF(msg);
    	client.writeUTF(client.getId());
	}

}
