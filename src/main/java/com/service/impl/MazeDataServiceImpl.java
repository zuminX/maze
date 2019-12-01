package com.service.impl;

import com.dao.MazeDao;
import com.domain.Maze;
import com.domain.Point;
import com.service.MazeDataService;
import com.utils.BaseHolder;
import com.utils.DirPoints;
import com.utils.Information;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import static com.utils.PublicUtils.getClone;
import static com.utils.PublicUtils.stackToArray;

/**
 * 迷宫业务层
 * 接收控制层的数据
 * 调用dao层获得数据
 * 返回数据给控制层
 */
@Service("mazeService")
public class MazeDataServiceImpl implements MazeDataService {
    private final MazeDao mazeDao;

    /**
     * 注入成员变量
     */
    public MazeDataServiceImpl(MazeDao mazeDao) {
        this.mazeDao = mazeDao;
    }

    /**
     * 对进行广度优先搜索后的迷宫数据进行逆向处理
     * 即尝试从终点回到起点
     * 为迷宫对象增加组成最短路径所有点的数据
     *
     * @param maze 迷宫对象
     * @return 有异常返回null, 无异常返回false
     */
    @Override
    public Boolean getPath(Maze maze) {
        final Point start = maze.getStart();
        final Point end = maze.getEnd();
        //确保存在起点和终点
        if (start == null || end == null) {
            throw new RuntimeException(Information.missingStartAndEndPoints);
        }

        //获取路径数据
        getPathData(maze);

        //若终点无步数数据，则该迷宫无法走出
        if (getPointValueByMaze(end, maze.getMazeStepData()) < 1) {
            throw new RuntimeException(Information.canTGetOut);
        }

        //在栈中存储迷宫路线的点
        Stack<Point> stack = new Stack<>();
        stack.push(end);

        Point currentPoint = end;
        //未回到起点
        while (!currentPoint.equals(start)) {
            //当前点的值
            int valueCurrent = getPointValueByMaze(currentPoint, maze.getMazeStepData());

            //下个点的值
            Integer valueNext;
            //尝试从四个方向寻找路线
            for (Point dirPoint : BaseHolder.getBean("dirPoints", DirPoints.class).getDirPoints()) {
                Point nextPoint = currentPoint.add(dirPoint);
                valueNext = getPointValueByMaze(nextPoint, maze.getMazeStepData());

                //该点不存在
                if (valueNext == null) {
                    continue;
                }

                //当前点的值为前一个点的值减一
                if (valueNext + 1 == valueCurrent) {
                    stack.push(nextPoint);
                    currentPoint = nextPoint;
                    break;
                }
            }
        }
        //将栈中数据转为数组返回
        Point[] points = stackToArray(Point.class, stack);
        maze.setMazePathPoints(points);
        return false;
    }

    /**
     * 对迷宫进行广度优先搜索
     * 尝试从起点进行搜索到终点
     * 为迷宫对象增加从起点到尝试点步数的数据
     *
     * @param maze 迷宫对象
     */
    private void getPathData(Maze maze) {
        //确保迷宫原始数据存在
        if (maze.getMazeOriginalData() == null) {
            getMazeOriginalData(maze);
        }

        //确保迷宫有起、终点
        if (maze.getStart() == null || maze.getEnd() == null) {
            throw new RuntimeException(Information.missingStartAndEndPoints);
        }

        final int[][] mazeData = maze.getMazeOriginalData();

        //深拷贝 防止修改迷宫原始数据
        int[][] stepData = getClone(int[].class, maze.getMazeOriginalData());

        //以迷宫的起点为开始对迷宫进行广度优先搜索
        Queue<Point> queue = new LinkedList(Collections.singleton(maze.getStart()));
        Integer value;

        while (true) {
            //获取当前点并将其移除队列中
            Point currentPoint = queue.poll();

            //遍历到队列中没有点的数据
            if (currentPoint == null) {
                break;
            }

            //尝试向四个方向移动
            for (Point dirPoint : BaseHolder.getBean("dirPoints", DirPoints.class).getDirPoints()) {
                Point nextPoint = currentPoint.add(dirPoint);

                value = getPointValueByMaze(nextPoint, mazeData);
                //判断不能走
                if (value == null || value == -1) {
                    continue;
                }
                //判断已经走过
                value = getPointValueByMaze(nextPoint, stepData);
                if (value != 0) {
                    continue;
                }
                //判断回到起点
                if (nextPoint.equals(maze.getStart())) {
                    continue;
                }

                //下一个点的步数为当前点的步数加一
                stepData[nextPoint.getI()][nextPoint.getJ()] = getPointValueByMaze(currentPoint, stepData) + 1;
                //添加下一个点
                queue.offer(nextPoint);
            }
        }
        //设置迷宫经过广度优先搜索的步数数据
        maze.setMazeStepData(stepData);
    }

    /**
     * 获取迷宫文件的原始数据
     * 为迷宫对象增加迷宫文件原始数据
     *
     * @param maze 迷宫对象
     * @return 有异常返回null, 无异常返回false
     */
    @Override
    public Boolean getMazeOriginalData(Maze maze) {
        int[][] mazeOriginalData;

        //如果迷宫的文件为空，则设置默认的文件位置
        if (maze.getMazeFilePath() == null) {
            maze.setMazeFilePath(getClass().getResource("/mazeData.maze").getPath());
        }

        //调用dao层获取迷宫文件数据
        try (var lines = Files.lines(new File(maze.getMazeFilePath()).toPath(), StandardCharsets.UTF_8)) {
            mazeOriginalData = mazeDao.getMazeData(lines);
        } catch (Exception e) {
            throw new RuntimeException(Information.readingMazeFileError);
        }

        //确保读取到迷宫文件存在数据
        if (mazeOriginalData == null) {
            throw new RuntimeException(Information.mazeFileIsEmpty);
        }

        //检测迷宫数据的合法性
        checkMazeDataValidity(mazeOriginalData);

        //设置迷宫对象的数据
        maze.setMazeOriginalData(mazeOriginalData);
        maze.setMazeRow(mazeOriginalData.length);
        maze.setMazeColumn(mazeOriginalData[0].length);
        return false;
    }

    /**
     * 检测迷宫原始数据的合法性
     *
     * @param mazeOriginalData 迷宫原始数据
     */
    private void checkMazeDataValidity(int[][] mazeOriginalData) {
        for (int i = 0, size = mazeOriginalData[0].length; i < mazeOriginalData.length; i++) {
            //确保迷宫数据是一个矩形
            if (mazeOriginalData[i].length != size) {
                throw new RuntimeException(Information.mazeSizeError);
            }
            for (int number : mazeOriginalData[i]) {
                //确保数据中只存在0和-1
                if (number != 0 && number != -1) {
                    throw new RuntimeException(Information.mazeDataError);
                }
            }
        }
    }

    /**
     * 获取给定点在迷宫对应位置的步数
     *
     * @param point 点
     * @param grid  迷宫数据
     * @return 成功返回迷宫数据对应位置的值，失败返回null
     */
    private Integer getPointValueByMaze(Point point, int[][] grid) {
        if (point.getI() < 0 || point.getI() >= grid.length) {
            return null;
        }
        if (point.getJ() < 0 || point.getJ() >= grid[0].length) {
            return null;
        }
        return grid[point.getI()][point.getJ()];
    }
}
