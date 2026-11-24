package test;

import java.util.Queue;
import java.util.ArrayDeque;
import java.util.concurrent.ThreadLocalRandom;

enum ProgramState {
    UNKNOWN,
    STOPPING,
    RUNNING,
    FATAL_ERROR
}

class AbstractProgram {
    private ProgramState state;
    private long calc;

    private Thread workerThread;

    private Thread stateGeneratorThread;

    private final Queue<ProgramState> stateEvents = new ArrayDeque<>();

    public AbstractProgram() {
        state = ProgramState.UNKNOWN;
        calc = 0;
    }

    public synchronized boolean start() {
        if (state == ProgramState.FATAL_ERROR) {
            System.out.println("[Program] Cannot start: FATAL_ERROR");
            return false;
        }

        if (workerThread != null && workerThread.isAlive()) {
            throw new IllegalStateException("Program is already running");
        }

        changeState(ProgramState.RUNNING);

        workerThread = new Thread(this::abstractWork);
        workerThread.start();

        return true;
    }

    private void abstractWork() {
        try {
            while (true) {
                calc++;

                System.out.println("Program is working: " + calc);

                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            System.out.println("Program worker stopped, total work count: " + calc);
        }
    }

    public void stop() throws InterruptedException {
        if (workerThread != null) {
            workerThread.interrupt();
            workerThread.join();
        }
    }

    public void stopGenerator() throws InterruptedException {
        if (stateGeneratorThread != null) {
            stateGeneratorThread.interrupt();
            stateGeneratorThread.join();
        }
    }

    public void startGenerator() {
        RandomStateGenerator generator = new RandomStateGenerator(this);

        stateGeneratorThread = new Thread(generator);
        stateGeneratorThread.setDaemon(true);
        stateGeneratorThread.start();
    }

    public synchronized void changeState(ProgramState newState) {
        state = newState;

        stateEvents.add(newState);

        System.out.println("[Program] State changed: " + newState);

        notifyAll();
    }

    public synchronized ProgramState getNextState() throws InterruptedException {
        while (stateEvents.isEmpty()) {
            wait();
        }

        return stateEvents.remove();
    }
}

class RandomStateGenerator implements Runnable {

    private final AbstractProgram program;

    private static final ProgramState[] ACTIVE_VALUES = {
            ProgramState.STOPPING,
            ProgramState.RUNNING,
            ProgramState.FATAL_ERROR
    };

    private static ProgramState generateState() {
        return ACTIVE_VALUES[ThreadLocalRandom.current().nextInt(ACTIVE_VALUES.length)];
    }

    public RandomStateGenerator(AbstractProgram program) {
        this.program = program;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(ThreadLocalRandom.current().nextLong(1000, 5000));

                ProgramState newState = generateState();

                program.changeState(newState);

                if (newState == ProgramState.FATAL_ERROR) {
                    break;
                }

            } catch (InterruptedException e) {
                break;
            }
        }
    }
}

class Supervisor extends Thread {

    private final AbstractProgram program;

    public Supervisor(AbstractProgram program) {
        this.program = program;
    }

    @Override
    public void run() {
        boolean interrupted = false;

        try {
            program.start();
            program.startGenerator();

            while (true) {
                ProgramState event = program.getNextState();

                System.out.println("[Supervisor] Received: " + event);

                switch (event) {
                    case RUNNING:
                        break;

                    case STOPPING:
                        program.stop();
                        System.out.println("[Supervisor] abstract work succefully stopped");
                        program.start();
                        System.out.println("[Supervisor] abstract work succefully restarted");
                        break;

                    case FATAL_ERROR:
                        return;

                    case UNKNOWN:
                        break;
                }
            }

        } catch (InterruptedException e) {
            System.out.println("[Supervisor] Interrupted");
            interrupted = true;

        } finally {
            try {
                program.stop();
            } catch (InterruptedException e) {
                interrupted = true;
            }

            try {
                program.stopGenerator();
            } catch (InterruptedException e) {
                interrupted = true;
            }

            if (interrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

public class App {
    public static void main(String[] args) {
        AbstractProgram program = new AbstractProgram();
        Supervisor supervisor = new Supervisor(program);

        supervisor.start();

        try {
            supervisor.join();
        } catch (InterruptedException e) {
            System.out.println("[Main] Interrupted while waiting");

            supervisor.interrupt();
            Thread.currentThread().interrupt();
        }

        System.out.println("[Main] Application finished");
    }
}
