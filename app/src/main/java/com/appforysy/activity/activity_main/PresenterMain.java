package com.appforysy.activity.activity_main;

import com.toolmvplibrary.activity_root.PresenterRoot;
import com.appforysy.ModelCom;
import com.appforysy.R;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PresenterMain extends PresenterRoot<InterUiMain, ModelCom> {
   private List<ItemMainBtn> dataList=new ArrayList<>();

   public PresenterMain(){

   }
    public void initData(){
        ItemMainBtn item=new ItemMainBtn();
        item.text=context.getResources().getString(R.string.title_home);
        item.img=R.mipmap.ic_launcher;
        dataList.add(item);
        ItemMainBtn item1=new ItemMainBtn();
        item1.img=R.mipmap.ic_launcher;
        item1.text=context.getResources().getString(R.string.title_notifications);
        dataList.add(item1);
        ItemMainBtn item2=new ItemMainBtn();
        item2.img=R.mipmap.ic_launcher;
        item2.text=context.getResources().getString(R.string.title_dashboard);
        dataList.add(item2);
        ItemMainBtn item3=new ItemMainBtn();
        item3.img=R.mipmap.ic_launcher;
        item3.text=context.getResources().getString(R.string.title_work);
        dataList.add(item3);
        ItemMainBtn item4=new ItemMainBtn();
        item4.img=R.mipmap.ic_launcher;
        item4.text=context.getResources().getString(R.string.title_my);
        dataList.add(item4);

    }

    @Nullable
    public List<ItemMainBtn> getBtnData() {
        return dataList;
    }
}
