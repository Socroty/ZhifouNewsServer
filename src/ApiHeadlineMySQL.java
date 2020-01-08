import java.io.*;
import java.sql.*;

//图片流测试，未使用
public class ApiHeadlineMySQL {

    private static String text_1 = "　　新华社 北京2月22日电 中共中央政治局2月22日召开会议，讨论国务院拟提请第十三届全国人民代表大会第二次会议审议的《政府工作报告》稿，审议《关于2018年中央巡视工作领导小组重点工作情况报告》、《关于中央脱贫攻坚专项巡视情况的综合报告》和《党政领导干部考核工作条例》。中共中央总书记习近平主持会议。\n" +
            "　　会议认为，过去一年，面对复杂严峻的国际形势和艰巨繁重的改革发展稳定任务，以习近平同志为核心的党中央团结带领全党全国各族人民砥砺奋进、攻坚克难，完成全年经济社会发展主要目标任务，决胜全面建成小康社会取得新的重大进展。\n" +
            "　　会议强调，今年是新中国成立70周年，是全面建成小康社会、实现第一个百年奋斗目标的关键之年。面对新形势新任务新挑战，做好政府工作，要在以习近平同志为核心的党中央坚强领导下，以习近平新时代中国特色社会主义思想为指导，全面贯彻党的十九大和十九届二中、三中全会精神，统筹推进“五位一体”总体布局，协调推进“四个全面”战略布局，坚持稳中求进工作总基调，坚持新发展理念，坚持推动高质量发展，坚持以供给侧结构性改革为主线，坚持深化市场化改革、扩大高水平开放，加快建设现代化经济体系，继续打好三大攻坚战，着力激发微观主体活力，创新和完善宏观调控，统筹推进稳增长、促改革、调结构、惠民生、防风险工作，保持经济运行在合理区间，进一步稳就业、稳金融、稳外贸、稳外资、稳投资、稳预期，提振市场信心，增强人民群众获得感、幸福感、安全感，保持经济持续健康发展和社会大局稳定，为全面建成小康社会收官打下决定性基础，以优异成绩庆祝中华人民共和国成立70周年。\n" +
            "　　会议指出，实现今年经济社会发展目标任务，要统筹实施好宏观政策、结构性政策、社会政策，落实好积极的财政政策、稳健的货币政策和就业优先政策。要着力优化营商环境，培育壮大新动能，促进形成强大国内市场，推进脱贫攻坚和乡村振兴，促进区域协调发展，加强污染防治和生态文明建设，深化重点领域改革，推动全方位对外开放，更好保障和改善民生。要加强政府自身建设，坚决反对一切形式主义、官僚主义，崇尚实干，埋头苦干，努力干出无愧于时代和人民的新业绩。\n" +
            "　　会议对中央巡视工作领导小组2018年的工作给予肯定，同意其对2019年的工作安排。会议认为，过去一年，中央巡视工作忠诚履行党章赋予的职责，把“两个维护”作为根本任务，坚持发现问题和整改落实并重，取得了新成效。会议强调，要准确把握形势任务，深化监督规律认识，加强政治监督，深入查找贯彻落实党的路线方针政策和党中央重大决策部署存在的政治偏差，坚决整治形式主义、官僚主义。要坚持巡视工作方针，突出问题导向，把巡视与纪检、监察、组织、审计等监督贯通起来，高质量推进全覆盖。要建立巡视整改日常监督机制，推动整改常态化长效化。要完善巡视巡察格局，建立指导督导机制，促进上下联动、上下贯通。要加强规范化建设，使巡视工作更加有效。\n" +
            "　　会议强调，开展脱贫攻坚专项巡视非常必要。脱贫攻坚成效显著，但也存在一些共性问题，要用好专项巡视成果，强化整改落实，促进精准脱贫攻坚。各级党组织要强化责任担当，聚焦深度贫困地区脱贫攻坚，坚持目标标准不动摇，贯彻精准方略不懈怠，切实抓好今明两年脱贫攻坚工作。要结合巡视发现的问题，深入开展扶贫领域腐败和作风问题专项治理，加强贫困地区基层党建和干部队伍建设，优化脱贫攻坚政策，完善工作机制，确保如期完成脱贫攻坚任务。";
    private static String text_2 = "　　电影《流浪地球》除了科幻和特效，更在人文精神层面触动了观众，引发了业内人士的热烈讨论。\n" +
            "　　《流浪地球》自正月初一上映以来，就一直热度不减。截至2月21日，影片累计票房突破40亿元。而日前《阿凡达》导演詹姆斯·卡梅隆与《流浪地球》作者刘慈欣的对话也引起不小关注，被称为“科幻界双神对谈”。\n" +
            "　　《流浪地球》火了，关于中国电影是否走出了寒冬的争论声再次响起。尽管众说纷纭，但一部国产科幻电影能引起如此巨大的讨论和关注，本身值得庆贺。除了“流浪”的诗意与让人大开眼界的特效，影片中传递的华夏文明关于亲情、土地、家园的理念，更是在人文精神的层面触动了观众，引发了业内人士对这部电影的讨论。\n" +
            "　　看完电影后，著名剧作家、评论家赵葆华评价道：“无论是书写方式、制作方式都做了颠覆性、充满创新性的表达。无论电影工业化的成熟度，电影思维想象力的丰富度，类型化表达的成熟度，在国产电影创作上达到一个新高度。”他甚至盛赞这部电影是中国电影由电影大国走向电影强国一个标志性的作品。\n" +
            "　　“里程碑”“中国硬核科幻开山之作”等等，无论是普通观众，还是业内人士对《流浪地球》有口皆碑。所有的讨论中，一个共识则是，依托于刘慈欣天马行空的科幻文本，青年导演郭凡将其可视化的努力，让多数走进影院的人“惊艳”了一把。\n" +
            "　　然而，如此高的评价背后，是导演与制作团队的精益求精，甚至是超乎寻常的勇气与魄力。\n" +
            "　　鲜有人知道，这个大项目，在开始只有包括导演郭帆在内的两个人。\n" +
            "　　“因为缺乏经验，我们在做这个项目的时候其实每天都会遇到新的困难，最大的困难，是信任的问题，外界还是抱着一个怀疑的态度来审视，为什么是你？你有什么能力？你能不能做好一个科幻片？”\n" +
            "　　为了向外界证明自己和团队不仅有想法还有能力，郭帆和团队先做了故事大纲，写了剧本，并拿出了3000多张概念设计。经过不断的打磨，他们后来做了8000多张分镜头画稿。\n" +
            "　　“这样一步步，让我们所接触的合作伙伴可以看到一个关于电影大致的雏形，从而慢慢建立起信心。大家可以看到片尾字幕，我们的团队最后达到了7000多人。”这个过程中，导演郭帆心里也越来越有底了。\n" +
            "　　中国电影制片人协会理事长明振江说，“中国电影工业化起步较晚，近十多年发展迅速，但是制作水平、工业化标准还有相当的差距，《流浪地球》是中国电影产业转型升级的鲜明标志之作，在制作上为中国电影界树立起了标杆，特别是拍摄科幻题材电影最能代表一个国家的电影工业制作水平。”\n" +
            "　　他认为，《流浪地球》主创团队的一条成功经验是，不把钱花在流量明星上，而是用在电影场景、道具、特效、数字制作上。电影庞大的、宏大的制作过程，应当说是这部电影成功的基础，除了8000多分镜头的画稿，还有1万多件道具制作，2300个特效制作合成镜头，这些数字又不是一次可以完成，而是反复多次，制作团队为此花了大量的心思。\n" +
            "　　据导演介绍，该电影75%的特效是由国内团队来完成的，另外25%是韩国和德国的团队完成，其中“修改次数最多的一个镜头达到了251次，我们的团队可以说是尽了全力。”" ;
    private static String text_3 = "　　时光飞逝，今天已经是三下乡的第五天。\n" +
            "　　清晨，一条条精彩的朋友圈映入眼帘，敲打着我的脑袋把我叫醒。由于后勤组和财务组已经分工明确，各自都能处理好自己的事情，相比其他副队长，我这个副队长过得相对轻松，所以，让队长安排其它任务给我，去给其他组帮忙，我也觉得一个队伍互相帮忙，团结一致才能彼此快乐，这对队伍的建设也有利。\n" +
            "　　早上，来到学校这边，去看看我们支教的孩子们，我喜欢看他们埋头认真的样子，喜欢看他们接触到新知识那种欣喜的表情，喜欢看他们满眼天真地对我笑。然后再来新闻组看下有没有新稿子可以改。\n" +
            "　　下午，本来晴朗的天气说变就变，下雨下的毫不客气，但是仍然阻止不了我们举办室内比赛，画画比赛，孩子们的作品颠覆了我的想象力，呈现的是不一样的美。还举办了书法比赛，虽然我没有去看，但是我相信在我们的小老师教导之下字迹应该也是有各自的特色吧。\n" +
            "　　晚上回来的时候，夜深人静，跟新闻组的队友结伴回来，她们不时让我靠她们近一点，生怕我走丢。感受到他们的关心，我很开心。\n" +
            "　　完美的一天落幕，期待更美好的明天。";
    private static  String text_4 = "　　海外网2月26日电 当地时间26日凌晨，伊朗外交部长扎里夫(Mahammed Javad Zarif)在个人社交平台账号上发布消息，宣布辞去外长一职。随后有外媒推测扎里夫辞职或与美国对伊朗的敌意有关。\n" +
            "　　综合美国有线电视新闻网(CNN)和今日俄罗斯网报道，扎里夫在社交平台上发文称：“我真诚地为无法继续服务民众和在职期间的所有缺点道歉，”并补充说“要积极和乐观”，但并未说明辞职的理由。\n" +
            "　　随后伊朗外交部副发言人阿巴斯(Sayyed Abbas Moussawi)在接受伊朗国家通讯社的采访时证实了扎里夫辞职的消息。伊朗半官方性质的法尔斯通讯社也对此事进行报道，但称目前尚不清楚伊朗总统鲁哈尼是否接受请辞。\n" +
            "　　2019年1月就曾有多家伊朗当地媒体曝出伊朗将退出“伊核协议”、外长扎里夫辞职的消息，但很快伊朗外交部发表声明进行辟谣。\n" +
            "　　扎里夫辞职的原因引发外界猜测。有分析人士指出扎里夫辞职可能标志着伊朗同西方进行接触的外交政策宣告结束。分析人士称特朗普政府对伊朗持续的敌意，比如宣布退出“伊核协议”等，或是扎里夫辞职的原因之一。\n" +
            "　　“伊核协议”的支持者、伊朗裔美国人全国理事会前主席帕尔西(Trita Parsi)表示：“如果扎里夫被取代，至少可以说明一个层面的问题，即美国对‘伊核协议’的攻击将会引发伊朗的政治危机。”他还补充说未来“伊核协议”的另一位支持者，欧盟外交政策负责人莫格里尼(Federika Mogherini)也将下台，帕尔西告诫称：“这可能预示‘伊核协议’的前景将更加黑暗。”\n" +
            "　　不过帕尔西也强调虽然扎里夫已递交辞呈，但“并不意味着故事的终结”。前不久美国在波兰召开中东峰会讨论伊朗问题，对此扎里夫做出强硬回应，他的表现在伊朗国内也受到欢迎，因此辞职一事也要考虑伊朗民众的反应。\n" +
            "　　扎里夫自2013年以来一直担任伊朗外交部长一职，也是“伊核协议”的关键设计者之一。扎里夫1981年离开伊朗前往美国留学，1981年获美国旧金山州立大学本科学位，1984年获丹佛大学国际关系硕士学位，随后在1988年获丹佛大学博士学位。";
    private static String text_5 = "　　北京时间2月21日，俄克拉荷马雷霆官方宣布，球队已正式签下前锋马基夫-莫里斯。\n" +
            "　　在被鹈鹕队买断后，包括火箭、猛龙、篮网和湖人等球队都对莫里斯有兴趣，但最终雷霆得到了这名29岁的小前锋。\n" +
            "　　马基夫是在交易截止日前被奇才交易到鹈鹕的，当时鹈鹕送出从奇才得到莫里斯和一个2023年的次轮签。来到鹈鹕，莫里斯从未为球队出场过，随即被买断。";

