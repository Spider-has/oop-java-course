package spiderhas.com;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PetQuest.class, name = "pet"),
        @JsonSubTypes.Type(value = CollectQuest.class, name = "collect"),
        @JsonSubTypes.Type(value = EscortQuest.class, name = "escort")
})

public sealed interface Quest permits PetQuest, CollectQuest, EscortQuest {
    String name();

    default int reward() {
        return 0;
    }

    void complete();
}

record QuestMeta(String name, Integer reward) {
}

record PetQuest(
        String name,
        String target,
        int count) implements Quest {
    @Override
    public void complete() {
        System.out.println("[Квест: " + name + "] Погладить '" + target + "' " + count + " раз.");
    }
}

record CollectQuest(
        String name,
        String item,
        int count,
        int reward) implements Quest {
    @Override
    public void complete() {
        System.out.println("[Квест: " + name + "] Собрано '" + item + "' (" + count + " шт.). Награда: " + reward);
    }
}

record EscortQuest(
        String name,
        String npc,
        String destination,
        int reward) implements Quest {
    @Override
    public void complete() {
        System.out.println(
                "[Квест: " + name + "] Сопровожден '" + npc + "' до локации '" + destination + "'. Награда: " + reward);
    }
}
