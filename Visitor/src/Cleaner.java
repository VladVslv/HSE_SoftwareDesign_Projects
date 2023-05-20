//Concrete element 1
public class Cleaner extends Person {

    public Cleaner(String n, int s) {
        super(n, s);
    }

    @Override
    void AcceptPrinter(Printer printer) {
        printer.PrintCleanerInfo(this);
    }
}
