package domain;

public class Maze {
    private Integer[][] mazeOriginalData;
    private Integer[][] mazeStepData;
    private Point[] mazePathPoints;
    private String mazeFilePath;
    private Integer mazeRow;
    private Integer mazeColumn;
    private Point start;
    private Point end;

    public Maze() {
        mazeFilePath = Maze.class.getResource("/mazeData.maze").getPath();
    }

    public boolean isWall(Integer row, Integer column) {
        return mazeOriginalData[row][column] == null || mazeOriginalData[row][column] == -1;
    }

    public Integer[][] getMazeOriginalData() {
        return mazeOriginalData;
    }

    public void setMazeOriginalData(Integer[][] mazeOriginalData) {
        this.mazeOriginalData = mazeOriginalData;
    }

    public Integer[][] getMazeStepData() {
        return mazeStepData;
    }

    public void setMazeStepData(Integer[][] mazeStepData) {
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

    public Integer getMazeRow() {
        return mazeRow;
    }

    public void setMazeRow(Integer mazeRow) {
        this.mazeRow = mazeRow;
    }

    public Integer getMazeColumn() {
        return mazeColumn;
    }

    public void setMazeColumn(Integer mazeColumn) {
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
