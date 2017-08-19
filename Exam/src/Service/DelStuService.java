package Service;

import Util.DBConnection;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class DelStuService {
    public void delstu(HttpServletRequest request) throws SQLException {
        int stuId = Integer.parseInt(request.getParameter("stuId"));
        String sql1 = "DELETE FROM student WHERE stuId = '" + stuId + "'";
        DBConnection dbConnection1 = new DBConnection();
        boolean rs1 = dbConnection1.excuteSqlWithoutResult(sql1);
        if (rs1) {
            System.out.println("删除成功");
        } else {
            System.out.println("删除失败");
        }
    }
}
