package com.jsyllibs.tool_gson

import com.google.gson.*

import java.lang.reflect.Type


class LongDefaultAdapter : JsonSerializer<Long>, JsonDeserializer<Long> {
    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Long? {
        try {
            if (json.asString == "" || json.asString == "null") {//定义为long类型,如果后台返回""或者null,则返回0
                return 0L
            }
        } catch (ignore: Exception) {
        }

        try {
            return json.asLong
        } catch (e: NumberFormatException) {
            throw JsonSyntaxException(e)
        }

    }

    override fun serialize(src: Long?, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(src!!)
    }
}
