package Service;

import Util.DBConnection;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddStuService {
    public void addstu(HttpServletRequest request) {
        int stuId = Integer.parseInt(request.getParameter("stuId"));
        String name = request.getParameter("name");
        int gender = Integer.parseInt(request.getParameter("gender"));
        int grade = Integer.parseInt(request.getParameter("grade"));
        String college = request.getParameter("college");
        String major = request.getParameter("major");
        int classnum = Integer.parseInt(request.getParameter("classnum"));

        //校验
        if (String.valueOf(stuId) == null || name == null || String.valueOf(gender) == null
                || String.valueOf(grade) == null || college == null || major == null ||
                String.valueOf(classnum) == null) {
            System.out.println("信息不能为空");
        }
        else {
            String sql1 = "SELECT * FROM student WHERE stuId = '"+stuId+"'";
            DBConnection dbConnection1 = new DBConnection();
            ResultSet rs1 = dbConnection1.excuteSqlWithResult(sql1);

            try {
                rs1.first();
                String strings="";
                while (rs1.next())
                    strings=rs1.getString("name");
                if (strings.compareTo("")==0) {
                    String sql2 = "INSERT INTO student (stuId, name, gender, grade, college, major, class) VALUES ('"+stuId+"', '"+name+"', '"+gender+"', '"+grade+"', '"+college+"', '"+major+"', '"+classnum+"')";
                    DBConnection dbConnection2 = new DBConnection();
                    boolean rs2 = dbConnection2.excuteSqlWithoutResult(sql2);
                    if (rs2) {
                        System.out.println("添加成功");
                    } else {
                        System.out.println("添加失败");
                    }
                }
                else {
                    System.out.println("学生信息已存在，添加失败");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }
    }
}
