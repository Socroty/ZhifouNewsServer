import java.sql.*;
import java.util.Objects;

class UserMySQL {

    private static Connection conn;

    static String SQLOperation(String type, String user_name, String user_password, String user_random) {

        String result = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(ServerConstant.mysql_address, ServerConstant.mysql_user, ServerConstant.mysql_password);
            if (!conn.isClosed())
                switch (type) {
                    case "fetch_sign_up":
                        result = fetch_sign_up(user_name);
                        break;
                    case "insert_sign_up":
                        result = insert_sign_up(user_name, user_password);
                        break;
                    case "fetch_sign_in":
                        result = fetch_sign_in(user_name, user_password);
                        break;
                    case "update_sign_in":
                        result = update_sign_in(user_name, user_random);
                        break;
                    case "fetch_hash_id":
                        result = fetch_hash_id(user_name, user_random);
                        break;
                    case "delete_user":
                        delete_user();
                        break;
                    default:
                        result = "control.error";
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

    private static String fetch_sign_up(String data_name) {
        String result = null;
        String user_id = null;
        try {
            String sql = "select * from user_info where user_name = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, data_name);
            ResultSet resultSet = preparedStatement.executeQuery();
            //提取用户信息
            while (resultSet.next()) {
                user_id = resultSet.getString("user_id");
            }
            if (user_id != null) {
                result = "fetch.exist";
            } else {
                result = "fetch.null";
            }
            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String insert_sign_up(String data_name, String data_password) {
        String result;
        try {
            String sql = "insert into user_info (user_name,user_password,user_description,user_sign_up_date) values (?,?,?,NOW())";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, data_name);
            preparedStatement.setString(2, data_password);
            preparedStatement.setString(3, "暂无简介！");
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            result = "insert.true";
        }
        return result;
    }

    private static String fetch_sign_in(String data_name, String data_password) {
        String result = null;
        String user_id = null;
        String user_password = null;

        try {
            String sql = "select * from user_info where user_name = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, data_name);
            ResultSet resultSet = preparedStatement.executeQuery();
            //提取用户信息
            while (resultSet.next()) {
                user_id = resultSet.getString("user_id");
                user_password = resultSet.getString("user_password");
            }
            //判断用户是否存在
            if (user_id != null) {
                //若存在，判断密码是否正确
                if (data_password.equals(user_password)) {
                    result = "fetch.true" + "/#/" + user_id;
                } else {
                    result = "fetch.false" + "/#/";
                }
            } else {
                result = "fetch.null" + "/#/";
            }
            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String update_sign_in(String data_id, String data_random) {
        String result;
        try {
            String sql = "update user_info set user_random = ? where user_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, data_random);
            preparedStatement.setString(2, data_id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            result = "update.true";
        }
        return result;
    }

    private static String fetch_hash_id(String data_name, String data_random) {
        String result = null;
        try {
            String sql = "select * from user_info where user_name = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, data_name);
            ResultSet rs = preparedStatement.executeQuery();// 返回结果集

            String user_random = null;
            while (rs.next()) {
                user_random = rs.getString("user_random");
            }
            if (Objects.equals(user_random, data_random)) {
                result = "fetch.true";
            } else {
                result = "fetch.false";
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static void delete_user() {
        try {
            String sql = "delete from user_info where user_id= ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setFloat(1, 10000002);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("数据库数据删除成功！");
        }
    }
}