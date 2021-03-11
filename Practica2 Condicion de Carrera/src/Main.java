

public class Main {
    public static class MyTask implements Runnable {

        // The count member variable is shared between multiple threads
        // that are executing the same instance of the MyTask runnable.
        private int count = 0;
        private Lock lock = new Lock("+");

        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            
            for (int i = 0; i < 10_000; i++) {
            	lock.ban(name);
            	if(name.contentEquals("+"))
            		this.count++;
            	else
            		this.count--;
            	lock.unBan(name);
            }
            System.out.printf("[%s] Count: %d\n", name, this.count);
        }
    }
    public static void main(String[] args) {
        Runnable myTask = new MyTask();

        Thread thread1 = new Thread(myTask, "+");
        Thread thread2 = new Thread(myTask, "-");
        thread1.start();
        thread2.start();
    }

}
