package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface MazeDao {
    Integer[][] getMazeData(BufferedReader br) throws Exception;
}
