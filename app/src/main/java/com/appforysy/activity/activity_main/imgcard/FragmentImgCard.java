package com.appforysy.activity.activity_main.imgcard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.appforysy.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentImgCard extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_img_card, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        return view;
    }

    RecyclerView recyclerView;
    private MyAdapterV2 mAdapter;
    private List<Integer> myDataset = new ArrayList<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myDataset.add(R.mipmap.img_guide_1);
        myDataset.add(R.mipmap.img_guide_2);
        myDataset.add(R.mipmap.img_guide_3);
        myDataset.add(R.mipmap.img_guide_4);

        myDataset.add(R.mipmap.img_guide_1);
        myDataset.add(R.mipmap.img_guide_2);
        myDataset.add(R.mipmap.img_guide_3);
        myDataset.add(R.mipmap.img_guide_4);

        myDataset.add(R.mipmap.img_guide_1);
        myDataset.add(R.mipmap.img_guide_2);
        myDataset.add(R.mipmap.img_guide_3);
        myDataset.add(R.mipmap.img_guide_4);

        myDataset.add(R.mipmap.img_guide_1);
        myDataset.add(R.mipmap.img_guide_2);
        myDataset.add(R.mipmap.img_guide_3);
        myDataset.add(R.mipmap.img_guide_4);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        mAdapter = new MyAdapterV2(myDataset, getContext(), new MyAdapterV2.OnUserPickDelegate() {
            @Override
            public void onClickPick(ItemUserDetail item) {

            }
        });
        recyclerView.setAdapter(mAdapter);
        //吸附效果
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(recyclerView);
    }
}
