package com.example.jiun.sookpam.model.vo

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer

import java.lang.reflect.Type

class RecordVOSerializer : JsonSerializer<RecordVO> {
    override fun serialize(src: RecordVO, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        val jsonObject = JsonObject()
        jsonObject.addProperty("category", src.category)
        jsonObject.addProperty("division", src.division)
        jsonObject.addProperty("keyword", src.keyword)
        jsonObject.addProperty("id", src.message!!.id)
        jsonObject.addProperty("phoneNumber", src.message!!.phoneNumber)
        jsonObject.addProperty("date", src.message!!.date.toString())
        jsonObject.addProperty("messageType", src.message!!.messageType)
        jsonObject.addProperty("body", src.message!!.body)
        return jsonObject
    }
}
