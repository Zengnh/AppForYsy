package com.jsyllibs.tool_gson

import com.google.gson.*

import java.lang.reflect.Type


class StringDefaultAdapter : JsonSerializer<String>, JsonDeserializer<String> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): String? {
        try {
            if (json.asString == "null") {
                return ""
            }
        } catch (ignore: Exception) {
            ignore.printStackTrace()
        }
        try {
            return json.toString();
        } catch (e: NumberFormatException) {
            throw JsonSyntaxException(e)
        }

    }

    override fun serialize(
        src: String?,
        typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement {
        return JsonPrimitive(src!!)
    }

}
