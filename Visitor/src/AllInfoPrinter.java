//Visitor 2
public class AllInfoPrinter implements Printer{
    @Override
    public void PrintCleanerInfo(Cleaner cleaner) {
        System.out.print("Cleaner: "+ cleaner.name+". Salary: "+ cleaner.salary+".\n");
    }

    @Override
    public void PrintProfessorInfo(Professor professor) {
        System.out.print("Super professor: "+professor.name+". Salary: "+professor.salary+".\n");
    }
}
