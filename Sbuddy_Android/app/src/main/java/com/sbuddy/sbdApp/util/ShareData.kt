package com.sbuddy.sbdApp.util

import android.content.Context

class ShareData {
    companion object{
        // 로그인 관련 프리퍼런스
        val LOGIN = "login"
        val LOGIN_SESSION = "login_session"
        val LOGIN_IDX_MEMBER = "login_idx_memeber"

        fun setStringData(context: Context, mainKey: String, subKey: String, strData:String ):Boolean{
            val editor = context.getSharedPreferences(mainKey, Context.MODE_PRIVATE).edit()
            editor.putString(subKey, strData)
            return editor.commit()
        }

        fun getStringData(context: Context, mainKey: String, subKey: String): String? {
            val sharedPreference = context.getSharedPreferences(mainKey, Context.MODE_PRIVATE)
            return sharedPreference.getString(subKey, "")
        }
    }
}