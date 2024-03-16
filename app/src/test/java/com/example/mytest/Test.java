package com.example.mytest;

import android.util.ArrayMap;

import com.example.mytest.queue.TestLinkedBlockQueue;
import com.example.mytest.queue.TestQueue;
import com.example.mytest.s.TestSemaphore;

import org.w3c.dom.Node;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * //TODO注释
 *
 * @author Created by xuepeng on 2022/4/20.
 */
public class Test {
    public static void main(String[] args) {
/*        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(9);
        ListNode node3 = new ListNode(9);
        node1.next = node2;
        node2.next = node3;*/

        //System.out.println(divide(15, 2) == 15 / 2);
        //System.out.println(addByteStr("1", "1111"));
        //calByteOneNum(5);
        /*Lock lock = new ReentrantLock();
        PrintRunnable runnable0 = new PrintRunnable(0, lock);
        PrintRunnable runnable1 = new PrintRunnable(1, lock);
        PrintRunnable runnable2 = new PrintRunnable(2, lock);
        runnable0.setNextRunnable(runnable1);
        runnable1.setNextRunnable(runnable2);
        runnable2.setNextRunnable(runnable0);*/
        //new Thread(runnable0).start();
        //new Thread(runnable1).start();
        //new Thread(runnable2).start();
        //Lock lock = new ReentrantLock();

        //System.out.println(findOnceOtherThree(new int[]{1, 2, 3, 4, 1, 3, 4}));
       /* Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 10; i++) {
            new Thread(new TestSemaphore(semaphore)).start();
        }*/
        //testQueue();
        //testArrayMap();
        //int x = Byte.MIN_VALUE & 0xff;
        //MyNode node = numToNode(98764);
        //System.out.println(NodeToNum(node));
        moveZeroes(new int[]{0, 1, 0, 3, 1, 2});

        System
    }

    private static void testArrayMap() {
        ArrayMap arrayMap = new ArrayMap(4);
        int x = 4;
        while (x-- > 0) {
            arrayMap.put(String.valueOf(x), x);
        }
        System.out.println(arrayMap.size());
    }

    private static void testQueue() {
        TestLinkedBlockQueue<Integer> queue = new TestLinkedBlockQueue<>(5);

        new Thread(new Runnable() {
            @Override
            public void run() {
                int x = 0;
                while (x < 12) {
                    queue.enqueue(x++);
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                int x = 0;
                while (true) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int result;
                    try {
                        result = queue.dequeue();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        result = -1;
                    }
                    System.out.println(result);
                }
            }
        }).start();
    }

    private static int findOnceOtherThree(int[] nums) {
        int[] x = new int[32];
        for (int i = 0; i < 32; i++) {
            for (int j=0; j<nums.length; j++) {
                x[i] += (nums[j] >> (31 - i)) & 1;
            }
        }

        int sum = 0;
        for (int i = 0; i < 32; i++) {
            //sum += (x[i] % 3) << (31 - i);
            sum = sum << 1 + (x[i] % 3);
        }
        return sum;
    }

    private static int findOnceOtherTwice(int[] nums) {
        int x = nums[0];
        for (int i = 1; i < nums.length; i++) {
            System.out.println(x);
            x = x ^ nums[i];
        }
        return x;
    }

    private static class PrintRunnable implements Runnable {

        private static final int MAX = 100;
        public static volatile int num;
        private int threadIndex;
        private Lock lock;
        private Condition currentCondition;
        private PrintRunnable nextRunnable;

        public PrintRunnable(int threadIndex, Lock lock) {
            this.threadIndex = threadIndex;
            this.lock = lock;
            currentCondition = lock.newCondition();
        }

        public void setNextRunnable(PrintRunnable next) {
            nextRunnable = next;
        }

        public void notifyRunnable() {
            //System.out.println("thread" + threadIndex + " notifyRunnable");

            currentCondition.signal();
        }

