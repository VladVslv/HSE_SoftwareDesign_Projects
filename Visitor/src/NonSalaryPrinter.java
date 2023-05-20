//Visitor 1
public class NonSalaryPrinter implements Printer{

    @Override
    public void PrintCleanerInfo(Cleaner cleaner) {
        System.out.print("Cleaner: "+ cleaner.name+"\n");
    }

    @Override
    public void PrintProfessorInfo(Professor professor) {
        System.out.print("Super professor: "+professor.name+"\n");
    }
}
