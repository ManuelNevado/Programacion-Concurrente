package model;

import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.*;

public class Client{
    private final String HOST = "127.0.0.1";
    private final int PUERTO = 5000;
    private DataInputStream in;
    private DataOutputStream out;
    private Socket sc = null;
    private String name = null;
    private String ID = null;
    private InetAddress IP = null;
    private List<String> files = null;
    private Gson trans = null;
    
    public Client(ArrayList<String> files, int puerto,String id) throws IOException{
    	trans = new Gson();
    	sc = new Socket(HOST, puerto);
    	in = new DataInputStream (sc.getInputStream());
    	out = new DataOutputStream(sc.getOutputStream());
    	IP = sc.getInetAddress();
    	this.files = files;
    	ID=id;
    }
    
    public void init() throws IOException {
    	String msg = trans.toJson(files);
    	out.writeUTF(msg);
    	out.writeUTF(ID);
    }
    
    public String readUTF() throws IOException{
    	return in.readUTF();
    }
    
    public void writeUTF(String s) throws IOException{
    	out.writeUTF(s);
    }
    
    public void exit() throws IOException{
    	sc.close();
    }
    
}