    private static String[] text = {text_1,text_2,text_3,text_4,text_5};

    private static String[] title = {
            "头条：习近平会议谈兴国之要",
            "头条：人民日报评价流浪地球",
            "头条：美好的一天从清晨开始",
            "头条：伊朗外交部长宣布辞职",
            "头条：雷霆队即将签下莫里斯"};

    private static String[] background = {"#27252a","#1e272a","#2c2c07","#242230","#331a07"};

    // 定义数据库连接参数
    private static final String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/zhifou_test?useUnicode=true&amp;characterEncoding=UTF-8&&useSSL=false";
    private static final String USERNAME = "socroty";
    private static final String PASSWORD = "96985232";

    // 注册数据库驱动
    static {
        try {
            Class.forName(DRIVER_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            System.out.println("注册失败！");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ImageToDB();
        DBToImage();
    }

    // 将图片插入数据库
    private static void ImageToDB() {
        String path = "C:\\Users\\Socroty\\Pictures\\head_11.png";
        Connection conn = null;
        PreparedStatement ps = null;
        FileInputStream in;
        try {
            in = readImageFromLocal(path);
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "insert into headline (headline_id,headline_title,headline_content,headline_image,headline_background)values(?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, 1000000001);
            ps.setString(2, title[0]);
            ps.setString(3, text[0]);
            ps.setBinaryStream(4, in, in.available());
            ps.setString(5, background[0]);
            int count = ps.executeUpdate();
            if (count > 0) {
                System.out.println("插入成功！");
            } else {
                System.out.println("插入失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConn(conn);
            if (null != ps) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    // 读取数据库中图片
    private static void DBToImage() {
        String targetPath = "D:/1.png";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "select * from headline where headline_id =?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, 1000000001);
            rs = ps.executeQuery();
            while (rs.next()) {
                InputStream in = rs.getBinaryStream("headline_image");
                readImageFromDB(in, targetPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConn(conn);
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    // 读取本地图片获取输入流
    private static FileInputStream readImageFromLocal(String path) throws IOException {
        return new FileInputStream(new File(path));
    }

    // 读取表中图片获取输出流
    private static void readImageFromDB(InputStream in, String targetPath) {
        File file = new File(targetPath);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = in.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();

            System.out.println(len);
            System.out.println(fos);
            System.out.println(file);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 关闭连接
    private static void closeConn(Connection conn) {
        if (null != conn) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("关闭连接失败！");
                e.printStackTrace();
            }
        }
    }

}