        public void notifyAllRunnable() {
            //System.out.println("thread" + threadIndex + " notifyAllRunnable");
            currentCondition.signal();
            nextRunnable.notifyRunnable();
        }

        @Override
        public void run() {
            while (num <= MAX) {
                lock.lock();

                try {
                    if (num % 3 == threadIndex) {
                        System.out.println("thread" + threadIndex + " - " + num++);
                        if (num <= MAX) {
                            nextRunnable.notifyRunnable();
                        }
                    }
                    if (num + 2 <= MAX) {
                        currentCondition.await();
                    } else {
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    private static void calByteOneNum(int n) {
        int[] array = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            array[i] = array[i >> 1] + (i & 1);
        }

        for (int i = 0; i <= n; i++) {
            System.out.print(array[i] + " ");
        }
    }

    private static String addByteStr(String str1, String str2) {
        if (str1 == null || str1.length() == 0) {
            return str2;
        }
        if (str2 == null || str2.length() == 0) {
            return str1;
        }

        StringBuilder stringBuilder = new StringBuilder();
        int index1 = str1.length() - 1;
        int index2 = str2.length() - 1;
        int carry = 0;
        while (index1 >= 0 || index2 >= 0) {
            if (index1 >= 0) {
                carry += str1.charAt(index1) - '0';
                index1--;
            }
            if (index2 >= 0) {
                carry += str2.charAt(index2) - '0';
                index2--;
            }
            stringBuilder.append(carry % 2);
            carry = carry / 2;
        }
        if (carry > 0) {
            stringBuilder.append(carry);
        }
        return stringBuilder.reverse().toString();
    }

    private static int divide(int x, int y) {
        if (x == 0) {
            return 0;
        }
        if (y == 0) {
            throw new IllegalArgumentException("y = 0");
        }
        boolean isPositive = x > 0 && y > 0 || x < 0 && y < 0;

        if (x > 0) {
            x = -x;
        }
        if (y > 0) {
            y = -y;
        }
        int sum = divideCore(x, y);
        return isPositive ? sum : -sum;
    }

    private static int divideCore(int negX, int negY) {
        int sum = 0;
        int px = negX, py = negY;
        int count = 0;
        while (px <= negY) {
            int pSum = 1;
            while (px <= py + py) {
                py += py;
                pSum += pSum;
                count++;
            }

            px = px - py;
            py = negY;
            sum += pSum;

            System.out.println("px=" + px + ",psum=" + pSum + ",sum=" + sum + ",py=" + py + ",count=" + count);
        }
        return sum;
    }

    private static class PrintRunnable2 implements Runnable {

        public static final int MAX = 100;
        public static volatile int num = 0;
        private int id;

        public PrintRunnable2 (int id) {
            this.id = id;
        }

        @Override
        public void run() {
            while (num <= MAX) {
                synchronized (PrintRunnable.class) {
                    if (num % 3 == id) {
                        System.out.println("thread " + id + num++);
                    }

                    PrintRunnable.class.notifyAll();
                    if (num <= MAX) {
                        try {
                            PrintRunnable.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private static class MyNode {
        private int val;
        private MyNode next;

        public MyNode(int val) {
            this.val = val;
        }

        public MyNode(int val, MyNode next) {
            this.val = val;
            this.next = next;
        }

        public MyNode reverse(MyNode head) {
            MyNode current = head;
            MyNode pre = null;
            while (current != null) {
                MyNode next = current.next;
                current.next = pre;
                pre = current;
                current = next;
            }
            return pre;
        }

        public static MyNode insert(MyNode head, int val) {
            MyNode newNode = new MyNode(val);
            if (head == null) {
                return newNode;
            }

            MyNode node = head;
            while (node.next != null) {
                node = node.next;
            }
            node.next = newNode;
            return head;
        }

        public static MyNode delete(MyNode head, int val) {
            if (head == null) {
                return null;
            }
            if (head.val == val) {
                return head.next;
            }
            MyNode node = head;
            while (node.next != null) {
                if (node.next.val == val) {
                    node.next = node.next.next;
                    break;
                }
                node = node.next;
            }
            return head;
        }

        //删除倒数K，先找到倒数K+1的节点，然后将其next指向倒数K-1的节点
        public static MyNode deleteReverseK(MyNode head, int k) {
            if (head == null) {
                return head;
            }
            MyNode left = head;
            MyNode right = head;
            int count = 0;
            while (count <= k) {
                if (right.next == null) {
                    //删除的正好是头
                    return head.next;
                }
                right = right.next;
            }

            while (right.next != null) {
                left = left.next;
                right = right.next;
            }

            left.next = left.next.next;
            return head;
        }
    }

    private static MyNode numToNode(int num) {
        if (num < 10) {
            return new MyNode(num);
        }

        MyNode head = new MyNode(num % 10);
        num = num / 10;
        MyNode current = head;
        while (num > 0) {
            MyNode newNode = new MyNode(num % 10);
            current.next = newNode;
            current = newNode;
            num = num / 10;
        }
        return head;
    }

    private static int NodeToNum(MyNode node) {
        MyNode p = node;
        int num = 0;
        int degree = 1;
        while (p != null) {
            num += degree * p.val;
            p = p.next;
            degree = degree * 10;
        }
        return num;
    }

    @org.junit.Test
    public static void moveZeroes(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return;
        }
        int left = 0, right = 1;
        int p;

        while (right < nums.length && left < nums.length) {
            while (left < nums.length && nums[left] != 0) {
                left++;
            }

            if (right <= left) {
                right = left + 1;
            }
            while (right < nums.length && nums[right] == 0) {
                right++;
            }

            if (right >= nums.length || left >= nums.length) {
                return;
            }
            p = nums[left];
            nums[left] = nums[right];
            nums[right] = p;

            left++;
            right++;
        }
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
    class Solution {
        public int maxDepth(TreeNode root) {
            return calc(root);
        }

        private int calc(TreeNode root) {
            if (root == null) {
                return 0;
            }
            return Math.max(calc(root.left), calc(root.right)) + 1;
        }

        public boolean leafSimilar(TreeNode root1, TreeNode root2) {
            List<Integer> leaf1 = new ArrayList<>();
            List<Integer> leaf2 = new ArrayList<>();

            getLeaf(leaf1, root1);
            getLeaf(leaf2, root2);
            if (leaf1.size() != leaf2.size()) {
                return false;
            }
            for (int i = 0;i < leaf1.size(); i++) {
                if (!leaf1.get(i).equals(leaf2.get(i))) {
                    return false;
                }
            }
            return true;
        }

        private void getLeaf(List<Integer> leafs, TreeNode root) {
            if (root == null) {
                return;
            }

            if (root.left == null && root.right == null) {
                leafs.add(root.val);
                return;
            }
            if (root.left != null) {
                getLeaf(leafs, root.left);
            }
            if (root.right != null) {
                getLeaf(leafs, root.right);
            }
        }

        public int goodNodes(TreeNode root) {
            if (root == null) {
                return 0;
            }

            return goodNodes(root, root.val);
        }

        public int goodNodes(TreeNode root, int max) {
            if (root == null) {
                return 0;
            }

            if (root.val <= max) {
                return goodNodes(root.left) + goodNodes(root.right) + 1;
            }
            return goodNodes(root.left) + goodNodes(root.right);
        }

        public int pathSum(TreeNode root, int targetSum) {
            if (root == null) {
                return 0;
            }
            return rootNum(root, targetSum) + pathSum(root.left, targetSum) + pathSum(root.right, targetSum);
        }

        public int rootNum(TreeNode node, int targetSum) {
            if (node == null) {
                return 0;
            }

            int result = 0;
            if (node.val == targetSum) {
                result = 1;
            }
            return result + rootNum(node.left, targetSum - node.val)
                    + rootNum(node.right, targetSum - node.val);
        }
    }
}
