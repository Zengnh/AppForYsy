package com.mlq.course;

import com.mlq.course.activity_mlq.ItemIntro;
import com.mlq.course.activity_mlq.MYLTYPE;

import java.util.ArrayList;
import java.util.List;

public class ToolDataInfo {
    private ToolDataInfo() {
        initCourse();
        initTeacher();
    }

    private static volatile ToolDataInfo instance;

    public static ToolDataInfo getInstance() {
        if (instance == null) {
            synchronized (ToolDataInfo.class) {
                if (instance == null) {
                    instance = new ToolDataInfo();
                }
            }
        }
        return instance;
    }

    public ItemIntro getCutCourse(String cid) {
        for (int i = 0; i < courseData.size(); i++) {
            if (courseData.get(i).id.equals(cid)) {
                return courseData.get(i);
            }
        }
        return null;
    }

    private List<ItemIntro> courseData = new ArrayList<>();

    public void initCourse() {
        courseData.clear();
        courseData.add(new ItemIntro(course01, R.mipmap.course_01, "形象美学进阶培训课程",
                "形象美学进阶培训课程，*教学老师教学，手把手教学，全方位帮助学员掌握形象", "",
                "厦门沐灵茜国际美妆学院", "厦门校区", "联系客服", "早班，晚班，周末班", ""));
        courseData.add(new ItemIntro(course02, R.mipmap.course_02, "形象美学基础培训课程",
                " 形象美学基础培训课程形象美学基础培训课程，业内**授课辅导，理论＋实操活学活用，循序渐进学习，", "",
                "厦门沐灵茜国际美妆学院", "厦门校区", "联系客服", "早班，晚班，周末班", ""));
        courseData.add(new ItemIntro(course03, R.mipmap.course_03, "高级搭配师培训课程",
                "高级搭配师培训课程，本培训零基础教学，主要针对美学爱好者，化妆师、纹绣师", "",
                "厦门沐灵茜国际美妆学院", "厦门校区", "联系客服", "早班，晚班，周末班", ""));
        courseData.add(new ItemIntro(course04, R.mipmap.course_04, "专业形体培训课程",
                "专业形体培训课程，沐灵茜*教学老师一对一教学，理论实践结合培训，致力于打", "",
                "厦门沐灵茜国际美妆学院", "厦门校区", "联系客服", "早班，晚班，周末班", ""));
        courseData.add(new ItemIntro(course05, R.mipmap.course_05, "高级*创业培训课程",
                "高级*创业培训课程，培训课时2个月，专业老师手把手教学，讲练结合，锻炼提升", "",
                "厦门沐灵茜国际美妆学院", "厦门校区", "联系客服", "早班，晚班，周末班", ""));
        courseData.add(new ItemIntro(course06, R.mipmap.course_06, "皮肤管理培训课程",
                "皮肤管理培训课程，培训课时7天，教学生动有趣，课堂活跃，从理论讲解到实际", "",
                "厦门沐灵茜国际美妆学院", "厦门校区", "联系客服", "早班，晚班，周末班", ""));

    }


    public static final String course01 = "course01";
    public static final String course02 = "course02";
    public static final String course03 = "course03";
    public static final String course04 = "course04";
    public static final String course05 = "course05";
    public static final String course06 = "course06";


//    ###################################################################

    public ItemIntro getCutTeacher(String cid) {
        for (int i = 0; i < teacherData.size(); i++) {
            if (teacherData.get(i).id.equals(cid)) {
                return teacherData.get(i);
            }
        }
        return null;
    }

    private List<ItemIntro> teacherData = new ArrayList<>();
    public static final String teacher01 = "teacher01";
    public static final String teacher02 = "teacher02";
    public static final String teacher03 = "teacher03";
    public static final String teacher04 = "teacher04";
    public static final String teacher05 = "teacher05";
    public static final String teacher06 = "teacher06";

