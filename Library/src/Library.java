import java.util.*;
import java.util.Scanner;

public class Library extends ArrayList<StoragedBook> {
    public Optional<Book> getBook(String name) {
        int counter = 1;
        for (StoragedBook book : this) {
            if (Objects.equals(book.getName(), name)) {
                if (book.numberOfBooks > 0) {
                    System.out.print(counter + ". " + book + "\n");
                    ++counter;
                }

            }
        }
        if (counter == 1) {
            System.out.print("There is no book with this name.\n");
            return Optional.empty();
        }
        if (counter == 2) {
            counter = 1;
        } else {
            System.out.print("Print book number.\n");
            Scanner in = new Scanner(System.in);
            counter = in.nextInt();
        }
        for (int i = 0; i < this.size(); ++i) {
            if (Objects.equals(this.get(i).getName(), name) && this.get(i).numberOfBooks > 0) {
                --counter;
                if (counter == 0) {
                    if(this.get(i).numberOfBooks==1){
                        return Optional.ofNullable(this.remove(i));
                    }else{
                        --this.get(i).numberOfBooks;
                        return Optional.ofNullable(this.get(i));
                    }
                }
            }
        }
        return Optional.empty();
    }

    public void addBook(Book book) {
        for (StoragedBook libraryBook : this) {
            if (Objects.equals(libraryBook.getName(), book.getName()) &&
                    libraryBook.getYearOfPublication() == book.getYearOfPublication() &&
                    libraryBook.getAuthors().equals(book.getAuthors())) {
                ++libraryBook.numberOfBooks;
                return;
            }
        }
        this.add(new StoragedBook(book.getName(), book.getNumberOfPages(), book.getAuthors(), book.getYearOfPublication()));
    }

    public boolean isAvaliable(String name) {
        for (StoragedBook book : this) {
            if (Objects.equals(book.getName(), name) && book.numberOfBooks > 0) {
                return true;
            }
        }
        return false;
    }
}
