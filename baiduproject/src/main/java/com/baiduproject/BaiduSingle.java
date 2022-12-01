package com.baiduproject;

import android.os.Environment;
import android.util.Log;

import com.baidu.aip.bodyanalysis.AipBodyAnalysis;

import org.json.JSONObject;

import java.util.HashMap;

public class BaiduSingle {
    private BaiduSingle() {
        client = new AipBodyAnalysis(APP_ID, API_KEY, SECRET_KEY);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
    }

    private static volatile BaiduSingle instance;

    public static BaiduSingle getInstance() {
        if (instance == null) {
            synchronized (BaiduSingle.class) {
                if (instance == null) {
                    instance = new BaiduSingle();
                }
            }
        }
        return instance;
    }

    //设置APPID/AK/SK
    public static final String APP_ID = "27595608";
    public static final String API_KEY = "SAEGFvirYdvbHhMeqzWItYno";
    public static final String SECRET_KEY = "fY6FM6tqkB38qrPnWNgBSddV50P6ZSe1";
    private AipBodyAnalysis client;
    private String path;

    public void demoCheckImg() {
        // 初始化一个AipBodyAnalysis

        String file = Environment.getExternalStorageDirectory().toString();
        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 调用接口
//        String path = "test.jpg";


        new Thread(new Runnable() {
            @Override
            public void run() {
                path = file + "/testbaidu.jpg";
                JSONObject res = client.bodyAnalysis(path, new HashMap<String, String>());


                Log.i("znh_baidu", res.toString());
//                {"error_code":18,"error_msg":"Open api qps request limit reached"}
//                {"person_num":1,
//                "person_info":[
//                {"body_parts":
//                {"nose":{"score":0.8791580200195312,"x":196.5,"y":144.5000152587891},
//                "right_knee":{"score":0.859477698802948,"x":161.5,"y":439.5},
//                "left_hip":{"score":0.8304731249809265,"x":231.5,"y":324.5},
//                "right_ankle":{"score":0.861189603805542,"x":126.5000076293945,"y":539.5},
//                "right_wrist":{"score":0.8087793588638306,"x":146.5,"y":289.5},
//                "left_eye":{"score":0.9074234962463379,"x":201.5,"y":134.5000152587891},
//                "left_mouth_corner":{"score":0.8869384527206421,"x":196.5,"y":149.5000152587891},
//                "right_elbow":{"score":0.9131285548210144,"x":91.50000762939453,"y":249.5000152587891},
//                "left_knee":{"score":0.8699974417686462,"x":246.5,"y":439.5},
//                "neck":{"score":0.8809999227523804,"x":176.5,"y":174.5000152587891},
//                "top_head":{"score":0.8782498240470886,"x":186.5,"y":109.5000228881836},
//                "right_ear":{"score":0.9013270735740662,"x":161.5,"y":139.5000152587891},
//                "left_ear":{"score":0.8100696206092834,"x":206.5,"y":144.5000152587891},
//                "left_elbow":{"score":0.8399980068206787,"x":266.5,"y":194.5000152587891},
//                "right_shoulder":{"score":0.8942723274230957,"x":131.5,"y":189.5000152587891},
//                "right_eye":{"score":0.8610709309577942,"x":186.5,"y":134.5000152587891},
//                "right_mouth_corner":{"score":0.884694516658783,"x":186.5,"y":154.5000152587891},
//                "left_ankle":{"score":0.8236147165298462,"x":241.5,"y":539.5},
//                "right_hip":{"score":0.843207597732544,"x":186.5,"y":329.5},
//                "left_wrist":{"score":0.8214221000671387,"x":226.5,"y":154.5000152587891},
//                "left_shoulder":{"score":0.8510128259658813,"x":216.5,"y":189.5000152587891}},
//                "location":{"score":0.9985112547874451,"top":96.71743774414062,"left":76.45033264160156,
//                "width":216.2137756347656,
//                "height":512.1986083984375}}],
//                "log_id":1573202009049695530}

                if (listener != null) {
                    listener.checkResult(res.toString());
                }
            }
        }).start();
    }

