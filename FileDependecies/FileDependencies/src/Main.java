import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.print("Print path to the root directory:\n");
        Scanner scanner = new Scanner(System.in);
        // Ввод пути к корневой директории.
        String path = scanner.nextLine();
        TextFileScanner searcher;
        try {
            // Объект для поиска текстовых файлов в директории.
            searcher = new TextFileScanner(path);
        } catch (Exception e) {
            System.out.print("Wrong path.");
            return;
        }
        ArrayList<File> allTextFiles = searcher.getFiles();
        try {
            // Пострение зависимостей и списка файлов, удовлетворяющим этим зависимостям.
            Concatenator constructor = new Concatenator(allTextFiles, path);
            // Вывод конкатенации всех файлов.
            constructor.printConcatenation();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }
}