package com.example.mytest;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * //TODO注释
 *
 * @author Created by xuepeng on 2023/12/27.
 */
public class testgg {
    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool(2, 3, 5, new ThreadPool.IReject() {
            @Override
            public void reject(Runnable runnable) {
                if (runnable instanceof TestRunnable) {
                    System.out.println("is full " + ((TestRunnable) runnable).id);
                }
            }
        });

        for (int i = 0; i < 90; i++) {
            threadPool.run(new TestRunnable(atomicInteger.incrementAndGet()));
        }

        Thread.sleep(5000);
        threadPool.shutDown();
    }

    private static class TestRunnable implements Runnable {

        private int id;
        public TestRunnable(int id) {
            this.id = id;
        }
        @Override
        public void run() {
            System.out.println("thread " + Thread.currentThread().getName() + ",id=" + id);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //线程池
    private static class ThreadPool {
        private static volatile int sThreadId = 1;
        private int coreNum;
        private int queSize;
        private int maxNum;

        private BlockingQueue<Runnable> queue;

        private ArrayList<Thread> mCoreThreads;
        private IReject mReject;

        public ThreadPool(int coreNum, int queSize, int maxNum, IReject reject) {
            //TODO 参数校验

            this.coreNum = coreNum;
            this.queSize = queSize;
            this.maxNum = maxNum;
            this.mReject = reject;

            mCoreThreads = new ArrayList<>(coreNum);
            queue = new ArrayBlockingQueue<>(maxNum);

            for (int i = 0; i < coreNum; i++) {
                Thread thread = createCoreThread(queue);
                thread.start();
                mCoreThreads.add(thread);
            }
        }

        public void shutDown() {
            for(Thread thread : mCoreThreads) {
                if (thread != null) {
                    thread.interrupt();
                }
            }
        }

        public void run(Runnable runnable) {
            if (runnable == null) {
                return;
            }

            boolean isOffered = queue.offer(runnable);
            //System.out.println("mCoreThreads.size():" + mCoreThreads.size() + ",queSize=" + queSize + ",maxNum=" + maxNum);
            if (isOffered) {
                //队列未满
                //queue.add(runnable);
            } else if (mCoreThreads.size() + queSize < maxNum) {
                //未达到最大值
                Thread thread = createThread(runnable);
                thread.start();
            } else {
                if (mReject != null) {
                    mReject.reject(runnable);
                }
                //拒绝策略
            }
        }

        private Thread createThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setName(String.valueOf(sThreadId++));
            return thread;
        }

        private Thread createCoreThread(BlockingQueue queue) {
            CoreThread thread = new CoreThread(queue);
            thread.setName("core-" + String.valueOf(sThreadId++));
            return thread;
        }

        private interface IReject {
            void reject(Runnable runnable);
        }

        private static class CoreThread extends Thread {
            private BlockingQueue<Runnable> mQueue;

            public CoreThread(BlockingQueue queue) {
                mQueue = queue;
            }

            @Override
            public synchronized void run() {
                while (!isInterrupted()) {
                    try {
                        mQueue.take().run();
                    } catch (InterruptedException e) {
                        break;
                        //e.printStackTrace();
                    }
                }
            }
        }
    }

}
