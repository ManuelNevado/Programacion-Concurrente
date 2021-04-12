
public class Main {
    public static class MyTask implements Runnable {

        private int count = 0;
        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            
            for (int i = 0; i < 10_000; i++) {
            	if(name.contentEquals("+")) {
            		this.count++;
            	}
            	else { 
            		this.count--;
            	}
            }
            System.out.printf("[%s] Count: %d\n", name, this.count);
        }
    }
    
    public static void main(String[] args) {
        Runnable myTask = new MyTask();
        Thread mas = new Thread(myTask, "+");
        Thread menos = new Thread(myTask, "-");
        mas.start();
        menos.start();
    }

}