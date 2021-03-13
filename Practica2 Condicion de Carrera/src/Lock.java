/*
 * Version del lock rompe empate para dos Threads
 * que comparten una misma variable.
 * Por Manuel Nevado para la Parte 1 de la practica 2 de Programacion Concurrente
 */


public class Lock {
	
	private long lastPID;
	
	public Lock(long l) {
		this.lastPID=l;
	}
	
	public void enter(long pid) {
		while(lastPID!=pid);
	}
	
	public void exit(long pid) {
		lastPID=pid;
	}
}
