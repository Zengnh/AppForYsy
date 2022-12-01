package com.jsyllibs.tool_gson

import com.google.gson.Gson
import com.google.gson.GsonBuilder

object GsonUtils {
    val gson by lazy { buildGson() }
    private fun buildGson(): Gson {
        return GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .registerTypeAdapter(Int::class.java, IntegerDefaultAdapter())
            .registerTypeAdapter(Int::class.javaPrimitiveType, IntegerDefaultAdapter())
            .registerTypeAdapter(Double::class.java, DoubleDefaultAdapter())
            .registerTypeAdapter(Double::class.javaPrimitiveType, DoubleDefaultAdapter())
            .registerTypeAdapter(Long::class.java, LongDefaultAdapter())
            .registerTypeAdapter(Long::class.javaPrimitiveType, LongDefaultAdapter())
            .registerTypeHierarchyAdapter(List::class.java, ListDefaultAdapter())
            .create()
    }

//    .registerTypeAdapter(String::class.java, StringDefaultAdapter())
//    .registerTypeAdapter(String::class.javaPrimitiveType, StringDefaultAdapter())
}