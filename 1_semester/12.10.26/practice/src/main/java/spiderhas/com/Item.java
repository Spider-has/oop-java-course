package spiderhas.com;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record Item(Long id, String name, Double price, @JsonProperty("inStocle") boolean inStock, List<String> tags) {
}
