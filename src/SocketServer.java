import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class SocketServer {

    //定义并初始化接收到的请求指令“command”和“next_command”
    private static String command = "no.control";
    private static String next_command = "no.control";

    //主方法
    public static void main(String[] args) {
        System.out.println("启动服务器，等待连接请求...\n");
        //新建线程，启动新闻数据传输服务
        new Thread(() -> {
            try {
                transferInfo();
            } catch (IOException e) {
                System.out.println("信息传输发生错误！");
            }
        }).start();
        //新建线程，启动新闻图片传输服务
        new Thread(() -> {
            try {
                transferImage();
            } catch (IOException e) {
                System.out.println("图片传输发生错误！");
            }
        }).start();
    }

    //数据传输方法
    static void transferInfo() throws IOException {
        //启动Socket进程
        ServerSocket serverSocket = new ServerSocket(ServerConstant.post_info_port);
        //循环监听来自客户端的请求
        while (!command.equals(ServerConstant.listen_stop)) {
            Socket socket = serverSocket.accept();
            //为此链接启动一个线程
            System.out.println("建立新的通信连接！");
            try {
                String result = null;
                //从客户端接收数据
                InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receive_data = bufferedReader.readLine();
                System.out.println("接收到请求：" + receive_data);
                //解析接收到的数据
                String[] receive_info = receive_data.split("/#/");
                command = receive_info[0];
                next_command = receive_info[1];
                //判断客户端请求的操作
                switch (command) {
                    case "user_sign_up":            //用户注册
                        System.out.println("发送" + next_command + "到用户注册方法！");
                        result = UserInfoControl.userSignUp(next_command);
                        break;
                    case "user_sign_in":            //用户登录
                        System.out.println("发送" + next_command + "到用户登录方法");
                        result = UserInfoControl.userSignIn(next_command);
                        break;
                    case "device_random_verify":    //设备验证
                        System.out.println("发送" + next_command + "到设备验证方法");
                        result = UserInfoControl.deviceRandomVerify(next_command);
                        break;
                    case "headline_get_info":       //头条内容获取
                        System.out.println("发送" + next_command + "到头条内容获取方法");
                        result = DataInfoControl.dataGetHeadline();
                        break;
                    case "data_get_title":          //新闻标题列表获取
                        System.out.println("发送" + next_command + "到新闻标题列表获取方法");
                        result = DataInfoControl.dataGetTitle(next_command);
                        break;
                    case "data_get_info":           //新闻内容获取
                        System.out.println("发送" + next_command + "到新闻内容获取方法");
                        result = DataInfoControl.dataGetInfo(next_command);
                        break;
                    case "data_follow_insert":      //作者关注添加
                        System.out.println("发送" + next_command + "到作者关注添加方法");
                        result = DataInfoControl.dataFollowInsert(next_command);
                        break;
                    case "data_follow_delete":      //作者关注删除
                        System.out.println("发送" + next_command + "到作者关注删除方法");
                        result = DataInfoControl.dataFollowDelete(next_command);
                        break;
                    case "data_favorite_insert":    //文章点赞添加
                        System.out.println("发送" + next_command + "到文章点赞添加方法");
                        result = DataInfoControl.dataFavoriteInsert(next_command);
                        break;
                    case "data_favorite_delete":    //文章点赞删除
                        System.out.println("发送" + next_command + "到文章点赞删除方法");
                        result = DataInfoControl.dataFavoriteDelete(next_command);
                        break;
                    case "data_favorite_title":     //文章点赞列表
                        System.out.println("发送" + next_command + "到文章点赞列表方法");
                        result = DataInfoControl.dataFavoriteFetch(next_command);
                        break;
                    case "data_comment_get":        //文章评论获取
                        System.out.println("发送" + next_command + "到文章评论获取方法");
                        result = DataInfoControl.dataCommentGet(next_command);
                        break;
                    case "data_comment_insert":     //文章评论添加
                        System.out.println("发送" + next_command + "到文章评论添加方法");
                        result = DataInfoControl.dataCommentInsert(next_command);
                        break;
                    case "data_comment_delete":     //文章评论删除
                        System.out.println("发送" + next_command + "到文章评论删除方法");
                        result = DataInfoControl.dataCommentDelete(next_command);
                        break;
                    case "fetch_user_profile":      //获取用户偏好
                        System.out.println("发送" + next_command + "到获取用户偏好方法");
                        result = Recommendations.fetch_history_num(next_command, "profile");
                        break;
                    case "fetch_profile_title":     //获取偏好标题列表
                        System.out.println("发送" + next_command + "到获取偏好标题列表方法");
                        result = Recommendations.fetch_history_num(next_command, "title");
                        break;
                    default:
                        System.out.println("无法识别此次请求！");
                        break;
                }
                //返回请求到的数据给客户端
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
                //检验请求到的数据是否为空
                if (result != null) {
                    outputStreamWriter.write(result);
                    System.out.println("向客户端返回字符串：" + result);
                } else {
                    outputStreamWriter.write("return.null");
                    System.out.println("发生未知错误,返回数据为空！");
                }
                //关闭连接
                outputStreamWriter.close();
                socket.close();
                System.out.println("通信完成，连接关闭！");
            } catch (IOException | ClassNotFoundException | SQLException e) {
                System.out.println("信息传输线程通信发生错误！");
            }
            System.out.println("线程执行完毕！\n");
        }
    }

    //图片传输方法
    static void transferImage() throws IOException {
        ServerSocket serverSocket = new ServerSocket(ServerConstant.post_image_port);
        while (!command.equals(ServerConstant.listen_stop)) {
            //创建输入流、byte数组、int型
            Socket socket = serverSocket.accept();
            System.out.println("建立新的通信连接！");
            try {
                InputStream inputStream = socket.getInputStream();
                byte[] buf_in = new byte[1024];
                int len_in = inputStream.read(buf_in);
                String receive_data = new String(buf_in, 0, len_in);
                System.out.println(receive_data);
                String[] qu = receive_data.split("/&/");
                command = qu[0];
                next_command = qu[1];
                if (command.equals("get_headline_image")) {
                    //从文件取出图片
                    String file_src = ServerConstant.image_local + next_command + ".png";
                    FileInputStream fileInputStream = new FileInputStream(file_src);
                    //创建输出流、byte数组、int型
                    OutputStream outputStream = socket.getOutputStream();
                    byte[] buf_out = new byte[1024];
                    int len_out;
                    //数据写入输出流
                    while ((len_out = fileInputStream.read(buf_out)) != -1) {
                        //输出数据
                        outputStream.write(buf_out, 0, len_out);
                    }
                    //关闭输出流
                    socket.shutdownOutput();
                    inputStream.close();
                    outputStream.close();
                    fileInputStream.close();
                } else {
                    System.out.println("无法识别此次请求！");
                }
            } catch (IOException e) {
                System.out.println("图片传输线程通信发生错误！");
            }
        }
    }
}
