package com.dao.impl;

import com.dao.MazeDao;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * 迷宫dao层
 * 读取文件数据
 * 返回数据给业务层
 */
@Repository("mazeDao")
public class MazeDaoImpl implements MazeDao {

    /**
     * 读取迷宫文件
     *
     * @param lines 文件数据的stream流
     * @return 迷宫文件数据
     */
    public int[][] getMazeData(Stream<String> lines) {
        //将流转化为List数组
        final List<int[]> list = lines.filter(s -> !StringUtils.isEmpty(s))
                .map(line -> Arrays.stream(line.split("\\s+")).mapToInt(Integer::valueOf).toArray())
                .collect(toList());

        //将List数组转化为一个二维数组返回
        int[][] mazeData = new int[list.size()][];
        for (int i = 0; i < mazeData.length; i++) {
            mazeData[i] = new int[list.get(i).length];
        }
        list.toArray(mazeData);

        return mazeData;
    }
}
