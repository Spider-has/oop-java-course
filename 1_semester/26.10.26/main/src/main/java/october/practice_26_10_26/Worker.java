package october.practice_26_10_26;

public class Worker {

    @Repeat(times = 4)
    public void printHello() {
        System.out.println("Привет!");
    }
}
