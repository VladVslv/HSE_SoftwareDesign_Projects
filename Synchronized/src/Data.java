public class Data {
    private final Integer[] data = new Integer[3];
    int size = 0;
    public synchronized void add(Integer val) {
        try {
            while (size == 10) {
                wait();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        data[size] = val;
        ++size;
        notify();
    }

    public synchronized Integer get() {
        try {
            while (size == 0) {
                wait();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Integer res = data[--size];
        notify();
        return res;
    }
}
