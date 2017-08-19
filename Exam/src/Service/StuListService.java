package Service;

import Util.DBConnection;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StuListService {
    public void stulist(HttpServletRequest request) {

//        {
//                "page": 1, //返回第几页
//                "per_page": 10, //每页多少数据
//                "sortby": "stuId", //根据什么排序
//                "sort": "acs|desc", //升序|降序
//                "name": "王", //模糊查询
//                "stuId": "2013", //模糊查询
//        }

        int page = Integer.parseInt(request.getParameter("page"));
        int per_page = Integer.parseInt(request.getParameter("per_page"));
        String sortby = request.getParameter("sortby");
        String sort = request.getParameter("sort");
        String name = request.getParameter("name");
        String stuId = request.getParameter("stuId");

//        System.out.println(page);
//        System.out.println(per_page);
//        System.out.println(sortby);
//        System.out.println(sort);
//        System.out.println(name);
//        System.out.println(stuId);

        if (String.valueOf(page).equals("")) {
            page = 1;
        }
        if (String.valueOf(per_page).equals("")) {
            per_page = 10;
        }
        if (sortby.equals("")) {
            sortby = "stuId";
        }
        if (sort.equals("")) {
            sort = "acs";
        }
        if (name.equals("") || stuId.equals("")) {
            System.out.println("请输入关键词");
        }

        String sql = "SELECT * FROM student WHERE name LIKE '%" + name +
                "%' AND stuId LIKE '%" + stuId +
                "%' ORDER BY " + sortby + " " + sort +
                " LIMIT " + (page - 1) * per_page +
                "," + per_page;

        System.out.println(sql);
        DBConnection dbConnection1 = new DBConnection();
        ResultSet rs = dbConnection1.excuteSqlWithResult(sql);
        try {
            rs.first();
            while (rs.next()) {
                System.out.println(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

//    public static void main(String[] args) throws SQLException {
//        Connection connection = new DBConnection().getConnection();
//        Statement statement = connection.createStatement();
//        String name = "李静";
//        String sql = "SELECT * FROM student WHERE name = \"李静\" ";
//        ResultSet resultSet = statement.executeQuery(sql);
//        System.out.println(resultSet.next());
//        while (resultSet.next()) {
//            System.out.println(resultSet.getString("name"));
//        }
//    }
}
