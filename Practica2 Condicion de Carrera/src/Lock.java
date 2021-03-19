import java.util.concurrent.atomic.AtomicInteger;

/*
 * Se asemeja a entrar a una panaderia en la que pides la vez:
 * 
 * int turn[i..N]=0
 * process CS[1..N]
 * {while (true) 
 *
 */

public class Lock {
	
	private int nprocesos;
	private int turn[];
	
	//Constructor
	public Lock(int num) {
		nprocesos=num;
		turn = new int [num];
	}
	
	private int max(int[] turn) { // O(n) siendo n el numero de procesos que implementan el lock
		
		int max = 0;
		for(int i =0;i<turn.length;i++) {
			if(max<turn[i]) {
				max = turn[i];
			}
		}
		
		return max;
	}
	
	private Boolean menormenor(int a, int b, int c, int d) {
		if((a>c) || ((a==c) && b>d))
			return true;
		return false;
	}
	
	//EneterLock
	public void enterLock(int idp) {
		while(true) {
			turn[idp]=1;
			turn[idp] = (max(turn)+1);
			
			for(int j=1;j<turn.length;j++) {
				while(turn[j]!=0 && menormenor(turn[idp],idp,turn[j],j));
			}
			turn[idp]=0;
		}
	}
}
