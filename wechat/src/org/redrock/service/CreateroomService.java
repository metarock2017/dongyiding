package org.redrock.service;

import com.google.gson.Gson;
import org.redrock.util.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class CreateroomService {
    public Gson gson = new Gson();
    public DBConnection dbConnection = new DBConnection();

    public int randomInt(int min, int max) {
        Random random = new Random(System.currentTimeMillis() / 1000);
        int randomInt = random.nextInt(max) + min;
        return randomInt;
    }

    public static int[] randomArray(int min, int max, int n) {
        int len = max - min + 1;

        if (max < min || n > len) {
            return null;
        }

        //初始化给定范围的待选数组
        int[] source = new int[len];
        for (int i = min; i < min + len; i++) {
            source[i - min] = i;
        }

        int[] result = new int[n];
        Random rd = new Random();
        int index = 0;
        for (int i = 0; i < result.length; i++) {
            //待选数组0到(len-2)随机一个下标
            index = Math.abs(rd.nextInt() % len--);
            //将随机到的数放入结果集
            result[i] = source[index];
            //将待选数组中被随机到的数，用待选数组(len-1)下标对应的数替换
            source[index] = source[len];
        }
        return result;
    }

    public int create(int max, String createrId) throws SQLException {
        DBConnection dbConnection = new DBConnection();
        int wordId = randomInt(1, 216);
        int undercover = 0;
        int[] undercovers = randomArray(1, max, 3);
        ResultSet roomIdQueryResult = dbConnection.getConnection().createStatement().executeQuery("SELECT `room_id` FROM `room` ORDER BY `room_id` ASC ");
        int roomId = 0;
        while (roomIdQueryResult.next())
            roomId = roomIdQueryResult.getInt("room_id") + 1;
        if (max >= 4 && max <= 6) {
            undercover = 1;
            undercovers[1] = 0;
            undercovers[2] = 0;
        }
        if (max >= 7 && max <= 11) {
            undercover = 2;
            undercovers[2] = 0;
        }
        if (max >= 12 && max <= 13) undercover = 3;
        PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement("INSERT INTO `room` " +
                "(`people_max`,`undercover`,`word_id`,`room_id`,`judge`,`undercover1`,`undercover2`,`undercover3`) VALUES (?,?,?,?,?,?,?,?)");
        preparedStatement.setInt(1, max);
        preparedStatement.setInt(2, undercover);
        preparedStatement.setInt(3, wordId);
        preparedStatement.setInt(4, roomId);
        preparedStatement.setString(5, createrId);
        preparedStatement.setInt(6, undercovers[0]);
        preparedStatement.setInt(7, undercovers[1]);
        preparedStatement.setInt(8, undercovers[2]);
        preparedStatement.addBatch();
        preparedStatement.executeBatch();
        preparedStatement.clearBatch();
        return roomId;
    }
}
