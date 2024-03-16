package com.example.mytest.tree;

import java.util.HashMap;
import java.util.Map;

/**
 * //TODO注释
 *
 * @author Created by xuepeng on 2024/1/5.
 */
public class TestGD {
    public static void main(String[] args) {

    }

    //输入字符串 26小写 第一个只出现一次
    public char getFirstOnce(String str) throws Exception {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("str is empty");
        }

        Map<Character, Integer> map = new HashMap<>();
        for (int i=0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (map.containsKey(c)) {
                int count = map.get(c);
                map.put(c, count);
            } else {
                map.put(c, 1);
            }
        }


        for (int i=0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (map.get(c) == 1) {
                return c;
            }
        }

        throw new Exception("not found");
    }

    //10.01.01 10.01.1
    private int compareVersion(String v1, String v2) {
        if (!isValide(v1) || !isValide(v2)) {
            throw new IllegalArgumentException("Argument is err");
        }


        String[] arr1 = v1.split(".");
        String[] arr2 = v2.split(".");

        int min = Math.min(arr1.length, arr2.length);
        for (int i=0; i < min; i++) {
            int firstA = Integer.valueOf(arr1[0]);
            int firstB = Integer.valueOf(arr2[0]);
            if (firstA > firstB) {
                return 1;
            }
            if (firstA < firstB) {
                return -1;
            }
        }
        return 0;
    }

    private boolean isValide(String v1) {
        return true;
    }
}
