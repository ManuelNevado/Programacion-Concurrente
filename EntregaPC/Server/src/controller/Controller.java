package controller;
import java.io.IOException;
import model.*;
import view.*;

public class Controller {
	public static void main(String args[]) {
		View view = new View("cmd");
		Server server = null;
		view.start();

		try {
			server = new Server();
			server.wait4clients(); //Se queda parado haste que acepta un client. En la version distribuida crear thread con el sc
			
		} catch (IOException e) {
			view.raiseException("Servidor no creado\n");
			e.getStackTrace();
		}
		
	}
}
