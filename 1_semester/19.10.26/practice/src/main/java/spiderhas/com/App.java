package spiderhas.com;

import java.io.File;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class App {
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        List<Quest> quests = mapper.readValue(
                new File("resources/Quests.json"),
                new TypeReference<List<Quest>>() {
                });

        for (Quest quest : quests) {
            quest.complete();
        }
    }
}
