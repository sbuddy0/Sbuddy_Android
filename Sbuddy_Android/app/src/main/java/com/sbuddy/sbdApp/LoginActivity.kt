package com.sbuddy.sbdApp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sbuddy.sbdApp.http.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LoginActivity : AppCompatActivity() {
    private lateinit var loginBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginBtn = findViewById(R.id.login_btn)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitService = retrofit.create(RetrofitService::class.java)

        val loginMap = HashMap<String, Any>()


        loginBtn.setOnClickListener{

//            retrofitService.login(loginMap).enqueue(object: Callback<Map<String, Any>> {
//                override fun onResponse(call: Call<Map<String, Any>>, response: Response<Map<String, Any>>) {
//                    if(response.isSuccessful){
//
//                        Toast.makeText(this@LoginActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
//
//                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
//                        startActivity(intent);
//                        finish()
//
//                    }
//                }
//
//                override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) {
//                }
//            })

            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent);
            finish()

        }
    }
}