package com.screenlib.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.screenlib.MainService;
import com.screenlib.R;
import com.screenlib.utils.UtilsService;

/**
 * Created by Zengnh on 2017/7/1.
 */

public class ActivityOpenState extends Activity {

    private Button btn_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityopenstate);
        btn_start=findViewById(R.id.btn_start);
        if(UtilsService.isServiceWork(this,"com.zeng.screentool.MainService")){
            toNext();
        }
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toStartService();
                toNext();
            }
        });
    }

    private void toStartService() {
        Intent intent = new Intent(this, MainService.class);
        startService(intent);
    }

    private void toNext(){
        Intent intent=new Intent(this, ScreenLibMainActivity.class);
        startActivity(intent);
        finish();
    }

}
