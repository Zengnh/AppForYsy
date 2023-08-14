package com.appforysy.activity.activity_main.imgcard;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.appforysy.R;
import com.cutimageview.EditImageView;

import java.io.IOException;
import java.io.InputStream;
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
        initView();
    }

    //    -----------------------------------------------------------------------------------------------------------------------
    private EditImageView mEditImageView;
    private SeekBar mBrightnessBar;
    private SeekBar mContrastBar;

    private void initView() {
        initEditView();
        initSeekBar();
        initViewBtn();
    }

    private void initSeekBar() {
        mBrightnessBar = (SeekBar) getView().findViewById(R.id.activity_main_brightness_seek_bar);
        mBrightnessBar.setOnSeekBarChangeListener(mOnBrightnessSeekBarChangeListener);

        mContrastBar = (SeekBar) getView().findViewById(R.id.activity_main_contrast_seek_bar);
        mContrastBar.setOnSeekBarChangeListener(mOnContrastSeekBarChangeListener);
    }

    private Bitmap getBitmap() {
        try {
            InputStream flowerStream = getResources().getAssets().open("flower.jpg");
            Bitmap bitmap = BitmapFactory.decodeStream(flowerStream);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void initEditView() {
        mEditImageView = (EditImageView) getView().findViewById(R.id.activity_main_edit_image);
        Bitmap bitmap = getBitmap();
        mEditImageView.setImage(bitmap);
    }

    public void withdrawClick(View view) {
        mEditImageView.withDraw();
        dismissSeekBar();
    }

    public void penClick(View view) {
        mEditImageView.drawLine();
        dismissSeekBar();
    }

    public void rotateClick(View view) {
        mEditImageView.rotate();
        dismissSeekBar();
    }

    public void reverseXClick(View view) {
        mEditImageView.reverseX();
        dismissSeekBar();
    }

    public void reverseYClick(View view) {
        mEditImageView.reverseY();
        dismissSeekBar();
    }

    public void brightnessClick(View view) {
        showBrightnessBar();
        dismissContrastBar();
    }

    public void contrastClick(View view) {
        showContrastBar();
        dismissBrightnessBar();
    }

    private void dismissSeekBar() {
        dismissContrastBar();
        dismissBrightnessBar();
    }

    private void showBrightnessBar() {
        mBrightnessBar.setVisibility(View.VISIBLE);
    }

    private void dismissBrightnessBar() {
        mBrightnessBar.setVisibility(View.GONE);
    }

    private void showContrastBar() {
        mContrastBar.setVisibility(View.VISIBLE);
    }

    private void dismissContrastBar() {
        mContrastBar.setVisibility(View.GONE);
    }

    private SeekBar.OnSeekBarChangeListener mOnBrightnessSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            mEditImageView.brightness(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            mEditImageView.brightnessDone(seekBar.getProgress());
        }
    };

    private SeekBar.OnSeekBarChangeListener mOnContrastSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            mEditImageView.contrast(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            mEditImageView.contrastDone(seekBar.getProgress());
        }
    };
    private ImageView withdrawView, penView, rotateView, reverseXView, reverseYView, brightnessView, contrastView;

    private void initViewBtn() {
        withdrawView = getView().findViewById(R.id.withdrawView);
        penView = getView().findViewById(R.id.penView);
        rotateView = getView().findViewById(R.id.rotateView);
        reverseXView = getView().findViewById(R.id.reverseXView);
        reverseYView = getView().findViewById(R.id.reverseYView);
        brightnessView = getView().findViewById(R.id.brightnessView);
        contrastView = getView().findViewById(R.id.contrastView);

        withdrawView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withdrawClick(v);
            }
        }); penView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
penClick(v);
            }
        }); rotateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            rotateClick(v);
            }
        });reverseXView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
reverseXClick(v);
            }
        });reverseYView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            reverseYClick(v);
            }
        });brightnessView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            brightnessClick(v);
            }
        });contrastView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contrastClick(v);
            }
        });
    }
}
