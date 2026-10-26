package october.practice_26_10_26;

import java.lang.reflect.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String[] args) throws Exception {
        Worker worker = new Worker();
        Class<?> clazz = worker.getClass();

        for (Class<?> nested : clazz.getDeclaredClasses()) {
            int mod = nested.getModifiers();
            log.debug("{}: маска={} ({}), isInterface={}, isAbstract={}",
                    nested.getSimpleName(), mod, Modifier.toString(mod),
                    nested.isInterface(), Modifier.isAbstract(mod));
        }

        for (Method method : clazz.getDeclaredMethods()) {
            if (!method.isAnnotationPresent(Repeat.class)) {
                continue;
            }

            int modifiers = method.getModifiers();
            boolean isProtected = Modifier.isProtected(modifiers);
            boolean isPrivate = Modifier.isPrivate(modifiers);

            if (!isProtected && !isPrivate) {
                log.debug("Пропуск метода {}: модификатор {}", method.getName(), Modifier.toString(modifiers));
                continue;
            }

            int times = method.getAnnotation(Repeat.class).times();
            method.setAccessible(true);

            log.info("--- Запуск метода: {} ({} раз) ---", method.getName(), times);

            for (int i = 0; i < times; i++) {
                int callNumber = i + 1;
                try {
                    Object[] testArgs = prepareArguments(method.getParameterTypes());
                    Object result = method.invoke(worker, testArgs);
                    log.info("Вызов #{} -> результат: {}", callNumber, result);
                } catch (InvocationTargetException e) {
                    log.warn("Вызов #{} -> метод бросил исключение: {}", callNumber, e.getCause().toString());
                } catch (ReflectiveOperationException e) {
                    log.error("Вызов #{} -> ошибка рефлексии", callNumber, e);
                }
            }
        }
    }

    private static Object[] prepareArguments(Class<?>[] paramTypes) throws ReflectiveOperationException {
        Object[] args = new Object[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
            args[i] = createValue(paramTypes[i]);
        }
        return args;
    }

    private static Object createValue(Class<?> type) throws ReflectiveOperationException {
        return switch (type.getSimpleName()) {
            case "double" -> 10.0;
            case "int" -> 2;
            case "String" -> "test";
            case "CharSequence[]" -> new CharSequence[] { "яблоко", "груша", "слива" };
            default -> createViaConstructor(type);
        };
    }

    private static Object createViaConstructor(Class<?> type) throws ReflectiveOperationException {
        boolean isInterface = type.isInterface();
        boolean isAbstract = Modifier.isAbstract(type.getModifiers());

        if (isInterface || isAbstract) {
            log.debug("Тип {} — интерфейс или абстрактный класс, подставляем null", type.getSimpleName());
            return null;
        }

        Constructor<?> constructor = type.getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        log.debug("Создаём {} через конструктор {}", type.getSimpleName(), constructor);

        Object[] constructorArgs = prepareArguments(constructor.getParameterTypes());
        return constructor.newInstance(constructorArgs);
    }
}
