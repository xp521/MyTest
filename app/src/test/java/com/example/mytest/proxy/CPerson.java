package com.example.mytest.proxy;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * //TODO注释
 *
 * @author Created by xuepeng on 2023/7/12.
 */
public class CPerson implements IPerson {
    @Override
    public void get() {
        System.out.println("get xxx");
    }
}
