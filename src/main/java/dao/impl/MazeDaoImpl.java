package dao.impl;

import dao.MazeDao;

import java.io.BufferedReader;
import java.util.ArrayList;

/**
 * dao层
 * 读取数据
 * 返回给service层
 */
public class MazeDaoImpl implements MazeDao {

    /**
     * 读取迷宫文件
     *
     * @param br 带缓冲的字符输入流
     * @return 迷宫文件数据
     * @throws Exception
     */
    public int[][] getMazeData(BufferedReader br) throws Exception {
        String data;
        ArrayList<int[]> list = new ArrayList<int[]>();
        //读取迷宫文件的每一行
        while ((data = br.readLine()) != null) {
            //以空格为分隔符分割数据
            String[] rowDataStr = data.split("\\s+");
            int[] rowData = new int[rowDataStr.length];
            for (int i = 0; i < rowData.length; i++) {
                rowData[i] = Integer.parseInt(rowDataStr[i]);
            }
            list.add(rowData);
        }

        //将List数组转化为一个二维数组返回
        int[][] mazeData = new int[list.size()][];
        for (int i = 0; i < mazeData.length; i++) {
            mazeData[i] = new int[list.get(i).length];
        }
        list.toArray(mazeData);

        return mazeData;
    }
}
