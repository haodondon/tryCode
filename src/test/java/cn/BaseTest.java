package cn;

import cn.list.MyArrayList;
import cn.map.MyConcurrentMap;
import cn.map.MyHashMap;
import cn.tree.BinarySearchTree;
import cn.tree.RedBlackTree;
import javafx.concurrent.Worker;

import java.util.Scanner;

/**
 * @Description: 全局测试类
 * @Author 一枚路过的程序猿
 * @Date 2023/11/10 11:38
 * @Version 1.0
 */
public class BaseTest {

    public static void main(String[] args) {

        myConcurrentMap();

    }

    /**
     * 线程安全集合测试
     */
    public static void myConcurrentMap(){

        // 创建一个 ConcurrentHashMap
        MyConcurrentMap<String, Integer> map = new MyConcurrentMap<>();

        // 创建并启动多个线程，向 ConcurrentHashMap 中添加元素
        for (int i = 0; i < 20; i++) {
            String key = "Key-" + i;
            Thread thread = new Thread(()->{
                // 模拟向 ConcurrentHashMap 中添加元素
                for (int j = 0; j < 1000; j++) {
                    map.putVal(key, j);
                }
                System.out.println(Thread.currentThread().getName() + " 完成了对 ConcurrentHashMap 的操作：" + map.get(key));

            });
            thread.start();
        }

        // 等待所有线程执行完毕
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * List测试
     */
    public static void myArrayListTest(){

        MyArrayList<Integer> myArrayListTest = new MyArrayList<Integer>();
        myArrayListTest.add(1);
        myArrayListTest.add(2);
        myArrayListTest.add(3);
        myArrayListTest.add(4);

        int size = myArrayListTest.getSize();
        System.out.println("size:" + size);

        myArrayListTest.remove(3);
        size = myArrayListTest.getSize();
        System.out.println("size:" + size);

    }

    /**
     * HashMap测试
     */
    public static void myHashMapTest(){

        // 创建HashMap
        MyHashMap<String, String> hashMap = new MyHashMap<String, String>();

        // 添加哈希不冲突的key，不少于70个
        for (int i = 0; i < 70; i++) {
            String key = "Key" + i;
            String value = "Value" + i;
            hashMap.put(key, value);
        }

        // 添加哈希冲突的key，不少于10个，但哈希值相同
        String collidingKeyPrefix = "CollisionKey";
        String collidingValue = "CollisionValue";

        for (int i = 0; i < 10; i++) {
            String collidingKey = collidingKeyPrefix + i;
            // 使用相同的哈希值
            int hash = collidingKeyPrefix.hashCode();
            // 强制将哈希值设为相同的值
            setHash(collidingKey, hash);
            hashMap.put(collidingKey, collidingValue+i);
        }

        String removeKey = "CollisionKey1";
        int hash = collidingKeyPrefix.hashCode();

        // 强制将哈希值设为相同的值
        setHash(removeKey, hash);
        //测试红黑树删除情况
        hashMap.remove(removeKey);

        //测试链表删除情况
        hashMap.remove("Key40");

    }

    /**
     * 二叉树测试
     */
    public static void binarySearchTreeTest() {

        int data[] = {4,1,2,0,8,7,5,123,21};

        BinarySearchTree root = new BinarySearchTree(data[0]);

        for (int i = 1; i < data.length; i++) {

            BinarySearchTree.add(root, data[i]);

        }

        BinarySearchTree.out(root);

    }

    /**
     * 红黑树测试
     */
    public static void redBlackTreeTest(){

        //添加测试
        redBlackTreeAdd();

        //删除测试
        //redBlackTreeRemove();

    }

    /**
     * 红黑树添加测试
     */
    private static void redBlackTreeAdd(){

        RedBlackTree tree = new RedBlackTree();

        Scanner scanner = new Scanner(System.in);

        System.out.println("请输入要插入的数值：");

        while (scanner.hasNextLine()) {

            tree.add(scanner.nextInt());
            System.out.println("------------------------------------------------------------------------------");
            TreePrintUtil.show(tree.getRoot());
            System.out.println("------------------------------------------------------------------------------");
            System.out.println();
            System.out.println();
            System.out.println("请输入要插入的数值：");

        }

        scanner.close();

    }

    /**
     * 红黑树删除测试
     */
    private static void redBlackTreeRemove(){

        RedBlackTree tree = new RedBlackTree();

        tree.add(1);
        tree.add(2);
        tree.add(3);
        tree.add(4);
        tree.add(5);
        tree.add(6);
        tree.add(7);
        tree.add(8);
        tree.add(9);
        tree.add(10);
        tree.add(11);
        TreePrintUtil.show(tree.getRoot());


        Scanner scanner = new Scanner(System.in);

        System.out.println("请输入要刪除的数值：");

        while (scanner.hasNextLine()) {

            tree.remove(scanner.nextInt());
            System.out.println("------------------------------------------------------------------------------");
            TreePrintUtil.show(tree.getRoot());
            System.out.println("------------------------------------------------------------------------------");
            System.out.println();
            System.out.println();
            System.out.println("请输入要刪除的数值：");

        }

        scanner.close();

    }

    /**
     * 设置key的hash值
     * @param key
     * @param hash
     */
    private static void setHash(Object key, int hash) {
        try {
            java.lang.reflect.Field field = String.class.getDeclaredField("hash");
            field.setAccessible(true);
            field.set(key, hash);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
