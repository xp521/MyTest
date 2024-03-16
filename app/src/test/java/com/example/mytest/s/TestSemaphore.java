package com.example.mytest.s;

import java.util.concurrent.Semaphore;

/**
 * //TODO注释
 *
 * @author Created by xuepeng on 2022/12/28.
 */
public class TestSemaphore implements Runnable {
    private Semaphore semaphore;
    public TestSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getId() + " start acquire");
            semaphore.acquire();
            System.out.println(Thread.currentThread().getId() + " acquired");
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
            System.out.println(Thread.currentThread().getId() + " released" + semaphore.availablePermits());
        }
    }
}
