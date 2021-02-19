package com.toolmvplibrary.activity_root;

public interface InterCallBack<R> {
     void result(R res);
     void err(String msg);
}
