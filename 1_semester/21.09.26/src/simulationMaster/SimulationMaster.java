package simulationMaster;

import java.util.ArrayList;
import java.util.Random;

import attraction.attraction.Attraction;
import attraction.happyWheel.HappyWheel;
import cashBox.CashBox;
import person.employee.Employee;
import person.visitor.Visitor;

public class SimulationMaster {
    private ArrayList<Employee> employees = new ArrayList<>();
    private ArrayList<Visitor> visitors = new ArrayList<>();
    private ArrayList<Attraction> attractions = new ArrayList<>();

    private CashBox cashBox;

    private final int daySteps = 100;
    private int step = 0;
    private Random random;

    public SimulationMaster() {
        employees.add(new Employee("slave", "1", 35, 500, 0));
        employees.add(new Employee("slave", "2", 40, 500, 0));
        employees.add(new Employee("slave", "3", 35, 500, 0));

        visitors.add(new Visitor("client", "1", 29, 5000));
        visitors.add(new Visitor("client", "2", 15, 100));
        visitors.add(new Visitor("client", "3", 28, 0));
        visitors.add(new Visitor("client", "4", 345, 50));
        visitors.add(new Visitor("client", "5", 32, 150));
        visitors.add(new Visitor("client", "6", 10, 10000));

        attractions.add(new HappyWheel());
        attractions.add(new HappyWheel());
        attractions.add(new HappyWheel());

        random = new Random();
    }

    public void run() {
        while (true) {

        }
    }

    private void processVisitorDesicion(Visitor visitor) {
        int decision = random.nextInt(attractions.size());
        System.out.println("visitor " + visitor.getId() + " decide go to attraction #" + decision);
        try {
            visitor.BuyTicket(attractions.get(decision).getName(), attractions.get(decision).getCost());
        } catch (Exception err) {
            System.out.println("error with buying ticket: " + err.getMessage());
        }
    }
}
