import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class DataMySQL {

    private static Connection conn;
    static String SQLOperation(String type, int data, String name) {

        String result = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(ServerConstant.mysql_address, ServerConstant.mysql_user, ServerConstant.mysql_password);
            if (!conn.isClosed())
                switch (type) {
                    case "fetch_headline_info":
                        result = fetch_headline_info();
                        break;
                    case "fetch_data_title":
                        result = fetch_title_variety(name);
                        break;
                    case "fetch_data_info":
                        result = fetch_data_info(data);
                        break;
                    case "fetch_follow_info":
                        result = fetch_follow_info(name);
                        break;
                    case "fetch_favorite_info":
                        result = fetch_favorite_info(name);
                        break;
                    case "insert_follow_info":
                        result = insert_follow_info(name);
                        break;
                    case "delete_follow_info":
                        result = delete_follow_info(name);
                        break;
                    case "insert_favorite_info":
                        result = insert_favorite_info(name);
                        break;
                    case "delete_favorite_info":
                        result = delete_favorite_info(name);
                        break;
                    case "fetch_comment_info":
                        result = fetch_comment_info(name);
                        break;
                    case "fetch_comment_user":
                        result = fetch_comment_user(name);
                        break;
                    case "insert_comment_info":
                        result = insert_comment_info(name);
                        break;
                    case "delete_comment_info":
                        result = delete_comment_info(name);
                        break;
                    case "fetch_favorite_title":
                        result = fetch_favorite_title(name);
                        break;
                    case "insert_history_info":
                        result = insert_history_info(data,name);
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

    private static String fetch_headline_info() {
        String result = null;
        try {
            String sql = "select * from headline_info order by headline_id desc";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            List<String> list_id = new ArrayList<>();
            List<String> list_data_id = new ArrayList<>();
            List<String> list_data_title = new ArrayList<>();
            StringBuilder result_id = new StringBuilder();
            StringBuilder result_data_id = new StringBuilder();
            StringBuilder result_data_title = new StringBuilder();

            while (rs.next()) {
                list_id.add(rs.getString("headline_id"));
                list_data_id.add(rs.getString("headline_data_id"));
                list_data_title.add(rs.getString("headline_title"));
            }

            for (int i = 0; i<6; i++){
                result_data_id.append("/%/").append(list_data_id.get(i));
                result_id.append("/%/").append(list_id.get(i));
                result_data_title.append("/%/").append(list_data_title.get(i));
            }
            result = result_data_id + "/@/" + result_id + "/@/" + result_data_title;
            rs.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String fetch_title_variety(String data) {
        String result = null;
        StringBuilder result_title = new StringBuilder();
        StringBuilder result_id = new StringBuilder();
        StringBuilder result_image_url = new StringBuilder();
        try {
            String sql = "select * from data_info where data_category = ? order by data_id desc";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, data);
            ResultSet rs = preparedStatement.executeQuery();
            List<String> list_title = new ArrayList<>();
            List<String> list_id = new ArrayList<>();
            List<String> list_image_url = new ArrayList<>();
            while (rs.next()) {
                list_title.add(rs.getString("data_title"));
                list_id.add(rs.getString("data_id"));
                list_image_url.add(rs.getString("data_image_url"));
            }
            for (int i=0; i<20; i++){
                System.out.println(list_title.get(i));
                System.out.println(list_id.get(i));
                result_title.append("/%/").append(list_title.get(i));
                result_id.append("/%/").append(list_id.get(i));
                result_image_url.append("/%/").append(list_image_url.get(i));
            }
            result = result_title + "/@/" + result_id + "/@/" + result_image_url;
            rs.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String fetch_data_info(int data) {
        String result = null;
        try {
            String sql = "select * from data_info where data_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, data);
            ResultSet rs = preparedStatement.executeQuery();
            List<String> list_id = new ArrayList<>();
            List<String> list_content = new ArrayList<>();
            List<String> list_author = new ArrayList<>();
            List<String> list_title = new ArrayList<>();
            List<String> list_url = new ArrayList<>();
            List<String> list_category = new ArrayList<>();
            List<String> list_image_url = new ArrayList<>();
            List<String> list_create_time = new ArrayList<>();

            while (rs.next()) {
                list_id.add(rs.getString("data_id"));
                list_title.add(rs.getString("data_title"));
                list_content.add(rs.getString("data_content"));
                list_author.add(rs.getString("data_author"));
                list_url.add(rs.getString("data_url"));
                list_category.add(rs.getString("data_category"));
                list_image_url.add(rs.getString("data_image_url"));
                list_create_time.add(rs.getString("data_create_time"));
            }

            result = list_id.get(0) + "/%/" + list_title.get(0) + "/%/" + list_content.get(0) + "/%/" + list_author.get(0) + "/%/" + list_url.get(0) + "/%/" + list_category.get(0) + "/%/" + list_image_url.get(0) + "/%/" + list_create_time.get(0);
            rs.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //读取数据函数
    private static String fetch_follow_info(String data) {
        String result = null;
        String[] info = data.split("/@/");
        try {
            String sql = "select * from follow_info where follow_data_author = ? and follow_user_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, info[0]);
            preparedStatement.setString(2, info[1]);
            ResultSet rs = preparedStatement.executeQuery();
            String list_star_id = null;

            while (rs.next()) {
                list_star_id = rs.getString("follow_id");
            }
            if (list_star_id != null){
                result = "exist";
            }else {
                result = "null";
            }
            rs.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //读取数据函数
    private static String fetch_favorite_info(String data) {
        String result = null;
        String[] info = data.split("/@/");
        try {
            String sql = "select * from favorite_info where favorite_data_id = ? and favorite_user_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, info[0]);
            preparedStatement.setString(2, info[1]);
            ResultSet rs = preparedStatement.executeQuery();
            String list_star_id = null;

            while (rs.next()) {
                list_star_id = rs.getString("favorite_id");
            }
            if (list_star_id != null){
                result = "exist";
            }else {
                result = "null";
            }
            rs.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String insert_follow_info(String data) {
        String result;
        String[] info = data.split("/&/");
        try {
            String sql = "insert into follow_info (follow_data_author,follow_user_id) values (?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, info[0]);
            preparedStatement.setString(2, info[1]);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            result = "insert.true";
        }
        return result;
    }

    private static String delete_follow_info(String data) {
        String result;
        String[] info = data.split("/&/");
        try {
            String sql = "delete from follow_info where follow_data_author = ? and follow_user_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, info[0]);
            preparedStatement.setString(2, info[1]);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            result = "delete.true";
        }
        return result;
    }

    private static String insert_favorite_info(String data) {
        String result;
        String[] info = data.split("/&/");
        try {
            String sql = "insert into favorite_info (favorite_user_id,favorite_data_id, favorite_data_category, favorite_data_title, favorite_data_image_url) values (?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, info[0]);
            preparedStatement.setString(2, info[1]);
            preparedStatement.setString(3, info[2]);
            preparedStatement.setString(4, info[3]);
            preparedStatement.setString(5, info[4]);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            result = "insert.true";
        }
        return result;
    }

    private static String delete_favorite_info(String data) {
        String result;
        String[] info = data.split("/&/");
        try {
            String sql = "delete from favorite_info where favorite_user_id = ? and favorite_data_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, info[0]);
            preparedStatement.setString(2, info[1]);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            result = "delete.true";
        }
        return result;
    }

    //读取数据函数
    private static String fetch_comment_info(String data) {
        String result = null;
        StringBuilder result_user_name = new StringBuilder();
        StringBuilder result_content = new StringBuilder();
        int info = Integer.parseInt(data);
        try {
            String sql = "select * from comment_info join user_info where comment_info.comment_data_id = ? order by comment_id desc";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, info);
            ResultSet rs = preparedStatement.executeQuery();
            List<String> comment_user_name = new ArrayList<>();
            List<String> comment_content = new ArrayList<>();
            int j=0;
            while (rs.next()) {
                comment_user_name.add(rs.getString("user_name"));
                comment_content.add(rs.getString("comment_content"));
                j++;
            }
            for (int i=0; i<j; i++){
                System.out.println(comment_user_name.get(i));
                System.out.println(comment_content.get(i));
                result_user_name.append("/%/").append(comment_user_name.get(i));
                result_content.append("/%/").append(comment_content.get(i));
            }
            result = result_user_name + "/@/" + result_content;
            rs.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //读取数据函数
    private static String fetch_comment_user(String data) {
        String result = null;
        String[] info = data.split("/&/");
        try {
            String sql = "select * from comment_info where comment_data_id = ? and comment_user_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, info[1]);
            preparedStatement.setString(2, info[0]);
            ResultSet rs = preparedStatement.executeQuery();
            String comment_content = null;
            while (rs.next()) {
                comment_content = rs.getString("comment_content");
            }
            if (comment_content != null){
                result = "exist/@/" + comment_content;
            }else {
                result = "null/@/null";
            }
            rs.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String insert_comment_info(String data) {
        String result;
        String[] info = data.split("/&/");
        try {
            String sql = "insert into comment_info (comment_user_id,comment_data_id,comment_content) values (?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, info[0]);
            preparedStatement.setString(2, info[1]);
            preparedStatement.setString(3, info[2]);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            result = "insert.true/@/";
        }
        return result;
    }

    private static String delete_comment_info(String data) {
        String result;
        String[] info = data.split("/&/");
        try {
            String sql = "delete from comment_info where comment_user_id = ? and comment_data_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, info[0]);
            preparedStatement.setString(2, info[1]);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            result = "delete.true";
        }
        return result;
    }

    private static String fetch_favorite_title(String data) {
        String result = null;
        StringBuilder result_id = new StringBuilder();
        StringBuilder result_title = new StringBuilder();
        StringBuilder result_image_url = new StringBuilder();
        int number = 0;
        try {
            String sql = "select * from favorite_info where favorite_user_id = ? order by favorite_id desc";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, data);
            ResultSet rs = preparedStatement.executeQuery();
            List<String> list_id = new ArrayList<>();
            List<String> list_title = new ArrayList<>();
            List<String> list_image_url = new ArrayList<>();
            while (rs.next()) {
                list_id.add(rs.getString("favorite_data_id"));
                list_title.add(rs.getString("favorite_data_title"));
                list_image_url.add(rs.getString("favorite_data_image_url"));
                number++;
            }
            for (int i=0; i<number; i++){
                System.out.println(list_id.get(i));
                result_id.append("/%/").append(list_id.get(i));
                result_title.append("/%/").append(list_title.get(i));
                result_image_url.append("/%/").append(list_image_url.get(i));
                //result_title = result_title + "/%/" + list_title.get(i);
                //result_id = result_id + "/%/" + list_id.get(i);
            }
            result = result_title + "/@/" + result_id + "/@/" + result_image_url;
            rs.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String insert_history_info(int data,String name) {
        String result;
        try {
            String sql = "insert into history_info (history_data_id,history_user_id) values (?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, data);
            preparedStatement.setInt(2, Integer.parseInt(name));
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            result = "insert.true";
        }
        return result;
    }
}
