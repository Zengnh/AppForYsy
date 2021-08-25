package com.appforysy.activity.activity_perfect;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.appforysy.R;
import com.appforysy.activity.activity_banner.ActivityBanner;
import com.appforysy.activity.activity_intruct.ActivityIntruct;
import com.appforysy.activity.activity_login.ActivityLogin;
import com.cameralib.ActivityCamrea;

import java.util.ArrayList;
import java.util.List;

public class AdapterViewPager extends PagerAdapter {
    private String[] title;
    private List<List<ItemPagerContent>> listContent = new ArrayList<>();

    public AdapterViewPager(String[] title) {
        this.title = title;
        listContent.clear();
        for (int i = 0; i < title.length; i++) {
            List<ItemPagerContent> content = new ArrayList<>();
            if (i == 0) {
                ItemPagerContent item = new ItemPagerContent();
                item.icon = R.mipmap.icon_red_action;
                item.itemName = "开始";
                item.nextClass = ActivityIntruct.class;
                content.add(item);


                ItemPagerContent item2 = new ItemPagerContent();
                item2.icon = R.mipmap.icon_red_add;
                item2.itemName = "添加";
                item2.nextClass = ActivityBanner.class;
                content.add(item2);

                ItemPagerContent item3 = new ItemPagerContent();
                item3.icon = R.mipmap.icon_red_at;
                item3.itemName = "@TA";
                item2.nextClass = ActivityLogin.class;
                content.add(item3);

                ItemPagerContent item4 = new ItemPagerContent();
                item4.icon = R.mipmap.icon_red_buycar;
                item4.itemName = "购物车";
                content.add(item4);

                ItemPagerContent item5 = new ItemPagerContent();
                item5.icon = R.mipmap.icon_red_heard;
                item5.itemName = "爱心";
                item5.nextClass = ActivityCamrea.class;
                content.add(item5);
            } else if (i == 1) {

            } else if (i == 3) {

            } else {

            }
            listContent.add(content);
        }
    }

    @Override
    public int getCount() {
        return title.length;
    }

    //判断是否是否为同一张图片，这里返回方法中的两个参数做比较就可以
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    //设置viewpage内部东西的方法，如果viewpage内没有子空间滑动产生不了动画效果
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_main_view_pager, container, false);
        RecyclerView pagerRecycler = view.findViewById(R.id.pagerRecycler);

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(container.getContext(), LinearLayoutManager.HORIZONTAL, false);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(container.getContext(), 3);

        AdapterRecycler adapter = new AdapterRecycler(listContent.get(position));
        pagerRecycler.setLayoutManager(linearLayoutManager);
        pagerRecycler.setAdapter(adapter);

        container.addView(view);
        //最后要返回的是控件本身
        return view;


//        TextView textView = new TextView(container.getContext());
//        textView.setText(title[position]);
//        textView.setGravity(Gravity.CENTER);
//        container.addView(textView);
//        //最后要返回的是控件本身
//        return textView;
    }

    //因为它默认是看三张图片，第四张图片的时候就会报错，还有就是不要返回父类的作用
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        //super.destroyItem(container, position, object);
    }

    //目的是展示title上的文字，
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }


}
