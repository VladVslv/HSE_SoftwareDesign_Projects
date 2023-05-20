//Concrete element 2
public class Professor extends Person {

    public Professor(String n, int s) {
        super(n, s);
    }

    @Override
    void AcceptPrinter(Printer printer) {
        printer.PrintProfessorInfo(this);
    }
}
