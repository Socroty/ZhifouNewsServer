import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

public class ApiDataGet {

    private static int count = 0;

    public static void main(String[] args) throws IOException, JSONException {
        getData();
    }

    private static void getData() throws IOException, JSONException {

        String url = ServerConstant.api_url;
        JSONObject json = readJsonFromUrl(url);
        System.out.println(json);
        JSONArray jsonArray = json.getJSONArray("data");

        Object retcode = json.get("retcode");
        String[] data_title = new String[jsonArray.length()];
        String[] data_hash_id = new String[jsonArray.length()];
        String[] data_author = new String[jsonArray.length()];
        String[] data_content = new String[jsonArray.length()];
        String[] data_url = new String[jsonArray.length()];
        String[] data_category = new String[jsonArray.length()];
        String[] data_image_url = new String[jsonArray.length()];

        if (retcode != null) {
            //返回状态码"000000"表示正常
            if (retcode.equals("000000")) {
                System.out.println("接口正常");
            } else {
                // 其它返回状态码,表示无法继续
                System.out.println(json.getString("message"));
            }
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject row = jsonArray.getJSONObject(i);
            data_title[i] = String.valueOf(row.get("title"));
            data_hash_id[i] = String.valueOf(row.get("id"));
            data_author[i] = String.valueOf(row.get("appName"));
            if (row.has("content")){
                data_content[i] = "<aa>content<bb>" + row.get("content");
            }else {
                data_content[i] = "null.content";
            }
            data_url[i] = String.valueOf(row.get("url"));
            data_category[i] = String.valueOf(row.get("catLabel1"));
            JSONObject image_object = jsonArray.getJSONObject(i);
            if (image_object.has("imageUrls")){
                JSONArray jsonArray_imageUrls = row.getJSONArray("imageUrls");
                data_image_url[i] = jsonArray_imageUrls.getString(0);
            }else {
                data_image_url[i] = "null.image";
            }
        }

        for (int i = 0; i < jsonArray.length(); i++){
            ApiDataWrite ApiDataWrite = new ApiDataWrite();
            String result_hash = ApiDataWrite.SQLOperation("fetch_hash",null,null,null,null,null, data_hash_id[i],null);
            if (result_hash.equals("insert.true")){
                String result = ApiDataWrite.SQLOperation("insert_data", data_title[i], data_author[i], data_content[i], data_url[i], data_category[i], data_hash_id[i], data_image_url[i]);
                if (result.equals("insert.true")){
                    count++;
                }
            }
        }
        System.out.println("插入"+count+"条数据！\n");
    }

    private static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        try (InputStream inputStream = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            return new JSONObject(jsonText);
        }
    }

    private static String readAll(Reader reader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int cp;
        while ((cp = reader.read()) != -1) {
            stringBuilder.append((char) cp);
        }
        return stringBuilder.toString();
    }
}
