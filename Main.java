import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    private final static String[] logFile = {"Game", "temp", "temp.txt"};

    private final static String[][] dirs = {
        {"Game"},
        {"Game", "src"},
        {"Game", "res"},
        {"Game", "res", "drawable"},
        {"Game", "res", "vectors"},
        {"Game", "res", "icons"},
        {"Game", "savegame"},
        {"Game", "temp"}
    };

    private final static String[][] files = {
        {"Game", "src", "Main.java"},
        {"Game", "src", "Utils.java"},
        logFile
    };

    

    public static void main(String[] args) {
        StringBuilder log = new StringBuilder();
        createDirs(dirs, log);
        createFiles(files, log);
        writeLog(logFile, log);
    }

    private static void createDirs(String[][] dirs, StringBuilder log) {
        for (String[] pathParts: dirs) {
            String path = getPath(pathParts);
            File dir = new File(path);
            if (!dir.exists()) {
                try {
                    log.append(
                        dir.mkdir()
                        ? String.format("Директория %s успешно создана%n", path)
                        : String.format("Ошибка: не могу создать директорию %s%n", path)
                    );
                } catch (SecurityException ex) {
                    log.append(String.format("Ошибка: не хватает прав для создания директории %s (%s)%n", path, ex.getMessage()));
                }
            } else {
                log.append(
                    dir.isFile()
                    ? String.format("Ошибка: по пути %s находится файл%n", path)
                    : String.format("Директория %s уже создана%n", path)
                );
            }
        }
    }

    private static void createFiles(String[][] files, StringBuilder log) {
        for (String[] pathParts: files) {
            String path = getPath(pathParts);
            File file = new File(path);
            if (!file.exists()) {
                try {
                    log.append(
                        file.createNewFile()
                        ? String.format("Файл %s успешно создан%n", path)
                        : String.format("Ошибка: не могу создать файл %s%n", path)
                    );
                } catch (SecurityException ex) {
                    log.append(String.format("Ошибка: не хватает прав для создания файла %s (%s)%n", path, ex.getMessage()));
                } catch (IOException ex) {
                    log.append(String.format("Ошибка: не могу создать файл %s (%s)%n", path, ex.getMessage()));
                }
            } else {
                log.append(
                    file.isDirectory()
                    ? String.format("Ошибка: по пути %s находится директория%n", path)
                    : String.format("Файл %s уже создан%n", path)
                );
            }
        }
    }

    private static void writeLog(String[] pathParts, StringBuilder log) {
        String path = getPath(pathParts);
        File file = new File(path);
        try (FileWriter writer = new FileWriter(file)) {
            writer.append(log);
            writer.flush();
        } catch (IOException ex){
            System.out.format("Не могу записать логи в файл: %s", ex.getMessage());
        }

    }

    private static String getPath(String[] pathParts) {
        return String.join(File.separator, pathParts);
    }
}