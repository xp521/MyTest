package com.example.mytest;

/**
 * //TODO注释
 *
 * @author Created by xuepeng on 2023/11/2.
 */
public class TestHH {
    public static void main(String[] args) {
        //给定一个二叉搜索树root和一个目标结果k，如果二叉树中存在两个元素且它们的和等于k，则返回true，否则返回false

        //事例：
        //输入：root = [5,3,6,2,4,null,7],k=9
        //输出：true
        Tree tree = new Tree(5);
        tree.left = new Tree(3);
        tree.right = new Tree(6);
        tree.left.left = new Tree(2);
        tree.left.right = new Tree(4);
        tree.right.right = new Tree(7);

    }

    public boolean findTwoValNum(Tree rootTree, int k) {
        if (rootTree == null) {
            return false;
        }
        int n2 = k - rootTree.val;
        if (search(rootTree, n2)) {
            //找到了
            return true;
        }
        return search(rootTree.left, k) || search(rootTree.right, k);
    }

    public boolean search(Tree rootTree, int val) {
        if (rootTree == null) {
            return false;
        }

        if (rootTree.val == val) {
            return true;
        }

        if ((rootTree.val > val)) {
            return search(rootTree.left, val);
        }

        return search(rootTree.right, val);
    }

    public static class Tree {
        public Tree(int val) {
            this.val = val;
        }
        int val;
        Tree left;
        Tree right;
    }
}
