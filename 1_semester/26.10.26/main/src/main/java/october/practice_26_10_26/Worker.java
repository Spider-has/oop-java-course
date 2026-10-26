package october.practice_26_10_26;

public class Worker {
    public static class Task {
        private final String title;

        public Task(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return "Task{title='" + title + "'}";
        }
    }

    @Repeat(times = 3)
    public double doAction1(double a, double b) {
        return a * b;
    }

    @Repeat(times = 2)
    protected double doAction2(double a, double b) {
        return a + b;
    }

    @Repeat(times = 1)
    double doAction3(double a, double b) {
        return a - b;
    }

    @Repeat(times = 10)
    private double doAction4(double a, double b) {
        return a / b;
    }

    public int doAction5(int a, int b) {
        return a & b;
    }

    protected int doAction6(int a, int b) {
        return a | b;
    }

    @Repeat(times = 11)
    int doAction7(int a, int b) {
        return a ^ b;
    }

    @Repeat(times = 3)
    private int doAction8(int a, int b) {
        return a << b;
    }

    @Repeat(times = 2)
    protected String doActionWithCollections(String prefix, CharSequence[] items) {
        return prefix + ": " + String.join(", ", items);
    }

    @Repeat(times = 3)
    private String doActionWithCustomObject(Task task, String status) {
        return "Обработка " + task + " со статусом: [" + status + "]";
    }

    @Repeat(times = 1)
    private String doActionWithInterface(Printable printable) {
        return printable == null ? "Printable не передан (null)" : printable.print();
    }

    @Repeat(times = 1)
    protected double doActionWithAbstract(Shape shape) {
        return shape.area();
    }
}

interface Printable {
    String print();
}

abstract class Shape {
    protected final String name;

    protected Shape(String name) {
        this.name = name;
    }

    public abstract double area();
}
