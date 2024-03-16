package com.example.mytest.queue;

import org.w3c.dom.Node;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * //TODO注释
 *
 * @author Created by xuepeng on 2023/4/4.
 */
public class TestLinkedBlockQueue<E> {
    private Node<E> head;
    private Node<E> tail;
    private Lock lock;
    private int size;
    private int max;
    private Condition emptyCondition;
    private Condition fullCondition;

    //尾进
    public void enqueue(E e) {
        try {
            lock.lock();
            if (isFull()) {
                fullCondition.await();
            }

            //默认tail不为空，是个非空的默认值
            tail.value = e;
            Node newNode = new Node();
            tail.next = newNode;
            tail = newNode;
            size++;
            emptyCondition.signal();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    //头出
    public E dequeue() throws InterruptedException {
        E result = null;
        try {
            lock.lock();
            //空
            while (isEmpty()) {
                emptyCondition.await();
            }
            result = head.value;
            Node first = head;
            head = head.next;
            first.next = null;//help GC
            size--;
            fullCondition.signal();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
            //TODO 处理异常情况
        } finally {
            lock.unlock();
        }
        return result;
    }

    public TestLinkedBlockQueue(int max) {
        this.max = max;
        lock = new ReentrantLock();
        head = new Node<>();
        tail = head;
        emptyCondition = lock.newCondition();
        fullCondition = lock.newCondition();
    }

    private boolean isFull() {
        return size == max;
    }

    private boolean isEmpty() {
        return size == 0;
    }

    private static class Node<E> {
        Node next;
        E value;

        public Node(E e) {
            value = e;
        }

        public Node() {
        }
    }
}

