package spiderhas.com;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;

public class App {
    private static final ObjectMapper MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static void main(String[] args) {
        Path path = Path.of("resources", "test.json");

        try (InputStream inputStream = Files.newInputStream(path)) {
            StoreData store = MAPPER.readValue(
                    inputStream,
                    new TypeReference<StoreData>() {
                    });

            System.out.println("=== Магазин: " + store.store() + " ===");
            int totalPrice = 0;
            HashSet<String> uniqTags = new HashSet<String>();
            for (Item item : store.products()) {
                System.out.println("  - " + item.name() + " (" + item.price() + "$), в наличии: " + item.inStock());
                totalPrice += item.price();
                for (String tag : item.tags()) {
                    uniqTags.add(tag);
                }
            }
            System.out.println("total price: " + totalPrice);
            System.out.println("unique tags: ");

            for (String tag : uniqTags) {
                System.out.println(" -  " + tag);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
