class UserInfoControl {

    static String userSignUp(String data) {
        String result;
        String[] info = data.split("/&/");
        String user_name = info[0];
        String user_password = info[1];
        String fetch_result = UserMySQL.SQLOperation("fetch_sign_up", user_name, null, null);
        switch (fetch_result) {
            case "fetch.exist":
                result = "sign.up.exist";
                break;
            case "fetch.null":
                System.out.println("用户名未被使用！");
                System.out.println("开始写入注册数据....");
                String sign_up_result = UserMySQL.SQLOperation("insert_sign_up", user_name, user_password, null);
                if (sign_up_result.equals("insert.true")) {
                    result = "sign.up.true";
                }else {
                    result = "sign.up.false";
                }
                break;
            default:
                result = "server.error";
                break;
        }
        return result;
    }

    static String userSignIn(String data){
        String result;
        String[] info = data.split("/&/");
        String user_name = info[0];
        String user_password = info[1];
        String user_random = info[2];
        String fetch_result = UserMySQL.SQLOperation("fetch_sign_in",user_name, user_password, null);
        String[] reply = fetch_result.split("/#/");

        switch (reply[0]) {
            case "fetch.null":
                result = "sign.in.null";
                break;
            case "fetch.false":
                result = "sign.in.false";
                break;
            case "fetch.true":
                System.out.println("密码正确！");
                System.out.println("修改登录验证码...");
                String sign_in_result = UserMySQL.SQLOperation("update_sign_in", reply[1], null, user_random);
                if (sign_in_result.equals("update.true")) {
                    result = "sign.in.true/%/" + reply[1];
                } else {
                    result = "server.error";
                }
                break;
            default:
                result = "server.error";
                break;
        }
        return result;
    }

    static String deviceRandomVerify(String data) {
        String result;
        String[] info = data.split("/&/");
        String user_name = info[0];
        String user_random = info[1];
        String fetch_result = UserMySQL.SQLOperation("fetch_hash_id", user_name, null, user_random);

        switch (fetch_result) {
            case "fetch.true":
                result = "verify.true";
                break;
            case "fetch.false":
                result = "verify.false";
                break;
            default:
                result = "server.error";
                break;
        }
        return result;
    }
}
