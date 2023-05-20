import java.util.ArrayList;

public class Book {
    private final String name;
    private final int numberOfPages;
    private final ArrayList<Person> authors;
    private final int yearOfPublication;

    public Book(String _name, int _numberOfPages, ArrayList<Person> _authors, int _yearOfPublication) {
        name = _name;
        numberOfPages = _numberOfPages;
        authors = _authors;
        yearOfPublication = _yearOfPublication;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Person> getAuthors() {
        return authors;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public String toString() {
        StringBuilder authorsInfo = new StringBuilder();
        for (Person author : authors) {
            authorsInfo.append(author.getName()).append(", ");
        }
        return "Name: " + name + "\nNumber of pages: " + numberOfPages +
                "\nAuthors: " + authorsInfo + "\nYear of publication: " + yearOfPublication + "\n";
    }
}
