package com.example.mytest;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * //TODO注释
 *
 * @author Created by xuepeng on 2023/12/25.
 */
public class HHH {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        PrintRunnable onePrint = new PrintRunnable(1, lock);
        PrintRunnable twoPrint = new PrintRunnable(2, lock);
        PrintRunnable threePrint = new PrintRunnable(0, lock);
        onePrint.setNext(twoPrint);
        twoPrint.setNext(threePrint);
        threePrint.setNext(onePrint);
        new Thread(onePrint).start();
        new Thread(twoPrint).start();
        new Thread(threePrint).start();
    }

    //三个线程交替打印 1、4、7～ 100
    private static class PrintRunnable implements Runnable {
        private static final int MAX = 100;
        private static volatile int num = 1;
        private int mId;
        private Condition mCondition;
        private Lock mLock;
        private PrintRunnable mNext;

        public PrintRunnable(int id, Lock lock) {
            this.mId = id;
            mLock = lock;
            mCondition = lock.newCondition();
        }

        public void setNext(PrintRunnable next) {
            this.mNext = next;
        }

        private void wakeNext() {
            //System.out.println("wakeNext " + mId + "-" + num);

            if (num <= MAX) {
                mNext.mCondition.signal();
            }
        }

        private void await() {
            System.out.println("await " + mId + "-" + num);

            if (num + 2 > MAX) {
                return;
            }

            try {
                mCondition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while (num <= MAX) {
                try {
                    mLock.lock();
                    if (num > MAX) {
                        break;
                    }
                    if (num % 3 == mId) {
                        System.out.println(mId + "-" + num++);

                        wakeNext();
                        await();
                    }
                } finally {
                    mLock.unlock();
                }
            }
        }
    }
}