    public void checkImage(byte[] image) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject res = client.bodyAnalysis(image, new HashMap<String, String>());
                if (listener != null) {
                    listener.checkResult(res.toString());
                }
            }
        }).start();
    }

    public BaiduInterface listener;

    public void setListener(BaiduInterface listener) {
        this.listener = listener;
    }

//    log_id	是	uint64	唯一的log id，用于问题定位
//    person_num	是	uint32	人体数目
//    person_info	是	object[]	人体姿态信息
//+location	是	object	人体坐标信息
//++height	是	float	人体区域的高度
//++left	是	float	人体区域离左边界的距离
//++top	是	float	人体区域离上边界的距离
//++width	是	float	人体区域的宽度
//++score	是	float	人体框的概率分数，取值0-1，得分越接近1表示识别准确的概率越大
//+body_parts	是	object	身体部位信息，包含21个关键点
//++top_head	是	object	头顶
//+++x	是	float	x坐标
//+++y	是	float	y坐标
//+++score	是	float	概率分数
//++left_eye	是	object	左眼
//+++x	是	float	x坐标
//+++y	是	float	y坐标
//+++score	是	float	概率分数
//++right_eye	是	object	右眼
//+++x	是	float	x坐标
//+++y	是	float	y坐标
//+++score	是	float	概率分数
//++nose	是	object	鼻子
//+++x	是	float	x坐标
//+++y	是	float	y坐标
//+++score	是	float	概率分数
//++left_ear	是	object	左耳
//+++x	是	float	x坐标
//+++y	是	float	y坐标
//+++score	是	float	概率分数
//++right_ear	是	object	右耳
//+++x	是	float	x坐标
//+++y	是	float	y坐标
//+++score	是	float	概率分数
//++left_mouth_corner	是	object	左嘴角
//+++x	是	float	x坐标
//+++y	是	float	y坐标
//+++score	是	float	概率分数
//++right_mouth_corner	是	object	右嘴角
//+++x	是	float	x坐标
//+++y	是	float	y坐标
//+++score	是	float	概率分数
//++neck	是	object	颈部
//+++x	是	float	x坐标
//+++y	是	float	y坐标
//+++score	是	float	概率分数
//++left_shoulder	是	object	左肩
//+++x	是	float	x坐标
//+++y	是	float	y坐标
//+++score	是	float	概率分数
//++right_shoulder	是	object	右肩
//+++x	是	float	x坐标
//+++y	是	float	y坐标
//+++score	是	float	概率分数
//++left_elbow	是	object	左手肘
//+++x	是	float	x坐标
//+++y	是	float	y坐标
//+++score	是	float	概率分数
//++right_elbow	是	object	右手肘
//+++x	是	float	x坐标
//+++y	是	float	y坐标
//+++score	是	float	概率分数
//++left_wrist	是	object	左手腕
//+++x	是	float	x坐标
//+++y	是	float	y坐标
//+++score	是	float	概率分数
//++right_wrist	是	object	右手腕
//+++x	是	float	x坐标
//+++y	是	float	y坐标
//+++score	是	float	概率分数
//++left_hip	是	object	左髋部
//+++x	是	float	x坐标
//+++y	是	float	y坐标
//+++score	是	float	概率分数
//++right_hip	是	object	右髋部
//+++x	是	float	x坐标
//+++y	是	float	y坐标
//+++score	是	float	概率分数
//++left_knee	是	object	左膝
//+++x	是	float	x坐标
//+++y	是	float	y坐标
//+++score	是	float	概率分数
//++right_knee	是	object	右膝
//+++x	是	float	x坐标
//+++y	是	float	y坐标
//+++score	是	float	概率分数
//++left_ankle	是	object	左脚踝
//+++x	是	float	x坐标
//+++y	是	float	y坐标
//+++score	是	float	概率分数
//++right_ankle	是	object	右脚踝
//+++x	是	float	x坐标
//+++y	是	float	y坐标
//+++score	是	float	概率分数

}
