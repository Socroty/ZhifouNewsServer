public interface ServerConstant {
    //服务器数据请求端口
    int post_info_port = 3011;
    //服务器图片请求端口
    int post_image_port = 3012;
    //数据库地址
    String mysql_address = "jdbc:mysql://localhost:3306/zhifounews_db?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    //数据库用户名
    String mysql_user = "root";
    //数据库密码
    String mysql_password = "root";
    //服务器远程关闭指令
    String listen_stop = "listen_stop";
    //图片存储文件夹路径
    String image_local = "D:\\Projects\\AppCache\\";
    //数据获取请求地址
    String api_url = "http://api01.idataapi.cn:8000/article/idataapi?KwPosition=3&appCode=qq.com&size=20&catLabel1=体育&publishDateRange=1593734400,1596326400&createDateRange=1593734400,1596326400&apikey=yoxcL1hSwUUdmeVPE0t3CdLrmQWVrCWcItCfwgfP92TSLi7L2I1ocC6YxIkBeMBg";
}