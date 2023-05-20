import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

// Класс для конкатенации текстовых файлов в один с учётом зависимостей.
public class Concatenator {

    // Массив всех файлов.
    private final ArrayList<DependentFile> files = new ArrayList<>();


    /**
     * Конструктор от массива всех файлов
     *
     * @param allTextFiles Список всех файлов (без зависимостей).
     * @param rootPath     Путь к корневой директории.
     * @throws IOException Исключени  при невозмодности чтения какого-либо файла или невозможности
     *                     сделать конкатенацию из-за циклических зависимостей.
     */
    Concatenator(ArrayList<File> allTextFiles, String rootPath) throws IOException {
        for (int i = 0; i < allTextFiles.size(); ++i) {
            DependentFile file;
            file = new DependentFile(allTextFiles.get(i).getAbsolutePath(),
                    (new DependenciesScanner(allTextFiles.get(i), rootPath)).getRequiredFiles());
            int minInsertIndex = 0, maxInsertIndex = i;
            for (int j = 0; j < i; ++j) {
                if (files.get(j).isRequired(file)) {
                    maxInsertIndex = j;
                    break;
                }
            }
            for (int j = i - 1; j >= 0; --j) {
                if (file.isRequired(files.get(j))) {
                    minInsertIndex = j + 1;
                    break;
                }
            }
            if (minInsertIndex > maxInsertIndex) {
                throw new IOException("Unable to concatenate files.");
            }
            for (int j = minInsertIndex; j <= maxInsertIndex && j < i; ++j) {
                if (files.get(j).getName().compareTo(file.getName()) > 0) {
                    files.add(j, file);
                    break;
                }
            }
            if (files.size() != i + 1) {
                files.add(maxInsertIndex, file);
            }
        }
    }

    /**
     * Метод для записи конкатенации файлов в консоль.
     *
     * @throws IOException Исключение при невозможности чтения какого-либо файла.
     */
    public void printConcatenation() throws IOException {
        for (File file : files) {
            String nextLine;
            try (BufferedReader reader = new BufferedReader(new FileReader(file));) {
                while ((nextLine = reader.readLine()) != null) {
                    System.out.print(nextLine + "\n");
                }
            } catch (IOException e) {
                throw new IOException("Unable to read the file " + file.getAbsolutePath() + ".");
            }
        }
    }
}
