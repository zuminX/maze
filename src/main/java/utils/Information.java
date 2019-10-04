package utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 存放迷宫提示信息的类
 */
@Component
@PropertySource(value = "classpath:information.properties", encoding = "utf-8")
public class Information {
    public static String mazeHelpInformation;
    public static String missingStartAndEndPoints;
    public static String canTGetOut;
    public static String readingMazeFileError;
    public static String mazeFileIsEmpty;
    public static String mazeSizeError;
    public static String mazeDataError;
    public static String missingMazeData;
    public static String StartAndEndPointsOnlyOne;
    public static String showPathError;
    public static String selectMazeFile;
    public static String selectMazeFileNoMatch;
    public static String openMazeFileError;
    public static String loadingImageError;

    @Value("${information.help}")
    public void setMazeHelpInformation(String mazeHelpInformation) {
        Information.mazeHelpInformation = mazeHelpInformation;
    }

    @Value("${information.missingStartAndEndPoints}")
    public void setMissingStartAndEndPoints(String missingStartAndEndPoints) {
        Information.missingStartAndEndPoints = missingStartAndEndPoints;
    }

    @Value("${information.canTGetOut}")
    public void setCanTGetOut(String canTGetOut) {
        Information.canTGetOut = canTGetOut;
    }

    @Value("${information.readingMazeFileError}")
    public void setReadingMazeFileError(String readingMazeFileError) {
        Information.readingMazeFileError = readingMazeFileError;
    }

    @Value("${information.mazeFileIsEmpty}")
    public void setMazeFileIsEmpty(String mazeFileIsEmpty) {
        Information.mazeFileIsEmpty = mazeFileIsEmpty;
    }

    @Value("${information.mazeSizeError}")
    public void setMazeSizeError(String mazeSizeError) {
        Information.mazeSizeError = mazeSizeError;
    }

    @Value("${information.mazeDataError}")
    public void setMazeDataError(String mazeDataError) {
        Information.mazeDataError = mazeDataError;
    }

    @Value("${information.missingMazeData}")
    public void setMissingMazeData(String missingMazeData) {
        Information.missingMazeData = missingMazeData;
    }

    @Value("${information.StartAndEndPointsOnlyOne}")
    public void setStartAndEndPointsOnlyOne(String startAndEndPointsOnlyOne) {
        StartAndEndPointsOnlyOne = startAndEndPointsOnlyOne;
    }

    @Value("${information.showPathError}")
    public void setShowPathError(String showPathError) {
        Information.showPathError = showPathError;
    }

    @Value("${information.selectMazeFile}")
    public void setSelectMazeFile(String selectMazeFile) {
        Information.selectMazeFile = selectMazeFile;
    }

    @Value("${information.selectMazeFileNoMatch}")
    public void setSelectMazeFileNoMatch(String selectMazeFileNoMatch) {
        Information.selectMazeFileNoMatch = selectMazeFileNoMatch;
    }

    @Value("${information.openMazeFileError}")
    public void setOpenMazeFileError(String openMazeFileError) {
        Information.openMazeFileError = openMazeFileError;
    }

    @Value("${information.loadingImageError}")
    public void setLoadingImageError(String loadingImageError) {
        Information.loadingImageError = loadingImageError;
    }
}
