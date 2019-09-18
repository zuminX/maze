package utils;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Stack;

import static java.lang.reflect.Array.newInstance;

public final class PublicUtils {

    /**
     * 将一个元素为数组类型的List转换为二维数组
     *
     * @param type 一维数组的class对象
     * @param list 待转换的List
     * @param <T>  泛型
     * @return 二维数组
     */
    public static <T> T[][] listToTwoArray(Class<T[]> type, List<T[]> list) {
        T[][] result = null;
        if (list != null) {//利用反射将泛型擦除后的Object类型转换为真实类型
            T[][] twoArray = (T[][]) newInstance(type, list.size());
            for (int i = 0, length = twoArray.length; i < length; i++) {
                twoArray[i] = (T[]) newInstance(type.getComponentType(), list.get(i).length);
                System.arraycopy(list.get(i), 0, twoArray[i], 0, twoArray[0].length);
            }
            result = twoArray;
        }
        return result;
    }

    public static <T> T[][] getClone(Class<T[]> type, T[][] src) {
        T[][] result = null;
        if (src != null) {
            T[][] twoArray = (T[][]) newInstance(type, src.length);
            for (int i = 0, length = twoArray.length; i < length; i++) {
                twoArray[i] = (T[]) newInstance(type.getComponentType(), src[i].length);
                System.arraycopy(src[i], 0, twoArray[i], 0, twoArray[0].length);
            }
            result = twoArray;
        }
        return result;
    }

    public static <T> T[] getClone(Class<T> type, T[] src) {
        T[] result = null;
        if (src != null) {
            T[] array = (T[]) newInstance(type, src.length);
            System.arraycopy(src, 0, array, 0, array.length);
            result = array;
        }
        return result;
    }

    public static <T> T[] stackToArray(Class<T> type, Stack<T> src) {
        T[] result = null;
        if (src != null) {
            T[] array = (T[]) newInstance(type, src.size());
            Stack<T> srcClone = (Stack) src.clone();
            int index = 0;
            while (!srcClone.empty()) {
                array[index++] = srcClone.pop();
            }
            result = array;
        }
        return result;
    }

}
