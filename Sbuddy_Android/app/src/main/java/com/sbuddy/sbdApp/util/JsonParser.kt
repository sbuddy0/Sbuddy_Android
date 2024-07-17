package com.sbuddy.sbdApp.util

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class JsonParser {
    companion object{
        @Throws(JSONException::class)
        fun getJsonData(data: String): HashMap<String, Any> {
            val jObj = JSONObject(data)
            val iKeys = jObj.keys()
            val map = HashMap<String, Any>()
            while (iKeys.hasNext()) {
                val key = iKeys.next()
                if (jObj[key] is JSONObject) {
                    map[key] = parse(jObj.getJSONObject(key))
                } else if (jObj[key] is JSONArray) {
                    map[key] = parse(jObj.getJSONArray(key))
                } else {
                    map[key] = jObj[key]
                }
            }
            return map
        }

        @Throws(JSONException::class)
        fun parse(jarr: JSONArray): Any {
            val list: ArrayList<HashMap<String, Any>> = ArrayList()
            val len = jarr.length()
            for (i in 0 until len) {
                val j = jarr.getJSONObject(i)
                list.add(getJsonData(j.toString()))
            }
            return list
        }

        @Throws(JSONException::class)
        private fun parse(jobj: JSONObject): Any {
            val map: HashMap<String, Any> = java.util.HashMap()
            val iter = jobj.keys()
            while (iter.hasNext()) {
                val key = iter.next()
                if (jobj[key] is JSONObject) {
                    map[key] = jobj.getString(key)
                } else if (jobj[key] is JSONArray) {
                    map[key] = parse(jobj.getJSONArray(key))
                } else {
                    map[key] = jobj.getString(key)
                }
            }
            return map
        }

    }
}