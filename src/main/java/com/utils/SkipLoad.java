package com.utils;

import com.domain.Maze;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * 跳过加载的类
 */
@Component
@Setter
public class SkipLoad {
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 文件修改时间
     */
    private long fileChangeTime;

    /**
     * 若文件未改变，则可以跳过加载
     *
     * @param nowFilePath       当前文件位置
     * @param nowFileChangeTime 当前文件的修改时间
     * @return 文件未改变返回true，否则返回false
     */
    public boolean canLazyLoad(String nowFilePath, Long nowFileChangeTime) {
        return nowFilePath.equals(filePath) && nowFileChangeTime.equals(fileChangeTime);
    }

    /**
     * 判断当前迷宫是否进行过搜索
     *
     * @param maze 迷宫对象
     * @return 进行迷宫最短路径的搜索返回true，未进行则返回false
     */
    public boolean hasFindMazePath(Maze maze) {
        return maze.getMazePathPoints() != null;
    }
}
