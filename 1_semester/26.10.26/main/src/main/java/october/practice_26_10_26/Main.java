package october.practice_26_10_26;

import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) throws Exception {
        Worker worker = new Worker();
        Method method = worker.getClass().getMethod("printHello");

        if (method.isAnnotationPresent(Repeat.class)) {
            Repeat annotation = method.getAnnotation(Repeat.class);

            int count = annotation.times();

            for (int i = 0; i < count; i++) {
                method.invoke(worker);
            }
        }
    }
}
