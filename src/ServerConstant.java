public interface ServerConstant {
    //服务器信息请求端口
    int post_info_port = 5232;
    //服务器图片请求端口
    int post_image_port = 5233;
    //数据库地址
    String mysql_address = "jdbc:mysql://localhost:3306/zhifounews?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    //数据库用户名
    String mysql_user = "root";
    //数据库密码
    String mysql_password = "Mysql@96985232";
    //服务器远程关闭指令
    String listen_stop = "listen_stop";
    //图片存储文件夹路径
    String image_local = "D:\\Studys\\Programs\\AppCache\\";
    //数据请求地址
    String api_url = "http://api01.idataapi.cn:8000/article/idataapi?KwPosition=3&appCode=kb.qq.com,qq.com&size=20&catLabel1=科技&publishDateRange=1577923200,1580515200&createDateRange=1577923200,1580515200&apikey=yoxcL1hSwUUdmeVPE0t3CdLrmQWVrCWcItCfwgfP92TSLi7L2I1ocC6YxIkBeMBg";
}
