package com.rootlibs.ok_http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.rootlibs.google_gson.ToolGson;

import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class PackDown<T> {
    public int code = 0;
    public String message = "";
    //#########################################################
    public String contentCode;
    public T content;

    protected void fitData(String cts) {
        try {
            JSONObject jsonObject = new JSONObject(cts);
            contentCode = jsonObject.optString("code");
            String ct = jsonObject.optString("data");

            content = ToolGson.fromJson(ct, new TypeToken<PackHellowDown>() {
            }.getType());

//            ResponseType<PackHellowDown> resp = new Parser<PackHellowDown>().parse(ct);
//            content = (T) resp.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class ResponseType<T> {
        private T body;

        public T getBody() {
            return body;
        }
    }

    private static class Parser<T> {
        private static final Gson gson = new GsonBuilder().create();
        private final Class<?> clazz = ResponseType.class;

        public ResponseType<T> parse(String json) {
            try {
                ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
                Type objectType = buildType(clazz, type.getActualTypeArguments());
                return gson.fromJson(json, objectType);
            } catch (Exception ignored) {
            }
            return null;
        }

        private static ParameterizedType buildType(final Class<?> raw, final Type... args) {
            return new ParameterizedType() {
                public Type getRawType() {
                    return raw;
                }

                public Type[] getActualTypeArguments() {
                    return args;
                }

                public Type getOwnerType() {
                    return null;
                }
            };
        }
    }

}
