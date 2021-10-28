package com.mykotlin.activity.activity_bluetouch;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.mykotlin.R;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class ActivityBluetooth extends AppCompatActivity {
    public static final String BT_UUID = "00001101-0000-1000-8000-00805F9B34FB";//uuid
    private int REQUEST_BT_ENABLE_CODE = 123;
    private BluetoothAdapter mBluetoothAdapter;//蓝牙适配器
    private BlueToothStateReceiver mReceiver;//广播接收器

    Button startScreen;
    TextView textView;
    //    #############################
    AcceptThread mAcceptThread;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetouch);
        openBT();
        textView = findViewById(R.id.textView);
        startScreen = findViewById(R.id.startScreen);
        startScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchDevice();
            }
        });

    }

//    点击了某个蓝牙后指向连接
    private ConnectThread mConnectThread;
    public void starClient(BluetoothDevice device) {
        mConnectThread = new ConnectThread(device);
        mConnectThread.start();
    }

    //    private ConnectThread mConnectThread; //客户端线程
//    private AcceptThread mAcceptThread; //服务端线程

    //    打开蓝牙
    private void openBT() {
        if (mBluetoothAdapter == null) {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        //1.设备不支持蓝牙，结束应用
        if (mBluetoothAdapter == null) {
            finish();
            return;
        }
        //2.判断蓝牙是否打开
        if (!mBluetoothAdapter.enable()) {
            //没打开请求打开
            Intent btEnable = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(btEnable, REQUEST_BT_ENABLE_CODE);
        } else {
            mAcceptThread = new AcceptThread();
            mAcceptThread.start();
        }
        registerRec();
    }


    private void registerRec() {
        //3.注册蓝牙广播
        mReceiver = new BlueToothStateReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);//搜多到蓝牙
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);//搜索结束
        registerReceiver(mReceiver, filter);
    }

    public void searchDevice() {
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.startDiscovery();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ActivityBluetooth.this, "收索时间到了。停止", Toast.LENGTH_SHORT).show();
                stopSearch();
            }
        }, 5000);
    }

    public void stopSearch() {
        if (mBluetoothAdapter != null && mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
    }

    String strList = "扫描结果：";

    //    接收收索结果
    class BlueToothStateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
//            Toast.makeText(MainActivity.this, "触发广播", Toast.LENGTH_SHORT).show();
            String action = intent.getAction();
            switch (action) {
                case BluetoothDevice.ACTION_FOUND:
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    strList += "\n" + device.getName() + device.getAddress();
                    textView.setText(strList);
//                    if (mRvAdapter != null) {
//                        mRvAdapter.addDevice(device);
//                    }
                    break;
                case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
//                    mMessageAdapter.addMessage("搜索结束");
                    break;
            }
        }
    }

    //    服务端#########################################
    class AcceptThread extends Thread {
        private BluetoothServerSocket mServerSocket;
        private BluetoothSocket mSocket;
        private InputStream btIs;
        private OutputStream btOs;
        private PrintWriter writer;
        private boolean canAccept;
        private boolean canRecv;

        public AcceptThread() {
            canAccept = true;
            canRecv = true;
        }

        @Override
        public void run() {
            try {
                //获取套接字
                BluetoothServerSocket temp = mBluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord("TEST", UUID.fromString(BT_UUID));
                mServerSocket = temp;
                //监听连接请求 -- 作为测试，只允许连接一个设备
                if (mServerSocket != null) {
                    // while (canAccept) {
                    mSocket = mServerSocket.accept();//阻塞等待客户端连接
                    sendHandlerMsg("有客户端连接");
                    // }
                }
                //获取输入输出流
                btIs = mSocket.getInputStream();
                btOs = mSocket.getOutputStream();
                //通讯-接收消息
                BufferedReader reader = new BufferedReader(new InputStreamReader(btIs, "UTF-8"));
                String content = null;
                while (canRecv) {
                    content = reader.readLine();
                    sendHandlerMsg("收到消息：" + content);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (mSocket != null) {
                        mSocket.close();
                    }
                    // btIs.close();//两个输出流都依赖socket，关闭socket即可
                    // btOs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    sendHandlerMsg("错误：" + e.getMessage());
                }
            }
        }

        private void sendHandlerMsg(String content) {
//            Message msg = mHandler.obtainMessage();
//            msg.what = 1001;
//            msg.obj = content;
//            mHandler.sendMessage(msg);
        }

        public void write(String msg) {
            if (btOs != null) {
                try {
                    if (writer == null) {
                        writer = new PrintWriter(new OutputStreamWriter(btOs, "UTF-8"), true);
                    }
                    writer.println(msg);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    writer.close();
                    sendHandlerMsg("错误：" + e.getMessage());
                }
            }
        }
    }

    //客户端###########################################################################
    class ConnectThread extends Thread {
        private BluetoothDevice mDevice;
        private BluetoothSocket mSocket;
        private InputStream btIs;
        private OutputStream btOs;
        private boolean canRecv;
        private PrintWriter writer;

        public ConnectThread(BluetoothDevice device) {
            mDevice = device;//被点击设备
            canRecv = true;
        }

        @Override
        public void run() {
            if (mDevice != null) {
                try {
                    //获取套接字
                    BluetoothSocket temp = mDevice.createInsecureRfcommSocketToServiceRecord(UUID.fromString(BT_UUID));
                    //mDevice.createRfcommSocketToServiceRecord(UUID.fromString(BT_UUID));//sdk 2.3以下使用
                    mSocket = temp;
                    //发起连接请求
                    if (mSocket != null) {
                        mSocket.connect();
                    }
                    sendHandlerMsg("连接 " + mDevice.getName() + "成功！");
                    //获取输入输出流
                    btIs = mSocket.getInputStream();
                    btOs = mSocket.getOutputStream();

                    //通讯-接收消息
                    BufferedReader reader = new BufferedReader(new InputStreamReader(btIs, "UTF-8"));
                    String content = null;
                    while (canRecv) {
                        content = reader.readLine();
                        sendHandlerMsg("收到消息：" + content);
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                    sendHandlerMsg("错误：" + e.getMessage());
                } finally {
                    try {
                        if (mSocket != null) {
                            mSocket.close();
                        }
                        //btIs.close();//两个输出流都依赖socket，关闭socket即可
                        //btOs.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        sendHandlerMsg("错误：" + e.getMessage());
                    }
                }
            }
        }

        private void sendHandlerMsg(String content) {
//            Message msg = mHandler.obtainMessage();
//            msg.what = 1001;
//            msg.obj = content;
//            mHandler.sendMessage(msg);
        }

        public void write(String msg) {
            if (btOs != null) {
                try {
                    if (writer == null) {
                        writer = new PrintWriter(new OutputStreamWriter(btOs, "UTF-8"), true);
                    }
                    writer.println(msg);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    writer.close();
                    sendHandlerMsg("错误：" + e.getMessage());
                }
            }
        }
    }

}
