public class Main {
    public static void main(String[] args) {
        University university=new University();
        university.people.add(new Cleaner("john",5));
        university.people.add(new Professor("ben",25));
        Printer printer=new NonSalaryPrinter();
        university.PrintInfo(printer);
        System.out.print("\n");
        printer=new AllInfoPrinter();
        university.PrintInfo(printer);
    }
}