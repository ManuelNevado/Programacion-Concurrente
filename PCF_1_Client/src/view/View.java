package view;

import java.util.Scanner;

public class View {
	
	public static final String TEXT_RESET = "\u001B[0m";
	public static final String TEXT_BLACK = "\u001B[30m";
	public static final String TEXT_RED = "\u001B[31m";
	public static final String TEXT_GREEN = "\u001B[32m";
	public static final String TEXT_YELLOW = "\u001B[33m";
	public static final String TEXT_BLUE = "\u001B[34m";
	public static final String TEXT_PURPLE = "\u001B[35m";
	public static final String TEXT_CYAN = "\u001B[36m";
	public static final String TEXT_WHITE = "\u001B[37m";
	
	private String tipo;
	private Scanner scanner;
	
	public View(String tipo) {
		this.tipo = tipo;
		scanner = new Scanner(System.in);
	}
	
	private void print(String s) {
		System.out.println(s);
	}
	
	public void start() {
		print(TEXT_GREEN + "Iniciando cliente..." + TEXT_RESET);
	}
	
	public void exit() {
		print(TEXT_GREEN + "Desconectado del servidor" + TEXT_RESET);
	}
	
	public void raiseException(String msg) {
		print(TEXT_RED + msg + TEXT_RESET);
	}
	
	public void notify_good(String s) {
		print(TEXT_BLUE + s + TEXT_RESET);
	}

	public String getString() {
		return scanner.nextLine();
	}
	
	public int getInt() {
		return scanner.nextInt();
	}
	
	
}