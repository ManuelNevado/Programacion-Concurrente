package model;

public abstract class Msg {
	Boolean petition;
	Boolean done;
	String what;
	
	public Msg(String w, Boolean d, Boolean p) {
		what=w;
		done=d;
		petition=p;
	}
	
	@Override
	public String toString() {
		String s = what;
		s+=".";
		if(done) s=s+"done";
		s+=".";
		if(petition)s=s+"?";
		return s;
	}
	public abstract Msg toObj(String msg);
	
	public String type(String msg) {
		String[] s = msg.split(".");
		return s[0];
	}
}
