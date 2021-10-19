package com.mlq.course.activity_mlq;
import com.mlq.course.R;
import com.mlq.course.ToolDataInfo;
import com.toolmvplibrary.activity_root.RootPresenter;
import java.util.ArrayList;
import java.util.List;
public class PresenterCollege extends RootPresenter<InterfactCollege, DataModel> {
    @Override
    public DataModel createModel() {
        return new DataModel();
    }

    private List<ItemIntro> dataListIntro = new ArrayList<>();

    public List<ItemIntro> getDataList() {
        return dataListIntro;
    }

    public void initData() {
        dataListIntro.clear();

        ItemIntro title = new ItemIntro();
        title.type = MYLTYPE.TITLE;
        title.titleType = MYLTYPE.COURSE;
        title.title = "课程简介：";
        dataListIntro.add(title);


        ItemIntro course = new ItemIntro();
        course.type = MYLTYPE.COURSE;
        course.id= ToolDataInfo.course01;
        course.imgResource = R.mipmap.course_01;
        course.content = "形象美学进阶培训课程，*教学老师教学，手把手教学，全方位帮助学员掌握形象美学专";
        course.title = "形象美学进阶培训课程";
        dataListIntro.add(course);

        ItemIntro course2 = new ItemIntro();
        course2.type = MYLTYPE.COURSE;
        course2.id= ToolDataInfo.course01;
        course2.imgResource = R.mipmap.course_02;
        course2.content = "形象美学基础培训课程，业内**授课辅导，理论＋实操活学活用，循序渐进学习，帮";
        course2.title = "形象美学基础培训课程";
        dataListIntro.add(course2);


        ItemIntro info = new ItemIntro();
        info.type = MYLTYPE.INFOIMG;
        info.imgResource = R.mipmap.introduct_01;
        info.title = "学院简介";
        dataListIntro.add(info);

//#######################################################################

        ItemIntro tInfo = new ItemIntro();
        tInfo.type = MYLTYPE.TITLE;
        tInfo.title = "师资推荐：";
        tInfo.titleType = MYLTYPE.TEACHER;
        dataListIntro.add(tInfo);


        ItemIntro teacher = new ItemIntro();
        teacher.type = MYLTYPE.TEACHER;
        teacher.imgResource = R.mipmap.teacher_01;
        teacher.id=ToolDataInfo.teacher01;
        teacher.id2=ToolDataInfo.teacher02;
        teacher.title = "厦门沐灵茜美妆学院导";
        teacher.content = "王江铃，沐灵茜国际美妆学院运营总监，沐灵茜国";
        teacher.imgResource2 = R.mipmap.teacher_02;
        teacher.title2 = "厦门沐灵茜美妆学院导";
        teacher.content2 = "傅丽娟，国家高级*师，*美业教育联盟*化妆师，*";
        dataListIntro.add(teacher);


        ItemIntro teacher1 = new ItemIntro();
        teacher1.type = MYLTYPE.TEACHER;
        teacher1.imgResource = R.mipmap.teacher_03;
        teacher1.title = "厦门沐灵茜美妆学院导";
        teacher1.id=ToolDataInfo.teacher03;
        teacher1.id2=ToolDataInfo.teacher04;
        teacher1.content = "敏容老师，国家职业技能鉴定考评员、国家职业技";
        teacher1.imgResource2 = R.mipmap.teacher_04;
        teacher1.title2 = "厦门沐灵茜美妆学院导";
        teacher1.content2 = "亦婷老师，沐灵茜国际美妆学院形体礼仪导师，形";
        dataListIntro.add(teacher1);


        ItemIntro info2 = new ItemIntro();
        info2.type = MYLTYPE.INFOIMG;
        info2.imgResource = R.mipmap.introduct_10;
        info2.title = "教学环境";
        dataListIntro.add(info2);
        ItemIntro info3 = new ItemIntro();
        info3.type = MYLTYPE.INFOIMG;
        info3.imgResource = R.mipmap.introduct_09;
        info3.title = "彩妆教室";
        dataListIntro.add(info3);
    }
}
