import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


// Клас файла, содержащий его зависимости.
public class DependentFile extends File {

    // Зависимости файла
    private final ArrayList<File> requiredFiles = new ArrayList<>();

    /**
     * Конструктор класса.
     *
     * @param pathName      Путь к файлу.
     * @param requiredFiles Список зависимостей.
     */
    public DependentFile(String pathName, ArrayList<File> requiredFiles) {
        super(pathName);
        this.requiredFiles.addAll(requiredFiles);
    }

    /**
     * Метод для проверки, требуется ли данный файл.
     *
     * @param file Файл для проверки.
     * @return Возвращает true, если данный файл есть в списке зависимостей и false - в обрвтном случае.
     */
    public final boolean isRequired(File file) {
        for (File requiredFile : requiredFiles) {
            if (file.getAbsolutePath().equals(requiredFile.getAbsolutePath())) {
                return true;
            }
        }
        return false;
    }
}