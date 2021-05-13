package controller;
import java.io.IOException;
import model.*;
import view.*;

public class Controller {
	public static void main(String args[]) {
		View view = new View("cmd");
		Server server = null;
		try {
			server = new Server();
		} catch (IOException e) {
			view.raiseException("Servidor no creado\n"+e.getMessage());
		}
		try {
			server.wait4client();
		}catch(Exception e) {

			view.raiseException("Error esperando al cliente\n"+e.getMessage());
		}
		try {
			if(server.checkConnection()) {
				
			}
		}catch(Exception e) {
			view.raiseException("Error en la comprobacion de la conexion\n"+e.getMessage());
		}
	}
}
