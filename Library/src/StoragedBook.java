import java.util.ArrayList;

public class StoragedBook extends Book {
    public StoragedBook(String _name, int _numberOfPages, ArrayList<Person> _authors, int _yearOfPublication) {
        super(_name, _numberOfPages, _authors, _yearOfPublication);
        numberOfBooks = 1;
    }

    public int numberOfBooks;

    @Override
    public String toString() {
        return super.toString() + "Number of avaliable books: " + numberOfBooks + "\n";
    }
}
