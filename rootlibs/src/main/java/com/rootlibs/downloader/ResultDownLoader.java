package com.rootlibs.downloader;


import com.rootlibs.result_root.Result;

public class ResultDownLoader extends Result {
    public int code;
    private InterListener listener;
    public long allSize, cutSize;
    public String locatiFile;
    public String netPath;
    public String err;
    public void setListener(InterListener listener) {
        this.listener = listener;
    }

    public void setProgressResult(long allSize, long cutSize, String locatiFile) {
        this.allSize = allSize;
        this.cutSize = cutSize;
        this.locatiFile = locatiFile;
        if(this.listener!=null){
            this.listener.resultFinish(this);
        }
    }
    public void setErrResult(String netPath, String locatiFile) {
        this.netPath = netPath;
        this.locatiFile = locatiFile;
        finalComp();
    }
    public void finalComp(){
        if(this.listener!=null){
            this.listener.resultFinish(this);
        }
    }
}
