package model;

public class DescargarFichero extends Msg{
	String fileName;
	Boolean p;
	public DescargarFichero(String fileName,Boolean p) {
		super("download", false, p);
		this.fileName=fileName;
		this.p=p;
	}

	@Override
	public DescargarFichero toObj(String msg) {
		String[] parts = msg.split(".");
		String fn = parts[0];
		String petition = parts[3];
		Boolean p=false;
		if(petition.contentEquals("?")) p=true;
		return new DescargarFichero(fn,p);
	}
	
	@Override
	public String toString() {
		String s = "";
		s+="download.";
		s+=".";
		if(p)s+="?";
		s+=".";
		s+=fileName;
		return s;
	}

}
