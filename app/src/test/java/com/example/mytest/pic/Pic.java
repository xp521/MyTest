package com.example.mytest.pic;

import java.util.LinkedList;
import java.util.Queue;

/**
 * //TODO注释
 *
 * @author Created by xuepeng on 2023/6/25.
 */
public class Pic {
    private int[] e;
    private int[][] v;

    private void deep() {
        boolean[] visitArray = new boolean[e.length];
        for (int i=0; i<e.length; i++) {
            if (!visitArray[i]) {
                deepNode(visitArray, i);
            }
        }
    }

    private void deepNode(boolean[] visitArray, int index) {
        System.out.println(e[index]);
        visitArray[index] = true;
        int nextIndex = findNextIndex(index, -1);
        while (nextIndex >= 0) {
            deepNode(visitArray, nextIndex);
            nextIndex = findNextIndex(index, nextIndex);
        }
    }

    private int findNextIndex(int index, int preIndex) {
        for (int i = preIndex + 1; i < e.length; i++) {
            if (v[index][i] == 1) {
                return i;
            }
        }
        return -1;
    }

    private void bNode(boolean[] visitArray, int index, Queue<Integer> queue) {
        printNode(visitArray, index);

        for (int i = 0; i<e.length; i++) {
            if (v[index][i] == 1) {
                queue.offer(i);
            }
        }

        Integer pollIndex;
        do {
            pollIndex = queue.poll();
        } while (pollIndex != null); {
            bNode(visitArray, pollIndex, queue);
        }
    }

    private void printNode(boolean[] visitArray, int index) {
        System.out.println(e[index]);
        visitArray[index] = true;
    }

    private void b() {
        boolean[] visitArray = new boolean[e.length];
        Queue<Integer> queue = new LinkedList();

        for (int i=0; i<e.length; i++) {
            if (!visitArray[i]) {
                bNode(visitArray, i, queue);
            }
        }
    }


    public static void main(String[] args) {
        System.out.println("xxx");
    }
}
