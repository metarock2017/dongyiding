package org.redrock.service;

import org.apache.avro.generic.GenericData;
import org.redrock.util.DBConnection;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class JoinroomService {
    public DBConnection dbConnection = new DBConnection();
    String responseInformation = "";


//    public String join(String toUser,int roomId) throws SQLException {
//
//        //获取总人数 卧底数 词 房号 卧底号
//        ResultSet roomCondition = dbConnection.getConnection().createStatement().executeQuery("SELECT * FROM `room` WHERE `room_id`=" + roomId);
//        Map roomConditionMap = new HashMap();
//        while (roomCondition.next()) {
//            roomConditionMap.put("sum_nums", roomCondition.getInt("people_max"));
//            roomConditionMap.put("undercover_nums", roomCondition.getInt("undercover"));
//            roomConditionMap.put("word_id", roomCondition.getInt("word_id"));
//            roomConditionMap.put("room_id", roomCondition.getInt("room_id"));
//            roomConditionMap.put("undercover1", roomCondition.getInt("undercover1"));
//            roomConditionMap.put("undercover2", roomCondition.getInt("undercover2"));
//            roomConditionMap.put("undercover3", roomCondition.getInt("undercover3"));
//        }
//        ResultSet wordSet = dbConnection.getConnection().createStatement().executeQuery("SELECT `word_one`,`word_two` FROM `words` WHERE `id`=" + roomConditionMap.get("word_id"));
//        String[] word = new String[2];
//        while (wordSet.next()) {
//            word[0] = wordSet.getString("word_one");
//            word[1] = wordSet.getString("word_two");
//        }
//
//
//        //发号码
//        DBConnection connection = new DBConnection();
//        int characterId = 0;
//        ResultSet resultSet = connection.excuteSqlWithResult("select count(id) as character_id from player where room_id=" + roomId + " and character_id >0");
//        while (resultSet.next()) {
//            characterId = resultSet.getInt("character_id") + 1;
//        }
//
//
//        //判断是否已在房内 判断是否为法官 判断房间人数有没有满
//        //TODO...
//        dbConnection = new DBConnection();
//        String playersql2 = "SELECT wechat_id FROM player WHERE room_id='" + roomId + "'";
//        System.out.println(roomId);
//        ResultSet rs2 = dbConnection.getConnection().createStatement().executeQuery(playersql2);
//        String result = "";
//        boolean check = false;
//        while (rs2.next()) {
//            result = rs2.getString("wechat_id");
//            if (result.equals(toUser))
//                check = true;
//            else {
//                check = false;
//            }
//        }
//        System.out.println(check);
//        if (check) {
//            responseInformation = "正在游戏中， 请返回游戏房间";
//        } else if (roomConditionMap.get("sum_nums").equals(characterId) || characterId <= 0) {
//            responseInformation = "房间已满";
//        }
//
//
//
//
//        /*DBConnection connection = new DBConnection();
//        int characterId = 0;
//        ResultSet resultSet = connection.excuteSqlWithResult("select count(id) as character_id from player where room_id=" + roomId + " and character_id >0");
//        while (resultSet.next()) {
//            characterId = resultSet.getInt("character_id") + 1;
//        }*/
//
//        //玩家表中插入 微信号 号码 房间号
//        PreparedStatement preparedStatement = connection.getConnection().prepareStatement("INSERT INTO `player` (`wechat_id`,`character_id`,`room_id`) VALUES (?,?,?)");
//        preparedStatement.setString(1, toUser);
//        preparedStatement.setInt(2, characterId);
//        preparedStatement.setInt(3, roomId);
//        preparedStatement.addBatch();
//        preparedStatement.executeBatch();
//        preparedStatement.clearBatch();
//        /*ResultSet roomCondition = dbConnection.getConnection().createStatement().executeQuery("SELECT * FROM `room` WHERE `room_id`=" + roomId);
//        Map roomConditionMap = new HashMap();
//        while (roomCondition.next()) {
//            roomConditionMap.put("sum_nums", roomCondition.getInt("people_max"));
//            roomConditionMap.put("undercover_nums", roomCondition.getInt("undercover"));
//            roomConditionMap.put("word_id", roomCondition.getInt("word_id"));
//            roomConditionMap.put("room_id", roomCondition.getInt("room_id"));
//            roomConditionMap.put("undercover1", roomCondition.getInt("undercover1"));
//            roomConditionMap.put("undercover2", roomCondition.getInt("undercover2"));
//            roomConditionMap.put("undercover3", roomCondition.getInt("undercover3"));
//        }
//        ResultSet wordSet = dbConnection.getConnection().createStatement().executeQuery("SELECT `word_one`,`word_two` FROM `words` WHERE `id`=" + roomConditionMap.get("word_id"));
//        String[] word = new String[2];
//        while (wordSet.next()) {
//            word[0] = wordSet.getString("word_one");
//            word[1] = wordSet.getString("word_two");
//        }*/
//        responseInformation = "";
//            if (roomConditionMap.get("undercover1").equals(characterId) || roomConditionMap.get("undercover2").equals(characterId) || roomConditionMap.get("undercover3").equals(characterId)) {
//                responseInformation = "房号：" + roomConditionMap.get("room_id") + ";\n词语:" + word[0] + ";\n你是:" + characterId + "号;\n配置：" + roomConditionMap.get("undercover_nums") + "个卧底，总人数" + roomConditionMap.get("sum_nums");
//            } else {
//                responseInformation = "房号：" + roomConditionMap.get("room_id") + ";\n词语:" + word[1] + ";\n你是:" + characterId + "号;\n配置：" + roomConditionMap.get("undercover_nums") + "个卧底，总人数" + roomConditionMap.get("sum_nums");
//            }
//        return responseInformation;
//    }


    public String join(String toUser, int roomId) throws SQLException {
        dbConnection = new DBConnection();
        int nums = 0;
        ResultSet resultSet = dbConnection.excuteSqlWithResult("select count(id) as character_id from player where room_id=" + roomId + " and character_id >0");
        if (!resultSet.next())
            return "这是一个无效的房间";
        ResultSet roomCondition = dbConnection.getConnection().createStatement().executeQuery("SELECT * FROM `room` WHERE `room_id`=" + roomId);
        Map roomConditionMap = new HashMap();
        while (roomCondition.next()) {
            roomConditionMap.put("sum_nums", roomCondition.getInt("people_max"));
            roomConditionMap.put("undercover_nums", roomCondition.getInt("undercover"));
            roomConditionMap.put("word_id", roomCondition.getInt("word_id"));
            roomConditionMap.put("room_id", roomCondition.getInt("room_id"));
            roomConditionMap.put("judge", roomCondition.getString("judge"));
            roomConditionMap.put("undercover1", roomCondition.getInt("undercover1"));
            roomConditionMap.put("undercover2", roomCondition.getInt("undercover2"));
            roomConditionMap.put("undercover3", roomCondition.getInt("undercover3"));
            nums = roomCondition.getInt("people_max");
        }

        //防止法官加入游戏
        if (roomConditionMap.get("judge").equals(toUser)) {
            ResultSet wordSet = dbConnection.getConnection().createStatement().executeQuery("SELECT `word_one`,`word_two` FROM `words` WHERE `id`=" + roomConditionMap.get("word_id"));
            String[] word = new String[2];
            while (wordSet.next()) {
                word[0] = wordSet.getString("word_one");
                word[1] = wordSet.getString("word_two");
            }
            responseInformation = "";
            if (nums > 11)
                responseInformation = "你是:法官\n房号：" + roomConditionMap.get("room_id") + "\n平民词：" + word[0] + "\n卧底词：" + word[1] + "\n配置：" + roomConditionMap.get("undercover_nums") + "个卧底," +
                        "总人数" + roomConditionMap.get("sum_nums") + "人\n卧底是：" + roomConditionMap.get("undercover1") + "号," + roomConditionMap.get("undercover2") + "号," + roomConditionMap.get("undercover3") + "号";
            else if (nums > 6)
                responseInformation = "你是:法官\n房号：" + roomConditionMap.get("room_id") + "\n平民词：" + word[0] + "\n卧底词：" + word[1] + "\n配置：" + roomConditionMap.get("undercover_nums") + "个卧底," +
                        "总人数" + roomConditionMap.get("sum_nums") + "人\n卧底是：" + roomConditionMap.get("undercover1") + "号," + roomConditionMap.get("undercover2") + "号";
            else
                responseInformation = "你是:法官\n房号：" + roomConditionMap.get("room_id") + "\n平民词：" + word[0] + "\n卧底词：" + word[1] + "\n配置：" + roomConditionMap.get("undercover_nums") + "个卧底," +
                        "总人数" + roomConditionMap.get("sum_nums") + "人\n卧底是：" + roomConditionMap.get("undercover1") + "号";

        } else {
            int characterId = 0;
            String playersql2 = "SELECT `character_id` FROM `player` WHERE `room_id`=" + roomId + " AND `wechat_id`='" + toUser + "'";
            ResultSet rs2 = dbConnection.getConnection().createStatement().executeQuery(playersql2);
            if (!rs2.next()) {
                String sql = "SELECT `people_max` FROM `room` WHERE `room_id`=" + roomId;
                ResultSet sumNum = dbConnection.excuteSqlWithResult(sql);
                ResultSet nowNum = dbConnection.excuteSqlWithResult("SELECT count(`id`) AS `num` FROM `player` WHERE `room_id`=" + roomId + " AND `character_id`>0");
                int sum = 0, now = 0;
                while (sumNum.next())
                    sum = sumNum.getInt("people_max");
                while (nowNum.next())
                    now = nowNum.getInt("num");
                if (now >= sum)
                    return "房间人数已满";


                ResultSet characterIDSet = dbConnection.excuteSqlWithResult("SELECT `character_id` FROM `player` WHERE `room_id`=" + roomId + " ORDER BY `character_id` ASC");
                while (characterIDSet.next())
                    characterId = characterIDSet.getInt("character_id") + 1;
                ResultSet wordSet = dbConnection.getConnection().createStatement().executeQuery("SELECT `word_one`,`word_two` FROM `words` WHERE `id`=" + roomConditionMap.get("word_id"));
                String[] word = new String[2];
                while (wordSet.next()) {
                    word[0] = wordSet.getString("word_one");
                    word[1] = wordSet.getString("word_two");
                }
                PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement("INSERT INTO `player` (`wechat_id`,`character_id`,`room_id`) VALUES (?,?,?)");
                preparedStatement.setString(1, toUser);
                preparedStatement.setInt(2, characterId);
                preparedStatement.setInt(3, roomId);
                preparedStatement.addBatch();
                preparedStatement.executeBatch();
                preparedStatement.clearBatch();
                if (roomConditionMap.get("undercover1").equals(characterId) || roomConditionMap.get("undercover2").equals(characterId) || roomConditionMap.get("undercover3").equals(characterId)) {
                    return  "房号：" + roomConditionMap.get("room_id") + ";\n词语:" + word[0] + ";\n你是:" + characterId + "号;\n配置：" + roomConditionMap.get("undercover_nums") + "个卧底，总人数" + roomConditionMap.get("sum_nums");
                } else {
                    return  "房号：" + roomConditionMap.get("room_id") + ";\n词语:" + word[1] + ";\n你是:" + characterId + "号;\n配置：" + roomConditionMap.get("undercover_nums") + "个卧底，总人数" + roomConditionMap.get("sum_nums");
                }
            } else {
//                while (rs2.next())
                    characterId=rs2.getInt("character_id");
//                System.out.println(characterId);
//                System.out.println(rs2.next());
                ResultSet wordSet = dbConnection.getConnection().createStatement().executeQuery("SELECT `word_one`,`word_two` FROM `words` WHERE `id`=" + roomConditionMap.get("word_id"));
                String[] word = new String[2];
                while (wordSet.next()) {
                    word[0] = wordSet.getString("word_one");
                    word[1] = wordSet.getString("word_two");
                }
                if (roomConditionMap.get("undercover1").equals(characterId) || roomConditionMap.get("undercover2").equals(characterId) || roomConditionMap.get("undercover3").equals(characterId)) {
                    responseInformation = "房号：" + roomConditionMap.get("room_id") + ";\n词语:" + word[0] + ";\n你是:" + characterId + "号;\n配置：" + roomConditionMap.get("undercover_nums") + "个卧底，总人数" + roomConditionMap.get("sum_nums");
                } else {
                    responseInformation = "房号：" + roomConditionMap.get("room_id") + ";\n词语:" + word[1] + ";\n你是:" + characterId + "号;\n配置：" + roomConditionMap.get("undercover_nums") + "个卧底，总人数" + roomConditionMap.get("sum_nums");
                }
            }
//            while (rs2.next()) {
//                result = rs2.getString("wechat_id");
//                if (result.equals(toUser)) {
//                    check = true;
//                } else {
//                    check = false;
//                }
//                System.out.println(check);
//                if (check) {
//                    responseInformation = "正在游戏中， 不能重复获取身份";
//                } else {
//                    if (roomConditionMap.get("sum_nums").equals(characterId) || characterId <= 0) {
//                        responseInformation = "房间已满";
//                    } else {
//
//                        ResultSet wordSet = dbConnection.getConnection().createStatement().executeQuery("SELECT `word_one`,`word_two` FROM `words` WHERE `id`=" + roomConditionMap.get("word_id"));
//                        String[] word = new String[2];
//                        while (wordSet.next()) {
//                            word[0] = wordSet.getString("word_one");
//                            word[1] = wordSet.getString("word_two");
//                        }
//
//                        PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement("INSERT INTO `player` (`wechat_id`,`character_id`,`room_id`) VALUES (?,?,?)");
//                        preparedStatement.setString(1, toUser);
//                        preparedStatement.setInt(2, characterId);
//                        preparedStatement.setInt(3, roomId);
//                        preparedStatement.addBatch();
//                        preparedStatement.executeBatch();
//                        preparedStatement.clearBatch();
//
//                        responseInformation = "";
//                        if (roomConditionMap.get("undercover1").equals(characterId) || roomConditionMap.get("undercover2").equals(characterId) || roomConditionMap.get("undercover3").equals(characterId)) {
//                            responseInformation = "房号：" + roomConditionMap.get("room_id") + ";\n词语:" + word[0] + ";\n你是:" + characterId + "号;\n配置：" + roomConditionMap.get("undercover_nums") + "个卧底，总人数" + roomConditionMap.get("sum_nums");
//                        } else {
//                            responseInformation = "房号：" + roomConditionMap.get("room_id") + ";\n词语:" + word[1] + ";\n你是:" + characterId + "号;\n配置：" + roomConditionMap.get("undercover_nums") + "个卧底，总人数" + roomConditionMap.get("sum_nums");
//                        }
//                    }
//                }
//
//
//            }
        }
        return responseInformation;
    }


    public String join(String toUser, int roomId, int characterId) throws SQLException {
        DBConnection connection = new DBConnection();
        PreparedStatement preparedStatement = connection.getConnection().prepareStatement("INSERT INTO `player` (`wechat_id`,`character_id`,`room_id`) VALUES (?,?,?)");
        preparedStatement.setString(1, toUser);
        preparedStatement.setInt(2, characterId);
        preparedStatement.setInt(3, roomId);
        preparedStatement.addBatch();
        preparedStatement.executeBatch();
        preparedStatement.clearBatch();
        ResultSet roomCondition = dbConnection.getConnection().createStatement().executeQuery("SELECT * FROM `room` WHERE `room_id`=" + roomId);
        Map roomConditionMap = new HashMap();
        int nums = 0;
        while (roomCondition.next()) {
            roomConditionMap.put("sum_nums", roomCondition.getInt("people_max"));
            roomConditionMap.put("undercover_nums", roomCondition.getInt("undercover"));
            roomConditionMap.put("word_id", roomCondition.getInt("word_id"));
            roomConditionMap.put("room_id", roomCondition.getInt("room_id"));
            roomConditionMap.put("undercover1", roomCondition.getInt("undercover1"));
            roomConditionMap.put("undercover2", roomCondition.getInt("undercover2"));
            roomConditionMap.put("undercover3", roomCondition.getInt("undercover3"));
            nums = roomCondition.getInt("people_max");
        }
        ResultSet wordSet = dbConnection.getConnection().createStatement().executeQuery("SELECT `word_one`,`word_two` FROM `words` WHERE `id`=" + roomConditionMap.get("word_id"));
        String[] word = new String[2];
        while (wordSet.next()) {
            word[0] = wordSet.getString("word_one");
            word[1] = wordSet.getString("word_two");
        }
        String responseInformation = "";
        if (nums > 11)
            responseInformation = "你是:法官\n房号：" + roomConditionMap.get("room_id") + "\n平民词：" + word[0] + "\n卧底词：" + word[1] + "\n配置：" + roomConditionMap.get("undercover_nums") + "个卧底," +
                    "总人数" + roomConditionMap.get("sum_nums") + "人\n卧底是：" + roomConditionMap.get("undercover1") + "号," + roomConditionMap.get("undercover2") + "号," + roomConditionMap.get("undercover3") + "号";
        else if (nums > 6)
            responseInformation = "你是:法官\n房号：" + roomConditionMap.get("room_id") + "\n平民词：" + word[0] + "\n卧底词：" + word[1] + "\n配置：" + roomConditionMap.get("undercover_nums") + "个卧底," +
                    "总人数" + roomConditionMap.get("sum_nums") + "人\n卧底是：" + roomConditionMap.get("undercover1") + "号," + roomConditionMap.get("undercover2") + "号";
        else
            responseInformation = "你是:法官\n房号：" + roomConditionMap.get("room_id") + "\n平民词：" + word[0] + "\n卧底词：" + word[1] + "\n配置：" + roomConditionMap.get("undercover_nums") + "个卧底," +
                    "总人数" + roomConditionMap.get("sum_nums") + "人\n卧底是：" + roomConditionMap.get("undercover1") + "号";
        return responseInformation;
    }
}
