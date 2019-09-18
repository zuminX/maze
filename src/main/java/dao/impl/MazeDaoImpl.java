package dao.impl;

import dao.MazeDao;

import java.io.*;
import java.util.ArrayList;

import static utils.PublicUtils.listToTwoArray;

public class MazeDaoImpl implements MazeDao {

    public Integer[][] getMazeData(BufferedReader br) throws Exception {
        String data;
        ArrayList<Integer[]> list = new ArrayList<Integer[]>();
        while ((data = br.readLine()) != null) {
            String[] rowDataStr = data.split("\\s+");
            Integer[] rowData = new Integer[rowDataStr.length];
            for (int i = 0; i < rowData.length; i++) {
                rowData[i] = Integer.parseInt(rowDataStr[i]);
            }
            list.add(rowData);
        }
        return listToTwoArray(Integer[].class, list);
    }
}
