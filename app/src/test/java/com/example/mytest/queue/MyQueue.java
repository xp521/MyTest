package com.example.mytest.queue;

import java.util.Stack;

/**
 * //TODO注释
 *
 * @author Created by xuepeng on 2023/6/21.
 */
public class MyQueue<D> {
    Stack<D> in = new Stack<>();
    Stack<D> out = new Stack<>();

    private D push(D data) {
        return in.push(data);
    }

    private D pop() {
        out.push(in.pop());
        return out.pop();
    }
}
