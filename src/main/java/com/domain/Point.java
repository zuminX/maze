package com.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 二维的点
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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
     * 进行当前点与给定点的加法运算
     *
     * @param point 被加点
     * @return 加法运算后的点
     */
    public Point add(Point point) {
        return new Point(this.i + point.i, this.j + point.j);
    }
}
