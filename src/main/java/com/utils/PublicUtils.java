package com.utils;

import java.util.Stack;

import static java.lang.reflect.Array.newInstance;

/**
 * 公共工具类
 */

@SuppressWarnings("all")
public final class PublicUtils {

    /**
     * 深拷贝一个二维数组
     *
     * @param type 一维数组的class对象
     * @param src  源数据
     * @param <T>  泛型
     *
     * @return 深拷贝的二维数组
     */
    public static <T> T[][] getClone(Class<T[]> type, T[][] src) {
        T[][] result = null;
        if (src != null) {
            //利用反射将泛型擦除后的Object类型转换为真实类型
            T[][] twoArray = (T[][]) newInstance(type, src.length);
            for (int i = 0, length = twoArray.length; i < length; i++) {
                twoArray[i] = (T[]) newInstance(type.getComponentType(), src[i].length);
                System.arraycopy(src[i], 0, twoArray[i], 0, twoArray[0].length);
            }
            result = twoArray;
        }
        return result;
    }

    /**
     * 深拷贝一个一维数组
     *
     * @param type 数组中元素的class对象
     * @param src  源数据
     * @param <T>  泛型
     *
     * @return 深拷贝的一维数组
     */
    public static <T> T[] getClone(Class<T> type, T[] src) {
        T[] result = null;
        if (src != null) {
            //利用反射将泛型擦除后的Object类型转换为真实类型
            T[] array = (T[]) newInstance(type, src.length);
            System.arraycopy(src, 0, array, 0, array.length);
            result = array;
        }
        return result;
    }

    /**
     * 将一个栈转换为数组
     *
     * @param type 数组中元素的class对象
     * @param src  源数据
     * @param <T>  泛型
     *
     * @return 数组
     */
    public static <T> T[] stackToArray(Class<T> type, Stack<T> src) {
        T[] result = null;
        if (src != null) {
            //利用反射将泛型擦除后的Object类型转换为真实类型
            T[] array = (T[]) newInstance(type, src.size());
            Stack<T> srcClone = (Stack) src.clone();
            int index = 0;
            //弹出栈顶的元素，直到栈为空
            while (!srcClone.empty()) {
                array[index++] = srcClone.pop();
            }
            result = array;
        }
        return result;
    }

}
