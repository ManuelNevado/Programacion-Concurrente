package model;

public class InfoServer extends Msg{

	public InfoServer(Boolean p) {
		super("infoserver", false, p);
	}

	@Override
	public InfoServer toObj(String msg) {
		String[] parts=msg.split(".");
		String what = parts[0];
		String done = parts[1];
		String pet = parts[2];
		Boolean p = false;
		if(pet.contentEquals("?")) p=true;
		return new InfoServer(p);
	}

}
