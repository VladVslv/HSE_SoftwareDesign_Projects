import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

// Класс для нахождения всех текстовых файлов в корневой директории и ее поддиректориях.
public class TextFileScanner {

    // Массив текстовых файлов.
    private final ArrayList<File> files = new ArrayList<>();

    TextFileScanner(String rootDirectoryPath) {
        searchForTextFiles(new File(rootDirectoryPath));
    }

    public ArrayList<File> getFiles() {
        return files;
    }

    /**
     * Метод для поиска всех текстовых файлов (запонения массива).
     *
     * @param directory Директория, в которой выполняется поиск.
     */
    private void searchForTextFiles(File directory) {
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isDirectory()) {
                searchForTextFiles(file);
            } else if (file.getName().endsWith(".txt")) {
                files.add(file);
            }
        }
    }
}
