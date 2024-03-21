import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.*;

public class ParallelProcessing {

    private ExecutorService executorService = Executors.newFixedThreadPool(4); // Thread pool

    public Future<Double> calculateMeanAsync(List<Double> values) {
        Callable<Double> task = () -> {
            double sum = 0;
            for (double value : values) {
                sum += value;
            }
            return sum / values.size();
        };
        return executorService.submit(task);
    }

    public Future<Double> findMinAsync(List<Double> values) {
        Callable<Double> task = () -> values.stream().min(Comparator.naturalOrder()).orElseThrow(NoSuchElementException::new);
        return executorService.submit(task);
    }

    public Future<Double> findMaxAsync(List<Double> values) {
        Callable<Double> task = () -> values.stream().max(Comparator.naturalOrder()).orElseThrow(NoSuchElementException::new);
        return executorService.submit(task);
    }

    public void shutdownExecutorService() {
        executorService.shutdown();
        try {
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
