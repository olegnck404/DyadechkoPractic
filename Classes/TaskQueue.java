import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TaskQueue {

    private static final TaskQueue instance = new TaskQueue();
    private BlockingQueue<String> tasks = new LinkedBlockingQueue<>();
    private WorkerThread workerThread;

    private TaskQueue() {
        workerThread = new WorkerThread();
        workerThread.start();
    }

    public static TaskQueue getInstance() {
        return instance;
    }

    public void addTask(String task) {
        tasks.add(task);
    }

    private class WorkerThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    String task = tasks.take(); // Blocking wait
                    processTask(task);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void processTask(String task) {
            // Implement task processing logic
        }
    }
}
