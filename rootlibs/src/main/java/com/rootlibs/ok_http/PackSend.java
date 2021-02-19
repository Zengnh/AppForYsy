package com.rootlibs.ok_http;

import java.util.HashMap;
import java.util.Map;

public class PackSend {
    public boolean isFile = false;
    public String url = "";
    public String token="";

    public Map<String, Object> upMap = new HashMap<>();
    public Map<String, Object> getUpMap() {
        return upMap;
    }
}
