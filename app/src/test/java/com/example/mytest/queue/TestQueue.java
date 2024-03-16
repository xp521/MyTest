package com.example.mytest.queue;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * //TODO注释
 *
 * @author Created by xuepeng on 2023/3/30.
 */
public class TestQueue<E> {
    private Object[] items;
    private int size;

    private int putIndex;
    private int getIndex;

    private Lock lock;
    private Condition emptyCondition;
    private Condition fullCondition;

    public TestQueue(int cap) {
        items = new Object[cap];
        lock = new ReentrantLock();
        emptyCondition = lock.newCondition();
        fullCondition = lock.newCondition();
    }

    /**
     * 头部进
     * @param e
     */
    public void enqueue(E e) {
        try {
            lock.lock();
            while(isFull()) {
                fullCondition.await();
            }

            items[putIndex++] = e;
            size++;

            if (putIndex == items.length) {
                putIndex = 0;
            }

            emptyCondition.signal();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 尾部出
     */
    public E dequeue() throws InterruptedException {
        try {
            lock.lock();
            while (size == 0) {
                emptyCondition.await();
            }

            E removeE = (E) items[getIndex++];
            size--;
            if (getIndex == items.length) {
                getIndex = 0;
            }

            fullCondition.signal();
            return removeE;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            lock.unlock();
        }
    }

    private boolean isFull() {
        return size == items.length;
    }

    private boolean isEmpty() {
        return size == 0;
    }
}
