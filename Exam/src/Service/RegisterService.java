package Service;


import Util.Base64;
import Util.DBConnection;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterService {

    public void register(HttpServletRequest request) {
        //获取用户名 密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String question = request.getParameter("question");
        //验证
        if (username == null || password == null || question ==null) {
            System.out.println("用户名或密码或密保问题不能为空");
        }
        else {
            String sql3 = "SELECT * FROM users WHERE username= '" + username + "'";
            DBConnection dbConnection3 = new DBConnection();
            ResultSet rs3 = dbConnection3.excuteSqlWithResult(sql3);
            try {
                rs3.first();
                if (rs3.getRow() > 0) {
                    //用户名存在
                    System.out.println("该用户名已存在，请直接登陆");
                }
                    else {
                    password = Base64.getBase64(password);
                    String sql4 = "INSERT INTO users (username, password, question) VALUES ('"+username+"', '"+password+"', '"+question+"')";
                    DBConnection dbConnection2 = new DBConnection();
                    boolean rs4 = dbConnection2.excuteSqlWithoutResult(sql4);
                    if (rs4) {
                        System.out.println("注册成功");
                    } else {
                        System.out.println("注册失败");
                    }

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
