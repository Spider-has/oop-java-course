package spiderhas.com;

import java.util.List;

public record StoreData(
        String store,
        List<Item> products) {
}
