package com.example.mytest;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.locks.Lock;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    public static void main(String[] a) {
        System.out.println(lengthOfLongestSubstring("bbtablud"));
    }

    public static int lengthOfLongestSubstring(String s) {
        if (s == null) {
            return 0;
        }
        int size = s.length();
        int max = 0;
        HashMap<Character, Integer> sets = new HashMap<>();
        int left = 0;
        for (int right=left; right<size; right++) {
            char c = s.charAt(right);
            if (sets.containsKey(c)) {
                //有重复 左下标向前移动 count对应减小,清楚之前的key\value
                int newLeft = sets.get(c) + 1;
                while (left < newLeft) {
                    sets.remove(s.charAt(left));
                    left++;
                }
                left = newLeft;
            }
            sets.put(c, right);
            if (max < sets.size()) {
                max = sets.size();
            }
        }
        return max;
    }
    public int[] twoSum(int[] nums, int target) {
        if (nums == null) {
            return null;
        }

        Map<Integer, Integer> maps = new HashMap();

        for(int i=0; i<nums.length; i++) {
            int other = target - nums[i];
            if (maps.containsKey(other)) {
                return new int[]{i, maps.get(other)};
            }

            maps.put(nums[i], i);
        }
        return null;
    }


    private static class Tree<T> {
        Tree left;
        Tree right;
        int value;

        public Tree(int value) {
            this.value = value;
        }
    }
    /**
     * 二叉树的先序遍历
     */
    public static void preOrder(Tree tree) {
        if (tree == null) {
            return;
        }
        System.out.println(tree.value);
        preOrder(tree.left);
        preOrder(tree.right);
    }

    public Tree search(Tree tree, int target) {
        if (tree == null) {
            return null;
        }

        if (target == tree.value) {
            return tree;
        }

        if (target < tree.value) {
            return search(tree.left, target);
        }

        return search(tree.right, target);
    }

    public int findMin(Tree tree) {
        int min = -1;
        while (tree != null) {
            min = tree.value;
            tree = tree.left;
        }
        return min;
    }

    public void insert (Tree tree, int target) {
        Tree parentTree = null;
        Tree pTree = tree;
        while (pTree != null) {
            parentTree = pTree;
            if (target > pTree.value) {
                pTree = pTree.right;
            } else {
                pTree = parentTree.left;
            }
        }

        if (parentTree == null) {
            return;
        }

        if (target > parentTree.value) {
            parentTree.right = new Tree(target);
        } else {
            parentTree.left = new Tree(target);
        }
    }

    public String mergeAlternately(String word1, String word2) {
        if (word1 == null) {
            return word2;
        }
        if (word2 == null) {
            return word1;
        }
        StringBuilder stringBuilder = new StringBuilder(word1.length() + word2.length());
        int index1 = 0;
        int index2 = 0;
        while (index1 < word1.length() || index2 < word2.length()) {
            if (index1 < word1.length()) {
                stringBuilder.append(word1.charAt(index1));
                index1++;
            }

            if (index2 < word2.length()) {
                stringBuilder.append(word2.charAt(index2));
                index2++;
            }
        }
        return stringBuilder.toString();
    }

    public String gcdOfStrings(String str1, String str2) {
        if (str1 == null || str2 == null) {
            return "";
        }

        int len1 = str1.length();
        int len2 = str2.length();
        for (int i = Math.min(len1, len2); i > 0; i--) {
            if (len1 % i == 0 && len2 % i == 0) {
                String gcd = str1.substring(0, i);
                if (check(gcd, str1) && check(gcd, str2)) {
                    return gcd;
                }
            }
        }
        return "";
    }

    private boolean check(String gcd, String target) {
        int time = target.length() / gcd.length();
        StringBuilder stringBuilder = new StringBuilder(target.length());
        while (time-- > 0) {
            stringBuilder.append(gcd);
        }
        return stringBuilder.toString().equals(target);
    }
}