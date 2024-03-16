package com.example.mytest;

/**
 * //TODO注释
 *
 * @author Created by xuepeng on 2023/6/30.
 */
public class Sort {
    public static void sort(int[] arr, int start, int end) {
        if (!(start < end)) {
            return;
        }

        int left = start;
        int right = end;
        int p = arr[left];
        while (left < right) {
            while (left < right && arr[right] > p) {
                right--;
            }
            if (left < right) {
                arr[left++] = arr[right];
            }

            while (left < right && arr[left] < p) {
                left++;
            }
            if (left < right) {
                arr[right--] = arr[left];
            }

            System.out.println("left=" + left + "，right=" + right);
        }
        arr[left] = p;

        sort(arr, start, left - 1);
        sort(arr, left + 1, end);
    }

    public static void main(String[] args) {
        int[] arr = {1, 8, 6, 9, 0, 2, 3, 7, 4, -4};
        sort(arr, 0, arr.length - 1);
        for (int x : arr) {
            System.out.print(x + " ");
        }
    }
}
