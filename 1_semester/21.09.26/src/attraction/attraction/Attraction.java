package attraction.attraction;

import java.util.HashMap;
import java.util.Map;

import person.human.Human;

public class Attraction {
    private final String ticketName;
    private int ticketCost;

    private HashMap<String, Human> currentVisitors;

    private final int limit;

    public Attraction(String ticketName, int ticketCost, int limit) {
        if (ticketCost < 0) {
            throw new IllegalArgumentException("ticket cost must be positive");
        }
        if (ticketName.isEmpty()) {
            throw new IllegalArgumentException("ticket name must non empty");
        }
        this.ticketCost = ticketCost;
        this.ticketName = ticketName;
        this.limit = limit;
    }

    public void addVisitor(Human visitor) {
        if (currentVisitors.size() >= limit) {
            throw new IllegalStateException("can't add visitor, attraction is already full");
        }
        visitor.enterAttraction(ticketName);
        currentVisitors.put(visitor.getId(), visitor);
    }

    public void resetAttraction() {
        for (Map.Entry<String, Human> entry : currentVisitors.entrySet()) {
            entry.getValue().exitAttraction(ticketName);
        }
        currentVisitors.clear();
    }

    public int getCost() {
        return ticketCost;
    }

    public String getName() {
        return ticketName;
    }
}
