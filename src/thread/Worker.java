package thread;

import data.List;

import java.util.Random;

public class Worker implements Runnable {
    private List list;
    private Random rng;

    public Worker(List list) {
        this.list = list;
        this.rng = new Random();
    }

    public void run() {
        try {
            int i = rng.nextInt(100) % 3;
            switch (i) {
                case 0:
                    list.prepend(rng.nextInt(100));
                    break;
                case 1:
                    list.pop();
                    break;
                case 2:
                    Object info = list.head();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            System.err.println("Thread interrupted");
        }
    }
}
