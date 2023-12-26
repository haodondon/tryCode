package cn.list;

import java.util.Arrays;

/**
 * @Description:
 * @Author 一枚路过的程序猿
 * @Date 2023/11/10 10:42
 * @Version 1.0
 */
public class MyArrayList<E> {

    /** 集合长度 */
    int size;

    /** 初始化数组长度 */
    final int initSize = 10;

    /** 初始一个空数组 */
    Object[] tab = new Object[size];

    /**
     * 添加方法
     * @param e 待添加元素
     * @return
     */
    public boolean add(E e){
        grow(); // 初始化尝试扩容
        tab[size++] = e; // 添加元素
        return true;
    }

    /**
     * 刪除元素
     * @param e 待刪除元素
     * @return 成功：true，失敗：false
     */
    public boolean remove(E e){

        if (e == null) return false;

        for (int i = 0; i < size; i++) {

            if (e.equals(tab[i])) {

                /** 将待删除元素移动到最后一位 */
                System.arraycopy(tab, i + 1, tab, i, size - i - 1);

                /** 最后一位置空，帮助垃圾回收，然后数组长度-1 */
                tab[--size] = null; //help GC
                return true;

            }

        }

        return false;

    }

    /** 扩容 */
    private void grow() {

        /** 数组长度和 size 相等时，表示需要扩容或初始化 */
        if (tab.length == size) {

            /** 为0表示需要初始化 */
            if(tab.length == 0){

                /** 构建一个长度10的数组 */
                tab = new Object[initSize];

            /** 扩容数组 */
            }else{

                /** 计算新长度： 旧数组长度 + (旧数组长度 / 2) */
                int newSize = tab.length + (tab.length >> 1);

                /** 扩容操作 */
                tab = Arrays.copyOf(tab, newSize);

            }

        }

    }

    public int getSize() {
        return size;
    }
}
