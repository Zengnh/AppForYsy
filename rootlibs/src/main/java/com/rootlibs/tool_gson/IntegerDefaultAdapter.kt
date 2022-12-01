package com.jsyllibs.tool_gson

import com.google.gson.*

import java.lang.reflect.Type



class IntegerDefaultAdapter : JsonSerializer<Int>, JsonDeserializer<Int> {
    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Int? {
        try {
            if (json.asString == "" || json.asString == "null") {//定义为int类型,如果后台返回""或者null,则返回0
                return 0
            }
        } catch (ignore: Exception) {
            ignore.printStackTrace()
        }

        try {
            return json.asInt
        } catch (e: NumberFormatException) {
            throw JsonSyntaxException(e)
        }

    }

    override fun serialize(src: Int?, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(src!!)
    }
}
