package com.rootlibs.retrofitpack;

import android.text.TextUtils;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class HttpLoggingInterceptor implements Interceptor {
    private final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String requestContent = "发送请求: \n请求方式：" + request.method()
                + "\n请求地址：" + request.url()
                + "\n请求头：" + request.headers();
        RequestBody requestBody = request.body();
        String body = "";
        if (requestBody != null) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            Charset charset = UTF8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            body = buffer.readString(charset);
        }
        requestContent += "\n请求参数: " + body;

        long startTime = System.currentTimeMillis();
        Response response = chain.proceed(chain.request());
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        requestContent += "\n请求结果：" + content;
        requestContent += "\n请求时长：" + duration;

        try {
            if (content.startsWith("{") && content.endsWith("}")) {
                JSONObject jsonRoot = new JSONObject(content);
                if (!jsonRoot.optString("statusCode").equals("0000")) {
                    jsonRoot.put("content", null);
                } else {
                    String str = jsonRoot.optString("content");
                    if (TextUtils.isEmpty(str)) {
                        jsonRoot.put("content", null);
                    }
                }
                content = jsonRoot.toString();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        log(requestContent);
        return response.newBuilder()
                .body(ResponseBody.create(mediaType, content))
                .build();
    }

    public void log(String str) {
        if (showLog) {
            Log.i("ylhk_req", str);
        }
    }

    private boolean showLog = false;

    public void setLevel(boolean b) {
        this.showLog = b;
    }
}