    public void initTeacher(){
        teacherData.clear();

        ItemIntro teacher = new ItemIntro();
        teacher.type = MYLTYPE.TEACHER;
        teacher.imgResource = R.mipmap.teacher_01;
        teacher.title = "王江铃";
        teacher.id = ToolDataInfo.teacher01;
        teacher.content = "厦门沐灵茜美妆学院导，沐灵茜国际美妆学院运营总监，沐灵茜国际美妆学院职业规划导师，一素YISU品牌联合创始人，香港国际*业管理与认证协会厦门分会会长，香港国际美业认证中心考评员，*美业教育联盟副秘书长，*美业教育联盟技能培训中心副主任，国家高级纹绣师，IBCA国际注册高级纹绣师";
        teacherData.add(teacher);

        ItemIntro teacher2 = new ItemIntro();
        teacher2.imgResource = R.mipmap.teacher_02;
        teacher2.id = ToolDataInfo.teacher02;
        teacher2.title = "傅丽娟";
        teacher2.content = "厦门沐灵茜美妆学院导，国家高级*师，*美业教育联盟*化妆师，*美业教育联盟技能培训中心化妆讲师，香港国际美业认证中心化妆评审，IBCA国际注册高级化妆师，CVEQC*职业教育资格认证高级化妆讲师，亚洲美业网签约化妆师，厦门大学特约化妆师";
        teacherData.add(teacher2);


        ItemIntro teacher3 = new ItemIntro();
        teacher3.type = MYLTYPE.TEACHER;
        teacher3.imgResource = R.mipmap.teacher_03;
        teacher3.id = ToolDataInfo.teacher03;
        teacher3.title = "敏容";
        teacher3.content = "厦门沐灵茜美妆学院导，国家职业技能鉴定考评员、国家职业技能竞赛裁判员，第二届亚洲美业大赛评委，第三届*国际纹饰艺术万人大赛特邀评委，*美业教育联盟*纹绣总监，香港国际美业认证中心纹绣考评员IBCA国际注册高级纹绣师2016年海西纹绣大赛评委";
        teacherData.add(teacher3);

        ItemIntro teacher4 = new ItemIntro();
        teacher4.imgResource = R.mipmap.teacher_04;
        teacher4.title = "亦婷";
        teacher4.id = ToolDataInfo.teacher04;
        teacher4.content = "厦门沐灵茜美妆学院导，沐灵茜国际美妆学院形体礼仪导师，形体礼仪课程总策划，ACI注册国家二级心理咨询师，ACIC美国认证协会国际形体礼仪培训师，高级私人形象管理师，*国际美业认证协会形体礼仪设计考评员";
        teacherData.add(teacher4);

        ItemIntro teacher5 = new ItemIntro();
        teacher5.type = MYLTYPE.TEACHER;
        teacher5.imgResource = R.mipmap.teacher_05;
        teacher5.id = ToolDataInfo.teacher05;
        teacher5.title = "万松源";
        teacher5.content = "厦门沐灵茜美妆学院导，沐灵茜国际美妆学院创业导师，PT冰瓷牙技术教官，PT超瓷牙贴面运营总监，香港·一米阳光国际贸易*董事长，*美牙师职业资格考核站长，德国牙科工业协会指定P.A美学修复专家，GIDE国际牙科美学临床大师，国际口腔*修复高级技术顾问，英国ITEC国际化妆师文凭/*导师，国家二级化妆技师，目前专注牙齿*项目：针对氟斑牙/烟渍牙/茶渍牙/牙菌斑，/遗传性黄牙/增龄性黄牙的牙齿美白修复，有着上千个修复美白案例，深受爱美人士的好评。从事美业10年，专注于五官美学研究和美学技术开发，提倡规范化标准化操作，对畸形牙，问题性黑黄牙修复，缺损修复等具有较好的心得和经验积累！专业严谨的教学取得万千学员的爱戴。\n" +
                "  2009年进入美业于蒙妮坦职业培训学校担任化妆导师\n" +
                "2013.14.15*受欢迎老师\n" +
                "2013年考取ITEC国际化妆师文凭\n" +
                "2014担任ITEC国际化妆师文凭考试功课导师\n" +
                "2015福建省*化妆美甲大赛冠军教练\n" +
                "2016年创立PT.美业\n" +
                "2016年创立PT.perfect tooth完美的牙齿品牌\n" +
                "2016年创立*午休式美牙品牌PT.超瓷牙贴面\n" +
                "2016年创立香港一米阳光国际贸易*\n" +
                "2017年创立国际美牙师职业技能鉴定\n" +
                "2017年担任*美牙师职业技能鉴定站总站长\n" +
                "2018年**届美牙师大赛创始人，*个将牙贴面雕刻技术带上比赛竞技舞台的人！\n" +
                "2018年**届美牙大赛执行主席";
        teacherData.add(teacher5);

        ItemIntro teacher6 = new ItemIntro();
        teacher6.imgResource = R.mipmap.teacher_06;
        teacher6.title = "姜茂伦";
        teacher6.id = ToolDataInfo.teacher06;
        teacher6.content = "厦门沐灵茜美妆学院导，\n" +
                "姜茂伦，沐灵茜国际美妆学院美甲高级讲师，*美业教育联盟联合创始成员，PT超瓷牙贴面高级导师，国家职业资格认证高级美甲技术技师，全球ITEC国际美甲师文凭，厦门国际蒙妮坦美甲讲师，广州国家“美甲冠军杯”特邀评委，福建省美甲大赛冠军，厦漳泉美甲大赛冠军，厦门市技术能手称号";
        teacherData.add(teacher6);


    }
}
