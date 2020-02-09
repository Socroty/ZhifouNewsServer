class DataInfoControl{

    static String dataGetHeadline() {
        String result;
        result = DataMySQL.SQLOperation("fetch_headline_info",0,null);
        return result;
    }

    static String dataGetTitle(String data) {
        String result;
        switch (data) {
            case "Domestic":
                result = DataMySQL.SQLOperation("fetch_data_title",0,"时事");
                break;
            case "International":
                result = DataMySQL.SQLOperation("fetch_data_title",0,"国际");
                break;
            case "Society":
                result = DataMySQL.SQLOperation("fetch_data_title",0,"社会");
                break;
            case "Sports":
                result = DataMySQL.SQLOperation("fetch_data_title",0,"体育");
                break;
            case "Amusement":
                result = DataMySQL.SQLOperation("fetch_data_title",0,"娱乐");
                break;
            case "Military":
                result = DataMySQL.SQLOperation("fetch_data_title",0,"军事");
                break;
            case "Economics":
                result = DataMySQL.SQLOperation("fetch_data_title",0,"财经");
                break;
            case "Fashion":
                result = DataMySQL.SQLOperation("fetch_data_title",0,"时尚");
                break;
            case "Science":
                result = DataMySQL.SQLOperation("fetch_data_title",0,"科技");
                break;
            default:
                result = null;
        }
        return result;
    }

    static String dataGetInfo(String data) {

        String[] info = data.split("/&/");
        String result;
        String result_data = DataMySQL.SQLOperation("fetch_data_info", Integer.parseInt(info[0]), null);

        String[] news_data = result_data.split("/%/");
        String data_id = news_data[0];
        String data_author = news_data[3];
        String result_follow;
        String result_favorite;
        if (!info[1].equals("null")){
            DataMySQL.SQLOperation("insert_history_info",Integer.parseInt(info[0]),info[1]);
            result_follow = DataMySQL.SQLOperation("fetch_follow_info", 0, data_author + "/@/" + info[1]);
            result_favorite = DataMySQL.SQLOperation("fetch_favorite_info", 0, data_id + "/@/" + info[1]);
        }else {
            result_follow = "null";
            result_favorite = "null";
        }

        result = result_data + "/%/" + result_follow + "/%/" + result_favorite;

        return result;
    }

    static String dataFollowInsert(String data){
        String result;
        result = DataMySQL.SQLOperation("insert_follow_info", 0, data);
        return result;
    }

    static String dataFollowDelete(String data){
        String result;
        result = DataMySQL.SQLOperation("delete_follow_info", 0, data);
        return result;
    }

    static String dataFavoriteInsert(String data){
        String result;
        result = DataMySQL.SQLOperation("insert_favorite_info", 0, data);
        return result;
    }

    static String dataFavoriteDelete(String data){
        String result;
        result = DataMySQL.SQLOperation("delete_favorite_info", 0, data);
        return result;
    }

    static String dataFavoriteFetch(String data){
        String result;
        result = DataMySQL.SQLOperation("fetch_favorite_title",0,data);
        return result;
    }

    static String dataCommentGet(String data) {
        String result;
        result = DataMySQL.SQLOperation("fetch_comment_info",0,data);
        return result;
    }

    static String dataCommentInsert(String data) {
        String result;
        String result_fetch = DataMySQL.SQLOperation("fetch_comment_user",0,data);
        String[] result_user = result_fetch.split("/@/");
        if (result_user[0].equals("exist")){
            result = result_fetch;
        }else {
            result = DataMySQL.SQLOperation("insert_comment_info",0,data);
        }
        return result;
    }

    static String dataCommentDelete(String data) {
        String result;
        result = DataMySQL.SQLOperation("delete_comment_info",0,data);
        return result;
    }
}
