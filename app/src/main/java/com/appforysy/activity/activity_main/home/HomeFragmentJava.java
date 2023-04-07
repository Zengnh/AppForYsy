package com.appforysy.activity.activity_main.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appforysy.R;
import com.appforysy.activity.activity_game.GAMEENUM;
import com.appforysy.activity.activity_img_main.ActivityImageMain;
import com.appforysy.activity.activity_game.ActivityGame;
import com.appforysy.activity.activity_perfect.ActivityPerfect;
import com.appforysy.activity.activity_game.fragment.game_shuerte.FragmentShuErTe;
import com.appforysy.activity.draw_image_view.ActivityDrawVeiw;
import com.toolmvplibrary.activity_root.ItemInfo;
import com.rootlibs.loadimg.ToolGlide;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.RectangleIndicator;

import java.util.ArrayList;
import java.util.List;

public class HomeFragmentJava extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mainBannerYsy = root.findViewById(R.id.mainBannerYsy);
        recyclerViewMainFra = root.findViewById(R.id.recyclerViewMainFra);
        imageEdit = root.findViewById(R.id.imageEdit);
        return root;
    }

    private ImageView imageEdit;
    private Banner mainBannerYsy;
    private RecyclerView recyclerViewMainFra;
    private AdapterMainFra adapterMainFra;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<Integer> imageList = new ArrayList();
        imageList.add(R.mipmap.banner_no1);
        imageList.add(R.mipmap.banner_no2);
        imageList.add(R.mipmap.banner_no3);
        imageList.add(R.mipmap.banner_no4);
        imageList.add(R.mipmap.banner_no5);
        imageList.add(R.mipmap.banner_no6);
        mainBannerYsy.setLoopTime(3000);

        mainBannerYsy.setAdapter(new BannerImageAdapter<Integer>(imageList) {

            @Override
            public void onBindView(BannerImageHolder holder, Integer data, int position, int size) {

                ToolGlide.loadImge(holder.imageView.getContext(), data, holder.imageView);

            }
        }).setIndicator(new RectangleIndicator(getActivity())).addBannerLifecycleObserver(getActivity());

        mainBannerYsy.start();
//  --------------------------------------------
        adapterMainFra = new AdapterMainFra(initListData());
        recyclerViewMainFra.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewMainFra.setAdapter(adapterMainFra);

//        ---------------------
        imageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ActivityPerfect.class);
                startActivity(intent);
            }
        });
    }

    public List<ItemInfo> initListData() {
        List<ItemInfo> itemList = new ArrayList<>();
        ItemInfo shuerte = new ItemInfo();
        shuerte.img = R.mipmap.icon_shuerte;
        shuerte.text = "舒尔特方格";
        shuerte.remark = "舒尔特方格，画一张有25个小方格的表格，将1~25的数字顺序打乱，填在表格里面，然后以最快的速度从1数到25，要边读边指出，一人指读一人帮忙计时";
        shuerte.nextClass = FragmentShuErTe.class;
        shuerte.type = 1;
        shuerte.attribute = GAMEENUM.SHUERTE.getAttibute();
        itemList.add(shuerte);

        ItemInfo changeImage = new ItemInfo(R.mipmap.icon_cutimage, "拼图游戏", "移动拼图，移动变换图片位置，最后拼成完整图片", 1, GAMEENUM.PINGTU.getAttibute());
        changeImage.setNextClass(ActivityGame.class);
        itemList.add(changeImage);

        ItemInfo changeImage2 = new ItemInfo(R.mipmap.icon_china_defi, "文字找不同", "找出不同的文字，训练专注力", 1, GAMEENUM.DIFFERENT.getAttibute());
        changeImage2.setNextClass(ActivityGame.class);
        itemList.add(changeImage2);



        ItemInfo drawerImage = new ItemInfo(R.mipmap.icon_drawer, "神兽绘本", "绘画工具");
        drawerImage.setNextClass(ActivityDrawVeiw.class);
        itemList.add(drawerImage);

//        ItemInfo info2 = new ItemInfo(R.mipmap.image_icon_all, "展示图片", "图片展示页面");
//        info2.setNextClass(ActivityImageMain.class);
//        itemList.add(info2);
        return itemList;
    }

}
