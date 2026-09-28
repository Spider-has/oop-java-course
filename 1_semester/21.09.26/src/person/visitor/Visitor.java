package person.visitor;

import person.human.Human;

public class Visitor extends Human {
    public Visitor(String name, String surname, int age, int budget) {
        super(name, surname, age, budget);
    }
}
