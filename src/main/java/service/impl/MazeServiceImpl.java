package service.impl;

import dao.MazeDao;
import dao.impl.MazeDaoImpl;
import domain.Maze;
import domain.Point;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.MazeService;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import static utils.PublicUtils.getClone;
import static utils.PublicUtils.stackToArray;

/**
 * 迷宫业务层
 * 接收控制层controller的数据
 * 调用dao层获得数据
 * 返回数据给controller层
 */
public class MazeServiceImpl implements MazeService {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MazeServiceImpl.class);

    /**
     * 四个方向的点
     */
    private static final Point[] dirPoints;

    static {
        dirPoints = new Point[4];
        dirPoints[0] = new Point(-1, 0);
        dirPoints[1] = new Point(0, -1);
        dirPoints[2] = new Point(1, 0);
        dirPoints[3] = new Point(0, 1);
    }

    /**
     * dao层对象
     */
    private MazeDao mazeDao = new MazeDaoImpl();

    /**
     * 对进行广度优先搜索后的迷宫数据进行逆向处理
     * 即尝试从终点回到起点
     * 为迷宫对象增加组成最短路径所有点的数据
     *
     * @param maze 迷宫对象
     * @return 异常信息
     */
    @Override
    public String getPath(Maze maze) {
        final Point start = maze.getStart();
        final Point end = maze.getEnd();
        if (start == null || end == null) {
            return "缺少该迷宫的起点和终点";
        }

        //若有异常信息，则返回异常信息到控制层
        String err = getPathData(maze);
        if (err != null) {
            return err;
        }

        //若终点无步数数据，则该迷宫无法走出
        if (getPointValueByMaze(end, maze.getMazeStepData()) < 1) {
            return "该迷宫无法走出";
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
            for (Point dirPoint : dirPoints) {
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

        return null;
    }

    /**
     * 对迷宫进行广度优先搜索
     * 尝试从起点进行搜索到终点
     * 为迷宫对象增加从起点到尝试点步数的数据
     *
     * @param maze 迷宫对象
     * @return 异常信息
     */
    private String getPathData(Maze maze) {
        //确保迷宫原始数据存在
        if (maze.getMazeOriginalData() == null) {
            final String err = getMazeOriginalData(maze);
            if (err != null) {
                return err;
            }
        }

        //确保迷宫有起、终点
        if (maze.getStart() == null || maze.getEnd() == null) {
            return "缺少起点与终点的数据";
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
            for (Point dirPoint : dirPoints) {
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

        return null;
    }

    /**
     * 获取迷宫文件的原始数据
     * 为迷宫对象增加迷宫文件原始数据
     *
     * @param maze 迷宫对象
     * @return 异常信息
     */
    @Override
    public String getMazeOriginalData(Maze maze) {
        int[][] mazeOriginalData;

        //如果迷宫的文件为空，则设置默认的文件位置
        if (maze.getMazeFilePath() == null) {
            maze.setMazeFilePath(MazeDaoImpl.class.getResource("/mazeData.maze").getPath());
        }

        //调用dao层获取迷宫文件数据
        try (InputStream is = new FileInputStream(new File(maze.getMazeFilePath()));
             InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8); BufferedReader br = new BufferedReader(isr)) {
            mazeOriginalData = mazeDao.getMazeData(br);
        } catch (Exception e) {
            LOGGER.error("", e);
            return "读取迷宫文件错误";
        }

        if (mazeOriginalData == null) {
            return "迷宫文件为空";
        }

        //检测迷宫数据的合法性
        String x = checkMazeDataValidity(mazeOriginalData);
        if (x != null) {
            return x;
        }

        //设置迷宫数据
        maze.setMazeOriginalData(mazeOriginalData);
        maze.setMazeRow(mazeOriginalData.length);
        maze.setMazeColumn(mazeOriginalData[0].length);

        return null;
    }

    /**
     * 检测迷宫原始数据的合法性
     *
     * @param mazeOriginalData 迷宫原始数据
     * @return  异常信息
     */
    @Nullable
    private String checkMazeDataValidity(int[][] mazeOriginalData) {
        for (int i = 0, size = mazeOriginalData[0].length; i < mazeOriginalData.length; i++) {
            if (mazeOriginalData[i].length != size) {
                return "迷宫必须为一个矩形";
            }
            for (int number : mazeOriginalData[i]) {
                if (number != 0 && number != -1) {
                    return "迷宫文件中只能有代表路的0和代表墙的-1，其他内容均为非法";
                }
            }
        }
        return null;
    }

    /**
     * 获取给定点在迷宫对应位置的步数
     *
     * @param point 点
     * @param grid  迷宫数据
     * @return 成功->迷宫数据对应位置的值；失败->null
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
