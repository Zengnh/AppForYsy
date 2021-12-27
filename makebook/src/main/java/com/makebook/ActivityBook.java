package com.makebook;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.makebook.view.BookPageView;

public class ActivityBook extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        initView();
    }
    BookPageView bookPack;
    public void initView(){
        bookPack=findViewById(R.id.bookPack);
//        bookPack.setDefaultPath();
    }
}
