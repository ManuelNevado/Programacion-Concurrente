

public class Main {
    public static class MyTask implements Runnable {

        private tieBreaker lock = new tieBreaker(2);
        
        private int count = 0;
        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            
            for (int i = 0; i < 10_000; i++) {
            	if(name.contentEquals("+")) {
            		lock.lock(0);
            		this.count++;
            		lock.unlock(0);
            	}
            	else {
            		lock.lock(1);
            		this.count--;
            		lock.unlock(1);
            	}
            }
            System.out.printf("[%s] Count: %d\n", name, this.count);
        }
    }
    
    public static void lanzarThreads(Thread mas, Thread menos, Lock lock) {
		mas.start();
		menos.start();
    	
    }
    
    public static void main(String[] args) {
        Runnable myTask = new MyTask();
        Thread mas = new Thread(myTask, "+");
        Thread menos = new Thread(myTask, "-");
        mas.start();
        menos.start();
    }

}
