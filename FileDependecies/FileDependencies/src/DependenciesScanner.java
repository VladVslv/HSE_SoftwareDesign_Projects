import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

// Класс для нахождения зависимостей файла.
public class DependenciesScanner {

    // Список требуемых файлов.
    private final ArrayList<File> requiredFiles = new ArrayList<>();

    /**
     * Конструктор класса.
     *
     * @param file     Файл, в котором ищутся зависимости.
     * @param rootPath Путь к корневой папке.
     * @throws IOException Исключение при невозможности чтения файла.
     */
    public DependenciesScanner(File file, String rootPath) throws IOException {
        searchForDependencies(file, rootPath);
    }


    /**
     * Метод для поиска зависимостей на основе текста файла.
     *
     * @param file     Файл для поиска зависимостей.
     * @param rootPath Путь к корневой папке
     * @throws IOException Исключение при невозможности чтения файла.
     */
    private void searchForDependencies(File file, String rootPath) throws IOException {
        StringBuilder text = new StringBuilder();
        String nextLine;
        try (BufferedReader reader = new BufferedReader(new FileReader(file));) {
            while ((nextLine = reader.readLine()) != null) {
                text.append(nextLine);
            }
        } catch (IOException e) {
            throw new IOException("Unable to read the file " + file.getAbsolutePath() + ".");
        }
        for (int i = 0; i < text.length() - 9; ++i) {
            if (text.subSequence(i, i + 9).equals("require ‘")) {
                for (int j = i + 9; j < text.length(); ++j) {
                    if (text.charAt(j) == '’') {
                        requiredFiles.add(new File(rootPath + "\\" + text.substring(i + 9, j)));
                        i = j;
                        break;
                    }
                }
            }
        }
    }

    public final ArrayList<File> getRequiredFiles() {
        return requiredFiles;
    }
}
