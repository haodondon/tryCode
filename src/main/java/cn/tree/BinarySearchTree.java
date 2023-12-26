package cn.tree;

/**
 * @Description: 二叉查找树，此树相对比较简单
 * @Author 一枚路过的程序猿
 * @Date 2023/11/14 11:10
 * @Version 1.0
 */
public class BinarySearchTree {

    private Integer val;

    private BinarySearchTree left;

    private BinarySearchTree right;

    public BinarySearchTree(Integer val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }

    /**
     * 添加方法
     * @param root 根节点
     * @param value 添加的值
     */
    public static void add(BinarySearchTree root, Integer value){

        /** 小于添加至左边 */
        if (root.val > value) {

            if (root.left == null) {

                root.left = new BinarySearchTree(value);

            }else {
                /** 从左边继续递归查找 */
                add(root.left, value);
            }

        }else { /** 大于从右边查找 */

            if (root.right == null) {

                root.right = new BinarySearchTree(value);

            }else {
                /** 从右边继续递归查找 */
                add(root.right, value);
            }

        }

    }

    /**
     * 输出
     * @param root
     */
    public static void out(BinarySearchTree root){
        if (root != null) {
            out(root.left);
            System.out.println(root.val);
            out(root.right);
        }
    }

}
