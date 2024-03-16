package com.example.mytest;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * //TODO注释
 *
 */
public class TestNiu {
    public static void main(String[] args) {
    }

    public int minDistance(String word1, String word2) {
        //长度加1，算上初始空串
        int len1 = word1.length() + 1;
        int len2 = word2.length() + 1;

        int[][] arr = new int[len1][len2];
        for (int i = 0; i < len1; i ++) {
            arr[i][0] = i;
        }
        for (int j = 0; j < len2; j++) {
            arr[0][j] = j;
        }

        for (int i = 1; i < len1; i++) {
            for (int j = 1; j < len2; j++) {
                if (word1.charAt(i) == word2.charAt(j)) {
                    arr[i][j] = arr[i -1][j-1];
                } else {
                    int min = Math.min(arr[i-1][j], arr[i][j-1]);
                    arr[i][j] = Math.min(min, arr[i-1][j-1]) + 1;
                }
            }
        }
        return arr[len1-1][len2-1];
    }

    public int rob(int[] nums) {
        int n = nums.length;
        if (n <= 1) {
            return nums[0];
        }

        int[] result = new int[2];
        result[0] = nums[0];
        result[1] = Math.max(nums[0], nums[1]);
        for (int i = 2; i < n; i++) {
            result[i % 2] = Math.max(result[(i-2)%2] + nums[i], result[(i-1)%2]);
        }
        return result[(n-1)%2];
    }

    public List<List<Integer>> subSet(int[] numS) {
        List<List<Integer>> result = new ArrayList<>();
        if (numS == null || numS.length == 0) {
            return result;
        }
        helper(numS, 0, new LinkedList<>(), result);
        return result;
    }

    private void helper(int[] numS, int index, LinkedList<Integer> subList, List<List<Integer>> result) {
        if (index == numS.length-1) {
            result.add(new ArrayList<>(subList));
        } else {
            helper(numS, index + 1, subList, result);

            subList.add(numS[index]);
            helper(numS, index + 1, subList, result);

            subList.removeLast();
        }
    }

    private void test(int[] cons, int sum) {
        int size = cons.length;
        int[][] result = new int[size][sum];

        for (int i = 0; i < sum; i++) {
            result[0][sum] = Integer.MAX_VALUE;
        }

        for (int i = 1; i < size; i++) {
            for (int j = sum; j>=1; j--) {
                for (int k = 0; j-k*cons[i]>=0; k++) {
                    result[i][j] = Math.min(result[i][j], result[i-1][j-k*cons[i]] + k);
                }
            }
        }

    }

    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        if (digits == null || digits.length() == 0) {
            return result;
        }

        help(0, digits, result, new StringBuilder());
        return result;
    }

    private void help(int index, String digits, List<String> result, StringBuilder sb) {
        if (index >= digits.length()) {
            result.add(sb.toString());
        } else {
            int n = digits.charAt(index) - '0';
            char[] chars = get(n);
            for (char c : chars) {
                sb.append(c);
                help(index + 1, digits, result, sb);
                int lastIndex = sb.length() - 1;
                sb.delete(lastIndex, lastIndex + 1);
            }
        }
    }

    private char[] get(int n) {
        if (2 <= n && n <= 6) {
            char startC = (char) ('a' + (n - 2) * 3);
            return new char[]{startC, (char) (startC + 1), (char) (startC + 2)};
        }
        if (n == 7) {
            return new char[]{'p', 'q', 'r', 's'};
        }
        if (n == 8) {
            return new char[]{'t', 'u', 'v'};
        }
        if (n == 9) {
            return new char[]{'w', 'x', 'y', 'z'};
        }
        return null;
    }

    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> result = new ArrayList<>();
        help(result, n, 1, k, new ArrayList<>());
        return result;
    }

    private void help (List<List<Integer>> result, int target, int index, int k, List<Integer> list) {
        if (list.size() == k) {
            if (target == 0) {
                result.add(new ArrayList<>(list));
            }
        } else if (target > 0 && index <= 9) {
            help(result, target, index + 1, k, list);

            //
            list.add(index);
            help(result, target - index, index + 1, k, list);
            list.remove(list.size() - 1);
        }
    }
}
