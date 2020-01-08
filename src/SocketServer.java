import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class SocketServer {

    private static String control = "no.control";

    public static void main(String[] args) {
        //新建线程，启动新闻数据传输服务
        new Thread(() -> {
            try {
                postNewsInfo();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        //新建线程，启动新闻图片传输服务
        new Thread(() -> {
            try {
                postHeadlineImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        System.out.println("服务器启动成功！等待连接请求...\n");
    }

    private static void postNewsInfo() throws IOException {
        //启动Socket进程
        ServerSocket s = new ServerSocket(ServerConstant.post_info_port);
        //循环监听来自客户端的请求
        while (!control.equals(ServerConstant.server_stop)) {
            Socket connection = s.accept();
            //为此链接启动一个线程
            new Thread(() -> {
                System.out.println("建立新的通信连接！");
                try {
                    String result = null;
                    //从客户端接收数据
                    InputStreamReader isr = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8);
                    BufferedReader br = new BufferedReader(isr);
                    String data = br.readLine();
                    System.out.println("接收到请求：" + data);
                    //解析接收到的数据
                    String[] info = data.split("/#/");
                    control = info[0];
                    //判断客户端请求的操作
                    switch (control) {
                        case "user_sign_up":
                            System.out.println("发送" + info[1] + "到用户注册方法！");
                            result = UserInfoControl.userSignUp(info[1]);
                            break;
                        case "user_sign_in":
                            System.out.println("发送" + info[1] + "到用户登录方法");
                            result = UserInfoControl.userSignIn(info[1]);
                            break;
                        case "user_random_verify":
                            System.out.println("发送" + info[1] + "到用户验证方法");
                            result = UserInfoControl.userRandomVerify(info[1]);
                            break;
                        case "headline_get_info":
                            System.out.println("发送" + info[1] + "到头条数据类");
                            result = DataInfoControl.dataGetHeadline();
                            break;
                        case "data_get_title":
                            System.out.println("发送" + info[1] + "到数据标题类");
                            result = DataInfoControl.dataGetTitle(info[1]);
                            break;
                        case "data_get_info":
                            System.out.println("发送" + info[1] + "到数据信息类");
                            result = DataInfoControl.dataGetInfo(info[1]);
                            break;
                        case "data_follow_insert":
                            System.out.println("发送" + info[1] + "到添加关注类");
                            result = DataInfoControl.dataFollowInsert(info[1]);
                            break;
                        case "data_follow_delete":
                            System.out.println("发送" + info[1] + "到删除关注类");
                            result = DataInfoControl.dataFollowDelete(info[1]);
                            break;
                        case "data_favorite_insert":
                            System.out.println("发送" + info[1] + "到添加喜好类");
                            result = DataInfoControl.dataFavoriteInsert(info[1]);
                            break;
                        case "data_favorite_delete":
                            System.out.println("发送" + info[1] + "到删除喜好类");
                            result = DataInfoControl.dataFavoriteDelete(info[1]);
                            break;
                        case "data_favorite_title":
                            System.out.println("发送" + info[1] + "到喜好标题类");
                            result = DataInfoControl.dataFavoriteFetch(info[1]);
                            break;
                        case "data_comment_get":
                            System.out.println("发送" + info[1] + "到评论信息类");
                            result = DataInfoControl.dataCommentGet(info[1]);
                            break;
                        case "data_comment_insert":
                            System.out.println("发送" + info[1] + "到评论插入类");
                            result = DataInfoControl.dataCommentInsert(info[1]);
                            break;
                        case "data_comment_delete":
                            System.out.println("发送" + info[1] + "到评论删除类");
                            result = DataInfoControl.dataCommentDelete(info[1]);
                            break;
                        case "fetch_user_profile":
                            result = Recommendations.fetch_history_num(info[1],"profile");
                            break;
                        case "fetch_profile_title":
                            result = Recommendations.fetch_history_num(info[1],"title");
                            break;
                        case "stop.server":
                            System.out.println("服务器被远程关闭！");
                            break;
                        default:
                            System.out.println("无法识别此次请求！");
                            break;
                    }
                    //返回请求到的数据给客户端
                    OutputStreamWriter pw = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8);
                    //检验请求到的数据是否为空
                    if (result != null) {
                        pw.write(result);
                        System.out.println("返回字符串：" + result);
                    } else {
                        pw.write("return.null");
                        System.out.println("发生未知错误,返回数据为空！");
                    }
                    //关闭连接
                    pw.close();
                    connection.close();
                    System.out.println("通信完成，连接关闭！");
                } catch (IOException | ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
                System.out.println("线程执行完毕！\n");
            }).start();
        }
    }

    private static void postHeadlineImage() throws IOException {
        ServerSocket ss = new ServerSocket(ServerConstant.post_image_port);
        while (!control.equals(ServerConstant.server_stop)) {
            //创建输入流、byte数组、int型
            Socket s = ss.accept();
            new Thread(() -> {
                System.out.println("建立新的通信连接！");
                try {
                    InputStream ins = s.getInputStream();
                    byte[] buf_in = new byte[1024];
                    int len_in = ins.read(buf_in);
                    String data = new String(buf_in, 0, len_in);
                    System.out.println(data);
                    String[] qu = data.split("/&/");
                    control = qu[0];
                    if (qu[0].equals("get_headline_image")) {
                        //从文件取出图片
                        String file_src = ServerConstant.image_local + qu[1] + ".png";
                        FileInputStream files = new FileInputStream(file_src);
                        //创建输出流、byte数组、int型
                        OutputStream outs = s.getOutputStream();
                        byte[] buf_out = new byte[1024];
                        int len_out;
                        //数据写入输出流
                        while ((len_out = files.read(buf_out)) != -1) {
                            //输出数据
                            outs.write(buf_out, 0, len_out);
                        }
                        //关闭输出流
                        s.shutdownOutput();
                        ins.close();
                        outs.close();
                        files.close();
                    } else {
                        System.out.println("错误的请求！");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
