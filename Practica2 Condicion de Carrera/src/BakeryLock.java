import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class BakeryLock {
	private AtomicBoolean[] flag;
    private AtomicInteger[] label;

    private int n;

    /**
     * Constructor for Bakery lock
     *
     * @param n thread count
     */
    public BakeryLock(int n) {
        this.n = n;
        flag = new AtomicBoolean[n];
        label = new AtomicInteger[n];
        for (int i = 0; i < n; i++) {
            flag[i] = new AtomicBoolean();
            label[i] = new AtomicInteger();
        }
    }

    /**
     * Acquires the lock.
     */
    public void lock(int id) {
        int i = id;
        flag[i].set(true);
        label[i].set(findMaximumElement(label) + 1);
        for (int k = 0; k < n; k++) {
            while ((k != i) && flag[k].get() && ((label[k].get() < label[i].get()) || ((label[k].get() == label[i].get()) && k < i))) {
                //spin wait
            }
        }
    }

    /**
     * Releases the lock.
     */
    
    public void unlock(int id) {
        flag[id].set(false);
    }

    /**
     * Finds maximum element within and {@link java.util.concurrent.atomic.AtomicInteger} array
     *
     * @param elementArray element array
     * @return maximum element
     */
    private int findMaximumElement(AtomicInteger[] elementArray) {
        int maxValue = Integer.MIN_VALUE;
        for (AtomicInteger element : elementArray) {
            if (element.get() > maxValue) {
                maxValue = element.get();
            }
        }
        return maxValue;
    }
}
