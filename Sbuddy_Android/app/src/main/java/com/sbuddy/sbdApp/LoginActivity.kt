package com.sbuddy.sbdApp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sbuddy.sbdApp.http.RetrofitService
import com.sbuddy.sbdApp.http.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
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

        val clientBuilder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        clientBuilder.addInterceptor(loggingInterceptor)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://13.238.152.155:8082/")
            .addConverterFactory(GsonConverterFactory.create())
            .client((clientBuilder.build()))
            .build()
        val retrofitService = retrofit.create(RetrofitService::class.java)

//        val loginMap = HashMap<String, String>()
//        loginMap.put("id", "tmddus2123")
//        loginMap.put("password", "1234")

        loginBtn.setOnClickListener{


            val user = User("tmddus2123", "1234")
            retrofitService.login(user).enqueue(object: Callback<Object> {

                override fun onResponse(call: Call<Object>, response: Response<Object>) {
                    Log.d("sbuddyy", "response : ${response}")
                    if(response.isSuccessful){

                        Toast.makeText(this@LoginActivity, "로그인 성공", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent);
                        finish()

                    }
                }

                override fun onFailure(call: Call<Object>, t: Throwable) {
                    Log.d("sbuddyy", "로그인 실패 : " + t.message)
                    Toast.makeText(this@LoginActivity, "로그인 실패 + ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })

//            val intent = Intent(this@LoginActivity, MainActivity::class.java)
//            startActivity(intent);
//            finish()
//            val map = HashMap<String, String>()
//            retrofitService.list(map).enqueue(object: Callback<Object>{
//                override fun onResponse(call: Call<Object>, response: Response<Object>) {
//                    Log.d("sbuddyy", "response : ${response}")
//                }
//
//                override fun onFailure(call: Call<Object>, t: Throwable) {
//                    Log.d("sbuddyy", "로그인 실패 : " + t.message)
//                    TODO("Not yet implemented")
//                }
//
//            })

        }
    }
}