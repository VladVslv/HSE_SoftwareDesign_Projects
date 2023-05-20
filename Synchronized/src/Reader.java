public class Reader extends Human {

    Reader(Data buffer, String name) {
        super(buffer, name);
    }

    @Override
    public void run() {
        while (true) {
            System.out.printf(name_ + " read " + buffer_.get()+"\n");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}