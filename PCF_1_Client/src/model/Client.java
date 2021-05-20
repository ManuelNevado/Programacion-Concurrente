package model;

import java.io.DataOutputStream;
import java.io.File;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.google.gson.*;

public class Client{
    private final String HOST = "127.0.0.1";
    private DataInputStream in;
    private DataOutputStream out;
    private Socket sc = null;
    private String name = null;
    private String ID = null;
    private InetAddress IP = null;
    private List<Integer> files = null;
    private Gson trans = null;
    private OyenteDescarga od;
    private int puerto;
    
    
    public Client(ArrayList<Integer> files, int puerto,String id) throws IOException{
    	trans = new Gson();
    	sc = new Socket(HOST, 5000);
    	in = new DataInputStream (sc.getInputStream());
    	out = new DataOutputStream(sc.getOutputStream());
    	IP = sc.getInetAddress();
    	this.files = files;
    	ID=id;
    	this.puerto = puerto;
    	startFileServer();
    }
    
    public void init() throws IOException {
    	String msg = trans.toJson(files);
    	out.writeUTF(msg);
    	out.writeUTF(ID);
    	out.writeInt(puerto);
    }
    
    public String readUTF() throws IOException{
    	return in.readUTF();
    }
    
    public int readInt() throws IOException{
    	return in.readInt();
    }
    
    public void writeUTF(String s) throws IOException{
    	out.writeUTF(s);
    }
    
    public void writeInt(int i) throws IOException {
    	out.writeInt(i);
    }
    
    public void exit() throws IOException{
    	sc.close();
    }
    
    private void startFileServer() throws IOException{
    	OyenteDescarga od = new OyenteDescarga(puerto,this);
    	new Thread(od,"Descarga"+ID).start();
    }
    
    public void updateFileList(int f) {
    	files.add(f);
    }
    
    public String getId() {
    	return this.ID;
    }
    
    public List<Integer> getFiles(){
    	return files;
    }
    
}