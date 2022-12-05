package com.appforysy.activity.activity_main.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appforysy.R;
import com.appforysy.activity.activity_perfect.ActivityPerfect;
import com.appforysy.activity.activity_shuerte.ActiviyShuErTe;
import com.appforysy.utils.ItemInfo;
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
        return root;
    }

    private Banner mainBannerYsy;
    private RecyclerView recyclerViewMainFra;
    private AdapterMainFra adapterMainFra;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
    }

    public List<ItemInfo> initListData() {
        List<ItemInfo> itemList = new ArrayList<>();
        ItemInfo shuerte = new ItemInfo();
        shuerte.img = R.mipmap.img_shuerte;
        shuerte.text = "舒尔特方格";
        shuerte.remark = "舒尔特方格，画一张有25个小方格的表格，将1~25的数字顺序打乱，填在表格里面，然后以最快的速度从1数到25，要边读边指出，一人指读一人帮忙计时";
        shuerte.nextClass = ActiviyShuErTe.class;
        itemList.add(shuerte);
        ItemInfo info2 = new ItemInfo(R.mipmap.ic_launcher,"Demo", "半成品入口");
        info2.setNextClass(ActivityPerfect.class);
        itemList.add(info2);
        return itemList;
    }

}
