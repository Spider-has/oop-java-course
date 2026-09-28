package person.employee;

import person.human.Human;

public class Employee extends Human {
    private int salary;

    private final int maxWorkCoef = 100;
    private int currWorkCoef = 0;

    public Employee(String name, String surname, int age, int salary, int budget) {
        super(name, surname, age, budget);
        if (salary < 0) {
            throw new IllegalArgumentException("salary cannot be negative");
        }
        this.salary = salary;
    }

    public void incWorkCoef(int inc) {
        if (inc < 0) {
            throw new IllegalArgumentException("inc cannot be negative");
        }
        currWorkCoef += inc;
    }

    public void decWorkCoef(int dec) {
        if (dec < 0) {
            throw new IllegalArgumentException("dec cannot be negative");
        }
        currWorkCoef -= dec;
    }

    public int paySalary() {
        if (currWorkCoef < 0) {
            currWorkCoef = 0;
        }
        double coef = (double) currWorkCoef / maxWorkCoef;
        this.incMoney((int) (salary * coef));
        return salary;
    }
}
