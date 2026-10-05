import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    static void old() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(""));
            String str;
            while ((str = reader.readLine()) != null) {
                System.out.println(str);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static void newIO() {
        try (BufferedReader reader = new BufferedReader(new FileReader(""))) {
            String str;
            while ((str = reader.readLine()) != null) {
                System.out.println(str);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            List<String> list = Files.readAllLines(Path.of("./output.txt"));
            HashMap<String, Integer> filtered = new HashMap<String, Integer>();
            int max_mark = 0;
            int min_mark = 100;
            int middle = 0;
            for (String line : list) {
                String[] words = line.split("\\s+");
                try {
                    int mark = Integer.parseInt(words[1]);
                    if (words.length == 2 && mark >= 0 && mark <= 100) {
                        filtered.put(words[0], mark);
                        if (max_mark < mark) {
                            max_mark = mark;
                        }
                        if (min_mark > mark) {
                            min_mark = mark;
                        }
                        middle += mark;
                    }
                } catch (NumberFormatException e) {
                }
            }
            for (Map.Entry<String, Integer> entry : filtered.entrySet()) {
                if (entry.getValue() == max_mark) {
                    System.out.printf("Максимальный балл: %s %d\n", entry.getKey(), entry.getValue());
                }
                if (entry.getValue() == min_mark) {
                    System.out.printf("Минимальный балл: %s %d\n", entry.getKey(), entry.getValue());
                }
            }

            if (filtered.size() > 0) {
                System.out.println("Средний балл: " + (middle / filtered.size()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
