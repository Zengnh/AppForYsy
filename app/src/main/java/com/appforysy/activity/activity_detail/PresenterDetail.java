package com.appforysy.activity.activity_detail;

import android.text.TextUtils;
import android.util.Log;

import com.photolib.ToolThreadPool;
import com.toolmvplibrary.activity_root.RootModel;
import com.toolmvplibrary.activity_root.RootPresenter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;

public class PresenterDetail extends RootPresenter {
    @Override
    public RootModel createModel() {
        return null;
    }

    public void sendMsg(String str) {
        ToolThreadPool.getInstance().exeRunable(new Runnable() {
            @Override
            public void run() {
                if (socket != null && socket.isConnected()) {
                    try {
//                      out.println(str.getBytes("GBK"));
                      out.println(str);

//                        String line = in.readLine();
//                        Log.i("znh", "得到数据：" + line);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    initConnect();
                }
            }
        });

    }

    public void initView() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket("192.168.3.100", 10010);
                    // socket.setSoTimeout(10000);//设置10秒超时
                    Log.i("Android", "与服务器建立连接：" + socket);
                    BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String line = br.readLine();
                    Log.i("Android", "得到数据：" + line);

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                    Log.i("Android", "err：" + e.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.i("Android", "err：" + e.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("Android", "err：" + e.getMessage());
                }
            }
        }).start();
    }

    Socket socket;
    BufferedReader in;
    PrintWriter out;

    public void initConnect() {


        ToolThreadPool.getInstance().exeRunable(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket("192.168.3.100", 10010);
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    out = new PrintWriter(socket.getOutputStream(), true);
                    String line = in.readLine();
                    Log.i("znh", "得到数据：" + line);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    boolean socketClose = true;

    public void closeSocket() {
        try {
            socketClose = false;
            if (socket != null) {
                in.close();
                out.close();
                socket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
