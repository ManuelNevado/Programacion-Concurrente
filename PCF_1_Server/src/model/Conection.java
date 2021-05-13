package model;

import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Conection {
	private final int PUERTO = 1234; //Puerto para la conexión
    private final String HOST = "localhost"; //Host para la conexión
    protected String mensajeServidor; //Mensajes entrantes (recibidos) en el servidor
    protected ServerSocket ss; //Socket del servidor
    protected Socket cs;
    protected DataOutputStream serverOut, clientOut; //Flujo de datos de salida
    protected DataInputStream serverIn, clientIn;

    public Conection(String tipo) throws IOException{ //Constructor
    	if(tipo.equalsIgnoreCase("server")) {
    		ss = new ServerSocket(PUERTO);//Se crea el socket para el servidor en puerto 1234 
    		cs = new Socket();
    	}else {
    		cs = new Socket(HOST,PUERTO);
    	}
    }
}
