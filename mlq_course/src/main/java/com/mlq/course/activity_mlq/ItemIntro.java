package com.mlq.course.activity_mlq;

public class ItemIntro {
    public ItemIntro(){

    }
    public ItemIntro(String id,int imgResource,String title,String content,String remark,String organization,String location,String price,String courseClass,String paht){
        this.id=id;
        this.imgResource=imgResource;//图标
        this.title=title;//标题
        this.content=content;//内容
        this.remark=remark;
        this.organization=organization;
        this.location=location;
        this.price=price;
        this.courseClass=courseClass;
        this.videoPath=paht;
    }

//    类型
    public int type;

    //    课程信息/教室基本信息
    public int imgResource;//图标
    public String title;//标题
    public String content;//内容
    public String id;
    public String remark;
    public String organization;
    public String location;
    public String price;
    public String courseClass;
    public String videoPath;
//############################################
public String id2;
    public int imgResource2;
    public String title2;
    public String content2;
//###################################
//标题类型
    public int titleType;
}
