package cashBox;

import person.employee.Employee;
import person.human.Human;

public class CashBox {
    private Employee currEmployee;

    public CashBox(Employee employee) {
        currEmployee = employee;
    }

    public void sellTicket(Human buyer, int cost, String ticketName) {
        buyer.BuyTicket(ticketName, cost);
        currEmployee.incWorkCoef(1);
    }
}
