package Demo.Crypt;

import java.sql.*;

/**
 * @Author: Donlin
 * @Date: Created in 14:33 2018/10/15
 * @Version: 1.0
 * @Description:
 */
public class JDBCDemo {
    static final String jdbc_driver = "com.mysql.jdbc.Driver";
    //static final String db_url = "jdbc:mysql://120.77.156.138:3306/" + " ";     
    static final String db_url = "jdbc:mysql://localhost:3306/imooc";

    static final String USER = "root";
    static final String PASS = "donlin";
    //static final String PASS = "root";      // ECS远程数据密码

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        try {
            //  注册jdbc驱动
            Class.forName(jdbc_driver);
            // 打开链接
            System.out.println("connect database...");
            connection = DriverManager.getConnection(db_url, USER, PASS);
            statement = connection.createStatement();
            String sql = "select * from test";
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                int userid = resultSet.getInt("userid");
                String username = resultSet.getString("username");
                System.out.println(userid + ":" + username);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Over!");
    }
}
