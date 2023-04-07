package com.appforysy.activity.activity_main.student;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appforysy.R;
import com.appforysy.utils.ToolTitleLayout;
import com.appforysy.wheel.SimpleWheelAdapter;
import com.appforysy.wheel.WheelView;
import com.google.android.material.tabs.TabLayout;

public class StudentFragmentJava extends Fragment {

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        requireActivity().getLifecycle().addObserver(new LifecycleEventObserver() {
            @Override
            public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                if (event.getTargetState() == Lifecycle.State.CREATED) {
//                    activity 创建

//                    注销observer
                    getLifecycle().removeObserver(this);
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_student, container, false);
        return root;
    }

    private ToolTitleLayout toolTitleLayout;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bgImageView = view.findViewById(R.id.bgImageView);
        bgImageView.setImageResource(R.mipmap.img_guide_1);
        toolTitleLayout = view.findViewById(R.id.titleLayout);
        toolTitleLayout.hitBack();
        toolTitleLayout.setTitle("学习");
        initListData();
        initContent();
    }


    private ImageView bgImageView;


    private int alpht = 500;

    public void initListData() {
//        ObjectAnimator  img1alpha = ObjectAnimator.ofFloat(bgImageView, View.ALPHA, 1f, 0f);
//        img1alpha.setDuration(alpht);
        ObjectAnimator img2alphaShow = ObjectAnimator.ofFloat(bgImageView, View.ALPHA, 0.2f, 1f, 0.2f);
        img2alphaShow.setDuration(5000);
        img2alphaShow.setRepeatMode(ValueAnimator.RESTART);
        img2alphaShow.setRepeatCount(Animation.INFINITE);
        img2alphaShow.addListener(animListenr1);

        AnimatorSet anset = new AnimatorSet();
//        anset.addListener(animListenr1);
        anset.play(img2alphaShow);
        anset.start();

    }

    private Animator.AnimatorListener animListenr1 = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
            Log.i("znh", "onAnimationStart-------");
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            Log.i("znh", "onAnimationEnd#####");
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            Log.i("znh", "onAnimationCancel@@@@@@@");
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
            Log.i("znh", "onAnimationRepeat@@@@@@@");
            count++;
            if (count % 4 == 0) {
                bgImageView.setImageResource(R.mipmap.img_guide_1);
            } else if (count % 4 == 1) {
                bgImageView.setImageResource(R.mipmap.img_guide_2);
            } else if (count % 4 == 2) {
                bgImageView.setImageResource(R.mipmap.img_guide_3);
            } else if (count % 4 == 3) {
                bgImageView.setImageResource(R.mipmap.img_guide_4);
            }

        }
    };
    private int count = 0;

    //    --------------------------------
    public void initContent() {
        initView();
        initEvent();
        initData();
    }

    private String[] titles = new String[]{"最新", "热门", "我的"};

    private void initData() {
        for (int i = 0; i < titles.length; i++) {
            tabLayout.addTab(tabLayout.newTab());
            tabLayout.getTabAt(i).setText(titles[i]);
//            tabLayout.getTabAt(i).setCustomView(titles[i]);
        }

//      tabLayout.setupWithViewPager(viewPager,false);
//        simpleWheelAdapter=new SimpleWheelAdapter();
//        wheelView.setAdapter(simpleWheelAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals("热门")) {
                    initPopWindow();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initEvent() {

    }

    private RecyclerView recyclerContentView;
    private LinearLayoutManager linearLayoutManager;
    private AdapterIntructRecycler adapter;
    private TabLayout tabLayout;

    private void initView() {
        recyclerContentView = getActivity().findViewById(R.id.recyclerContentView);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        adapter = new AdapterIntructRecycler();
        recyclerContentView.setLayoutManager(linearLayoutManager);
        recyclerContentView.setAdapter(adapter);
        tabLayout = getActivity().findViewById(R.id.tableBar);
    }

    //    ###########################
    SimpleWheelAdapter simpleWheelAdapter;
    WheelView wheelView;
    PopupWindow popWindown;

    private void initPopWindow() {
        if (popWindown == null) {
            popWindown = new PopupWindow(getActivity());
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.pop_window_select, null);
            wheelView = view.findViewById(R.id.wheelView);
            popWindown.setContentView(view);
            popWindown.setWidth(RecyclerView.LayoutParams.MATCH_PARENT);
            popWindown.setHeight(RecyclerView.LayoutParams.WRAP_CONTENT);
            WheelView.WheelAdapter adapter = new WheelView.WheelAdapter() {
                @Override
                public int getItemCount() {
                    return 15;
                }

                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(LayoutInflater inflater, int viewType) {
                    View view = inflater.inflate(R.layout.item_text_view, null);
                    return new ItemHolder(view);
                }

                @Override
                public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                    ItemHolder hd = (ItemHolder) holder;
                    hd.tv.setText(position + "");
                }

                class ItemHolder extends RecyclerView.ViewHolder {
                    public TextView tv;

                    public ItemHolder(@NonNull View itemView) {
                        super(itemView);
                        tv = itemView.findViewById(R.id.textView);
                    }
                }
            };
            wheelView.setAdapter(adapter);
            popWindown.setOutsideTouchable(true);
        }
        popWindown.showAsDropDown(tabLayout);
    }

}
