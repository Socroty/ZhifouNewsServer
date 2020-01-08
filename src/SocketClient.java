import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

//模拟客户端，未使用
public class SocketClient {

    public static void main(String[] args) throws IOException {
        String result = null;
        Random ra = new Random();
        int random = ra.nextInt(899999999) + 100000000;
        String user_random = String.valueOf(random);
        result = Client("get");
        System.out.println(result);
    }

    private static String Client(String data) throws IOException {
        //Socket连接某个服务器端，host表示服务器的Ip地址 port表示端口号
        Socket s=new Socket("127.0.0.1", 1912);

        PrintWriter pw=new PrintWriter(s.getOutputStream(),true);
        pw.println(data);

        InputStreamReader isr=new InputStreamReader(s.getInputStream());
        BufferedReader br = new BufferedReader (isr);
        String response;
        response = br.readLine();
        return response;
    }

}
