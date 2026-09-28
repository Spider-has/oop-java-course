package person.human;

public abstract class Human {
    public final String name;
    public final String surname;
    public final int age;

    private String ticketName;
    private boolean isOnAttraction;

    private int budget;

    public Human(String name, String surname, int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        this.name = name;
        this.surname = surname;
        this.age = age;
        isOnAttraction = false;
    }

    public Human(String name, String surname, int age, int budget) {
        this(name, surname, age);
        this.budget = budget;
    }

    public String getId() {
        return name + "_" + surname + "_" + age + ":" + budget;
    }

    public void incMoney(int money) {
        if (money <= 0) {
            throw new IllegalArgumentException("Human: Can't increment zero or below zero money");
        }
        budget += money;
    }

    private void spendMoney(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Human: Amount must be positive");
        }
        if (budget < amount) {
            throw new IllegalStateException("Human: Not enough budget");
        }
        budget -= amount;
    }

    public int getBudget() {
        return budget;
    }

    public void BuyTicket(String ticket, int cost) {
        spendMoney(cost);
        ticketName = ticket;
    }

    public String getTicket() {
        return ticketName;
    }

    public void enterAttraction(String attractionName) {
        if (attractionName != ticketName) {
            throw new IllegalArgumentException("Human: own ticket isn't for this attraction");
        }
        isOnAttraction = true;
        ticketName = "";
    }

    public void exitAttraction(String attractionName) {
        isOnAttraction = false;
    }

    public boolean isBusy() {
        return isOnAttraction;
    }

}
