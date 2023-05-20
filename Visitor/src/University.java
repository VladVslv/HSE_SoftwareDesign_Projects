import java.util.ArrayList;

//Structure with all elements
public class University {
    ArrayList<Person> people = new ArrayList<Person>();
    //Add and Remove can be added
    public void PrintInfo(Printer printer) {
        for (Person person : people) {
            person.AcceptPrinter(printer);
        }
    }
}
