package com.rootlibs.downloader;





//   implementation 'com.liulishuo.filedownloader:library:1.7.7'

import android.app.Application;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

public class ToolFileDownLoader  {

    private ToolFileDownLoader(){}
    private static ToolFileDownLoader instance;
    public static ToolFileDownLoader with(){
//        FileDownloader.setupOnApplicationOnCreate();
        //InitCustomMaker, 否则你只需要在使用FileDownloader之前的任意时候调用
//         FileDownloader.setup(context);//即可。
        if(instance==null){
            instance=new ToolFileDownLoader();
        }
        return instance;
    }

    public void init(Application application){
        FileDownloader.setupOnApplicationOnCreate(application);
    }

    private ResultDownLoader resultBean=new ResultDownLoader();
    public void downLoadFile(String downUrl,String locadPath,InterListener listener){
        resultBean.setListener(listener);
        FileDownloader.getImpl().create(downUrl)
                .setPath(locadPath)

                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
//                        resultBean.code=2;
                        resultBean.setProgressResult(totalBytes,soFarBytes,task.getTargetFilePath());
//                        Log.i("znh_download","loading  "+totalBytes+"   "+soFarBytes);

                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                    }

                    @Override
                    protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
//                        resultBean.code=0;
                        resultBean.netPath=task.getUrl();
                        resultBean.locatiFile=task.getPath();
                        resultBean.finalComp();
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
//                        resultBean.code=-1;
                        resultBean.err=e.getMessage();
                        resultBean.setErrResult(task.getUrl(),task.getPath());
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                    }
                }).start();
    }
}
