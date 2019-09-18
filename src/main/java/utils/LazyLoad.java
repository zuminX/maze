package utils;

import domain.Maze;
import org.jetbrains.annotations.NotNull;

public class LazyLoad {
    private String filePath;
    private Long fileChangeTime;

    public Boolean canLazyLoad(@NotNull String nowFilePath, @NotNull Long nowFileChangeTime) {
        return nowFilePath.equals(filePath) && nowFileChangeTime.equals(fileChangeTime);
    }

    public Boolean hasFindMazePath(@NotNull Maze maze) {
       return maze.getMazePathPoints()!=null;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getFileChangeTime() {
        return fileChangeTime;
    }

    public void setFileChangeTime(Long fileChangeTime) {
        this.fileChangeTime = fileChangeTime;
    }
}
