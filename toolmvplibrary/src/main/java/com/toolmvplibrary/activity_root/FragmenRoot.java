package com.toolmvplibrary.activity_root;

import android.app.Dialog;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.toolmvplibrary.R;

import java.util.ArrayList;
import java.util.List;

public abstract class FragmenRoot<P extends RootPresenter> extends Fragment implements RootInterUi {
    protected abstract P createPresenter();

    protected P presenter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = createPresenter();
        if (presenter != null) {
            presenter.attUIView(this);
        }
    }

//    @Deprecated
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        presenter = createPresenter();
//        if (presenter != null) {
//            presenter.attUIView(this);
//        }
//    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
    }

    private Toast toast;

    public void showToast(final String str) {
        Log.i("znh_root", "@=" + str);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (toast == null) {
                    toast = Toast.makeText(getContext(), str, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                } else {
                    toast.setText(str);
                }
                toast.show();
            }
        });
    }


    private Dialog progressDialog;

    public void showLoading() {
        Log.i("znh_root", "@=show_progress");
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressDialog == null) {
                    progressDialog = new Dialog(getContext());
                    progressDialog.setContentView(R.layout.layout_top_loadng);
                    progressDialog.getWindow().setBackgroundDrawableResource(R.drawable.share_loading_black70);
                    progressDialog.setCanceledOnTouchOutside(false);
                }
                progressDialog.show();
            }
        });
    }


    public void hitLoading() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
            }
        });
    }

    @Override
    public void hitKeyBox() {

    }


    //###########################################################################################


    private PopupWindow popupWindowBase;
    private ListView listContentBase;
    private TextView toCancelBase;
    private View rootView;
    private AdapterPopBase adapterPopBase;
    private List<String> dataListBase = new ArrayList<>();
    private ItemClick interListener;

    public void showPopWindow(View view, List<String> dl, ItemClick listener) {
        this.interListener = listener;
        if (popupWindowBase == null) {
            popupWindowBase = new PopupWindow(getContext());
            View pView = LayoutInflater.from(getContext()).inflate(R.layout.popupwindow_base, null);
            rootView = pView.findViewById(R.id.rootView);
            listContentBase = pView.findViewById(R.id.listContent);
            toCancelBase = pView.findViewById(R.id.toCancel);
            popupWindowBase.setContentView(pView);
            popupWindowBase.setOutsideTouchable(true);
            popupWindowBase.setBackgroundDrawable(new BitmapDrawable());
            popupWindowBase.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            popupWindowBase.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            popupWindowBase.setOutsideTouchable(true);
            adapterPopBase = new AdapterPopBase(dataListBase);
            listContentBase.setAdapter(adapterPopBase);
            listContentBase.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    popupWindowBase.dismiss();
                    if (interListener != null) {
                        interListener.itemClick(position);
                    }
                }
            });
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindowBase.dismiss();
                }
            });
            toCancelBase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindowBase.dismiss();
                }
            });
        }
        this.dataListBase.clear();
        this.dataListBase.addAll(dl);
        adapterPopBase.notifyDataSetChanged();
        popupWindowBase.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }
}
