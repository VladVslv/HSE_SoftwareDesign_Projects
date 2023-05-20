import java.util.Optional;

public class Person {
    private final String name;
    private final Library privateLibrary;

    Person(String _name) {
        name = _name;
        privateLibrary = new Library();
    }

    public String getName() {
        return name;
    }

    public Library getPrivateLibrary() {
        return privateLibrary;
    }

    public void takeTheBook(Book book) {
        privateLibrary.addBook(book);
    }

    public Optional<Book> returnTheBook(String name) {
        return privateLibrary.getBook(name);
    }
}
