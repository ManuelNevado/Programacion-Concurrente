package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Usuario {
	private String ID;
	private String IP;
	private List<String> files = null; 
	
	public Usuario(String ID, String IP, List<String> files) {
		this.ID = ID;
		this.IP = IP;
		this.files = files;
	}
	
	
}
