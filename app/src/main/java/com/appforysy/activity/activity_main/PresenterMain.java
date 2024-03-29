package com.appforysy.activity.activity_main;

import com.appforysy.activity.activity_main.device.PdfRendererBasicFragment;
import com.appforysy.activity.activity_main.home.HomeFragmentJava;
import com.appforysy.activity.activity_main.imgcard.FragmentImgCard;
import com.appforysy.activity.activity_main.my.FragmentMy;
import com.appforysy.activity.activity_main.book.FragmentBookJava;
import com.toolmvplibrary.activity_root.ItemInfo;
import com.toolmvplibrary.activity_root.RootPresenter;
import com.appforysy.ModelCom;
import com.appforysy.R;
import com.appforysy.activity.activity_main.information.FragmentInformation;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PresenterMain extends RootPresenter<InterUiMain, ModelCom> {
    private List<ItemInfo> dataList = new ArrayList<>();

    @Override
    public ModelCom createModel() {
        return null;
    }

    public void initData() {
        ItemInfo item = new ItemInfo();
        item.text = getContext().getResources().getString(R.string.title_home);
        item.fra=new HomeFragmentJava();
        item.img = R.mipmap.ic_launcher;
        item.imgSel = R.mipmap.home_select;
        item.imgNor = R.mipmap.home_unselect;
        dataList.add(item);


        ItemInfo item2 = new ItemInfo();
        item2.img = R.mipmap.ic_launcher;
//        item2.text = getContext().getResources().getString(R.string.title_dashboard);
        item2.text = "书本";
        item2.fra=new FragmentBookJava();
        item2.imgSel = R.mipmap.order_select;
        item2.imgNor = R.mipmap.order_unselect;
        dataList.add(item2);

        ItemInfo item3 = new ItemInfo();
        item3.img = R.mipmap.ic_launcher;
        item3.text = getContext().getResources().getString(R.string.title_work);
        item3.fra=new FragmentInformation();
        item3.imgSel = R.mipmap.msg_sel;
        item3.imgNor = R.mipmap.msg_nor;
        dataList.add(item3);

        ItemInfo item4 = new ItemInfo();
        item4.img = R.mipmap.ic_launcher;
        item4.fra=new FragmentMy();
        item4.text = getContext().getResources().getString(R.string.title_my);
        item4.imgSel = R.mipmap.my_select;
        item4.imgNor = R.mipmap.my_unselect;
        dataList.add(item4);

        ItemInfo item1 = new ItemInfo();
        item1.img = R.mipmap.ic_launcher;
        item1.text = getContext().getResources().getString(R.string.title_notifications);
        item1.imgSel = R.mipmap.cat_select;
        item1.imgNor = R.mipmap.cat_unselect;
        item1.fra=new PdfRendererBasicFragment();
        dataList.add(item1);
    }

    @Nullable
    public List<ItemInfo> getBtnData() {
        return dataList;
    }

}
