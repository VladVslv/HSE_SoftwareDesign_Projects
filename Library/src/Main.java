import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        ArrayList<Person> authors=new ArrayList<>();
        authors.add(new Person("Greg"));
        authors.add(new Person("Oleg"));

        Optional<Book> book;
        String bookName;
        Scanner in = new Scanner(System.in);
        String command;
        Library grandLibrary=new Library();
        grandLibrary.addBook(new Book("First",145,authors,1973));
        grandLibrary.addBook(new Book("Second",134,authors,2019));
        grandLibrary.addBook(new Book("Second",134,authors,2019));
        grandLibrary.addBook(new Book("Second",167,authors,2021));

        Person student = new Person("Steve");
        while (true) {
            System.out.print("Print next command:\n");
            command = in.next();
            if (Objects.equals(command, "get")) {
                bookName = in.next();
                book = grandLibrary.getBook(bookName);
                book.ifPresent(student::takeTheBook);
            } else if (Objects.equals(command, "put")) {
                bookName = in.next();
                book = student.returnTheBook(bookName);
                book.ifPresent(grandLibrary::addBook);
            } else if (Objects.equals(command, "list")) {
                for (Book studentBook : student.getPrivateLibrary()) {
                    System.out.print(studentBook + "\n");
                }
            } else if (Objects.equals(command, "all")) {
                for (Book grandLibraryBook : grandLibrary) {
                    System.out.print(grandLibraryBook + "\n");
                }
            } else {
                break;
            }
        }
    }

}