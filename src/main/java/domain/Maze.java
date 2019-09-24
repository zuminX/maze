package domain;

/**
 * 迷宫对象
 */
public class Maze {
    /**
     * 迷宫原始数据
     */
    private int[][] mazeOriginalData;
    /**
     * 迷宫步数数据
     */
    private int[][] mazeStepData;
    /**
     * 组成迷宫路径的点
     */
    private Point[] mazePathPoints;
    /**
     * 迷宫文件路径
     */
    private String mazeFilePath;
    /**
     * 迷宫的行
     */
    private int mazeRow;
    /**
     * 迷宫的列
     */
    private int mazeColumn;
    /**
     * 迷宫的起点
     */
    private Point start;
    /**
     * 迷宫的终点
     */
    private Point end;

    /**
     * Maze的无参构造方法
     */
    public Maze() {
        mazeFilePath = Maze.class.getResource("/mazeData.maze").getPath();
    }

    /**
     * 判断此位置是否为墙
     *
     * @param row    行
     * @param column 列
     * @return 是墙->true 不是墙->false
     */
    public boolean isWall(int row, int column) {
        return mazeOriginalData[row][column] == -1;
    }

    public int[][] getMazeOriginalData() {
        return mazeOriginalData;
    }

    public void setMazeOriginalData(int[][] mazeOriginalData) {
        this.mazeOriginalData = mazeOriginalData;
    }

    public int[][] getMazeStepData() {
        return mazeStepData;
    }

    public void setMazeStepData(int[][] mazeStepData) {
        this.mazeStepData = mazeStepData;
    }

    public Point[] getMazePathPoints() {
        return mazePathPoints;
    }

    public void setMazePathPoints(Point[] mazePathPoints) {
        this.mazePathPoints = mazePathPoints;
    }

    public String getMazeFilePath() {
        return mazeFilePath;
    }

    public void setMazeFilePath(String mazeFilePath) {
        this.mazeFilePath = mazeFilePath;
    }

    public int getMazeRow() {
        return mazeRow;
    }

    public void setMazeRow(int mazeRow) {
        this.mazeRow = mazeRow;
    }

    public int getMazeColumn() {
        return mazeColumn;
    }

    public void setMazeColumn(int mazeColumn) {
        this.mazeColumn = mazeColumn;
    }

    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        this.end = end;
    }
}
