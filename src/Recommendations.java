import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Recommendations {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        fetch_history_num("1000000004","title");
    }

    static String fetch_history_num(String data, String type) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(ServerConstant.mysql_address, ServerConstant.mysql_user, ServerConstant.mysql_password);
        String result = null;
        int[] user_category = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        String[] user_category_name = {"国内","国际","社会","体育","娱乐","军事","财经","时尚","科技"};
        try {
            String sql = "select * from history_info a, data_info b where a.history_data_id = b.data_id and a.history_user_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, data);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                switch (rs.getString("b.data_category")) {
                    case "时事":
                        user_category[0]++;
                        break;
                    case "国际":
                        user_category[1]++;
                        break;
                    case "社会":
                        user_category[2]++;
                        break;
                    case "体育":
                        user_category[3]++;
                        break;
                    case "娱乐":
                        user_category[4]++;
                        break;
                    case "军事":
                        user_category[5]++;
                        break;
                    case "财经":
                        user_category[6]++;
                        break;
                    case "时尚":
                        user_category[7]++;
                        break;
                    case "科技":
                        user_category[8]++;
                        break;
                    default:
                        break;
                }
            }
            rs.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        update_profile(user_category, data);
        String[] info = pass(user_category,user_category_name);
        switch (type) {
            case "profile":
                result = info[0] + "/%/" + info[1] + "/%/" + info[2];
                break;
            case "title":
                result = fetch_data_profile(info);
                break;
        }
        System.out.println(result);
        return result;
    }

    private static String[] pass(int[] data, String[] name){
        int temp;
        String temp_name;
        for(int i=0;i<data.length;i++) {
            for(int j=i+1;j<data.length;j++) {
                if(data[i]<data[j]) {
                    temp = data[i];
                    data[i] = data[j];
                    data[j] = temp;
                    temp_name = name[i];
                    name[i] = name[j];
                    name[j] = temp_name;
                }
            }
        }
        return name;
    }

    private static void update_profile(int[] data, String name) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(ServerConstant.mysql_address, ServerConstant.mysql_user, ServerConstant.mysql_password);
        try {
            String sql = "update profile_info set profile_category_domestic=?, profile_category_international=?, profile_category_society=?, profile_category_sports=?, profile_category_entertainment=?, profile_category_military=?, profile_category_economic=?, profile_category_fashion=?, profile_category_science=? where profile_user_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, data[0]);
            preparedStatement.setInt(2, data[1]);
            preparedStatement.setInt(3, data[2]);
            preparedStatement.setInt(4, data[3]);
            preparedStatement.setInt(5, data[4]);
            preparedStatement.setInt(6, data[5]);
            preparedStatement.setInt(7, data[6]);
            preparedStatement.setInt(8, data[7]);
            preparedStatement.setInt(9, data[8]);
            preparedStatement.setString(10, name);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String fetch_data_profile(String[] data) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(ServerConstant.mysql_address, ServerConstant.mysql_user, ServerConstant.mysql_password);
        String result = null;
        StringBuilder result_title = new StringBuilder();
        StringBuilder result_id = new StringBuilder();
        StringBuilder result_image_url = new StringBuilder();
        try {
            String sql = "select * from data_info where data_category in (?,?,?) order by data_id desc";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, data[0]);
            preparedStatement.setString(2, data[1]);
            preparedStatement.setString(3, data[2]);
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
}
