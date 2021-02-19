package com.toolmvplibrary.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.toolmvplibrary.R;


public class DialogStyleMy extends Dialog {
    private Context context;
    private Button positiveButton;
    private Button negativeButton;
    private Button neutralbutton;

    private View layout_positiveButton;
    private View layout_negativeButton;
    private View layout_neutralbutton;


    private String posistr = "";
    private String negastr = "";
    private String neutralstr = "";

    private DialogListener dialogListener;
    private TextView titleTextView;

    public DialogStyleMy(Context context, View view, String posistr, DialogListener dialogListener) {
        this(context, view, posistr, "", dialogListener);
    }

    public DialogStyleMy(Context context, View view, String posistr, String negastr, DialogListener dialogListener) {
        this(context, view, posistr, negastr, "", dialogListener);
    }

    public DialogStyleMy(Context context, View view, String posistr, String negastr, String neutralstr, DialogListener dialogListener) {
        super(context, R.style.MyDialog);
        this.context = context;
        this.posistr = posistr;
        this.negastr = negastr;
        this.neutralstr = neutralstr;
        this.dialogListener = dialogListener;
        this.setContentView(view);
        setTitle("");
    }


    public DialogStyleMy(Context context, String str, String posistr, String negastr, String neutralstr, DialogListener dialogListener) {
        super(context, R.style.MyDialog);
        this.context = context;

        this.posistr = posistr;
        this.negastr = negastr;
        this.neutralstr = neutralstr;
        this.dialogListener = dialogListener;
        this.setContentView(setDefMessage(context, str));
        setTitle("");
    }

    private View setDefMessage(Context context, String str) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dialog_message, null);
        TextView dg_message = view.findViewById(R.id.dg_message);
        dg_message.setText(str);
        return view;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void setTitle(@Nullable CharSequence title) {
//        super.setTitle(title);
        titleTextView.setText(title);
    }

    public void setPositiveButtonColor(int color){
        positiveButton.setTextColor(context.getResources().getColor(color));
    }

    @Override
    public void setContentView(View chilview) {
        View viewparent = LayoutInflater.from(context).inflate(R.layout.dialog_parentlayoutthreebutton, null);
        LinearLayout contentLayout = viewparent.findViewById(R.id.content_layout);
        titleTextView = viewparent.findViewById(R.id.title);
        positiveButton = viewparent.findViewById(R.id.positivebutton);
        positiveButton.setText(posistr);

        layout_positiveButton = viewparent.findViewById(R.id.layout_positivebutton);
        layout_negativeButton = viewparent.findViewById(R.id.layout_negativebutton);
        layout_neutralbutton = viewparent.findViewById(R.id.layout_neutralbutton);


        if (TextUtils.isEmpty(posistr)) {
            layout_positiveButton.setVisibility(View.GONE);
        } else {
            layout_positiveButton.setVisibility(View.VISIBLE);
        }
        negativeButton = viewparent.findViewById(R.id.negativebutton);
        negativeButton.setText(negastr);
        if (TextUtils.isEmpty(negastr)) {
            layout_negativeButton.setVisibility(View.GONE);
        } else {
            layout_negativeButton.setVisibility(View.VISIBLE);
        }
        neutralbutton = viewparent.findViewById(R.id.neutralbutton);
        neutralbutton.setText(neutralstr);
        if (TextUtils.isEmpty(neutralstr)) {
            layout_neutralbutton.setVisibility(View.GONE);
        } else {
            layout_neutralbutton.setVisibility(View.VISIBLE);
        }
        neutralbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogListener.click(neutralstr);
            }
        });
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogListener.click(negastr);
            }
        });
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogListener.click(posistr);
            }
        });
        LinearLayout.LayoutParams pareams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        contentLayout.addView(chilview, pareams);
        super.setContentView(viewparent);
        setdialogwidth(context);
    }

    public void changeBtnTxt(String posistr, String negastr, String neutralstr) {
        this.posistr = posistr;
        this.negastr = negastr;
        this.neutralstr = neutralstr;
        positiveButton.setText(posistr);
        if (TextUtils.isEmpty(posistr)) {
            layout_positiveButton.setVisibility(View.GONE);
        } else {
            layout_positiveButton.setVisibility(View.VISIBLE);
        }
        negativeButton.setText(negastr);
        if (TextUtils.isEmpty(negastr)) {
            layout_negativeButton.setVisibility(View.GONE);
        } else {
            layout_negativeButton.setVisibility(View.VISIBLE);
        }
        neutralbutton.setText(neutralstr);
        if (TextUtils.isEmpty(neutralstr)) {
            layout_neutralbutton.setVisibility(View.GONE);
        } else {
            layout_neutralbutton.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置dialog的宽
     *
     * @param context
     */
    private void setdialogwidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = (display.getWidth() / 11) * 10; // 设置宽度
        lp.gravity = Gravity.CENTER;
        this.getWindow().setAttributes(lp);
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(true);
        this.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                    return true;
                }
                return false;
            }
        });
    }


}