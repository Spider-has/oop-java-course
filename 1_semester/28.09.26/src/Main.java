import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import hero.Hero;
import moves.DirectionVector;
import moves.Point;
import moves.Walking;

public class Main {

    // Очередь для хранения кодов нажатых клавиш
    private static final Queue<Integer> keyQueue = new ConcurrentLinkedQueue<>();

    public static void main(String[] args) {
        Hero hero = new Hero(new Point(5, 5, 10), new Walking());

        // 1. Прячем курсор и переводим терминал в raw-режим
        System.out.print("\033[?25l");
        setTerminalMode("raw -echo");

        // 2. Гарантированно возвращаем терминал в исходное состояние при выходе
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            setTerminalMode("cooked echo");
            System.out.print("\033[?25h"); // Возвращаем курсор
        }));

        // 3. Фоновый поток чтения клавиш
        Thread inputThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    int key = System.in.read();
                    if (key != -1) {
                        keyQueue.offer(key);
                    }
                } catch (IOException e) {
                    break;
                }
            }
        });
        inputThread.setDaemon(true);
        inputThread.start();

        int lastDebugKey = -1;

        while (true) {
            Integer keyObj;
            while ((keyObj = keyQueue.poll()) != null) {
                int key = keyObj;
                lastDebugKey = key;

                if (key == 'q' || key == 'Q' || key == 3) { // 3 — это Ctrl+C
                    System.exit(0);
                }

                if (key == 'w' || key == 'W')
                    hero.setDirection(new DirectionVector(new Point(0, -1, 0)));
                if (key == 's' || key == 'S')
                    hero.setDirection(new DirectionVector(new Point(0, 1, 0)));
                if (key == 'a' || key == 'A')
                    hero.setDirection(new DirectionVector(new Point(-1, 0, 0)));
                if (key == 'd' || key == 'D')
                    hero.setDirection(new DirectionVector(new Point(1, 0, 0)));
            }

            render(hero.position(), lastDebugKey);
            hero.move();

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    private static void render(Point heroCords, int debugKey) {
        StringBuilder frame = new StringBuilder();
        frame.append("\033[2J\033[H");

        int screenWidth = 30;
        int screenHeight = 10;

        for (int y = 0; y < screenHeight; y++) {
            for (int x = 0; x < screenWidth; x++) {
                if (x == heroCords.getX() && y == heroCords.getY()) {
                    frame.append("☺");
                } else if (x == 0 || x == screenWidth - 1 || y == 0 || y == screenHeight - 1) {
                    frame.append("#");
                } else {
                    frame.append(" ");
                }
            }
            frame.append("\r\n");
        }

        frame.append("Код последней клавиши: ")
                .append(debugKey)
                .append(" (Нажмите Q для выхода)\r\n");

        frame.append("Координаты героя ")
                .append(heroCords.getX())
                .append(", ")
                .append(heroCords.getY())
                .append(" (Нажмите Q для выхода)\r\n");

        System.out.print(frame.toString());
        System.out.flush();
    }

    private static void setTerminalMode(String mode) {
        try {
            new ProcessBuilder("sh", "-c", "stty " + mode + " </dev/tty")
                    .inheritIO()
                    .start()
                    .waitFor();
        } catch (Exception e) {
            System.err.println("Ошибка настройки терминала: " + e.getMessage());
        }
    }
}
