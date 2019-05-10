package com.urise.webapp;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.*;

public class MainConcurrency {
    private static int counter;
    private static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        Thread t1 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
            }
        };
        t1.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        }).start();

        new Thread(() -> System.out.println(Thread.currentThread().getName())).start();
        System.out.println(t1.getState());

        //Object lock=new Object();
        MainConcurrency mainConcurrency = new MainConcurrency();
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 10_000; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.increment();
                }
            });
            thread.start();
            threads.add(thread);
        }
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
       // Thread.sleep(500);
        System.out.println(counter);


    }

    private synchronized void increment() {
        double a = Math.sin(13.);
        // synchronized (LOCK) {
        counter++;
        //   }
    }
}
