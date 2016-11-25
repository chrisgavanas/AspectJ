import data.List;
import thread.Worker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(50);
        List list = new List();
        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(new Worker(list));
            executor.execute(thread);
        }
        executor.shutdown();
    }
}