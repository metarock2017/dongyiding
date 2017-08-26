//package org.redrock.service;
//
//import org.redrock.util.DBConnection;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Random;
//
//public class ChangewordsService {
//    public String change(String toUser) throws SQLException {
//        DBConnection dbConnection = new DBConnection();
//        String newresponse = "";
//        int newwordId = randomInt(1, 216);
//
//        //房间换词前状态
//        ResultSet roomCondition = dbConnection.getConnection().createStatement().executeQuery("SELECT * FROM `room` WHERE `judge`=" + toUser);
//        Map roomConditionMap = new HashMap();
//        int nums = 0;
//        while (roomCondition.next()) {
//            roomConditionMap.put("sum_nums", roomCondition.getInt("people_max"));
//            roomConditionMap.put("undercover_nums", roomCondition.getInt("undercover"));
//            roomConditionMap.put("word_id", roomCondition.getInt("word_id"));
//            roomConditionMap.put("room_id", roomCondition.getInt("room_id"));
//            roomConditionMap.put("undercover1", roomCondition.getInt("undercover1"));
//            roomConditionMap.put("undercover2", roomCondition.getInt("undercover2"));
//            roomConditionMap.put("undercover3", roomCondition.getInt("undercover3"));
//            nums = roomCondition.getInt("people_max");
//        }
////        dbConnection = new DBConnection();
////        String updatewords = "UPDATE `room` SET `word_id` = " + newwordId + " WHERE `judge` = " + toUser;
////        System.out.println(updatewords);
////        dbConnection.excuteSqlWithoutResult(updatewords);
//
//        String getnewwords = "SELECT `word_one`, `word_two` FROM `words` WHERE `id` = " + newwordId;
//        ResultSet wordQueryResult = dbConnection.getConnection().createStatement().executeQuery(getnewwords);
//        String word_one = "";
//        String word_two = "";
//        while (wordQueryResult.next()) {
//            word_one = wordQueryResult.getString("word_one");
//            word_two = wordQueryResult.getString("word_two");
//        }
//
//
//        if (nums > 11) {
//            newresponse = "你是:法官\n房号：" + roomConditionMap.get("room_id") + "\n平民词：" + word_two + "\n卧底词：" + word_one + "\n配置：" + roomConditionMap.get("undercover_nums") + "个卧底," +
//                    "总人数" + roomConditionMap.get("sum_nums") + "人\n卧底是：" + roomConditionMap.get("undercover1") + "号," + roomConditionMap.get("undercover2") + "号," + roomConditionMap.get("undercover3") + "号";
//        } else if (nums > 6) {
//            newresponse = "你是:法官\n房号：" + roomConditionMap.get("room_id") + "\n平民词：" + word_two + "\n卧底词：" + word_one + "\n配置：" + roomConditionMap.get("undercover_nums") + "个卧底," +
//                    "总人数" + roomConditionMap.get("sum_nums") + "人\n卧底是：" + roomConditionMap.get("undercover1") + "号," + roomConditionMap.get("undercover2") + "号";
//        } else {
//            newresponse = "你是:法官\n房号：" + roomConditionMap.get("room_id") + "\n平民词：" + word_two + "\n卧底词：" + word_one + "\n配置：" + roomConditionMap.get("undercover_nums") + "个卧底," +
//                    "总人数" + roomConditionMap.get("sum_nums") + "人\n卧底是：" + roomConditionMap.get("undercover1") + "号";
//        }
//        return getnewwords;
//    }
//
//
//
//    public int randomInt(int min, int max) {
//        Random random = new Random(System.currentTimeMillis() / 1000);
//        int randomInt = random.nextInt(max) + min;
//        return randomInt;
//    }
//}
