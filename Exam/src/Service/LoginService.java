package Service;


import Util.Base64;
import Util.DBConnection;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginService {

    public void login(HttpServletRequest request) {
        //获取用户名 密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        password = Base64.getBase64(password);

        System.out.println("-----------");
        System.out.println(username);
        System.out.println(password);
        System.out.println("------------");
//        String username = "dyd3";
//        String password = "123456";
        //验证
        boolean checkpassword = password.matches(("^.*[0-9]+.*$"));
        if (username == null || password == null) {
            System.out.println("用户名或密码不能为空");
        }
        else if (false) {
            System.out.println("密码格式错误");
        }
        else {
            //String sql1 = "SELECT * FROM users WHERE username='" + username + "' AND password='" + password + "'";
            String sql1 = "SELECT * FROM users WHERE username= '" + username + "'";
            DBConnection dbConnection1 = new DBConnection();
            ResultSet rs1 = dbConnection1.excuteSqlWithResult(sql1);

            try {
                rs1.first();
                if (rs1.getRow() > 0) {
                    //用户名存在
                    String sql2 = "SELECT password FROM users WHERE username='" + username + "'";
                    DBConnection dbConnection2 = new DBConnection();
                    ResultSet rs2 = dbConnection2.excuteSqlWithResult(sql2);
                    rs2.first();
                    if (rs2.getString("password").equals(password)){
                        System.out.println("登陆成功");
                    }
                    else {
                        System.out.println("登录失败");
                    }
                }
                else {
                    //用户名不存在
                    System.out.println("该用户名不存在，请先注册");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

}
