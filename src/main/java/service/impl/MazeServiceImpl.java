package service.impl;

import dao.MazeDao;
import dao.impl.MazeDaoImpl;
import domain.Maze;
import domain.Point;
import service.MazeService;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static utils.PublicUtils.getClone;
import static utils.PublicUtils.stackToArray;

public class MazeServiceImpl implements MazeService {
    private MazeDao mazeDao = new MazeDaoImpl();

    private static Point[] dirPoints;

    static {
        dirPoints = new Point[4];
        dirPoints[0] = new Point(-1, 0);
        dirPoints[1] = new Point(0, -1);
        dirPoints[2] = new Point(1, 0);
        dirPoints[3] = new Point(0, 1);
    }

    /**
     * 对进行广度优先搜索后的迷宫数据进行逆向处理
     * 即尝试从终点回到起点
     * 为迷宫对象增加组成最短路径所有点的数据
     *
     * @return 异常信息
     */
    public String getPath(Maze maze) {
        final Point start = maze.getStart();
        final Point end = maze.getEnd();
        if (start == null || end == null) {
            return "缺少该迷宫的起点和终点";
        }

        String err = getPathData(maze);

        //若有异常信息，则返回异常信息到控制层
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
        while (!currentPoint.equals(start)) {
            Integer valueCurrent = getPointValueByMaze(currentPoint, maze.getMazeStepData());

            Integer valueNext;
            //尝试从四个方向寻找路线
            for (Point dirPoint : dirPoints) {
                Point nextPoint = currentPoint.add(dirPoint);
                valueNext = getPointValueByMaze(nextPoint, maze.getMazeStepData());

                if (valueNext == null) {
                    continue;
                }

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
    public String getPathData(Maze maze) {
        if (maze.getMazeOriginalData() == null) {
            final String err = getMazeOriginalData(maze);
            if (err != null) {
                return err;
            }
        }
        if (maze.getStart() == null || maze.getEnd() == null) {
            return "缺少起点与终点的数据";
        }

        final Integer[][] mazeData = maze.getMazeOriginalData();

        Integer[][] stepData = getClone(Integer[].class, maze.getMazeOriginalData());

        Integer value;
        Queue<Point> queue = new LinkedList(Collections.singleton(maze.getStart()));
        while (queue.size() > 0) {
            Point currentPoint = queue.poll();

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

                stepData[nextPoint.getI()][nextPoint.getJ()] = getPointValueByMaze(currentPoint, stepData) + 1;
                queue.offer(nextPoint);
            }
        }
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
    public String getMazeOriginalData(Maze maze) {
        Integer[][] mazeData;

        if (maze.getMazeFilePath() == null) {
            maze.setMazeFilePath(MazeDaoImpl.class.getResource("/mazeData.maze").getPath());
        }
        try (
                InputStream is = new FileInputStream(new File(maze.getMazeFilePath()));
                InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr)
        ) {
            mazeData = mazeDao.getMazeData(br);
        } catch (Exception e) {
            e.printStackTrace();
            return "读取迷宫文件错误";
        }

        if (mazeData == null) {
            return "迷宫文件为空";
        }

        for (int i = 0, size = mazeData[0].length; i < mazeData.length; i++) {
            if (mazeData[i].length != size) {
                return "迷宫必须为一个矩形";
            }
            for (Integer number : mazeData[i]) {
                if (number != 0 && number != -1) {
                    return "迷宫文件中只能有代表路的0和代表墙的-1，其他内容均为非法";
                }
            }
        }

        maze.setMazeOriginalData(mazeData);
        maze.setMazeRow(mazeData.length);
        maze.setMazeColumn(mazeData[0].length);

        return null;
    }

    /**
     * 获取给定点在迷宫对应位置的数据
     *
     * @param point 点
     * @param grid  迷宫数据
     * @return 成功->迷宫数据对应位置的值；失败->null
     */
    public Integer getPointValueByMaze(Point point, Integer[][] grid) {
        if (point.getI() < 0 || point.getI() >= grid.length) {
            return null;
        }
        if (point.getJ() < 0 || point.getJ() >= grid[0].length) {
            return null;
        }
        return grid[point.getI()][point.getJ()];
    }
}
