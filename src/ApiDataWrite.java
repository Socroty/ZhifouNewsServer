import java.sql.*;

class ApiDataWrite {

    private static Connection conn;

    String SQLOperation(String type, String data_title, String data_author, String data_content, String data_url, String data_category, String data_hash_id, String data_image_url) {

        String driver = "com.mysql.jdbc.Driver";
        String url = ServerConstant.mysql_address;
        String user = ServerConstant.mysql_user;   //MySQL用户名
        String password = ServerConstant.mysql_password;  //MySQL密码
        String result = null;

        try {
            Class.forName(driver);  //用class加载动态链接库
            conn = DriverManager.getConnection(url, user, password);  //利用信息链接数据库
            if (!conn.isClosed())
                switch (type) {
                    case "fetch_hash":
                        result = fetch_hash(data_hash_id);
                        break;
                    case "insert_data":
                        result = insert_data(data_title,data_author,data_content,data_url,data_category,data_hash_id,data_image_url);
                        break;
                    default:
                        System.out.println("无此操作！");
                        break;
                }
            conn.close();
        } catch (ClassNotFoundException e) {
            System.out.println("找不到驱动程序！");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String fetch_hash(String data) {
        String result = null;
        String data_title = null;
        try {
            String sql = "select * from data_info where data_hash_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, data);
            ResultSet rs = preparedStatement.executeQuery();// 返回结果集

            while (rs.next()) {
                data_title = rs.getString("data_title");
            }
            if (data_title == null) {
                result = "insert.true";
            } else {
                result = "insert.false";
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //插入数据函数
    private static String insert_data(String data_title, String data_author, String data_content, String data_url, String data_category, String data_hash_id, String data_image_url) {
        String result;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("insert into data_info (data_title,data_author,data_content,data_url,data_category,data_hash_id,data_image_url,data_create_time)" + "values (?,?,?,?,?,?,?,NOW())");  //用preparedStatement预处理来执行sql语句
            preparedStatement.setString(1, data_title);
            preparedStatement.setString(2, data_author);
            preparedStatement.setString(3, data_content);
            preparedStatement.setString(4, data_url);
            preparedStatement.setString(5, data_category);
            preparedStatement.setString(6, data_hash_id);
            preparedStatement.setString(7, data_image_url);
            preparedStatement.executeUpdate();  //参数准备后执行语句
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            result = "insert.true";
        }
        return result;
    }
}
