package com.utils;

import com.domain.Point;

import java.util.List;

/**
 * 提供四个方向点的类
 */
public class DirPoints {

    /**
     * 四个方向的点
     */
    private List<Point> dirPoints;

    public List<Point> getDirPoints() {
        return dirPoints;
    }

    public void setDirPoints(List<Point> dirPoints) {
        this.dirPoints = dirPoints;
    }
}
