package model;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Server extends Conection{
	
	public Server() throws IOException {
		super("servidor");
	}
	
	public void wait4client() throws IOException{
		cs = ss.accept();
		serverOut = new DataOutputStream(cs.getOutputStream());
		serverIn = new DataInputStream(cs.getInputStream());
	}
	
	public Boolean checkConnection() throws IOException {
		serverOut.writeUTF("cnxdone?");
		String in = serverIn.readUTF();
		if(in.contentEquals("cnxdone"))
			return true;
		return false;
	}
	

}
