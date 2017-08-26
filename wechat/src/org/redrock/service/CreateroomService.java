package org.redrock.service;

import com.google.gson.Gson;
import com.sun.org.apache.regexp.internal.RE;
import org.redrock.Model.Room;
import org.redrock.util.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class CreateroomService {
    public Gson gson = new Gson();
    public DBConnection dbConnection = new DBConnection();

    //随机换词
//    public void randomwords(String toUser) throws SQLException {
//        int newwordId = randomInt(1, 216);
//        dbConnection = new DBConnection();
//        String updatewords = "UPDATE room SET word_id = "+newwordId+" WHERE judge = "+toUser;
//        System.out.println(updatewords);
//        dbConnection.excuteSqlWithoutResult(updatewords);
//        dbConnection = new DBConnection();
//        String getnewwords = "SELECT word_one, word_two FROM words WHERE id = '" + newwordId + "'";
//        ResultSet wordQueryResult = dbConnection.getConnection().createStatement().executeQuery(getnewwords);
//        String word_one = "";
//        String word_two = "";
//        while (wordQueryResult.next()) {
//            word_one = wordQueryResult.getString("word_one");
//            word_two = wordQueryResult.getString("word_two");
//        }
//
//    }


    //自定义词
//    public void definewords(String word_one, String word_two) throws SQLException {
//        String insertwords = "INSERT INTO words (word_one, word_two) VALUES (" + word_one + "," + word_two + ")";
//        System.out.println(insertwords);
//        dbConnection.excuteSqlWithoutResult(insertwords);
//    }


/*
    public String create(int max, String toUser) throws SQLException {
        String responseforjudge = "";
        int roomOpenId = randomInt(1001, 9999);
        int wordId = randomInt(1, 216);
        int undercover = 0;
        int undercover1 = 0;
        int undercover2 = 0;
        int undercover3 = 0;
        int[] undercovers = randomArray(1, max, undercover);
        if (max >= 4 && max <= 6) {
            undercover = 1;
            undercover1 = undercovers[0];
        }
        if (max >= 7 && max <= 11) {
            undercover = 2;
            undercover1 = undercovers[0];
            undercover2 = undercovers[1];
        }
        if (max >= 12 && max <= 13) {
            undercover = 3;
            undercover1 = undercovers[0];
            undercover2 = undercovers[1];
            undercover3 = undercovers[2];
        }


        dbConnection = new DBConnection();
        String ifjudge = "SELECT judge FROM room";
        ResultSet judgeResult = dbConnection.getConnection().createStatement().executeQuery(ifjudge);
        String judge = "";
        while (judgeResult.next()) {
            judge = judgeResult.getString("judge");
        }
        if (judge.equals(toUser)) {
            System.out.println("您已创建房间");
        } else {
            //String selectSql = "SELECT word_one, word_two FROM words WHERE id = '" + wordId + "'";
            String insertSql = "INSERT INTO room (people_max, word_id, room_id, judge, undercover) VALUES (" + max + "," + wordId + ", " + roomOpenId + ", " + toUser + ", " + undercover + ")";
            System.out.println(insertSql);
            dbConnection.excuteSqlWithoutResult(insertSql);
        }
        dbConnection = new DBConnection();
        String getSql = "SELECT * FROM room WHERE room_id='" + roomOpenId + "'";
        String getSql2 = "SELECT word_one, word_two FROM words WHERE id = '" + wordId + "'";
        ResultSet wordQueryResult = dbConnection.getConnection().createStatement().executeQuery(getSql2);
        ResultSet rs = dbConnection.getConnection().createStatement().executeQuery(getSql);
        String word_one = "";
        String word_two = "";
        while (wordQueryResult.next()) {
            word_one = wordQueryResult.getString("word_one");
            word_two = wordQueryResult.getString("word_two");
        }
        System.out.println(rs.next());
        try {
            rs.first();
            System.out.println(rs.getRow());
            Room room = new Room(
                    Integer.parseInt(rs.getString("open_id")),
                    Integer.parseInt(rs.getString("people_max")),
                    Integer.parseInt(rs.getString("people_now")) + 1,
                    word_one,
                    word_two
            );
            System.out.println(gson.toJson(room));
            return gson.toJson(room);
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        responseforjudge = "你是法官;房号：" + roomOpenId + ";平民词:" + word_two + ";卧底词:" + word_one + "配置：" + undercover + "个卧底，" + (max - undercover) + "个平民";
//        return responseforjudge;
        return null;
    }

*/
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

    public int create(int max,String createrId) throws SQLException{
        DBConnection dbConnection=new DBConnection();
        int wordId=randomInt(1,216);
        int undercover=0;
        int[] undercovers=randomArray(1,max,3);
        ResultSet roomIdQueryResult=dbConnection.getConnection().createStatement().executeQuery("SELECT `room_id` FROM `room` ORDER BY `room_id` ASC ");
        int roomId=0;
        while (roomIdQueryResult.next())
            roomId=roomIdQueryResult.getInt("room_id")+1;
        if (max >= 4 && max <= 6) {
            undercover = 1;
            undercovers[1]=0;
            undercovers[2]=0;
        }
        if (max >= 7 && max <= 11) {
            undercover = 2;
            undercovers[2]=0;
        }
        if (max >= 12 && max <= 13) undercover = 3;
        PreparedStatement preparedStatement=dbConnection.getConnection().prepareStatement("INSERT INTO `room` " +
                "(`people_max`,`undercover`,`word_id`,`room_id`,`judge`,`undercover1`,`undercover2`,`undercover3`) VALUES (?,?,?,?,?,?,?,?)");
        preparedStatement.setInt(1,max);
        preparedStatement.setInt(2,undercover);
        preparedStatement.setInt(3,wordId);
        preparedStatement.setInt(4,roomId);
        preparedStatement.setString(5,createrId);
        preparedStatement.setInt(6,undercovers[0]);
        preparedStatement.setInt(7,undercovers[1]);
        preparedStatement.setInt(8,undercovers[2]);
        preparedStatement.addBatch();
        preparedStatement.executeBatch();
        preparedStatement.clearBatch();
        return roomId;
    }
//
//
//    public static void main(String[] args) throws SQLException {
//        CreateroomService createroomService = new CreateroomService();
//
//        System.out.println(createroomService.create(10, "wx9825405b418c4a79"));
//    }
}
