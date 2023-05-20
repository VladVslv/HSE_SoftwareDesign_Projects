//Abstract element
public abstract class Person {
    String name;
    int salary;

    public Person(String n, int s) {
        name = n;
        salary = s;
    }

    abstract void AcceptPrinter(Printer printer);
}
