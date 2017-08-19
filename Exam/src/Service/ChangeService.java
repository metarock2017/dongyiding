package Service;

import Util.DBConnection;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChangeService {
    public void change(HttpServletRequest request) {
        String username = request.getParameter("username");
        String question = request.getParameter("question");
        String newpassword = request.getParameter("newpassword");

        String sql1 = "SELECT question FROM users WHERE username= '" + username + "'";
        DBConnection dbConnection1 = new DBConnection();
        ResultSet rs1 = dbConnection1.excuteSqlWithResult(sql1);

        try {
            rs1.first();
            if (rs1.getString("question").equals(question)) {
                String sql2 = "UPDATE users SET password = '" + newpassword + "' WHERE username = '" + username + "'";
                DBConnection dbConnection2 = new DBConnection();
                boolean rs2 = dbConnection2.excuteSqlWithoutResult(sql2);
                if (rs2) {
                    System.out.println("更改密码成功");
                } else {
                    System.out.println("更改密码失败");
                }
            } else {
                System.out.println("密保问题错误");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
