package com.example.mytest;

import org.w3c.dom.Node;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * //TODO注释
 *
 * @author Created by xuepeng on 2022/5/19.
 */
public class Test<S> implements Serializable, IT {
    private static final boolean DEBUG = false;
    public void log() {
    }

    @Override
    public S getData() {
        return null;
    }

    public <T> T get(Class<T> tClass) throws IllegalAccessException, InstantiationException {
        return tClass.newInstance();
    }

    private int[] find(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("arr can not be empty");
        }
        int i = 0;
        int j = arr.length - 1;
        while (i < j) {
            if (arr[i] + arr[j] == target) {
                break;
            } else if (arr[i] + arr[j] < target) {
                i++;
            } else {
                j--;
            }
        }

        return new int[]{i, j};
    }

    private void moveZero(int[] nums) {
        int index = 0;
        for (int num : nums) {
            if (num != 0) {
                nums[index++] = num;
            }
        }

        while (index < nums.length) {
            nums[index++] = 0;
        }
    }

    public static int findMinArrowShots(int[][] points) {
        if (points == null || points.length == 0) {
            return 0;
        }
        Arrays.sort(points, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return Integer.compare(o1[1], o2[1]);
            }
        });

        int minArrowShots = 1;
        int currentX = points[0][1];
        for (int i = 1; i < points.length; i++) {
            if (points[i][0] > currentX) {
                minArrowShots++;
                currentX = points[i][1];
            }
        }
        return minArrowShots;
    }

    public static class Trie {
        private Trie[] childern;
        private boolean isEnd;

        public Trie() {
            childern = new Trie[26];
            isEnd = false;
        }

        public void insert(String word) {
            Trie node = this;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                int index = c - 'a';
                if (node.childern[index] == null) {
                    node.childern[index] = new Trie();
                }
                node = node.childern[index];
            }
            node.isEnd = true;
        }

        public boolean search(String word) {
            Trie node = searchPrefix(word);
            return node != null && node.isEnd;
        }

        public boolean startsWith(String prefix) {
            return searchPrefix(prefix) != null;
        }

        private Trie searchPrefix(String prefix) {
            Trie node = this;
            for (int i = 0; i < prefix.length(); i++) {
                char c = prefix.charAt(i);
                int index = c - 'a';
                if (node.childern[index] == null) {
                    return null;
                }
                node = node.childern[index];
            }
            return node;
        }
    }

    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        Trie trie = new Trie();
        for (String str : products) {
            trie.insert(str);
        }

        List<List<String>> result = new ArrayList<>(searchWord.length());
        for (int i = 0; i < searchWord.length(); i++) {
            Trie searchTrie = trie.searchPrefix(searchWord.substring(0, i + 1));
            List<String> childeResult = null;
            if (searchTrie != null) {
                childeResult = new ArrayList<>(3);
                for (int j =0; j < searchTrie.childern.length; j++) {
                    if (searchTrie.childern[i] != null) {
                    }
                }
            }
            result.add(childeResult);
        }
        return result;
    }

    public static int uniquePaths(int m, int n) {
        int[][] destArr = new int[m][n];
        destArr[0][0] = 1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i - 1 >= 0) {
                    destArr[i][j] = destArr[i - 1][j];
                }
                if (j - 1 >= 0) {
                    destArr[i][j] += destArr[i][j - 1];
                }
            }
        }
        return destArr[m-1][n-1];
    }

    public int longestCommonSubsequence(String text1, String text2) {
        int len1 = text1.length() + 1;
        int len2 = text2.length() + 1;
        int[][] longestArr = new int[len1][len2];
        for (int i = 0; i < len1; i++) {
            longestArr[i][0] = 0;
        }
        for (int i = 0; i < len2; i++) {
            longestArr[0][i] = 0;
        }
        for (int i = 1; i < len1; i ++) {
            for (int j = 1; j < len2; j++) {
                char c1 = text1.charAt(i);
                char c2 = text2.charAt(j);
                if (c1 == c2) {
                    longestArr[i][j] = longestArr[i-1][j-1] + 1;
                } else {
                    longestArr[i][j] = Math.max(longestArr[i][j-1], longestArr[i-1][j]);
                }
            }
        }
        return longestArr[len1-1][len2-1];
    }

    public static String reverseWords(String s) {
        String[] arr = s.split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = arr.length - 1;i>=0;  i--) {
            if (arr[i].trim().length() == 0) {
                continue;
            }
            if (i == 0) {
                stringBuilder.append(arr[i]);
            } else {
                stringBuilder.append(arr[i] + " ");
            }
        }
        return stringBuilder.toString();
    }

    public int minEatingSpeed(int[] piles, int h) {
        int low = 1;
        int high = 1;
        for (int p : piles) {
            if (p > high) {
                high = p;
            }
        }
        int pSpeed = high;
        while (low < high) {
            int middle = (low + high) / 2;
            boolean result = isValid(middle, piles, h);
            if (result) {
                if (pSpeed > middle) {
                    pSpeed = middle;
                }
                high--;
            } else {
                low++;
            }
        }
        return pSpeed;
    }

    private boolean isValid(int speed, int[] piles, int h) {
        int time = 0;
        for (int p : piles) {
            if (p % speed == 0) {
                time += p / speed;
            } else {
                time += p / speed + 1;
            }
        }
        return time <= h;
    }

    public static void main(String[] args) {
        int[] arr = {3, 6, 7, 11};
        HashMap map;

        System.out.println(tableSizeFor(3));
        System.out.println(tableSizeFor(5));
        System.out.println(tableSizeFor(1));

    }

    static final int MAXIMUM_CAPACITY = 1 << 30;
    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
}
