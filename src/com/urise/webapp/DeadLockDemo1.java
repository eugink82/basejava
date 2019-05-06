package com.urise.webapp;

public class DeadLockDemo1 {
    private final static Object one = new Object(), two = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread() {
            public void run() {
                synchronized (one) {
                    System.out.println("thread1 one");
                    synchronized (two) {
                        System.out.println("thread1 two");
                    }
                }
            }
        };

        Thread thread2 = new Thread() {
            public void run() {
                synchronized (two) {
                    System.out.println("thread2 two");
                    synchronized (one) {
                        System.out.println("thread2 one");
                    }
                }
            }
        };
        thread1.start();
        thread2.start();
    }
}
