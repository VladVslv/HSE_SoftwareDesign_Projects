public class Main {
    public static void main(String[] args) {
        Data buffer = new Data();
        new Thread(new Reader(buffer, "John")).start();
        new Thread(new Writer(buffer, "Oleg")).start();
    }
}