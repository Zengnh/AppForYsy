package com.appforysy.activity.mlqcollege.activity_mlq_teacher_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appforysy.R;
import com.appforysy.activity.mlqcollege.MLQTitle;
import com.appforysy.activity.mlqcollege.ToolDataInfo;
import com.appforysy.activity.mlqcollege.activity_mlq.ItemIntro;
import com.appforysy.activity.mlqcollege.activity_mlq.MYLTYPE;
import com.toolmvplibrary.activity_root.ActivityRoot;

import java.util.ArrayList;
import java.util.List;

public class ActivityMLQTeacherList extends ActivityRoot {

    public static void startAct(Context context) {
        Intent intent = new Intent(context, ActivityMLQTeacherList.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mlq_teacher_list);
        initView();
        initData();
    }

    private GridLayoutManager linearLayoutManager;
    private MLQTitle titleLayout;

    private void initView() {

        contentRecycler = findViewById(R.id.contentRecycler);
        linearLayoutManager = new GridLayoutManager(this, 2);
        contentRecycler.setLayoutManager(linearLayoutManager);

        titleLayout = findViewById(R.id.titleLayout);
        titleLayout.setTitle("师资力量");
        titleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private List<ItemIntro> dataListIntro = new ArrayList<>();

    private void initData() {
        ItemIntro teacher = new ItemIntro();
        teacher.type = MYLTYPE.TEACHER;
        teacher.imgResource = R.mipmap.teacher_01;
        teacher.title = "王江铃";
        teacher.id = ToolDataInfo.teacher01;
        teacher.content = "厦门沐灵茜美妆学院导，沐灵茜国际美妆学院运营总监，沐灵茜国际美妆学院职业规划导师，一素YISU品牌联合创始人，香港国际*业管理与认证协会厦门分会会长，香港国际美业认证中心考评员，*美业教育联盟副秘书长，*美业教育联盟技能培训中心副主任，国家高级纹绣师，IBCA国际注册高级纹绣师";
        dataListIntro.add(teacher);

        ItemIntro teacher2 = new ItemIntro();
        teacher2.imgResource = R.mipmap.teacher_02;
        teacher2.id = ToolDataInfo.teacher02;
        teacher2.title = "傅丽娟";
        teacher2.content = "厦门沐灵茜美妆学院导，国家高级*师，*美业教育联盟*化妆师，*美业教育联盟技能培训中心化妆讲师，香港国际美业认证中心化妆评审，IBCA国际注册高级化妆师，CVEQC*职业教育资格认证高级化妆讲师，亚洲美业网签约化妆师，厦门大学特约化妆师";
        dataListIntro.add(teacher2);


        ItemIntro teacher3 = new ItemIntro();
        teacher3.type = MYLTYPE.TEACHER;
        teacher3.imgResource = R.mipmap.teacher_03;
        teacher3.id = ToolDataInfo.teacher03;
        teacher3.title = "敏容";
        teacher3.content = "厦门沐灵茜美妆学院导，国家职业技能鉴定考评员、国家职业技能竞赛裁判员，第二届亚洲美业大赛评委，第三届*国际纹饰艺术万人大赛特邀评委，*美业教育联盟*纹绣总监，香港国际美业认证中心纹绣考评员IBCA国际注册高级纹绣师2016年海西纹绣大赛评委";
        dataListIntro.add(teacher3);

        ItemIntro teacher4 = new ItemIntro();
        teacher4.imgResource = R.mipmap.teacher_04;
        teacher4.title = "亦婷";
        teacher4.id = ToolDataInfo.teacher04;
        teacher4.content = "厦门沐灵茜美妆学院导，沐灵茜国际美妆学院形体礼仪导师，形体礼仪课程总策划，ACI注册国家二级心理咨询师，ACIC美国认证协会国际形体礼仪培训师，高级私人形象管理师，*国际美业认证协会形体礼仪设计考评员";
        dataListIntro.add(teacher4);

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
        dataListIntro.add(teacher5);

        ItemIntro teacher6 = new ItemIntro();
        teacher6.imgResource = R.mipmap.teacher_06;
        teacher6.title = "姜茂伦";
        teacher6.id = ToolDataInfo.teacher06;
        teacher6.content = "厦门沐灵茜美妆学院导，\n" +
                "姜茂伦，沐灵茜国际美妆学院美甲高级讲师，*美业教育联盟联合创始成员，PT超瓷牙贴面高级导师，国家职业资格认证高级美甲技术技师，全球ITEC国际美甲师文凭，厦门国际蒙妮坦美甲讲师，广州国家“美甲冠军杯”特邀评委，福建省美甲大赛冠军，厦漳泉美甲大赛冠军，厦门市技术能手称号";
        dataListIntro.add(teacher6);


        adapter = new AdapterTeacherList(dataListIntro);
        contentRecycler.setAdapter(adapter);
    }

    private RecyclerView contentRecycler;
    private AdapterTeacherList adapter;
}
