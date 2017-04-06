import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.List;

public class FileHelper {

    public static int[] readFile(String fileName) {

        try {
            Path path = Paths.get("./", fileName);
            List<String> lines = Files.readAllLines(path, Charset.defaultCharset());
            int[] values = new int[lines.size()];
            for (int i = 0; i < values.length; i++) {
                values[i] = Integer.parseInt(lines.get(i));
            }
            return values;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeFile(String fileName, int[] values) {

        try {
            PrintWriter pr = new PrintWriter(fileName);
            for (int i = 0; i < values.length; i++) {
                pr.println(values[i]);
            }
            pr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteFile(String fileName) {
        File f = new File(fileName);
        if (f.exists()) f.delete();
    }

    public static boolean fileExists(String fileName) {
        File f = new File(fileName);
        return f.exists();
    }
}
