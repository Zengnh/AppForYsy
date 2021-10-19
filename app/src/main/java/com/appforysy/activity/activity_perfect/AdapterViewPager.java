package com.appforysy.activity.activity_perfect;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.appforysy.R;
import com.appforysy.activity.activity_banner.ActivityBanner;
import com.appforysy.activity.activity_edt_note.ActivityNotiEdit;
import com.appforysy.activity.activity_guide.ActivityGuide;
import com.appforysy.activity.activity_intruct.ActivityIntruct;
import com.appforysy.activity.activity_login.ActivityLogin;
import com.appforysy.activity.draw_line.ActivityDrawVeiw;
import com.cameralib.ActivityCameraDemo;
import com.cameralib.ActivityCamrea;
import com.medialib.ActivityVideoTrim;
import com.mlq.course.activity_mlq.ActivityMLQCollege;
import com.mykotlin.activity.activity_music.ActivityMusic;
import com.photolib.selectpic.ActivityLocalImageList;
import com.photolib.selectpic.ToolToSelectPic;

import java.util.ArrayList;
import java.util.List;

public class AdapterViewPager extends PagerAdapter {
    private String[] title;
    private List<List<ItemPagerContent>> listContent = new ArrayList<>();
    private int[] iconAll = {
            R.mipmap.icon_red_action, R.mipmap.icon_red_add, R.mipmap.icon_red_at, R.mipmap.icon_red_write
            , R.mipmap.icon_red_buycar, R.mipmap.icon_red_chat, R.mipmap.icon_red_connect, R.mipmap.icon_red_count
            , R.mipmap.icon_red_cut, R.mipmap.icon_red_delete, R.mipmap.icon_red_distory, R.mipmap.icon_red_email
            , R.mipmap.icon_red_flag, R.mipmap.icon_red_heard, R.mipmap.icon_red_home, R.mipmap.icon_red_infoa
            , R.mipmap.icon_red_link, R.mipmap.icon_red_list, R.mipmap.icon_red_lock, R.mipmap.icon_red_music
            , R.mipmap.icon_red_mygod, R.mipmap.icon_red_no, R.mipmap.icon_red_phone, R.mipmap.icon_red_point
            , R.mipmap.icon_red_save, R.mipmap.icon_red_search, R.mipmap.icon_red_set, R.mipmap.icon_red_singlechat
            , R.mipmap.icon_red_start, R.mipmap.icon_red_ti, R.mipmap.icon_red_todown, R.mipmap.icon_red_toleft
            , R.mipmap.icon_red_toright, R.mipmap.icon_red_toup, R.mipmap.icon_red_tool, R.mipmap.icon_red_true
            , R.mipmap.icon_red_user, R.mipmap.icon_red_voice, R.mipmap.icon_red_what, R.mipmap.icon_red_wifi};


    public AdapterViewPager(String[] title) {
        this.title = title;
        listContent.clear();
        for (int i = 0; i < title.length; i++) {
            List<ItemPagerContent> content = new ArrayList<>();
            if (i == 0) {
                ItemPagerContent mlqApp = new ItemPagerContent();
                mlqApp.icon = R.mipmap.ic_launcher_mlq;
                mlqApp.itemName = "沐林茜学院";
                mlqApp.nextClass = ActivityMLQCollege.class;
                content.add(mlqApp);
                ItemPagerContent itemCamera = new ItemPagerContent();
                itemCamera.icon = R.mipmap.ic_lunacher_camera;
                itemCamera.itemName = "拍照";
                itemCamera.nextClass = ActivityCamrea.class;
                content.add(itemCamera);

                ItemPagerContent destory = new ItemPagerContent();
                destory.icon = R.mipmap.icon_select_app;
                destory.itemName = "选择图片/视频";
                destory.nextClass = ActivityLocalImageList.class;
                ToolToSelectPic.create().setLitmitPic(9);
                content.add(destory);


            } else if (i == 1) {
                ItemPagerContent itemCamera = new ItemPagerContent();
                itemCamera.icon = R.mipmap.ic_lunacher_camera;
                itemCamera.itemName = "拍照";
                itemCamera.nextClass = ActivityCameraDemo.class;
                content.add(itemCamera);


                ItemPagerContent delete = new ItemPagerContent();
                delete.icon = R.mipmap.icon_red_delete;
                delete.itemName = "删除";
                content.add(delete);

                ItemPagerContent edit = new ItemPagerContent();
                edit.icon = R.mipmap.icon_red_cut;
                edit.itemName = "编辑";
                edit.nextClass = ActivityNotiEdit.class;
                content.add(edit);

                ItemPagerContent search = new ItemPagerContent();
                search.icon = R.mipmap.icon_red_search;
                search.itemName = "视频编辑";
                search.nextClass = ActivityVideoTrim.class;
                content.add(search);

                ItemPagerContent item = new ItemPagerContent();
                item.icon = R.mipmap.icon_red_start;
                item.itemName = "教案";
                item.nextClass = ActivityIntruct.class;
                content.add(item);

                ItemPagerContent item2 = new ItemPagerContent();
                item2.icon = R.mipmap.icon_red_link;
                item2.itemName = "链接";
                item2.nextClass = ActivityBanner.class;
                content.add(item2);

                ItemPagerContent item3 = new ItemPagerContent();
                item3.icon = R.mipmap.icon_red_list;
                item3.itemName = "展示";
                item3.nextClass = ActivityCameraDemo.class;

                content.add(item3);

                ItemPagerContent item4 = new ItemPagerContent();
                item4.icon = R.mipmap.icon_red_heard;
                item4.itemName = "关心";
                item4.nextClass = ActivityLogin.class;
                content.add(item4);

                ItemPagerContent item5 = new ItemPagerContent();
                item5.icon = R.mipmap.icon_red_flag;
                item5.itemName = "标记";
                item5.nextClass = ActivityDrawVeiw.class;
                content.add(item5);


            } else if (i == 2) {
                ItemPagerContent set = new ItemPagerContent();
                set.icon = R.mipmap.icon_red_set;
                set.itemName = "设置";
                content.add(set);

//                ItemPagerContent home = new ItemPagerContent();
//                home.icon = R.mipmap.icon_red_home;
//                home.itemName = "主页";
//                content.add(home);

                ItemPagerContent safe = new ItemPagerContent();
                safe.icon = R.mipmap.icon_red_save;
                safe.itemName = "安全";
                content.add(safe);
                ItemPagerContent wifi = new ItemPagerContent();
                wifi.icon = R.mipmap.icon_red_wifi;
                wifi.itemName = "WIFI";

                content.add(wifi);

                ItemPagerContent voice = new ItemPagerContent();
                voice.icon = R.mipmap.icon_red_voice;
                voice.itemName = "音乐";
                voice.nextClass = ActivityMusic.class;
                content.add(voice);

                ItemPagerContent help = new ItemPagerContent();
                help.icon = R.mipmap.icon_red_what;
                help.itemName = "帮助";
                help.nextClass = ActivityGuide.class;
                content.add(help);

            } else {
                ItemPagerContent star = new ItemPagerContent();
                star.icon = R.mipmap.icon_red_start;
                star.itemName = "星星";
                content.add(star);
                ItemPagerContent left = new ItemPagerContent();
                left.icon = R.mipmap.icon_red_toleft;
                left.itemName = "转向";

                content.add(left);
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
