/*
 * Version del lock rompe empate para dos Threads
 * que comparten una misma variable.
 * Por Manuel Nevado para la Parte 1 de la practica 2 de Programacion Concurrente
 */


public class Lock {
	String last;
	public Lock(String last) {
		this.last=last;
	}
	
	public void ban(String name) {
		while(last==name);
		
	}
	
	public void unBan(String name) {
		last=name;
	}
}
