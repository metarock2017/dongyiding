package Service;

import Util.DBConnection;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetStuService {
    public void getstu(HttpServletRequest request) throws SQLException {
        int stuId = Integer.parseInt(request.getParameter("stuId"));
        String sql1 = "SELECT * FROM student WHERE stuId = '" + stuId + "'";
        DBConnection dbConnection1 = new DBConnection();
        ResultSet rs1 = dbConnection1.excuteSqlWithResult(sql1);
        rs1.first();
        System.out.println(rs1.getString("name"));
    }
}
