package com.appforysy.activity.activity_time_note;

import com.appforysy.R;
import java.util.ArrayList;
import java.util.List;

public class ToolColor {
    private List<Integer> colorList=new ArrayList<>();
    private ToolColor(){
        colorList.add(R.color.content1);
        colorList.add(R.color.content2);
        colorList.add(R.color.content3);
        colorList.add(R.color.content4);
        colorList.add(R.color.content5);
        colorList.add(R.color.content6);
        colorList.add(R.color.content7);
        colorList.add(R.color.content8);
        colorList.add(R.color.content9);
        colorList.add(R.color.content10);
        colorList.add(R.color.content11);
        colorList.add(R.color.content12);
        colorList.add(R.color.content13);
        colorList.add(R.color.content14);
    }
    private static ToolColor instance;
    public static ToolColor getInstance(){
        if(instance==null){
            instance=new ToolColor();
        }
        return  instance;
    }
    public int getColor(int pos){
        int item=Math.abs(pos);
        if(pos<colorList.size()){

        }else{
            item=Math.abs(pos)%colorList.size();
        }

        return colorList.get(item);
    }

}
