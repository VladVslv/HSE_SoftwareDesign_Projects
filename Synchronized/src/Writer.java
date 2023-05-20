import java.util.Random;

public class Writer extends Human {

    Writer(Data buffer, String name) {
        super(buffer, name);
    }

    @Override
    public void run() {
        while (true) {
            Integer num = new Random().nextInt() % 100;
            buffer_.add(num);
            System.out.printf(name_ + " wrote " + num + "\n");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
