package com.domain;

import java.util.Objects;

/**
 * 二维的点
 */
public class Point {
    /**
     * 第一维的位置
     */
    private int i;
    /**
     * 第二维的位置
     */
    private int j;

    /**
     * Point的无参构造方法
     */
    public Point() {
    }

    /**
     * Point的构造方法
     *
     * @param i 第一维
     * @param j 第二维
     */
    public Point(int i, int j) {
        this.i = i;
        this.j = j;
    }

    /**
     * Point的拷贝构造方法
     *
     * @param point 点
     */
    public Point(Point point) {
        this.i = point.i;
        this.j = point.j;
    }

    /**
     * 进行当前点与给定点的加法运算
     *
     * @param point 被加点
     *
     * @return 加法运算后的点
     */
    public Point add(Point point) {
        return new Point(this.i + point.i, this.j + point.j);
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Point point = (Point) o;
        return Objects.equals(i, point.i) && Objects.equals(j, point.j);
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j);
    }
}
