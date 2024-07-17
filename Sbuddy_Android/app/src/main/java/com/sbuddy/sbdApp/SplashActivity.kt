package com.sbuddy.sbdApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.sbuddy.sbdApp.login.LoginActivity
import com.sbuddy.sbdApp.util.MetaData
import com.sbuddy.sbdApp.util.ShareData

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        goMain()


    }
    fun goMain(){
        // 로그인 유지가 되어있으면 main으로 로그인 해야되면 로그인 페이지로 이동
        MetaData.token = ShareData.getStringData(this, ShareData.LOGIN, ShareData.LOGIN_SESSION).toString()
        if(MetaData.token != ""){
            Handler().postDelayed(Runnable {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, 2000)
        }else{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}