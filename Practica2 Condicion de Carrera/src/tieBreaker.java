import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class tieBreaker {
	private AtomicInteger turn;
	private AtomicBoolean[] flag;
	private int nprocesos;
	
	 tieBreaker(int nprocesos) {
		this.nprocesos=nprocesos;
		flag = new AtomicBoolean[nprocesos];
		for(int i=0;i<nprocesos;i++)
			flag[i] = new AtomicBoolean();
		turn= new AtomicInteger(0);
	}
	 
	 void lock(int id) {
		 flag[id].set(true);
		 turn.set(id);
		 while(turn.get() == id && flag[nprocesos-1-id].get());
	 }
	 
	  void unlock(int id) {
		  flag[id].set(false);
	  }
}
